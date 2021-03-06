package com.technics.dogwizard;

import com.parse.CountCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.trnql.smart.base.SmartCompatActivity;
import com.trnql.smart.location.LocationEntry;
import com.trnql.smart.places.PlaceEntry;
import com.trnql.smart.places.PlaceType;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
    private CardView gpsCard;
    private Boolean gpsWarningIgnored = false;
    private ImageView placePhoto;
    private TextView placeName;
    private TextView placeDistance;
    private List<PlaceEntry> placesFound = new ArrayList<>();
    private PlaceEntry currentPlace;
    private TextView announcementCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isFirstRun()) {
            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
            preferences.edit().putBoolean("firstRun",false).apply();
            Intent intent = new Intent(MainActivity.this,SetupActivity.class);
            startActivity(intent);
            finish();
        }

        announcementCount = (TextView)findViewById(R.id.announcement_count);
        placeCard = (CardView)findViewById(R.id.placeCard);
        placePhoto = (ImageView)findViewById(R.id.placePhoto);
        placeName = (TextView)findViewById(R.id.placeName);
        placeDistance = (TextView)findViewById(R.id.placeDistance);
        gpsCard = (CardView)findViewById(R.id.gpsOffCard);

        initSmartPlaces();

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {}
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {
                gpsCard.setVisibility(View.GONE);
            }
            @Override
            public void onProviderDisabled(String provider) {
                if (!gpsWarningIgnored) {
                    gpsCard.setVisibility(View.VISIBLE);
                }
            }
        };
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } catch (SecurityException exc) {
            if (!gpsWarningIgnored) {
                gpsCard.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void smartLatLngChange(LocationEntry locationEntry) {
        latitude = locationEntry.getLatitude();
        longitude = locationEntry.getLongitude();
        countAnnouncements();
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
        nextCard();
    }

    public void nextCard(){
        ParseUser user = ParseUser.getCurrentUser();

        List skipped = user.getList("skipped");
        List markedFriendly = user.getList("markedFriendly");
        PlaceEntry placeFound = null;

        for (PlaceEntry place : placesFound) {
            if ((skipped == null || !skipped.contains(place.getPlaceId()))
                    && (markedFriendly == null || !markedFriendly.contains(place.getPlaceId()))) {
                placeFound = place;
                break;
            }
        }

        currentPlace = placeFound;
        renderCard();
    }

    private void renderCard(){
        if (currentPlace == null) {
            placeCard.setVisibility(View.GONE);
        } else {
            if (currentPlace.getImages().size() > 0) {
                placePhoto.setImageBitmap(currentPlace.getImages().get(0));
                placePhoto.setVisibility(View.VISIBLE);
            } else {
                placePhoto.setVisibility(View.GONE);
            }
            placeName.setText(currentPlace.getName());

            int distance = currentPlace.getDistanceFromUser();
            if (distance > 0) {
                placeDistance.setText(String.valueOf(distance)+"m");
                placeDistance.setVisibility(View.VISIBLE);
            } else {
                placeDistance.setVisibility(View.GONE);
            }
            placeCard.setVisibility(View.VISIBLE);
        }
    }

    public void skip(View view) {
        if (currentPlace != null) {
            ParseUser user = ParseUser.getCurrentUser();
            user.addUnique("skipped",currentPlace.getPlaceId());
            user.saveInBackground();

            nextCard();
        }
    }

    public void markAsFriendly(View view) {
        ParseUser user = ParseUser.getCurrentUser();
        user.addUnique("markedFriendly", currentPlace.getPlaceId());
        user.saveInBackground();

        ParseObject friendlyPlace = new ParseObject("FriendlyPlace");
        friendlyPlace.put("name",currentPlace.getName());
        if (currentPlace.getAddress() != null) {
            friendlyPlace.put("address",currentPlace.getAddress());
        }
        if (currentPlace.getIntlPhoneNumber() != null) {
            friendlyPlace.put("phone",currentPlace.getIntlPhoneNumber());
        }
        if (currentPlace.getWebsite() != null) {
            friendlyPlace.put("website",currentPlace.getWebsite());
        }
        ParseGeoPoint location = new ParseGeoPoint(currentPlace.getLatitude(),currentPlace.getLongitude());
        friendlyPlace.put("location", location);
        friendlyPlace.saveInBackground();

        nextCard();
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

    public void showSettings(View View) {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    public void showVetsNearby(View View){
        Intent intent = new Intent(MainActivity.this, VetsNearbyActivity.class);
        intent.putExtra("com.technics.dogwizard.LAT", latitude);
        intent.putExtra("com.technics.dogwizard.LONG", longitude);
        startActivity(intent);
    }

    public void showMessages(View view) {
        Intent intent = new Intent(MainActivity.this,MessageInboxActivity.class);
        startActivity(intent);
    }

    public void showAnnouncements(View view) {
        Intent intent = new Intent(MainActivity.this,AnnouncementsActivity.class);
        intent.putExtra("com.technics.dogwizard.LAT", latitude);
        intent.putExtra("com.technics.dogwizard.LONG", longitude);
        startActivity(intent);
    }

    public void showDogFriendlyPlaces (View view) {
        Intent intent = new Intent(MainActivity.this, FriendlyPlacesActivity.class);
        intent.putExtra("com.technics.dogwizard.LAT", latitude);
        intent.putExtra("com.technics.dogwizard.LONG", longitude);
        startActivity(intent);
    }

    public void ignoreGPSWarning(View view) {
        gpsCard.setVisibility(View.GONE);
        gpsWarningIgnored = true;
    }
    public void openGPSSettings(View view) {
        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }

    private void initSmartPlaces() {
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

    private void countAnnouncements() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Announcement");
        ParseGeoPoint point = new ParseGeoPoint(latitude,longitude);
        query.whereWithinKilometers("location",point,8.00).orderByDescending("createdAt");
        query.countInBackground(new CountCallback() {
            @Override
            public void done(int count, ParseException e) {
                if (e == null && count > 0) {
                    announcementCount.setText(String.valueOf(count));
                } else {
                    announcementCount.setText("");
                }
            }
        });
    }
}
