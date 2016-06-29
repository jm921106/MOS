package com.example.sejune.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by SE JUNE on 2016-06-27.
 */
public class forgetPW extends Activity {
    ImageView forgetPW_home;
    EditText forgetPW_ID, forgetPW_PW, newpw_pwd, newpw_pwd2;
    Button forgetPW_cancel, forgetPW_search;
    View dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mos_forgertpw);
        forgetPW_home = (ImageView)findViewById(R.id.forgetPW_home);
        forgetPW_ID = (EditText)findViewById(R.id.forgetPW_ID);
        forgetPW_PW = (EditText)findViewById(R.id.forgetPW_PW);
        forgetPW_cancel = (Button)findViewById(R.id.forgetPW_cancel);
        forgetPW_search = (Button)findViewById(R.id.forgetPW_search);

        forgetPW_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        forgetPW_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!forgetPW_ID.getText().toString().equals("") && !forgetPW_PW.getText().toString().equals("")) {
                    dialogView = (View)View.inflate(forgetPW.this, R.layout.mos_dialog_newpw, null);
                    AlertDialog.Builder dlg = new AlertDialog.Builder(forgetPW.this);
                    dlg.setTitle("비밀번호 변경");
                    dlg.setView(dialogView);
                    dlg.setPositiveButton("변경", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            newpw_pwd = (EditText)dialogView.findViewById(R.id.newpw_pwd);
                            newpw_pwd2 = (EditText)dialogView.findViewById(R.id.newpw_pwd2);

                            if(!newpw_pwd.getText().toString().equals("") && !newpw_pwd2.getText().toString().equals("")){
                                if(!newpw_pwd.getText().toString().equals(newpw_pwd2.getText().toString())){
                                    Toast.makeText(getApplicationContext(), "비밀번호가 같지 않습니다. 다시 한번 입력해 주세요", Toast.LENGTH_SHORT).show();
                                }else{
                                    finish();
                                }
                            }else if (newpw_pwd.getText().toString().equals("")){
                                Toast.makeText(getApplicationContext(), "변경할 비밀번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
                            }else if(newpw_pwd2.getText().toString().equals("")){
                                Toast.makeText(getApplicationContext(), "비밀번호를 다시 한번 입력해 주세요", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dlg.setNegativeButton("취소", null);
                    dlg.show();
                }else{
                    Toast.makeText(getApplicationContext(), "아이디와 이름을 입력해주세요. ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        forgetPW_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(forgetPW_ID.getText().toString().equals("") && forgetPW_PW.getText().toString().equals("")){
                    finish();
                }else{
                    forgetPW_ID.setText("");
                    forgetPW_PW.setText("");
                }
            }
        });
    }
}
