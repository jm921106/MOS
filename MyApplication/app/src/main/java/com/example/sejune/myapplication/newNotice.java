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
public class newNotice extends Activity {
    ImageView newNotice_back;
    Button newNotice_Post;
    EditText newNotice_title,  newNotice_Text;

    final dataBase dbManager = new dataBase(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mos_new_notice);

        newNotice_back = (ImageView)findViewById(R.id.newNotice_back);
        newNotice_Post = (Button)findViewById(R.id.newNotice_Post);
        newNotice_title = (EditText)findViewById(R.id.newNotice_title);
        newNotice_Text = (EditText)findViewById(R.id.newNotice_Text);

        newNotice_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        newNotice_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!newNotice_Text.getText().toString().equals("") &&
                        !newNotice_title.getText().toString().equals("")){

                    int count = 0;
                    Cursor cursor = dbManager.select("SELECT * FROM noticeTBL");
                    while(cursor.moveToNext()) count++;

                    Intent intent = getIntent();
                    String date = intent.getStringExtra("noticeDATE");
                    //String email = intent.getStringExtra("noticeFROM");
                    String name = intent.getStringExtra("NAME");
                    int storeID = intent.getIntExtra("StoreID", -1);
                    if(storeID != -1){
                        dbManager.insert("INSERT INTO noticeTBL VALUES("+
                                count + ", " +
                                storeID + ", '" +
                                date + "', '" +
                                name + "', '" +
                                newNotice_title.getText().toString() + "', '" +
                                newNotice_Text.getText().toString() + "');");
                        Toast.makeText(getApplicationContext(), "공지가 등록되었습니다.", Toast.LENGTH_SHORT).show();
                    }

                    finish();
                }else
                    Toast.makeText(getApplicationContext(), "제목과 내용을 입력하세요,", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
