package com.example.sejune.myapplication;

import android.app.Activity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mos_new_account);
        newAccount_home = (ImageView)findViewById(R.id.newAccount_home);
        newAccount_user = (RadioButton)findViewById(R.id.newAccount_user);
        newAccount_admin = (RadioButton)findViewById(R.id.newAccount_admin);
        newAccount_id = (EditText)findViewById(R.id.newAccount_id);
        newAccount_pw = (EditText)findViewById(R.id.newAccount_pw);
        newAccount_name = (EditText)findViewById(R.id.newAccount_name);
        newAccount_confirm = (Button)findViewById(R.id.newAccount_confirm);
        newAccount_cancel = (Button)findViewById(R.id.newAccount_cancel);

        newAccount_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        newAccount_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((newAccount_admin.isChecked() == true || newAccount_user.isChecked() == true) &&
                        !newAccount_id.getText().toString().equals("") &&
                        !newAccount_pw.getText().toString().equals("") &&
                        !newAccount_name.getText().toString().equals("")) {
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
