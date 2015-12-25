package com.technics.trnqlchallenge;

import android.content.ClipData;
import android.content.Intent;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MessageHolder extends RecyclerView.ViewHolder{
    protected TextView body;
    protected TextView from;
    protected Message item;

    public MessageHolder(View view){
        super(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("foo" + item.fromUserId);
                Intent intent = new Intent(view.getContext(),ViewMessageActivity.class);
                intent.putExtra("body", item.body);
                intent.putExtra("from", item.from);
                intent.putExtra("fromUserId", item.fromUserId);
                view.getContext().startActivity(intent);
            }
        });
        body = (TextView) view.findViewById(R.id.messageBody);
        from = (TextView) view.findViewById(R.id.messageFromName);
    }
}
