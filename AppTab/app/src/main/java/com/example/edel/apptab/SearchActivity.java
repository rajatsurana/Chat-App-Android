package com.example.edel.apptab;

/**
 * Created by Rajat on 21-02-2016.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.example.edel.apptab.Volley.VolleyClick;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.example.edel.apptab.Volley.VolleyClick;

/**
 * Created by Rajat on 20-02-2016.
 */
public class SearchActivity extends AppCompatActivity implements View.OnClickListener{
    EditText et;
    EditText url;
    WebView ourBrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.websearch);

        ourBrow =(WebView) findViewById(R.id.wvBrowser);

        ourBrow.getSettings().setJavaScriptEnabled(true);
        ourBrow.getSettings().setLoadWithOverviewMode(true);
        ourBrow.getSettings().setUseWideViewPort(true);
        ourBrow.setWebViewClient(new ourViewClient());
        try{
            ourBrow.loadUrl("http://www.google.com");
        }catch (Exception e) {
            e.printStackTrace();
        }
        Button go= (Button) findViewById(R.id.bGo);
        Button refresh= (Button) findViewById(R.id.bRefresh);
        Button back= (Button) findViewById(R.id.bBack);
        Button forward= (Button) findViewById(R.id.bForward);
        Button history= (Button) findViewById(R.id.bHistory);
        url =(EditText) findViewById(R.id.etURL);
        go.setOnClickListener(this);
        refresh.setOnClickListener(this);
        back.setOnClickListener(this);
        forward.setOnClickListener(this);
        history.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId()){
            case R.id.bGo:
                String theWebsite = url.getText().toString();
                ourBrow.loadUrl(theWebsite);
                //hiding keyboard after using an EditText
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(url.getWindowToken(), 0);
                break;
            case R.id.bBack:
                if(ourBrow.canGoBack()){
                    ourBrow.goBack();}
                break;
            case R.id.bForward:
                if(ourBrow.canGoForward()){
                    ourBrow.goForward();}
                break;
            case R.id.bRefresh:
                ourBrow.reload();
                break;
            case R.id.bHistory:
                ourBrow.clearHistory();
                break;
        }
    }

}
