package com.eat.erp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CustomGridViewAdapter extends BaseAdapter {
    Context context;
    int logos[];
    LayoutInflater inflater;
    public CustomGridViewAdapter(Context applicationContext, int[] logos) {
        this.context = applicationContext;
        this.logos = logos;
        inflater = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return logos.length;
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        view = inflater.inflate(R.layout.grid_image_icon, null); // inflate the layout
        ImageView icon = (ImageView) view.findViewById(R.id.grid_image);
        TextView title=view.findViewById(R.id.image_title);
        // get the reference of ImageView
        icon.setImageResource(logos[i]);
        // set logo images
        switch(i){
            case 0:
                title.setText("Checkout");
                break;
            case 1:
                title.setText("New Visit");
                break;
            case 2:
                title.setText("Old Visit");
                break;
            case 3:
                title.setText("Activity");
                break;
        }

        return view;
    }
}