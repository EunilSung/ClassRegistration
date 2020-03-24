package com.example.classregistration;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.List;

public class StatFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ListView statListView;
    private StatListAdapter adapter;
    private List<Course> listCourse;
    static int totalCredit;
    static TextView textViewTotalCredit;
    private RadioGroup radioGroupSemester;

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        statListView = getView().findViewById(R.id.listViewStat);
        listCourse = new ArrayList<>();
        adapter = new StatListAdapter(getContext(), listCourse);
        statListView.setAdapter(adapter);
        textViewTotalCredit = getView().findViewById(R.id.textViewTotalCredit);
        radioGroupSemester = getView().findViewById(R.id.radioGroupSemester);
        radioGroupSemester.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                new BackgroundTask().execute();
            }
        });
        new BackgroundTask().execute();
    }

    public StatFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static StatFragment newInstance(String param1, String param2) {
        StatFragment fragment = new StatFragment();
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
        return inflater.inflate(R.layout.fragment_stat, container, false);
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
                target = "https://suneunil93.cafe24.com/ClassStatList.php?userID=" + URLEncoder.encode(MainActivity.userID, "UTF-8") + "&semester=" + URLEncoder.encode(semester, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            listCourse.clear();
            totalCredit = 0;
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int courseID, capacity, applicant, credit;
                String department, courseName;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject row = jsonArray.getJSONObject(i);
                    courseID = row.getInt("courseID");
                    department = row.getString("department");
                    courseName = row.getString("courseName");
                    capacity = row.getInt("capacity");
                    credit = row.getInt("credit");
                    applicant = row.getInt("applicant");
                    totalCredit += credit;
                    listCourse.add(new Course(courseID, department, courseName, credit, capacity, applicant));
                } adapter.notifyDataSetChanged();
                textViewTotalCredit.setText(totalCredit + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void decTotalCredit(int credit) {
        totalCredit -= credit;
        textViewTotalCredit.setText(totalCredit + "");
    }
}