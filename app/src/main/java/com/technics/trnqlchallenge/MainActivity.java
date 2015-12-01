package com.technics.trnqlchallenge;

import com.trnql.smart.base.SmartCompatActivity;
import android.os.Bundle;
import com.trnql.zen.core.AppData;

public class MainActivity extends SmartCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getAppData().setApiKey("a260b0e4-74f9-4bc8-bc2c-a73c4861ea82");
        AppData.startAllServices(this);
    }

    @Override
    protected void onDestroy() {
        AppData.stopAllServices(getApplication());
        super.onDestroy();
    }
}
