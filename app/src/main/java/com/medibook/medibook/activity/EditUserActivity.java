package com.medibook.medibook.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.medibook.medibook.R;
import com.medibook.medibook.common.API;

public class EditUserActivity extends AppCompatActivity implements View.OnClickListener {
    private String name;
    private String email;
    private String birthday;
    private String gender;
    private String healthcard;
    private String address;
    private String userEmail;
    private Button btnSaveProfile;
    private Button btnCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSaveProfile = (Button) findViewById(R.id.btnSave);

        Intent intent = getIntent();
        userEmail = intent.getStringExtra("EMAIL");
        API apiHandler = new API(this);
        apiHandler.getUser(userEmail);
    }

    @Override
    public void onClick(View v) {
        if (v == btnCancel){
            Toast toast = Toast.makeText(getApplicationContext(),"Profile not edited",Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(this, ViewProfileActivity.class);
            intent.putExtra("EMAIL", email);
            startActivity(intent);
        }
        if (v== btnSaveProfile){
            Toast toast = Toast.makeText(getApplicationContext(),"Successfully edited",Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(this, ViewProfileActivity.class);
            intent.putExtra("EMAIL", email);
            startActivity(intent);
        }
    }
}
