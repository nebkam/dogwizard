package com.technics.trnqlchallenge;

import com.trnql.zen.core.AppData;
import com.parse.Parse;
import com.parse.ParseUser;

public class MyApplication extends AppData {
    //This will only be called once in your app's entire lifecycle.
    @Override
    public void onCreate() {
        super.onCreate();

        //Init Parse SDK
        Parse.initialize(this, "HjP85guTMScAOKptkuVmXNY7OuHxF0HbNEpWl2gD", "LDtwzrmTgBdQPICRLLdhLaNqUR4Q2vnLdzlUewAs");
        ParseUser.enableAutomaticUser();
    }
}
