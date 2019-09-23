package com.eat.erp;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends ArrayAdapter<Order> {
    private Context mContext;
    private List<Order> order;
    customButtonListener customListner;

    public interface customButtonListener {
        public void onButtonClickListner(View convertView, int position, String value);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    private static class ViewHolder {
        TextView txtsrno;
        TextView txtretailername;
        TextView txtid;
        TextView txtdistributor;
        Button btncheckin;
    }

    public OrderAdapter(ArrayList<Order> order, Context context) {
        super(context, R.layout.activity_order_list, order);
        this.order = order;
        this.mContext=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Order order1 = getItem(position);
        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.activity_order_list, parent, false);
            Typeface face= Typeface.createFromAsset(getContext().getAssets(), "AvenirNextLTPro-Regular.otf");
            Typeface face1= Typeface.createFromAsset(getContext().getAssets(), "AvenirNextLTPro-Demi.otf");
            viewHolder.txtsrno = (TextView) convertView.findViewById(R.id.txtsrno);
            viewHolder.txtretailername = (TextView) convertView.findViewById(R.id.txtretailername);
            viewHolder.txtid = (TextView) convertView.findViewById(R.id.txtid);
            viewHolder.txtdistributor= (TextView) convertView.findViewById(R.id.txtretailer);
            viewHolder.btncheckin = (Button) convertView.findViewById(R.id.btncheckin);

            viewHolder.txtsrno.setTypeface(face);
            viewHolder.txtretailername.setTypeface(face);
            viewHolder.txtdistributor.setTypeface(face);
            viewHolder.btncheckin.setTypeface(face1);

            viewHolder.txtsrno.setTextSize(12);
            viewHolder.txtretailername.setTextSize(12);
            viewHolder.txtdistributor.setTextSize(12);
            viewHolder.btncheckin.setTextSize(12);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtsrno.setText(order1.getsrno());
        viewHolder.txtretailername.setText(order1.getretailer()+" ("+order1.getlocation()+")");
        viewHolder.txtid.setText(order1.getid());
        viewHolder.txtdistributor.setText(order1.getdistributor());

        viewHolder.btncheckin.setText("View");

        viewHolder.btncheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customListner != null) {
                    customListner.onButtonClickListner(result,position,order1.getbuttonname());
                }
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }


}
