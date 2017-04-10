package com.hirebuddy.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hirebuddy.R;
import com.hirebuddy.util.ScrollableImageView;

import java.io.File;

public class ProfileEditActivity extends AppCompatActivity implements View.OnClickListener {
    String path;
    FrameLayout container;
    ImageView image;
    private Intent cropIntent;
    private Uri uri;
    private TextView tv_save_pea;
    private RelativeLayout rl_image_parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        image = (ImageView)findViewById(R.id.image);
        rl_image_parent = (RelativeLayout) findViewById(R.id.rl_image_parent);

        tv_save_pea = (TextView) findViewById(R.id.tv_save_pea);
        tv_save_pea.setOnClickListener(this);

        Intent intent = getIntent();
//        image.setImageBitmap(BitmapFactory.decodeFile(intent.getStringExtra("image")));
//        byte[] b = intent.getByteArrayExtra("image_byte");

        Bitmap bMap = BitmapFactory.decodeFile(intent.getStringExtra("image"));
        container= (FrameLayout)findViewById(R.id.container);

        uri = Uri.fromFile(new File(intent.getStringExtra("image")));

//        image.setImageBitmap(bMap);
//        setImageBitmap(bMap);
//        Bitmap bitmap = BitmapFactory.decodeByteArray(b,0,b.length);

        setImageBitmap(bMap);
//        image.setImageBitmap(bMap);
    }

    private void setImageBitmap(Bitmap bmp) {
        ImageView imageView = new ScrollableImageView(this);
        imageView.setScaleType(ImageView.ScaleType.FIT_START);
        imageView.canScrollHorizontally(0);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(1080, getWindow().getDecorView().getHeight()));

        imageView.setImageBitmap(bmp);
        FrameLayout container = (FrameLayout) findViewById(R.id.container);
//        container.addView(imageView);

//        image.setImageBitmap(bmp);
        container.addView(imageView);
    }

    private void cropImage() {

        try {
            cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(uri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("outputX", 480);
            cropIntent.putExtra("outputY", 480);
            cropIntent.putExtra("aspectX", 3);
            cropIntent.putExtra("aspectY", 4);
            cropIntent.putExtra("scaleUpIfNeeded", true);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, 1);

        } catch (ActivityNotFoundException e)
        {
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            if (data != null)
            {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = bundle.getParcelable("data");
                image.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view == tv_save_pea)
        {
            cropImage();
        }
    }
}
