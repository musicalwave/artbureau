package com.itsoft.ab.controllers.tm;

import com.itsoft.ab.beans.AuthMaster;
import com.itsoft.ab.beans.JournalMaster;
import com.itsoft.ab.beans.UserMaster;
import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.model.JClientsModel;
import com.itsoft.ab.model.JLessonTransfer;
import com.itsoft.ab.model.SimpleModel;
import com.itsoft.ab.persistence.JournalMapper;
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
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 18.02.14
 * Time: 21:11
 */
@Controller
public class AdminController {
    Logger LOG = Logger.getLogger(AdminController.class);
    @Autowired
    private AuthMaster authMaster;

    @Autowired
    private JournalMapper journalMapper;

    @Autowired
    private UserMaster userMaster;

    @Autowired
    private JournalMaster journalMaster;

    @RequestMapping(value="/tm/admin", method = RequestMethod.GET)
    public String home(Model m){
        m = authMaster.setModel(m);
        return "/tm/admin/index";
    }

    @RequestMapping(value="/tm/transfer", method = RequestMethod.POST)
    public String transfer(@ModelAttribute SimpleModel date, Model m){
        m = authMaster.setModel(m);

        try {
            java.util.Date d = new SimpleDateFormat("dd-MM-yyyy").parse(date.getName());
            List<JLessonTransfer> data = journalMapper.getDateTransfers(new java.sql.Date(d.getTime()));

            for(JLessonTransfer j : data){
                j.setFromDateS(new SimpleDateFormat("dd MMMM yyyy").format(j.getFromDate()));
                j.setToDateS(new SimpleDateFormat("dd MMMM yyyy").format(j.getToDate()));
                j.setActDateS(new SimpleDateFormat("dd MMMM yyyy").format(j.getActDate()));
            }


            m.addAttribute("lessons", data);
            m.addAttribute("date", new SimpleModel());
            return "/tm/admin/transfer";
        } catch (ParseException e) {
            throw new ApplicationException(ECode.ERROR415);
        }
    }

    @RequestMapping(value="/tm/transfer", method = RequestMethod.GET)
    public String transferDefault(Model m){
        m = authMaster.setModel(m);
        m.addAttribute("lessons", new ArrayList<JLessonTransfer>());
        m.addAttribute("date", new SimpleModel());
        return "/tm/admin/transfer";
    }

    @RequestMapping(value="/tm/clients", method = RequestMethod.POST)
    public String client(@ModelAttribute SimpleModel date, Model m){
        m = authMaster.setModel(m);

        try {
            Date fromDate = new SimpleDateFormat("dd-MM-yyyy").parse(date.getName());

            //Получаем следующий день
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(fromDate);
            calendar.add(Calendar.DATE, 1);

            Date toDate = calendar.getTime();

            m.addAttribute("clients", journalMaster.getDateClients(fromDate, toDate));
            m.addAttribute("date", date);
            return "/tm/admin/clients";
        } catch (ParseException e) {
            throw new ApplicationException(ECode.ERROR415);
        }
    }

    @RequestMapping(value="/tm/clients", method = RequestMethod.GET)
    public String clientsDefault(Model m){
        m = authMaster.setModel(m);
        m.addAttribute("clients", new ArrayList<JClientsModel>());
        m.addAttribute("date", new SimpleModel());
        return "/tm/admin/clients";
    }

    @RequestMapping(value="/tm/staff", method = RequestMethod.GET)
    public String staff(Model m){
        m = authMaster.setModel(m);

        m.addAttribute("users", userMaster.getAllUsers());
        return "/tm/admin/staff";
    }
}
