package com.hirebuddy.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hirebuddy.R;
import com.hirebuddy.util.Fonts;

import java.util.ArrayList;
import java.util.List;

public class SecurityQuestionsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Spinner spin_syqo,spin_syqt;
    TextView txt_sqo,txt_sqt,txt_qo,txt_qt;
    EditText edt_eyq,edt_eyqt,edt_eyao,edt_eyat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_questions);

        spin_syqo = (Spinner) findViewById(R.id.spin_syqo);
        spin_syqt = (Spinner) findViewById(R.id.spin_syqt);
        txt_sqo = (TextView) findViewById(R.id.txt_sqo);
//        ((TextView) findViewById(R.id.txt_sqo)).setText();
        txt_sqt = (TextView) findViewById(R.id.txt_sqt);
        txt_qo = (TextView) findViewById(R.id.txt_qo);
        txt_qt = (TextView) findViewById(R.id.txt_qt);
        txt_sqo.requestFocus();
        edt_eyq = (EditText) findViewById(R.id.edt_eyq);
        edt_eyqt = (EditText) findViewById(R.id.edt_eyqt);
        edt_eyao = (EditText) findViewById(R.id.edt_eyao);
        edt_eyat = (EditText) findViewById(R.id.edt_eyat);
         findViewById(R.id.btn_sq_next).setOnClickListener(this);
        spin_syqo.setOnItemSelectedListener(this);
        spin_syqt.setOnItemSelectedListener(this);

        Fonts.railawaySemiBold(txt_qo, getAssets());
        Fonts.railawaySemiBold(txt_qt, getAssets());

        // Spinner Drop down elements
        List<String> securityq = new ArrayList<String>();
        securityq.add(getString(R.string.syq));
        securityq.add("What is your nickname?");
        securityq.add("Who is your best friend?");
        securityq.add("Which is your favorite movie?");
        securityq.add("Which team is your favorite?");
        securityq.add("In which city were you born?");
        securityq.add("Other");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, securityq);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // attaching data adapter to spinner
        spin_syqo.setAdapter(dataAdapter);
        spin_syqt.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        ((TextView)view).setText("");

        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.spin_syqo){
            txt_sqo.setText(item);
            if (txt_sqo.getText().toString().equalsIgnoreCase("Other")){
                txt_sqo.setTextColor(getResources().getColor(R.color.yellow));
                edt_eyq.setVisibility(View.VISIBLE);
                edt_eyq.requestFocus();
            }
            else {
                txt_sqo.setTextColor(getResources().getColor(R.color.black));
                edt_eyq.setVisibility(View.GONE);
            }
        }
        else if(spinner.getId() == R.id.spin_syqt)
        {
            txt_sqt.setText(item);
            if (txt_sqt.getText().toString().equalsIgnoreCase("Other")){
                txt_sqt.setTextColor(getResources().getColor(R.color.yellow));
                edt_eyqt.setVisibility(View.VISIBLE);
                edt_eyqt.requestFocus();
            }
            else {
                txt_sqt.setTextColor(getResources().getColor(R.color.black));
                edt_eyqt.setVisibility(View.GONE);
            }
        }


        // Showing selected spinner item
//        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        txt_sqo.setText(getString(R.string.syq));
    }

//    public void next(View view) {
//        startActivity(new Intent(this, IdProofActivity.class));
//
//    }
public void sqvalidation(View view){
    if (!(txt_sqo.getText().toString().equals(getString(R.string.syq)))) {
        if (txt_sqo.getText().toString().equals("Other")){
            if (edt_eyq.getText().toString().length()>0){
                if (edt_eyao.getText().toString().length()>0){
                    if (!(txt_sqt.getText().toString().equals(getString(R.string.syq)))){
                        if (txt_sqt.getText().toString().equals("Other")){
                            if (edt_eyqt.getText().toString().length()>0){
                                if (edt_eyat.getText().toString().length()>0){
                                    startActivity(new Intent(this, IdProofActivity.class));
                                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                                }else {
                                    Snackbar.make(view, getString(R.string.peasq), Snackbar.LENGTH_SHORT).show();
                                    edt_eyat.requestFocus();
                                    edt_eyat.setError("Enter your answer");
                                }
                            }else{
                                Snackbar.make(view, getString(R.string.peysq), Snackbar.LENGTH_SHORT).show();
                                edt_eyqt.requestFocus();
                                edt_eyqt.setError("Enter Security Question");
                            }
                        }else {
                            if (edt_eyat.getText().toString().length()>0){
                                startActivity(new Intent(this, IdProofActivity.class));
                                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                            }else {
                                Snackbar.make(view, getString(R.string.peasq), Snackbar.LENGTH_SHORT)
                                        .setAction("Action", null).show();
                                edt_eyat.requestFocus();
                                edt_eyat.setError("Enter your answer");
                            }
                        }
                    }else {
                        Snackbar.make(view, getString(R.string.psysq), Snackbar.LENGTH_SHORT).show();
                    }
                }else {
                    Snackbar.make(view, getString(R.string.peasq), Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    edt_eyao.requestFocus();
                    edt_eyao.setError("Enter your answer");
                }
            }else {
                Snackbar.make(view, getString(R.string.peysq), Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                edt_eyq.requestFocus();
                edt_eyq.setError("Enter Security Question");
            }
        }else{
            if (edt_eyao.getText().toString().length()>0){
                if (!(txt_sqt.getText().toString().equals(getString(R.string.syq)))){
                    if (txt_sqt.getText().toString().equals("Other")){
                        if (edt_eyqt.getText().toString().length()>0){
                            if (edt_eyat.getText().toString().length()>0){
                                startActivity(new Intent(this, IdProofActivity.class));
                                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                            }else {
                                Snackbar.make(view, getString(R.string.peasq), Snackbar.LENGTH_SHORT)
                                        .setAction("Action", null).show();
                                edt_eyat.requestFocus();
                                edt_eyat.setError("Enter your answer");
                            }
                        }else{
                            Snackbar.make(view, getString(R.string.peysq), Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                            edt_eyqt.requestFocus();
                            edt_eyqt.setError("Enter Security Question");
                        }
                    }else {
                        if (edt_eyat.getText().toString().length()>0){
                            startActivity(new Intent(this, IdProofActivity.class));
                            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                        }else {
                            Snackbar.make(view, getString(R.string.peasq), Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                            edt_eyat.requestFocus();
                            edt_eyat.setError("Enter your answer");
                        }
                    }
                }else {
                    Snackbar.make(view, getString(R.string.psysq), Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            }else {
                Snackbar.make(view, getString(R.string.peasq), Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                edt_eyao.requestFocus();
                edt_eyao.setError("Enter your answer");
            }
        }
    }else {
        Snackbar.make(view, getString(R.string.psysq), Snackbar.LENGTH_SHORT).show();
    }
}
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sq_next:
                sqvalidation(v);
                break;


        }
    }
}