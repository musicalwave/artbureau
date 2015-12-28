package com.itsoft.ab.beans;

import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.model.*;
import com.itsoft.ab.persistence.ClientsMapper;
import com.itsoft.ab.persistence.GroupsMapper;
import com.itsoft.ab.persistence.TasksMapper;
import com.itsoft.ab.persistence.UsersMapper;
import com.itsoft.ab.sys.Consts;
import com.itsoft.ab.sys.ECode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 14.01.14
 * Time: 23:55
 */
@Service
public class TaskMaster {
    Log LOG = LogFactory.getLog(TaskMaster.class);

    @Autowired
    private TasksMapper tasksMapper;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private MailMaster mailMaster;

    @Autowired
    private ClientsMapper clientsMapper;

    @Autowired
    private GroupsMapper groupsMapper;

    //
    // Служебный класс для работы с заданиями
    //


    public TaskMaster() {
    }

    //Создание задания из формы для нескольких пользователей
    public int createTasksFromWeb(TaskWeb task){
        TaskModel t = new TaskModel();

        //Проверка введенных данных
        this.isValidTask(task);

        t.setClientId(task.getClientId());
        t.setClientName(task.getClientName());
        t.setTeacherName(task.getTeacherName());
        t.setTeacherId(task.getTeacherId());
        t.setFromId(task.getFromId());
        t.setMessage(task.getMessage());
        t.setHash(new Date().getTime());

        //Установка времени выполнения
        try {
            t.setDate(new SimpleDateFormat("dd-MM-yyyy").parse(task.getDate()).getTime());
        } catch (ParseException e) {
            throw new ApplicationException(ECode.ERROR415);
        }

        if(null != task.getStartDate() && !task.getStartDate().equals("")){
            try {
                t.setStartDate(new SimpleDateFormat("dd-MM-yyyy").parse(task.getStartDate()).getTime());
            } catch (ParseException e) {
                throw new ApplicationException(ECode.ERROR415);
            }
        }

        if(null != task.getTime() && !task.getTime().equals("")){
            try {
                t.setTime(new SimpleDateFormat("HH:mm").parse(task.getTime()).getTime());
            } catch (ParseException e) {
                throw new ApplicationException(ECode.ERROR415);
            }
        }

        //Различные флаги
        if(task.getImportant() != null && task.getImportant().equals("true")){
            t.setImportant(1);
        } else{
            t.setImportant(0);
        }
        if(task.getInfo() != null && task.getInfo().equals("true")){
            t.setInfo(1);
        } else{
            t.setInfo(0);
        }
        t.setActive(1);

        t.setComment(task.getComment());

        //Получаем данные создавшего задание
        UserModel userFrom = usersMapper.selectById(t.getFromId());
        //Собираем мейлы
        List<MailModel> mails = new ArrayList<MailModel>();


        String title = "MusicalWave - Новое задание";
        String date = new SimpleDateFormat("dd MMMM yyyy").format(new Date(t.getDate()));

        if(null != task.getToIds()){
            //Сохраняем персональные задания
            String [] ids = task.getToIds().split(",");
            for (String id : ids){
                t.setToId(Integer.parseInt(id));
                //
                // Временное решение
                // Позднее заменить на
                // tasksMapper.insertTask(t);
                //
                tasksMapper.insertTaskTemp(t);

                UserModel user = usersMapper.selectById(Integer.parseInt(id));

                String message = createMessage(userFrom, user.getName(), date,t);

                MailModel mail = new MailModel();
                mail.setTitle(title);
                mail.setToMail(user.getMail());
                mail.setText(message);

                mails.add(mail);
            }
        }

        if(null != task.getToGroups()){
            //Сохраняем групповые задания
            String [] groups = task.getToGroups().split(",");
            for (String group : groups){
                int groupId =  Integer.parseInt(group);
                List<UserModel> users = getIdFromGroup(groupId);
                GroupModel g = groupsMapper.selectById(groupId);
                t.setGroupId(groupId);
                //
                // Временное решение
                // Позднее заменить на
                // tasksMapper.insertTaskGroup(t);
                //
                tasksMapper.insertTaskGroupTemp(t);

                for (UserModel user : users){
                    String message = createMessage(userFrom, g.getName(), date, t);

                    MailModel mail = new MailModel();
                    mail.setTitle(title);
                    mail.setToMail(user.getMail());
                    mail.setText(message);

                    mails.add(mail);

                }
            }
        }

        LOG.error("ВОТ ТАКОЙ ВОТ task.getMail(): " + task.getMail());
        if(task.getMail() != null && task.getMail().equals("true")){
            mailMaster.sendMails(mails);
        }

        return 200;
    }

    private List<UserModel> getIdFromGroup(int groupId){
        return groupsMapper.selectIdFromGroup(groupId);
    }

    private String createMessage(UserModel userFrom, String to, String date, TaskModel t){
        return userFrom.getName() + " отметил вас как исполнителя нового задания.\nДата: " + date + "\nИсполнитель: " + to +"\nЗадание: " + t.getMessage() + "\nПерейти в систему: 212.71.253.29/tasks";
    }

    public TaskModel prepareTask(TaskModel task){
        //Дополняем модель данными для отображения
        task = prepareTaskFull(task);

        //Эстетичность отображения больших заданий
        if(task.getMessage().length() > Consts.WEB_TASK_MESSAGE_LENGTH){
            task.setMessage(task.getMessage().substring(0, Consts.WEB_TASK_MESSAGE_LENGTH) + "...");
        }

        return task;
    }

    public TaskModel prepareTaskFull(TaskModel task){
        ClientModel client = clientsMapper.getClientById(task.getClientId());
        if (client!=null){
            task.setClientName(client.getFname() + " " + client.getLname());
        }
        UserModel from = usersMapper.selectById(task.getFromId());
        if (from!=null){
            task.setFromName(from.getName());
        }
        UserModel to = usersMapper.selectById(task.getToId());
        if (to!=null){
            task.setToName(to.getName());
        }

        //Преобразование временных констант
        try{
            task.setDateS(new SimpleDateFormat("dd MMMM yyyy").format(new Date(task.getDate())));
        } catch (NullPointerException e){
            //Nothing to do
        }

        if(task.getTime()>0){
            try{
                task.setTimeS(new SimpleDateFormat("HH:mm").format(new Date(task.getTime())));
            }catch (NullPointerException e){
                //Nothing to do
            }
        }
        if(task.getStartDate()>0){
            try{
                task.setStartDateS(new SimpleDateFormat("dd MMMM yyyy").format(new Date(task.getStartDate())));
            } catch (NullPointerException e){
                //Nothing to do
            }
        }
        task.setHashS(new SimpleDateFormat("dd MMMM yyyy HH:mm").format(new Date(task.getHash())));

        //
        if(task.getGroupId()>0){
            task.setGroupName(groupsMapper.selectById(task.getGroupId()).getName());
        }

        return task;
    }

    private boolean isValidTask(TaskWeb task){
        //Проверка заполнения полей Исполнители
        if(null == task.getToIds() && null == task.getToGroups()) {
            throw new ApplicationException(ECode.ERROR1101);
        }
        //Проверка заполнения поля Даты
        if(null == task.getDate()) {
            throw new ApplicationException(ECode.ERROR1102);
        }
        //Проверка заполнения поля Задание
        if(null == task.getMessage()) {
            throw new ApplicationException(ECode.ERROR1103);
        }
        return true;
    }
}
