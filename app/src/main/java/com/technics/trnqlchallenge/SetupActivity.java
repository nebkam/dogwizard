package com.technics.trnqlchallenge;

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
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.parse.ParseUser;

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

//        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
//        preferences.edit().putBoolean("firstRun",false).apply();
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
