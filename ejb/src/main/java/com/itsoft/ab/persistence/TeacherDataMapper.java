package com.itsoft.ab.persistence;

import com.itsoft.ab.model.TeacherDataModel;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 09.04.14
 * Time: 12:37
 */
public interface TeacherDataMapper {
    TeacherDataModel getDataByTeacherId(int teacherId);

    void insertData(TeacherDataModel t);
    void updateData(TeacherDataModel t);
}
