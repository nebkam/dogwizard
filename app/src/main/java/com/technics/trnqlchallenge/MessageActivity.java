package com.technics.trnqlchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
    }

    public void sendMessage(View view) {
        EditText body = (EditText) findViewById(R.id.messageBody);
        if (body.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),R.string.err_message,Toast.LENGTH_SHORT).show();
            return;
        }
//        ParseObject message = new ParseObject("Message");
    }
}
