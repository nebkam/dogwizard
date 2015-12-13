package com.technics.trnqlchallenge;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.parse.ParseUser;

public class SettingsFragment extends PreferenceFragment {
    public static final String KEY_PREF_DOG_NAME = "pref_name";
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
                }
            };

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        //Set values to summaries
        Preference dogName = findPreference(KEY_PREF_DOG_NAME);
        dogName.setSummary( dogName.getSharedPreferences().getString(KEY_PREF_DOG_NAME,"Anonymous") );

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
