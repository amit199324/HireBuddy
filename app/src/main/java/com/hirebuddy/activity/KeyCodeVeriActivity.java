package com.hirebuddy.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hirebuddy.R;
import com.hirebuddy.sharedprefrences.SPreferenceKey;
import com.hirebuddy.sharedprefrences.SharedPreferenceWriter;
import com.hirebuddy.util.TakeImage;


import java.io.File;

public class KeyCodeVeriActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnGenerate;
    private TextView tvRefreshCode, tvImagePath;
    private Button btnDone;

    private LinearLayout ll_code;
    private String generatedCode= "HB8668";
    ImageView img_kcv_ui;
    private static final int CAMERA_REQUEST = 11;
    static int RESULT_LOAD_IMAGE = 1;
    String path = "";
    private Snackbar bar;
    private TextView tvSkip;
    private int PICKFILE_REQUEST_CODE = 12;
    private static int CHOOSE_FILE_REQUESTCODE = 13;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_code_veri);

        btnDone = (Button) findViewById(R.id.button_done_key_code);
        btnDone.setOnClickListener(this);

        tvImagePath = (TextView) findViewById(R.id.txt_image_key_code);

        tvSkip = (TextView) findViewById(R.id.txt_skip);
        tvSkip.setOnClickListener(this);

        btnGenerate = (Button) findViewById(R.id.btn_generate);
        btnGenerate.setOnClickListener(this);

        tvRefreshCode = (TextView) findViewById(R.id.tv_refresh_code);
        img_kcv_ui = (ImageView) findViewById(R.id.img_kcv_ui);
        img_kcv_ui.setOnClickListener(this);

        findViewById(R.id.txt_keycoddoc_remove).setOnClickListener(this);
        ll_code = (LinearLayout) findViewById(R.id.ll_code);

        for (int i = 0; i < generatedCode.length(); i++) {
            TextView codeText = new TextView(this);
            codeText.setTextColor(ContextCompat.getColor(this, R.color.black));
            codeText.setText(generatedCode.substring(i,i+1));
            codeText.setBackgroundResource(R.drawable.underline);
            codeText.setTextSize(20);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMarginEnd(20);
            codeText.setLayoutParams(params);

            ll_code.addView(codeText);

            ll_code.setVisibility(View.GONE);
        }

        if (SharedPreferenceWriter.getInstance(this).getString(SPreferenceKey.LOGIN).equalsIgnoreCase("temporary"))
        {
            tvSkip.setVisibility(View.INVISIBLE);
        }

    }

    public void doneKeyCodeVeri(View view) {
        startActivity(new Intent(this, PoliceVerificationActivity.class));
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }


    @Override
    public void onClick(View view) {
        if (bar != null) {
            bar.dismiss();
        }
        switch (view.getId()){

            case R.id.txt_skip:
                bar = Snackbar.make(view, getString(R.string.ifuskip), Snackbar.LENGTH_LONG)
                        .setAction("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Snackbar.make(v, "Registration Complete", Snackbar.LENGTH_SHORT).show();
//                                Intent intent = new Intent(KeyCodeVeriActivity.this, ReferenceContactsActivity.class);
//                                startActivity(intent);
//                                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                            }
                        });
                TextView tv = (TextView) bar.getView().findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(Color.WHITE);
                bar.setActionTextColor(Color.RED);
                bar.show();
                break;

            case R.id.btn_generate:
                btnGenerate.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadeout));
                btnGenerate.setVisibility(View.GONE);
                ll_code.setVisibility(View.VISIBLE);
                tvRefreshCode.setVisibility(View.VISIBLE);
                tvRefreshCode.startAnimation(AnimationUtils.loadAnimation(this, R.anim.flip_in_left));
                break;

            case R.id.img_kcv_ui:
                takePic(1);
                break;

            case R.id.button_done_key_code:
                if (btnGenerate.getVisibility()==View.GONE){
                    if (!(tvImagePath.getText().toString().equals(getString(R.string.nfs)))){
//                    startActivity(new Intent(this, ReferenceContactsActivity.class));
//                    overridePendingTransition(R.anim.pull_in_right,R.anim.push_out_left);

                        Snackbar.make(view, "Registration Complete", Snackbar.LENGTH_SHORT).show();
                    }else{
                        Snackbar.make(view,getString(R.string.puyipwtgkc),Snackbar.LENGTH_SHORT)
                                .setAction("Action",null).show();
                    }
                }else{
                    Snackbar.make(view,getString(R.string.pgkc),Snackbar.LENGTH_SHORT)
                            .setAction("Action",null).show();
                }


                break;
            case R.id.txt_keycoddoc_remove:
                tvImagePath.setText(getString(R.string.nfs));
                img_kcv_ui.setImageResource(R.drawable.dummy_file);
                break;
        }

    }

    private void takePic(final int value) {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent)));
        dialog.setContentView(R.layout.camerapopup_profile);
        dialog.setCancelable(true);
        TextView cameraTv = (TextView) dialog.findViewById(R.id.buttoncamera_cpup);
        TextView galleryTv = (TextView) dialog.findViewById(R.id.buttongallery_cpup);
        dialog.findViewById(R.id.rl_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView cancelTv = (TextView) dialog.findViewById(R.id.cancelbutton_cpup);
//        dialog.findViewById(R.id.txt_drive_popup).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("*/*");
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//
//                // special intent for Samsung file manager
//                Intent sIntent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
//                // if you want any file type, you can skip next line
//                sIntent.putExtra("CONTENT_TYPE", "*/*");
//                sIntent.addCategory(Intent.CATEGORY_DEFAULT);
//
//                Intent chooserIntent;
//                if (getPackageManager().resolveActivity(sIntent, 0) != null){
//                    // it is device with samsung file manager
//                    chooserIntent = Intent.createChooser(sIntent, "Open file");
//                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { intent});
//                }
//                else {
//                    chooserIntent = Intent.createChooser(intent, "Open file");
//                }
//
//                try {
//                    startActivityForResult(chooserIntent, CHOOSE_FILE_REQUESTCODE);
//                } catch (android.content.ActivityNotFoundException ex) {
//                    Toast.makeText(getApplicationContext(), "No suitable File Manager was found.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        cameraTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KeyCodeVeriActivity.this, TakeImage.class);
                intent.putExtra("from", "camera");
                intent.putExtra("value", value);
                startActivityForResult(intent, CAMERA_REQUEST);
                dialog.dismiss();
            }
        });
        galleryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KeyCodeVeriActivity.this, TakeImage.class);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == this.RESULT_OK) {
            path = data.getStringExtra("filePath");
            if (path != null) {
                File imgFile = new File(path);
                    img_kcv_ui.setImageURI(Uri.parse(path));
                    int count = 0;
                    count = path.lastIndexOf("/");
                        tvImagePath.setText(path.substring(count+1));

//            }else if(requestCode == CHOOSE_FILE_REQUESTCODE){
//                path = data.getData().getPath();
//                if (path != null) {
//                File drivefile = new File(path);
//                    int count = 0;
//
//                    count = path.lastIndexOf("/");
//                    tvImagePath.setText(path.substring(count+1));
//                }
            }else {
                path = "";
            }
            Log.i("File Path", "" + path);
        }
    }


}
