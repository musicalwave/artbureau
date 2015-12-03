package com.itsoft.ab.controllers;

import com.itsoft.ab.beans.AuthMaster;
import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.sys.ECode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 18.11.13
 * Time: 21:07
 */
@Controller
public class IndexController {
    private final static Logger LOG = Logger.getLogger(IndexController.class);

    @Autowired
    private AuthMaster authMaster;

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String init(Model m)  {
        //return "redirect:" + authMaster.getPrefix() + "/home";
        return "redirect:/tasks";
    }

    @RequestMapping(value="/abweb", method=RequestMethod.GET)
    public String old(Model m)  {
        return "redirect:/";
    }

    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public String logout() {
        return "logout";
    }

    @RequestMapping(value="/settings", method= RequestMethod.GET)
    public String settings()  {
        return "redirect:" + authMaster.getPrefix() + "/sets";
    }

    @RequestMapping(value="/admin", method= RequestMethod.GET)
    public String admin()  {
        throw new ApplicationException(ECode.ERROR404);
    }


    @RequestMapping(value="/404", method=RequestMethod.GET)
    public String error404()  {
        throw new ApplicationException(ECode.ERROR404);
    }

    @RequestMapping(value="/403", method=RequestMethod.GET)
    public String error403()  {
        throw new ApplicationException(ECode.ERROR403);
    }

    @RequestMapping(value="/500", method=RequestMethod.GET)
    public String error500()  {
        throw new ApplicationException(ECode.ERROR500);
    }


}
