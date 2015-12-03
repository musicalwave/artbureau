package com.itsoft.ab.controllers.tm;

import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.sys.ECode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 23.01.14
 * Time: 13:20
 */
@Controller
@RequestMapping("/tm/groups")
public class GroupsController {
    @RequestMapping(method = RequestMethod.GET)
    public String init(Model m){
        throw new ApplicationException(ECode.ERROR403);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createGroup(Model m){
        throw new ApplicationException(ECode.ERROR403);
    }
}
