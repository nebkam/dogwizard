package com.technics.trnqlchallenge;

import com.trnql.smart.people.PeopleManager;
import com.trnql.zen.core.AppData;
import com.parse.Parse;
import com.parse.ParseUser;

public class MyApplication extends AppData {
    //This will only be called once in your app's entire lifecycle.
    @Override
    public void onCreate() {
        super.onCreate();

        //Init TRNQL SDK
        setApiKey("a260b0e4-74f9-4bc8-bc2c-a73c4861ea82");

        //Init Parse SDK
        Parse.initialize(this, "HjP85guTMScAOKptkuVmXNY7OuHxF0HbNEpWl2gD", "LDtwzrmTgBdQPICRLLdhLaNqUR4Q2vnLdzlUewAs");
        ParseUser.enableAutomaticUser();
        ParseUser user = ParseUser.getCurrentUser();

        //If we've got an ID from Parse, use it for TRNQL access token
        if (user.getObjectId() != null) {
            PeopleManager.INSTANCE.setUserToken(user.getObjectId());
        }
    }
}
