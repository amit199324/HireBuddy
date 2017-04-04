package com.hirebuddy.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hirebuddy.R;
import com.hirebuddy.activity.LoginActivity;
import com.hirebuddy.sharedprefrences.SPreferenceKey;
import com.hirebuddy.sharedprefrences.SharedPreferenceWriter;
import com.hirebuddy.util.MyAlertDialog;

public class RoleSelectionActivity extends AppCompatActivity {

    private  String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);

        Intent intent = getIntent();
        number = intent.getStringExtra("phone_number");

    }

    public void customerLogin(View view) {
        SharedPreferenceWriter.getInstance(this).writeStringValue(SPreferenceKey.LOGIN, "customer");
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra("phone_number", number);
        intent.putExtra("comingFrom", "RoleSelection");
        intent.putExtra("selected_what", "phone");
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }


    public void buddy(View view) {

//        View dialogView = getLayoutInflater().inflate(R.layout.login_pop_up, null);
//        Button permanentBuddy = (Button) dialogView.findViewById(R.id.btn_permanent_buddy);
//        Button temporaryBuddy = (Button) dialogView.findViewById(R.id.btn_temporary_buddy);
//
//        permanentBuddy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
                SharedPreferenceWriter.getInstance(RoleSelectionActivity.this).writeStringValue(SPreferenceKey.LOGIN, "buddy");
//                startActivity(new Intent(RoleSelectionActivity.this, LoginActivity.class));
//            }
//        });
//
//        temporaryBuddy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferenceWriter.getInstance(RoleSelectionActivity.this).writeStringValue(SPreferenceKey.LOGIN, "temporary");
//                startActivity(new Intent(RoleSelectionActivity.this, LoginActivity.class));
//            }
//        });
//
//        AlertDialog.Builder loginBuilder = MyAlertDialog.getMyDialogBuilder(this, true, "", dialogView);
//        loginBuilder.create().show();
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra("phone_number", number);
        intent.putExtra("comingFrom", "RoleSelection");
        intent.putExtra("selected_what", "phone");
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

    }
}
