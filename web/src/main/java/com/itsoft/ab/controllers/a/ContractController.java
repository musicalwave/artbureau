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
        contractMaster.planLessons(contract);
        session.setComplete();
        return "redirect:/client/" + contract.getClientId();
    }

    @RequestMapping(value="/contract/freeze", method = RequestMethod.POST)
    public String freezeContract(@ModelAttribute("contract") ContractModel c, Model m) throws ParseException {

        if(c != null){

            if(c.getFreezeDateS() != null && c.getFreezeFinishDateS() != null){
                lessonMaster.freezeContract(c);
                return "redirect:/contract/" + c.getId();
            }
            throw new ApplicationException(ECode.ERROR409);
        }

        throw new ApplicationException(ECode.ERROR415);
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
}
