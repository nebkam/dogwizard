package com.technics.trnqlchallenge;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.trnql.smart.base.SmartCompatActivity;
import com.trnql.smart.people.PersonEntry;

import java.util.List;

public class OwnersNearbyActivity extends SmartCompatActivity implements OnMapReadyCallback {
    private Double latitude;
    private Double longitude;
    private GoogleMap mMap;
    private Boolean isMapReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owners_nearby);

        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("com.technics.trnqlchallenge.LAT",0);
        longitude = intent.getDoubleExtra("com.technics.trnqlchallenge.LONG",0);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        isMapReady = true;

        LatLng myLatLong = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLong,17));

        //Add My location pin and control
        mMap.setMyLocationEnabled(true);
    }

    @Override
    protected void smartPeopleChange(List<PersonEntry> people) {
        if (isMapReady) {
            for (int i = 0; i < people.size(); i++) {
                PersonEntry personEntry = people.get(1);
                LatLng latLng = new LatLng(personEntry.getLatitude(),personEntry.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng).title(personEntry.getUserToken()));
            }
        }
    }
}
