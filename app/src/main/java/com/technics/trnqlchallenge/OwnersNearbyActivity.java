package com.technics.trnqlchallenge;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.trnql.smart.base.SmartCompatActivity;
import com.trnql.smart.people.PersonEntry;

import java.util.HashMap;
import java.util.List;

public class OwnersNearbyActivity extends SmartCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Boolean isMapReady = false;
    private Boolean isMapSynced = false;
    private List<PersonEntry> ownersNearby;
    private HashMap<String,String> markers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owners_nearby);

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
        //Add My location pin and control
        mMap.setMyLocationEnabled(true);
        syncOwners();
    }

    @Override
    protected void smartPeopleChange(List<PersonEntry> people) {
        ownersNearby = people;
        syncOwners();
    }

    public void syncOwners() {
        if (isMapReady && !isMapSynced && ownersNearby.size() > 0) {
            final LatLngBounds.Builder boundsBuilder = LatLngBounds.builder();
            for (int i = 0; i < ownersNearby.size(); i++) {
                PersonEntry personEntry = ownersNearby.get(i);
                LatLng latLng = new LatLng(personEntry.getLatitude(),personEntry.getLongitude());
                Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));
                boundsBuilder.include(latLng);
                markers.put(marker.getId(),personEntry.getUserToken());
            }
            // Gets screen size
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(),width,height,30));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    String userToken = markers.get(marker.getId());
                    Intent intent = new Intent(OwnersNearbyActivity.this,OwnerDetailsActivity.class);
                    intent.putExtra("userToken",userToken);
                    startActivity(intent);
                    return true;
                }
            });
            isMapSynced = true;
        }
    }
}
