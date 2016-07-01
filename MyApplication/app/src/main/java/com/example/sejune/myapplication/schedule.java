package com.example.sejune.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.icu.util.Calendar.*;

/**
 * Created by SE JUNE on 2016-06-27.
 */

public class schedule extends Fragment {
    ImageView schedule_add, schedule_menu;
    TextView schedule_notice;
    CalendarView schedule_calendar;
    ListView schedule_list;
    String date, name, storeID;

    ArrayList<String> noticeList;
    dataBase dbManager;
    ArrayAdapter<String> adapter;

    private Handler mHandler;
    private NoticeThread mNoticeThread;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.mos_schedule, container, false);

        //노티스 보드 시작!
        mHandler = new Handler();
        mNoticeThread = new NoticeThread(true);
        mNoticeThread.start();

        //final dataBase dbManager = new dataBase(view.getContext());
        dbManager = new dataBase(view.getContext());

        Bundle bundle = getArguments();
        final String email = bundle.getString("EMAIL");
        name = bundle.getString("NAME");
        storeID = bundle.getString("StoreID");

        schedule_add = (ImageView) view.findViewById(R.id.schedule_add);
        schedule_menu = (ImageView) view.findViewById(R.id.schedule_menu);
        schedule_notice = (TextView) view.findViewById(R.id.schedule_notice);
        schedule_calendar = (CalendarView) view.findViewById(R.id.schedule_calendar);
        schedule_list = (ListView) view.findViewById(R.id.schedule_list);

        final java.util.Calendar Cal = java.util.Calendar.getInstance();
        date = Cal.get(java.util.Calendar.YEAR) + "_" + (Cal.get(java.util.Calendar.MONTH) + 1) + "_" + Cal.get(java.util.Calendar.DAY_OF_MONTH);

        //final ArrayList<String> noticeList = new ArrayList<String>();
        noticeList = new ArrayList<String>();

        //스케쥴 띄우기
        noticeList.clear();
        Cursor cursor = dbManager.select("SELECT * FROM scheduleTBL WHERE scheduleDATE = '" + date + "';");
        while (cursor.moveToNext()) {
            noticeList.add(cursor.getString(4) + " >>  " + cursor.getString(2));
        }

        //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, noticeList);
        adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, noticeList);

        schedule_list.setAdapter(adapter);

        schedule_calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                Cal.setTimeInMillis(calendarView.getDate());
                //달력에 선택된 날짜 셋팅.
                date = i + "_" + (i1 + 1) + "_" + i2;

                noticeList.clear();
                Cursor cursor = dbManager.select("SELECT * FROM scheduleTBL WHERE scheduleDATE='" + date + "';");
                while (cursor.moveToNext()) {
                    noticeList.add(cursor.getString(4) + " >>  " + cursor.getString(2));
                }
                adapter.notifyDataSetChanged();
            }
        });

        //스케쥴 추가 시
        schedule_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), newSchedule.class);
                intent.putExtra("EMAIL", email);
                intent.putExtra("DATE", date);
                intent.putExtra("NAME", name);
                mNoticeThread.stopThread();
                //Toast.makeText(view.getContext(), date, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        //스케쥴 크게 보기
        schedule_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        noticeList.clear();
        Cursor cursor = dbManager.select("SELECT * FROM scheduleTBL WHERE scheduleDATE = '" + date + "';");
        while (cursor.moveToNext()) {
            noticeList.add(cursor.getString(4) + " >>  " + cursor.getString(2));
        }
        adapter.notifyDataSetChanged();
    }

    class NoticeThread extends Thread {

        private int i = 0;
        private boolean isPlay = false;
        private String str;

        public NoticeThread(boolean isPlay) {
            this.isPlay = isPlay;
        }

        public void stopThread() {
            isPlay = !isPlay;
        }

        @Override
        public void run() {
            super.run();
            //schedule_notice.setText("공지가 없습니다!");
            while (isPlay) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Cursor cursor = dbManager.select("SELECT * FROM noticeTBL WHERE storeINDEX = '" + storeID + "';");
                        if(cursor.getCount() != 0){
                            while (cursor.moveToNext()) {
                                    str = cursor.getString(4);
                            }
                            schedule_notice.setText(str);
                        }
                    }
                });
            }
        }
    }
}
