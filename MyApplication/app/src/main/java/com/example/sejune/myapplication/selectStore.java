package com.example.sejune.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by SE JUNE on 2016-06-27.
 */


public class selectStore extends Activity {

    Button selectStore_logOut;
    ImageView selectStore_add;
    ListView selectStore_list;
    StoreListAdapter adapter = new StoreListAdapter();

    final dataBase dbManager = new dataBase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mos_select_store);

        //로그인에서 데이터 전달
        Intent intent = getIntent();
        final String userEmail = intent.getStringExtra("EMAIL");
        final int userType = intent.getIntExtra("TYPE", 2);
        final String name = intent.getStringExtra("NAME");

        selectStore_logOut = (Button) findViewById(R.id.selectStore_logOut);
        selectStore_add = (ImageView) findViewById(R.id.selectStore_add);
        selectStore_list = (ListView) findViewById(R.id.selectStore_list);
        selectStore_list.setAdapter(adapter);

//        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.photo),
//                "exName", "exTel", "exAddr");

        Cursor cursor = dbManager.select("SELECT * FROM staffTBL WHERE staffEmail = '" + userEmail + "';");
        final ArrayList<Integer> storeIDList = new ArrayList<>();
        while (cursor.moveToNext()) {
            storeIDList.add(cursor.getInt(1));
        }

        cursor.close();

        final ArrayList<String> storeNameList = new ArrayList<>();
        ArrayList<String> storeTelList = new ArrayList<>();
        ArrayList<String> storeAddrList = new ArrayList<>();
        String test = "";

        for (int i = 0; i < storeIDList.size(); i++) {
            cursor = dbManager.select("SELECT * FROM storeTBL WHERE storeINDEX = '" + storeIDList.get(i) + "';");

            while (cursor.moveToNext()) {
                storeNameList.add(cursor.getString(1));
                storeTelList.add(cursor.getString(3));
                storeAddrList.add(cursor.getString(4));
                adapter.addItem(ContextCompat.getDrawable(this, R.drawable.photo),
                        cursor.getString(1), cursor.getString(3), cursor.getString(4));
            }
            cursor.close();
        }


        selectStore_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int storeID = storeIDList.get(i);
                String storeName = storeNameList.get(i);
                Intent intent = new Intent(getApplicationContext(), BaseActivity.class);
                intent.putExtra("EMAIL", userEmail);
                intent.putExtra("UserType", userType);
                intent.putExtra("StoreID", storeID);
                intent.putExtra("storeNAME", storeName);
                intent.putExtra("NAME", name);
                startActivity(intent);
            }
        });

        /*
        selectStore_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //클릭한 상점 이름를 가지고 옴
                final String name = storeList.get(i).toString();
                AlertDialog.Builder dlg = new AlertDialog.Builder(selectStore.this);
                dlg.setTitle("Delete Store");
                dlg.setMessage(name + "을 제거하시겠습니까 ? ");
                dlg.setPositiveButton("제거", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbManager.delete("DELETE FROM storeTBL WHERE storeNAME='" + name + "', storeADMIN = '" + "");
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();

                return false;
            }
        });
        */

        selectStore_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), newStore.class);
                intent.putExtra("EMAIL", userEmail);
                startActivity(intent);
            }
        });

        selectStore_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
