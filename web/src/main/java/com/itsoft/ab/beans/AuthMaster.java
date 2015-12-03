package com.itsoft.ab.beans;

import com.itsoft.ab.model.UserModel;
import com.itsoft.ab.persistence.GroupsMapper;
import com.itsoft.ab.persistence.TasksMapper;
import com.itsoft.ab.persistence.UsersMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 07.01.14
 * Time: 6:18
 */
@Service
public class AuthMaster {
    Logger LOG = Logger.getLogger(AuthMaster.class);

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private GroupsMapper groupsMapper;

    @Autowired
    private TasksMapper tasksMapper;

    //
    //     Служебный класс для операций с авторизованным пользователем
    //

    public AuthMaster(){
    }

    //Получение из бд объект пользователя по логину из SecurityContext
    private UserModel getLoggedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return usersMapper.selectForAuth(name);
    }

    //Получение id авторизованного пользователя
    public int getLoggedUserId(){
        return getLoggedUser().getId();
    }

    //Получение имени авторизованного пользователя
    public String getLoggedUserName(){
        return getLoggedUser().getName();
    }

    //Получение числа заданий для авторизованного пользователя
    private int getNTasks(int id){
        return tasksMapper.getNTasks(id);
    }

    public String getPrefix(){
        UserModel user = getLoggedUser();
        return user.getRolePrefix();
    }

    //Установка в модель контроллера значения имени авторизованного пользователя
    public Model setModel(Model m){
        UserModel user = getLoggedUser();
        m.addAttribute("userid", user.getId());
        m.addAttribute("username", user.getName());
        m.addAttribute("ntasks", getNTasks(user.getId()));
        return m;
    }

    //Проверка, находится ли данный пользователь в данной группе
    public boolean isInGroup(int userId, int groupId){
        List<UserModel> users = groupsMapper.selectIdFromGroup(groupId);
        for(UserModel user : users){
            if(userId == user.getId()){
                return true;
            }
        }
        return false;
    }
}
