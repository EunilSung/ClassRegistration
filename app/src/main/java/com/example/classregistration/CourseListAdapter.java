package com.example.classregistration;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class CourseListAdapter extends BaseAdapter {
    private Context context;
    private List<Course> listCourse;
    private Schedule schedule;
    private final String userID = MainActivity.userID;

    public CourseListAdapter(Context context, List<Course> listCourse) {
        this.context = context;
        this.listCourse = listCourse;
        schedule = new Schedule();
        new BackgroundTask().execute("1");
        new BackgroundTask().execute("2");
    }

    @Override
    public int getCount() {
        return listCourse.size();
    }

    @Override
    public Object getItem(int position) {
        return listCourse.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_course, null);
        }
        TextView textViewSemester = convertView.findViewById(R.id.textViewSemester);
        TextView textViewName = convertView.findViewById(R.id.textViewCourseName);
        TextView textViewDepartment = convertView.findViewById(R.id.textViewDepartment);
        TextView textViewCredit = convertView.findViewById(R.id.textViewCredit);
        TextView textViewSchedule = convertView.findViewById(R.id.textViewSchedule);
        textViewSemester.setText(listCourse.get(position).semester + "학기");
        textViewName.setText(listCourse.get(position).name);
        textViewDepartment.setText(listCourse.get(position).department);
        textViewCredit.setText(Integer.toString(listCourse.get(position).credit) + "학점");
        textViewSchedule.setText(listCourse.get(position).getSchedule());
        Button button = convertView.findViewById(R.id.buttonAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (schedule.isAlreadyIn(listCourse.get(position))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Already added.").setNegativeButton("Retry", null).create().show();
                    return;
                }
                if (schedule.isDuplicated(listCourse.get(position))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Schedule is duplicated.").setNegativeButton("Retry", null).create().show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean isSuccess = jsonResponse.getBoolean("success");
                            if (isSuccess) {
                                schedule.addCourse(listCourse.get(position));
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Added.").setPositiveButton("OK", null).create().show();
                            } else {
                                Log.e("jack", "trying to add course already in DB at CourseListAdapter");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                AddCourseRequest addCourseRequest = new AddCourseRequest(userID, listCourse.get(position).courseID, responseListener);
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(addCourseRequest);
            }
        });
        return convertView;
    }

    class BackgroundTask extends AsyncTask<String, Void, String> {
        String target, semester;

        @Override
        protected String doInBackground(String... sem) {
            semester = sem[0];
            try {
                try {
                    target = "https://10.0.2.2/ClassScheduleList.php?userID=" + URLEncoder.encode(userID, "UTF-8") + "&semester=" + URLEncoder.encode(semester, "UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        }
    }
}
