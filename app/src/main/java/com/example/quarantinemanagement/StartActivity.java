package com.example.quarantinemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {
    private TextView text1,text2;
    ImageView imageView,imageView2;
    private RelativeLayout relativeLayout;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        text1=(TextView)findViewById(R.id.txt1);
        text2=(TextView)findViewById(R.id.txt2);
        imageView2=(ImageView) findViewById(R.id.img2);
        imageView=(ImageView) findViewById(R.id.img);
        relativeLayout=(RelativeLayout)findViewById(R.id.relative);
        linearLayout=(LinearLayout)findViewById(R.id.linear);

        linearLayout.animate().alpha(1f).setDuration(10000);
        imageView2.animate().alpha(0f).setDuration(1);


        TranslateAnimation animation=new TranslateAnimation(-1000,0,-1000,-350);
        animation.setDuration(2200);
        animation.setFillAfter(false);
        animation.setAnimationListener(new MyAnimationListener());

        TranslateAnimation animation2=new TranslateAnimation(1000,0,1000,400);
        animation2.setDuration(2200);
        animation2.setFillAfter(false);
        animation2.setAnimationListener(new MyAnimationListener());

        TranslateAnimation animation3=new TranslateAnimation(-1000,0,0,0);
        animation3.setDuration(2200);
        animation3.setFillAfter(false);
        animation3.setAnimationListener(new MyAnimationListener());


        text1.setAnimation(animation);
        text2.setAnimation(animation2);
        imageView.setAnimation(animation3);

    }

    private class MyAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            text1.clearAnimation();
            text1.setVisibility(View.INVISIBLE);
            text2.clearAnimation();
            text2.setVisibility(View.INVISIBLE);
            linearLayout.animate().alpha(0f).setDuration(2000);
            imageView2.animate().alpha(1f).setDuration(4000);

            final Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(StartActivity.this,DrawerActivity.class));
                    finish();
                }
            },4000);


        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
