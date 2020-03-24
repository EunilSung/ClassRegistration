package com.example.classregistration;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class StatListAdapter extends BaseAdapter {
    private Context context;
    private List<Course> listCourse;
    private final String userID = MainActivity.userID;

    public StatListAdapter(Context context, List<Course> listCourse) {
        this.context = context;
        this.listCourse = listCourse;
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
            convertView = View.inflate(context, R.layout.item_stat, null);
        }
        TextView textViewDepartment = convertView.findViewById(R.id.textViewDepartment);
        TextView textViewCourseName = convertView.findViewById(R.id.textViewCourseName);
        TextView textViewCredit = convertView.findViewById(R.id.textViewCreditStat);
        TextView textViewApplicant = convertView.findViewById(R.id.textViewApplicant);
        TextView textViewRatio = convertView.findViewById(R.id.textViewRatio);
        Button buttonDelete = convertView.findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean isSuccess = jsonResponse.getBoolean("success");
                            if (isSuccess) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Deleted.").setPositiveButton("OK", null).create().show();
                                StatFragment.decTotalCredit(listCourse.get(position).credit);
                                listCourse.remove(position);
                                notifyDataSetChanged();
                            } else {
                                Log.e("jack", "failed to delete schedule in DB at StatListAdapter");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                DeleteCourseRequest deleteCourseRequest = new DeleteCourseRequest(userID, listCourse.get(position).courseID, responseListener);
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(deleteCourseRequest);
            }
        });
        textViewDepartment.setText(listCourse.get(position).department);
        textViewCourseName.setText(listCourse.get(position).name);
        textViewCredit.setText(listCourse.get(position).credit + "");
        textViewApplicant.setText(listCourse.get(position).numOfApplicant + " / " + listCourse.get(position).capacity);
        double ratio = listCourse.get(position).numOfApplicant * 100 / listCourse.get(position).capacity;
        textViewRatio.setText(ratio + "%");
        if (ratio >= 100) {
            textViewRatio.setTextColor(parent.getResources().getColor(R.color.colorWarning));
        } else {
            textViewRatio.setTextColor(parent.getResources().getColor(android.R.color.background_light));
        }
        return convertView;
    }
}
