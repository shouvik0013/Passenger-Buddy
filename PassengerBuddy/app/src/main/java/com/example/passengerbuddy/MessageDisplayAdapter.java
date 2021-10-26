package com.example.passengerbuddy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MessageDisplayAdapter extends ArrayAdapter<MessageDetails> {

    Context context;
    ArrayList<MessageDetails> messageList;

    public MessageDisplayAdapter(Context context, ArrayList<MessageDetails> messageList){
        super(context,0,messageList);
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MessageDetails messageDetails = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.message_info_layout, parent, false);
        }

        TextView userName = (TextView) convertView.findViewById(R.id.text_message_name);
        TextView messageHead = (TextView) convertView.findViewById(R.id.textView2);
        TextView dateTime = (TextView) convertView.findViewById(R.id.text_message_time);
        TextView messageDescription = (TextView) convertView.findViewById(R.id.text_message_body);

        userName.setText(messageDetails.getUserName());
        messageHead.setText(messageDetails.getMessageHead());
        dateTime.setText(messageDetails.getDateTime());
        messageDescription.setText(messageDetails.getMessageDescription());

        /*userName.setTextColor(context.getResources().getColor(R.color.white));
        messageHead.setTextColor(context.getResources().getColor(R.color.white));
        dateTime.setTextColor(context.getResources().getColor(R.color.white));
        messageDescription.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));*/
        return convertView;
    }
}
