package com.itsoft.ab.persistence;

import com.itsoft.ab.model.GroupModel;
import com.itsoft.ab.model.UserModel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 23.01.14
 * Time: 14:00
 */
public interface GroupsMapper {
    List<GroupModel> selectAllActive();
    List<UserModel> selectIdFromGroup(int id);

    GroupModel selectById(int groupId);

    List<GroupModel> selectUserGroups(int id);
}
