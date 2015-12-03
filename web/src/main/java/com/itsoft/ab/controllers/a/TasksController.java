package com.itsoft.ab.controllers.a;

import com.itsoft.ab.beans.AuthMaster;
import com.itsoft.ab.beans.TaskMaster;
import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.logger.LoggerConstants;
import com.itsoft.ab.model.GroupModel;
import com.itsoft.ab.model.TaskModel;
import com.itsoft.ab.model.TaskWeb;
import com.itsoft.ab.model.UserModel;
import com.itsoft.ab.persistence.GroupsMapper;
import com.itsoft.ab.persistence.TasksMapper;
import com.itsoft.ab.persistence.UsersMapper;
import com.itsoft.ab.sys.ECode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 01.01.14
 * Time: 21:39
 */

@Controller
public class TasksController {
    private Logger LOG = Logger.getLogger(LoggerConstants.TASKS_LOGGER);

    @Autowired
    private TasksMapper tasksMapper;

    @Autowired
    private AuthMaster authMaster;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private TaskMaster taskMaster;

    @Autowired
    private GroupsMapper groupsMapper;

    //Обработка сигнала "Показать все задания, назначенные мне"
    @RequestMapping(value="/tasks", method = RequestMethod.GET)
    public String showTasks(Model m){
        m = authMaster.setModel(m);
        long today = (new Date()).getTime();
        List<TaskModel> tasks = tasksMapper.getTasksWeb(authMaster.getLoggedUserId(), today);
        List<TaskModel> gtasks = tasksMapper.getGTasksWeb(authMaster.getLoggedUserId(), today);
        List<TaskModel> tws = new ArrayList<TaskModel>();

        if(!tasks.isEmpty()){
            for (TaskModel task:tasks){
                tws.add(taskMaster.prepareTask(task));
            }
        }

        if(!gtasks.isEmpty()){
            for (TaskModel task:gtasks){
                tws.add(taskMaster.prepareTask(task));
            }
        }

        m.addAttribute("tasks", tws);
        return "/a/tasks/tasks";
    }


    //Обработка сигнала "Показать личные задания, назначенные мне"
    @RequestMapping(value="/tasks/p", method = RequestMethod.GET)
    public String showPTasks(Model m){
        m = authMaster.setModel(m);
        long today = (new Date()).getTime();
        List<TaskModel> tasks = tasksMapper.getTasksWeb(authMaster.getLoggedUserId(), today);

        List<TaskModel> tws = new ArrayList<TaskModel>();

        if(!tasks.isEmpty()){
            for (TaskModel task:tasks){
                tws.add(taskMaster.prepareTask(task));
            }
        }

        m.addAttribute("tasks", tws);
        return "/a/tasks/tasks";
    }

    //Обработка сигнала "Показать групповые задания, назначенные мне"
    @RequestMapping(value="/tasks/g", method = RequestMethod.GET)
    public String showGTasks(Model m){
        m = authMaster.setModel(m);
        long today = (new Date()).getTime();
        List<TaskModel> gtasks = tasksMapper.getGTasksWeb(authMaster.getLoggedUserId(), today);
        List<TaskModel> tws = new ArrayList<TaskModel>();

        if(!gtasks.isEmpty()){
            for (TaskModel task:gtasks){
                tws.add(taskMaster.prepareTask(task));
            }
        }

        m.addAttribute("tasks", tws);
        return "/a/tasks/tasks";
    }

    //Обработка сигнала "Показать все задания, созданные мной"
    @RequestMapping(value="/tasks/my", method = RequestMethod.GET)
    public String getMyTasks(Model m){
        m = authMaster.setModel(m);
        List<TaskModel> tasks = tasksMapper.getMyTasksWeb(authMaster.getLoggedUserId());
        List<TaskModel> tws = new ArrayList<TaskModel>();

        if(!tasks.isEmpty()){
            for (TaskModel task:tasks){
                tws.add(taskMaster.prepareTask(task));
            }
        }

        m.addAttribute("tasks", tws);
        return "/a/tasks/tasksmy";
    }

    //Обработка сигнала "Показать выполненные задания, созданные мной"
    @RequestMapping(value="/tasks/my/done", method = RequestMethod.GET)
    public String selectDMyTasks(Model m){
        m = authMaster.setModel(m);
        List<TaskModel> tasks = tasksMapper.getDoneMyTasksWeb(authMaster.getLoggedUserId());
        List<TaskModel> tws = new ArrayList<TaskModel>();

        if(!tasks.isEmpty()){
            for (TaskModel task:tasks){
                tws.add(taskMaster.prepareTask(task));
            }
        }

        m.addAttribute("tasks", tws);
        return "/a/tasks/tasksmy";
    }

    //Показать страницу "Новое задание"
    @RequestMapping(value="/task", method = RequestMethod.GET)
    public String newTask(Model m){
        //TODO:Security
        //Проверка прав пользователя на создание этого задания
        m = authMaster.setModel(m);
        List<UserModel> users = usersMapper.selectAllActiveOrdered();
        m.addAttribute("users", users);
        List<GroupModel> groups = groupsMapper.selectAllActive();
        m.addAttribute("groups", groups);
        TaskWeb task = new TaskWeb();
        m.addAttribute("task", task);
        return "/a/tasks/new";
    }

    //Обработка сигнала "Создать задание"
    @RequestMapping(value="/task", method = RequestMethod.POST)
    public String createTask(@ModelAttribute TaskWeb task,Model m){
        //TODO:Security
        //Проверка прав пользователя на создание этого задания
        taskMaster.createTasksFromWeb(task);
        LOG.info("New task created: " + task.toString());
        return "redirect:/tasks";
    }

    //Обработка сигнала "Просмотр задания"
    @RequestMapping(value="/task/{taskId}", method = RequestMethod.GET)
    public String showTask(@PathVariable int taskId, Model m){
        //TODO:Security
        //Проверка прав пользователя на создание этого задания
        m = authMaster.setModel(m);
        TaskModel task = tasksMapper.getTaskById(taskId);
        m.addAttribute("task", taskMaster.prepareTaskFull(task));
        return "/a/tasks/show";
    }

        //Обработка сигнала "Задание выполнено"
    @RequestMapping(value="/task/{taskId}/done", method = RequestMethod.POST)
    public String doneTask(@PathVariable int taskId, @ModelAttribute TaskModel task, Model m){
        int toId = task.getToId();
        int groupId = task.getGroupId();
        int id = authMaster.getLoggedUserId();
        if(id == toId || authMaster.isInGroup(id, groupId)){
            String comment = task.getComment();
            comment = comment + "<b>" + authMaster.getLoggedUserName() + "</b>:" + "<br>Задание выполнено " + "<br>" + task.getCommentNew();

            tasksMapper.doneTask(taskId, id, comment);
            LOG.info("Task completed: taskId = " + taskId);
            return "redirect:/tasks";
        }
        throw new ApplicationException(ECode.ERROR1201);
    }

    //Обработка сигнала "Задание удалил автор"
    @RequestMapping(value="/task/{taskId}/delete", method = RequestMethod.POST)
    public String deleteTask(@PathVariable int taskId, @ModelAttribute TaskModel task, Model m){
        int fromId = task.getFromId();

        if(fromId == authMaster.getLoggedUserId()){
            tasksMapper.deleteTask(taskId);
            LOG.info("Task deleted: taskId = " + taskId);
            return "redirect:/tasks";
        }
        throw new ApplicationException(ECode.ERROR1201);
    }

    //Обработка сигнала "Задание перенес исполнитель"
    @RequestMapping(value="/task/{taskId}/transfer", method = RequestMethod.POST)
    public String transferTask(@PathVariable int taskId, @ModelAttribute TaskModel task, Model m){
        int toId = task.getToId();
        int groupId = task.getGroupId();
        int id = authMaster.getLoggedUserId();
        if(id == toId || authMaster.isInGroup(id, groupId)){
            try {
                long date = new SimpleDateFormat("dd-MM-yyyy").parse(task.getDateS()).getTime();
                String comment = task.getComment();
                comment = comment + "<b>" + authMaster.getLoggedUserName() + "</b>:" + "<br>Задание перенесено на " + task.getDateS() + "<br>" + task.getCommentNew();

                tasksMapper.transferTask(taskId, date, comment);
            } catch (ParseException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            return "redirect:/tasks";
        }
        throw new ApplicationException(ECode.ERROR1201);
    }

    //Обработка сигнала "Добавить комментарий к заданию"
    @RequestMapping(value="/task/{taskId}/comment", method = RequestMethod.POST)
    public String commentTask(@PathVariable int taskId, @ModelAttribute TaskModel task, Model m){
        LOG.warn(task.toString());
        int toId = task.getToId();
        int groupId = task.getGroupId();
        int id = authMaster.getLoggedUserId();
        if(id == toId || authMaster.isInGroup(id, groupId)){
            String comment = task.getComment();
            comment = comment + "<b>" + authMaster.getLoggedUserName() + "</b>:" + "<br>" + task.getCommentNew();
            tasksMapper.transferTask(taskId, task.getDate(), comment);
            return "redirect:/tasks";
        }
        throw new ApplicationException(ECode.ERROR1201);
    }


}
