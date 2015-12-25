package com.technics.trnqlchallenge;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MessageHolder extends RecyclerView.ViewHolder{
    protected TextView body;
    protected TextView from;

    public MessageHolder(View view){
        super(view);
        body = (TextView) view.findViewById(R.id.messageBody);
        from = (TextView) view.findViewById(R.id.messageFromName);
    }
}
