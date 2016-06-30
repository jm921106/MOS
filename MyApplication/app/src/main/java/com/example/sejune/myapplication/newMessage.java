package com.example.sejune.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by SE JUNE on 2016-06-27.
 */
public class newMessage extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mos_new_message);

        final EditText edtToEmail = (EditText) findViewById(R.id.edtToEmail);
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

        final int messageINDEX = count;

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager.insert("INSERT INTO messageTBL VALUES("
                        + messageINDEX +", " // Manager
                        + storeID + ", '"
                        + userEmail +"', '"
                        + edtToEmail.getText().toString() +"', '"
                        + edtContent.getText().toString() + "');");

                Toast.makeText(getApplicationContext(), "insert ok", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

    }
}
