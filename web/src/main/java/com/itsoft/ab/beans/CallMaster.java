package com.itsoft.ab.beans;

import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.model.*;
import com.itsoft.ab.persistence.*;
import com.itsoft.ab.sys.ECode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 27.01.14
 * Time: 14:45
 */
@Service
public class CallMaster {
    Logger LOG = Logger.getLogger(CallMaster.class);
    @Autowired
    private ClientsMapper clientsMapper;

    @Autowired
    private ClientsMaster clientsMaster;

    @Autowired
    private AuthMaster authMaster;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private CallsMapper callsMapper;

    @Autowired
    private CallsStatusMapper callsStatusMapper;

    @Autowired
    private AdsMapper adsMapper;

    @Autowired
    private JCallsMapper jCallsMapper;

    public void saveCallWeb(CallModel call){
        //Проверяем обозначенного клиента
        if(call.getClientId() == 0){
            //Новый клиент

            //Проверяем только первый телефон
            List<ClientModel> clients = clientsMapper.findClients(null, null, call.getPhone1(), null, null, null);
            if(clients.size() == 0){
                //Совпадений в бд не нашлось
                //Создаем нового клиента
                ClientModel client = new ClientModel();
                client.setFname(call.getClientFName());
                client.setLname(call.getClientLName());
                client.setPhone1(call.getPhone1());
                client.setPhone2(call.getPhone2());
                client.setComment(call.getClientComment());
                clientsMapper.insertClient(client);

                //Получаем id созданного клиента
                List<ClientModel> c = clientsMapper.findClients(null, null, call.getPhone1(), null, null, null);
                if(c.size() == 1){
                    for(ClientModel cc : c){
                        call.setClientId(cc.getId());
                    }
                }else
                {
                    LOG.error("ERROR#1104 Ошибка создания клиента: размер массива объектов, полученных из базы данных не соответствует ожидаемому: c.size()=" + c.size());
                    throw new ApplicationException(ECode.ERROR1104);
                }
            } else if(clients.size() == 1){
                for(ClientModel cc : clients){
                    call.setClientId(cc.getId());
                }
            } else {
                LOG.error("ERROR#1104 Ошибка создания клиента: размер массива объектов, полученных из базы данных не соответствует ожидаемому: c.size()=" + clients.size());
                throw new ApplicationException(ECode.ERROR1104);
            }
        }else if(call.getClientId() > 0){
            //Клиент известен
            ClientModel client = new ClientModel();
            client.setId(call.getClientId());
            client.setFname(call.getClientFName());
            client.setLname(call.getClientLName());
            client.setPhone1(call.getPhone1());
            client.setPhone2(call.getPhone2());
            client.setComment(call.getClientComment());
            clientsMapper.updateClientPart(client);
        }else{
            throw new ApplicationException(ECode.ERROR415);
        }

        //Обработка комментария
        if(call.getCommentNew() != null && call.getCommentNew().trim() != ""){
            call.setComment(call.getComment() + "<b>" + authMaster.getLoggedUserName() + ":</b><br>" + call.getCommentNew());
        }


        //Проверка новый ли звонок
        if(call.getId() == 0){
            //Новый звонок
            call.setDate(new Date());

            //Сохраняем в бд
            callsMapper.insertCall(call);
        }else if(call.getId() > 0){
            //Изменяем звонок
            callsMapper.updateCall(call);
        }else{
            throw new ApplicationException(ECode.ERROR415);
        }
    }

    public void deleteCallModel(int callId){
        callsMapper.deleteCall(callId);
    }

    public List<CallModel> getCalls(Date fromDate, Date toDate){
        List<CallModel> calls = new ArrayList<CallModel>();
        if(fromDate == null && toDate == null){
            calls = callsMapper.getAllCalls();
        } else if (fromDate == null && toDate != null){
            calls = callsMapper.getCallsTo(toDate);

        } else if (fromDate != null && toDate == null){
            calls = callsMapper.getCallsFrom(fromDate);

        } else if (fromDate != null && toDate != null) {
            calls = callsMapper.getCalls(fromDate, toDate);

        }
        return calls;
    }

    //TODO использовать prepareCallNew
    public CallModel prepareCall(CallModel call){
        call.setDateS(new SimpleDateFormat("dd MMMM yyyy HH:mm").format(call.getDate()));
        call.setStatusName(callsStatusMapper.selectById(call.getStatusId()).getName());
        ClientModel client = clientsMapper.getClientById(call.getClientId());
        call.setClientFName(client.getFname());
        call.setClientLName(client.getLname());
        call.setClientComment(client.getComment());
        call.setPhone1(client.getPhone1());
        call.setAdS(adsMapper.selectById(call.getAdId()).getName());

        return call;
    }

    public CallWeb prepareCallWeb(CallModel call) {
        ClientModel client = clientsMapper.getClientById(call.getClientId());
        return new CallWeb(client, call);
    }

    public CallModel prepareCallNew(CallModel call){
        call.setDateS(new SimpleDateFormat("dd MMMM yyyy HH:mm").format(call.getDate()));
        TeacherModel teacher = teacherMapper.getTeacherById(call.getTeacherId());
        if(teacher != null)
            call.setTeacherName(teacher.getName());
        return call;
    }

    public List<CallModel> prepareCallsNew(List<CallModel> calls){
        List<CallModel> l = new ArrayList<CallModel>();
        for(CallModel call : calls){
            l.add(prepareCallNew(call));
        }
        return l;
    }

    public ClientModel getClientFromWeb(CallWeb cw) {
        ClientModel client = new ClientModel();

        String c;

        client.setId(cw.getClientId());

        c = cw.getClientFname();
        if(c != null && !c.equals("")){
            client.setFname(c.trim());
        }

        c = cw.getClientLname();
        if(c != null && !c.equals("")){
            client.setLname(c.trim());
        }

        c = cw.getClientPname();
        if(c != null && !c.equals("")){
            client.setPname(c.trim());
        }

        c = cw.getClientPhone1();
        if(c != null && !c.equals("")){
            client.setPhone1(c.trim());
        }

        c = cw.getClientPhone1name();
        if(c != null && !c.equals("")){
            client.setPhone1name(c.trim());
        }

        c = cw.getClientPhone2();
        if(c != null && !c.equals("")){
            client.setPhone2(c.trim());
        }

        c = cw.getClientPhone2name();
        if(c != null && !c.equals("")){
            client.setPhone2name(c.trim());
        }

        c = cw.getClientComment();
        if(c != null && !c.equals("")){
            client.setComment(c.trim());
        }

        c = cw.getClientMail();
        if(c != null && !c.equals("")){
            client.setEmail(c.trim());
        }
        return client;
    }

    public CallModel getCallFromWeb(CallWeb cw) {
        CallModel call = new CallModel();


        call.setId(cw.getCallId());
        call.setAdId(cw.getCallAdId());
        call.setClientId(cw.getClientId());
        call.setTypeIdsList(cw.getCallTypeIds());
        call.setStatusId(cw.getCallStatusId());
        call.setContractOptionId(cw.getContractOptionId());

        String comment = prepareCallComment(cw.getCallComment(), cw.getCallCommentNew());
        if(comment != null && !comment.isEmpty())
            call.setComment(comment.trim());

        int teacherId = cw.getCallTeacherId();
        if(teacherId != 0)
            call.setTeacherId(teacherId);

        return call;
    }

    public CallModel insertCall(CallModel call) {
        clientsMaster.checkClientId(call.getClientId());
        callsMapper.insertCall(call);
        callsMapper.insertCallTypes(getCallTypeModelList(call));

        return call;
    }

    public void updateCall(CallModel call) {
        callsMapper.updateCall(call);
        callsMapper.deleteCallTypes(call.getId());
        List<CallTypeModel> callTypes = getCallTypeModelList(call);
        if(!callTypes.isEmpty())
            callsMapper.insertCallTypes(getCallTypeModelList(call));
    }

    private String prepareCallComment(String comment, String newComment) {
        if(comment == null || comment.isEmpty())
            return newComment;
        else if (newComment == null || newComment.isEmpty())
            return comment;
        else
            return (comment + "\n" + newComment).trim();
    }

    private List<CallTypeModel> getCallTypeModelList(CallModel callModel) {
        List<String> typeIdsList = callModel.getTypeIdsList();
        List<CallTypeModel> callTypeModels = new ArrayList<>();
        if(typeIdsList != null)
            for(String typeId: typeIdsList)
                callTypeModels.add(new CallTypeModel(callModel.getId(), Integer.parseInt(typeId)));
        return callTypeModels;
    }
}
