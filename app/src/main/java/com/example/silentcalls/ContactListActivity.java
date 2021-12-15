package com.example.silentcalls;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;
    private RecyclerView recyclerView;
    private ArrayList<ContactModel> contactModels = new ArrayList<ContactModel>();
    private RecyclerVAdapter recyclerVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        recyclerView = findViewById(R.id.recyclerView);

        checkPermission();
    }

    private void checkPermission() {
        if(ContextCompat.checkSelfPermission(ContactListActivity.this,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ContactListActivity.this,
                    new String[]{Manifest.permission.READ_CONTACTS},REQUEST_CALL);
        } else {
            getContactsList();
        }
    }

    private void getContactsList() {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC";
        Cursor cursor = getContentResolver().query(
                uri,null,null,null,sort);
        if(cursor.getCount() > 0 ) {
            while(cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Uri uriPhone = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" =?";
                Cursor phoneCursor = getContentResolver().query(uriPhone,null,
                        selection,new String[]{id},null);
                if(phoneCursor.moveToNext()) {
                    String number = phoneCursor.getString(phoneCursor.
                            getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    ContactModel contactModel = new ContactModel(name,number);
                    contactModels.add(contactModel);
                    phoneCursor.close();
                }
            }
            cursor.close();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerVAdapter = new RecyclerVAdapter(this,contactModels,this);
        recyclerView.setAdapter(recyclerVAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CALL && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getContactsList();
        } else {
            Toast.makeText(ContactListActivity.this,"Permission Denied",Toast.LENGTH_SHORT);
            checkPermission();
        }
    }
}