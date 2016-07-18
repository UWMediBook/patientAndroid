package com.medibook.medibook.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;


import com.medibook.medibook.R;
import com.medibook.medibook.common.API;

public class ViewProfileActivity extends AppCompatActivity {
    private String[] mOptions;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private Button btnEditProfile;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        apiHandler.getUser(email);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Overflow menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.popupmenu, menu);

        return true;
    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        Toast.makeText(this, R.string.app_name, Toast.LENGTH_SHORT).show();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
//        setTitle(mOptions[position]);
        Log.d("drawer",mOptions[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
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
