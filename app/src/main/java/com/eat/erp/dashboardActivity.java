package com.eat.erp;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.nfc.cardemulation.OffHostApduService;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.core.app.ActivityCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.yalantis.phoenix.PullToRefreshView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class dashboardActivity extends AppCompatActivity {


    PullToRefreshView mPullToRefreshView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Context context;
    private session session;
    MenuItem nav_checkintime;
    MenuItem nav_checkouttime;
    String distributor_id_new;
    String beatplan_id_new;
    String reporting_manager_id;
    String distributor_id_old;
    String beatplan_id_old;
    private List<Beat> beatList;
    private BeatAdapter adapter;
    RecyclerView recyclerView;
    ProgressDialog spinnerDialog;
    TextView txtvisits, txtcalls, txtvper, txtcper, txtucalls, textView5, textView10, textView11, txtbars, txtcookies, txttrailmix, txtcheckout;
    LinearLayout llhome, llrouteplan, llorder, llaccount;
    Typeface tfavv;
    Typeface face;
    LinearLayout llcheckin, llcheckout;
    Button btnchange, btnpending, btnapproved_route;
    View_load_dialog View_load_dialog;
    boolean doubleBackToExitPressedOnce = false;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    String resultglobal="";
    String resultglobal1="";
    boolean restartflag=false,restartflag2=false, dialogflag=false;
    String old_beat_event_purpose="", new_beat_event_purpose;
    int logos[] = {R.drawable.option_checkout, R.drawable.option_new_visit, R.drawable.option_old_visit, R.drawable.option_timeline};
    Button fab_btn;
    RelativeLayout container_dashboard;
    boolean flagpressed,restartflag4;
    LinearLayout llview_activity, llno_rp_view_activity,llno_rp_view_attendance;
    TextView activity_title, attendance_title;
    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        restartflag=false;
        restartflag2=false;
        dialogflag=false;
        flagpressed=false;

        llno_rp_view_activity=findViewById(R.id.llno_rp_view_activity);
        llno_rp_view_attendance=findViewById(R.id.llno_rp_view_attendance);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));

        context=dashboardActivity.this;
        final String Version="3.1";
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new session(context);
        restartflag4=false;

        // full name form of the day
        try {

            checkforupdates(Version);
            String check_in_time = session.getcheck_in_time();
            String strDate1 = "";
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String strDate2 = sdf.format(cal.getTime());
            Log.e("dash", "checkin restart: "+check_in_time+" "+strDate2);
            if(!check_in_time.equals("null")&&!check_in_time.equals("")) {
                SimpleDateFormat dateFormatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date2 = dateFormatter2.parse(check_in_time);
                strDate1 = sdf.format(date2);

                if(!strDate1.equals(strDate2)){
                    Intent i=new Intent(context, NavigationActivity.class);
                    startActivity(i);
                }


            }


        } catch (Exception e) {
            Toast.makeText(context, ""+"error! try again later", Toast.LENGTH_LONG).show();

        }

       /* OfflineDbHelper offlineDbHelper=new OfflineDbHelper(context);
        SQLiteDatabase db=offlineDbHelper.getWritableDatabase();
        String where[]={"Monday"};
        db.delete(OfflineDatabase.VisitData.TABLE_NAME, "DAY==?", where);
        offlineDbHelper.addVISITDATA("Monday", "4", "0", "25", "3", db);
*/



        /*
        OfflineDbHelper offlineDbHelper=new OfflineDbHelper(context);
        SQLiteDatabase db=offlineDbHelper.getWritableDatabase();
        String proj[]={OfflineDatabase.Events.EVENT_NO, OfflineDatabase.Events.EVENT_NAME, OfflineDatabase.Events.EVENT_TIME, OfflineDatabase.Events.EVENT_JSON};
        String where="EVENT_NAME!=?";
        String whereargs[]={"Attendance"};
        db.delete(OfflineDatabase.Events.TABLE_NAME,where, whereargs );
        Log.e("datab", "deleted");
*/

        /*DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.bringToFront();
        drawerLayout.invalidate();

        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        linearLayout.invalidate();*/

        TextView todaydate = (TextView) findViewById(R.id.todaydate);
        Calendar c=Calendar.getInstance();
        String dayNumberSuffix = getDayNumberSuffix(c.get(Calendar.DAY_OF_MONTH));
        Date date3 = Calendar.getInstance().getTime();
        SimpleDateFormat timeFormatter3 = new SimpleDateFormat("dd'"+dayNumberSuffix+"' MMM yyyy");
        String time3 = timeFormatter3.format(date3);
        container_dashboard=findViewById(R.id.drawer_layout2);
        todaydate.setText(time3+ ", Today");
        fab_btn=findViewById(R.id.fab_btn);
        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flagpressed){
                    flagpressed=false;
                    container_dashboard.removeViewAt(container_dashboard.getChildCount()-2);
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

        face= Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Demi.otf");


        activity_title=findViewById(R.id.txtno_rp_activity);
        attendance_title=findViewById(R.id.txtno_rp_attendance);
        activity_title.setTypeface(face);
        attendance_title.setTypeface(face);

        TextView txttitle = (TextView) findViewById(R.id.txttitle);
        txttitle.setText("Dashboard");


        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new MyAsyncTask(context).execute();
                        checkforupdates(Version);
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, 500);
            }
        });

        //DrawerLayout mDrawerList = (DrawerLayout) findViewById(R.id.drawer_layout);
        TextView txtcheckin = (TextView) findViewById(R.id.txtcheckin);
        txtvisits = (TextView) findViewById(R.id.txtvisits);
        txtcalls = (TextView) findViewById(R.id.txtcalls);
        txtucalls = (TextView) findViewById(R.id.txtucalls);
        txtvper = (TextView) findViewById(R.id.txtvper);
        txtcper = (TextView) findViewById(R.id.txtcper);
        textView5 = (TextView) findViewById(R.id.textView5);
        textView10 = (TextView) findViewById(R.id.textView10);
        textView11 = (TextView) findViewById(R.id.textView11);
        txtbars = (TextView) findViewById(R.id.txtbars);
        txtcookies = (TextView) findViewById(R.id.txtcookies);
        txttrailmix = (TextView) findViewById(R.id.txttrailmix);

        txtvisits.setTypeface(face);
        txtcalls.setTypeface(face);
        txtucalls.setTypeface(face);
        textView5.setTypeface(face);
        textView10.setTypeface(face);
        textView11.setTypeface(face);
        txtbars.setTypeface(face);
        txtcookies.setTypeface(face);
        txttrailmix.setTypeface(face);

        llhome = (LinearLayout) findViewById(R.id.llhome);
        llrouteplan = (LinearLayout) findViewById(R.id.llrouteplan);
        llorder = (LinearLayout) findViewById(R.id.llorder);
        llaccount = (LinearLayout) findViewById(R.id.llaccount);
        llcheckin = (LinearLayout) findViewById(R.id.llcheckin);
        llcheckout = (LinearLayout) findViewById(R.id.llcheckout);
        llview_activity=findViewById(R.id.llview_activity);

        txtcheckout = (TextView) findViewById(R.id.txtcheckout);
        View_load_dialog = new View_load_dialog(this);


        TextView txtactivity=findViewById(R.id.txt_activity);
        txtactivity.setTypeface(face);
        txtactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OrdersData.reporting_manager.equals("no")){
                    View_load_dialog.showDialog();
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            Intent i=new Intent(context, TimelineActivity.class);
                            i.putExtra("sales_rep_id", session.getsales_rep_id());
                            startActivityForResult(i, 1);
                        }
                    },500);
                }else{
                    View_load_dialog.showDialog();
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            Intent i=new Intent(context, ReportingManager.class);
                            i.putExtra("source", "activity");
                            startActivityForResult(i, 1);
                        }
                    },500);
                }
            }
        });


        llview_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OrdersData.reporting_manager.equals("no")){
                    View_load_dialog.showDialog();
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            Intent i=new Intent(context, TimelineActivity.class);
                            i.putExtra("sales_rep_id", session.getsales_rep_id());
                            startActivityForResult(i, 1);
                        }
                    },150);
                }else{
                    View_load_dialog.showDialog();
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            Intent i=new Intent(context, ReportingManager.class);
                            i.putExtra("source", "activity");
                            startActivityForResult(i, 1);
                        }
                    },500);
                }
            }
        });


        llhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View_load_dialog.showDialog();

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent i = new Intent(context, dashboardActivity.class);
                        startActivityForResult(i, 1);
                        //finish();
                        return;
                    }
                }, 1000);
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
                }, 1000);
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





        txtvper.setTypeface(face);
        txtcper.setTypeface(face);



        /*DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);*/
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // FloatingActionButton fab = findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
         //   @Override
         //   public void onClick(View view) {
        /*try {
            Snackbar.make(findViewById(R.id.drawer_layout2), ""+recieveVersion("2.4"), Snackbar.LENGTH_LONG).setAction("Action", null).show();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        //    }        //});



        /*Menu menu = navigationView.getMenu();
        nav_checkintime = menu.findItem(R.id.nav_checkintime);
        nav_checkouttime = menu.findItem(R.id.nav_checkouttime);*/

        String emailid=session.getusername();
        if(emailid.equals("")) {
            Intent i = new Intent(context,MainActivity.class);
            startActivity(i);
        }
        else {
            try {
                StrictMode.ThreadPolicy policy1 = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                StrictMode.setThreadPolicy(policy1);

                String checkintime = session.getcheck_in_time();

                if(checkintime.equals("")){
                    Log.e("dash", checkintime);
                }

                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                try {
                    Date date = dateFormatter.parse(checkintime);
                    SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a");
                    String time = timeFormatter.format(date);

                    txtcheckin.setText(time);

                    txtcheckin.setTypeface(face);

                }catch (Exception e){}

                                //nav_checkouttime.setTitle("Check Out Time (" + time_checkout + ")");

                /*String first_name = session.getfirst_name();
                String last_name = session.getlast_name();
                String initial = first_name.substring(0, loading1);
                TextView txtname= findViewById(R.id.txtname);
                TextView txtinitial= findViewById(R.id.txtinitial);
                //Toast.makeText(rootView.getContext(),first_name,Toast.LENGTH_LONG).show();
                txtname.setText(first_name + " " + last_name);
                txtinitial.setText(initial.toUpperCase());
*/
                TextView txtretailer = findViewById(R.id.txtretailer);
                TextView txtrouteplan = findViewById(R.id.txtrouteplan);
                btnpending = findViewById(R.id.pending);
                btnapproved_route = findViewById(R.id.approved_route);
                btnchange=(Button) findViewById(R.id.change);
                TextView todayrouteplan = findViewById(R.id.todayrouteplan);

                btnapproved_route.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopUp2();
                    }
                });

                if(session.getcheck_out_time()!=null) {
                    if (!session.getcheck_out_time().equals("null")) {
                        //Toast.makeText(context, session.getcheck_out_time(), Toast.LENGTH_LONG).show();
                        llcheckout.setVisibility(View.VISIBLE);
                        llcheckin.setVisibility(View.GONE);
                        btnpending.setVisibility(View.GONE);
                        btnapproved_route.setVisibility(View.GONE);
                        btnchange.setVisibility(View.GONE);
                        txtcheckout.setTypeface(face);
                        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        if(!session.getcheck_out_time().equals("null")&&session.getcheck_out_time()!=null) {
                            Date date2 = dateFormatter2.parse(session.getcheck_out_time());
                            SimpleDateFormat timeFormatter2 = new SimpleDateFormat("hh:mm a");
                            String time2 = timeFormatter2.format(date2);
                            txtcheckout.setText(time2);
                        }

                    } else {
                        llcheckout.setVisibility(View.GONE);
                        llcheckin.setVisibility(View.VISIBLE);
                        btnpending.setVisibility(View.VISIBLE);
                        btnchange.setVisibility(View.VISIBLE);
                        btnapproved_route.setVisibility(View.VISIBLE);
                    }
                }else{
                    Log.e("dash", session.getcheck_in_time());
                }

                btnchange.setTypeface(face);
                btnapproved_route.setTypeface(face);
                btnpending.setTypeface(face);

                try {
                    new MyAsyncTask(context).execute();

                }
                catch (Exception ex) {
                    Log.e("dash", "er: "+ex.toString());
                    String message = getStackTrace(ex);
                    //Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                }

                todayrouteplan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View_load_dialog.showDialog();

                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                //your method code
                                Intent i = new Intent(context,Visit_list_pageActivity.class);

                                //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivityForResult(i,1);
                            }
                        },500);

                    }
                });

                llno_rp_view_activity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View_load_dialog.showDialog();

                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                //your method code
                                Intent i = new Intent(context, TimelineActivity.class);
                                i.putExtra("sales_rep_id", session.getsales_rep_id());
                                //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivityForResult(i,1);
                            }
                        },500);
                    }
                });

                llno_rp_view_attendance.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View_load_dialog.showDialog();

                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                //your method code
                                Intent i = new Intent(context, AttendanceActivity.class);
                                i.putExtra("sales_rep_id", session.getsales_rep_id());
                                //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivityForResult(i,1);
                            }
                        },500);
                    }
                });


                try {
                    String resp=getTeamList();
                    JSONArray object=new JSONArray(resp);
                    if(object.length()<=0){
                        OrdersData.reporting_manager="no";
                        llview_activity.setVisibility(View.GONE);
                        llno_rp_view_attendance.setVisibility(View.VISIBLE);
                        llno_rp_view_activity.setVisibility(View.VISIBLE);
                    }else{
                        OrdersData.reporting_manager="yes";
                        llview_activity.setVisibility(View.VISIBLE);
                        llno_rp_view_attendance.setVisibility(View.GONE);
                        llno_rp_view_activity.setVisibility(View.GONE);
                    }

                    String responsetext = getretailer();
                    JSONObject obj = new JSONObject(responsetext);
                    String distributor_name = obj.getString("distributor_name");
                    String beat_name = obj.getString("beat_name");
                    old_beat_event_purpose=beat_name;

                    String beat_status = obj.getString("beat_status");
                    reporting_manager_id = obj.getString("reporting_manager_id");
                    //Toast.makeText(context, reporting_manager_id, Toast.LENGTH_LONG).show();
                    distributor_id_old=obj.getString("distributor_id_og");
                    beatplan_id_old=obj.getString("beat_id_og");

                    JSONArray pending_beat_plan = obj.getJSONArray("pending_beat_plan");
                    //Log.e("pending_beat", "pending: "+pending_beat_plan.toString());
                    if(pending_beat_plan.length()>0) {
                        btnapproved_route.setVisibility(View.VISIBLE);
                        showPopUp2();
                    }
                    else {
                        btnapproved_route.setVisibility(View.GONE);
                    }

                    if(beat_status.equals("Pending")) {
                        btnpending.setVisibility(View.VISIBLE);

                    }
                    else {
                        btnpending.setVisibility(View.INVISIBLE);
                    }


                    txtretailer.setText(distributor_name);
                    txtrouteplan.setText(beat_name);
//++++                    session.setwed_frag("");

                    //distributor_id_new= session.getdistributor_id();
                    //beatplan_id_new=session.getbeat_id();
                    //String beatid_distributorid_new=""+beatplan_id_new.concat(distributor_id_new);
                    if(!session.getdistributor_id().equals(distributor_id_old)||!session.getbeat_id().equals(beatplan_id_old)){

                        Calendar calendar1 = Calendar.getInstance();
                        int day = calendar1.get(Calendar.DAY_OF_WEEK);

                        switch (day) {
                            case Calendar.MONDAY:
                                session.setmon_frag("");
                                break;
                            case Calendar.TUESDAY:
                                session.settues_frag("");
                                break;
                            case Calendar.WEDNESDAY:
                                session.setwed_frag("");
                                break;
                            case Calendar.THURSDAY:
                                session.setthu_frag("");
                                break;
                            case Calendar.FRIDAY:
                                session.setfri_frag("");
                                break;
                            case Calendar.SATURDAY:
                                session.setsat_frag("");
                                break;
                            case Calendar.SUNDAY:
                                session.setsun_frag("");
                                break;
                        }

                    }

                    session.setdistributor_id(distributor_id_old);
                    session.setbeat_id(beatplan_id_old);
                    session.setdistributor_name(distributor_name);
                    session.setbeatname(beat_name);
                    btnchange.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showPopUp();
                        }
                    });
                }
                catch (Exception ex) {
                    Log.e("dash", "error oncr: "+ex.toString());
                }


                //checkout
                TextView checkout = findViewById(R.id.checkout);
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

                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(dashboardActivity.this, new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        String newToken = instanceIdResult.getToken();
                    }
                });
            }
            catch (Exception ex) {

                Log.e("dash10", ex.toString());
                //Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
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
                container_dashboard.removeView(menuOption);
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
                                container_dashboard.removeView(menuOption);
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
                                container_dashboard.removeView(menuOption);
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
                                    container_dashboard.removeView(menuOption);
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
                                    container_dashboard.removeView(menuOption);
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

        container_dashboard.addView(menuOption, container_dashboard.getChildCount()-1);


    }

    private void checkforupdates(String version) {
        try {
            String latestUpdate=recieveVersion(version);
            //Toast.makeText(context, ""+latestUpdate, Toast.LENGTH_SHORT).show();
            boolean isNet=isNETWORK();
            if(isNet){
                Log.e("dash", "latest: "+latestUpdate+"Now: "+version);
                if(!latestUpdate.contains(version)&&!latestUpdate.equals("error")){
                    showPopUp4();
                }else if(latestUpdate.contains("error")){
                    showPopUp5();
                }else{Log.e("dash", "updated version");}
            }
        } catch (Exception e) {
            Toast.makeText(context, ""+"error! try again later", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onRestart(){
        super.onRestart();
        try{
            String check_in_time = session.getcheck_in_time();
            String strDate1 = "";
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String strDate2 = sdf.format(cal.getTime());
            Log.e("dash", "checkin: "+check_in_time+" "+strDate2);
            if(!check_in_time.equals("null")&&!check_in_time.equals("")) {
                SimpleDateFormat dateFormatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date2 = dateFormatter2.parse(check_in_time);
                strDate1 = sdf.format(date2);

                if(!strDate1.equals(strDate2)){
                    Intent i=new Intent(context, NavigationActivity.class);
                    startActivity(i);
                }


            }
        }catch(Exception e){}

        if (restartflag){
            View_load_dialog.showDialog();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i=new Intent(context, dashboardActivity.class);
                    startActivityForResult(i, 1);
                }
            }, 1000);
        }
        else if(restartflag2){
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
        }else if (restartflag4){

        }
    }


    private void showPopUp4() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.alertdialog_update, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).setIcon(R.drawable.shape).create();
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) deleteDialogView);
        deleteDialog.setView(deleteDialogView);
        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final String appPackageName = getPackageName(); // package name of the app
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
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
    }

    private void showPopUp5() {
            LayoutInflater factory = LayoutInflater.from(this);
            final View deleteDialogView = factory.inflate(R.layout.alertdialog_update_mandatory, null);
            final AlertDialog deleteDialog = new AlertDialog.Builder(this).setIcon(R.drawable.shape).create();
            FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
            fontChanger.replaceFonts((ViewGroup) deleteDialogView);
            deleteDialog.setView(deleteDialogView);
            deleteDialogView.findViewById(R.id.btn_okay).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        deleteDialog.dismiss();
                    }
                    catch (Exception ex) {
                    }
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

    public static String getDayNumberSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    private String recieveVersion(String v) throws UnsupportedEncodingException {

        String data = URLEncoder.encode("version", "UTF-8")
                    + "=" + URLEncoder.encode(v, "UTF-8");

            String text = "";
            BufferedReader reader=null;
            // Send data
            try
            {
                // Defined URL  where to send data
                URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/dashboard_mobile_app/get_version_api");
                // Send POST data request
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write( data );
                wr.flush();


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
                Log.e("dash9", ex.toString());
            return "error";
            }
            finally
            {
                try
                {
                    reader.close();
                }
                catch(Exception ex) {
                    Log.e("dash8", ex.toString());
                    return "error";
                }
            }
            // Show response on activity

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
                                    if (ActivityCompat.checkSelfPermission(dashboardActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(dashboardActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                                llcheckin.setVisibility(View.GONE);
                                llcheckout.setVisibility(View.VISIBLE);
                                SimpleDateFormat dateFormatter=new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
                                Date date=dateFormatter.parse(response);

                                SimpleDateFormat timeFormatter=new SimpleDateFormat("hh:mma");
                                String time=timeFormatter.format(date);
                                txtcheckout.setText(time);
                                txtcheckout.setTypeface(face);
                                btnchange.setVisibility(View.GONE);
                                btnpending.setVisibility(View.GONE);
                                btnapproved_route.setVisibility(View.GONE);


                                Calendar calendar0 = Calendar.getInstance();
                                Date date0 = calendar0.getTime();
                                String t = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date0.getTime());
/*
                                OfflineDbHelper offlineDbHelper=new OfflineDbHelper(context);
                                SQLiteDatabase db=offlineDbHelper.getWritableDatabase();
                                offlineDbHelper.addVISITDATA(t, OrdersData.actual_count, OrdersData.unplanned_count, OrdersData.total_count, OrdersData.p_call, db);
*/
                            }catch (Exception e){
                                Log.e("dash checkout",e.toString());
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
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));}

    private String returnWhatsappText() {
        String result="";
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        result+="Name: "+session.getfirst_name()+" "+session.getlast_name()+"\n";
        result+="Date Of Reporting: "+date+"\n";
        int num=session.getbeat_name().indexOf('-');
        result+="Area: "+session.getbeat_name().substring(num+1)+"\n";
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


    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            return;
        }
        else { Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(View_load_dialog.isShowing()){
            View_load_dialog.hideDialog();}
        }
    }//

    private void showPopUp2() {

        final AlertDialog.Builder helpBuilder;
        helpBuilder = new AlertDialog.Builder(context);
        helpBuilder.setTitle("Approve Route Plan ?");
        LayoutInflater inflater = getLayoutInflater();
        View route = inflater.inflate(R.layout.activity_recycle_view, null);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts(( ViewGroup) route);
        helpBuilder.setView(route);


        // Remember, create doesn't show the dialog
        final AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();

        recyclerView = (RecyclerView) route.findViewById(R.id.recycler_view);
        beatList = new ArrayList<>();
        try{
            String rep = getretailer();
            JSONObject obj = new JSONObject(rep);
            JSONArray pending_beat_plan = obj.getJSONArray("pending_beat_plan");

            for(int i=0;i<pending_beat_plan.length();i++) {
                JSONObject data1 = pending_beat_plan.getJSONObject(i);
                String sales_rep_name=data1.getString("sales_rep_name");
                String r_id=data1.getString("id");

                String old_plan=data1.getString("distributor_name1")+"-"+data1.getString("beat_name1");
                String new_plan=data1.getString("distributor_name2")+"-"+data1.getString("beat_name2");
                Beat a = new Beat(sales_rep_name, old_plan, new_plan, r_id);
                beatList.add(a);
                //Toast.makeText(getContext(),""+beatList.toString(),Toast.LENGTH_LONG).show();
            }

        }
        catch (Exception ex) {
            //Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
        }
        adapter = new BeatAdapter(context, beatList, new BeatAdapter.MyAdapterListener() {
            @Override
            public void approveTextViewOnClick(View v, int position) {
                try {
                    String r_id = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.r_id)).getText().toString();
                    //Toast.makeText(context,r_id,Toast.LENGTH_LONG).show();
                    changebeatplan(r_id,"Approved");
                    Intent intent = new Intent(context, dashboardActivity.class);
                    startActivity(intent);
                    helpDialog.dismiss();

                }
                catch (Exception ex) {
                    //Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void rejectTextViewOnClick(View v, int position) {
                try {
                    String r_id = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.r_id)).getText().toString();
                    //Toast.makeText(context,r_id,Toast.LENGTH_LONG).show();
                    changebeatplan(r_id, "Rejected");
                    Intent intent = new Intent(context, dashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    helpDialog.dismiss();
                }
                catch (Exception ex) {
                    //Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void showPopUp() {
        final AlertDialog helpBuilder = new AlertDialog.Builder(context).setIcon(R.drawable.shape).create();

        LayoutInflater inflater = getLayoutInflater();
        final View route = inflater.inflate(R.layout.activity_change_route, null);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) route);
        helpBuilder.setView(route);

        TextView txtretailer=(TextView)findViewById(R.id.txtretailer);
        final String distributorName=txtretailer.getText().toString();
                View_load_dialog.showDialog();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final ArrayList<StringWithTag> mylist = new ArrayList<StringWithTag>();
                            String responsetext = getretailer();
                            JSONObject obj = new JSONObject(responsetext);
                            HashMap<Integer,String> list = new HashMap<Integer, String>();
                            JSONArray distributor = obj.getJSONArray("distributor");
                            //Toast.makeText(context,""+distributor.length(),Toast.LENGTH_LONG).show();
                            for(int i=0;i<distributor.length();i++) {
                                JSONObject data1 = distributor.getJSONObject(i);
                                String distributor_name = data1.getString("distributor_name");
                                int distributor_id = Integer.parseInt(data1.getString("id"));
                                //mylist.add(distributor_name);
                                list.put(distributor_id,distributor_name);

                            }
                            int count=0;
                            int post=0;
                            for (Map.Entry<Integer, String> entry : list.entrySet()) {
                                Integer key = entry.getKey();
                                String value = entry.getValue();
                                count+=1;
                                if(value.contains(distributorName)){
                                    post=count-1;
                                }
                                /* Build the StringWithTag List using these keys and values. */
                                mylist.add(new StringWithTag(value, key));
                            }
                            // Do nothing but close the dialog
                            Spinner s_retailer = route.findViewById(R.id.spinner3);

                            ArrayAdapter<StringWithTag> adapter = new ArrayAdapter<StringWithTag>(context, android.R.layout.simple_spinner_item, mylist)
                            {
                                public View getView(int position, View convertView, android.view.ViewGroup parent) {
                                    View v = (TextView) super.getView(position, convertView, parent);
                                    tfavv = Typeface.createFromAsset(getAssets(),"AvenirNextLTPro-Regular.otf");
                                    ((TextView)v).setTypeface(tfavv);
                                    v.setPadding(5,10,5,10);
                                    //v.setTextColor(Color.RED);
                                    //v.setTextSize(35);
                                    return v;
                                }


                                public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                                    View v = (TextView) super.getView(position, convertView, parent);
                                    tfavv = Typeface.createFromAsset(getAssets(),"AvenirNextLTPro-Regular.otf");
                                    ((TextView)v).setTypeface(tfavv);
                                    v.setPadding(5,10,5,10);
                                    //v.setTextColor(Color.RED);
                                    //v.setTextSize(35);
                                    return v;
                                }
                            };
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            s_retailer.setAdapter(adapter);
                            s_retailer.setSelection(post);
                            s_retailer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {

                                    try {
                                        View_load_dialog.showDialog();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                try{
                                                    StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                                                    Integer key = (Integer) swt.tag;

                                                    distributor_id_new=""+key;

                                                    final ArrayList<StringWithTag> mylist1 = new ArrayList<StringWithTag>();

                                                    HashMap<Integer,String> list1 = new HashMap<Integer, String>();

                                                    String response = getbeantname(""+key);
                                                    JSONArray obj = new JSONArray(response);
                                                    for (int i=0;i<obj.length();i++) {
                                                        JSONObject beatplan = obj.getJSONObject(i);
                                                        String beat_id=beatplan.getString("beat_id");
                                                        String beat_name=beatplan.getString("beat_name");
                                                        int bid=Integer.parseInt(beatplan.getString("id"));
                                                        list1.put(bid,beat_id+"-"+beat_name);
                                                    }

                                                    for (Map.Entry<Integer, String> entry : list1.entrySet()) {
                                                        Integer key1 = entry.getKey();
                                                        String value1 = entry.getValue();

                                                        /* Build the StringWithTag List using these keys and values. */
                                                        mylist1.add(new StringWithTag(value1, key1));
                                                    }



                                                    final Spinner s_beatplan = route.findViewById(R.id.spinner4);

                                                    ArrayAdapter<StringWithTag> adapter = new ArrayAdapter<StringWithTag>(context, android.R.layout.simple_spinner_item, mylist1)
                                                    {
                                                        public View getView(int position, View convertView, android.view.ViewGroup parent) {
                                                            tfavv = Typeface.createFromAsset(getAssets(),"AvenirNextLTPro-Regular.otf");
                                                            TextView v = (TextView) super.getView(position, convertView, parent);
                                                            v.setTypeface(tfavv);
                                                            //v.setTextColor(Color.RED);
                                                            //v.setTextSize(35);
                                                            return v;
                                                        }
                                                        public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                                                            tfavv = Typeface.createFromAsset(getAssets(),"AvenirNextLTPro-Regular.otf");
                                                            TextView v = (TextView) super.getView(position, convertView, parent);
                                                            v.setTypeface(tfavv);
                                                            //v.setTextColor(Color.RED);
                                                            //v.setTextSize(35);
                                                            return v;
                                                        }
                                                    };
                                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                    s_beatplan.setAdapter(adapter);

                                                    s_beatplan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                        @Override
                                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                            new_beat_event_purpose=s_beatplan.getSelectedItem().toString();
                                                            StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                                                            Integer key2 = (Integer) swt.tag;
                                                            beatplan_id_new=""+key2;
                                                        }
                                                        @Override
                                                        public void onNothingSelected(AdapterView<?> parent) { }
                                                    });
                                                }catch(Exception e){}
                                               View_load_dialog.hideDialog();
                                            }
                                        }, 150);

                                    }
                                    catch (Exception ex) { }
                                    //doSomethingWith(mylist.get(key)mylist.get(key));
                                    //Toast.makeText(context,""+key,Toast.LENGTH_LONG).show();
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                        catch (Exception ex) {
                            //Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
                        }
                        helpBuilder.show();
                        View_load_dialog.hideDialog();
                    }
                },250);


        route.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        try{

                            final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

                            if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                                showSettingsAlert();
                            }else{
                                if(beatplan_id_old!=beatplan_id_new){

                                    View_load_dialog.showDialog();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                setbeantname(reporting_manager_id, distributor_id_old, beatplan_id_old, distributor_id_new, beatplan_id_new);
                                            }catch(Exception e){

                                            }
                                            View_load_dialog.hideDialog();
                                        }
                                    }, 550);

                                    if(reporting_manager_id!=null && !reporting_manager_id.equals(0) && !reporting_manager_id.equals("null")){
                                        showPopUp3();
                                        //helpBuilder.dismiss();
                                    }
                                    else {
                                        View_load_dialog.showDialog();

                                        new Handler().postDelayed(new Runnable() {
                                            public void run() {
                                                //your method code
                                                Intent intent = new Intent(context, dashboardActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivityForResult(intent, 1);
                                                overridePendingTransition(0, 0);
                                            }
                                        },1000);
                                    }


                                    int oldnum=old_beat_event_purpose.indexOf('-');
                                    int newnum=new_beat_event_purpose.indexOf('-');
                                    final String date = new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());
                                    JSONObject jsonObject=new JSONObject();


                                    final String[] lat = {"0.0"};
                                    final String[] longi = {"0.0"};

                                    View_load_dialog.showDialog();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            GPSTracker gpsTracker=new GPSTracker(context);
                                            lat[0] =gpsTracker.getLatitude()+"";
                                            longi[0] =gpsTracker.getLongitude()+"";

                                            View_load_dialog.hideDialog();
                                        }
                                    }, 2000);
                                    View_load_dialog.hideDialog();


                                    jsonObject.put("latitude", lat[0]);
                                    jsonObject.put("longitude", longi[0]);
                                    jsonObject.put("old_beatplan", old_beat_event_purpose.substring(oldnum+1));
                                    jsonObject.put("new_beatplan", new_beat_event_purpose.substring(newnum+1));
                                    final String event_json=jsonObject.toString();
                                    OfflineDbHelper offlineDbHelper = new OfflineDbHelper(context);
                                    SQLiteDatabase db = offlineDbHelper.getWritableDatabase();
                                    offlineDbHelper.addEVENT("Changed beatplan", date, event_json, db);
                                    Log.e("dash_db", "event-routeplan added to db");
                                    postActvityDetails("Changed beatplan",date, event_json);


                                } else {

                                    View_load_dialog.showDialog();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                setoriginalbeatplan(reporting_manager_id,distributor_id_old,beatplan_id_old,distributor_id_new,beatplan_id_new);
                                            }catch(Exception e){

                                            }
                                            View_load_dialog.hideDialog();
                                        }
                                    }, 500);

                                    String resp = setoriginalbeatplan(reporting_manager_id,distributor_id_old,beatplan_id_old,distributor_id_new,beatplan_id_new);

                                    View_load_dialog.showDialog();

                                    new Handler().postDelayed(new Runnable() {
                                        public void run() {
                                            //your method code
                                            Intent i = new Intent(context,Visit_list_pageActivity.class);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivityForResult(i,1);
                                        }
                                    },500);
                                }
                            }


                        }
                        catch (Exception ex) {
                            //Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
        route.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpBuilder.dismiss();
                    }
                });

        // Remember, create doesn't show the dialog
        //AlertDialog helpDialog = helpBuilder.create();

        helpBuilder.getWindow().setLayout(150, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        helpBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


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
                /*GPSTracker gpsTracker = new GPSTracker(context);
                if (gpsTracker.getIsGPSTrackingEnabled()) {
                String stringLatitude = String.valueOf(gpsTracker.latitude);
                Toast.makeText(context,stringLatitude,Toast.LENGTH_LONG).show();        */
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                deleteDialognew.dismiss();
                restartflag4=true;
                startActivity(intent);
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
                deleteDialognew.dismiss();
                finish();
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

    public String setoriginalbeatplan(String reporting_manager_id, String distributor_id_og, String beat_id_og, String distributor_id,String beatplan_id)  throws UnsupportedEncodingException
    {
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        String sales_rep_id=session.getsales_rep_id();
        String session_id=session.getSessionid();

        String data = URLEncoder.encode("reporting_manager_id", "UTF-8")
                + "=" + URLEncoder.encode(reporting_manager_id, "UTF-8");

        data += "&" + URLEncoder.encode("distributor_id_og", "UTF-8")
                + "=" + URLEncoder.encode(distributor_id_og, "UTF-8");

        data += "&" + URLEncoder.encode("beat_id_og", "UTF-8")
                + "=" + URLEncoder.encode(beat_id_og, "UTF-8");

        data += "&" + URLEncoder.encode("distributor_id", "UTF-8")
                + "=" + URLEncoder.encode(distributor_id, "UTF-8");

        data += "&" + URLEncoder.encode("beat_id", "UTF-8")
                + "=" + URLEncoder.encode(beatplan_id, "UTF-8");

        data += "&" + URLEncoder.encode("sales_rep_id", "UTF-8")
                + "=" + URLEncoder.encode(sales_rep_id, "UTF-8");

        data += "&" + URLEncoder.encode("session_id", "UTF-8")
                + "=" + URLEncoder.encode(session_id, "UTF-8");

        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/dashboard_mobile_app/set_original_beat_plan_api");

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
            //Toast.makeText(getContext(),"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
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

    private void showPopUp3() {

        AlertDialog.Builder helpBuilder;
        helpBuilder = new AlertDialog.Builder(context);

        LayoutInflater inflater = getLayoutInflater();
        final View route = inflater.inflate(R.layout.activity_contact_manager, null);
        helpBuilder.setView(route);

        helpBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            Intent intent = new Intent(context, dashboardActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                        }
                        catch (Exception ex) {
                            //Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(helpDialog.getWindow().getAttributes());

        int dialogWindowWidth = (int) (displayWidth * 0.7f);
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        helpDialog.getWindow().setAttributes(layoutParams);
        helpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

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
            Log.e("test", text);
            return text;
        }
        catch(Exception ex)
        {
            Log.e("checkout dash", "error: "+ex.toString());
            //Toast.makeText(context,"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        }
        finally
        {
            try
            {

                reader.close();
            }

            catch(Exception ex) {
                Log.e("checkout dash", "error1: "+ex.toString());
            }
        }

        // Show response on activity
        return "";

    }


    public String  getretailer()  throws UnsupportedEncodingException
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
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/dashboard_mobile_app/get_dashboard_data_api");

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
                Log.e("dash", "postactivitydetails: "+ex.toString());
                //Toast.makeText(context,"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
            }
            finally {
                try {

                    reader.close();
                } catch (Exception ex) {
                    Log.e("dash", "postactivitydetails2: "+ex.toString());
                }
            }
    }

    public String  getbeantname(String distributor_id)  throws UnsupportedEncodingException
    {
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        //String sales_rep_id=session.getsales_rep_id();
        String data = URLEncoder.encode("distributor_id", "UTF-8")
                + "=" + URLEncoder.encode(distributor_id, "UTF-8");


        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/dashboard_mobile_app/get_beat_plan_api");

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



    public String setbeantname(String reporting_manager_id, String distributor_id_og, String beat_id_og, String distributor_id,String beatplan_id)  throws UnsupportedEncodingException
    {
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        String sales_rep_id=session.getsales_rep_id();
        String session_id=session.getSessionid();

        String data = URLEncoder.encode("reporting_manager_id", "UTF-8")
                + "=" + URLEncoder.encode(reporting_manager_id, "UTF-8");

        data += "&" + URLEncoder.encode("distributor_id_og", "UTF-8")
                + "=" + URLEncoder.encode(distributor_id_og, "UTF-8");

        data += "&" + URLEncoder.encode("beat_id_og", "UTF-8")
                + "=" + URLEncoder.encode(beat_id_og, "UTF-8");

        data += "&" + URLEncoder.encode("distributor_id", "UTF-8")
                + "=" + URLEncoder.encode(distributor_id, "UTF-8");

        data += "&" + URLEncoder.encode("beat_id", "UTF-8")
                + "=" + URLEncoder.encode(beatplan_id, "UTF-8");

        data += "&" + URLEncoder.encode("sales_rep_id", "UTF-8")
                + "=" + URLEncoder.encode(sales_rep_id, "UTF-8");

        data += "&" + URLEncoder.encode("session_id", "UTF-8")
                + "=" + URLEncoder.encode(session_id, "UTF-8");

        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/dashboard_mobile_app/set_beat_plan_api");

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
            Toast.makeText(context,"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
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


    public String changebeatplan(String pending_id, String status)  throws UnsupportedEncodingException
    {
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        String sales_rep_id=session.getsales_rep_id();
        String session_id=session.getSessionid();

        String data = URLEncoder.encode("pending_id", "UTF-8")
                + "=" + URLEncoder.encode(pending_id, "UTF-8");

        data += "&" + URLEncoder.encode("status", "UTF-8")
                + "=" + URLEncoder.encode(status, "UTF-8");

        data += "&" + URLEncoder.encode("session_id", "UTF-8")
                + "=" + URLEncoder.encode(session_id, "UTF-8");

        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/dashboard_mobile_app/approve_beat_plan_api");

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




    public String getdashdata()  throws UnsupportedEncodingException
    {
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server
        String sales_rep_id=session.getsales_rep_id();
        String session_id = session.getSessionid();
        String data = URLEncoder.encode("sales_rep_id", "UTF-8")
                + "=" + URLEncoder.encode(sales_rep_id, "UTF-8");

        data += "&" + URLEncoder.encode("session_id", "UTF-8")
                + "=" + URLEncoder.encode(session_id, "UTF-8");


        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/dashboard_mobile_app/get_visit_details_api");

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


    private static class StringWithTag {
        public String string;
        public Object tag;

        public StringWithTag(String string, Integer tag) {
            this.string = string;
            this.tag = tag;
        }

        @Override
        public String toString() {
            return string;
        }
    }

    public class MyAsyncTask extends AsyncTask<String, Integer, String> {

        Context context;
        Dialog dialog;
        ProgressBar progressBar;
        TextView txt_loading;
        Typeface face;

        MyAsyncTask(Context context){
            this.context=context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // create dialog
            dialog=new Dialog(context);
            dialog.setCancelable(true);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.alertdialog_progressbar);
            txt_loading=dialog.findViewById(R.id.txt_loading);
            face = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Demi.otf");
            txt_loading.setTypeface(face);
            progressBar=dialog.findViewById(R.id.determinateBar);
            progressBar.setProgress(0);
            dialog.setCancelable(false);
            if(session.getmon_frag().equals("")) {
                dialog.show();
                dialogflag=true;
            }
            else{View_load_dialog.showDialog();}
           // View_load_dialog.showDialog();
        }
        @Override
        protected String doInBackground(String... params) {

            try {
                resultglobal=getdashdata();
                resultglobal1=getdashdata1();
                if(dialogflag) {
                    Calendar calendar0 = Calendar.getInstance();
                    Date date0 = calendar0.getTime();
                    String t = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date0.getTime());
                    String response = "";
                    response = session.getmon_frag();
                    if (response.equals("")) {
                        publishProgress(1);
                        session.setmon_frag(getbeat("Monday", 1));
                        Log.e("dash", "mon loaded");
                    }
                    response = session.gettues_frag();
                    if (response.equals("")) {
                        publishProgress(14);
                        session.settues_frag(getbeat("Tuesday", 14));
                        Log.e("dash", "tue loaded");
                    }
                    response = session.getwed_frag();
                    if (response.equals("")) {
                        publishProgress(28);
                        session.setwed_frag(getbeat("Wednesday", 28));
                        Log.e("dash", "wed loaded");
                    }
                    response = session.getthu_frag();
                    if (response.equals("")) {
                        publishProgress(42);
                        session.setthu_frag(getbeat("Thursday", 42));
                        Log.e("dash", "thu loaded");
                    }
                    response = session.getfri_frag();
                    if (response.equals("")) {
                        publishProgress(56);
                        session.setfri_frag(getbeat("Friday", 56));
                        Log.e("dash", "fri loaded");
                    }
                    response = session.getsat_frag();
                    if (response.equals("")) {
                        publishProgress(71);
                        session.setsat_frag(getbeat("Saturday", 71));
                        Log.e("dash", "sat loaded");
                    }
                    response = session.getsun_frag();
                    if (response.equals("")) {
                        publishProgress(85);
                        session.setsun_frag(getbeat("Sunday", 85));
                        Log.e("dash", "sun loaded");
                    }
                }

            } catch (UnsupportedEncodingException e) {
                Log.e("dash12", e.getMessage());
            }
            return "";
        }

        @Override
        protected void onProgressUpdate(Integer ... progress) {
            super.onProgressUpdate(progress);
            Log.e("what", "pro: "+progress[0]);
            progressBar.setProgress(progress[0]);

            //pb.setProgress(progress[0]);
            //View_load_dialog.showDialog();
            //Toast.makeText(dashboardActivity.this, "command sent" +progress[0],
            //Toast.LENGTH_LONG).show();

        }

        @Override
        protected void onPostExecute(String result) {
            try{
            if(!resultglobal.equals("")) {
                String respdata = resultglobal;
                JSONArray obj = new JSONArray(respdata);
                JSONObject jsonObject = obj.getJSONObject(0);

                String unplanned_count = jsonObject.getString("unplanned_count");
                String p_call = jsonObject.getString("p_call");
                String actual_count = jsonObject.getString("actual_count");
                String total_count = jsonObject.getString("total_count");
                Log.e("visit", "UP:"+unplanned_count+" PC:"+p_call+" AC:"+actual_count+" TC:"+total_count);
                //Toast.makeText(context,total_count, Toast.LENGTH_LONG).show();
                OrdersData.actual_count=actual_count;
                OrdersData.p_call=p_call;
                OrdersData.unplanned_count=unplanned_count;
                OrdersData.total_count=total_count;

                int actcnt = Integer.parseInt(actual_count.trim()) + Integer.parseInt(unplanned_count.trim());
                actual_count = "" + actcnt;
                if (actcnt <= 0) {
                    btnchange.setVisibility(View.VISIBLE);
                } else {
                    btnchange.setVisibility(View.GONE);
                }

                int totalcnt = Integer.parseInt(total_count.trim()) + Integer.parseInt(unplanned_count.trim());
                total_count = "" + totalcnt;

                txtvisits.setText(actual_count + " / " + total_count);
                txtcalls.setText(p_call + " / " + actual_count);
                txtucalls.setText(unplanned_count);
                //Toast.makeText(context,""+total_count,Toast.LENGTH_LONG).show();

                int act_cnt = Integer.parseInt(actual_count.trim());
                int tot_cnt = Integer.parseInt(total_count.trim());
                int act_perc = Math.round(((float) act_cnt / (float) tot_cnt) * 100);
                txtvper.setText("" + act_perc + "%");


                if (!p_call.equals("0") && !actual_count.equals("0")) {
                    int pcall = Integer.parseInt(p_call.trim());
                    int actp_cnt = Integer.parseInt(actual_count.trim());
                    int actp_perc = Math.round(((float) pcall / (float) actp_cnt) * 100);
                    txtcper.setText("" + actp_perc + "%");
                    //Toast.makeText(context,""+actual_count,Toast.LENGTH_LONG).show();
                } else {
                    txtcper.setText("0%");
                }
            }
            if(!resultglobal1.equals("")) {
                String respdata1 = resultglobal1;
                JSONArray obj1 = new JSONArray(respdata1);
                JSONObject jsonObject1 = obj1.getJSONObject(0);
                String bar_cnt = jsonObject1.getString("bar_cnt");
                String box_cnt = jsonObject1.getString("box_cnt");
                String cookies_cnt = jsonObject1.getString("cookies_cnt");
                String trailmix_cnt = jsonObject1.getString("trailmix_cnt");


                Log.e("dash", "bar: "+bar_cnt+" box: "+box_cnt+" cook: "+cookies_cnt+" trail: "+trailmix_cnt);
                if (bar_cnt.equals("0") && box_cnt.equals("0")) {
                    txtbars.setText("0");
                    OrdersData.order_bars="0";
                } else if (bar_cnt.equals("0") && !box_cnt.equals("0")) {
                    //Toast.makeText(context, box_cnt, Toast.LENGTH_LONG).show();
                    int barcount = Integer.parseInt(box_cnt) * 6;
                    txtbars.setText("" + barcount);
                    OrdersData.order_bars=barcount+"";
                } else if (!bar_cnt.equals("0") && box_cnt.equals("0")) {
                    txtbars.setText(bar_cnt);
                    OrdersData.order_bars=bar_cnt+"";
                } else {
                    //Toast.makeText(context, box_cnt+"in else "+bar_cnt, Toast.LENGTH_LONG).show();
                    int barcount = (Integer.parseInt(box_cnt) * 6) + Integer.parseInt(bar_cnt);
                    txtbars.setText("" + barcount);
                    OrdersData.order_bars=barcount+"";
                }


                if (cookies_cnt == null) {
                    txtcookies.setText("0");
                } else {
                    txtcookies.setText(cookies_cnt);
                    OrdersData.order_cookies=cookies_cnt+"";
                }


                if (trailmix_cnt == null) {
                    txttrailmix.setText("0");
                } else {
                    txttrailmix.setText(trailmix_cnt);
                    OrdersData.order_trailmix=trailmix_cnt+"";
                }
            }
            if(dialogflag){
            progressBar.setProgress(100);
            dialog.dismiss();
            }else{View_load_dialog.hideDialog();}
           // View_load_dialog.hideDialog();
            }catch (Exception e){
                progressBar.setProgress(100);
                dialog.dismiss();
                //View_load_dialog.hideDialog();
                Log.e("dash11", e.toString());
            }
        }

        public String  getbeat(String day, int progress)  throws UnsupportedEncodingException {
            // Get user defined values
            //Name = fname.getText().toString();

            // Create data variable for sent values to server


            String sales_rep_id=session.getsales_rep_id();
            String data = URLEncoder.encode("sales_rep_id", "UTF-8")
                    + "=" + URLEncoder.encode(sales_rep_id, "UTF-8");

            data += "&" + URLEncoder.encode("frequency", "UTF-8")
                    + "=" + URLEncoder.encode(day, "UTF-8");

            data += "&" + URLEncoder.encode("temp_date", "UTF-8")
                    + "=" + URLEncoder.encode(day, "UTF-8");

            String text = "";
            BufferedReader reader=null;

            publishProgress(progress+3);
            // Send data
            try
            {

                // Defined URL  where to send data
                URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/Sales_rep_store_plan_mobile_app/checkstatus_api");

                // Send POST data request

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write( data );
                wr.flush();

                publishProgress(progress+6);
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

                publishProgress(progress+9);

                text = sb.toString();

                publishProgress(progress+13);
                return text;
            }
            catch(Exception ex)
            {
                //Toast.makeText(getContext(),"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
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
        public String getdashdata()  throws UnsupportedEncodingException {
            // Get user defined values
            //Name = fname.getText().toString();

            // Create data variable for sent values to server

            String sales_rep_id=session.getsales_rep_id();
            String session_id = session.getSessionid();
            String data = URLEncoder.encode("sales_rep_id", "UTF-8")
                    + "=" + URLEncoder.encode(sales_rep_id, "UTF-8");

            data += "&" + URLEncoder.encode("session_id", "UTF-8")
                    + "=" + URLEncoder.encode(session_id, "UTF-8");


            String text = "";
            BufferedReader reader=null;

            // Send data
            try
            {

                // Defined URL  where to send data
                URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/dashboard_mobile_app/get_visit_details_api");

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
        public String getdashdata1()  throws UnsupportedEncodingException {
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
                URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/dashboard_mobile_app/get_order_details_api");

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






}
