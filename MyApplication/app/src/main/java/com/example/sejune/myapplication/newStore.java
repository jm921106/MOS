package com.example.sejune.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by SE JUNE on 2016-06-27.
 */
public class newStore extends Activity {
    ImageView newStore_back;
    EditText newStore_name, newStore_admin, newStore_phone, newStore_address;
    Button newStore_add;

    //selectStore selectStore = new selectStore();

    final dataBase dbManager = new dataBase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mos_new_store);

        //getIntent
        Intent intent = getIntent();
        final String adminEmail = intent.getStringExtra("EMAIL");

        newStore_back = (ImageView) findViewById(R.id.newStore_back);
        newStore_name = (EditText) findViewById(R.id.newStore_name);
        newStore_admin = (EditText) findViewById(R.id.newStore_admin);
        newStore_phone = (EditText) findViewById(R.id.newStore_phone);
        newStore_address = (EditText) findViewById(R.id.newStore_address);
        newStore_add = (Button) findViewById(R.id.newStore_add);
        newStore_admin.setText(adminEmail);

        newStore_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        newStore_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!newStore_name.getText().toString().equals("") &&
                        !newStore_admin.getText().toString().equals("") &&
                        !newStore_phone.getText().toString().equals("") &&
                        !newStore_address.getText().toString().equals("")) {

                    //유효한 계정인지 체크
                    if(!newStore_admin.getText().toString().equals("")){
                        Cursor cursor = dbManager.select("SELECT * FROM accountTBL WHERE EMAIL='" + newStore_admin.getText().toString() + "';");
                        int count = 0;
                        while(cursor.moveToNext()) {
                            count++;
                        }

                        //유효 계정일때
                        if(count !=0){
                            newStore_admin.setBackgroundColor(Color.WHITE);
                            cursor = dbManager.select("SELECT * FROM storeTBL;");
                            count = 0;
                            while(cursor.moveToNext()) {
                                count++;
                            }
                            cursor.close();

                            //가게정보 등록
                            dbManager.insert("INSERT INTO storeTBL VALUES("
                                    + count +", '"
                                    + newStore_name.getText().toString() +"', '"
                                    + newStore_admin.getText().toString()  +"', '"
                                    + newStore_phone.getText().toString() +"', '"
                                    + newStore_address.getText().toString() +"');");

                            //스태프 추가
                            dbManager.insert("INSERT INTO staffTBL VALUES("
                                    + 0 +", " // Admin
                                    + count + ", '"
                                    + adminEmail +"', '"
                                    + newStore_name.getText().toString() +"');");

                            if(!adminEmail.equals(newStore_admin.getText().toString())){
                                dbManager.insert("INSERT INTO staffTBL VALUES("
                                        + 1 +", " // Manager
                                        + count + ", '"
                                        + newStore_admin.getText().toString() +"', '"
                                        + newStore_name.getText().toString() +"');");

                                dbManager.update("UPDATE accountTBL SET TYPE= 1 WHERE EMAIL ='" + newStore_admin.getText().toString() +"';");
                            }

                            //완료되서 등록
                            Toast.makeText(getApplicationContext(), "가게가 등록되었습니다.", Toast.LENGTH_SHORT).show();

                            //select 스토어의 가게 부분 갱신
                    /*
                                        final ArrayList<String> storeList = new ArrayList<String>();
                                        cursor = dbManager.select("SELECT * FROM staffTBL WHERE staffEMAIL = '" + adminEmail + "';");
                                        while (cursor.moveToNext()) {
                                            storeList.add(cursor.getString(2));
                                        }
                                        ArrayAdapter<String> adapter =
                                                new ArrayAdapter<String>(selectStore.getApplicationContext(), android.R.layout.simple_list_item_1, storeList);
                                        selectStore.selectStore_list.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                     */


                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "유효한 계정이 아닙니다.", Toast.LENGTH_SHORT).show();
                            newStore_admin.setBackgroundColor(Color.RED);
                        }
                    }
                } else
                    Toast.makeText(getApplicationContext(), "빈 칸 없이 입력해 주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
