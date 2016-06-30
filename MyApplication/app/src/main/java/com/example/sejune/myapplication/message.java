package com.example.sejune.myapplication;

        import android.app.Fragment;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

/**
 * Created by SE JUNE on 2016-06-28.
 */
public class message extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = null; //Fragment가 보여줄 View 객체를 참조할 참조변수
        view = inflater.inflate(R.layout.mos_message, null);

        Bundle args = getArguments();
        String userEmail = args.getString("EMAIL");
        int userType = args.getInt("UserType");
        int storeID = args.getInt("StoreID");


        return inflater.inflate(R.layout.mos_message, container, false);
    }
}
