package com.itsoft.ab.persistence;

import com.itsoft.ab.model.TeacherTypeModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 06.03.14
 * Time: 20:17
 */
public interface TeacherTypeMapper {
    List<TeacherTypeModel> getAllActive();

    TeacherTypeModel getById(int id);

    List<TeacherTypeModel> getAllActiveByType(int typeId);

    List<TeacherTypeModel> getTeacherTypes(int teacherId);

    void insertTypeTeacher(@Param("type")int typeId, @Param("teacher")int teacherId);

    void deleteTypeTeacher(int id);

    void insertModel(TeacherTypeModel tt);

    List<TeacherTypeModel> getTypeByTeacherAndType(@Param("teacher")int teacherId, @Param("type")int typeId);

    void updateType(TeacherTypeModel t);
}
