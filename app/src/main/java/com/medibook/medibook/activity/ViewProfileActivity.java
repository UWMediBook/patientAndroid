package com.medibook.medibook.activity;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.medibook.medibook.R;
import com.medibook.medibook.common.API;

public class ViewProfileActivity extends AppCompatActivity {
    private String[] mOptions;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        refresh();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    private boolean selectItem(int position) {
//        Toast.makeText(this, mOptions[position], Toast.LENGTH_SHORT).show();
        Intent intent;
        Intent intentEmail = getIntent();
        String email = intentEmail.getStringExtra("EMAIL");
        switch (mOptions[position]){
            case "Profile":
                mDrawerList.setItemChecked(position, true);
                mDrawerLayout.closeDrawer(mDrawerList);
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

    @Override
    protected void onResume(){
        super.onResume();
        refresh();
    }

    private void refresh() {
        setContentView(R.layout.activity_view_profile);

        mOptions = getResources().getStringArray(R.array.optionsArray);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mOptions));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        Intent intent = getIntent();
        String email = intent.getStringExtra("EMAIL");
        API apiHandler = new API(this);
        apiHandler.getUserByEmail(email);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Overflow menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case R.id.Refresh:
                refresh();
                return true;
        }
        return true;
    }
}
