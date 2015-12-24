package com.technics.trnqlchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;
import java.util.List;

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

        RecyclerView announcementList = (RecyclerView)findViewById(R.id.announcement_list);
        announcementList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        announcementList.setLayoutManager(layoutManager);

        AnnouncementAdapter adapter = new AnnouncementAdapter(createList());
        announcementList.setAdapter(adapter);
    }

    private List<Announcement> createList() {
        List<Announcement> list = new ArrayList<>();
        Announcement ann1 = new Announcement();
        ann1.body = "Hello World";
        list.add(ann1);
        return list;
    }
}
