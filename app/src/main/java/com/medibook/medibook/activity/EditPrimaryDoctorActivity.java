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
import android.widget.Toast;

import com.medibook.medibook.R;
import com.medibook.medibook.common.API;

public class EditPrimaryDoctorActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText fname;
    private EditText lname;
    private EditText phonenum;
    private EditText address;
    private Button btnSave;

    private String userEmail;
    private String[] mOptions;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_primary_doctor);

        fname = (EditText) findViewById(R.id.firstname_pd);
        lname = (EditText) findViewById(R.id.lastname_pd);
        phonenum = (EditText) findViewById(R.id.phonenumber_pd);
        address = (EditText) findViewById(R.id.address_pd);
        btnSave = (Button) findViewById(R.id.btnSavePD);
        mOptions = getResources().getStringArray(R.array.optionsArray);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

// Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mOptions));
// Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        Intent intentPD = getIntent();
        userEmail = intentPD.getStringExtra("EMAIL");
        API handler = new API(this);
        handler.getUserIdPrimaryDoctor(userEmail);

        btnSave.setOnClickListener(this);

    }

    public void updatePrimaryDoctor(String email) {
        String first_name = fname.getText().toString().trim();
        String last_name = lname.getText().toString().trim();
        String phonenumber = phonenum.getText().toString().trim();
        String addr = address.getText().toString().trim();


        API handler = new API(this);
        handler.postUserIdUpdatePD(email, first_name, last_name, phonenumber, addr);

    }

    @Override
    public void onClick(View v) {
        if (v == btnSave) {
            Intent intentEC = getIntent();
            String email = intentEC.getStringExtra("EMAIL");
            updatePrimaryDoctor(email);
            Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ViewProfileActivity.class);
            intent.putExtra("EMAIL", email);
            startActivity(intent);
            finish();
        }
    }

    private boolean selectItem(int position) {
//        Toast.makeText(this, mOptions[position], Toast.LENGTH_SHORT).show();
        Intent intent;
        Intent intentEmail = getIntent();
        String email = intentEmail.getStringExtra("EMAIL");
        switch (mOptions[position]) {
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
                intent = new Intent(this, ViewEmergencyContactActivity.class);
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
                intent = new Intent(this, generateQRActivity.class);
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
}
