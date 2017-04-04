package com.hirebuddy.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.hirebuddy.R;
import com.hirebuddy.sharedprefrences.SPreferenceKey;
import com.hirebuddy.sharedprefrences.SharedPreferenceWriter;

public class SocialMediaVerificationActivity extends AppCompatActivity implements View.OnClickListener {

    private Snackbar bar;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smv);

        nextBtn = (Button) findViewById(R.id.btn_smv);
        nextBtn.setOnClickListener(this);
        findViewById(R.id.tv_skip_smv).setOnClickListener(this);
        findViewById(R.id.activity_smv).setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
//        if (bar != null) {
//            bar.dismiss();
//        }
        switch (view.getId()) {

            case R.id.btn_smv:
                if (SharedPreferenceWriter.getInstance(this).getString(SPreferenceKey.LOGIN).equals("customer")) {
                    startActivity(new Intent(this, ProfileActivity.class));
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                } else if (SharedPreferenceWriter.getInstance(this).getString(SPreferenceKey.LOGIN).equals("temporary")) {
                    startActivity(new Intent(this, ProfileActivity.class));
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                } else {
                    startActivity(new Intent(this, ProfileActivity.class));
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                }

                break;
            case R.id.activity_smv:

                if (bar != null)
                {
                    bar.dismiss();
                    nextBtn.setEnabled(true);
                }

                break;
            case R.id.tv_skip_smv:
                bar = Snackbar.make(view, getString(R.string.ifuskipl), Snackbar.LENGTH_INDEFINITE)
                        .setAction("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(SocialMediaVerificationActivity.this, ProfileActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

                            }
                        });
//                TextView tv = (TextView) bar.getView().findViewById(android.support.design.R.id.snackbar_text);
//                tv.setTextColor(Color.RED);
                bar.setActionTextColor(Color.RED);
                bar.show();


                nextBtn.setEnabled(false);
        }

        }

    @Override
    protected void onStop() {
        super.onStop();
        nextBtn.setEnabled(true);
    }
}

