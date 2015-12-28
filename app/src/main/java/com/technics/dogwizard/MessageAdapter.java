package com.technics.dogwizard;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageHolder>{
    private List<Message> messageList;

    public MessageAdapter(List<Message> list) {
        this.messageList = list;
    }

    protected void refresh(List<Message> list){
        this.messageList = list;
    }

    @Override
    public int getItemCount(){
        if (messageList == null) {
            return 0;
        } else {
            return messageList.size();
        }
    }

    @Override
    public void onBindViewHolder(MessageHolder messageHolder, int position) {
        if (messageList != null) {
            Message message = messageList.get(position);
            messageHolder.body.setText(message.body);
            messageHolder.from.setText(message.from);
            messageHolder.item = message;
            if (message.fromPhoto != null) {
                messageHolder.fromPhoto.setParseFile(message.fromPhoto);
                messageHolder.fromPhoto.loadInBackground();
            }
        }
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (messageList == null) {
            return null;
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_message,parent,false);
            return new MessageHolder(itemView);
        }
    }
}
