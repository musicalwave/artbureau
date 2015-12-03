package com.itsoft.ab.beans;

import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.model.ClientModel;
import com.itsoft.ab.model.JClientsModel;
import com.itsoft.ab.persistence.ClientsMapper;
import com.itsoft.ab.persistence.JClientsMapper;
import com.itsoft.ab.sys.ECode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 16.05.14
 * Time: 10:31
 */
@Service
public class ClientsMaster {
    private Logger LOG = Logger.getLogger(ClientsMaster.class);

    @Autowired
    private JClientsMapper j_clients_mapper;

    @Autowired
    private ClientsMapper clientsMapper;

    @Autowired
    private AuthMaster authMaster;

    public void deleteClient(int id){
        ClientModel client = clientsMapper.getClientById(id);

        //Удаление клиента из области видимости системы
        clientsMapper.deleteClient(id);

        //Сохранение записи в журнале работы с клиентами
        JClientsModel c = new JClientsModel();

        c.setClientId(id);
        c.setAction("delete");
        c.setClientName(client.getLname() + " " + client.getFname() + " " + client.getPname());
        c.setUserId(authMaster.getLoggedUserId());
        c.setUserName(authMaster.getLoggedUserName());

        j_clients_mapper.insert(c);
    }

    public ClientModel getClientById(int id){
        ClientModel cc = clientsMapper.getClientById(id);
        if(cc != null && cc.getId() > 0){
            return cc;
        }
        throw new ApplicationException(ECode.ERROR1107);
    }

    public ClientModel insertClient(ClientModel client) {
        //Проверка наличия паспортных данных
        if(null != client.getJdata() && !client.getJdata().equals("")){
            client.setHasJdata(1);
        }else{
            client.setHasJdata(0);
        }

        if(client.getFname() != null)
        client.setFname(client.getFname().trim());
        if(client.getLname() != null)
        client.setLname(client.getLname().trim());
        if(client.getPname() != null)
        client.setPname(client.getPname().trim());
        if(client.getEmail() != null)
        client.setEmail(client.getEmail().trim());

        clientsMapper.insertClient(client);

        //Сохранение записи в журнале работы с клиентами
        JClientsModel c = new JClientsModel();

        c.setClientId(client.getId());
        c.setAction("add");
        c.setClientName(client.getLname() + " " + client.getFname() + " " + client.getPname());
        c.setUserId(authMaster.getLoggedUserId());
        c.setUserName(authMaster.getLoggedUserName());

        j_clients_mapper.insert(c);

        return client;
    }

    public void checkClientId(int clientId) {
        ClientModel client = clientsMapper.getClientById(clientId);
        if(client != null){
            return;
        }
        LOG.error("Клиента с id=" + clientId + " не существует.");
        throw new ApplicationException(ECode.ERROR500);
    }

    public void updateClient(ClientModel client) {
        //Проверка наличия клиента в базе
        checkClientId(client.getId());

        //Проверка наличия паспортных данных
        if(null != client.getJdata() && !client.getJdata().equals("")){
            client.setHasJdata(1);
        }else{
            client.setHasJdata(0);
        }
        clientsMapper.updateClient(client);

        //Сохранение записи в журнале работы с клиентами
        JClientsModel c = new JClientsModel();

        c.setClientId(client.getId());
        c.setAction("edit");
        c.setClientName(client.getLname() + " " + client.getFname() + " " + client.getPname());
        c.setUserId(authMaster.getLoggedUserId());
        c.setUserName(authMaster.getLoggedUserName());

        j_clients_mapper.insert(c);
    }
}
