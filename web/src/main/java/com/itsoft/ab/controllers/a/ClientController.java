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

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 02.12.13
 * Time: 3:12
 */
@Controller
public class ClientController {
    private Logger LOG = Logger.getLogger(ClientController.class);

    @Autowired
    private ClientsMapper clientsMapper;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private AuthMaster authMaster;

    @Autowired
    private ContractsMapper contractMapper;

    @Autowired
    private CallsMapper callsMapper;

    @Autowired
    private ClientsMaster clientsMaster;

    @Autowired
    private ContractMaster contractMaster;

    @Autowired
    private ContractsMapper contractsMapper;

    @Autowired
    private EventMaster eventMaster;

    @Autowired
    private LessonsMapper lessonsMapper;

    @Autowired
    private ScheduleMapper scheduleMapper;

    @RequestMapping(value="/client", method = RequestMethod.GET)
    public String newClient(Model m){
        m = authMaster.setModel(m);
        m.addAttribute("client", new ClientModel());
        return "/a/client/new";
    }

    @RequestMapping(value="/client/{clientId}", method = RequestMethod.GET)
    public String showClient(@PathVariable String clientId, Model m){
        m = authMaster.setModel(m);
        ClientModel client = clientsMaster.getClientById(Integer.parseInt(clientId));
        m.addAttribute("client", client);
        return "/a/client/show";
    }

    @RequestMapping(value="/client", method = RequestMethod.POST)
    public String editClient(@ModelAttribute ClientModel client, Model m){
        clientsMaster.updateClient(client);
        return "redirect:/client/" + client.getId();
    }

    @RequestMapping(value="/client/new", method = RequestMethod.POST)
    public String newClient(@ModelAttribute ClientModel client, Model m){
        client = clientsMaster.insertClient(client);
        return "redirect:/client/" + client.getId();
    }

    @RequestMapping(value="/clients", method = RequestMethod.GET)
    public String initClients(Model m){
        return "redirect:/client/site";
    }

    @RequestMapping(value="/client/site", method = RequestMethod.GET)
    public String siteClients(Model m){
        m = authMaster.setModel(m);
        List<ClientModel> clients = clientsMapper.getSiteUndoneClients();
        m.addAttribute("clients", clients);
        return "/a/client/site";
    }

    @RequestMapping(value="/client/search", method = RequestMethod.GET)
    public String searchClients(Model m){
        m = authMaster.setModel(m);
        ClientModel c = new ClientModel();
        List<ClientModel> clients = new ArrayList<ClientModel>();
        m.addAttribute("clients", clients);
        m.addAttribute("client", c);
        return "/a/client/search";
    }

    @RequestMapping(value="/client/search", method = RequestMethod.POST)
    public String postSearchClient(@ModelAttribute ClientModel client, Model m){
        m = authMaster.setModel(m);

        //Подготавливаем данные для поиска
        if(client.getFname().isEmpty()){
            client.setFname(null);
        }
        if(client.getLname().isEmpty()){
            client.setLname(null);
        }
        if(client.getPhone1().isEmpty()){
            client.setPhone1(null);
        }
        if(client.getBdate().isEmpty()){
            client.setBdate(null);
        }
        if(client.getEmail().isEmpty()){
            client.setEmail(null);
        }
        if(client.getComment().isEmpty()){
            client.setComment(null);
        }

        List<ClientModel> result = clientsMapper.findClients(client.getFname(), client.getLname(), client.getPhone1(), client.getBdate(), client.getEmail(), client.getComment());

        if(result != null){
            m.addAttribute("clients", result);
        } else{
            m.addAttribute("clients", new ArrayList<ClientModel>());
        }
        m.addAttribute("client", client);
        return "/a/client/search";
    }

    @RequestMapping(value="/client/stats", method = RequestMethod.GET)
    public String statClients(Model m){
        throw new ApplicationException(ECode.ERROR503);
    }

    @RequestMapping(value="/client/{clientId}/contracts", method = RequestMethod.GET)
    public String allContracts(@PathVariable String clientId, Model m){
        m = authMaster.setModel(m);
        ClientModel client = clientsMaster.getClientById(Integer.parseInt(clientId));
        List<ContractModel> contracts = contractMapper.getClientContracts(client.getId());

        m.addAttribute("client", client);
        m.addAttribute("contracts", contracts);
        return "/a/client/contracts";
    }



    ////ТопМенеджеры
    @RequestMapping(value="/tm/jdata/{clientId}", method = RequestMethod.GET)
    public String setJdata(@PathVariable int clientId, Model m){
        m = authMaster.setModel(m);
        ClientModel c = clientsMaster.getClientById(clientId);
        m.addAttribute("client", c);
        return "/tm/client/jdata";
    }

    @RequestMapping(value="/tm/jdata", method = RequestMethod.POST)
    public String postJdata(@ModelAttribute ClientModel client, Model m){
        clientsMapper.updateJdata(client);
        return "redirect:/client/" + client.getId();
    }

    @RequestMapping(value="/tm/client/{clientId}/delete", method = RequestMethod.POST)
    public String deleteClient(@PathVariable int clientId, Model m){
        //Удаление клиента
        //Договора, занятия, платежи
        clientsMaster.deleteClient(clientId);
        return "redirect:/client/search";
    }

    // Ajax

    @RequestMapping(value = "/do/client", method = RequestMethod.GET)
    public
    @ResponseBody
    ClientModel getClient(@RequestParam(value = "id") int id) {
       return clientsMaster.getClientWithDataById(id);
    }

    @RequestMapping(value = "/do/client", method = RequestMethod.POST)
    public
    @ResponseBody
    ClientModel updateClient(@RequestParam(value = "id") int id,
                             @RequestParam(value = "fname") String fname,
                             @RequestParam(value = "lname") String lname,
                             @RequestParam(value = "pname") String pname,
                             @RequestParam(value = "phone1") String phone1,
                             @RequestParam(value = "email") String email,
                             @RequestParam(value = "bdate") String bdate) {
        ClientModel client = clientsMapper.getClientById(id);
        client.setFname(fname);
        client.setLname(lname);
        client.setPname(pname);
        client.setPhone1(phone1);
        client.setEmail(email);
        client.setBdate(bdate);
        clientsMapper.updateClient(client);
        return getClient(id);
    }

    @RequestMapping(value = "/do/client/contracts", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ContractModel> getClientContracts(@RequestParam(value = "clientId") int clientId) {
        List<ContractModel> contracts = contractsMapper.getClientContracts(clientId);
        for(ContractModel contract : contracts) {
            contract.setAvailableLessons(contractsMapper.getPlannedLessonCount(contract.getId()));
            List<LessonModel> lessons = lessonsMapper.getContractLessons(contract.getId());
            contract.setLessons(lessons);
            contract.setCountLessons(lessons == null ? 0 : lessons.size());
            contract.setPayments(paymentMapper.getContractPayments(contract.getId()));
            contract.setContractOptionModel(
                contractsMapper.getContractOptionById(contract.getContractOptionId()));
            contract.setTeacherEvents(eventMaster.getEmptyEvents(contract.getTeacherId()));
            contract.setSchedule(scheduleMapper.getContractSchedule(contract.getId()));
            contract.setPrice(contractMapper.getPrice(contract.getId()));
            contract.setBalance(contractMaster.getContractBalance(contract.getId()));
        }
        return contracts;
    }
}
