package com.eat.erp;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import com.imanoweb.calendarview.DayDecorator;
import com.imanoweb.calendarview.DayView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

public class MySelectorDecorator implements DayDecorator {

    private final Drawable drawable_present;
    private final Drawable drawable_absent;
    private final Drawable drawable_late;
    //private final Drawable drawable_current;

    String attendance="";

    public MySelectorDecorator(Activity context, String attendance) {
        drawable_present = context.getResources().getDrawable(R.drawable.round1);
        drawable_absent=context.getResources().getDrawable(R.drawable.absent_bg);
        drawable_late=context.getResources().getDrawable(R.drawable.late_bg);
        //drawable_current=context.getResources().getDrawable(R.drawable.current_date_bg);
        this.attendance=attendance;
    }

    @Override
    public void decorate(DayView view) {


        try{

            JSONObject object=new JSONObject(this.attendance);
            //Log.e("attend", this.attendance);
            String present=object.getString("present");
            String absent=object.getString("absent");
            String late=object.getString("late");
            if(!present.equals("0")){
            JSONObject obj_p=new JSONObject(present);

           // Log.e("attend","leng: "+arr_p.length()+"");
                Iterator<String> keys = obj_p.keys();
                while(keys.hasNext()){
                    String datepresent=keys.next();
                    if(view.getDate().toString().contains(datepresent)){
                        view.setBackground(drawable_present);
                        //Log.e("atria",view.getDate()+" present");
                    }
                }
            }

            if(!absent.equals("0")) {
                JSONObject obj_a=new JSONObject(absent);
                // Log.e("attend","leng: "+arr_p.length()+"");
                Iterator<String> keys = obj_a.keys();
                while(keys.hasNext()){
                    String dateabsent=keys.next();
                    if(view.getDate().toString().contains(dateabsent)){
                        view.setBackground(drawable_absent);
                        //Log.e("atria",view.getDate()+" present");
                    }
                }
            }
            if(!late.equals("0")){
                JSONObject obj_l=new JSONObject(late);
                // Log.e("attend","leng: "+arr_p.length()+"");
                Iterator<String> keys = obj_l.keys();
                while(keys.hasNext()){
                    String datelate=keys.next();
                    if(view.getDate().toString().contains(datelate)){
                        view.setBackground(drawable_late);
                        //Log.e("atria",view.getDate()+" present");
                    }
                }
            }

        }catch (Exception e){
            Log.e("attend", "e: "+e.toString());
        }
    }
}
