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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 28.02.14
 * Time: 0:08
 */
@Controller
@SessionAttributes({"contractObj"})
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

    @RequestMapping(value="/contract", method = RequestMethod.GET)
    public String newContract(@RequestParam("client") int clientId, @RequestParam(value = "prev", defaultValue = "0") Integer prev, Model m){
        m = authMaster.setModel(m);

        //Список направлений
        List<TypeModel> types = typesMapper.selectAllActive();
        m.addAttribute("types", types);

        ClientModel client = clientsMapper.getClientById(clientId);

        //Список типов контракта
        List<SimpleModel> ctypes = contractMapper.selectContractTypes();
        m.addAttribute("ctypes", ctypes);

        //Список скидок
        List<SimpleModel> discounts = discountsMapper.selectAllActive();
        m.addAttribute("discounts", discounts);

        //Создание сессионного объекта
        ContractModel contractObj = new ContractModel();
        contractObj.setClientId(clientId);
        contractObj.setClientFS(client.getFname());
        contractObj.setClientLS(client.getLname());
        contractObj.setPrev(prev);

        m.addAttribute("contractObj", contractObj);

        return "/a/contract/new1";
    }

    @RequestMapping(value="/contract", method = RequestMethod.POST)
    public String newContract2(@RequestParam("s") int step, @ModelAttribute("contractObj") ContractModel c, Model m, SessionStatus status) throws ParseException {
        m = authMaster.setModel(m);

        if(step == 2){
            return "redirect:/contract/step2";
        }

        if(step == 3){
            return "redirect:/contract/step3";
        }

        if(step == 4){
            //TODO Check out the contract object

            // this line inserts contact into DB
            // need to check for correct schedule before that
            c = contractMaster.saveFromWeb(c);

            try{
                contractMaster.createSchedule(c);
            }catch(Exception e){
                e.printStackTrace();
                throw new ApplicationException(ECode.ERROR1106);
            }

            //Сброс сессии
            status.setComplete();

            return "redirect:/client/" + c.getClientId() + "/payment?c=" + c.getId();
        }

        throw new ApplicationException(ECode.ERROR415);
    }

    @RequestMapping(value="/contract/step2", method = RequestMethod.GET)
    public String step2Contract(@ModelAttribute("contractObj") ContractModel c, Model m) throws ParseException {
        m = authMaster.setModel(m);

        List<TeacherTypeModel> teachers = teacherTypeMapper.getAllActiveByType(Integer.parseInt(c.getTypeS()));

        m.addAttribute("contractObj", c);
        m.addAttribute("ts", teachers);

        return "/a/contract/new2";
    }

    @RequestMapping(value="/contract/step3", method = RequestMethod.GET)
    public String step3Contract(@ModelAttribute("contractObj") ContractModel c, Model m) throws ParseException {
        m = authMaster.setModel(m);

        TeacherTypeModel t = teacherTypeMapper.getById(c.getTeacherTypeId());

        List<EventModel> events = eventMaster.getEmptyEventsByDate(t.getTeacherId(), c.getDateS());

        int price1 = 0;

        //Выбор цены урока исходя из типа занятия
        if(c.getContractType() == 1){
            price1 = t.getpPrice();
        }if(c.getContractType() == 2){
            price1 = t.getgPrice();
        }if(c.getContractType() == 3){
            price1 = t.getdPrice();
        }if(c.getContractType() == 4){
            price1 = t.getaPrice();
        }

        c.setPrice(c.getCountLessons() * price1 * (100 - c.getDiscount()) / 100);

        m.addAttribute("price1", price1);
        m.addAttribute("contractObj", c);
        m.addAttribute("t", t);
        m.addAttribute("events", events);

        return "/a/contract/new3";
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
