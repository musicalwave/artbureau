package com.itsoft.ab.persistence;

import com.itsoft.ab.model.UserModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 24.11.13
 * Time: 23:53
 */
public interface UsersMapper {
    List<UserModel> selectAllById(int id);
    UserModel selectById(int id);
    UserModel selectByLogin(@Param("login")String login);
    UserModel selectForAuth(@Param("login")String login);
    void insert(UserModel userModel);
    String selectRoleById(int roleId);
    List<UserModel> selectAllActive();
    List<UserModel> selectAllActiveOrdered();
}
