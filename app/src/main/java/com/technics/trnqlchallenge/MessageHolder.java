package com.technics.trnqlchallenge;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MessageHolder extends RecyclerView.ViewHolder{
    protected TextView body;
    protected TextView from;

    public MessageHolder(View view){
        super(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ViewMessageActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        body = (TextView) view.findViewById(R.id.messageBody);
        from = (TextView) view.findViewById(R.id.messageFromName);
    }
}
