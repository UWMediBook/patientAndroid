package com.medibook.medibook.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.medibook.medibook.R;
import com.medibook.medibook.common.API;

public class ViewProfileActivity extends AppCompatActivity {
    private String name;
    private String email;
    private String birthday;
    private String gender;
    private String healthcard;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

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
                intent = new Intent(this, UserAreaActivity.class);
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
            case R.id.ViewPrescriptions:
                intent = new Intent(this, ViewPrescriptionsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//            return true;
//        } else
        if (id==R.id.action_view_profile){
            Intent intent = new Intent(this, UserAreaActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id==R.id.action_show_allergy){
            Intent intent = new Intent(this, ViewAllergiesActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
