package com.example.classregistration;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private ArrayAdapter adapter;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        spinner = findViewById(R.id.spinnerDepartment);
        adapter = ArrayAdapter.createFromResource(this, R.array.department, android.R.
                layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}

