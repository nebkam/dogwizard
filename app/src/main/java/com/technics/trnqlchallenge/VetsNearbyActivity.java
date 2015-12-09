package com.technics.trnqlchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class VetsNearbyActivity extends AppCompatActivity  implements OnMapReadyCallback{
    private Double latitude;
    private Double longitude;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owners_nearby);
        setTitle(R.string.vets_title);

        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("com.technics.trnqlchallenge.LAT",0);
        longitude = intent.getDoubleExtra("com.technics.trnqlchallenge.LONG",0);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        final LatLngBounds.Builder boundsBuilder = LatLngBounds.builder();

        String url = "https://api.vetfinder.mobi/json/nearest_vets/?lat="+String.valueOf(latitude)+"&long="+String.valueOf(longitude)+"&k=45661619478f11be9a8f8d227e4f3";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    try {
                        JSONObject data = new JSONObject(response);
                        JSONArray resultsArray = data.getJSONArray("results");

                        for(int i=0; i<resultsArray.length(); i++) {
                            JSONObject obj = resultsArray.getJSONObject(i);
                            Double markerLatitude = obj.getDouble("latitude");
                            Double markerLongitude  = obj.getDouble("longitude");
                            String company  = obj.getString("company");

                            LatLng latLng = new LatLng(markerLatitude, markerLongitude);
                            mMap.addMarker(new MarkerOptions().position(latLng).title(company));
                            boundsBuilder.include(latLng);
                        }
                        if (resultsArray.length() > 0) {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(),30));
                        }
                    }
                    catch (JSONException ex) {
                        ex.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Error handling
                System.out.println("Something went wrong!");
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        LatLng myLatLong = new LatLng(latitude, longitude);
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLong, 17));

        //Add My location pin and control
        mMap.setMyLocationEnabled(true);
    }
}
