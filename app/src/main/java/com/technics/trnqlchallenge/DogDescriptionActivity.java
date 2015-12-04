package com.technics.trnqlchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class DogDescriptionActivity extends AppCompatActivity {

    private ParseUser user;

    private static final String[] BREEDS = new String[] {
            "American Bulldog", "Beagle","Labrador Retriever"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_description);

        user = ParseUser.getCurrentUser();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, BREEDS);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.dogBreed);
        textView.setAdapter(adapter);
    }

    public void saveDescription(View View){
//        EditText dogName = (EditText)findViewById(R.id.dogName);
//        AutoCompleteTextView dogBreed = (AutoCompleteTextView)findViewById(R.id.dogBreed);
//        user.put("dogName", dogName.getText().toString());
//        user.put("dogBreed", dogBreed.getText().toString());
//        user.saveInBackground();
    }

    public void saveDefaultDescription(View View) {
//        user.put("dogName", "Anonymous");
//        user.put("dogBreed", "Unknown");
//        user.saveInBackground();
    }
}
