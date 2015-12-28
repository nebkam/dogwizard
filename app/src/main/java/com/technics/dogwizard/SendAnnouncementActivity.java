package com.technics.dogwizard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class SendAnnouncementActivity extends AppCompatActivity {
    private Double latitude;
    private Double longitude;
    private EditText announcementBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_announcement);

        latitude = getIntent().getDoubleExtra("latitude",0.00);
        longitude = getIntent().getDoubleExtra("longitude",0.00);
        announcementBody = (EditText) findViewById(R.id.announcementBody);
    }

    public void sendAnnouncement(View view) {
        if (latitude == 0.00 || longitude == 0.00) {
            Toast.makeText(getApplicationContext(), R.string.err_location, Toast.LENGTH_SHORT).show();
            return;
        }

        if (announcementBody.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.err_message, Toast.LENGTH_SHORT).show();
            return;
        }

        ParseObject announcement = new ParseObject("Announcement");
        ParseGeoPoint point = new ParseGeoPoint(latitude,longitude);
        ParseUser user = ParseUser.getCurrentUser();
        announcement.put("body", announcementBody.getText().toString());
        announcement.put("location",point);
        announcement.put("createdBy",user);
        announcement.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), R.string.announcement_sent, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Intent intent = new Intent(SendAnnouncementActivity.this, OwnersNearbyActivity.class);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        startActivity(intent);
    }
}
