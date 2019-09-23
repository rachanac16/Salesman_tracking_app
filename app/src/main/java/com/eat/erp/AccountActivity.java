package com.eat.erp;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

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

public class AccountActivity extends AppCompatActivity {

    boolean restartflag = false, restartflag2 = false, flagpressed = false;
    Context context;
    LinearLayout llhome, llrouteplan, llorder, llaccount;
    ProgressDialog spinnerDialog;
    session session;
    TextView txtname, txtinitial, txttitle, txtcheckout;
    LinearLayout llcheckin, llcheckout;
    Typeface face;
    View_load_dialog View_load_dialog;
    RelativeLayout container_account;
    Button fab_btn;
    int logos[] = {R.drawable.option_checkout, R.drawable.option_new_visit, R.drawable.option_old_visit, R.drawable.option_timeline};
    LinearLayout llview_attendance;
    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));

        context = AccountActivity.this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        restartflag = false;
        restartflag2 = false;
        flagpressed = false;
        session = new session(context);

        /*try {
            GPSTracker gps = new GPSTracker(this);
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            Log.e("acc", "lat: " + latitude + " long: " + longitude);
        }catch (Exception e){
            Log.e("acc", "err: "+e.toString());
        }*/
        face = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Demi.otf");
        txttitle = (TextView) findViewById(R.id.txttitle);
        txttitle.setText("Account");
        container_account = findViewById(R.id.container_account);

        llhome = (LinearLayout) findViewById(R.id.llhome);
        llrouteplan = (LinearLayout) findViewById(R.id.llrouteplan);
        llorder = (LinearLayout) findViewById(R.id.llorder);
        llaccount = (LinearLayout) findViewById(R.id.llaccount);
        txtname = (TextView) findViewById(R.id.textView2);
        txtinitial = (TextView) findViewById(R.id.txtinitial);
        TextView txtcheckin = (TextView) findViewById(R.id.txtcheckin);
        llcheckin = (LinearLayout) findViewById(R.id.llcheckin);
        llcheckout = (LinearLayout) findViewById(R.id.llcheckout);

        fab_btn = findViewById(R.id.fab_btn);
        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flagpressed) {
                    flagpressed = false;
                    container_account.removeViewAt(container_account.getChildCount() - 2);
                    fab_btn.setRotation(0);
                    llhome.setEnabled(true);
                    llrouteplan.setEnabled(true);
                    llorder.setEnabled(true);
                    llaccount.setEnabled(true);
                } else {
                    flagpressed = true;
                    llhome.setEnabled(false);
                    llrouteplan.setEnabled(false);
                    llorder.setEnabled(false);
                    llaccount.setEnabled(false);
                    showMenuOption();
                    fab_btn.setRotation(45);
                }

            }
        });


        if (session.getcheck_out_time() != null) {
            if (!session.getcheck_out_time().equals("null") || session.getcheck_out_time().equals("")) {
                llcheckout.setVisibility(View.VISIBLE);
                llcheckin.setVisibility(View.GONE);
            } else {
                llcheckout.setVisibility(View.GONE);
                llcheckin.setVisibility(View.VISIBLE);
            }
        }

        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            showSettingsAlert();
        }else{client = LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(AccountActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AccountActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                        Toast.makeText(context, "lat: "+location.getLatitude()+" longi: "+location.getLongitude(), Toast.LENGTH_SHORT).show();
                    }
                }
            });}

        txtcheckout = (TextView) findViewById(R.id.txtcheckout);

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



        Button checkout = (Button) findViewById(R.id.checkout);
        checkout.setTypeface(face);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    showcheckoutpopup();
                            /*String response = GetText();
                            session.setcheck_out_time(response);
                            Intent i = new Intent(context,NavigationActivity.class);
                            startActivity(i);*/

                }
                catch (Exception ex) {

                }
            }
        });

        llview_attendance=findViewById(R.id.llview_attendance);
        llview_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View_load_dialog.showDialog();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(context, AttendanceActivity.class);
                        i.putExtra("source", "activity");
                        i.putExtra("sales_rep_id", session.getsales_rep_id());
                        startActivityForResult(i, 1);
                    }
                }, 200);


            }
        });

        String emailid=session.getusername();
        if(emailid.equals("")) {
            Intent i = new Intent(context,MainActivity.class);
            startActivity(i);
        }
        else {
            try {
                String first_name = session.getfirst_name();
                String last_name = session.getlast_name();
                String initial = first_name.substring(0, 1);
                txtname.setText(first_name + " " + last_name);
                txtinitial.setText(initial.toUpperCase());

                String checkintime = session.getcheck_in_time();
                String checkouttime = session.getcheck_out_time();

                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = dateFormatter.parse(checkintime);

                SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a");
                String time = timeFormatter.format(date);

                txtcheckin.setText(time);
                txtcheckin.setTypeface(face);

                if(checkouttime!=null) {
                    SimpleDateFormat dateFormatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date2 = dateFormatter2.parse(checkouttime);

                    SimpleDateFormat timeFormatter2 = new SimpleDateFormat("hh:mm a");
                    String time2 = timeFormatter2.format(date2);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String strDate1 = sdf.format(date2);
                    Calendar cal = Calendar.getInstance();
                    String strDate2 = sdf.format(cal.getTime());

                    if(strDate1.equals(strDate2)){
                        txtcheckout.setText(time2);
                        txtcheckout.setTypeface(face);

                        llcheckout.setVisibility(View.VISIBLE);
                        llcheckin.setVisibility(View.GONE);
                    } else {
                        llcheckout.setVisibility(View.GONE);
                        llcheckin.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    llcheckout.setVisibility(View.GONE);
                    llcheckin.setVisibility(View.VISIBLE);
                }
            }
            catch (Exception ex) {

            }
        }
    }

    private void showSettingsAlert() {
        LayoutInflater factory = LayoutInflater.from(context);
        final View deleteDialogView = factory.inflate(R.layout.alertdialog_location, null);
        final AlertDialog deleteDialognew = new AlertDialog.Builder(context).setIcon(R.drawable.shape).create();
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) deleteDialogView);
        TextView title_dialog=deleteDialogView.findViewById(R.id.enable_lbl);
        deleteDialognew.setView(deleteDialogView);
        deleteDialognew.setCancelable(false);

        title_dialog.setText("GPS is not enabled. Enable it now?");

        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                deleteDialognew.dismiss();
                startActivity(intent);
            }
        });
        deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialognew.dismiss();
            }
        });

        deleteDialognew.show();
        deleteDialognew.setCancelable(false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(deleteDialognew.getWindow().getAttributes());

        int dialogWindowWidth = (int) (displayWidth * 0.7f);
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        deleteDialognew.getWindow().setAttributes(layoutParams);
        deleteDialognew.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

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
                container_account.removeView(menuOption);
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
                                container_account.removeView(menuOption);
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
                                container_account.removeView(menuOption);
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
                                    container_account.removeView(menuOption);
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
                                    container_account.removeView(menuOption);
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

        container_account.addView(menuOption, container_account.getChildCount()-1);


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
                    final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                        showSettingsAlert();
                    }else{
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
                                        final String[] lat = {"0.0"};
                                        final String[] longi = {"0.0"};
                                        JSONObject jsonObject=new JSONObject();
                                        client = LocationServices.getFusedLocationProviderClient(context);
                                        if (ActivityCompat.checkSelfPermission(AccountActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AccountActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                                    Log.e("account checkout",e.toString());
                                }
                                View_load_dialog.hideDialog();
                                showPopUp6();
                            }
                        }, 150);
                    }

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
}
