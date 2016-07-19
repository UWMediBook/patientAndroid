package com.medibook.medibook.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.medibook.medibook.R;
import com.medibook.medibook.common.API;
import com.medibook.medibook.common.IntentIntegrator;
import com.medibook.medibook.common.IntentResult;
import com.medibook.medibook.models.User;

public class ScanActivity extends AppCompatActivity implements View.OnClickListener {

    public Button scanBtn;
    public API apiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        scanBtn = (Button) findViewById(R.id.scan_button);
        scanBtn.setOnClickListener(this);

        this.apiHandler = new API(this);

    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.scan_button){
            //scan
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }

    public class ScanTask extends AsyncTask<Void, Void, User> {

        private final Integer user_id;

        ScanTask(Integer user_id) {
            this.user_id = user_id;
        }

        @Override
        protected User doInBackground(Void... params) {
            return apiHandler.getUserById(this.user_id);
        }

        @Override
        protected void onPostExecute(final User user) {
            Log.e("SCAN", user.toJson());
        }

        @Override
        protected void onCancelled() {

        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            //we have a result
            String scanContent = scanningResult.getContents();
            Log.e("scan", scanContent);
            String scanFormat = scanningResult.getFormatName();
            if(scanContent!= null && scanFormat != null){
                ScanTask scanTask = new ScanTask(Integer.parseInt(scanContent));
                scanTask.execute((Void) null);
            }
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
