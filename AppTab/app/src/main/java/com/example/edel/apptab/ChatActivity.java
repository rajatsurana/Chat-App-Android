package com.example.edel.apptab;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.DataSetObserver;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.edel.apptab.Objects.MessageObject;
import com.example.edel.apptab.Objects.MessageObjectComparator;
import com.example.edel.apptab.Volley.VolleyClick;

import java.util.ArrayList;
import android.speech.RecognizerIntent;
import android.widget.Toast;

import java.util.Collections;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private static final String TAG = "ChatActivity";
    private TextToSpeech tts;
    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private Button buttonSend,buttonSpeechToText,btnSpeak,btnSearch;
    String user_name="";
    String to_name="";
    Intent intent;
    Bundle b;
    private boolean side = false;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        tts = new TextToSpeech(this, this);
        btnSpeak = (Button) findViewById(R.id.buttonSpeak);
        btnSearch=(Button)findViewById(R.id.websearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i -
            }
        });
        intent = getIntent();
        b=intent.getExtras();
        MessageObject[] strFrom =(MessageObject[]) b.getSerializable("msgObjects");
        MessageObject[] strTo =(MessageObject[]) b.getSerializable("msgs");
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.content);
        user_name=strTo[0].getTo_name();
        to_name=strTo[0].getUser_name();
        buttonSend = (Button) findViewById(R.id.buttonSend);
        buttonSpeechToText=(Button) findViewById(R.id.buttonSpeechToText);

        listView = (ListView) findViewById(R.id.listView1);
        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.activity_chat_singlemessage);
        ArrayList<MessageObject> allMsgs = new ArrayList<MessageObject>();
        for(int c=0; c<strFrom.length;c++){
            allMsgs.add(strFrom[c]);
        }
        for(int c=0; c<strTo.length;c++){
            allMsgs.add(strTo[c]);
        }
        Collections.sort(allMsgs,new MessageObjectComparator());
        listView.setAdapter(chatArrayAdapter);
        for(MessageObject s : allMsgs){
            if(s.getUser_name().equals(strFrom[0].getUser_name())){
                chatArrayAdapter.add(new ChatMessage(false,s.getMsg()));
            }else{
                chatArrayAdapter.add(new ChatMessage(true,s.getMsg()));
            }

        }
//        for(MessageObject s : strTo){
//            chatArrayAdapter.add(new ChatMessage(true,s.getMsg()));
//        }

        chatText = (EditText) findViewById(R.id.chatText);

        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendChatMessage();
            }
        });
        buttonSpeechToText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
            }
        });
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakOut();
            }
        });
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });
    }

    private boolean sendChatMessage(){
        String sendText=chatText.getText().toString();
        if(sendText.length()!=0) {
            chatArrayAdapter.add(new ChatMessage(false, sendText));
            //side = !side;
            VolleyClick.post_msges("", user_name, "", to_name, sendText, ChatActivity.this);

            chatText.setText("");
            return true;
        }
        return false;
    }
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    chatText.setText(result.get(0));
                }
                break;
            }

        }
    }
    private void speakOut() {

        String text = chatText.getText().toString();

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                btnSpeak.setEnabled(true);
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }
    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

}














