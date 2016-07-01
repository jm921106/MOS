package com.example.sejune.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by SE JUNE on 2016-06-27.
 */
public class newMessage extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mos_new_message);

        final Spinner spinnerToEmail = (Spinner) findViewById(R.id.spinnerToEmail);
        final EditText edtContent = (EditText) findViewById(R.id.edtContent);
        Button btnSend = (Button) findViewById(R.id.btnSend);

        Intent intent = getIntent();
        final String userEmail = intent.getStringExtra("EMAIL");
        final int userType = intent.getIntExtra("UserType", 2);
        final String storeName = intent.getStringExtra("StoreName");
        final int storeID = intent.getIntExtra("StoreID", -1);

        final dataBase dbManager = new dataBase(this);

        Cursor cursor = dbManager.select("SELECT * FROM messageTBL;");
        int count = 0;
        while(cursor.moveToNext()) {
            count++;
        }
        cursor.close();

        ArrayList<String> memberName = new ArrayList<>();
        cursor = dbManager.select("SELECT * FROM staffTBL WHERE storeINDEX = "+ storeID +";");
        while(cursor.moveToNext()) {
            memberName.add(cursor.getString(2));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, memberName);
        spinnerToEmail.setAdapter(adapter);

        final int messageINDEX = count;


        //add btn
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtContent.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "input content." , Toast.LENGTH_SHORT).show();
                } else {
                    dbManager.insert("INSERT INTO messageTBL VALUES("
                            + messageINDEX +", " // Manager
                            + storeID + ", '"
                            + userEmail +"', '"
                            + spinnerToEmail.getSelectedItem().toString() +"', '"
                            + edtContent.getText().toString() + "');");
                    finish();
                }

            }
        });

    }
}
