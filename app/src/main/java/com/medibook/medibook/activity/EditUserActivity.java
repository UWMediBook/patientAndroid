package com.medibook.medibook.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.medibook.medibook.R;
import com.medibook.medibook.common.API;

import java.util.Calendar;
import java.util.TimeZone;

public class EditUserActivity extends AppCompatActivity implements View.OnClickListener {

    private String[] mOptions;
    private DrawerLayout mDrawerLayout;

    private ListView mDrawerList;
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
        mOptions = getResources().getStringArray(R.array.optionsArray);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mOptions));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        Intent intent = getIntent();
        userEmail = intent.getStringExtra("EMAIL");
        API apiHandler = new API(this);
        apiHandler.getUserByEmail(userEmail);

        btnSaveProfile.setOnClickListener(this);
        birthday.setOnClickListener(this);
    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private boolean selectItem(int position) {
//        Toast.makeText(this, mOptions[position], Toast.LENGTH_SHORT).show();
        Intent intent;
        Intent intentEmail = getIntent();
        String email = intentEmail.getStringExtra("EMAIL");
        switch (mOptions[position]){
            case "Profile":
                intent = new Intent(this, ViewProfileActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                finish();
                return true;
            case "Edit Profile":
                intent = new Intent(this, EditUserActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                finish();
                return true;
            case "Allergies":
                intent = new Intent(this, ViewAllergiesActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                finish();
                return true;
            case "Emergency Contact":
                intent = new Intent(this,ViewEmergencyContactActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                finish();
                return true;
            case "Past Operations":
                intent = new Intent(this, ViewPastOperationsActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                finish();
                return true;
            case "Past Visits":
                intent = new Intent(this, ViewPastVisitsActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                finish();
                return true;
            case "Generate QR Code":
                intent = new Intent(this,generateQRActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                finish();
                return true;
            case "Prescriptions":
                intent = new Intent(this, ViewPrescriptionsActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                finish();
                return true;
            case "Primary Doctor":
                intent = new Intent(this, ViewPrimaryDoctorActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                finish();
                return true;
            case "Edit Primary Doctor":
                intent = new Intent(this, EditPrimaryDoctorActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                finish();
                return true;
            case "Edit Emergency Contact":
                intent = new Intent(this, EditEmergencyContactActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                finish();
                return true;
        }
        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
        return true;
    }
    private void updateUser() {
        int selected=rbGender.getCheckedRadioButtonId();
        RadioButton genderBtn=(RadioButton) findViewById(selected);
        String FName = first_name.getText().toString().trim();
        String LName = last_name.getText().toString().trim();
        String uEmail = email.getText().toString().trim();
        String birthdate = birthday.getText().toString().trim();
        String[] splitDate = birthdate.split("/");
        mYear = Integer.parseInt(splitDate[2]);
        mMonth = Integer.parseInt(splitDate[1]) - 1;
        mDay = Integer.parseInt(splitDate[0]);
        dob.setTimeZone(TimeZone.getTimeZone("UTC"));
        dob.set(mYear, mMonth, mDay);

        String uGender = genderBtn.getText().toString().trim();
        String uAddress = address.getText().toString().trim();
        String uPassword = password.getText().toString().trim();
        String uHealthCard = healthcard.getText().toString().trim();

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
