package com.itsoft.ab.model;

import com.itsoft.ab.BasicModel;

/**
 * Created with IntelliJ IDEA.
 * UserModel: itertychnyi
 * Date: 13.10.13
 * Time: 15:13
 */
public class LessonsStatusModel extends BasicModel{
    private int id;
    private String name;

    public LessonsStatusModel() {
    }

    public LessonsStatusModel(int id, String name) {
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
        return "LessonsStatusModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
