package com.example.classregistration;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteCourseRequest extends StringRequest {
    final static private String URL = "https://suneunil93.cafe24.com/ClassScheduleDelete.php";
    private Map<String, String> parameters;

    public DeleteCourseRequest(String userID, int courseID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("jack", error.getMessage());
            }
        });
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("courseID", Integer.toString(courseID));
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}