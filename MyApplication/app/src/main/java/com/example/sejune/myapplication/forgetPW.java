package com.example.sejune.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by SE JUNE on 2016-06-27.
 */
public class forgetPW extends Activity {
    ImageView forgetPW_home;
    EditText forgetPW_ID, forgetPW_PW, newpw_pwd, newpw_pwd2;
    Button forgetPW_cancel, forgetPW_search, dailog_confirm, dailog_cancel;
    View dialogView;

    //DB
    SQLiteDatabase sqlDB;

    //문자 체크용
    final String[] special = {"!", "@", "#", "$", "%", "=", "+", "*"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mos_forgertpw);
        forgetPW_home = (ImageView) findViewById(R.id.forgetPW_home);
        forgetPW_ID = (EditText) findViewById(R.id.forgetPW_ID);
        forgetPW_PW = (EditText) findViewById(R.id.forgetPW_PW);
        forgetPW_cancel = (Button) findViewById(R.id.forgetPW_cancel);
        forgetPW_search = (Button) findViewById(R.id.forgetPW_search);

        forgetPW_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        forgetPW_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!forgetPW_ID.getText().toString().equals("") && !forgetPW_PW.getText().toString().equals("")) {

                    //비밀번호 찾기.
                    final dataBase db = new dataBase(getApplicationContext());       //디비 변수
                    sqlDB = db.getReadableDatabase();
                    final String sql = "SELECT * FROM accountTBL WHERE EMAIL ='" + forgetPW_ID.getText().toString() + "';";
                    Cursor cursor = db.select(sql);
                    while (cursor.moveToNext()) {
                        if (cursor.getString(2).equals(forgetPW_PW.getText().toString())) {
                            dialogView = (View) View.inflate(forgetPW.this, R.layout.mos_dialog_newpw, null);
                            AlertDialog.Builder dlg = new AlertDialog.Builder(forgetPW.this);
                            dlg.setTitle("Change PW");
                            dlg.setView(dialogView);
                            newpw_pwd = (EditText) dialogView.findViewById(R.id.newpw_pwd);
                            newpw_pwd2 = (EditText) dialogView.findViewById(R.id.newpw_pwd2);
                            dailog_confirm = (Button) dialogView.findViewById(R.id.dialog_confirm);
                            dailog_cancel = (Button) dialogView.findViewById(R.id.dialog_cancel);

                            //비밀번호 변경 시에
                            dailog_confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (!newpw_pwd.getText().toString().equals("") && !newpw_pwd2.getText().toString().equals("")) {
                                        if (!newpw_pwd.getText().toString().equals(newpw_pwd2.getText().toString())) {
                                            Toast.makeText(getApplicationContext(), "비밀번호가 같지 않습니다. 다시 한번 입력해 주세요", Toast.LENGTH_SHORT).show();
                                        } else {
                                            final newAccount pwCheck = new newAccount();
                                            boolean key1 = pw_check(newpw_pwd.getText().toString(), pwCheck.convert(newpw_pwd.getText().toString()));
                                            if (key1){
                                                //비밀번호 변경
                                                sqlDB = db.getWritableDatabase();
                                                String sql2 = "UPDATE accountTBL "
                                                        + " SET PW = '" + newpw_pwd.getText().toString() + "' "
                                                        + "WHERE EMAIL = '" + forgetPW_ID.getText().toString() + "';";
                                                db.update(sql2);

                                                Toast.makeText(getApplicationContext(), "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();

                                                // 초기화
                                                forgetPW_ID.setText("");
                                                forgetPW_PW.setText("");
                                                newpw_pwd.setText("");
                                                newpw_pwd2.setText("");

                                                finish();
                                            }else{
                                                //
                                            }
                                        }
                                    } else if (newpw_pwd.getText().toString().equals("")) {
                                        Toast.makeText(getApplicationContext(), "변경할 비밀번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
                                    } else if (newpw_pwd2.getText().toString().equals("")) {
                                        Toast.makeText(getApplicationContext(), "비밀번호를 다시 한번 입력해 주세요", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            //변경 취소 시에
                            dailog_cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (!newpw_pwd.getText().toString().equals("") || !newpw_pwd2.getText().toString().equals("")) {
                                        newpw_pwd.setText("");
                                        newpw_pwd2.setText("");
                                    } else finish();
                                }
                            });

                            dlg.show();
                        } else {
                            Toast.makeText(getApplicationContext(), "아이디와 이름이 매칭되지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (cursor.getCount() == 0)
                        Toast.makeText(getApplicationContext(), "존재하지 않는 계정 정보 입니다.", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "아이디와 이름을 입력해주세요. ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        forgetPW_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (forgetPW_ID.getText().toString().equals("") && forgetPW_PW.getText().toString().equals("")) {
                    finish();
                } else {
                    forgetPW_ID.setText("");
                    forgetPW_PW.setText("");
                }
            }
        });
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
}
