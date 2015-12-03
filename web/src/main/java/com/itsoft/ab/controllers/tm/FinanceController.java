package com.itsoft.ab.controllers.tm;

import com.itsoft.ab.beans.AuthMaster;
import com.itsoft.ab.beans.TemplateMaster;
import com.itsoft.ab.model.DatesWeb;
import com.itsoft.ab.model.TeacherDataModel;
import com.itsoft.ab.model.TeacherModel;
import com.itsoft.ab.persistence.TeacherDataMapper;
import com.itsoft.ab.persistence.TeacherMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 26.03.14
 * Time: 21:30
 */
@Controller
public class FinanceController {
    private Logger LOG = Logger.getLogger(FinanceController.class);

    @Autowired
    private AuthMaster authMaster;

    @Autowired
    private TeacherDataMapper teacherDataMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private TemplateMaster templateMaster;

    @RequestMapping(value = "/finance/t/{teacherId}", method = RequestMethod.POST)
    public String calculateTeacher(@PathVariable("teacherId") int teacherId, @ModelAttribute DatesWeb dates, Model m){
        m = authMaster.setModel(m);

        TeacherModel teacher = teacherMapper.getTeacherById(teacherId);

        TeacherDataModel t = teacherDataMapper.getDataByTeacherId(teacherId);
        if(null == t){
            t = new TeacherDataModel();
        }


        t.setTeacherId(teacherId);
        t.setStartdate(dates.getStartdate());
        t.setFinishdate(dates.getFinishdate());

        m.addAttribute("teacher", teacher);
        m.addAttribute("dates", dates);
        m.addAttribute("data", t);
        return "/tm/finance/teacher";
    }

    @RequestMapping(value = "/finance/t/get", method = RequestMethod.POST)
    public ResponseEntity<byte[]> calculateTeacher2(@ModelAttribute TeacherDataModel t, Model m){
        LOG.warn("ОБЪЕКТ ДЛЯ РАССЧЕТА: " + t.toString());
        TeacherDataModel tt = teacherDataMapper.getDataByTeacherId(t.getTeacherId());

        if (null == tt) {
            teacherDataMapper.insertData(t);
        } else {
            teacherDataMapper.updateData(t);
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {

            templateMaster.fillTemplate(os, t.getStartdate(), t.getFinishdate(), t.getSoloquantity(),
                    t.getGroupquantity(), t.getPairquantity(), t.getEnsemblequantity(), t.getSolorate(),
                    t.getGrouprate(), t.getPairrate(), t.getEnsemblerate(), t.getTeacherId(), t.getActnumber());
            byte[] contents = os.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/docx"));
            String filename = t.getStartdate() + "_" + t.getFinishdate() + "_" + t.getLname() + ".docx";
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
            os.close();

            return response;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
    }

    @RequestMapping(value = "/tm/finance", method = RequestMethod.GET)
    public String index(Model m){
        m = authMaster.setModel(m);

        return "/tm/finance/index";
    }

    @RequestMapping(value = "/tm/finance/in", method = RequestMethod.GET)
    public String newPaymentIn(Model m){
        m = authMaster.setModel(m);

        return "/tm/finance/in";
    }

    @RequestMapping(value = "/tm/finance/out", method = RequestMethod.GET)
    public String newPaymentOut(Model m){
        m = authMaster.setModel(m);

        return "/tm/finance/out";
    }

    @RequestMapping(value = "/tm/finance/accounting", method = RequestMethod.GET)
    public String accounting(Model m){
        m = authMaster.setModel(m);

        return "/tm/finance/accounting";
    }

    @RequestMapping(value = "/tm/finance/cassa", method = RequestMethod.GET)
    public String cassa(Model m){
        m = authMaster.setModel(m);

        return "/tm/finance/cassa";
    }

    @RequestMapping(value = "/tm/finance/employers", method = RequestMethod.GET)
    public String employers(Model m){
        m = authMaster.setModel(m);

        return "/tm/finance/employers";
    }

    @RequestMapping(value = "/tm/finance/refund", method = RequestMethod.GET)
    public String refund(Model m){
        m = authMaster.setModel(m);

        return "/tm/finance/refund";
    }

    @RequestMapping(value = "/tm/finance/stat", method = RequestMethod.GET)
    public String stat(Model m){
        m = authMaster.setModel(m);

        return "/tm/finance/stat";
    }

    @RequestMapping(value = "/tm/finance/teachers", method = RequestMethod.GET)
    public String teachers(Model m){
        m = authMaster.setModel(m);

        return "/tm/finance/teachers";
    }

//    @RequestMapping(value = "/tm/finance/teachers", method = RequestMethod.GET)
//    public String payments(Model m){
//        m = authMaster.setModel(m);
//
//        List<PaymentModel> payments = new ArrayList<PaymentModel>();
//
//        m.addAttribute("payments", payments);
//        m.addAttribute("date", new SimpleModel());
//        return "/tm/finance/payments";
//    }
}
