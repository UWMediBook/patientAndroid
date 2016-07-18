package com.medibook.medibook.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.medibook.medibook.R;
import com.medibook.medibook.common.API;

public class ViewPrescriptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prescriptions);

        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("EMAIL");

        API handler = new API(this);
        handler.getUserIdPrescriptions(userEmail);
    }
}
