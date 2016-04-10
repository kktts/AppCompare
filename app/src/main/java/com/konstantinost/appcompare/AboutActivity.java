package com.konstantinost.appcompare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Konstantinos on 13/12/2015.
 */
public class AboutActivity extends AppCompatActivity {

    private final String about="This project has educational and research purposes.This application is collecting data from multiple individual mobile devices and make a comparison from the applications already installed on each device\n" +
            "and compare them with a certain unique device. Therefore, the outcome/result that arise is showing the similarity between the unique device and the applications from the devices.";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity_layout);

        TextView aboutText=(TextView)findViewById(R.id.about_textview);
        aboutText.setText(about);

    }
}
