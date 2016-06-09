package com.example.edel.apptab.Volley;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.edel.apptab.MainActivity;
import com.example.edel.apptab.Objects.MessageObject;
import com.example.edel.apptab.Tools.Tools;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CallVolley {
        //dialog box appears showing progress
        private static ProgressDialog pDialog;
        //TimeOut and maximum number of tries
        private static void setCustomRetryPolicy(StringRequest jsonObjReq) {
                Log.i("rajat", "setCustomRetryPolicy");
                jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
        // method that will send request  to server and get a response back
        public static void makeUserCall(String url, final Context context)
        {
                pDialog=  Tools.showProgressBar(context);

                        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
                        {
                        // if a reponse is recieved after sending request
                                @Override
                                public void onResponse(String response)
                                {
                                        try
                                        {
                                                Log.i("rajat", " onResponseActive " + response);
                                                //JSONParser.LoginApiJsonParser(response, context);
                                                pDialog.dismiss();
                                        }
                                        catch (Exception localException)
                                        {
                                                Log.i("rajat"," onResponseException "+localException.getMessage());
                                                localException.printStackTrace();
                                        }
                                }
                        }
                                , new Response.ErrorListener()
                        {
                                //if error occurs
                                @Override
                                public void onErrorResponse(VolleyError error)
                                {
                                        Log.i("rajat", "onErrorResponse" + error.toString());
                                           pDialog.dismiss();
                                }
                        }){

                        };

                //how many times to try and for how much duration
                        setCustomRetryPolicy(request);
                //get instance of volleysingleton and add reuest to the queue
                        VolleySingleton.getInstance(context).addToRequestQueue(request);
                }
                // method will give message to user depending on respons from server


        public static void makeLogoutCall(String url, final Context context)
        {
                pDialog=  Tools.showProgressBar(context);

                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
                {
                        // if a reponse is recieved after sending request
                        @Override
                        public void onResponse(String response)
                        {
                                try
                                {
                                        Log.i("rajat", " onResponseActive " + response);
                                        //LoginApiJsonParser(response, context);

                                        pDialog.dismiss();
                                }
                                catch (Exception localException)
                                {
                                        Log.i("rajat"," onResponseException "+localException.getMessage());
                                        localException.printStackTrace();
                                }
                        }
                }
                        , new Response.ErrorListener()
                {
                        //if error occurs
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                                Log.i("rajat", "onErrorResponse" + error.toString());
                                pDialog.dismiss();


                        }
                });

                //how many times to try and for how much duration
                setCustomRetryPolicy(request);
                //get instance of volleysingleton and add reuest to the queue
                VolleySingleton.getInstance(context).addToRequestQueue(request);
        }
        //post_msg_Call
        //create_user_Call

        public static void find_msg_Call(String url, final Context context,final String user_id,final String user_name, final int API_NUMBER){
                pDialog=  Tools.showProgressBar(context);

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                        // if a reponse is recieved after sending request
                        @Override
                        public void onResponse(String response)
                        {
                                try
                                {
                                        switch (API_NUMBER){
                                                case 1:
                                                        JSONParser.find_msgApiJsonParser(response, context);
                                                        break;

                                        }
                                        Log.i("rajat", " onResponseActive " + response);

                                        pDialog.dismiss();
                                }
                                catch (Exception localException)
                                {
                                        Log.i("rajat"," onResponseException "+localException.getMessage());
                                        localException.printStackTrace();
                                }
                        }
                }
                        , new Response.ErrorListener()
                {
                        //if error occurs
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                                error.printStackTrace();
                                Log.i("rajat", "onErrorResponse" + error.toString());
                                pDialog.dismiss();
                                Tools.showAlertDialog(error.toString(), context);
                                MessageObject[] messageObjects = new MessageObject[1];

                                //messageObjects[0]=new MessageObject("","","","","","","");
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.putExtra("msgObjects", (Serializable)messageObjects);
                                context.startActivity(intent);

                        }

                }
                ){
                        @Override
                        public String getBodyContentType() {
                                return "application/x-www-form-urlencoded; charset=UTF-8";
                        }
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                        //params.put("user_id",user_id);
                                        params.put("user_name",user_name);
                                return params;
                        }
                };
                try{
                        Log.i("rajat", request + " : request" + request.getBody().length);
                        for(int i=0;i<request.getBody().length; i++){
                                //Log.i("rajat", request + " : 1request" + request.getBody()[i]);
                        }
                        String str = new String(request.getBody(), StandardCharsets.UTF_8);
                        //String str2 = IOUtils.toString(request.getBody(), StandardCharsets.UTF_8);
                        Log.i("rajat",str +": req1");
                }catch (AuthFailureError e){

                }
                //how many times to try and for how much duration
                setCustomRetryPolicy(request);
                //get instance of volleysingleton and add reuest to the queue
                VolleySingleton.getInstance(context).addToRequestQueue(request);
        }
        //notify

        public static void post_msg_Call(String url, final Context context,final String user_id,final String user_name,final String to_id,final String to_name,final String message,final int API_NUMBER){
                pDialog=  Tools.showProgressBar(context);

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                        // if a reponse is recieved after sending request
                        @Override
                        public void onResponse(String response)
                        {
                                try
                                {
                                        switch (API_NUMBER){
                                                case 2:
                                                        JSONParser.post_msgApiJsonParser(response, context);
                                                        break;

                                        }
                                        Log.i("rajat", " onResponseActive " + response);

                                        pDialog.dismiss();
                                }
                                catch (Exception localException)
                                {
                                        Log.i("rajat"," onResponseException "+localException.getMessage());
                                        localException.printStackTrace();
                                }
                        }
                }
                        , new Response.ErrorListener()
                {
                        //if error occurs
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                                error.printStackTrace();
                                Log.i("rajat", "onErrorResponse" + error.toString());
                                pDialog.dismiss();
                                Tools.showAlertDialog(error.toString(), context);

                        }

                }
                ){
                        @Override
                        public String getBodyContentType() {
                                return "application/x-www-form-urlencoded; charset=UTF-8";
                        }
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                //message.user_id = req.body.user_id || 'default',
                                //message.msg = req.body.msg || 'default',
                                 //       message.user_name=req.body.user_name ||'default',
                                  //      message.to_name=req.body.to_name ||'default',
                                   //     message.to_id=req.body.to_id ||'default'
                               // params.put("user_id",user_id);
                                params.put("user_name",user_name);
                                //params.put("to_id",to_id);
                                params.put("to_name",to_name);
                                params.put("msg",message);
                                return params;
                        }
                };
                try{
                        Log.i("rajat", request + " : request" + request.getBody().length);
                        for(int i=0;i<request.getBody().length; i++){
                                //Log.i("rajat", request + " : 1request" + request.getBody()[i]);
                        }
                        String str = new String(request.getBody(), StandardCharsets.UTF_8);
                        //String str2 = IOUtils.toString(request.getBody(), StandardCharsets.UTF_8);
                        Log.i("rajat",str +": req1");
                }catch (AuthFailureError e){

                }
                //how many times to try and for how much duration
                Log.i("rajat", request + " : request");
                setCustomRetryPolicy(request);
                //get instance of volleysingleton and add reuest to the queue
                VolleySingleton.getInstance(context).addToRequestQueue(request);
        }
        public static void create_user_Call(String url, final Context context,final String user_name,final String password,final int API_NUMBER){
                pDialog=  Tools.showProgressBar(context);

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                        // if a reponse is recieved after sending request
                        @Override
                        public void onResponse(String response)
                        {
                                try
                                {
                                        switch (API_NUMBER){
                                                case 3:
                                                        JSONParser.create_userApiJsonParser(response, context);
                                                        break;

                                        }
                                        Log.i("rajat", " onResponseActive " + response);

                                        pDialog.dismiss();
                                }
                                catch (Exception localException)
                                {
                                        Log.i("rajat"," onResponseException "+localException.getMessage());
                                        localException.printStackTrace();
                                }
                        }
                }
                        , new Response.ErrorListener()
                {
                        //if error occurs
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                                error.printStackTrace();
                                Log.i("rajat", "onErrorResponse" + error.toString());
                                pDialog.dismiss();
                                Tools.showAlertDialog(error.toString(), context);

                        }

                }
                ){
                        @Override
                        public String getBodyContentType() {
                                return "application/x-www-form-urlencoded; charset=UTF-8";
                        }
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("user_name",user_name);
                                params.put("password",password);
                                return params;
                        }
                };

                //how many times to try and for how much duration
                setCustomRetryPolicy(request);
                //get instance of volleysingleton and add reuest to the queue
                VolleySingleton.getInstance(context).addToRequestQueue(request);
        }


        public static void mutual_msgs_Call(String url, final Context context,final String to_name,final String user_name, final int API_NUMBER,final Intent intent){
                pDialog=  Tools.showProgressBar(context);

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                        // if a reponse is recieved after sending request
                        @Override
                        public void onResponse(String response)
                        {
                                try
                                {
                                        switch (API_NUMBER){
                                                case 4:
                                                        JSONParser.mutual_msgApiJsonParser(response, context, intent);
                                                        break;

                                        }
                                        Log.i("rajat", " onResponseActive " + response);

                                        pDialog.dismiss();
                                }
                                catch (Exception localException)
                                {
                                        Log.i("rajat"," onResponseException "+localException.getMessage());
                                        localException.printStackTrace();
                                }
                        }
                }
                        , new Response.ErrorListener()
                {
                        //if error occurs
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                                error.printStackTrace();
                                Log.i("rajat", "onErrorResponse" + error.toString());
                                pDialog.dismiss();
                                Tools.showAlertDialog(error.toString(), context);
                        }

                }
                ){
                        @Override
                        public String getBodyContentType() {
                                return "application/x-www-form-urlencoded; charset=UTF-8";
                        }
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();

                                params.put("msg_to",to_name);
                                params.put("msg_from",user_name);//user_name

                                Log.i("rajat", params.toString() + " : params");
                                return params;
                        }
                };
                //how many times to try and for how much duration

                setCustomRetryPolicy(request);
                //get instance of volleysingleton and add reuest to the queue
                try{
                        Log.i("rajat", request + " : request" + request.getBody().length);
                        for(int i=0;i<request.getBody().length; i++){
                                //Log.i("rajat", request + " : 1request" + request.getBody()[i]);
                        }
                        String str = new String(request.getBody(), StandardCharsets.UTF_8);
                        //String str2 = IOUtils.toString(request.getBody(), StandardCharsets.UTF_8);
                        Log.i("rajat",str +": req1");
                }catch (AuthFailureError e){

                }
                VolleySingleton.getInstance(context).addToRequestQueue(request);
        }
        //notify
}