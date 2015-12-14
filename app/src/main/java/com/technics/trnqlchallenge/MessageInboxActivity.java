package com.technics.trnqlchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

public class MessageInboxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_inbox);

        final ParseQueryAdapter<ParseObject> adapter = new ParseQueryAdapter<ParseObject>(this,"Message");
        adapter.setTextKey("body");

        ListView messageList = (ListView)findViewById(R.id.messageList);
        messageList.setAdapter(adapter);

        messageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ParseUser friend = mAdapter.getItem(position);
                  ParseObject message = adapter.getItem(position);

                Intent intent = new Intent(MessageInboxActivity.this,ViewMessageActivity.class);
                intent.putExtra("body", message.getString("body"));
                intent.putExtra("from", message.getString("from"));
                startActivity(intent);
            }
        });
    }
}
