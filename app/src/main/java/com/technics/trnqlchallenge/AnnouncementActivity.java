package com.technics.trnqlchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class AnnouncementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        Button announceButton = (Button) findViewById(R.id.announceButton);
        final ParseUser user = ParseUser.getCurrentUser();
        final String latitude = getIntent().getStringExtra("latitude");
        final String longitude = getIntent().getStringExtra("longitude");
        final EditText message = (EditText) findViewById(R.id.announceMessage);
        final ParseObject announcements = new ParseObject("announcements");

        announceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                announcements.put("message", message.getText().toString());
                announcements.put("latitude", latitude);
                announcements.put("longitude", longitude);
                announcements.put("userToken", user.getObjectId());
                announcements.saveInBackground();

                Intent intent = new Intent(AnnouncementActivity.this, OwnersNearbyActivity.class);
                startActivity(intent);
            }
        });

    }
}
