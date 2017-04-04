package com.hirebuddy.util;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;

public class Fonts {

	public static void railawaySemiBold(TextView tv, AssetManager asm)
	{
		Typeface fontsStyle= Typeface.createFromAsset(asm, "fonts/Raleway-SemiBold.ttf");
		tv.setTypeface(fontsStyle);
	}
}
