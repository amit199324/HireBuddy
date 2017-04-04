package com.hirebuddy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hirebuddy.R;
import com.hirebuddy.sharedprefrences.SPreferenceKey;
import com.hirebuddy.sharedprefrences.SharedPreferenceWriter;

public class LoginPopUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button temporaryBtn;
    private Button permanentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_pop_up);

        temporaryBtn = (Button) findViewById(R.id.btn_temporary_buddy);
        permanentBtn = (Button) findViewById(R.id.btn_permanent_buddy);

        temporaryBtn.setOnClickListener(this);
        permanentBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_temporary_buddy:
                SharedPreferenceWriter.getInstance(LoginPopUpActivity.this).writeStringValue(SPreferenceKey.LOGIN, "temporary");
                startActivity(new Intent(LoginPopUpActivity.this, SocialMediaVerificationActivity.class));
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;
            case R.id.btn_permanent_buddy:
                SharedPreferenceWriter.getInstance(LoginPopUpActivity.this).writeStringValue(SPreferenceKey.LOGIN, "permanent");
                startActivity(new Intent(LoginPopUpActivity.this, SocialMediaVerificationActivity.class));
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;
        }
    }
}
