package com.medibook.medibook.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.medibook.medibook.R;
import com.medibook.medibook.common.API;

import java.util.Calendar;
import java.util.TimeZone;

public class RegisterMainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etFname;
    private EditText etLname;
    private EditText etAddress;
    private EditText etDOB;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPassword_2;
    private EditText etHCNum;
    private Button butNext;
    private RadioGroup rbg;
    private int mYear, mMonth, mDay;
    Calendar dob = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_main);

        etFname = (EditText) findViewById(R.id.etFname);
        etLname = (EditText) findViewById(R.id.etLname);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etDOB = (EditText) findViewById(R.id.etDOB);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassword_2 = (EditText) findViewById(R.id.etPassword_2);
        etHCNum = (EditText) findViewById(R.id.etHCNumber);

        butNext = (Button) findViewById(R.id.butNext);
        rbg=(RadioGroup) findViewById(R.id.rg1);

        butNext.setOnClickListener(this);
        etDOB.setOnClickListener(this);
    }

    private boolean registerUser() {
        if (etFname.length() == 0 || etLname.length() == 0 || etAddress.length() == 0 || etDOB.length() == 0 || etEmail.length() == 0 || etPassword.length() == 0 || etPassword_2.length() == 0 || etHCNum.length() == 0) {
            if (etFname.length() == 0) {
                etFname.setError("Cannot be empty");
            }
            if (etLname.length() == 0) {
                etLname.setError("Cannot be empty");
            }
            if (etAddress.length() == 0) {
                etAddress.setError("Cannot be empty");
            }
            if (etDOB.length() == 0) {
                etDOB.setError("Cannot be empty");
            }
            if (etEmail.length() == 0) {
                etEmail.setError("Cannot be empty");
            }
            if (etPassword.length() == 0) {
                etPassword.setError("Cannot be empty");
            }
            if (etPassword_2.length() == 0) {
                etPassword_2.setError("Cannot be empty");
            }
            if (etHCNum.length() == 0) {
                etHCNum.setError("Cannot be empty");
            }
            return false;
        } else {
            String password1 = etPassword.getText().toString().trim();
            String password2 = etPassword_2.getText().toString().trim();
            if (!(password1.equals(password2))) {
                etPassword.setError("Passwords do not match");
                etPassword_2.setError("Passwords do not match");
                return false;
            } else {
                int selected = rbg.getCheckedRadioButtonId();
                RadioButton genderBtn = (RadioButton) findViewById(selected);
                String first_name = etFname.getText().toString().trim();
                String last_name = etLname.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                String gender = genderBtn.getText().toString().trim();
                dob.setTimeZone(TimeZone.getTimeZone("UTC"));
                dob.set(mYear, mMonth, mDay);
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String HealthCard = etHCNum.getText().toString().trim();

                API handler = new API(this);
                handler.putUser(first_name, last_name, address, gender, dob, email, password, HealthCard);

                return true;
            }
        }
    }

    public void onClick(View v) {
        if (v == butNext) {
            if (registerUser() == true) {
                Intent intent = new Intent(RegisterMainActivity.this, EmergencyContactActivity.class);
                intent.putExtra("EMAIL", etEmail.getText().toString());
                startActivity(intent);
                finish();
            }
        }else if(v == etDOB){
            Calendar c = Calendar.getInstance();
            c.setTimeZone(TimeZone.getTimeZone("UTC"));
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    etDOB.setText(day + "/" + (month+1) + "/" +year);

                    mYear = year;
                    mMonth = month;
                    mDay = day;

                }
            }, mYear, mMonth, mDay);
            dpd.show();
        }
    }
}
