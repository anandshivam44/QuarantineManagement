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
                .setSummary("#stayHome #staySafe")
                .build());
        addFragment(new Step.Builder().setTitle("wear mask")
                .setContent("avoid handshake only🙏")
                .setBackgroundColor(Color.parseColor("#FF3F51B5")) // int background color
                .setDrawable(R.drawable.mask) // int top drawable
                .setSummary("#stayHome #staySafe")
                .build());
        addFragment(new Step.Builder().setTitle("Self Quarantine")
                .setContent("Don't go outside unnecessary")
                .setBackgroundColor(Color.parseColor("#FF3F51B5")) // int background color
                .setDrawable(R.drawable.corona) // int top drawable
                .setSummary("#stayHome #staySafe")
                .build());
        addFragment(new Step.Builder().setTitle("Consult Doctors")
                .setContent("Don't beleive on fake news always consult it with doctors")
                .setBackgroundColor(Color.parseColor("#FF3F51B5")) // int background color
                .setDrawable(R.drawable.doctorsgroup) // int top drawable
                .setSummary("#stayHome #staySafe")
                .build());
    }

    @Override
    public void currentFragmentPosition(int position) {

    }
}
