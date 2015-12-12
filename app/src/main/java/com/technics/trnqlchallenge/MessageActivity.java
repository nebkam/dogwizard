package com.technics.trnqlchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
    }

    public void sendMessage(View view) {
        ParseUser user = ParseUser.getCurrentUser();
        String sendFrom = user.getObjectId();
        Intent intent = getIntent();
        String sendTo = intent.getStringExtra("to");

        EditText body = (EditText) findViewById(R.id.messageBody);
        if (body.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),R.string.err_message,Toast.LENGTH_SHORT).show();
            return;
        }

        ParseObject message = new ParseObject("Message");
        message.put("from",sendFrom);
        message.put("to",sendTo);
        message.put("body",body.getText().toString());
        message.put("isRead", false);
        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), R.string.message_sent, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Intent leave = new Intent(MessageActivity.this,OwnerDetailsActivity.class);
        leave.putExtra("userToken", sendTo);
        startActivity(leave);
    }
}
