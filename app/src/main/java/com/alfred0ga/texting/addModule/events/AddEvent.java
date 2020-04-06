package com.alfred0ga.texting.addModule.events;

public class AddEvent {
    public static final int SEND_REQUEST_SUCCESS = 0;
    public static final int ERROR_SERVER = 100;
    public static final int ERROR_EXIST = 101;

    private int typeEvent;
    private int resMsg;

    public AddEvent(){
    }

    public int getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(int typeEvent) {
        this.typeEvent = typeEvent;
    }

    public int getResMsg() {
        return resMsg;
    }

    public void setResMsg(int resMsg) {
        this.resMsg = resMsg;
    }
}
