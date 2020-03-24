package com.example.classregistration;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ScheduleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    final public static int NUM_OF_TIME_SLOTS = 10;
    private Schedule schedule = new Schedule();
    private TextView textViewMon[] = new TextView[NUM_OF_TIME_SLOTS];
    private TextView textViewTue[] = new TextView[NUM_OF_TIME_SLOTS];
    private TextView textViewWed[] = new TextView[NUM_OF_TIME_SLOTS];
    private TextView textViewThu[] = new TextView[NUM_OF_TIME_SLOTS];
    private TextView textViewFri[] = new TextView[NUM_OF_TIME_SLOTS];
    private int idsTextViewMon[] = new int[NUM_OF_TIME_SLOTS];
    private int idsTextViewTue[] = new int[NUM_OF_TIME_SLOTS];
    private int idsTextViewWed[] = new int[NUM_OF_TIME_SLOTS];
    private int idsTextViewThu[] = new int[NUM_OF_TIME_SLOTS];
    private int idsTextViewFri[] = new int[NUM_OF_TIME_SLOTS];
    private RadioGroup radioGroupSemester;

    private void setIdsTextView() {
        idsTextViewMon[0] = R.id.textViewMon0;
        idsTextViewMon[1] = R.id.textViewMon1;
        idsTextViewMon[2] = R.id.textViewMon2;
        idsTextViewMon[3] = R.id.textViewMon3;
        idsTextViewMon[4] = R.id.textViewMon4;
        idsTextViewMon[5] = R.id.textViewMon5;
        idsTextViewMon[6] = R.id.textViewMon6;
        idsTextViewMon[7] = R.id.textViewMon7;
        idsTextViewMon[8] = R.id.textViewMon8;
        idsTextViewMon[9] = R.id.textViewMon9;
        idsTextViewTue[0] = R.id.textViewTue0;
        idsTextViewTue[1] = R.id.textViewTue1;
        idsTextViewTue[2] = R.id.textViewTue2;
        idsTextViewTue[3] = R.id.textViewTue3;
        idsTextViewTue[4] = R.id.textViewTue4;
        idsTextViewTue[5] = R.id.textViewTue5;
        idsTextViewTue[6] = R.id.textViewTue6;
        idsTextViewTue[7] = R.id.textViewTue7;
        idsTextViewTue[8] = R.id.textViewTue8;
        idsTextViewTue[9] = R.id.textViewTue9;
        idsTextViewWed[0] = R.id.textViewWed0;
        idsTextViewWed[1] = R.id.textViewWed1;
        idsTextViewWed[2] = R.id.textViewWed2;
        idsTextViewWed[3] = R.id.textViewWed3;
        idsTextViewWed[4] = R.id.textViewWed4;
        idsTextViewWed[5] = R.id.textViewWed5;
        idsTextViewWed[6] = R.id.textViewWed6;
        idsTextViewWed[7] = R.id.textViewWed7;
        idsTextViewWed[8] = R.id.textViewWed8;
        idsTextViewWed[9] = R.id.textViewWed9;
        idsTextViewThu[0] = R.id.textViewThu0;
        idsTextViewThu[1] = R.id.textViewThu1;
        idsTextViewThu[2] = R.id.textViewThu2;
        idsTextViewThu[3] = R.id.textViewThu3;
        idsTextViewThu[4] = R.id.textViewThu4;
        idsTextViewThu[5] = R.id.textViewThu5;
        idsTextViewThu[6] = R.id.textViewThu6;
        idsTextViewThu[7] = R.id.textViewThu7;
        idsTextViewThu[8] = R.id.textViewThu8;
        idsTextViewThu[9] = R.id.textViewThu9;
        idsTextViewFri[0] = R.id.textViewFri0;
        idsTextViewFri[1] = R.id.textViewFri1;
        idsTextViewFri[2] = R.id.textViewFri2;
        idsTextViewFri[3] = R.id.textViewFri3;
        idsTextViewFri[4] = R.id.textViewFri4;
        idsTextViewFri[5] = R.id.textViewFri5;
        idsTextViewFri[6] = R.id.textViewFri6;
        idsTextViewFri[7] = R.id.textViewFri7;
        idsTextViewFri[8] = R.id.textViewFri8;
        idsTextViewFri[9] = R.id.textViewFri9;
    }

    // TODO: Rename and change types and number of parameters
    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);
        setIdsTextView();
        for (int i = 0; i < NUM_OF_TIME_SLOTS; i++) {
            textViewMon[i] = getView().findViewById(idsTextViewMon[i]);
            textViewTue[i] = getView().findViewById(idsTextViewTue[i]);
            textViewWed[i] = getView().findViewById(idsTextViewWed[i]);
            textViewThu[i] = getView().findViewById(idsTextViewThu[i]);
            textViewFri[i] = getView().findViewById(idsTextViewFri[i]);
        }
        radioGroupSemester = getView().findViewById(R.id.radioGroupSemester);
        radioGroupSemester.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                new BackgroundTask().execute();
            }
        });
        new BackgroundTask().execute();
    }


    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target, semester;

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
            semester = ((RadioButton) (getView().findViewById(radioGroupSemester.getCheckedRadioButtonId()))).getText().toString().substring(0, 1);
            try {
                target = "https://suneunil93.cafe24.com/ClassScheduleList.php?userID=" + URLEncoder.encode(MainActivity.userID, "UTF-8") + "&semester=" + URLEncoder.encode(semester, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int courseID;
                String mon, tue, wed, thu, fri, courseName;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject row = jsonArray.getJSONObject(i);
                    courseID = row.getInt("courseID");
                    mon = row.getString("mon");
                    tue = row.getString("tue");
                    wed = row.getString("wed");
                    thu = row.getString("thu");
                    fri = row.getString("fri");
                    courseName = row.getString("courseName");
                    Course course = new Course(courseID, mon, tue, wed, thu, fri, courseName, semester);
                    schedule.addCourse(course);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            schedule.setTextViewForDays(textViewMon, textViewTue, textViewWed, textViewThu, textViewFri, getContext(), semester);
        }
    }
}