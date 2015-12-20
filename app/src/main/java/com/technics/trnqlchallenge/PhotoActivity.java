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

import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PhotoActivity extends AppCompatActivity {
    private static final int SELECT_PHOTO = 100;
    private ImageView dogPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        dogPhoto = (ImageView)findViewById(R.id.dogPhoto);

        ParseUser user = ParseUser.getCurrentUser();
        if (user.getString("photo").equals("")) {
            Button btn = (Button)findViewById(R.id.btn_switch);
            btn.setText(R.string.btn_set_photo);
        } else {
            byte[] bytes = Base64.decode(user.getString("photo"), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            dogPhoto.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK) {
            try {
                //Sync UI
                InputStream inputStream = getContentResolver().openInputStream(intent.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                dogPhoto.setImageBitmap(bitmap);
                //Encode to string
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                String photoAsString = Base64.encodeToString(bytes, Base64.DEFAULT);
                //Save to Parse
                ParseUser user = ParseUser.getCurrentUser();
                user.put("photo", photoAsString);
                user.saveInBackground();
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    public void changePhoto(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SELECT_PHOTO);
    }
}
