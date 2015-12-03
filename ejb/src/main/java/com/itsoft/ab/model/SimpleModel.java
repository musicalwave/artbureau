package com.itsoft.ab.model;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 27.01.14
 * Time: 14:41
 */
public class SimpleModel {
    private int id;
    private String name;

    public SimpleModel() {
    }

    @Override
    public String toString() {
        return "SimpleModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
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
}
