package com.technics.trnqlchallenge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class OwnerDetailsActivity extends AppCompatActivity {
    private String userToken;
    private TextView dogName;
    private TextView dogBreed;
    private ImageView dogPhoto;
    private Button contactBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_details);

//        Intent intent = getIntent();
//        userToken = intent.getStringExtra("userToken");
//        dogName = (TextView)findViewById(R.id.dogName);
//        dogBreed = (TextView)findViewById(R.id.dogBreed);
//        dogPhoto = (ImageView)findViewById(R.id.dogPhoto);
//        contactBtn = (Button)findViewById(R.id.btn_contact);
//
//        ParseQuery<ParseUser> query = ParseUser.getQuery();
//        query.getInBackground(userToken, new GetCallback<ParseUser>() {
//            @Override
//            public void done(ParseUser user, ParseException e) {
//                if (e == null && user != null) {
//                    if (user.getString("dogName") != null) {
//                        dogName.setText(user.getString("dogName"));
//                    }
//                    if (user.getString("dogBreed") != null) {
//                        dogBreed.setText(user.getString("dogBreed"));
//                    }
//                    if (user.getString("photo") != null) {
//                        byte[] bytes = Base64.decode(user.getString("photo"),Base64.DEFAULT);
//                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//                        dogPhoto.setImageBitmap(bitmap);
//                    }
//                    if (user.getBoolean("contact")) {
//                        contactBtn.setVisibility(View.VISIBLE);
//                    }
//                    else {
//                        contactBtn.setVisibility(View.GONE);
//                    }
//                }
//            }
//        });
    }

//    public void contact(View view) {
//        Intent intent = new Intent(OwnerDetailsActivity.this,MessageActivity.class);
//        intent.putExtra("to",userToken);
//        startActivity(intent);
//    }
}
