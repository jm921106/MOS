package com.example.sejune.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by SE JUNE on 2016-06-27.
 */
public class notice extends Fragment {
    ImageView notice_add;
    ListView msgList;
    ArrayList<String> titleList;
    ArrayAdapter<String> adapter;
    dataBase dbManager;
    int storeID;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mos_notice, container, false);
        notice_add = (ImageView)view.findViewById(R.id.notice_add);
        msgList = (ListView)view.findViewById(R.id.msgList);

        dbManager = new dataBase(view.getContext());

        Bundle bundle = getArguments();
        int type = bundle.getInt("UserType");
        final String email = bundle.getString("EMAIL");
        storeID = bundle.getInt("StoreID");
        final String name = bundle.getString("NAME");

        java.util.Calendar cal = java.util.Calendar.getInstance();
        final String date = cal.get(java.util.Calendar.YEAR) + "_" + (cal.get(java.util.Calendar.MONTH) + 1) + "_" + cal.get(java.util.Calendar.DAY_OF_MONTH);

        if(type == 2){
            notice_add.setVisibility(View.GONE);
        }
        //공지 띄워주기
        titleList = new ArrayList<String>();
        Cursor cursor = dbManager.select("SELECT * FROM noticeTBL WHERE storeINDEX = " + storeID + ";");
        while (cursor.moveToNext()) {
            titleList.add(cursor.getString(3) + " >>  " +cursor.getString(4));
        }

        adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, titleList);
        msgList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        notice_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), newNotice.class);
                //공지작성에 필요한 데이터
                intent.putExtra("noticeDATE", date);
                intent.putExtra("noticeFROM", email);
                intent.putExtra("StoreID", storeID);
                intent.putExtra("NAME", name);
                startActivity(intent);
            }
        });

        //공지 누르면 크게 보이기
        msgList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        titleList.clear();
        Cursor cursor = dbManager.select("SELECT * FROM noticeTBL WHERE storeINDEX = " + storeID + ";");
        while (cursor.moveToNext()) {
            titleList.add(cursor.getString(3) + " >>  " +cursor.getString(4));
        }
        adapter.notifyDataSetChanged();
    }
}
