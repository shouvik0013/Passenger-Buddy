package com.example.passengerbuddy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{

    // url which fetches trains info
    private String fetchTrainInfoUrl = "http://3.18.81.69/AWSUpload/fetchTrainInfo6.php";

    // Source station autocomplete textview
    AutoCompleteTextView sourceSationTextView;
    AutoCompleteTextView destinationStationTextView;

    ArrayList<String> trainNameList = new ArrayList<String>();
    ArrayList<Integer> trainNumberList = new ArrayList<Integer>();
    ArrayList<String> stationName = new ArrayList<String>();
    ArrayList<String> stationCode = new ArrayList<String>();
    ArrayList<String> arrivalTime = new ArrayList<String>();
    ArrayList<String> departureTime = new ArrayList<String>();
    Intent intent;
    String[] stationList;
    String sourceStationName;
    String sourceStationCode;
    String destinationStationName;
    String destinationStationCode;
    String weekday;
    Calendar c;
    Button searchButton;
    TextView navHeadTextView;
    Context context;

    SharedPreferenceConfig sharedPreferenceConfig;

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sourceSationTextView = (AutoCompleteTextView) findViewById(R.id.sourceAutoComplete);
        destinationStationTextView = (AutoCompleteTextView) findViewById(R.id.destinationAutoComplete);
        context = this;
        // GETTING NAME OF THE WEEK DAY
        c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if(dayOfWeek == 1) weekday = "sun";
        else if(dayOfWeek == 2) weekday = "mon";
        else if(dayOfWeek == 3) weekday = "tue";
        else if(dayOfWeek == 4) weekday = "wed";
        else if(dayOfWeek == 5) weekday = "thu";
        else if(dayOfWeek == 6) weekday = "fri";
        else if(dayOfWeek == 7) weekday = "sat";
        System.out.println("Name of the day " + weekday);
        intent = getIntent();
        stationList = intent.getExtras().getStringArray("StationList");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, stationList);
        sourceSationTextView.setAdapter(adapter);
        destinationStationTextView.setAdapter(adapter);
        searchButton = (Button) findViewById(R.id.searchTrain);
        searchButton.setOnClickListener(this);

        // SHARED PREFERENCE PART
        sharedPreferenceConfig = new SharedPreferenceConfig(this);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E3EB9")));
        // SETTING UP DRAWER
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_mainActivity);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.open, R.string.close);
        navigationView = (NavigationView) findViewById(R.id.navigation_view_main_activity);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);

        navHeadTextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_head_user_name);
        navHeadTextView.setText(sharedPreferenceConfig.readUserName());
    }

/*
    String trainInfoLongString = "{\"server_response\":[{\"trainName\":\"SEALDAH-NAIHATI LOCAL\",\"trainNumber\":\"3380\",\"schedule\":[{\"stationName\":\"SEALDAH\",\"stationCode\":\"SDAH\",\"arrival\":\"09:21:00\"},{\"stationName\":\"DUM DUM JUNCTION\",\"stationCode\":\"DDJ\",\"arrival\":\"09:50:00\"}]},{\"trainName\":\"SEALDAH-BANGAON LOCAL\",\"trainNumber\":\"3371\",\"schedule\":[{\"stationName\":\"SEALDAH\",\"stationCode\":\"SDAH\",\"arrival\":\"09:23:00\"},{\"stationName\":\"BIDHANNAGAR ROAD\",\"stationCode\":\"BNXR\",\"arrival\":\"09:30:00\"},{\"stationName\":\"DUM DUM JUNCTION\",\"stationCode\":\"DDJ\",\"arrival\":\"09:40:00\"},{\"stationName\":\"DUMDUM CANTONMENT\",\"stationCode\":\"DDC\",\"arrival\":\"09:55:00\"}]}]}";
*/

    // NAVIGATION DRAWER IMPLEMENTATION PART


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_train_search:
                Toast.makeText(this, "Already in train search", Toast.LENGTH_SHORT).show();
                mDrawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_form:
                Toast.makeText(this, "Activity is under development", Toast.LENGTH_SHORT).show();
                mDrawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(getApplicationContext(), ForumActivity.class));
                break;
            case R.id.nav_pnr:
                Toast.makeText(this, "Activity is under development", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(context, PnrActivity.class));
                mDrawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_logout:
                if(sharedPreferenceConfig.readLoginStatus()){
                    sharedPreferenceConfig.writeLoginStatus(false);
                    finish();
                }
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if(mDrawer.isDrawerOpen(GravityCompat.START)){
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        String strs[] = sourceSationTextView.getText().toString().split(" - ");
        String strs2[] = destinationStationTextView.getText().toString().split(" - ");
        sourceStationName = strs[0];
        sourceStationCode = strs[1];
        destinationStationName = strs2[0];
        destinationStationCode = strs2[1];
        System.out.println("In MainActivity: " + destinationStationCode);
        System.out.println("In MainActivity: " + sourceStationCode);
        sourceSationTextView.setText("");
        destinationStationTextView.setText("");

        String queryString = "?sourceCode=" + sourceStationCode + "&destinationCode=" + destinationStationCode +
                                "&weekDay=" + weekday;
        System.out.println("In MainActivity Query String: " + queryString);
        new DownloadTrainList().execute(queryString);
    }

    // CLASS WHICH DOWNLOADS TRAINS INFO INTO JSON STRING
    private class DownloadTrainList extends AsyncTask<String, Void, String>{
        URL url;
        HttpURLConnection httpURLConnection;
        BufferedReader bufferedReader;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuffer stringBuffer = new StringBuffer();
            String trainInfoUrl = fetchTrainInfoUrl + strings[0];
            String line;
            try{
                InputStream inputStream;
                url = new URL(trainInfoUrl);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                while((line=bufferedReader.readLine()) != null){
                    stringBuffer.append(line + "\n");
                }


            } catch (MalformedURLException mfexc){
                System.out.println("In MalformedURLException block");
                System.out.println(mfexc.getMessage());
            } catch (IOException ioexc){
                System.out.println("In IOException block\n");
                System.out.println(ioexc.getMessage());
            }

            System.out.println("In doInBackground : " + stringBuffer.toString());
            return stringBuffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {

            // NEW CODE
            try{
                JSONObject jsonObject = new JSONObject(s);
                JSONArray serverResponseArray = jsonObject.getJSONArray("server_response");
                for(int i=0; i<serverResponseArray.length(); i++){
                    JSONObject jsonObject1 = serverResponseArray.getJSONObject(i);
                    trainNameList.add(jsonObject1.getString("trainName"));
                    trainNumberList.add(Integer.parseInt(jsonObject1.getString("trainNumber")));
                    JSONArray jsonArray = jsonObject1.getJSONArray("schedule");
                    for(int j=0; j<jsonArray.length(); j++){
                        JSONObject jsonObject2 = jsonArray.getJSONObject(j);
                        System.out.println(jsonObject2);
                        stationName.add(jsonObject2.getString("stationName"));
                        stationCode.add(jsonObject2.getString("stationCode"));
                        arrivalTime.add(jsonObject2.getString("arrival"));
                        departureTime.add(jsonObject2.getString("departure"));
                    }
                }
                String convertedServerResponse = serverResponseArray.toString();
                System.out.println("Converted: " + convertedServerResponse);
                /*Schedule schedule = new Schedule(sourceStationName, sourceStationCode,
                                                destinationStationName, destinationStationCode, serverResponseArray);*/
                try{
                    Intent intent = new Intent(getApplicationContext(), DisplayTrainList.class);
                    /*intent.putExtra("details", schedule);*/
                    intent.putExtra("sourceStationName", sourceStationName);
                    intent.putExtra("destinationStationName", destinationStationName);
                    intent.putExtra("sourceStationCode", sourceStationCode);
                    intent.putExtra("destinationStationCode", destinationStationCode);
                    intent.putExtra("server_response", convertedServerResponse);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                } catch (Exception exc){
                    System.out.println("In MainActivity at intent startActivity part: " + exc.getMessage());
                    finish();
                }
            } catch (JSONException jExc){

                System.out.println("In MainActivity at JSONException: " + jExc.getMessage());
            }

        }
    }

}
