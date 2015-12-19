package com.technics.trnqlchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
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
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DogFriendlyPlacesActivity extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap mMap;
    private HashMap<String,DogFriendlyPlace> dogFriendlyPlaceHashMap = new HashMap<>();
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

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("FriendlyPlace");
        query.whereWithinKilometers("location", point, 8.00).orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (!objects.isEmpty()){
                        for (int i=0; i<objects.size(); i++) {
//                            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 30));
                            ParseObject object = objects.get(i);

                            String name = object.getString("name");
                            String address = object.getString("address");
                            String phone = object.getString("phone");
                            String website = object.getString("website");

                            ParseGeoPoint location = object.getParseGeoPoint("location");

                            Double markerLatitude = location.getLatitude();
                            Double markerLongitude = location.getLongitude();

                            LatLng latLng = new LatLng(markerLatitude, markerLongitude);
                            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(object.getString("name")));

                            DogFriendlyPlace dogFriendlyPlace = new DogFriendlyPlace(name,address,phone,website);
                            dogFriendlyPlaceHashMap.put(marker.getId(), dogFriendlyPlace);

                            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(Marker marker) {
                                    DogFriendlyPlace markedPlace = dogFriendlyPlaceHashMap.get(marker.getId());
                                    Intent intent = new Intent(DogFriendlyPlacesActivity.this, DogFriendlyPlaceDetailsActivity.class);
                                    intent.putExtra("name", markedPlace.name);
                                    intent.putExtra("city", markedPlace.address);
                                    intent.putExtra("phone", markedPlace.phone);
                                    intent.putExtra("website", markedPlace.website);
                                    startActivity(intent);
                                    return true;
                                }
                            });
                        }
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
