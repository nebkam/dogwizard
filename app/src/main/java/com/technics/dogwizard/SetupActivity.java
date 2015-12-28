package com.technics.dogwizard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SetupActivity extends AppCompatActivity {
    private static final int NUM_PAGES = 4;
    private static final int SELECT_PHOTO = 100;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        ParseUser user = ParseUser.getCurrentUser();
        user.put("dogName","Anonymous");
        user.put("dogBreed","Unknown");
        user.put("dogSex","Unknown");
        user.put("contact", true);
        user.saveInBackground();


        pager = (ViewPager)findViewById(R.id.setupPager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }

    public void nextSlide(View view) {
        pager.setCurrentItem( pager.getCurrentItem() + 1 );
    }

    public void closeSetup(View view) {
        Intent intent = new Intent(SetupActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void pickImage(View View) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SELECT_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK) {
            if (data == null) {
                System.out.print("Error occurred");
                return;
            }
            try {
                Context context = this;
                InputStream inputStream = context.getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                Button btnUploadPhoto = (Button)findViewById(R.id.btn_upload_photo);
                btnUploadPhoto.setVisibility(View.GONE);
                ImageView dogPhoto = (ImageView)findViewById(R.id.dogPhoto);
                dogPhoto.setImageBitmap(bitmap);
                dogPhoto.setVisibility(View.VISIBLE);
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
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;

            switch (position) {
                case 0:
                default:
                    fragment = new SetupIntroFragment();
                    break;
                case 1:
                    fragment = new SetupNameSexFragment();
                    break;
                case 2:
                    fragment = new SetupBreedFragment();
                    break;
                case 3:
                    fragment = new SetupPhotoFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
