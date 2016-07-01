package com.example.sejune.myapplication;

        import android.app.Fragment;
        import android.content.Intent;
        import android.database.Cursor;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.Toast;

        import java.util.ArrayList;

/**
 * Created by SE JUNE on 2016-06-28.
 *
 * 출력할때 거꾸로 하기.
 */
public class message extends Fragment {
    dataBase dbManager;
    ArrayList<String> myMessageList;
    ArrayAdapter<String> adapter;

    String userEmail;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mos_message, container, false);
//        final dataBase dbManager = new dataBase(view.getContext());
        dbManager = new dataBase(view.getContext());

        ListView listMessage = (ListView) view.findViewById(R.id.listMessage);
        Button btnMessageAdd = (Button) view.findViewById(R.id.btnMessageAdd);
        Bundle args = getArguments();
        userEmail = args.getString("EMAIL");
        final String storeName = args.getString("StoreName");
        final int userType = args.getInt("UserType");
        final int storeID = args.getInt("StoreID");

        //ArrayList<String> myMessageList = new ArrayList<String>();
        myMessageList = new ArrayList<String>();

        Cursor cursor = dbManager.select("SELECT * FROM messageTBL WHERE toEMAIL = '"+ userEmail +"';");
        while(cursor.moveToNext()) {
            String str = "from : " + cursor.getString(2) + "\n contents : " + cursor.getString(4);
            myMessageList.add(str);
        }

        adapter = new ArrayAdapter<String>(
                view.getContext(),
                android.R.layout.simple_list_item_1, myMessageList);
        listMessage.setAdapter(adapter);

//        listMessage.notifyAll();
//        adapter.notifyAll();

        //stMessage.setLongClickable(new On);

        btnMessageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), newMessage.class);

                intent.putExtra("EMAIL", userEmail);
                intent.putExtra("UserType", userType);                                                     // 이름
                intent.putExtra("StoreName", storeName);
                intent.putExtra("StoreID", storeID);

                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        myMessageList.clear();
        Cursor cursor = dbManager.select("SELECT * FROM messageTBL WHERE toEMAIL = '"+ userEmail +"';");
        while(cursor.moveToNext()) {
            String str = "from : " + cursor.getString(2) + "\n contents : " + cursor.getString(4);
            myMessageList.add(str);
        }
        adapter.notifyDataSetChanged();
    }
}
