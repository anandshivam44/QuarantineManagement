package com.example.quarantinemanagement;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private  int PERMISSION_CODE=1;
     private Button btn1,btn2,btn3,btn4,btn5,btn6,permission_to_otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn1=(Button)findViewById(R.id.permission1);
        btn2=(Button)findViewById(R.id.permission2);
        btn3=(Button)findViewById(R.id.permission3);
        btn4=(Button)findViewById(R.id.permission4);
        btn5=(Button)findViewById(R.id.permission5);
        btn6=(Button)findViewById(R.id.permission6);
        permission_to_otp=(Button)findViewById(R.id.permission_to_otp);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //btn1.setBackgroundColor(Color.parseColor("#27ae60"));
                btn1.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_back3));


                    if(ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.INTERNET)== PackageManager.PERMISSION_GRANTED)
                    {
                        Toast.makeText(MainActivity.this,"permission already granted",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        requestPermission();
                    }

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn2.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_back3));
                startActivity(new Intent(MainActivity.this,DrawerActivity.class));
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn3.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_back3));
                startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn4.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_back3));
                startActivity(new Intent(MainActivity.this,Telephone.class));
                

            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn5.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_back3));
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn6.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_back3));
            }
        });

        permission_to_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,otp.class));
            }
        });
    }

    private void requestPermission()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.INTERNET))
        {
            new AlertDialog.Builder(this)
                    .setTitle("permission needed")
                    .setMessage("kindly grant permission")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                             ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.INTERNET},PERMISSION_CODE);
                        }
                    })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                    .create().show();


        }
        else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},PERMISSION_CODE);
            }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==PERMISSION_CODE)
        {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"permission granted",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this,"permission denied",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
