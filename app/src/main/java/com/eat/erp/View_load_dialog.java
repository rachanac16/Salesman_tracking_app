package com.eat.erp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


public class View_load_dialog {

    Activity activity;
    Dialog dialog;

    public View_load_dialog(Activity activity) {
        this.activity = activity;
    }

    public void showDialog() {

        dialog  = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_custom_loader);

        final ImageView gifImageView = dialog.findViewById(R.id.custom_loading_imageView);
        /*Runnable runnable = new Runnable() {
            @Override
            public void run() {
                gifImageView.animate().rotationBy(360).withEndAction(this).setDuration(5000).setInterpolator(new LinearInterpolator()).start();
            }
        };
        gifImageView.animate().rotationBy(360).withEndAction(runnable).setDuration(5000).setInterpolator(new LinearInterpolator()).start();*/


        //GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(gifImageView);
        Glide.with(activity.getApplicationContext())
                .load(R.drawable.loading5)
                .placeholder(R.drawable.loading5)
                .centerCrop()
                .into(gifImageView);

        dialog.show();

    }

    public void hideDialog(){
        dialog.dismiss();
    }

    public boolean isShowing(){
        return dialog.isShowing();
    }

}
