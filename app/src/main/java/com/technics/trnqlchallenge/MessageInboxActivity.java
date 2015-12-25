package com.technics.trnqlchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MessageInboxActivity extends AppCompatActivity {
    public String userId;
    private RecyclerView messagesView;
    private MessageAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_inbox);

        Intent intent = getIntent();

       messagesView = (RecyclerView)findViewById(R.id.messages_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        messagesView.setLayoutManager(layoutManager);

         messageAdapter = new MessageAdapter(null);
        messagesView.setAdapter(messageAdapter);
        fetch();
    }

    private void fetch() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Message");
        ParseUser user = ParseUser.getCurrentUser();
        query.whereEqualTo("to", user.getObjectId());
        query.include("from");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> messages, ParseException e) {
                if (e == null) {
                    messageAdapter.refresh(decorate(messages));
                    messageAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.err_messages, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private List<Message> decorate(List<ParseObject> objects) {
        List<Message> decorated = new ArrayList<>();
        for (ParseObject object:objects) {
            Message message = new Message();

            message.body = object.getString("body");
            ParseObject from = object.getParseObject("from");
            message.from = from.getString("dogName");
            message.fromUserId = object.getString("fromUserId");

            decorated.add(message);
        }
        return  decorated;
    }
}
