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

public class EditUserActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText name;
    private EditText email;
    private EditText birthday;
    private EditText gender;
    private EditText healthcard;
    private EditText address;
    private String userEmail;
    private Button btnSaveProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        btnSaveProfile = (Button) findViewById(R.id.btnSave);

        Intent intent = getIntent();
        userEmail = intent.getStringExtra("EMAIL");
        API apiHandler = new API(this);
        apiHandler.getUser(userEmail);
    }

    @Override
    public void onClick(View v) {
        if (v== btnSaveProfile){
            name = (EditText) findViewById(R.id.user_name);
            email = (EditText) findViewById(R.id.user_email);
            birthday = (EditText) findViewById(R.id.user_birthday);
            gender = (EditText) findViewById(R.id.user_gender);
            address = (EditText) findViewById(R.id.user_address);
            healthcard = (EditText) findViewById(R.id.user_health_card);

            Toast toast = Toast.makeText(getApplicationContext(),"Successfully edited",Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(this, ViewProfileActivity.class);
            intent.putExtra("EMAIL", userEmail);
            startActivity(intent);
        }
    }
}
