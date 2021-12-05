package com.example.silentcalls;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ManualOrContactsActivity extends AppCompatActivity {

    Button chooseFromContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_or_contacts);

        initButtons();
        chooseFromContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ManualOrContactsActivity.this,"Not yet implemented",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initButtons(){
        chooseFromContacts = findViewById(R.id.btnChooseFromContacts);
    }
}