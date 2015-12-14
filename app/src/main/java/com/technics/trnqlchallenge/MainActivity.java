package com.technics.trnqlchallenge;

import com.trnql.smart.base.SmartCompatActivity;
import com.trnql.smart.location.LocationEntry;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends SmartCompatActivity {
    private Double latitude = 37.441883;
    private Double longitude = -122.143019;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isFirstRun()) {
            showSplash();
        }
    }

    @Override
    public void smartLatLngChange(LocationEntry locationEntry) {
        latitude = locationEntry.getLatitude();
        longitude = locationEntry.getLongitude();
    }

    public void showOwnersNearby(View View) {
        Intent intent = new Intent(MainActivity.this,OwnersNearbyActivity.class);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        startActivity(intent);
    }

    private boolean isFirstRun() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        return preferences.getBoolean("firstRun",true);
    }

    private void showSplash(){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        preferences.edit().putBoolean("firstRun",false).apply();
        Intent intent = new Intent(MainActivity.this,DogDescriptionActivity.class);
        startActivity(intent);
    }

    public void showSettings(View View) {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    public void showVetsNearby(View View){
        Intent intent = new Intent(MainActivity.this, VetsNearbyActivity.class);
        intent.putExtra("com.technics.trnqlchallenge.LAT", latitude);
        intent.putExtra("com.technics.trnqlchallenge.LONG", longitude);
        startActivity(intent);
    }

    public void showMessages(View view) {
        Intent intent = new Intent(MainActivity.this,MessageInboxActivity.class);
        startActivity(intent);
    }
}
