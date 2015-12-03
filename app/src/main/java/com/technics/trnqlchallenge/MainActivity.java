package com.technics.trnqlchallenge;

import com.trnql.smart.base.SmartCompatActivity;
import com.trnql.smart.location.LocationEntry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends SmartCompatActivity {
    private Double latitude = 37.441883;
    private Double longitude = -122.143019;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void smartLatLngChange(LocationEntry locationEntry) {
        latitude = locationEntry.getLatitude();
        longitude = locationEntry.getLongitude();
    }

    public void showSitters(View View) {
        Intent intent = new Intent(MainActivity.this,MapsActivity.class);
        intent.putExtra("com.technics.trnqlchallenge.LAT", latitude);
        intent.putExtra("com.technics.trnqlchallenge.LONG", longitude);
        startActivity(intent);
    }
}
