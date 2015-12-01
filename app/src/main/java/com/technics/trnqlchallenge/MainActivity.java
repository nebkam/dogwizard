package com.technics.trnqlchallenge;

import android.os.Bundle;

import com.trnql.smart.base.SmartCompatActivity;
import com.trnql.zen.core.db.DbKVP;

public class MainActivity extends SmartCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getAppData().setApiKey("a260b0e4-74f9-4bc8-bc2c-a73c4861ea82");
        DbKVP myDB = getDBManager().getDB_KVP(R.id.db_kvp_trnql);
    }
}
