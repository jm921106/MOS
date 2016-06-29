package com.example.sejune.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by SE JUNE on 2016-06-27.
 */
public class newSchedule extends Activity {
    ImageView newSchedule_back;
    EditText newSchedule_text;
    Button newSchedule_insert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mos_new_schedule);

        newSchedule_back = (ImageView)findViewById(R.id.newSchedule_back);
        newSchedule_text = (EditText)findViewById(R.id.newSchedule_text);
        newSchedule_insert = (Button)findViewById(R.id.newSchedule_insert);

        newSchedule_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        newSchedule_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(newSchedule_text.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "스케쥴을 입력해 주세요", Toast.LENGTH_SHORT).show();
                }else{
                    finish();
                }
            }
        });


    }
}
