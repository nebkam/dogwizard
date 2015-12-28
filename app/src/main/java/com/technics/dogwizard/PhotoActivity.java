package com.technics.dogwizard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

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
        ParseFile file = user.getParseFile("photoFile");
        if (file == null) {
            Button btn = (Button)findViewById(R.id.btn_switch);
            btn.setText(R.string.btn_set_photo);
        } else {
            file.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, ParseException e) {
                    if (e == null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        dogPhoto.setImageBitmap(bitmap);
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.err_photo_download, Toast.LENGTH_SHORT).show();
                    }
                }
            });
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
                //Save to Parse
                final ParseFile file = new ParseFile("photo.jpg",bytes);
                file.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            ParseUser user = ParseUser.getCurrentUser();
                            user.put("photoFile", file);
                            user.saveInBackground();
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.err_photo_upload, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
