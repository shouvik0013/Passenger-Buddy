package com.example.passengerbuddy;

import org.json.JSONArray;

import java.io.Serializable;

public class Schedule {
    public String sourceStationName;
    public String sourceStationCode;
    public String destinationStationCode;
    public String destinationStationName;
    public JSONArray allTrainInfo;

    public Schedule(String sourceStationName, String sourceStationCode,
                    String destinationStationName, String destinationStationCode, JSONArray allTrainInfo){

        this.sourceStationName = sourceStationName;
        this.sourceStationCode = sourceStationCode;
        this.destinationStationName = destinationStationName;
        this.destinationStationCode = destinationStationCode;
        this.allTrainInfo = allTrainInfo;
    }

    public String getSourceStationName() {
        return sourceStationName;
    }

    public void setSourceStationName(String sourceStationName) {
        this.sourceStationName = sourceStationName;
    }

    public String getSourceStationCode() {
        return sourceStationCode;
    }

    public void setSourceStationCode(String sourceStationCode) {
        this.sourceStationCode = sourceStationCode;
    }

    public String getDestinationStationCode() {
        return destinationStationCode;
    }

    public void setDestinationStationCode(String destinationStationCode) {
        this.destinationStationCode = destinationStationCode;
    }

    public String getDestinationStationName() {
        return destinationStationName;
    }

    public void setDestinationStationName(String destinationStationName) {
        this.destinationStationName = destinationStationName;
    }

    public JSONArray getAllTrainInfo() {
        return allTrainInfo;
    }

    public void setAllTrainInfo(JSONArray allTrainInfo) {
        this.allTrainInfo = allTrainInfo;
    }
}
