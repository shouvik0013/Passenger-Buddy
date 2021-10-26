package com.example.passengerbuddy;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.CompletableFuture;

public class PostMessageActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText messageHeadEditText;
    private EditText messageDescriptionEditText;
    private Button postButton;
    SharedPreferenceConfig sharedPreferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_message);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E3EB9")));
        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        messageHeadEditText = (EditText) findViewById(R.id.postHeadTextView);
        messageDescriptionEditText = (EditText) findViewById(R.id.postDescriptionTextView);
        postButton = (Button) findViewById(R.id.postMessageButtonPostMessageActivity);

        postButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String messageHeadCharacters = messageHeadEditText.getText().toString();
        String messageDescriptionCharacters = messageDescriptionEditText.getText().toString();
        new PostMessage().execute(messageHeadCharacters, messageDescriptionCharacters);
    }

    class PostMessage extends AsyncTask<String, Void, String>{

        URL url;
        HttpURLConnection httpURLConnection;
        BufferedReader bufferedReader;
        String line;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuffer stringBuffer = new StringBuffer();
            try{
                String messageHead = strings[0];
                String messageDescription = strings[1];
                String uId = sharedPreferenceConfig.readUid();
                System.out.println("Message head: " + messageHead + "\n");
                System.out.println("Message description: " + messageDescription + "\n");
                System.out.println("User id: " + uId + "\n");

                String data = URLEncoder.encode("post_head", "UTF-8") + "=" + URLEncoder.encode(messageHead, "UTF-8");
                data += "&" + URLEncoder.encode("post_desc", "UTF-8") + "=" + URLEncoder.encode(messageDescription, "UTF-8");
                data += "&" + URLEncoder.encode("uId", "UTF-8") + "=" + URLEncoder.encode(uId, "UTF-8");
                url = new URL(ServerLinks.messageUpload);
                httpURLConnection = (HttpURLConnection) url.openConnection();



                System.out.println("Requesting data: " + data);

                // SENDING DATA TO THE SERVER
                httpURLConnection.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());
                wr.write(data);
                wr.flush();

                // RECEIVING DATA FROM THE SERVER
                InputStream inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                while((line = bufferedReader.readLine()) != null){
                    stringBuffer.append(line + "\n");
                }

            } catch (MalformedURLException exc){
                System.out.println("In malformedUrlException block");
                System.out.println(exc.getMessage());
            } catch (IOException ioExc){
                System.out.println("In IOException block");
                System.out.println(ioExc.getMessage());
            }

            System.out.println("In doInBackground : " + stringBuffer.toString());
            return stringBuffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            JSONObject jsonObject;
            try{
                jsonObject = new JSONObject(s);
                String response = jsonObject.getString("server_response");
                if(response.equals("true")){
                    Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), ForumActivity.class));
                    finish();
                } else{
                    Toast.makeText(getApplicationContext(), "Message not sent", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), ForumActivity.class));
                    finish();
                }
            } catch (JSONException jExc){
                System.out.println("In jsonException onPost method " + "\n");
                System.out.println(jExc.getMessage());
            }
        }
    }
}
