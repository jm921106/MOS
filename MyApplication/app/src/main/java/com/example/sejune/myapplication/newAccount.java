package com.example.sejune.myapplication;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by SE JUNE on 2016-06-27.
 */
public class newAccount extends Activity {
    ImageView newAccount_home;
    RadioButton newAccount_user, newAccount_admin;
    EditText newAccount_id, newAccount_pw, newAccount_name;
    Button newAccount_confirm, newAccount_cancel;

    //DB
    SQLiteDatabase sqlDB;

    //아이디와 비밀번호 생성 가능 여부 태그
    Boolean tags[] = {false, false};

    //문자 체크용
    final String[] special = {"!", "@", "#", "$", "%", "=", "+", "*"};
    boolean key1 = false, key2 = false;		//중복 체크용 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mos_new_account);

        newAccount_home = (ImageView)findViewById(R.id.newAccount_home);           // 홈 버튼

        newAccount_user = (RadioButton)findViewById(R.id.newAccount_user);              // 유저 타입 ( 2 )
        newAccount_admin = (RadioButton)findViewById(R.id.newAccount_admin);     // 관리자 타입 ( 0 )
        newAccount_id = (EditText)findViewById(R.id.newAccount_id);                              // 아이디
        newAccount_pw = (EditText)findViewById(R.id.newAccount_pw);                          // 비밀번호
        newAccount_name = (EditText)findViewById(R.id.newAccount_name);                // 이름

        newAccount_confirm = (Button)findViewById(R.id.newAccount_confirm);           // 확인 버튼
        newAccount_cancel = (Button)findViewById(R.id.newAccount_cancel);              // 취소

        //홈 버튼 클릭 시에
        newAccount_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //ID 체킹
        newAccount_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!view.isFocused() && !newAccount_id.getText().toString().equals("")){
                    //ID 및 비밀번호 가능여부 체킹용.
                    key1 = id_check(newAccount_id.getText().toString(), convert(newAccount_id.getText().toString()));
                    //아이디 생성 가능 여부 확인.
                    if(key1) {   //가능한 아이디일때
                        key2 = dup_id(newAccount_id.getText().toString());   //중복 체크
                        if(key2)    tags[0] = true; //아이디 중복 체크 + 생성 가능 여부 체크 OK일때
                    }

                    if(tags[0] && tags[1])  newAccount_confirm.setEnabled(true);
                }
            }
        });

        //비밀번호 체킹
        newAccount_pw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!view.isFocused() && !newAccount_pw.getText().toString().equals("")){
                    //비밀번호 사용 가능 여부 체크
                    key1 = pw_check(newAccount_pw.getText().toString(), convert(newAccount_pw.getText().toString()));

                    if(key1){
                        newAccount_pw.setBackgroundColor(Color.WHITE);
                        tags[1] = true;
                    }
                    else    newAccount_pw.setBackgroundColor(Color.RED);

                    //조건 통과시 계정 생성 가능.
                    if(tags[0] && tags[1])  newAccount_confirm.setEnabled(true);
                }
            }
        });

        //확인 버튼 클릭 시
        newAccount_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((newAccount_admin.isChecked() == true || newAccount_user.isChecked() == true) &&
                        !newAccount_id.getText().toString().equals("") &&
                        !newAccount_pw.getText().toString().equals("") &&
                        !newAccount_name.getText().toString().equals("")) {

                    //Type값 셋팅.
                    int newAccount_type;
                    if(newAccount_admin.isChecked())    newAccount_type = 0;
                    else    newAccount_type = 2;

                    // 실제 계정이 추가 되는 부분.
                    dataBase db = new dataBase(getApplicationContext());       //디비 변수
                    sqlDB = db.getWritableDatabase();
                    String sql = "INSERT INTO accountTBL VALUES ( '" +
                            newAccount_id.getText().toString() + "', '" +
                            newAccount_pw.getText().toString() + "', '" +
                            newAccount_name.getText().toString() + "', " +
                            newAccount_type + ");";
                    db.insert(sql);

                    Toast.makeText(getApplicationContext(), "계정이 생성되었습니다.", Toast.LENGTH_SHORT).show();

                    // 생성 후 초기화.
                    newAccount_user.setChecked(false);
                    newAccount_admin.setChecked(false);
                    newAccount_id.setText("");
                    newAccount_pw.setText("");
                    newAccount_name.setText("");

                    //화면 변경
                    finish();

                }else if(!newAccount_admin.isChecked() && !newAccount_user.isChecked()){
                    Toast.makeText(getApplicationContext(), "유저 타입을 선택해 주세요.", Toast.LENGTH_SHORT).show();
                }else if(newAccount_id.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "ID를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }else if(newAccount_pw.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }else if(newAccount_name.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //취소 버튼 클릭 시
        newAccount_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((newAccount_admin.isChecked() == true || newAccount_user.isChecked() == true) ||
                        !newAccount_id.getText().toString().equals("") ||
                        !newAccount_pw.getText().toString().equals("") ||
                        !newAccount_name.getText().toString().equals("")) {
                    newAccount_admin.setChecked(false);
                    newAccount_user.setChecked(false);
                    newAccount_id.setText("");
                    newAccount_pw.setText("");
                    newAccount_name.setText("");
                }else{
                    finish();
                }
            }
        });
    }

    //아이디 생성 가능 여부 체킹
    public boolean id_check(String id, String[] idList){
         if(id.length() < 5 || id.length() > 20){	//범위 체크
            Toast.makeText(getApplicationContext(), "5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.", Toast.LENGTH_SHORT).show();
             newAccount_id.setBackgroundColor(Color.RED);
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
                    Toast.makeText(getApplicationContext(), "<< 소문자와 숫자만 사용 가능 >>", Toast.LENGTH_SHORT).show();
                    newAccount_id.setBackgroundColor(Color.RED);
                    return false;
                }
            }

            //특수문자 체크.
            for(int i = 0; i < special.length;i++){
                if(Collections.binarySearch(list, special[i]) >= 0){
                    Toast.makeText(getApplicationContext(), "<< 특수기호(_),(-)만 사용 가능 >>", Toast.LENGTH_SHORT).show();
                    newAccount_id.setBackgroundColor(Color.RED);
                    return false;
                }
            }

            //조건 만족
            newAccount_id.setBackgroundColor(Color.WHITE);
            return true;
        }
    }

    //비밀번호 생성 가능 여부 체킹
    public boolean pw_check(String pw, String[] pwList){
        boolean[] key = {false, false, false, false};
        //소문자, 대문자, 숫자, 특수문자 체킹

        //길이 체크
        if(pw.length() < 6 || pw.length() > 16){	//범위 체크
            Toast.makeText(getApplicationContext(), "6~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.", Toast.LENGTH_SHORT).show();
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

            if(key[0] && key[1] && key[2] && key[3]){
                Toast.makeText(getApplicationContext(), "사용가능한 비밀번호 입니다.", Toast.LENGTH_SHORT).show();
                return true;
            }
            else if(!key[0]){
                Toast.makeText(getApplicationContext(), "<<소문자 없음>>", Toast.LENGTH_SHORT).show();
            }else if(!key[1]){
                Toast.makeText(getApplicationContext(), "<<대문자 없음>>", Toast.LENGTH_SHORT).show();
            }else if(!key[2]){
                Toast.makeText(getApplicationContext(), "<<숫자 없음>>", Toast.LENGTH_SHORT).show();
            }else if(!key[3]){
                Toast.makeText(getApplicationContext(), "<<특수문자 없음>>", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    }

    //아이디 중복 체크
    public boolean dup_id(String id){
        dataBase db = new dataBase(getApplicationContext());       //디비 변수
        sqlDB = db.getWritableDatabase();
        String sql = "SELECT * FROM accountTBL WHERE EMAIL ='" + id + "';";
        Cursor cursor = db.select(sql);
        int count = 0;
        while(cursor.moveToNext()){
            count++;
        }

        if(count != 0){
            Toast.makeText(getApplicationContext(), "이미 존재하는 ID입니다.", Toast.LENGTH_SHORT).show();
            newAccount_id.setBackgroundColor(Color.RED);
            return false;
        }else{
            Toast.makeText(getApplicationContext(), "사용가능한 ID 입니다.", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public String[] convert(String str){
        char[] ch = str.toCharArray();
        String[] strArr = new String[ch.length];

        //문자 하나 하나씩 변환.
        for(int i = 0; i< ch.length;i++)	strArr[i] = String.valueOf(ch[i]);

        return strArr;
    }

}
