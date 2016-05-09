package com.itsoft.ab.beans;

import com.itsoft.ab.model.TeacherScheduleModel;
import com.itsoft.ab.persistence.TeacherScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherScheduleMaster {

    @Autowired
    private TeacherScheduleMapper teacherScheduleMapper;

    public List<TeacherScheduleModel> getTeacherSchedule(int teacherId) {
        return teacherScheduleMapper.getTeacherSchedule(teacherId);
    }

    public List<TeacherScheduleModel> getTeacherScheduleByRoom(int teacherId, int roomId) {
        return teacherScheduleMapper.getTeacherScheduleByRoom(teacherId, roomId);
    }
}
