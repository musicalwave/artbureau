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

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"callObj", "clientObj"})
public class CallsController{
    Logger LOG = Logger.getLogger(CallsController.class);

    @Autowired
    private AuthMaster authMaster;

    @Autowired
    private CallMaster callMaster;

    @Autowired
    private CallsMapper callsMapper;

    @Autowired
    private CallsStatusMapper callsStatusMapper;

    @Autowired
    private AdsMapper adsMapper;

    @Autowired
    private TypesMapper typesMapper;

    @Autowired
    private ClientsMaster clientsMaster;

    @Autowired
    private ClientsMapper clientsMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    //Обработка запроса Создать новый звонок
    @RequestMapping(value = "/call", method = RequestMethod.GET)
    public String newCall(@RequestParam("client")String client, Model m){
        m = authMaster.setModel(m);

        m.addAttribute("ads", adsMapper.selectAll());
        m.addAttribute("types", typesMapper.selectAll());

        List<TeacherModel> teachers = new ArrayList<>();
        teachers.add(TeacherMaster.getEmptyTeacher());
        teachers.addAll(teacherMapper.getActiveTeachers());
        m.addAttribute("teachers", teachers);

        m.addAttribute("callStatus", callsStatusMapper.selectAll());

        if(client.equals("new")) {
            CallWeb c = new CallWeb();
            m.addAttribute("call", c);
            return "/a/calls/new";
        }
        else {
            try {
                int clientId = Integer.parseInt(client);
                ClientModel clientObj = clientsMapper.getClientById(clientId);
                CallWeb c = new CallWeb(clientObj);
                m.addAttribute("call", c);
                m.addAttribute("clientId", clientId);
                return "/a/calls/new2";
            }
            catch(NumberFormatException nfe) {
                LOG.error("Передан неверный аттрибут client");
            }

            throw new ApplicationException(ECode.ERROR500);
        }
    }


    //Обработка запроса Сохранить звонок
    @RequestMapping(value = "/call", method = RequestMethod.POST)
    public String saveCall(@ModelAttribute CallWeb cw, @RequestParam String action, Model m){

        ClientModel client = callMaster.getClientFromWeb(cw);
        CallModel call = callMaster.getCallFromWeb(cw);

        m.addAttribute("clientObj", client);
        m.addAttribute("callObj", call);

        switch (action.trim()) {
            case "save":
                List<ClientModel> clients = clientsMapper.findClients(null, null, client.getPhone1(), null, null, null);

                if(clients.isEmpty()) {
                    client = clientsMaster.insertClient(client);
                    call.setClientId(client.getId());
                    callMaster.insertCall(call);

                    return "redirect:/client/" + client.getId();
                }
                else {
                    call.setId(0);
                    m = authMaster.setModel(m);
                    m.addAttribute("clients", clients);
                    m.addAttribute("callId", call.getId());
                    m.addAttribute("postModel", new SimpleModel());
                    return "/a/calls/clients";
                }

            case "update" :
                callMaster.updateCall(call);
                return "redirect:/client/" + client.getId();

            default :
                throw new ApplicationException(ECode.ERROR404);
        }
    }

    @RequestMapping(value = "/call/{clientId}", method = RequestMethod.POST)
    public String saveClientCall(@ModelAttribute CallWeb cw, @PathVariable String clientId, Model m){
        int id = Integer.parseInt(clientId);
        if(id == (int)id){
            clientsMaster.checkClientId(id);

            CallModel call = callMaster.getCallFromWeb(cw);
            call.setClientId(id);
            callMaster.insertCall(call);

            return "redirect:/client/" + id;
        }
        LOG.error("Введен неверный идентификатор клиента id=" + clientId);
        throw new ApplicationException(ECode.ERROR500);
    }

    @RequestMapping(value = "/call/use", method = RequestMethod.POST)
    public String useClientCall(@RequestParam("client") String client, @ModelAttribute("clientObj")ClientModel clientObj, @ModelAttribute("callObj")CallModel callObj, Model m, SessionStatus status){

        if(client != null && clientObj != null && callObj != null){
            if(client.equals("new")){

                clientObj = clientsMaster.insertClient(clientObj);
                callObj.setClientId(clientObj.getId());
                callObj = callMaster.insertCall(callObj);

                //Сброс сессии
                status.setComplete();
                return "redirect:/client/" + clientObj.getId();
            }else{

                Integer clientId = Integer.parseInt(client);
                if(!client.equals(null)){
                    callObj.setClientId(clientId);
                    callObj = callMaster.insertCall(callObj);

                    //Сброс сессии
                    status.setComplete();
                    return "redirect:/client/" + clientId;
                }
            }

        }

        throw new ApplicationException(ECode.ERROR404);
    }

    //Обработка запроса Изменить звонок
    @RequestMapping(value="/call/{callId}", method = RequestMethod.GET)
    public String editCall(@PathVariable String callId, Model m){
        m = authMaster.setModel(m);
        CallModel call = callMaster.prepareCall(callsMapper.getCallById(Integer.parseInt(callId)));
        CallWeb callWeb = callMaster.prepareCallWeb(call);

        m.addAttribute("call", callWeb);
        m.addAttribute("ads", adsMapper.selectAll());
        m.addAttribute("types", typesMapper.selectAll());

        List<TeacherModel> teachers = new ArrayList<>();
        teachers.add(TeacherMaster.getEmptyTeacher());

        List<String> callTypes = callWeb.getCallTypeIds();
        if(callTypes == null || callTypes.isEmpty())
            teachers.addAll(teacherMapper.getActiveTeachers());
        else
            teachers.addAll(teacherMapper.getActiveTeachersByTypes(
                    callTypes.toArray(new String[callTypes.size()])));
        m.addAttribute("teachers", teachers);

        m.addAttribute("callStatus", callsStatusMapper.selectAll());
        return "/a/calls/edit";
    }

    //Обработка запроса Удалить звонок
    @RequestMapping(value="/call/{callId}/delete", method = RequestMethod.GET)
    public String deleteCall(@PathVariable String callId, Model m){
        callMaster.deleteCallModel(Integer.parseInt(callId));
        return "redirect:/calls";
    }

    //Обработка запроса Звонки с ошибкой
    @RequestMapping(value="/calls/e", method = RequestMethod.GET)
    public String errorCalls(Model m){
        throw new ApplicationException(ECode.ERROR404);
    }

    //Обработка запроса Показать все активные звонки
    @RequestMapping(value="/calls", method = RequestMethod.GET)
    public String init(Model m)  {
        m = authMaster.setModel(m);
        List<CallModel> calls = callMaster.getCalls(null,null);
        List<CallModel> cws = new ArrayList<CallModel>();

        if(!calls.isEmpty()){
            for (CallModel call : calls){
                cws.add(callMaster.prepareCall(call));
            }
        }

        m.addAttribute("calls", cws);
        return "/a/calls/list";
    }

} 