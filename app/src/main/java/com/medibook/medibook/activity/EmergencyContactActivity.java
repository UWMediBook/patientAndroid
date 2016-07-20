package com.medibook.medibook.activity;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.medibook.medibook.R;
import com.medibook.medibook.common.API;

public class EmergencyContactActivity extends AppCompatActivity implements View.OnClickListener{
    private String[] mOptions;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etRelationship;
    private EditText etPhoneNumber;
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);
        mOptions = getResources().getStringArray(R.array.optionsArray);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mOptions));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        etFirstName = (EditText) findViewById(R.id.etFname);
        etLastName = (EditText) findViewById(R.id.etLname);
        etRelationship = (EditText) findViewById(R.id.etRelationship);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        buttonNext = (Button) findViewById(R.id.butNext);

        buttonNext.setOnClickListener(this);
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
    public boolean addEmergencyContact(String email){
        if (etFirstName.length() == 0 || etLastName.length() == 0 || etRelationship.length() == 0 || etPhoneNumber.length() == 0) {
            if (etFirstName.length() == 0) {
                etFirstName.setError("Cannot be empty");
            }
            if (etLastName.length() == 0) {
                etLastName.setError("Cannot be empty");
            }
            if (etRelationship.length() == 0) {
                etRelationship.setError("Cannot be empty");
            }
            if (etPhoneNumber.length() == 0) {
                etPhoneNumber.setError("Cannot be empty");
            }
            return false;
        } else {
            String first_name = etFirstName.getText().toString().trim();
            String last_name = etLastName.getText().toString().trim();
            String relationship = etRelationship.getText().toString().trim();
            String phonenumber = etPhoneNumber.getText().toString().trim();


            API handler = new API(this);
            handler.putUserEmergContData(email, first_name, last_name, phonenumber, relationship);

            return true;
        }
    }

    public void onClick(View v) {
        if (v == buttonNext) {
            Intent intentEC = getIntent();
            String email = intentEC.getStringExtra("EMAIL");
            if (addEmergencyContact(email) == true) {
                Intent intent = new Intent(EmergencyContactActivity.this, MedicalDataActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                finish();
            }
        }
    }
}