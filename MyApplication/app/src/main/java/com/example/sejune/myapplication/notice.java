package com.example.sejune.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by SE JUNE on 2016-06-27.
 */
public class notice extends Fragment {
    ImageView notice_add;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mos_notice, container, false);
        notice_add = (ImageView)view.findViewById(R.id.notice_add);
        Bundle bundle = getArguments();
        int type = bundle.getInt("UserType");

        java.util.Calendar cal = java.util.Calendar.getInstance();
        String date = cal.get(java.util.Calendar.YEAR) + "_" + (cal.get(java.util.Calendar.MONTH) + 1) + "_" + cal.get(java.util.Calendar.DAY_OF_MONTH);
        Toast.makeText(view.getContext(), type + " " , Toast.LENGTH_SHORT).show();

        if(type == 2){
            notice_add.setVisibility(View.GONE);
        }

        notice_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), newNotice.class);
                //공지작성에 필요한 데이터

                startActivity(intent);
            }
        });


        return view;
    }
}
