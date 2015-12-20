package com.technics.trnqlchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FriendlyPlaceDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendly_place_details);
        setTitle(R.string.friendly_place_details);

        String name = getIntent().getStringExtra("name");
        String address = getIntent().getStringExtra("address");
        String website = getIntent().getStringExtra("website");
        String phone = getIntent().getStringExtra("phone");

        TextView nameView = (TextView) findViewById(R.id.friendlyPlaceName);
        nameView.setText(name);

        TextView addressView = (TextView) findViewById(R.id.friendlyPlaceAddress);
        addressView.setText(address);

        TextView phoneView = (TextView) findViewById(R.id.friendlyPlacePhone);
        phoneView.setText(phone);

        if (!website.isEmpty()){
            TextView websiteLink = (TextView) findViewById(R.id.friendlyPlaceWebsite);
            websiteLink.setText(website);
        }
    }
}
