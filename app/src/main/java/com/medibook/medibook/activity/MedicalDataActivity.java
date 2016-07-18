package com.medibook.medibook.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.medibook.medibook.R;
import com.medibook.medibook.common.API;

public class MedicalDataActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etAllergy;
    private EditText etSeverity;
    private EditText etFirst_name;
    private EditText etLast_name;
    private Button butCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_data);

        etAllergy = (EditText) findViewById(R.id.etAllergy);
        etSeverity = (EditText) findViewById(R.id.etSeverity);
        etFirst_name = (EditText) findViewById(R.id.etFName);
        etLast_name = (EditText) findViewById(R.id.etLName);
        butCreate = (Button) findViewById(R.id.butCreateAccount);

        butCreate.setOnClickListener(this);
    }

    private void addMedicalData(String email) {
        String allergy = etAllergy.getText().toString().trim();
        String severity = etSeverity.getText().toString().trim();
        String first_name = etFirst_name.getText().toString().trim();
        String last_name = etLast_name.getText().toString().trim();

        API handler = new API(this);
        handler.putPrimaryDoctor(first_name, last_name);
        handler.putUserAllergyData(email,allergy, severity);
    }

    public void onClick(View v) {
        if (v == butCreate) {
            Intent intentMD = getIntent();
            String email = intentMD.getStringExtra("EMAIL");
            addMedicalData(email);
            Intent intent = new Intent(MedicalDataActivity.this, ViewProfileActivity.class);
            intent.putExtra("EMAIL", email);
            startActivity(intent);
        }
    }
}