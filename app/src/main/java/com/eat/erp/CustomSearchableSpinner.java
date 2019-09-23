package com.eat.erp;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class CustomSearchableSpinner extends SearchableSpinner {

    public static boolean isSpinnerDialogOpen = false;

    public CustomSearchableSpinner(Context context) {
        super(context);

    }

    public CustomSearchableSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSearchableSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //Log.e("spinner", "working");

        FontChangeCrawler fontChanger = new FontChangeCrawler(getResources().getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup)v);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (!isSpinnerDialogOpen) {
                isSpinnerDialogOpen = true;
                return super.onTouch(v, event);
            }
            isSpinnerDialogOpen = false;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isSpinnerDialogOpen = false;
            }
        }, 500);
        return true;
    }

}
