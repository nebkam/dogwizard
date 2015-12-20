package com.technics.trnqlchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.HashMap;
import java.util.List;

public class DogFriendlyPlacesActivity extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap mMap;
    private HashMap<String,DogFriendlyPlace> placesFound = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_friendly_places);

        setTitle(R.string.dog_friendly_places);

        Intent intent = getIntent();
        final Double latitude = intent.getDoubleExtra("com.technics.trnqlchallenge.LAT", 0);
        final Double longitude = intent.getDoubleExtra("com.technics.trnqlchallenge.LONG", 0);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        final LatLngBounds.Builder boundsBuilder = LatLngBounds.builder();
        boundsBuilder.include(new LatLng(latitude,longitude));

        ParseGeoPoint point = new ParseGeoPoint(latitude,longitude);

        ParseQuery<ParseObject> query = new ParseQuery<>("FriendlyPlace");
        query.whereWithinKilometers("location", point, 8.00).orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> places, ParseException e) {
                if (e == null) {
                    if (!places.isEmpty()){
                        for (int i=0; i<places.size(); i++) {
                            ParseObject placeFound = places.get(i);
                            if (placeFound.getString("name") != null) {
                                //Must be present
                                String name = placeFound.getString("name");
                                ParseGeoPoint location = placeFound.getParseGeoPoint("location");
                                //May be present
                                String address = "";
                                String phone = "";
                                String website = "";

                                if (placeFound.getString("address") != null) {
                                    address = placeFound.getString("address");
                                }
                                if (placeFound.getString("phone") != null) {
                                    phone = placeFound.getString("phone");
                                }
                                if (placeFound.getString("website") != null) {
                                    website = placeFound.getString("website");
                                }

                                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));
                                boundsBuilder.include(latLng);

                                DogFriendlyPlace dogFriendlyPlace = new DogFriendlyPlace(name,address,phone,website);
                                placesFound.put(marker.getId(), dogFriendlyPlace);
                            }
                        }
                        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 30));
                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                DogFriendlyPlace markedPlace = placesFound.get(marker.getId());
                                Intent intent = new Intent(DogFriendlyPlacesActivity.this, DogFriendlyPlaceDetailsActivity.class);
                                intent.putExtra("name", markedPlace.name);
                                intent.putExtra("address", markedPlace.address);
                                intent.putExtra("phone", markedPlace.phone);
                                intent.putExtra("website", markedPlace.website);
                                startActivity(intent);
                                return true;
                            }
                        });
                    }
                    else {
                        Toast.makeText(getApplicationContext(), R.string.no_friendly_places_nearby, Toast.LENGTH_LONG).show();
                    }
                } else {
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Add My location pin and control
        mMap.setMyLocationEnabled(true);
    }
}
