package com.technics.trnqlchallenge;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseImageView;

public class AnnouncementHolder extends RecyclerView.ViewHolder {
    protected TextView body;
    protected TextView creatorName;
    protected ParseImageView creatorPhoto;
    protected String creatorToken;

    public AnnouncementHolder(final View view){
        super(view);
        body = (TextView) view.findViewById(R.id.announcementBody);
        creatorName = (TextView) view.findViewById(R.id.announcementCreatorName);
        creatorPhoto = (ParseImageView) view.findViewById(R.id.announcementCreatorPhoto);

        Button showProfile = (Button) view.findViewById(R.id.btn_owner_details);
        showProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),OwnerDetailsActivity.class);
                intent.putExtra("userToken",creatorToken);
                v.getContext().startActivity(intent);
            }
        });
        Button sendMessage = (Button) view.findViewById(R.id.btn_send_msg);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),MessageActivity.class);
                intent.putExtra("to",creatorToken);
                v.getContext().startActivity(intent);
            }
        });
    }
}
