package com.technics.trnqlchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementsActivity extends AppCompatActivity {
    private Double latitude;
    private Double longitude;
    private RecyclerView announcementsView;
    private AnnouncementAdapter announcementAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements_nearby);

        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("com.technics.trnqlchallenge.LAT",0);
        longitude = intent.getDoubleExtra("com.technics.trnqlchallenge.LONG",0);

        announcementsView = (RecyclerView)findViewById(R.id.announcement_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        announcementsView.setLayoutManager(layoutManager);

        announcementAdapter = new AnnouncementAdapter(null);
        announcementsView.setAdapter(announcementAdapter);
        fetch();
    }

    private void fetch() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Announcement");
        ParseGeoPoint point = new ParseGeoPoint(latitude,longitude);
        query.whereWithinKilometers("location",point,8.00).orderByDescending("createdAt");
        query.include("createdBy");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> announcements, ParseException e) {
                if (e == null) {
                    announcementAdapter.refresh(decorate(announcements));
                    announcementAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.err_announcements, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private List<Announcement> decorate(List<ParseObject> objects) {
        List<Announcement> decorated = new ArrayList<>();
        for (ParseObject object:objects) {
            Announcement announcement = new Announcement();

            announcement.body = object.getString("body");
            ParseObject createdBy = object.getParseObject("createdBy");
            announcement.creatorName = createdBy.getString("dogName");

            decorated.add(announcement);
        }
        return  decorated;
    }
}