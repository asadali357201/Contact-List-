package com.example.contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    TextView nametext,numbertext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nametext = findViewById(R.id.nametext);
        numbertext = findViewById(R.id.numbertext);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS)!=
                PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},1);

        }
        else {
            getContacts();
        }
    }

    private void getContacts() {
        //        talinh all mobile contact in cursor variable
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while(phones.moveToNext()) {
//            store name and number by getting column indexes
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            nametext.append(name+"-->"+number+"\n");

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getContacts();
            }

        }
    }
}
