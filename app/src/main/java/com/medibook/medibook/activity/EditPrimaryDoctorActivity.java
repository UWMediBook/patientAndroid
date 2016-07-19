package com.medibook.medibook.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.medibook.medibook.R;
import com.medibook.medibook.common.API;

public class EditPrimaryDoctorActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText fname;
    private EditText lname;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_primary_doctor);

        fname = (EditText) findViewById(R.id.first_name_pd);
        lname = (EditText) findViewById(R.id.last_name_pd);
        btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(this);

    }

    public void updatePrimaryDoctor(String email){
        String first_name = fname.getText().toString().trim();
        String last_name = lname.getText().toString().trim();

        API handler = new API(this);
        handler.postUserIdUpdatePD(email, first_name,last_name);

    }

    @Override
    public void onClick(View v) {
        if (v == btnSave){
            Intent intentEC = getIntent();
            String email = intentEC.getStringExtra("EMAIL");
            updatePrimaryDoctor(email);
            Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ViewProfileActivity.class);
            intent.putExtra("EMAIL", email);
            startActivity(intent);
        }
    }
}
