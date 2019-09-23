package com.eat.erp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.JsonArray;
import com.imanoweb.calendarview.CalendarListener;
import com.imanoweb.calendarview.CustomCalendarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class AttendanceActivity extends AppCompatActivity {


    boolean restartflag=false, restartflag2=false, flagpressed=false;
    Context context;
    LinearLayout llhome, llrouteplan, llorder, llaccount, llevent_time;
    ProgressDialog spinnerDialog;
    session session;
    TextView txtname, txtinitial, txttitle, txtcheckout ,txt_checkin_attend, txt_checkout_attend, lbl_checkin_time, lbl_checkout_time, lbl_date, txt_date_current;
    LinearLayout llcheckin, llcheckout;
    Typeface face , face1;
    View_load_dialog View_load_dialog;
    RelativeLayout container_attend;
    Button fab_btn;
    int logos[] = {R.drawable.option_checkout, R.drawable.option_new_visit, R.drawable.option_old_visit, R.drawable.option_timeline};
    TextView no_present, no_absent, no_late, no_inactive;
    ImageView checkin_loc;
    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);


        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));

        context=AttendanceActivity.this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        restartflag=false;
        restartflag2=false;
        flagpressed=false;
        session = new session(context);

        no_absent=findViewById(R.id.no_absent);
        no_late=findViewById(R.id.no_late);
        no_present=findViewById(R.id.no_present);
        txt_checkin_attend=findViewById(R.id.txt_checkin_attend);
        txt_checkout_attend=findViewById(R.id.txt_checkout_attend);
        lbl_checkin_time=findViewById(R.id.lbl_checkin_time);
        lbl_checkout_time=findViewById(R.id.lbl_checkout_time);
        llevent_time=findViewById(R.id.llevent_time);
        lbl_date=findViewById(R.id.lbl_date);
        txt_date_current=findViewById(R.id.txt_date_current);
        checkin_loc=findViewById(R.id.checkin_loc);
        /*try {
            GPSTracker gps = new GPSTracker(this);
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            Log.e("acc", "lat: " + latitude + " long: " + longitude);
        }catch (Exception e){
            Log.e("acc", "err: "+e.toString());
        }*/
        face= Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Demi.otf");
        face1= Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Regular.otf");
        txttitle = (TextView) findViewById(R.id.txttitle);
        txttitle.setText("Attendance");
        container_attend=findViewById(R.id.container_attend);

        llhome = (LinearLayout) findViewById(R.id.llhome);
        llrouteplan = (LinearLayout) findViewById(R.id.llrouteplan);
        llorder = (LinearLayout) findViewById(R.id.llorder);
        llaccount = (LinearLayout) findViewById(R.id.llaccount);
        txtname = (TextView) findViewById(R.id.textView2);
        txtinitial = (TextView) findViewById(R.id.txtinitial);
        TextView txtcheckin = (TextView) findViewById(R.id.txtcheckin);
        llcheckin = (LinearLayout) findViewById(R.id.llcheckin);
        llcheckout = (LinearLayout) findViewById(R.id.llcheckout);

        fab_btn=findViewById(R.id.fab_btn);
        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flagpressed){
                    flagpressed=false;
                    container_attend.removeViewAt(container_attend.getChildCount()-2);
                    fab_btn.setRotation(0);
                    llhome.setEnabled(true);
                    llrouteplan.setEnabled(true);
                    llorder.setEnabled(true);
                    llaccount.setEnabled(true);
                }else{
                    flagpressed=true;
                    llhome.setEnabled(false);
                    llrouteplan.setEnabled(false);
                    llorder.setEnabled(false);
                    llaccount.setEnabled(false);
                    showMenuOption();
                    fab_btn.setRotation(45);
                }

            }
        });

        TextView todaydate = (TextView) findViewById(R.id.todaydate);
        Calendar c=Calendar.getInstance();
        String dayNumberSuffix = dashboardActivity.getDayNumberSuffix(c.get(Calendar.DAY_OF_MONTH));
        Date date3 = Calendar.getInstance().getTime();
        SimpleDateFormat timeFormatter3 = new SimpleDateFormat("dd'"+dayNumberSuffix+"' MMM yyyy");
        String time3 = timeFormatter3.format(date3);
        todaydate.setText(time3+ ", Today");

        View_load_dialog = new View_load_dialog(this);


        llhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View_load_dialog.showDialog();

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(context, dashboardActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivityForResult(intent, 1);
                        overridePendingTransition(0, 0);
                        return;
                    }
                },500);
            }
        });


        llrouteplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View_load_dialog.showDialog();

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent i = new Intent(context, Visit_list_pageActivity.class);
                        startActivityForResult(i, 1);
                        //finish();
                        return;
                    }
                },500);
            }
        });

        llorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View_load_dialog.showDialog();

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent i = new Intent(context, Order_list_pageActivity.class);
                        startActivityForResult(i, 1);
                        //finish();
                        return;
                    }
                },500);
            }
        });

        llaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View_load_dialog.showDialog();

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent i = new Intent(context, AccountActivity.class);
                        startActivityForResult(i, 1);
                        //finish();
                        return;
                    }
                },100);
            }
        });

        final CustomCalendarView calendarView = (CustomCalendarView) findViewById(R.id.calendar_view);
//Initialize calendar with date
        final Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());
//Show Monday as first date of week
        calendarView.setFirstDayOfWeek(Calendar.MONDAY);
//Show/hide overflow days of a month
//call refreshCalendar to update calendar the view
        calendarView.setShowOverflowDate(false);
        calendarView.setCustomTypeface(face1);
        calendarView.refreshCalendar(currentCalendar);
        int num_p=0, num_a=0, num_l=0;

        Intent i=getIntent();
        String attend_details=getAttendanceDetails(i.getStringExtra("sales_rep_id"));
        //Log.e("attending", attend_details);
        try {
            final JSONArray jsonArray=new JSONArray(attend_details);

            final JSONObject present=new JSONObject();
            final JSONObject absent=new JSONObject();
            final JSONObject late=new JSONObject();
            JSONObject attend=new JSONObject();


            String todays = new SimpleDateFormat("EEE MMM dd").format(Calendar.getInstance().getTime());            String today2 = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
            txt_date_current.setText(today2);
            txt_date_current.setTypeface(face);

            boolean  flag_today=false;

            if(jsonArray.length()>0){
                for(int h=0;h<jsonArray.length();h++){

                    JSONObject ob=jsonArray.getJSONObject(h);
                    String working_status=ob.getString("working_status");

                    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = dateFormatter.parse(ob.getString("check_in_time"));
                    Date checkout_date=null;
                    if(!ob.getString("check_out_time").equals("null")) {
                        checkout_date = dateFormatter.parse(ob.getString("check_out_time"));
                    }
                    String year=new SimpleDateFormat("yyyy-MM-dd 10:00:00").format(date);
                    Date date1=dateFormatter.parse(year);

                    String month_of_checkin=new SimpleDateFormat("MMM").format(date);
                    String month_of_current=new SimpleDateFormat("MMM").format(Calendar.getInstance().getTime());


                    if(new SimpleDateFormat("EEE MMM dd").format(date).equals(todays)){
                        txt_checkin_attend.setText(new SimpleDateFormat("hh:mm a").format(dateFormatter.parse(ob.getString("check_in_time"))));

                        String saddr="0,0", daddr="0,0";
                        if(ob.getString("latitude").equals("0") || ob.getString("latitude").equals("null")){
                            checkin_loc.setImageResource(R.drawable.ic_place_strike);
                        }else{
                            checkin_loc.setImageResource(R.drawable.option_old_visit);
                            daddr=ob.getString("latitude")+","+ob.getString("longitude");
                            final String finalSaddr = saddr;
                            final String finalDaddr = daddr;
                            checkin_loc.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    String uri = "http://maps.google.com/maps?q="+ finalDaddr;
                                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                                    startActivityForResult(intent,1);
                                }
                            });
                        }



                        flag_today=true;
                    }else{flag_today=false;}



                    if(working_status.toLowerCase().equals("present")){
                        if(date.after(date1)){
                            if(checkout_date!=null) {
                                JSONObject object=new JSONObject();
                                object.put("checkin_time", (new SimpleDateFormat("hh:mm a").format(date)));
                                object.put("checkout_time", new SimpleDateFormat("hh:mm a").format(checkout_date));
                                object.put("latitude", ob.getString("latitude"));
                                object.put("longitude", ob.getString("longitude"));
                                late.put(new SimpleDateFormat("EEE MMM dd").format(date), object.toString());
                                if(flag_today){
                                    txt_checkout_attend.setText(new SimpleDateFormat("hh:mm a").format(dateFormatter.parse(ob.getString("check_out_time"))));
                                }

                            }else{
                                JSONObject object=new JSONObject();
                                object.put("checkin_time", (new SimpleDateFormat("hh:mm a").format(date)));
                                object.put("checkout_time", "-");
                                object.put("latitude", ob.getString("latitude"));
                                object.put("longitude", ob.getString("longitude"));
                                late.put(new SimpleDateFormat("EEE MMM dd").format(date), object.toString());
                            }
                            if(month_of_checkin.equals(month_of_current)){
                                num_l++;
                            }

                        }else{
                            if(month_of_checkin.equals(month_of_current)){
                                num_p++;
                            }
                            if(checkout_date!=null){

                                if(flag_today){
                                    txt_checkout_attend.setText(new SimpleDateFormat("hh:mm a").format(dateFormatter.parse(ob.getString("check_out_time"))));
                                }
                                JSONObject object=new JSONObject();
                                object.put("checkin_time", (new SimpleDateFormat("hh:mm a").format(date)));
                                object.put("checkout_time", new SimpleDateFormat("hh:mm a").format(checkout_date));
                                object.put("latitude", ob.getString("latitude"));
                                object.put("longitude", ob.getString("longitude"));
                                present.put(new SimpleDateFormat("EEE MMM dd").format(date), object.toString());
                            }else{
                                JSONObject object=new JSONObject();
                                object.put("checkin_time", (new SimpleDateFormat("hh:mm a").format(date)));
                                object.put("checkout_time", "-");
                                object.put("latitude", ob.getString("latitude"));
                                object.put("longitude", ob.getString("longitude"));
                                present.put(new SimpleDateFormat("EEE MMM dd").format(date), object.toString());
                            }
                        }

                    }else if(working_status.toLowerCase().equals("absent")){
                        if(month_of_checkin.equals(month_of_current)){
                            num_a++;
                        }
                        JSONObject object=new JSONObject();
                        object.put("checkin_time", (new SimpleDateFormat("hh:mm a").format(date)));
                        object.put("checkout_time", "-");
                        object.put("latitude", ob.getString("latitude"));
                        object.put("longitude", ob.getString("longitude"));
                        absent.put(new SimpleDateFormat("EEE MMM dd").format(date), object.toString());
                    }
                }
                if(!flag_today){
                    checkin_loc.setVisibility(View.GONE);
                }

            }

            num_p=num_p+num_l;

            if (present.length()>0){
                attend.put("present", present.toString());
                no_present.setText(num_p+"");
            }else{
                attend.put("present", "0");
                no_present.setText(0+"");
            }
            if(absent.length()>0){
                attend.put("absent", absent.toString());
                no_absent.setText(num_a+"");
            }else{
                attend.put("absent", "0");
                no_absent.setText(0+"");
            }
            if(late.length()>0){
                attend.put("late", late.toString());
                no_late.setText(num_l+"");
            }else{
                attend.put("late", "0");
                no_late.setText(0+"");
            }


            List decorators = new ArrayList<>();
            decorators.add(new MySelectorDecorator(AttendanceActivity.this, attend.toString()));
            calendarView.setDecorators(decorators);

            calendarView.refreshCalendar(currentCalendar);

//Handling custom calendar events

        calendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(Date date) {
                try {
                    String daddr="0,0";
                    SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd");
                    //Log.e("present", present.toString());
                    Calendar calendar=Calendar.getInstance();
                    calendar.set(Integer.parseInt(new SimpleDateFormat("yyyy").format(date)), Integer.parseInt(new SimpleDateFormat("MM").format(date))-1,Integer.parseInt(new SimpleDateFormat("dd").format(date)) );
                    txt_date_current.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
                    txt_date_current.setTypeface(face);
                    checkin_loc.setVisibility(View.VISIBLE);
                    if(present.toString().contains(df.format(date))){
                        final JSONObject object=new JSONObject(present.getString(df.format(date)));
                        txt_checkin_attend.setText(object.getString("checkin_time"));
                        txt_checkout_attend.setText(object.getString("checkout_time"));
                        if(object.getString("latitude").equals("0") || object.getString("latitude").equals("null")){
                            checkin_loc.setImageResource(R.drawable.ic_place_strike);
                        }else{
                            checkin_loc.setImageResource(R.drawable.option_old_visit);
                            daddr=object.getString("latitude")+","+object.getString("longitude");
                            final String finalDaddr = daddr;
                            checkin_loc.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    String uri = "http://maps.google.com/maps?q="+ finalDaddr;
                                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                                    startActivityForResult(intent,1);
                                }
                            });
                        }
                        lbl_checkout_time.setVisibility(View.VISIBLE);
                        txt_checkout_attend.setVisibility(View.VISIBLE);
                        lbl_checkin_time.setText("Checkin Time");
                        //Toast.makeText(context, df.format(date), Toast.LENGTH_SHORT).show();
                    }
                    else if(absent.toString().contains(df.format(date))){
                        JSONObject jsonObject=new JSONObject(absent.getString(df.format(date)));
                        lbl_checkin_time.setText("ABSENT");
                        lbl_checkout_time.setVisibility(View.GONE);
                        txt_checkout_attend.setVisibility(View.GONE);
                        txt_checkin_attend.setText(absent.getString(jsonObject.getString("checkin_time")));
                        //Log.e("locd",jsonObject.getString("latitude").equals("0")+" "+jsonObject.getString("latitude").equals("null") );
                        if(jsonObject.getString("latitude").equals("0") || jsonObject.getString("latitude").equals("null")){
                            checkin_loc.setImageResource(R.drawable.ic_place_strike);
                        }else{
                            daddr=jsonObject.getString("latitude")+","+jsonObject.getString("longitude");

                            checkin_loc.setImageResource(R.drawable.option_old_visit);
                            final String finalDaddr1 = daddr;
                            checkin_loc.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String uri = "http://maps.google.com/maps?q="+ finalDaddr1;
                                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                                    startActivityForResult(intent,1);
                                }
                            });
                        }
                        //Toast.makeText(context, df.format(date), Toast.LENGTH_SHORT).show();
                    }
                    else if(late.toString().contains(df.format(date))){
                        JSONObject object=new JSONObject(late.getString(df.format(date)));
                        txt_checkin_attend.setText(object.getString("checkin_time"));
                        txt_checkout_attend.setText(object.getString("checkout_time"));
                        //Log.e("locd",object.getString("latitude").equals("0")+" "+object.getString("latitude").equals("null") );
                        if(object.getString("latitude").equals("0") || object.getString("latitude").equals("null")){
                            checkin_loc.setImageResource(R.drawable.ic_place_strike);
                        }else{
                            daddr=object.getString("latitude")+","+object.getString("longitude");
                            checkin_loc.setImageResource(R.drawable.option_old_visit);
                            final String finalDaddr2 = daddr;
                            checkin_loc.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String uri = "http://maps.google.com/maps?q="+ finalDaddr2;
                                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                                    startActivityForResult(intent,1);
                                }
                            });
                        }
                        lbl_checkout_time.setVisibility(View.VISIBLE);
                        txt_checkout_attend.setVisibility(View.VISIBLE);
                        lbl_checkin_time.setText("Checkin Time");
                        //Toast.makeText(context, df.format(date), Toast.LENGTH_SHORT).show();
                    }else{
                        checkin_loc.setVisibility(View.GONE);
                        txt_checkin_attend.setText("-");
                        lbl_checkout_time.setVisibility(View.VISIBLE);
                        txt_checkout_attend.setVisibility(View.VISIBLE);
                        lbl_checkin_time.setText("Checkin Time");
                    }
                    calendarView.refreshCalendar(calendar);
                }catch (Exception e){
                    Log.e("ondate", e.toString());
                 e.printStackTrace();
                }
                //SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                //Toast.makeText(context, df.format(date), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onMonthChanged(Date date) {

                try{
                    int count_p=0, count_a=0, count_l=0;
                    String month_of_current=new SimpleDateFormat("MMM").format(date);

                    Iterator<String> keys = present.keys();

                    while(keys.hasNext()) {
                        String key = keys.next();
                        String month_of_checkin=key.substring(4, 7);
                        if(month_of_checkin.equals(month_of_current)){count_p++;}
                    }

                    keys=late.keys();
                    while(keys.hasNext()){
                        String key=keys.next();
                        String month_of_checkin=key.substring(4, 7);
                        if(month_of_checkin.equals(month_of_current)){count_l++;}
                    }

                    keys=absent.keys();
                    while(keys.hasNext()){
                        String key=keys.next();
                        String month_of_checkin=key.substring(4, 7);
                        if(month_of_checkin.equals(month_of_current)){count_a++;}
                    }

                    //Log.e("atria", "Absent: "+absent.toString()+" "+present.toString());

                    no_present.setText(count_p+count_l+"");
                    no_absent.setText(count_a+"");
                    no_late.setText(count_l+"");

                }catch(Exception e){
                    Log.e("attend", e.toString());
                }                //Toast.makeText(context, df.format(date), Toast.LENGTH_SHORT).show();
            }
        });

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("errorinat", e.toString());
        }

    }



    private void showMenuOption() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View menuOption = inflater.inflate(R.layout.menu_bottom, null);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) menuOption);
        GridView simplegridview=menuOption.findViewById(R.id.simpleGridView);
        menuOption.setFocusableInTouchMode(true);
        CustomGridViewAdapter customAdapter = new CustomGridViewAdapter(getApplicationContext(), logos);
        simplegridview.setAdapter(customAdapter);
        ImageView top_image=menuOption.findViewById(R.id.top_image);
        ImageView bottom_imageview=menuOption.findViewById(R.id.bottom_image);


        View.OnClickListener thisListener1 = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                container_attend.removeView(menuOption);
                llhome.setEnabled(true);
                llrouteplan.setEnabled(true);
                llorder.setEnabled(true);
                llaccount.setEnabled(true);
                flagpressed=false;
                fab_btn.setRotation(0);
            }
        };
        top_image.setOnClickListener(thisListener1);
        bottom_imageview.setOnClickListener(thisListener1);
        // implement setOnItemClickListener event on GridView
        simplegridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        new Handler().postDelayed(new Runnable() {
                            public void run() {

                                showcheckoutpopup();
                            }
                        },1000);
                        break;
                    case 1:
                        View_load_dialog.showDialog();

                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                Intent myIntent = new Intent(context, Visit_detailsActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("fabtype", "New");
                                myIntent.putExtras(bundle);
                                container_attend.removeView(menuOption);
                                flagpressed=false;
                                startActivityForResult(myIntent, 1);
                                return;
                            }
                        }, 500);
                        break;
                    case 2:
                        View_load_dialog.showDialog();

                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                Intent myIntent2 = new Intent(context, Visit_detailsActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("fabtype", "Old");
                                myIntent2.putExtras(bundle);
                                container_attend.removeView(menuOption);
                                flagpressed=false;
                                startActivityForResult(myIntent2, 1);

                                return;
                            }
                        },500);
                        break;
                    case 3:
                        if(OrdersData.reporting_manager.equals("no")){
                            View_load_dialog.showDialog();
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    Intent i=new Intent(context, TimelineActivity.class);
                                    i.putExtra("sales_rep_id", session.getsales_rep_id());
                                    container_attend.removeView(menuOption);
                                    flagpressed=false;
                                    startActivityForResult(i, 1);
                                }
                            },500);
                        }else{
                            View_load_dialog.showDialog();
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    Intent i=new Intent(context, ReportingManager.class);
                                    i.putExtra("source", "activity");
                                    container_attend.removeView(menuOption);
                                    flagpressed=false;
                                    startActivityForResult(i, 1);
                                }
                            },500);
                        }

                        break;
                }
            }
        });
        menuOption.setFocusable(true);

        container_attend.addView(menuOption, container_attend.getChildCount()-1);


    }

    private void showcheckoutpopup(){

        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.alertdialog_checkout, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).setIcon(R.drawable.shape).create();
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) deleteDialogView);
        deleteDialog.setView(deleteDialogView);
        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    showcheckoutpopup1();
                    deleteDialog.dismiss();
                }
                catch (Exception ex) {

                }
            }
        });
        deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
            }
        });

        deleteDialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(deleteDialog.getWindow().getAttributes());

        int dialogWindowWidth = (int) (displayWidth * 0.7f);
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        deleteDialog.getWindow().setAttributes(layoutParams);
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void showcheckoutpopup1() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.alertdialog_again_checkout, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).setIcon(R.drawable.shape).create();
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) deleteDialogView);
        deleteDialog.setView(deleteDialogView);
        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    deleteDialogView.findViewById(R.id.btn_yes).setEnabled(false);
                    deleteDialog.dismiss();
                    View_load_dialog.showDialog();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                if(session.getcheck_out_time().equals("")||session.getcheck_out_time().equals(null)||session.getcheck_out_time().equals("null")){
                                    Calendar calendar0 = Calendar.getInstance();
                                    Date date0 = calendar0.getTime();
                                    String t = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date0.getTime());

                                    JSONObject jsonObject=new JSONObject();
                                    final String[] lat = {"0.0"};
                                    final String[] longi = {"0.0"};
                                    client = LocationServices.getFusedLocationProviderClient(context);
                                    if (ActivityCompat.checkSelfPermission(AttendanceActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AttendanceActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                        // TODO: Consider calling
                                        //    Activity#requestPermissions
                                        // here to request the missing permissions, and then overriding
                                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                        //                                          int[] grantResults)
                                        // to handle the case where the user grants the permission. See the documentation
                                        // for Activity#requestPermissions for more details.
                                        return;
                                    }
                                    client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                                        @Override
                                        public void onSuccess(Location location) {
                                            if(location!=null){
                                                lat[0] =location.getLatitude()+"";
                                                longi[0] =location.getLongitude()+"";
                                            }
                                        }
                                    });
                                    jsonObject.put("latitude", lat[0]);
                                    jsonObject.put("longitude", longi[0]);

                                    OfflineDbHelper offlineDbHelper=new OfflineDbHelper(context);
                                    SQLiteDatabase db=offlineDbHelper.getWritableDatabase();
                                    offlineDbHelper.addVISITDATA(t, OrdersData.actual_count, OrdersData.unplanned_count, OrdersData.total_count, OrdersData.p_call, db);
                                    final String date = new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());
                                    offlineDbHelper.addEVENT("Checkout", date, jsonObject.toString(), db);
                                    //View_load_dialog.showDialog();

                                    postActvityDetails("Checkout",date, jsonObject.toString());
                                }
                                String response=GetText();
                                session.setcheck_out_time(response);

                                Calendar calendar0 = Calendar.getInstance();
                                Date date0 = calendar0.getTime();
                                String t = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date0.getTime());

                                OfflineDbHelper offlineDbHelper=new OfflineDbHelper(context);
                                SQLiteDatabase db=offlineDbHelper.getWritableDatabase();
                                offlineDbHelper.addVISITDATA(t, OrdersData.actual_count, OrdersData.unplanned_count, OrdersData.total_count, OrdersData.p_call, db);

                            }catch (Exception e){
                                Log.e("attend checkout",e.toString());
                            }
                            View_load_dialog.hideDialog();
                            showPopUp6();
                        }
                    }, 150);
                }
                catch (Exception ex) {

                }
            }
        });
        deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
            }
        });

        deleteDialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(deleteDialog.getWindow().getAttributes());

        int dialogWindowWidth = (int) (displayWidth * 0.7f);
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        deleteDialog.getWindow().setAttributes(layoutParams);
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void postActvityDetails(String event_name, String event_time, String event_json){
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        //String sales_rep_id=session.getsales_rep_id();

        String data=null;

        try{


            data = URLEncoder.encode("event_name", "UTF-8")
                    + "=" + URLEncoder.encode(event_name, "UTF-8");
            data += "&" + URLEncoder.encode("event_time", "UTF-8")
                    + "=" + URLEncoder.encode(event_time, "UTF-8");
            data += "&" + URLEncoder.encode("event_json", "UTF-8")
                    + "=" + URLEncoder.encode(event_json, "UTF-8");
            data += "&" + URLEncoder.encode("sales_rep_id", "UTF-8")
                    + "=" + URLEncoder.encode(session.getsales_rep_id(), "UTF-8");



        }catch(Exception e){
            e.printStackTrace();
        }



        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/dashboard_mobile_app/set_notifications_api");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
        }
        catch(Exception ex)
        {
            Log.e("account", "postactivitydetails: "+ex.toString());
            //Toast.makeText(context,"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        }
        finally {
            try {

                reader.close();
            } catch (Exception ex) {
                Log.e("account", "postactivitydetails2: "+ex.toString());
            }
        }
    }

    private void showPopUp6() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.alertdialog_share_whatsapp, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).setIcon(R.drawable.shape).create();
        TextView txt_share=deleteDialogView.findViewById(R.id.txt_share);
        txt_share.setText("Do you want to share EOD details on whatsapp?");
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) deleteDialogView);
        deleteDialog.setView(deleteDialogView);
        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!isAccessibilityOn (context, WhatsappAccessibilityService.class)) {
                        Intent intent = new Intent (Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        context.startActivity (intent);
                        restartflag2=true;
                    }else {
                        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                        whatsappIntent.setType("text/plain");
                        whatsappIntent.setPackage("com.whatsapp");
                        whatsappIntent.putExtra(Intent.EXTRA_TEXT, returnWhatsappText()+"\n"+getResources().getString(R.string.whatsapp_suffix));
                        try {
                            startActivity(whatsappIntent);
                            restartflag=true;
                            deleteDialog.dismiss();
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(context, "Please install whatsapp to share details on whatsapp", Toast.LENGTH_LONG).show();
                            deleteDialog.dismiss();
                        }
                    }
                }
                catch (Exception ex) {
                    Log.e("visit", "error12 "+ex.toString());
                }
                deleteDialog.dismiss();
            }
        });
        deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View_load_dialog.showDialog();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent(context, dashboardActivity.class);
                        startActivityForResult(intent, 1);
                    }
                }, 1000);
                deleteDialog.dismiss();
            }
        });

        deleteDialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(deleteDialog.getWindow().getAttributes());

        int dialogWindowWidth = (int) (displayWidth * 0.7f);
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        deleteDialog.getWindow().setAttributes(layoutParams);
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  }

    private String returnWhatsappText() {
        String result="";
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        result+="Name: "+session.getfirst_name()+" "+session.getlast_name()+"\n";
        result+="Date Of Reporting: "+date+"\n";
        result+="Area: "+session.getbeat_name().substring(8)+"\n";
        result+="Dist Name: "+session.getdistributor_name()+"\n";
        String actual_count=OrdersData.actual_count;
        String unplanned_count=OrdersData.unplanned_count;
        String total_calls=OrdersData.total_count;
        if(actual_count.matches("[0-9]+")&&total_calls.matches("[0-9]+")&&unplanned_count.matches("[0-9]+")) {
            int actcnt = Integer.parseInt(actual_count.trim()) + Integer.parseInt(unplanned_count.trim());
            actual_count = "" + actcnt;
            result += "Calls as per beat: " + actual_count + "\n";
            result += "New Calls: " + OrdersData.unplanned_count + "\n";
            result += "Productive calls: " + OrdersData.p_call + "\n";
            result += "Stores not visited: " + (Integer.parseInt(total_calls) - Integer.parseInt(actual_count)) + "\n";
        }
        result+="Bars sold: "+OrdersData.order_bars+"\n";
        result+="Cookies sold: "+OrdersData.order_cookies+"\n";
        result+="Trailmix sold: "+OrdersData.order_trailmix+"\n";

        return result;
    }

    private boolean isAccessibilityOn (Context context, Class<? extends AccessibilityService> clazz) {
        int accessibilityEnabled = 0;
        final String service = context.getPackageName () + "/" + clazz.getCanonicalName ();
        try {
            accessibilityEnabled = Settings.Secure.getInt (context.getApplicationContext ().getContentResolver (), Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException ignored) {  }

        TextUtils.SimpleStringSplitter colonSplitter = new TextUtils.SimpleStringSplitter (':');

        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString (context.getApplicationContext ().getContentResolver (), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                colonSplitter.setString (settingValue);
                while (colonSplitter.hasNext ()) {
                    String accessibilityService = colonSplitter.next ();

                    if (accessibilityService.equalsIgnoreCase (service)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            //spinnerDialog.dismiss();
            try{
                View_load_dialog.hideDialog();}catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public String  GetText()  throws UnsupportedEncodingException
    {
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        String sales_rep_id=session.getsales_rep_id();
        String data = URLEncoder.encode("sales_rep_id", "UTF-8")
                + "=" + URLEncoder.encode(sales_rep_id, "UTF-8");
        data += "&" + URLEncoder.encode("actual_count", "UTF-8")
                + "=" + URLEncoder.encode(OrdersData.actual_count, "UTF-8");
        data += "&" + URLEncoder.encode("unplanned_count", "UTF-8")
                + "=" + URLEncoder.encode(OrdersData.unplanned_count, "UTF-8");
        data += "&" + URLEncoder.encode("total_count", "UTF-8")
                + "=" + URLEncoder.encode(OrdersData.total_count, "UTF-8");
        data += "&" + URLEncoder.encode("p_call", "UTF-8")
                + "=" + URLEncoder.encode(OrdersData.p_call, "UTF-8");
        data += "&" + URLEncoder.encode("cookies", "UTF-8")
                + "=" + URLEncoder.encode(OrdersData.order_cookies, "UTF-8");
        data += "&" + URLEncoder.encode("bars", "UTF-8")
                + "=" + URLEncoder.encode(OrdersData.order_bars, "UTF-8");
        data += "&" + URLEncoder.encode("trailmix", "UTF-8")
                + "=" + URLEncoder.encode(OrdersData.order_trailmix, "UTF-8");


        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/Sales_Attendence/checkout_api");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();

            return text;
        }
        catch(Exception ex)
        {
            //Toast.makeText(context,"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        }
        finally
        {
            try
            {

                reader.close();
            }

            catch(Exception ex) {}
        }

        // Show response on activity
        return "";

    }
    @Override
    protected void onRestart(){
        super.onRestart();
        if(restartflag){
            View_load_dialog.showDialog();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i=new Intent(context, dashboardActivity.class);
                    startActivityForResult(i, 1);
                }
            }, 1000);
        }else if(restartflag2){
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, returnWhatsappText()+"\n\n"+getResources().getString(R.string.whatsapp_suffix));
            try {
                startActivity(whatsappIntent);
                Log.e("visit", "service started");
                restartflag = true;
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(context, "Please install whatsapp to share details on whatsapp", Toast.LENGTH_LONG).show();
                Log.e("visit", "error21 "+ex.toString());

            }
        }else {}
    }

    public String getAttendanceDetails(String sales_rep_id){
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        //String sales_rep_id=session.getsales_rep_id();
       // Log.e("saelse", sales_rep_id);

        String data=null;

        try{

            data = URLEncoder.encode("sales_rep_id", "UTF-8")
                    + "=" + URLEncoder.encode(sales_rep_id, "UTF-8");


        }catch(Exception e){
            e.printStackTrace();
        }



        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/dashboard_mobile_app/get_sales_rep_attendance_api");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
            return text;
        }
        catch(Exception ex)
        {
            Log.e("time", "postactivitydetails: "+ex.toString());
            //Toast.makeText(context,"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        }
        finally {
            try {

                reader.close();
            } catch (Exception ex) {
                Log.e("time", "postactivitydetails2: "+ex.toString());
            }
        }
        return "";
    }

}
























































