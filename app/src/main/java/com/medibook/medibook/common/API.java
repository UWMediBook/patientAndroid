package com.medibook.medibook.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
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
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.medibook.medibook.R;
import com.medibook.medibook.activity.RegisterMainActivity;
import com.medibook.medibook.activity.generateQRActivity;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Kevin on 6/19/2016.
 * <p/>
 * Modified by Jason on 7/13/2016
 * Modified by Jesper on 7/13/2016
 */
public class API {
    private RequestQueue queue;
    private View rootView;


    public API(Context context) {
        // Instantiate the RequestQueue.
        this.queue = Volley.newRequestQueue(context);
        this.rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
    }

    // Gets the user emergency contact information
    public void getEmergencyContact(int user_id) {
        String url = "http://52.41.78.184:8000/api/users/" + user_id + "/emergency_contacts/";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonContact = jsonArray.getJSONObject(0);
                            JSONObject jsonContactUID = jsonContact.getJSONObject("user");
                            Contact contact = new Contact(
                                    jsonContact.getInt("id"),
                                    jsonContactUID.getInt("id"),
                                    jsonContact.getString("first_name"),
                                    jsonContact.getString("last_name"),
                                    jsonContact.getString("phone_number"),
                                    jsonContact.getString("relationship")
                            );
                            TextView contactFirstName = (TextView) rootView.findViewById(R.id.first_name_ec);
                            TextView contactLastName = (TextView) rootView.findViewById(R.id.last_name_ec);
                            TextView contactPhoneNumber = (TextView) rootView.findViewById(R.id.phone_number_ec);
                            TextView contactRelationship = (TextView) rootView.findViewById(R.id.relationship_ec);

                            contactFirstName.setText(contact.getFirst_name());
                            contactLastName.setText(contact.getLast_name());
                            contactPhoneNumber.setText(contact.getPhone_number());
                            contactRelationship.setText(contact.getRelationship());

                        } catch (JSONException j) {
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
    public void updateEmergencyContact(final String first_name, final String last_name, final String phone_number, final String relationship, final int eid) {
        String EMERGENCY_CONTACT_URL = "http://52.41.78.184:8000/api/emergency_contacts/" + eid + "/";

        JSONObject params = new JSONObject();

        try {
            params.put("first_name", first_name);
            params.put("last_name", last_name);
            params.put("phone_number", phone_number);
            params.put("relationship", relationship);
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
                    Log.e("Volley", "Error. HTTP Status Code:" + networkResponse.statusCode);
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        this.queue.add(jor);
    }

    // Put Emergency Contact information about the user to the emergency contact database
    public void putEmergencyContact(final String first_name, final String last_name, final String phone_number, final String relationship, final int user_id) {
        String EMERGENCY_CONTACT_URL = "http://52.41.78.184:8000/api/emergency_contacts/";

        JSONObject params = new JSONObject();

        try {
            params.put("first_name", first_name);
            params.put("last_name", last_name);
            params.put("phone_number", phone_number);
            params.put("relationship", relationship);
            params.put("user_id", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.PUT, EMERGENCY_CONTACT_URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null) {
                    Log.e("Volley", "Error. HTTP Status Code:" + networkResponse.statusCode);
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        this.queue.add(jor);
    }

    // Put the Allergy information about the user to the allergy databsae
    public void putUserAllergy(final String allergy_name, final String severity, final int user_id) {
        String ALLERGY_URL = "http://52.41.78.184:8000/api/allergies/";

        JSONObject params = new JSONObject();

        try {
            params.put("name", allergy_name);
            params.put("severity", severity);
            params.put("user_id", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.PUT, ALLERGY_URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null) {
                    Log.e("Volley", "Error. HTTP Status Code:" + networkResponse.statusCode);
                }
            }
        }) {
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
        String url = "http://52.41.78.184:8000/api/users/" + user_id + "/allergies/";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            String allergyData = "";
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String severityData = "";
                                JSONObject jsonAllergy = jsonArray.getJSONObject(i);
                                JSONObject jsonObjectUID = jsonAllergy.getJSONObject("user");
                                Allergy allergy = new Allergy(
                                        jsonObjectUID.getInt("id"),
                                        jsonAllergy.getString("name"),
                                        jsonAllergy.getString("severity"),
                                        jsonAllergy.getInt("id")
                                );

                                if (allergy.getSeverity().equals("S") || allergy.getSeverity().equals("s")) {
                                    severityData = "Severe";
                                } else if (allergy.getSeverity().equals("m") || allergy.getSeverity().equals("M")) {
                                    severityData = "Mild";
                                } else {
                                    severityData = "Unknown";
                                }

                                allergyData = allergyData + "Allergy Name: " + allergy.getAllergyName() + "\nSeverity of Allergy: " + severityData + "\n \n";
                            }

                            TextView allergyInfo = (TextView) rootView.findViewById(R.id.tvAllergyList);

                            allergyInfo.setText(allergyData);

                        } catch (JSONException j) {
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

    // Put the users personal information entered by the user to the user database.
    public void putUser(final String fname, final String lname, final String address, final String gender, final Calendar birthday, final String email, final String password, final String healthcard) {
        String REGISTER_URL = "http://52.41.78.184:8000/api/users/";

        long dob = birthday.getTimeInMillis() / 1000;

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
            params.put("doctor_id", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.PUT, REGISTER_URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null) {
                    Log.e("Volley", "Error. HTTP Status Code:" + networkResponse.statusCode);
                }
                if (error instanceof TimeoutError) {
                    Log.e("Volley", "TimeoutError");
                } else if (error instanceof NoConnectionError) {
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
        }) {
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
    public void updateUser(final String fname, final String lname, final String address, final String gender, final Calendar birthday, final String email, final String password, final String healthcard, int user_id) {
        String REGISTER_URL = "http://52.41.78.184:8000/api/users/" + user_id;

        long dob = birthday.getTimeInMillis() / 1000;

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
            params.put("doctor_id", "1");
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
                    Log.e("Volley", "Error. HTTP Status Code:" + networkResponse.statusCode);
                }
                if (error instanceof TimeoutError) {
                    Log.e("Volley", "TimeoutError");
                } else if (error instanceof NoConnectionError) {
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
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
        this.queue.add(jor);
    }

    // Gets the users information from the users database
    public void doctorGetUser(int uid) {
        String url = "http://52.41.78.184:8000/api/users/" + uid;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonUser = new JSONObject(response);
                            User user = new User(
                                    jsonUser.getString("first_name"),
                                    jsonUser.getString("last_name"),
                                    jsonUser.getInt("id"),
                                    jsonUser.getString("gender"),
                                    jsonUser.getString("address"),
                                    jsonUser.getLong("birthday"),
                                    jsonUser.getString("email"),
                                    jsonUser.getString("password"),
                                    jsonUser.getString("healthcard"),
                                    null
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

                        } catch (JSONException j) {
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

    // Gets the users information from the users database
    public void getUserByEmail(String email_string) {
        String url = "http://52.41.78.184:8000/api/users/" + email_string + "/";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonUserData = new JSONObject(response);
                            User user = new User(
                                    jsonUserData.getString("first_name"),
                                    jsonUserData.getString("last_name"),
                                    jsonUserData.getInt("id"),
                                    jsonUserData.getString("gender"),
                                    jsonUserData.getString("address"),
                                    jsonUserData.getLong("birthday"),
                                    jsonUserData.getString("email"),
                                    jsonUserData.getString("password"),
                                    jsonUserData.getString("healthcard"),
                                    null
                            );
                            TextView userFirstName = (TextView) rootView.findViewById(R.id.user_first_name);
                            TextView userLastName = (TextView) rootView.findViewById(R.id.user_last_name);
                            TextView userGender = (TextView) rootView.findViewById(R.id.user_gender);
                            TextView userEmail = (TextView) rootView.findViewById(R.id.user_email);
                            TextView userAddress = (TextView) rootView.findViewById(R.id.user_address);
                            TextView userBirthday = (TextView) rootView.findViewById(R.id.user_birthday);
                            TextView userHealthcard = (TextView) rootView.findViewById(R.id.user_health_card);

                            userFirstName.setText(user.getFirst_name());
                            userLastName.setText(user.getLast_name());
                            userGender.setText(user.getGender());
                            userBirthday.setText(user.getBirthday());
                            userHealthcard.setText(user.getHealthcard());
                            userAddress.setText(user.getAddress());
                            userEmail.setText(user.getEmail());

                        } catch (JSONException j) {
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

    // Gets the users information from the users database
    public User getUserById(final Integer user_id) {
        String USER_URL = "http://52.41.78.184:8000/api/users/" + user_id + "/";

        // Request a string response from the provided URL.
        RequestFuture<JSONObject> future = RequestFuture.newFuture();

        JsonObjectRequest request = new JsonObjectRequest(USER_URL, null, future, future);
        this.queue.add(request);
        try {
            JSONObject jsonUser = future.get(5, TimeUnit.SECONDS); // Blocks for at most 5 seconds.
            try {
                JSONObject jsonDoctor = jsonUser.getJSONObject("doctor");
                Doctor doctor = new Doctor(
                        jsonDoctor.getInt("id"),
                        jsonDoctor.getString("first_name"),
                        jsonDoctor.getString("last_name"),
                        jsonDoctor.getString("address"),
                        jsonDoctor.getString("phonenumber")
                );
                User user = new User(
                        jsonUser.getString("first_name"),
                        jsonUser.getString("last_name"),
                        jsonUser.getInt("id"),
                        jsonUser.getString("gender"),
                        jsonUser.getString("address"),
                        jsonUser.getLong("birthday"),
                        jsonUser.getString("email"),
                        jsonUser.getString("password"),
                        jsonUser.getString("healthcard"),
                        doctor
                );
                return user;

            } catch (JSONException je) {
                je.printStackTrace();
                return null;
            }

        } catch (InterruptedException e) {
            // Exception handling
            e.printStackTrace();
        } catch (ExecutionException e) {
            // Exception handling
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Gets the users primary doctor information
    public void getPrimaryDoctor(int user_id) {
        String url = "http://52.41.78.184:8000/api/users/" + user_id + "/physicians/";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonPhysician = jsonArray.getJSONObject(0);
                            Doctor doctor = new Doctor(
                                    jsonPhysician.getInt("id"),
                                    jsonPhysician.getString("first_name"),
                                    jsonPhysician.getString("last_name"),
                                    jsonPhysician.getString("phone_number"),
                                    jsonPhysician.getString("address")
                            );

                            TextView doctorFirst = (TextView) rootView.findViewById(R.id.firstname_pd);
                            TextView doctorLast = (TextView) rootView.findViewById(R.id.lastname_pd);
                            TextView doctorPhone = (TextView) rootView.findViewById(R.id.phonenumber_pd);
                            TextView doctorAddress = (TextView) rootView.findViewById(R.id.address_pd);

                            doctorFirst.setText(doctor.getFirst());
                            doctorLast.setText(doctor.getLast());
                            doctorPhone.setText(doctor.getPhoneNumber());
                            doctorAddress.setText(doctor.getAddress());

                        } catch (JSONException j) {
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

    // Put the information entered by the user about their primary doctor to the doctor database
    public void putPrimaryDoctor(final String first_name, final String last_name, final String address, final String phone_number, final int user_id) {
        String DOCTOR_URL = "http://52.41.78.184:8000/api/physicians/";

        JSONObject params = new JSONObject();

        try {
            params.put("user_id", user_id);
            params.put("first_name", first_name);
            params.put("last_name", last_name);
            params.put("address", address);
            params.put("phone_number", phone_number);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.PUT, DOCTOR_URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null) {
                    Log.e("Volley", "Error. HTTP Status Code:" + networkResponse.statusCode);
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        this.queue.add(jor);
    }

    // Updates the Users Primary doctor information
    public void updateDoctor(final String first_name, final String last_name, final String address, final String phone_number,int doctor_id) {
        String DOCTOR_URL = "http://52.41.78.184:8000/api/physicians/"+ doctor_id+"/";

        JSONObject params = new JSONObject();

        try {
            params.put("first_name", first_name);
            params.put("last_name", last_name);
            params.put("address",address);
            params.put("phone_number",phone_number);
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
                    Log.e("Volley", "Error. HTTP Status Code:" + networkResponse.statusCode);
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        this.queue.add(jor);
    }

    // Gets the users prescriptions
    public void getPrescriptions(int user_id) {
        String url = "http://52.41.78.184:8000/api/users/" + user_id + "/prescriptions/";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            String prescriptionData = "";

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonPrescription = jsonArray.getJSONObject(i);
                                JSONObject jsonObjectUID = jsonPrescription.getJSONObject("user");
                                Prescription prescription = new Prescription(
                                        jsonPrescription.getInt("id"),
                                        jsonObjectUID.getInt("id"),
                                        jsonPrescription.getString("name"),
                                        jsonPrescription.getString("dosage")
                                );
                                prescriptionData = prescriptionData + "Prescription Name: " + prescription.getName() + "\nDosage for prescription: " + prescription.getPrescription() + "\n \n";
                            }

                            TextView prescriptionInfo = (TextView) rootView.findViewById(R.id.tvPrescriptionList);

                            prescriptionInfo.setText(prescriptionData);

                        } catch (JSONException j) {
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
    public void getPastOperations(int user_id) {
        String url = "http://52.41.78.184:8000/api/users/" + user_id + "/operations/";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            TextView operationName = (TextView) rootView.findViewById(R.id.tvPastOperationList);
                            String OperationData = "";
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjectOP = jsonArray.getJSONObject(i);
                                JSONObject jsonOperation = jsonObjectOP.getJSONObject("user");
                                Operation operation = new Operation(
                                        jsonObjectOP.getInt("id"),
                                        jsonOperation.getInt("id"),
                                        jsonObjectOP.getString("operation")
                                );
                                OperationData = OperationData + "Operation " + (i + 1) + ":\n" + operation.getOperation() + "\n \n";
                            }

                            operationName.setText(OperationData);
                        } catch (JSONException j) {
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
    public void getPastVisits(int user_id) {
        String url = "http://52.41.78.184:8000/api/users/" + user_id + "/visits/";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String visitData = "";
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjectVisit = jsonArray.getJSONObject(i);
                                JSONObject jsonVisit = jsonObjectVisit.getJSONObject("user");
                                Visit visit = new Visit(
                                        jsonVisit.getInt("id"),
                                        jsonObjectVisit.getInt("id"),
                                        jsonObjectVisit.getString("visit"),
                                        jsonObjectVisit.getLong("created_at")

                                );

                                visitData = visitData + "Visit on " + visit.getCreated() + ":\n" + visit.getVisit() + "\n \n";

                            }
                            TextView visitInfo = (TextView) rootView.findViewById(R.id.tvPastVisitList);

                            visitInfo.setText(visitData);

                        } catch (JSONException j) {
                            Log.e("JSON Conversion", "Failed to convert past visit to JSON");
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonUserID = jsonArray.getJSONObject(0);
                            callback.onSuccess(jsonUserID);
                        } catch (JSONException j) {
                            Log.e("JSON Conversion", "Failed to convert JSON to User");
                            j.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Get User API", "That didn't work!");
                error.printStackTrace();
            }
        });
        // Add the request to the RequestQueue.
        this.queue.add(stringRequest);
    }

    public void getECID(int uid, final DataCallback callback) {
        String url = "http://52.41.78.184:8000/api/users/" + uid + "/emergency_contacts/";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonECID = jsonArray.getJSONObject(0);
                            callback.onSuccess(jsonECID);
                        } catch (JSONException j) {
                            Log.e("JSON Conversion", "Failed to convert JSON to User");
                            j.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Get User API", "That didn't work!");
                error.printStackTrace();
            }
        });
        // Add the request to the RequestQueue.
        this.queue.add(stringRequest);
    }

    public void getPDID(int uid, final DataCallback callback){
        String url = "http://52.41.78.184:8000/api/users/" + uid + "/physicians/";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonPDID = jsonArray.getJSONObject(0);
                            callback.onSuccess(jsonPDID);
                        } catch (JSONException j) {
                            Log.e("JSON Conversion", "Failed to convert JSON to User");
                            j.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Get User API", "That didn't work!");
                error.printStackTrace();
            }
        });
        // Add the request to the RequestQueue.
        this.queue.add(stringRequest);
    }

    // waits to get the User id of the user then calls the put allergy method if successful
    public void putUserAllergyData(String email, String allergy, String severity) {
        final String userAllergy = allergy;
        final String userSeverity = severity;
        getUserId(email, new DataCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    int uid = result.getInt("pk");
                    putUserAllergy(userAllergy, userSeverity, uid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // waits to get the User id of the user then calls the post emergency contact method if successful
    public void putUserEmergContData(String email, String fname, String lname, String phone, String relationship) {
        final String userFirstName = fname;
        final String userLastName = lname;
        final String userPhoneNum = phone;
        final String userRelationship = relationship;

        getUserId(email, new DataCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    int uid = result.getInt("pk");
                    putEmergencyContact(userFirstName, userLastName, userPhoneNum, userRelationship, uid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void putUserPhysicianData(String email, String fname, String lname, String phone, String address) {
        final String PDfirst = fname;
        final String PDlast = lname;
        final String PDphone = phone;
        final String PDaddress = address;

        getUserId(email, new DataCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try{
                    int uid = result.getInt("pk");
                    putPrimaryDoctor(PDfirst, PDlast, PDphone, PDaddress, uid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // waits to get the user id of the user then calls the GET users emergency contact information
    public void getUserIdEmergencyContact(String email) {
        getUserId(email, new DataCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    int uid = result.getInt("pk");
                    getEmergencyContact(uid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Wait to get the user id of the user then calls the GET users Allergies
    public void getUserIdAllergies(String email) {
        getUserId(email, new DataCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    int uid = result.getInt("pk");
                    getAllergy(uid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Wait to get the user id of the user then calls the GET users Visits
    public void getUserIdVisits(String email) {
        getUserId(email, new DataCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    int uid = result.getInt("pk");
                    getPastVisits(uid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    // Wait to get the user id of the user then calls the GET users Prescriptions
    public void getUserIdPrescriptions(String email) {
        getUserId(email, new DataCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    int uid = result.getInt("pk");
                    getPrescriptions(uid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    // Wait to get the user id of the user then calls the GET users Operations
    public void getUserIdOperations(String email) {
        getUserId(email, new DataCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    int uid = result.getInt("pk");
                    getPastOperations(uid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    // Wait to get the user if of the user then calls the GET users Primary Doctor
    public void getUserIdPrimaryDoctor(String email) {
        getUserId(email, new DataCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    int uid = result.getInt("pk");
                    getPrimaryDoctor(uid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void postUserIdUpdateUser(String userEmail, final String fname, final String lname, final String address, final String gender, final Calendar birthday, final String email, final String password, final String healthcard) {
        getUserId(userEmail, new DataCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    int uid = result.getInt("pk");
                    updateUser(fname, lname, address, gender, birthday, email, password, healthcard, uid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void postUserIdUpdateEC(String email, final String first_name, final String last_name, final String relationship, final String phone_number) {
        getUserId(email, new DataCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    int uid = result.getInt("pk");
                    getECID(uid, new DataCallback() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            try {
                                int eid = result.getInt("id");
                                updateEmergencyContact(first_name, last_name, phone_number, relationship, eid);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void postUserIdUpdatePD(String email, final String first_name, final String last_name, final String Phone_number, final String address) {
        getUserId(email, new DataCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    int uid = result.getInt("pk");
                    getPDID(uid, new DataCallback() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            try {
                                int did = result.getInt("id");
                                updateDoctor(first_name, last_name, address, Phone_number, did);
                            }catch(JSONException e){
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Generates the users QR code Fwith the user id
    public void userGenerateQR(String email) {
        getUserId(email, new DataCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    int uid = result.getInt("pk");
                    String qrInputText = uid + "";

                    //Encode with a QR Code image
                    QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrInputText,
                            null,
                            Contents.Type.TEXT,
                            BarcodeFormat.QR_CODE.toString(),
                            150);
                    try {
                        Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
                        ImageView myImage = (ImageView) rootView.findViewById(R.id.ivQRcode);
                        myImage.setImageBitmap(bitmap);

                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public interface DataCallback {
        void onSuccess(JSONObject result);
    }

    public Integer authenticate(final String email, final String password) {
        String AUTHENTICATE_URL = "http://52.41.78.184:8000/api/authenticate/";

        JSONObject params = new JSONObject();

        try {
            params.put("email", email);
            params.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestFuture<JSONObject> future = RequestFuture.newFuture();

        JsonObjectRequest request = new JsonObjectRequest(AUTHENTICATE_URL, params, future, future);
        this.queue.add(request);
        try {
            JSONObject response = future.get(5, TimeUnit.SECONDS); // Blocks for at most 5 seconds.
            Integer is_doctor;
            try {
                is_doctor = response.getInt("is_doctor");
                return is_doctor;
            } catch (JSONException j) {
                j.printStackTrace();
                return -1;
            }
        } catch (InterruptedException e) {
            // Exception handling
            e.printStackTrace();
        } catch (ExecutionException e) {
            // Exception handling
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return -1;
    }


}