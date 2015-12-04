package com.technics.trnqlchallenge;

import com.parse.ParseUser;
import com.trnql.smart.base.SmartCompatActivity;
import com.trnql.smart.location.LocationEntry;
import com.trnql.smart.people.PersonEntry;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class MainActivity extends SmartCompatActivity {
    private ParseUser user = ParseUser.getCurrentUser();
    private Double latitude = 37.441883;
    private Double longitude = -122.143019;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //If we've got an ID from Parse, use it as an access token
        if (user.getObjectId() != null) {
            getPeopleManager().setUserToken(user.getObjectId());
        }
    }

    @Override
    public void smartLatLngChange(LocationEntry locationEntry) {
        latitude = locationEntry.getLatitude();
        longitude = locationEntry.getLongitude();
    }

    @Override
    protected void smartPeopleChange(List<PersonEntry> people) {
        PersonEntry person = people.get(0);
        Double personLatitude = person.getLatitude();
        Double personLongitude = person.getLongitude();
        Integer personDistanceFromUser = person.getDistanceFromUser();
    }

    public void showSitters(View View) {
        Intent intent = new Intent(MainActivity.this,MapsActivity.class);
        intent.putExtra("com.technics.trnqlchallenge.LAT", latitude);
        intent.putExtra("com.technics.trnqlchallenge.LONG", longitude);
        startActivity(intent);
    }
}
