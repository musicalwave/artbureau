package com.itsoft.ab.beans;

import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.model.TeacherTypeModel;
import com.itsoft.ab.model.TypeModel;
import com.itsoft.ab.persistence.TeacherTypeMapper;
import com.itsoft.ab.sys.ECode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 07.06.14
 * Time: 20:48
 */
@Service
public class TeacherTypeMaster {
    Logger LOG = Logger.getLogger(TeacherTypeMaster.class);

    @Autowired
    private TeacherTypeMapper teacherTypeMapper;

    @Autowired
    private TypeMaster typeMaster;

    public void insertTypeTeacher(int typeId, int id) {
        TypeModel type = typeMaster.getTypeById(typeId);
        if(type != null){
            TeacherTypeModel tt = new TeacherTypeModel(type);
            tt.setTeacherId(id);
            teacherTypeMapper.insertModel(tt);
            return;
        }
        LOG.error("Тип занятия с id=" + typeId + " не найден.");
        throw new ApplicationException(ECode.ERROR500);
    }

    public TeacherTypeModel getTypeByTeacherAndType(int teacherId, int typeId){
        List<TeacherTypeModel> result = teacherTypeMapper.getTypeByTeacherAndType(teacherId, typeId);

        if (result.size() > 1 || result.size() < 1) {
            LOG.error("Полученный массив данных имеет неподходящий размер.");
            throw new ApplicationException(ECode.ERROR500);
        }

        return result.get(0);
    }

    public void updateType(TeacherTypeModel t) {
        teacherTypeMapper.updateType(t);
    }
}
