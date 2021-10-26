package com.example.passengerbuddy;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class FullRouteDisplayActivity extends AppCompatActivity {

    ListView listView;
    Intent intent;
    String responseJsonArrayString;
    int position;
    JSONArray serverResponseJsonArray;
    String trainName;
    String trainNumber;
    ArrayList<StationDetails> arrayListStationDetails;
    FullRouteDisplayAdapter adapter;
    private TextView currentDate;
    Calendar calendar;

    String months[] = {
            "Jan", "Feb", "Mar", "Apr",
            "May", "June", "July", "August",
            "Sep", "Oct", "Nov", "Dec"
    };

    String week_days[] = {
            "Sun", "Mon", "Tue",
            "Wed", "Thu", "Fri", "Sat"
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_display_list_view);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E3EB9")));
        calendar = Calendar.getInstance();
        currentDate = (TextView) findViewById(R.id.dateRouteDisplay);
        currentDate.setText(months[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.DATE) + ", "
                + week_days[calendar.get(Calendar.DAY_OF_WEEK)-1]);
        listView = (ListView) findViewById(R.id.routeDisplayListView);
        intent = getIntent();
        responseJsonArrayString = intent.getStringExtra("server_response");
        position = intent.getIntExtra("position", 0);
        arrayListStationDetails = new ArrayList<StationDetails>();
        try {
            serverResponseJsonArray = new JSONArray(responseJsonArrayString);
            JSONObject jsonObject = serverResponseJsonArray.getJSONObject(position);
            trainName = jsonObject.getString("trainName");
            trainNumber = jsonObject.getString("trainNumber");
            JSONArray scheduleArray = jsonObject.getJSONArray("schedule");
            for (int i = 0; i < scheduleArray.length(); i++) {
                JSONObject jsonObject2 = scheduleArray.getJSONObject(i);
                String sName = jsonObject2.getString("stationName");
                String sCode = jsonObject2.getString("stationCode");
                String arrival = jsonObject2.getString("arrival");
                String departure = jsonObject2.getString("departure");
                StationDetails stationDetails = new StationDetails(sName, sCode, arrival, departure);
                arrayListStationDetails.add(stationDetails);
            }
            adapter = new FullRouteDisplayAdapter(getApplicationContext(), trainName, trainNumber, arrayListStationDetails);
            listView.setAdapter(adapter);
        } catch (JSONException jExc) {
            System.out.println("In FullRouteDisplayActivity at JsonException: " + jExc.getMessage());
        }
    }
}
