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
        switch (item.getItemId()){
            case R.id.EditProfile:
                intent = new Intent(this, EditUserActivity.class);
                startActivity(intent);
                return true;
            case R.id.ViewAllergies:
                intent = new Intent(this, ViewAllergiesActivity.class);
                startActivity(intent);
                return true;
            case R.id.ViewPastOp:
                intent = new Intent(this, ViewPastOperationsActivity.class);
                startActivity(intent);
                return true;
            case R.id.ViewPastVisits:
                intent = new Intent(this, ViewPastVisitsActivity.class);
                startActivity(intent);
                return true;
            case R.id.generateQR:
                Intent intentQR = getIntent();
                String email = intentQR.getStringExtra("EMAIL");
                intent = new Intent(this,generateQRActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                return true;
            case R.id.ViewPrescriptions:
                intent = new Intent(this, ViewPrescriptionsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
