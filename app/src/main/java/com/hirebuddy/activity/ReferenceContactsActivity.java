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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hirebuddy.R;
import com.hirebuddy.util.TakeImage;
import com.hirebuddy.util.TakeImage2;

import java.io.File;

public class ReferenceContactsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CHOOSE_FILE_REQUESTCODE = 13 ;
    String path="";
    int from = 0;
    private static final int CAMERA_REQUEST = 11;
    static int RESULT_LOAD_IMAGE = 1;
    TextView txt_contoimage,txt_conttimage;

    private EditText etName1;
    private EditText etName2;
    private EditText phone1;
    private EditText phone2;

    private int PICKFILE_REQUEST_CODE = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference_contacts);

        txt_contoimage=(TextView)findViewById(R.id.txt_contoimage);
        txt_contoimage.setOnClickListener(this);
        txt_conttimage=((TextView)findViewById(R.id.txt_conttimage));
        txt_conttimage.setOnClickListener(this);

        etName1 = (EditText) findViewById(R.id.et_name_rc);
        etName2 = (EditText) findViewById(R.id.et_name_rc2);
        phone1 = (EditText) findViewById(R.id.et_phone_rc);
        phone2 = (EditText) findViewById(R.id.et_phone_rc2);
    }

    public void finish(View view) {

        if (validations(view))
        {
            startActivity(new Intent(this, KeyCodeVeriActivity.class));

//            if (!(txt_contoimage.getText().toString().equals(getString(R.string.nfs)))){
//                if (!(txt_conttimage.getText().toString().equals(getString(R.string.nfs)))){
//
//                    startActivity(new Intent(this, KeyCodeVeriActivity.class));
//                }else {
//                    Snackbar.make(view, getString(R.string.puic), Snackbar.LENGTH_SHORT).show();
//                }
//            }else {
//                Snackbar.make(view, getString(R.string.puic), Snackbar.LENGTH_SHORT).show();
//            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.txt_contoimage:
                takePic(5);
                from = 1;
                break;

            case R.id.txt_conttimage:
                takePic(5);
                from = 2;
                break;
        }
    }

    private boolean validations(View view)
    {
        if (etName1.getText().toString().equals("")){
            Snackbar.make(view, "Name can't be empty", Snackbar.LENGTH_SHORT).show();
            etName1.setError("Please Enter the Name");
            etName1.requestFocus();
            return false;
        }
        if (phone1.getText().toString().equals("")){
            phone1.requestFocus();
            Snackbar.make(view, "Number can't be empty", Snackbar.LENGTH_SHORT).show();
            phone1.setError("Please Enter the Phone Number");
            return false;
        }
        if (!(phone1.getText().length() == 10))
        {
            phone1.requestFocus();
            Snackbar.make(view, "Phone Number should be of 10 digits long", Snackbar.LENGTH_SHORT).show();
            phone1.setError("Phone Number is not 10 digits long");
            return false;
        }
        if (etName2.getText().toString().equals(""))
        {
            etName2.requestFocus();
            Snackbar.make(view, "Name can't be empty", Snackbar.LENGTH_SHORT).show();
            etName2.setError("Please Enter the Name");
            return false;
        }
        if (phone2.getText().toString().equals(""))
        {
            phone2.requestFocus();
            Snackbar.make(view, "Number can't be empty", Snackbar.LENGTH_SHORT).show();
            phone2.setError("Please Enter the Phone Number");
            return false;
        }
        if (!(phone2.getText().length() == 10))
        {
            phone2.requestFocus();
            Snackbar.make(view, "Phone Number should be of 10 digits long", Snackbar.LENGTH_SHORT).show();
            phone2.setError("Phone Number is not 10 digits long");
            return false;
        }
        if (phone1.getText().toString().equals(phone2.getText().toString()))
        {
            Snackbar.make(view, "Both numbers should not be identical", Snackbar.LENGTH_SHORT).show();
            phone2.requestFocus();
            phone2.setError("Enter another number");
            return false;
        }
        return true;
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
            }
        });
        cameraTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReferenceContactsActivity.this, TakeImage.class);
                intent.putExtra("from", "camera");
                intent.putExtra("value", value);
                startActivityForResult(intent, CAMERA_REQUEST);
                dialog.dismiss();
            }
        });
        galleryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReferenceContactsActivity.this, TakeImage.class);
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
                File imgFile = new File(data.getStringExtra("filePath"));
//                if (imgFile.exists()) {
//                    .setImageURI(Uri.fromFile(imgFile));
                    int count = 0;
                    count = path.lastIndexOf("/");
                    if (from == 1){
                        txt_contoimage.setText(path.substring(count+1));
                    }else if(from ==2){
                        txt_conttimage.setText(path.substring(count+1));
                    }
//                }
            }else if(requestCode == CHOOSE_FILE_REQUESTCODE){
                path = data.getData().getPath();
                if (path != null) {
                    File drivefile = new File(path);
//                    if (drivefile.exists()) {
//                    .setImageURI(Uri.fromFile(imgFile));
                        int count = 0;
                        count = path.lastIndexOf("/");
                        if (from == 1) {
                            txt_contoimage.setText(path.substring(count + 1));
                        } else if (from == 2) {
                            txt_conttimage.setText(path.substring(count + 1));
                        }
//                    }
                }
            }else {
                path = "";
            }
            Log.i("File Path", "" + path);
        }
    }

}
