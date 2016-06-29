package com.example.sejune.myapplication;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

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

        newAccount_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!view.isFocused()){
                    //아이디 중복 체크 + 생성 가능 여부 체크 OK일때
                    tags[0] = true;

                    if(tags[0] && tags[1])  newAccount_confirm.setEnabled(true);
                }
            }
        });

        newAccount_pw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!view.isFocused()){
                    //비밀번호 사용 가능 여부 체크
                    tags[1] = true;

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
                    sqlDB.execSQL(sql);
                    sqlDB.close();
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
}
