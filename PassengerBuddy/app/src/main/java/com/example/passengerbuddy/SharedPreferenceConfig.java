package com.example.passengerbuddy;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceConfig {

    Context context;
    private SharedPreferences sharedPreferences;
    public SharedPreferenceConfig(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.login_preference), Context.MODE_PRIVATE);
    }

    public void writeLoginStatus(Boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.login_status_preference), status);
        editor.commit();
    }

    public boolean readLoginStatus(){
        Boolean status;
        status = sharedPreferences.getBoolean(context.getResources().getString(R.string.login_status_preference), false);
        return  status;
    }

    public void writeUserId(String uId){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.userId), uId);
        editor.commit();
/*        System.out.println(sharedPreferences.getString(context.getResources().getString(R.string.userId), "no uId"));
        System.out.println(sharedPreferences.getString(context.getResources().getString(R.string.userName), "no userName"));*/
    }

    public void writeUserName(String userName){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.userName), userName);
        editor.commit();
    }

    public String readUid(){
        String uId;
        uId = sharedPreferences.getString(context.getResources().getString(R.string.userId), "no uId");
        return uId;
    }

    public String readUserName(){
        String userName;
        userName = sharedPreferences.getString(context.getResources().getString(R.string.userName), "no userName");
        return userName;
    }
}
