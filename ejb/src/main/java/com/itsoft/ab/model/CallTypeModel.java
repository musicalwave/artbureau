package com.itsoft.ab.model;

/**
 * Created by razdvapoka on 24.12.15.
 */
public class CallTypeModel {

    public CallTypeModel(int callId, int typeId) {
        this.callId = callId;
        this.typeId = typeId;
    }

    private int callId;
    private int typeId;

    public int getCallId() {
        return callId;
    }

    public void setCallId(int callId) {
        this.callId = callId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
