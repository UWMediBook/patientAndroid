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

public class EditEmergencyContactActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText fname;
    private EditText lname;
    private EditText relationship;
    private EditText phonenumber;

    private Button btnSave;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_emergency_contact);

        fname = (EditText) findViewById(R.id.first_name_ec);
        lname = (EditText) findViewById(R.id.last_name_ec);
        relationship = (EditText) findViewById(R.id.relationship_ec);
        phonenumber = (EditText) findViewById(R.id.phone_number_ec);
        btnSave = (Button) findViewById(R.id.btnSave);


        Intent intentEC = getIntent();
        userEmail = intentEC.getStringExtra("EMAIL");
        API handlerID = new API(this);
        handlerID.getUserIdEmergencyContact(userEmail);

        btnSave.setOnClickListener(this);
    }

    public void updateEC(String email){
        String first_name = fname.getText().toString().trim();
        String last_name = lname.getText().toString().trim();
        String uRelationship = relationship.getText().toString().trim();
        String phone_number = phonenumber.getText().toString().trim();

        API handler = new API(this);
        handler.postUserIdUpdateEC(email,first_name,last_name,uRelationship,phone_number);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSave){
            updateEC(userEmail);
            Toast.makeText(EditEmergencyContactActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ViewProfileActivity.class);
            intent.putExtra("EMAIL", userEmail);
            startActivity(intent);
        }
    }
}
