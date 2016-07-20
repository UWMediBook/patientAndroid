package com.medibook.medibook.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.medibook.medibook.R;
import com.medibook.medibook.common.API;
import com.medibook.medibook.common.IntentIntegrator;
import com.medibook.medibook.common.IntentResult;
import com.medibook.medibook.models.Allergy;
import com.medibook.medibook.models.Operation;
import com.medibook.medibook.models.Prescription;
import com.medibook.medibook.models.User;
import com.medibook.medibook.models.Visit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ScanActivity extends AppCompatActivity implements View.OnClickListener {

    public Button scanBtn;
    public API apiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.apiHandler = new API(this);

        scanBtn = (Button) findViewById(R.id.scan_button);
        scanBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.scan_button){
            //scan
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            //we have a result
            String scanContent = scanningResult.getContents();
            if(scanContent!= null){
                ScanTask scanTask = new ScanTask(this, Integer.parseInt(scanContent));
                scanTask.execute((Void) null);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Unable to parse contents!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    public class ScanTask extends AsyncTask<Void, Void, JSONObject> {


        private final Integer user_id;
        private Context context;

        ScanTask(Context context, Integer user_id) {
            this.user_id = user_id;
            this.context = context;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject user_data = new JSONObject();

            try{

                // User
                User user = apiHandler.getUserById(this.user_id);
                user_data.put("user", user.toJson());

                // Visits
                List<Visit> visits = apiHandler.getVisitsByUserId(user.getId());
                JSONArray json_visits = new JSONArray();
                for (int i=0; i < visits.size(); i++) {
                    json_visits.put(visits.get(i).toJson());
                }
                user_data.put("visits", json_visits);

                // Prescriptions
                List<Prescription> prescriptions = apiHandler.getPrescriptionsByUserId(user.getId());
                JSONArray json_prescriptions = new JSONArray();
                for (int i=0; i < prescriptions.size(); i++) {
                    json_prescriptions.put(prescriptions.get(i).toJson());
                }
                user_data.put("prescriptions", json_prescriptions);

                // Operations
                List<Operation> operations = apiHandler.getOperationsByUserId(user.getId());
                JSONArray json_operations = new JSONArray();
                for (int i=0; i < operations.size(); i++) {
                    json_operations.put(operations.get(i).toJson());
                }
                user_data.put("operations", json_operations);

                // Allergies
                List<Allergy> allergies = apiHandler.getAllergiesByUserId(user.getId());
                JSONArray json_allergies = new JSONArray();
                for (int i=0; i < allergies.size(); i++) {
                    json_allergies.put(allergies.get(i).toJson());
                }
                user_data.put("allergies", json_allergies);



            } catch (JSONException je){
                je.printStackTrace();
            }
            return user_data;

        }


        @Override
        protected void onPostExecute(final JSONObject user_data) {
            LinearLayout scan_visit_layout = (LinearLayout) findViewById(R.id.scan_visit_details);

            try{
                // User
                User user = User.fromJson(user_data.getString("user"));

                TextView userName = (TextView) findViewById(R.id.user_name);
                TextView userGender = (TextView) findViewById(R.id.user_gender);
                TextView userBirthday = (TextView) findViewById(R.id.user_birthday);
                TextView userHealthcard = (TextView) findViewById(R.id.user_healthcard);
                userName.setText("Name: " + user.getName());
                userGender.setText("Gender: " + user.getGender());
                userBirthday.setText("Birthday: " + user.getBirthday());
                userHealthcard.setText("Healthcard Number: " + user.getHealthcard());

                // Visits
                TextView info = new TextView(ScanActivity.this);
                String text = String.format("Past Visits");

                info.setText(text);
                info.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
                info.setWidth(scan_visit_layout.getWidth());
                scan_visit_layout.addView(info);

                JSONArray jsonVisits = user_data.getJSONArray("visits");
                for (int i = 0; i < jsonVisits.length(); i++) {
                    Visit visit = Visit.fromJson(jsonVisits.getString(i));

                    info = new TextView(ScanActivity.this);
                    text = String.format("Visit Date: %s \nVisit Details: %s \n", visit.getCreated(), visit.getVisit());

                    info.setText(text);
                    info.setWidth(scan_visit_layout.getWidth());
                    scan_visit_layout.addView(info);
                }

                // Prescriptions

                info = new TextView(ScanActivity.this);
                text = String.format("Patient's Prescriptions");

                info.setText(text);
                info.setWidth(scan_visit_layout.getWidth());
                info.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
                scan_visit_layout.addView(info);

                JSONArray jsonPrescriptions = user_data.getJSONArray("prescriptions");
                for (int i = 0; i < jsonPrescriptions.length(); i++) {
                    Prescription prescription = Prescription.fromJson(jsonPrescriptions.getString(i));

                    info = new TextView(ScanActivity.this);
                    text = String.format("Prescription Name: %s \nPrescription Dosage: %s \n", prescription.getName(), prescription.getDosage());

                    info.setText(text);
                    info.setWidth(scan_visit_layout.getWidth());
                    scan_visit_layout.addView(info);
                }

                // Operations

                info = new TextView(ScanActivity.this);
                text = String.format("Previous Operations");

                info.setText(text);
                info.setWidth(scan_visit_layout.getWidth());
                info.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
                scan_visit_layout.addView(info);
                JSONArray jsonOperations = user_data.getJSONArray("operations");
                for (int i = 0; i < jsonOperations.length(); i++) {
                    Operation operation = Operation.fromJson(jsonOperations.getString(i));

                    info = new TextView(ScanActivity.this);
                    text = String.format("Operation Date: %s \nOperation Details: %s \n", operation.getCreated(), operation.getOperation());

                    info.setText(text);
                    info.setWidth(scan_visit_layout.getWidth());
                    scan_visit_layout.addView(info);
                }

                // Allergies
                info = new TextView(ScanActivity.this);
                text = String.format("Allergies");

                info.setText(text);
                info.setWidth(scan_visit_layout.getWidth());
                info.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
                scan_visit_layout.addView(info);

                JSONArray jsonAllergies = user_data.getJSONArray("allergies");
                for (int i = 0; i < jsonAllergies.length(); i++) {
                    Allergy allergy = Allergy.fromJson(jsonAllergies.getString(i));

                    info = new TextView(ScanActivity.this);
                    text = String.format("Allergy Name: %s \nAllergy Severity: %s \n", allergy.getName(), allergy.getSeverity());

                    info.setText(text);
                    info.setWidth(scan_visit_layout.getWidth());
                    scan_visit_layout.addView(info);
                }

            } catch (JSONException je){
                je.printStackTrace();
            }

        }

        @Override
        protected void onCancelled() {

        }
    }
}
