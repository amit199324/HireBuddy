package com.hirebuddy.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hirebuddy.*;
import com.hirebuddy.adapter.CountryAdapter;
import com.hirebuddy.bean.CountryCodeBean;
import com.hirebuddy.sharedprefrences.SPreferenceKey;
import com.hirebuddy.sharedprefrences.SharedPreferenceWriter;
import com.hirebuddy.util.MyToast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvPrivacyNPolicy;
//    private CheckBox checkBox;
    private EditText etPhoneNumber;
    private TextView tvCountryCode;

    ArrayList<CountryCodeBean> countries = new ArrayList<>();
    ArrayList<CountryCodeBean> countriesList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setValidating(false);
            XmlPullParser myxml = factory.newPullParser();
            InputStream raw = getAssets().open("countries.xml");
            myxml.setInput(raw, null);

            parseXML(myxml);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tvCountryCode = (TextView) findViewById(R.id.tv_country_code_sign_up);
        tvPrivacyNPolicy = (TextView) findViewById(R.id.privacy_policy_sign_up);
        etPhoneNumber = (EditText) findViewById(R.id.et_phone_sign_up);
//        checkBox = (CheckBox) findViewById(R.id.checkbox_sign_up);
        (findViewById(R.id.tv_already_a_member)).setOnClickListener(this);

        tvCountryCode.setOnClickListener(this);


        SpannableString spannableString = new SpannableString(tvPrivacyNPolicy.getText());

        ClickableSpan privacyNPolicySpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUpActivity.this, "Privacy And Policy", Toast.LENGTH_SHORT).show();
            }
        };

        spannableString.setSpan(privacyNPolicySpan, 87, spannableString.length(), 0);
        spannableString.setSpan(new RelativeSizeSpan(1.1f), 62, 82, 0); // set size
        spannableString.setSpan(new RelativeSizeSpan(1.1f), 87, spannableString.length(), 0); // set size
        ClickableSpan tNc = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUpActivity.this, "Terms and Conditions", Toast.LENGTH_SHORT).show();
            }
        };
        spannableString.setSpan(tNc, 62, 82, 0);
        tvPrivacyNPolicy.setMovementMethod(LinkMovementMethod.getInstance());
        tvPrivacyNPolicy.setText(spannableString);

    }

    private void parseXML(XmlPullParser parser) throws XmlPullParserException,
            IOException {
        int eventType = parser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name = null;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
//                    countries = new ArrayList<CountryCodeBean>();
//                    tempCountries = new ArrayList<CountryCodeBean>();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("country")) {
                        String countryCode = parser.getAttributeValue(0);
                        String phoneCode = parser.getAttributeValue(1);
                        String countryName = parser.getAttributeValue(2);
                        String lat1 = parser.getAttributeValue(3);
                        String lng1 = parser.getAttributeValue(4);
                        String lat2 = parser.getAttributeValue(5);
                        String lng2 = parser.getAttributeValue(6);
                        countries.add(new CountryCodeBean("+"+phoneCode, countryCode, countryName));
//                        tempCountries.add(new CountryCodeBean(phoneCode, countryCode, countryName));
                    }
                    break;
            }
            eventType = parser.next();
        }

        countriesList.addAll(countries);
        Log.e("signup","");

    }

    public void countrypopup() {
        final ArrayList countryCode = new ArrayList();
        // custom dialog
        final Dialog dialog = new Dialog(SignUpActivity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen );

        dialog.setContentView(R.layout.countrycode);
        dialog.setTitle("");

        EditText etSearch = (EditText)dialog.findViewById(R.id.et_search);
        TextView ivpppop = (TextView) dialog.findViewById(R.id.ivclose);
        ivpppop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final ListView listView = (ListView) dialog.findViewById(R.id.countrylist);

        final CountryAdapter adapter = new CountryAdapter(this,countries);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tvCountryCode.setText(countries.get(i).getCountryCode());
                dialog.dismiss();
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 0){
                    countries.clear();
                    countries.addAll(countriesList);
                }else{
                    searchCountruy(charSequence.toString(),adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        dialog.show();
    }

    private void searchCountruy(String str,CountryAdapter adapter){
        countries.clear();
        for (int i= 0;i<countriesList.size();i++){
            if (countriesList.get(i).getCountryName().toLowerCase().contains(str.toLowerCase())){
                countries.add(countriesList.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }


    public void signUp(View view) {

        validation(view);

//        if (validation(view))
//        {
//
//        }
//        if (SharedPreferenceWriter.getInstance(this).getString(SPreferenceKey.LOGIN).equals("customer"))
//        {
//
//        }
//        else if (SharedPreferenceWriter.getInstance(this).getString(SPreferenceKey.LOGIN).equals("temporary"))
//        {
//
//        }
//        else {
//
//        }
    }

    private boolean validation(View view)
    {
        if (etPhoneNumber.getText().toString().length() > 0){
            if (etPhoneNumber.getText().toString().length() > 9){
//                if (checkBox.isChecked())
//                {
                    Intent intent = new Intent(this, VerificationActivity.class);
                    intent.putExtra("phone_number", etPhoneNumber.getText().toString());
                    intent.putExtra("comingFrom", "signUp");
                    intent.putExtra("selected_what", "phone");
                    startActivity(intent);
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
//                }
//                else {
//
//                    Snackbar.make(view, "Please Select Terms And Conditions", Snackbar.LENGTH_SHORT)
//                            .setAction("Action", null).show();
//                }
                return true;
            }else {
                Snackbar.make(view, "Phone Number should be of 10 digits long", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                return false;
            }
        }else {
            Snackbar.make(view, getString(R.string.peypn), Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return false;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_already_a_member:
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;
            case R.id.tv_country_code_sign_up:
                countrypopup();
                break;
        }
    }
}
