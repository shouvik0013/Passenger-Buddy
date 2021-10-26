package com.example.passengerbuddy;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TrainInfoDisplayAdapter extends BaseAdapter {

    public Context context;
    public Schedule schedule;

    public TrainInfoDisplayAdapter(Context context, Schedule schedule){
        System.out.println("In TrainInfoDisplayAdapter.");
        this.context = context;
        this.schedule = schedule;
    }

    @Override
    public int getCount() {
        return schedule.getAllTrainInfo().length();
    }

    @Override
    public Object getItem(int position) {
        try{
            return schedule.allTrainInfo.getJSONObject(position);
        } catch (JSONException jexc){

        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(this.context).inflate(R.layout.train_info, parent, false);
        }

        TextView trainNumberTextView = (TextView) convertView.findViewById(R.id.trainNumberTextView);
        TextView trainArrivalTimeTextView = (TextView) convertView.findViewById(R.id.arrivalTimeTextView);
        TextView trainDestinationTimeTextView = (TextView) convertView.findViewById(R.id.destinationTimeTextView);
        TextView trainNameTextView = (TextView) convertView.findViewById(R.id.trainNameTextView);
        ImageView arrowImageView = (ImageView) convertView.findViewById(R.id.smallArrowImageView);

        try{
            JSONObject jsonObject = schedule.getAllTrainInfo().getJSONObject(position);
            trainNameTextView.setText(jsonObject.getString("trainName"));
            trainNumberTextView.setText(jsonObject.getString("trainNumber"));
            JSONArray scheduleJsonArray = jsonObject.getJSONArray("schedule");
            for(int i=0; i<scheduleJsonArray.length(); i++){
                JSONObject jsonObject1 = scheduleJsonArray.getJSONObject(i);
                String stationName = jsonObject1.getString("stationName");
                if(stationName.equals(schedule.getSourceStationName())){
                    trainArrivalTimeTextView.setText(jsonObject1.getString("arrival"));
                }
                if(stationName.equals(schedule.getDestinationStationName())){
                    trainDestinationTimeTextView.setText(jsonObject1.getString("departure"));
                }

            }
        } catch (JSONException jexc){
            System.out.println("In TrainInfoDisplayAdapter at getView " + jexc.getMessage());
        }
        arrowImageView.setImageResource(R.drawable.right_arrow);
        return convertView;
    }
}
