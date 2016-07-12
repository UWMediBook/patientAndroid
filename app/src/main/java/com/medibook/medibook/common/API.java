package com.medibook.medibook.common;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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

    public void postEmergencyContact(final String first_name,final String last_name, final String phone_number, final String relationship, final String user_id){
        String EMERGENCY_CONTACT_URL = "http://52.41.78.184:8000/api/emergencycontactviewset/";

        JSONObject params = new JSONObject();

        try {
            params.put("first_name", first_name);
            params.put("last_name", last_name);
            params.put("phone_number", phone_number);
            params.put("relationship", relationship);
            params.put("user", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, EMERGENCY_CONTACT_URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null) {
                    Log.e("Volley", "Error. HTTP Status Code:"+networkResponse.statusCode);
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
        this.queue.add(jor);
    }

    public void postUserAllergy(final String allergy_name, final String severity, final Integer user_id){
        String ALLERGY_URL = "http://52.41.78.184:8000/allergies/";

        JSONObject params = new JSONObject();

        try {
            params.put("first_name", allergy_name);
            params.put("severity", severity);
            params.put("user_id", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, ALLERGY_URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null) {
                    Log.e("Volley", "Error. HTTP Status Code:"+networkResponse.statusCode);
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
        this.queue.add(jor);
    }

    public void postUserPrimaryDoctor(final String first_name,final String last_name){
        String DOCTOR_URL = "http://52.41.78.184:8000/doctors/";

        JSONObject params = new JSONObject();

        try {
            params.put("first_name", first_name);
            params.put("last_name", last_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, DOCTOR_URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null) {
                    Log.e("Volley", "Error. HTTP Status Code:"+networkResponse.statusCode);
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
        this.queue.add(jor);
    }

    public void postUser(final String fname, final String lname, final String address, final String gender, final String birthday, final String email, final String password, final String healthcard){
        String REGISTER_URL = "http://52.41.78.184:8000/api/users/";

        JSONObject params = new JSONObject();

        try {
            params.put("first_name", fname);
            params.put("last_name", lname);
            params.put("address", address);
            params.put("gender", gender);
            params.put("birthday", birthday);
            params.put("email", email);
            params.put("password", password);
            params.put("healthcard", healthcard);
            params.put("doctor_id","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, REGISTER_URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
        this.queue.add(jor);
    }

    public void getUser(String email_string) {
        String url = "http://52.41.78.184:8000/api/users/?email=" + email_string;

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

    public Integer getUserID(String email){
        String url = "http://52.41.78.184:8000/api/users/?email=" + email;
        final int[] user_id = new int[1];
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
                            user_id[0] = user.getId();
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

        return user_id[0];
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
