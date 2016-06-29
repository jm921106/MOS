package com.example.sejune.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by SE JUNE on 2016-06-27.
 */
public class newStore extends Activity {
    ImageView newStore_back;
    EditText newStore_name, newStore_admin, newStore_phone, newStore_address;
    Button newStore_add;

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

                    Cursor cursor = dbManager.select("SELECT * FROM storeTBL;");
                    int count = 0;
                    while(cursor.moveToNext()) {
                        count++;
                    }

                    dbManager.insert("INSERT INTO storeTBL VALUES("
                            + count +", '"
                            + newStore_name.getText().toString() +"', '"
                            + adminEmail +"', '"
                            + newStore_phone.getText().toString() +"', '"
                            + newStore_address.getText().toString() +"') ");
                    cursor.close();

                    cursor = dbManager.select("SELECT * FROM staffTBL;");

                    dbManager.insert("INSERT INTO staffTBL VALUES("
                            + 0 +", '" // First Member
                            + adminEmail +"', '"
                            + newStore_name.getText().toString() +"') ");
                    cursor.close();
                    //완료되서 등록

                    cursor = dbManager.select("SELECT * FROM storeTBL;");
                    String str = "";
                    while(cursor.moveToNext()) {
                        str += cursor.getInt(0) + " " +
                                cursor.getString(1) + " " +
                                cursor.getString(2) + " " +
                                cursor.getString(3) + " " +
                                cursor.getString(4) + " " +
                                "\n";
                    }
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();

                    finish();
                } else
                    Toast.makeText(getApplicationContext(), "빈 칸 없이 입력해 주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
