package com.example.sejune.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
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
                if(!edtToEmail.getText().toString().equals("") && !edtContent.getText().toString().equals("")){
                    String sql = "SELECT * FROM accountTBL WHERE EMAIL='" + edtToEmail.getText().toString() +"';";
                    Cursor cursor1 = dbManager.select(sql);
                    if(cursor1.getCount() != 0){
                        dbManager.insert("INSERT INTO messageTBL VALUES("
                                + messageINDEX +", " // Manager
                                + storeID + ", '"
                                + userEmail +"', '"
                                + edtToEmail.getText().toString() +"', '"
                                + edtContent.getText().toString() + "');");

                        edtToEmail.setBackgroundColor(Color.WHITE);
                        Toast.makeText(getApplicationContext(), "메시지를 전송하였습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "아이디가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                        edtToEmail.setBackgroundColor(Color.RED);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "보내는 사람과 내용을 모두 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
