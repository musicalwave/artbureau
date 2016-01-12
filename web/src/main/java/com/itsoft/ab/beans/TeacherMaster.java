package com.itsoft.ab.beans;

import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.model.EventModel;
import com.itsoft.ab.model.TeacherModel;
import com.itsoft.ab.persistence.RoomMapper;
import com.itsoft.ab.persistence.TeacherMapper;
import com.itsoft.ab.sys.ECode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 25.03.14
 * Time: 17:25
 */
@Service
public class TeacherMaster {

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    public List<EventModel> checkTeacherDays(TeacherModel teacher) {
        List<EventModel> events = new ArrayList<EventModel>();

        String [] wd = teacher.getWeekDays().split(",");
        for(String d : wd){
            int dd = Integer.parseInt(d);

        }
        return events;
    }

    public TeacherModel insertTeacher(TeacherModel teacher){
        teacherMapper.insertTeacher(teacher);
        return teacher;
    }

    public TeacherModel getTeacherById(int teacherId) {
        TeacherModel teacher = teacherMapper.getTeacherById(teacherId);
        if(teacher != null && teacher.getId() > 0){
            return teacher;
        }
        throw new ApplicationException(ECode.ERROR1108);
    }

    public static TeacherModel getEmptyTeacher() {
        TeacherModel teacher = new TeacherModel();
        teacher.setName("---");
        return teacher;
    }
}
