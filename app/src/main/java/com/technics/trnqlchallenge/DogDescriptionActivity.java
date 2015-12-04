package com.technics.trnqlchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class DogDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_description);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, BREEDS);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.breedAutoComplete);
        textView.setAdapter(adapter);
    }

    private static final String[] BREEDS = new String[] {
        "American Bulldog", "Beagle","Labrador Retriever"
    };
}
