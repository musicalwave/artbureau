package com.itsoft.ab.controllers.a;

import com.itsoft.ab.beans.*;
import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.model.*;
import com.itsoft.ab.persistence.*;
import com.itsoft.ab.sys.ECode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 28.02.14
 * Time: 0:08
 */
@Controller
@SessionAttributes({"contract"})
public class ContractController {
    Logger LOG = Logger.getLogger(ContractController.class);

    @Autowired
    private AuthMaster authMaster;

    @Autowired
    private LessonsMapper lessonsMapper;

    @Autowired
    private LessonMaster lessonMaster;

    @Autowired
    private ContractsMapper contractMapper;

    @Autowired
    private ClientsMapper clientsMapper;

    @Autowired
    private ContractMaster contractMaster;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private PaymentMaster paymentMaster;

    @Autowired
    private TeacherTypeMapper teacherTypeMapper;

    @Autowired
    private TeacherTypeMaster teacherTypeMaster;

    @Autowired
    private EventMaster eventMaster;

    @Autowired
    private TypesMapper typesMapper;

    @Autowired
    private DiscountsMapper discountsMapper;

    @Autowired
    private ContractsMapper contractsMapper;

    @Autowired
    private ContractScheduleMaster contractScheduleMaster;

    @RequestMapping(value="/contract/{contractId}", method = RequestMethod.GET)
    public String showContract(@PathVariable int contractId, Model m){
        m = authMaster.setModel(m);
        ContractModel contract = contractMapper.getContractById(contractId);

        if(contract == null){
            throw new ApplicationException(ECode.ERROR404);
        }

        contract = contractMaster.prepareContract(contract);

        ClientModel client = clientsMapper.getClientByContract(contractId);

        List<LessonModel> lessons = lessonsMapper.getContractLessons(contractId);

        List<PaymentModel> payments = paymentMapper.getContractPayments(contractId);

        m.addAttribute("lessons", lessonMaster.prepareLessons(lessons));
        m.addAttribute("contract", contract);
        m.addAttribute("client", client);
        m.addAttribute("payments", paymentMaster.preparePayments(payments));
        m.addAttribute("l", new LessonModel());

        return "/a/contract/show";
    }

    @RequestMapping(value="/contract/new", method = RequestMethod.GET)
    public String newContract(@RequestParam("client") int clientId,
                              @RequestParam(value = "prev", defaultValue = "0") Integer prev,
                              @RequestParam(value = "contractOptionId", defaultValue = "0") Integer contractOptionId,
                              @RequestParam(value = "typeId", defaultValue = "0") Integer typeId,
                              @RequestParam(value = "teacherId", defaultValue = "0") Integer teacherId,
                              Model m) {
        m = authMaster.setModel(m);

        //Список направлений
        List<TypeModel> types = typesMapper.selectAllActive();
        m.addAttribute("types", types);

        // Список учителей по первому направлению
        int selectedTypeId = typeId == 0 ? types.get(0).getId() : typeId;
        List<TeacherTypeModel> teacherTypes = teacherTypeMapper.getAllActiveByType(selectedTypeId);
        m.addAttribute("teacherTypes", teacherTypes);

        // Расписание первого в списке учителя
        int selectedTeacherId = teacherId == 0 ? teacherTypes.get(0).getTeacherId() : teacherId;
        List<EventModel> emptyEvents = eventMaster.getEmptyEvents(selectedTeacherId);
        m.addAttribute("emptyEvents", emptyEvents);

        //Список типов контракта
        List<SimpleModel> contractTypes = contractMapper.selectContractTypes();
        m.addAttribute("contractTypes", contractTypes);

        int singleLessonPrice;
        int contractType = contractTypes.get(0).getId();

        //Выбор цены урока исходя из типа занятия и учителя
        singleLessonPrice = teacherTypeMaster.getPrice(
                selectedTeacherId,
                selectedTypeId,
                contractType);

        m.addAttribute("singleLessonPrice", singleLessonPrice);

        //Список скидок
        List<SimpleModel> discounts = discountsMapper.selectAllActive();
        m.addAttribute("discounts", discounts);


        ContractModel contract = new ContractModel();

        ClientModel client = clientsMapper.getClientById(clientId);
        contract.setClientId(clientId);
        contract.setClientFS(client.getFname());
        contract.setClientLS(client.getLname());

        contract.setDiscount(discounts.get(0).getId());

        List<ContractOptionModel> contractOptions = contractMapper.selectContractOptions();
        m.addAttribute("contractOptions", contractOptions);

        ContractOptionModel contractOption =
            contractOptionId == 0
                    ? contractOptions.get(0)
                    : contractMapper.getContractOptionById(contractOptionId);

        contract.setContractOptionId(contractOption.getId());
        contract.setCountLessons(contractOption.getLessonCount());
        contract.setPrice(contract.getCountLessons() * singleLessonPrice * (100 - contract.getDiscount()) / 100);

        contract.setPrev(prev);
        contract.setTypeId(selectedTypeId);

        int selectedTeacherTypeId =  teacherTypeMapper.getTypeByTeacherAndType(
                selectedTeacherId, selectedTypeId).get(0).getId();
        contract.setTeacherTypeId(selectedTeacherTypeId);

        m.addAttribute("contract", contract);

        return "/a/contract/new";
    }

    @RequestMapping(value = "/contract/save", method = RequestMethod.POST)
    public String saveNewContract(@ModelAttribute("contract") ContractModel contract,
                                  Model m,
                                  SessionStatus session) {
        authMaster.setModel(m);

        contract = contractMaster.saveFromWeb(contract);
        for(PaymentModel payment : contract.getPayments())
            if(!(payment.getDateS() == null || payment.getDateS().isEmpty())) { // means that the payment WAS added on the form
                payment.setContractId(contract.getId());
                paymentMaster.savePaymentFromWeb(payment);
            }

        contractMaster.updateSchedule(contract.getId(), contract.getDays().split(","));
        contractMaster.replanLessons(contract);
        session.setComplete();
        return "redirect:/client/" + contract.getClientId();
    }

    @RequestMapping(value="/contract/cash", method = RequestMethod.POST)
    public String statusContract(@ModelAttribute("contract") ContractModel c, Model m) throws ParseException {

        if(c != null){

            if(c.getCash() != 0){
                contractMaster.updateCash(c.getId(), c.getCash());
                return "redirect:/contract/" + c.getId();
            }
            throw new ApplicationException(ECode.ERROR409);
        }

        throw new ApplicationException(ECode.ERROR415);
    }

    // Ajax

    @RequestMapping(value = "/do/contract/lessons", method = RequestMethod.GET)
    public
    @ResponseBody
    List<LessonModel> getContractLessons(@RequestParam(value = "contractId") int contractId) {
        return lessonsMapper.getContractLessons(contractId);
    }

    @RequestMapping(value = "/do/contract/schedule", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ContractScheduleModel> getContractSchedule(@RequestParam(value = "contractId") int contractId) {
        return contractScheduleMaster.getContractSchedule(contractId);
    }

    @RequestMapping(value = "/do/contract/payments", method = RequestMethod.GET)
    public
    @ResponseBody
    List<PaymentModel> getContractPayments(@RequestParam(value = "contractId") int contractId) {
        return paymentMapper.getContractPayments(contractId);
    }

    @RequestMapping(value = "/do/contract/freeze", method = RequestMethod.POST)
    public
    @ResponseBody
    void freezeContract(@RequestParam(value = "contractId") int contractId,
                        @RequestParam(value = "lockFrom") long lockFromDate,
                        @RequestParam(value = "lockTo") long lockToDate) {
        contractMaster.freezeContract(contractId,
                                      new Date(lockFromDate),
                                      new Date(lockToDate));
    }

    @RequestMapping(value = "/do/contract/unfreeze", method = RequestMethod.POST)
    public
    @ResponseBody
    void unfreezeContract(@RequestParam(value = "contractId") int contractId) {
        ContractModel contract = contractsMapper.getContractById(contractId);
        contract.setFreezed(0);
        contractsMapper.freezeContract(contract);
    }

    @RequestMapping(value = "/do/contract/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    void deleteContract(@RequestParam(value = "contractId") int contractId) {
        //contractsMapper.deleteContract(contractId);
        contractsMapper.updateStatus(contractId, 3);
    }

    @RequestMapping(value = "/do/contract/restore", method = RequestMethod.POST)
    public
    @ResponseBody
    void restoreContract(@RequestParam(value = "contractId") int contractId) {
        if(contractsMapper.getPlannedLessonCount(contractId) == 0)
            contractsMapper.updateStatus(contractId, 2); // finished
        else
            contractsMapper.updateStatus(contractId, 1); // active
    }

    @RequestMapping(value = "/do/contract/writeoff", method = RequestMethod.POST)
    public
    @ResponseBody
    void writeoffContract(@RequestParam(value = "contractId") int contractId) {
        contractMaster.writeoff(contractId);
    }

    @RequestMapping(value = "/do/contract/cashback", method = RequestMethod.POST)
    public
    @ResponseBody
    void cashbackContract(@RequestParam(value = "contractId") int contractId) {
        contractMaster.cashback(contractId);
    }

    @RequestMapping(
        value = "/do/contract/schedule/insert",
        method = RequestMethod.POST,
        headers = {"Content-type=application/json"})
    public
    @ResponseBody
    void insertContractScheduleItem(@RequestBody ContractScheduleModel item) {
        contractMaster.insertScheduleItem(item);
    }

    @RequestMapping(
        value = "/do/contract/schedule/update",
        method = RequestMethod.POST,
        headers = {"Content-type=application/json"}
    )
    public
    @ResponseBody
    void updateContractScheduleItem(@RequestBody ContractScheduleModel item) {
        contractMaster.updateScheduleItem(item);
    }

    @RequestMapping(
        value = "/do/contract/schedule/delete",
        method = RequestMethod.POST,
        headers = {"Content-type=application/json"}
    )
    public
    @ResponseBody
    void deleteContractScheduleItem(@RequestBody ContractScheduleModel item) {
        contractMaster.deleteScheduleItem(item);
    }

    @RequestMapping(value = "/do/contract/option", method = RequestMethod.GET)
    public
    @ResponseBody
    ContractOptionModel getContractOption(@RequestParam(value = "id") int id) {
        return contractsMapper.getContractOptionById(id);
    }

    @RequestMapping(value = "/do/contract/create",
                    method = RequestMethod.POST,
                    headers = {"Content-type=application/json"})
    public
    @ResponseBody
    int createContract(@RequestBody ContractModel contract) {
        return contractMaster.createContract(contract);
    }


}
