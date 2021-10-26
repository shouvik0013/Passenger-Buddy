package com.example.passengerbuddy;

public class StationDetails {

    String stationName;
    String stationCode;
    String arrival;
    String departure;


    public StationDetails(String stationName, String stationCode,
                          String arrival, String departure){
        this.stationName = stationName;
        this.stationCode = stationCode;
        this.arrival = arrival;
        this.departure = departure;
    }

    public String getStationName() {
        return stationName;
    }

    public String getStationCode() {
        return stationCode;
    }

    public String getArrival() {
        return arrival;
    }

    public String getDeparture() {
        return departure;
    }
}
