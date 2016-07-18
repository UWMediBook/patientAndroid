package com.medibook.medibook.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.renderscript.Type;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;

import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.medibook.medibook.R;
import com.medibook.medibook.common.API;
import com.medibook.medibook.common.Contents;
import com.medibook.medibook.common.QRCodeEncoder;

public class generateQRActivity extends AppCompatActivity implements View.OnClickListener {

    private Button QRbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);

        QRbtn = (Button) findViewById(R.id.generateQRbtn);
        QRbtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v == QRbtn){
            Intent intentMD = getIntent();
            String email = intentMD.getStringExtra("EMAIL");
            API handler = new API(this);
            handler.userGenerateQR(email);
        }
    }

}
