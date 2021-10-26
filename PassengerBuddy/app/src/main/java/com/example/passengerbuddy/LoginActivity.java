package com.example.passengerbuddy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity {

    private EditText userNameEditText;
    private EditText userPasswordEditText;
    private Button btnLogin;
    Intent intent;
    String[] stationList;
    SharedPreferenceConfig sharedPreferenceConfig;
    Button btnSignup;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f26c78")));
        getSupportActionBar().setTitle("Log in");
        btnSignup = (Button) findViewById(R.id.btnSignup);
        intent = getIntent();
        stationList = intent.getExtras().getStringArray("StationList");
        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SignupActivity.class));
            }
        });

        if(sharedPreferenceConfig.readLoginStatus()){
            startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra("StationList", stationList));
            finish();
        }

        userNameEditText = (EditText) findViewById(R.id.userNameEditText);
        userPasswordEditText = (EditText) findViewById(R.id.userPasswordEditText);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
    }


    public void userLogin(){
        String userName = userNameEditText.getText().toString();
        String userPassword = userPasswordEditText.getText().toString();
        new CheckUserLogin().execute(userName, userPassword);
    }

    class CheckUserLogin extends AsyncTask<String, Void, String>{

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
                String userNameText = strings[0];
                String userPasswordText = strings[1];
                String data = URLEncoder.encode("userMail","UTF-8") + "=" + URLEncoder.encode(userNameText, "UTF-8");
                data += "&" + URLEncoder.encode("userPassword", "UTF-8") + "=" + URLEncoder.encode(userPasswordText, "UTF-8");
                url = new URL(UserLoginLinks.userLoginUrl);
                httpURLConnection = (HttpURLConnection) url.openConnection();

                // SEND POST DATA REQUEST
                httpURLConnection.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());
                wr.write(data);
                wr.flush();

                // get response from the server
                InputStream inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                while((line=bufferedReader.readLine()) != null){
                    stringBuffer.append(line + "\n");
                }


            }catch (MalformedURLException exc){
                System.out.println(exc.getMessage());
            } catch (IOException exc){
                System.out.println(exc.getMessage());
            }

            System.out.println("In doInBackground : " + stringBuffer.toString());
            return stringBuffer.toString();

        }

        @Override
        protected void onPostExecute(String s) {
            JSONObject jsonObject;
            try{
                jsonObject = new JSONObject(s);
                JSONObject jsonObject1 = jsonObject.getJSONObject("server_response");
                String status = jsonObject1.getString("status");
                if(status.equals("true")){
                    String uId = jsonObject1.getString("uId");
                    String userMail = jsonObject1.getString("userMail");
                    String userName = jsonObject1.getString("userName");

                    // DEBUGGING PURPOSE
                    System.out.println("In LoginActivity: uId = " + uId + " userName = " + userName + "\n");


                    sharedPreferenceConfig.writeLoginStatus(true);
                    sharedPreferenceConfig.writeUserId(uId);
                    sharedPreferenceConfig.writeUserName(userName);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra("StationList", stationList));
                    finish();
                } else{
                    Toast.makeText(getApplicationContext(), "Login failed!!! Try again....", Toast.LENGTH_SHORT).show();
                    userNameEditText.setText("");
                    userPasswordEditText.setText("");
                }
                /*String response = jsonObject.getString("server_response");
                if(response.equals("true")){
                    sharedPreferenceConfig.writeLoginStatus(true);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra("StationList", stationList));
                    finish();
                } else{
                    Toast.makeText(getApplicationContext(), "Login failed!!! Try again....", Toast.LENGTH_SHORT).show();
                    userNameEditText.setText("");
                    userPasswordEditText.setText("");
                }*/
            } catch (JSONException jexc){
                System.out.println(jexc.getMessage());
            }
        }
    }
}
