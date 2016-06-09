package com.example.edel.apptab.Volley;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.example.edel.apptab.ChatActivity;
import com.example.edel.apptab.MainActivity;
import com.example.edel.apptab.Objects.MessageObject;
import com.example.edel.apptab.Tools.Tools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Rajat on 19-02-2016.
 */
public class JSONParser {
    public static void LoginApiJsonParser(String JsonStringResult,Context con)
    {
        try {
            boolean status;

            JSONObject user;
            String last_name="",reset_password_key="",registration_key="",
                    first_name="",entry_no="",email="",username="",registration_id="",password="";
            int id =0;
            int type_=0;
            //create json object from response string
            JSONObject resultJson = new JSONObject(JsonStringResult);
            if (resultJson.has("success"))
            {

                status = resultJson.getBoolean("success");

                user= resultJson.getJSONObject("user");
                if(status)
                {
                    if(user.has("last_name")){last_name=user.getString("last_name");}
                    if(user.has("reset_password_key")){reset_password_key=user.getString("reset_password_key");}
                    if(user.has("registration_key")){registration_key=user.getString("registration_key");}
                    if(user.has("first_name")){first_name=user.getString("first_name");}
                    if(user.has("entry_no")){entry_no=user.getString("entry_no");}
                    if(user.has("email")){email=user.getString("email");}
                    if(user.has("username")){username=user.getString("username");}
                    if(user.has("registration_id")){registration_id=user.getString("registration_id");}
                    if(user.has("password")){password=user.getString("password");}
                    if(user.has("id")){id=user.getInt("id");}
                    if(user.has("type_")){type_=user.getInt("type_");}
                    Tools.showAlertDialog(last_name + " "+id+" "+reset_password_key+" "
                            +registration_key+" "+first_name+" "+entry_no+" "+username+" "
                            +registration_id+" "+password+" "+type_+" "+email,con);
                }
                else
                {

                }
            }
        }
        catch (Exception e)
        {
            Log.i("rajat","Exception: Login: "+ e.getLocalizedMessage());
        }
    }
    //post_msgApiJsonParser
    public static void post_msgApiJsonParser(String JsonStringResult,Context con)
    {
        try {
            String message_create="";
            JSONObject msgObj=new JSONObject();
            String user_name="",to_name="",time_readable="",msg="";
            String user_id ="",_id="",to_id="";
            //create json object from response string
            JSONObject resultJson = new JSONObject(JsonStringResult);
            if (resultJson.has("message")) {
                message_create=resultJson.getString("message");
            }
            if (resultJson.has("newMsg")) {

                    msgObj = resultJson.getJSONObject("newMsg");
                    if (msgObj.has("user_id")) {user_id = msgObj.getString("user_id");                    }
                    if (msgObj.has("msg")) {msg = msgObj.getString("msg");}
                    if (msgObj.has("user_name")) {user_name = msgObj.getString("user_name");}
                    if (msgObj.has("to_name")) {to_name = msgObj.getString("to_name");}
                    if (msgObj.has("time_readable")) {time_readable = msgObj.getString("time_readable");                    }
                    if (msgObj.has("to_id")) {to_id = msgObj.getString("to_id");}
                    if (msgObj.has("_id")) {to_id = msgObj.getString("_id");}
                    //after each value is initialized
                    //messageObjects[i]=new MessageObject(user_name,to_name,time_readable,msg,user_id,_id,to_id);

                //Tools.showAlertDialog(user_id+" "+msg,con);
            }
            //Tools.showAlertDialog(message_create+"",con);
            Log.i("rajat",msg+" msg created");
            // do something with NotificationObjectArray
        }
        catch (Exception e)
        {
            Log.i("rajat", e.getLocalizedMessage());
        }

    }
    //create_userApiJsonParser
    public static void create_userApiJsonParser(String JsonStringResult,Context con)
    {
        try {

            JSONObject user= new JSONObject();
            String username="",password="";
            String message="";
            //create json object from response string
            JSONObject resultJson = new JSONObject(JsonStringResult);
            if (resultJson.has("message")) {
                message = resultJson.getString("message");
                              // Tools.showAlertDialog(messageObjects.length+" : length",con);
            }
            if(resultJson.has("user")){
                user = resultJson.getJSONObject("user");
                if(user.has("name")){
                    username=user.getString("name");}
                if(user.has("password")){
                    password=user.getString("password");
                }
            }
            Tools.showAlertDialog(message+" "+username+" "+password+" : length",con);
            // do something with NotificationObjectArray
        }
        catch (Exception e)
        {
            Log.i("rajat", e.getLocalizedMessage());
        }
    }
    //mutual_msgApiJsonParser
    public static void mutual_msgApiJsonParser(String JsonStringResult,Context con,Intent intent)
    {
        try {
            JSONArray msgs=new JSONArray();
            JSONObject msgObj=new JSONObject();
            String user_name="",to_name="",time_readable="",msg="";
            String user_id ="",_id="",to_id="";
            //create json object from response string
            JSONObject resultJson = new JSONObject(JsonStringResult);
            if (resultJson.has("message")) {
                msgs = resultJson.getJSONArray("message");
                MessageObject[] messageObjects = new MessageObject[msgs.length()];
                for (int i = 0; i < msgs.length(); i++) {
                    msgObj = msgs.getJSONObject(i);
                    if (msgObj.has("user_id")) {user_id = msgObj.getString("user_id");                    }
                    if (msgObj.has("msg")) {msg = msgObj.getString("msg");}
                    if (msgObj.has("user_name")) {user_name = msgObj.getString("user_name");}
                    if (msgObj.has("to_name")) {to_name = msgObj.getString("to_name");}
                    if (msgObj.has("time_readable")) {time_readable = msgObj.getString("time_readable");                    }
                    if (msgObj.has("to_id")) {to_id = msgObj.getString("to_id");}
                    if (msgObj.has("_id")) {_id = msgObj.getString("_id");}
                    //after each value is initialized
                    messageObjects[i]=new MessageObject(user_name,to_name,time_readable,msg,user_id,_id,to_id);
                }
                //Context a;
                Log.i("rajat",messageObjects.length+" :len "+ msgs.length());
                //
                //intent = new Intent(con, ChatActivity.class);
                intent.putExtra("msgObjects",messageObjects);
                con.startActivity(intent);
                //


            }
            // do something with NotificationObjectArray
        }
        catch (Exception e)
        {
            Log.i("rajat", e.getLocalizedMessage());
        }

    }
    public static void find_msgApiJsonParser(String JsonStringResult,Context con)
    {
        try {
            JSONArray msgs=new JSONArray();
            JSONObject msgObj=new JSONObject();
            String user_name="",to_name="",time_readable="",msg="";
            String user_id ="",_id="",to_id="";
            //create json object from response string
            JSONObject resultJson = new JSONObject(JsonStringResult);
            if (resultJson.has("msg")) {
                msgs = resultJson.getJSONArray("msg");
                MessageObject[] messageObjects = new MessageObject[msgs.length()];
                for (int i = 0; i < msgs.length(); i++) {
                    msgObj = msgs.getJSONObject(i);
                    if (msgObj.has("user_id")) {user_id = msgObj.getString("user_id");                    }
                    if (msgObj.has("msg")) {msg = msgObj.getString("msg");}
                    if (msgObj.has("user_name")) {user_name = msgObj.getString("user_name");}
                    if (msgObj.has("to_name")) {to_name = msgObj.getString("to_name");}
                    if (msgObj.has("time_readable")) {time_readable = msgObj.getString("time_readable");                    }
                    if (msgObj.has("to_id")) {to_id = msgObj.getString("to_id");}
                    if (msgObj.has("_id")) {to_id = msgObj.getString("_id");}
                    //after each value is initialized
                    messageObjects[i]=new MessageObject(user_name,to_name,time_readable,msg,user_id,_id,to_id);
                }
                //Context a;
                Log.i("rajat",messageObjects.length+" :len");
                //Tools.showAlertDialog(messageObjects.length+" : length",con);
                //Bundle b =new Bundle();
                //ArrayList<MessageObject> ass= new ArrayList<MessageObject>(Arrays.asList(messageObjects));
                //intent.putParcelableArrayListExtra("messageObjects",(ArrayList<? extends Parcelable>)ass);
                //Intent.putExtra("car", (Serializable)myCarObject);
                //intent.putExtra()
//intent.putParcelableArrayListExtra("messageObjects",(ArrayList<? extends Parcelable>)ass);
                //Intent.putExtra("car", (Serializable)myCarObject);
                //intent.putExtra()

                Intent intent = new Intent(con, MainActivity.class);
                intent.putExtra("msgObjects",messageObjects);
                con.startActivity(intent);
            }
                // do something with NotificationObjectArray
        }
        catch (Exception e)
        {
            Log.i("rajat", e.getLocalizedMessage());
        }

    }
}