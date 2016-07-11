package com.medibook.medibook.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.medibook.medibook.R;

public class EmergencyContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);


        final EditText etFname = (EditText) findViewById(R.id.etFname);
        final EditText etLname = (EditText) findViewById(R.id.etLname);
        final EditText etRelationship = (EditText) findViewById(R.id.etRelationship);
        final EditText etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);

        final Button butNext = (Button) findViewById(R.id.butNext);

        butNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MDIntent = new Intent(EmergencyContactActivity.this, MedicalDataActivity.class);
                EmergencyContactActivity.this.startActivity(MDIntent);
            }
        });
    }
}
