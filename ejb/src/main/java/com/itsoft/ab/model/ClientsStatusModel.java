package com.itsoft.ab.model;

import com.itsoft.ab.BasicModel;

/**
 * Created with IntelliJ IDEA.
 * UserModel: itertychnyi
 * Date: 13.10.13
 * Time: 13:46
 */
public class ClientsStatusModel extends BasicModel{
    private int id;
    private String name;

    public ClientsStatusModel() {
    }

    public ClientsStatusModel(int id, String name) {
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
        return "ClientsStatusModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
