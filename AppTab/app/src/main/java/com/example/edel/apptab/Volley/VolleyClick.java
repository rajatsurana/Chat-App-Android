package com.example.edel.apptab.Volley;

import android.content.Context;
import android.content.Intent;

import com.example.edel.apptab.Tools.CheckNetwork;
import com.example.edel.apptab.Tools.Tools;

/**
 * Created by Rajat on 20-02-2016.
 */
public  class VolleyClick {
    //Context context;
    static String ip="http://192.168.3.1:3000";
    public static void   create_user_new(String user_name,String  password,Context context){
        CheckNetwork chkNet = new CheckNetwork(context);
        String URL = ip+"/api/users";
        if (chkNet.checkNetwork()) {
            VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
            CallVolley.create_user_Call(URL, context, user_name, password, 3);
        } else {
            Tools.showAlertDialog("Internet Available", context);
        }
    }
    public static void   post_msges(String user_id, String user_name, String to_id, String to_name,String message,Context context){
        CheckNetwork chkNet = new CheckNetwork(context);
        String URL = ip+"/api/msg";
        if (chkNet.checkNetwork()) {
            VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
            CallVolley.post_msg_Call(URL, context, user_id, user_name, to_id, to_name, message, 2);
        } else {
            Tools.showAlertDialog("Internet Available", context);
        }
    }
    public static void listUsersCall(Context context){
        //String entryNumber1 = "cs5110281", studentName1="jasmeet";
        CheckNetwork chkNet = new CheckNetwork(context);
        String URL = ip+"/api/users";
        if (chkNet.checkNetwork()) {
            VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
            CallVolley.makeUserCall(URL, context);
        } else {
            Tools.showAlertDialog("Internet Available", context);
        }
    }
    public static void find_msges(String user_id,String user_name,Context context){

        CheckNetwork chkNet = new CheckNetwork(context);
        String URL = ip+"/api/find_msgs";
        if (chkNet.checkNetwork()) {
            VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
            CallVolley.find_msg_Call(URL, context, user_id, user_name, 1);
        } else {
            Tools.showAlertDialog("Internet Available", context);
        }
    }
    public static void mutual_msgs(String to_name,String user_name,Context context,Intent intent){

        CheckNetwork chkNet = new CheckNetwork(context);
        String URL = ip+"/api/mutual_msgs";
        if (chkNet.checkNetwork()) {
            VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
            CallVolley.mutual_msgs_Call(URL, context, to_name, user_name, 4,intent);
        } else {
            Tools.showAlertDialog("Internet Available", context);
        }
    }
}
