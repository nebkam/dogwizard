package com.technics.trnqlchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class MessageInboxActivity extends AppCompatActivity {
    public String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_inbox);
        setTitle(R.string.inbox_title);
        final ParseUser user = ParseUser.getCurrentUser();
        final ParseQueryAdapter<ParseObject> adapter =
                new ParseQueryAdapter<ParseObject>(this, new ParseQueryAdapter.QueryFactory<ParseObject>() {
                    public ParseQuery<ParseObject> create() {
                        // Here we can configure a ParseQuery to our heart's desire.
                        ParseQuery query = new ParseQuery("Message");
                        query.whereEqualTo("to",user.getObjectId());
                        return query;
                    }
                });
        if (adapter.isEmpty()){
            Toast.makeText(getApplicationContext(), R.string.no_messages, Toast.LENGTH_LONG).show();
        }
        else{
            adapter.setTextKey("body");

            ListView messageList = (ListView) findViewById(R.id.messageList);
            messageList.setAdapter(adapter);

            messageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ParseObject message = adapter.getItem(position);

                    Intent intent = new Intent(MessageInboxActivity.this,ViewMessageActivity.class);
                    intent.putExtra("body", message.getString("body"));
                    intent.putExtra("from", message.getString("from"));
                    startActivity(intent);
                }
            });
        }
    }
}
