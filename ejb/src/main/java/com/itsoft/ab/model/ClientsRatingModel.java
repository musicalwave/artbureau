package com.itsoft.ab.model;

import com.itsoft.ab.BasicModel;

/**
 * Created with IntelliJ IDEA.
 * UserModel: itertychnyi
 * Date: 13.10.13
 * Time: 13:41
 */
public class ClientsRatingModel extends BasicModel{
    private int id;
    private String name;

    public ClientsRatingModel() {
    }

    public ClientsRatingModel(int id, String name) {
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
        return "ClientsRatingModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
