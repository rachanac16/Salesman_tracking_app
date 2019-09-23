package com.eat.erp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.TimeKeyListener;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;

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
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimelineActivity extends AppCompatActivity {

    Thread thread;
    String checkresponse="";
    LinearLayout llcontent;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    session session;
    Context context;
    MenuItem nav_checkintime;
    MenuItem nav_checkouttime;
    Typeface face;
    ProgressDialog spinnerDialog;
    public static Activity fa;
    LinearLayout llhome, llrouteplan, llorder, llaccount;
    TextView txttitle;
    View_load_dialog View_load_dialog;
    boolean restartflag, restartflag2, dbflag;
    int logos[] = {R.drawable.option_checkout, R.drawable.option_new_visit, R.drawable.option_old_visit, R.drawable.option_timeline};
    Button fab_btn;
    RelativeLayout container_timeline;
    boolean flagpressed;
    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        restartflag=false;
        restartflag2=false;
        checkresponse="";
        llcontent=findViewById(R.id.llcontent);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        face = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Demi.otf");
        context = TimelineActivity.this;
        txttitle = (TextView) findViewById(R.id.txttitle);
        txttitle.setText("Activity Timeline");
        session = new session(context);
        dbflag=false;
        fa = this;

        final TextView todaydate = (TextView) findViewById(R.id.todaydate);
        final Calendar c = Calendar.getInstance();
        String dayNumberSuffix = dashboardActivity.getDayNumberSuffix(c.get(Calendar.DAY_OF_MONTH));
        Date date3 = Calendar.getInstance().getTime();
        SimpleDateFormat timeFormatter3 = new SimpleDateFormat("dd'" + dayNumberSuffix + "' MMM yyyy");
        String time3 = timeFormatter3.format(date3);
        todaydate.setText(time3 + ", Today");

        llhome = (LinearLayout) findViewById(R.id.llhome);
        llrouteplan = (LinearLayout) findViewById(R.id.llrouteplan);
        llorder = (LinearLayout) findViewById(R.id.llorder);
        llaccount = (LinearLayout) findViewById(R.id.llaccount);

        View_load_dialog = new View_load_dialog(this);

        fab_btn=findViewById(R.id.fab_btn);
        container_timeline=findViewById(R.id.container_timeline);

        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flagpressed){
                    flagpressed=false;
                    container_timeline.removeViewAt(container_timeline.getChildCount()-2);
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
                        //finish();
                        return;
                    }
                }, 500);
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
                }, 500);
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
                }, 500);
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
                }, 100);
            }
        });

         thread = new Thread() {
            @Override
            public void run() {
                try {
                    while(true) {
                        sleep(5000);
                        Intent i=getIntent();
                        String response=getActivityDetails(i.getStringExtra("sales_rep_id"));
                        if(!checkresponse.equals(response)){
                            try {
                                JSONArray jsonArray=new JSONArray(response);
                                for(int k=0; k<jsonArray.length(); k++) {
                                    JSONObject ob = jsonArray.getJSONObject(k);
                                    String event_name = ob.getString("event_name");
                                    String event_date = ob.getString("event_date");
                                    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date date = dateFormatter.parse(event_date);

                                    SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a");
                                    String event_time = timeFormatter.format(date);
                                    String event_json = ob.getString("event_json");
                                    //Log.e("timeline object details", event_name + " " + event_time + " " + event_json);
                                    add_events_in_timeline(event_name, event_time, event_json);
                                }

                            }catch (Exception e){
                                Log.e("async_db", "inside handler: "+e.toString());
                            }
                        }else{Log.e("async", "equal");}
                    }
                } catch (Exception e) {
                    Log.e("handler thread", e.toString());
                }
            }
        };

        final boolean[] attendance_today = {false}, any_event={false};
        final String date_today=new SimpleDateFormat("EEE MMM dd").format(Calendar.getInstance().getTime());

        try{
        OfflineDbHelper offlineDbHelper =new OfflineDbHelper(context);
        SQLiteDatabase db=offlineDbHelper.getReadableDatabase();
        Cursor cursor=offlineDbHelper.readEVENT(db);
        final Intent i=getIntent();
        if(i.getStringExtra("sales_rep_id").equals(session.getsales_rep_id())){
            if(!(cursor.getCount()<=0)){
                while (cursor.moveToNext()){

                    String event_no=cursor.getString(cursor.getColumnIndex(OfflineDatabase.Events.EVENT_NO));
                    String event_name=cursor.getString(cursor.getColumnIndex(OfflineDatabase.Events.EVENT_NAME));
                    String event_time=cursor.getString(cursor.getColumnIndex(OfflineDatabase.Events.EVENT_TIME));
                    String event_json=cursor.getString(cursor.getColumnIndex(OfflineDatabase.Events.EVENT_JSON));
                    add_events_in_timeline(event_name, event_time, event_json);
                }

                if(!dbflag){
                    OfflineDbHelper of=new OfflineDbHelper(context);
                    SQLiteDatabase dbd=of.getWritableDatabase();
                    dbd.delete(OfflineDatabase.Events.TABLE_NAME, null, null);
                    llcontent.removeAllViews();
                    View_load_dialog.showDialog();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String response= getActivityDetails(session.getsales_rep_id());
                                JSONArray jsonArray=new JSONArray(response);
                                if(jsonArray.length()>0){
                                    for(int i=0; i<jsonArray.length(); i++) {
                                        JSONObject ob = jsonArray.getJSONObject(i);

                                        String event_name = ob.getString("event_name");
                                        String event_date = ob.getString("event_date");
                                        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        Date date = dateFormatter.parse(event_date);

                                        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a");
                                        String event_time = timeFormatter.format(date);
                                        String event_json = ob.getString("event_json");
                                        //Log.e("timeline object details", event_name + " " + event_time + " " + event_json);
                                        OfflineDbHelper offlineDbHelper1 = new OfflineDbHelper(context);
                                        SQLiteDatabase db1 = offlineDbHelper1.getWritableDatabase();
                                        offlineDbHelper1.addEVENT(event_name, event_time, event_json, db1);
                                        if(date.toString().contains(date_today) && event_name.equals("Attendance")){
                                            attendance_today[0]=true;
                                        }else if(date.toString().contains(date_today) && !event_name.equals("Attendance") && !attendance_today[0]){
                                            JSONObject js=new JSONObject();
                                            js.put("is_present", "yes");
                                            js.put("date", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
                                            String attend_details=getAttendanceDetails(session.getsales_rep_id());
                                            String event_time1="-";
                                            if(attend_details.contains(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()))){
                                                JSONArray jsonArray1=new JSONArray(attend_details);
                                                if(jsonArray1.length()>0){
                                                    for(int h=0;h<jsonArray1.length();h++){
                                                        JSONObject ob1=jsonArray1.getJSONObject(h);
                                                        //Log.e("check", "check: "+ob1.getString("check_in_time")+" "+new SimpleDateFormat("yyyy-MM-dd").format(date));
                                                        if(ob1.getString("check_in_time").contains(new SimpleDateFormat("yyyy-MM-dd").format(date))){
                                                            SimpleDateFormat dateFormatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                            Date date1 = dateFormatter1.parse(ob1.getString("check_in_time"));
                                                            event_time1=new SimpleDateFormat("hh:mm a").format(date1);
                                                            js.put("latitude", ob1.getString("latitude"));
                                                            js.put("longitude", ob1.getString("longitude"));
                                                        }
                                                    }
                                                }
                                            }
                                            add_events_in_timeline("Attendance", event_time1, js.toString());
                                            attendance_today[0]=true;
                                        }
                                        add_events_in_timeline(event_name, event_time, event_json);
                                    }

                                }

                            }catch (Exception e){
                                Log.e("timeline_db", "inside handler1: "+e.toString());
                            }


                            View_load_dialog.hideDialog();
                        }
                    }, 200);

                }
            }else{
                View_load_dialog.showDialog();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String response= getActivityDetails(session.getsales_rep_id());
                            JSONArray jsonArray=new JSONArray(response);
                            if(jsonArray.length()>0){
                                for(int i=0; i<jsonArray.length(); i++) {
                                    JSONObject ob = jsonArray.getJSONObject(i);
                                    String event_name = ob.getString("event_name");
                                    String event_date = ob.getString("event_date");
                                    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date date = dateFormatter.parse(event_date);

                                    SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a");
                                    String event_time = timeFormatter.format(date);
                                    String event_json = ob.getString("event_json");
                                    //Log.e("timeline object details", event_name + " " + event_time + " " + event_json);
                                    OfflineDbHelper offlineDbHelper1 = new OfflineDbHelper(context);
                                    SQLiteDatabase db1 = offlineDbHelper1.getWritableDatabase();
                                    offlineDbHelper1.addEVENT(event_name, event_time, event_json, db1);
                                    if(date.toString().contains(date_today) && event_name.equals("Attendance")){
                                        attendance_today[0]=true;
                                    }else if(date.toString().contains(date_today) && !event_name.equals("Attendance") && !attendance_today[0]){
                                        JSONObject js=new JSONObject();
                                        js.put("is_present", "yes");
                                        js.put("date", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
                                        String attend_details=getAttendanceDetails(session.getsales_rep_id());
                                        String event_time1="-";
                                        if(attend_details.contains(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()))){
                                            JSONArray jsonArray1=new JSONArray(attend_details);
                                            if(jsonArray1.length()>0){
                                                for(int h=0;h<jsonArray1.length();h++){
                                                    JSONObject ob1=jsonArray1.getJSONObject(h);
                                                    //Log.e("check", "check: "+ob1.getString("check_in_time")+" "+new SimpleDateFormat("yyyy-MM-dd").format(date));
                                                    if(ob1.getString("check_in_time").contains(new SimpleDateFormat("yyyy-MM-dd").format(date))){
                                                        SimpleDateFormat dateFormatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                        Date date1 = dateFormatter1.parse(ob1.getString("check_in_time"));
                                                        event_time1=new SimpleDateFormat("hh:mm a").format(date1);
                                                        js.put("latitude", ob1.getString("latitude"));
                                                        js.put("longitude", ob1.getString("longitude"));
                                                    }
                                                }
                                            }
                                        }
                                        add_events_in_timeline("Attendance", event_time1, js.toString());
                                        attendance_today[0]=true;
                                    }
                                    add_events_in_timeline(event_name, event_time, event_json);
                                }

                            }

                        }catch (Exception e){
                            Log.e("timeline_db", "inside handler2: "+e.toString());
                        }


                        View_load_dialog.hideDialog();
                    }
                }, 200);
            }
        } else{
            View_load_dialog.showDialog();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        thread.start();
                        String sales_id=i.getStringExtra("sales_rep_id");
                        String response= getActivityDetails(sales_id);
                        checkresponse=response;
                        JSONArray jsonArray=new JSONArray(response);
                        for(int i=0; i<jsonArray.length(); i++) {
                            JSONObject ob = jsonArray.getJSONObject(i);
                            String event_name = ob.getString("event_name");
                            String event_date = ob.getString("event_date");
                            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date date = dateFormatter.parse(event_date);
                            SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a");
                            String event_time = timeFormatter.format(date);
                            String event_json = ob.getString("event_json");
                            //Log.e("timeline object details", event_name + " " + event_time + " " + event_json);
                            if(date.toString().contains(date_today) && event_name.equals("Attendance")){
                                attendance_today[0]=true;
                            }else if(date.toString().contains(date_today) && !event_name.equals("Attendance") && !attendance_today[0]){
                                JSONObject js=new JSONObject();
                                js.put("is_present", "yes");
                                js.put("date", new SimpleDateFormat("EEE MMM dd").format(Calendar.getInstance().getTime()));
                                String attend_details=getAttendanceDetails(sales_id);
                                String event_time1="-";
                                if(attend_details.contains(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()))){
                                    JSONArray jsonArray1=new JSONArray(attend_details);
                                    if(jsonArray1.length()>0){
                                        for(int h=0;h<jsonArray1.length();h++){
                                            JSONObject ob1=jsonArray1.getJSONObject(h);
                                            //Log.e("check", "check: "+ob1.getString("check_in_time")+" "+new SimpleDateFormat("yyyy-MM-dd").format(date));
                                            if(ob1.getString("check_in_time").contains(new SimpleDateFormat("yyyy-MM-dd").format(date))){
                                                SimpleDateFormat dateFormatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                Date date1 = dateFormatter1.parse(ob1.getString("check_in_time"));
                                                event_time1=new SimpleDateFormat("hh:mm a").format(date1);
                                                js.put("latitude", ob1.getString("latitude"));
                                                js.put("longitude", ob1.getString("longitude"));
                                            }
                                        }
                                    }
                                }
                                add_events_in_timeline("Attendance", event_time1, js.toString());
                                attendance_today[0]=true;
                            }
                            add_events_in_timeline(event_name, event_time, event_json);
                        }

                    }catch (Exception e){
                        Log.e("timeline_db", "inside handler3: "+e.toString());
                    }


                    View_load_dialog.hideDialog();
                }
            }, 200);
        }


        }catch(Exception e){
            Log.e("timeline_db", e.toString());
        }
    }



    public String getActivityDetails(String sales_rep_id){
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        //String sales_rep_id=session.getsales_rep_id();

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
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/dashboard_mobile_app/get_notifications_api");

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


    public void add_events_in_timeline(String event_name, String event_time, String event_json) throws JSONException {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        try {
            if (event_name.equals("Attendance")) {
                dbflag = true;
                final View rowView1 = inflater.inflate(R.layout.view_date, null);
                FontChangeCrawler fontChanger1 = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Demi.otf");
                fontChanger1.replaceFonts((ViewGroup) rowView1);
                final JSONObject jsonObject = new JSONObject(event_json);
                TextView txt_date = rowView1.findViewById(R.id.date_txt);
                txt_date.setText(jsonObject.getString("date"));
                llcontent.addView(rowView1, 0);

                final View rowView = inflater.inflate(R.layout.view_attendance_activity, null);
                FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Demi.otf");
                fontChanger.replaceFonts((ViewGroup) rowView);
                TextView time_txt = rowView.findViewById(R.id.txt_time_attend);
                time_txt.setText(event_time);
                ImageView attend_location_mark=rowView.findViewById(R.id.attend_location_mark);
                TextView attend_name = rowView.findViewById(R.id.attend_name);
                TextView attend_desc = rowView.findViewById(R.id.attendance_description);
                ImageView imageView = rowView.findViewById(R.id.event_pic_attend);
                if (jsonObject.has("is_present")) {
                    if (jsonObject.getString("is_present").equals("no")) {
                        attend_name.setText("Absent Marked");
                        attend_desc.setText("Beat");
                        imageView.setImageResource(R.drawable.absent_marked);
                    }
                }
                if(jsonObject.has("latitude") && jsonObject.has("longitude")){
                    attend_location_mark.setVisibility(View.VISIBLE);
                    float lat=Float.parseFloat(jsonObject.getString("latitude"));
                    if(lat==0.0 || jsonObject.getString("latitude").equals("null")){
                        attend_location_mark.setImageResource(R.drawable.ic_place_strike);
                    }else{
                        attend_location_mark.setImageResource(R.drawable.option_old_visit);
                        final String finalDaddr =jsonObject.getString("latitude") + "," + jsonObject.getString("longitude");
                        attend_location_mark.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String uri = "http://maps.google.com/maps?q="+ finalDaddr;
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                startActivityForResult(intent, 1);

                            }
                        });
                    }
                }else{
                    attend_location_mark.setVisibility(View.GONE);
                }
                llcontent.addView(rowView, 1);

            }
            else if (event_name.equals("Working again")) {
                final View rowView = inflater.inflate(R.layout.view_timeline, null);
                FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Demi.otf");
                fontChanger.replaceFonts((ViewGroup) rowView);
                TextView time_txt = rowView.findViewById(R.id.txt_time);
                TextView txt_event_name = rowView.findViewById(R.id.event_name);
                TextView txt_event_desc = rowView.findViewById(R.id.event_description);
                ImageView imageView = rowView.findViewById(R.id.event_pic);
                ImageView location_mark=rowView.findViewById(R.id.location_mark);
                time_txt.setText(event_time);
                txt_event_name.setText("Working Again");
                txt_event_desc.setText("Beat");
                imageView.setImageResource(R.drawable.attendance);
                if(!event_json.equals("")) {
                    JSONObject jsonObject = new JSONObject(event_json);

                    if (jsonObject.has("latitude") && jsonObject.has("longitude")) {
                        location_mark.setVisibility(View.VISIBLE);
                        float lat=Float.parseFloat(jsonObject.getString("latitude"));
                        if (lat==0.0 || jsonObject.getString("latitude").equals("null")) {
                            location_mark.setImageResource(R.drawable.ic_place_strike);
                        } else {
                            location_mark.setImageResource(R.drawable.option_old_visit);
                            final String finalDaddr =jsonObject.getString("latitude") + "," + jsonObject.getString("longitude");
                            location_mark.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String uri = "http://maps.google.com/maps?q=" + finalDaddr;
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                    startActivity(intent);

                                }
                            });
                        }
                    } else {
                        location_mark.setVisibility(View.GONE);
                    }
                }else {
                    location_mark.setVisibility(View.GONE);
                }
                if(llcontent.getChildCount()>1){
                    llcontent.addView(rowView, 1);
                }

            }
            else if (event_name.equals("Followup")) {
                final View rowView = inflater.inflate(R.layout.view_timeline, null);
                FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Demi.otf");
                fontChanger.replaceFonts((ViewGroup) rowView);
                TextView time_txt = rowView.findViewById(R.id.txt_time);
                time_txt.setText(event_time);
                TextView txt_event_name = rowView.findViewById(R.id.event_name);
                TextView txt_event_desc = rowView.findViewById(R.id.event_description);
                ImageView location_mark=rowView.findViewById(R.id.location_mark);

                ImageView imageView = rowView.findViewById(R.id.event_pic);
                JSONObject jsonObject = new JSONObject(event_json);
                if (jsonObject.has("distributor_name")) {
                    txt_event_name.setText(jsonObject.getString("distributor_name") + ", " + jsonObject.getString("area"));
                }

                if(jsonObject.has("latitude") && jsonObject.has("longitude")){
                    location_mark.setVisibility(View.VISIBLE);
                    float lat=Float.parseFloat(jsonObject.getString("latitude"));
                    if(lat==0.0 || jsonObject.getString("latitude").equals("null")){
                        location_mark.setImageResource(R.drawable.ic_place_strike);
                    }else{
                        location_mark.setImageResource(R.drawable.option_old_visit);
                        final String finalDaddr =jsonObject.getString("latitude") + "," + jsonObject.getString("longitude");
                        location_mark.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String uri = "http://maps.google.com/maps?q="+ finalDaddr;
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                startActivity(intent);

                            }
                        });
                    }
                }else{
                    location_mark.setVisibility(View.GONE);
                }




                txt_event_desc.setText("Date of Followup: " + jsonObject.getString("followup_date"));
                imageView.setImageResource(R.drawable.followup);
                if(llcontent.getChildCount()>1){
                    llcontent.addView(rowView, 1);
                }
            }
            else if (event_name.equals("Store visit")) {
                final View rowView = inflater.inflate(R.layout.view_timeline, null);
                FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Demi.otf");
                fontChanger.replaceFonts((ViewGroup) rowView);
                TextView time_txt = rowView.findViewById(R.id.txt_time);
                ImageView location_mark=rowView.findViewById(R.id.location_mark);

                TextView txt_event_name = rowView.findViewById(R.id.event_name);
                TextView txt_event_desc = rowView.findViewById(R.id.event_description);
                ImageView imageView = rowView.findViewById(R.id.event_pic);
                JSONObject jsonObject = new JSONObject(event_json);
                if (jsonObject.has("distributor_name")) {
                    txt_event_name.setText(jsonObject.getString("distributor_name") + ", " + jsonObject.getString("area"));
                }
                if (jsonObject.getString("type").equals("add")) {
                    txt_event_name.setTextColor(getResources().getColor(R.color.title));
                    if (jsonObject.getString("new_or_old").equals("old")) {
                        if (jsonObject.has("zero_inventory")) {
                            if (jsonObject.getString("zero_inventory").equals("true")) {
                                txt_event_desc.setText("No Stock");
                                txt_event_desc.setTextColor(getResources().getColor(R.color.brightred));
                            } else {
                                txt_event_desc.setText("Inventory Added");
                            }
                        } else {
                            txt_event_desc.setText("Inventory Added");
                        }
                    } else {
                        if (jsonObject.getString("new_or_old").equals("new_new_gt")) {
                            if (jsonObject.getString("is_permanent").equals("Yes")) {
                                txt_event_desc.setText(" New Retailer added(permanent) ");
                            } else {
                                txt_event_desc.setText(" New Retailer added ");
                            }
                            txt_event_desc.setTextColor(getResources().getColor(R.color.white));
                            txt_event_desc.setBackgroundColor(getResources().getColor(R.color.brightred));
                        } else {
                            String first = "";
                            if (jsonObject.getString("new_or_old").equals("new_old_gt")) {
                                first = " Unscheduled GT ";
                            } else {
                                first = " Unscheduled MT ";
                            }

                            String next = "";
                            txt_event_desc.setText(first + next, TextView.BufferType.SPANNABLE);
                            Spannable s = (Spannable) txt_event_desc.getText();
                            int start = 0;
                            int end = first.length();
                            s.setSpan(new ForegroundColorSpan(0xFFFFFFFF), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            s.setSpan(new BackgroundColorSpan(0xFFFF0000), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            if (jsonObject.has("zero_inventory")) {
                                if (jsonObject.getString("zero_inventory").equals("true")) {
                                    next = "Inventory Empty";
                                    s.setSpan(new ForegroundColorSpan(0xFFFF0000), next.length(), first.length() + next.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                } else {
                                    next = "\n\nInventory added";
                                }
                            } else {
                                next = "\n\nInventory added";
                            }

                        }
                        txt_event_name.setTextColor(getResources().getColor(R.color.black));
                        imageView.setImageResource(R.drawable.new_retailer);
                    }
                } else {

                    txt_event_name.setTextColor(getResources().getColor(R.color.black));
                    txt_event_desc.setText("Inventory Updated");
                    imageView.setImageResource(R.drawable.visit_edit);
                }
                time_txt.setText(event_time);

                if(jsonObject.has("latitude") && jsonObject.has("longitude")){
                    location_mark.setVisibility(View.VISIBLE);
                    float lat=Float.parseFloat(jsonObject.getString("latitude"));
                    if(lat==0.0|| jsonObject.getString("latitude").equals("null")){
                        location_mark.setImageResource(R.drawable.ic_place_strike);
                    }else{
                        location_mark.setImageResource(R.drawable.option_old_visit);
                        final String finalDaddr =jsonObject.getString("latitude") + "," + jsonObject.getString("longitude");
                        location_mark.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String uri = "http://maps.google.com/maps?q="+ finalDaddr;
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                startActivity(intent);

                            }
                        });
                    }
                }else{
                    location_mark.setVisibility(View.GONE);
                }




                if(llcontent.getChildCount()>1) {
                    //Toast.makeText(context, "childe: "+llcontent.getChildCount() , Toast.LENGTH_SHORT).show();
                    llcontent.addView(rowView, 1);
                }
                }
            else if (event_name.equals("Order placed")) {
                final View rowView = inflater.inflate(R.layout.view_timeline, null);
                FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Demi.otf");
                fontChanger.replaceFonts((ViewGroup) rowView);
                TextView time_txt = rowView.findViewById(R.id.txt_time);
                TextView txt_event_name = rowView.findViewById(R.id.event_name);
                TextView txt_event_desc = rowView.findViewById(R.id.event_description);
                ImageView location_mark=rowView.findViewById(R.id.location_mark);

                ImageView imageView = rowView.findViewById(R.id.event_pic);
                time_txt.setText(event_time);
                JSONObject jsonObject = new JSONObject(event_json);
                imageView.setImageResource(R.drawable.order_placed);
                txt_event_name.setText(jsonObject.getString("distributor_name") + ", " + jsonObject.getString("area"));
                txt_event_name.setTextColor(getResources().getColor(R.color.green));
                String desc = "Order Placed\n";
                if (!jsonObject.getString("order_bar").equals("0")) {
                    desc += "Bars: " + jsonObject.getString("order_bar") + " ";
                }
                if (!jsonObject.getString("order_cookies").equals("0")) {
                    desc += "Cookies: " + jsonObject.getString("order_cookies") + " ";
                }
                if (!jsonObject.getString("order_trailmix").equals("0")) {
                    desc += "Trailmix: " + jsonObject.getString("order_trailmix") + " ";
                }
                txt_event_desc.setText(desc);

                if(jsonObject.has("latitude") && jsonObject.has("longitude")){
                    location_mark.setVisibility(View.VISIBLE);
                    float lat=Float.parseFloat(jsonObject.getString("latitude"));
                    if(lat==0.0|| jsonObject.getString("latitude").equals("null")){
                        location_mark.setImageResource(R.drawable.ic_place_strike);
                    }else{
                        location_mark.setImageResource(R.drawable.option_old_visit);
                        final String finalDaddr =jsonObject.getString("latitude") + "," + jsonObject.getString("longitude");
                        location_mark.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String uri = "http://maps.google.com/maps?q="+ finalDaddr;
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                startActivity(intent);

                            }
                        });
                    }
                }else{
                    location_mark.setVisibility(View.GONE);
                }




                if(llcontent.getChildCount()>1){
                    llcontent.addView(rowView, 1);
                }

            }
            else if (event_name.equals("Changed beatplan")) {
                final View rowView = inflater.inflate(R.layout.view_timeline, null);
                FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Demi.otf");
                fontChanger.replaceFonts((ViewGroup) rowView);
                TextView time_txt = rowView.findViewById(R.id.txt_time);
                ImageView location_mark=rowView.findViewById(R.id.location_mark);

                TextView txt_event_name = rowView.findViewById(R.id.event_name);
                TextView txt_event_desc = rowView.findViewById(R.id.event_description);
                JSONObject jsonObject = new JSONObject(event_json);
                txt_event_name.setText("Beat Plan Changed");
                txt_event_desc.setText("From " + jsonObject.getString("old_beatplan") + " to " + jsonObject.getString("new_beatplan"));
                time_txt.setText(event_time);
                ImageView imageView = rowView.findViewById(R.id.event_pic);
                imageView.setImageResource(R.drawable.route_plan_change);

                if(jsonObject.has("latitude") && jsonObject.has("longitude")){
                    location_mark.setVisibility(View.VISIBLE);
                    float lat=Float.parseFloat(jsonObject.getString("latitude"));
                    if(lat==0.0 || jsonObject.getString("latitude").equals("null")){
                        location_mark.setImageResource(R.drawable.ic_place_strike);
                    }else{
                        location_mark.setImageResource(R.drawable.option_old_visit);
                        final String finalDaddr =jsonObject.getString("latitude") + "," + jsonObject.getString("longitude");
                        location_mark.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String uri = "http://maps.google.com/maps?q="+ finalDaddr;
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                startActivity(intent);

                            }
                        });
                    }
                }else{
                    location_mark.setVisibility(View.GONE);
                }




                if(llcontent.getChildCount()>1){
                    llcontent.addView(rowView, 1);
                }
            }
            else if (event_name.equals("Checkout")) {
                final View rowView = inflater.inflate(R.layout.view_timeline, null);
                FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Demi.otf");
                fontChanger.replaceFonts((ViewGroup) rowView);
                TextView time_txt = rowView.findViewById(R.id.txt_time);
                ImageView location_mark=rowView.findViewById(R.id.location_mark);

                TextView txt_event_name = rowView.findViewById(R.id.event_name);
                TextView txt_event_desc = rowView.findViewById(R.id.event_description);
                ImageView imageView = rowView.findViewById(R.id.event_pic);
                imageView.setImageResource(R.drawable.checkout);
                txt_event_name.setText("Marked EOD");
                txt_event_desc.setText("");
                time_txt.setText(event_time);
                if(!event_json.equals("")) {
                    JSONObject jsonObject = new JSONObject(event_json);

                    if (jsonObject.has("latitude") && jsonObject.has("longitude")) {
                        location_mark.setVisibility(View.VISIBLE);
                        float lat=Float.parseFloat(jsonObject.getString("latitude"));
                        if (lat==0.0 || jsonObject.getString("latitude").equals("null")) {
                            location_mark.setImageResource(R.drawable.ic_place_strike);
                        } else {
                            location_mark.setImageResource(R.drawable.option_old_visit);
                            final String finalDaddr =jsonObject.getString("latitude") + "," + jsonObject.getString("longitude");

                            location_mark.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String uri = "http://maps.google.com/maps?q=" + finalDaddr;
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                    startActivity(intent);

                                }
                            });
                        }
                    } else {
                        location_mark.setVisibility(View.GONE);
                    }

                }else {
                    location_mark.setVisibility(View.GONE);
                }

                if(llcontent.getChildCount()>1){
                    llcontent.addView(rowView, 1);
                }

            }
        }catch (Exception e){
            e.printStackTrace();

        }


    }


    @Override
    public void onBackPressed() {

        thread.interrupt();
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();

            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
            super.onBackPressed();
        }
        // your code.
    }

    private void showcheckoutpopup() {

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
                } catch (Exception ex) {

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
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); }

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
                                    if (ActivityCompat.checkSelfPermission(TimelineActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(TimelineActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                                Log.e("timeline checkout",e.toString());
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
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  }

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
                container_timeline.removeView(menuOption);
                flagpressed=false;
                llhome.setEnabled(true);
                llrouteplan.setEnabled(true);
                llorder.setEnabled(true);
                llaccount.setEnabled(true);
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
                                container_timeline.removeView(menuOption);
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
                                container_timeline.removeView(menuOption);
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
                                    container_timeline.removeView(menuOption);
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
                                    container_timeline.removeView(menuOption);
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

        container_timeline.addView(menuOption, container_timeline.getChildCount()-1);


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
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            //spinnerDialog.dismiss();
            try{
                View_load_dialog.hideDialog();}catch (Exception e){
                e.printStackTrace();
            }
        }
    }//

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

    public String  GetText()  throws UnsupportedEncodingException {
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


    public String getDashDetails(String sales_rep_id){

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
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/dashboard_mobile_app/get_sales_rep_visit_count_api");

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

    @Override
    public void  onDestroy(){
        super.onDestroy();
        thread.interrupt();
    }


}
