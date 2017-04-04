package com.hirebuddy.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hirebuddy.R;
import java.util.regex.Pattern;

public class ForgotPinActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edt_phone_fp;//edt_email_fp;
    boolean email;
    private String regularExpresionOfEmailId = "^(?!.{51})([A-Za-z0-9])+([A-Za-z0-9._-])+@([A-Za-z0-9._-])+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern patternForEmailId = Pattern.compile(regularExpresionOfEmailId);

    private String buddyIdRegularExp1 = "[A-Za-z0-9]+";
    private String buddyIdRegularExp2 = "[0-9]+[a-zA-Z]+";
    private String readbudyidformat = "[Hh][Bb][0-9][0-9][A-Za-z][A-Za-z][0-9][0-9][0-9][0-9]";
    private Pattern patternForBuddyId1 = Pattern.compile(buddyIdRegularExp1);
    private Pattern patternForBuddyId2 = Pattern.compile(readbudyidformat);

    private String phoneNumber = "[0-9]+";
    private Pattern patternForPhone = Pattern.compile(phoneNumber);
    private String phoneOrEmailOrBuddyId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pin);

        edt_phone_fp = (EditText) findViewById(R.id.edt_phone_fp);
//        edt_email_fp = (EditText) findViewById(R.id.edt_email_fp);
//        edt_email_fp.setOnClickListener(this);
        findViewById(R.id.btn_send_fbp).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send_fbp:
                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
//                EMAIL
                if (edt_phone_fp.getText().toString().contains("@")){
                    if (edt_phone_fp.getText().toString().length() > 0) {
                        if (patternForEmailId.matcher(edt_phone_fp.getText().toString().trim()).matches()) {
                            Log.e(ForgotPinActivity.class.getSimpleName(), "EMAIL");
                            phoneOrEmailOrBuddyId = "email";
                            showDialog();

                        }else {
                            Snackbar.make(v, getString(R.string.pevei), Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                        }
                    }else {
                        Snackbar.make(v, getString(R.string.peyei), Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                }

//                PHONE NUMBER
                    else if (patternForPhone.matcher(edt_phone_fp.getText().toString()).matches()) {
                    {
                        if (edt_phone_fp.getText().toString().length() == 10) {

                            Log.e(ForgotPinActivity.class.getSimpleName(), "Phone Number");
                            phoneOrEmailOrBuddyId = "phone";
                            showDialog();


                        } else {
                            Snackbar.make(v, getString(R.string.pnmb), Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                        }
                    }
                } else if(edt_phone_fp.getText().toString().length()==0){
                    Snackbar.make(v, "Please enter valid Email/Phone number/Buddy-Id", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }


//                BUDDY ID
                else if(patternForBuddyId1.matcher(edt_phone_fp.getText().toString()).matches()) {
                        if (patternForBuddyId2.matcher(edt_phone_fp.getText().toString()).matches()){
//                            if (edt_phone_fp.getText().toString().length() == 10) {
                                Log.e(ForgotPinActivity.class.getSimpleName(), "Buddy Id");
                                phoneOrEmailOrBuddyId = "buddy_id";
                                showDialog();
//                            }
                        }else {
                        Snackbar.make(v, getString(R.string.pevbi), Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                }

//
                break;

            case R.id.edt_phone_fp:
            //    email = false;
             //   if (edt_phone_fp.isFocusableInTouchMode()) {
//                    edt_email_fp.setText("");
            //    }
                break;


//            case R.id.edt_email_fp:
//                email = true;
//                if (edt_email_fp.isFocusableInTouchMode())
//                {
//                    edt_phone_fp.setText("");
//                }
//
//                break;

//            case R.id.til_number_fbp:
//                email = false;
//                edt_email_fp.setText("");
//                break;
//            case R.id.til_email_fbp:
//                email = true;
//                edt_phone_fp.setText("");
//                break;


        }
    }
    private void showDialog()
    {
        final Dialog dialogView = new Dialog(this, android.R.style.Theme_Translucent);
        dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = getLayoutInflater().inflate(R.layout.sendc_pop_up, null);
        TextView tv_title_login_pop_up = (TextView) view.findViewById(R.id.tv_title_login_pop_up);
        if (phoneOrEmailOrBuddyId.equalsIgnoreCase("phone")) {
            tv_title_login_pop_up.setText(getString(R.string.otp_description_phone));
        }
        else if (phoneOrEmailOrBuddyId.equalsIgnoreCase("email"))
        {
            tv_title_login_pop_up.setText(getString(R.string.otp_description_email));
        }
        dialogView.setContentView(view);

        final Button send = (Button) dialogView.findViewById(R.id.btn_send);
        dialogView.findViewById(R.id.rl_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView.dismiss();
            }
        });
        Button cancel = (Button) dialogView.findViewById(R.id.btn_cancel);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                        SharedPreferenceWriter.getInstance(ForgotPinActivity.this).writeStringValue(SPreferenceKey.LOGIN, "permanent");
                dialogView.dismiss();
                Intent intent = new Intent(ForgotPinActivity.this, VerificationActivity.class);
                intent.putExtra("phone_number",edt_phone_fp.getText().toString());
                intent.putExtra("comingFrom", "dialog");
                intent.putExtra("selected_what", phoneOrEmailOrBuddyId);

                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);



            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                        SharedPreferenceWriter.getInstance(ForgotPinActivity.this).writeStringValue(SPreferenceKey.LOGIN, "temporary");
                dialogView.dismiss();
            }
        });
        dialogView.show();


    }
}
