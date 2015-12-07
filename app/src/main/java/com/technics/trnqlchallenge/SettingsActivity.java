package com.technics.trnqlchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.parse.ParseUser;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(R.string.settings_title);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch contactSwitch = (Switch) findViewById(R.id.switch_contact);
        Switch dogWalkSwitch = (Switch) findViewById(R.id.switch_dog_walk);

        //Prepopulate
        ParseUser user = ParseUser.getCurrentUser();
        contactSwitch.setChecked(user.getBoolean("contact"));
        dogWalkSwitch.setChecked(user.getBoolean("dogWalkReminder"));

        //Listeners
        contactSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ParseUser user = ParseUser.getCurrentUser();
                user.put("contact", isChecked);
                user.saveInBackground();
            }
        });
        dogWalkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ParseUser user = ParseUser.getCurrentUser();
                user.put("dogWalkReminder",isChecked);
                user.saveInBackground();
            }
        });
    }

    public void goToDogDescription(View view) {
        Intent intent = new Intent(SettingsActivity.this,DogDescriptionActivity.class);
        intent.putExtra("prePopulate",true);
        startActivity(intent);
    }
}
