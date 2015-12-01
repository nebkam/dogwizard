package com.technics.trnqlchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.trnql.zen.core.AppData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppData.startAllServices(this);
    }

    @Override
    protected void onDestroy() {
        AppData.stopAllServices(getApplication());
        super.onDestroy();
    }
}
