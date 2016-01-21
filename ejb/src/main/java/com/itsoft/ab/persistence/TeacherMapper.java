package com.itsoft.ab.persistence;

import com.itsoft.ab.model.TeacherModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 19.02.14
 * Time: 14:22
 */
public interface TeacherMapper {
    List<TeacherModel> findTeacher(@Param("name")String name, @Param("bdate")String bdate,
                                   @Param("phone")String phone, @Param("type")int type);

    void saveTeacher(TeacherModel t);

    void insertTeacher(TeacherModel t);

    TeacherModel getTeacherById(int teacherId);

    TeacherModel getTeacherCredById(int teacherId);

    void updateCred(TeacherModel teacher);

    void updateTeacher(TeacherModel teacher);

    List<TeacherModel> getActiveTeachers();

    List<TeacherModel> getActiveTeachersByTypes(@Param("typeIds") String[] typeIds);

    List<Integer> getWorkingDays(@Param("id") int teacherId);

}
