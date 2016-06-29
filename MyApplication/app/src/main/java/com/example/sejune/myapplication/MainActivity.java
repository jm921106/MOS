package com.example.sejune.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    //Login 변수
    EditText login_email, login_pwd;
    Button login_login;
    TextView login_newAccount, login_forgetPW;

    //디비객체생성
    final dataBase dbManager = new dataBase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, splash.class));
        setContentView(R.layout.mos_login);

        login_email = (EditText) findViewById(R.id.login_email);
        login_pwd = (EditText) findViewById(R.id.login_pwd);
        login_login = (Button) findViewById(R.id.login_login);
        login_newAccount = (TextView) findViewById(R.id.login_newAccount);
        login_forgetPW = (TextView) findViewById(R.id.login_forgetPW);
        try {
            dbManager.insert("INSERT INTO accountTBL VALUES('ab', 'ab', 'ab', '0');");
            Toast.makeText(getApplicationContext(), "insert ok", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "error!", Toast.LENGTH_SHORT).show();
        }


        /*
        dataBase db = new dataBase(this);       //디비 변수
        sqlDB = db.getReadableDatabase();
        */

        //로그인 버튼
        login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //go to SelectStore
                Intent intent = new Intent(getApplicationContext(), selectStore.class);

                //go to Base
                //Intent intent = new Intent(getApplicationContext(), Base.class);
                startActivity(intent);
                //Intent intent = new Intent(getApplicationContext(), selectStore.class);
                /*
                class_Login login = new class_Login();
                String ID = login_email.getText().toString();
                int key = login.login(ID, login_pwd.getText().toString());
                switch(key){
                    case 0:
                        Toast.makeText(getApplicationContext(),"" + ID + " 님 환영합니다!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Base.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "비밀번호를 다시 확인해 주세요.", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "존재하지 않는 계정입니다.", Toast.LENGTH_SHORT).show();
                        break;
                }
                */
            }
        });

        //신규 계정
        login_newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), newAccount.class);
                startActivity(intent);
            }
        });

        //비밀번호 찾기
        login_forgetPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), forgetPW.class));
            }
        });
    }
}
