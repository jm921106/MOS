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
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.icu.util.Calendar.*;

/**
 * Created by SE JUNE on 2016-06-27.
 */

public class schedule extends Fragment {
    ImageView schedule_add;
    TextView schedule_notice;
    CalendarView schedule_calendar;
    ListView schedule_list;

    String date, name;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.mos_schedule, container,false);

        final dataBase dbManager = new dataBase(view.getContext());

        Bundle bundle = getArguments();
        final String email = bundle.getString("EMAIL");
        name = bundle.getString("NAME");

        schedule_add = (ImageView)view.findViewById(R.id.schedule_add);
        schedule_notice = (TextView)view.findViewById(R.id.schedule_notice);
        schedule_calendar = (CalendarView)view.findViewById(R.id.schedule_calendar);
        schedule_list = (ListView)view.findViewById(R.id.schedule_list);

        schedule_calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                java.util.Calendar Cal = java.util.Calendar.getInstance();
                Cal.setTimeInMillis(calendarView.getDate());
                //달력에 선택된 날짜 셋팅.
                date = i + "_" + (i1 + 1) + "_" + i2;
            }
        });

        schedule_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), newSchedule.class);
                intent.putExtra("EMAIL", email);
                intent.putExtra("DATE", date);
                intent.putExtra("NAME", name);
                startActivity(intent);
            }
        });

        //스케쥴 띄우기
        final ArrayList<String> noticeList = new ArrayList<String>();
        Cursor cursor = dbManager.select("SELECT * FROM scheduleTBL WHERE scheduleDATE = '" + date + "';");
        while (cursor.moveToNext()) {
            noticeList.add(cursor.getString(3) + " >>  " +cursor.getString(2));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, noticeList);
        schedule_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //스케쥴 크게 보기
        schedule_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        return view;
    }
}
