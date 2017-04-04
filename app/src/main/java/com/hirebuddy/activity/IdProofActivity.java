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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hirebuddy.R;
import com.hirebuddy.util.Fonts;
import com.hirebuddy.util.TakeImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IdProofActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Spinner spin_idtype, spin_addtype;
    TextView txt_s_one, txt_s_two, txt_idproof, txt_addproof, txt_uidproof, txt_uaddproof;
    private Button btnNext;
    String path = "";
    int from = 0;
    private static final int CAMERA_REQUEST = 11;
    static int RESULT_LOAD_IMAGE = 1;
    private static int PICKFILE_REQUEST_CODE = 12;
    private static int CHOOSE_FILE_REQUESTCODE = 13;
    private EditText edt_eyq_idproof, edt_eyq_addproof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_proof);

        spin_idtype = (Spinner) findViewById(R.id.spin_idtype);
        spin_addtype = (Spinner) findViewById(R.id.spin_addtype);
        txt_s_one = (TextView) findViewById(R.id.txt_s_one);
        txt_s_two = (TextView) findViewById(R.id.txt_s_two);
        txt_idproof = (TextView) findViewById(R.id.txt_idproof);
        txt_addproof = (TextView) findViewById(R.id.txt_addproof);
        txt_uidproof = (TextView) findViewById(R.id.txt_uidproof);
        txt_uaddproof = (TextView) findViewById(R.id.txt_uaddproof);

        edt_eyq_idproof = (EditText) findViewById(R.id.edt_eyq_idproof);
        edt_eyq_addproof = (EditText) findViewById(R.id.edt_eyq_addproof);

        findViewById(R.id.txt_uidproof_remove).setOnClickListener(this);
        findViewById(R.id.txt_uaddproof_remove).setOnClickListener(this);

        spin_idtype.setOnItemSelectedListener(this);
        spin_addtype.setOnItemSelectedListener(this);

        Fonts.railawaySemiBold(txt_idproof, getAssets());
        Fonts.railawaySemiBold(txt_addproof, getAssets());

        btnNext = (Button) findViewById(R.id.next_id_proof);
        btnNext.setOnClickListener(this);
        txt_uaddproof.setOnClickListener(this);
        txt_uidproof.setOnClickListener(this);

        // Spinner Drop down elements
        List<String> adressproof = new ArrayList<String>();
        adressproof.add(getString(R.string.choose_address_proof));
        adressproof.add("Aadhaar Card");
        adressproof.add("Passport");
        adressproof.add("Voter ID card");
        adressproof.add("Telephone  bill");
        adressproof.add("Electricity bill");
        adressproof.add("Income Tax Assessment Order");
        adressproof.add("Proof of Gas Connection");
        adressproof.add("Other");

        List<String> identityproof = new ArrayList<String>();
        identityproof.add(getString(R.string.cit));
        identityproof.add("Aadhaar Card");
        identityproof.add("Passport");
        identityproof.add("Voter ID card");
        identityproof.add("Pan Card");
        identityproof.add("Driving Licence");
        identityproof.add("Other");
        // Creating adapter for spinner
        ArrayAdapter<String> idAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, identityproof);
        ArrayAdapter<String> addAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, adressproof);
        // Drop down layout style - list view with radio button
        idAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        addAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // attaching data adapter to spinner
        spin_idtype.setAdapter(idAdapter);
        spin_addtype.setAdapter(addAdapter);
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
                if (getPackageManager().resolveActivity(sIntent, 0) != null) {
                    // it is device with samsung file manager
                    chooserIntent = Intent.createChooser(sIntent, "Open file");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intent});
                } else {
                    chooserIntent = Intent.createChooser(intent, "Open file");
                }

                try {
                    startActivityForResult(chooserIntent, CHOOSE_FILE_REQUESTCODE);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "No suitable File Manager was found.", Toast.LENGTH_SHORT).show();
                }

//                Intent intent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
//                intent.putExtra("CONTENT_TYPE", "*/*");
//                intent.addCategory(Intent.CATEGORY_DEFAULT);
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("file/*");
//                startActivityForResult(intent, PICKFILE_REQUEST_CODE);
                dialog.dismiss();
            }
        });
        cameraTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IdProofActivity.this, TakeImage.class);
                intent.putExtra("from", "camera");
                intent.putExtra("value", value);
                startActivityForResult(intent, CAMERA_REQUEST);
                dialog.dismiss();
            }
        });
        galleryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IdProofActivity.this, TakeImage.class);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        ((TextView) view).setText("");

        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.spin_idtype) {
            txt_s_one.setText(item);
            if (txt_s_one.getText().toString().equalsIgnoreCase("other"))
            {
                txt_s_one.setTextColor(getResources().getColor(R.color.yellow));
                edt_eyq_idproof.setVisibility(View.VISIBLE);
                edt_eyq_idproof.requestFocus();
            }
            else {
                edt_eyq_idproof.setVisibility(View.GONE);
            }
        } else {
            txt_s_two.setText(item);
            if (txt_s_two.getText().toString().equalsIgnoreCase("other"))
            {
                txt_s_two.setTextColor(getResources().getColor(R.color.yellow));
                edt_eyq_addproof.setVisibility(View.VISIBLE);
                edt_eyq_addproof.requestFocus();
            }else {
                edt_eyq_addproof.setVisibility(View.GONE);
            }
        }



    }

    public void onNothingSelected(AdapterView<?> arg0) {
//        txt_s_one.setText(getString(R.string.cit));
    }

    //    public void next(View view) {
//        startActivity(new Intent(this, PoliceVerificationActivity.class));
//        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
//    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == this.RESULT_OK) {
            path = data.getStringExtra("filePath");
            if (path != null) {
                File imgFile = new File(data.getStringExtra("filePath"));
                if (imgFile.exists()) {
//                    .setImageURI(Uri.fromFile(imgFile));
                    int count = 0;
                    count = path.lastIndexOf("/");
                    if (from == 1) {
                        txt_uidproof.setText(path.substring(count + 1));
                    } else if (from == 2) {
                        txt_uaddproof.setText(path.substring(count + 1));
                    }
                }
            } else if (requestCode == CHOOSE_FILE_REQUESTCODE) {
                path = data.getData().getPath();
                if (path != null) {
//                File drivefile = new File(path);
//                if (pathts()) {
//                    .setImageURI(Uri.fromFile(imgFile));
                    int count = 0;
                    count = path.lastIndexOf("/");
                    if (from == 1) {
                        txt_uidproof.setText(path.substring(count + 1));
                    } else if (from == 2) {
                        txt_uaddproof.setText(path.substring(count + 1));
                    }
//                }
                }
            } else {
                path = "";
            }
            Log.i("File Path", "" + path);
        }
    }

    public void idvalidation(View view) {
        if (!(txt_s_one.getText().toString().equals(getString(R.string.cit)))) {
            if (!(txt_uidproof.getText().equals(getString(R.string.nfs)))) {
                if (!(txt_s_two.getText().toString().equals(getString(R.string.choose_address_proof)))) {
                    if (!(txt_uaddproof.getText().equals(getString(R.string.nfs)))) {
                        if (edt_eyq_idproof.isShown() && edt_eyq_idproof.getText().toString().equals("")){
                            edt_eyq_idproof.setError("Enter ID proof type");
                        }else {
                            if (edt_eyq_addproof.isShown() && edt_eyq_addproof.getText().toString().equals("")){
                                edt_eyq_addproof.setError("Enter Address proof type");
                            }else {
                                startActivity(new Intent(this, PoliceVerificationActivity.class));
                                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                            }
                        }
                    } else {
                        Snackbar.make(view, getString(R.string.puyap), Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                } else {
                    Snackbar.make(view, getString(R.string.psyap), Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            } else {
                Snackbar.make(view, getString(R.string.puyip), Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        } else {
            Snackbar.make(view, getString(R.string.psyit), Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.next_id_proof:
                idvalidation(v);
                break;

            case R.id.txt_uaddproof:
                if (txt_s_two.getText().toString().equalsIgnoreCase("other")){
                    if (edt_eyq_addproof.getText().toString().equals("")){
                        edt_eyq_addproof.setError("Enter Address proof type");
                    }else {
                        takePic(1);
                        from = 1;
                    }
                }else {
                    takePic(1);
                    from = 1;
                }
                takePic(1);
                from = 2;
                break;
            case R.id.txt_uidproof:
                if (txt_s_one.getText().toString().equalsIgnoreCase("other")){
                    if (edt_eyq_idproof.getText().toString().equals("")){
                        edt_eyq_idproof.setError("Enter ID proof type");
                    }else {
                        takePic(1);
                        from = 1;
                    }
                }else {
                    takePic(1);
                    from = 1;
                }

                break;
            case R.id.txt_uidproof_remove:
                txt_uidproof.setText(getString(R.string.nfs));
                break;
            case R.id.txt_uaddproof_remove:
                txt_uaddproof.setText(getString(R.string.nfs));
                break;
        }
    }
}
