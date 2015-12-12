package com.technics.trnqlchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class AnnouncementActivity extends AppCompatActivity {
    private ParseUser user = ParseUser.getCurrentUser();
    private Double latitude;
    private Double longitude;
    private EditText message;
    private ParseObject announcement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        Button announceButton = (Button) findViewById(R.id.announceButton);
        latitude = getIntent().getDoubleExtra("latitude",0.00);
        longitude = getIntent().getDoubleExtra("longitude",0.00);
        message = (EditText) findViewById(R.id.announceMessage);
        announcement = new ParseObject("Announcement");

        announceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                announcement.put("message", message.getText().toString());
                announcement.put("latitude", latitude);
                announcement.put("longitude", longitude);
                announcement.put("userToken", user.getObjectId());
                announcement.saveInBackground();

                Intent intent = new Intent(AnnouncementActivity.this, OwnersNearbyActivity.class);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                startActivity(intent);
            }
        });

    }
}
