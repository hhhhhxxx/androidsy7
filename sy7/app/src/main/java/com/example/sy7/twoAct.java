package com.example.sy7;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class twoAct extends Activity {

    Uri uri;
    ContentResolver resolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.two);

        uri = Uri.parse("content://com.sy7");
        resolver = getContentResolver();


        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_CONTACTS},1);
        } else {
            Log.d("--------------", "成");
            readContacts();

            Cursor query = resolver.query(uri, null, null, null, null);

            while (query.moveToNext()) {
                @SuppressLint("Range") String name = query.getString(query.getColumnIndex("name"));
                @SuppressLint("Range") String phone = query.getString(query.getColumnIndex("phone"));

                Log.d("))))", name + phone);
            }
        }
    }

    private void readContacts() {
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,null,null,null);
            if(cursor != null) {

                ContentValues values = new ContentValues();

                while(cursor.moveToNext()) {
                    @SuppressLint("Range")
                    String displayName = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                    @SuppressLint("Range")
                    String number = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER));

//                    contactsList.add(displayName+"\n"+number);
                    values.put("name",displayName);
                    values.put("phone",number);
                    resolver.insert(uri,values);
                }
            }
        }catch (Exception e) {

        } finally {
            if(cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                } else {
                    Toast.makeText(this, "没有权限", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
        }
    }
}
