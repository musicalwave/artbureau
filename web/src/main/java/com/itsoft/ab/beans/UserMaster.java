package com.itsoft.ab.beans;

import com.itsoft.ab.model.GroupModel;
import com.itsoft.ab.model.UserModel;
import com.itsoft.ab.persistence.GroupsMapper;
import com.itsoft.ab.persistence.UsersMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 22.05.14
 * Time: 15:29
 */
@Service
public class UserMaster {
    private Logger LOG = Logger.getLogger(UserMaster.class);

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private GroupsMapper groupsMapper;

    public List<UserModel> getAllUsers(){
        List<UserModel> users = usersMapper.selectAllActive();
        for (UserModel u : users){
            List<GroupModel> groups = groupsMapper.selectUserGroups(u.getId());

            String ggs = "";
            for(GroupModel g : groups){
                ggs = ggs + g.getName() + " ";
            }

            u.setGroups(ggs);
        }

        return users;
    }
}
