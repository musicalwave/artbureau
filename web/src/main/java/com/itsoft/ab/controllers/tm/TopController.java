package com.itsoft.ab.controllers.tm;

import com.itsoft.ab.beans.AuthMaster;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 11.01.14
 * Time: 22:20
 */
@Controller
@RequestMapping("/tm")
public class TopController {
    Logger LOG = Logger.getLogger(TopController.class);

    @Autowired
    private AuthMaster authMaster;


    @RequestMapping(value="/home", method = RequestMethod.GET)
    public String home(Model m){
        m = authMaster.setModel(m);
        return "/tm/home";
    }

    @RequestMapping(value="/sets", method = RequestMethod.GET)
    public String settings(Model m){
        m = authMaster.setModel(m);
        return "/tm/settings";
    }
}
