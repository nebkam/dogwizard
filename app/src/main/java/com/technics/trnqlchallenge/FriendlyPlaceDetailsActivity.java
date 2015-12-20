package com.technics.trnqlchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FriendlyPlaceDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendly_place_details);

        Intent intent   = getIntent();
        String name     = intent.getStringExtra("name");
        String address  = intent.getStringExtra("address");
        String website  = intent.getStringExtra("website");
        String phone    = intent.getStringExtra("phone");

        if (!name.equals("")) {
            TextView nameView = (TextView) findViewById(R.id.friendlyPlaceName);
            nameView.setText(name);
        }
        if (!address.equals("")) {
            TextView addressView = (TextView) findViewById(R.id.friendlyPlaceAddress);
            addressView.setText(address);
            addressView.setVisibility(View.VISIBLE);
            TextView addressViewLabel = (TextView) findViewById(R.id.friendlyPlaceAddressLabel);
            addressViewLabel.setVisibility(View.VISIBLE);
        }
        if (!phone.equals("")) {
            TextView phoneView = (TextView) findViewById(R.id.friendlyPlacePhone);
            phoneView.setText(phone);
            phoneView.setVisibility(View.VISIBLE);
            TextView phoneViewLabel = (TextView) findViewById(R.id.friendlyPlacePhoneLabel);
            phoneViewLabel.setVisibility(View.VISIBLE);
        }
        if (!website.equals("")) {
            TextView websiteView = (TextView) findViewById(R.id.friendlyPlaceWebsite);
            websiteView.setText(website);
            websiteView.setVisibility(View.VISIBLE);
            TextView websiteViewLabel = (TextView) findViewById(R.id.friendlyPlaceWebsiteLabel);
            websiteViewLabel.setVisibility(View.VISIBLE);
        }
    }
}
