package com.technics.trnqlchallenge;

import com.parse.ParseUser;
import com.trnql.smart.base.SmartCompatActivity;
import com.trnql.smart.location.LocationEntry;
import com.trnql.smart.places.PlaceEntry;
import com.trnql.smart.places.PlaceType;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends SmartCompatActivity {
    private Double latitude = 37.441883;
    private Double longitude = -122.143019;
    private CardView placeCard;
    private ImageView placePhoto;
    private TextView placeName;
    private TextView placeDistance;
    private List<PlaceEntry> placesFound = new ArrayList<>();
    private PlaceEntry currentPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isFirstRun()) {
            showSplash();
        }
        placeCard = (CardView)findViewById(R.id.placeCard);
        placePhoto = (ImageView)findViewById(R.id.placePhoto);
        placeName = (TextView)findViewById(R.id.placeName);
        placeDistance = (TextView)findViewById(R.id.placeDistance);
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
        getPlacesManager().setIncludeImages(true);
    }

    @Override
    public void smartLatLngChange(LocationEntry locationEntry) {
        latitude = locationEntry.getLatitude();
        longitude = locationEntry.getLongitude();
    }

    @Override
    public void smartPlacesChange(List<PlaceEntry> places) {
        Collections.sort(places, new Comparator<PlaceEntry>() {
            @Override
            public int compare(PlaceEntry left, PlaceEntry right) {
                int leftDistance = left.getDistanceFromUser();
                int rightDistance = right.getDistanceFromUser();
                if (leftDistance == rightDistance) {
                    return 0;
                } else {
                    if (leftDistance > rightDistance) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
        });
        placesFound = places;
//pick closest
renderCard(placesFound.get(0));
currentPlace = placesFound.get(0);
    }

    private void renderCard(PlaceEntry place){
        if (place.getImages().size() > 0) {
            placePhoto.setImageBitmap(place.getImages().get(0));
        }
        placeName.setText(place.getName());

        int distance = place.getDistanceFromUser();
        if (distance > 0) {
            placeDistance.setText(String.valueOf(distance)+"m");
            placeDistance.setVisibility(View.VISIBLE);
        } else {
            placeDistance.setVisibility(View.GONE);
        }
    }

    public void skip(View view) {
        ParseUser user = ParseUser.getCurrentUser();
        user.addUnique("skipped",currentPlace.getPlaceId());
        user.saveInBackground();
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
