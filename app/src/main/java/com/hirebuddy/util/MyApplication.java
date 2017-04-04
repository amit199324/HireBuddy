package com.hirebuddy.util;

import android.app.Application;

import com.hirebuddy.util.TypefaceUtil;

/**
 * Created by mobulous11 on 8/3/17.
 */

public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "SERIF", "Raleway-Light.ttf");
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }
}
