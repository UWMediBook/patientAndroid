package com.medibook.medibook.common;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.medibook.medibook.R;
import com.medibook.medibook.activity.RegisterMainActivity;
import com.medibook.medibook.models.Allergy;
import com.medibook.medibook.models.Doctor;
import com.medibook.medibook.models.Prescription;
import com.medibook.medibook.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kevin on 6/19/2016.
 */
public class API {
    private RequestQueue queue;
    private View rootView;

    public API(Context context){
        // Instantiate the RequestQueue.
        this.queue = Volley.newRequestQueue(context);
        this.rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
    }

    /*
    public void postUser(final String fname, final String lname, final String address, final String gender, final String email, final String password, final String healthcard){
        showProgessDialog();
        String url = "http://52.41.78.184:8000/api/userviewset/";
        JsonObjectRequest JOR = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d(TAG, response.toString());
            },new Response.ErrorListener()
            {

            }
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("first_name",fname);
                    params.put("last_name",lname);
                    params.put("address",address);
                    params.put("gender",gender);
                    params.put("birthday",null);
                    params.put("email",email);
                    params.put("password",password);
                    params.put("healthcard",healthcard);

                    return params;
            }
        }
        });
    } */

    public void getUser(String email_string) {
        String url = "http://52.41.78.184:8000/api/users/?email=" + email_string;
        System.out.println(url);
        // Request a string response from the provided URL.
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonUserID = jsonArray.getJSONObject(0);
                            JSONObject jsonUser = jsonArray.getJSONObject(0).getJSONObject("fields");
                            User user = new User(
                                    jsonUser.getString("first_name"),
                                    jsonUser.getString("last_name"),
                                    jsonUserID.getInt("pk"),
                                    jsonUser.getString("gender"),
                                    jsonUser.getString("address"),
                                    jsonUser.getString("birthday"),
                                    jsonUser.getString("email"),
                                    jsonUser.getString("password"),
                                    jsonUser.getString("healthcard")
                            );
                            TextView userName = (TextView) rootView.findViewById(R.id.user_name);
                            TextView userGender = (TextView) rootView.findViewById(R.id.user_gender);
                            TextView userEmail = (TextView) rootView.findViewById(R.id.user_email);
                            TextView userAddress = (TextView) rootView.findViewById(R.id.user_address);
                            TextView userBirthday = (TextView) rootView.findViewById(R.id.user_birthday);
                            TextView userHealthcard = (TextView) rootView.findViewById(R.id.user_health_card);

                            userName.setText("Name: " + user.getName());
                            userGender.setText("Gender: " + user.getGender());
                            userBirthday.setText("Birthday: " + user.getBirthday());
                            userHealthcard.setText("Healthcard Number: " + user.getHealthcard());
                            userAddress.setText("Address: " + user.getAddress());
                            userEmail.setText("Email: " + user.getEmail());

                        } catch(JSONException j){
                            Log.e("JSON Conversion", "Failed to convert JSON to User");
                            j.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Get User API", "That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        this.queue.add(stringRequest);
    }


    public void getAllergiesByUser(Integer user_id) {
        String url = "http://52.41.78.184:8000/api/allergies/?user_id=" + user_id;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            //TODO: complete the assignment
                            JSONArray jsonAllergies = new JSONArray(response);
                            Allergy allergy = new Allergy(1, "", 1);

                        } catch(JSONException j){
                            Log.e("JSON Conversion", "Failed to convert allergy to JSON");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Get Allergy API", "That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        this.queue.add(stringRequest);
    }

    public void getDoctor(Integer doctor_id) {
        String url = "http://52.41.78.184:8000/api/primarydoctor/" + doctor_id;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            //TODO: complete this
                            JSONObject jsonDoctor = new JSONObject(response);
                            Doctor doctor = new Doctor(1, "Kevin", "Yang");

                        } catch(JSONException j){
                            Log.e("JSON Conversion", "Failed to convert allergy to JSON");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Get Allergy API", "That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        this.queue.add(stringRequest);
    }

    public void getPrescriptionsByUser(Integer user_id) {
        String url = "http://52.41.78.184:8000/api/prescriptions/?user_id=" + user_id;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            //TODO: complete this
                            JSONArray jsonPrescriptions = new JSONArray(response);
                            Prescription prescription = new Prescription(1, 1, "");

                        } catch(JSONException j){
                            Log.e("JSON Conversion", "Failed to convert allergy to JSON");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Get Allergy API", "That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        this.queue.add(stringRequest);
    }

}
