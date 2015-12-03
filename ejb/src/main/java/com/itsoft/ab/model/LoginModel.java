package com.itsoft.ab.model;

import com.itsoft.ab.BasicModel;

/**
 * Created with IntelliJ IDEA.
 * UserModel: itertychnyi
 * Date: 18.10.13
 * Time: 11:45
 */
public class LoginModel extends BasicModel{
    private int userId;
    private String login;
    private int roleId;

    public LoginModel() {
    }

    public LoginModel(int userId, String login, int roleId) {
        this.userId = userId;
        this.login = login;
        this.roleId = roleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "LoginModel{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
