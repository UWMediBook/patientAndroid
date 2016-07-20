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


public class EditEmergencyContactActivity extends AppCompatActivity implements View.OnClickListener{

    private String[] mOptions;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private EditText fname;
    private EditText lname;
    private EditText relationship;
    private EditText phonenumber;

    private Button btnSave;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_emergency_contact);

        fname = (EditText) findViewById(R.id.first_name_ec);
        lname = (EditText) findViewById(R.id.last_name_ec);
        relationship = (EditText) findViewById(R.id.relationship_ec);
        phonenumber = (EditText) findViewById(R.id.phone_number_ec);
        btnSave = (Button) findViewById(R.id.btnSave);

        mOptions = getResources().getStringArray(R.array.optionsArray);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mOptions));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        Intent intentEC = getIntent();
        userEmail = intentEC.getStringExtra("EMAIL");
        API handlerID = new API(this);
        handlerID.getUserIdEmergencyContact(userEmail);

        btnSave.setOnClickListener(this);
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
                return true;
            case "Edit Profile":
                intent = new Intent(this, EditUserActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                return true;
            case "Allergies":
                intent = new Intent(this, ViewAllergiesActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                return true;
            case "Emergency Contact":
                intent = new Intent(this,ViewEmergencyContactActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                return true;
            case "Past Operations":
                intent = new Intent(this, ViewPastOperationsActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                return true;
            case "Past Visits":
                intent = new Intent(this, ViewPastVisitsActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                return true;
            case "Generate QR Code":
                intent = new Intent(this,generateQRActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                return true;
            case "Prescriptions":
                intent = new Intent(this, ViewPrescriptionsActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                return true;
            case "Primary Doctor":
                intent = new Intent(this, ViewPrimaryDoctorActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                return true;
            case "Edit Primary Doctor":
                intent = new Intent(this, EditPrimaryDoctorActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                return true;
            case "Edit Emergency Contact":
                intent = new Intent(this, EditEmergencyContactActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                return true;
        }
        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
        return true;
    }
    public void updateEC(String email){
        String first_name = fname.getText().toString().trim();
        String last_name = lname.getText().toString().trim();
        String uRelationship = relationship.getText().toString().trim();
        String phone_number = phonenumber.getText().toString().trim();

        API handler = new API(this);
        handler.postUserIdUpdateEC(email,first_name,last_name,uRelationship,phone_number);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSave){
            updateEC(userEmail);
            Toast.makeText(EditEmergencyContactActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ViewProfileActivity.class);
            intent.putExtra("EMAIL", userEmail);
            startActivity(intent);
        }
    }
}
