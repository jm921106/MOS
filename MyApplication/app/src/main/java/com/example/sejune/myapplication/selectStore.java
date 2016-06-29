package com.example.sejune.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by SE JUNE on 2016-06-27.
 */
public class selectStore extends Activity {
    Button selectStore_logOut;
    ImageView selectStore_add;
    ListView selectStore_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mos_select_store);

        selectStore_logOut = (Button)findViewById(R.id.selectStore_logOut);
        selectStore_add = (ImageView)findViewById(R.id.selectStore_add);
        selectStore_list = (ListView)findViewById(R.id.selectStore_list);

        selectStore_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        selectStore_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), newStore.class);
                startActivity(intent);
            }
        });

        selectStore_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), Base.class);
                startActivity(intent);
            }
        });
    }
}
