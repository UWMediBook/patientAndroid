package com.medibook.medibook.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.medibook.medibook.R;
import com.medibook.medibook.common.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        Intent intent = getIntent();
        String email = intent.getStringExtra("EMAIL");
        API apiHandler = new API(this);
        apiHandler.getUser(email);

        PopupMenu popup = new PopupMenu(UserAreaActivity.this, button);
    }
}
