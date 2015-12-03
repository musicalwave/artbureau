package com.itsoft.ab.model;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 23.01.14
 * Time: 13:55
 */
public class GroupWeb {
    private int id;
    private String name;

    public GroupWeb() {
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
        return "GroupWeb{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
