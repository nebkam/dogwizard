package com.technics.trnqlchallenge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.parse.ParseUser;

public class SetupNameSexFragment extends Fragment  {
    private EditText dogName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_setup_2_name_sex, container, false);

        dogName = (EditText)rootView.findViewById(R.id.dogName);
        dogName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String updated = dogName.getText().toString();
                if (!hasFocus && !updated.equals("")) {
                    ParseUser user = ParseUser.getCurrentUser();
                    user.put("dogName",updated);
                    user.saveInBackground();
                }
            }
        });

        return rootView;
    }
}
