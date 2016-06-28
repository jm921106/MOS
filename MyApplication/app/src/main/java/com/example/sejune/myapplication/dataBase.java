package com.example.sejune.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SE JUNE on 2016-06-28.
 */
public class dataBase extends SQLiteOpenHelper {
    public dataBase(Context context) {
        super(context, "mosDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //계좌 테이블 생성
        String sql = "CREATE TABLE accountTBL ( ID CHAR(20) PRIMARY KEY, PW CHAR(20), NAME CHAR(20), TYPE INTEGER);";
        sqLiteDatabase.execSQL(sql);

        //가게 테이블 생성
        sql = "CREATE TABLE storeTBL ( NAME CHAR(20) PRIMARY KEY, ADMIN CHAR(20), PHONE INTEGER, ADDRESS CHAR(20) );";
        sqLiteDatabase.execSQL(sql);

        //스태프 생성
        sql = "CREATE TABLE staffTBL (INDEX INTEGER PRIMARY KEY,  ID CHAR(20), NAME CHAR(20));";
        sqLiteDatabase.execSQL(sql);

        //스케쥴 테이블 생성
        sql = "CREATE TABLE scheduleTBL (DATE CHAR(20) PRIMARY KEY, TEXT CHAR(20), FROM CHAR(20));";
        sqLiteDatabase.execSQL(sql);

        //메세지 테이블 생성
        sql = "CREATE TABLE messageTBL (INDEX INTEGER PRIMARY KEY,  FROM CHAR(20), TO CHAR(20), TEXT CHAR(20));";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //계정
        String sql = "DROP TABLE IF EXISTS accountTBL";
        sqLiteDatabase.execSQL(sql);

        //가계
        sql = "DROP TABLE IF EXISTS storeTBL";
        sqLiteDatabase.execSQL(sql);

        //가게 스태프
        sql = "DROP TABLE IF EXISTS staffTBL";
        sqLiteDatabase.execSQL(sql);

        //스케쥴
        sql = "DROP TABLE IF EXISTS scheduleTBL";
        sqLiteDatabase.execSQL(sql);

        //메세지
        sql = "DROP TABLE IF EXISTS messageTBL";
        sqLiteDatabase.execSQL(sql);

        //초기화
        onCreate(sqLiteDatabase);
    }
}