package com.itsoft.ab.controllers.tm;

import com.itsoft.ab.beans.*;
import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.model.TeacherTypeModel;
import com.itsoft.ab.model.TypeModel;
import com.itsoft.ab.sys.ECode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 24.05.14
 * Time: 16:28
 */
@Controller
public class TypesController {
    private Logger LOG = Logger.getLogger(FinanceController.class);

    @Autowired
    private AuthMaster authMaster;

    @Autowired
    private TypeMaster typeMaster;

    @Autowired
    private TeacherTypeMaster teacherTypeMaster;

    @Autowired
    private FinanceMaster financeMaster;

    @Autowired
    private TeacherMaster teacherMaster;

    @RequestMapping(value = "/types", method = RequestMethod.GET)
    public String prices(Model m){
        m = authMaster.setModel(m);

        List<TypeModel> prices = financeMaster.getAllTypesDefault();

        m.addAttribute("prices", prices);
        return "/tm/types/list";
    }

    @RequestMapping(value = "/tm/type", method = RequestMethod.GET)
    public String newtype(Model m){
        m = authMaster.setModel(m);

        m.addAttribute("type", new TypeModel());
        return "/tm/types/new";
    }

    @RequestMapping(value = "/tm/type", method = RequestMethod.POST)
    public String newtypePost(@ModelAttribute TypeModel type, Model m){
        if (type != null && type instanceof TypeModel){
            if(typeMaster.checkExistance(type) == false){
                //type не содержит id
                typeMaster.insertType(type);
                type = typeMaster.getTypeByName(type.getName());
                //type содержит id
                typeMaster.insertPrice(type);
                return "redirect:/tm/type/" + type.getId();
            }
            throw new ApplicationException(ECode.ERROR409);
        }
        throw new ApplicationException(ECode.ERROR500);
    }

    @RequestMapping(value = "/tm/type/{typeId}", method = RequestMethod.GET)
    public String edittype(@PathVariable int typeId, Model m){
        m = authMaster.setModel(m);

        TypeModel type = typeMaster.getTypeById(typeId);

        m.addAttribute("type", type);
        return "/tm/types/edit";
    }

    @RequestMapping(value = "/tm/type/{typeId}", method = RequestMethod.POST)
    public String edittypePost(@ModelAttribute TypeModel type, @PathVariable int typeId, Model m){
        if (type != null && type instanceof TypeModel){
            type.setId(typeId);
            typeMaster.updateType(type);
            return "redirect:/types";
        }
        throw new ApplicationException(ECode.ERROR500);
    }

    @RequestMapping(value = "/tm/teacher/cp", method = RequestMethod.GET)
    public String editTeacherType(@RequestParam("te") String teacherIdS, @RequestParam("ty") String typeIdS, Model m){
        m = authMaster.setModel(m);
        int teacherId = Integer.parseInt(teacherIdS);
        int typeId = Integer.parseInt(typeIdS);

        TeacherTypeModel type = teacherTypeMaster.getTypeByTeacherAndType(teacherId, typeId);

        m.addAttribute("type", type);
        return "/tm/teacher/changeprice";
    }

    @RequestMapping(value = "/tm/teacher/cp", method = RequestMethod.POST)
    public String saveTeacherType(@ModelAttribute TeacherTypeModel t, Model m){

        teacherTypeMaster.updateType(t);
        return "redirect:/teacher/" + t.getTeacherId();
    }
}
