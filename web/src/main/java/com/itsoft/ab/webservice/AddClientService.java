package com.itsoft.ab.webservice;

import com.itsoft.ab.model.ClientModel;
import com.itsoft.ab.persistence.ClientsMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: Павел
 * Date: 30.12.13
 * Time: 4:46
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/webservices/newclient")
public class AddClientService {

    private final static Logger LOG = Logger.getLogger(AddClientService.class);

    @Autowired
    private ClientsMapper clientsMapper;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public @ResponseBody Object addClient(@RequestParam(value="name", required=true) String name,
                                   @RequestParam(value="lastname", required=true) String lastname,
                                   @RequestParam(value="phone", required=true) String phone,
                                   @RequestParam(value="email", required=true) String email){
        LOG.debug("Adding " + name + " as a client");
        ClientModel client = new ClientModel();
        client.setFname(name);
        client.setLname(lastname);
        client.setPhone1(phone);
        client.setEmail(email);
        clientsMapper.insertClient(client);
        LOG.debug("Client " + name + " was added.");
        return new Object();
    }

}
