package com.itsoft.ab.model;

import com.itsoft.ab.BasicModel;

/**
 * Created with IntelliJ IDEA.
 * UserModel: itertychnyi
 * Date: 12.10.13
 * Time: 21:36
 */
public class AuthStatusModel extends BasicModel{
    private int id;
    private String name;

    public AuthStatusModel() {
    }

    public AuthStatusModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AuthStatusModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
