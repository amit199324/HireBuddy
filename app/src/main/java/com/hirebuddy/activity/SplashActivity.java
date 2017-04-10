package com.hirebuddy.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.hirebuddy.*;

public class SplashActivity extends AppCompatActivity {
    ImageView splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splash = (ImageView) findViewById(R.id.splash);
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_in_middle);
        splash.startAnimation(myFadeInAnimation);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashActivity.this, ProfileActivity.class));
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                finish();
            }
        }, 1500);
    }


}
