package com.technics.trnqlchallenge;

import com.parse.Parse;
import com.parse.ParseObject;
import com.trnql.smart.base.SmartCompatActivity;
import android.os.Bundle;

public class MainActivity extends SmartCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Init TRNQL SDK
        getAppData().setApiKey("a260b0e4-74f9-4bc8-bc2c-a73c4861ea82");
        //Init Parse SDK
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "HjP85guTMScAOKptkuVmXNY7OuHxF0HbNEpWl2gD", "LDtwzrmTgBdQPICRLLdhLaNqUR4Q2vnLdzlUewAs");

//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();
    }
}
