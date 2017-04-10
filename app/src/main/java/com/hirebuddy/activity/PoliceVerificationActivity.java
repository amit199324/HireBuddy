package com.hirebuddy.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hirebuddy.R;

import com.hirebuddy.util.TakeImage;


import java.io.File;
import java.util.regex.Pattern;

public class PoliceVerificationActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener , View.OnClickListener{

    private LinearLayout linearLayoutYesPv;
    private LinearLayout linearLayoutNoPv;
    private RadioGroup rgYesOrNo, rg_ref_id;
    Button btn_pv_next;
    private boolean isYesClicked = false;
    TextView txt_veriproof, tv_no_des;
    private static final int CAMERA_REQUEST = 11;
    static int RESULT_LOAD_IMAGE = 1;
    String path = "";
    Snackbar bar;
    private int PICKFILE_REQUEST_CODE = 12;
    private static int CHOOSE_FILE_REQUESTCODE = 13;
    private boolean polVer = true;

    private EditText edt_buddy_id_pv;

    private String buddyIdRegularExp1 = "[A-Za-z0-9]+";
    private Pattern patternForBuddyId1 = Pattern.compile(buddyIdRegularExp1);
    private String readbudyidformat = "[Hh][Bb][0-9][0-9][A-Za-z][A-Za-z][0-9][0-9][0-9][0-9]";
    private Pattern patternForBuddyId2 = Pattern.compile(readbudyidformat);

    private boolean isYes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_verification);

        edt_buddy_id_pv = (EditText) findViewById(R.id.edt_buddy_id_pv);
        linearLayoutYesPv = (LinearLayout) findViewById(R.id.ll_yespv);
        txt_veriproof = (TextView) findViewById(R.id.txt_veriproof);
        btn_pv_next = (Button) findViewById(R.id.btn_pv_next);
        btn_pv_next.setOnClickListener(this);
        rgYesOrNo = (RadioGroup) findViewById(R.id.rg_pv);
        rgYesOrNo.setOnCheckedChangeListener(this);
        txt_veriproof.setOnClickListener(this);
        rg_ref_id = (RadioGroup) findViewById(R.id.rg_ref_id);
        rg_ref_id.setOnCheckedChangeListener(this);

        findViewById(R.id.txt_pvudoc_remove).setOnClickListener(this);
        linearLayoutNoPv = (LinearLayout) findViewById(R.id.ll_nopv);
        tv_no_des = (TextView) findViewById(R.id.tv_no_des);
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.right_to_left);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.right_to_left);
        tv_no_des.setTextColor(getResources().getColor(R.color.background_color_profile));
        switch (checkedId)
        {
            case R.id.rb_yes:
                tv_no_des.setVisibility(View.GONE);
                linearLayoutYesPv.setVisibility(View.VISIBLE);
                linearLayoutNoPv.setVisibility(View.GONE);
                isYesClicked = true;
                if (bar != null)
                {
                    bar.dismiss();
                }
                btn_pv_next.setText(getString(R.string.next));
                polVer = true;

                break;
            case R.id.rb_no:
                tv_no_des.setVisibility(View.GONE);
                linearLayoutYesPv.setVisibility(View.GONE);
                linearLayoutNoPv.setVisibility(View.VISIBLE);
                linearLayoutNoPv.startAnimation(animation);
                isYesClicked = false;
                polVer = false;
                btn_pv_next.setText(getString(R.string.next));
                rg_ref_id.check(R.id.rd_ridp_y_invisible);
                break;
            case R.id.rd_ridp_y_invisible:
                polVer  = false;
                isYes = true;
                linearLayoutNoPv.setVisibility(View.VISIBLE);
                tv_no_des.setVisibility(View.GONE);
                btn_pv_next.setText(getString(R.string.next));
//                if (bar != null)
//                {
//                    bar.dismiss();
//                }

                break;
            case R.id.rb_id_no_invisible:
                isYes = false;
                polVer  = false;
                linearLayoutNoPv.setVisibility(View.GONE);
                tv_no_des.setVisibility(View.VISIBLE);
                tv_no_des.setTextColor(getResources().getColor(R.color.red));
                tv_no_des.startAnimation(animation2);
                btn_pv_next.setText(getString(R.string.ok));
//                bar = Snackbar.make(radioGroup.getRootView(), getString(R.string.nrid), Snackbar.LENGTH_INDEFINITE)
//                        .setAction("Ok", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Intent intent = new Intent(PoliceVerificationActivity.this, KeyCodeVeriActivity.class);
//                                startActivity(intent);
//                                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
//
//                            }
//                        });
//                TextView tv = (TextView) bar.getView().findViewById(android.support.design.R.id.snackbar_text);
//                tv.setTextColor(Color.RED);
//                bar.setActionTextColor(Color.RED);
//                bar.show();
                break;


        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_pv_next:
                if (polVer){
                    if (txt_veriproof.getText().toString().equals(getString(R.string.nfs))){
                        Snackbar.make(v,getString(R.string.pupv),Snackbar.LENGTH_SHORT)
                                .setAction("Action",null).show();
                    }else {
                        startActivity(new Intent(this, KeyCodeVeriActivity.class));
                        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                    }
                }else {
                    if (isYes) {
                        if (patternForBuddyId1.matcher(edt_buddy_id_pv.getText().toString()).matches()) {
                            if (patternForBuddyId2.matcher(edt_buddy_id_pv.getText().toString()).matches()){
                                startActivity(new Intent(this, KeyCodeVeriActivity.class));
                                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                            } else {
                                Snackbar.make(v, getString(R.string.pevbi), Snackbar.LENGTH_SHORT)
                                        .setAction("Action", null).show();
                            }
                        } else {
                            Snackbar.make(v, getString(R.string.pevbi), Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                        }
                    }else {
                        startActivity(new Intent(this, KeyCodeVeriActivity.class));
                        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                    }
                }

                break;
            case R.id.txt_veriproof:
                takePic(5);
                break;
            case R.id.txt_pvudoc_remove:
                txt_veriproof.setText(getString(R.string.nfs));
                break;

        }
    }

    private void takePic(final int value) {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent)));
        dialog.setContentView(R.layout.camerapopup);
        dialog.setCancelable(true);
        TextView cameraTv = (TextView) dialog.findViewById(R.id.buttoncamerac);
        TextView galleryTv = (TextView) dialog.findViewById(R.id.buttongalleryc);
        dialog.findViewById(R.id.rl_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView cancelTv = (TextView) dialog.findViewById(R.id.cancelbuttonc);
        dialog.findViewById(R.id.txt_drive_popup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                // special intent for Samsung file manager
                Intent sIntent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
                // if you want any file type, you can skip next line
                sIntent.putExtra("CONTENT_TYPE", "*/*");
                sIntent.addCategory(Intent.CATEGORY_DEFAULT);

                Intent chooserIntent;
                if (getPackageManager().resolveActivity(sIntent, 0) != null){
                    // it is device with samsung file manager
                    chooserIntent = Intent.createChooser(sIntent, "Open file");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { intent});
                }
                else {
                    chooserIntent = Intent.createChooser(intent, "Open file");
                }

                try {
                    startActivityForResult(chooserIntent, CHOOSE_FILE_REQUESTCODE);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "No suitable File Manager was found.", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        cameraTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PoliceVerificationActivity.this, TakeImage.class);
                intent.putExtra("from", "camera");
                intent.putExtra("value", value);
                startActivityForResult(intent, CAMERA_REQUEST);
                dialog.dismiss();
            }
        });
        galleryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PoliceVerificationActivity.this, TakeImage.class);
                intent.putExtra("from", "gallery");
                intent.putExtra("value", value);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
                dialog.cancel();
            }
        });

        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == this.RESULT_OK) {
            path = data.getStringExtra("filePath");
            if (path != null) {
                File imgFile = new File(data.getStringExtra("filePath"));
//                if (imgFile.exists()) {
//                    .setImageURI(Uri.fromFile(imgFile));
                    int count = 0;
                    count = path.lastIndexOf("/");

                    txt_veriproof.setText(path.substring(count+1));

//                }
            }else if(requestCode == CHOOSE_FILE_REQUESTCODE){
                path = data.getData().getPath();
                if (path != null) {
                    File drivefile = new File(path);
//                    if (drivefile.exists()) {
//                    .setImageURI(Uri.fromFile(imgFile));
                        int count = 0;
                        count = path.lastIndexOf("/");
                        txt_veriproof.setText(path.substring(count+1));
//                    }
                }
            }else {
                path = "";
            }
            Log.i("File Path", "" + path);
        }
    }



}
