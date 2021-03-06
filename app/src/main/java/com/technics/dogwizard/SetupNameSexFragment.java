package com.technics.dogwizard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.parse.ParseUser;

public class SetupNameSexFragment extends Fragment  {
    private EditText dogName;
    private RadioGroup dogSex;
    private SharedPreferences preferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_setup_2_name_sex, container, false);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        dogName = (EditText)rootView.findViewById(R.id.dogName);
        dogName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String updated = dogName.getText().toString();
                if (!hasFocus && !updated.equals("")) {
                    ParseUser user = ParseUser.getCurrentUser();
                    user.put("dogName", updated);
                    user.saveInBackground();

                    preferences.edit().putString("pref_name", updated).apply();
                }
            }
        });

        dogSex = (RadioGroup) rootView.findViewById(R.id.dogSex);
        dogSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    ParseUser user = ParseUser.getCurrentUser();
                    if (checkedId == R.id.male) {
                        user.put("dogSex", "male");
                        preferences.edit().putString("pref_sex","male").apply();
                    } else {
                        user.put("dogSex", "female");
                        preferences.edit().putString("pref_sex","female").apply();
                    }
                    user.saveInBackground();
                }
            }
        });

        return rootView;
    }
}
