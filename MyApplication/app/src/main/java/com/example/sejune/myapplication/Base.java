package com.example.sejune.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

/**
 * Created by SE JUNE on 2016-06-28.
 */
public class Base extends Activity {
    String[] menues = {"Store INFO", "SCHEDULE", "MESSAGE", "NOTICE"};
    ListView lvNavList;
    FrameLayout flContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mos_base);

        lvNavList = (ListView) findViewById(R.id.base_nav_list);
        flContainer = (FrameLayout) findViewById(R.id.base_container);

        lvNavList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menues));
        lvNavList.setOnItemClickListener(new DrawerItemClickListener());
    }

    public class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Fragment fr = null;

            if (i == 0) {                   // 가게정보
                fr = new storeInfo();
            } else if (i == 1) {        // 스케쥴
                fr = new schedule();
            } else if (i == 2) {        // 메세지
                fr = new message();
            } else if (i == 3) {        // 공지
               fr = new notice();
            }

            android.app.FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.base_container, fr);
            fragmentTransaction.commit();
        }
    }
}
