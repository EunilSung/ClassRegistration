package com.example.classregistration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button buttonList, buttonSchedule, buttonStat;
    LinearLayout layoutNotice;
    ListView listViewNotice;
    NoticeListAdapter adapter;
    List<Notice> listNotice;
    public static String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        userID = getIntent().getStringExtra("userID");
        buttonList = findViewById(R.id.buttonList);
        buttonSchedule = findViewById(R.id.buttonSchedule);
        buttonStat = findViewById(R.id.buttonStat);
        layoutNotice = findViewById(R.id.layoutNotice);
        listViewNotice = findViewById(R.id.listViewNotice);
        listNotice = new ArrayList<>();
        listNotice.add(new Notice("공지 제목", "작성자", "2019-1-1"));
        listNotice.add(new Notice("공지 제목", "작성자", "2019-1-1"));
        listNotice.add(new Notice("공지 제목", "작성자", "2019-1-1"));
        adapter = new NoticeListAdapter(this, listNotice);
        listViewNotice.setAdapter(adapter);
    }

    public void onClickList(View v) {
        layoutNotice.setVisibility(View.GONE);
        buttonList.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        buttonSchedule.setBackgroundColor(Color.TRANSPARENT);
        buttonStat.setBackgroundColor(Color.TRANSPARENT);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layoutFragment, new ListFragment());
        fragmentTransaction.commit();
    }

    public void onClickSchedule(View v) {
        layoutNotice.setVisibility(View.GONE);
        buttonList.setBackgroundColor(Color.TRANSPARENT);
        buttonSchedule.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        buttonStat.setBackgroundColor(Color.TRANSPARENT);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layoutFragment, new ScheduleFragment());
        fragmentTransaction.commit();
    }

    public void onClickStat(View v) {
        layoutNotice.setVisibility(View.GONE);
        buttonList.setBackgroundColor(Color.TRANSPARENT);
        buttonSchedule.setBackgroundColor(Color.TRANSPARENT);
        buttonStat.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layoutFragment, new StatFragment());
        fragmentTransaction.commit();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            target = "http://suneunil93.cafe24.com/ClassNoticeList.php";
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                String noticeTitle, noticeName, noticeDate;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject row = jsonArray.getJSONObject(i);
                    noticeTitle = row.getString("title");
                    noticeName = row.getString("name");
                    noticeDate = row.getString("date");
                    Notice notice = new Notice(noticeTitle, noticeName, noticeDate);
                    listNotice.add(notice);
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    long lastTimeBackPressed;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            finish();
        } else {
            Toast.makeText(this, "'뒤로' 버튼을한번더눌러종료합니다", Toast.LENGTH_SHORT).show();
            lastTimeBackPressed = System.currentTimeMillis();
        }
    }
}

