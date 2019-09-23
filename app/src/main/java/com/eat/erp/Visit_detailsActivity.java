package com.eat.erp;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Visit_detailsActivity extends AppCompatActivity implements LocationListener {
    private final static int ALL_PERMISSIONS_RESULT = 101;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    ScrollView mScrollView;
    session session;
    Context context;
    String respglobal = "";
    String respglobaledit = "";
    MenuItem nav_checkintime;
    MenuItem nav_checkouttime;
    ExpandableListAdapter1 listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Button btnDatePicker, btnTimePicker, btnsave, btncancel_btn, btncheckpoqty, btnbatchdetails;
    EditText txtDate, txtTime, txtretailer, txtremarks, txtretailer1;
    Spinner channel, type;
    LinearLayout type_linear, type_label_linear, llarea, llrelation, llretailer, llspinner, lllabel, lllblarea, lllblrelation, lllblretailer, lllbltype, llsplocation;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Spinner zone, area, location, retailer, relation;
    TextView txtdate, txtdate1;
    TextView channel1, type1, zone1, area1, relation1, location1, retailer1;
    TextView zone1_id, area1_id, relation1_id, location1_id, retailer1_id, txttitle;
    TextView date_label, type_label, type_label1, channel_label, channel_label1, remarks_label, relation_label, relation_label1, area_label, area_label1, zone_label1, zone_label, location_label, retailer_label, retailer_label1, location_label1, date_label1;
    int flag, vflag, saveFlag_duplicate;
    String id = "", bit_plan_id = "", sequence = "", mid = "", sales_rep_loc_id = "", merchandiser_stock_id = "", follow_type = "";
    ProgressDialog spinnerDialog;
    public static Activity fa;
    Typeface face;
    LinearLayout llhome, llrouteplan, llorder, llaccount;
    View_load_dialog View_load_dialog;
    LocationManager locationManager;
    Location loc;
    ArrayList<String> permissions = new ArrayList<>();
    ArrayList<String> permissionsToRequest;
    ArrayList<String> permissionsRejected = new ArrayList<>();
    boolean isGPS = false;
    boolean isNetwork = false;
    boolean canGetLocation = true;
    Spinner po_number_spn;
    String type_global = "";
    String zone_id_val = "", area_id_val = "", store_id_val = "", location_id_val = "";
    View po_qty_button;
    LinearLayout llpending_po, llorange_bar, llorange_bar_qty, llorange_bar_diff, llbutterscotch_bar, llbutterscotch_bar_qty, llbutterscotch_bar_diff,
            llchocopeanut_bar, llchocopeanut_bar_qty, llchocopeanut_bar_diff, llbambaiyachaat_bar, llbambaiyachaat_bar_qty, llbambaiyachaat_bar_diff,
            llmangoginger_bar, llmangoginger_bar_qty, llmangoginger_bar_diff, llberry_blast_bar, llberry_blast_bar_qty, llberry_blast_bar_diff,
            llchyawanprash_bar, llchyawanprash_bar_qty, llchyawanprash_bar_diff, llorange_box, llorange_box_qty, llorange_box_diff, llbutterscotch_box, llbutterscotch_box_qty,
            llbutterscotch_box_diff, llchocopeanut_box, llchocopeanut_box_qty, llchocopeanut_box_diff, llbambaiyachaat_box,
            llbambaiyachaat_box_qty, llbambaiyachaat_box_diff, llmangoginger_box, llmangoginger_box_qty, llmangoginger_box_diff,
            llberry_blast_box, llberry_blast_box_qty, llberry_blast_box_diff, llchyawanprash_box, llchyawanprash_box_qty, llchyawanprash_box_diff,
            llvariety_box, llvariety_box_qty, llvariety_box_diff, llchocolate_cookies, llchocolate_cookies_qty, llchocolate_cookies_diff,
            lldark_chocolate_cookies, lldark_chocolate_cookies_qty, lldark_chocolate_cookies_diff, llcranberry_cookies, llcranberry_cookies_qty, llcranberry_cookies_diff,
            llcranberry_orange_zest, llcranberry_orange_zest_qty, llcranberry_orange_zest_diff, llfig_raisins, llfig_raisins_qty, llfig_raisins_diff,
            llpapaya_pineapple, llpapaya_pineapple_qty, llpapaya_pineapple_diff;
    TextView orange_bar_qty, orange_bar_diff, butterscotch_bar_qty, butterscotch_bar_diff, chocopeanut_bar_qty, chocopeanut_bar_diff,
            bambaiyachaat_bar_qty, bambaiyachaat_bar_diff, mangoginger_bar_qty, mangoginger_bar_diff, berry_blast_bar_qty, berry_blast_bar_diff,
            chyawanprash_bar_qty, chyawanprash_bar_diff, orange_box_qty, orange_box_diff, butterscotch_box_qty, butterscotch_box_diff,
            chocopeanut_box_qty, chocopeanut_box_diff, bambaiyachaat_box_qty, bambaiyachaat_box_diff, mangoginger_box_qty, mangoginger_box_diff,
            berry_blast_box_qty, berry_blast_box_diff, chyawanprash_box_qty, chyawanprash_box_diff, variety_box_qty, variety_box_diff,
            chocolate_cookies_qty, chocolate_cookies_diff, dark_chocolate_cookies_qty, dark_chocolate_cookies_diff,
            cranberry_cookies_qty, cranberry_cookies_diff, cranberry_orange_zest_qty, cranberry_orange_zest_diff,
            fig_raisins_qty, fig_raisins_diff, papaya_pineapple_qty, papaya_pineapple_diff;
    EditText orange_bar, butterscotch_bar, chocopeanut_bar, bambaiyachaat_bar, mangoginger_bar, berry_blast_bar, chyawanprash_bar,
            orange_box, butterscotch_box, chocopeanut_box, bambaiyachaat_box, mangoginger_box, berry_blast_box, chyawanprash_box, variety_box,
            chocolate_cookies, dark_chocolate_cookies, cranberry_cookies, cranberry_orange_zest, fig_raisins, papaya_pineapple;
    Integer intpo_id = 0, intorange_bar_qty = 0, intorange_bar = 0, intorange_bar_diff = 0, intbutterscotch_bar_qty = 0, intbutterscotch_bar = 0, intbutterscotch_bar_diff = 0,
            intchocopeanut_bar_qty = 0, intchocopeanut_bar = 0, intchocopeanut_bar_diff = 0, intbambaiyachaat_bar_qty = 0, intbambaiyachaat_bar = 0, intbambaiyachaat_bar_diff = 0,
            intmangoginger_bar_qty = 0, intmangoginger_bar = 0, intmangoginger_bar_diff = 0, intberry_blast_bar_qty = 0, intberry_blast_bar = 0, intberry_blast_bar_diff = 0,
            intchyawanprash_bar_qty = 0, intchyawanprash_bar = 0, intchyawanprash_bar_diff = 0, intorange_box_qty = 0, intorange_box = 0, intorange_box_diff = 0,
            intbutterscotch_box_qty = 0, intbutterscotch_box = 0, intbutterscotch_box_diff = 0, intchocopeanut_box_qty = 0, intchocopeanut_box = 0, intchocopeanut_box_diff = 0,
            intbambaiyachaat_box_qty = 0, intbambaiyachaat_box = 0, intbambaiyachaat_box_diff = 0, intmangoginger_box_qty = 0, intmangoginger_box = 0, intmangoginger_box_diff = 0,
            intberry_blast_box_qty = 0, intberry_blast_box = 0, intberry_blast_box_diff = 0, intchyawanprash_box_qty = 0, intchyawanprash_box = 0, intchyawanprash_box_diff = 0,
            intvariety_box_qty = 0, intvariety_box = 0, intvariety_box_diff = 0, intchocolate_cookies_qty = 0, intchocolate_cookies = 0, intchocolate_cookies_diff = 0,
            intdark_chocolate_cookies_qty = 0, intdark_chocolate_cookies = 0, intdark_chocolate_cookies_diff = 0,
            intcranberry_cookies_qty = 0, intcranberry_cookies = 0, intcranberry_cookies_diff = 0,
            intcranberry_orange_zest_qty = 0, intcranberry_orange_zest = 0, intcranberry_orange_zest_diff = 0, intfig_raisins_qty = 0, intfig_raisins = 0, intfig_raisins_diff = 0,
            intpapaya_pineapple_qty = 0, intpapaya_pineapple = 0, intpapaya_pineapple_diff = 0;
    TextView mismatch_po_nos;
    String text = "";
    String strorange_bar = "0", strbutterscotch_bar = "0", strchocopeanut_bar = "0", strbambaiyachaat_bar = "0", strmangoginger_bar = "0";
    String strberry_blast_bar = "0", strchyawanprash_bar = "0", strorange_box = "0", strbutterscotch_box = "0", strchocopeanut_box = "0";
    String strbambaiyachaat_box = "0", strmangoginger_box = "0", strberry_blast_box = "0", strchyawanprash_box = "0", strvariety_box = "0";
    String strchocolate_cookies = "0", strdark_chocolate_cookies = "0", strcranberry_cookies = "0", strcranberry_orange_zest = "0";
    String strfig_raisins = "0", strpapaya_pineapple = "0";
    String strsales_rep_id, strchannel_type = "", strdistributor_type = "", strstore_id = "", strdistributor_id = "", strzone_id = "";
    String strarea_id = "", strlocation_id = "", strdistributor_name = "", strremarks = "", strreation_id = "", stris_permanent = "";
    int countarea = 0, countlocation = 0, countrelation = 0, countzone = 0;

    LinearLayout llcheckin, llcheckout;
    Button btnchange, btnpending, btnapproved_route;
    TextView txtcheckout;
    boolean restartflag = false;
    boolean restartflag2 = false;
    boolean restartflag3 = false, restartflag4;
    boolean zero_inventory_flag = false;
    int logos[] = {R.drawable.option_checkout, R.drawable.option_new_visit, R.drawable.option_old_visit, R.drawable.option_timeline};
    Button fab_btn;
    RelativeLayout container_details;
    boolean flagpressed;
    private FusedLocationProviderClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_details);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        context = Visit_detailsActivity.this;
        fa = this;
        countarea = 0;
        countlocation = 0;
        countrelation = 0;
        countzone = 0;
        btnpending = findViewById(R.id.pending);
        btnapproved_route = findViewById(R.id.approved_route);
        btnchange = (Button) findViewById(R.id.change);
        llcheckin = (LinearLayout) findViewById(R.id.llcheckin);
        llcheckout = (LinearLayout) findViewById(R.id.llcheckout);
        txtcheckout = (TextView) findViewById(R.id.txtcheckout);
        restartflag = false;
        restartflag2 = false;
        restartflag3 = false;
        restartflag4 = false;
        flagpressed = false;
        zero_inventory_flag = false;
        Bundle bundle = getIntent().getExtras();
        String fabtype = bundle.getString("fabtype");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        face = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Demi.otf");
        Typeface face1 = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Regular.otf");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        View_load_dialog = new View_load_dialog(this);
        txttitle = (TextView) findViewById(R.id.txttitle);
        txttitle.setText("Visit Details");
        mScrollView = findViewById(R.id.mScrollView);

        TextView todaydate = (TextView) findViewById(R.id.todaydate);
        Calendar c1 = Calendar.getInstance();
        String dayNumberSuffix = dashboardActivity.getDayNumberSuffix(c1.get(Calendar.DAY_OF_MONTH));
        Date date3 = Calendar.getInstance().getTime();
        SimpleDateFormat timeFormatter3 = new SimpleDateFormat("dd'" + dayNumberSuffix + "' MMM yyyy");
        String time3 = timeFormatter3.format(date3);
        todaydate.setText(time3 + ", Today");

        date_label = (TextView) findViewById(R.id.date_label);
        date_label1 = (TextView) findViewById(R.id.date_label1);
        channel_label = (TextView) findViewById(R.id.channel_label);
        channel_label1 = (TextView) findViewById(R.id.channel_label1);
        type_label = (TextView) findViewById(R.id.type_label);
        type_label1 = (TextView) findViewById(R.id.type_label1);
        remarks_label = (TextView) findViewById(R.id.remarks_label);
        relation_label = (TextView) findViewById(R.id.relation_label);
        relation_label1 = (TextView) findViewById(R.id.relation_label1);
        area_label = (TextView) findViewById(R.id.area_label);
        area_label1 = (TextView) findViewById(R.id.area_label1);
        zone_label1 = (TextView) findViewById(R.id.zone_label1);
        zone_label = (TextView) findViewById(R.id.zone_label);
        location_label = (TextView) findViewById(R.id.location_label);
        location_label1 = (TextView) findViewById(R.id.location_label1);
        retailer_label = (TextView) findViewById(R.id.retailer_label);
        retailer_label1 = (TextView) findViewById(R.id.retailer_label1);
        llarea = (LinearLayout) findViewById(R.id.llarea);
        llsplocation = (LinearLayout) findViewById(R.id.llsplocation);
        llrelation = (LinearLayout) findViewById(R.id.llrelation);
        llretailer = (LinearLayout) findViewById(R.id.llretailer);

        llarea.setVisibility(View.GONE);
        llsplocation.setVisibility(View.GONE);
        llretailer.setVisibility(View.GONE);
        llrelation.setVisibility(View.GONE);

        OrdersData.latitude = "0";
        OrdersData.longitude = "0";

        date_label.setTypeface(face);
        date_label1.setTypeface(face);
        type_label.setTypeface(face);
        type_label1.setTypeface(face);
        channel_label.setTypeface(face);
        channel_label1.setTypeface(face);
        remarks_label.setTypeface(face);
        relation_label.setTypeface(face);
        relation_label1.setTypeface(face);
        area_label.setTypeface(face);
        area_label1.setTypeface(face);
        zone_label1.setTypeface(face);
        zone_label.setTypeface(face);
        location_label.setTypeface(face);
        location_label1.setTypeface(face);
        retailer_label.setTypeface(face);
        retailer_label1.setTypeface(face);

        /*DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);*/
        llhome = (LinearLayout) findViewById(R.id.llhome);
        llrouteplan = (LinearLayout) findViewById(R.id.llrouteplan);
        llorder = (LinearLayout) findViewById(R.id.llorder);
        llaccount = (LinearLayout) findViewById(R.id.llaccount);
        llpending_po = (LinearLayout) findViewById(R.id.llpending_po);

        session = new session(context);

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
            try {
                if(OrdersData.batch_no_json==null){
                    OrdersData.batch_no_json=getbatchnos();
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            client = LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(Visit_detailsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Visit_detailsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                    if (location != null) {
                        Toast.makeText(context, "Location found", Toast.LENGTH_SHORT).show();
                        OrdersData.latitude = location.getLatitude() + "";
                        OrdersData.longitude = location.getLongitude() + "";
                    }
                }
            });


            //Toast.makeText(context,OrdersData.latitude+OrdersData.longitude,Toast.LENGTH_LONG).show();
        }

        //Log.e("locatuon", OrdersData.latitude+" "+OrdersData.longitude);

        View_load_dialog = new View_load_dialog(this);

        fab_btn = findViewById(R.id.fab_btn);
        container_details = findViewById(R.id.container_details);

        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flagpressed) {
                    flagpressed = false;
                    container_details.removeViewAt(container_details.getChildCount() - 2);
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
        /*Menu menu = navigationView.getMenu();
        nav_checkintime = menu.findItem(R.id.nav_checkintime);
        nav_checkouttime = menu.findItem(R.id.nav_checkouttime);*/

        String emailid = session.getusername();
        if (emailid.equals("")) {
            Intent i = new Intent(context, MainActivity.class);
            startActivity(i);
        } else {
            try {

                Button btnfollowup = (Button) findViewById(R.id.follow_up);
                btnfollowup.setTypeface(face);
                Button btnplaceorder = (Button) findViewById(R.id.place_order);
                btnplaceorder.setTypeface(face);
                btnbatchdetails = findViewById(R.id.batch_details);
                btnbatchdetails.setTypeface(face);
                btnsave = (Button) findViewById(R.id.btnsave);
                btncancel_btn = (Button) findViewById(R.id.cancel_btn);
                btncheckpoqty = (Button) findViewById(R.id.btncheckpoqty);

                type_linear = (LinearLayout) findViewById(R.id.type_linear);
                type_label_linear = (LinearLayout) findViewById(R.id.type_label_linear);
                llarea = (LinearLayout) findViewById(R.id.llarea);
                llrelation = (LinearLayout) findViewById(R.id.llrelation);
                llretailer = (LinearLayout) findViewById(R.id.llretailer);
                llspinner = (LinearLayout) findViewById(R.id.llspinner);
                lllabel = (LinearLayout) findViewById(R.id.lllabel);
                lllblarea = (LinearLayout) findViewById(R.id.lllblarea);
                lllblrelation = (LinearLayout) findViewById(R.id.lllblrelation);
                lllblretailer = (LinearLayout) findViewById(R.id.lllblretailer);
                lllbltype = (LinearLayout) findViewById(R.id.lllbltype);

                txtretailer = (EditText) findViewById(R.id.txtretailer);
                txtremarks = (EditText) findViewById(R.id.txtremarks);
                txtretailer1 = (EditText) findViewById(R.id.txtretailer1);


                channel = (Spinner) findViewById(R.id.channel);
                type = (Spinner) findViewById(R.id.type);
                area = (Spinner) findViewById(R.id.area);
                zone = findViewById(R.id.zone);
                location = findViewById(R.id.location);
                retailer = findViewById(R.id.retailer);
                relation = findViewById(R.id.relation);
                expListView = (ExpandableListView) findViewById(R.id.product_entry);
                expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                        return parent.isGroupExpanded(groupPosition);
                    }
                });

                channel1 = (TextView) findViewById(R.id.channel1);
                type1 = (TextView) findViewById(R.id.type1);
                zone1 = (TextView) findViewById(R.id.zone1);
                area1 = (TextView) findViewById(R.id.area1);
                relation1 = (TextView) findViewById(R.id.relation1);
                location1 = (TextView) findViewById(R.id.location1);
                retailer1 = (TextView) findViewById(R.id.retailer1);

                zone1_id = (TextView) findViewById(R.id.zone1_id);
                area1_id = (TextView) findViewById(R.id.area1_id);
                relation1_id = (TextView) findViewById(R.id.relation1_id);
                location1_id = (TextView) findViewById(R.id.location1_id);
                retailer1_id = (TextView) findViewById(R.id.retailer1_id);
                area.setEnabled(false);
                location.setEnabled(false);
                retailer.setEnabled(false);

                txtdate = (TextView) findViewById(R.id.txtdate);
                txtdate1 = (TextView) findViewById(R.id.txtdate1);
                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);

                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = df.format(c);
                txtdate.setText(formattedDate);
                txtdate1.setText(formattedDate);

                btncancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OrdersData od = new OrdersData();
                        od.clear();
                        View_load_dialog.showDialog();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(context, Visit_list_pageActivity.class);
                                startActivity(intent);
                            }
                        }, 50);
                        finish();
                    }
                });


                String[] myChannel = {"GT", "MT"};

                ArrayAdapter<String> adapterchannel = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, myChannel) {
                    public View getView(int position, View convertView, android.view.ViewGroup parent) {
                        View v = (TextView) super.getView(position, convertView, parent);
                        Typeface tfavv = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Regular.otf");
                        ((TextView) v).setTypeface(tfavv);
                        v.setPadding(15, 10, 5, 10);
                        //v.setTextColor(Color.RED);
                        //v.setTextSize(35);
                        return v;
                    }
                };
                adapterchannel.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                channel.setAdapter(adapterchannel);


               /* if(OrdersData.channel_type!="" || OrdersData.channel_type!=null) {
                    channel.setSelection(adapterchannel.getPosition(OrdersData.channel_type));
                    //Toast.makeText(context,OrdersData.channel_type,Toast.LENGTH_LONG).show();
                }*/

                channel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String swt = (String) parent.getItemAtPosition(position);
                        String stype = type.getSelectedItem().toString();
                        //Toast.makeText(context,swt,Toast.LENGTH_LONG).show();
                        if (swt.equals("GT") && stype.equals("Old")) {
                            btnbatchdetails.setVisibility(View.VISIBLE);
                            //Toast.makeText(context, "this is IF in channel", Toast.LENGTH_LONG).show();
                            llrelation.setVisibility(View.GONE);
                            try {
                                type.setVisibility(View.VISIBLE);
                                type_label_linear.setVisibility(View.VISIBLE);
                                type_linear.setVisibility(View.VISIBLE);
                                final ArrayList<StringWithTag> mylist = new ArrayList<StringWithTag>();
                                String responsetext = getzone(swt);
                                //JSONObject obj = new JSONObject(responsetext);
                                HashMap<Integer, String> list = new HashMap<Integer, String>();

                                JSONArray data = new JSONArray(responsetext);
                                //list.put(0,"Select");
                                //Toast.makeText(getContext(),""+distributor.length(),Toast.LENGTH_LONG).show();
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject data1 = data.getJSONObject(i);
                                    String zone = data1.getString("zone");
                                    int zone_id = Integer.parseInt(data1.getString("zone_id"));
                                    //mylist.add(distributor_name);
                                    list.put(zone_id, zone);

                                }

                                mylist.add(new StringWithTag("Select", 0));

                                for (Map.Entry<Integer, String> entry : list.entrySet()) {
                                    Integer key = entry.getKey();
                                    String value = entry.getValue();

                                    /* Build the StringWithTag List using these keys and values. */
                                    mylist.add(new StringWithTag(value, key));
                                }
                                // Do nothing but close the dialog

                                ArrayAdapter<StringWithTag> adapter = new ArrayAdapter<StringWithTag>(context, android.R.layout.simple_spinner_item, mylist) {
                                    public View getView(int position, View convertView, android.view.ViewGroup parent) {
                                        View v = (TextView) super.getView(position, convertView, parent);
                                        Typeface tfavv = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Regular.otf");
                                        ((TextView) v).setTypeface(tfavv);
                                        v.setPadding(15, 10, 5, 10);
                                        //v.setTextColor(Color.RED);
                                        //v.setTextSize(35);
                                        return v;
                                    }


                                    public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                                        View v = (TextView) super.getView(position, convertView, parent);
                                        Typeface tfavv = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Regular.otf");
                                        ((TextView) v).setTypeface(tfavv);
                                        v.setPadding(15, 10, 5, 10);
                                        //v.setTextColor(Color.RED);
                                        //v.setTextSize(35);
                                        return v;
                                    }
                                };
                                adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                                zone.setAdapter(adapter);
                            } catch (Exception ex) {
                                //Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
                            }
                        } else if (swt.equals("MT")) {
                            //Toast.makeText(context, "this is else if in channel", Toast.LENGTH_LONG).show();
                            llarea.setVisibility(View.GONE);
                            llretailer.setVisibility(View.GONE);
                            expListView.setVisibility(View.VISIBLE);
                            btnbatchdetails.setVisibility(View.VISIBLE);
                            try {
                                type.setVisibility(View.GONE);
                                type_linear.setVisibility(View.GONE);
                                type_label_linear.setVisibility(View.GONE);
                                final ArrayList<StringWithTag> mylist = new ArrayList<StringWithTag>();
                                String responsetext = getzone(swt);
                                //JSONObject obj = new JSONObject(responsetext);
                                HashMap<Integer, String> list = new HashMap<Integer, String>();
                                JSONArray data = new JSONArray(responsetext);
                                //list.put(0,"Select");
                                //Toast.makeText(getContext(),""+distributor.length(),Toast.LENGTH_LONG).show();
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject data1 = data.getJSONObject(i);
                                    String zone = data1.getString("zone");
                                    int zone_id = Integer.parseInt(data1.getString("zone_id"));
                                    //mylist.add(distributor_name);
                                    list.put(zone_id, zone);
                                }
                                mylist.add(new StringWithTag("Select", 0));

                                for (Map.Entry<Integer, String> entry : list.entrySet()) {
                                    Integer key = entry.getKey();
                                    String value = entry.getValue();

                                    /* Build the StringWithTag List using these keys and values. */
                                    mylist.add(new StringWithTag(value, key));
                                }
                                // Do nothing but close the dialog


                                ArrayAdapter<StringWithTag> adapter = new ArrayAdapter<StringWithTag>(context, android.R.layout.simple_spinner_item, mylist) {
                                    public View getView(int position, View convertView, android.view.ViewGroup parent) {
                                        View v = (TextView) super.getView(position, convertView, parent);
                                        Typeface tfavv = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Regular.otf");
                                        ((TextView) v).setTypeface(tfavv);
                                        v.setPadding(15, 10, 5, 10);
                                        //v.setTextColor(Color.RED);
                                        //v.setTextSize(35);
                                        return v;
                                    }

                                    public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                                        View v = (TextView) super.getView(position, convertView, parent);
                                        Typeface tfavv = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Regular.otf");
                                        ((TextView) v).setTypeface(tfavv);
                                        v.setPadding(15, 10, 5, 10);
                                        //v.setTextColor(Color.RED);
                                        //v.setTextSize(35);
                                        return v;
                                    }
                                };
                                adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                                zone.setAdapter(adapter);
                            } catch (Exception ex) {
                                //Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
                            }
                        } else {
                            llrelation.setVisibility(View.GONE);
                            try {
                                type.setVisibility(View.VISIBLE);
                                type_label_linear.setVisibility(View.VISIBLE);
                                type_linear.setVisibility(View.VISIBLE);
                                final ArrayList<StringWithTag> mylist = new ArrayList<StringWithTag>();
                                String responsetext = getzone(swt);
                                //JSONObject obj = new JSONObject(responsetext);
                                HashMap<Integer, String> list = new HashMap<Integer, String>();

                                JSONArray data = new JSONArray(responsetext);
                                //list.put(0,"Select");
                                //Toast.makeText(getContext(),""+distributor.length(),Toast.LENGTH_LONG).show();
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject data1 = data.getJSONObject(i);
                                    String zone = data1.getString("zone");
                                    int zone_id = Integer.parseInt(data1.getString("zone_id"));
                                    //mylist.add(distributor_name);
                                    list.put(zone_id, zone);

                                }

                                mylist.add(new StringWithTag("Select", 0));

                                for (Map.Entry<Integer, String> entry : list.entrySet()) {
                                    Integer key = entry.getKey();
                                    String value = entry.getValue();

                                    /* Build the StringWithTag List using these keys and values. */
                                    mylist.add(new StringWithTag(value, key));
                                }
                                // Do nothing but close the dialog

                                ArrayAdapter<StringWithTag> adapter = new ArrayAdapter<StringWithTag>(context, android.R.layout.simple_spinner_item, mylist) {
                                    public View getView(int position, View convertView, android.view.ViewGroup parent) {
                                        View v = (TextView) super.getView(position, convertView, parent);
                                        Typeface tfavv = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Regular.otf");
                                        ((TextView) v).setTypeface(tfavv);
                                        v.setPadding(15, 10, 5, 10);
                                        //v.setTextColor(Color.RED);
                                        //v.setTextSize(35);
                                        return v;
                                    }


                                    public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                                        View v = (TextView) super.getView(position, convertView, parent);
                                        Typeface tfavv = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Regular.otf");
                                        ((TextView) v).setTypeface(tfavv);
                                        v.setPadding(15, 10, 5, 10);
                                        //v.setTextColor(Color.RED);
                                        //v.setTextSize(35);
                                        return v;
                                    }
                                };
                                adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                                zone.setAdapter(adapter);
                            } catch (Exception ex) {
                                //Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
                            }

                            retailer.setVisibility(View.GONE);
                            txtretailer.setVisibility(View.VISIBLE);
                            expListView.setVisibility(View.GONE);
                            btnbatchdetails.setVisibility(View.GONE);
                        }

                    }


                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                String[] myType = {"Old", "New"};

                ArrayAdapter<String> adaptertype = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, myType) {
                    public View getView(int position, View convertView, android.view.ViewGroup parent) {
                        View v = (TextView) super.getView(position, convertView, parent);
                        Typeface tfavv = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Regular.otf");
                        ((TextView) v).setTypeface(tfavv);
                        v.setPadding(15, 10, 5, 10);
                        //v.setTextColor(Color.RED);
                        //v.setTextSize(35);
                        return v;
                    }

                };

                adaptertype.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                type.setAdapter(adaptertype);


                int spinnerPosition = adaptertype.getPosition(fabtype);
                type.setSelection(spinnerPosition);
                type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String stype = type.getSelectedItem().toString();
                        String channeltype = channel.getSelectedItem().toString();
                        if (stype.equals("Old")) {
                            retailer.setVisibility(View.VISIBLE);
                            txtretailer.setVisibility(View.GONE);
                            expListView.setVisibility(View.VISIBLE);
                            btnbatchdetails.setVisibility(View.VISIBLE);
                        } else if (stype.equals("New") && channeltype.equals("GT")) {
                            //Toast.makeText(context, "this is else if in type", Toast.LENGTH_LONG).show();
                            retailer.setVisibility(View.GONE);
                            txtretailer.setVisibility(View.VISIBLE);
                            expListView.setVisibility(View.GONE);
                            btnbatchdetails.setVisibility(View.GONE);
                        } else {
                            //Toast.makeText(context, "this is only else in type", Toast.LENGTH_LONG).show();
                            retailer.setVisibility(View.GONE);
                            txtretailer.setVisibility(View.VISIBLE);
                            expListView.setVisibility(View.GONE);
                            btnbatchdetails.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                zone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
                        countzone += 1;
                        if (countzone >= 2) {
                            try {
                                llsplocation.setVisibility(View.GONE);
                                llretailer.setVisibility(View.GONE);
                                View_load_dialog.showDialog();

                                new Handler().postDelayed(new Runnable() {
                                    public void run() {

                                        StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                                        Integer key2 = (Integer) swt.tag;
                                        String key1 = "" + key2;

                                        String schannel = channel.getSelectedItem().toString();
                                        if (schannel.equals("GT")) {
                                            //Toast.makeText(context,key1,Toast.LENGTH_LONG).show();
                                            try {
                                                String response = getarea(key1);
                                                final ArrayList<StringWithTag1> mylist_area = new ArrayList<StringWithTag1>();
                                                //JSONObject obj = new JSONObject(responsetext);
                                                HashMap<Integer, String> list_area = new HashMap<Integer, String>();

                                                JSONArray data = new JSONArray(response);
                                                //list_area.put(0, "Select");
                                                //Toast.makeText(getContext(),""+distributor.length(),Toast.LENGTH_LONG).show();
                                                for (int i = 0; i < data.length(); i++) {
                                                    JSONObject data1 = data.getJSONObject(i);
                                                    String area = data1.getString("area");
                                                    int area_id = Integer.parseInt(data1.getString("area_id"));
                                                    //mylist.add(distributor_name);
                                                    list_area.put(area_id, area);

                                                }

                                                mylist_area.add(new StringWithTag1("Select", 0));

                                                for (Map.Entry<Integer, String> entry : list_area.entrySet()) {
                                                    Integer key = entry.getKey();
                                                    String value = entry.getValue();

                                                    /* Build the StringWithTag List using these keys and values. */
                                                    mylist_area.add(new StringWithTag1(value, key));
                                                }
                                                // Do nothing but close the dialog


                                                ArrayAdapter<StringWithTag1> adapter_area = new ArrayAdapter<StringWithTag1>(context, android.R.layout.simple_spinner_item, mylist_area) {
                                                    public View getView(int position, View convertView, android.view.ViewGroup parent) {
                                                        View v = (TextView) super.getView(position, convertView, parent);
                                                        Typeface tfavv = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Regular.otf");
                                                        ((TextView) v).setTypeface(tfavv);
                                                        v.setPadding(15, 10, 5, 10);
                                                        //v.setTextColor(Color.RED);
                                                        //v.setTextSize(35);
                                                        return v;
                                                    }


                                                    public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                                                        View v = (TextView) super.getView(position, convertView, parent);
                                                        Typeface tfavv = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Regular.otf");
                                                        ((TextView) v).setTypeface(tfavv);
                                                        v.setPadding(15, 10, 5, 10);
                                                        //v.setTextColor(Color.RED);
                                                        //v.setTextSize(35);
                                                        return v;
                                                    }
                                                };
                                                adapter_area.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                area.setAdapter(adapter_area);
                                                get_po_nos();
                                                if (key2 != 0) {
                                                    llarea.setVisibility(View.VISIBLE);
                                                    area.setEnabled(true);
                                                    View_load_dialog.hideDialog();
                                                } else {
                                                    area.setEnabled(false);
                                                    View_load_dialog.hideDialog();
                                                }

                                            } catch (Exception ex) {
                                                //Toast.makeText(context, ex.toString(), Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            try {
                                                String response = getrelation(key1);
                                                final ArrayList<StringWithTag4> mylist_relation = new ArrayList<StringWithTag4>();
                                                //JSONObject obj = new JSONObject(responsetext);
                                                HashMap<Integer, String> list_relation = new HashMap<Integer, String>();

                                                JSONArray data = new JSONArray(response);
                                                //list_relation.put(0, "Select");
                                                //Toast.makeText(getContext(),""+distributor.length(),Toast.LENGTH_LONG).show();
                                                for (int i = 0; i < data.length(); i++) {
                                                    JSONObject data1 = data.getJSONObject(i);
                                                    String relation = data1.getString("store_name");
                                                    int relation_id = Integer.parseInt(data1.getString("store_id"));
                                                    //mylist.add(distributor_name);
                                                    list_relation.put(relation_id, relation);

                                                }

                                                mylist_relation.add(new StringWithTag4("Select", 0));

                                                for (Map.Entry<Integer, String> entry : list_relation.entrySet()) {
                                                    Integer key = entry.getKey();
                                                    String value = entry.getValue();

                                                    /* Build the StringWithTag List using these keys and values. */
                                                    mylist_relation.add(new StringWithTag4(value, key));
                                                }
                                                // Do nothing but close the dialog


                                                ArrayAdapter<StringWithTag4> adapter_relation = new ArrayAdapter<StringWithTag4>(context, android.R.layout.simple_spinner_item, mylist_relation) {
                                                    public View getView(int position, View convertView, android.view.ViewGroup parent) {
                                                        View v = (TextView) super.getView(position, convertView, parent);
                                                        Typeface tfavv = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Regular.otf");
                                                        ((TextView) v).setTypeface(tfavv);
                                                        v.setPadding(15, 10, 5, 10);
                                                        //v.setTextColor(Color.RED);
                                                        //v.setTextSize(35);
                                                        return v;
                                                    }


                                                    public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                                                        View v = (TextView) super.getView(position, convertView, parent);
                                                        Typeface tfavv = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Regular.otf");
                                                        ((TextView) v).setTypeface(tfavv);
                                                        v.setPadding(15, 10, 5, 10);
                                                        //v.setTextColor(Color.RED);
                                                        //v.setTextSize(35);
                                                        return v;
                                                    }
                                                };
                                                adapter_relation.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                                                relation.setAdapter(adapter_relation);
                                                get_po_nos();
                                                if (key2 != 0) {
                                                    llrelation.setVisibility(View.VISIBLE);
                                                    relation.setEnabled(true);
                                                    View_load_dialog.hideDialog();
                                                } else {
                                                    relation.setEnabled(false);
                                                    View_load_dialog.hideDialog();
                                                }


                                            } catch (Exception ex) {
                                                //Toast.makeText(context, ex.toString(), Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        zone_id_val = key2.toString();


                                        View_load_dialog.hideDialog();
                                    }


                                }, 50);

                            } catch (Exception ex) {
                                //Toast.makeText(context, ex.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
                        countarea += 1;
                        if (countarea >= 2) {
                            View_load_dialog.showDialog();
                            llretailer.setVisibility(View.GONE);
                            //Toast.makeText(context, ""+countarea, Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    // Toast.makeText(context, "gd", Toast.LENGTH_LONG);
                                    StringWithTag1 swt = (StringWithTag1) parent.getItemAtPosition(position);
                                    Integer key2 = (Integer) swt.tag;
                                    String key1 = "" + key2;
                                    StringWithTag swt4 = (StringWithTag) zone.getSelectedItem();
                                    Integer zoneid = (Integer) swt4.tag;
                                    String szoneid = "" + zoneid;
                                    //Toast.makeText(context,szoneid,Toast.LENGTH_LONG).show();
                                    try {
                                        String response = getlocation(szoneid, key1);
                                        final ArrayList<StringWithTag2> mylist_location = new ArrayList<StringWithTag2>();
                                        //JSONObject obj = new JSONObject(responsetext);
                                        HashMap<Integer, String> list_location = new HashMap<Integer, String>();

                                        JSONArray data = new JSONArray(response);
                                        //list_location.put(0,"Select");
                                        //Toast.makeText(getContext(),""+distributor.length(),Toast.LENGTH_LONG).show();
                                        for (int i = 0; i < data.length(); i++) {
                                            JSONObject data1 = data.getJSONObject(i);
                                            String location = data1.getString("location");
                                            int location_id = Integer.parseInt(data1.getString("id"));
                                            //mylist.add(distributor_name);
                                            list_location.put(location_id, location);

                                        }

                                        mylist_location.add(new StringWithTag2("Select", 0));
                                        for (Map.Entry<Integer, String> entry : list_location.entrySet()) {
                                            Integer key = entry.getKey();
                                            String value = entry.getValue();

                                            /* Build the StringWithTag List using these keys and values. */
                                            mylist_location.add(new StringWithTag2(value, key));
                                        }
                                        // Do nothing but close the dialog


                                        ArrayAdapter<StringWithTag2> adapter_location = new ArrayAdapter<StringWithTag2>(context, android.R.layout.simple_spinner_item, mylist_location) {
                                            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                                                View v = (TextView) super.getView(position, convertView, parent);
                                                Typeface tfavv = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Regular.otf");
                                                ((TextView) v).setTypeface(tfavv);
                                                v.setPadding(15, 10, 5, 10);
                                                //v.setTextColor(Color.RED);
                                                //v.setTextSize(35);
                                                return v;
                                            }


                                            public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                                                View v = (TextView) super.getView(position, convertView, parent);
                                                Typeface tfavv = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Regular.otf");
                                                ((TextView) v).setTypeface(tfavv);
                                                v.setPadding(15, 10, 5, 10);
                                                //v.setTextColor(Color.RED);
                                                //v.setTextSize(35);
                                                return v;
                                            }
                                        };
                                        adapter_location.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        location.setAdapter(adapter_location);
                                        get_po_nos();
                                        if (key2 != 0) {
                                            llsplocation.setVisibility(View.VISIBLE);
                                            location.setEnabled(true);
                                            View_load_dialog.hideDialog();
                                        } else {
                                            location.setEnabled(false);
                                            View_load_dialog.hideDialog();
                                        }


                                    } catch (Exception ex) {
                                        Toast.makeText(context, "exc: " + ex.toString(), Toast.LENGTH_LONG).show();
                                    }
                                    View_load_dialog.hideDialog();
                                }


                            }, 50);

                        }
                    }


                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
                        countlocation += 1;
                        if (countlocation >= 2) {

                            View_load_dialog.showDialog();
                            //Toast.makeText(context, "location selected", Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    try {


                                        StringWithTag2 swt = (StringWithTag2) parent.getItemAtPosition(position);
                                        Integer key2 = (Integer) swt.tag;
                                        String key1 = "" + key2;
                                        StringWithTag swt4 = (StringWithTag) zone.getSelectedItem();
                                        Integer zoneid = (Integer) swt4.tag;
                                        String szoneid = "" + zoneid;

                                        StringWithTag1 swt5;
                                        String sareaid = "";
                                        if (channel.getSelectedItem().equals("GT")) {
                                            swt5 = (StringWithTag1) area.getSelectedItem();
                                            Integer areaid = (Integer) swt5.tag;
                                            sareaid = "" + areaid;
                                        }

                                        String dtype = type.getSelectedItem().toString();

                                        String response = getretailer(szoneid, sareaid, key1, dtype);
                                        final ArrayList<StringWithTag3> mylist_retailer = new ArrayList<StringWithTag3>();
                                        //JSONObject obj = new JSONObject(responsetext);
                                        HashMap<String, String> list_retailer = new HashMap<String, String>();

                                        JSONArray data = new JSONArray(response);
                                        //list_retailer.put("d_0","Select");
                                        //Toast.makeText(getContext(),""+distributor.length(),Toast.LENGTH_LONG).show();
                                        for (int i = 0; i < data.length(); i++) {
                                            JSONObject data1 = data.getJSONObject(i);
                                            String retailer = data1.getString("distributor_name");
                                            String retailer_id = data1.getString("id");
                                            //mylist.add(distributor_name);
                                            list_retailer.put(retailer_id, retailer);

                                        }

                                        mylist_retailer.add(new StringWithTag3("Select", "d_0"));
                                        for (Map.Entry<String, String> entry : list_retailer.entrySet()) {
                                            String key = entry.getKey();
                                            String value = entry.getValue();


                                            mylist_retailer.add(new StringWithTag3(value, key));
                                        }

                                        Collections.sort(mylist_retailer, new Comparator<StringWithTag3>() {
                                            public int compare(StringWithTag3 a1, StringWithTag3 a2) {
                                                return a1.tag.toString().compareToIgnoreCase(a2.tag.toString());
                                            }
                                        });
                                        // Do nothing but close the dialog


                                        ArrayAdapter<StringWithTag3> adapter_retailer = new ArrayAdapter<StringWithTag3>(context, android.R.layout.simple_spinner_item, mylist_retailer) {
                                            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                                                View v = (TextView) super.getView(position, convertView, parent);
                                                Typeface tfavv = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Regular.otf");
                                                ((TextView) v).setTypeface(tfavv);
                                                v.setPadding(15, 10, 5, 10);
                                                //v.setTextColor(Color.RED);
                                                //v.setTextSize(35);
                                                return v;
                                            }


                                            public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                                                View v = (TextView) super.getView(position, convertView, parent);
                                                Typeface tfavv = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Regular.otf");
                                                ((TextView) v).setTypeface(tfavv);
                                                v.setPadding(15, 10, 5, 10);
                                                //v.setTextColor(Color.RED);
                                                //v.setTextSize(35);
                                                return v;
                                            }
                                        };
                                        adapter_retailer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        retailer.setAdapter(adapter_retailer);

                                        location_id_val = key2.toString();
                                        get_po_nos();
                                        if (key2 != 0 && channel.getSelectedItem().equals("GT")) {
                                            retailer.setEnabled(true);
                                            llretailer.setVisibility(View.VISIBLE);
                                            View_load_dialog.hideDialog();
                                        } else {
                                            retailer.setEnabled(false);
                                            View_load_dialog.hideDialog();
                                        }
                                    } catch (Exception ex) {
                                        //Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
                                    }
                                    View_load_dialog.hideDialog();
                                }
                            }, 50);

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                relation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
                        countrelation += 1;
                        if (countrelation >= 2) {
                            View_load_dialog.showDialog();
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    try {
                                        //Toast.makeText(context, "retailer selected", Toast.LENGTH_SHORT).show();

                                        StringWithTag4 swt = (StringWithTag4) parent.getItemAtPosition(position);
                                        Integer key2 = (Integer) swt.tag;
                                        String key1 = "" + key2;

                                        StringWithTag swt4 = (StringWithTag) zone.getSelectedItem();
                                        Integer zoneid = (Integer) swt4.tag;
                                        String szoneid = "" + zoneid;

                                        String response = getlocationdata(szoneid, key1);
                                        final ArrayList<StringWithTag2> mylist_locationdata = new ArrayList<StringWithTag2>();
                                        //JSONObject obj = new JSONObject(responsetext);
                                        HashMap<Integer, String> list_locationdata = new HashMap<Integer, String>();

                                        JSONArray data = new JSONArray(response);
                                        //list_locationdata.put(0,"Select");
                                        //Toast.makeText(getContext(),""+distributor.length(),Toast.LENGTH_LONG).show();
                                        for (int i = 0; i < data.length(); i++) {
                                            JSONObject data1 = data.getJSONObject(i);
                                            String locationdata = data1.getString("location");
                                            int location_id = Integer.parseInt(data1.getString("location_id"));
                                            //mylist.add(distributor_name);
                                            list_locationdata.put(location_id, locationdata);

                                        }


                                        mylist_locationdata.add(new StringWithTag2("Select", 0));
                                        for (Map.Entry<Integer, String> entry : list_locationdata.entrySet()) {
                                            int key = entry.getKey();
                                            String value = entry.getValue();


                                            mylist_locationdata.add(new StringWithTag2(value, key));
                                        }


                                        ArrayAdapter<StringWithTag2> adapter_locationdata = new ArrayAdapter<StringWithTag2>(context, android.R.layout.simple_spinner_item, mylist_locationdata) {
                                            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                                                View v = (TextView) super.getView(position, convertView, parent);
                                                Typeface tfavv = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Regular.otf");
                                                ((TextView) v).setTypeface(tfavv);
                                                v.setPadding(15, 10, 5, 10);
                                                //v.setTextColor(Color.RED);
                                                //v.setTextSize(35);
                                                return v;
                                            }


                                            public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                                                View v = (TextView) super.getView(position, convertView, parent);
                                                Typeface tfavv = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Regular.otf");
                                                ((TextView) v).setTypeface(tfavv);
                                                v.setPadding(15, 10, 5, 10);
                                                //v.setTextColor(Color.RED);
                                                //v.setTextSize(35);
                                                return v;
                                            }
                                        };
                                        adapter_locationdata.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        location.setAdapter(adapter_locationdata);

                                        store_id_val = key2.toString();
                                        get_po_nos();
                                        if (key2 != 0) {
                                            llsplocation.setVisibility(View.VISIBLE);
                                            location.setEnabled(true);
                                            View_load_dialog.hideDialog();
                                        } else {
                                            location.setEnabled(false);
                                            View_load_dialog.hideDialog();
                                        }

                                    } catch (Exception ex) {
                                        //Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
                                    }
                                    View_load_dialog.hideDialog();
                                }
                            }, 50);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                btnfollowup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Log.e("retailer name",retailer1.getText().toString());
                        showPopUp();
                    }

                });

                btnbatchdetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnbatchdetails.setEnabled(false);
                        View view = expListView.getChildAt(1);
                        EditText orange_bar_editText = view.findViewById(R.id.lblListItem2);
                        strorange_bar = orange_bar_editText.getText().toString();

                        view = expListView.getChildAt(2);
                        EditText butterscotch_bar_editText = view.findViewById(R.id.lblListItem2);
                        strbutterscotch_bar = butterscotch_bar_editText.getText().toString();

                        view = expListView.getChildAt(3);
                        EditText chocopeanut_bar_editText = view.findViewById(R.id.lblListItem2);
                        strchocopeanut_bar = chocopeanut_bar_editText.getText().toString();

                        view = expListView.getChildAt(4);
                        EditText bambaiyachaat_bar_editText = view.findViewById(R.id.lblListItem2);
                        strbambaiyachaat_bar = bambaiyachaat_bar_editText.getText().toString();

                        view = expListView.getChildAt(5);
                        EditText mangoginger_bar_editText = view.findViewById(R.id.lblListItem2);
                        strmangoginger_bar = mangoginger_bar_editText.getText().toString();

                        view = expListView.getChildAt(6);
                        EditText berry_blast_bar_editText = view.findViewById(R.id.lblListItem2);
                        strberry_blast_bar = berry_blast_bar_editText.getText().toString();

                        view = expListView.getChildAt(7);
                        EditText chyawanprash_bar_editText = view.findViewById(R.id.lblListItem2);
                        strchyawanprash_bar = chyawanprash_bar_editText.getText().toString();

                        view = expListView.getChildAt(9);
                        EditText orange_box_editText = view.findViewById(R.id.lblListItem2);
                        strorange_box = orange_box_editText.getText().toString();

                        view = expListView.getChildAt(10);
                        EditText butterscotch_box_editText = view.findViewById(R.id.lblListItem2);
                        strbutterscotch_box = butterscotch_box_editText.getText().toString();

                        view = expListView.getChildAt(11);
                        EditText chocopeanut_box_editText = view.findViewById(R.id.lblListItem2);
                        strchocopeanut_box = chocopeanut_box_editText.getText().toString();

                        view = expListView.getChildAt(12);
                        EditText bambaiyachaat_box_editText = view.findViewById(R.id.lblListItem2);
                        strbambaiyachaat_box = bambaiyachaat_box_editText.getText().toString();

                        view = expListView.getChildAt(13);
                        EditText mangoginger_box_editText = view.findViewById(R.id.lblListItem2);
                        strmangoginger_box = mangoginger_box_editText.getText().toString();

                        view = expListView.getChildAt(14);
                        EditText berry_blast_box_editText = view.findViewById(R.id.lblListItem2);
                        strberry_blast_box = berry_blast_box_editText.getText().toString();

                        view = expListView.getChildAt(15);
                        EditText chyawanprash_box_editText = view.findViewById(R.id.lblListItem2);
                        strchyawanprash_box = chyawanprash_box_editText.getText().toString();

                        view = expListView.getChildAt(16);
                        EditText variety_box_editText = view.findViewById(R.id.lblListItem2);
                        strvariety_box = variety_box_editText.getText().toString();

                        view = expListView.getChildAt(18);
                        EditText chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
                        strchocolate_cookies = chocolate_cookies_editText.getText().toString();

                        view = expListView.getChildAt(19);
                        EditText dark_chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
                        strdark_chocolate_cookies = dark_chocolate_cookies_editText.getText().toString();

                        view = expListView.getChildAt(20);
                        EditText cranberry_cookies_editText = view.findViewById(R.id.lblListItem2);
                        strcranberry_cookies = cranberry_cookies_editText.getText().toString();

                        view = expListView.getChildAt(22);
                        EditText cranberry_orange_zest_editText = view.findViewById(R.id.lblListItem2);
                        strcranberry_orange_zest = cranberry_orange_zest_editText.getText().toString();

                        view = expListView.getChildAt(23);
                        EditText fig_raisins_editText = view.findViewById(R.id.lblListItem2);
                        strfig_raisins = fig_raisins_editText.getText().toString();

                        view = expListView.getChildAt(24);
                        EditText papaya_pineapple_editText = view.findViewById(R.id.lblListItem2);
                        strpapaya_pineapple = papaya_pineapple_editText.getText().toString();

                        if ((strorange_bar.equals("") || strorange_bar.equals("0")) && (strorange_box.equals("") || strorange_box.equals("0")) && (strbutterscotch_bar.equals("") || strbutterscotch_bar.equals("0")) && (strbutterscotch_box.equals("") || strbutterscotch_box.equals("0")) && (strchocopeanut_bar.equals("") || strchocopeanut_bar.equals("0")) && (strchocopeanut_box.equals("") || strchocopeanut_box.equals("0")) && (strbambaiyachaat_bar.equals("") || strbambaiyachaat_bar.equals("0")) && (strbambaiyachaat_box.equals("") || strbambaiyachaat_box.equals("0")) && (strmangoginger_bar.equals("") || strmangoginger_bar.equals("0")) && (strmangoginger_box.equals("") || strmangoginger_box.equals("0")) && (strberry_blast_bar.equals("") || strberry_blast_bar.equals("0")) && (strberry_blast_box.equals("") || strberry_blast_box.equals("0")) && (strchyawanprash_bar.equals("") || strchyawanprash_bar.equals("0")) && (strchyawanprash_box.equals("") || strchyawanprash_box.equals("0")) && (strvariety_box.equals("") || strvariety_box.equals("0")) && (strchocolate_cookies.equals("") || strchocolate_cookies.equals("0")) && (strdark_chocolate_cookies.equals("") || strdark_chocolate_cookies.equals("0")) && (strcranberry_cookies.equals("") || strcranberry_cookies.equals("0")) && (strfig_raisins.equals("") || strfig_raisins.equals("0")) && (strpapaya_pineapple.equals("") || strpapaya_pineapple.equals("0")) && (strcranberry_orange_zest.equals("") || strcranberry_orange_zest.equals("0"))) {
                            showPopUp6();
                            btnbatchdetails.setEnabled(true);
                        } else {

                            showPopUp5(strorange_bar, strorange_box, strbutterscotch_bar, strbutterscotch_box, strchocopeanut_bar, strchocopeanut_box, strbambaiyachaat_bar, strbambaiyachaat_box, strmangoginger_bar, strmangoginger_box, strberry_blast_bar, strberry_blast_box, strchyawanprash_bar, strchyawanprash_box, strvariety_box, strchocolate_cookies, strdark_chocolate_cookies, strcranberry_cookies, strfig_raisins, strpapaya_pineapple, strcranberry_orange_zest);
                            btnbatchdetails.setEnabled(true);
                        }
                    }
                });

                btnplaceorder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopUp1();
                    }

                });
                prepareListData();

                listAdapter = new ExpandableListAdapter1(this, listDataHeader, listDataChild);
                expListView.setAdapter(listAdapter);
                expandAll();
                //Log.e("visitd", "start");
                Intent intent = getIntent();
                if (intent.hasExtra("type")) {
                    Log.e("visit", "type_global: " + type_global);
                    type_global = intent.getStringExtra("type");
                    if (intent.getStringExtra("type").equals("add")) {
                        OrdersData od = new OrdersData();
                        od.clear();
                    }
                    String type = intent.getStringExtra("type");
                    flag = 0;
                    saveFlag_duplicate = 1;
                    //String type = intent.getStringExtra("type");
                    //if(type.equals("add"))
                    llspinner.setVisibility(View.GONE);
                    lllabel.setVisibility(View.VISIBLE);
                    String channel_type = intent.getStringExtra("channel_type");
                    follow_type = intent.getStringExtra("follow_type");
                    //Toast.makeText(context,type,Toast.LENGTH_LONG).show();
                    if (channel_type.equals("GT")) {
                        lllblarea.setVisibility(View.VISIBLE);
                        lllblretailer.setVisibility(View.VISIBLE);
                        lllbltype.setVisibility(View.VISIBLE);
                        lllblrelation.setVisibility(View.GONE);
                        channel1.setText("GT");
                        //type1.setText("Old");
                        //getaddapi();
                    } else {
                        lllblarea.setVisibility(View.GONE);
                        lllblretailer.setVisibility(View.GONE);
                        lllbltype.setVisibility(View.GONE);
                        lllblrelation.setVisibility(View.VISIBLE);
                        channel1.setText("MT");
                        //type1.setText("Old");
                    }

                    if (type.equals("add")) {
                        try {
                            isNetwork = isNETWORK();
                            if (!isNetwork) {
                                showSettingsAlert();
                                getLastLocation();
                            } else {
                                Log.e("visit1", "add type");
                                new MyAsyncTask().execute();
                            }
                        } catch (Exception ex) {
                            // Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
                        }
                    } else {
                        try {
                            if (!isNetwork) {
                                showSettingsAlert();
                                getLastLocation();
                            } else {
                                Log.e("visit1", "edit type");
                                new MyAsyncTaskedit().execute();
                                //Toast.makeText(context,"id: "+id+"\nchannel: "+channel_type+"\nfollow: "+intent.getStringExtra("temp"),Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception ex) {
                            //Toast.makeText(context,"else "+ex.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    llspinner.setVisibility(View.VISIBLE);
                    lllabel.setVisibility(View.GONE);
                    flag = 1;
                    type_global = "add";
                    OrdersData od = new OrdersData();
                    od.clear();
                    saveFlag_duplicate = 1;
                }
                //Log.e("visitd", "finish");


                btnsave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.e("save", "stage1");
                        if (flag == 1) {
                            if (zone.getSelectedItemPosition() <= 0) {
                                //zone.setError
                                setSpinnerError(zone, "Required");
                                vflag = 0;
                            } else {
                                vflag = 1;
                            }


                            if (channel.getSelectedItem().toString().equals("GT")) {
                                if (area.getSelectedItemPosition() <= 0) {
                                    //zone.setError
                                    setSpinnerError(area, "Required");
                                    vflag = 0;
                                } else {
                                    vflag = 1;
                                }

                                if (location.getSelectedItemPosition() <= 0) {
                                    //zone.setError
                                    setSpinnerError(location, "Required");
                                    vflag = 0;
                                } else {
                                    vflag = 1;
                                }

                                if (type.getSelectedItem().toString().equals("Old")) {
                                    if (retailer.getSelectedItemPosition() <= 0) {
                                        setSpinnerError(retailer, "Required");
                                        vflag = 0;
                                    } else {
                                        vflag = 1;
                                    }
                                } else {
                                    if (txtretailer.getText().toString().equals("")) {
                                        txtretailer.setError("Required");
                                        focusOnViewEdit(txtretailer);
                                        vflag = 0;
                                    } else {
                                        //vflag = 1;
                                    }
                                }

                            } else {
                                if (relation.getSelectedItemPosition() <= 0) {

                                    setSpinnerError(relation, "Required");
                                    vflag = 0;
                                } else {
                                    vflag = 1;
                                }

                                if (location.getSelectedItemPosition() <= 0) {
                                    setSpinnerError(location, "Required");
                                    vflag = 0;
                                } else {
                                    vflag = 1;
                                }

                            }
                        } else {
                            if (channel1.equals("GT")) {
                                if (type1.equals("New")) {
                                    if (txtretailer1.getText().toString().equals("")) {
                                        txtretailer1.setError("Required");
                                        focusOnViewEdit(txtretailer1);
                                        vflag = 0;
                                        //Toast.makeText(context, "d " + vflag, Toast.LENGTH_LONG).show();
                                    } else {
                                        vflag = 1;
                                        //Toast.makeText(context, "" + vflag, Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    vflag = 1;
                                }
                            } else {
                                vflag = 1;
                            }
                        }

                        //Toast.makeText(context,""+flag,Toast.LENGTH_LONG).show();
                        //showPopUp3();
                        Log.e("save", "stage2");
                        if (vflag == 1) {
                            String orange_bar = "", orange_box = "", butterscotch_bar = "", butterscotch_box = "", chocopeanut_bar = "", bambaiyachaat_bar = "", mangoginger_bar = "", berry_blast_bar = "", chyawanprash_bar = "", chocopeanut_box = "", bambaiyachaat_box = "", mangoginger_box = "", berry_blast_box = "", chyawanprash_box = "", variety_box = "", chocolate_cookies = "", dark_chocolate_cookies = "", cranberry_cookies = "", cranberry_orange_zest = "", fig_raisins = "", papaya_pineapple = "";

                            View view = expListView.getChildAt(1);
                            EditText orange_bar_editText = view.findViewById(R.id.lblListItem2);
                            orange_bar = orange_bar_editText.getText().toString();

                            view = expListView.getChildAt(2);
                            EditText butterscotch_bar_editText = view.findViewById(R.id.lblListItem2);
                            butterscotch_bar = butterscotch_bar_editText.getText().toString();

                            view = expListView.getChildAt(3);
                            EditText chocopeanut_bar_editText = view.findViewById(R.id.lblListItem2);
                            chocopeanut_bar = chocopeanut_bar_editText.getText().toString();

                            view = expListView.getChildAt(4);
                            EditText bambaiyachaat_bar_editText = view.findViewById(R.id.lblListItem2);
                            bambaiyachaat_bar = bambaiyachaat_bar_editText.getText().toString();

                            view = expListView.getChildAt(5);
                            EditText mangoginger_bar_editText = view.findViewById(R.id.lblListItem2);
                            mangoginger_bar = mangoginger_bar_editText.getText().toString();

                            view = expListView.getChildAt(6);
                            EditText berry_blast_bar_editText = view.findViewById(R.id.lblListItem2);
                            berry_blast_bar = berry_blast_bar_editText.getText().toString();

                            view = expListView.getChildAt(7);
                            EditText chyawanprash_bar_editText = view.findViewById(R.id.lblListItem2);
                            chyawanprash_bar = chyawanprash_bar_editText.getText().toString();

                            view = expListView.getChildAt(9);
                            EditText orange_box_editText = view.findViewById(R.id.lblListItem2);
                            orange_box = orange_box_editText.getText().toString();

                            view = expListView.getChildAt(10);
                            EditText butterscotch_box_editText = view.findViewById(R.id.lblListItem2);
                            butterscotch_box = butterscotch_box_editText.getText().toString();

                            view = expListView.getChildAt(11);
                            EditText chocopeanut_box_editText = view.findViewById(R.id.lblListItem2);
                            chocopeanut_box = chocopeanut_box_editText.getText().toString();

                            view = expListView.getChildAt(12);
                            EditText bambaiyachaat_box_editText = view.findViewById(R.id.lblListItem2);
                            bambaiyachaat_box = bambaiyachaat_box_editText.getText().toString();

                            view = expListView.getChildAt(13);
                            EditText mangoginger_box_editText = view.findViewById(R.id.lblListItem2);
                            mangoginger_box = mangoginger_box_editText.getText().toString();

                            view = expListView.getChildAt(14);
                            EditText berry_blast_box_editText = view.findViewById(R.id.lblListItem2);
                            berry_blast_box = berry_blast_box_editText.getText().toString();

                            view = expListView.getChildAt(15);
                            EditText chyawanprash_box_editText = view.findViewById(R.id.lblListItem2);
                            chyawanprash_box = chyawanprash_box_editText.getText().toString();

                            view = expListView.getChildAt(16);
                            EditText variety_box_editText = view.findViewById(R.id.lblListItem2);
                            variety_box = variety_box_editText.getText().toString();

                            view = expListView.getChildAt(18);
                            EditText chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
                            chocolate_cookies = chocolate_cookies_editText.getText().toString();

                            view = expListView.getChildAt(19);
                            EditText dark_chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
                            dark_chocolate_cookies = dark_chocolate_cookies_editText.getText().toString();

                            view = expListView.getChildAt(20);
                            EditText cranberry_cookies_editText = view.findViewById(R.id.lblListItem2);
                            cranberry_cookies = cranberry_cookies_editText.getText().toString();

                            view = expListView.getChildAt(22);
                            EditText cranberry_orange_zest_editText = view.findViewById(R.id.lblListItem2);
                            cranberry_orange_zest = cranberry_orange_zest_editText.getText().toString();

                            view = expListView.getChildAt(23);
                            EditText fig_raisins_editText = view.findViewById(R.id.lblListItem2);
                            fig_raisins = fig_raisins_editText.getText().toString();

                            view = expListView.getChildAt(24);
                            EditText papaya_pineapple_editText = view.findViewById(R.id.lblListItem2);
                            papaya_pineapple = papaya_pineapple_editText.getText().toString();

                            if (((orange_bar.equals("") || orange_bar.equals("0")) && (orange_box.equals("") || orange_box.equals("0")) && (butterscotch_bar.equals("") || butterscotch_bar.equals("0")) && (butterscotch_box.equals("") || butterscotch_box.equals("0")) && (chocopeanut_bar.equals("") || chocopeanut_bar.equals("0")) && (chocopeanut_box.equals("") || chocopeanut_box.equals("0")) && (bambaiyachaat_bar.equals("") || bambaiyachaat_bar.equals("0")) && (bambaiyachaat_box.equals("") || bambaiyachaat_box.equals("0")) && (mangoginger_bar.equals("") || mangoginger_bar.equals("0")) && (mangoginger_box.equals("") || mangoginger_box.equals("0")) && (berry_blast_bar.equals("") || berry_blast_bar.equals("0")) && (berry_blast_box.equals("") || berry_blast_box.equals("0")) && (chyawanprash_bar.equals("") || chyawanprash_bar.equals("0")) && (chyawanprash_box.equals("") || chyawanprash_box.equals("0")) && (variety_box.equals("") || variety_box.equals("0")) && (chocolate_cookies.equals("") || chocolate_cookies.equals("0")) && (dark_chocolate_cookies.equals("") || dark_chocolate_cookies.equals("0")) && (cranberry_cookies.equals("") || cranberry_cookies.equals("0")) && (fig_raisins.equals("") || fig_raisins.equals("0")) && (papaya_pineapple.equals("") || papaya_pineapple.equals("0")) && (cranberry_orange_zest.equals("") || cranberry_orange_zest.equals("0"))) && expListView.getVisibility() == View.VISIBLE) {
                                showPopUp14(3);
                            } else {
                                savePopUp();
                            }

                        } else {
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.container_details), "Please fill all the required fields", Snackbar.LENGTH_LONG);
                            View snackBarView = snackbar.getView();
                            snackBarView.setBackgroundColor(Color.BLACK);
                            TextView textView = (TextView) snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
                            textView.setTextColor(Color.LTGRAY);
                            snackbar.show();
                        }
                    }
                });

                btncheckpoqty.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            get_po_nos();
                        } catch (Exception ex) {

                        }
                    }
                });

                get_po_nos();
                get_pending_po_nos();
            } catch (Exception ex) {

            }


        }
    }

    private void showMenuOption() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View menuOption = inflater.inflate(R.layout.menu_bottom, null);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) menuOption);
        GridView simplegridview = menuOption.findViewById(R.id.simpleGridView);
        menuOption.setFocusableInTouchMode(true);
        CustomGridViewAdapter customAdapter = new CustomGridViewAdapter(getApplicationContext(), logos);
        simplegridview.setAdapter(customAdapter);
        ImageView top_image = menuOption.findViewById(R.id.top_image);
        ImageView bottom_imageview = menuOption.findViewById(R.id.bottom_image);


        View.OnClickListener thisListener1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container_details.removeView(menuOption);
                flagpressed = false;
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
                switch (position) {
                    case 0:
                        new Handler().postDelayed(new Runnable() {
                            public void run() {

                                showcheckoutpopup();
                            }
                        }, 1000);
                        break;
                    case 1:
                        View_load_dialog.showDialog();

                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                Intent myIntent = new Intent(context, Visit_detailsActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("fabtype", "New");
                                myIntent.putExtras(bundle);
                                container_details.removeView(menuOption);
                                flagpressed = false;
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
                                container_details.removeView(menuOption);
                                flagpressed = false;
                                startActivityForResult(myIntent2, 1);

                                return;
                            }
                        }, 500);
                        break;
                    case 3:
                        if (OrdersData.reporting_manager.equals("no")) {
                            View_load_dialog.showDialog();
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    Intent i = new Intent(context, TimelineActivity.class);
                                    i.putExtra("sales_rep_id", session.getsales_rep_id());
                                    container_details.removeView(menuOption);
                                    flagpressed = false;
                                    startActivityForResult(i, 1);
                                }
                            }, 500);
                        } else {
                            View_load_dialog.showDialog();
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    Intent i = new Intent(context, ReportingManager.class);
                                    i.putExtra("source", "activity");
                                    container_details.removeView(menuOption);
                                    flagpressed = false;
                                    startActivityForResult(i, 1);
                                }
                            }, 500);
                        }

                        break;
                }
            }
        });
        menuOption.setFocusable(true);

        container_details.addView(menuOption, container_details.getChildCount() - 1);


    }

    public void savePopUp() {

        strsales_rep_id = session.getsales_rep_id();

        if (flag == 1) {

            strchannel_type = channel.getSelectedItem().toString();
            strdistributor_type = type.getSelectedItem().toString();

            if (strchannel_type.equals("MT")) {
                strdistributor_type = "Old";
                strdistributor_name = relation.getSelectedItem().toString();

                StringWithTag4 swt = (StringWithTag4) relation.getSelectedItem();
                int s_id = (Integer) swt.tag;
                strstore_id = "" + s_id;
            } else {
                if (type.getSelectedItem().toString().toLowerCase().equals("new")) {
                    strdistributor_name = txtretailer.getText().toString();
                    strdistributor_id = "";

                } else {
                    strdistributor_name = retailer.getSelectedItem().toString();
                    StringWithTag3 swt = (StringWithTag3) retailer.getSelectedItem();
                    strdistributor_id = (String) swt.tag;
                }

                StringWithTag1 swtarea = (StringWithTag1) area.getSelectedItem();
                int a_id = (Integer) swtarea.tag;
                strarea_id = "" + a_id;
            }

            OrdersData.distributor_name = strdistributor_name;

            StringWithTag swtzone = (StringWithTag) zone.getSelectedItem();
            int z_id = (Integer) swtzone.tag;
            strzone_id = "" + z_id;


            StringWithTag2 swtlocation = (StringWithTag2) location.getSelectedItem();
            int l_id = (Integer) swtlocation.tag;
            strlocation_id = "" + l_id;


            strremarks = txtremarks.getText().toString();

            strreation_id = strstore_id;


            try {
                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                String t1 = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
                String response1 = "";
                switch (t1) {
                    case "Monday":
                        response1 = session.getmon_frag();
                        Log.e("mon", response1);
                        break;
                    case "Tuesday":
                        response1 = session.gettues_frag();
                        Log.e("tue", response1);
                        break;
                    case "Wednesday":
                        response1 = session.getwed_frag();
                        Log.e("wed", response1);
                        break;
                    case "Thursday":
                        response1 = session.getthu_frag();
                        Log.e("thu", response1);
                        break;
                    case "Friday":
                        response1 = session.getfri_frag();
                        Log.e("fri", response1);
                        break;
                    case "Saturday":
                        response1 = session.getsat_frag();
                        Log.e("sat", response1);
                        break;
                    case "Sunday":
                        response1 = session.getsun_frag();
                        Log.e("sun", response1);
                        break;
                }
                JSONObject obj1 = new JSONObject(response1);
                JSONArray data2 = obj1.getJSONArray("data");
                JSONArray gt_followup = obj1.getJSONArray("gt_followup");
                JSONArray mt_followup = obj1.getJSONArray("mt_followup");
                JSONArray mt_visit = obj1.getJSONArray("merchendizer");

                //check gt visits
                for (int i = 0; i < data2.length(); i++) {
                    JSONObject data1 = data2.getJSONObject(i);
                    String distributor_id1 = data1.getString("store_id");
                    Log.e("distributor1", distributor_id1 + " " + strdistributor_id);
                    if (strdistributor_id.equals(distributor_id1)) {
                        showPopUp4();
                        saveFlag_duplicate = 0;
                        break;

                    } else {
                        saveFlag_duplicate = 1;
                        continue;
                    }
                }
                //check for gt followup
                for (int i = 0; i < gt_followup.length(); i++) {
                    JSONObject data1 = gt_followup.getJSONObject(i);
                    String distributor_id1 = data1.getString("store_id");
                    Log.e("distributor2", distributor_id1 + " " + strdistributor_id);
                    if (strdistributor_id.equals(distributor_id1)) {
                        showPopUp4();
                        Log.e("here", "cseg1");
                        saveFlag_duplicate = 0;
                        break;

                    } else {
                        saveFlag_duplicate = 1;
                        continue;
                    }
                }

            } catch (JSONException e) {
                Log.e("distributor5", e.getMessage());
            }
            Intent in = getIntent();
            if (!in.hasExtra("type")) {
                if (OrdersData.unplanned_count != null && OrdersData.unplanned_count.matches("[0-9]+")) {
                    int unplanned_c = Integer.parseInt(OrdersData.unplanned_count) + 1;
                    OrdersData.unplanned_count = unplanned_c + "";
                }
            }
            //Log.e("visit", "unplanned count: "+OrdersData.unplanned_count);
        } else {

            strchannel_type = channel1.getText().toString();
            strdistributor_type = type1.getText().toString();
            strstore_id = relation1_id.getText().toString();
            strdistributor_id = retailer1_id.getText().toString();
            strzone_id = zone1_id.getText().toString();
            strarea_id = area1_id.getText().toString();
            strlocation_id = location1_id.getText().toString();
            strdistributor_name = retailer1.getText().toString();
            strremarks = txtremarks.getText().toString();
            strreation_id = strstore_id;

            OrdersData.distributor_name = strdistributor_name;
            Intent in = getIntent();
            if (in.getStringExtra("type").equals("add")) {
                if (OrdersData.actual_count != null && OrdersData.actual_count.matches("[0-9]+")) {
                    int actual_c = Integer.parseInt(OrdersData.actual_count) + 1;
                    OrdersData.actual_count = actual_c + "";
                }
            }
            Log.e("visit", "distributor_name: " + strdistributor_name);
            Log.e("visit", "actual count: " + OrdersData.actual_count);
        }

        Log.e("save", "stage3");
        expandAll();

        if (!strdistributor_type.equals("New")) {

            View view = expListView.getChildAt(1);
            EditText orange_bar_editText = view.findViewById(R.id.lblListItem2);
            strorange_bar = orange_bar_editText.getText().toString();

            view = expListView.getChildAt(2);
            EditText butterscotch_bar_editText = view.findViewById(R.id.lblListItem2);
            strbutterscotch_bar = butterscotch_bar_editText.getText().toString();

            view = expListView.getChildAt(3);
            EditText chocopeanut_bar_editText = view.findViewById(R.id.lblListItem2);
            strchocopeanut_bar = chocopeanut_bar_editText.getText().toString();

            view = expListView.getChildAt(4);
            EditText bambaiyachaat_bar_editText = view.findViewById(R.id.lblListItem2);
            strbambaiyachaat_bar = bambaiyachaat_bar_editText.getText().toString();

            view = expListView.getChildAt(5);
            EditText mangoginger_bar_editText = view.findViewById(R.id.lblListItem2);
            strmangoginger_bar = mangoginger_bar_editText.getText().toString();

            view = expListView.getChildAt(6);
            EditText berry_blast_bar_editText = view.findViewById(R.id.lblListItem2);
            strberry_blast_bar = berry_blast_bar_editText.getText().toString();

            view = expListView.getChildAt(7);
            EditText chyawanprash_bar_editText = view.findViewById(R.id.lblListItem2);
            strchyawanprash_bar = chyawanprash_bar_editText.getText().toString();

            view = expListView.getChildAt(9);
            EditText orange_box_editText = view.findViewById(R.id.lblListItem2);
            strorange_box = orange_box_editText.getText().toString();

            view = expListView.getChildAt(10);
            EditText butterscotch_box_editText = view.findViewById(R.id.lblListItem2);
            strbutterscotch_box = butterscotch_box_editText.getText().toString();

            view = expListView.getChildAt(11);
            EditText chocopeanut_box_editText = view.findViewById(R.id.lblListItem2);
            strchocopeanut_box = chocopeanut_box_editText.getText().toString();

            view = expListView.getChildAt(12);
            EditText bambaiyachaat_box_editText = view.findViewById(R.id.lblListItem2);
            strbambaiyachaat_box = bambaiyachaat_box_editText.getText().toString();

            view = expListView.getChildAt(13);
            EditText mangoginger_box_editText = view.findViewById(R.id.lblListItem2);
            strmangoginger_box = mangoginger_box_editText.getText().toString();

            view = expListView.getChildAt(14);
            EditText berry_blast_box_editText = view.findViewById(R.id.lblListItem2);
            strberry_blast_box = berry_blast_box_editText.getText().toString();

            view = expListView.getChildAt(15);
            EditText chyawanprash_box_editText = view.findViewById(R.id.lblListItem2);
            strchyawanprash_box = chyawanprash_box_editText.getText().toString();

            view = expListView.getChildAt(16);
            EditText variety_box_editText = view.findViewById(R.id.lblListItem2);
            strvariety_box = variety_box_editText.getText().toString();

            view = expListView.getChildAt(18);
            EditText chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
            strchocolate_cookies = chocolate_cookies_editText.getText().toString();

            view = expListView.getChildAt(19);
            EditText dark_chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
            strdark_chocolate_cookies = dark_chocolate_cookies_editText.getText().toString();

            view = expListView.getChildAt(20);
            EditText cranberry_cookies_editText = view.findViewById(R.id.lblListItem2);
            strcranberry_cookies = cranberry_cookies_editText.getText().toString();

            view = expListView.getChildAt(22);
            EditText cranberry_orange_zest_editText = view.findViewById(R.id.lblListItem2);
            strcranberry_orange_zest = cranberry_orange_zest_editText.getText().toString();

            view = expListView.getChildAt(23);
            EditText fig_raisins_editText = view.findViewById(R.id.lblListItem2);
            strfig_raisins = fig_raisins_editText.getText().toString();

            view = expListView.getChildAt(24);
            EditText papaya_pineapple_editText = view.findViewById(R.id.lblListItem2);
            strpapaya_pineapple = papaya_pineapple_editText.getText().toString();

            if (saveFlag_duplicate == 1) {
                try {
                    //View_load_dialog.showDialog();
                    //showPopUp5(strorange_bar, strorange_box, strbutterscotch_bar, strbutterscotch_box, strchocopeanut_bar, strchocopeanut_box, strbambaiyachaat_bar, strbambaiyachaat_box, strmangoginger_bar, strmangoginger_box, strberry_blast_bar, strberry_blast_box, strchyawanprash_bar, strchyawanprash_box, strvariety_box, strchocolate_cookies, strdark_chocolate_cookies, strcranberry_cookies, strfig_raisins, strpapaya_pineapple, strcranberry_orange_zest);
                    Log.e("save", "stage5");
                    saveData(strsales_rep_id, strchannel_type, strdistributor_type, strdistributor_id, strstore_id, strzone_id, strarea_id, strlocation_id, strdistributor_name, strremarks, strreation_id, strorange_bar, strorange_box, strbutterscotch_bar, strbutterscotch_box, strchocopeanut_bar, strchocopeanut_box, strbambaiyachaat_bar, strbambaiyachaat_box, strmangoginger_bar, strmangoginger_box, strberry_blast_bar, strberry_blast_box, strchyawanprash_bar, strchyawanprash_box, strvariety_box, strchocolate_cookies, strdark_chocolate_cookies, strcranberry_cookies, strfig_raisins, strpapaya_pineapple, strcranberry_orange_zest, follow_type, "", "Save", id, bit_plan_id, sequence, mid, sales_rep_loc_id, merchandiser_stock_id, "Yes");
                    //Toast.makeText(context, "Data Saved", Toast.LENGTH_LONG).show();
                                    /*Snackbar snackbar = Snackbar
                                            .make(findViewById(R.id.drawer_layout3), "Data Saved", Snackbar.LENGTH_LONG);
                                    View snackBarView = snackbar.getView();
                                    snackBarView.setBackgroundColor(Color.BLACK);
                                    TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
                                    textView.setTextColor(Color.LTGRAY);
                                    snackbar.show();*/


                                        /*new Handler().postDelayed(new Runnable() {
                                            public void run() {
                                                Intent i = new Intent(context, Visit_list_pageActivity.class);
                                                startActivity(i);
                                                finish();
                                            }
                                        }, 500);*/
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                Log.e("here", "cseg3545");
            }
        } else {

            Calendar calendar0 = Calendar.getInstance();
            Date date0 = calendar0.getTime();
            String t = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date0.getTime());
            if (t.contains("Sunday")) {
                stris_permanent = "No";
                try {
                    saveData(strsales_rep_id, strchannel_type, strdistributor_type, strdistributor_id, strstore_id, strzone_id, strarea_id, strlocation_id, strdistributor_name, strremarks, strreation_id, strorange_bar, strorange_box, strbutterscotch_bar, strbutterscotch_box, strchocopeanut_bar, strchocopeanut_box, strbambaiyachaat_bar, strbambaiyachaat_box, strmangoginger_bar, strmangoginger_box, strberry_blast_bar, strberry_blast_box, strchyawanprash_bar, strchyawanprash_box, strvariety_box, strchocolate_cookies, strdark_chocolate_cookies, strcranberry_cookies, strfig_raisins, strpapaya_pineapple, strcranberry_orange_zest, follow_type, "", "Save", id, bit_plan_id, sequence, mid, sales_rep_loc_id, merchandiser_stock_id, stris_permanent);
                } catch (UnsupportedEncodingException | JSONException e) {
                    e.printStackTrace();
                }
            } else {
                showpermanant_route_popup();
            }
        }


    }


    public void showPopUp14(final int num) {
        LayoutInflater factory = getLayoutInflater();
        final View zero_inventory = factory.inflate(R.layout.alertdialog_zero_inventory, null);
        final AlertDialog helpBuilder = new AlertDialog.Builder(this).setIcon(R.drawable.shape).create();
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) zero_inventory);
        helpBuilder.setView(zero_inventory);
        //Button btnDatePicker = (Button)
        zero_inventory.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpBuilder.dismiss();
                // Do nothing but close the dialog
            }
        });
        zero_inventory.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zero_inventory_flag = true;
                if (num == 1) {
                    followPopUp();
                } else if (num == 2) {
                    placeorderPopUp();
                } else if (num == 3) {
                    savePopUp();
                }
                helpBuilder.dismiss();
            }
        });

        helpBuilder.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(helpBuilder.getWindow().getAttributes());

        int dialogWindowWidth = (int) (displayWidth * 0.7f);
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        helpBuilder.getWindow().setAttributes(layoutParams);
        helpBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    private void showPopUp6() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.alertdialog_empty_batch, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).setIcon(R.drawable.shape).create();
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) deleteDialogView);
        deleteDialog.setView(deleteDialogView);
        deleteDialogView.findViewById(R.id.btn_okay).setOnClickListener(new View.OnClickListener() {
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

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("loading", "closed from restart");
        View_load_dialog.hideDialog();
        listAdapter.notifyDataSetChanged();


        try{
            String orange_bar_json = OrdersData.orange_bar_json;
            JSONObject obj = new JSONObject(orange_bar_json);

            Iterator<String> keys = obj.keys();
            int count = 0;
            JSONObject bar_qty=new JSONObject();
            while (keys.hasNext()) {
                String key = keys.next();

                if (count == 0) {
                    JSONArray data = new JSONArray(OrdersData.batch_no_json);
                    JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                    bar_qty.put(data1.getString("batch_no"), obj.getString(key));
                }else{
                    JSONArray data = new JSONArray(OrdersData.batch_no_json);
                    JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                    bar_qty.put(data1.getString("batch_no"), obj.getString(key));
                }
            }
            JSONObject find=new JSONObject();
            find.put("orange", bar_qty.toString());
            Log.e("orange_batch", find.toString());
        }catch(Exception e){

        }

        if (restartflag || restartflag4) {
            View_load_dialog.showDialog();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(context, Visit_list_pageActivity.class);
                    startActivityForResult(i, 1);
                    finish();
                }
            }, 150);
        } else if (restartflag2) {
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, returnWhatsappText() + "\n\n" + getResources().getString(R.string.whatsapp_suffix));
            try {
                startActivity(whatsappIntent);
                Log.e("visit", "service started");
                restartflag = true;
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(context, "Please install whatsapp to share details on whatsapp", Toast.LENGTH_LONG).show();
                Log.e("visit", "error21 " + ex.toString());

            }
        } else if (restartflag3) {
            View_load_dialog.showDialog();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(context, dashboardActivity.class);
                    startActivityForResult(i, 1);
                }
            }, 1000);
        } else {

        }

    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    private void showPopUp5(final String orange_bar, final String orange_box, final String butterscotch_bar, final String butterscotch_box, final String chocopeanut_bar, final String chocopeanut_box, final String bambaiyachaat_bar, final String bambaiyachaat_box, final String mangoginger_bar, final String mangoginger_box, final String berry_blast_bar, final String berry_blast_box, final String chyawanprash_bar, final String chyawanprash_box, final String variety_box, final String chocolate_cookies, final String dark_chocolate_cookies, final String cranberry_cookies, final String fig_raisins, final String papaya_pineapple, final String cranberry_orange_zest) {
        View_load_dialog.showDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(context, Batch_detailsActivity.class);
                i.putExtra("orange_bar", orange_bar);
                i.putExtra("orange_box", orange_box);
                i.putExtra("butterscotch_bar", butterscotch_bar);
                i.putExtra("butterscotch_box", butterscotch_box);
                i.putExtra("chocopeanut_bar", chocopeanut_bar);
                i.putExtra("chocopeanut_box", chocopeanut_box);
                i.putExtra("bambaiyachaat_bar", bambaiyachaat_bar);
                i.putExtra("bambaiyachaat_box", bambaiyachaat_box);
                i.putExtra("mangoginger_bar", mangoginger_bar);
                i.putExtra("mangoginger_box", mangoginger_box);
                i.putExtra("berry_blast_bar", berry_blast_bar);
                i.putExtra("berry_blast_box", berry_blast_box);
                i.putExtra("chyawanprash_bar", chyawanprash_bar);
                i.putExtra("chyawanprash_box", chyawanprash_box);
                i.putExtra("variety_box", variety_box);
                i.putExtra("chocolate_cookies", chocolate_cookies);
                i.putExtra("dark_chocolate_cookies", dark_chocolate_cookies);
                i.putExtra("cranberry_cookies", cranberry_cookies);
                i.putExtra("fig_raisins", fig_raisins);
                i.putExtra("papaya_pineapple", papaya_pineapple);
                i.putExtra("cranberry_orange_zest", cranberry_orange_zest);
                startActivityForResult(i, 0);
            }
        }, 500);

    }

    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    private void setSpinnerError(Spinner spinner, String error) {
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error);
            focusOnViewSpinner(spinner);

            // actual error message
            // to open the spinner list if error is found.

        }
    }

    private final void focusOnViewEdit(final EditText editText) {
        mScrollView.post(new Runnable() {
            @Override
            public void run() {
                mScrollView.scrollTo(0, editText.getBottom());
            }
        });
    }

    private final void focusOnViewSpinner(final Spinner spinner) {
        mScrollView.post(new Runnable() {
            @Override
            public void run() {
                mScrollView.scrollTo(0, spinner.getBottom());
            }
        });
    }

    @Override
    public void onBackPressed() {
        OrdersData od = new OrdersData();
        od.clear();
        View_load_dialog.showDialog();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent i = new Intent(context, Visit_list_pageActivity.class);
                startActivityForResult(i, 1);
                finish();
            }
        }, 500);

        // your code.
    }


    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            expListView.expandGroup(i);
        }
    }

    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            expListView.collapseGroup(i);
        }
    }


    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Bar");
        listDataHeader.add("Box");
        listDataHeader.add("Cookies");
        listDataHeader.add("TrailMix");

        // Adding child data
        List<String> Bar = new ArrayList<String>();
        Bar.add("Orange");
        Bar.add("Butterscotch");
        Bar.add("Choco");
        Bar.add("Chaat");
        Bar.add("Mango");
        Bar.add("Berry");
        Bar.add("Chywanprash");


        List<String> Box = new ArrayList<String>();
        Box.add("Orange");
        Box.add("Butterscotch");
        Box.add("Choco");
        Box.add("Chaat");
        Box.add("Mango");
        Box.add("Berry");
        Box.add("Chywanprash");
        Box.add("Variety Box");


        List<String> Cookies = new ArrayList<String>();
        Cookies.add("Chocolate");
        Cookies.add("Dark Chocolate");
        Cookies.add("Cranberry");


        List<String> TrailMix = new ArrayList<String>();
        TrailMix.add("Cranberry & Orange");
        TrailMix.add("Fig & Raisins");
        TrailMix.add("Papaya & Pineapple");


        listDataChild.put(listDataHeader.get(0), Bar); // Header, Child data
        listDataChild.put(listDataHeader.get(1), Box);
        listDataChild.put(listDataHeader.get(2), Cookies);
        listDataChild.put(listDataHeader.get(3), TrailMix);
    }

    private void showPopUp() {
        if (flag == 1) {
            if (zone.getSelectedItemPosition() <= 0) {
                //zone.setError
                setSpinnerError(zone, "Required");
                vflag = 0;
            } else {
                vflag = 1;
            }


            if (channel.getSelectedItem().toString().equals("GT")) {
                if (area.getSelectedItemPosition() <= 0) {
                    //zone.setError
                    setSpinnerError(area, "Required");
                    vflag = 0;
                } else {
                    vflag = 1;
                }

                if (location.getSelectedItemPosition() <= 0) {
                    //zone.setError
                    setSpinnerError(location, "Required");
                    vflag = 0;
                } else {
                    vflag = 1;
                }

                if (type.getSelectedItem().toString().equals("Old")) {
                    if (retailer.getSelectedItemPosition() <= 0) {
                        setSpinnerError(retailer, "Required");
                        vflag = 0;
                    } else {
                        vflag = 1;
                    }
                } else {
                    if (txtretailer.getText().toString().equals("")) {
                        txtretailer.setError("Required");
                        vflag = 0;
                    } else {
                        //vflag = 1;
                    }
                }

            } else {
                if (relation.getSelectedItemPosition() <= 0) {

                    setSpinnerError(relation, "Required");
                    vflag = 0;
                } else {
                    vflag = 1;
                }

                if (location.getSelectedItemPosition() <= 0) {
                    setSpinnerError(location, "Required");
                    vflag = 0;
                } else {
                    vflag = 1;
                }

            }
        } else {
            if (channel1.equals("GT")) {
                if (type1.equals("New")) {
                    if (txtretailer1.getText().toString().equals("")) {
                        txtretailer1.setError("Required");
                        vflag = 0;
                        //Toast.makeText(context, "d " + vflag, Toast.LENGTH_LONG).show();
                    } else {
                        vflag = 1;
                        //Toast.makeText(context, "" + vflag, Toast.LENGTH_LONG).show();
                    }
                } else {
                    vflag = 1;
                }
            } else {
                vflag = 1;
            }
        }
        if (vflag == 1) {

            String orange_bar = "", orange_box = "", butterscotch_bar = "", butterscotch_box = "", chocopeanut_bar = "", bambaiyachaat_bar = "", mangoginger_bar = "", berry_blast_bar = "", chyawanprash_bar = "", chocopeanut_box = "", bambaiyachaat_box = "", mangoginger_box = "", berry_blast_box = "", chyawanprash_box = "", variety_box = "", chocolate_cookies = "", dark_chocolate_cookies = "", cranberry_cookies = "", cranberry_orange_zest = "", fig_raisins = "", papaya_pineapple = "";

            View view = expListView.getChildAt(1);
            EditText orange_bar_editText = view.findViewById(R.id.lblListItem2);
            orange_bar = orange_bar_editText.getText().toString();

            view = expListView.getChildAt(2);
            EditText butterscotch_bar_editText = view.findViewById(R.id.lblListItem2);
            butterscotch_bar = butterscotch_bar_editText.getText().toString();

            view = expListView.getChildAt(3);
            EditText chocopeanut_bar_editText = view.findViewById(R.id.lblListItem2);
            chocopeanut_bar = chocopeanut_bar_editText.getText().toString();

            view = expListView.getChildAt(4);
            EditText bambaiyachaat_bar_editText = view.findViewById(R.id.lblListItem2);
            bambaiyachaat_bar = bambaiyachaat_bar_editText.getText().toString();

            view = expListView.getChildAt(5);
            EditText mangoginger_bar_editText = view.findViewById(R.id.lblListItem2);
            mangoginger_bar = mangoginger_bar_editText.getText().toString();

            view = expListView.getChildAt(6);
            EditText berry_blast_bar_editText = view.findViewById(R.id.lblListItem2);
            berry_blast_bar = berry_blast_bar_editText.getText().toString();

            view = expListView.getChildAt(7);
            EditText chyawanprash_bar_editText = view.findViewById(R.id.lblListItem2);
            chyawanprash_bar = chyawanprash_bar_editText.getText().toString();

            view = expListView.getChildAt(9);
            EditText orange_box_editText = view.findViewById(R.id.lblListItem2);
            orange_box = orange_box_editText.getText().toString();

            view = expListView.getChildAt(10);
            EditText butterscotch_box_editText = view.findViewById(R.id.lblListItem2);
            butterscotch_box = butterscotch_box_editText.getText().toString();

            view = expListView.getChildAt(11);
            EditText chocopeanut_box_editText = view.findViewById(R.id.lblListItem2);
            chocopeanut_box = chocopeanut_box_editText.getText().toString();

            view = expListView.getChildAt(12);
            EditText bambaiyachaat_box_editText = view.findViewById(R.id.lblListItem2);
            bambaiyachaat_box = bambaiyachaat_box_editText.getText().toString();

            view = expListView.getChildAt(13);
            EditText mangoginger_box_editText = view.findViewById(R.id.lblListItem2);
            mangoginger_box = mangoginger_box_editText.getText().toString();

            view = expListView.getChildAt(14);
            EditText berry_blast_box_editText = view.findViewById(R.id.lblListItem2);
            berry_blast_box = berry_blast_box_editText.getText().toString();

            view = expListView.getChildAt(15);
            EditText chyawanprash_box_editText = view.findViewById(R.id.lblListItem2);
            chyawanprash_box = chyawanprash_box_editText.getText().toString();

            view = expListView.getChildAt(16);
            EditText variety_box_editText = view.findViewById(R.id.lblListItem2);
            variety_box = variety_box_editText.getText().toString();

            view = expListView.getChildAt(18);
            EditText chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
            chocolate_cookies = chocolate_cookies_editText.getText().toString();

            view = expListView.getChildAt(19);
            EditText dark_chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
            dark_chocolate_cookies = dark_chocolate_cookies_editText.getText().toString();

            view = expListView.getChildAt(20);
            EditText cranberry_cookies_editText = view.findViewById(R.id.lblListItem2);
            cranberry_cookies = cranberry_cookies_editText.getText().toString();

            view = expListView.getChildAt(22);
            EditText cranberry_orange_zest_editText = view.findViewById(R.id.lblListItem2);
            cranberry_orange_zest = cranberry_orange_zest_editText.getText().toString();

            view = expListView.getChildAt(23);
            EditText fig_raisins_editText = view.findViewById(R.id.lblListItem2);
            fig_raisins = fig_raisins_editText.getText().toString();

            view = expListView.getChildAt(24);
            EditText papaya_pineapple_editText = view.findViewById(R.id.lblListItem2);
            papaya_pineapple = papaya_pineapple_editText.getText().toString();

            if (((orange_bar.equals("") || orange_bar.equals("0")) && (orange_box.equals("") || orange_box.equals("0")) && (butterscotch_bar.equals("") || butterscotch_bar.equals("0")) && (butterscotch_box.equals("") || butterscotch_box.equals("0")) && (chocopeanut_bar.equals("") || chocopeanut_bar.equals("0")) && (chocopeanut_box.equals("") || chocopeanut_box.equals("0")) && (bambaiyachaat_bar.equals("") || bambaiyachaat_bar.equals("0")) && (bambaiyachaat_box.equals("") || bambaiyachaat_box.equals("0")) && (mangoginger_bar.equals("") || mangoginger_bar.equals("0")) && (mangoginger_box.equals("") || mangoginger_box.equals("0")) && (berry_blast_bar.equals("") || berry_blast_bar.equals("0")) && (berry_blast_box.equals("") || berry_blast_box.equals("0")) && (chyawanprash_bar.equals("") || chyawanprash_bar.equals("0")) && (chyawanprash_box.equals("") || chyawanprash_box.equals("0")) && (variety_box.equals("") || variety_box.equals("0")) && (chocolate_cookies.equals("") || chocolate_cookies.equals("0")) && (dark_chocolate_cookies.equals("") || dark_chocolate_cookies.equals("0")) && (cranberry_cookies.equals("") || cranberry_cookies.equals("0")) && (fig_raisins.equals("") || fig_raisins.equals("0")) && (papaya_pineapple.equals("") || papaya_pineapple.equals("0")) && (cranberry_orange_zest.equals("") || cranberry_orange_zest.equals("0"))) && expListView.getVisibility() == View.VISIBLE) {
                showPopUp14(1);
            } else {
                followPopUp();
            }


        } else {
            Log.e("followup else", "else ");
        }

    }

    public void followPopUp() {

        LayoutInflater factory = getLayoutInflater();
        final View follow_up_button = factory.inflate(R.layout.activity_follow_up, null);
        final AlertDialog helpBuilder = new AlertDialog.Builder(this).setIcon(R.drawable.shape).create();
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) follow_up_button);
        helpBuilder.setView(follow_up_button);
        //Button btnDatePicker = (Button)
        follow_up_button.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpBuilder.dismiss();
                // Do nothing but close the dialog
            }
        });

        follow_up_button.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    follow_up_button.findViewById(R.id.btn_yes).setEnabled(false);
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

                        GPSTracker gpstrack = new GPSTracker(context);
                        OrdersData.longitude = "" + gpstrack.getLongitude();
                        OrdersData.latitude = "" + gpstrack.getLatitude();

                    }
                    String sales_rep_id = session.getsales_rep_id();
                    String channel_type = "";
                    String distributor_type = "";
                    String store_id = "";
                    String distributor_id = "";
                    String zone_id = "";
                    String area_id = "";
                    String location_id = "";
                    String distributor_name = "";
                    String remarks = "";
                    String reation_id = "";


                    if (flag == 1) {

                        channel_type = channel.getSelectedItem().toString();
                        distributor_type = type.getSelectedItem().toString();

                        if (channel_type.equals("MT")) {
                            distributor_type = "Old";

                            StringWithTag4 swt = (StringWithTag4) relation.getSelectedItem();
                            int s_id = (Integer) swt.tag;
                            store_id = "" + s_id;


                        } else {
                            StringWithTag1 swtarea = (StringWithTag1) area.getSelectedItem();
                            int a_id = (Integer) swtarea.tag;
                            area_id = "" + a_id;

                            StringWithTag3 swt = (StringWithTag3) retailer.getSelectedItem();
                            distributor_id = (String) swt.tag;
                        }

                        if (channel_type.equals("GT") && distributor_type.equals("New")) {
                            distributor_name = txtretailer.getText().toString();
                        } else if (channel_type.equals("MT")) {
                            distributor_name = relation.getSelectedItem().toString();
                        } else {
                            distributor_name = retailer.getSelectedItem().toString();
                        }

                        OrdersData.distributor_name = distributor_name;


                        StringWithTag swtzone = (StringWithTag) zone.getSelectedItem();
                        int z_id = (Integer) swtzone.tag;
                        zone_id = "" + z_id;


                        StringWithTag2 swtlocation = (StringWithTag2) location.getSelectedItem();
                        int l_id = (Integer) swtlocation.tag;
                        location_id = "" + l_id;

                        remarks = txtremarks.getText().toString();

                        reation_id = store_id;
                    } else {
                        channel_type = channel1.getText().toString();
                        distributor_type = type1.getText().toString();
                        store_id = relation1_id.getText().toString();
                        distributor_id = retailer1_id.getText().toString();
                        zone_id = zone1_id.getText().toString();
                        area_id = area1_id.getText().toString();
                        location_id = location1_id.getText().toString();
                        distributor_name = retailer1.getText().toString();
                        remarks = txtremarks.getText().toString();
                        reation_id = store_id;
                    }

                    expandAll();


                    String orange_bar = "0";
                    String butterscotch_bar = "0";
                    String chocopeanut_bar = "0";
                    String bambaiyachaat_bar = "0";
                    String mangoginger_bar = "0";
                    String berry_blast_bar = "0";
                    String chyawanprash_bar = "0";
                    String orange_box = "0";
                    String butterscotch_box = "0";
                    String chocopeanut_box = "0";
                    String bambaiyachaat_box = "0";
                    String mangoginger_box = "0";
                    String berry_blast_box = "0";
                    String chyawanprash_box = "0";
                    String variety_box = "0";
                    String chocolate_cookies = "0";
                    String dark_chocolate_cookies = "0";
                    String cranberry_cookies = "0";
                    String cranberry_orange_zest = "0";
                    String fig_raisins = "0";
                    String papaya_pineapple = "0";


                    if (!distributor_type.equals("New")) {


                        View view = expListView.getChildAt(1);
                        EditText orange_bar_editText = view.findViewById(R.id.lblListItem2);
                        orange_bar = orange_bar_editText.getText().toString();

                        view = expListView.getChildAt(2);
                        EditText butterscotch_bar_editText = view.findViewById(R.id.lblListItem2);
                        butterscotch_bar = butterscotch_bar_editText.getText().toString();

                        view = expListView.getChildAt(3);
                        EditText chocopeanut_bar_editText = view.findViewById(R.id.lblListItem2);
                        chocopeanut_bar = chocopeanut_bar_editText.getText().toString();

                        view = expListView.getChildAt(4);
                        EditText bambaiyachaat_bar_editText = view.findViewById(R.id.lblListItem2);
                        bambaiyachaat_bar = bambaiyachaat_bar_editText.getText().toString();

                        view = expListView.getChildAt(5);
                        EditText mangoginger_bar_editText = view.findViewById(R.id.lblListItem2);
                        mangoginger_bar = mangoginger_bar_editText.getText().toString();

                        view = expListView.getChildAt(6);
                        EditText berry_blast_bar_editText = view.findViewById(R.id.lblListItem2);
                        berry_blast_bar = berry_blast_bar_editText.getText().toString();

                        view = expListView.getChildAt(7);
                        EditText chyawanprash_bar_editText = view.findViewById(R.id.lblListItem2);
                        chyawanprash_bar = chyawanprash_bar_editText.getText().toString();

                        view = expListView.getChildAt(9);
                        EditText orange_box_editText = view.findViewById(R.id.lblListItem2);
                        orange_box = orange_box_editText.getText().toString();

                        view = expListView.getChildAt(10);
                        EditText butterscotch_box_editText = view.findViewById(R.id.lblListItem2);
                        butterscotch_box = butterscotch_box_editText.getText().toString();

                        view = expListView.getChildAt(11);
                        EditText chocopeanut_box_editText = view.findViewById(R.id.lblListItem2);
                        chocopeanut_box = chocopeanut_box_editText.getText().toString();

                        view = expListView.getChildAt(12);
                        EditText bambaiyachaat_box_editText = view.findViewById(R.id.lblListItem2);
                        bambaiyachaat_box = bambaiyachaat_box_editText.getText().toString();

                        view = expListView.getChildAt(13);
                        EditText mangoginger_box_editText = view.findViewById(R.id.lblListItem2);
                        mangoginger_box = mangoginger_box_editText.getText().toString();

                        view = expListView.getChildAt(14);
                        EditText berry_blast_box_editText = view.findViewById(R.id.lblListItem2);
                        berry_blast_box = berry_blast_box_editText.getText().toString();

                        view = expListView.getChildAt(15);
                        EditText chyawanprash_box_editText = view.findViewById(R.id.lblListItem2);
                        chyawanprash_box = chyawanprash_box_editText.getText().toString();

                        view = expListView.getChildAt(16);
                        EditText variety_box_editText = view.findViewById(R.id.lblListItem2);
                        variety_box = variety_box_editText.getText().toString();

                        view = expListView.getChildAt(18);
                        EditText chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
                        chocolate_cookies = chocolate_cookies_editText.getText().toString();

                        view = expListView.getChildAt(19);
                        EditText dark_chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
                        dark_chocolate_cookies = dark_chocolate_cookies_editText.getText().toString();

                        view = expListView.getChildAt(20);
                        EditText cranberry_cookies_editText = view.findViewById(R.id.lblListItem2);
                        cranberry_cookies = cranberry_cookies_editText.getText().toString();

                        view = expListView.getChildAt(22);
                        EditText cranberry_orange_zest_editText = view.findViewById(R.id.lblListItem2);
                        cranberry_orange_zest = cranberry_orange_zest_editText.getText().toString();

                        view = expListView.getChildAt(23);
                        EditText fig_raisins_editText = view.findViewById(R.id.lblListItem2);
                        fig_raisins = fig_raisins_editText.getText().toString();

                        view = expListView.getChildAt(24);
                        EditText papaya_pineapple_editText = view.findViewById(R.id.lblListItem2);
                        papaya_pineapple = papaya_pineapple_editText.getText().toString();

                    } else {
                        Log.e("foll: ", "here no");
                    }


                    // Do nothing but close the dialog
                    if (!txtDate.getText().toString().equals("")) {
                        Log.e("foll: ", "here too");
                        add_followup(distributor_name);
                        saveData(sales_rep_id, channel_type, distributor_type, distributor_id, store_id, zone_id, area_id, location_id, distributor_name, remarks, reation_id, orange_bar, orange_box, butterscotch_bar, butterscotch_box, chocopeanut_bar, chocopeanut_box, bambaiyachaat_bar, bambaiyachaat_box, mangoginger_bar, mangoginger_box, berry_blast_bar, berry_blast_box, chyawanprash_bar, chyawanprash_box, variety_box, chocolate_cookies, dark_chocolate_cookies, cranberry_cookies, fig_raisins, papaya_pineapple, cranberry_orange_zest, follow_type, txtDate.getText().toString(), "Follow Up", id, bit_plan_id, sequence, mid, sales_rep_loc_id, merchandiser_stock_id, "");
                    } else {
                        Log.e("foll: ", "here");
                        txtDate.setError("Required");
                    }
                    helpBuilder.dismiss();
                } catch (Exception ex) {
                    Log.e("follopopup", ex.toString());
                    //Toast.makeText(context, ex.toString(), Toast.LENGTH_LONG).show();
                }

            }
        });

        // Remember, create doesn't show the dialog

        helpBuilder.show();
        helpBuilder.getWindow().setLayout(600, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        helpBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Button btnDatePicker = (Button) helpDialog.findViewById(R.id.btn_date);
        txtDate = (EditText) follow_up_button.findViewById(R.id.follow_up_date);
        txtdate.setTextSize(12);
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                final DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                CharSequence strDate = null;
                                Time chosenDate = new Time();
                                chosenDate.set(dayOfMonth, monthOfYear, year);
                                long dtDob = chosenDate.toMillis(true);

                                strDate = DateFormat.format("dd/MM/yyyy", dtDob);

                                txtDate.setText(strDate);
                                //Intent intent = new Intent(context,Visit_list_pageActivity.class);
                                //startActivity(intent);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        updateUI(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

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

    private void showPopUp2(String response) {
        try {
            AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);


            LayoutInflater inflater = getLayoutInflater();
            po_qty_button = inflater.inflate(R.layout.activity_po_qty_popup, null);
            helpBuilder.setView(po_qty_button);
            helpBuilder.setTitle("Purchase Order Quantity");

            po_number_spn = po_qty_button.findViewById(R.id.po_number);

            final ArrayList<StringWithTag1> mylist_po = new ArrayList<StringWithTag1>();
            //JSONObject obj = new JSONObject(responsetext);
            HashMap<Integer, String> list_po = new HashMap<Integer, String>();

            JSONArray data = new JSONArray(response);
            //list_area.put(0, "Select");
            //Toast.makeText(getContext(),""+distributor.length(),Toast.LENGTH_LONG).show();
            for (int i = 0; i < data.length(); i++) {
                JSONObject data1 = data.getJSONObject(i);
                String po_number = data1.getString("po_number");
                int po_id = Integer.parseInt(data1.getString("id"));
                //mylist.add(distributor_name);
                list_po.put(po_id, po_number);
            }

            mylist_po.add(new StringWithTag1("Select", 0));

            for (Map.Entry<Integer, String> entry : list_po.entrySet()) {
                Integer key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                mylist_po.add(new StringWithTag1(value, key));
            }
            // Do nothing but close the dialog

            ArrayAdapter<StringWithTag1> adapter_po = new ArrayAdapter<StringWithTag1>(context, android.R.layout.simple_spinner_item, mylist_po) {
                public View getView(int position, View convertView, android.view.ViewGroup parent) {
                    View v = (TextView) super.getView(position, convertView, parent);
                    Typeface tfavv = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Regular.otf");
                    ((TextView) v).setTypeface(tfavv);
                    v.setPadding(15, 10, 5, 10);
                    //v.setTextColor(Color.RED);
                    //v.setTextSize(35);
                    return v;
                }


                public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                    View v = (TextView) super.getView(position, convertView, parent);
                    Typeface tfavv = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Regular.otf");
                    ((TextView) v).setTypeface(tfavv);
                    v.setPadding(15, 10, 5, 10);
                    //v.setTextColor(Color.RED);
                    //v.setTextSize(35);
                    return v;
                }
            };
            adapter_po.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            po_number_spn.setAdapter(adapter_po);

            llorange_bar = (LinearLayout) po_qty_button.findViewById(R.id.llorange_bar);
            orange_bar_qty = (TextView) po_qty_button.findViewById(R.id.orange_bar_qty);
            orange_bar = (EditText) po_qty_button.findViewById(R.id.orange_bar);
            orange_bar_diff = (TextView) po_qty_button.findViewById(R.id.orange_bar_diff);
            llbutterscotch_bar = (LinearLayout) po_qty_button.findViewById(R.id.llbutterscotch_bar);
            butterscotch_bar_qty = (TextView) po_qty_button.findViewById(R.id.butterscotch_bar_qty);
            butterscotch_bar = (EditText) po_qty_button.findViewById(R.id.butterscotch_bar);
            butterscotch_bar_diff = (TextView) po_qty_button.findViewById(R.id.butterscotch_bar_diff);
            llchocopeanut_bar = (LinearLayout) po_qty_button.findViewById(R.id.llchocopeanut_bar);
            chocopeanut_bar_qty = (TextView) po_qty_button.findViewById(R.id.chocopeanut_bar_qty);
            chocopeanut_bar = (EditText) po_qty_button.findViewById(R.id.chocopeanut_bar);
            chocopeanut_bar_diff = (TextView) po_qty_button.findViewById(R.id.chocopeanut_bar_diff);
            llbambaiyachaat_bar = (LinearLayout) po_qty_button.findViewById(R.id.llbambaiyachaat_bar);
            bambaiyachaat_bar_qty = (TextView) po_qty_button.findViewById(R.id.bambaiyachaat_bar_qty);
            bambaiyachaat_bar = (EditText) po_qty_button.findViewById(R.id.bambaiyachaat_bar);
            bambaiyachaat_bar_diff = (TextView) po_qty_button.findViewById(R.id.bambaiyachaat_bar_diff);
            llmangoginger_bar = (LinearLayout) po_qty_button.findViewById(R.id.llmangoginger_bar);
            mangoginger_bar_qty = (TextView) po_qty_button.findViewById(R.id.mangoginger_bar_qty);
            mangoginger_bar = (EditText) po_qty_button.findViewById(R.id.mangoginger_bar);
            mangoginger_bar_diff = (TextView) po_qty_button.findViewById(R.id.mangoginger_bar_diff);
            llberry_blast_bar = (LinearLayout) po_qty_button.findViewById(R.id.llberry_blast_bar);
            berry_blast_bar_qty = (TextView) po_qty_button.findViewById(R.id.berry_blast_bar_qty);
            berry_blast_bar = (EditText) po_qty_button.findViewById(R.id.berry_blast_bar);
            berry_blast_bar_diff = (TextView) po_qty_button.findViewById(R.id.berry_blast_bar_diff);
            llchyawanprash_bar = (LinearLayout) po_qty_button.findViewById(R.id.llchyawanprash_bar);
            chyawanprash_bar_qty = (TextView) po_qty_button.findViewById(R.id.chyawanprash_bar_qty);
            chyawanprash_bar = (EditText) po_qty_button.findViewById(R.id.chyawanprash_bar);
            chyawanprash_bar_diff = (TextView) po_qty_button.findViewById(R.id.chyawanprash_bar_diff);
            llorange_box = (LinearLayout) po_qty_button.findViewById(R.id.llorange_box);
            orange_box_qty = (TextView) po_qty_button.findViewById(R.id.orange_box_qty);
            orange_box = (EditText) po_qty_button.findViewById(R.id.orange_box);
            orange_box_diff = (TextView) po_qty_button.findViewById(R.id.orange_box_diff);
            llbutterscotch_box = (LinearLayout) po_qty_button.findViewById(R.id.llbutterscotch_box);
            butterscotch_box_qty = (TextView) po_qty_button.findViewById(R.id.butterscotch_box_qty);
            butterscotch_box = (EditText) po_qty_button.findViewById(R.id.butterscotch_box);
            butterscotch_box_diff = (TextView) po_qty_button.findViewById(R.id.butterscotch_box_diff);
            llchocopeanut_box = (LinearLayout) po_qty_button.findViewById(R.id.llchocopeanut_box);
            chocopeanut_box_qty = (TextView) po_qty_button.findViewById(R.id.chocopeanut_box_qty);
            chocopeanut_box = (EditText) po_qty_button.findViewById(R.id.chocopeanut_box);
            chocopeanut_box_diff = (TextView) po_qty_button.findViewById(R.id.chocopeanut_box_diff);
            llbambaiyachaat_box = (LinearLayout) po_qty_button.findViewById(R.id.llbambaiyachaat_box);
            bambaiyachaat_box_qty = (TextView) po_qty_button.findViewById(R.id.bambaiyachaat_box_qty);
            bambaiyachaat_box = (EditText) po_qty_button.findViewById(R.id.bambaiyachaat_box);
            bambaiyachaat_box_diff = (TextView) po_qty_button.findViewById(R.id.bambaiyachaat_box_diff);
            llmangoginger_box = (LinearLayout) po_qty_button.findViewById(R.id.llmangoginger_box);
            mangoginger_box_qty = (TextView) po_qty_button.findViewById(R.id.mangoginger_box_qty);
            mangoginger_box = (EditText) po_qty_button.findViewById(R.id.mangoginger_box);
            mangoginger_box_diff = (TextView) po_qty_button.findViewById(R.id.mangoginger_box_diff);
            llberry_blast_box = (LinearLayout) po_qty_button.findViewById(R.id.llberry_blast_box);
            berry_blast_box_qty = (TextView) po_qty_button.findViewById(R.id.berry_blast_box_qty);
            berry_blast_box = (EditText) po_qty_button.findViewById(R.id.berry_blast_box);
            berry_blast_box_diff = (TextView) po_qty_button.findViewById(R.id.berry_blast_box_diff);
            llchyawanprash_box = (LinearLayout) po_qty_button.findViewById(R.id.llchyawanprash_box);
            chyawanprash_box_qty = (TextView) po_qty_button.findViewById(R.id.chyawanprash_box_qty);
            chyawanprash_box = (EditText) po_qty_button.findViewById(R.id.chyawanprash_box);
            chyawanprash_box_diff = (TextView) po_qty_button.findViewById(R.id.chyawanprash_box_diff);
            llvariety_box = (LinearLayout) po_qty_button.findViewById(R.id.llvariety_box);
            variety_box_qty = (TextView) po_qty_button.findViewById(R.id.variety_box_qty);
            variety_box = (EditText) po_qty_button.findViewById(R.id.variety_box);
            variety_box_diff = (TextView) po_qty_button.findViewById(R.id.variety_box_diff);
            llchocolate_cookies = (LinearLayout) po_qty_button.findViewById(R.id.llchocolate_cookies);
            chocolate_cookies_qty = (TextView) po_qty_button.findViewById(R.id.chocolate_cookies_qty);
            chocolate_cookies = (EditText) po_qty_button.findViewById(R.id.chocolate_cookies);
            chocolate_cookies_diff = (TextView) po_qty_button.findViewById(R.id.chocolate_cookies_diff);
            lldark_chocolate_cookies = (LinearLayout) po_qty_button.findViewById(R.id.lldark_chocolate_cookies);
            dark_chocolate_cookies_qty = (TextView) po_qty_button.findViewById(R.id.dark_chocolate_cookies_qty);
            dark_chocolate_cookies = (EditText) po_qty_button.findViewById(R.id.dark_chocolate_cookies);
            dark_chocolate_cookies_diff = (TextView) po_qty_button.findViewById(R.id.dark_chocolate_cookies_diff);
            llcranberry_cookies = (LinearLayout) po_qty_button.findViewById(R.id.llcranberry_cookies);
            cranberry_cookies_qty = (TextView) po_qty_button.findViewById(R.id.cranberry_cookies_qty);
            cranberry_cookies = (EditText) po_qty_button.findViewById(R.id.cranberry_cookies);
            cranberry_cookies_diff = (TextView) po_qty_button.findViewById(R.id.cranberry_cookies_diff);
            llcranberry_orange_zest = (LinearLayout) po_qty_button.findViewById(R.id.llcranberry_orange_zest);
            cranberry_orange_zest_qty = (TextView) po_qty_button.findViewById(R.id.cranberry_orange_zest_qty);
            cranberry_orange_zest = (EditText) po_qty_button.findViewById(R.id.cranberry_orange_zest);
            cranberry_orange_zest_diff = (TextView) po_qty_button.findViewById(R.id.cranberry_orange_zest_diff);
            llfig_raisins = (LinearLayout) po_qty_button.findViewById(R.id.llfig_raisins);
            fig_raisins_qty = (TextView) po_qty_button.findViewById(R.id.fig_raisins_qty);
            fig_raisins = (EditText) po_qty_button.findViewById(R.id.fig_raisins);
            fig_raisins_diff = (TextView) po_qty_button.findViewById(R.id.fig_raisins_diff);
            llpapaya_pineapple = (LinearLayout) po_qty_button.findViewById(R.id.llpapaya_pineapple);
            papaya_pineapple_qty = (TextView) po_qty_button.findViewById(R.id.papaya_pineapple_qty);
            papaya_pineapple = (EditText) po_qty_button.findViewById(R.id.papaya_pineapple);
            papaya_pineapple_diff = (TextView) po_qty_button.findViewById(R.id.papaya_pineapple_diff);

            po_number_spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    StringWithTag1 swt = (StringWithTag1) parent.getItemAtPosition(position);
                    Integer key2 = (Integer) swt.tag;
                    String key1 = "" + key2;
                    intpo_id = key2;
                    //Toast.makeText(context,key1,Toast.LENGTH_LONG).show();
                    try {
                        String response = get_po_data(zone_id_val, store_id_val, location_id_val, key1);
                        JSONArray data = new JSONArray(response);
                        //Toast.makeText(context,""+data.length(),Toast.LENGTH_LONG).show();

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject data1 = data.getJSONObject(i);

                            text = data1.getString("orange_bar_qty");
                            if (text.matches("\\d+")) {
                                intorange_bar_qty = Integer.parseInt(text);
                            }
                            if (intorange_bar_qty > 0) {
                                orange_bar_qty.setText(intorange_bar_qty.toString());
                                orange_bar_diff.setText(intorange_bar_qty.toString());
                                orange_bar.setText(data1.getString("orange_bar_physical_qty"));
                                llorange_bar.setVisibility(View.VISIBLE);
                            } else {
                                llorange_bar.setVisibility(View.GONE);
                            }
                            text = data1.getString("butterscotch_bar_qty");
                            if (text.matches("\\d+")) {
                                intbutterscotch_bar_qty = Integer.parseInt(text);
                            }
                            if (intbutterscotch_bar_qty > 0) {
                                butterscotch_bar_qty.setText(intbutterscotch_bar_qty.toString());
                                butterscotch_bar_diff.setText(intbutterscotch_bar_qty.toString());
                                butterscotch_bar.setText(data1.getString("butterscotch_bar_physical_qty"));
                                llbutterscotch_bar.setVisibility(View.VISIBLE);
                            } else {
                                llbutterscotch_bar.setVisibility(View.GONE);
                            }
                            text = data1.getString("chocopeanut_bar_qty");
                            if (text.matches("\\d+")) {
                                intchocopeanut_bar_qty = Integer.parseInt(text);
                            }
                            if (intchocopeanut_bar_qty > 0) {
                                chocopeanut_bar_qty.setText(intchocopeanut_bar_qty.toString());
                                chocopeanut_bar_diff.setText(intchocopeanut_bar_qty.toString());
                                chocopeanut_bar.setText(data1.getString("chocopeanut_bar_physical_qty"));
                                llchocopeanut_bar.setVisibility(View.VISIBLE);
                            } else {
                                llchocopeanut_bar.setVisibility(View.GONE);
                            }
                            text = data1.getString("bambaiyachaat_bar_qty");
                            if (text.matches("\\d+")) {
                                intbambaiyachaat_bar_qty = Integer.parseInt(text);
                            }
                            if (intbambaiyachaat_bar_qty > 0) {
                                bambaiyachaat_bar_qty.setText(intbambaiyachaat_bar_qty.toString());
                                bambaiyachaat_bar_diff.setText(intbambaiyachaat_bar_qty.toString());
                                bambaiyachaat_bar.setText(data1.getString("bambaiyachaat_bar_physical_qty"));
                                llbambaiyachaat_bar.setVisibility(View.VISIBLE);
                            } else {
                                llbambaiyachaat_bar.setVisibility(View.GONE);
                            }
                            text = data1.getString("mangoginger_bar_qty");
                            if (text.matches("\\d+")) {
                                intmangoginger_bar_qty = Integer.parseInt(text);
                            }
                            if (intmangoginger_bar_qty > 0) {
                                mangoginger_bar_qty.setText(intmangoginger_bar_qty.toString());
                                mangoginger_bar_diff.setText(intmangoginger_bar_qty.toString());
                                mangoginger_bar.setText(data1.getString("mangoginger_bar_physical_qty"));
                                llmangoginger_bar.setVisibility(View.VISIBLE);
                            } else {
                                llmangoginger_bar.setVisibility(View.GONE);
                            }
                            text = data1.getString("berry_blast_bar_qty");
                            if (text.matches("\\d+")) {
                                intberry_blast_bar_qty = Integer.parseInt(text);
                            }
                            if (intberry_blast_bar_qty > 0) {
                                berry_blast_bar_qty.setText(intberry_blast_bar_qty.toString());
                                berry_blast_bar_diff.setText(intberry_blast_bar_qty.toString());
                                berry_blast_bar.setText(data1.getString("berry_blast_bar_physical_qty"));
                                llberry_blast_bar.setVisibility(View.VISIBLE);
                            } else {
                                llberry_blast_bar.setVisibility(View.GONE);
                            }
                            text = data1.getString("chyawanprash_bar_qty");
                            if (text.matches("\\d+")) {
                                intchyawanprash_bar_qty = Integer.parseInt(text);
                            }
                            if (intchyawanprash_bar_qty > 0) {
                                chyawanprash_bar_qty.setText(intchyawanprash_bar_qty.toString());
                                chyawanprash_bar_diff.setText(intchyawanprash_bar_qty.toString());
                                chyawanprash_bar.setText(data1.getString("chyawanprash_bar_physical_qty"));
                                llchyawanprash_bar.setVisibility(View.VISIBLE);
                            } else {
                                llchyawanprash_bar.setVisibility(View.GONE);
                            }
                            text = data1.getString("orange_box_qty");
                            if (text.matches("\\d+")) {
                                intorange_box_qty = Integer.parseInt(text);
                            }
                            if (intorange_box_qty > 0) {
                                orange_box_qty.setText(intorange_box_qty.toString());
                                orange_box_diff.setText(intorange_box_qty.toString());
                                orange_box.setText(data1.getString("orange_box_physical_qty"));
                                llorange_box.setVisibility(View.VISIBLE);
                            } else {
                                llorange_box.setVisibility(View.GONE);
                            }
                            text = data1.getString("butterscotch_box_qty");
                            if (text.matches("\\d+")) {
                                intbutterscotch_box_qty = Integer.parseInt(text);
                            }
                            if (intbutterscotch_box_qty > 0) {
                                butterscotch_box_qty.setText(intbutterscotch_box_qty.toString());
                                butterscotch_box_diff.setText(intbutterscotch_box_qty.toString());
                                butterscotch_box.setText(data1.getString("butterscotch_box_physical_qty"));
                                llbutterscotch_box.setVisibility(View.VISIBLE);
                            } else {
                                llbutterscotch_box.setVisibility(View.GONE);
                            }
                            text = data1.getString("chocopeanut_box_qty");
                            if (text.matches("\\d+")) {
                                intchocopeanut_box_qty = Integer.parseInt(text);
                            }
                            if (intchocopeanut_box_qty > 0) {
                                chocopeanut_box_qty.setText(intchocopeanut_box_qty.toString());
                                chocopeanut_box_diff.setText(intchocopeanut_box_qty.toString());
                                chocopeanut_box.setText(data1.getString("chocopeanut_box_physical_qty"));
                                llchocopeanut_box.setVisibility(View.VISIBLE);
                            } else {
                                llchocopeanut_box.setVisibility(View.GONE);
                            }
                            text = data1.getString("bambaiyachaat_box_qty");
                            if (text.matches("\\d+")) {
                                intbambaiyachaat_box_qty = Integer.parseInt(text);
                            }
                            if (intbambaiyachaat_box_qty > 0) {
                                bambaiyachaat_box_qty.setText(intbambaiyachaat_box_qty.toString());
                                bambaiyachaat_box_diff.setText(intbambaiyachaat_box_qty.toString());
                                bambaiyachaat_box.setText(data1.getString("bambaiyachaat_box_physical_qty"));
                                llbambaiyachaat_box.setVisibility(View.VISIBLE);
                            } else {
                                llbambaiyachaat_box.setVisibility(View.GONE);
                            }
                            text = data1.getString("mangoginger_box_qty");
                            if (text.matches("\\d+")) {
                                intmangoginger_box_qty = Integer.parseInt(text);
                            }
                            if (intmangoginger_box_qty > 0) {
                                mangoginger_box_qty.setText(intmangoginger_box_qty.toString());
                                mangoginger_box_diff.setText(intmangoginger_box_qty.toString());
                                mangoginger_box.setText(data1.getString("mangoginger_box_physical_qty"));
                                llmangoginger_box.setVisibility(View.VISIBLE);
                            } else {
                                llmangoginger_box.setVisibility(View.GONE);
                            }
                            text = data1.getString("berry_blast_box_qty");
                            if (text.matches("\\d+")) {
                                intberry_blast_box_qty = Integer.parseInt(text);
                            }
                            if (intberry_blast_box_qty > 0) {
                                berry_blast_box_qty.setText(intberry_blast_box_qty.toString());
                                berry_blast_box_diff.setText(intberry_blast_box_qty.toString());
                                berry_blast_box.setText(data1.getString("berry_blast_box_physical_qty"));
                                llberry_blast_box.setVisibility(View.VISIBLE);
                            } else {
                                llberry_blast_box.setVisibility(View.GONE);
                            }
                            text = data1.getString("chyawanprash_box_qty");
                            if (text.matches("\\d+")) {
                                intchyawanprash_box_qty = Integer.parseInt(text);
                            }
                            if (intchyawanprash_box_qty > 0) {
                                chyawanprash_box_qty.setText(intchyawanprash_box_qty.toString());
                                chyawanprash_box_diff.setText(intchyawanprash_box_qty.toString());
                                chyawanprash_box.setText(data1.getString("chyawanprash_box_physical_qty"));
                                llchyawanprash_box.setVisibility(View.VISIBLE);
                            } else {
                                llchyawanprash_box.setVisibility(View.GONE);
                            }
                            text = data1.getString("variety_box_qty");
                            if (text.matches("\\d+")) {
                                intvariety_box_qty = Integer.parseInt(text);
                            }
                            if (intvariety_box_qty > 0) {
                                variety_box_qty.setText(intvariety_box_qty.toString());
                                variety_box_diff.setText(intvariety_box_qty.toString());
                                variety_box.setText(data1.getString("variety_box_physical_qty"));
                                llvariety_box.setVisibility(View.VISIBLE);
                            } else {
                                llvariety_box.setVisibility(View.GONE);
                            }
                            text = data1.getString("chocolate_cookies_qty");
                            if (text.matches("\\d+")) {
                                intchocolate_cookies_qty = Integer.parseInt(text);
                            }
                            if (intchocolate_cookies_qty > 0) {
                                chocolate_cookies_qty.setText(intchocolate_cookies_qty.toString());
                                chocolate_cookies_diff.setText(intchocolate_cookies_qty.toString());
                                chocolate_cookies.setText(data1.getString("chocolate_cookies_physical_qty"));
                                llchocolate_cookies.setVisibility(View.VISIBLE);
                            } else {
                                llchocolate_cookies.setVisibility(View.GONE);
                            }
                            text = data1.getString("dark_chocolate_cookies_qty");
                            if (text.matches("\\d+")) {
                                intdark_chocolate_cookies_qty = Integer.parseInt(text);
                            }
                            if (intdark_chocolate_cookies_qty > 0) {
                                dark_chocolate_cookies_qty.setText(intdark_chocolate_cookies_qty.toString());
                                dark_chocolate_cookies_diff.setText(intdark_chocolate_cookies_qty.toString());
                                dark_chocolate_cookies.setText(data1.getString("dark_chocolate_cookies_physical_qty"));
                                lldark_chocolate_cookies.setVisibility(View.VISIBLE);
                            } else {
                                lldark_chocolate_cookies.setVisibility(View.GONE);
                            }
                            text = data1.getString("cranberry_cookies_qty");
                            if (text.matches("\\d+")) {
                                intcranberry_cookies_qty = Integer.parseInt(text);
                            }
                            if (intcranberry_cookies_qty > 0) {
                                cranberry_cookies_qty.setText(intcranberry_cookies_qty.toString());
                                cranberry_cookies_diff.setText(intcranberry_cookies_qty.toString());
                                cranberry_cookies.setText(data1.getString("cranberry_cookies_physical_qty"));
                                llcranberry_cookies.setVisibility(View.VISIBLE);
                            } else {
                                llcranberry_cookies.setVisibility(View.GONE);
                            }
                            text = data1.getString("cranberry_orange_zest_qty");
                            if (text.matches("\\d+")) {
                                intcranberry_orange_zest_qty = Integer.parseInt(text);
                            }
                            if (intcranberry_orange_zest_qty > 0) {
                                cranberry_orange_zest_qty.setText(intcranberry_orange_zest_qty.toString());
                                cranberry_orange_zest_diff.setText(intcranberry_orange_zest_qty.toString());
                                cranberry_orange_zest.setText(data1.getString("cranberry_orange_zest_physical_qty"));
                                llcranberry_orange_zest.setVisibility(View.VISIBLE);
                            } else {
                                llcranberry_orange_zest.setVisibility(View.GONE);
                            }
                            text = data1.getString("fig_raisins_qty");
                            if (text.matches("\\d+")) {
                                intfig_raisins_qty = Integer.parseInt(text);
                            }
                            if (intfig_raisins_qty > 0) {
                                fig_raisins_qty.setText(intfig_raisins_qty.toString());
                                fig_raisins_diff.setText(intfig_raisins_qty.toString());
                                fig_raisins.setText(data1.getString("fig_raisins_physical_qty"));
                                llfig_raisins.setVisibility(View.VISIBLE);
                            } else {
                                llfig_raisins.setVisibility(View.GONE);
                            }
                            text = data1.getString("papaya_pineapple_qty");
                            if (text.matches("\\d+")) {
                                intpapaya_pineapple_qty = Integer.parseInt(text);
                            }
                            if (intpapaya_pineapple_qty > 0) {
                                papaya_pineapple_qty.setText(intpapaya_pineapple_qty.toString());
                                papaya_pineapple_diff.setText(intpapaya_pineapple_qty.toString());
                                papaya_pineapple.setText(data1.getString("papaya_pineapple_physical_qty"));
                                llpapaya_pineapple.setVisibility(View.VISIBLE);
                            } else {
                                llpapaya_pineapple.setVisibility(View.GONE);
                            }
                        }
                    } catch (Exception ex) {
                        //Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            if (po_number_spn.getAdapter().getCount() > 0) {
                po_number_spn.setSelection(1, true);
            }


            orange_bar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    text = orange_bar.getText().toString();
                    intorange_bar = 0;
                    if (text.matches("\\d+")) {
                        intorange_bar = Integer.parseInt(text);
                    }
                    intorange_bar_diff = intorange_bar_qty - intorange_bar;
                    orange_bar_diff.setText(intorange_bar_diff.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            butterscotch_bar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    text = butterscotch_bar.getText().toString();
                    intbutterscotch_bar = 0;
                    if (text.matches("\\d+")) {
                        intbutterscotch_bar = Integer.parseInt(text);
                    }
                    intbutterscotch_bar_diff = intbutterscotch_bar_qty - intbutterscotch_bar;
                    butterscotch_bar_diff.setText(intbutterscotch_bar_diff.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            chocopeanut_bar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    text = chocopeanut_bar.getText().toString();
                    intchocopeanut_bar = 0;
                    if (text.matches("\\d+")) {
                        intchocopeanut_bar = Integer.parseInt(text);
                    }
                    intchocopeanut_bar_diff = intchocopeanut_bar_qty - intchocopeanut_bar;
                    chocopeanut_bar_diff.setText(intchocopeanut_bar_diff.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            bambaiyachaat_bar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    text = bambaiyachaat_bar.getText().toString();
                    intbambaiyachaat_bar = 0;
                    if (text.matches("\\d+")) {
                        intbambaiyachaat_bar = Integer.parseInt(text);
                    }
                    intbambaiyachaat_bar_diff = intbambaiyachaat_bar_qty - intbambaiyachaat_bar;
                    bambaiyachaat_bar_diff.setText(intbambaiyachaat_bar_diff.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            mangoginger_bar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    text = mangoginger_bar.getText().toString();
                    intmangoginger_bar = 0;
                    if (text.matches("\\d+")) {
                        intmangoginger_bar = Integer.parseInt(text);
                    }
                    intmangoginger_bar_diff = intmangoginger_bar_qty - intmangoginger_bar;
                    mangoginger_bar_diff.setText(intmangoginger_bar_diff.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            berry_blast_bar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    text = berry_blast_bar.getText().toString();
                    intberry_blast_bar = 0;
                    if (text.matches("\\d+")) {
                        intberry_blast_bar = Integer.parseInt(text);
                    }
                    intberry_blast_bar_diff = intberry_blast_bar_qty - intberry_blast_bar;
                    berry_blast_bar_diff.setText(intberry_blast_bar_diff.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            chyawanprash_bar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    text = chyawanprash_bar.getText().toString();
                    intchyawanprash_bar = 0;
                    if (text.matches("\\d+")) {
                        intchyawanprash_bar = Integer.parseInt(text);
                    }
                    intchyawanprash_bar_diff = intchyawanprash_bar_qty - intchyawanprash_bar;
                    chyawanprash_bar_diff.setText(intchyawanprash_bar_diff.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            orange_box.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    text = orange_box.getText().toString();
                    intorange_box = 0;
                    if (text.matches("\\d+")) {
                        intorange_box = Integer.parseInt(text);
                    }
                    intorange_box_diff = intorange_box_qty - intorange_box;
                    orange_box_diff.setText(intorange_box_diff.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            butterscotch_box.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    text = butterscotch_box.getText().toString();
                    intbutterscotch_box = 0;
                    if (text.matches("\\d+")) {
                        intbutterscotch_box = Integer.parseInt(text);
                    }
                    intbutterscotch_box_diff = intbutterscotch_box_qty - intbutterscotch_box;
                    butterscotch_box_diff.setText(intbutterscotch_box_diff.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            chocopeanut_box.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    text = chocopeanut_box.getText().toString();
                    intchocopeanut_box = 0;
                    if (text.matches("\\d+")) {
                        intchocopeanut_box = Integer.parseInt(text);
                    }
                    intchocopeanut_box_diff = intchocopeanut_box_qty - intchocopeanut_box;
                    chocopeanut_box_diff.setText(intchocopeanut_box_diff.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            bambaiyachaat_box.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    text = bambaiyachaat_box.getText().toString();
                    intbambaiyachaat_box = 0;
                    if (text.matches("\\d+")) {
                        intbambaiyachaat_box = Integer.parseInt(text);
                    }
                    intbambaiyachaat_box_diff = intbambaiyachaat_box_qty - intbambaiyachaat_box;
                    bambaiyachaat_box_diff.setText(intbambaiyachaat_box_diff.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            mangoginger_box.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    text = mangoginger_box.getText().toString();
                    intmangoginger_box = 0;
                    if (text.matches("\\d+")) {
                        intmangoginger_box = Integer.parseInt(text);
                    }
                    intmangoginger_box_diff = intmangoginger_box_qty - intmangoginger_box;
                    mangoginger_box_diff.setText(intmangoginger_box_diff.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            berry_blast_box.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    text = berry_blast_box.getText().toString();
                    intberry_blast_box = 0;
                    if (text.matches("\\d+")) {
                        intberry_blast_box = Integer.parseInt(text);
                    }
                    intberry_blast_box_diff = intberry_blast_box_qty - intberry_blast_box;
                    berry_blast_box_diff.setText(intberry_blast_box_diff.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            chyawanprash_box.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    text = chyawanprash_box.getText().toString();
                    intchyawanprash_box = 0;
                    if (text.matches("\\d+")) {
                        intchyawanprash_box = Integer.parseInt(text);
                    }
                    intchyawanprash_box_diff = intchyawanprash_box_qty - intchyawanprash_box;
                    chyawanprash_box_diff.setText(intchyawanprash_box_diff.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            variety_box.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    text = variety_box.getText().toString();
                    intvariety_box = 0;
                    if (text.matches("\\d+")) {
                        intvariety_box = Integer.parseInt(text);
                    }
                    intvariety_box_diff = intvariety_box_qty - intvariety_box;
                    variety_box_diff.setText(intvariety_box_diff.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            chocolate_cookies.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    text = chocolate_cookies.getText().toString();
                    intchocolate_cookies = 0;
                    if (text.matches("\\d+")) {
                        intchocolate_cookies = Integer.parseInt(text);
                    }
                    intchocolate_cookies_diff = intchocolate_cookies_qty - intchocolate_cookies;
                    chocolate_cookies_diff.setText(intchocolate_cookies_diff.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            dark_chocolate_cookies.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    text = dark_chocolate_cookies.getText().toString();
                    intdark_chocolate_cookies = 0;
                    if (text.matches("\\d+")) {
                        intdark_chocolate_cookies = Integer.parseInt(text);
                    }
                    intdark_chocolate_cookies_diff = intdark_chocolate_cookies_qty - intdark_chocolate_cookies;
                    dark_chocolate_cookies_diff.setText(intdark_chocolate_cookies_diff.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            cranberry_cookies.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    text = cranberry_cookies.getText().toString();
                    intcranberry_cookies = 0;
                    if (text.matches("\\d+")) {
                        intcranberry_cookies = Integer.parseInt(text);
                    }
                    intcranberry_cookies_diff = intcranberry_cookies_qty - intcranberry_cookies;
                    cranberry_cookies_diff.setText(intcranberry_cookies_diff.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            cranberry_orange_zest.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    text = cranberry_orange_zest.getText().toString();
                    intcranberry_orange_zest = 0;
                    if (text.matches("\\d+")) {
                        intcranberry_orange_zest = Integer.parseInt(text);
                    }
                    intcranberry_orange_zest_diff = intcranberry_orange_zest_qty - intcranberry_orange_zest;
                    cranberry_orange_zest_diff.setText(intcranberry_orange_zest_diff.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            fig_raisins.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    text = fig_raisins.getText().toString();
                    intfig_raisins = 0;
                    if (text.matches("\\d+")) {
                        intfig_raisins = Integer.parseInt(text);
                    }
                    intfig_raisins_diff = intfig_raisins_qty - intfig_raisins;
                    fig_raisins_diff.setText(intfig_raisins_diff.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            papaya_pineapple.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    text = papaya_pineapple.getText().toString();
                    intpapaya_pineapple = 0;
                    if (text.matches("\\d+")) {
                        intpapaya_pineapple = Integer.parseInt(text);
                    }
                    intpapaya_pineapple_diff = intpapaya_pineapple_qty - intpapaya_pineapple;
                    papaya_pineapple_diff.setText(intpapaya_pineapple_diff.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            // helpBuilder.setNegativeButton("Cancel",
            //         new DialogInterface.OnClickListener() {

            //             public void onClick(DialogInterface dialog, int which) {
            //                 // Do nothing but close the dialog
            //             }
            //         });

            helpBuilder.setNeutralButton("Check Qty",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                        }
                    });


            helpBuilder.setPositiveButton("Save",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                        }
                    });

            // Remember, create doesn't show the dialog
            final AlertDialog helpDialog = helpBuilder.create();
            helpDialog.show();

            helpDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    llorange_bar_qty = (LinearLayout) po_qty_button.findViewById(R.id.llorange_bar_qty);
                    llorange_bar_qty.setVisibility(View.VISIBLE);
                    llorange_bar_diff = (LinearLayout) po_qty_button.findViewById(R.id.llorange_bar_diff);
                    llorange_bar_diff.setVisibility(View.VISIBLE);
                    llbutterscotch_bar_qty = (LinearLayout) po_qty_button.findViewById(R.id.llbutterscotch_bar_qty);
                    llbutterscotch_bar_qty.setVisibility(View.VISIBLE);
                    llbutterscotch_bar_diff = (LinearLayout) po_qty_button.findViewById(R.id.llbutterscotch_bar_diff);
                    llbutterscotch_bar_diff.setVisibility(View.VISIBLE);
                    llchocopeanut_bar_qty = (LinearLayout) po_qty_button.findViewById(R.id.llchocopeanut_bar_qty);
                    llchocopeanut_bar_qty.setVisibility(View.VISIBLE);
                    llchocopeanut_bar_diff = (LinearLayout) po_qty_button.findViewById(R.id.llchocopeanut_bar_diff);
                    llchocopeanut_bar_diff.setVisibility(View.VISIBLE);
                    llbambaiyachaat_bar_qty = (LinearLayout) po_qty_button.findViewById(R.id.llbambaiyachaat_bar_qty);
                    llbambaiyachaat_bar_qty.setVisibility(View.VISIBLE);
                    llbambaiyachaat_bar_diff = (LinearLayout) po_qty_button.findViewById(R.id.llbambaiyachaat_bar_diff);
                    llbambaiyachaat_bar_diff.setVisibility(View.VISIBLE);
                    llmangoginger_bar_qty = (LinearLayout) po_qty_button.findViewById(R.id.llmangoginger_bar_qty);
                    llmangoginger_bar_qty.setVisibility(View.VISIBLE);
                    llmangoginger_bar_diff = (LinearLayout) po_qty_button.findViewById(R.id.llmangoginger_bar_diff);
                    llmangoginger_bar_diff.setVisibility(View.VISIBLE);
                    llberry_blast_bar_qty = (LinearLayout) po_qty_button.findViewById(R.id.llberry_blast_bar_qty);
                    llberry_blast_bar_qty.setVisibility(View.VISIBLE);
                    llberry_blast_bar_diff = (LinearLayout) po_qty_button.findViewById(R.id.llberry_blast_bar_diff);
                    llberry_blast_bar_diff.setVisibility(View.VISIBLE);
                    llchyawanprash_bar_qty = (LinearLayout) po_qty_button.findViewById(R.id.llchyawanprash_bar_qty);
                    llchyawanprash_bar_qty.setVisibility(View.VISIBLE);
                    llchyawanprash_bar_diff = (LinearLayout) po_qty_button.findViewById(R.id.llchyawanprash_bar_diff);
                    llchyawanprash_bar_diff.setVisibility(View.VISIBLE);
                    llorange_box_qty = (LinearLayout) po_qty_button.findViewById(R.id.llorange_box_qty);
                    llorange_box_qty.setVisibility(View.VISIBLE);
                    llorange_box_diff = (LinearLayout) po_qty_button.findViewById(R.id.llorange_box_diff);
                    llorange_box_diff.setVisibility(View.VISIBLE);
                    llbutterscotch_box_qty = (LinearLayout) po_qty_button.findViewById(R.id.llbutterscotch_box_qty);
                    llbutterscotch_box_qty.setVisibility(View.VISIBLE);
                    llbutterscotch_box_diff = (LinearLayout) po_qty_button.findViewById(R.id.llbutterscotch_box_diff);
                    llbutterscotch_box_diff.setVisibility(View.VISIBLE);
                    llchocopeanut_box_qty = (LinearLayout) po_qty_button.findViewById(R.id.llchocopeanut_box_qty);
                    llchocopeanut_box_qty.setVisibility(View.VISIBLE);
                    llchocopeanut_box_diff = (LinearLayout) po_qty_button.findViewById(R.id.llchocopeanut_box_diff);
                    llchocopeanut_box_diff.setVisibility(View.VISIBLE);
                    llbambaiyachaat_box_qty = (LinearLayout) po_qty_button.findViewById(R.id.llbambaiyachaat_box_qty);
                    llbambaiyachaat_box_qty.setVisibility(View.VISIBLE);
                    llbambaiyachaat_box_diff = (LinearLayout) po_qty_button.findViewById(R.id.llbambaiyachaat_box_diff);
                    llbambaiyachaat_box_diff.setVisibility(View.VISIBLE);
                    llmangoginger_box_qty = (LinearLayout) po_qty_button.findViewById(R.id.llmangoginger_box_qty);
                    llmangoginger_box_qty.setVisibility(View.VISIBLE);
                    llmangoginger_box_diff = (LinearLayout) po_qty_button.findViewById(R.id.llmangoginger_box_diff);
                    llmangoginger_box_diff.setVisibility(View.VISIBLE);
                    llberry_blast_box_qty = (LinearLayout) po_qty_button.findViewById(R.id.llberry_blast_box_qty);
                    llberry_blast_box_qty.setVisibility(View.VISIBLE);
                    llberry_blast_box_diff = (LinearLayout) po_qty_button.findViewById(R.id.llberry_blast_box_diff);
                    llberry_blast_box_diff.setVisibility(View.VISIBLE);
                    llchyawanprash_box_qty = (LinearLayout) po_qty_button.findViewById(R.id.llchyawanprash_box_qty);
                    llchyawanprash_box_qty.setVisibility(View.VISIBLE);
                    llchyawanprash_box_diff = (LinearLayout) po_qty_button.findViewById(R.id.llchyawanprash_box_diff);
                    llchyawanprash_box_diff.setVisibility(View.VISIBLE);
                    llvariety_box_qty = (LinearLayout) po_qty_button.findViewById(R.id.llvariety_box_qty);
                    llvariety_box_qty.setVisibility(View.VISIBLE);
                    llvariety_box_diff = (LinearLayout) po_qty_button.findViewById(R.id.llvariety_box_diff);
                    llvariety_box_diff.setVisibility(View.VISIBLE);
                    llchocolate_cookies_qty = (LinearLayout) po_qty_button.findViewById(R.id.llchocolate_cookies_qty);
                    llchocolate_cookies_qty.setVisibility(View.VISIBLE);
                    llchocolate_cookies_diff = (LinearLayout) po_qty_button.findViewById(R.id.llchocolate_cookies_diff);
                    llchocolate_cookies_diff.setVisibility(View.VISIBLE);
                    lldark_chocolate_cookies_qty = (LinearLayout) po_qty_button.findViewById(R.id.lldark_chocolate_cookies_qty);
                    lldark_chocolate_cookies_qty.setVisibility(View.VISIBLE);
                    lldark_chocolate_cookies_diff = (LinearLayout) po_qty_button.findViewById(R.id.lldark_chocolate_cookies_diff);
                    lldark_chocolate_cookies_diff.setVisibility(View.VISIBLE);
                    llcranberry_cookies_qty = (LinearLayout) po_qty_button.findViewById(R.id.llcranberry_cookies_qty);
                    llcranberry_cookies_qty.setVisibility(View.VISIBLE);
                    llcranberry_cookies_diff = (LinearLayout) po_qty_button.findViewById(R.id.llcranberry_cookies_diff);
                    llcranberry_cookies_diff.setVisibility(View.VISIBLE);
                    llcranberry_orange_zest_qty = (LinearLayout) po_qty_button.findViewById(R.id.llcranberry_orange_zest_qty);
                    llcranberry_orange_zest_qty.setVisibility(View.VISIBLE);
                    llcranberry_orange_zest_diff = (LinearLayout) po_qty_button.findViewById(R.id.llcranberry_orange_zest_diff);
                    llcranberry_orange_zest_diff.setVisibility(View.VISIBLE);
                    llfig_raisins_qty = (LinearLayout) po_qty_button.findViewById(R.id.llfig_raisins_qty);
                    llfig_raisins_qty.setVisibility(View.VISIBLE);
                    llfig_raisins_diff = (LinearLayout) po_qty_button.findViewById(R.id.llfig_raisins_diff);
                    llfig_raisins_diff.setVisibility(View.VISIBLE);
                    llpapaya_pineapple_qty = (LinearLayout) po_qty_button.findViewById(R.id.llpapaya_pineapple_qty);
                    llpapaya_pineapple_qty.setVisibility(View.VISIBLE);
                    llpapaya_pineapple_diff = (LinearLayout) po_qty_button.findViewById(R.id.llpapaya_pineapple_diff);
                    llpapaya_pineapple_diff.setVisibility(View.VISIBLE);
                }
            });

            helpDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        text = savePoData();

                        // Toast.makeText(po_qty_button.getContext(),"testing save po "+text,Toast.LENGTH_LONG).show();

                        if (text.equals("1")) {
                            showPopUp3();
                        }

                        helpDialog.dismiss();
                        get_pending_po_nos();
                    } catch (Exception ex) {
                        //Toast.makeText(context,"else "+ex.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            });

        } catch (Exception ex) {
            //Toast.makeText(context,"else "+ex.toString(),Toast.LENGTH_LONG).show();
        }
    }

    private void showPopUp3() {
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View report_to_manager = inflater.inflate(R.layout.alertdialog_report_to_manager, null);
        helpBuilder.setView(report_to_manager);
        //Button btnDatePicker = (Button)
        helpBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();

        helpDialog.show();
    }

    private void showpermanant_route_popup() {
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View permanent_route = inflater.inflate(R.layout.activity_permanent_route, null);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) permanent_route);
        helpBuilder.setView(permanent_route);
        //Button btnDatePicker = (Button)
        helpBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            View_load_dialog.showDialog();

                            stris_permanent = "Yes";
                            saveData(strsales_rep_id, strchannel_type, strdistributor_type, strdistributor_id, strstore_id, strzone_id, strarea_id, strlocation_id, strdistributor_name, strremarks, strreation_id, strorange_bar, strorange_box, strbutterscotch_bar, strbutterscotch_box, strchocopeanut_bar, strchocopeanut_box, strbambaiyachaat_bar, strbambaiyachaat_box, strmangoginger_bar, strmangoginger_box, strberry_blast_bar, strberry_blast_box, strchyawanprash_bar, strchyawanprash_box, strvariety_box, strchocolate_cookies, strdark_chocolate_cookies, strcranberry_cookies, strfig_raisins, strpapaya_pineapple, strcranberry_orange_zest, follow_type, "", "Save", id, bit_plan_id, sequence, mid, sales_rep_loc_id, merchandiser_stock_id, stris_permanent);
                            Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show();
                            OrdersData od = new OrdersData();
                            od.clear();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent i = new Intent(context, Visit_list_pageActivity.class);
                                    startActivityForResult(i, 1);
                                    finish();
                                }
                            }, 500);
                        } catch (Exception ex) {
                            Toast.makeText(context, "f \n" + ex.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

        helpBuilder.setNeutralButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            View_load_dialog.showDialog();

                            stris_permanent = "No";
                            saveData(strsales_rep_id, strchannel_type, strdistributor_type, strdistributor_id, strstore_id, strzone_id, strarea_id, strlocation_id, strdistributor_name, strremarks, strreation_id, strorange_bar, strorange_box, strbutterscotch_bar, strbutterscotch_box, strchocopeanut_bar, strchocopeanut_box, strbambaiyachaat_bar, strbambaiyachaat_box, strmangoginger_bar, strmangoginger_box, strberry_blast_bar, strberry_blast_box, strchyawanprash_bar, strchyawanprash_box, strvariety_box, strchocolate_cookies, strdark_chocolate_cookies, strcranberry_cookies, strfig_raisins, strpapaya_pineapple, strcranberry_orange_zest, follow_type, "", "Save", id, bit_plan_id, sequence, mid, sales_rep_loc_id, merchandiser_stock_id, stris_permanent);

                            Toast.makeText(context, "Data Saved", Toast.LENGTH_LONG).show();
                            OrdersData od = new OrdersData();
                            od.clear();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent i = new Intent(context, Visit_list_pageActivity.class);
                                    startActivityForResult(i, 1);
                                    finish();
                                }
                            }, 500);
                        } catch (Exception ex) {
                            //Toast.makeText(context, "f \n" + ex.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
        helpBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(context, Visit_list_pageActivity.class);
                        startActivityForResult(i, 1);
                        finish();
                    }
                }, 500);
            }
        });
        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();

        helpDialog.show();
    }

    private void showPopUp1() {

        if (flag == 1) {
            Log.e("visit", "zone: " + zone.getSelectedItemPosition() + "");
            if (zone.getSelectedItemPosition() <= 0) {
                //zone.setError
                setSpinnerError(zone, "Required");
                vflag = 0;
            } else {
                vflag = 1;
            }


            if (channel.getSelectedItem().toString().equals("GT")) {
                if (area.getSelectedItemPosition() <= 0) {
                    //zone.setError
                    setSpinnerError(area, "Required");
                    vflag = 0;
                } else {
                    vflag = 1;
                }

                if (location.getSelectedItemPosition() <= 0) {
                    //zone.setError
                    setSpinnerError(location, "Required");
                    vflag = 0;
                } else {
                    vflag = 1;
                }

                if (type.getSelectedItem().toString().equals("Old")) {
                    if (retailer.getSelectedItemPosition() <= 0) {
                        setSpinnerError(retailer, "Required");
                        vflag = 0;
                    } else {
                        vflag = 1;
                    }
                } else {
                    if (txtretailer.getText().toString().equals("")) {
                        txtretailer.setError("Required");
                        focusOnViewEdit(txtretailer);
                        vflag = 0;
                    } else {
                        //vflag = 1;
                    }
                }

            } else {
                if (relation.getSelectedItemPosition() <= 0) {

                    setSpinnerError(relation, "Required");
                    vflag = 0;
                } else {
                    vflag = 1;
                }

                if (location.getSelectedItemPosition() <= 0) {
                    setSpinnerError(location, "Required");
                    vflag = 0;
                } else {
                    vflag = 1;
                }

            }
        } else {
            if (channel1.equals("GT")) {
                if (type1.equals("New")) {
                    if (txtretailer1.getText().toString().equals("")) {
                        txtretailer1.setError("Required");
                        focusOnViewEdit(txtretailer1);
                        vflag = 0;
                        //Toast.makeText(context, "d " + vflag, Toast.LENGTH_LONG).show();
                    } else {
                        vflag = 1;
                        //Toast.makeText(context, "" + vflag, Toast.LENGTH_LONG).show();
                    }
                } else {
                    vflag = 1;
                }
            } else {
                vflag = 1;
            }
        }


        if (vflag == 1) {
            String orange_bar = "", orange_box = "", butterscotch_bar = "", butterscotch_box = "", chocopeanut_bar = "", bambaiyachaat_bar = "", mangoginger_bar = "", berry_blast_bar = "", chyawanprash_bar = "", chocopeanut_box = "", bambaiyachaat_box = "", mangoginger_box = "", berry_blast_box = "", chyawanprash_box = "", variety_box = "", chocolate_cookies = "", dark_chocolate_cookies = "", cranberry_cookies = "", cranberry_orange_zest = "", fig_raisins = "", papaya_pineapple = "";

            View view = expListView.getChildAt(1);
            EditText orange_bar_editText = view.findViewById(R.id.lblListItem2);
            orange_bar = orange_bar_editText.getText().toString();

            view = expListView.getChildAt(2);
            EditText butterscotch_bar_editText = view.findViewById(R.id.lblListItem2);
            butterscotch_bar = butterscotch_bar_editText.getText().toString();

            view = expListView.getChildAt(3);
            EditText chocopeanut_bar_editText = view.findViewById(R.id.lblListItem2);
            chocopeanut_bar = chocopeanut_bar_editText.getText().toString();

            view = expListView.getChildAt(4);
            EditText bambaiyachaat_bar_editText = view.findViewById(R.id.lblListItem2);
            bambaiyachaat_bar = bambaiyachaat_bar_editText.getText().toString();

            view = expListView.getChildAt(5);
            EditText mangoginger_bar_editText = view.findViewById(R.id.lblListItem2);
            mangoginger_bar = mangoginger_bar_editText.getText().toString();

            view = expListView.getChildAt(6);
            EditText berry_blast_bar_editText = view.findViewById(R.id.lblListItem2);
            berry_blast_bar = berry_blast_bar_editText.getText().toString();

            view = expListView.getChildAt(7);
            EditText chyawanprash_bar_editText = view.findViewById(R.id.lblListItem2);
            chyawanprash_bar = chyawanprash_bar_editText.getText().toString();

            view = expListView.getChildAt(9);
            EditText orange_box_editText = view.findViewById(R.id.lblListItem2);
            orange_box = orange_box_editText.getText().toString();

            view = expListView.getChildAt(10);
            EditText butterscotch_box_editText = view.findViewById(R.id.lblListItem2);
            butterscotch_box = butterscotch_box_editText.getText().toString();

            view = expListView.getChildAt(11);
            EditText chocopeanut_box_editText = view.findViewById(R.id.lblListItem2);
            chocopeanut_box = chocopeanut_box_editText.getText().toString();

            view = expListView.getChildAt(12);
            EditText bambaiyachaat_box_editText = view.findViewById(R.id.lblListItem2);
            bambaiyachaat_box = bambaiyachaat_box_editText.getText().toString();

            view = expListView.getChildAt(13);
            EditText mangoginger_box_editText = view.findViewById(R.id.lblListItem2);
            mangoginger_box = mangoginger_box_editText.getText().toString();

            view = expListView.getChildAt(14);
            EditText berry_blast_box_editText = view.findViewById(R.id.lblListItem2);
            berry_blast_box = berry_blast_box_editText.getText().toString();

            view = expListView.getChildAt(15);
            EditText chyawanprash_box_editText = view.findViewById(R.id.lblListItem2);
            chyawanprash_box = chyawanprash_box_editText.getText().toString();

            view = expListView.getChildAt(16);
            EditText variety_box_editText = view.findViewById(R.id.lblListItem2);
            variety_box = variety_box_editText.getText().toString();

            view = expListView.getChildAt(18);
            EditText chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
            chocolate_cookies = chocolate_cookies_editText.getText().toString();

            view = expListView.getChildAt(19);
            EditText dark_chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
            dark_chocolate_cookies = dark_chocolate_cookies_editText.getText().toString();

            view = expListView.getChildAt(20);
            EditText cranberry_cookies_editText = view.findViewById(R.id.lblListItem2);
            cranberry_cookies = cranberry_cookies_editText.getText().toString();

            view = expListView.getChildAt(22);
            EditText cranberry_orange_zest_editText = view.findViewById(R.id.lblListItem2);
            cranberry_orange_zest = cranberry_orange_zest_editText.getText().toString();

            view = expListView.getChildAt(23);
            EditText fig_raisins_editText = view.findViewById(R.id.lblListItem2);
            fig_raisins = fig_raisins_editText.getText().toString();

            view = expListView.getChildAt(24);
            EditText papaya_pineapple_editText = view.findViewById(R.id.lblListItem2);
            papaya_pineapple = papaya_pineapple_editText.getText().toString();

            if (((orange_bar.equals("") || orange_bar.equals("0")) && (orange_box.equals("") || orange_box.equals("0")) && (butterscotch_bar.equals("") || butterscotch_bar.equals("0")) && (butterscotch_box.equals("") || butterscotch_box.equals("0")) && (chocopeanut_bar.equals("") || chocopeanut_bar.equals("0")) && (chocopeanut_box.equals("") || chocopeanut_box.equals("0")) && (bambaiyachaat_bar.equals("") || bambaiyachaat_bar.equals("0")) && (bambaiyachaat_box.equals("") || bambaiyachaat_box.equals("0")) && (mangoginger_bar.equals("") || mangoginger_bar.equals("0")) && (mangoginger_box.equals("") || mangoginger_box.equals("0")) && (berry_blast_bar.equals("") || berry_blast_bar.equals("0")) && (berry_blast_box.equals("") || berry_blast_box.equals("0")) && (chyawanprash_bar.equals("") || chyawanprash_bar.equals("0")) && (chyawanprash_box.equals("") || chyawanprash_box.equals("0")) && (variety_box.equals("") || variety_box.equals("0")) && (chocolate_cookies.equals("") || chocolate_cookies.equals("0")) && (dark_chocolate_cookies.equals("") || dark_chocolate_cookies.equals("0")) && (cranberry_cookies.equals("") || cranberry_cookies.equals("0")) && (fig_raisins.equals("") || fig_raisins.equals("0")) && (papaya_pineapple.equals("") || papaya_pineapple.equals("0")) && (cranberry_orange_zest.equals("") || cranberry_orange_zest.equals("0"))) && expListView.getVisibility() == View.VISIBLE) {
                showPopUp14(2);
            } else {
                placeorderPopUp();
            }

        }
    }

    private void placeorderPopUp() {
        String orange_bar = "", orange_box = "", butterscotch_bar = "", butterscotch_box = "", chocopeanut_bar = "", bambaiyachaat_bar = "", mangoginger_bar = "", berry_blast_bar = "", chyawanprash_bar = "", chocopeanut_box = "", bambaiyachaat_box = "", mangoginger_box = "", berry_blast_box = "", chyawanprash_box = "", variety_box = "", chocolate_cookies = "", dark_chocolate_cookies = "", cranberry_cookies = "", cranberry_orange_zest = "", fig_raisins = "", papaya_pineapple = "";
        String distributor_type = "";
        if (flag == 1) {
            distributor_type = type.getSelectedItem().toString();
        } else {
            distributor_type = type1.getText().toString();
        }

        boolean saveflag = true;
        try {
            if (!distributor_type.equals("New")) {


                View view = expListView.getChildAt(1);
                EditText orange_bar_editText = view.findViewById(R.id.lblListItem2);
                orange_bar = orange_bar_editText.getText().toString();

                view = expListView.getChildAt(2);
                EditText butterscotch_bar_editText = view.findViewById(R.id.lblListItem2);
                butterscotch_bar = butterscotch_bar_editText.getText().toString();

                view = expListView.getChildAt(3);
                EditText chocopeanut_bar_editText = view.findViewById(R.id.lblListItem2);
                chocopeanut_bar = chocopeanut_bar_editText.getText().toString();

                view = expListView.getChildAt(4);
                EditText bambaiyachaat_bar_editText = view.findViewById(R.id.lblListItem2);
                bambaiyachaat_bar = bambaiyachaat_bar_editText.getText().toString();

                view = expListView.getChildAt(5);
                EditText mangoginger_bar_editText = view.findViewById(R.id.lblListItem2);
                mangoginger_bar = mangoginger_bar_editText.getText().toString();

                view = expListView.getChildAt(6);
                EditText berry_blast_bar_editText = view.findViewById(R.id.lblListItem2);
                berry_blast_bar = berry_blast_bar_editText.getText().toString();

                view = expListView.getChildAt(7);
                EditText chyawanprash_bar_editText = view.findViewById(R.id.lblListItem2);
                chyawanprash_bar = chyawanprash_bar_editText.getText().toString();

                view = expListView.getChildAt(9);
                EditText orange_box_editText = view.findViewById(R.id.lblListItem2);
                orange_box = orange_box_editText.getText().toString();

                view = expListView.getChildAt(10);
                EditText butterscotch_box_editText = view.findViewById(R.id.lblListItem2);
                butterscotch_box = butterscotch_box_editText.getText().toString();

                view = expListView.getChildAt(11);
                EditText chocopeanut_box_editText = view.findViewById(R.id.lblListItem2);
                chocopeanut_box = chocopeanut_box_editText.getText().toString();

                view = expListView.getChildAt(12);
                EditText bambaiyachaat_box_editText = view.findViewById(R.id.lblListItem2);
                bambaiyachaat_box = bambaiyachaat_box_editText.getText().toString();

                view = expListView.getChildAt(13);
                EditText mangoginger_box_editText = view.findViewById(R.id.lblListItem2);
                mangoginger_box = mangoginger_box_editText.getText().toString();

                view = expListView.getChildAt(14);
                EditText berry_blast_box_editText = view.findViewById(R.id.lblListItem2);
                berry_blast_box = berry_blast_box_editText.getText().toString();

                view = expListView.getChildAt(15);
                EditText chyawanprash_box_editText = view.findViewById(R.id.lblListItem2);
                chyawanprash_box = chyawanprash_box_editText.getText().toString();

                view = expListView.getChildAt(16);
                EditText variety_box_editText = view.findViewById(R.id.lblListItem2);
                variety_box = variety_box_editText.getText().toString();

                view = expListView.getChildAt(18);
                EditText chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
                chocolate_cookies = chocolate_cookies_editText.getText().toString();

                view = expListView.getChildAt(19);
                EditText dark_chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
                dark_chocolate_cookies = dark_chocolate_cookies_editText.getText().toString();

                view = expListView.getChildAt(20);
                EditText cranberry_cookies_editText = view.findViewById(R.id.lblListItem2);
                cranberry_cookies = cranberry_cookies_editText.getText().toString();

                view = expListView.getChildAt(22);
                EditText cranberry_orange_zest_editText = view.findViewById(R.id.lblListItem2);
                cranberry_orange_zest = cranberry_orange_zest_editText.getText().toString();

                view = expListView.getChildAt(23);
                EditText fig_raisins_editText = view.findViewById(R.id.lblListItem2);
                fig_raisins = fig_raisins_editText.getText().toString();

                view = expListView.getChildAt(24);
                EditText papaya_pineapple_editText = view.findViewById(R.id.lblListItem2);
                papaya_pineapple = papaya_pineapple_editText.getText().toString();


                if ((OrdersData.chocolate_cookies_json != null || OrdersData.dark_chocolate_cookies_json != null || OrdersData.cranberry_cookies_json != null || OrdersData.cranberry_orange_json != null || OrdersData.fig_raisins_json != null || OrdersData.papaya_pineapple_json != null || OrdersData.orange_bar_json != null || OrdersData.orange_box_json != null || OrdersData.butterscotch_bar_json != null || OrdersData.butterscotch_box_json != null || OrdersData.chocopeanut_bar_json != null || OrdersData.chocopeanut_box_json != null || OrdersData.bambaiyachaat_bar_json != null || OrdersData.bambaiyachaat_box_json != null || OrdersData.mangoginger_bar_json != null || OrdersData.mangoginger_box_json != null || OrdersData.berry_blast_bar_json != null || OrdersData.berry_blast_box_json != null || OrdersData.chyawanprash_bar_json != null || OrdersData.chyawanprash_box_json != null || OrdersData.variety_box_json != null) || ((orange_bar.equals("") || orange_bar.equals("0")) && (orange_box.equals("") || orange_box.equals("0")) && (butterscotch_bar.equals("") || butterscotch_bar.equals("0")) && (butterscotch_box.equals("") || butterscotch_box.equals("0")) && (chocopeanut_bar.equals("") || chocopeanut_bar.equals("0")) && (chocopeanut_box.equals("") || chocopeanut_box.equals("0")) && (bambaiyachaat_bar.equals("") || bambaiyachaat_bar.equals("0")) && (bambaiyachaat_box.equals("") || bambaiyachaat_box.equals("0")) && (mangoginger_bar.equals("") || mangoginger_bar.equals("0")) && (mangoginger_box.equals("") || mangoginger_box.equals("0")) && (berry_blast_bar.equals("") || berry_blast_bar.equals("0")) && (berry_blast_box.equals("") || berry_blast_box.equals("0")) && (chyawanprash_bar.equals("") || chyawanprash_bar.equals("0")) && (chyawanprash_box.equals("") || chyawanprash_box.equals("0")) && (variety_box.equals("") || variety_box.equals("0")) && (chocolate_cookies.equals("") || chocolate_cookies.equals("0")) && (dark_chocolate_cookies.equals("") || dark_chocolate_cookies.equals("0")) && (cranberry_cookies.equals("") || cranberry_cookies.equals("0")) && (fig_raisins.equals("") || fig_raisins.equals("0")) && (papaya_pineapple.equals("") || papaya_pineapple.equals("0")) && (cranberry_orange_zest.equals("") || cranberry_orange_zest.equals("0")))) {

                    //Log.e("visit", "inside if false ");

                    if (OrdersData.orange_bar_json == null && (!orange_bar.equals("") && !orange_bar.equals("0"))) {
                        showPopUp7();
                        saveflag = false;
                        //Log.e("visit", "inside false");
                    }
                    if (OrdersData.butterscotch_bar_json == null && (!butterscotch_bar.equals("") && !butterscotch_bar.equals("0")) && saveflag) {
                        showPopUp7();
                        saveflag = false;
                    }
                    if (OrdersData.bambaiyachaat_bar_json == null && (!bambaiyachaat_bar.equals("") && !bambaiyachaat_bar.equals("0")) && saveflag) {
                        showPopUp7();
                        saveflag = false;
                    }
                    if (OrdersData.chocopeanut_bar_json == null && (!chocopeanut_bar.equals("") && !chocopeanut_bar.equals("0")) && saveflag) {
                        showPopUp7();
                        saveflag = false;
                    }
                    if (OrdersData.mangoginger_bar_json == null && (!mangoginger_bar.equals("") && !mangoginger_bar.equals("0")) && saveflag) {
                        showPopUp7();
                        saveflag = false;
                    }
                    if (OrdersData.berry_blast_bar_json == null && (!berry_blast_bar.equals("") && !berry_blast_bar.equals("0")) && saveflag) {
                        showPopUp7();
                        saveflag = false;
                    }
                    if (OrdersData.chyawanprash_bar_json == null && (!chyawanprash_bar.equals("") && !chyawanprash_bar.equals("0")) && saveflag) {
                        showPopUp7();
                        saveflag = false;
                    }
                    if (OrdersData.orange_box_json == null && (!orange_box.equals("") && !orange_box.equals("0")) && saveflag) {
                        showPopUp7();
                        saveflag = false;
                    }
                    if (OrdersData.butterscotch_box_json == null && (!butterscotch_box.equals("") && !butterscotch_box.equals("0")) && saveflag) {
                        showPopUp7();
                        saveflag = false;
                    }
                    if (OrdersData.bambaiyachaat_box_json == null && (!bambaiyachaat_box.equals("") && !bambaiyachaat_box.equals("0")) && saveflag) {
                        showPopUp7();
                        saveflag = false;
                    }
                    if (OrdersData.chocopeanut_box_json == null && (!chocopeanut_box.equals("") && !chocopeanut_box.equals("0")) && saveflag) {
                        showPopUp7();
                        saveflag = false;
                    }
                    if (OrdersData.mangoginger_box_json == null && (!mangoginger_box.equals("") && !mangoginger_box.equals("0")) && saveflag) {
                        showPopUp7();
                        saveflag = false;
                    }
                    if (OrdersData.berry_blast_box_json == null && (!berry_blast_box.equals("") && !berry_blast_box.equals("0")) && saveflag) {
                        showPopUp7();
                        saveflag = false;
                    }
                    if (OrdersData.chyawanprash_box_json == null && (!chyawanprash_box.equals("") && !chyawanprash_box.equals("0")) && saveflag) {
                        showPopUp7();
                        saveflag = false;
                    }
                    if (OrdersData.variety_box_json == null && (!variety_box.equals("") && !variety_box.equals("0")) && saveflag) {
                        showPopUp7();
                        saveflag = false;
                    }
                    if (OrdersData.chocolate_cookies_json == null && (!chocolate_cookies.equals("") && !chocolate_cookies.equals("0")) && saveflag) {
                        showPopUp7();
                        saveflag = false;
                    }
                    if (OrdersData.dark_chocolate_cookies_json == null && (!dark_chocolate_cookies.equals("") && !dark_chocolate_cookies.equals("0")) && saveflag) {
                        showPopUp7();
                        saveflag = false;
                    }
                    if (OrdersData.cranberry_cookies_json == null && (!cranberry_cookies.equals("") && !cranberry_cookies.equals("0")) && saveflag) {
                        showPopUp7();
                        saveflag = false;
                    }
                    if (OrdersData.cranberry_orange_json == null && (!cranberry_orange_zest.equals("") && !cranberry_orange_zest.equals("0")) && saveflag) {
                        showPopUp7();
                        saveflag = false;
                    }
                    if (OrdersData.fig_raisins_json == null && (!fig_raisins.equals("") && !fig_raisins.equals("0")) && saveflag) {
                        showPopUp7();
                        saveflag = false;
                    }
                    if (OrdersData.papaya_pineapple_json == null && (!papaya_pineapple.equals("") && !papaya_pineapple.equals("0")) && saveflag) {
                        showPopUp7();
                        saveflag = false;
                    }


                    JSONObject batch_details_json=new JSONObject();
                    JSONArray arr=new JSONArray(OrdersData.batch_no_json);

                    if (OrdersData.orange_bar_json != null && saveflag) {
                        String orange_bar_json = OrdersData.orange_bar_json;
                        JSONObject obj = new JSONObject(orange_bar_json);
                        JSONObject bar_json=new JSONObject();
                        Iterator<String> keys = obj.keys();
                        int count = 0;
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                            bar_json.put(ob.getString("batch_no"), obj.getString(key));
                            count = addStringNo(count + "", obj.getString(key));
                        }

                        if (!(count + "").equals(orange_bar)) {
                            showPopUp7();
                            Log.e("visit", "count: " + count + " orange_bar: " + orange_bar);
                            saveflag = false;
                        }else{
                            batch_details_json.put("orange_bar", bar_json.toString());
                            //Log.e("totalorange", batch_details_json.toString());
                        }
                    }

                    if (OrdersData.butterscotch_bar_json != null && saveflag) {
                        String orange_bar_json = OrdersData.butterscotch_bar_json;
                        JSONObject obj = new JSONObject(orange_bar_json);
                        JSONObject bar_json=new JSONObject();
                        Iterator<String> keys = obj.keys();
                        int count = 0;
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                            bar_json.put(ob.getString("batch_no"), obj.getString(key));
                            count = addStringNo(count + "", obj.getString(key));
                        }

                        if (!(count + "").equals(butterscotch_bar)) {
                            showPopUp7();
                            Log.e("visit", "count: " + count + " butter_bar: " + butterscotch_bar);
                            saveflag = false;
                        }else{
                            batch_details_json.put("butterscotch_bar", bar_json.toString());
                        }
                    }
                    if (OrdersData.chocopeanut_bar_json != null && saveflag) {
                        String orange_bar_json = OrdersData.chocopeanut_bar_json;
                        JSONObject obj = new JSONObject(orange_bar_json);
                        JSONObject bar_json=new JSONObject();
                        Iterator<String> keys = obj.keys();
                        int count = 0;
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                            bar_json.put(ob.getString("batch_no"), obj.getString(key));
                            count = addStringNo(count + "", obj.getString(key));
                        }

                        if (!(count + "").equals(chocopeanut_bar)) {
                            showPopUp7();
                            saveflag = false;
                        }else{
                            batch_details_json.put("chocopeanut_bar", bar_json.toString());
                        }
                    }
                    if (OrdersData.bambaiyachaat_bar_json != null && saveflag) {
                        String orange_bar_json = OrdersData.bambaiyachaat_bar_json;
                        JSONObject obj = new JSONObject(orange_bar_json);
                        JSONObject bar_json=new JSONObject();
                        Iterator<String> keys = obj.keys();
                        int count = 0;
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                            bar_json.put(ob.getString("batch_no"), obj.getString(key));
                            count = addStringNo(count + "", obj.getString(key));
                        }

                        if (!(count + "").equals(bambaiyachaat_bar)) {
                            showPopUp7();
                            saveflag = false;
                        }else{
                            batch_details_json.put("bambaiyachaat_bar", bar_json.toString());
                        }
                    }
                    if (OrdersData.mangoginger_bar_json != null && saveflag) {
                        String orange_bar_json = OrdersData.mangoginger_bar_json;
                        JSONObject obj = new JSONObject(orange_bar_json);
                        JSONObject bar_json=new JSONObject();
                        Iterator<String> keys = obj.keys();
                        int count = 0;
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                            bar_json.put(ob.getString("batch_no"), obj.getString(key));
                            count = addStringNo(count + "", obj.getString(key));
                        }

                        if (!(count + "").equals(mangoginger_bar)) {
                            //Log.e("visit", "count: " + count + " mango_bar: " + mangoginger_bar);
                            showPopUp7();
                            saveflag = false;
                        }else{
                            batch_details_json.put("mangoginger_bar", bar_json.toString());
                        }
                    }
                    if (OrdersData.berry_blast_bar_json != null && saveflag) {
                        String orange_bar_json = OrdersData.berry_blast_bar_json;
                        JSONObject obj = new JSONObject(orange_bar_json);
                        JSONObject bar_json=new JSONObject();
                        Iterator<String> keys = obj.keys();
                        int count = 0;
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                            bar_json.put(ob.getString("batch_no"), obj.getString(key));
                            count = addStringNo(count + "", obj.getString(key));
                        }

                        if (!(count + "").equals(berry_blast_bar)) {
                            showPopUp7();
                            saveflag = false;

                        }else{
                            batch_details_json.put("berry_blast_bar", bar_json.toString());
                        }
                    }
                    if (OrdersData.chyawanprash_bar_json != null && saveflag) {
                        String orange_bar_json = OrdersData.chyawanprash_bar_json;
                        JSONObject obj = new JSONObject(orange_bar_json);
                        JSONObject bar_json=new JSONObject();
                        Iterator<String> keys = obj.keys();
                        int count = 0;
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                            bar_json.put(ob.getString("batch_no"), obj.getString(key));
                            count = addStringNo(count + "", obj.getString(key));
                        }

                        if (!(count + "").equals(chyawanprash_bar)) {
                            showPopUp7();
                            saveflag = false;
                        }else{
                            batch_details_json.put("chyawanprash_bar", bar_json.toString());
                        }
                    }
                    if (OrdersData.orange_box_json != null && saveflag) {
                        String orange_bar_json = OrdersData.orange_box_json;
                        JSONObject obj = new JSONObject(orange_bar_json);
                        JSONObject bar_json=new JSONObject();
                        Iterator<String> keys = obj.keys();
                        int count = 0;
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                            bar_json.put(ob.getString("batch_no"), obj.getString(key));
                            count = addStringNo(count + "", obj.getString(key));
                        }

                        if (!(count + "").equals(orange_box)) {
                            showPopUp7();
                        }else{
                            batch_details_json.put("orange_box", bar_json.toString());
                        }
                    }
                    if (OrdersData.butterscotch_box_json != null && saveflag) {
                        String orange_bar_json = OrdersData.butterscotch_box_json;
                        JSONObject obj = new JSONObject(orange_bar_json);
                        JSONObject bar_json=new JSONObject();
                        Iterator<String> keys = obj.keys();
                        int count = 0;
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                            bar_json.put(ob.getString("batch_no"), obj.getString(key));
                            count = addStringNo(count + "", obj.getString(key));
                        }

                        if (!(count + "").equals(butterscotch_box)) {
                            showPopUp7();
                            saveflag = false;
                        }else{
                            batch_details_json.put("butterscotch_box", bar_json.toString());
                        }
                    }
                    if (OrdersData.chocopeanut_box_json != null && saveflag) {
                        String orange_bar_json = OrdersData.chocopeanut_box_json;
                        JSONObject obj = new JSONObject(orange_bar_json);
                        JSONObject bar_json=new JSONObject();
                        Iterator<String> keys = obj.keys();
                        int count = 0;
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                            bar_json.put(ob.getString("batch_no"), obj.getString(key));
                            count = addStringNo(count + "", obj.getString(key));
                        }

                        if (!(count + "").equals(chocopeanut_box)) {
                            showPopUp7();
                            saveflag = false;
                        }else{
                            batch_details_json.put("chocopeanut_box", bar_json.toString());
                        }
                    }
                    if (OrdersData.bambaiyachaat_box_json != null && saveflag) {
                        String orange_bar_json = OrdersData.bambaiyachaat_box_json;
                        JSONObject obj = new JSONObject(orange_bar_json);
                        JSONObject bar_json=new JSONObject();
                        Iterator<String> keys = obj.keys();
                        int count = 0;
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                            bar_json.put(ob.getString("batch_no"), obj.getString(key));
                            count = addStringNo(count + "", obj.getString(key));
                        }

                        if (!(count + "").equals(bambaiyachaat_box)) {
                            showPopUp7();
                            saveflag = false;
                        }else{
                            batch_details_json.put("bambaiyachaat_box", bar_json.toString());
                        }
                    }
                    if (OrdersData.mangoginger_box_json != null && saveflag) {
                        String orange_bar_json = OrdersData.mangoginger_box_json;
                        JSONObject obj = new JSONObject(orange_bar_json);
                        JSONObject bar_json=new JSONObject();
                        Iterator<String> keys = obj.keys();
                        int count = 0;
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                            bar_json.put(ob.getString("batch_no"), obj.getString(key));
                            count = addStringNo(count + "", obj.getString(key));
                        }

                        if (!(count + "").equals(mangoginger_box)) {
                            showPopUp7();
                            saveflag = false;
                        }else{
                            batch_details_json.put("mangoginger_box", bar_json.toString());
                        }
                    }
                    if (OrdersData.berry_blast_box_json != null && saveflag) {
                        String orange_bar_json = OrdersData.berry_blast_box_json;
                        JSONObject obj = new JSONObject(orange_bar_json);
                        JSONObject bar_json=new JSONObject();
                        Iterator<String> keys = obj.keys();
                        int count = 0;
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                            bar_json.put(ob.getString("batch_no"), obj.getString(key));
                            count = addStringNo(count + "", obj.getString(key));
                        }

                        if (!(count + "").equals(berry_blast_box)) {
                            showPopUp7();
                            saveflag = false;
                        }else{
                            batch_details_json.put("berry_blast_box", bar_json.toString());
                        }
                    }
                    if (OrdersData.chyawanprash_box_json != null && saveflag) {
                        String orange_bar_json = OrdersData.chyawanprash_box_json;
                        JSONObject obj = new JSONObject(orange_bar_json);
                        JSONObject bar_json=new JSONObject();
                        Iterator<String> keys = obj.keys();
                        int count = 0;
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                            bar_json.put(ob.getString("batch_no"), obj.getString(key));
                            count = addStringNo(count + "", obj.getString(key));
                        }

                        if (!(count + "").equals(chyawanprash_box)) {
                            showPopUp7();
                            saveflag = false;
                        }else{
                            batch_details_json.put("chyawanprash_box", bar_json.toString());
                        }
                    }
                    if (OrdersData.variety_box_json != null && saveflag) {
                        String orange_bar_json = OrdersData.variety_box_json;
                        JSONObject obj = new JSONObject(orange_bar_json);
                        JSONObject bar_json=new JSONObject();
                        Iterator<String> keys = obj.keys();
                        int count = 0;
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                            bar_json.put(ob.getString("batch_no"), obj.getString(key));
                            count = addStringNo(count + "", obj.getString(key));
                        }

                        if (!(count + "").equals(variety_box)) {
                            showPopUp7();
                            saveflag = false;
                        }else{
                            batch_details_json.put("variety_box", bar_json.toString());
                        }
                    }
                    if (OrdersData.chocolate_cookies_json != null && saveflag) {
                        String orange_bar_json = OrdersData.chocolate_cookies_json;
                        JSONObject obj = new JSONObject(orange_bar_json);
                        JSONObject bar_json=new JSONObject();
                        Iterator<String> keys = obj.keys();
                        int count = 0;
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                            bar_json.put(ob.getString("batch_no"), obj.getString(key));
                            count = addStringNo(count + "", obj.getString(key));
                        }

                        if (!(count + "").equals(chocolate_cookies)) {
                            Log.e("visit", "count: " + count + " choco_cook: " + chocolate_cookies);
                            showPopUp7();
                            saveflag = false;
                        }else{
                            batch_details_json.put("chocolate_cookies", bar_json.toString());
                        }
                    }
                    if (OrdersData.dark_chocolate_cookies_json != null && saveflag) {
                        String orange_bar_json = OrdersData.dark_chocolate_cookies_json;
                        JSONObject obj = new JSONObject(orange_bar_json);
                        JSONObject bar_json=new JSONObject();
                        Iterator<String> keys = obj.keys();
                        int count = 0;
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                            bar_json.put(ob.getString("batch_no"), obj.getString(key));
                            count = addStringNo(count + "", obj.getString(key));
                        }

                        if (!(count + "").equals(dark_chocolate_cookies)) {
                            showPopUp7();
                            saveflag = false;
                        }else{
                            batch_details_json.put("dark_chocolate_cookies", bar_json.toString());
                        }
                    }
                    if (OrdersData.cranberry_cookies_json != null && saveflag) {
                        String orange_bar_json = OrdersData.cranberry_cookies_json;
                        JSONObject obj = new JSONObject(orange_bar_json);
                        JSONObject bar_json=new JSONObject();
                        Iterator<String> keys = obj.keys();
                        int count = 0;
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                            bar_json.put(ob.getString("batch_no"), obj.getString(key));
                            count = addStringNo(count + "", obj.getString(key));
                        }

                        if (!(count + "").equals(cranberry_cookies)) {
                            showPopUp7();
                            saveflag = false;
                        }else{
                            batch_details_json.put("cranberry_cookies", bar_json.toString());
                        }
                    }
                    if (OrdersData.cranberry_orange_json != null && saveflag) {
                        String orange_bar_json = OrdersData.cranberry_orange_json;
                        JSONObject obj = new JSONObject(orange_bar_json);
                        JSONObject bar_json=new JSONObject();
                        Iterator<String> keys = obj.keys();
                        int count = 0;
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                            bar_json.put(ob.getString("batch_no"), obj.getString(key));
                            count = addStringNo(count + "", obj.getString(key));
                        }

                        if (!(count + "").equals(cranberry_orange_zest)) {
                            showPopUp7();
                            saveflag = false;
                        }else{
                            batch_details_json.put("cranberry_orange_zest", bar_json.toString());
                        }
                    }
                    if (OrdersData.fig_raisins_json != null && saveflag) {
                        String orange_bar_json = OrdersData.fig_raisins_json;
                        JSONObject obj = new JSONObject(orange_bar_json);
                        JSONObject bar_json=new JSONObject();
                        Iterator<String> keys = obj.keys();
                        int count = 0;
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                            bar_json.put(ob.getString("batch_no"), obj.getString(key));
                            count = addStringNo(count + "", obj.getString(key));
                        }

                        if (!(count + "").equals(fig_raisins)) {
                            showPopUp7();
                            saveflag = false;
                        }else{
                            batch_details_json.put("fig_raisins", bar_json.toString());
                        }
                    }
                    if (OrdersData.papaya_pineapple_json != null && saveflag) {
                        String orange_bar_json = OrdersData.papaya_pineapple_json;
                        JSONObject obj = new JSONObject(orange_bar_json);
                        JSONObject bar_json=new JSONObject();
                        Iterator<String> keys = obj.keys();
                        int count = 0;
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                            bar_json.put(ob.getString("batch_no"), obj.getString(key));
                            count = addStringNo(count + "", obj.getString(key));
                        }

                        if (!(count + "").equals(papaya_pineapple)) {
                            showPopUp7();
                            saveflag = false;
                        }else{
                            batch_details_json.put("papaya_pineapple", bar_json.toString());
                        }
                    }
                    if(batch_details_json!=null){
                        OrdersData.batch_details=batch_details_json.toString();
                    }

                } else {
                    saveflag = false;
                }
            } else {
                saveflag = true;
            }
            if (!saveflag) {

                LayoutInflater factory = LayoutInflater.from(context);
                final View deleteDialogView = factory.inflate(R.layout.alertdialog_save_data, null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(context).setIcon(R.drawable.shape).create();
                FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
                fontChanger.replaceFonts((ViewGroup) deleteDialogView);
                deleteDialog.setView(deleteDialogView);
                deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //your business logic
                        //deleteDialog.dismiss();
                        deleteDialogView.findViewById(R.id.btn_yes).setEnabled(false);
                        View_load_dialog.showDialog();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
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

                                    GPSTracker gpstrack = new GPSTracker(context);
                                    OrdersData.longitude = "" + gpstrack.getLongitude();
                                    OrdersData.latitude = "" + gpstrack.getLatitude();

                                }
                                String sales_rep_id = session.getsales_rep_id();
                                String channel_type = "";
                                String distributor_type = "";
                                String store_id = "";
                                String distributor_id = "";
                                String zone_id = "";
                                String area_id = "";
                                String location_id = "";
                                String distributor_name = "";
                                String remarks = "";
                                String reation_id = "";

                                OrdersData.ispermenant = "Yes";

                                if (flag == 1) {

                                    channel_type = channel.getSelectedItem().toString();
                                    OrdersData.channel_type = channel_type;

                                    distributor_type = type.getSelectedItem().toString();
                                    OrdersData.distributor_type = distributor_type;
                                    //OrdersData.distributor_name = txtretailer.getText().toString();


                                    if (channel_type.equals("MT")) {
                                        distributor_type = "Old";

                                        StringWithTag4 swt = (StringWithTag4) relation.getSelectedItem();
                                        int s_id = (Integer) swt.tag;
                                        store_id = "" + s_id;

                                    } else {
                                        StringWithTag1 swtarea = (StringWithTag1) area.getSelectedItem();
                                        int a_id = (Integer) swtarea.tag;
                                        area_id = "" + a_id;
                                        OrdersData.area_id = area_id;
                                        OrdersData.area = area.getSelectedItem().toString();
                                    }

                                    StringWithTag3 swt = (StringWithTag3) retailer.getSelectedItem();
                                    distributor_id = (String) swt.tag;

                                    if (distributor_type.equals("Old")) {
                                        if (channel_type.equals("GT")) {
                                            OrdersData.distributor_name = retailer.getSelectedItem().toString();
                                            OrdersData.distributor_id = distributor_id;
                                        } else {
                                            OrdersData.distributor_name = relation.getSelectedItem().toString();
                                            OrdersData.distributor_id = store_id;
                                        }
                                    } else {
                                        OrdersData.distributor_name = txtretailer.getText().toString();
                                        OrdersData.distributor_id = "";
                                    }

                                    StringWithTag swtzone = (StringWithTag) zone.getSelectedItem();
                                    int z_id = (Integer) swtzone.tag;
                                    zone_id = "" + z_id;
                                    OrdersData.zone_id = zone_id;
                                    OrdersData.zone = zone.getSelectedItem().toString();


                                    StringWithTag2 swtlocation = (StringWithTag2) location.getSelectedItem();
                                    int l_id = (Integer) swtlocation.tag;
                                    location_id = "" + l_id;
                                    OrdersData.location_id = location_id;
                                    OrdersData.location = location.getSelectedItem().toString();

                                    if (channel_type.equals("MT")) {
                                        OrdersData.reation = relation.getSelectedItem().toString();
                                    }

                                    //remarks = txtremarks.getText().toString();

                                    reation_id = store_id;
                                    OrdersData.reation_id = reation_id;
                                    OrdersData.order_flag = "1";
                                } else {
                                    channel_type = channel1.getText().toString();
                                    OrdersData.channel_type = channel_type;

                                    distributor_type = type1.getText().toString();
                                    OrdersData.distributor_type = distributor_type;

                                    store_id = relation1_id.getText().toString();


                                    distributor_id = retailer1_id.getText().toString();
                                    OrdersData.distributor_id = distributor_id;

                                    //Toast.makeText(context,retailer1.getText().toString(),Toast.LENGTH_LONG).show();
                                    if (!retailer1.getText().toString().equals("")) {
                                        OrdersData.distributor_name = retailer1.getText().toString();
                                    } else {
                                        OrdersData.distributor_name = txtretailer1.getText().toString();
                                    }


                                    zone_id = zone1_id.getText().toString();
                                    OrdersData.zone_id = zone_id;
                                    OrdersData.zone = zone1.getText().toString();

                                    area_id = area1_id.getText().toString();
                                    OrdersData.area_id = area_id;
                                    OrdersData.area = area1.getText().toString();

                                    location_id = location1_id.getText().toString();
                                    OrdersData.location_id = location_id;
                                    OrdersData.location = location1.getText().toString();

                                    OrdersData.reation = relation1.getText().toString();

                                    reation_id = store_id;
                                    OrdersData.reation_id = reation_id;

                                    OrdersData.order_flag = "0";
                                }

                                OrdersData.remarks = txtremarks.getText().toString();
                                OrdersData.mid = mid;
                                OrdersData.store_id = reation_id;
                                OrdersData.beat_plan_id = bit_plan_id;
                                OrdersData.distributor_status = "";
                                OrdersData.sales_rep_loc_id = sales_rep_loc_id;
                                OrdersData.sequence = sequence;
                                OrdersData.follow_type = follow_type;
                                OrdersData.merchandiser_stock_id = merchandiser_stock_id;

                                expandAll();

                                if (distributor_type.equals("Old")) {
                                    View view = expListView.getChildAt(1);
                                    EditText orange_bar_editText = view.findViewById(R.id.lblListItem2);
                                    String orange_bar = orange_bar_editText.getText().toString();
                                    OrdersData.orange_bar = orange_bar;

                                    view = expListView.getChildAt(2);
                                    EditText butterscotch_bar_editText = view.findViewById(R.id.lblListItem2);
                                    String butterscotch_bar = butterscotch_bar_editText.getText().toString();
                                    OrdersData.butterscotch_bar = butterscotch_bar;

                                    view = expListView.getChildAt(3);
                                    EditText chocopeanut_bar_editText = view.findViewById(R.id.lblListItem2);
                                    String chocopeanut_bar = chocopeanut_bar_editText.getText().toString();
                                    OrdersData.chocopeanut_bar = chocopeanut_bar;

                                    view = expListView.getChildAt(4);
                                    EditText bambaiyachaat_bar_editText = view.findViewById(R.id.lblListItem2);
                                    String bambaiyachaat_bar = bambaiyachaat_bar_editText.getText().toString();
                                    OrdersData.bambaiyachaat_bar = bambaiyachaat_bar;

                                    view = expListView.getChildAt(5);
                                    EditText mangoginger_bar_editText = view.findViewById(R.id.lblListItem2);
                                    String mangoginger_bar = mangoginger_bar_editText.getText().toString();
                                    OrdersData.mangoginger_bar = mangoginger_bar;

                                    view = expListView.getChildAt(6);
                                    EditText berry_blast_bar_editText = view.findViewById(R.id.lblListItem2);
                                    String berry_blast_bar = berry_blast_bar_editText.getText().toString();
                                    OrdersData.berry_blast_bar = berry_blast_bar;

                                    view = expListView.getChildAt(7);
                                    EditText chyawanprash_bar_editText = view.findViewById(R.id.lblListItem2);
                                    String chyawanprash_bar = chyawanprash_bar_editText.getText().toString();
                                    OrdersData.chyawanprash_bar = chyawanprash_bar;

                                    view = expListView.getChildAt(9);
                                    EditText orange_box_editText = view.findViewById(R.id.lblListItem2);
                                    String orange_box = orange_box_editText.getText().toString();
                                    OrdersData.orange_box = orange_box;

                                    view = expListView.getChildAt(10);
                                    EditText butterscotch_box_editText = view.findViewById(R.id.lblListItem2);
                                    String butterscotch_box = butterscotch_box_editText.getText().toString();
                                    OrdersData.butterscotch_box = butterscotch_box;

                                    view = expListView.getChildAt(11);
                                    EditText chocopeanut_box_editText = view.findViewById(R.id.lblListItem2);
                                    String chocopeanut_box = chocopeanut_box_editText.getText().toString();
                                    OrdersData.chocopeanut_box = chocopeanut_box;

                                    view = expListView.getChildAt(12);
                                    EditText bambaiyachaat_box_editText = view.findViewById(R.id.lblListItem2);
                                    String bambaiyachaat_box = bambaiyachaat_box_editText.getText().toString();
                                    OrdersData.bambaiyachaat_box = bambaiyachaat_box;

                                    view = expListView.getChildAt(13);
                                    EditText mangoginger_box_editText = view.findViewById(R.id.lblListItem2);
                                    String mangoginger_box = mangoginger_box_editText.getText().toString();
                                    OrdersData.mangoginger_box = mangoginger_box;

                                    view = expListView.getChildAt(14);
                                    EditText berry_blast_box_editText = view.findViewById(R.id.lblListItem2);
                                    String berry_blast_box = berry_blast_box_editText.getText().toString();
                                    OrdersData.berry_blast_box = berry_blast_box;

                                    view = expListView.getChildAt(15);
                                    EditText chyawanprash_box_editText = view.findViewById(R.id.lblListItem2);
                                    String chyawanprash_box = chyawanprash_box_editText.getText().toString();
                                    OrdersData.chyawanprash_box = chyawanprash_box;

                                    view = expListView.getChildAt(16);
                                    EditText variety_box_editText = view.findViewById(R.id.lblListItem2);
                                    String variety_box = variety_box_editText.getText().toString();
                                    OrdersData.variety_box = variety_box;

                                    view = expListView.getChildAt(18);
                                    EditText chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
                                    String chocolate_cookies = chocolate_cookies_editText.getText().toString();
                                    OrdersData.chocolate_cookies_box = chocolate_cookies;

                                    view = expListView.getChildAt(19);
                                    EditText dark_chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
                                    String dark_chocolate_cookies = dark_chocolate_cookies_editText.getText().toString();
                                    OrdersData.dark_chocolate_cookies_box = dark_chocolate_cookies;

                                    view = expListView.getChildAt(20);
                                    EditText cranberry_cookies_editText = view.findViewById(R.id.lblListItem2);
                                    String cranberry_cookies = cranberry_cookies_editText.getText().toString();
                                    OrdersData.cranberry_cookies_box = cranberry_cookies;

                                    view = expListView.getChildAt(22);
                                    EditText cranberry_orange_zest_editText = view.findViewById(R.id.lblListItem2);
                                    String cranberry_orange_zest = cranberry_orange_zest_editText.getText().toString();
                                    OrdersData.cranberry_orange_box = cranberry_orange_zest;

                                    view = expListView.getChildAt(23);
                                    EditText fig_raisins_editText = view.findViewById(R.id.lblListItem2);
                                    String fig_raisins = fig_raisins_editText.getText().toString();
                                    OrdersData.fig_raisins_box = fig_raisins;

                                    view = expListView.getChildAt(24);
                                    EditText papaya_pineapple_editText = view.findViewById(R.id.lblListItem2);
                                    String papaya_pineapple = papaya_pineapple_editText.getText().toString();
                                    OrdersData.papaya_pineapple_box = papaya_pineapple;
                                } else {

                                }
                                View_load_dialog.hideDialog();
                                if (channel_type.equals("MT")) {
                                    View_load_dialog.showDialog();

                                    new Handler().postDelayed(new Runnable() {
                                        public void run() {
                                            //your method code

                                            Intent intent = new Intent(context, OrderDetailsActivity.class);
                                            intent.putExtra("type", type_global);
                                            intent.putExtra("flag", flag);
                                            startActivityForResult(intent, 1);
                                        }
                                    }, 500);
                                } else {
                                    if (distributor_type.equals("Old")) {
                                        View_load_dialog.showDialog();

                                        new Handler().postDelayed(new Runnable() {
                                            public void run() {
                                                //your method code
                                                Intent intent = new Intent(context, OrderDetailsActivity.class);
                                                intent.putExtra("type", type_global);
                                                intent.putExtra("flag", flag);
                                                startActivityForResult(intent, 1);
                                            }
                                        }, 500);

                                    } else {
                                        View_load_dialog.showDialog();

                                        new Handler().postDelayed(new Runnable() {
                                            public void run() {
                                                //your method code
                                                Intent intent = new Intent(context, RetailerDetailsActivity.class);
                                                intent.putExtra("type", type_global);
                                                startActivityForResult(intent, 1);
                                            }
                                        }, 200);
                                    }

                                }
                            }
                        }, 200);
                        deleteDialog.dismiss();

                    }
                });

                deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        View view = expListView.getChildAt(1);
                        EditText orange_bar_editText = view.findViewById(R.id.lblListItem2);
                        String orange_bar = orange_bar_editText.getText().toString();
                        OrdersData.orange_bar = orange_bar;

                        view = expListView.getChildAt(2);
                        EditText butterscotch_bar_editText = view.findViewById(R.id.lblListItem2);
                        String butterscotch_bar = butterscotch_bar_editText.getText().toString();
                        OrdersData.butterscotch_bar = butterscotch_bar;

                        view = expListView.getChildAt(3);
                        EditText chocopeanut_bar_editText = view.findViewById(R.id.lblListItem2);
                        String chocopeanut_bar = chocopeanut_bar_editText.getText().toString();
                        OrdersData.chocopeanut_bar = chocopeanut_bar;

                        view = expListView.getChildAt(4);
                        EditText bambaiyachaat_bar_editText = view.findViewById(R.id.lblListItem2);
                        String bambaiyachaat_bar = bambaiyachaat_bar_editText.getText().toString();
                        OrdersData.bambaiyachaat_bar = bambaiyachaat_bar;

                        view = expListView.getChildAt(5);
                        EditText mangoginger_bar_editText = view.findViewById(R.id.lblListItem2);
                        String mangoginger_bar = mangoginger_bar_editText.getText().toString();
                        OrdersData.mangoginger_bar = mangoginger_bar;

                        view = expListView.getChildAt(6);
                        EditText berry_blast_bar_editText = view.findViewById(R.id.lblListItem2);
                        String berry_blast_bar = berry_blast_bar_editText.getText().toString();
                        OrdersData.berry_blast_bar = berry_blast_bar;

                        view = expListView.getChildAt(7);
                        EditText chyawanprash_bar_editText = view.findViewById(R.id.lblListItem2);
                        String chyawanprash_bar = chyawanprash_bar_editText.getText().toString();
                        OrdersData.chyawanprash_bar = chyawanprash_bar;

                        view = expListView.getChildAt(9);
                        EditText orange_box_editText = view.findViewById(R.id.lblListItem2);
                        String orange_box = orange_box_editText.getText().toString();
                        OrdersData.orange_box = orange_box;

                        view = expListView.getChildAt(10);
                        EditText butterscotch_box_editText = view.findViewById(R.id.lblListItem2);
                        String butterscotch_box = butterscotch_box_editText.getText().toString();
                        OrdersData.butterscotch_box = butterscotch_box;

                        view = expListView.getChildAt(11);
                        EditText chocopeanut_box_editText = view.findViewById(R.id.lblListItem2);
                        String chocopeanut_box = chocopeanut_box_editText.getText().toString();
                        OrdersData.chocopeanut_box = chocopeanut_box;

                        view = expListView.getChildAt(12);
                        EditText bambaiyachaat_box_editText = view.findViewById(R.id.lblListItem2);
                        String bambaiyachaat_box = bambaiyachaat_box_editText.getText().toString();
                        OrdersData.bambaiyachaat_box = bambaiyachaat_box;

                        view = expListView.getChildAt(13);
                        EditText mangoginger_box_editText = view.findViewById(R.id.lblListItem2);
                        String mangoginger_box = mangoginger_box_editText.getText().toString();
                        OrdersData.mangoginger_box = mangoginger_box;

                        view = expListView.getChildAt(14);
                        EditText berry_blast_box_editText = view.findViewById(R.id.lblListItem2);
                        String berry_blast_box = berry_blast_box_editText.getText().toString();
                        OrdersData.berry_blast_box = berry_blast_box;

                        view = expListView.getChildAt(15);
                        EditText chyawanprash_box_editText = view.findViewById(R.id.lblListItem2);
                        String chyawanprash_box = chyawanprash_box_editText.getText().toString();
                        OrdersData.chyawanprash_box = chyawanprash_box;

                        view = expListView.getChildAt(16);
                        EditText variety_box_editText = view.findViewById(R.id.lblListItem2);
                        String variety_box = variety_box_editText.getText().toString();
                        OrdersData.variety_box = variety_box;

                        view = expListView.getChildAt(18);
                        EditText chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
                        String chocolate_cookies = chocolate_cookies_editText.getText().toString();
                        OrdersData.chocolate_cookies_box = chocolate_cookies;

                        view = expListView.getChildAt(19);
                        EditText dark_chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
                        String dark_chocolate_cookies = dark_chocolate_cookies_editText.getText().toString();
                        OrdersData.dark_chocolate_cookies_box = dark_chocolate_cookies;

                        view = expListView.getChildAt(20);
                        EditText cranberry_cookies_editText = view.findViewById(R.id.lblListItem2);
                        String cranberry_cookies = cranberry_cookies_editText.getText().toString();
                        OrdersData.cranberry_cookies_box = cranberry_cookies;

                        view = expListView.getChildAt(22);
                        EditText cranberry_orange_zest_editText = view.findViewById(R.id.lblListItem2);
                        String cranberry_orange_zest = cranberry_orange_zest_editText.getText().toString();
                        OrdersData.cranberry_orange_box = cranberry_orange_zest;

                        view = expListView.getChildAt(23);
                        EditText fig_raisins_editText = view.findViewById(R.id.lblListItem2);
                        String fig_raisins = fig_raisins_editText.getText().toString();
                        OrdersData.fig_raisins_box = fig_raisins;

                        view = expListView.getChildAt(24);
                        EditText papaya_pineapple_editText = view.findViewById(R.id.lblListItem2);
                        String papaya_pineapple = papaya_pineapple_editText.getText().toString();
                        OrdersData.papaya_pineapple_box = papaya_pineapple;


                        View_load_dialog.showDialog();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i = new Intent(context, Batch_detailsActivity.class);
                                i.putExtra("orange_bar", OrdersData.orange_bar);
                                i.putExtra("orange_box", OrdersData.orange_box);
                                i.putExtra("butterscotch_bar", OrdersData.butterscotch_bar);
                                i.putExtra("butterscotch_box", OrdersData.butterscotch_box);
                                i.putExtra("chocopeanut_bar", OrdersData.chocopeanut_bar);
                                i.putExtra("chocopeanut_box", OrdersData.chocopeanut_box);
                                i.putExtra("bambaiyachaat_bar", OrdersData.bambaiyachaat_bar);
                                i.putExtra("bambaiyachaat_box", OrdersData.bambaiyachaat_box);
                                i.putExtra("mangoginger_bar", OrdersData.mangoginger_bar);
                                i.putExtra("mangoginger_box", OrdersData.mangoginger_box);
                                i.putExtra("berry_blast_bar", OrdersData.berry_blast_bar);
                                i.putExtra("berry_blast_box", OrdersData.berry_blast_box);
                                i.putExtra("chyawanprash_bar", OrdersData.chyawanprash_bar);
                                i.putExtra("chyawanprash_box", OrdersData.chyawanprash_box);
                                i.putExtra("variety_box", OrdersData.variety_box);
                                i.putExtra("chocolate_cookies", OrdersData.chocolate_cookies_box);
                                i.putExtra("dark_chocolate_cookies", OrdersData.dark_chocolate_cookies_box);
                                i.putExtra("cranberry_cookies", OrdersData.cranberry_cookies_box);
                                i.putExtra("fig_raisins", OrdersData.fig_raisins_box);
                                i.putExtra("papaya_pineapple", OrdersData.papaya_pineapple_box);
                                i.putExtra("cranberry_orange_zest", OrdersData.cranberry_orange_box);
                                startActivityForResult(i, 1);
                                deleteDialog.dismiss();
                            }
                        }, 50);
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
            } else {

                showPopUp11();


            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("visit", "error in popup1 " + e.toString());
        }
    }

    private void showPopUp11() {

        LayoutInflater factory = LayoutInflater.from(context);
        final View deleteDialogView = factory.inflate(R.layout.alertdialog_placeorder, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(context).setIcon(R.drawable.shape).create();
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) deleteDialogView);
        deleteDialog.setView(deleteDialogView);
        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                deleteDialogView.findViewById(R.id.btn_yes).setEnabled(false);
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

                    GPSTracker gpstrack = new GPSTracker(context);
                    OrdersData.longitude = "" + gpstrack.getLongitude();
                    OrdersData.latitude = "" + gpstrack.getLatitude();

                }
                String sales_rep_id = session.getsales_rep_id();
                String channel_type = "";
                String distributor_type = "";
                String store_id = "";
                String distributor_id = "";
                String zone_id = "";
                String area_id = "";
                String location_id = "";
                String distributor_name = "";
                String remarks = "";
                String reation_id = "";

                OrdersData.ispermenant = "Yes";

                if (flag == 1) {

                    channel_type = channel.getSelectedItem().toString();
                    OrdersData.channel_type = channel_type;

                    distributor_type = type.getSelectedItem().toString();
                    OrdersData.distributor_type = distributor_type;

                    //OrdersData.distributor_name = txtretailer.getText().toString();


                    if (channel_type.equals("MT")) {
                        distributor_type = "Old";

                        StringWithTag4 swt = (StringWithTag4) relation.getSelectedItem();
                        int s_id = (Integer) swt.tag;
                        store_id = "" + s_id;
                    }

                    StringWithTag3 swt = (StringWithTag3) retailer.getSelectedItem();
                    distributor_id = (String) swt.tag;

                    if (distributor_type.equals("Old")) {
                        if (channel_type.equals("GT")) {
                            OrdersData.distributor_name = retailer.getSelectedItem().toString();
                            OrdersData.distributor_id = distributor_id;
                        } else {
                            OrdersData.distributor_name = relation.getSelectedItem().toString();
                            OrdersData.distributor_id = store_id;
                        }
                    } else {
                        OrdersData.distributor_name = txtretailer.getText().toString();
                        OrdersData.distributor_id = "";
                    }

                    StringWithTag swtzone = (StringWithTag) zone.getSelectedItem();
                    int z_id = (Integer) swtzone.tag;
                    zone_id = "" + z_id;
                    OrdersData.zone_id = zone_id;
                    OrdersData.zone = zone.getSelectedItem().toString();

                    if (channel_type.equals("GT")) {
                        StringWithTag1 swtarea = (StringWithTag1) area.getSelectedItem();
                        int a_id = (Integer) swtarea.tag;
                        area_id = "" + a_id;
                        OrdersData.area_id = area_id;
                        OrdersData.area = area.getSelectedItem().toString();
                    }
                    StringWithTag2 swtlocation = (StringWithTag2) location.getSelectedItem();
                    int l_id = (Integer) swtlocation.tag;
                    location_id = "" + l_id;
                    OrdersData.location_id = location_id;
                    OrdersData.location = location.getSelectedItem().toString();

                    if (channel_type.equals("MT")) {
                        OrdersData.reation = relation.getSelectedItem().toString();
                    }

                    //remarks = txtremarks.getText().toString();

                    reation_id = store_id;
                    OrdersData.reation_id = reation_id;
                    OrdersData.order_flag = "1";
                } else {
                    channel_type = channel1.getText().toString();
                    OrdersData.channel_type = channel_type;

                    distributor_type = type1.getText().toString();
                    OrdersData.distributor_type = distributor_type;

                    store_id = relation1_id.getText().toString();


                    distributor_id = retailer1_id.getText().toString();
                    OrdersData.distributor_id = distributor_id;
                    //Toast.makeText(context,retailer1.getText().toString(),Toast.LENGTH_LONG).show();
                    if (!retailer1.getText().toString().equals("")) {
                        OrdersData.distributor_name = retailer1.getText().toString();
                    } else {
                        OrdersData.distributor_name = txtretailer1.getText().toString();
                    }


                    zone_id = zone1_id.getText().toString();
                    OrdersData.zone_id = zone_id;
                    OrdersData.zone = zone1.getText().toString();

                    area_id = area1_id.getText().toString();
                    OrdersData.area_id = area_id;
                    OrdersData.area = area1.getText().toString();

                    location_id = location1_id.getText().toString();
                    OrdersData.location_id = location_id;
                    OrdersData.location = location1.getText().toString();

                    OrdersData.reation = relation1.getText().toString();

                    reation_id = store_id;
                    OrdersData.reation_id = reation_id;

                    OrdersData.order_flag = "0";
                }

                OrdersData.remarks = txtremarks.getText().toString();
                OrdersData.mid = mid;
                OrdersData.store_id = reation_id;
                OrdersData.beat_plan_id = bit_plan_id;
                OrdersData.distributor_status = "";
                OrdersData.sales_rep_loc_id = sales_rep_loc_id;
                OrdersData.sequence = sequence;
                OrdersData.follow_type = follow_type;
                OrdersData.merchandiser_stock_id = merchandiser_stock_id;

                expandAll();

                if (distributor_type.equals("Old")) {
                    View view = expListView.getChildAt(1);
                    EditText orange_bar_editText = view.findViewById(R.id.lblListItem2);
                    String orange_bar = orange_bar_editText.getText().toString();
                    OrdersData.orange_bar = orange_bar;

                    view = expListView.getChildAt(2);
                    EditText butterscotch_bar_editText = view.findViewById(R.id.lblListItem2);
                    String butterscotch_bar = butterscotch_bar_editText.getText().toString();
                    OrdersData.butterscotch_bar = butterscotch_bar;

                    view = expListView.getChildAt(3);
                    EditText chocopeanut_bar_editText = view.findViewById(R.id.lblListItem2);
                    String chocopeanut_bar = chocopeanut_bar_editText.getText().toString();
                    OrdersData.chocopeanut_bar = chocopeanut_bar;

                    view = expListView.getChildAt(4);
                    EditText bambaiyachaat_bar_editText = view.findViewById(R.id.lblListItem2);
                    String bambaiyachaat_bar = bambaiyachaat_bar_editText.getText().toString();
                    OrdersData.bambaiyachaat_bar = bambaiyachaat_bar;

                    view = expListView.getChildAt(5);
                    EditText mangoginger_bar_editText = view.findViewById(R.id.lblListItem2);
                    String mangoginger_bar = mangoginger_bar_editText.getText().toString();
                    OrdersData.mangoginger_bar = mangoginger_bar;

                    view = expListView.getChildAt(6);
                    EditText berry_blast_bar_editText = view.findViewById(R.id.lblListItem2);
                    String berry_blast_bar = berry_blast_bar_editText.getText().toString();
                    OrdersData.berry_blast_bar = berry_blast_bar;

                    view = expListView.getChildAt(7);
                    EditText chyawanprash_bar_editText = view.findViewById(R.id.lblListItem2);
                    String chyawanprash_bar = chyawanprash_bar_editText.getText().toString();
                    OrdersData.chyawanprash_bar = chyawanprash_bar;

                    view = expListView.getChildAt(9);
                    EditText orange_box_editText = view.findViewById(R.id.lblListItem2);
                    String orange_box = orange_box_editText.getText().toString();
                    OrdersData.orange_box = orange_box;

                    view = expListView.getChildAt(10);
                    EditText butterscotch_box_editText = view.findViewById(R.id.lblListItem2);
                    String butterscotch_box = butterscotch_box_editText.getText().toString();
                    OrdersData.butterscotch_box = butterscotch_box;

                    view = expListView.getChildAt(11);
                    EditText chocopeanut_box_editText = view.findViewById(R.id.lblListItem2);
                    String chocopeanut_box = chocopeanut_box_editText.getText().toString();
                    OrdersData.chocopeanut_box = chocopeanut_box;

                    view = expListView.getChildAt(12);
                    EditText bambaiyachaat_box_editText = view.findViewById(R.id.lblListItem2);
                    String bambaiyachaat_box = bambaiyachaat_box_editText.getText().toString();
                    OrdersData.bambaiyachaat_box = bambaiyachaat_box;

                    view = expListView.getChildAt(13);
                    EditText mangoginger_box_editText = view.findViewById(R.id.lblListItem2);
                    String mangoginger_box = mangoginger_box_editText.getText().toString();
                    OrdersData.mangoginger_box = mangoginger_box;

                    view = expListView.getChildAt(14);
                    EditText berry_blast_box_editText = view.findViewById(R.id.lblListItem2);
                    String berry_blast_box = berry_blast_box_editText.getText().toString();
                    OrdersData.berry_blast_box = berry_blast_box;

                    view = expListView.getChildAt(15);
                    EditText chyawanprash_box_editText = view.findViewById(R.id.lblListItem2);
                    String chyawanprash_box = chyawanprash_box_editText.getText().toString();
                    OrdersData.chyawanprash_box = chyawanprash_box;

                    view = expListView.getChildAt(16);
                    EditText variety_box_editText = view.findViewById(R.id.lblListItem2);
                    String variety_box = variety_box_editText.getText().toString();
                    OrdersData.variety_box = variety_box;

                    view = expListView.getChildAt(18);
                    EditText chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
                    String chocolate_cookies = chocolate_cookies_editText.getText().toString();
                    OrdersData.chocolate_cookies_box = chocolate_cookies;

                    view = expListView.getChildAt(19);
                    EditText dark_chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
                    String dark_chocolate_cookies = dark_chocolate_cookies_editText.getText().toString();
                    OrdersData.dark_chocolate_cookies_box = dark_chocolate_cookies;

                    view = expListView.getChildAt(20);
                    EditText cranberry_cookies_editText = view.findViewById(R.id.lblListItem2);
                    String cranberry_cookies = cranberry_cookies_editText.getText().toString();
                    OrdersData.cranberry_cookies_box = cranberry_cookies;

                    view = expListView.getChildAt(22);
                    EditText cranberry_orange_zest_editText = view.findViewById(R.id.lblListItem2);
                    String cranberry_orange_zest = cranberry_orange_zest_editText.getText().toString();
                    OrdersData.cranberry_orange_box = cranberry_orange_zest;

                    view = expListView.getChildAt(23);
                    EditText fig_raisins_editText = view.findViewById(R.id.lblListItem2);
                    String fig_raisins = fig_raisins_editText.getText().toString();
                    OrdersData.fig_raisins_box = fig_raisins;

                    view = expListView.getChildAt(24);
                    EditText papaya_pineapple_editText = view.findViewById(R.id.lblListItem2);
                    String papaya_pineapple = papaya_pineapple_editText.getText().toString();
                    OrdersData.papaya_pineapple_box = papaya_pineapple;
                } else {

                }

                if (channel_type.equals("MT")) {
                    View_load_dialog.showDialog();
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //your method code

                            Intent intent = new Intent(context, OrderDetailsActivity.class);
                            intent.putExtra("type", type_global);
                            intent.putExtra("flag", flag);
                            startActivityForResult(intent, 1);
                            deleteDialog.dismiss();
                        }
                    }, 50);

                    View_load_dialog.hideDialog();
                } else {
                    View_load_dialog.hideDialog();
                    if (distributor_type.equals("Old")) {

                        View_load_dialog.showDialog();
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                //your method code
                                Intent intent = new Intent(context, OrderDetailsActivity.class);
                                intent.putExtra("type", type_global);
                                intent.putExtra("flag", flag);
                                startActivityForResult(intent, 1);
                                deleteDialog.dismiss();
                            }
                        }, 50);

                    } else {
                        View_load_dialog.showDialog();
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                //your method code

                                Intent intent = new Intent(context, RetailerDetailsActivity.class);
                                intent.putExtra("type", type_global);
                                startActivityForResult(intent, 1);
                                deleteDialog.dismiss();
                            }
                        }, 100);
                    }
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

    private static class StringWithTag1 {
        public String string;
        public Object tag;

        public StringWithTag1(String string, Integer tag) {
            this.string = string;
            this.tag = tag;
        }

        @Override
        public String toString() {
            return string;
        }
    }

    private static class StringWithTag2 {
        public String string;
        public Object tag;

        public StringWithTag2(String string, Integer tag) {
            this.string = string;
            this.tag = tag;
        }

        @Override
        public String toString() {
            return string;
        }
    }

    private static class StringWithTag3 {
        public String string;
        public Object tag;

        public StringWithTag3(String string, String tag) {
            this.string = string;
            this.tag = tag;
        }

        @Override
        public String toString() {
            return string;
        }
    }

    private static class StringWithTag4 {
        public String string;
        public Object tag;

        public StringWithTag4(String string, Integer tag) {
            this.string = string;
            this.tag = tag;
        }

        @Override
        public String toString() {
            return string;
        }
    }

    private static class StringWithTag5 {
        public String string;
        public Object tag;

        public StringWithTag5(String string, Integer tag) {
            this.string = string;
            this.tag = tag;
        }

        @Override
        public String toString() {
            return string;
        }
    }

    private void get_po_nos() throws UnsupportedEncodingException {
        try {
            // Toast.makeText(context,"Zone id \n"+zone_id_val.toString(),Toast.LENGTH_LONG).show();

            if (zone_id_val != "" && store_id_val != "" && location_id_val != "") {
                String data = URLEncoder.encode("zone_id", "UTF-8")
                        + "=" + URLEncoder.encode(zone_id_val, "UTF-8");

                data += "&" + URLEncoder.encode("store_id", "UTF-8")
                        + "=" + URLEncoder.encode(store_id_val, "UTF-8");

                data += "&" + URLEncoder.encode("location_id", "UTF-8")
                        + "=" + URLEncoder.encode(location_id_val, "UTF-8");

                String text = "";
                BufferedReader reader = null;

                // Send data
                // Defined URL  where to send data
                URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/Sales_rep_store_plan_mobile_app/get_po_nos_api");

                // Send POST data request

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "\n");
                }

                text = sb.toString();

                JSONArray arr = new JSONArray(text);
                if (arr.length() > 0) {
                    showPopUp2(text);
                }
            }
        } catch (Exception ex) {
            // Toast.makeText(getContext(),"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        } finally {
            // Do nothing
        }
    }

    private void get_pending_po_nos() throws UnsupportedEncodingException {
        try {
            if (zone_id_val != "" && store_id_val != "" && location_id_val != "") {
                String data = URLEncoder.encode("zone_id", "UTF-8")
                        + "=" + URLEncoder.encode(zone_id_val, "UTF-8");

                data += "&" + URLEncoder.encode("store_id", "UTF-8")
                        + "=" + URLEncoder.encode(store_id_val, "UTF-8");

                data += "&" + URLEncoder.encode("location_id", "UTF-8")
                        + "=" + URLEncoder.encode(location_id_val, "UTF-8");

                String text = "";
                BufferedReader reader = null;

                // Send data
                // Defined URL  where to send data
                URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/Sales_rep_store_plan_mobile_app/get_pending_po_nos_api");

                // Send POST data request

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "\n");
                }

                text = sb.toString();

                JSONArray arr = new JSONArray(text);
                if (arr.length() > 0) {
                    set_pending_po_list(text);
                    btncheckpoqty.setVisibility(View.VISIBLE);
                    llpending_po.setVisibility(View.VISIBLE);
                } else {
                    btncheckpoqty.setVisibility(View.GONE);
                    llpending_po.setVisibility(View.GONE);
                }
            }
        } catch (Exception ex) {
            // Toast.makeText(getContext(),"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        } finally {
            // Do nothing
        }
    }

    private void set_pending_po_list(String response) {
        try {
            mismatch_po_nos = (TextView) findViewById(R.id.mismatch_po_nos);

            text = "";
            JSONArray data = new JSONArray(response);
            for (int i = 0; i < data.length(); i++) {
                JSONObject data1 = data.getJSONObject(i);
                String po_number = data1.getString("po_number");
                // String po_id = data1.getString("id");
                // String mismatch_date = data1.getString("modified_on");

                text = text + po_number + ", ";
            }

            text = text.substring(0, text.lastIndexOf(","));

            mismatch_po_nos.setText("Po Nos " + text + " are pending due to mismatch. Please contact your reporting manager to clear mismatch.");
        } catch (Exception ex) {
            //Toast.makeText(context,"else "+ex.toString(),Toast.LENGTH_LONG).show();
        }
    }

    private String get_po_data(String zone_id, String store_id, String location_id, String po_id) throws UnsupportedEncodingException {
        if (zone_id != "" && store_id != "" && location_id != "") {
            String data = URLEncoder.encode("zone_id", "UTF-8")
                    + "=" + URLEncoder.encode(zone_id, "UTF-8");

            data += "&" + URLEncoder.encode("store_id", "UTF-8")
                    + "=" + URLEncoder.encode(store_id, "UTF-8");

            data += "&" + URLEncoder.encode("location_id", "UTF-8")
                    + "=" + URLEncoder.encode(location_id, "UTF-8");

            data += "&" + URLEncoder.encode("po_id", "UTF-8")
                    + "=" + URLEncoder.encode(po_id, "UTF-8");

            String text = "";
            BufferedReader reader = null;

            // Send data
            try {
                // Defined URL  where to send data
                URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/Sales_rep_store_plan_mobile_app/get_po_data_api");

                // Send POST data request

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "\n");
                }


                text = sb.toString();

                return text;
            } catch (Exception ex) {
                //Toast.makeText(getContext(),"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
            } finally {
                try {

                    reader.close();
                } catch (Exception ex) {
                }
            }
        }

        return "";
    }

    public String savePoData() throws UnsupportedEncodingException {
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        String session_id = session.getSessionid();

        String data = "&" + URLEncoder.encode("po_id", "UTF-8")
                + "=" + URLEncoder.encode(intpo_id.toString(), "UTF-8");

        data += "&" + URLEncoder.encode("session_id", "UTF-8")
                + "=" + URLEncoder.encode(session_id, "UTF-8");

        data += "&" + URLEncoder.encode("po_orange_bar_qty", "UTF-8")
                + "=" + URLEncoder.encode(intorange_bar_qty.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_orange_bar", "UTF-8")
                + "=" + URLEncoder.encode(intorange_bar.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_orange_bar_diff", "UTF-8")
                + "=" + URLEncoder.encode(intorange_bar_diff.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_butterscotch_bar_qty", "UTF-8")
                + "=" + URLEncoder.encode(intbutterscotch_bar_qty.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_butterscotch_bar", "UTF-8")
                + "=" + URLEncoder.encode(intbutterscotch_bar.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_butterscotch_bar_diff", "UTF-8")
                + "=" + URLEncoder.encode(intbutterscotch_bar_diff.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_chocopeanut_bar_qty", "UTF-8")
                + "=" + URLEncoder.encode(intchocopeanut_bar_qty.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_chocopeanut_bar", "UTF-8")
                + "=" + URLEncoder.encode(intchocopeanut_bar.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_chocopeanut_bar_diff", "UTF-8")
                + "=" + URLEncoder.encode(intchocopeanut_bar_diff.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_bambaiyachaat_bar_qty", "UTF-8")
                + "=" + URLEncoder.encode(intbambaiyachaat_bar_qty.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_bambaiyachaat_bar", "UTF-8")
                + "=" + URLEncoder.encode(intbambaiyachaat_bar.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_bambaiyachaat_bar_diff", "UTF-8")
                + "=" + URLEncoder.encode(intbambaiyachaat_bar_diff.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_mangoginger_bar_qty", "UTF-8")
                + "=" + URLEncoder.encode(intmangoginger_bar_qty.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_mangoginger_bar", "UTF-8")
                + "=" + URLEncoder.encode(intmangoginger_bar.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_mangoginger_bar_diff", "UTF-8")
                + "=" + URLEncoder.encode(intmangoginger_bar_diff.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_berry_blast_bar_qty", "UTF-8")
                + "=" + URLEncoder.encode(intberry_blast_bar_qty.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_berry_blast_bar", "UTF-8")
                + "=" + URLEncoder.encode(intberry_blast_bar.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_berry_blast_bar_diff", "UTF-8")
                + "=" + URLEncoder.encode(intberry_blast_bar_diff.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_chyawanprash_bar_qty", "UTF-8")
                + "=" + URLEncoder.encode(intchyawanprash_bar_qty.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_chyawanprash_bar", "UTF-8")
                + "=" + URLEncoder.encode(intchyawanprash_bar.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_chyawanprash_bar_diff", "UTF-8")
                + "=" + URLEncoder.encode(intchyawanprash_bar_diff.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_orange_box_qty", "UTF-8")
                + "=" + URLEncoder.encode(intorange_box_qty.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_orange_box", "UTF-8")
                + "=" + URLEncoder.encode(intorange_box.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_orange_box_diff", "UTF-8")
                + "=" + URLEncoder.encode(intorange_box_diff.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_butterscotch_box_qty", "UTF-8")
                + "=" + URLEncoder.encode(intbutterscotch_box_qty.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_butterscotch_box", "UTF-8")
                + "=" + URLEncoder.encode(intbutterscotch_box.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_butterscotch_box_diff", "UTF-8")
                + "=" + URLEncoder.encode(intbutterscotch_box_diff.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_chocopeanut_box_qty", "UTF-8")
                + "=" + URLEncoder.encode(intchocopeanut_box_qty.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_chocopeanut_box", "UTF-8")
                + "=" + URLEncoder.encode(intchocopeanut_box.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_chocopeanut_box_diff", "UTF-8")
                + "=" + URLEncoder.encode(intchocopeanut_box_diff.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_bambaiyachaat_box_qty", "UTF-8")
                + "=" + URLEncoder.encode(intbambaiyachaat_box_qty.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_bambaiyachaat_box", "UTF-8")
                + "=" + URLEncoder.encode(intbambaiyachaat_box.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_bambaiyachaat_box_diff", "UTF-8")
                + "=" + URLEncoder.encode(intbambaiyachaat_box_diff.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_mangoginger_box_qty", "UTF-8")
                + "=" + URLEncoder.encode(intmangoginger_box_qty.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_mangoginger_box", "UTF-8")
                + "=" + URLEncoder.encode(intmangoginger_box.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_mangoginger_box_diff", "UTF-8")
                + "=" + URLEncoder.encode(intmangoginger_box_diff.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_berry_blast_box_qty", "UTF-8")
                + "=" + URLEncoder.encode(intberry_blast_box_qty.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_berry_blast_box", "UTF-8")
                + "=" + URLEncoder.encode(intberry_blast_box.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_berry_blast_box_diff", "UTF-8")
                + "=" + URLEncoder.encode(intberry_blast_box_diff.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_chyawanprash_box_qty", "UTF-8")
                + "=" + URLEncoder.encode(intchyawanprash_box_qty.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_chyawanprash_box", "UTF-8")
                + "=" + URLEncoder.encode(intchyawanprash_box.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_chyawanprash_box_diff", "UTF-8")
                + "=" + URLEncoder.encode(intchyawanprash_box_diff.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_variety_box_qty", "UTF-8")
                + "=" + URLEncoder.encode(intvariety_box_qty.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_variety_box", "UTF-8")
                + "=" + URLEncoder.encode(intvariety_box.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_variety_box_diff", "UTF-8")
                + "=" + URLEncoder.encode(intvariety_box_diff.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_chocolate_cookies_qty", "UTF-8")
                + "=" + URLEncoder.encode(intchocolate_cookies_qty.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_chocolate_cookies", "UTF-8")
                + "=" + URLEncoder.encode(intchocolate_cookies.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_chocolate_cookies_diff", "UTF-8")
                + "=" + URLEncoder.encode(intchocolate_cookies_diff.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_dark_chocolate_cookies_qty", "UTF-8")
                + "=" + URLEncoder.encode(intdark_chocolate_cookies_qty.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_dark_chocolate_cookies", "UTF-8")
                + "=" + URLEncoder.encode(intdark_chocolate_cookies.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_dark_chocolate_cookies_diff", "UTF-8")
                + "=" + URLEncoder.encode(intdark_chocolate_cookies_diff.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_cranberry_cookies_qty", "UTF-8")
                + "=" + URLEncoder.encode(intcranberry_cookies_qty.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_cranberry_cookies", "UTF-8")
                + "=" + URLEncoder.encode(intcranberry_cookies.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_cranberry_cookies_diff", "UTF-8")
                + "=" + URLEncoder.encode(intcranberry_cookies_diff.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_cranberry_orange_zest_qty", "UTF-8")
                + "=" + URLEncoder.encode(intcranberry_orange_zest_qty.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_cranberry_orange_zest", "UTF-8")
                + "=" + URLEncoder.encode(intcranberry_orange_zest.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_cranberry_orange_zest_diff", "UTF-8")
                + "=" + URLEncoder.encode(intcranberry_orange_zest_diff.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_fig_raisins_qty", "UTF-8")
                + "=" + URLEncoder.encode(intfig_raisins_qty.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_fig_raisins", "UTF-8")
                + "=" + URLEncoder.encode(intfig_raisins.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_fig_raisins_diff", "UTF-8")
                + "=" + URLEncoder.encode(intfig_raisins_diff.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_papaya_pineapple_qty", "UTF-8")
                + "=" + URLEncoder.encode(intpapaya_pineapple_qty.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_papaya_pineapple", "UTF-8")
                + "=" + URLEncoder.encode(intpapaya_pineapple.toString(), "UTF-8");
        data += "&" + URLEncoder.encode("po_papaya_pineapple_diff", "UTF-8")
                + "=" + URLEncoder.encode(intpapaya_pineapple_diff.toString(), "UTF-8");


        String text = "";
        BufferedReader reader = null;

        // Send data
        try {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/Sales_rep_store_plan_mobile_app/save_po_qty_api");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line);
            }

            text = sb.toString();

            return text;
        } catch (Exception ex) {
            //Toast.makeText(context,"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
            }
        }

        // Show response on activity
        return "";

    }

    public String getzone(String type) throws UnsupportedEncodingException {
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        String sales_rep_id = session.getsales_rep_id();
        String distributor_id = session.getdistributor_id();
        String beat_id = session.getbeat_id();

        String data = URLEncoder.encode("sales_rep_id", "UTF-8")
                + "=" + URLEncoder.encode(sales_rep_id, "UTF-8");

        data += "&" + URLEncoder.encode("distributor_id", "UTF-8")
                + "=" + URLEncoder.encode(distributor_id, "UTF-8");

        data += "&" + URLEncoder.encode("beat_id", "UTF-8")
                + "=" + URLEncoder.encode(beat_id, "UTF-8");

        data += "&" + URLEncoder.encode("type", "UTF-8")
                + "=" + URLEncoder.encode(type, "UTF-8");

        String text = "";
        BufferedReader reader = null;

        // Send data
        try {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/Sales_rep_store_plan_mobile_app/get_zone_api");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();

            return text;
        } catch (Exception ex) {
            //Toast.makeText(getContext(),"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
            }
        }

        // Show response on activity
        return "";

    }

    public String getarea(String zone_id) throws UnsupportedEncodingException {
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        String sales_rep_id = session.getsales_rep_id();
        String distributor_id = session.getdistributor_id();
        String beat_id = session.getbeat_id();

        String data = URLEncoder.encode("sales_rep_id", "UTF-8")
                + "=" + URLEncoder.encode(sales_rep_id, "UTF-8");

        data += "&" + URLEncoder.encode("distributor_id", "UTF-8")
                + "=" + URLEncoder.encode(distributor_id, "UTF-8");

        data += "&" + URLEncoder.encode("beat_id", "UTF-8")
                + "=" + URLEncoder.encode(beat_id, "UTF-8");

        data += "&" + URLEncoder.encode("zone_id", "UTF-8")
                + "=" + URLEncoder.encode(zone_id, "UTF-8");

        String text = "";
        BufferedReader reader = null;

        // Send data
        try {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/Sales_rep_store_plan_mobile_app/get_area_api");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();

            return text;
        } catch (Exception ex) {
            //Toast.makeText(getContext(),"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
            }
        }

        // Show response on activity
        return "";

    }


    public String getlocation(String zone_id, String area_id) throws UnsupportedEncodingException {
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        String sales_rep_id = session.getsales_rep_id();
        String distributor_id = session.getdistributor_id();
        String beat_id = session.getbeat_id();

        String data = URLEncoder.encode("sales_rep_id", "UTF-8")
                + "=" + URLEncoder.encode(sales_rep_id, "UTF-8");

        data += "&" + URLEncoder.encode("distributor_id", "UTF-8")
                + "=" + URLEncoder.encode(distributor_id, "UTF-8");

        data += "&" + URLEncoder.encode("beat_id", "UTF-8")
                + "=" + URLEncoder.encode(beat_id, "UTF-8");

        data += "&" + URLEncoder.encode("zone_id", "UTF-8")
                + "=" + URLEncoder.encode(zone_id, "UTF-8");

        data += "&" + URLEncoder.encode("area_id", "UTF-8")
                + "=" + URLEncoder.encode(area_id, "UTF-8");

        String text = "";
        BufferedReader reader = null;

        // Send data
        try {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/Sales_rep_store_plan_mobile_app/get_locations_api");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();

            return text;
        } catch (Exception ex) {
            //Toast.makeText(getContext(),"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
            }
        }

        // Show response on activity
        return "";

    }


    public String getretailer(String zone_id, String area_id, String location_id, String dtype) throws UnsupportedEncodingException {
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        //String sales_rep_id=session.getsales_rep_id();
        //String distributor_id=session.getdistributor_id();
        //String beat_id=session.getbeat_id();


        String data = "&" + URLEncoder.encode("zone_id", "UTF-8")
                + "=" + URLEncoder.encode(zone_id, "UTF-8");

        data += "&" + URLEncoder.encode("area_id", "UTF-8")
                + "=" + URLEncoder.encode(area_id, "UTF-8");

        data += "&" + URLEncoder.encode("location_id", "UTF-8")
                + "=" + URLEncoder.encode(location_id, "UTF-8");

        data += "&" + URLEncoder.encode("dist_type", "UTF-8")
                + "=" + URLEncoder.encode(dtype, "UTF-8");

        String text = "";
        BufferedReader reader = null;

        // Send data
        try {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/Sales_rep_store_plan_mobile_app/get_retailer_api");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();

            return text;
        } catch (Exception ex) {
            //Toast.makeText(getContext(),"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
            }
        }

        // Show response on activity
        return "";

    }

    public String getrelation(String zone_id) throws UnsupportedEncodingException {
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server


        String data = URLEncoder.encode("zone_id", "UTF-8")
                + "=" + URLEncoder.encode(zone_id, "UTF-8");


        String text = "";
        BufferedReader reader = null;

        // Send data
        try {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/Sales_rep_store_plan_mobile_app/get_store_api");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();

            return text;
        } catch (Exception ex) {
            //Toast.makeText(getContext(),"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
            }
        }

        // Show response on activity
        return "";

    }

    public String getlocationdata(String zone_id, String relation_id) throws UnsupportedEncodingException {
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server


        String data = "&" + URLEncoder.encode("zone_id", "UTF-8")
                + "=" + URLEncoder.encode(zone_id, "UTF-8");

        data += "&" + URLEncoder.encode("store_id", "UTF-8")
                + "=" + URLEncoder.encode(relation_id, "UTF-8");

        String text = "";
        BufferedReader reader = null;

        // Send data
        try {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/Sales_rep_store_plan_mobile_app/get_location_data_api");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();

            return text;
        } catch (Exception ex) {
            //Toast.makeText(getContext(),"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
            }
        }

        // Show response on activity
        return "";

    }


    public void showPopUp10(final String sales_rep_id, final String channel_type, final String distributor_type, final String distributor_id, final String store_id, final String zone_id, final String area_id, final String location_id, final String distributor_name, final String remarks, final String reation_id, final String orange_bar, final String orange_box, final String butterscotch_bar, final String butterscotch_box, final String chocopeanut_bar, final String chocopeanut_box, final String bambaiyachaat_bar, final String bambaiyachaat_box, final String mangoginger_bar, final String mangoginger_box, final String berry_blast_bar, final String berry_blast_box, final String chyawanprash_bar, final String chyawanprash_box, final String variety_box, final String chocolate_cookies, final String dark_chocolate_cookies, final String cranberry_cookies, final String fig_raisins, final String papaya_pineapple, final String cranberry_orange_zest, final String follow_type, final String followup_date, final String srld, final String id, final String bit_plan_id, final String sequence, final String mid, final String sales_rep_loc_id, final String merchandiser_stock_id, final String ispermenant) {

        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.alertdialog_save_data, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).setIcon(R.drawable.shape).create();
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) deleteDialogView);
        deleteDialog.setView(deleteDialogView);
        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    showPopUp12();

                    add_event_in_db(distributor_name);
                    View_load_dialog.showDialog();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isNetwork = isNETWORK();
                            if (isNetwork) {
                                Toast.makeText(context, "Data Saved ", Toast.LENGTH_LONG).show();
                                OrdersData od = new OrdersData();
                                od.clear();
                                GPSTracker gpsTracker = new GPSTracker(context);
                                OrdersData.latitude = gpsTracker.getLatitude() + "";
                                OrdersData.longitude = gpsTracker.getLongitude() + "";
                                Log.e("visithere", "lat: " + OrdersData.latitude + " lon: " + OrdersData.longitude);

                                // Get user defined values
                                //Name = fname.getText().toing();

                                // Create data variable for sent values to server
                                //Toast.makeText(context, ""+sales_rep_id+channel_type+distributor_type+distributor_id+"  "+store_id+ distributor_name+remarks+follow_type+ispermenant, Toast.LENGTH_SHORT).show();
                                String session_id = session.getSessionid();
                                OrdersData.orange_bar = orange_bar;
                                OrdersData.butterscotch_bar = butterscotch_bar;
                                OrdersData.chocopeanut_bar = chocopeanut_bar;
                                OrdersData.bambaiyachaat_bar = bambaiyachaat_bar;
                                OrdersData.mangoginger_bar = mangoginger_bar;
                                OrdersData.berry_blast_bar = berry_blast_bar;
                                OrdersData.chyawanprash_bar = chyawanprash_bar;
                                OrdersData.orange_box = orange_box;
                                OrdersData.butterscotch_box = butterscotch_box;
                                OrdersData.chocopeanut_box = chocopeanut_box;
                                OrdersData.bambaiyachaat_box = bambaiyachaat_box;
                                OrdersData.mangoginger_box = mangoginger_box;
                                OrdersData.berry_blast_box = berry_blast_box;
                                OrdersData.chyawanprash_box = chyawanprash_box;
                                OrdersData.variety_box = variety_box;
                                OrdersData.chocolate_cookies_box = chocolate_cookies;
                                OrdersData.dark_chocolate_cookies_box = dark_chocolate_cookies;
                                OrdersData.cranberry_cookies_box = cranberry_cookies;
                                OrdersData.cranberry_orange_box = cranberry_orange_zest;
                                OrdersData.fig_raisins_box = fig_raisins;
                                OrdersData.papaya_pineapple_box = papaya_pineapple;
                                OrdersData.distributor_name = distributor_name;
                                if (flag == 1) {
                                    if (channel_type.equals("GT")) {
                                        OrdersData.area = area.getSelectedItem().toString();
                                    } else {
                                        OrdersData.area = relation.getSelectedItem().toString();
                                    }
                                } else {
                                    if (channel_type.equals("GT")) {
                                        OrdersData.area = area1.getText().toString();
                                    } else {
                                        OrdersData.area = relation1.getText().toString();
                                    }
                                }


                                try {


                                    String data = null;
                                    if (OrdersData.latitude == null || OrdersData.longitude == null || OrdersData.latitude.equals("0") || OrdersData.longitude.equals("0")) {
                                        GPSTracker gpsTracker1 = new GPSTracker(context);
                                        OrdersData.latitude = gpsTracker1.getLatitude() + "";
                                        OrdersData.longitude = gpsTracker1.getLongitude() + "";
                                    }

                                    data = "&" + URLEncoder.encode("sales_rep_id", "UTF-8")
                                            + "=" + URLEncoder.encode(sales_rep_id, "UTF-8");

                                    data += "&" + URLEncoder.encode("id", "UTF-8")
                                            + "=" + URLEncoder.encode(id, "UTF-8");

                                    data += "&" + URLEncoder.encode("ispermenant", "UTF-8")
                                            + "=" + URLEncoder.encode(ispermenant, "UTF-8");

                                    data += "&" + URLEncoder.encode("sequence", "UTF-8")
                                            + "=" + URLEncoder.encode(sequence, "UTF-8");

                                    data += "&" + URLEncoder.encode("beat_plan_id", "UTF-8")
                                            + "=" + URLEncoder.encode(bit_plan_id, "UTF-8");

                                    data += "&" + URLEncoder.encode("srld", "UTF-8")
                                            + "=" + URLEncoder.encode(srld, "UTF-8");

                                    data += "&" + URLEncoder.encode("session_id", "UTF-8")
                                            + "=" + URLEncoder.encode(session_id, "UTF-8");

                                    data += "&" + URLEncoder.encode("channel_type", "UTF-8")
                                            + "=" + URLEncoder.encode(channel_type, "UTF-8");

                                    data += "&" + URLEncoder.encode("distributor_type", "UTF-8")
                                            + "=" + URLEncoder.encode(distributor_type, "UTF-8");

                                    data += "&" + URLEncoder.encode("distributor_id", "UTF-8")
                                            + "=" + URLEncoder.encode(distributor_id, "UTF-8");

                                    data += "&" + URLEncoder.encode("store_id", "UTF-8")
                                            + "=" + URLEncoder.encode(store_id, "UTF-8");

                                    data += "&" + URLEncoder.encode("zone_id", "UTF-8")
                                            + "=" + URLEncoder.encode(zone_id, "UTF-8");

                                    data += "&" + URLEncoder.encode("area_id", "UTF-8")
                                            + "=" + URLEncoder.encode(area_id, "UTF-8");

                                    data += "&" + URLEncoder.encode("location_id", "UTF-8")
                                            + "=" + URLEncoder.encode(location_id, "UTF-8");

                                    data += "&" + URLEncoder.encode("distributor_name", "UTF-8")
                                            + "=" + URLEncoder.encode(distributor_name, "UTF-8");

                                    data += "&" + URLEncoder.encode("place_order", "UTF-8")
                                            + "=" + URLEncoder.encode("No", "UTF-8");

                                    data += "&" + URLEncoder.encode("latitude", "UTF-8")
                                            + "=" + URLEncoder.encode(OrdersData.latitude, "UTF-8");

                                    data += "&" + URLEncoder.encode("longitude", "UTF-8")
                                            + "=" + URLEncoder.encode(OrdersData.longitude, "UTF-8");

                                    data += "&" + URLEncoder.encode("remarks", "UTF-8")
                                            + "=" + URLEncoder.encode(remarks, "UTF-8");

                                    data += "&" + URLEncoder.encode("reation_id", "UTF-8")
                                            + "=" + URLEncoder.encode(reation_id, "UTF-8");

                                    data += "&" + URLEncoder.encode("mid", "UTF-8")
                                            + "=" + URLEncoder.encode(mid, "UTF-8");

                                    data += "&" + URLEncoder.encode("distributor_status", "UTF-8")
                                            + "=" + URLEncoder.encode("", "UTF-8");

                                    data += "&" + URLEncoder.encode("sales_rep_loc_id", "UTF-8")
                                            + "=" + URLEncoder.encode(sales_rep_loc_id, "UTF-8");

                                    data += "&" + URLEncoder.encode("merchandiser_stock_id", "UTF-8")
                                            + "=" + URLEncoder.encode(merchandiser_stock_id, "UTF-8");

                                    data += "&" + URLEncoder.encode("follow_type", "UTF-8")
                                            + "=" + URLEncoder.encode(follow_type, "UTF-8");

                                    data += "&" + URLEncoder.encode("followup_date", "UTF-8")
                                            + "=" + URLEncoder.encode(followup_date, "UTF-8");

                                    data += "&" + URLEncoder.encode("orange_bar", "UTF-8")
                                            + "=" + URLEncoder.encode(orange_bar, "UTF-8");

                                    data += "&" + URLEncoder.encode("orange_box", "UTF-8")
                                            + "=" + URLEncoder.encode(orange_box, "UTF-8");

                                    data += "&" + URLEncoder.encode("butterscotch_bar", "UTF-8")
                                            + "=" + URLEncoder.encode(butterscotch_bar, "UTF-8");

                                    data += "&" + URLEncoder.encode("butterscotch_box", "UTF-8")
                                            + "=" + URLEncoder.encode(butterscotch_box, "UTF-8");

                                    data += "&" + URLEncoder.encode("chocopeanut_bar", "UTF-8")
                                            + "=" + URLEncoder.encode(chocopeanut_bar, "UTF-8");

                                    data += "&" + URLEncoder.encode("chocopeanut_box", "UTF-8")
                                            + "=" + URLEncoder.encode(chocopeanut_box, "UTF-8");

                                    data += "&" + URLEncoder.encode("bambaiyachaat_bar", "UTF-8")
                                            + "=" + URLEncoder.encode(bambaiyachaat_bar, "UTF-8");

                                    data += "&" + URLEncoder.encode("bambaiyachaat_box", "UTF-8")
                                            + "=" + URLEncoder.encode(bambaiyachaat_box, "UTF-8");

                                    data += "&" + URLEncoder.encode("mangoginger_bar", "UTF-8")
                                            + "=" + URLEncoder.encode(mangoginger_bar, "UTF-8");

                                    data += "&" + URLEncoder.encode("mangoginger_box", "UTF-8")
                                            + "=" + URLEncoder.encode(mangoginger_box, "UTF-8");

                                    data += "&" + URLEncoder.encode("berry_blast_bar", "UTF-8")
                                            + "=" + URLEncoder.encode(berry_blast_bar, "UTF-8");

                                    data += "&" + URLEncoder.encode("berry_blast_box", "UTF-8")
                                            + "=" + URLEncoder.encode(berry_blast_box, "UTF-8");

                                    data += "&" + URLEncoder.encode("chyawanprash_bar", "UTF-8")
                                            + "=" + URLEncoder.encode(chyawanprash_bar, "UTF-8");

                                    data += "&" + URLEncoder.encode("chyawanprash_box", "UTF-8")
                                            + "=" + URLEncoder.encode(chyawanprash_box, "UTF-8");

                                    data += "&" + URLEncoder.encode("variety_box", "UTF-8")
                                            + "=" + URLEncoder.encode(variety_box, "UTF-8");

                                    data += "&" + URLEncoder.encode("chocolate_cookies", "UTF-8")
                                            + "=" + URLEncoder.encode(chocolate_cookies, "UTF-8");

                                    data += "&" + URLEncoder.encode("dark_chocolate_cookies", "UTF-8")
                                            + "=" + URLEncoder.encode(dark_chocolate_cookies, "UTF-8");

                                    data += "&" + URLEncoder.encode("cranberry_cookies", "UTF-8")
                                            + "=" + URLEncoder.encode(cranberry_cookies, "UTF-8");

                                    data += "&" + URLEncoder.encode("fig_raisins", "UTF-8")
                                            + "=" + URLEncoder.encode(fig_raisins, "UTF-8");

                                    data += "&" + URLEncoder.encode("papaya_pineapple", "UTF-8")
                                            + "=" + URLEncoder.encode(papaya_pineapple, "UTF-8");

                                    data += "&" + URLEncoder.encode("cranberry_orange_zest", "UTF-8")
                                            + "=" + URLEncoder.encode(cranberry_orange_zest, "UTF-8");
                                    if(OrdersData.batch_details!=null) {
                                        data += "&" + URLEncoder.encode("batch_detail", "UTF-8")
                                                + "=" + URLEncoder.encode(OrdersData.batch_details, "UTF-8");
                                    }else{
                                        data += "&" + URLEncoder.encode("batch_detail", "UTF-8")
                                                + "=" + URLEncoder.encode("", "UTF-8");

                                    }

                                    String text = "";
                                    BufferedReader reader = null;
                                    // Send data
                                    try {
                                        Log.e("visit1", "4");
                                        // Defined URL  where to send data
                                        URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/Sales_rep_store_plan_mobile_app/save_api");

                                        // Send POST data request

                                        URLConnection conn = url.openConnection();
                                        conn.setDoOutput(true);
                                        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                                        wr.write(data);
                                        wr.flush();

                                        // Get the server response

                                        reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                        StringBuilder sb = new StringBuilder();
                                        String line = null;

                                        // Read Server Response
                                        while ((line = reader.readLine()) != null) {
                                            // Append server response in string
                                            sb.append(line + "\n");
                                        }


                                        text = sb.toString();
                                        //
                                        //Toast.makeText(context,"Text "+text,Toast.LENGTH_LONG).show();
                                        JSONObject obj = new JSONObject(text);

                                        if (obj == null) {
                                            //return "";
                                        } else {
                                            String monday = obj.getString("Monday");
                                            String tuesday = obj.getString("Tuesday");
                                            String wednesday = obj.getString("Wednesday");
                                            String thursday = obj.getString("Thursday");
                                            String friday = obj.getString("Friday");
                                            String saturday = obj.getString("Saturday");
                                            String sunday = obj.getString("Sunday");

                                            if (!monday.equals("[]")) {
                                                //Toast.makeText(context,"monday "+text,Toast.LENGTH_LONG).show();
                                                session.setmon_frag(monday);
                                            }
                                            if (!tuesday.equals("[]")) {
                                                //Toast.makeText(context,"tuesday "+text,Toast.LENGTH_LONG).show();
                                                session.settues_frag(tuesday);
                                            }
                                            if (!wednesday.equals("[]")) {
                                                //Toast.makeText(context,"wednesday "+text,Toast.LENGTH_LONG).show();
                                                session.setwed_frag(wednesday);
                                            }
                                            if (!thursday.equals("[]")) {
                                                //Toast.makeText(context,"thursday "+text,Toast.LENGTH_LONG).show();
                                                session.setthu_frag(thursday);
                                            }
                                            if (!friday.equals("[]")) {
                                                //Toast.makeText(context,"friday "+text,Toast.LENGTH_LONG).show();
                                                session.setfri_frag(friday);
                                            }
                                            if (!saturday.equals("[]")) {
                                                //Toast.makeText(context,"saturday "+text,Toast.LENGTH_LONG).show();
                                                session.setsat_frag(saturday);
                                            }
                                            if (!sunday.equals("[]")) {
                                                //Toast.makeText(context,"saturday "+text,Toast.LENGTH_LONG).show();
                                                session.setsun_frag(sunday);
                                            }
                                        }
                                        View_load_dialog.hideDialog();
                                        // return text;
                                    } catch (Exception ex) {
                                        Log.e("visit", "savedata error51: " + ex.toString());
                                        View_load_dialog.hideDialog();
                                    } finally {
                                        try {

                                            reader.close();
                                        } catch (Exception ex) {
                                            Log.e("visit", "savedata error2: " + ex.toString());
                                            View_load_dialog.hideDialog();
                                        }
                                    }
                                } catch (Exception e) {
                                    Log.e("visit", "savedata error3: " + e.toString());
                                    View_load_dialog.hideDialog();
                                }
                                View_load_dialog.hideDialog();
                            }
                        }
                    }, 200);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                deleteDialog.dismiss();
            }
        });
        deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp5(orange_bar, orange_box, butterscotch_bar, butterscotch_box, chocopeanut_bar, chocopeanut_box, bambaiyachaat_bar, bambaiyachaat_box, mangoginger_bar, mangoginger_box, berry_blast_bar, berry_blast_box, chyawanprash_bar, chyawanprash_box, variety_box, chocolate_cookies, dark_chocolate_cookies, cranberry_cookies, fig_raisins, papaya_pineapple, cranberry_orange_zest);
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


    boolean saveflag = true;

    public void saveData(final String sales_rep_id, final String channel_type, final String distributor_type, final String distributor_id, final String store_id, final String zone_id, final String area_id, final String location_id, final String distributor_name, final String remarks, final String reation_id, final String orange_bar, final String orange_box, final String butterscotch_bar, final String butterscotch_box, final String chocopeanut_bar, final String chocopeanut_box, final String bambaiyachaat_bar, final String bambaiyachaat_box, final String mangoginger_bar, final String mangoginger_box, final String berry_blast_bar, final String berry_blast_box, final String chyawanprash_bar, final String chyawanprash_box, final String variety_box, final String chocolate_cookies, final String dark_chocolate_cookies, final String cranberry_cookies, final String fig_raisins, final String papaya_pineapple, final String cranberry_orange_zest, final String follow_type, final String followup_date, final String srld, final String id, final String bit_plan_id, final String sequence, final String mid, final String sales_rep_loc_id, final String merchandiser_stock_id, final String ispermenant) throws UnsupportedEncodingException, JSONException {

        View_load_dialog.showDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if ((OrdersData.chocolate_cookies_json != null || OrdersData.dark_chocolate_cookies_json != null || OrdersData.cranberry_cookies_json != null || OrdersData.cranberry_orange_json != null || OrdersData.fig_raisins_json != null || OrdersData.papaya_pineapple_json != null || OrdersData.orange_bar_json != null || OrdersData.orange_box_json != null || OrdersData.butterscotch_bar_json != null || OrdersData.butterscotch_box_json != null || OrdersData.chocopeanut_bar_json != null || OrdersData.chocopeanut_box_json != null || OrdersData.bambaiyachaat_bar_json != null || OrdersData.bambaiyachaat_box_json != null || OrdersData.mangoginger_bar_json != null || OrdersData.mangoginger_box_json != null || OrdersData.berry_blast_bar_json != null || OrdersData.berry_blast_box_json != null || OrdersData.chyawanprash_bar_json != null || OrdersData.chyawanprash_box_json != null || OrdersData.variety_box_json != null) || ((orange_bar.equals("") || orange_bar.equals("0")) && (orange_box.equals("") || orange_box.equals("0")) && (butterscotch_bar.equals("") || butterscotch_bar.equals("0")) && (butterscotch_box.equals("") || butterscotch_box.equals("0")) && (chocopeanut_bar.equals("") || chocopeanut_bar.equals("0")) && (chocopeanut_box.equals("") || chocopeanut_box.equals("0")) && (bambaiyachaat_bar.equals("") || bambaiyachaat_bar.equals("0")) && (bambaiyachaat_box.equals("") || bambaiyachaat_box.equals("0")) && (mangoginger_bar.equals("") || mangoginger_bar.equals("0")) && (mangoginger_box.equals("") || mangoginger_box.equals("0")) && (berry_blast_bar.equals("") || berry_blast_bar.equals("0")) && (berry_blast_box.equals("") || berry_blast_box.equals("0")) && (chyawanprash_bar.equals("") || chyawanprash_bar.equals("0")) && (chyawanprash_box.equals("") || chyawanprash_box.equals("0")) && (variety_box.equals("") || variety_box.equals("0")) && (chocolate_cookies.equals("") || chocolate_cookies.equals("0")) && (dark_chocolate_cookies.equals("") || dark_chocolate_cookies.equals("0")) && (cranberry_cookies.equals("") || cranberry_cookies.equals("0")) && (fig_raisins.equals("") || fig_raisins.equals("0")) && (papaya_pineapple.equals("") || papaya_pineapple.equals("0")) && (cranberry_orange_zest.equals("") || cranberry_orange_zest.equals("0")))) {


                        saveflag = true;
                        if (OrdersData.orange_bar_json == null && (!orange_bar.equals("") && !orange_bar.equals("0")) && saveflag) {
                            showPopUp7();
                            saveflag = false;
                        }
                        if (OrdersData.butterscotch_bar_json == null && (!butterscotch_bar.equals("") && !butterscotch_bar.equals("0")) && saveflag) {
                            showPopUp7();
                            saveflag = false;
                        }
                        if (OrdersData.bambaiyachaat_bar_json == null && (!bambaiyachaat_bar.equals("") && !bambaiyachaat_bar.equals("0")) && saveflag) {
                            showPopUp7();
                            saveflag = false;
                        }
                        if (OrdersData.chocopeanut_bar_json == null && (!chocopeanut_bar.equals("") && !chocopeanut_bar.equals("0")) && saveflag) {
                            showPopUp7();
                            saveflag = false;
                        }
                        if (OrdersData.mangoginger_bar_json == null && (!mangoginger_bar.equals("") && !mangoginger_bar.equals("0")) && saveflag) {
                            showPopUp7();
                            saveflag = false;
                        }
                        if (OrdersData.berry_blast_bar_json == null && (!berry_blast_bar.equals("") && !berry_blast_bar.equals("0")) && saveflag) {
                            showPopUp7();
                            saveflag = false;
                        }
                        if (OrdersData.chyawanprash_bar_json == null && (!chyawanprash_bar.equals("") && !chyawanprash_bar.equals("0")) && saveflag) {
                            showPopUp7();
                            saveflag = false;
                        }
                        if (OrdersData.orange_box_json == null && (!orange_box.equals("") && !orange_box.equals("0")) && saveflag) {
                            showPopUp7();
                            saveflag = false;
                        }
                        if (OrdersData.butterscotch_box_json == null && (!butterscotch_box.equals("") && !butterscotch_box.equals("0")) && saveflag) {
                            showPopUp7();
                            saveflag = false;
                        }
                        if (OrdersData.bambaiyachaat_box_json == null && (!bambaiyachaat_box.equals("") && !bambaiyachaat_box.equals("0")) && saveflag) {
                            showPopUp7();
                            saveflag = false;
                        }
                        if (OrdersData.chocopeanut_box_json == null && (!chocopeanut_box.equals("") && !chocopeanut_box.equals("0")) && saveflag) {
                            showPopUp7();
                            saveflag = false;
                        }
                        if (OrdersData.mangoginger_box_json == null && (!mangoginger_box.equals("") && !mangoginger_box.equals("0")) && saveflag) {
                            showPopUp7();
                            saveflag = false;
                        }
                        if (OrdersData.berry_blast_box_json == null && (!berry_blast_box.equals("") && !berry_blast_box.equals("0")) && saveflag) {
                            showPopUp7();
                            saveflag = false;
                        }
                        if (OrdersData.chyawanprash_box_json == null && (!chyawanprash_box.equals("") && !chyawanprash_box.equals("0")) && saveflag) {
                            showPopUp7();
                            saveflag = false;
                        }
                        if (OrdersData.variety_box_json == null && (!variety_box.equals("") && !variety_box.equals("0")) && saveflag) {
                            showPopUp7();
                            saveflag = false;
                        }
                        if (OrdersData.chocolate_cookies_json == null && (!chocolate_cookies.equals("") && !chocolate_cookies.equals("0")) && saveflag) {
                            showPopUp7();
                            saveflag = false;
                        }
                        if (OrdersData.dark_chocolate_cookies_json == null && (!dark_chocolate_cookies.equals("") && !dark_chocolate_cookies.equals("0")) && saveflag) {
                            showPopUp7();
                            saveflag = false;
                        }
                        if (OrdersData.cranberry_cookies_json == null && (!cranberry_cookies.equals("") && !cranberry_cookies.equals("0")) && saveflag) {
                            showPopUp7();
                            saveflag = false;
                        }
                        if (OrdersData.cranberry_orange_json == null && (!cranberry_orange_zest.equals("") && !cranberry_orange_zest.equals("0")) && saveflag) {
                            showPopUp7();
                            saveflag = false;
                        }
                        if (OrdersData.fig_raisins_json == null && (!fig_raisins.equals("") && !fig_raisins.equals("0")) && saveflag) {
                            showPopUp7();
                            saveflag = false;
                        }
                        if (OrdersData.papaya_pineapple_json == null && (!papaya_pineapple.equals("") && !papaya_pineapple.equals("0")) && saveflag) {
                            showPopUp7();
                            saveflag = false;
                        }

                        JSONObject batch_details_json=new JSONObject();
                        JSONArray arr=new JSONArray(OrdersData.batch_no_json);

                        if (OrdersData.orange_bar_json != null && saveflag) {
                            String orange_bar_json = OrdersData.orange_bar_json;
                            JSONObject obj = new JSONObject(orange_bar_json);
                            JSONObject bar_json=new JSONObject();
                            Iterator<String> keys = obj.keys();
                            int count = 0;
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                                bar_json.put(ob.getString("batch_no"), obj.getString(key));
                                count = addStringNo(count + "", obj.getString(key));
                            }

                            if (!(count + "").equals(orange_bar)) {
                                showPopUp7();
                                Log.e("visit", "count: " + count + " orange_bar: " + orange_bar);
                                saveflag = false;
                            }else{
                                batch_details_json.put("orange_bar", bar_json.toString());
                                //Log.e("totalorange", batch_details_json.toString());
                            }
                        }

                        if (OrdersData.butterscotch_bar_json != null && saveflag) {
                            String orange_bar_json = OrdersData.butterscotch_bar_json;
                            JSONObject obj = new JSONObject(orange_bar_json);
                            JSONObject bar_json=new JSONObject();
                            Iterator<String> keys = obj.keys();
                            int count = 0;
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                                bar_json.put(ob.getString("batch_no"), obj.getString(key));
                                count = addStringNo(count + "", obj.getString(key));
                            }

                            if (!(count + "").equals(butterscotch_bar)) {
                                showPopUp7();
                                Log.e("visit", "count: " + count + " butter_bar: " + butterscotch_bar);
                                saveflag = false;
                            }else{
                                batch_details_json.put("butterscotch_bar", bar_json.toString());
                            }
                        }
                        if (OrdersData.chocopeanut_bar_json != null && saveflag) {
                            String orange_bar_json = OrdersData.chocopeanut_bar_json;
                            JSONObject obj = new JSONObject(orange_bar_json);
                            JSONObject bar_json=new JSONObject();
                            Iterator<String> keys = obj.keys();
                            int count = 0;
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                                bar_json.put(ob.getString("batch_no"), obj.getString(key));
                                count = addStringNo(count + "", obj.getString(key));
                            }

                            if (!(count + "").equals(chocopeanut_bar)) {
                                showPopUp7();
                                saveflag = false;
                            }else{
                                batch_details_json.put("chocopeanut_bar", bar_json.toString());
                            }
                        }
                        if (OrdersData.bambaiyachaat_bar_json != null && saveflag) {
                            String orange_bar_json = OrdersData.bambaiyachaat_bar_json;
                            JSONObject obj = new JSONObject(orange_bar_json);
                            JSONObject bar_json=new JSONObject();
                            Iterator<String> keys = obj.keys();
                            int count = 0;
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                                bar_json.put(ob.getString("batch_no"), obj.getString(key));
                                count = addStringNo(count + "", obj.getString(key));
                            }

                            if (!(count + "").equals(bambaiyachaat_bar)) {
                                showPopUp7();
                                saveflag = false;
                            }else{
                                batch_details_json.put("bambaiyachaat_bar", bar_json.toString());
                            }
                        }
                        if (OrdersData.mangoginger_bar_json != null && saveflag) {
                            String orange_bar_json = OrdersData.mangoginger_bar_json;
                            JSONObject obj = new JSONObject(orange_bar_json);
                            JSONObject bar_json=new JSONObject();
                            Iterator<String> keys = obj.keys();
                            int count = 0;
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                                bar_json.put(ob.getString("batch_no"), obj.getString(key));
                                count = addStringNo(count + "", obj.getString(key));
                            }

                            if (!(count + "").equals(mangoginger_bar)) {
                                //Log.e("visit", "count: " + count + " mango_bar: " + mangoginger_bar);
                                showPopUp7();
                                saveflag = false;
                            }else{
                                batch_details_json.put("mangoginger_bar", bar_json.toString());
                            }
                        }
                        if (OrdersData.berry_blast_bar_json != null && saveflag) {
                            String orange_bar_json = OrdersData.berry_blast_bar_json;
                            JSONObject obj = new JSONObject(orange_bar_json);
                            JSONObject bar_json=new JSONObject();
                            Iterator<String> keys = obj.keys();
                            int count = 0;
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                                bar_json.put(ob.getString("batch_no"), obj.getString(key));
                                count = addStringNo(count + "", obj.getString(key));
                            }

                            if (!(count + "").equals(berry_blast_bar)) {
                                showPopUp7();
                                saveflag = false;

                            }else{
                                batch_details_json.put("berry_blast_bar", bar_json.toString());
                            }
                        }
                        if (OrdersData.chyawanprash_bar_json != null && saveflag) {
                            String orange_bar_json = OrdersData.chyawanprash_bar_json;
                            JSONObject obj = new JSONObject(orange_bar_json);
                            JSONObject bar_json=new JSONObject();
                            Iterator<String> keys = obj.keys();
                            int count = 0;
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                                bar_json.put(ob.getString("batch_no"), obj.getString(key));
                                count = addStringNo(count + "", obj.getString(key));
                            }

                            if (!(count + "").equals(chyawanprash_bar)) {
                                showPopUp7();
                                saveflag = false;
                            }else{
                                batch_details_json.put("chyawanprash_bar", bar_json.toString());
                            }
                        }
                        if (OrdersData.orange_box_json != null && saveflag) {
                            String orange_bar_json = OrdersData.orange_box_json;
                            JSONObject obj = new JSONObject(orange_bar_json);
                            JSONObject bar_json=new JSONObject();
                            Iterator<String> keys = obj.keys();
                            int count = 0;
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                                bar_json.put(ob.getString("batch_no"), obj.getString(key));
                                count = addStringNo(count + "", obj.getString(key));
                            }

                            if (!(count + "").equals(orange_box)) {
                                showPopUp7();
                            }else{
                                batch_details_json.put("orange_box", bar_json.toString());
                            }
                        }
                        if (OrdersData.butterscotch_box_json != null && saveflag) {
                            String orange_bar_json = OrdersData.butterscotch_box_json;
                            JSONObject obj = new JSONObject(orange_bar_json);
                            JSONObject bar_json=new JSONObject();
                            Iterator<String> keys = obj.keys();
                            int count = 0;
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                                bar_json.put(ob.getString("batch_no"), obj.getString(key));
                                count = addStringNo(count + "", obj.getString(key));
                            }

                            if (!(count + "").equals(butterscotch_box)) {
                                showPopUp7();
                                saveflag = false;
                            }else{
                                batch_details_json.put("butterscotch_box", bar_json.toString());
                            }
                        }
                        if (OrdersData.chocopeanut_box_json != null && saveflag) {
                            String orange_bar_json = OrdersData.chocopeanut_box_json;
                            JSONObject obj = new JSONObject(orange_bar_json);
                            JSONObject bar_json=new JSONObject();
                            Iterator<String> keys = obj.keys();
                            int count = 0;
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                                bar_json.put(ob.getString("batch_no"), obj.getString(key));
                                count = addStringNo(count + "", obj.getString(key));
                            }

                            if (!(count + "").equals(chocopeanut_box)) {
                                showPopUp7();
                                saveflag = false;
                            }else{
                                batch_details_json.put("chocopeanut_box", bar_json.toString());
                            }
                        }
                        if (OrdersData.bambaiyachaat_box_json != null && saveflag) {
                            String orange_bar_json = OrdersData.bambaiyachaat_box_json;
                            JSONObject obj = new JSONObject(orange_bar_json);
                            JSONObject bar_json=new JSONObject();
                            Iterator<String> keys = obj.keys();
                            int count = 0;
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                                bar_json.put(ob.getString("batch_no"), obj.getString(key));
                                count = addStringNo(count + "", obj.getString(key));
                            }

                            if (!(count + "").equals(bambaiyachaat_box)) {
                                showPopUp7();
                                saveflag = false;
                            }else{
                                batch_details_json.put("bambaiyachaat_box", bar_json.toString());
                            }
                        }
                        if (OrdersData.mangoginger_box_json != null && saveflag) {
                            String orange_bar_json = OrdersData.mangoginger_box_json;
                            JSONObject obj = new JSONObject(orange_bar_json);
                            JSONObject bar_json=new JSONObject();
                            Iterator<String> keys = obj.keys();
                            int count = 0;
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                                bar_json.put(ob.getString("batch_no"), obj.getString(key));
                                count = addStringNo(count + "", obj.getString(key));
                            }

                            if (!(count + "").equals(mangoginger_box)) {
                                showPopUp7();
                                saveflag = false;
                            }else{
                                batch_details_json.put("mangoginger_box", bar_json.toString());
                            }
                        }
                        if (OrdersData.berry_blast_box_json != null && saveflag) {
                            String orange_bar_json = OrdersData.berry_blast_box_json;
                            JSONObject obj = new JSONObject(orange_bar_json);
                            JSONObject bar_json=new JSONObject();
                            Iterator<String> keys = obj.keys();
                            int count = 0;
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                                bar_json.put(ob.getString("batch_no"), obj.getString(key));
                                count = addStringNo(count + "", obj.getString(key));
                            }

                            if (!(count + "").equals(berry_blast_box)) {
                                showPopUp7();
                                saveflag = false;
                            }else{
                                batch_details_json.put("berry_blast_box", bar_json.toString());
                            }
                        }
                        if (OrdersData.chyawanprash_box_json != null && saveflag) {
                            String orange_bar_json = OrdersData.chyawanprash_box_json;
                            JSONObject obj = new JSONObject(orange_bar_json);
                            JSONObject bar_json=new JSONObject();
                            Iterator<String> keys = obj.keys();
                            int count = 0;
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                                bar_json.put(ob.getString("batch_no"), obj.getString(key));
                                count = addStringNo(count + "", obj.getString(key));
                            }

                            if (!(count + "").equals(chyawanprash_box)) {
                                showPopUp7();
                                saveflag = false;
                            }else{
                                batch_details_json.put("chyawanprash_box", bar_json.toString());
                            }
                        }
                        if (OrdersData.variety_box_json != null && saveflag) {
                            String orange_bar_json = OrdersData.variety_box_json;
                            JSONObject obj = new JSONObject(orange_bar_json);
                            JSONObject bar_json=new JSONObject();
                            Iterator<String> keys = obj.keys();
                            int count = 0;
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                                bar_json.put(ob.getString("batch_no"), obj.getString(key));
                                count = addStringNo(count + "", obj.getString(key));
                            }

                            if (!(count + "").equals(variety_box)) {
                                showPopUp7();
                                saveflag = false;
                            }else{
                                batch_details_json.put("variety_box", bar_json.toString());
                            }
                        }
                        if (OrdersData.chocolate_cookies_json != null && saveflag) {
                            String orange_bar_json = OrdersData.chocolate_cookies_json;
                            JSONObject obj = new JSONObject(orange_bar_json);
                            JSONObject bar_json=new JSONObject();
                            Iterator<String> keys = obj.keys();
                            int count = 0;
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                                bar_json.put(ob.getString("batch_no"), obj.getString(key));
                                count = addStringNo(count + "", obj.getString(key));
                            }

                            if (!(count + "").equals(chocolate_cookies)) {
                                Log.e("visit", "count: " + count + " choco_cook: " + chocolate_cookies);
                                showPopUp7();
                                saveflag = false;
                            }else{
                                batch_details_json.put("chocolate_cookies", bar_json.toString());
                            }
                        }
                        if (OrdersData.dark_chocolate_cookies_json != null && saveflag) {
                            String orange_bar_json = OrdersData.dark_chocolate_cookies_json;
                            JSONObject obj = new JSONObject(orange_bar_json);
                            JSONObject bar_json=new JSONObject();
                            Iterator<String> keys = obj.keys();
                            int count = 0;
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                                bar_json.put(ob.getString("batch_no"), obj.getString(key));
                                count = addStringNo(count + "", obj.getString(key));
                            }

                            if (!(count + "").equals(dark_chocolate_cookies)) {
                                showPopUp7();
                                saveflag = false;
                            }else{
                                batch_details_json.put("dark_chocolate_cookies", bar_json.toString());
                            }
                        }
                        if (OrdersData.cranberry_cookies_json != null && saveflag) {
                            String orange_bar_json = OrdersData.cranberry_cookies_json;
                            JSONObject obj = new JSONObject(orange_bar_json);
                            JSONObject bar_json=new JSONObject();
                            Iterator<String> keys = obj.keys();
                            int count = 0;
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                                bar_json.put(ob.getString("batch_no"), obj.getString(key));
                                count = addStringNo(count + "", obj.getString(key));
                            }

                            if (!(count + "").equals(cranberry_cookies)) {
                                showPopUp7();
                                saveflag = false;
                            }else{
                                batch_details_json.put("cranberry_cookies", bar_json.toString());
                            }
                        }
                        if (OrdersData.cranberry_orange_json != null && saveflag) {
                            String orange_bar_json = OrdersData.cranberry_orange_json;
                            JSONObject obj = new JSONObject(orange_bar_json);
                            JSONObject bar_json=new JSONObject();
                            Iterator<String> keys = obj.keys();
                            int count = 0;
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                                bar_json.put(ob.getString("batch_no"), obj.getString(key));
                                count = addStringNo(count + "", obj.getString(key));
                            }

                            if (!(count + "").equals(cranberry_orange_zest)) {
                                showPopUp7();
                                saveflag = false;
                            }else{
                                batch_details_json.put("cranberry_orange_zest", bar_json.toString());
                            }
                        }
                        if (OrdersData.fig_raisins_json != null && saveflag) {
                            String orange_bar_json = OrdersData.fig_raisins_json;
                            JSONObject obj = new JSONObject(orange_bar_json);
                            JSONObject bar_json=new JSONObject();
                            Iterator<String> keys = obj.keys();
                            int count = 0;
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                                bar_json.put(ob.getString("batch_no"), obj.getString(key));
                                count = addStringNo(count + "", obj.getString(key));
                            }

                            if (!(count + "").equals(fig_raisins)) {
                                showPopUp7();
                                saveflag = false;
                            }else{
                                batch_details_json.put("fig_raisins", bar_json.toString());
                            }
                        }
                        if (OrdersData.papaya_pineapple_json != null && saveflag) {
                            String orange_bar_json = OrdersData.papaya_pineapple_json;
                            JSONObject obj = new JSONObject(orange_bar_json);
                            JSONObject bar_json=new JSONObject();
                            Iterator<String> keys = obj.keys();
                            int count = 0;
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject ob=new JSONObject(arr.getString(Integer.parseInt(key) - 1));
                                bar_json.put(ob.getString("batch_no"), obj.getString(key));
                                count = addStringNo(count + "", obj.getString(key));
                            }

                            if (!(count + "").equals(papaya_pineapple)) {
                                showPopUp7();
                                saveflag = false;
                            }else{
                                batch_details_json.put("papaya_pineapple", bar_json.toString());
                            }
                        }
                        if(batch_details_json!=null){
                            OrdersData.batch_details=batch_details_json.toString();
                        }
                        if (saveflag) {
                            View_load_dialog.showDialog();
                        }
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (saveflag) {
                                    Log.e("save", "stage6");
                                    Log.e("visit", "here");
                                    isNetwork = isNETWORK();
                                    if (isNetwork) {
                                        Toast.makeText(context, "Data Saved ", Toast.LENGTH_LONG).show();

                                        Log.e("visit", "lat: " + OrdersData.latitude + " lon: " + OrdersData.longitude);
                                        OrdersData.distributor_name = distributor_name;
                                        if (flag == 1) {
                                            OrdersData.area = area.getSelectedItem().toString();
                                        } else {
                                            if (channel_type.equals("GT")) {
                                                OrdersData.area = area1.getText().toString();
                                            } else {
                                                OrdersData.location = location1.getText().toString();
                                            }
                                        }

                                        // Get user defined values
                                        //Name = fname.getText().toing();

                                        // Create data variable for sent values to server
                                        //Toast.makeText(context, ""+sales_rep_id+channel_type+distributor_type+distributor_id+"  "+store_id+ distributor_name+remarks+follow_type+ispermenant, Toast.LENGTH_SHORT).show();
                                        String session_id = session.getSessionid();
                                        OrdersData.orange_bar = orange_bar;
                                        OrdersData.butterscotch_bar = butterscotch_bar;
                                        OrdersData.chocopeanut_bar = chocopeanut_bar;
                                        OrdersData.bambaiyachaat_bar = bambaiyachaat_bar;
                                        OrdersData.mangoginger_bar = mangoginger_bar;
                                        OrdersData.berry_blast_bar = berry_blast_bar;
                                        OrdersData.chyawanprash_bar = chyawanprash_bar;
                                        OrdersData.orange_box = orange_box;
                                        OrdersData.butterscotch_box = butterscotch_box;
                                        OrdersData.chocopeanut_box = chocopeanut_box;
                                        OrdersData.bambaiyachaat_box = bambaiyachaat_box;
                                        OrdersData.mangoginger_box = mangoginger_box;
                                        OrdersData.berry_blast_box = berry_blast_box;
                                        OrdersData.chyawanprash_box = chyawanprash_box;
                                        OrdersData.variety_box = variety_box;
                                        OrdersData.chocolate_cookies_box = chocolate_cookies;
                                        OrdersData.dark_chocolate_cookies_box = dark_chocolate_cookies;
                                        OrdersData.cranberry_cookies_box = cranberry_cookies;
                                        OrdersData.cranberry_orange_box = cranberry_orange_zest;
                                        OrdersData.fig_raisins_box = fig_raisins;
                                        OrdersData.papaya_pineapple_box = papaya_pineapple;

                                        showPopUp12();

                                        add_event_in_db(distributor_name);


                                        try {


                                            String data = null;
                                            if (OrdersData.latitude == null || OrdersData.longitude == null || OrdersData.latitude.equals("0") || OrdersData.longitude.equals("0")) {
                                                GPSTracker gpsTracker = new GPSTracker(context);
                                                OrdersData.latitude = gpsTracker.getLatitude() + "";
                                                OrdersData.longitude = gpsTracker.getLongitude() + "";
                                            }


                                            data = "&" + URLEncoder.encode("sales_rep_id", "UTF-8")
                                                    + "=" + URLEncoder.encode(sales_rep_id, "UTF-8");

                                            data += "&" + URLEncoder.encode("id", "UTF-8")
                                                    + "=" + URLEncoder.encode(id, "UTF-8");

                                            data += "&" + URLEncoder.encode("ispermenant", "UTF-8")
                                                    + "=" + URLEncoder.encode(ispermenant, "UTF-8");

                                            data += "&" + URLEncoder.encode("sequence", "UTF-8")
                                                    + "=" + URLEncoder.encode(sequence, "UTF-8");

                                            data += "&" + URLEncoder.encode("beat_plan_id", "UTF-8")
                                                    + "=" + URLEncoder.encode(bit_plan_id, "UTF-8");

                                            data += "&" + URLEncoder.encode("srld", "UTF-8")
                                                    + "=" + URLEncoder.encode(srld, "UTF-8");

                                            data += "&" + URLEncoder.encode("session_id", "UTF-8")
                                                    + "=" + URLEncoder.encode(session_id, "UTF-8");

                                            data += "&" + URLEncoder.encode("channel_type", "UTF-8")
                                                    + "=" + URLEncoder.encode(channel_type, "UTF-8");

                                            data += "&" + URLEncoder.encode("distributor_type", "UTF-8")
                                                    + "=" + URLEncoder.encode(distributor_type, "UTF-8");

                                            data += "&" + URLEncoder.encode("distributor_id", "UTF-8")
                                                    + "=" + URLEncoder.encode(distributor_id, "UTF-8");

                                            data += "&" + URLEncoder.encode("store_id", "UTF-8")
                                                    + "=" + URLEncoder.encode(store_id, "UTF-8");

                                            data += "&" + URLEncoder.encode("zone_id", "UTF-8")
                                                    + "=" + URLEncoder.encode(zone_id, "UTF-8");

                                            data += "&" + URLEncoder.encode("area_id", "UTF-8")
                                                    + "=" + URLEncoder.encode(area_id, "UTF-8");

                                            data += "&" + URLEncoder.encode("location_id", "UTF-8")
                                                    + "=" + URLEncoder.encode(location_id, "UTF-8");

                                            data += "&" + URLEncoder.encode("distributor_name", "UTF-8")
                                                    + "=" + URLEncoder.encode(distributor_name, "UTF-8");

                                            data += "&" + URLEncoder.encode("place_order", "UTF-8")
                                                    + "=" + URLEncoder.encode("No", "UTF-8");

                                            data += "&" + URLEncoder.encode("latitude", "UTF-8")
                                                    + "=" + URLEncoder.encode(OrdersData.latitude, "UTF-8");

                                            data += "&" + URLEncoder.encode("longitude", "UTF-8")
                                                    + "=" + URLEncoder.encode(OrdersData.longitude, "UTF-8");

                                            data += "&" + URLEncoder.encode("remarks", "UTF-8")
                                                    + "=" + URLEncoder.encode(remarks, "UTF-8");

                                            data += "&" + URLEncoder.encode("reation_id", "UTF-8")
                                                    + "=" + URLEncoder.encode(reation_id, "UTF-8");

                                            data += "&" + URLEncoder.encode("mid", "UTF-8")
                                                    + "=" + URLEncoder.encode(mid, "UTF-8");

                                            data += "&" + URLEncoder.encode("distributor_status", "UTF-8")
                                                    + "=" + URLEncoder.encode("", "UTF-8");

                                            data += "&" + URLEncoder.encode("sales_rep_loc_id", "UTF-8")
                                                    + "=" + URLEncoder.encode(sales_rep_loc_id, "UTF-8");

                                            data += "&" + URLEncoder.encode("merchandiser_stock_id", "UTF-8")
                                                    + "=" + URLEncoder.encode(merchandiser_stock_id, "UTF-8");

                                            data += "&" + URLEncoder.encode("follow_type", "UTF-8")
                                                    + "=" + URLEncoder.encode(follow_type, "UTF-8");

                                            data += "&" + URLEncoder.encode("followup_date", "UTF-8")
                                                    + "=" + URLEncoder.encode(followup_date, "UTF-8");

                                            data += "&" + URLEncoder.encode("orange_bar", "UTF-8")
                                                    + "=" + URLEncoder.encode(orange_bar, "UTF-8");

                                            data += "&" + URLEncoder.encode("orange_box", "UTF-8")
                                                    + "=" + URLEncoder.encode(orange_box, "UTF-8");

                                            data += "&" + URLEncoder.encode("butterscotch_bar", "UTF-8")
                                                    + "=" + URLEncoder.encode(butterscotch_bar, "UTF-8");

                                            data += "&" + URLEncoder.encode("butterscotch_box", "UTF-8")
                                                    + "=" + URLEncoder.encode(butterscotch_box, "UTF-8");

                                            data += "&" + URLEncoder.encode("chocopeanut_bar", "UTF-8")
                                                    + "=" + URLEncoder.encode(chocopeanut_bar, "UTF-8");

                                            data += "&" + URLEncoder.encode("chocopeanut_box", "UTF-8")
                                                    + "=" + URLEncoder.encode(chocopeanut_box, "UTF-8");

                                            data += "&" + URLEncoder.encode("bambaiyachaat_bar", "UTF-8")
                                                    + "=" + URLEncoder.encode(bambaiyachaat_bar, "UTF-8");

                                            data += "&" + URLEncoder.encode("bambaiyachaat_box", "UTF-8")
                                                    + "=" + URLEncoder.encode(bambaiyachaat_box, "UTF-8");

                                            data += "&" + URLEncoder.encode("mangoginger_bar", "UTF-8")
                                                    + "=" + URLEncoder.encode(mangoginger_bar, "UTF-8");

                                            data += "&" + URLEncoder.encode("mangoginger_box", "UTF-8")
                                                    + "=" + URLEncoder.encode(mangoginger_box, "UTF-8");

                                            data += "&" + URLEncoder.encode("berry_blast_bar", "UTF-8")
                                                    + "=" + URLEncoder.encode(berry_blast_bar, "UTF-8");

                                            data += "&" + URLEncoder.encode("berry_blast_box", "UTF-8")
                                                    + "=" + URLEncoder.encode(berry_blast_box, "UTF-8");

                                            data += "&" + URLEncoder.encode("chyawanprash_bar", "UTF-8")
                                                    + "=" + URLEncoder.encode(chyawanprash_bar, "UTF-8");

                                            data += "&" + URLEncoder.encode("chyawanprash_box", "UTF-8")
                                                    + "=" + URLEncoder.encode(chyawanprash_box, "UTF-8");

                                            data += "&" + URLEncoder.encode("variety_box", "UTF-8")
                                                    + "=" + URLEncoder.encode(variety_box, "UTF-8");

                                            data += "&" + URLEncoder.encode("chocolate_cookies", "UTF-8")
                                                    + "=" + URLEncoder.encode(chocolate_cookies, "UTF-8");

                                            data += "&" + URLEncoder.encode("dark_chocolate_cookies", "UTF-8")
                                                    + "=" + URLEncoder.encode(dark_chocolate_cookies, "UTF-8");

                                            data += "&" + URLEncoder.encode("cranberry_cookies", "UTF-8")
                                                    + "=" + URLEncoder.encode(cranberry_cookies, "UTF-8");

                                            data += "&" + URLEncoder.encode("fig_raisins", "UTF-8")
                                                    + "=" + URLEncoder.encode(fig_raisins, "UTF-8");

                                            data += "&" + URLEncoder.encode("papaya_pineapple", "UTF-8")
                                                    + "=" + URLEncoder.encode(papaya_pineapple, "UTF-8");

                                            data += "&" + URLEncoder.encode("cranberry_orange_zest", "UTF-8")
                                                    + "=" + URLEncoder.encode(cranberry_orange_zest, "UTF-8");

                                            Log.e("nafoe", OrdersData.batch_details);

                                            if(OrdersData.batch_details!=null) {
                                                data += "&" + URLEncoder.encode("batch_detail", "UTF-8")
                                                        + "=" + URLEncoder.encode(OrdersData.batch_details, "UTF-8");
                                            }else{
                                                data += "&" + URLEncoder.encode("batch_detail", "UTF-8")
                                                        + "=" + URLEncoder.encode("", "UTF-8");

                                            }

                                            String text = "";
                                            BufferedReader reader = null;
                                            // Send data
                                            try {
                                                Log.e("visit1", "4");
                                                // Defined URL  where to send data
                                                URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/Sales_rep_store_plan_mobile_app/save_api");

                                                // Send POST data request

                                                URLConnection conn = url.openConnection();
                                                conn.setDoOutput(true);
                                                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                                                wr.write(data);
                                                wr.flush();

                                                // Get the server response

                                                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                                StringBuilder sb = new StringBuilder();
                                                String line = null;

                                                // Read Server Response
                                                while ((line = reader.readLine()) != null) {
                                                    // Append server response in string
                                                    sb.append(line + "\n");
                                                }


                                                text = sb.toString();
                                                //Log.e("visit1", text+" text");
                                                // Toast.makeText(context,"Text sec"+text,Toast.LENGTH_LONG).show();
                                                JSONObject obj = new JSONObject(text);



                                                if (obj == null) {
                                                    //return "";
                                                } else {
                                                    String monday = obj.getString("Monday");
                                                    String tuesday = obj.getString("Tuesday");
                                                    String wednesday = obj.getString("Wednesday");
                                                    String thursday = obj.getString("Thursday");
                                                    String friday = obj.getString("Friday");
                                                    String saturday = obj.getString("Saturday");
                                                    String sunday = obj.getString("Sunday");

                                                    if (!monday.equals("[]")) {
                                                        //Toast.makeText(context,"monday "+text,Toast.LENGTH_LONG).show();
                                                        session.setmon_frag(monday);
                                                    }
                                                    if (!tuesday.equals("[]")) {
                                                        //Toast.makeText(context,"tuesday "+text,Toast.LENGTH_LONG).show();
                                                        session.settues_frag(tuesday);
                                                    }
                                                    if (!wednesday.equals("[]")) {
                                                        //Toast.makeText(context,"wednesday "+text,Toast.LENGTH_LONG).show();
                                                        session.setwed_frag(wednesday);
                                                    }
                                                    if (!thursday.equals("[]")) {
                                                        //Toast.makeText(context,"thursday "+text,Toast.LENGTH_LONG).show();
                                                        session.setthu_frag(thursday);
                                                    }
                                                    if (!friday.equals("[]")) {
                                                        //Toast.makeText(context,"friday "+text,Toast.LENGTH_LONG).show();
                                                        session.setfri_frag(friday);
                                                    }
                                                    if (!saturday.equals("[]")) {
                                                        //Toast.makeText(context,"saturday "+text,Toast.LENGTH_LONG).show();
                                                        session.setsat_frag(saturday);
                                                    }
                                                    if (!sunday.equals("[]")) {
                                                        //Toast.makeText(context,"saturday "+text,Toast.LENGTH_LONG).show();
                                                        session.setsun_frag(sunday);
                                                    }
                                                }


                                                Log.e("save", "stage7");
                                                // return text;
                                            } catch (Exception ex) {
                                                Log.e("visit", "savedata error: " + ex.toString());
                                                View_load_dialog.hideDialog();
                                            } finally {
                                                try {

                                                    reader.close();
                                                } catch (Exception ex) {
                                                    Log.e("visit", "savedata error2: " + ex.toString());
                                                    View_load_dialog.hideDialog();
                                                }
                                            }
                                        } catch (UnsupportedEncodingException e) {
                                            Log.e("visit", "savedata error3: " + e.toString());
                                            View_load_dialog.hideDialog();
                                        }
                                        View_load_dialog.hideDialog();
                                    }
                                    View_load_dialog.hideDialog();
                                } else {
                                    Log.e("save", "could not save");
                                    View_load_dialog.hideDialog();
                                }
                                View_load_dialog.hideDialog();

                            }
                        }, 3000);
                        View_load_dialog.hideDialog();

                    } else {
                        showPopUp10(sales_rep_id, channel_type, distributor_type, distributor_id, store_id, zone_id, area_id, location_id, distributor_name, remarks, reation_id, orange_bar, orange_box, butterscotch_bar, butterscotch_box, chocopeanut_bar, chocopeanut_box, bambaiyachaat_bar, bambaiyachaat_box, mangoginger_bar, mangoginger_box, berry_blast_bar, berry_blast_box, chyawanprash_bar, chyawanprash_box, variety_box, chocolate_cookies, dark_chocolate_cookies, cranberry_cookies, fig_raisins, papaya_pineapple, cranberry_orange_zest, follow_type, followup_date, srld, id, bit_plan_id, sequence, mid, sales_rep_loc_id, merchandiser_stock_id, ispermenant);
                        View_load_dialog.hideDialog();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                View_load_dialog.hideDialog();
            }
        }, 500);

        // Show response on activity
        // return "";
    }


    private void add_followup(String distributor_name) {
        String area2 = "";
        Intent i = getIntent();
        try {
            if (i.hasExtra("channel_type")) {
                if (flag != 1) {
                    if (i.getStringExtra("channel_type").equals("GT")) {
                        area2 = area1.getText().toString();
                    } else {
                        area2 = location1.getText().toString();
                    }
                } else {
                    if (i.getStringExtra("channel_type").equals("GT")) {
                        area2 = area.getSelectedItem().toString();
                    } else {
                        area2 = location.getSelectedItem().toString();
                    }
                }
            } else {
                if (flag != 1) {
                    if (channel1.getText().toString().equals("GT")) {
                        area2 = area1.getText().toString();
                    } else {
                        area2 = location1.getText().toString();
                    }
                } else {
                    if (channel.getSelectedItem().toString().equals("GT")) {
                        area2 = area.getSelectedItem().toString();
                    } else {
                        area2 = location.getSelectedItem().toString();
                    }
                }
            }


            String date = new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());
            JSONObject jsonObject = new JSONObject();
            GPSTracker gpsTracker = new GPSTracker(context);
            jsonObject.put("latitude", gpsTracker.getLatitude() + "");
            jsonObject.put("longitude", gpsTracker.getLongitude() + "");
            jsonObject.put("distributor_name", distributor_name);
            jsonObject.put("area", area2);
            jsonObject.put("followup_date", txtDate.getText().toString());

            String event_json = jsonObject.toString();
            OfflineDbHelper offlineDbHelper = new OfflineDbHelper(context);
            SQLiteDatabase db = offlineDbHelper.getWritableDatabase();
            offlineDbHelper.addEVENT("Followup", date, event_json, db);
            postActvityDetails("Followup", date, event_json);
            Log.e("visit", "followup event added to db");


        } catch (Exception e) {
            Log.e("visitfollow", e.toString());
        }
    }

    private void add_event_in_db(String distributor_name) {
        try {
            String area2 = "";
            Intent i = getIntent();
            if (i.hasExtra("channel_type")) {
                if (flag != 1) {
                    if (i.getStringExtra("channel_type").equals("GT")) {
                        area2 = area1.getText().toString();
                    } else {
                        area2 = location1.getText().toString();
                    }
                } else {
                    if (i.getStringExtra("channel_type").equals("GT")) {
                        area2 = area.getSelectedItem().toString();
                    } else {
                        area2 = location.getSelectedItem().toString();
                    }
                }
            } else {
                if (flag != 1) {
                    if (channel1.getText().toString().equals("GT")) {
                        area2 = area1.getText().toString();
                    } else {
                        area2 = location1.getText().toString();
                    }
                } else {
                    if (channel.getSelectedItem().toString().equals("GT")) {
                        area2 = area.getSelectedItem().toString();
                    } else {
                        area2 = location.getSelectedItem().toString();
                    }
                }
            }


            String date = new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());
            JSONObject jsonObject = new JSONObject();
            GPSTracker gpsTracker = new GPSTracker(context);
            jsonObject.put("latitude", gpsTracker.getLatitude() + "");
            jsonObject.put("longitude", gpsTracker.getLongitude() + "");
            jsonObject.put("distributor_name", distributor_name);
            jsonObject.put("area", area2);
            jsonObject.put("type", type_global);
            if (flag == 1) {
                jsonObject.put("new_or_old", "new_" + type.getSelectedItem().toString().toLowerCase() + "_" + channel.getSelectedItem().toString().toLowerCase());
            } else {
                jsonObject.put("new_or_old", "old");
            }
            if (zero_inventory_flag) {
                jsonObject.put("zero_inventory", "true");
            } else {
                jsonObject.put("zero_inventory", "false");
            }

            jsonObject.put("is_permanent", stris_permanent);
            String event_json = jsonObject.toString();

            OfflineDbHelper offlineDbHelper = new OfflineDbHelper(context);
            SQLiteDatabase db = offlineDbHelper.getWritableDatabase();
            offlineDbHelper.addEVENT("Store visit", date, event_json, db);
            Log.e("visitdetails_db", "event-inventory added to db");
            postActvityDetails("Store visit", date, event_json);

        } catch (Exception e) {
            Log.e("visitdetails_db", e.toString());
        }
    }

    public void postActvityDetails(String event_name, String event_time, String event_json) {
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        //String sales_rep_id=session.getsales_rep_id();

        String data = null;

        try {

            data = URLEncoder.encode("event_name", "UTF-8")
                    + "=" + URLEncoder.encode(event_name, "UTF-8");
            data += "&" + URLEncoder.encode("event_time", "UTF-8")
                    + "=" + URLEncoder.encode(event_time, "UTF-8");
            data += "&" + URLEncoder.encode("event_json", "UTF-8")
                    + "=" + URLEncoder.encode(event_json, "UTF-8");
            data += "&" + URLEncoder.encode("sales_rep_id", "UTF-8")
                    + "=" + URLEncoder.encode(session.getsales_rep_id(), "UTF-8");


        } catch (Exception e) {
            e.printStackTrace();
        }


        String text = "";
        BufferedReader reader = null;

        // Send data
        try {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/dashboard_mobile_app/set_notifications_api");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
        } catch (Exception ex) {
            Log.e("visitdetails", "postactivitydetails: " + ex.toString());
            //Toast.makeText(context,"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
                Log.e("visitdetails", "postactivitydetails2: " + ex.toString());
            }
        }
    }


    private void showPopUp12() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.alertdialog_share_whatsapp, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).setIcon(R.drawable.shape).create();
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) deleteDialogView);
        deleteDialog.setView(deleteDialogView);
        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!isAccessibilityOn(context, WhatsappAccessibilityService.class)) {
                        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        context.startActivity(intent);
                        restartflag2 = true;
                    } else {
                        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                        whatsappIntent.setType("text/plain");
                        whatsappIntent.setPackage("com.whatsapp");
                        whatsappIntent.putExtra(Intent.EXTRA_TEXT, returnWhatsappText() + "\n" + getResources().getString(R.string.whatsapp_suffix));
                        try {
                            startActivity(whatsappIntent);
                            Log.e("visit", "service started");
                            restartflag = true;
                            deleteDialog.dismiss();
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(context, "Please install whatsapp to share details on whatsapp", Toast.LENGTH_LONG).show();
                            deleteDialog.dismiss();
                            Log.e("visit", "error21 " + ex.toString());

                        }
                    }
                    OrdersData od = new OrdersData();
                    od.clear();
                } catch (Exception ex) {
                    Log.e("visit", "error12 " + ex.toString());
                }
                deleteDialog.dismiss();
            }
        });
        deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Visit_list_pageActivity.class);
                startActivityForResult(i, 1);
                finish();
                OrdersData od = new OrdersData();
                od.clear();
                deleteDialog.dismiss();
            }
        });
        if (!((Activity) context).isFinishing()) {
            deleteDialog.show();
            //show dialog
        }

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

    private String returnWhatsappText() {
        String result = "";
        try {

            java.text.DateFormat df = new SimpleDateFormat("h:mm a");
            String date = df.format(Calendar.getInstance().getTime());
            String area2 = "";
            Intent i = getIntent();
            if (i.hasExtra("channel_type")) {
                if (flag != 1) {
                    if (i.getStringExtra("channel_type").equals("GT")) {
                        area2 = area1.getText().toString();
                    } else {
                        area2 = location1.getText().toString();
                    }
                } else {
                    if (i.getStringExtra("channel_type").equals("GT")) {
                        area2 = area.getSelectedItem().toString();
                    } else {
                        area2 = location.getSelectedItem().toString();
                    }
                }
            } else {
                if (flag != 1) {
                    if (channel1.getText().toString().equals("GT")) {
                        area2 = area1.getText().toString();
                    } else {
                        area2 = location1.getText().toString();
                    }
                } else {
                    if (channel.getSelectedItem().toString().equals("GT")) {
                        area2 = area.getSelectedItem().toString();
                    } else {
                        area2 = location.getSelectedItem().toString();
                    }
                }
            }


            result += "Checkin: " + OrdersData.distributor_name + ", " + area2 + "\n";
            result += "Time: " + date + "\n\n";

            result += "*Inventory* \n";
            if ((!OrdersData.orange_bar.equals("") && !OrdersData.orange_bar.equals("0")) || (!OrdersData.butterscotch_bar.equals("") && !OrdersData.butterscotch_bar.equals("0")) || (!OrdersData.chocopeanut_bar.equals("") && !OrdersData.chocopeanut_bar.equals("0")) || (!OrdersData.bambaiyachaat_bar.equals("") && !OrdersData.bambaiyachaat_bar.equals("0")) || (!OrdersData.mangoginger_bar.equals("") && !OrdersData.mangoginger_bar.equals("0")) || (!OrdersData.berry_blast_bar.equals("") && !OrdersData.berry_blast_bar.equals("0")) || (!OrdersData.berry_blast_bar.equals("") && !OrdersData.berry_blast_bar.equals("0")) || (!OrdersData.chyawanprash_bar.equals("") && !OrdersData.chyawanprash_bar.equals("0"))) {
                result += "Bars- " + "\n";
            }
            if (!OrdersData.orange_bar.equals("") && !OrdersData.orange_bar.equals("0")) {
                result += "Orange: " + OrdersData.orange_bar + "\n";
            }
            if (OrdersData.orange_bar_json != null) {
                String orange_bar_json = OrdersData.orange_bar_json;
                JSONObject obj = new JSONObject(orange_bar_json);

                Iterator<String> keys = obj.keys();
                int count = 0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (count == 0) {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                    }
                }
                result += "\n";
            }
            if (!OrdersData.butterscotch_bar.equals("") && !OrdersData.butterscotch_bar.equals("0")) {
                result += "Butterscotch: " + OrdersData.butterscotch_bar + "\n";
            }
            if (OrdersData.butterscotch_bar_json != null) {
                String butterscotch_bar_json = OrdersData.butterscotch_bar_json;
                JSONObject obj = new JSONObject(butterscotch_bar_json);

                Iterator<String> keys = obj.keys();
                int count = 0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (count == 0) {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                    }
                }
                result += "\n";
            }
            if (!OrdersData.chocopeanut_bar.equals("") && !OrdersData.chocopeanut_bar.equals("0")) {
                result += "Choco: " + OrdersData.chocopeanut_bar + "\n";
            }
            if (OrdersData.chocopeanut_bar_json != null) {
                String chocopeanut_bar_json = OrdersData.chocopeanut_bar_json;
                JSONObject obj = new JSONObject(chocopeanut_bar_json);

                Iterator<String> keys = obj.keys();
                int count = 0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (count == 0) {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                    }
                }
                result += "\n";
            }
            if (!OrdersData.bambaiyachaat_bar.equals("") && !OrdersData.bambaiyachaat_bar.equals("0")) {
                result += "Chaat: " + OrdersData.bambaiyachaat_bar + "\n";
            }
            if (OrdersData.bambaiyachaat_bar_json != null) {
                String bambaiyachaat_bar_json = OrdersData.bambaiyachaat_bar_json;
                JSONObject obj = new JSONObject(bambaiyachaat_bar_json);

                Iterator<String> keys = obj.keys();
                int count = 0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (count == 0) {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                    }
                }
                result += "\n";
            }
            if (!OrdersData.mangoginger_bar.equals("") && !OrdersData.mangoginger_bar.equals("0")) {
                result += "Mango: " + OrdersData.mangoginger_bar + "\n";
            }
            if (OrdersData.mangoginger_bar_json != null) {
                String mangoginger_bar_json = OrdersData.mangoginger_bar_json;
                JSONObject obj = new JSONObject(mangoginger_bar_json);

                Iterator<String> keys = obj.keys();
                int count = 0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (count == 0) {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                    }
                }
                result += "\n";
            }
            if (!OrdersData.berry_blast_bar.equals("") && !OrdersData.berry_blast_bar.equals("0")) {
                result += "Berry: " + OrdersData.berry_blast_bar + "\n";
            }
            if (OrdersData.berry_blast_bar_json != null) {
                String berry_blast_bar_json = OrdersData.berry_blast_bar_json;
                JSONObject obj = new JSONObject(berry_blast_bar_json);

                Iterator<String> keys = obj.keys();
                int count = 0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (count == 0) {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                    }
                }
                result += "\n";
            }
            if (!OrdersData.chyawanprash_bar.equals("") && !OrdersData.chyawanprash_bar.equals("0")) {
                result += "Chyawanprash: " + OrdersData.chyawanprash_bar + "\n";
            }
            if (OrdersData.chyawanprash_bar_json != null) {
                String chyawanprash_bar_json = OrdersData.chyawanprash_bar_json;
                JSONObject obj = new JSONObject(chyawanprash_bar_json);

                Iterator<String> keys = obj.keys();
                int count = 0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (count == 0) {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                    }
                }
                result += "\n";
            }
            if ((!OrdersData.orange_box.equals("") && !OrdersData.orange_box.equals("0")) || (!OrdersData.butterscotch_box.equals("") && !OrdersData.butterscotch_box.equals("0")) || (!OrdersData.chocopeanut_box.equals("") && !OrdersData.chocopeanut_box.equals("0")) || (!OrdersData.bambaiyachaat_box.equals("") && !OrdersData.bambaiyachaat_box.equals("0")) || (!OrdersData.mangoginger_box.equals("") && !OrdersData.mangoginger_box.equals("0")) || (!OrdersData.berry_blast_box.equals("") && !OrdersData.berry_blast_box.equals("0")) || (!OrdersData.chyawanprash_box.equals("") && !OrdersData.chyawanprash_box.equals("0"))) {
                result += "Box- " + "\n";
            }

            if (!OrdersData.orange_box.equals("") && !OrdersData.orange_box.equals("0")) {
                result += "Orange: " + OrdersData.orange_box + "\n";
            }
            if (OrdersData.orange_box_json != null) {
                String orange_box_json = OrdersData.orange_box_json;
                JSONObject obj = new JSONObject(orange_box_json);

                Iterator<String> keys = obj.keys();
                int count = 0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (count == 0) {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                    }
                }
                result += "\n";
            }
            if (!OrdersData.butterscotch_box.equals("") && !OrdersData.butterscotch_box.equals("0")) {
                result += "Butterscotch: " + OrdersData.butterscotch_box + "\n";
            }
            if (OrdersData.butterscotch_box_json != null) {
                String butterscotch_box_json = OrdersData.butterscotch_box_json;
                JSONObject obj = new JSONObject(butterscotch_box_json);

                Iterator<String> keys = obj.keys();
                int count = 0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (count == 0) {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                    }
                }
                result += "\n";
            }
            if (!OrdersData.chocopeanut_box.equals("") && !OrdersData.chocopeanut_box.equals("0")) {
                result += "Choco: " + OrdersData.chocopeanut_box + "\n";
            }
            if (OrdersData.chocopeanut_box_json != null) {
                String chocopeanut_box_json = OrdersData.chocopeanut_box_json;
                JSONObject obj = new JSONObject(chocopeanut_box_json);

                Iterator<String> keys = obj.keys();
                int count = 0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (count == 0) {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                    }
                }
                result += "\n";
            }
            if (!OrdersData.bambaiyachaat_box.equals("") && !OrdersData.bambaiyachaat_box.equals("0")) {
                result += "Chaat: " + OrdersData.bambaiyachaat_box + "\n";
            }
            if (OrdersData.bambaiyachaat_box_json != null) {
                String bambaiyachaat_box_json = OrdersData.bambaiyachaat_box_json;
                JSONObject obj = new JSONObject(bambaiyachaat_box_json);

                Iterator<String> keys = obj.keys();
                int count = 0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (count == 0) {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                    }
                }
                result += "\n";
            }
            if (!OrdersData.mangoginger_box.equals("") && !OrdersData.mangoginger_box.equals("0")) {
                result += "Mango: " + OrdersData.mangoginger_box + "\n";
            }
            if (OrdersData.mangoginger_box_json != null) {
                String mangoginger_box_json = OrdersData.mangoginger_box_json;
                JSONObject obj = new JSONObject(mangoginger_box_json);

                Iterator<String> keys = obj.keys();
                int count = 0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (count == 0) {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                    }
                }
                result += "\n";
            }
            if (!OrdersData.chyawanprash_box.equals("") && !OrdersData.chyawanprash_box.equals("0")) {
                result += "Chyawanprash: " + OrdersData.chyawanprash_box + "\n";
            }
            if (OrdersData.chyawanprash_box_json != null) {
                String chyawanprash_box_json = OrdersData.chyawanprash_box_json;
                JSONObject obj = new JSONObject(chyawanprash_box_json);

                Iterator<String> keys = obj.keys();
                int count = 0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (count == 0) {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                    }
                }
                result += "\n";
            }
            if (!OrdersData.variety_box.equals("") && !OrdersData.variety_box.equals("0")) {
                result += "Variety: " + OrdersData.variety_box + "\n";
            }
            if (OrdersData.variety_box_json != null) {
                String variety_box_json = OrdersData.variety_box_json;
                JSONObject obj = new JSONObject(variety_box_json);

                Iterator<String> keys = obj.keys();
                int count = 0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (count == 0) {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                    }
                }
                result += "\n";
            }
            if ((!OrdersData.cranberry_cookies_box.equals("") && !OrdersData.cranberry_cookies_box.equals("0")) || (!OrdersData.dark_chocolate_cookies_box.equals("") && !OrdersData.dark_chocolate_cookies_box.equals("0")) || (!OrdersData.chocolate_cookies_box.equals("") && !OrdersData.chocolate_cookies_box.equals("0"))) {
                result += "Cookies- " + "\n";
            }
            if (!OrdersData.chocolate_cookies_box.equals("") && !OrdersData.chocolate_cookies_box.equals("0")) {
                result += "Chocolate: " + OrdersData.chocolate_cookies_box + "\n";
            }
            if (OrdersData.chocolate_cookies_json != null) {
                String chocolate_cookies_json = OrdersData.chocolate_cookies_json;
                JSONObject obj = new JSONObject(chocolate_cookies_json);

                Iterator<String> keys = obj.keys();
                int count = 0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (count == 0) {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                    }
                }
                result += "\n";
            }
            if (!OrdersData.dark_chocolate_cookies_box.equals("") && !OrdersData.dark_chocolate_cookies_box.equals("0")) {
                result += "Dark chocolate: " + OrdersData.dark_chocolate_cookies_box + "\n";
            }
            if (OrdersData.dark_chocolate_cookies_json != null) {
                String dark_chocolate_cookies_json = OrdersData.dark_chocolate_cookies_json;
                JSONObject obj = new JSONObject(dark_chocolate_cookies_json);

                Iterator<String> keys = obj.keys();
                int count = 0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (count == 0) {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                    }
                }
                result += "\n";
            }
            if (!OrdersData.cranberry_cookies_box.equals("") && !OrdersData.cranberry_cookies_box.equals("0")) {
                result += "Cranberry: " + OrdersData.cranberry_cookies_box + "\n";
            }
            if (OrdersData.cranberry_cookies_json != null) {
                String cranberry_cookies_json = OrdersData.cranberry_cookies_json;
                JSONObject obj = new JSONObject(cranberry_cookies_json);

                Iterator<String> keys = obj.keys();
                int count = 0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (count == 0) {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                    }
                }
                result += "\n";
            }
            if ((!OrdersData.cranberry_orange_box.equals("") && !OrdersData.cranberry_orange_box.equals("0")) || (!OrdersData.fig_raisins_box.equals("") && !OrdersData.fig_raisins_box.equals("0")) || (!OrdersData.papaya_pineapple_box.equals("") && !OrdersData.papaya_pineapple_box.equals("0"))) {
                result += "Trailmix- " + "\n";
            }

            if (!OrdersData.fig_raisins_box.equals("") && !OrdersData.fig_raisins_box.equals("0")) {
                result += "Figs raisin: " + OrdersData.fig_raisins_box + "\n";
            }
            if (OrdersData.fig_raisins_json != null) {
                String fig_raisins_json = OrdersData.fig_raisins_json;
                JSONObject obj = new JSONObject(fig_raisins_json);

                Iterator<String> keys = obj.keys();
                int count = 0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (count == 0) {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                    }
                }
                result += "\n";
            }
            if (!OrdersData.cranberry_orange_box.equals("") && !OrdersData.cranberry_orange_box.equals("0")) {
                result += "Cranberry orange: " + OrdersData.cranberry_orange_box + "\n";
            }
            if (OrdersData.cranberry_orange_json != null) {
                String cranberry_orange_json = OrdersData.cranberry_orange_json;
                JSONObject obj = new JSONObject(cranberry_orange_json);

                Iterator<String> keys = obj.keys();
                int count = 0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (count == 0) {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                    }
                }
                result += "\n";
            }
            if (!OrdersData.papaya_pineapple_box.equals("") && !OrdersData.papaya_pineapple_box.equals("0")) {
                result += "Papaya pineapple: " + OrdersData.papaya_pineapple_box + "\n";
            }
            if (OrdersData.papaya_pineapple_json != null) {
                String papaya_pineapple_json = OrdersData.papaya_pineapple_json;
                JSONObject obj = new JSONObject(papaya_pineapple_json);

                Iterator<String> keys = obj.keys();
                int count = 0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (count == 0) {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key) - 1);
                        result += data1.getString("batch_no") + ":    " + obj.getString(key) + "\n";
                    }
                }
                result += "\n";
            }
            if (result.endsWith("*Inventory* \n")) {
                result += "No Stock\n";
            }
        } catch (Exception e) {
            Log.e("visit", "whatsapp: " + e.toString());
        }

        return result;
    }

    private void showPopUp7() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.alertdialog_visit_details_changed, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).setIcon(R.drawable.shape).create();
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) deleteDialogView);
        deleteDialog.setView(deleteDialogView);
        deleteDialogView.findViewById(R.id.btn_okay).setOnClickListener(new View.OnClickListener() {
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

    private void showPopUp4() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.alertdialog_duplicate_retailer, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).setIcon(R.drawable.shape).create();
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) deleteDialogView);
        deleteDialog.setView(deleteDialogView);
        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View_load_dialog.showDialog();

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent i = new Intent(context, Visit_detailsActivity.class);
                        i.putExtra("fabtype", "Old");
                        startActivityForResult(i, 1);
                        finish();
                        return;
                    }
                }, 500);
                deleteDialog.dismiss();
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
                        finish();
                        return;
                    }
                }, 500);
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

    public String getaddapi(String id, String get_channel_type, String follow_type) throws UnsupportedEncodingException {
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        String sales_rep_id = session.getsales_rep_id();
        String distributor_id = session.getdistributor_id();
        String bit_id = session.getbeat_id();


        String data = "&" + URLEncoder.encode("id", "UTF-8")
                + "=" + URLEncoder.encode(id, "UTF-8");

        data += "&" + URLEncoder.encode("get_channel_type", "UTF-8")
                + "=" + URLEncoder.encode(get_channel_type, "UTF-8");

        data += "&" + URLEncoder.encode("follow_type", "UTF-8")
                + "=" + URLEncoder.encode(follow_type, "UTF-8");

        data += "&" + URLEncoder.encode("sales_rep_id", "UTF-8")
                + "=" + URLEncoder.encode(sales_rep_id, "UTF-8");

        data += "&" + URLEncoder.encode("distributor_id", "UTF-8")
                + "=" + URLEncoder.encode(distributor_id, "UTF-8");

        data += "&" + URLEncoder.encode("beat_id", "UTF-8")
                + "=" + URLEncoder.encode(bit_id, "UTF-8");

        String text = "";
        BufferedReader reader = null;

        // Send data
        try {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/Sales_rep_store_plan_mobile_app/add_api");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
            //Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            return text;
        } catch (Exception ex) {
            //Toast.makeText(context,"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
            }
        }

        // Show response on activity
        return "";

    }

    public String getbatchnos()  throws UnsupportedEncodingException
    {
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        String data1="";
        String data = URLEncoder.encode("data", "UTF-8")
                + "=" + URLEncoder.encode(data1, "UTF-8");


        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/Sales_rep_store_plan_mobile_app/get_batch_details_api");

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
            Log.e("batch details", "8 "+ex.toString());
            //Toast.makeText(getContext(),"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        }
        finally
        {
            try
            {

                reader.close();
            }

            catch(Exception ex) {
                Log.e("batch details", "9 "+ex.toString());
            }
        }

        // Show response on activity
        return "";

    }


    public String geteditapi(String id, String get_channel_type, String follow_type) throws UnsupportedEncodingException {
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        String sales_rep_id = session.getsales_rep_id();
        String distributor_id = session.getdistributor_id();
        String bit_id = session.getbeat_id();


        String data = "&" + URLEncoder.encode("id", "UTF-8")
                + "=" + URLEncoder.encode(id, "UTF-8");

        data += "&" + URLEncoder.encode("get_channel_type", "UTF-8")
                + "=" + URLEncoder.encode(get_channel_type, "UTF-8");

        data += "&" + URLEncoder.encode("temp", "UTF-8")
                + "=" + URLEncoder.encode(follow_type, "UTF-8");

        data += "&" + URLEncoder.encode("sales_rep_id", "UTF-8")
                + "=" + URLEncoder.encode(sales_rep_id, "UTF-8");

        data += "&" + URLEncoder.encode("distributor_id", "UTF-8")
                + "=" + URLEncoder.encode(distributor_id, "UTF-8");

        data += "&" + URLEncoder.encode("beat_id", "UTF-8")
                + "=" + URLEncoder.encode(bit_id, "UTF-8");

        String text = "";
        BufferedReader reader = null;

        // Send data
        try {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/Sales_rep_store_plan_mobile_app/edit_api");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();

            return text;
        } catch (Exception ex) {
            //Toast.makeText(getContext(),"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
            }
        }

        // Show response on activity
        return "";

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
                showcheckoutpopup1();
                deleteDialog.dismiss();
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

    private void getLocation() {
        try {
            if (canGetLocation) {
                Log.e("outside", "hererer");
                if (isNetwork) {
                    // from GPS
                    Log.e("network", "hererer");
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (loc != null) {
                            updateUI(loc);
                        }
                    }

                } else if (isGPS) {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (loc != null)
                            updateUI(loc);
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
            //          String provider = locationManager.getBestProvider(criteria, false);
//            Location location = locationManager.getLastKnownLocation(provider);

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

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            View_load_dialog.showDialog();

                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    Intent i = new Intent(context, Visit_list_pageActivity.class);
                                    startActivityForResult(i, 1);
                                    finish();
                                    return;
                                }
                            }, 500);

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


    public void showSettingsAlert() {

        LayoutInflater factory = LayoutInflater.from(context);
        final View deleteDialogView = factory.inflate(R.layout.alertdialog_location, null);
        final AlertDialog deleteDialognew = new AlertDialog.Builder(context).setIcon(R.drawable.shape).create();
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) deleteDialogView);
        TextView title_dialog = deleteDialogView.findViewById(R.id.enable_lbl);
        deleteDialognew.setView(deleteDialogView);
        deleteDialognew.setCancelable(false);
        if (!isGPS) {
            title_dialog.setText("GPS is not enabled. Enable it now?");
        } else {
            title_dialog.setText("Network is not enabled. Enable it now?");
        }
        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*GPSTracker gpsTracker = new GPSTracker(context);
                if (gpsTracker.getIsGPSTrackingEnabled()) {
                String stringLatitude = String.valueOf(gpsTracker.latitude);
                Toast.makeText(context,stringLatitude,Toast.LENGTH_LONG).show();        */
                if (!isGPS) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    deleteDialognew.dismiss();
                    restartflag4 = true;
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Settings.ACTION_DATA_USAGE_SETTINGS);
                    PackageManager packageManager = getPackageManager();
                    if (intent.resolveActivity(packageManager) != null) {
                        deleteDialognew.dismiss();
                        restartflag4 = true;
                        startActivity(intent);
                    } else {
                        intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                        deleteDialognew.dismiss();
                        restartflag4 = true;
                        startActivity(intent);
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

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment.isAdded())
            return;
        super.onAttachFragment(fragment);
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
                            try {
                                if (session.getcheck_out_time().equals("") || session.getcheck_out_time().equals(null) || session.getcheck_out_time().equals("null")) {
                                    Calendar calendar0 = Calendar.getInstance();
                                    Date date0 = calendar0.getTime();
                                    String t = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date0.getTime());

                                    JSONObject jsonObject = new JSONObject();
                                    final String[] lat = {"0.0"};
                                    final String[] longi = {"0.0"};
                                    client = LocationServices.getFusedLocationProviderClient(context);
                                    if (ActivityCompat.checkSelfPermission(Visit_detailsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Visit_detailsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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


                                    OfflineDbHelper offlineDbHelper = new OfflineDbHelper(context);
                                    SQLiteDatabase db = offlineDbHelper.getWritableDatabase();
                                    offlineDbHelper.addVISITDATA(t, OrdersData.actual_count, OrdersData.unplanned_count, OrdersData.total_count, OrdersData.p_call, db);
                                    final String date = new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());
                                    offlineDbHelper.addEVENT("Checkout", date, jsonObject.toString(), db);
                                    //View_load_dialog.showDialog();

                                    postActvityDetails("Checkout", date, jsonObject.toString());
                                }
                                String response = GetText();
                                session.setcheck_out_time(response);

                                Calendar calendar0 = Calendar.getInstance();
                                Date date0 = calendar0.getTime();
                                String t = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date0.getTime());

                                OfflineDbHelper offlineDbHelper = new OfflineDbHelper(context);
                                SQLiteDatabase db = offlineDbHelper.getWritableDatabase();
                                offlineDbHelper.addVISITDATA(t, OrdersData.actual_count, OrdersData.unplanned_count, OrdersData.total_count, OrdersData.p_call, db);

                            } catch (Exception e) {
                                Log.e("visit_details checkout", e.toString());
                            }
                            View_load_dialog.hideDialog();
                            showPopUp6();
                        }
                    }, 150);
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
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void showPopUp13() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.alertdialog_share_whatsapp, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).setIcon(R.drawable.shape).create();
        TextView txt_share = deleteDialogView.findViewById(R.id.txt_share);
        txt_share.setText("Do you want to share EOD details on whatsapp?");
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) deleteDialogView);
        deleteDialog.setView(deleteDialogView);
        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!isAccessibilityOn(context, WhatsappAccessibilityService.class)) {
                        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        context.startActivity(intent);
                    } else {
                        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                        whatsappIntent.setType("text/plain");
                        whatsappIntent.setPackage("com.whatsapp");
                        whatsappIntent.putExtra(Intent.EXTRA_TEXT, returnWhatsappText1() + "\n" + getResources().getString(R.string.whatsapp_suffix));
                        try {
                            startActivity(whatsappIntent);
                            restartflag3 = true;
                            deleteDialog.dismiss();
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(context, "Please install whatsapp to share details on whatsapp", Toast.LENGTH_LONG).show();
                            deleteDialog.dismiss();
                        }
                    }
                } catch (Exception ex) {
                    Log.e("visit", "error12 " + ex.toString());
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
                        Intent intent = new Intent(context, dashboardActivity.class);
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
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private String returnWhatsappText1() {
        String result = "";
        java.text.DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        result += "Name: " + session.getfirst_name() + " " + session.getlast_name() + "\n";
        result += "Date Of Reporting: " + date + "\n";
        result += "Area: " + session.getbeat_name().substring(8) + "\n";
        result += "Dist Name: " + session.getdistributor_name() + "\n";
        String actual_count = OrdersData.actual_count;
        String unplanned_count = OrdersData.unplanned_count;
        String total_calls = OrdersData.total_count;
        if (actual_count.matches("[0-9]+") && total_calls.matches("[0-9]+") && unplanned_count.matches("[0-9]+")) {
            int actcnt = Integer.parseInt(actual_count.trim()) + Integer.parseInt(unplanned_count.trim());
            actual_count = "" + actcnt;
            result += "Calls as per beat: " + actual_count + "\n";
            result += "New Calls: " + OrdersData.unplanned_count + "\n";
            result += "Productive calls: " + OrdersData.p_call + "\n";
            result += "Stores not visited: " + (Integer.parseInt(total_calls) - Integer.parseInt(actual_count)) + "\n";
        }
        result += "Bars sold: " + OrdersData.order_bars + "\n";
        result += "Cookies sold: " + OrdersData.order_cookies + "\n";
        result += "Trailmix sold: " + OrdersData.order_trailmix + "\n";

        return result;
    }

    public String GetText() throws UnsupportedEncodingException {
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        String sales_rep_id = session.getsales_rep_id();
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
        BufferedReader reader = null;

        // Send data
        try {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/Sales_Attendence/checkout_api");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();

            return text;
        } catch (Exception ex) {
            //Toast.makeText(context,"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
            }
        }

        // Show response on activity
        return "";

    }

    public int addStringNo(String val1, String val2) {
        try {
            int v1 = 0;
            int v2 = 0;
            if (val1.equals("") || val2.equals("")) {
                if (val1.equals("")) {
                    v1 = 0;
                }
                if (val2.equals("")) {
                    v2 = 0;
                }
            } else {
                v1 = Integer.parseInt(val1);
                v2 = Integer.parseInt(val2);
            }
            int v3 = v1 + v2;
            return v3;

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("batch details", "5 " + e.toString());
        }
        return 0;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            View_load_dialog.hideDialog();
            Log.e("loading", "closed");
        }
    }


    public class MyAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            View_load_dialog.showDialog();
        }

        @Override
        protected String doInBackground(String... params) {

            Intent intent = getIntent();
            id = intent.getStringExtra("id");
            try {
                respglobal = getaddapi(intent.getStringExtra("id"), intent.getStringExtra("channel_type"), intent.getStringExtra("follow_type"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return "";
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            //pb.setProgress(progress[0]);
            //View_load_dialog.showDialog();
            //Toast.makeText(dashboardActivity.this, "command sent" +progress[0],
            //Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(String result) {
            if (!respglobal.equals("")) {
                try {
                    Intent intent = getIntent();
                    JSONObject jsonObject = new JSONObject(respglobal);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject data1 = jsonArray.getJSONObject(i);

                        type1.setText(data1.getString("distributor_type"));
                        if (type1.getText().toString().equals("New")) {
                            expListView.setVisibility(View.GONE);
                            btnbatchdetails.setVisibility(View.GONE);

                        } else {
                            expListView.setVisibility(View.VISIBLE);
                            btnbatchdetails.setVisibility(View.VISIBLE);
                        }

                        zone1.setText(data1.getString("zone"));
                        zone1_id.setText(data1.getString("zone_id"));

                        area1.setText(data1.getString("area"));
                        area1_id.setText(data1.getString("area_id"));

                        location1.setText(data1.getString("location"));
                        location1_id.setText(data1.getString("location_id"));

                        retailer1.setText(data1.getString("store_name"));
                        retailer1_id.setText(data1.getString("store_id"));

                        relation1.setText(data1.getString("store_name"));
                        relation1_id.setText(data1.getString("store_id"));

                        bit_plan_id = data1.getString("bit_plan_id");

                        sequence = data1.getString("sequence");

                        if (intent.getStringExtra("follow_type").equals("gt_followup")) {
                            sales_rep_loc_id = data1.getString("sales_rep_loc_id");
                            //Toast.makeText(context,sales_rep_loc_id,Toast.LENGTH_LONG).show();
                        } else if (intent.getStringExtra("follow_type").equals("mt_followup")) {
                            merchandiser_stock_id = data1.getString("merchandiser_stock_id");
                        }
                        if (!data1.getString("remarks").equals("null")) {
                            txtremarks.setText(data1.getString("remarks"));
                        }

                        zone_id_val = data1.getString("zone_id");
                        area_id_val = data1.getString("area_id");
                        location_id_val = data1.getString("location_id");
                        store_id_val = data1.getString("store_id");

                        //Toast.makeText(context,data1.getString("latitude"),Toast.LENGTH_LONG).show();
                        if (data1.getString("latitude").equals("null") && data1.getString("longitude").equals("null") || data1.getString("latitude").equals("0") && data1.getString("longitude").equals("0")) {
                            LayoutInflater factory = LayoutInflater.from(context);
                            final View deleteDialogView = factory.inflate(R.layout.alertdialog_location, null);
                            final AlertDialog deleteDialog = new AlertDialog.Builder(context).setIcon(R.drawable.shape).create();
                            FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
                            fontChanger.replaceFonts((ViewGroup) deleteDialogView);
                            deleteDialog.setView(deleteDialogView);
                            deleteDialog.setCancelable(false);
                            deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //your business logic
                                            /*GPSTracker gpsTracker = new GPSTracker(context);
                                            if (gpsTracker.getIsGPSTrackingEnabled()) {
                                                String stringLatitude = String.valueOf(gpsTracker.latitude);
                                                Toast.makeText(context,stringLatitude,Toast.LENGTH_LONG).show();
                                            }*/
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
                                        if (OrdersData.latitude.equals("0") || OrdersData.latitude.equals("null") || OrdersData.latitude == null) {
                                            if (ActivityCompat.checkSelfPermission(Visit_detailsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Visit_detailsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                                                    OrdersData.latitude = location.getLatitude() + "";
                                                    OrdersData.longitude = location.getLongitude() + "";
                                                }
                                            });
                                        }
                                        // check permissions

                                    }
                                    deleteDialog.dismiss();

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
                        //Toast.makeText(context, "sequence: " + sequence, Toast.LENGTH_LONG).show();
                    }

                    if (!jsonObject.isNull("data1")) {
                        JSONArray jsonArray1 = jsonObject.getJSONArray("data1");


                        for (int i = 0; i < jsonArray1.length(); i++) {
                            JSONObject data1 = jsonArray1.getJSONObject(i);

                            Intent io=getIntent();
                            if(io.hasExtra("type")) {
                                if(io.getStringExtra("type").equals("add")){
                                    new OrdersData().clear();
                                }
                            }

                            if(data1.has("fig_raisins_box")){
                                OrdersData.fig_raisins_box = data1.getString("fig_raisins_box");
                            }else{OrdersData.fig_raisins_box ="";}

                            if(data1.has("dark_chocolate_cookies_box")){
                                OrdersData.dark_chocolate_cookies_box = data1.getString("dark_chocolate_cookies_box");
                            }else{OrdersData.dark_chocolate_cookies_box ="";}

                            if(data1.has("cranberry_orange_box")){
                                OrdersData.cranberry_orange_box = data1.getString("cranberry_orange_box");
                            }else{OrdersData.cranberry_orange_box ="";}

                            if(data1.has("chocolate_cookies_box")){
                                OrdersData.chocolate_cookies_box = data1.getString("chocolate_cookies_box");
                            }else{OrdersData.chocolate_cookies_box="";}

                            if(data1.has("chyawanprash_box")){
                                OrdersData.chyawanprash_box = data1.getString("chyawanprash_box");
                            }else{OrdersData.chyawanprash_box ="";}

                            if(data1.has("chyawanprash_bar")){
                                OrdersData.chyawanprash_bar = data1.getString("chyawanprash_bar");
                            }else{OrdersData.chyawanprash_bar ="";}

                            if(data1.has("berry_blast_box")){
                                OrdersData.berry_blast_box = data1.getString("berry_blast_box");
                            }else{OrdersData.berry_blast_box ="";}

                            if(data1.has("berry_blast_bar")){
                                OrdersData.berry_blast_bar = data1.getString("berry_blast_bar");
                            }else{OrdersData.berry_blast_bar="";}

                            if(data1.has("mangoginger_box")){
                                OrdersData.mangoginger_box = data1.getString("mangoginger_box");
                            }else{OrdersData.mangoginger_box ="";}

                            if(data1.has("mangoginger_bar")){
                                OrdersData.mangoginger_bar = data1.getString("mangoginger_bar");
                            }else{OrdersData.mangoginger_bar="";}

                            if(data1.has("bambaiyachaat_box")){
                                OrdersData.bambaiyachaat_box = data1.getString("bambaiyachaat_box");
                            }else{OrdersData.bambaiyachaat_box ="";}

                            if(data1.has("bambaiyachaat_bar")){
                                OrdersData.bambaiyachaat_bar = data1.getString("bambaiyachaat_bar");
                            }else{OrdersData.bambaiyachaat_bar="";}

                            if(data1.has("chocopeanut_box")){
                                OrdersData.chocopeanut_box = data1.getString("chocopeanut_box");
                            }else{OrdersData.chocopeanut_box ="";}

                            if(data1.has("chocopeanut_bar")){
                                OrdersData.chocopeanut_bar = data1.getString("chocopeanut_bar");
                            }else{OrdersData.chocopeanut_bar="";}

                            if(data1.has("butterscotch_box")){
                                OrdersData.butterscotch_box = data1.getString("butterscotch_box");
                            }else{OrdersData.butterscotch_box="";}

                            if(data1.has("butterscotch_bar")){
                                OrdersData.butterscotch_bar = data1.getString("butterscotch_bar");
                            }else{OrdersData.butterscotch_bar ="";}

                            if(data1.has("orange_box")){
                                OrdersData.orange_box = data1.getString("orange_box");
                            }else{
                                OrdersData.orange_box="";
                            }
                            if(data1.has("orange_bar")){
                                OrdersData.orange_bar = data1.getString("orange_bar");
                            }else{
                                OrdersData.orange_bar="";
                            }

                            if(data1.has("variety_box")){
                                OrdersData.variety_box = data1.getString("variety_box");
                            }else{
                                OrdersData.variety_box="";
                            }

                            if(data1.has("cranberry_cookies_box")){
                                OrdersData.cranberry_cookies_box = data1.getString("cranberry_cookies_box");
                            }else{ OrdersData.cranberry_cookies_box ="";}

                            if(data1.has("papaya_pineapple_box")){
                                OrdersData.papaya_pineapple_box = data1.getString("papaya_pineapple_box");
                            }else{ OrdersData.papaya_pineapple_box ="";}
                        }
                    }
                    View_load_dialog.hideDialog();
            }catch (Exception e){
                Log.e("visit1", e.toString());
                }
            }
        }
    }

    public class MyAsyncTaskedit extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            View_load_dialog.showDialog();
        }
        @Override
        protected String doInBackground(String... params) {
            Intent intent=getIntent();
            id = intent.getStringExtra("id");
            Log.e("id: ", id);
            try {
                respglobaledit = geteditapi(intent.getStringExtra("id"), intent.getStringExtra("channel_type"), intent.getStringExtra("temp"));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            return "";
        }

        @Override
        protected void onProgressUpdate(Integer ... progress) {
            //pb.setProgress(progress[0]);
            //View_load_dialog.showDialog();
            //Toast.makeText(dashboardActivity.this, "command sent" +progress[0],
            //Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(String result) {

            if(!respglobaledit.equals("")){
            try{


                JSONObject jsonObject = new JSONObject(respglobaledit);

                JSONObject ob=new JSONObject(jsonObject.getString("batch_detail"));
                if(ob.has("orange_bar")) {
                    JSONArray jso=new JSONArray(ob.getString("orange_bar"));
                    JSONArray jsonArray=new JSONArray(OrdersData.batch_no_json);

                    JSONObject batch_json=new JSONObject();
                    for(int j=0; j<jso.length(); j++){
                        JSONObject json=jso.getJSONObject(j);
                        Iterator<String> keys = json.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            int pos=0;
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject k=jsonArray.getJSONObject(i);
                                if(k.getString("batch_no").equals(key))
                                    pos=i+1;
                            }
                            batch_json.put(pos+"", json.getString(key));
                        }
                    }
                    OrdersData.orange_bar_json = batch_json.toString();
                }
                if(ob.has("butterscotch_bar")) {
                    JSONArray jso=new JSONArray(ob.getString("butterscotch_bar"));
                    JSONArray jsonArray=new JSONArray(OrdersData.batch_no_json);

                    JSONObject batch_json=new JSONObject();
                    for(int j=0; j<jso.length(); j++){
                        JSONObject json=jso.getJSONObject(j);
                        Iterator<String> keys = json.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            int pos=0;
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject k=jsonArray.getJSONObject(i);
                                if(k.getString("batch_no").equals(key))
                                    pos=i+1;
                            }
                            batch_json.put(pos+"", json.getString(key));
                        }
                    }
                    OrdersData.butterscotch_bar_json = batch_json.toString();
                }
                if(ob.has("chocopeanut_bar")) {
                    JSONArray jso=new JSONArray(ob.getString("chocopeanut_bar"));
                    JSONArray jsonArray=new JSONArray(OrdersData.batch_no_json);

                    JSONObject batch_json=new JSONObject();
                    for(int j=0; j<jso.length(); j++){
                        JSONObject json=jso.getJSONObject(j);
                        Iterator<String> keys = json.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            int pos=0;
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject k=jsonArray.getJSONObject(i);
                                if(k.getString("batch_no").equals(key))
                                    pos=i+1;
                            }
                            batch_json.put(pos+"", json.getString(key));
                        }
                    }
                    OrdersData.chocopeanut_bar_json = batch_json.toString();
                }
                if(ob.has("bambaiyachaat_bar")) {
                    JSONArray jso=new JSONArray(ob.getString("bambaiyachaat_bar"));
                    JSONArray jsonArray=new JSONArray(OrdersData.batch_no_json);

                    JSONObject batch_json=new JSONObject();
                    for(int j=0; j<jso.length(); j++){
                        JSONObject json=jso.getJSONObject(j);
                        Iterator<String> keys = json.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            int pos=0;
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject k=jsonArray.getJSONObject(i);
                                if(k.getString("batch_no").equals(key))
                                    pos=i+1;
                            }
                            batch_json.put(pos+"", json.getString(key));
                        }
                    }
                    OrdersData.bambaiyachaat_bar_json = batch_json.toString();
                }
                if(ob.has("mangoginger_bar")) {
                    JSONArray jso=new JSONArray(ob.getString("mangoginger_bar"));
                    JSONArray jsonArray=new JSONArray(OrdersData.batch_no_json);

                    JSONObject batch_json=new JSONObject();
                    for(int j=0; j<jso.length(); j++){
                        JSONObject json=jso.getJSONObject(j);
                        Iterator<String> keys = json.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            int pos=0;
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject k=jsonArray.getJSONObject(i);
                                if(k.getString("batch_no").equals(key))
                                    pos=i+1;
                            }
                            batch_json.put(pos+"", json.getString(key));
                        }
                    }
                    OrdersData.mangoginger_bar_json = batch_json.toString();                }
                if(ob.has("berry_blast_bar")) {
                    JSONArray jso=new JSONArray(ob.getString("berry_blast_bar"));
                    JSONArray jsonArray=new JSONArray(OrdersData.batch_no_json);

                    JSONObject batch_json=new JSONObject();
                    for(int j=0; j<jso.length(); j++){
                        JSONObject json=jso.getJSONObject(j);
                        Iterator<String> keys = json.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            int pos=0;
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject k=jsonArray.getJSONObject(i);
                                if(k.getString("batch_no").equals(key))
                                    pos=i+1;
                            }
                            batch_json.put(pos+"", json.getString(key));
                        }
                    }
                    OrdersData.berry_blast_bar_json = batch_json.toString();                }
                if(ob.has("chyawanprash_bar")) {
                    JSONArray jso=new JSONArray(ob.getString("chyawanprash_bar"));
                    JSONArray jsonArray=new JSONArray(OrdersData.batch_no_json);

                    JSONObject batch_json=new JSONObject();
                    for(int j=0; j<jso.length(); j++){
                        JSONObject json=jso.getJSONObject(j);
                        Iterator<String> keys = json.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            int pos=0;
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject k=jsonArray.getJSONObject(i);
                                if(k.getString("batch_no").equals(key))
                                    pos=i+1;
                            }
                            batch_json.put(pos+"", json.getString(key));
                        }
                    }
                    OrdersData.chyawanprash_bar_json = batch_json.toString();                }
                if(ob.has("orange_box")) {
                    JSONArray jso=new JSONArray(ob.getString("orange_box"));
                    JSONArray jsonArray=new JSONArray(OrdersData.batch_no_json);

                    JSONObject batch_json=new JSONObject();
                    for(int j=0; j<jso.length(); j++){
                        JSONObject json=jso.getJSONObject(j);
                        Iterator<String> keys = json.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            int pos=0;
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject k=jsonArray.getJSONObject(i);
                                if(k.getString("batch_no").equals(key))
                                    pos=i+1;
                            }
                            batch_json.put(pos+"", json.getString(key));
                        }
                    }
                    OrdersData.orange_box_json = batch_json.toString();                }
                if(ob.has("butterscotch_box")) {
                    JSONArray jso=new JSONArray(ob.getString("butterscotch_box"));
                    JSONArray jsonArray=new JSONArray(OrdersData.batch_no_json);

                    JSONObject batch_json=new JSONObject();
                    for(int j=0; j<jso.length(); j++){
                        JSONObject json=jso.getJSONObject(j);
                        Iterator<String> keys = json.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            int pos=0;
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject k=jsonArray.getJSONObject(i);
                                if(k.getString("batch_no").equals(key))
                                    pos=i+1;
                            }
                            batch_json.put(pos+"", json.getString(key));
                        }
                    }
                    OrdersData.butterscotch_box_json = batch_json.toString();                }
                if(ob.has("chocopeanut_box")) {
                    JSONArray jso=new JSONArray(ob.getString("chocopeanut_box"));
                    JSONArray jsonArray=new JSONArray(OrdersData.batch_no_json);

                    JSONObject batch_json=new JSONObject();
                    for(int j=0; j<jso.length(); j++){
                        JSONObject json=jso.getJSONObject(j);
                        Iterator<String> keys = json.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            int pos=0;
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject k=jsonArray.getJSONObject(i);
                                if(k.getString("batch_no").equals(key))
                                    pos=i+1;
                            }
                            batch_json.put(pos+"", json.getString(key));
                        }
                    }
                    OrdersData.chocopeanut_box_json = batch_json.toString();                }
                if(ob.has("bambaiyachaat_box")) {
                    JSONArray jso=new JSONArray(ob.getString("bambaiyachaat_box"));
                    JSONArray jsonArray=new JSONArray(OrdersData.batch_no_json);

                    JSONObject batch_json=new JSONObject();
                    for(int j=0; j<jso.length(); j++){
                        JSONObject json=jso.getJSONObject(j);
                        Iterator<String> keys = json.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            int pos=0;
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject k=jsonArray.getJSONObject(i);
                                if(k.getString("batch_no").equals(key))
                                    pos=i+1;
                            }
                            batch_json.put(pos+"", json.getString(key));
                        }
                    }
                    OrdersData.bambaiyachaat_box_json = batch_json.toString();                }
                if(ob.has("mangoginger_box")) {
                    JSONArray jso=new JSONArray(ob.getString("mangoginger_box"));
                    JSONArray jsonArray=new JSONArray(OrdersData.batch_no_json);

                    JSONObject batch_json=new JSONObject();
                    for(int j=0; j<jso.length(); j++){
                        JSONObject json=jso.getJSONObject(j);
                        Iterator<String> keys = json.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            int pos=0;
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject k=jsonArray.getJSONObject(i);
                                if(k.getString("batch_no").equals(key))
                                    pos=i+1;
                            }
                            batch_json.put(pos+"", json.getString(key));
                        }
                    }
                    OrdersData.mangoginger_box_json = batch_json.toString();                }
                if(ob.has("berry_blast_box")) {
                    JSONArray jso=new JSONArray(ob.getString("berry_blast_box"));
                    JSONArray jsonArray=new JSONArray(OrdersData.batch_no_json);

                    JSONObject batch_json=new JSONObject();
                    for(int j=0; j<jso.length(); j++){
                        JSONObject json=jso.getJSONObject(j);
                        Iterator<String> keys = json.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            int pos=0;
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject k=jsonArray.getJSONObject(i);
                                if(k.getString("batch_no").equals(key))
                                    pos=i+1;
                            }
                            batch_json.put(pos+"", json.getString(key));
                        }
                    }
                    OrdersData.berry_blast_box_json = batch_json.toString();
                }
                if(ob.has("chyawanprash_box")) {
                    JSONArray jso=new JSONArray(ob.getString("chyawanprash_box"));
                    JSONArray jsonArray=new JSONArray(OrdersData.batch_no_json);

                    JSONObject batch_json=new JSONObject();
                    for(int j=0; j<jso.length(); j++){
                        JSONObject json=jso.getJSONObject(j);
                        Iterator<String> keys = json.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            int pos=0;
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject k=jsonArray.getJSONObject(i);
                                if(k.getString("batch_no").equals(key))
                                    pos=i+1;
                            }
                            batch_json.put(pos+"", json.getString(key));
                        }
                    }
                    OrdersData.chyawanprash_box_json = batch_json.toString();
                }
                if(ob.has("variety_box")) {
                    JSONArray jso=new JSONArray(ob.getString("variety_box"));
                    JSONArray jsonArray=new JSONArray(OrdersData.batch_no_json);

                    JSONObject batch_json=new JSONObject();
                    for(int j=0; j<jso.length(); j++){
                        JSONObject json=jso.getJSONObject(j);
                        Iterator<String> keys = json.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            int pos=0;
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject k=jsonArray.getJSONObject(i);
                                if(k.getString("batch_no").equals(key))
                                    pos=i+1;
                            }
                            batch_json.put(pos+"", json.getString(key));
                        }
                    }
                    OrdersData.variety_box_json = batch_json.toString();
                }
                if(ob.has("chocolate_cookies")) {
                    JSONArray jso=new JSONArray(ob.getString("chocolate_cookies"));
                    JSONArray jsonArray=new JSONArray(OrdersData.batch_no_json);

                    JSONObject batch_json=new JSONObject();
                    for(int j=0; j<jso.length(); j++){
                        JSONObject json=jso.getJSONObject(j);
                        Iterator<String> keys = json.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            int pos=0;
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject k=jsonArray.getJSONObject(i);
                                if(k.getString("batch_no").equals(key))
                                    pos=i+1;
                            }
                            batch_json.put(pos+"", json.getString(key));
                        }
                    }
                    OrdersData.chocolate_cookies_json = batch_json.toString();
                }
                if(ob.has("dark_chocolate_cookies")) {
                    JSONArray jso=new JSONArray(ob.getString("dark_chocolate_cookies"));
                    JSONArray jsonArray=new JSONArray(OrdersData.batch_no_json);

                    JSONObject batch_json=new JSONObject();
                    for(int j=0; j<jso.length(); j++){
                        JSONObject json=jso.getJSONObject(j);
                        Iterator<String> keys = json.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            int pos=0;
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject k=jsonArray.getJSONObject(i);
                                if(k.getString("batch_no").equals(key))
                                    pos=i+1;
                            }
                            batch_json.put(pos+"", json.getString(key));
                        }
                    }
                    OrdersData.dark_chocolate_cookies_json = batch_json.toString();
                }
                if(ob.has("cranberry_cookies")) {
                    JSONArray jso=new JSONArray(ob.getString("cranberry_cookies"));
                    JSONArray jsonArray=new JSONArray(OrdersData.batch_no_json);

                    JSONObject batch_json=new JSONObject();
                    for(int j=0; j<jso.length(); j++){
                        JSONObject json=jso.getJSONObject(j);
                        Iterator<String> keys = json.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            int pos=0;
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject k=jsonArray.getJSONObject(i);
                                if(k.getString("batch_no").equals(key))
                                    pos=i+1;
                            }
                            batch_json.put(pos+"", json.getString(key));
                        }
                    }
                    OrdersData.cranberry_cookies_json = batch_json.toString();
                }
                if(ob.has("papaya_pineapple")) {
                    JSONArray jso=new JSONArray(ob.getString("papaya_pineapple"));
                    JSONArray jsonArray=new JSONArray(OrdersData.batch_no_json);

                    JSONObject batch_json=new JSONObject();
                    for(int j=0; j<jso.length(); j++){
                        JSONObject json=jso.getJSONObject(j);
                        Iterator<String> keys = json.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            int pos=0;
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject k=jsonArray.getJSONObject(i);
                                if(k.getString("batch_no").equals(key))
                                    pos=i+1;
                            }
                            batch_json.put(pos+"", json.getString(key));
                        }
                    }
                    OrdersData.papaya_pineapple_json = batch_json.toString();
                }
                if(ob.has("fig_raisins")) {
                    JSONArray jso=new JSONArray(ob.getString("fig_raisins"));
                    JSONArray jsonArray=new JSONArray(OrdersData.batch_no_json);

                    JSONObject batch_json=new JSONObject();
                    for(int j=0; j<jso.length(); j++){
                        JSONObject json=jso.getJSONObject(j);
                        Iterator<String> keys = json.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            int pos=0;
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject k=jsonArray.getJSONObject(i);
                                if(k.getString("batch_no").equals(key))
                                    pos=i+1;
                            }
                            batch_json.put(pos+"", json.getString(key));
                        }
                    }
                    OrdersData.fig_raisins_json = batch_json.toString();
                }
                if(ob.has("cranberry_orange_zest")) {
                    JSONArray jso=new JSONArray(ob.getString("cranberry_orange_zest"));
                    JSONArray jsonArray=new JSONArray(OrdersData.batch_no_json);

                    JSONObject batch_json=new JSONObject();
                    for(int j=0; j<jso.length(); j++){
                        JSONObject json=jso.getJSONObject(j);
                        Iterator<String> keys = json.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            int pos=0;
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject k=jsonArray.getJSONObject(i);
                                if(k.getString("batch_no").equals(key))
                                    pos=i+1;
                            }
                            batch_json.put(pos+"", json.getString(key));
                        }
                    }
                    OrdersData.cranberry_orange_json = batch_json.toString();
                }


                JSONArray jsonArray = jsonObject.getJSONArray("data");
                //Toast.makeText(context,"id: "+resp,Toast.LENGTH_LONG).show();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject data1 = jsonArray.getJSONObject(i);
                    type1.setText(data1.getString("distributor_type"));
                    if (type1.getText().toString().equals("New")) {
                        expListView.setVisibility(View.GONE);
                        btnbatchdetails.setVisibility(View.GONE);
                        txtretailer1.setVisibility(View.VISIBLE);
                        retailer1.setVisibility(View.GONE);
                        txtretailer1.setText(data1.getString("store_name"));
                        Log.e("visit", "txtretailer1: "+txtretailer1.getText().toString());
                    } else {
                        expListView.setVisibility(View.VISIBLE);
                        btnbatchdetails.setVisibility(View.VISIBLE);
                        txtretailer1.setVisibility(View.GONE);
                        retailer1.setVisibility(View.VISIBLE);
                        relation1.setText(data1.getString("store_name"));
                        retailer1.setText(data1.getString("store_name"));
                        //Toast.makeText(context, ""+relation1.getText().toString(), Toast.LENGTH_SHORT).show();
                        Log.e("visit", "txtretailer1: "+txtretailer1.getText().toString()+" retailer1: "+retailer1.getText().toString());
                    }

                    txtremarks.setText(data1.getString("remarks"));

                    zone1.setText(data1.getString("zone"));
                    zone1_id.setText(data1.getString("zone_id"));

                    area1.setText(data1.getString("area"));
                    area1_id.setText(data1.getString("area_id"));

                    location1.setText(data1.getString("location"));
                    location1_id.setText(data1.getString("location_id"));

                    retailer1_id.setText(data1.getString("store_id"));


                    relation1_id.setText(data1.getString("store_id"));

                    bit_plan_id = data1.getString("bit_plan_id");

                    mid = data1.getString("mid");

                    sequence = data1.getString("sequence");

                    zone_id_val = data1.getString("zone_id");
                    area_id_val = data1.getString("area_id");
                    location_id_val = data1.getString("location_id");
                    store_id_val = data1.getString("store_id");

                    //Toast.makeText(context, "sequence: " + sequence, Toast.LENGTH_LONG).show();
                }
                JSONArray jsonArray1 = jsonObject.getJSONArray("data1");
                for (int i = 0; i < jsonArray1.length(); i++) {
                    JSONObject data1 = jsonArray1.getJSONObject(i);


                    if(data1.has("fig_raisins_box")){
                        OrdersData.fig_raisins_box = data1.getString("fig_raisins_box");
                    }else{OrdersData.fig_raisins_box ="";}

                    if(data1.has("dark_chocolate_cookies_box")){
                        OrdersData.dark_chocolate_cookies_box = data1.getString("dark_chocolate_cookies_box");
                    }else{OrdersData.dark_chocolate_cookies_box ="";}

                    if(data1.has("cranberry_orange_box")){
                        OrdersData.cranberry_orange_box = data1.getString("cranberry_orange_box");
                    }else{OrdersData.cranberry_orange_box ="";}

                    if(data1.has("chocolate_cookies_box")){
                        OrdersData.chocolate_cookies_box = data1.getString("chocolate_cookies_box");
                    }else{OrdersData.chocolate_cookies_box="";}

                    if(data1.has("chyawanprash_box")){
                        OrdersData.chyawanprash_box = data1.getString("chyawanprash_box");
                    }else{OrdersData.chyawanprash_box ="";}

                    if(data1.has("chyawanprash_bar")){
                        OrdersData.chyawanprash_bar = data1.getString("chyawanprash_bar");
                    }else{OrdersData.chyawanprash_bar ="";}

                    if(data1.has("berry_blast_box")){
                        OrdersData.berry_blast_box = data1.getString("berry_blast_box");
                    }else{OrdersData.berry_blast_box ="";}

                    if(data1.has("berry_blast_bar")){
                        OrdersData.berry_blast_bar = data1.getString("berry_blast_bar");
                    }else{OrdersData.berry_blast_bar="";}

                    if(data1.has("mangoginger_box")){
                        OrdersData.mangoginger_box = data1.getString("mangoginger_box");
                    }else{OrdersData.mangoginger_box ="";}

                    if(data1.has("mangoginger_bar")){
                        OrdersData.mangoginger_bar = data1.getString("mangoginger_bar");
                    }else{OrdersData.mangoginger_bar="";}

                    if(data1.has("bambaiyachaat_box")){
                        OrdersData.bambaiyachaat_box = data1.getString("bambaiyachaat_box");
                    }else{OrdersData.bambaiyachaat_box ="";}

                    if(data1.has("bambaiyachaat_bar")){
                        OrdersData.bambaiyachaat_bar = data1.getString("bambaiyachaat_bar");
                    }else{OrdersData.bambaiyachaat_bar="";}

                    if(data1.has("chocopeanut_box")){
                        OrdersData.chocopeanut_box = data1.getString("chocopeanut_box");
                    }else{OrdersData.chocopeanut_box ="";}

                    if(data1.has("chocopeanut_bar")){
                        OrdersData.chocopeanut_bar = data1.getString("chocopeanut_bar");
                    }else{OrdersData.chocopeanut_bar="";}

                    if(data1.has("butterscotch_box")){
                        OrdersData.butterscotch_box = data1.getString("butterscotch_box");
                    }else{OrdersData.butterscotch_box="";}

                    if(data1.has("butterscotch_bar")){
                        OrdersData.butterscotch_bar = data1.getString("butterscotch_bar");
                    }else{OrdersData.butterscotch_bar ="";}

                    if(data1.has("orange_box")){
                        OrdersData.orange_box = data1.getString("orange_box");
                    }else{
                        OrdersData.orange_box="";
                    }
                    if(data1.has("orange_bar")){
                        OrdersData.orange_bar = data1.getString("orange_bar");
                    }else{
                        OrdersData.orange_bar="";
                    }

                    if(data1.has("variety_box")){
                        OrdersData.variety_box = data1.getString("variety_box");
                    }else{
                        OrdersData.variety_box="";
                    }

                    if(data1.has("cranberry_cookies_box")){
                        OrdersData.cranberry_cookies_box = data1.getString("cranberry_cookies_box");
                    }else{ OrdersData.cranberry_cookies_box ="";}

                    if(data1.has("papaya_pineapple_box")){
                        OrdersData.papaya_pineapple_box = data1.getString("papaya_pineapple_box");
                    }else{ OrdersData.papaya_pineapple_box ="";}

                }
                listAdapter.notifyDataSetChanged();
                View_load_dialog.hideDialog();



            }catch (Exception e){
                Log.e("visit1", e.toString());
                View_load_dialog.hideDialog();
            }
            }
        }

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
