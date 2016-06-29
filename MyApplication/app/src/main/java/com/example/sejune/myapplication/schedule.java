package com.example.sejune.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by SE JUNE on 2016-06-27.
 */

public class schedule extends Fragment {

    Button testBtn;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mos_schedule, container,false);

        testBtn = (Button) view.findViewById(R.id.testBtn);

        return view;
    }
}
