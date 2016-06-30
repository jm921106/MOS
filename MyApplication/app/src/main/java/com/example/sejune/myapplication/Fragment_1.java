package com.example.sejune.myapplication;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Fragment_1 extends Fragment {



    //Fragment의 lifecycle 메소드 중에서 Fragment가 보여줄 View를 설정하는 메소드
    //MainActivity.java 에서 onCreate() 메소드 안에 setContentView()하듯이
    //Fragment에서는 이 메소드에서 Fragment가 보여줄 View 객체를 생성해서 return 시켜줘야 함.

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // TODO Auto-generated method stub
        View view = null; //Fragment가 보여줄 View 객체를 참조할 참조변수

        Bundle args = getArguments();
        String userEmail = args.getString("EMAIL");
        int userType = args.getInt("UserType");
        int storeID = args.getInt("StoreID");

        //매개변수로 전달된 LayoutInflater객체를 통해 fragment_analog.xml 레이아웃 파일을
        //View 객체로 생성
        view = inflater.inflate(R.layout.mos_storeinfo_frag_1, null);

        final dataBase dbManager = new dataBase(view.getContext());

        String storeName ="";
        String storeAdmin = "";
        String storeTel = "";
        String storeAddr = "";

        Cursor cursor = dbManager.select("SELECT * FROM storeTBL WHERE storeINDEX = "+ storeID +";");
        while(cursor.moveToNext()) {
            storeName = cursor.getString(1);
            storeAdmin = cursor.getString(2);
            storeTel = cursor.getString(3);
            storeAddr = cursor.getString(4);
        }

        TextView txtStoreName = (TextView) view.findViewById(R.id.txtStoreName);
        TextView txtAdminName = (TextView) view.findViewById(R.id.txtAdminName);
        TextView txtStoreTel = (TextView) view.findViewById(R.id.txtStoreTel);
        TextView txtStoreAddr = (TextView) view.findViewById(R.id.txtStoreAddr);

        txtStoreName.setText(" store name : " + storeName);
        txtAdminName.setText(" store admin : " + storeAdmin);
        txtStoreTel.setText(" store tel : " + storeTel);
        txtStoreAddr.setText(" store address : " + storeAddr);

        //생성된 View 객체를 리턴
        return view;
    }
}
