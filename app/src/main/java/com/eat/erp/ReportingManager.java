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
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Spannable;
import android.text.TextUtils;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReportingManager extends AppCompatActivity {

    private FusedLocationProviderClient client;
    LinearLayout llcontent;
    TextView my_name, my_zone;
    Button view_my_activity, view_my_attendance;
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
    boolean restartflag, restartflag2;
    int logos[] = {R.drawable.option_checkout, R.drawable.option_new_visit, R.drawable.option_old_visit, R.drawable.option_timeline};
    Button fab_btn;
    RelativeLayout container_reporting;
    boolean flagpressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporting_manager);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        restartflag=false;
        restartflag2=false;
        llcontent=findViewById(R.id.llcontent_reporting_manager);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        face = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Demi.otf");
        context = ReportingManager.this;
        txttitle = (TextView) findViewById(R.id.txttitle);
        txttitle.setText("Sales Rep Activity");
        session = new session(context);
        my_name=findViewById(R.id.my_name);
        my_zone=findViewById(R.id.my_assigned_zone);
        view_my_activity=findViewById(R.id.btn_view_activity);
        view_my_attendance=findViewById(R.id.btn_view_attendance);
        my_zone.setTypeface(face);
        my_name.setTypeface(face);
        view_my_attendance.setTypeface(face);
        view_my_activity.setTypeface(face);

        view_my_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View_load_dialog.showDialog();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent i=new Intent(context, TimelineActivity.class);
                        i.putExtra("sales_rep_id", session.getsales_rep_id());
                        startActivityForResult(i, 1);
                    }
                },500);
            }
        });
        view_my_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View_load_dialog.showDialog();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent i=new Intent(context, AttendanceActivity.class);
                        i.putExtra("sales_rep_id", session.getsales_rep_id());
                        startActivityForResult(i, 1);
                    }
                },500);
            }
        });


        fa = this;

        TextView todaydate = (TextView) findViewById(R.id.todaydate);
        Calendar c = Calendar.getInstance();
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

        fab_btn=findViewById(R.id.fab_btn);
        container_reporting=findViewById(R.id.container_reporting);
        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flagpressed){
                    flagpressed=false;
                    container_reporting.removeViewAt(container_reporting.getChildCount()-2);
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

        my_name.setText("Reporting Manager: "+session.getfirst_name());

        String response="";
        response=getTeamList();
        if(!response.equals("[]")&&!response.trim().isEmpty()){
            //Log.e("sales_rep_list", getTeamList());
            try {
                JSONArray jsonArray=new JSONArray(response);
                for(int i=0; i<jsonArray.length();i++){
                    JSONObject ob=jsonArray.getJSONObject(i);
                    String sales_rep_name=ob.getString("sales_rep_name");
                    String sales_rep_zone=ob.getString("zone");
                    String sales_rep_contact=ob.getString("mobile");
                    String sales_rep_id=ob.getString("sales_rep_id");
                    inflate_sales_rep(sales_rep_name, sales_rep_zone,sales_rep_contact, sales_rep_id);
                    my_zone.setText(ob.getString("my_zone"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
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
                container_reporting.removeView(menuOption);
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
                                container_reporting.removeView(menuOption);
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
                                container_reporting.removeView(menuOption);
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
                                    container_reporting.removeView(menuOption);
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
                                    container_reporting.removeView(menuOption);
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

        container_reporting.addView(menuOption, container_reporting.getChildCount()-1);


    }



    public void inflate_sales_rep(String sales_rep_name, String sales_rep_zone, final String sales_rep_contact, final String sales_rep_id){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.view_sales_rep, null);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Demi.otf");
        fontChanger.replaceFonts((ViewGroup) rowView);
        TextView txtsales_rep_name=rowView.findViewById(R.id.sales_rep_name);
        TextView txtsales_rep_zone=rowView.findViewById(R.id.assigned_zone);
        TextView txtcall_number=rowView.findViewById(R.id.call_number);
        Button source_activity=rowView.findViewById(R.id.source_activity);
        Button source_attendance=rowView.findViewById(R.id.source_attendance);
        Button call_button=rowView.findViewById(R.id.call_button);
        txtsales_rep_name.setText(sales_rep_name);
        txtsales_rep_zone.setText(sales_rep_zone);
        txtcall_number.setText(sales_rep_contact);

        txtcall_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+sales_rep_contact));
                startActivity(callIntent);
            }
        });
        call_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+sales_rep_contact));
                startActivity(callIntent);
            }
        });


        source_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View_load_dialog.showDialog();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent i = new Intent(context, TimelineActivity.class);
                        i.putExtra("sales_rep_id", sales_rep_id);
                        startActivityForResult(i, 1);
                    }
                }, 500);
            }
        });

        source_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View_load_dialog.showDialog();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent i = new Intent(context, AttendanceActivity.class);
                        i.putExtra("sales_rep_id", sales_rep_id);
                        startActivityForResult(i, 1);
                    }
                }, 500);
            }
        });

            llcontent.addView(rowView, llcontent.getChildCount()-1);





    }

    public String getMyActivityDetails(){
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        //String sales_rep_id=session.getsales_rep_id();

        String data=null;

        try{

            data = URLEncoder.encode("sales_rep_id", "UTF-8")
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
            Log.e("my_report", "postactivitydetails: "+ex.toString());
            //Toast.makeText(context,"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        }
        finally {
            try {

                reader.close();
            } catch (Exception ex) {
                Log.e("my_report", "postactivitydetails2: "+ex.toString());
            }
        }
        return "";
    }

    public String getTeamActivityDetails(String sales_rep_id){
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
            Log.e("report", "postactivitydetails: "+ex.toString());
            //Toast.makeText(context,"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        }
        finally {
            try {

                reader.close();
            } catch (Exception ex) {
                Log.e("report", "postactivitydetails2: "+ex.toString());
            }
        }
        return "";
    }

    public String getTeamList(){

        String data=null;

        try{

            data = URLEncoder.encode("sales_rep_id", "UTF-8")
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
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/dashboard_mobile_app/get_sales_rep_list_api");

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
            Log.e("report", "get: "+ex.toString());
            //Toast.makeText(context,"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        }
        finally {
            try {

                reader.close();
            } catch (Exception ex) {
                Log.e("report", "get1: "+ex.toString());
            }
        }
        return "";
    }
    @Override
    public void onBackPressed() {

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
                                    if (ActivityCompat.checkSelfPermission(ReportingManager.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ReportingManager.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                                Log.e("reporting checkout",e.toString());
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


}
