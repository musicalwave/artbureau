package com.itsoft.ab.model;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 23.01.14
 * Time: 13:58
 */
public class GroupModel {
    private int id;
    private String name;
    private int active;

    public GroupModel() {
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

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "GroupModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", active=" + active +
                '}';
    }
}
