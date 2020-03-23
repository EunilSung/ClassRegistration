package com.example.classregistration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    Button buttonList, buttonSchedule, buttonStat;
    LinearLayout layoutNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonList = findViewById(R.id.buttonList);
        buttonSchedule = findViewById(R.id.buttonSchedule);
        buttonStat = findViewById(R.id.buttonStat);
        layoutNotice = findViewById(R.id.layoutNotice);
    }

    public void onClickList(View v) {
       // layoutNotice.setVisibility(View.GONE);
        buttonList.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        buttonSchedule.setBackgroundColor(Color.TRANSPARENT);
        buttonStat.setBackgroundColor(Color.TRANSPARENT);
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutNotice, new ListFragment()).commit();

    }

    public void onClickSchedule(View v) {
       // layoutNotice.setVisibility(View.GONE);
        buttonList.setBackgroundColor(Color.TRANSPARENT);
        buttonSchedule.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        buttonStat.setBackgroundColor(Color.TRANSPARENT);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layoutNotice, new ScheduleFragment());
        fragmentTransaction.commit();
    }

    public void onClickStat(View v) {
        //layoutNotice.setVisibility(View.GONE);
        buttonList.setBackgroundColor(Color.TRANSPARENT);
        buttonSchedule.setBackgroundColor(Color.TRANSPARENT);
        buttonStat.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layoutNotice, new StatFragment());
        fragmentTransaction.commit();
    }
}

