package com.example.edel.apptab.Objects;

import java.io.Serializable;

/**
 * Created by Rajat on 20-02-2016.
 */
public class MessageObject implements Serializable{

    String user_name="",to_name="",time_readable="",msg="";
    String user_id ="",_id="",to_id="";

    public MessageObject(String user_name, String to_name, String time_readable, String msg, String user_id, String _id, String to_id) {
        this.user_name = user_name;
        this.to_name = to_name;
        this.time_readable = time_readable;
        this.msg = msg;
        this.user_id = user_id;
        this._id = _id;
        this.to_id = to_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getTo_name() {
        return to_name;
    }

    public String getTime_readable() {
        return time_readable;
    }

    public String getMsg() {
        return msg;
    }

    public String getUser_id() {
        return user_id;
    }

    public String get_id() {
        return _id;
    }

    public String getTo_id() {
        return to_id;
    }
}
