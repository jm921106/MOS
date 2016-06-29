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

/*
    //아이디 생성 가능 여부 체킹
    public boolean id_check(String id, String[] idList){
        if(id.equals("admin")){
            System.out.println("admin은 사용할 수 없는 아이디 입니다.");
            return false;
        }
        else if(id.length() < 5 || id.length() > 20){	//범위 체크
            System.out.println("5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.");
            System.out.println("\t<<길이가 맞지 않음>>");
            return false;
        }
        else
        {
            char[] ch = id.toCharArray();
            List<String> list = new ArrayList<>(Arrays.asList(idList));
            Collections.sort(list);

            //대문자 체크
            for(int i = 0; i < ch.length; i++){
                if(ch[i] >= 65 && ch[i] <= 90){
                    System.out.println("5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.");
                    System.out.println("\t<<소문자만 사용가능>>");
                    return false;
                }
            }

            //특수문자 체크.
            for(int i = 0; i < special.length;i++){
                if(Collections.binarySearch(list, special[i]) >= 0){
                    System.out.println("5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.");
                    System.out.println("\t<<특수문자 사용불가>>");
                    return false;
                }
            }
            //조건 만족
            return true;
        }
    }

    //비밀번호 생성 가능 여부 체킹
    public boolean pw_check(String pw, String[] pwList){
        boolean[] key = {false, false, false, false};
        //소문자, 대문자, 숫자, 특수문자 체킹

        //길이 체크
        if(pw.length() < 6 || pw.length() > 16){	//범위 체크
            System.out.println("6~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
            System.out.println("\t<<길이가 맞지 않음>>");
            return false;
        }
        else
        {
            char[] ch = pw.toCharArray();
            List<String> list = new ArrayList<>(Arrays.asList(pwList));
            Collections.sort(list);

            //대문자 체크 & 특수문자 체크.
            for(int i = 0; i < ch.length; i++){
                //소문자
                if(ch[i] >= 97 && ch[i] <= 122)	key[0] = true;
                    //대문자
                else if(ch[i] >= 65 && ch[i] <= 90)	key[1] = true;
                    //숫자
                else if(ch[i] >= 48 && ch[i] <= 57)	key[2] = true;
            }

            for(int i = 0; i < special.length;i++){
                //특수문자
                if(Collections.binarySearch(list, special[i]) >= 0) key[3] = true;
            }

            if(key[0] && key[1] && key[2] && key[3])	return true;
            else if(!key[0]){
                System.out.println("6~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
                System.out.println("\t<<소문자 없음>>");
            }else if(!key[1]){
                System.out.println("6~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
                System.out.println("\t<<대문자 없음>>");
            }else if(!key[2]){
                System.out.println("6~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
                System.out.println("\t<<숫자 없음>>");
            }else if(!key[3]){
                System.out.println("6~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
                System.out.println("\t<<특수문자 없음>>");
            }
            return false;
        }
    }

    //사용자 중복 체크
    public boolean per_check(String name, String idNum){
        for(int i = 1; i < super.accCount; i++){
            if(name.equals(acc[i].getName()) && idNum.equals(acc[i].getIdNum()))
            {
                System.out.println("Already existed!!");
                return false;
            }
        }
        return true;
    }

    //아이디 중복 체크
    public boolean dup_id(String id){
        if(id.equals("admin")){
            System.out.println("admin은 사용할 수 없는 아이디 입니다.");
            return false;
        }
        else
        {
            for(int i = 1; i < super.accCount;i++){
                if(id.equals(acc[i].getID())){
                    System.out.println("Duplicated!!");
                    return false;
                }
            }
        }

        return true;
    }

    //유효계좌 체크
    public boolean acc_Check(String ID, String name){
        if(ID.equals("admin") && name.equals("admin")){
            System.out.println("NOT PERMITTED!!");
            return false;
        }else{
            for(int i = 1; i < super.accCount; i ++)
            {
                if(ID.equals(acc[i].getID()) && name.equals(acc[i].getName())) // 유효함.
                {
                    index.index_send = i;
                    return true;
                }
            }
            return false;
        }
    }
    */
}
