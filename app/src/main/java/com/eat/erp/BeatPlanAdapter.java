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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BeatPlanAdapter extends ArrayAdapter<BeatPlan> {
    private Context mContext;
    private List<BeatPlan> beatPlan;
    customButtonListener customListner;
    customButtonListener1 customListner1;

    public interface customButtonListener {
        public void onButtonClickListner(View convertView, int position,String value);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    public interface customButtonListener1 {
        public void onButtonClickListner1(View convertView, int position,String value);
    }

    public void setCustomButtonListner1(customButtonListener1 listener1) {
        this.customListner1 = listener1;
    }

    private static class ViewHolder {
        TextView txtsrno;
        TextView txtretailername;
        TextView txtid;
        TextView txtcheckin;
        TextView txtchanneltype;
        TextView txtfollowtype;
        TextView txttemp;
        TextView txtlat;
        TextView txtlong;
        Button btncheckin;
        Button geo_loc;
    }

    public BeatPlanAdapter(ArrayList<BeatPlan> beatPlan, Context context) {
        super(context, R.layout.activity_route_list, beatPlan);
        this.beatPlan = beatPlan;
        this.mContext=context;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final BeatPlan beatPlan1 = getItem(position);
        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.activity_route_list, parent, false);
            Typeface face= Typeface.createFromAsset(getContext().getAssets(), "AvenirNextLTPro-Regular.otf");
            Typeface face1= Typeface.createFromAsset(getContext().getAssets(), "AvenirNextLTPro-Demi.otf");
            viewHolder.txtsrno = (TextView) convertView.findViewById(R.id.txtsrno);
            viewHolder.txtretailername = (TextView) convertView.findViewById(R.id.txtretailername);
            viewHolder.txtid = (TextView) convertView.findViewById(R.id.txtid);
            viewHolder.txtcheckin= (TextView) convertView.findViewById(R.id.txtcheckin);
            viewHolder.txtfollowtype = (TextView) convertView.findViewById(R.id.txtfollowtype);
            viewHolder.txtchanneltype = (TextView) convertView.findViewById(R.id.txtchanneltype);
            viewHolder.txttemp = (TextView) convertView.findViewById(R.id.txttemp);
            viewHolder.txtlat = (TextView) convertView.findViewById(R.id.txtlat);
            viewHolder.txtlong = (TextView) convertView.findViewById(R.id.txtlong);
            viewHolder.btncheckin = (Button) convertView.findViewById(R.id.btncheckin);
            viewHolder.geo_loc = (Button) convertView.findViewById(R.id.geo_loc);

            viewHolder.txtsrno.setTypeface(face);
            viewHolder.txtretailername.setTypeface(face);
            viewHolder.btncheckin.setTypeface(face1);

            viewHolder.txtsrno.setTextSize(12);
            viewHolder.txtretailername.setTextSize(12);
            viewHolder.btncheckin.setTextSize(12);



            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }



        viewHolder.txtsrno.setText(beatPlan1.getsrno());
        viewHolder.txtretailername.setText(beatPlan1.getretailer());
        viewHolder.txtid.setText(beatPlan1.getid());
        viewHolder.txtchanneltype.setText(beatPlan1.getChannel_type());
        viewHolder.txtfollowtype.setText(beatPlan1.getFollow_type());
        viewHolder.txttemp.setText(beatPlan1.gettemp());
        viewHolder.txtlat.setText(beatPlan1.getlatitude());
        viewHolder.txtlong.setText(beatPlan1.getlongitude());

        if(beatPlan1.getbuttonname().toUpperCase().equals("EDIT")) {
            //viewHolder.btncheckin.set
            Drawable img = getContext().getResources().getDrawable( R.drawable.ic_edit_blue_24dp );
            viewHolder.btncheckin.setCompoundDrawablesWithIntrinsicBounds( img, null, null, null);
            viewHolder.btncheckin.setText("");
            viewHolder.txtcheckin.setText("EDIT");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(80, 80);
            viewHolder.btncheckin.setLayoutParams(params);
        }
        else {
            viewHolder.btncheckin.setText(beatPlan1.getbuttonname());
            viewHolder.btncheckin.setText("");
            viewHolder.txtcheckin.setText("CHECKIN");
            Drawable img = getContext().getResources().getDrawable( R.drawable.ic_flag_black_24dp );
            viewHolder.btncheckin.setCompoundDrawablesWithIntrinsicBounds( img, null, null, null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(80, 80);
            viewHolder.btncheckin.setLayoutParams(params);
        }

        //Toast.makeText(getContext(),beatPlan1.getlatitude(),Toast.LENGTH_LONG).show();

        if((beatPlan1.getlatitude().equals("null") && beatPlan1.getlongitude().equals("null"))|| (beatPlan1.getlatitude().equals("0") && beatPlan1.getlongitude().equals("0"))) {
            Drawable img = getContext().getResources().getDrawable( R.drawable.ic_place_strike );
            viewHolder.geo_loc.setCompoundDrawablesWithIntrinsicBounds( img, null, null, null);

        }
        else {
            Drawable img = getContext().getResources().getDrawable( R.drawable.ic_place_blue_24dp );
            viewHolder.geo_loc.setCompoundDrawablesWithIntrinsicBounds( img, null, null, null);

        }


        viewHolder.btncheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customListner != null) {
                    customListner.onButtonClickListner(result,position,beatPlan1.getbuttonname());
                }
            }
        });

        viewHolder.geo_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customListner1 != null) {
                    customListner1.onButtonClickListner1(result,position,beatPlan1.getbuttonname());
                }
            }
        });



        if(beatPlan1.getcheckin().equals("false")) {
            viewHolder.btncheckin.setVisibility(View.GONE);
        }
        else {
            viewHolder.btncheckin.setVisibility(View.VISIBLE);
        }
        // Return the completed view to render on screen
        return convertView;
    }


}
