package com.technics.dogwizard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ViewMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.message_title);
        setContentView(R.layout.activity_view_message);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        query.whereEqualTo("objectId", getIntent().getStringExtra("fromUserId"));
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object != null) {
                    String dogName = object.getString("dogName");
                    TextView messageFrom = (TextView) findViewById(R.id.messageFrom);
                    messageFrom.setText(dogName);
                }
            }
        });

        TextView messageBody = (TextView) findViewById(R.id.messageBody);
        messageBody.setText(getIntent().getStringExtra("body"));

        Button replyButton = (Button) findViewById(R.id.replyButton);
        replyButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(ViewMessageActivity.this,MessageActivity.class);
                intent.putExtra("to", getIntent().getStringExtra("from"));
                startActivity(intent);
            }
        });
    }
}
