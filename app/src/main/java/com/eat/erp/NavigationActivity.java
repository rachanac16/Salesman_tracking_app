package com.eat.erp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.TestLooperManager;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;

import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NavigationActivity extends AppCompatActivity implements LocationListener {
    private session session;
    Context context;
    TextView txtname, txtinitial, txttitle;
    String response;
    MenuItem nav_checkintime;
    MenuItem nav_checkouttime;
    LocationManager locationManager;
    Location loc;
    ArrayList<String> permissions = new ArrayList<>();
    ArrayList<String> permissionsToRequest;
    ArrayList<String> permissionsRejected = new ArrayList<>();
    boolean isGPS = false;
    boolean isNetwork = false;
    boolean canGetLocation = true;
    private final static int ALL_PERMISSIONS_RESULT = 101;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    View_load_dialog View_load_dialog;
    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        Typeface face = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Demi.otf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        context = NavigationActivity.this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        View_load_dialog = new View_load_dialog(this);

        getSupportActionBar().setDisplayShowTitleEnabled(true);


        /*DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
*/
        txtname = (TextView) findViewById(R.id.textView2);
        txtinitial = (TextView) findViewById(R.id.txtinitial);
        txttitle = (TextView) findViewById(R.id.txttitle);
        txttitle.setText("Attendance");

        TextView todaydate = (TextView) findViewById(R.id.todaydate);
        Calendar c = Calendar.getInstance();
        String dayNumberSuffix = dashboardActivity.getDayNumberSuffix(c.get(Calendar.DAY_OF_MONTH));
        Date date3 = Calendar.getInstance().getTime();
        SimpleDateFormat timeFormatter3 = new SimpleDateFormat("dd'" + dayNumberSuffix + "' MMM yyyy");
        String time3 = timeFormatter3.format(date3);

        todaydate.setText(time3 + ", Today");

        txtname.setTypeface(face);

        session = new session(context);

        session.setcheck_out_time("null");
        /*Menu menu = navigationView.getMenu();
        nav_checkintime = menu.findItem(R.id.nav_checkintime);
        nav_checkouttime = menu.findItem(R.id.nav_checkouttime);*/

        String emailid = session.getusername();
        if (emailid.equals("")) {
            Intent i = new Intent(context, MainActivity.class);
            startActivity(i);
        } else {
            try {

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                StrictMode.setThreadPolicy(policy);

                response = GetText();
                //Log.e("getText", GetText());
                JSONObject obj = new JSONObject(response);
                if (obj != null) {
                    JSONArray data = obj.getJSONArray("data");
                    if (data.length() > 0) {
                        //Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                        JSONObject data1 = data.getJSONObject(0);
                        String check_out = data1.getString("check_out");
                        String working_status = data1.getString("working_status");
                        session.setcheck_in_time(data1.getString("check_in_time"));

                        if (working_status.equals("Absent")) {
                            Intent i = new Intent(context, Check_attendanceActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            if (check_out.equals("0")) {
                                //Toast.makeText(context,"h",Toast.LENGTH_LONG).show();
                                Log.e("here an", "dash");
                                Intent i = new Intent(context, dashboardActivity.class);
                                startActivity(i);
                                finish();
                            }
                            String first_name = session.getfirst_name();
                            String last_name = session.getlast_name();
                            String initial = first_name.substring(0, 1);
                            txtname.setText(first_name + " " + last_name);
                            txtinitial.setText(initial.toUpperCase());

                        }
                    } else {
                        String first_name = session.getfirst_name();
                        String last_name = session.getlast_name();
                        String initial = first_name.substring(0, 1);
                        txtname.setText(first_name + " " + last_name);
                        txtinitial.setText(initial.toUpperCase());
                    }
                } else {
                    JSONArray data = obj.getJSONArray("data");
                    JSONObject data1 = data.getJSONObject(0);
                    String check_out = data1.getString("check_out");
                    String working_status = data1.getString("working_status");
                    if (working_status.equals("Absent")) {
                        Log.e("nav to check", "nav to check");
                        Intent i = new Intent(context, Check_attendanceActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        String first_name = session.getfirst_name();
                        String last_name = session.getlast_name();
                        String initial = first_name.substring(0, 1);
                        txtname.setText(first_name + " " + last_name);
                        txtinitial.setText(initial.toUpperCase());
                    }

                }
            } catch (Exception ex) {
                //Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
            }

            TextView workingTextview = (TextView) findViewById(R.id.working);
            TextView absentTextview = (TextView) findViewById(R.id.absent);

            workingTextview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
                    isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    isNetwork = isNETWORK();
                    permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
                    permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
                    permissionsToRequest = findUnAskedPermissions(permissions);

                    if ((!isGPS && !isNetwork) || (!isNetwork && isGPS) || (!isGPS && isNetwork)) {
                        showSettingsAlert();
                        getLastLocation();

                    } else {

                        // check permissions
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (permissionsToRequest.size() > 0) {
                                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
                                canGetLocation = false;
                            }
                        }

                        // get location

                        GPSTracker gpsTracker = new GPSTracker(context);
                        OrdersData.latitude = gpsTracker.getLatitude() + "";
                        OrdersData.longitude = gpsTracker.getLongitude() + "";

                        LayoutInflater factory = LayoutInflater.from(context);
                        final View deleteDialogView = factory.inflate(R.layout.alertdialog_working, null);
                        final AlertDialog deleteDialog = new AlertDialog.Builder(context).setIcon(R.drawable.shape).create();
                        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
                        fontChanger.replaceFonts((ViewGroup) deleteDialogView);
                        deleteDialog.setView(deleteDialogView);
                        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //your business logic
                                //deleteDialog.dismiss();
                                try {
                                    View_load_dialog.showDialog();

                                    Calendar c = Calendar.getInstance();
                                    String dayNumberSuffix = dashboardActivity.getDayNumberSuffix(c.get(Calendar.DAY_OF_MONTH));
                                    Date date1 = Calendar.getInstance().getTime();
                                    SimpleDateFormat timeFormatter1 = new SimpleDateFormat("dd'" + dayNumberSuffix + "' MMM yyyy");
                                    String time1 = timeFormatter1.format(date1);

                                    JSONObject jsonObject = new JSONObject();

                                    final String[] lat = {"0.0"};
                                    final String[] longi = {"0.0"};
                                    if (ActivityCompat.checkSelfPermission(NavigationActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NavigationActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                                            lat[0] = location.getLatitude() + "";
                                            longi[0] = location.getLongitude() + "";
                                        }
                                    });

                                    jsonObject.put("latitude", lat[0]);
                                    jsonObject.put("longitude", longi[0]);
                                    jsonObject.put("date", time1);
                                    jsonObject.put("is_present", "yes");
                                    String responsetime = Savetext("Present","","");
                                    session.setcheck_in_time(responsetime);
                                    String checkintime = session.getcheck_in_time();
                                    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date date = dateFormatter.parse(checkintime);

                                    SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a");
                                    String time = timeFormatter.format(date);

                                    try {

                                        OfflineDbHelper offlineDbHelper=new OfflineDbHelper(context);
                                        SQLiteDatabase db=offlineDbHelper.getReadableDatabase();
                                        Cursor cursor=offlineDbHelper.readEVENT(db);
                                        if(cursor.getCount()<=0){
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
                                                                Log.e("timeline object details", event_name + " " + event_time + " " + event_json);
                                                                OfflineDbHelper offlineDbHelper1 = new OfflineDbHelper(context);
                                                                SQLiteDatabase db1 = offlineDbHelper1.getWritableDatabase();
                                                                offlineDbHelper1.addEVENT(event_name, event_time, event_json, db1);
                                                            }

                                                        }

                                                    }catch (Exception e){
                                                        Log.e("timeline_db", "inside handler2: "+e.toString());
                                                    }


                                                    View_load_dialog.hideDialog();
                                                }
                                            }, 200);
                                        }




                                        OfflineDbHelper offline = new OfflineDbHelper(context);
                                        SQLiteDatabase d = offline.getWritableDatabase();
                                        offline.addEVENT("Attendance", time, jsonObject.toString(), d);


                                        Log.e("nav_db", "event-attendance added to db");

                                       postActivityDetails("Attendance",time,jsonObject.toString()  );

                                    }catch (Exception e)
                                    {
                                        Log.e("nav_db", e.toString());
                                    }
                                    //nav_checkintime.setTitle("Check In Time (" + time + ")");


                                    new Handler().postDelayed(new Runnable() {
                                        public void run() {
                                            Intent intent = new Intent(context, dashboardActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivityForResult(intent, 1);
                                            overridePendingTransition(0, 0);
                                            finish();
                                            return;
                                        }
                                    }, 2000);
                                    //Toast.makeText(context, "You clicked yes button", Toast.LENGTH_LONG).show();
                                }
                                catch (Exception ex) {
                                    //Toast.makeText(context, "s\n"+ex.toString(), Toast.LENGTH_LONG).show();
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
                        //Toast.makeText(context,OrdersData.latitude,Toast.LENGTH_LONG).show();
                    }
                }


            });

            absentTextview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        showPopUp2();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            });

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
            Log.e("time", "getactivitydetails: "+ex.toString());
            //Toast.makeText(context,"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        }
        finally {
            try {

                reader.close();
            } catch (Exception ex) {
                Log.e("time", "getactivitydetails: "+ex.toString());
            }
        }
        return "";
    }



    private void showPopUp2() throws JSONException {

        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.alertdialog, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).setIcon(R.drawable.shape).create();
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) deleteDialogView);
        deleteDialog.setView(deleteDialogView);
        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                //deleteDialog.dismiss();
                showPopUp3();
            }
        });

        deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp4();
            }
        });

        deleteDialog.setCancelable(false);
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
        /*AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);

        helpBuilder.setPositiveButton("Approved",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        showPopUp3();
                    }
                });

        helpBuilder.setNegativeButton("Casual", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog1, int which1) {
                showPopUp4();
            }
        });



        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();*/

    }

    public void postActivityDetails(String event_name, String event_time, String event_json){
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
            Log.e("nav", "postactivitydetails: "+ex.toString());
            //Toast.makeText(context,"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        }
        finally {
            try {

                reader.close();
            } catch (Exception ex) {
                Log.e("nav", "postactivitydetails2: "+ex.toString());
            }
        }
    }


    private void showPopUp3(){

        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.alertdialog_approved, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).setIcon(R.drawable.shape).create();
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) deleteDialogView);
        deleteDialog.setView(deleteDialogView);
        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                //deleteDialog.dismiss();
                try {

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            try {
                                locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
                                isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                                isNetwork = isNETWORK();
                                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
                                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
                                permissionsToRequest = findUnAskedPermissions(permissions);

                                if ((!isGPS && !isNetwork)||(!isNetwork&&isGPS)||(!isGPS&&isNetwork)) {
                                    showSettingsAlert();
                                    getLastLocation();

                                } else {

                                    // check permissions
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        if (permissionsToRequest.size() > 0) {
                                            requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
                                            canGetLocation = false;
                                        }
                                    }
                                    Savetext("Absent", "Yes", "");
                                    OfflineDbHelper offlineDbHelper = new OfflineDbHelper(context);
                                    SQLiteDatabase db = offlineDbHelper.getReadableDatabase();
                                    Cursor cursor = offlineDbHelper.readEVENT(db);
                                    if (cursor.getCount() <= 0) {
                                        View_load_dialog.showDialog();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    String response = getActivityDetails(session.getsales_rep_id());
                                                    JSONArray jsonArray = new JSONArray(response);
                                                    if (jsonArray.length() > 0) {
                                                        for (int i = 0; i < jsonArray.length(); i++) {
                                                            JSONObject ob = jsonArray.getJSONObject(i);
                                                            String event_name = ob.getString("event_name");
                                                            String event_date = ob.getString("event_date");
                                                            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                            Date date = dateFormatter.parse(event_date);

                                                            SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a");
                                                            String event_time = timeFormatter.format(date);
                                                            String event_json = ob.getString("event_json");
                                                            Log.e("timeline object details", event_name + " " + event_time + " " + event_json);
                                                            OfflineDbHelper offlineDbHelper1 = new OfflineDbHelper(context);
                                                            SQLiteDatabase db1 = offlineDbHelper1.getWritableDatabase();
                                                            offlineDbHelper1.addEVENT(event_name, event_time, event_json, db1);
                                                        }

                                                    }

                                                } catch (Exception e) {
                                                    Log.e("timeline_db", "inside handler2: " + e.toString());
                                                }


                                                View_load_dialog.hideDialog();
                                            }
                                        }, 200);
                                    }


                                    Calendar c1 = Calendar.getInstance();
                                    String dayNumberSuffix = dashboardActivity.getDayNumberSuffix(c1.get(Calendar.DAY_OF_MONTH));
                                    Date date12 = Calendar.getInstance().getTime();
                                    SimpleDateFormat timeFormatter1 = new SimpleDateFormat("dd'" + dayNumberSuffix + "' MMM yyyy");
                                    String time12 = timeFormatter1.format(date12);

                                    JSONObject jsonObject = new JSONObject();
                                    GPSTracker gpsTracker = new GPSTracker(context);
                                    jsonObject.put("latitude", gpsTracker.getLatitude() + "");
                                    jsonObject.put("longitude", gpsTracker.getLongitude() + "");
                                    jsonObject.put("date", time12);
                                    jsonObject.put("is_present", "no");
                                    OfflineDbHelper offline = new OfflineDbHelper(context);
                                    SQLiteDatabase d = offline.getWritableDatabase();
                                    String date = new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());

                                    offlineDbHelper.addEVENT("Attendance", date, jsonObject.toString(), d);
                                    postActivityDetails("Attendance", date, jsonObject.toString());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Log.e("absent nav","to check");
                            Intent i = new Intent(context,Check_attendanceActivity.class);
                            startActivity(i);
                            finish();
                        }
                    },100);


                }
                catch (Exception ex) {
                    Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });
        deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp5();
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
        /*AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Approved Leave");
        helpBuilder.setMessage("Have you Applied on Keka?");
        helpBuilder.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog2, int which2) {
                        showPopUp5();
                    }
                });

        helpBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                try {
                    Savetext("Absent","Yes","");
                    Intent i = new Intent(context,Check_attendanceActivity.class);
                    startActivity(i);
                }
                catch (Exception ex) {

                }
            }
        });






        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();*/


    }


    private void showPopUp4(){

        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.alertdialog_casual, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).setIcon(R.drawable.shape).create();
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) deleteDialogView);
        deleteDialog.setView(deleteDialogView);
        final EditText input = deleteDialogView.findViewById(R.id.input);
        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                //deleteDialog.dismiss();
                try {
                    locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
                    isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    isNetwork = isNETWORK();
                    permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
                    permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
                    permissionsToRequest = findUnAskedPermissions(permissions);

                    if ((!isGPS && !isNetwork) || (!isNetwork && isGPS) || (!isGPS && isNetwork)) {
                        showSettingsAlert();
                        getLastLocation();

                    } else {

                        // check permissions
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (permissionsToRequest.size() > 0) {
                                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
                                canGetLocation = false;
                            }
                        }
                        Savetext("Absent", "No", input.getText().toString());
                        OfflineDbHelper offlineDbHelper = new OfflineDbHelper(context);
                        SQLiteDatabase db = offlineDbHelper.getReadableDatabase();
                        Cursor cursor = offlineDbHelper.readEVENT(db);
                        if (cursor.getCount() <= 0) {
                            View_load_dialog.showDialog();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String response = getActivityDetails(session.getsales_rep_id());
                                        JSONArray jsonArray = new JSONArray(response);
                                        if (jsonArray.length() > 0) {
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                JSONObject ob = jsonArray.getJSONObject(i);
                                                String event_name = ob.getString("event_name");
                                                String event_date = ob.getString("event_date");
                                                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                Date date = dateFormatter.parse(event_date);

                                                SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a");
                                                String event_time = timeFormatter.format(date);
                                                String event_json = ob.getString("event_json");
                                                Log.e("timeline object details", event_name + " " + event_time + " " + event_json);
                                                OfflineDbHelper offlineDbHelper1 = new OfflineDbHelper(context);
                                                SQLiteDatabase db1 = offlineDbHelper1.getWritableDatabase();
                                                offlineDbHelper1.addEVENT(event_name, event_time, event_json, db1);
                                            }

                                        }

                                    } catch (Exception e) {
                                        Log.e("timeline_db", "inside handler2: " + e.toString());
                                    }


                                    View_load_dialog.hideDialog();
                                }
                            }, 200);
                        }


                        Calendar c1 = Calendar.getInstance();
                        String dayNumberSuffix = dashboardActivity.getDayNumberSuffix(c1.get(Calendar.DAY_OF_MONTH));
                        Date date12 = Calendar.getInstance().getTime();
                        SimpleDateFormat timeFormatter1 = new SimpleDateFormat("dd'" + dayNumberSuffix + "' MMM yyyy");
                        String time12 = timeFormatter1.format(date12);

                        JSONObject jsonObject = new JSONObject();
                        GPSTracker gpsTracker = new GPSTracker(context);
                        jsonObject.put("latitude", gpsTracker.getLatitude() + "");
                        jsonObject.put("longitude", gpsTracker.getLongitude() + "");
                        jsonObject.put("date", time12);
                        jsonObject.put("is_present", "no");
                        OfflineDbHelper offline = new OfflineDbHelper(context);
                        SQLiteDatabase d = offline.getWritableDatabase();
                        String date = new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());

                        offlineDbHelper.addEVENT("Attendance", date, jsonObject.toString(), d);
                        postActivityDetails("Attendance", date, jsonObject.toString());
                        Intent i = new Intent(context, Check_attendanceActivity.class);
                        startActivity(i);
                        finish();
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

    private void showPopUp5(){

        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.alertdialog_appky, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).setIcon(R.drawable.shape).create();
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) deleteDialogView);
        deleteDialog.setView(deleteDialogView);
        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                //deleteDialog.dismiss();
                try {


                    locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
                    isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    isNetwork = isNETWORK();
                    permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
                    permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
                    permissionsToRequest = findUnAskedPermissions(permissions);

                    if ((!isGPS && !isNetwork) || (!isNetwork && isGPS) || (!isGPS && isNetwork)) {
                        showSettingsAlert();
                        getLastLocation();

                    } else {

                        // check permissions
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (permissionsToRequest.size() > 0) {
                                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
                                canGetLocation = false;
                            }
                        }

                        Savetext("Absent", "No", "");

                        OfflineDbHelper offlineDbHelper = new OfflineDbHelper(context);
                        SQLiteDatabase db = offlineDbHelper.getReadableDatabase();
                        Cursor cursor = offlineDbHelper.readEVENT(db);
                        if (cursor.getCount() <= 0) {
                            View_load_dialog.showDialog();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String response = getActivityDetails(session.getsales_rep_id());
                                        JSONArray jsonArray = new JSONArray(response);
                                        if (jsonArray.length() > 0) {
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                JSONObject ob = jsonArray.getJSONObject(i);
                                                String event_name = ob.getString("event_name");
                                                String event_date = ob.getString("event_date");
                                                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                Date date = dateFormatter.parse(event_date);

                                                SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a");
                                                String event_time = timeFormatter.format(date);
                                                String event_json = ob.getString("event_json");
                                                Log.e("timeline object details", event_name + " " + event_time + " " + event_json);
                                                OfflineDbHelper offlineDbHelper1 = new OfflineDbHelper(context);
                                                SQLiteDatabase db1 = offlineDbHelper1.getWritableDatabase();
                                                offlineDbHelper1.addEVENT(event_name, event_time, event_json, db1);
                                            }

                                        }

                                    } catch (Exception e) {
                                        Log.e("timeline_db", "inside handler2: " + e.toString());
                                    }


                                    View_load_dialog.hideDialog();
                                }
                            }, 200);
                        }


                        Calendar c1 = Calendar.getInstance();
                        String dayNumberSuffix = dashboardActivity.getDayNumberSuffix(c1.get(Calendar.DAY_OF_MONTH));
                        Date date12 = Calendar.getInstance().getTime();
                        SimpleDateFormat timeFormatter1 = new SimpleDateFormat("dd'" + dayNumberSuffix + "' MMM yyyy");
                        String time12 = timeFormatter1.format(date12);

                        JSONObject jsonObject = new JSONObject();
                        GPSTracker gpsTracker = new GPSTracker(context);
                        jsonObject.put("latitude", gpsTracker.getLatitude() + "");
                        jsonObject.put("longitude", gpsTracker.getLongitude() + "");
                        jsonObject.put("date", time12);
                        jsonObject.put("is_present", "no");
                        OfflineDbHelper offline = new OfflineDbHelper(context);
                        SQLiteDatabase d = offline.getWritableDatabase();
                        String date = new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());

                        offlineDbHelper.addEVENT("Attendance", date, jsonObject.toString(), d);
                        postActivityDetails("Attendance", date, jsonObject.toString());


                        Log.e("nav to check1", "nav to checl");
                        Intent i = new Intent(context, Check_attendanceActivity.class);
                        startActivity(i);
                        finish();
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

        /*AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Employee Alert!!");
        helpBuilder.setMessage("Please apply on keka else shall be loss of pay");


        helpBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                try {
                    Savetext("Absent","No","");
                    Intent i = new Intent(context,Check_attendanceActivity.class);
                    startActivity(i);
                }
                catch (Exception ex) {

                }
            }
        });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();*/

    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")



    public String  GetText()  throws UnsupportedEncodingException
    {
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        String sales_rep_id=session.getsales_rep_id();
        String data = URLEncoder.encode("sales_rep_id", "UTF-8")
                + "=" + URLEncoder.encode(sales_rep_id, "UTF-8");


        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/Sales_Attendence/get_todays_attendance_api");

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




    public String Savetext(String working_status_save, String applied_on_keka_save, String casual_reason_save)  throws UnsupportedEncodingException
    {
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        String sales_rep_id=session.getsales_rep_id();
        String working_status=working_status_save;
        String stringLatitude=OrdersData.latitude;
        String stringLongitude=OrdersData.longitude;
        String applied_on_keka=applied_on_keka_save;
        String casual_reason=casual_reason_save;


        String data = URLEncoder.encode("working_status", "UTF-8")
                + "=" + URLEncoder.encode(working_status, "UTF-8");

        data += "&" + URLEncoder.encode("sales_rep_id", "UTF-8")
                + "=" + URLEncoder.encode(sales_rep_id, "UTF-8");

        data += "&" + URLEncoder.encode("casual_reason", "UTF-8")
                + "=" + URLEncoder.encode(casual_reason, "UTF-8");

        data += "&" + URLEncoder.encode("applied_on_keka", "UTF-8")
                + "=" + URLEncoder.encode(applied_on_keka, "UTF-8");

        data += "&" + URLEncoder.encode("latitude", "UTF-8")
                + "=" + URLEncoder.encode(stringLatitude, "UTF-8");

        data += "&" + URLEncoder.encode("longitude", "UTF-8")
                + "=" + URLEncoder.encode(stringLongitude, "UTF-8");


        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/Sales_Attendence/save_api");

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
    public void onLocationChanged(Location location) {
        updateUI(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {
        getLocation();
    }

    @Override
    public void onProviderDisabled(String s) {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    private void getLocation() {
        try {
            if (canGetLocation) {

                if (isNetwork) {
                    // from GPS
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (loc != null){
                            updateUI(loc);}else{
                            GPSTracker gpstrack=new GPSTracker(context);
                            loc.setLongitude(gpstrack.getLongitude());
                            loc.setLatitude(gpstrack.getLatitude());
                            updateUI(loc);
                        }
                    }


                } else if (isGPS) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (loc != null){
                            updateUI(loc);}else{
                            GPSTracker gpstrack=new GPSTracker(context);
                            loc.setLongitude(gpstrack.getLongitude());
                            loc.setLatitude(gpstrack.getLatitude());
                            updateUI(loc);
                        }
                    }
                    // from Network Provider
                    } else {
                    loc.setLatitude(0);
                    loc.setLongitude(0);
                    updateUI(loc);
                }
            } else {

            }
        } catch (SecurityException e) {
            //Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    private void getLastLocation() {
        try {
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);
            if(provider!=null) {
                Location location = locationManager.getLastKnownLocation(provider);
            }
        } catch (SecurityException e) {
            //Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canAskPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public void showSettingsAlert() {

        LayoutInflater factory = LayoutInflater.from(context);
        final View deleteDialogView = factory.inflate(R.layout.alertdialog_location, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(context).setIcon(R.drawable.shape).create();
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) deleteDialogView);
        TextView title_dialog=deleteDialogView.findViewById(R.id.enable_lbl);
        deleteDialog.setView(deleteDialogView);
        deleteDialog.setCancelable(false);
        if(!isGPS){
            title_dialog.setText("GPS is not enabled. Enable it now?");
        }else{
            title_dialog.setText("Network is not enabled. Enable it now?");
        }
        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*GPSTracker gpsTracker = new GPSTracker(context);
                if (gpsTracker.getIsGPSTrackingEnabled()) {
                String stringLatitude = String.valueOf(gpsTracker.latitude);
                Toast.makeText(context,stringLatitude,Toast.LENGTH_LONG).show();        */
                deleteDialog.dismiss();
                if(!isGPS){
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(Settings.ACTION_DATA_USAGE_SETTINGS);
                    PackageManager packageManager = getPackageManager();
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent);
                    } else {
                        intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                        startActivity(intent);
                        Log.e("navigation", "No Intent available to handle action");
                    }
                }
            }
        });
        deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
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
                deleteDialog.dismiss();
                finish();
            }
        });

        deleteDialog.show();
        deleteDialog.setCancelable(false);
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
    //Toast.makeText(context, "sequence: " + sequence, Toast.LENGTH_LONG).show();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {

    }

    private void updateUI(Location loc) {

        OrdersData.latitude = "" + Double.toString(loc.getLatitude());
        OrdersData.longitude = "" + Double.toString(loc.getLongitude());
//        Toast.makeText(context, "latitude"+loc.getLatitude(),Toast.LENGTH_LONG).show();
        //tvLatitude.setText(Double.toString(loc.getLatitude()));
        //tvLongitude.setText(Double.toString(loc.getLongitude()));
        //tvTime.setText(DateFormat.getTimeInstance().format(loc.getTime()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:

                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        View_load_dialog.showDialog();

                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                Intent i = new Intent(context, Visit_list_pageActivity.class);
                                startActivityForResult(i, 1);
                                finish();
                                return;
                            }
                        }, 500);
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() >0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            View_load_dialog.showDialog();

                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    Intent i = new Intent(context, NavigationActivity.class);
                                    startActivityForResult(i, 1);
                                    finish();
                                    return;
                                }
                            }, 50);

                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(
                                                        new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                        Toast.makeText(context, "give permission2", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    canGetLocation = true;
                    getLocation();
                }
                break;
        }
    }

    public boolean isNETWORK() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {


            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {


            return false;
        }
        return false;
    }


}
