package com.hirebuddy.activity;

import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hirebuddy.R;
import com.hirebuddy.sharedprefrences.SPreferenceKey;
import com.hirebuddy.sharedprefrences.SharedPreferenceWriter;
import com.hirebuddy.util.MyAlertDialog;
import com.hirebuddy.util.MyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txt_forgot_pin_login;
    private EditText etPhoneNumber;
    private EditText etBuddyPin;
    private TextInputLayout textInputLayoutBuddyPin;
    private boolean isEyeOpen = true;
    private AlertDialog permanentOrTemporaryDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.txt_forgot_pin_login).setOnClickListener(this);


        etPhoneNumber = (EditText) findViewById(R.id.et_phone_login);
        etBuddyPin = (EditText) findViewById(R.id.et_buddy_pin_login);

        textInputLayoutBuddyPin = (TextInputLayout) findViewById(R.id.til_buddy_pin_login);
        textInputLayoutBuddyPin.setOnClickListener(this);
    }

    public void signUp(View view) {

//        if (SharedPreferenceWriter.getInstance(this).getString(SPreferenceKey.LOGIN).equals("customer")) {
//            startActivity(new Intent(this, VerificationActivity.class));
//            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
//        } else{
//            final Dialog dialogView = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
//            dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialogView.setContentView(R.layout.login_pop_up);
//            dialogView.findViewById(R.id.rl_root).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialogView.dismiss();
//                }
//            });
//
//            final Button permanentBuddy = (Button) dialogView.findViewById(R.id.btn_permanent_buddy);
//            Button temporaryBuddy = (Button) dialogView.findViewById(R.id.btn_temporary_buddy);
//
//            permanentBuddy.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(SPreferenceKey.LOGIN, "permanent");
//                    startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
//                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
//                    dialogView.dismiss();
//
//                }
//            });
//
//            temporaryBuddy.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    SharedPreferenceWriter.getInstance(LoginActivity.this).writeStringValue(SPreferenceKey.LOGIN, "temporary");
//                    startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
//                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
//                    dialogView.dismiss();
//                }
//            });
//
            startActivity(new Intent(this, RoleSelectionActivity.class));
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);



        }

    private boolean validation(View view)
    {
        if (etPhoneNumber.getText() == null || etPhoneNumber.getText().toString().equalsIgnoreCase("") || etPhoneNumber.getText().toString().length() < 10)
        {

            Snackbar.make(view, "Phone Number should be of 10 digits long", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return false;
        }
        if (etBuddyPin.getText() == null || etBuddyPin.getText().toString().equalsIgnoreCase("") || !(etBuddyPin.getText().toString().length() == 6))
        {
            Snackbar.make(view, "Buddy PIN should be 6 digits long", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.txt_forgot_pin_login:

                startActivity(new Intent(LoginActivity.this, ForgotPinActivity.class));
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;
            case R.id.til_buddy_pin_login:
//                if(textInputLayoutBuddyPin.isSelected()){
//                    textInputLayoutBuddyPin.setSelected(false);
//                }
//                else {
//                    textInputLayoutBuddyPin.setSelected(true);
//                }
//                if (isEyeOpen) {
//                    textInputLayoutBuddyPin.setPasswordVisibilityToggleDrawable(R.drawable.visible);
//                    textInputLayoutBuddyPin.setSelected(true);
//                    isEyeOpen = false;
//                }
//                else {
//                    textInputLayoutBuddyPin.setPasswordVisibilityToggleDrawable(R.drawable.hide);
//                    isEyeOpen = true;
//                }
                break;
        }
    }

    public void login(View view) {
        if (validation(view)) {
            if (SharedPreferenceWriter.getInstance(this).getString(SPreferenceKey.LOGIN).equals("customer")) {


            } else if (SharedPreferenceWriter.getInstance(this).getString(SPreferenceKey.LOGIN).equals("permanent")) {

            } else {

            }
            Snackbar.make(view, "Home Screen", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }
    }

    //HANDLING MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == R.id.profile)
            {
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
    }

}
