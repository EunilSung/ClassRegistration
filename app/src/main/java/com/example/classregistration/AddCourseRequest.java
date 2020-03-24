package com.example.classregistration;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddCourseRequest extends StringRequest {
    final static private String URL = "https://10.0.2.2/ClassCourseAdd.php";
    private Map<String, String> parameters;
    public AddCourseRequest(String userID, int courseID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RegisterRequest", error.getMessage());
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
