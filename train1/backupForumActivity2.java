package com.example.passengerbuddy;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ForumActivity extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton fab;
    ArrayList<MessageDetails> messageList;
    ListView messageListView;
    MessageDisplayAdapter messageDisplayAdapter;
    Context context;
    GetMessage getMessage;
    Boolean flag = true;
    Thread updateThread;
    /*TextView textView;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        flag = true;
        context = this;
        messageListView = (ListView) findViewById(R.id.listViewForum);
        /*textView = (TextView) findViewById(R.id.textView_forumActivity);*/
        fab = (FloatingActionButton) findViewById(R.id.floatingButton);
        fab.setOnClickListener(this);

        messageList = new ArrayList<MessageDetails>();

        messageDisplayAdapter = new MessageDisplayAdapter(context, messageList);
        messageListView.setAdapter(messageDisplayAdapter);

    }




    @Override
    protected void onRestart() {
        super.onRestart();
        
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            updateThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    updateList();
                }

            });

            updateThread.start();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        flag = false;

        try{
            updateThread.interrupt();
            updateThread=null;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        try{
            updateThread.interrupt();
            updateThread=null;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }




    private void updateList(){
        getMessage = new GetMessage();
        try {
            getMessage.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }

        try {

            Thread.sleep(10000);
            updateThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    updateList();
                }
            });

            updateThread.start();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this, "Welcome to forum", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), PostMessageActivity.class));
        finish();
    }

    class GetMessage extends AsyncTask<String, Void, String>{
        URL url;
        HttpURLConnection httpURLConnection;
        BufferedReader bufferedReader;
        String line;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            messageList.clear();
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuffer stringBuffer = new StringBuffer();
            try {
                url = new URL(ServerLinks.getMessagesLink);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                while((line=bufferedReader.readLine()) != null){
                    stringBuffer.append(line + "\n");
                }
            } catch (MalformedURLException exc){
                System.out.println(exc.getMessage());
            } catch (IOException ioExc){
                System.out.println(ioExc.getMessage());
            }

            return stringBuffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            /*textView.setText(s)*/;
            JSONObject jsonObject;
            try{
                jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String messageHead = jsonObject1.getString("post_head");
                    String messageDescription = jsonObject1.getString("post_desc");
                    String userName = jsonObject1.getString("user_name");
                    String dateTime = jsonObject1.getString("date_time");
                    messageList.add(new MessageDetails(messageHead, messageDescription,
                            userName, dateTime));
                }
                messageDisplayAdapter.notifyDataSetChanged();

                ForumActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Just Updated the list", Toast.LENGTH_SHORT).show();
                    }
                });



            } catch (JSONException jExc){
                System.out.println(jExc.getMessage());
            }

        }
    }
}
