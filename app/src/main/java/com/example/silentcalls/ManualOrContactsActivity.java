package com.example.silentcalls;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ManualOrContactsActivity extends AppCompatActivity {

    Button chooseFromContacts, enterNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_or_contacts);

        initButtons();
        chooseFromContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManualOrContactsActivity.this,ContactListActivity.class);
                startActivity(intent);
            }
        });
        enterNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManualOrContactsActivity.this,PhoneCallActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initButtons(){
        chooseFromContacts = findViewById(R.id.btnChooseFromContacts);
        enterNumber = findViewById(R.id.btnEnterNumber);
    }
}