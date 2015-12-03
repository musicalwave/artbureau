package com.itsoft.ab.model;

import com.itsoft.ab.BasicModel;

/**
 * Created with IntelliJ IDEA.
 * UserModel: itertychnyi
 * Date: 13.10.13
 * Time: 15:03
 */
public class FilialModel extends BasicModel{
    private int id;
    private String name;
    private String address;
    private int countRooms;

    public FilialModel() {
    }

    public FilialModel(int id, String name, String address, int countRooms) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.countRooms = countRooms;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCountRooms() {
        return countRooms;
    }

    public void setCountRooms(int countRooms) {
        this.countRooms = countRooms;
    }

    @Override
    public String toString() {
        return "FilialModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", countRooms=" + countRooms +
                '}';
    }
}
