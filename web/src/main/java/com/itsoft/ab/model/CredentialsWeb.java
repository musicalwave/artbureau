package com.itsoft.ab.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 20.11.13
 * Time: 21:16
 */
public class CredentialsWeb implements Serializable {
    private String login;
    private String password;
    private boolean remember;

    public CredentialsWeb() {
    }

    public CredentialsWeb(String login, String password, boolean remember) {
        this.login = login;
        this.password = password;
        this.remember = remember;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    @Override
    public String toString() {
        return "CredentialsWeb{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", remember=" + remember +
                '}';
    }
}
