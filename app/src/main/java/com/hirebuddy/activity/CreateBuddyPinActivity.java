package com.hirebuddy.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hirebuddy.R;
import com.hirebuddy.sharedprefrences.SPreferenceKey;
import com.hirebuddy.sharedprefrences.SharedPreferenceWriter;
import com.hirebuddy.util.MyToast;

public class CreateBuddyPinActivity extends AppCompatActivity {

    private EditText etBuddyPin;
    private EditText etConfirmBuddyPin;
    private TextView tvMatched;
    private ImageView imgChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_buddy_pin);

        etBuddyPin = (EditText) findViewById(R.id.edt_buddy_pin);
        etConfirmBuddyPin = (EditText) findViewById(R.id.edt_confirm_buddy_pin);



        imgChecked = (ImageView) findViewById(R.id.img_checked_cbp);
        imgChecked.setVisibility(View.INVISIBLE);
        tvMatched = (TextView) findViewById(R.id.tv_matched_cbp);
        tvMatched.setVisibility(View.INVISIBLE);

        etBuddyPin.addTextChangedListener(passwordmatcher);
        etConfirmBuddyPin.addTextChangedListener(passwordmatcher);
    }

    public void create(View view) {

        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        if (validation(view))
        {
            if (SharedPreferenceWriter.getInstance(this).getString(SPreferenceKey.LOGIN).equals("customer"))
            {
                Snackbar.make(view, getString(R.string.crs), Snackbar.LENGTH_LONG).show();
            }
            else {
                startActivity(new Intent(this, LoginPopUpActivity.class));
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        }



    }

    private boolean validation(View view)
    {
        if ((etBuddyPin.getText().toString().length()>5)){
            if ((etConfirmBuddyPin.getText().toString().length()>5)){
                if(etBuddyPin.getText().toString().equals(etConfirmBuddyPin.getText().toString())){
                    return true;
                }else {
                    Snackbar.make(view, getString(R.string.pdnm), Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    imgChecked.setVisibility(View.INVISIBLE);
                    tvMatched.setVisibility(View.INVISIBLE);
                    return false;
                }
            }else{
                Snackbar.make(view, getString(R.string.pebp), Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                imgChecked.setVisibility(View.INVISIBLE);
                tvMatched.setVisibility(View.INVISIBLE);
                return false;
            }
        } else {
            Snackbar.make(view, getString(R.string.pebp), Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            imgChecked.setVisibility(View.INVISIBLE);
            tvMatched.setVisibility(View.INVISIBLE);
            return false;
        }
    }

    private final TextWatcher passwordmatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            if (s.length() == 6 && s.length() == etBuddyPin.getText().toString().length()) {
                if (etBuddyPin.getText().toString().equals(etConfirmBuddyPin.getText().toString())){

                    imgChecked.setVisibility(View.VISIBLE);
                    tvMatched.setVisibility(View.VISIBLE);
                }
            }else{
                imgChecked.setVisibility(View.INVISIBLE);
                tvMatched.setVisibility(View.INVISIBLE);
            }
        }
    };
}
