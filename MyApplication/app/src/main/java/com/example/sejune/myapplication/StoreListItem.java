package com.example.sejune.myapplication;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by superMoon on 2016-07-01.
 */
public class StoreListItem {
    private Drawable list_imgStore;
    private String list_txtStoreName;
    private String list_txtStoreTel;
    private String list_txtStoreAddr;

    public void setIcon (Drawable icon) {
        list_imgStore = icon;
    }

    public void setName (String title) {
        list_txtStoreName = title;
    }

    public void setTel (String tel) {
        list_txtStoreTel = tel;
    }

    public void setAddr (String addr) {
        list_txtStoreAddr = addr;
    }

    public Drawable getIcon () {
        return this.list_imgStore;
    }

    public String getName() {
        return this.list_txtStoreName;
    }

    public String getTel() {
        return this.list_txtStoreTel;
    }

    public String getAddr() {
        return this.list_txtStoreAddr;
    }
}
