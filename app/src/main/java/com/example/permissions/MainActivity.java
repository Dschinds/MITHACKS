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
import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;


import android.os.Bundle;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.MultiplePermissionsReport;

import java.security.Permissions;

public class MainActivity extends AppCompatActivity {

    private Button btn_grant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(MainActivity.this  , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
      && ContextCompat.checkSelfPermission(MainActivity.this  , Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)  {
            finish();
        }


        btn_grant = findViewById(R.id.btn_grant);


        btn_grant.setOnClickListener(new View.OnClickListener(){
         @Override
            public void onClick(View v){
             Dexter.withActivity(MainActivity.this) .withPermissions(
                     Manifest.permission.ACCESS_FINE_LOCATION,
                     Manifest.permission.CAMERA
             ).withListener(new MultiplePermissionsListener() {
                 @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                     startActivity(new Intent(MainActivity.this,MapActivity.class));
                     finish();
                    }
                 @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                     token.continuePermissionRequest();
                 }
             }).check();

         }
        });
    }
}
