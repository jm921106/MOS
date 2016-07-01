package com.example.sejune.myapplication;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//프래그먼트에서 listVIew 사용하는 법
//View view = inflater.inflate(R.layout.fragment1, null) ;
//ArrayAdapter Adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, LIST_MENU) ;

public class Fragment_2 extends Fragment {

    ListView memeberList;
    ArrayList<String> staffEmail;
    ArrayList<String> staffName;
    ArrayAdapter<String> adapter;



    dataBase dbManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.mos_storeinfo_frag_2, container, false);
        dbManager = new dataBase(view.getContext());

        memeberList = (ListView) view.findViewById(R.id.memeberList);

        //여기에 있어야함 전역변수 아님
        Bundle args = getArguments();
        String userEmail = args.getString("EMAIL");
        int userType = args.getInt("UserType");
        int storeID = args.getInt("StoreID");

        staffEmail = new ArrayList<String>();

        Cursor cursor = dbManager.select("SELECT * FROM staffTBL WHERE storeINDEX = " + storeID + ";");
        while (cursor.moveToNext()) {
            staffEmail.add(cursor.getString(2));
        }

        adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1, staffEmail);

        memeberList.setAdapter(adapter);


        //생성된 View 객체를 리턴
        return view;
    }
}
