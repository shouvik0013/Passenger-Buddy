package com.example.passengerbuddy;

import android.content.Context;
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
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.Buffer;
import java.util.concurrent.ExecutionException;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userName;
    private EditText userPassword;
    private EditText userMail;
    private EditText userContact;
    private Button submitButton;
    Thread uploadThread;
    String response;
    Context context;
    UserSignup userSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E3EB9")));
        context = this;
        userName = (EditText) findViewById(R.id.userNameSignup);
        userPassword = (EditText) findViewById(R.id.userPasswordSignup);
        userMail = (EditText) findViewById(R.id.userMailSignup);
        userContact = (EditText) findViewById(R.id.userContactSignup);
        submitButton = (Button) findViewById(R.id.submitButtonSignup);
        submitButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        final String userName = this.userName.getText().toString();
        final String userPassword = this.userPassword.getText().toString();
        final String userMail = this.userMail.getText().toString();
        final String userContact = this.userContact.getText().toString();

        userSignup = new UserSignup();
        userSignup.execute(userName, userPassword, userMail, userContact);
    }

    /*public void checkSignup(String userName, String userPassword, String userMail, String userContact){
        userSignup = new UserSignup();
        userSignup.execute(userName, userPassword, userMail, userContact).execute();
    }*/

    class UserSignup extends AsyncTask<String, Void, String> {
        URL url;
        HttpURLConnection httpURLConnection;
        String line;
        BufferedReader bufferedReader;
        StringBuffer stringBuffer;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String userName = strings[0];
            String userPassword = strings[1];
            String userMail = strings[2];
            String userContact = strings[3];
            stringBuffer = new StringBuffer();
            try {
                url = new URL(ServerLinks.signupLink);
                httpURLConnection = (HttpURLConnection) url.openConnection();

                String data = URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8");
                data += "&" + URLEncoder.encode("userPassword", "UTF-8") + "=" + URLEncoder.encode(userPassword, "UTF-8");
                data += "&" + URLEncoder.encode("userMail", "UTF-8") + "=" + URLEncoder.encode(userMail, "UTF-8");
                data += "&" + URLEncoder.encode("userContactNumber", "UTF-8") + "=" + URLEncoder.encode(userContact, "UTF-8");

                httpURLConnection.setDoOutput(true);

                // SENDING DATA TO THE SERVER
                OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());
                wr.write(data);
                wr.flush();

                // RECEIVING DATA FROM THE SERVER
                InputStream inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }

            } catch (MalformedURLException exc) {
                exc.printStackTrace();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
            System.out.println("Printing String buffer: " + stringBuffer.toString());
            return stringBuffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            JSONObject jsonObject;
            try {
                System.out.println("In onPostExecute String s: " + s);
                jsonObject = new JSONObject(s);
                response = jsonObject.getString("server_response");
                System.out.println("Server response in background: " + response + "\n");
            } catch (JSONException jexc) {
                jexc.printStackTrace();
            }
            if (response.equals("same_mail")) {
                Toast.makeText(context, "Try another Mail-Id", Toast.LENGTH_SHORT).show();
                userName.setText("");
                userPassword.setText("");
                userMail.setText("");
                userContact.setText("");
            } else if (response.equals("true")) {
                Toast.makeText(context, "Login with your new credentials", Toast.LENGTH_SHORT).show();
                onBackPressed();
                finish();
            } else if (response.equals("false")) {
                Toast.makeText(context, "Server error!!! try again....", Toast.LENGTH_SHORT).show();
                userName.setText("");
                userPassword.setText("");
                userMail.setText("");
                userContact.setText("");
            }
        }
    }
}
