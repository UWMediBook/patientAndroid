package com.medibook.medibook.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.medibook.medibook.R;

public class MedicalDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_data);

        final EditText allergy = (EditText) findViewById(R.id.etAllergy);
        final EditText first_name = (EditText) findViewById(R.id.etFname);
        final EditText last_name = (EditText) findViewById(R.id.etLname);

        final Button butCreate = (Button) findViewById(R.id.butCreateAccount);

        butCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentMD = getIntent();
                String email = intentMD.getStringExtra("EMAIL");
                Intent intent = new Intent(MedicalDataActivity.this, UserAreaActivity.class);
                intent.putExtra("EMAIL",email);
                startActivity(intent);
            }
        });
    }
}
