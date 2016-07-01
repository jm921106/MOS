package com.example.sejune.myapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by superMoon on 2016-07-01.
 */
public class StoreListAdapter extends BaseAdapter{

    private ArrayList<StoreListItem> storeViewItemList = new ArrayList<StoreListItem>();

    // listViewAdapter 생성자
    public StoreListAdapter() {}

    //adapter에 사용되는 데이터의 개수를 리턴 : 필수 구현
    @Override
    public int getCount() {
        return storeViewItemList.size();
    }

    //Adapter 에 사용되는 데이터를 화면에 출력하는데 사용될 view를 리턴 : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        //listView_item layout을 inflate하여 converView 참조 획득
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.storelistview_item, parent, false);
        }

        //화면에 표시될 VIew (layouyt이 inflate된) 으로부터 위젯에 대한 참조 획득
        ImageView list_imgStore = (ImageView) convertView.findViewById(R.id.list_imgStore);
        TextView list_txtStoreName = (TextView) convertView.findViewById(R.id.list_txtStoreName);
        TextView list_txtStoreTel = (TextView) convertView.findViewById(R.id.list_txtStoreTel);
        TextView list_txtStoreAddr = (TextView) convertView.findViewById(R.id.list_txtStoreAddr);

        //Data set(listViewItemList)에서 position에 위치한 데이터 참조획득
        StoreListItem storeViewItem = storeViewItemList.get(position);

        list_imgStore.setImageDrawable(storeViewItem.getIcon());
        list_txtStoreName.setText(storeViewItem.getName());
        list_txtStoreTel.setText(storeViewItem.getTel());
        list_txtStoreAddr.setText(storeViewItem.getAddr());

        return convertView;
    }

    //지정한 위치에 있는 데이터와 관계된 아이템 (row)의 ID 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return storeViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Drawable icon, String name, String tel, String addr) {
        StoreListItem item = new StoreListItem();

        item.setIcon(icon);
        item.setName(name);
        item.setTel(tel);
        item.setAddr(addr);

        storeViewItemList.add(item);
    }
}
