package com.technics.trnqlchallenge;

import android.os.Bundle;
import com.trnql.smart.base.SmartCompatActivity;
import com.trnql.smart.location.LocationEntry;
import com.trnql.zen.core.AppData;

public class MainActivity extends SmartCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getAppData().setApiKey("a260b0e4-74f9-4bc8-bc2c-a73c4861ea82");
        AppData.startAllServices(this);
    }

    @Override
    protected void smartLatLngChange(LocationEntry value) {
//        location_latitude.setText(String.format("Latitude:   %s", String.valueOf(value.getLatitude())));
//        location_longitude.setText(String.format("Longitude:   %s", String.valueOf(value.getLongitude())));
//        location_accuracy.setText(String.format("Accuracy:   %s", String.valueOf(value.getAccuracy())));
    }

    @Override
    protected void onDestroy() {
        AppData.stopAllServices(getApplication());
        super.onDestroy();
    }
}
