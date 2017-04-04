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
import com.hirebuddy.util.MyToast;

public class ResetBuddyPinActivity extends AppCompatActivity {

    private EditText etCreateBuddyPin;
    private EditText etConfirmBuddyPin;
    private ImageView imgChecked;
    private TextView tvMatched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_buddy_pin);

        etCreateBuddyPin = (EditText) findViewById(R.id.reset_buddy_pin);
        etConfirmBuddyPin = (EditText) findViewById(R.id.reset_confirm_buddy_pin);

        imgChecked = (ImageView) findViewById(R.id.img_checked_rbp);
        tvMatched = (TextView) findViewById(R.id.tv_matched_rbp);

        imgChecked.setVisibility(View.INVISIBLE);
        tvMatched.setVisibility(View.INVISIBLE);

        etCreateBuddyPin.addTextChangedListener(passwordmatcher);
        etConfirmBuddyPin.addTextChangedListener(passwordmatcher);



    }

    final TextWatcher passwordmatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            if (s.length() == 6 && s.length() == etCreateBuddyPin.getText().toString().length()) {
                if (etCreateBuddyPin.getText().toString().equals(etConfirmBuddyPin.getText().toString())){

                    imgChecked.setVisibility(View.VISIBLE);
                    tvMatched.setVisibility(View.VISIBLE);
                }
            }else{
                imgChecked.setVisibility(View.INVISIBLE);
                tvMatched.setVisibility(View.INVISIBLE);
            }
        }
    };

    public void done(View view) {

        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        if ((etCreateBuddyPin.getText().toString().length()>5)){
            if ((etConfirmBuddyPin.getText().toString().length()>5)){
                if(etCreateBuddyPin.getText().toString().equals(etConfirmBuddyPin.getText().toString())){

                    startActivity(new Intent(this, LoginActivity.class));
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

                }else {
                    Snackbar.make(view, getString(R.string.pdnm), Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    imgChecked.setVisibility(View.INVISIBLE);
                    tvMatched.setVisibility(View.INVISIBLE);
                }
            }else{
                Snackbar.make(view, getString(R.string.pebp), Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                imgChecked.setVisibility(View.INVISIBLE);
                tvMatched.setVisibility(View.INVISIBLE);
            }
        } else {
            Snackbar.make(view, getString(R.string.pebp), Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            imgChecked.setVisibility(View.INVISIBLE);
            tvMatched.setVisibility(View.INVISIBLE);
        }
    }
}
