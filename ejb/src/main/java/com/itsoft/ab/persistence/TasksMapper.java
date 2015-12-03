package com.itsoft.ab.persistence;

import com.itsoft.ab.model.TaskModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 01.01.14
 * Time: 21:50
 */
public interface TasksMapper {
    //Методы, возвращающие все данные по заданиям
    List<TaskModel> getTasks(int userId);
    List<TaskModel> getMyTasks(int userId);
    //Методы, возвращающие данные по заданиям, достаточные для отображения в таблице
    List<TaskModel> getTasksWeb(@Param("id")int userId, @Param("fromdate")long date);
    List<TaskModel> getMyTasksWeb(int userId);
    List<TaskModel> getDoneMyTasksWeb(int userId);

    List<TaskModel> getGTasksWeb(@Param("id")int userId, @Param("fromdate")long date);
    List<TaskModel> getMyGTasksWeb(int userId);

    List<TaskModel> getAllTasks();
    int getNTasks(int userId);
    void doneTask(@Param("id")int taskId, @Param("by")int userId, @Param("comment")String comment);
    void deleteTask(int taskId);
    void transferTask(@Param("id")int taskId, @Param("date")long date ,@Param("comment")String comment);
    void insertTask(TaskModel task);
    //Временное решение, пока не перенесены все клиенты в бд
    void insertTaskTemp(TaskModel task);
    void insertTaskGroup(TaskModel task);
    //Временное решение, пока не перенесены все клиенты в бд
    void insertTaskGroupTemp(TaskModel task);

    TaskModel getTaskById(int taskId);
}
