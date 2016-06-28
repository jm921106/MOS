package com.example.sejune.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    //Login 변수
    EditText login_email, login_pwd;
    Button login_login;
    TextView login_newAccount, login_forgetPW;
    SQLiteDatabase sqlDB;

    //ID와 비밀번호.
    String id, pwd;

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

        /*
        dataBase db = new dataBase(this);       //디비 변수
        sqlDB = db.getReadableDatabase();
        */


        //로그인 버튼
        login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), selectStore.class);
                startActivity(intent);
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
