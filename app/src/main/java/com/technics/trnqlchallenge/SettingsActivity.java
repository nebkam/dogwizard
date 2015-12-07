package com.technics.trnqlchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(R.string.settings_title);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
}
