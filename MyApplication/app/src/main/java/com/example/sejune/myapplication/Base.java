package com.example.sejune.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by SE JUNE on 2016-06-28.
 */
public class Base extends Activity {
    String[] menues = {"Store INFO", "SCHEDULE", "MESSAGE", "NOTICE"};
    ListView lvNavList;
    FrameLayout flContainer;

    FragmentManager manager;  //Fragment를 관리하는 클래스의 참조변수
    FragmentTransaction tran;  //실제로 Fragment를 추가/삭제/재배치 하는 클래스의 참조변수
    Fragment frag1, frag2, frag3; //3개의 Fragment 참조변수

    String email;
    int userType, storeID;
    String storeName;
    Bundle args = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mos_base);

        Intent intent = getIntent();

        email = intent.getStringExtra("EMAIL");
        userType = intent.getIntExtra("UserType", 2);
        storeName = intent.getStringExtra("storeNAME");
        storeID = intent.getIntExtra("StoreID", -1);

        args.putString("EMAIL", email);
        args.putInt("UserType", userType);
        args.putInt("StoreID", storeID);

        manager = (FragmentManager) getFragmentManager();

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
            Bundle args = new Bundle();
            args.putString("EMAIL", email);
            args.putInt("UserType", userType);
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
        }
    }
}
