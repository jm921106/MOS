package com.example.sejune.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by SE JUNE on 2016-06-27.
 */


public class selectStore extends Activity {

    Button selectStore_logOut;
    ImageView selectStore_add;
    ListView selectStore_list;

    final dataBase dbManager = new dataBase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mos_select_store);

        selectStore_logOut = (Button) findViewById(R.id.selectStore_logOut);
        selectStore_add = (ImageView) findViewById(R.id.selectStore_add);
        selectStore_list = (ListView) findViewById(R.id.selectStore_list);

        //로그인에서 데이터 전달
        Intent intent = getIntent();
        final String userEmail = intent.getStringExtra("EMAIL");
        final int userType = intent.getIntExtra("TYPE", 2);
        final String name = intent.getStringExtra("NAME");

        selectStore_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final ArrayList<String> storeNameList = new ArrayList<String>();
        final ArrayList<Integer> storeIndexList = new ArrayList<Integer>();
        Cursor cursor = dbManager.select("SELECT * FROM staffTBL WHERE staffEMAIL = '" + userEmail + "';");

        while (cursor.moveToNext()) {
            storeNameList.add(cursor.getString(3));
            storeIndexList.add(cursor.getInt(0));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, storeNameList);
        selectStore_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        selectStore_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int storeID = storeIndexList.get(i);
                String storeName = storeNameList.get(i);
                Intent intent = new Intent(getApplicationContext(), Base.class);
                intent.putExtra("EMAIL", userEmail);
                intent.putExtra("UserType", userType);
                intent.putExtra("StoreID", storeID);
                intent.putExtra("storeNAME", storeName);

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

    }
}
