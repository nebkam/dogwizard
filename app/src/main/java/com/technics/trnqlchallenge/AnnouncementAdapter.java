package com.technics.trnqlchallenge;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementHolder> {
    private List<Announcement> announcementList;

    public AnnouncementAdapter(List<Announcement> list) {
        this.announcementList = list;
    }

    protected void refresh(List<Announcement> list){
        this.announcementList = list;
    }

    @Override
    public int getItemCount(){
        if (announcementList == null) {
            return 0;
        } else {
            return announcementList.size();
        }
    }

    @Override
    public void onBindViewHolder(AnnouncementHolder announcementHolder, int position) {
        if (announcementList != null) {
            Announcement announcement = announcementList.get(position);
            announcementHolder.body.setText( announcement.body );
            announcementHolder.creatorName.setText( announcement.creatorName );
            if (announcement.creatorPhoto != null) {
                announcementHolder.creatorPhoto.setParseFile(announcement.creatorPhoto);
                announcementHolder.creatorPhoto.loadInBackground();
            }
            announcementHolder.creatorToken = announcement.creatorToken;
        }
    }

    @Override
    public AnnouncementHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (announcementList == null) {
            return null;
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_announcement,parent,false);
            return new AnnouncementHolder(itemView);
        }
    }
}
