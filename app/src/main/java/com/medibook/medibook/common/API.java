package com.medibook.medibook.common;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
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
import com.medibook.medibook.models.Contact;
import com.medibook.medibook.models.Doctor;
import com.medibook.medibook.models.Operation;
import com.medibook.medibook.models.Prescription;
import com.medibook.medibook.models.User;
import com.medibook.medibook.models.Visit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kevin on 6/19/2016.
 *
 * Modified by Jason on 7/13/2016
 * Modified by Jesper on 7/13/2016
 */
public class API {
    private RequestQueue queue;
    private View rootView;

    public API(Context context){
        // Instantiate the RequestQueue.
        this.queue = Volley.newRequestQueue(context);
        this.rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
    }

    // Gets the user emergency contact information
    public void getEmergencyContact(int user_id){
        String url = "http://52.41.78.184:8000/api/emergencycontacts/?user=" + user_id;

        // Request a string response from the provided URL.
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonContact = jsonArray.getJSONObject(0).getJSONObject("fields");
                            Contact contact = new Contact(
                                    jsonContact.getInt("id"),
                                    jsonContact.getInt("user"),
                                    jsonContact.getString("first_name"),
                                    jsonContact.getString("last_name"),
                                    jsonContact.getString("phone_number"),
                                    jsonContact.getString("relationship")
                            );
                            TextView contactName = (TextView) rootView.findViewById(R.id.user_name);
                            TextView contactPhoneNumber = (TextView) rootView.findViewById(R.id.user_gender);
                            TextView contactRelationship = (TextView) rootView.findViewById(R.id.user_email);

                            contactName.setText("Name: " + contact.getName());
                            contactPhoneNumber.setText("Phone Number: " + contact.getPhone_number());
                            contactRelationship.setText("Relationship: " + contact.getRelationship());

                        } catch(JSONException j){
                            Log.e("JSON Conversion", "Failed to convert JSON to User");
                            j.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Emergency Contact API", "That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        this.queue.add(stringRequest);
    }

    // Updates the users Emergency contact information
    public void updateEmergencyContact(){

    }

    // Post Emergency Contact information about the user to the emergency contact database
    public void postEmergencyContact(final String first_name,final String last_name, final String phone_number, final String relationship, final int user_id){
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

    // Post the Allergy information about the user to the allergy databsae
    public void postUserAllergy(final String allergy_name, final String severity, final int user_id){
        String ALLERGY_URL = "http://52.41.78.184:8000/api/allergyviewset/";

        JSONObject params = new JSONObject();

        try {
            params.put("name", allergy_name);
            params.put("severity", severity);
            params.put("user", user_id);
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

    // Gets the list of the users allergies along with the severity of the allergy
    public void getAllergy(int user_id) {
        String url = "http://52.41.78.184:8000/api/allergies/?user_id=" + user_id;

        // Request a string response from the provided URL.
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonAllergy = jsonArray.getJSONObject(0).getJSONObject("fields");
                           Allergy allergy = new Allergy(
                                   jsonAllergy.getInt("id"),
                                   jsonAllergy.getString("allergy"),
                                   jsonAllergy.getString("severity"),
                                   jsonAllergy.getInt("allergy_id")
                            );

                            TextView allergyName = (TextView) rootView.findViewById(R.id.user_name);
                            TextView allergySeverity = (TextView) rootView.findViewById(R.id.user_gender);

                            allergyName.setText("Name: " + allergy.getAllergyName());
                            allergySeverity.setText("Severity: " + allergy.getSeverity());

                        } catch(JSONException j){
                            Log.e("JSON Conversion", "Failed to convert JSON to User");
                            j.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("GET allergy API", "That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        this.queue.add(stringRequest);
    }

    // Posts the information entered by the user about their primary doctor to the doctor database
    public void postUserPrimaryDoctor(final String first_name,final String last_name){
        String DOCTOR_URL = "http://52.41.78.184:8000/api/doctors/";

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

    // Posts the users personal information entered by the user to the user database.
    public void postUser(final String fname, final String lname, final String address, final String gender, final Calendar birthday, final String email, final String password, final String healthcard){
        String REGISTER_URL = "http://52.41.78.184:8000/api/users/";

        String dob = String.valueOf(birthday.get(Calendar.YEAR)) + "/" + String.valueOf(birthday.get(Calendar.MONTH) + "/" + String.valueOf(birthday.get(Calendar.DAY_OF_MONTH)));


        JSONObject params = new JSONObject();

        try {
            params.put("first_name", fname);
            params.put("last_name", lname);
            params.put("address", address);
            params.put("gender", gender);
            params.put("birthday", dob);
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

    // Updates the user table
    public void updateUser(int user_id){

    }

    // Gets the users information from the users database
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

    // Gets the users primary doctor information
    public void getPrimaryDoctor(int doctor_id) {
        String url = "http://52.41.78.184:8000/api/primarydoctor/" + doctor_id;

        // Request a string response from the provided URL.
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonPrimaryDoctor = jsonArray.getJSONObject(0).getJSONObject("fields");
                            Doctor doctor = new Doctor(
                                    jsonPrimaryDoctor.getInt("id"),
                                    jsonPrimaryDoctor.getString("first_name"),
                                    jsonPrimaryDoctor.getString("last_name")
                            );

                            TextView doctorName = (TextView) rootView.findViewById(R.id.user_name);

                            doctorName.setText("Name: " + doctor.getName());

                        } catch(JSONException j){
                            Log.e("JSON Conversion", "Failed to convert JSON to User");
                            j.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("GET Doctor API", "That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        this.queue.add(stringRequest);
    }

    // Updates the Users Primary doctor information
    public void updateDoctor(){

    }

    // Gets the users prescriptions
    public void getPrescriptions(int user_id) {
        String url = "http://52.41.78.184:8000/api/prescriptions/?user_id=" + user_id;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonPrescription = jsonArray.getJSONObject(0).getJSONObject("fields");
                            Prescription prescription = new Prescription(
                                    jsonPrescription.getInt("id"),
                                    jsonPrescription.getInt("user"),
                                    jsonPrescription.getString("name"),
                                    jsonPrescription.getString("dosage")
                            );

                            TextView prescriptionName = (TextView) rootView.findViewById(R.id.user_name);
                            TextView prescriptionDosage = (TextView) rootView.findViewById(R.id.user_email);

                            prescriptionName.setText("Name of prescription: " + prescription.getName());
                            prescriptionDosage.setText("Dosage: " + prescription.getPrescription());

                        } catch(JSONException j){
                            Log.e("JSON Conversion", "Failed to convert allergy to JSON");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Get Prescription API", "That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        this.queue.add(stringRequest);
    }

    // Get the users past operations
    public void getPastOperations(int user_id){
        String url = "http://52.41.78.184:8000/api/operations/?user_id=" + user_id;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonOperation = jsonArray.getJSONObject(0).getJSONObject("fields");
                            Operation operation = new Operation(
                                    jsonOperation.getInt("id"),
                                    jsonOperation.getInt("user"),
                                    jsonOperation.getString("operation")
                            );

                            TextView operationName = (TextView) rootView.findViewById(R.id.user_name);

                            operationName.setText("Operation: " + operation.getOperation());

                        } catch(JSONException j){
                            Log.e("JSON Conversion", "Failed to convert allergy to JSON");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Get Operation API", "That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        this.queue.add(stringRequest);
    }

    // Gets the users past visits to a medical facility
    public void getPastVisits(int user_id){
        String url = "http://52.41.78.184:8000/api/visits/?user_id=" + user_id;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonVisit = jsonArray.getJSONObject(0).getJSONObject("fields");
                            Visit visit = new Visit(
                                    jsonVisit.getInt("id"),
                                    jsonVisit.getInt("user"),
                                    jsonVisit.getString("visit")
                            );

                            TextView visitInfo = (TextView) rootView.findViewById(R.id.user_name);

                            visitInfo.setText("Visit Information: " + visit.getVisit());

                        } catch(JSONException j){
                            Log.e("JSON Conversion", "Failed to convert allergy to JSON");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Get Past Visit API", "That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        this.queue.add(stringRequest);
    }

    // Gets the user id
    public void getUserId(String email_string, final DataCallback callback) {
        String url = "http://52.41.78.184:8000/api/users/?email=" + email_string;

        // Request a string response from the provided URL.
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonUserID = jsonArray.getJSONObject(0);
                            callback.onSuccess(jsonUserID);
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

    // waits to get the User id of the user then calls the post allergy method if successful
    public void userAllergyData(String email,String allergy,String severity){
        final String userAllergy = allergy;
        final String userSeverity = severity;
        getUserId(email, new DataCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try{
                    int uid = result.getInt("pk");
                    postUserAllergy(userAllergy,userSeverity,uid);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    // waits to get the User id of the user then calls the post emergency contact method if successful
    public void userEmergContData(String email, String fname, String lname, String phone, String relationship){
        final String userFirstName = fname;
        final String userLastName = lname;
        final String userPhoneNum = phone;
        final String userRelationship = relationship;

        getUserId(email, new DataCallback(){
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    int uid = result.getInt("pk");
                    postEmergencyContact(userFirstName,userLastName,userPhoneNum,userRelationship,uid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public interface DataCallback{
        void onSuccess (JSONObject result);
    }

}
