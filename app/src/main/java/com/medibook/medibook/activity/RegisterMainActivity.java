package com.medibook.medibook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.medibook.medibook.R;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegisterMainActivity extends AppCompatActivity implements View.OnClickListener{
    String tag_json_obj = "json_obj_req";
    private static final String REGISTER_URL =  "http://52.41.78.184:8000/api/userviewset";

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

    private void registerUser(){

        RequestQueue rQueue = Volley.newRequestQueue(this);

        final String first_name = etFname.getText().toString().trim();
        final String last_name = etLname.getText().toString().trim();
        final String address = etAddress.getText().toString().trim();
        final String gender = etGender.getText().toString().trim();
        //final Date birthday = etDOB.getText().toString();
        final String email = etEmail.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();
        final String HealthCard = etHCNum.getText().toString().trim();

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, REGISTER_URL,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(RegisterMainActivity.this, "post success", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterMainActivity.this, "error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("first_name",first_name);
                params.put("last_name",last_name);
                params.put("address",address);
                params.put("gender",gender);
                params.put("birthday",null);
                params.put("email",email);
                params.put("password",password);
                params.put("healthcard",HealthCard);

                return params;

            }
        };
        rQueue.add(jor);
    }
    public void onClick(View v){
        if (v == butNext){
            registerUser();
            startActivity(new Intent(this,EmergencyContactActivity.class));
        }
    }
}
