package com.hirebuddy.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hirebuddy.R;
import com.hirebuddy.sharedprefrences.SPreferenceKey;
import com.hirebuddy.sharedprefrences.SharedPreferenceWriter;
import com.hirebuddy.util.MyToast;
import com.hirebuddy.util.ScrollableImageView;
import com.hirebuddy.util.TakeImage;
import com.hirebuddy.util.TakeImage2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import static android.R.attr.bitmap;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private static final int CAMERA_REQUEST = 11;
    static int RESULT_LOAD_IMAGE = 1;
    private static int CHOOSE_REQUESTCODE = 13;
    TextView txt_dob_profile, txt_dob;
    RadioGroup radioGroupGender;
    RadioButton radioButtonMale;
    DatePickerDialog datePickerDialog;
    LinearLayout ll_dob;
    EditText edt_first_name_profile, edt_last_name_profile, edt_email_profile;
    ImageView circular_image_pofile;
    private int i;
    String path = "";
    boolean circular;

    // LISTENER FOR DATE PICKER
    // WHEN DATE SELECTED
    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            String day;
            String mnth;
            monthOfYear = monthOfYear + 1;

            if (dayOfMonth < 10) {
                day = "0" + dayOfMonth;
            } else {
                day = dayOfMonth + "";
            }
            if (monthOfYear < 10) {
                mnth = "0" + monthOfYear;
            } else {
                mnth = monthOfYear + "";
            }
            String selectedDate = year + "-" + monthOfYear + "-" + day;
            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            if (camparedateIsValid(selectedDate, currentDate) == true) {
                String currentyear = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
                int cyear = Integer.parseInt(currentyear);
                if (year <= cyear - 15) {
                    txt_dob_profile.setText(day + "/" + mnth + "/" + year);

                } else {
                    Toast.makeText(ProfileActivity.this, "Age should be greater than or equal to 15 years", Toast.LENGTH_SHORT).show();
//                    Snackbar.make(view, "Age should be greater than or equal to 15 years", Snackbar.LENGTH_SHORT)
//                            .setAction("Action", null).show();

                    txt_dob_profile.setText(getResources().getString(R.string.ddmmyy));
                }
            }
        }
    };
    private String regularExpresionOfEmailId = "^(?!.{51})([A-Za-z0-9])+([A-Za-z0-9._-])+@([A-Za-z0-9._-])+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern patternForEmailId = Pattern.compile(regularExpresionOfEmailId);
    private ImageView img_backimage;

    public static boolean camparedateIsValid(String selectedDate, String currentDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date SDate = format.parse(selectedDate);
            Date CDate = format.parse(currentDate);
            if (SDate.before(CDate)) {
                return true;
            }
            return false;
        } catch (Exception e) {

        }
        return false;
    }

    @SuppressLint("NewApi")
    public static Bitmap blurRenderScript(Context context, Bitmap smallBitmap, int radius) {
        try {
            smallBitmap = RGB565toARGB888(smallBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bitmap bitmap = Bitmap.createBitmap(
                smallBitmap.getWidth(), smallBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

        RenderScript renderScript = RenderScript.create(context);

        Allocation blurInput = Allocation.createFromBitmap(renderScript, smallBitmap);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(radius); // radius must be 0 < r <= 25
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();

        return bitmap;

    }

    private static Bitmap RGB565toARGB888(Bitmap img) throws Exception {
        int numPixels = img.getWidth() * img.getHeight();
        int[] pixels = new int[numPixels];

        //Get JPEG pixels.  Each int is the color values for one pixel.
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        //Create a Bitmap of the appropriate format.
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);

        //Set RGB pixels.
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txt_dob_profile = (TextView) findViewById(R.id.txt_dob_profile);
        txt_dob = (TextView) findViewById(R.id.txt_dob);
        edt_first_name_profile = (EditText) findViewById(R.id.edt_first_name_profile);
        edt_last_name_profile = (EditText) findViewById(R.id.edt_last_name_profile);
        edt_email_profile = (EditText) findViewById(R.id.edt_email_profile);
        circular_image_pofile = (ImageView) findViewById(R.id.circular_image_pofile);
        circular_image_pofile.setOnClickListener(this);
        txt_dob.setOnClickListener(this);

        img_backimage = (ImageView) findViewById(R.id.img_backimage);
        findViewById(R.id.img_coveruicon).setOnClickListener(this);
//        img_backimage.setOnClickListener(this);


        ll_dob = (LinearLayout) findViewById(R.id.ll_dob);
        ll_dob.setOnClickListener(this);

        radioGroupGender = (RadioGroup) findViewById(R.id.rg_gender);
        radioGroupGender.setOnCheckedChangeListener(this);


        radioButtonMale = (RadioButton) findViewById(R.id.rb_male_profile);
        radioButtonMale.setChecked(true);

//        INSTANTIATE DATE PICKER DIALOG
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) - 29);
        calendar.add(Calendar.YEAR, -15);
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

    }
    private void setImageBitmap(Bitmap bmp) {
        ImageView imageView = new ScrollableImageView(this);
        imageView.setScaleType(ImageView.ScaleType.FIT_START);
        imageView.canScrollHorizontally(0);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(1080, getWindow().getDecorView().getHeight()));
        imageView.setImageBitmap(bmp);
        ViewGroup container = (ViewGroup) findViewById(R.id.linearLayout);
        container.addView(imageView);
    }
    public void next(View view) {
        validation(view);
    }

    public void validation(View view) {
        if (circular) {
            if (edt_first_name_profile.getText().toString().length() > 0) {
                if (edt_last_name_profile.getText().toString().length() > 0) {
                    if (!(txt_dob_profile.getText().toString().equalsIgnoreCase(getString(R.string.ddmmyy)))) {
                        if (edt_email_profile.getText().toString().length() > 0) {
                            if (patternForEmailId.matcher(edt_email_profile
                                    .getText().toString().trim()).matches()) {

                                if (SharedPreferenceWriter.getInstance(this).getString(SPreferenceKey.LOGIN).equals("customer")) {
                                    Snackbar.make(view, "Home Screen", Snackbar.LENGTH_SHORT).show();
                                } else if (SharedPreferenceWriter.getInstance(this).getString(SPreferenceKey.LOGIN).equals("temporary")) {
                                    startActivity(new Intent(this, ReferenceContactsActivity.class));
                                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                                } else {
                                    startActivity(new Intent(this, SecurityQuestionsActivity.class));
                                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                                }

                            } else {
                                Snackbar.make(view, getString(R.string.pevei), Snackbar.LENGTH_SHORT)
                                        .setAction("Action", null).show();
                                edt_email_profile.requestFocus();
//                                edt_email_profile.setError(getString(R.string.invemail));
                            }
                        } else {
                            Snackbar.make(view, getString(R.string.peyei), Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                            edt_email_profile.requestFocus();
//                            edt_email_profile.setError(getString(R.string.peyei));
                        }
                    } else {
                        Snackbar.make(view, getString(R.string.pedob), Snackbar.LENGTH_SHORT).show();
                    }

                } else {
                    Snackbar.make(view, getString(R.string.peyln), Snackbar.LENGTH_SHORT).show();

                    edt_last_name_profile.requestFocus();
//                    edt_last_name_profile.setError(getString(R.string.peyln));
                }
            } else {
                Snackbar.make(view, getString(R.string.peyfn), Snackbar.LENGTH_SHORT).show();
                edt_first_name_profile.requestFocus();
//                edt_first_name_profile.setError(getString(R.string.peyln));
//                ((TextInputLayout)findViewById(R.id.til_fname)).setError(getString(R.string.peyln));
            }
        } else {
            Snackbar.make(view, getString(R.string.puyi), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_dob:
                datePickerDialog.show();
                break;

            case R.id.ll_dob:
                datePickerDialog.show();
                break;

            case R.id.circular_image_pofile:
                i = 1;
                takePic(5);
                break;

            case R.id.img_coveruicon:
                i = 2;
//                DisplayMetrics displayMetrics = new DisplayMetrics();
//                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//                int height = displayMetrics.heightPixels;
//                int width = displayMetrics.widthPixels;
                takePic(25);
                break;
        }
    }

    //    RADIO BUTTON LISTENER
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

        RadioButton radioButton = (RadioButton) radioGroup.findViewById(checkedId);
        if (radioButton.isChecked()) {
//            MyToast.show(this, radioButton.getText()+"", false);
        }
    }
//    *****************************************************************************


//second parametre is radius


//    FOR BLURRING IMAGE

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
        cameraTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, TakeImage.class);
                intent.putExtra("from", "camera");
                intent.putExtra("value", value);
                startActivityForResult(intent, CAMERA_REQUEST);
                dialog.dismiss();
            }
        });
        galleryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, TakeImage.class);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_OK || requestCode == 0) {
//            String filePath = data.getExtras().getString("filePath");
            path = data.getStringExtra("filePath");
            if (path != null) {

//                Bitmap image = blurRenderScript(this, BitmapFactory.decodeFile(path), 10);

                if (i == 1) {
                    circular_image_pofile.setImageURI(Uri.parse(path));
                    circular = true;

                } else if (i == 2) {

//                    Bitmap bitmap = BitmapFactory.decodeFile(path, new BitmapFactory.Options());
//                    bitmap = Bitmap.createScaledBitmap(bitmap,parent.getWidth(),parent.getHeight(),true);

                    Bitmap bMap = BitmapFactory.decodeFile(path);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bMap .compress(Bitmap.CompressFormat.JPEG, 50, baos);
                    //bMap is the bitmap object
                    byte[] b = baos.toByteArray();

                    File image = new File(path);
                    Intent intent = new Intent(this, ProfileEditActivity.class);
                    intent.putExtra("image",path);
//                    ("path", path);
                    startActivity(intent);

                    setImageBitmap(bMap);
                }
            }
        }
    }
}
