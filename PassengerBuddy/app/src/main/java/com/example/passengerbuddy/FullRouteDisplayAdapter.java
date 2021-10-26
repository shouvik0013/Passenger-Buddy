package com.example.passengerbuddy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FullRouteDisplayAdapter extends ArrayAdapter<StationDetails> {
    Context context;
    String trainName;
    String trainNumber;
    ArrayList<StationDetails> routeDetails;
    public FullRouteDisplayAdapter(Context context, String trainName, String trainNumber, ArrayList<StationDetails> routeDetails){
        super(context, 0, routeDetails);
        this.context = context;
        this.trainName = trainName;
        this.trainNumber = trainNumber;
        this.routeDetails = routeDetails;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        StationDetails stationDetails = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.full_route_info, parent, false);
        }

        TextView stationNameTextView = (TextView) convertView.findViewById(R.id.stationNameFullRout);
        TextView arrivalTimeTextView = (TextView) convertView.findViewById(R.id.arrivalTimeFullRoute);
        TextView departureTimeTextView = (TextView) convertView.findViewById(R.id.departureFullRoute);

        stationNameTextView.setText(stationDetails.getStationName());
        arrivalTimeTextView.setText(stationDetails.getArrival());
        departureTimeTextView.setText(stationDetails.getDeparture());

        return convertView;
    }
}