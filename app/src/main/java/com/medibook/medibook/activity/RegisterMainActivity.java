package com.medibook.medibook.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.medibook.medibook.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegisterMainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etFname;
    private EditText etLname;
    private EditText etAddress;
    private EditText etGender;
    private EditText etDOB;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etHCNum;

    private Button butNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_main);

        etFname = (EditText) findViewById(R.id.etFname);
        etLname = (EditText) findViewById(R.id.etLname);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etGender = (EditText) findViewById(R.id.etGender);
        etDOB = (EditText) findViewById(R.id.etBirthday);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etHCNum = (EditText) findViewById(R.id.etHCNumber);

        butNext = (Button) findViewById(R.id.butNext);

        butNext.setOnClickListener(this);
    }

    private void registerUser() {
        String REGISTER_URL = "http://52.41.78.184:8000/api/users/";
        RequestQueue rQueue = Volley.newRequestQueue(this);

        String first_name = etFname.getText().toString().trim();
        String last_name = etLname.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String gender = etGender.getText().toString().trim();
        String birthday = etDOB.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String HealthCard = etHCNum.getText().toString().trim();
        JSONArray jar = new JSONArray();
        JSONObject params = new JSONObject();
        try {
            params.put("first_name", first_name);
            params.put("last_name", last_name);
            params.put("address", address);
            params.put("gender", gender);
            params.put("birthday", birthday);
            params.put("email", email);
            params.put("password", password);
            params.put("healthcard", HealthCard);
            params.put("doctor_id","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jar.put(params);

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, REGISTER_URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(RegisterMainActivity.this, "post success", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterMainActivity.this, "error", Toast.LENGTH_SHORT).show();
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null) {
                    Log.e("Volley", "Error. HTTP Status Code:"+networkResponse.statusCode);
                }

                if (error instanceof TimeoutError) {
                    Log.e("Volley", "TimeoutError");
                }else if(error instanceof NoConnectionError){
                    Log.e("Volley", "NoConnectionError");
                } else if (error instanceof AuthFailureError) {
                    Log.e("Volley", "AuthFailureError");
                } else if (error instanceof ServerError) {
                    Log.e("Volley", "ServerError");
                } else if (error instanceof NetworkError) {
                    Log.e("Volley", "NetworkError");
                } else if (error instanceof ParseError) {
                    Log.e("Volley", "ParseError");
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
        rQueue.add(jor);
    }

    public void onClick(View v) {
        if (v == butNext) {
            registerUser();
            Intent intent = new Intent(RegisterMainActivity.this, EmergencyContactActivity.class);
            intent.putExtra("EMAIL", etEmail.getText().toString());
            startActivity(intent);
        }
    }
}
