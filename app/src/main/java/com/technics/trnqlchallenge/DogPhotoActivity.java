package com.technics.trnqlchallenge;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.parse.ParseUser;

public class DogPhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_photo);

        ParseUser user = ParseUser.getCurrentUser();
        ImageView dogPhoto = (ImageView)findViewById(R.id.dogPhoto);
        if (user.getString("photo").equals("")) {
            Button btn = (Button)findViewById(R.id.btn_switch);
            btn.setText(R.string.btn_set_photo);
        } else {
            byte[] bytes = Base64.decode(user.getString("photo"), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            dogPhoto.setImageBitmap(bitmap);
        }
    }

    public void changePhoto(View view) {
    }
}
