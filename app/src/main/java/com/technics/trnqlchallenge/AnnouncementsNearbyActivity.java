package com.technics.trnqlchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

public class AnnouncementsNearbyActivity extends AppCompatActivity {
    private Double latitude;
    private Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements_nearby);

        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("com.technics.trnqlchallenge.LAT",0);
        longitude = intent.getDoubleExtra("com.technics.trnqlchallenge.LONG",0);

        ParseQueryAdapter<ParseObject> adapter = new ParseQueryAdapter<ParseObject>(this, new ParseQueryAdapter.QueryFactory() {
            @Override
            public ParseQuery<ParseObject> create() {
                ParseQuery query = new ParseQuery("Announcement");
                ParseGeoPoint point = new ParseGeoPoint(latitude,longitude);
                query.whereWithinKilometers("location",point,8.00).orderByDescending("createdAt");
                return query;
            }
        });
        adapter.setTextKey("body");
        ListView announcementList = (ListView)findViewById(R.id.announcementList);
        announcementList.setAdapter(adapter);
    }
}
