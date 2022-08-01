package com.example.qrcodesanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button scanBtn;
    TextView messageText, messageFormat;
    EditText numberCccdEdt,nameEdt,dateOfBirthEdt,sexEdt,placeOfResidenceEdt,numberCmndEct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        scanBtn.setOnClickListener(this);
    }

    private void addControls() {
        scanBtn = findViewById(R.id.scan_btn);
        messageText = findViewById(R.id.text_content);
        messageFormat = findViewById(R.id.text_format);
        numberCccdEdt= findViewById(R.id.set_numbercccd_edt);
        nameEdt= findViewById(R.id.set_name_edt);
        dateOfBirthEdt= findViewById(R.id.set_date_of_birth_edt);
        sexEdt= findViewById(R.id.set_sex_edt);
        placeOfResidenceEdt= findViewById(R.id.set_place_of_residence_edt);
        numberCmndEct= findViewById(R.id.set_number_cmnd_ect);
    }

    @Override
    public void onClick(View view) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setPrompt("Scan a barcode or QR Code");
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {

                String stringRequest=intentResult.getContents();
                String[] listData= stringRequest.split("\\|");
                numberCccdEdt.setText(listData[0]);
                nameEdt.setText(listData[2]);
                String dateOfBirth=listData[3];
                dateOfBirthEdt.setText(dateOfBirth.substring(0,2)+"/"+dateOfBirth.substring(2,4)+"/"+dateOfBirth.substring(4));
                sexEdt.setText(listData[4]);
                placeOfResidenceEdt.setText(listData[5]);
                numberCmndEct.setText(listData[6]);
                messageText.setText(intentResult.getContents());
                messageFormat.setText(intentResult.getFormatName());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}