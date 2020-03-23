package com.example.classregistration;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private ArrayAdapter adapter;
    private Spinner spinner;
    private EditText editTextUserID, editTextUserPassword, editTextUserEmail;
    private Button buttonCheck;
    private AlertDialog dialog;
    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        spinner = findViewById(R.id.spinnerDepartment);
        adapter = ArrayAdapter.createFromResource(this, R.array.department, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        editTextUserID = findViewById(R.id.editTextID);
        editTextUserPassword = findViewById(R.id.editTextPassword);
        editTextUserEmail = findViewById(R.id.editTextEmail);
        buttonCheck = findViewById(R.id.buttonCheck);
    }

    public String getGenderAsMorF(RadioGroup radioGroupGender) {
        int checkedIndex = radioGroupGender.getCheckedRadioButtonId();
        String gender = ((RadioButton) radioGroupGender.findViewById(checkedIndex)).getText().toString();
        if (gender.equals("Male")) return "M";
        else return "F";
    }

    public void onClickCheck(View v) {
        String userID = editTextUserID.getText().toString();
        if (userID.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            dialog = builder.setMessage("ID is empty.").setNegativeButton("Retry", null).create();
            dialog.show();
            return;
        }
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean isSuccess = jsonResponse.getBoolean("success");
                    if (isSuccess) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        dialog = builder.setMessage("Good ID").setPositiveButton("OK", null).create();
                        dialog.show();
                        editTextUserID.setEnabled(false);
                        buttonCheck.setEnabled(false);
                        validate = true;
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        dialog = builder.setMessage("ID is already used").setNegativeButton("Retry", null).create();
                        dialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        requestQueue.add(validateRequest);
    }

    public void onClickRegister(View v) {
        if (!validate) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            dialog = builder.setMessage("Press Check").setNegativeButton("Retry", null).create();
            dialog.show();
            return;
        }
        String userID = editTextUserID.getText().toString();
        String userPassword = editTextUserPassword.getText().toString();
        String userEmail = editTextUserEmail.getText().toString();
        String userDepartment = spinner.getSelectedItem().toString();
        String userGender = getGenderAsMorF((RadioGroup) findViewById(R.id.radioGroupGender));
        if (userID.equals("") | userPassword.equals("") | userDepartment.equals("") | userGender.equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            dialog = builder.setMessage("Field is empty.").setNegativeButton("Retry", null).create();
            dialog.show();
            return;
        }
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean isSuccess = jsonResponse.getBoolean("success");
                    if (isSuccess) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        dialog = builder.setMessage("Created.").setPositiveButton("OK", new DialogInterface.OnClickListener(){
                            @Override public void onClick (DialogInterface dialog,int which){
                            dialog.dismiss();
                            finish();
                        }
                        }).create();
                        dialog.show();
                    } else {
                        Log.e("RegisterActivity", "register failed");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userGender, userDepartment, userEmail, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        requestQueue.add(registerRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}



