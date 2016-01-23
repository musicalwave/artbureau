package com.itsoft.ab.controllers.a;

import com.itsoft.ab.beans.AuthMaster;
import com.itsoft.ab.model.ClientModel;
import com.itsoft.ab.model.ContractModel;
import com.itsoft.ab.model.PaymentModel;
import com.itsoft.ab.persistence.ClientsMapper;
import com.itsoft.ab.persistence.ContractsMapper;
import com.itsoft.ab.persistence.PaymentMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Controller
public class ReportsController {
    Logger LOG = Logger.getLogger(ReportsController.class);

    @Autowired
    private AuthMaster authMaster;

    @Autowired
    private ContractsMapper contractsMapper;

    @Autowired
    private PaymentMapper paymentMapper;

    private String getTomorrowDateString() {
        Calendar calendar = Calendar.getInstance(
                TimeZone.getTimeZone("Europe/Moscow"));
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    @RequestMapping(value = "/reports/last-lesson", method = RequestMethod.GET)
    public String lastLessonReport(Model m) {
        authMaster.setModel(m);
        List<ContractModel> contracts = contractsMapper.getContractsByLastLessonDate(
                getTomorrowDateString());
        m.addAttribute("contracts", contracts);
        return "/a/reports/last-lesson";
    }

    @RequestMapping(value = "/reports/payment-soon", method = RequestMethod.GET)
    public String paymentSoonReport(Model m) {
        authMaster.setModel(m);
        List<PaymentModel> payments = paymentMapper.getPlannedPaymentsByDate(
                getTomorrowDateString());
        m.addAttribute("payments", payments);
        return "/a/reports/payment-soon";
    }
}
