package com.technics.trnqlchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class OwnerDetailsActivity extends AppCompatActivity {
    private TextView dogName;
    private TextView dogBreed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_details);

        Intent intent = getIntent();
        String userToken = intent.getStringExtra("userToken");
        dogName = (TextView)findViewById(R.id.dogName);
        dogBreed = (TextView)findViewById(R.id.dogBreed);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.getInBackground(userToken, new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser user, ParseException e) {
                dogName.setText("Anonymous");
                dogBreed.setText("Unknown");
                if (e == null && user != null) {
                    if (user.getString("dogName") != null) {
                        dogName.setText(user.getString("dogName"));
                    }
                    if (user.getString("dogBreed") != null) {
                        dogBreed.setText(user.getString("dogBreed"));
                    }
                }
            }
        });
    }
}
