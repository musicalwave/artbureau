package com.itsoft.ab.controllers.a;

import com.itsoft.ab.beans.AuthMaster;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 13.02.14
 * Time: 14:50
 */
@Controller
public class SchedController {
    private Log LOG = LogFactory.getLog(SchedController.class);

    @Autowired
    private AuthMaster authMaster;

    @RequestMapping(value="/sched", method = RequestMethod.GET)
    public String showSchedule(Model m){
        authMaster.setModel(m);
        return "/a/sched/schedule";
    }
}
