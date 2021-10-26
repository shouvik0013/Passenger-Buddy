package com.example.passengerbuddy;

public class MessageDetails {
    String messageHead;
    String messageDescription;
    String userName;
    String dateTime;

    public MessageDetails(String messageHead, String messageDescription,
                          String userName, String dateTime){
        this.messageHead = messageHead;
        this.messageDescription = messageDescription;
        this.userName = userName;
        this.dateTime = dateTime;
    }

    public String getMessageHead() {
        return messageHead;
    }

    public String getMessageDescription() {
        return messageDescription;
    }

    public String getUserName() {
        return userName;
    }

    public String getDateTime() {
        return dateTime;
    }
}
