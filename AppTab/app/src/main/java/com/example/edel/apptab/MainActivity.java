package com.example.edel.apptab;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edel.apptab.Objects.MessageObject;
import com.example.edel.apptab.Tools.Tools;
import com.example.edel.apptab.Volley.VolleyClick;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";

    //EditText editText;
    ListView users;
    Intent i;
    Bundle b;

    MessageObject[] msgObjArr;
    private ArrayList<ListUsersActivity> data = new ArrayList<ListUsersActivity>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //toolbar
        i = getIntent();
        b = i.getExtras();

       msgObjArr = (MessageObject[])b.getSerializable("msgObjects");
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

    //listview
        users = (ListView)findViewById(R.id.userlist);
        generateListContent();

        MyListAdapter mylistadapter = new MyListAdapter(this,R.layout.list_user_activity,data);

        users.setAdapter(mylistadapter);
        
        users.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(MainActivity.this, "Chat opened was at "+ position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(),ChatActivity.class);
                ArrayList<String> from = new ArrayList<String>();
                for(MessageObject messageObject :  msgObjArr){
                    if(!from.contains(messageObject.getUser_name())){
                        from.add(messageObject.getUser_name());
                    }
                }
                String name= from.get(position);
                //int count=0;
                ArrayList<MessageObject> msgObj = new ArrayList<MessageObject>();
                for(MessageObject messageObject :  msgObjArr){
                    if(messageObject.getUser_name().equals(name)){
                        //count++;
                        msgObj.add(messageObject);
                    }
                }
                MessageObject[] msgs = new MessageObject[msgObj.size()];
                msgs= msgObj.toArray(msgs);
                intent.putExtra("msgs",msgs);
                Log.i("rajat",msgs[0].getTo_name()+msgs[0].getUser_name());
                VolleyClick.mutual_msgs(msgs[0].getTo_name(), msgs[0].getUser_name(),MainActivity.this,intent);
                //startActivity(intent);
            }
        });

    }
    private void generateListContent(){
        for (final ListUsersActivity entry: getUserentries()){
            data.add(entry);
            System.out.println(entry.getName());
        }
    }
    private List<ListUsersActivity> getUserentries(){
        //i.getBundleExtra("messageObjects");
        //ArrayList<MessageObject> asd= b.getParcelableArrayList("msgObjects");//new ArrayList<MessageObject>();

        ArrayList<String> from = new ArrayList<String >();

        for(MessageObject messageObject :  msgObjArr){
            //if(msgObjArr!=null)
            if(messageObject!=null)
            if(!from.contains(messageObject.getUser_name())){
                from.add(messageObject.getUser_name());
            }
        }
        //Tools.showAlertDialog("len: "+msgObjArr.length,MainActivity.this);
        Log.i("rajat",msgObjArr.length+"");
        final List<ListUsersActivity> entries = new ArrayList<ListUsersActivity>();

        for(int i = 0; i < from.size(); i++){
            entries.add(new ListUsersActivity(from.get(i),
                            new GregorianCalendar(2016,2,i).getTime(),
                            i % 2 == 0 ? R.drawable.person1 :  R.drawable.person3
                    )
            );
        }
        return entries;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_speaker:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private class MyListAdapter extends ArrayAdapter<ListUsersActivity>{

        private int layout;
        ArrayList<ListUsersActivity> data;

        public MyListAdapter(Context context, int resource, ArrayList<ListUsersActivity> data) {
            super(context, resource,data);
            layout = resource;
            this.data=data;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final ListUsersActivity entry = getItem(position);

            ViewHolder mainViewholder =null;

            if (convertView == null){

                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout,parent,false);

                ViewHolder viewHolder = new ViewHolder();
                viewHolder.chathead =(ImageView) convertView.findViewById(R.id.chat_head);
                viewHolder.name = (TextView) convertView.findViewById(R.id.Name);
                viewHolder.date = (TextView) convertView.findViewById(R.id.date);
                viewHolder.chathead.setImageResource(data.get(position).getIcon());
                viewHolder.name.setText(data.get(position).getName());
                viewHolder.date.setText(data.get(position).getPostDate().toString());

                convertView.setTag(viewHolder);



            }
            else{
                mainViewholder = (ViewHolder) convertView.getTag();
                mainViewholder.name.setText(entry.getName());


            }
            return convertView;
        }
    }

    public class ViewHolder{
        ImageView chathead;
        TextView name;
        //Button btn;
        TextView date;

    }
}
