package com.technics.trnqlchallenge;

import com.trnql.smart.base.SmartCompatActivity;
import com.trnql.smart.location.LocationEntry;
import com.trnql.smart.places.PlaceEntry;
import com.trnql.smart.places.PlaceType;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends SmartCompatActivity {
    private Double latitude = 37.441883;
    private Double longitude = -122.143019;
    private ArrayList<PlaceEntry> placesFound = new ArrayList<>();
    private ArrayList<String> placesRated = new ArrayList<>();
    private String placeShownGoogleMapUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isFirstRun()) {
            showSplash();
        }
        getPlacesManager().setTypeFilters(
                PlaceType.AIRPORT,
                PlaceType.AMUSEMENT_PARK,
                PlaceType.AQUARIUM,
                PlaceType.ART_GALLERY,
                PlaceType.BAKERY,
                PlaceType.BANK,
                PlaceType.BAR,
                PlaceType.BEAUTY_SALON,
                PlaceType.BOOK_STORE,
                PlaceType.BOWLING_ALLEY,
                PlaceType.BUS_STATION,
                PlaceType.CAFE,
                PlaceType.CASINO,
                PlaceType.CLOTHING_STORE,
                PlaceType.CONVENIENCE_STORE,
                PlaceType.DEPARTMENT_STORE,
                PlaceType.ELECTRONICS_STORE,
                PlaceType.FLORIST,
                PlaceType.FOOD,
                PlaceType.FURNITURE_STORE,
                PlaceType.GAS_STATION,
                PlaceType.GROCERY_OR_SUPERMARKET,
                PlaceType.GYM,
                PlaceType.HAIR_CARE,
                PlaceType.HARDWARE_STORE,
                PlaceType.HOME_GOODS_STORE,
                PlaceType.JEWELRY_STORE,
                PlaceType.LAUNDRY,
                PlaceType.LIBRARY,
                PlaceType.LODGING,
                PlaceType.MOVIE_THEATER,
                PlaceType.MUSEUM,
                PlaceType.NIGHT_CLUB,
                PlaceType.PARK,
                PlaceType.POST_OFFICE,
                PlaceType.RESTAURANT,
                PlaceType.RV_PARK,
                PlaceType.SCHOOL,
                PlaceType.SHOE_STORE,
                PlaceType.SHOPPING_MALL,
                PlaceType.SPA,
                PlaceType.STADIUM,
                PlaceType.STORE,
                PlaceType.SUBWAY_STATION,
                PlaceType.TRAIN_STATION,
                PlaceType.UNIVERSITY,
                PlaceType.ZOO
        );
    }

    @Override
    public void smartLatLngChange(LocationEntry locationEntry) {
        latitude = locationEntry.getLatitude();
        longitude = locationEntry.getLongitude();
    }

    @Override
    public void smartPlacesChange(List<PlaceEntry> places) {
        for (PlaceEntry place : places){
            //because of the `cid` param, use Google Maps URL as a unique identifier
            if (place.getGoogleMapsUrl() != null
                    && !placesRated.contains(place.getGoogleMapsUrl())) {
                placesFound.add(place);
            }
        }
        nextPlace();
    }

    public void nextPlace() {
        CardView placeCard = (CardView)findViewById(R.id.placeCard);
        if (placesFound.size() > 0) {
            placeCard.setVisibility(View.VISIBLE);
            PlaceEntry randomPlace = placesFound.get(new Random().nextInt(placesFound.size()));

            TextView placeName = (TextView)findViewById(R.id.placeName);
            placeName.setText(randomPlace.getName());

            TextView placeAddress = (TextView)findViewById(R.id.placeAddress);
            placeAddress.setText(randomPlace.getAddress());
        } else {
            placeCard.setVisibility(View.GONE);
        }
    }

    public void skip(View view) {
        nextPlace();
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

    public void showAnnouncements(View view) {
        Intent intent = new Intent(MainActivity.this,AnnouncementsNearbyActivity.class);
        intent.putExtra("com.technics.trnqlchallenge.LAT", latitude);
        intent.putExtra("com.technics.trnqlchallenge.LONG", longitude);
        startActivity(intent);
    }
}
