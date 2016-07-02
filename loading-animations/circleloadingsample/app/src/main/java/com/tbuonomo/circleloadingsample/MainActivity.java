package com.tbuonomo.circleloadingsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidUtils.hideStatusBar(this);
        setContentView(R.layout.activity_main);

    }
}
