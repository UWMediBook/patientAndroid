package com.medibook.medibook.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.medibook.medibook.R;

public class ViewProfileActivity extends AppCompatActivity {
    private String name;
    private String email;
    private String birthday;
    private String gender;
    private String healthcard;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
    }
}
