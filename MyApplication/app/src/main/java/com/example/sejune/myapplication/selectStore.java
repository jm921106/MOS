package com.example.sejune.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by SE JUNE on 2016-06-27.
 */


public class selectStore extends Activity {
    //intent get data
    final String userEmail = "email1";
    final int userType = 0;

    SQLiteDatabase sqlDB;

    Button selectStore_logOut;
    ImageView selectStore_add;
    ListView selectStore_list;


    final dataBase dbManager = new dataBase(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mos_select_store);

        selectStore_logOut = (Button) findViewById(R.id.selectStore_logOut);
        selectStore_add = (ImageView) findViewById(R.id.selectStore_add);
        selectStore_list = (ListView) findViewById(R.id.selectStore_list);

        selectStore_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //userEmail 가져와서 staff의 가게명을 찾는다.
        final ArrayList<String> storeList = new ArrayList<String>();
        Cursor cursor = dbManager.select("SELECT * FROM staffTBL WHERE staffEMAIL = '" + userEmail + "';");

        while (cursor.moveToNext()) {
            storeList.add(cursor.getString(2));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, storeList);
        selectStore_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        selectStore_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String storeID = storeList.get(i);
                Intent intent = new Intent(getApplicationContext(), Base.class);
                //intent.putExtra("StoreID", storeID);
                intent.putExtra("StoreID", storeID);
                startActivity(intent);
            }
        });

        selectStore_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), newStore.class);
                intent.putExtra("EMAIL", userEmail);
                startActivity(intent);
            }
        });


    }
}
