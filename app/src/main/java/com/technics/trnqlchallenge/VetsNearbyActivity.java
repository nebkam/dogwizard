package com.technics.trnqlchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class VetsNearbyActivity extends AppCompatActivity  implements OnMapReadyCallback{
    private Double latitude;
    private Double longitude;
    private GoogleMap mMap;
    private HashMap<String,Vet> vetsFound = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owners_nearby);
        setTitle(R.string.vets_title);

        Intent intent = getIntent();
//        latitude = intent.getDoubleExtra("com.technics.trnqlchallenge.LAT",0);
//        longitude = intent.getDoubleExtra("com.technics.trnqlchallenge.LONG",0);

        latitude = 46.10027780;
        longitude = 19.66555560;
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
                            final String markerTitle;
                            if (obj.getBoolean("hasCompany")) {
                                markerTitle = obj.getString("company");
                            } else {
                                markerTitle = obj.getString("title")+" "+obj.getString("firstName")+" "+obj.get("lastName");
                            }

                            final String city = obj.getString("city");
                            final String street = obj.getString("street");
                            final String streetNumber = obj.getString("streetNumber");
                            final String phone = obj.getString("phone");

                            LatLng latLng = new LatLng(markerLatitude, markerLongitude);
                            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(markerTitle));
                            boundsBuilder.include(latLng);

                            Vet vetFound = new Vet(markerTitle,city,street,streetNumber,phone);
                            vetsFound.put(marker.getId(), vetFound);
                        }
                        if (resultsArray.length() > 0) {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 30));
                            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(Marker marker) {
                                    Vet markedVet = vetsFound.get(marker.getId());
                                    Intent intent = new Intent(VetsNearbyActivity.this, VetDetailsActivity.class);
                                    intent.putExtra("title", markedVet.title);
                                    intent.putExtra("city", markedVet.city);
                                    intent.putExtra("street", markedVet.street);
                                    intent.putExtra("streetNumber", markedVet.streetNumber);
                                    intent.putExtra("phone", markedVet.phone);
                                    startActivity(intent);
                                    return true;
                                }
                            });
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
        //Add My location pin and control
        mMap.setMyLocationEnabled(true);
    }
}
