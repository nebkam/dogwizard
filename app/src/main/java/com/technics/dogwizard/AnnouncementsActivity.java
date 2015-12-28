package com.technics.dogwizard;

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
    private AnnouncementAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements_nearby);

        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("com.technics.dogwizard.LAT",0);
        longitude = intent.getDoubleExtra("com.technics.dogwizard.LONG",0);

        RecyclerView view = (RecyclerView)findViewById(R.id.announcement_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        view.setLayoutManager(layoutManager);

        adapter = new AnnouncementAdapter(null);
        view.setAdapter(adapter);
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
                    adapter.refresh(decorate(announcements));
                    adapter.notifyDataSetChanged();
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
            announcement.creatorPhoto = createdBy.getParseFile("photoFile");
            announcement.creatorToken = createdBy.getObjectId();

            decorated.add(announcement);
        }
        return  decorated;
    }
}
