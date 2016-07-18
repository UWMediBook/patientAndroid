package com.medibook.medibook.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.medibook.medibook.R;
import com.medibook.medibook.common.API;

public class EmergencyContactActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etRelationship;
    private EditText etPhoneNumber;
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);

        etFirstName = (EditText) findViewById(R.id.etFname);
        etLastName = (EditText) findViewById(R.id.etLname);
        etRelationship = (EditText) findViewById(R.id.etRelationship);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        buttonNext = (Button) findViewById(R.id.butNext);

        buttonNext.setOnClickListener(this);
    }

    public void addEmergencyContact(String email){
        String first_name = etFirstName.getText().toString().trim();
        String last_name = etLastName.getText().toString().trim();
        String relationship = etRelationship.getText().toString().trim();
        String phonenumber = etPhoneNumber.getText().toString().trim();

        API handler = new API(this);
        handler.putUserEmergContData(email,first_name,last_name,phonenumber,relationship);
    }

    public void onClick(View v) {
        if (v == buttonNext) {
            Intent intentEC = getIntent();
            String email = intentEC.getStringExtra("EMAIL");
            addEmergencyContact(email);
            Intent intent = new Intent(EmergencyContactActivity.this, MedicalDataActivity.class);
            intent.putExtra("EMAIL", email);
            startActivity(intent);
        }
    }
}