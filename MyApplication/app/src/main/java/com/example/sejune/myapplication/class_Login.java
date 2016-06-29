package com.example.sejune.myapplication;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import java.util.InputMismatchException;

/**
 * Created by SE JUNE on 2016-06-29.
 */

public class class_Login extends Activity {

    //문자 체크용
    final String[] special = {"!", "@", "#", "$", "%", "=", "+", "*"};

    //DB
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //로그인
    public int  login(String ID, String pw){
        dataBase db = new dataBase(this);       //디비 변수
        sqlDB = db.getReadableDatabase();
        Cursor cursor;

        String sql = "SELECT * FROM accountTBL WHERE ID='" + ID +"';";
        cursor = sqlDB.rawQuery(sql, null);
        if (cursor != null && cursor.getCount() != 0) {  //올바른 비밀번호인지 체크
            while (cursor.moveToNext()) {
                if (cursor.getString(1).equals(pw)){
                    ID = cursor.getString(2);
                    //이름을 ID에 넣어준다.

                    cursor.close();
                    sqlDB.close();
                    //로그인 성공
                    return 0;
                    }
                else{
                    cursor.close();
                    sqlDB.close();

                    //비밀번호 틀림
                    return 1;
                }
            }
        }
        else{
            cursor.close();
            sqlDB.close();

            //계정 없음
            return 2;
        }

        return 2;
    }
}
