package com.itsoft.ab.controllers.a;

import com.itsoft.ab.beans.AuthMaster;
import com.itsoft.ab.beans.LessonMaster;
import com.itsoft.ab.beans.PaymentMaster;
import com.itsoft.ab.beans.StatMaster;
import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.model.LessonWeb;
import com.itsoft.ab.model.PaymentModel;
import com.itsoft.ab.model.SimpleModel;
import com.itsoft.ab.sys.ECode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 21.06.14
 * Time: 14:50
 */
@Controller
public class StatController {
    Logger LOG = Logger.getLogger(StatController.class);

    @Autowired
    private AuthMaster authMaster;

    @Autowired
    private PaymentMaster paymentMaster;

    @Autowired
    private LessonMaster lessonMaster;

    @Autowired
    private StatMaster statMaster;

    @RequestMapping(value="/stat/planned", method = RequestMethod.GET)
    public String paymentsDefault(Model m){
        m = authMaster.setModel(m);

        m.addAttribute("payments", new ArrayList<PaymentModel>());
        m.addAttribute("date", new SimpleModel());
        return "/a/stat/planned";
    }

    @RequestMapping(value="/stat/planned", method = RequestMethod.POST)
    public String payments(@ModelAttribute("date")SimpleModel date, Model m){
        m = authMaster.setModel(m);

        try {
            Date fromDate = new SimpleDateFormat("dd-MM-yyyy").parse(date.getName());

            //Получаем следующий день
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(fromDate);
            calendar.add(Calendar.DATE, 1);

            Date toDate = calendar.getTime();

            m.addAttribute("payments", paymentMaster.getDatePlannedPayments(fromDate, toDate));
            m.addAttribute("date", date);
            return "/a/stat/planned";
        } catch (ParseException e) {
            throw new ApplicationException(ECode.ERROR415);
        }
    }

    @RequestMapping(value="/stat/done", method = RequestMethod.GET)
    public String doneDefault(Model m){
        m = authMaster.setModel(m);

        m.addAttribute("payments", new ArrayList<PaymentModel>());
        m.addAttribute("date", new SimpleModel());
        m.addAttribute("valueDo", 0);
        m.addAttribute("valuePlan", 0);
        return "/a/stat/done";
    }

    @RequestMapping(value="/stat/done", method = RequestMethod.POST)
    public String done(@ModelAttribute("date")SimpleModel date, Model m){
        m = authMaster.setModel(m);

        try {
            Date fromDate = new SimpleDateFormat("dd-MM-yyyy").parse(date.getName());

            //Получаем следующий день
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(fromDate);
            calendar.add(Calendar.DATE, 1);

            Date toDate = calendar.getTime();

            m.addAttribute("payments", paymentMaster.getDateDonePayments(fromDate, toDate));
            m.addAttribute("date", date);
            m.addAttribute("valueDo", statMaster.getDateValueDo(fromDate));
            m.addAttribute("valuePlan", statMaster.getDateValuePlan(fromDate));
            return "/a/stat/done";
        } catch (ParseException e) {
            throw new ApplicationException(ECode.ERROR415);
        }
    }

    @RequestMapping(value="/stat/last", method = RequestMethod.GET)
    public String lastDefault(Model m){
        m = authMaster.setModel(m);

        m.addAttribute("lessons", new ArrayList<LessonWeb>());
        m.addAttribute("date", new SimpleModel());
        return "/a/stat/last";
    }

    @RequestMapping(value="/stat/last", method = RequestMethod.POST)
    public String last(@ModelAttribute("date")SimpleModel date, Model m){
        m = authMaster.setModel(m);

        try {
            Date fromDate = new SimpleDateFormat("dd-MM-yyyy").parse(date.getName());

            //Получаем следующий день
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(fromDate);
            calendar.add(Calendar.DATE, 1);

            Date toDate = calendar.getTime();

            m.addAttribute("lessons", lessonMaster.getDateLastLessons(fromDate, toDate));
            m.addAttribute("date", date);
            return "/a/stat/last";
        } catch (ParseException e) {
            throw new ApplicationException(ECode.ERROR415);
        }
    }
}
