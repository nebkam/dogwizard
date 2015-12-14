package com.technics.trnqlchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

public class MessageInboxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_inbox);

        ParseQueryAdapter<ParseObject> adapter = new ParseQueryAdapter<ParseObject>(this,"Message");
        adapter.setTextKey("body");
        ListView messageList = (ListView)findViewById(R.id.messageList);
        messageList.setAdapter(adapter);
    }
}
