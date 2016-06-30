package com.example.sejune.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by SE JUNE on 2016-06-27.
 */

public class schedule extends Fragment {

    ImageView schedule_add;
    TextView schedule_notice;
    CalendarView schedule_calendar;
    ListView schedule_list;

    String email;
    String date;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mos_schedule, container,false);

        Bundle bundle = getArguments();
        email = bundle.getString("EMAIL");

        schedule_add = (ImageView)view.findViewById(R.id.schedule_add);
        schedule_notice = (TextView)view.findViewById(R.id.schedule_notice);
        schedule_calendar = (CalendarView)view.findViewById(R.id.schedule_calendar);
        schedule_list = (ListView)view.findViewById(R.id.schedule_list);

        schedule_calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                java.util.Calendar diaryCal = java.util.Calendar.getInstance();
                diaryCal.setTimeInMillis(schedule_calendar.getDate());
                //달력에 선택된 날짜 셋팅.
                date = Integer.toString(diaryCal.get(java.util.Calendar.YEAR)) + "_"
                        + Integer.toString(diaryCal.get(java.util.Calendar.MONTH) + 1) + "_"
                        + Integer.toString(diaryCal.get(java.util.Calendar.DATE));
            }
        });
        schedule_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), newSchedule.class);
                intent.putExtra("EMAIl", email);
                intent.putExtra("DATE", date);
                startActivity(intent);
            }
        });

        return view;
    }
}
