package com.example.sejune.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by SE JUNE on 2016-06-28.
 */
public class BaseActivity extends Activity {
    String[] menues = {"Store INFO", "SCHEDULE", "MESSAGE", "NOTICE"};
    ListView lvNavList;
    FrameLayout flContainer;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle dtToggle;

    FragmentManager manager;  //Fragment를 관리하는 클래스의 참조변수
    FragmentTransaction tran;  //실제로 Fragment를 추가/삭제/재배치 하는 클래스의 참조변수
    Fragment frag1, frag2, frag3; //3개의 Fragment 참조변수
    ImageView btn_Menu;

    Bundle args = new Bundle();
    String storeName, email, name;
    int storeID, userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mos_base);

        //리스트에 대한 부분.
        drawerLayout = (DrawerLayout) findViewById(R.id.base_drawer);
        dtToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(dtToggle);
        dtToggle.setHomeAsUpIndicator(R.drawable.ic_drawer);
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        email = intent.getStringExtra("EMAIL");
        userType = intent.getIntExtra("UserType", 2);
        storeName = intent.getStringExtra("storeNAME");
        storeID = intent.getIntExtra("StoreID", -1);
        name = intent.getStringExtra("NAME");

        args.putString("EMAIL", email);
        args.putInt("UserType", userType);
        args.putInt("StoreID", storeID);
        args.putString("StoreName", storeName);
        args.putString("NAME", name);

        Fragment fragment = new storeInfo();
        manager = (FragmentManager) getFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(R.id.base_container, fragment);
        fragmentTransaction.commit();

        frag1 = new Fragment_1();
        frag1.setArguments(args);
        frag2 = new Fragment_2();
        frag2.setArguments(args);
        frag3 = new Fragment_3();
        frag3.setArguments(args);


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

            fr.setArguments(args);
            android.app.FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.base_container, fr);
            fragmentTransaction.commit();
            drawerLayout.closeDrawer(lvNavList);
        }
    }

    //Activity가 보여주는 activity_main.xml 파일에 태그문으로
    //onClick속성이 설정된 View를 클릭했을 때 자동으로 호출되는 콜백 메소드
    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.btn01:

                //fragment_analog.xml을 보여주는 AnalogFragment 객체로 재배치(replace)
                tran = manager.beginTransaction();
                tran.replace(R.id.container, frag1);
                tran.commit();
                break;

            case R.id.btn02:
                //fragment_digital.xml을 보여주는 DigitalFragment 객체로 재배치(replace)
                tran = manager.beginTransaction();
                tran.replace(R.id.container, frag2);
                tran.commit();
                break;

            case R.id.btn03:

                //fragment_calendar.xml을 보여주는 CalendarFragment 객체로 재배치(replace)
                tran = manager.beginTransaction();
                tran.replace(R.id.container, frag3);
                tran.commit();
                break;
            case R.id.btnMenu:
                drawerLayout.openDrawer(lvNavList);
                break;
            case R.id.schedule_menu:
                drawerLayout.openDrawer(lvNavList);
                break;
            case R.id.message_menu:
                drawerLayout.openDrawer(lvNavList);
                break;
            case R.id.notice_menu:
                drawerLayout.openDrawer(lvNavList);
                ;
                break;
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        dtToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dtToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (dtToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
