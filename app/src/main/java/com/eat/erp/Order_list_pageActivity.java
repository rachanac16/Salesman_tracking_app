package com.eat.erp;

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
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;

import android.provider.Settings;
import android.text.TextUtils;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.eat.erp.dashboardActivity.getDayNumberSuffix;

public class Order_list_pageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
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
    boolean restartflag=false, restartflag2=false;
    ArrayList<Order> order;
    private static OrderAdapter adapter;
    String temp="";
    String sday;
    View_load_dialog View_load_dialog;
    LinearLayout llcheckin, llcheckout;
    TextView txtcheckout;
    RelativeLayout container_order;
    boolean flagpressed=false;
    Button fab_btn;
    int logos[] = {R.drawable.option_checkout, R.drawable.option_new_visit, R.drawable.option_old_visit, R.drawable.option_timeline};
    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        restartflag=false;
        restartflag2=false;
        flagpressed=false;
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));
        face= Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Demi.otf");
        context=Order_list_pageActivity.this;
        fa=this;

        container_order=findViewById(R.id.container_order);
        fab_btn=findViewById(R.id.fab_btn);

        llcheckout = (LinearLayout) findViewById(R.id.llcheckout);
        txtcheckout = (TextView) findViewById(R.id.txtcheckout);
        llcheckin = (LinearLayout) findViewById(R.id.llcheckin);
        llhome = (LinearLayout) findViewById(R.id.llhome);
        llrouteplan = (LinearLayout) findViewById(R.id.llrouteplan);
        llorder = (LinearLayout) findViewById(R.id.llorder);
        llaccount = (LinearLayout) findViewById(R.id.llaccount);

        TextView txttitle = (TextView) findViewById(R.id.txttitle);
        txttitle.setText("Orders");

        TextView todaydate = (TextView) findViewById(R.id.todaydate);
        Calendar c=Calendar.getInstance();
        String dayNumberSuffix = getDayNumberSuffix(c.get(Calendar.DAY_OF_MONTH));
        Date date3 = Calendar.getInstance().getTime();
        SimpleDateFormat timeFormatter3 = new SimpleDateFormat("dd'"+dayNumberSuffix+"' MMM yyyy");
        String time3 = timeFormatter3.format(date3);
        todaydate.setText(time3+ ", Today");

        View_load_dialog = new View_load_dialog(this);


        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flagpressed){
                    flagpressed=false;
                    container_order.removeViewAt(container_order.getChildCount()-2);
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
                        Intent i = new Intent(context, dashboardActivity.class);
                        startActivityForResult(i, 1);
                        //finish();
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




        session = new session(context);
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

                String checkintime = session.getcheck_in_time();

                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = dateFormatter.parse(checkintime);

                SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a");
                String time = timeFormatter.format(date);

                viewPager = (ViewPager) findViewById(R.id.visit_viewpager);
                setupViewPager(viewPager);

                /*TabLayout tabLayout = (TabLayout) findViewById(R.id.visit_tabs);
                tabLayout.setupWithViewPager(viewPager);

                ListView lv = findViewById(R.id.order_list1);
                LinearLayout order_list = findViewById(R.id.order_list);

                String response = getorders();
                JSONObject obj = new JSONObject(response);
                JSONArray data = obj.getJSONArray("data");

                order= new ArrayList<>();
                if(data.length()>0) {
                    order_list.setVisibility(View.VISIBLE);
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject data1 = data.getJSONObject(i);
                        String retailer = data1.getString("distributor_name");
                        String distributor = data1.getString("distributor");
                        String location = data1.getString("location");
                        String id = data1.getString("order_id");
                        order.add(new Order("" + (i + 1), id, retailer, distributor, location, "View"));
                    }
                    adapter= new OrderAdapter(order,context);
                    adapter.setCustomButtonListner((BeatPlanAdapter.customButtonListener) this);
                    lv.setAdapter(adapter);
                    lv.setScrollContainer(false);
                }
                else {
                    order_list.setVisibility(View.GONE);
                }*/
            }
            catch (Exception ex) {
                //Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
            }
        }

        // tabthu.select()
        }

    public String  getorders()  throws UnsupportedEncodingException
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
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/Sales_rep_store_plan_mobile_app/get_order_api");

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            View_load_dialog.hideDialog();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OrderFragment(), "Order");
        viewPager.setAdapter(adapter);
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
                container_order.removeView(menuOption);
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
                                container_order.removeView(menuOption);
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
                                container_order.removeView(menuOption);
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
                                    container_order.removeView(menuOption);
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
                                    container_order.removeView(menuOption);
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

        container_order.addView(menuOption, container_order.getChildCount()-1);


    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(context, dashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(0, 0);
            // Handle the camera action
        } else if (id == R.id.nav_checkintime) {

        } else if (id == R.id.nav_checkouttime) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            // return null to display only the icon
            return null;
        }
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
                                    if (ActivityCompat.checkSelfPermission(Order_list_pageActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Order_list_pageActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                                Log.e("order_list checkout",e.toString());
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
            Log.e("orderlist", "postactivitydetails: "+ex.toString());
            //Toast.makeText(context,"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        }
        finally {
            try {

                reader.close();
            } catch (Exception ex) {
                Log.e("orderlist", "postactivitydetails2: "+ex.toString());
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
        }else{}
    }


}
