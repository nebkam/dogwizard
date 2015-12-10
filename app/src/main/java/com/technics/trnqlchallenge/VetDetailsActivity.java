package com.technics.trnqlchallenge;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        String phone = getIntent().getStringExtra("phone");

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
    }
}
