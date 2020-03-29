package com.example.quarantinemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;

import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

public class Precaution extends TutorialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_prevent);

        addFragment(new Step.Builder().setTitle("Symptoms")
                .setContent("maintain social distancing")
                //btn4.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_back3));
               .setBackgroundColor(Color.parseColor("#FF3F51B5")) // int background color
                .setDrawable(R.drawable.symptoms) // int top drawable
                .setSummary("stay safe")
                .build());
        addFragment(new Step.Builder().setTitle("This is header")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#FF0957")) // int background color
                .setDrawable(R.drawable.who) // int top drawable
                .setSummary("This is summary")
                .build());
    }

    @Override
    public void currentFragmentPosition(int position) {

    }
}
