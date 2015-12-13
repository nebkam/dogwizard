package com.technics.trnqlchallenge;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import com.parse.ParseUser;

public class DogPhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_photo);

        ParseUser user = ParseUser.getCurrentUser();
        ImageView dogPhoto = (ImageView)findViewById(R.id.dogPhoto);
        if (user.getString("photo") != null) {
            byte[] bytes = Base64.decode(user.getString("photo"), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            dogPhoto.setImageBitmap(bitmap);
        }
    }
}
