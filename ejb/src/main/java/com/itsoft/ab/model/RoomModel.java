package com.itsoft.ab.model;

import com.itsoft.ab.BasicModel;

/**
 * Created with IntelliJ IDEA.
 * UserModel: itertychnyi
 * Date: 13.10.13
 * Time: 18:49
 */
public class RoomModel extends BasicModel{
    private int id;
    private String name;
    private int filialId;
    private String filialName;

    public RoomModel() {
    }

    public RoomModel(int id, int filialId, String name) {
        this.id = id;
        this.filialId = filialId;
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

    public int getFilialId() {
        return filialId;
    }

    public void setFilialId(int filialId) {
        this.filialId = filialId;
    }

    public String getFilialName() {
        return filialName;
    }

    public void setFilialName(String filialName) {
        this.filialName = filialName;
    }

    @Override
    public String toString() {
        return "RoomModel{" +
                "id=" + id +
                ", filialId=" + filialId +
                ", name='" + name + '\'' +
                '}';
    }
}
