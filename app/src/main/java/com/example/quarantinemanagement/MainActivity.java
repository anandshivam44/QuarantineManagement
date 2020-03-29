package com.example.quarantinemanagement;

import android.Manifest;
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
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class MainActivity extends AppCompatActivity {
    private int PERMISSION_CODE = 1;
    private ImageView SMS, Battery, GPS, autoStart, btn5, btn6;
    PowerManager pm;
    Button nextButton;
    String[] perms = {"android.permission.FINE_LOCATION","android.permission.COARSE_LOCATION"};
    int permsRequestCode = 200;


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
                requestPermissions(perms, permsRequestCode);



            }
        });

    }

//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //btn1.setBackgroundColor(Color.parseColor("#27ae60"));
//                btn1.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_back3));
//
//
//                    if(ContextCompat.checkSelfPermission(MainActivity.this,
//                            Manifest.permission.INTERNET)== PackageManager.PERMISSION_GRANTED)
//                    {
//                        Toast.makeText(MainActivity.this,"permission already granted",Toast.LENGTH_SHORT).show();
//                    }
//                    else
//                    {
//                        requestPermission();
//                    }
//
//            }
//        });
//
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btn2.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_back3));
//                startActivity(new Intent(MainActivity.this,DrawerActivity.class));
//            }
//        });
//        btn3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btn3.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_back3));
//                startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
//            }
//        });
//        btn4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btn4.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_back3));
//                startActivity(new Intent(MainActivity.this,Telephone.class));
//
//
//            }
//        });
//        btn5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btn5.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_back3));
//            }
//        });
//
//        btn6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btn6.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_back3));
//            }
//        });


    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
            new AlertDialog.Builder(this)
                    .setTitle("permission needed")
                    .setMessage("kindly grant permission")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.INTERNET}, PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();


        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PERMISSION_CODE);
        }

    }


    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults){

        switch(permsRequestCode){

            case 200:

                boolean locationAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                boolean cameraAccepted = grantResults[1]==PackageManager.PERMISSION_GRANTED;

                break;

        }

    }
}
