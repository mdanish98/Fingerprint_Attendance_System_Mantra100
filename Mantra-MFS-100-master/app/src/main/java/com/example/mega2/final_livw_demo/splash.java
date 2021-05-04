package com.example.mega2.final_livw_demo;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class splash extends AppCompatActivity{

    ImageView imageView;
    private ProgressBar mProgress;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView=(ImageView)findViewById(R.id.imageView);
        mProgress = (ProgressBar) findViewById(R.id.splash_screen_progress_bar);
        mProgress.setProgressTintList(ColorStateList.valueOf(Color.RED));
        new Thread(new Runnable() {
            public void run() {
                for (int progress=0; progress<100; progress+=11) {
                    try {
                        Thread.sleep(1000);
                        mProgress.setProgress(progress);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
                finish();
            }
        }).start();
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.welcome_animation);
        imageView.setAnimation(animation);


        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                //startActivity(new Intent(splash.this,Main_screen_main.class));
                startActivity(new Intent(splash.this,LoginActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }
}
