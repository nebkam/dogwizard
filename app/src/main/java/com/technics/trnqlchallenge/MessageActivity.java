package com.technics.trnqlchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseObject;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
    }

    public void sendMessage(View view) {
        EditText body = (EditText) findViewById(R.id.messageBody);

        ParseObject message = new ParseObject("Message");

    }
}
