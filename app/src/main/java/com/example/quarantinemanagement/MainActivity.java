package com.example.quarantinemanagement;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import java.lang.SecurityException;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {
    private int PERMISSION_CODE = 1;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private ImageView SMS, Battery, GPS, autoStart, btn5, btn6;
    PowerManager pm;
    Button nextButton;
    String[] perms = {"android.permission.FINE_LOCATION","android.permission.COARSE_LOCATION"};
    int permsRequestCode = 200;
    private Context context;
    private Activity activity;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private View view;


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        activity = this;

        SMS = findViewById(R.id.give_permission_sms);
        Battery = findViewById(R.id.give_permission_battery);
        GPS = findViewById(R.id.give_permission_gps);
        autoStart = findViewById(R.id.give_permission_autostart);
        nextButton = findViewById(R.id.next);
        pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);

        Battery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String PACKAGE_NAME = getApplicationContext().getPackageName();
                boolean status = false;
                status = pm.isIgnoringBatteryOptimizations(PACKAGE_NAME);
                if (!status) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                    startActivity(intent);
                }
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, otp.class));
            }
        });
        GPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean b= checkPermissionGPS();
                if (b==false){
                    requestPermissionGPS();
                }

            }
        });
        SMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b=checkPermissionSendSMS();
                if (b){
                    requestPermissionSendSMS();
                }

                boolean c= checkPermissionReadSMS();
                if (c==false){
                    requestPermissionReceiveSMS();
                }

            }
        });

    }

    private boolean checkPermissionSendSMS() {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS);
        if (result == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {

            return false;

        }
    }

    private boolean checkPermissionGPS(){
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {

            return false;

        }
    }
    private boolean checkPermissionReadSMS(){
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS);
        if (result == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {

            return false;

        }
    }



    private void requestPermissionGPS(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.ACCESS_FINE_LOCATION)){

            Toast.makeText(context,"GPS permission allows us to access location data. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE);
        }
    }
    private void requestPermissionReceiveSMS(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.READ_SMS)){

            Toast.makeText(context,"GPS permission allows us to access location data. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.READ_SMS},PERMISSION_REQUEST_CODE);
        }
    }
    private void requestPermissionSendSMS(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.SEND_SMS)){

            Toast.makeText(context,"GPS permission allows us to access location data. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.SEND_SMS},PERMISSION_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                   // Snackbar.make(view,"Permission Granted, Now you can access location data.",Snackbar.LENGTH_LONG).show();

                } else {

                   // Snackbar.make(view,"Permission Denied, You cannot access location data.",Snackbar.LENGTH_LONG).show();

                }
                break;
        }
    }



}
