package com.itsoft.ab.controllers.a;

import com.itsoft.ab.beans.AuthMaster;
import com.itsoft.ab.beans.PaymentMaster;
import com.itsoft.ab.beans.StatMaster;
import com.itsoft.ab.sys.Dates;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 22.08.14
 * Time: 13:24
 */
@Controller
public class HeadController {
    Logger LOG = Logger.getLogger(HeadController.class);

    @Autowired
    private AuthMaster authMaster;

    @Autowired
    private StatMaster statMaster;

    @Autowired
    private PaymentMaster paymentMaster;


    @RequestMapping(value="/today", method = RequestMethod.GET)
    public String todayDefault(Model m) throws ParseException {
        m = authMaster.setModel(m);

        //Сегодня
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date today = sdf.parse(sdf.format(new Date()));

        m.addAttribute("paymentsDo", paymentMaster.getDateDonePayments(today, Dates.getNextDay(today)));
        m.addAttribute("paymentsPlan", paymentMaster.getDatePlannedPayments(today, Dates.getNextDay(today)));

        m.addAttribute("valueDo", statMaster.getDateValueDo(today));
        m.addAttribute("valuePlan", statMaster.getDateValuePlan(today));
        m.addAttribute("weekValueDo", statMaster.getWeekValueDo(today));
        m.addAttribute("weekValuePlan", statMaster.getWeekValuePlan(today));
        m.addAttribute("nclients", statMaster.getDateClients(today));
        m.addAttribute("ncalls", statMaster.getDateCalls(today));
        return "/a/head/today";
    }
}
