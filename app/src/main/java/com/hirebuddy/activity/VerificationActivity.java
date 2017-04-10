package com.hirebuddy.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hirebuddy.R;
import com.hirebuddy.sharedprefrences.SPreferenceKey;
import com.hirebuddy.sharedprefrences.SharedPreferenceWriter;

public class VerificationActivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    TextView tvNumberVerification, tv_desc_verification,tv_resend_verification;;
    EditText et_otp_verification;
    private String comingFrom = "";
    private String phoneOrEmailOrBuddyId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        tv_resend_verification = (TextView) findViewById(R.id.tv_resend_verification);
        tv_resend_verification.setOnClickListener(this);
        et_otp_verification = (EditText) findViewById(R.id.et_otp_verification);
        tvNumberVerification = (TextView) findViewById(R.id.tv_number_verification);
        tv_desc_verification = (TextView) findViewById(R.id.tv_desc_verification);

        intent = getIntent();
        if (!(intent.getStringExtra(("comingFrom")).equals("signUp"))) {
//            tvNumberVerification.setText(intent.getStringExtra("phone_number"));
            comingFrom = intent.getStringExtra("comingFrom");
            phoneOrEmailOrBuddyId = intent.getStringExtra("selected_what");
            tvNumberVerification.setText(intent.getStringExtra("phone_number"));

            if (phoneOrEmailOrBuddyId != null && !phoneOrEmailOrBuddyId.equals("")) {
//                tvNumberVerification.setText(phoneOrEmailOrBuddyId);
                if (phoneOrEmailOrBuddyId.equalsIgnoreCase("email")) {
                    tv_desc_verification.setText(getString(R.string.peotp_email));
                    tvNumberVerification.setText(intent.getStringExtra("phone_number"));
                } else if (phoneOrEmailOrBuddyId.equalsIgnoreCase("phone")) {
                    tv_desc_verification.setText(getString(R.string.peotp_phone));
                    tvNumberVerification.setText(intent.getStringExtra("phone_number"));
                } else if (phoneOrEmailOrBuddyId.equalsIgnoreCase("buddy_id")) {
                    tv_desc_verification.setText(getString(R.string.peotp_buddy_id));
                    tvNumberVerification.setText("Buddy Id : " + intent.getStringExtra("phone_number"));
                }
            }

        } else {
            phoneOrEmailOrBuddyId = "phone";
            tvNumberVerification.setText(intent.getStringExtra("phone_number"));
            tv_desc_verification.setText(getString(R.string.peotp_phone));
        }
    }

    public void verify(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
//        String comingFromWhich = intent.getStringExtra("comingFrom");
//        if (comingFromWhich.equals("signUp"))
//        {
//            startActivity(new Intent(this, CreateBuddyPinActivity.class));
//            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
//        }else if (comingFromWhich.equals("dialog"))
//        {
//            startActivity(new Intent(this, ResetBuddyPinActivity.class));
//            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
//        }

//        if (SharedPreferenceWriter.getInstance(this).getString(SPreferenceKey.LOGIN).equals("customer"))
//        {
//
//        }
//        else {
        if (et_otp_verification.getText().toString().length() > 3) {
            if (comingFrom != null) {
                if (comingFrom.equalsIgnoreCase("dialog")) {
                    startActivity(new Intent(this, ResetBuddyPinActivity.class));
                } else {
                    startActivity(new Intent(this, CreateBuddyPinActivity.class));
                }
            }

            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        } else {
            Snackbar.make(view, getString(R.string.potp), Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }

//        }
//        else if (SharedPreferenceWriter.getInstance(this).getString(SPreferenceKey.LOGIN).equals("temporary"))
//        {
//            startActivity(new Intent(this, CreateBuddyPinActivity.class));
//            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
//        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_resend_verification:

                if (v != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                if (phoneOrEmailOrBuddyId != null) {
                    if (phoneOrEmailOrBuddyId.equalsIgnoreCase("email")) {
                        Snackbar.make(v, getString(R.string.otp_description_email), Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                    if (phoneOrEmailOrBuddyId.equalsIgnoreCase("phone")) {
                        Snackbar.make(v, getString(R.string.otp_description_phone), Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                    if (phoneOrEmailOrBuddyId.equalsIgnoreCase("buddy_id")) {
                        Snackbar.make(v, getString(R.string.otp_description), Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                }


                break;
        }
    }
}
