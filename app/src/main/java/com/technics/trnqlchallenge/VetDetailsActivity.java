package com.technics.trnqlchallenge;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;

import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class VetDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet_details);

        setTitle(R.string.vet_details_title);

        String title = getIntent().getStringExtra("title");
        String city = getIntent().getStringExtra("city");
        String street = getIntent().getStringExtra("street");
        String streetNumber = getIntent().getStringExtra("streetNumber");
        String website = getIntent().getStringExtra("website");
        final String phone = getIntent().getStringExtra("phone");

        TextView titleView = (TextView) findViewById(R.id.titleView);
        titleView.setText(title);

        TextView cityView = (TextView) findViewById(R.id.cityView);
        cityView.setText(city);

        TextView phoneView = (TextView) findViewById(R.id.phoneView);
        phoneView.setText(phone);

        Resources res = getResources();
        String address = String.format(res.getString(R.string.vet_address), street, streetNumber);

        TextView addressView = (TextView) findViewById(R.id.addressView);
        addressView.setText(address);

        if (!website.isEmpty()){
            TextView websiteLink = (TextView) findViewById(R.id.websiteLink);
            websiteLink.setText(website);
        }


        ImageButton callButton = (ImageButton)findViewById(R.id.imageButton);
        callButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + phone.trim()));
                    int permissionCheck = ContextCompat.checkSelfPermission(VetDetailsActivity.this, Manifest.permission.CALL_PHONE);

                    startActivity(callIntent);


                } catch (ActivityNotFoundException activityException) {
                    Log.e("Calling a Phone Number", "Call failed", activityException);
                }
            }
        });
    }
}
