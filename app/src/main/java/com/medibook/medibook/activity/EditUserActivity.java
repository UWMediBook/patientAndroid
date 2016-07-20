package com.medibook.medibook.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.medibook.medibook.R;
import com.medibook.medibook.common.API;

import java.util.Calendar;
import java.util.TimeZone;

public class EditUserActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText first_name;
    private EditText last_name;
    private EditText email;
    private EditText birthday;
    private EditText gender;
    private EditText healthcard;
    private EditText address;
    private EditText password;

    private String userEmail;
    private RadioGroup rbGender;

    private Button btnSaveProfile;
    private int mYear, mMonth, mDay;
    Calendar dob = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        first_name = (EditText) findViewById(R.id.user_first_name);
        last_name = (EditText) findViewById(R.id.user_last_name);
        email = (EditText) findViewById(R.id.user_email);
        birthday = (EditText) findViewById(R.id.user_birthday);
        address = (EditText) findViewById(R.id.user_address);
        healthcard = (EditText) findViewById(R.id.user_health_card);
        password = (EditText)findViewById(R.id.user_password);
        rbGender=(RadioGroup) findViewById(R.id.rg1);


        btnSaveProfile = (Button) findViewById(R.id.btnSaveUser);

        Intent intent = getIntent();
        userEmail = intent.getStringExtra("EMAIL");
        API apiHandler = new API(this);
        apiHandler.getUserByEmail(userEmail);

        btnSaveProfile.setOnClickListener(this);
        birthday.setOnClickListener(this);
    }

    private void updateUser() {
        int selected=rbGender.getCheckedRadioButtonId();
        RadioButton genderBtn=(RadioButton) findViewById(selected);
        String FName = first_name.getText().toString().trim();
        String LName = last_name.getText().toString().trim();
        String uEmail = email.getText().toString().trim();
        String birthdate = birthday.getText().toString().trim();
        String[] splitDate = birthdate.split("/");
        dob.setTimeZone(TimeZone.getTimeZone("UTC"));
        dob.set(Integer.parseInt(splitDate[2]), Integer.parseInt(splitDate[1]), Integer.parseInt(splitDate[0]));

        String uGender = genderBtn.getText().toString().trim();
        String uAddress = address.getText().toString().trim();
        String uPassword = password.getText().toString().trim();
        String uHealthCard = healthcard.getText().toString().trim();
        long x = dob.getTimeInMillis()/1000;

        API handler = new API(this);
        handler.postUserWithDoctorID(userEmail,FName,LName,uAddress,uGender,dob,uEmail,uPassword,uHealthCard);
    }

    @Override
    public void onClick(View v) {
        if (v== btnSaveProfile){
            updateUser();
            Toast toast = Toast.makeText(getApplicationContext(),"Successfully edited",Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(this, ViewProfileActivity.class);
            intent.putExtra("EMAIL", userEmail);
            startActivity(intent);
            finish();
        }else if(v == birthday){
            Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    birthday.setText(day + "/" + (month+1) + "/" +year);

                    mYear = year;
                    mMonth = month;
                    mDay = day;

                }
            }, mYear, mMonth, mDay);
            dpd.show();
        }
    }
}
