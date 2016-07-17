package com.medibook.medibook.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.view.KeyEvent;
import android.widget.TextView;
import android.view.inputmethod.EditorInfo;

import com.medibook.medibook.R;
import com.medibook.medibook.common.API;

import java.util.Calendar;

public class RegisterMainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etFname;
    private EditText etLname;
    private EditText etAddress;
    private EditText etDOB;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etREPassword;
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
        etREPassword = (EditText) findViewById(R.id.etREPassword);
        etREPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.validatePassword || id == EditorInfo.IME_NULL) {
                    validate();
                    return true;
                }
                return false;
            }
        });
        etHCNum = (EditText) findViewById(R.id.etHCNumber);
        butNext = (Button) findViewById(R.id.butNext);
        rbg=(RadioGroup) findViewById(R.id.radioGroup1);

        butNext.setOnClickListener(this);
        etDOB.setOnClickListener(this);
    }

    private void registerUser() {

        int selected=rbg.getCheckedRadioButtonId();
        RadioButton genderBtn=(RadioButton) findViewById(selected);
        String first_name = etFname.getText().toString().trim();
        String last_name = etLname.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String gender = genderBtn.getText().toString().trim();
        dob.set(mYear, mMonth, mDay);
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String HealthCard = etHCNum.getText().toString().trim();

        API handler = new API(this);
        System.out.println("Date of birth is: "+dob);
        handler.postUser(first_name,last_name,address,gender,dob,email,password,HealthCard);
    }

    public void onClick(View v) {
        if (v == butNext) {
            registerUser();
            Intent intent = new Intent(RegisterMainActivity.this, EmergencyContactActivity.class);
            intent.putExtra("EMAIL", etEmail.getText().toString());
            startActivity(intent);
        }else if(v == etDOB){
            Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    etDOB.setText(day + "/" + (month+1) + "/" +year);
                    System.out.println("Year: " + year + " Month: " + month + " Day: " + day);

                    mYear = year;
                    mMonth = month;
                    mDay = day;
                    System.out.println("After re-setting the variables, Year: " + mYear + " Month: " + mMonth + " Day: " + mDay);

                }
            }, mYear, mMonth, mDay);
            dpd.show();
            System.out.println("Year: " + mYear + " Month: " + mMonth + " Day: " + mDay);
        }
    }
    private void validate(){
        etREPassword.setError(null);
        String password1 = etREPassword.getText().toString();
        String password2 = etPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password1) && !isPasswordSame(password1, password2)) {
            etREPassword.setError(getString(R.string.error_password_mismatch));
            focusView = etREPassword;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
    }
    private boolean isPasswordSame(String password1, String password2) {
        return password1.equals(password2);
    }
}
