package com.example.silentcalls;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PhoneCallActivity extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    private EditText edTextPhoneNumber;
    private Button btnMakeCall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_call);
        initVariables();
        btnMakeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    makePhoneCall();
            }
        });
    }

    private void makePhoneCall() {
        String userNumber = edTextPhoneNumber.getText().toString();
        if(userNumber.trim().length() > 0){
            if(ContextCompat.checkSelfPermission(PhoneCallActivity.this ,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(PhoneCallActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            } else {
                try {
                    String encodedNumberToCall = URLEncoder.encode("#31#" + userNumber, "UTF-8");
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + encodedNumberToCall));
                    startActivity(callIntent);
                } catch (UnsupportedEncodingException exception) {
                    Toast.makeText(PhoneCallActivity.this,"Wrong phone number",Toast.LENGTH_SHORT);
                }
            }
        } else {
            Toast.makeText(PhoneCallActivity.this,"Phone number cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED);
                makePhoneCall();
        } else {
            Toast.makeText(PhoneCallActivity.this,"Permission Denied",Toast.LENGTH_SHORT);
        }
    }

    private void initVariables(){
        edTextPhoneNumber = findViewById(R.id.edTextPhoneNumber);
        btnMakeCall = findViewById(R.id.btnMakeCall);
    }
}