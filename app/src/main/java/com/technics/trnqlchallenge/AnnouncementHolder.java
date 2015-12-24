package com.technics.trnqlchallenge;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class AnnouncementHolder extends RecyclerView.ViewHolder {
    protected TextView body;
    protected TextView creatorName;

    public AnnouncementHolder(View view){
        super(view);
        body = (TextView) view.findViewById(R.id.announcementBody);
        creatorName = (TextView) view.findViewById(R.id.announcementCreatorName);
    }
}
