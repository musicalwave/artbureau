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
    public String newContract(@RequestParam("client") int clientId, @RequestParam(value = "prev", defaultValue = "0") Integer prev, Model m) {
        m = authMaster.setModel(m);

        //Список направлений
        List<TypeModel> types = typesMapper.selectAllActive();
        m.addAttribute("types", types);

        // Список учителей по первому направлению
        TypeModel firstType = types.get(0);
        List<TeacherTypeModel> teacherTypes = teacherTypeMapper.getAllActiveByType(firstType.getId());
        m.addAttribute("teacherTypes", teacherTypes);

        // Расписание первого в списке учителя
        TeacherTypeModel firstTeacherType = teacherTypes.get(0);
        List<EventModel> emptyEvents = eventMaster.getEmptyEvents(firstTeacherType.getTeacherId());
        m.addAttribute("emptyEvents", emptyEvents);

        //Список типов контракта
        List<SimpleModel> contractTypes = contractMapper.selectContractTypes();
        m.addAttribute("contractTypes", contractTypes);

        int singleLessonPrice;
        int contractType = contractTypes.get(0).getId();

        //Выбор цены урока исходя из типа занятия и учителя
        singleLessonPrice = teacherTypeMaster.getPrice(
                firstTeacherType.getTeacherId(),
                firstType.getId(),
                contractType);

        m.addAttribute("singleLessonPrice", singleLessonPrice);

        //Список скидок
        List<SimpleModel> discounts = discountsMapper.selectAllActive();
        m.addAttribute("discounts", discounts);

        ClientModel client = clientsMapper.getClientById(clientId);

        ContractModel contract = new ContractModel();
        contract.setClientId(clientId);
        contract.setClientFS(client.getFname());
        contract.setClientLS(client.getLname());
        contract.setCountLessons(12);
        contract.setDiscount(discounts.get(0).getId());
        contract.setPrice(contract.getCountLessons() * singleLessonPrice * (100 - contract.getDiscount()) / 100);
        contract.setPrev(prev);

        m.addAttribute("contract", contract);

        return "/a/contract/new";
    }

    @RequestMapping(value = "/contract/save", method = RequestMethod.POST)
    public String saveNewContract(@ModelAttribute("contract") ContractModel contract, Model m, SessionStatus session) {
        authMaster.setModel(m);

        contract = contractMaster.saveFromWeb(contract);
        contractMaster.createSchedule(contract);
        session.setComplete();
        return "redirect:/client/" + contract.getClientId() + "/payment?c=" + contract.getId();
    }

    @RequestMapping(value="/contract/freeze", method = RequestMethod.POST)
    public String freezeContract(@ModelAttribute("contract") ContractModel c, Model m) throws ParseException {

        if(c != null && c instanceof ContractModel){

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

        if(c != null && c instanceof ContractModel){

            if(c.getCash() != 0){
                contractMaster.updateCash(c.getId(), c.getCash());
                return "redirect:/contract/" + c.getId();
            }
            throw new ApplicationException(ECode.ERROR409);
        }

        throw new ApplicationException(ECode.ERROR415);
    }
}
