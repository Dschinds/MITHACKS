package com.example.permissions;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.*;
import android.view.*;
import android.provider.Settings;



import android.os.Bundle;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.security.Permissions;

public class MainActivity extends AppCompatActivity {

    private Button btn_grant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(MainActivity.this  , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            finish();
        }
        btn_grant = findViewById(R.id.btn_grant);


        btn_grant.setOnClickListener(new View.OnClickListener(){
         @Override
            public void onClick(View v){
             Dexter.withActivity(MainActivity.this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
                 @Override
                 public void onPermissionGranted(PermissionGrantedResponse response) {
                   startActivity(new Intent(MainActivity.this,MapActivity.class));
                   finish();
                 }

                 @Override
                 public void onPermissionDenied(PermissionDeniedResponse response) {
                    if(response.isPermanentlyDenied()){
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Permission denied").setMessage("Permission is permanently denied go to settings")
                                .setNegativeButton("cancel",null)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                     Intent intent = new Intent();
                                     intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                     intent.setData(Uri.fromParts("package",getPackageName(),null));
                                    }
                                }).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Permissions Denied", Toast.LENGTH_SHORT).show();
                    }
                 }

                 @Override
                 public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                    token.continuePermissionRequest();
                 }
             }).check();
         }
        });
    }
}