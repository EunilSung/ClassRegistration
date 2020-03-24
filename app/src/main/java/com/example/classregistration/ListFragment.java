package com.example.classregistration;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

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

public class ListFragment extends Fragment {
    ArrayAdapter arrayAdapterDepartment;
    Spinner spinnerDepartment;
    RadioGroup radioGroupSemester;
    ListView listViewCourse;
    CourseListAdapter adapter;
    List<Course> listCourse;
    // TODO: Rename and change types of parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
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
        return inflater.inflate(R.layout.fragment_list, container, false);
    }
    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);
        radioGroupSemester = getView().findViewById(R.id.radioGroupSemester);
        spinnerDepartment = getView().findViewById(R.id.spinnerDepartment);
        arrayAdapterDepartment = ArrayAdapter.createFromResource(getContext(), R.array.department,
                android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(arrayAdapterDepartment);
        listViewCourse = getView().findViewById(R.id.listViewCourse);
        listCourse = new ArrayList<>();
        adapter = new CourseListAdapter(getContext(), listCourse);
        listViewCourse.setAdapter(adapter);
        Button button = getView().findViewById(R.id.buttonSearch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask().execute();
            }
        });
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
            String semester = ((RadioButton) (getView().findViewById(radioGroupSemester.getCheckedRadioButtonId()))).getText().toString();
            String department = spinnerDepartment.getSelectedItem().toString();
            try {
                target = "https://suneunil93.cafe24.com/ClassCourseList.php?semester=" + URLEncoder.encode(semester, "UTF-8") + "&department=" + URLEncoder.encode(department, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        protected void onPostExecute(String s) {
            try {
                listCourse.clear();
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                String semester, department, name, mon, tue, wed, thu, fri;
                int courseID, credit;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject row = jsonArray.getJSONObject(i);
                    courseID = row.getInt("courseID");
                    semester = row.getString("semester");
                    department = row.getString("department");
                    name = row.getString("name");
                    credit = row.getInt("credit");
                    mon = row.getString("mon");
                    tue = row.getString("tue");
                    wed = row.getString("wed");
                    thu = row.getString("thu");
                    fri = row.getString("fri");
                    Course course = new Course(courseID, semester, department, name, credit, mon, tue, wed, thu, fri);

                    listCourse.add(course);
                }
                adapter.notifyDataSetChanged();
                if (jsonArray.length() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListFragment.this.getContext());
                    builder.setMessage("No match").setNegativeButton("Retry", null).create().show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


