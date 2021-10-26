package com.example.passengerbuddy;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class DisplayTrainList extends AppCompatActivity {

    ListView listView;
    Schedule schedule;
    Intent intent;
    TrainInfoDisplayAdapter adapter;
    JSONArray newJsonArray;
    TextView topSourceStation;
    TextView topDestinationStation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_train_list);
        System.out.println("DisplayTrainList activity started.");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E3EB9")));
        topSourceStation = (TextView) findViewById(R.id.sourceStationDisplaytrain);
        topDestinationStation = (TextView) findViewById(R.id.destinationStationDisplaytrain);
        listView = (ListView) findViewById(R.id.displayTrainListListView);
        intent = getIntent();
        String sourceStationName = intent.getStringExtra("sourceStationName");
        String destinationStationName = intent.getStringExtra("destinationStationName");
        String sourceStationCode = intent.getStringExtra("sourceStationCode");
        String destinationStationCode = intent.getStringExtra("destinationStationCode");
        topSourceStation.setText(sourceStationName);
        topDestinationStation.setText(destinationStationName);
        final String responseJsonArray = intent.getStringExtra("server_response");
        try{
            newJsonArray = new JSONArray(responseJsonArray);

        } catch (JSONException jExc){
            System.out.println("In DisplayTrainList in Json Exception: " + jExc.getMessage());
        }
        schedule = new Schedule(sourceStationName, sourceStationCode, destinationStationName, destinationStationCode, newJsonArray);
        System.out.println("For debugging purpose: \n");
        System.out.println(schedule.getSourceStationName());
        System.out.println(schedule.getDestinationStationName());
        System.out.println(schedule.getSourceStationCode());
        System.out.println(schedule.getDestinationStationCode());
        System.out.println(schedule.getAllTrainInfo());

        adapter = new TrainInfoDisplayAdapter(this, schedule);
        if(adapter==null){
            System.out.println("\n\nAdapter null.");
        } else{
            System.out.println("In displayTrainList adapter not null");
        }
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent2 = new Intent(getApplicationContext(), FullRouteDisplayActivity.class);
                intent2.putExtra("server_response", responseJsonArray);
                intent2.putExtra("position", position);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
            }
        });
    }


}
