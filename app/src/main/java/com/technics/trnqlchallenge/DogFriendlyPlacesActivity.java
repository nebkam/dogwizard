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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

public class DogFriendlyPlacesActivity extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_friendly_places);

        setTitle(R.string.dog_friendly_places);

        Intent intent = getIntent();
        final Double latitude = intent.getDoubleExtra("com.technics.trnqlchallenge.LAT",0);
        final Double longitude = intent.getDoubleExtra("com.technics.trnqlchallenge.LONG", 0);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        final LatLngBounds.Builder boundsBuilder = LatLngBounds.builder();

        ParseGeoPoint point = new ParseGeoPoint(latitude,longitude);
        System.out.println(point);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("FriendlyPlace");
        query.whereWithinKilometers("location",point,8.00).orderByDescending("createdAt");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Toast.makeText(getApplicationContext(), R.string.no_friendly_places_nearby, Toast.LENGTH_LONG).show();
                } else {
//                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 30));
                    System.out.println(object);
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
