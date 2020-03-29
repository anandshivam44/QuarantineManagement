package com.example.quarantinemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuListActivity extends AppCompatActivity {

    private Button update_location,who_data,DonateBtn,Guidlines,precaution;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_list);
        update_location=(Button)findViewById(R.id.update_location);
        who_data=(Button)findViewById(R.id.btn_who);
        DonateBtn=(Button)findViewById(R.id.btn_donate);
        Guidlines=(Button)findViewById(R.id.btn_guidelines);
        precaution=(Button)findViewById(R.id.precaution_btn);


        update_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuListActivity.this,MapsActivity.class));
            }
        });

        who_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browse("https://www.who.int/");
            }
        });
        Guidlines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browse("https://www.mygov.in/covid-19/");
            }
        });
       DonateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browse("https://www.mygov.in/covid-19/");
            }
        });
       precaution.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(MenuListActivity.this,Precaution.class));
           }
       });


    }

    public void browse(String url) {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);

    }


}
