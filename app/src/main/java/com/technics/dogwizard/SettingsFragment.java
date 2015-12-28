package com.technics.dogwizard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.parse.ParseUser;

public class SettingsFragment extends PreferenceFragment {
    public static final String KEY_PREF_DOG_NAME = "pref_name";
    public static final String KEY_PREF_DOG_BREED = "pref_breed";
    public static final String KEY_PREF_DOG_SEX = "pref_sex";

    private SharedPreferences.OnSharedPreferenceChangeListener listener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                    // listener implementation
                    if (key.equals(KEY_PREF_DOG_NAME)) {
                        String dogName = prefs.getString(KEY_PREF_DOG_NAME,"Anonymous");

                        //Update UI
                        Preference pref = findPreference(KEY_PREF_DOG_NAME);
                        pref.setSummary(dogName);

                        //Persist to Parse
                        ParseUser user = ParseUser.getCurrentUser();
                        user.put("dogName",dogName);
                        user.saveInBackground();
                    }
                    if (key.equals(KEY_PREF_DOG_BREED)) {
                        String dogBreed = prefs.getString(KEY_PREF_DOG_BREED,"Unknown");

                        //Update UI
                        Preference pref = findPreference(KEY_PREF_DOG_BREED);
                        pref.setSummary(dogBreed);

                        //Persist to Parse
                        ParseUser user = ParseUser.getCurrentUser();
                        user.put("dogBreed",dogBreed);
                        user.saveInBackground();
                    }
                    if (key.equals(KEY_PREF_DOG_SEX)) {
                        String dogSex = prefs.getString(KEY_PREF_DOG_SEX, "Unknown");
                        Preference pref = findPreference(KEY_PREF_DOG_SEX);
                        pref.setSummary(dogSex);

                        //Persist to Parse
                        ParseUser user = ParseUser.getCurrentUser();
                        user.put("dogSex",dogSex);
                        user.saveInBackground();
                    }
                }
            };

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        //Set values to summaries
        Preference dogName = findPreference(KEY_PREF_DOG_NAME);
        dogName.setSummary(dogName.getSharedPreferences().getString(KEY_PREF_DOG_NAME, "Anonymous"));
        Preference dogBreed = findPreference(KEY_PREF_DOG_BREED);
        dogBreed.setSummary( dogBreed.getSharedPreferences().getString(KEY_PREF_DOG_BREED,"Unknown") );
        Preference dogSex = findPreference(KEY_PREF_DOG_SEX);
        dogSex.setSummary( dogSex.getSharedPreferences().getString(KEY_PREF_DOG_SEX,"Unknown") );


        // Register a listener for persisting to Parse
        getPreferenceScreen()
                .getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(listener);
    }
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen()
                .getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen()
                .getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(listener);
    }
}
