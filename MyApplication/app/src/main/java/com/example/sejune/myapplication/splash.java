package com.example.sejune.myapplication;

        import android.app.Activity;
        import android.os.Bundle;
        import android.os.Handler;

/**
 * Created by SE JUNE on 2016-06-27.
 */
public class splash extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mos_splash);

        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
         }, 500);
    }
}
