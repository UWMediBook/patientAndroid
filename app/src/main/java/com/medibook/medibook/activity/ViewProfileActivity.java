package com.medibook.medibook.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.medibook.medibook.R;
import com.medibook.medibook.common.API;

public class ViewProfileActivity extends AppCompatActivity {

    private Button btnEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        Intent intent = getIntent();
        String email = intent.getStringExtra("EMAIL");
        API apiHandler = new API(this);
        apiHandler.getUser(email);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Overflow menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.popupmenu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        Intent intentEmail = getIntent();
        String email = intentEmail.getStringExtra("EMAIL");
        switch (item.getItemId()){
            case R.id.EditProfile:
                intent = new Intent(this, EditUserActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                return true;
            case R.id.ViewAllergies:
                intent = new Intent(this, ViewAllergiesActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                return true;
            case R.id.ViewEmergencyContact:
                intent = new Intent(this,ViewEmergencyContactActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                return true;
            case R.id.ViewPastOp:
                intent = new Intent(this, ViewPastOperationsActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                return true;
            case R.id.ViewPastVisits:
                intent = new Intent(this, ViewPastVisitsActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                return true;
            case R.id.generateQR:
                intent = new Intent(this,generateQRActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                return true;
            case R.id.ViewPrescriptions:
                intent = new Intent(this, ViewPrescriptionsActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                return true;
            case R.id.ViewPrimaryDoctor:
                intent = new Intent(this, ViewPrimaryDoctorActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
