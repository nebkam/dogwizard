package com.technics.trnqlchallenge;

import com.trnql.smart.base.SmartCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends SmartCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init TRNQL SDK
        getAppData().setApiKey("a260b0e4-74f9-4bc8-bc2c-a73c4861ea82");
    }

    public void showSitters(View View) {
        Intent intent = new Intent(MainActivity.this,MapsActivity.class);
        startActivity(intent);
    }
}
