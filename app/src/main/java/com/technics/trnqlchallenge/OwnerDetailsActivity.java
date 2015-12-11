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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_details);

        Intent intent = getIntent();
        String userToken = intent.getStringExtra("userToken");
        dogName = (TextView)findViewById(R.id.dogName);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.getInBackground(userToken, new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null && user != null && user.getString("dogName") != null) {
                    dogName.setText(user.getString("dogName"));
                } else {
                    dogName.setText("Unknown");
                }
            }
        });
    }
}
