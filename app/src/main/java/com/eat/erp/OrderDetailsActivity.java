package com.eat.erp;

import android.accessibilityservice.AccessibilityService;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.nfc.cardemulation.HostApduService;
import android.os.Handler;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;

import android.provider.Settings;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity {
    session session;
    Context context;
    MenuItem nav_checkintime;
    MenuItem nav_checkouttime;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    com.eat.erp.ExpandableListAdapter listAdapter;
    TextView txtretailer, txtarea, txtzone, txtlocation, txtrelation, txtdistributor, txtremarks, txttitle;
    LinearLayout llarea, llrelation, llretailer;
    Button btnsave, cancel_btn;
    ProgressDialog spinnerDialog;
    TextView date_label, type_label, type_label1, channel_label, channel_label1, remarks_label, relation_label, relation_label1, area_label, area_label1, zone_label1, zone_label, location_label, retailer_label, retailer_label1, location_label1, date_label1;
    Typeface face;
    View_load_dialog View_load_dialog;
    boolean restartflag=false;
    boolean restartflag2=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        context=OrderDetailsActivity.this;
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));
        View_load_dialog = new View_load_dialog(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        face= Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Demi.otf");
        Typeface face1= Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Regular.otf");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txttitle = (TextView) findViewById(R.id.txttitle);
        txttitle.setText("Order Details");
        restartflag=false;
        restartflag2=false;
        TextView todaydate = (TextView) findViewById(R.id.todaydate);
        Calendar c=Calendar.getInstance();
        String dayNumberSuffix = dashboardActivity.getDayNumberSuffix(c.get(Calendar.DAY_OF_MONTH));
        Date date3 = Calendar.getInstance().getTime();
        SimpleDateFormat timeFormatter3 = new SimpleDateFormat("dd'"+dayNumberSuffix+"' MMM yyyy");
        String time3 = timeFormatter3.format(date3);
        todaydate.setText(time3+ ", Today");

        date_label = (TextView) findViewById(R.id.date_label);
        channel_label = (TextView) findViewById(R.id.channel_label);
        remarks_label = (TextView) findViewById(R.id.remarks_label);
        relation_label = (TextView) findViewById(R.id.relation_label);
        area_label = (TextView) findViewById(R.id.area_label);
        zone_label = (TextView) findViewById(R.id.zone_label);
        location_label = (TextView) findViewById(R.id.location_label);
        retailer_label = (TextView) findViewById(R.id.retailer_label);

        date_label.setTypeface(face);
        channel_label.setTypeface(face);
        remarks_label.setTypeface(face);
        relation_label.setTypeface(face);
        area_label.setTypeface(face);
        zone_label.setTypeface(face);
        location_label.setTypeface(face);
        retailer_label.setTypeface(face);

        /*DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);*/


        session = new session(context);
        /*Menu menu = navigationView.getMenu();
        nav_checkintime = menu.findItem(R.id.nav_checkintime);
        nav_checkouttime = menu.findItem(R.id.nav_checkouttime);*/

        expListView = (ExpandableListView) findViewById(R.id.product_entry);
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return parent.isGroupExpanded(groupPosition);
            }
        });

        txtretailer = (TextView) findViewById(R.id.retailer);
        txtzone = (TextView) findViewById(R.id.zone);
        txtarea = (TextView) findViewById(R.id.area);
        txtlocation = (TextView) findViewById(R.id.location);
        txtrelation = (TextView) findViewById(R.id.relation);
        txtdistributor = (TextView) findViewById(R.id.distributor);
        txtremarks = (TextView) findViewById(R.id.txtremarks);

        llarea = (LinearLayout) findViewById(R.id.llarea);
        llrelation = (LinearLayout) findViewById(R.id.llrelation);
        llretailer = (LinearLayout) findViewById(R.id.llretailer);

        btnsave = (Button) findViewById(R.id.btnsave);
        btnsave.setTypeface(face);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);
        cancel_btn.setTypeface(face);

        String emailid=session.getusername();
        if(emailid.equals("")) {
            Intent i = new Intent(context,MainActivity.class);
            startActivity(i);
        }
        else {
            prepareListData();
            listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
            //expListView.setIndicatorBounds(0, 100);
            expListView.setAdapter(listAdapter);
            expandAll();

            String channel_type = OrdersData.channel_type;
            if(channel_type.equals("GT")) {
                llretailer.setVisibility(View.VISIBLE);
                llarea.setVisibility(View.VISIBLE);
                llrelation.setVisibility(View.GONE);
                txtretailer.setText(OrdersData.distributor_name);
                //Toast.makeText(context,OrdersData.distributor_name,Toast.LENGTH_LONG).show();
                txtarea.setText(OrdersData.area);
            }
            else {
                llrelation.setVisibility(View.VISIBLE);
                llarea.setVisibility(View.GONE);
                llretailer.setVisibility(View.GONE);
                txtrelation.setText(OrdersData.reation);
            }
            txtzone.setText(OrdersData.zone);
            txtlocation.setText(OrdersData.location);
            txtdistributor.setText(session.getdistributor_name());


            if(OrdersData.order_flag!=null) {
                if(OrdersData.order_flag.equals("0")) {
                    try {
                        //Toast.makeText(context, "mid: "+OrdersData.mid+"\nsaleslocid "+OrdersData.sales_rep_loc_id+"\nmerchandiserid "+OrdersData.merchandiser_stock_id,Toast.LENGTH_LONG).show();
                        String resp = addorderapi();
                        //Toast.makeText(context,"id: "+id+"\nchannel: "+channel_type+"\nfollow: "+intent.getStringExtra("follow_type"),Toast.LENGTH_LONG).show();
                        JSONObject jsonObject = new JSONObject(resp);
                        txtremarks.setText(jsonObject.getString("remarks"));
                        JSONArray jsonArray1 = jsonObject.getJSONArray("data1");

                        for(int i = 0; i < jsonArray1.length(); i++) {
                            JSONObject data1 = jsonArray1.getJSONObject(i);


                            View view;


                            if(OrdersData.order_orange_bar!=null) {
                                view = expListView.getAdapter().getView(1,null,null);
                                final EditText orange_bar_editText = (EditText) view.findViewById(R.id.lblListItem2);
                                orange_bar_editText.setText(OrdersData.order_orange_bar);
                            }
                            else {
                                if(data1.has("orange_bar")){
                                OrdersData.order_orange_bar = data1.getString("orange_bar");
                                }
                            }

                            if(OrdersData.order_butterscotch_bar!=null) {
                                view = expListView.getAdapter().getView(2,null,null);
                                EditText butterscotch_bar_editText = view.findViewById(R.id.lblListItem2);
                                butterscotch_bar_editText.setText(OrdersData.order_butterscotch_bar);
                            }
                            else {
                                if(data1.has("butterscotch_bar")){
                                OrdersData.order_butterscotch_bar = data1.getString("butterscotch_bar");
                            }
                            }

                            if(OrdersData.order_chocopeanut_bar!=null) {
                                view = expListView.getAdapter().getView(3,null,null);
                                EditText chocopeanut_bar_editText = view.findViewById(R.id.lblListItem2);
                                chocopeanut_bar_editText.setText(OrdersData.order_chocopeanut_bar);
                            }
                            else {
                                if(data1.has("chocopeanut_bar")) {
                                    OrdersData.order_chocopeanut_bar = data1.getString("chocopeanut_bar");
                                }
                            }

                            if(OrdersData.order_bambaiyachaat_bar!=null) {
                                view = expListView.getAdapter().getView(4,null,null);
                                EditText bambaiyachaat_bar_editText = view.findViewById(R.id.lblListItem2);
                                bambaiyachaat_bar_editText.setText(OrdersData.order_bambaiyachaat_bar);
                            }
                            else {
                                if (data1.has("bambaiyachaat_bar")) {
                                    OrdersData.order_bambaiyachaat_bar = data1.getString("bambaiyachaat_bar");
                                }
                            }
                            if(OrdersData.order_mangoginger_bar!=null) {
                                view = expListView.getAdapter().getView(5,null,null);
                                EditText mangoginger_bar_editText = view.findViewById(R.id.lblListItem2);
                                mangoginger_bar_editText.setText(OrdersData.order_mangoginger_bar);
                            }
                            else {
                                if (data1.has("mangoginger_bar")) {
                                    OrdersData.order_mangoginger_bar = data1.getString("mangoginger_bar");
                                }
                            }
                            if(OrdersData.order_berry_blast_bar!=null) {
                                view = expListView.getAdapter().getView(6,null,null);
                                EditText berry_blast_bar_editText = view.findViewById(R.id.lblListItem2);
                                berry_blast_bar_editText.setText(OrdersData.order_berry_blast_bar);
                            }
                            else {
                                if (data1.has("berry_blast_bar")) {
                                    OrdersData.order_berry_blast_bar = data1.getString("berry_blast_bar");
                                }
                            }
                            if(OrdersData.order_chyawanprash_bar!=null) {
                                view = expListView.getAdapter().getView(7,null,null);
                                EditText chyawanprash_bar_editText = view.findViewById(R.id.lblListItem2);
                                chyawanprash_bar_editText.setText(OrdersData.order_chyawanprash_bar);
                            }
                            else {
                                if (data1.has("chyawanprash_bar")) {
                                    OrdersData.order_chyawanprash_bar = data1.getString("chyawanprash_bar");
                                }
                            }
                            if(OrdersData.order_orange_box!=null) {
                                view = expListView.getAdapter().getView(9,null,null);
                                EditText orange_box_editText = view.findViewById(R.id.lblListItem2);
                                orange_box_editText.setText(OrdersData.order_orange_box);
                            }
                            else {
                                if (data1.has("orange_box")) {
                                    OrdersData.order_orange_box = data1.getString("orange_box");
                                }
                            }
                            if(OrdersData.order_butterscotch_box!=null) {
                                view = expListView.getAdapter().getView(10,null,null);
                                EditText butterscotch_box_editText = view.findViewById(R.id.lblListItem2);
                                butterscotch_box_editText.setText(OrdersData.order_butterscotch_box);
                            }
                            else {
                                if (data1.has("butterscotch_box")) {
                                    OrdersData.order_butterscotch_box = data1.getString("butterscotch_box");
                                }
                            }
                            if(OrdersData.order_chocopeanut_box!=null) {
                                view = expListView.getAdapter().getView(11,null,null);
                                EditText chocopeanut_box_editText = view.findViewById(R.id.lblListItem2);
                                chocopeanut_box_editText.setText(OrdersData.order_chocopeanut_box);
                            }
                            else {
                                if (data1.has("chocopeanut_box")) {
                                    OrdersData.order_chocopeanut_box = data1.getString("chocopeanut_box");
                                }
                            }
                            if(OrdersData.order_bambaiyachaat_box!=null) {
                                view = expListView.getAdapter().getView(12,null,null);
                                EditText bambaiyachaat_box_editText = view.findViewById(R.id.lblListItem2);
                                bambaiyachaat_box_editText.setText(OrdersData.order_bambaiyachaat_box);
                            }
                            else {
                                if (data1.has("bambaiyachaat_box")) {
                                    OrdersData.order_bambaiyachaat_box = data1.getString("bambaiyachaat_box");
                                }
                            }
                            if(OrdersData.order_mangoginger_box!=null) {
                                view = expListView.getAdapter().getView(13,null,null);
                                EditText mangoginger_box_editText = view.findViewById(R.id.lblListItem2);
                                mangoginger_box_editText.setText(OrdersData.order_mangoginger_box);
                            }
                            else {
                                if (data1.has("mangoginger_box")) {
                                    OrdersData.order_mangoginger_box = data1.getString("mangoginger_box");
                                }
                            }
                            if(OrdersData.order_berry_blast_box!=null) {
                                view = expListView.getAdapter().getView(14,null,null);
                                EditText berry_blast_box_editText = view.findViewById(R.id.lblListItem2);
                                berry_blast_box_editText.setText(OrdersData.order_berry_blast_box);
                            }
                            else {
                                if (data1.has("berry_blast_box")) {
                                    OrdersData.order_berry_blast_box = data1.getString("berry_blast_box");
                                }
                            }
                            if(OrdersData.order_chyawanprash_box!=null) {
                                view = expListView.getAdapter().getView(15,null,null);
                                EditText chyawanprash_box_editText = view.findViewById(R.id.lblListItem2);
                                chyawanprash_box_editText.setText(OrdersData.order_chyawanprash_box);
                            }
                            else {
                                if(data1.has("chyawanprash_box")){
                                OrdersData.order_chyawanprash_box = data1.getString("chyawanprash_box");
                            }
                            }

                            if(OrdersData.order_variety_box!=null) {
                                view = expListView.getAdapter().getView(16,null,null);
                                EditText variety_box_editText = view.findViewById(R.id.lblListItem2);
                                variety_box_editText.setText(OrdersData.order_variety_box);
                            }
                            else {
                                if (data1.has("variety_box")) {
                                    OrdersData.order_variety_box = data1.getString("variety_box");
                                }
                            }
                            if(OrdersData.order_chocolate_cookies_box!=null) {
                                view = expListView.getAdapter().getView(18,null,null);
                                EditText chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
                                chocolate_cookies_editText.setText(OrdersData.order_chocolate_cookies_box);
                            }
                            else {
                                if(data1.has("chocolate_cookies_box")){
                                OrdersData.order_chocolate_cookies_box = data1.getString("chocolate_cookies_box");
                                }
                            }
                            if(OrdersData.order_dark_chocolate_cookies_box!=null) {
                                view = expListView.getAdapter().getView(19,null,null);
                                EditText dark_chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
                                dark_chocolate_cookies_editText.setText(OrdersData.order_dark_chocolate_cookies_box);
                            }
                            else {
                                if (data1.has("dark_chocolate_cookies_box")) {
                                    OrdersData.order_dark_chocolate_cookies_box = data1.getString("dark_chocolate_cookies_box");
                                }
                            }
                            if(OrdersData.order_cranberry_cookies_box!=null) {
                                view = expListView.getAdapter().getView(20,null,null);
                                EditText cranberry_cookies_editText = view.findViewById(R.id.lblListItem2);
                                cranberry_cookies_editText.setText(OrdersData.order_cranberry_cookies_box);
                            }
                            else {
                                if (data1.has("cranberry_cookies_box")) {
                                    OrdersData.order_cranberry_cookies_box = data1.getString("cranberry_cookies_box");
                                }
                            }

                            if(OrdersData.order_cranberry_orange_box!=null) {
                                view = expListView.getAdapter().getView(22,null,null);
                                EditText cranberry_orange_zest_editText = view.findViewById(R.id.lblListItem2);
                                cranberry_orange_zest_editText.setText(OrdersData.order_cranberry_orange_box);
                            }
                            else {
                                if (data1.has("cranberry_orange_box")) {
                                    OrdersData.order_cranberry_orange_box = data1.getString("cranberry_orange_box");
                                }
                            }

                            if(OrdersData.order_fig_raisins_box!=null) {
                                view = expListView.getAdapter().getView(23,null,null);
                                EditText fig_raisins_editText = view.findViewById(R.id.lblListItem2);
                                fig_raisins_editText.setText(OrdersData.order_fig_raisins_box);
                            }
                            else {
                                if (data1.has("fig_raisins_box")) {
                                    OrdersData.order_fig_raisins_box = data1.getString("fig_raisins_box");
                                }
                            }
                            if(OrdersData.order_papaya_pineapple_box!=null) {
                                view = expListView.getAdapter().getView(24,null,null);
                                EditText papaya_pineapple_editText = view.findViewById(R.id.lblListItem2);
                                papaya_pineapple_editText.setText(OrdersData.order_papaya_pineapple_box);
                            }
                            else {
                                if (data1.has("papaya_pineapple_box")) {
                                    OrdersData.order_papaya_pineapple_box = data1.getString("papaya_pineapple_box");
                                }
                            }
                            if(OrdersData.order_remarks!=null) {
                                txtremarks.setText(OrdersData.order_remarks);
                            }

                        }
                    } catch (Exception ex) {
                        Log.e("order_details", "oncreate: "+ex.toString());
                        //Toast.makeText(context,"else "+ex.toString(),Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    View view;


                    if(OrdersData.order_orange_bar!=null) {
                        view = expListView.getAdapter().getView(1,null,null);
                        final EditText orange_bar_editText = (EditText) view.findViewById(R.id.lblListItem2);
                        orange_bar_editText.setText(OrdersData.order_orange_bar);
                    }

                    if(OrdersData.order_butterscotch_bar!=null) {
                        view = expListView.getAdapter().getView(2,null,null);
                        EditText butterscotch_bar_editText = view.findViewById(R.id.lblListItem2);
                        butterscotch_bar_editText.setText(OrdersData.order_butterscotch_bar);
                    }

                    if(OrdersData.order_chocopeanut_bar!=null) {
                        view = expListView.getAdapter().getView(3,null,null);
                        EditText chocopeanut_bar_editText = view.findViewById(R.id.lblListItem2);
                        chocopeanut_bar_editText.setText(OrdersData.order_chocopeanut_bar);
                    }

                    if(OrdersData.order_bambaiyachaat_bar!=null) {
                        view = expListView.getAdapter().getView(4,null,null);
                        EditText bambaiyachaat_bar_editText = view.findViewById(R.id.lblListItem2);
                        bambaiyachaat_bar_editText.setText(OrdersData.order_bambaiyachaat_bar);
                    }

                    if(OrdersData.order_mangoginger_bar!=null) {
                        view = expListView.getAdapter().getView(5,null,null);
                        EditText mangoginger_bar_editText = view.findViewById(R.id.lblListItem2);
                        mangoginger_bar_editText.setText(OrdersData.order_mangoginger_bar);
                    }

                    if(OrdersData.order_berry_blast_bar!=null) {
                        view = expListView.getAdapter().getView(6,null,null);
                        EditText berry_blast_bar_editText = view.findViewById(R.id.lblListItem2);
                        berry_blast_bar_editText.setText(OrdersData.order_berry_blast_bar);
                    }

                    if(OrdersData.order_chyawanprash_bar!=null) {
                        view = expListView.getAdapter().getView(7,null,null);
                        EditText chyawanprash_bar_editText = view.findViewById(R.id.lblListItem2);
                        chyawanprash_bar_editText.setText(OrdersData.order_chyawanprash_bar);
                    }

                    if(OrdersData.order_orange_box!=null) {
                        view = expListView.getAdapter().getView(9,null,null);
                        EditText orange_box_editText = view.findViewById(R.id.lblListItem2);
                        orange_box_editText.setText(OrdersData.order_orange_box);
                    }

                    if(OrdersData.order_butterscotch_box!=null) {
                        view = expListView.getAdapter().getView(10,null,null);
                        EditText butterscotch_box_editText = view.findViewById(R.id.lblListItem2);
                        butterscotch_box_editText.setText(OrdersData.order_butterscotch_box);
                    }

                    if(OrdersData.order_chocopeanut_box!=null) {
                        view = expListView.getAdapter().getView(11,null,null);
                        EditText chocopeanut_box_editText = view.findViewById(R.id.lblListItem2);
                        chocopeanut_box_editText.setText(OrdersData.order_chocopeanut_box);
                    }

                    if(OrdersData.order_bambaiyachaat_box!=null) {
                        view = expListView.getAdapter().getView(12,null,null);
                        EditText bambaiyachaat_box_editText = view.findViewById(R.id.lblListItem2);
                        bambaiyachaat_box_editText.setText(OrdersData.order_bambaiyachaat_box);
                    }

                    if(OrdersData.order_mangoginger_box!=null) {
                        view = expListView.getAdapter().getView(13,null,null);
                        EditText mangoginger_box_editText = view.findViewById(R.id.lblListItem2);
                        mangoginger_box_editText.setText(OrdersData.order_mangoginger_box);
                    }

                    if(OrdersData.order_berry_blast_box!=null) {
                        view = expListView.getAdapter().getView(14,null,null);
                        EditText berry_blast_box_editText = view.findViewById(R.id.lblListItem2);
                        berry_blast_box_editText.setText(OrdersData.order_berry_blast_box);
                    }

                    if(OrdersData.order_chyawanprash_box!=null) {
                        view = expListView.getAdapter().getView(15,null,null);
                        EditText chyawanprash_box_editText = view.findViewById(R.id.lblListItem2);
                        chyawanprash_box_editText.setText(OrdersData.order_chyawanprash_box);
                    }

                    if(OrdersData.order_variety_box!=null) {
                        view = expListView.getAdapter().getView(16,null,null);
                        EditText variety_box_editText = view.findViewById(R.id.lblListItem2);
                        variety_box_editText.setText(OrdersData.order_variety_box);
                    }

                    if(OrdersData.order_chocolate_cookies_box!=null) {
                        view = expListView.getAdapter().getView(18,null,null);
                        EditText chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
                        chocolate_cookies_editText.setText(OrdersData.order_chocolate_cookies_box);
                    }

                    if(OrdersData.order_dark_chocolate_cookies_box!=null) {
                        view = expListView.getAdapter().getView(19,null,null);
                        EditText dark_chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
                        dark_chocolate_cookies_editText.setText(OrdersData.order_dark_chocolate_cookies_box);
                    }

                    if(OrdersData.order_cranberry_cookies_box!=null) {
                        view = expListView.getAdapter().getView(20,null,null);
                        EditText cranberry_cookies_editText = view.findViewById(R.id.lblListItem2);
                        cranberry_cookies_editText.setText(OrdersData.order_cranberry_cookies_box);
                    }

                    if(OrdersData.order_cranberry_orange_box!=null) {
                        view = expListView.getAdapter().getView(22,null,null);
                        EditText cranberry_orange_zest_editText = view.findViewById(R.id.lblListItem2);
                        cranberry_orange_zest_editText.setText(OrdersData.order_cranberry_orange_box);
                    }

                    if(OrdersData.order_fig_raisins_box!=null) {
                        view = expListView.getAdapter().getView(23,null,null);
                        EditText fig_raisins_editText = view.findViewById(R.id.lblListItem2);
                        fig_raisins_editText.setText(OrdersData.order_fig_raisins_box);
                    }

                    if(OrdersData.order_papaya_pineapple_box!=null) {
                        view = expListView.getAdapter().getView(24,null,null);
                        EditText papaya_pineapple_editText = view.findViewById(R.id.lblListItem2);
                        papaya_pineapple_editText.setText(OrdersData.order_papaya_pineapple_box);
                    }

                    if(OrdersData.order_remarks!=null) {
                        txtremarks.setText(OrdersData.order_remarks);
                    }
                }
            }

            btnsave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                            btnsave.setEnabled(false);

                            View view = expListView.getChildAt(1);
                            EditText orange_bar_editText = view.findViewById(R.id.lblListItem2);
                            String orange_bar = orange_bar_editText.getText().toString();
                            OrdersData.order_orange_bar = orange_bar;

                            view = expListView.getChildAt(2);
                            EditText butterscotch_bar_editText = view.findViewById(R.id.lblListItem2);
                            String butterscotch_bar = butterscotch_bar_editText.getText().toString();
                            OrdersData.order_butterscotch_bar = butterscotch_bar;

                            view = expListView.getChildAt(3);
                            EditText chocopeanut_bar_editText = view.findViewById(R.id.lblListItem2);
                            String chocopeanut_bar = chocopeanut_bar_editText.getText().toString();
                            OrdersData.order_chocopeanut_bar = chocopeanut_bar;

                            view = expListView.getChildAt(4);
                            EditText bambaiyachaat_bar_editText = view.findViewById(R.id.lblListItem2);
                            String bambaiyachaat_bar = bambaiyachaat_bar_editText.getText().toString();
                            OrdersData.order_bambaiyachaat_bar = bambaiyachaat_bar;

                            view = expListView.getChildAt(5);
                            EditText mangoginger_bar_editText = view.findViewById(R.id.lblListItem2);
                            String mangoginger_bar = mangoginger_bar_editText.getText().toString();
                            OrdersData.order_mangoginger_bar = mangoginger_bar;

                            view = expListView.getChildAt(6);
                            EditText berry_blast_bar_editText = view.findViewById(R.id.lblListItem2);
                            String berry_blast_bar = berry_blast_bar_editText.getText().toString();
                            OrdersData.order_berry_blast_bar = berry_blast_bar;

                            view = expListView.getChildAt(7);
                            EditText chyawanprash_bar_editText = view.findViewById(R.id.lblListItem2);
                            String chyawanprash_bar = chyawanprash_bar_editText.getText().toString();
                            OrdersData.order_chyawanprash_bar = chyawanprash_bar;

                            view = expListView.getChildAt(9);
                            EditText orange_box_editText = view.findViewById(R.id.lblListItem2);
                            String orange_box = orange_box_editText.getText().toString();
                            OrdersData.order_orange_box = orange_box;

                            view = expListView.getChildAt(10);
                            EditText butterscotch_box_editText = view.findViewById(R.id.lblListItem2);
                            String butterscotch_box = butterscotch_box_editText.getText().toString();
                            OrdersData.order_butterscotch_box = butterscotch_box;

                            view = expListView.getChildAt(11);
                            EditText chocopeanut_box_editText = view.findViewById(R.id.lblListItem2);
                            String chocopeanut_box = chocopeanut_box_editText.getText().toString();
                            OrdersData.order_chocopeanut_box = chocopeanut_box;

                            view = expListView.getChildAt(12);
                            EditText bambaiyachaat_box_editText = view.findViewById(R.id.lblListItem2);
                            String bambaiyachaat_box = bambaiyachaat_box_editText.getText().toString();
                            OrdersData.order_bambaiyachaat_box = bambaiyachaat_box;

                            view = expListView.getChildAt(13);
                            EditText mangoginger_box_editText = view.findViewById(R.id.lblListItem2);
                            String mangoginger_box = mangoginger_box_editText.getText().toString();
                            OrdersData.order_mangoginger_box = mangoginger_box;

                            view = expListView.getChildAt(14);
                            EditText berry_blast_box_editText = view.findViewById(R.id.lblListItem2);
                            String berry_blast_box = berry_blast_box_editText.getText().toString();
                            OrdersData.order_berry_blast_box = berry_blast_box;

                            view = expListView.getChildAt(15);
                            EditText chyawanprash_box_editText = view.findViewById(R.id.lblListItem2);
                            String chyawanprash_box = chyawanprash_box_editText.getText().toString();
                            OrdersData.order_chyawanprash_box = chyawanprash_box;

                            view = expListView.getChildAt(16);
                            EditText variety_box_editText = view.findViewById(R.id.lblListItem2);
                            String variety_box = variety_box_editText.getText().toString();
                            OrdersData.order_variety_box = variety_box;

                            view = expListView.getChildAt(18);
                            EditText chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
                            String chocolate_cookies = chocolate_cookies_editText.getText().toString();
                            OrdersData.order_chocolate_cookies_box = chocolate_cookies;

                            view = expListView.getChildAt(19);
                            EditText dark_chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
                            String dark_chocolate_cookies = dark_chocolate_cookies_editText.getText().toString();
                            OrdersData.order_dark_chocolate_cookies_box = dark_chocolate_cookies;

                            view = expListView.getChildAt(20);
                            EditText cranberry_cookies_editText = view.findViewById(R.id.lblListItem2);
                            String cranberry_cookies = cranberry_cookies_editText.getText().toString();
                            OrdersData.order_cranberry_cookies_box = cranberry_cookies;

                            view = expListView.getChildAt(22);
                            EditText cranberry_orange_zest_editText = view.findViewById(R.id.lblListItem2);
                            String cranberry_orange_zest = cranberry_orange_zest_editText.getText().toString();
                            OrdersData.order_cranberry_orange_box = cranberry_orange_zest;

                            view = expListView.getChildAt(23);
                            EditText fig_raisins_editText = view.findViewById(R.id.lblListItem2);
                            String fig_raisins = fig_raisins_editText.getText().toString();
                            OrdersData.order_fig_raisins_box = fig_raisins;

                            view = expListView.getChildAt(24);
                            EditText papaya_pineapple_editText = view.findViewById(R.id.lblListItem2);
                            String papaya_pineapple = papaya_pineapple_editText.getText().toString();
                            OrdersData.order_papaya_pineapple_box = papaya_pineapple;
                            if ((orange_bar.equals("") || orange_bar.equals("0")) && (butterscotch_bar.equals("") || butterscotch_bar.equals("0")) && (chocopeanut_bar.equals("") || chocopeanut_bar.equals("0")) && (bambaiyachaat_bar.equals("") || bambaiyachaat_bar.equals("0")) && (mangoginger_bar.equals("") || mangoginger_bar.equals("0")) && (berry_blast_bar.equals("") || berry_blast_bar.equals("0")) && (chyawanprash_bar.equals("") || chyawanprash_bar.equals("0")) && (orange_box.equals("") || orange_box.equals("0")) && (butterscotch_box.equals("") || butterscotch_box.equals("0")) && (chocopeanut_box.equals("") || chocopeanut_box.equals("0")) && (bambaiyachaat_box.equals("") || bambaiyachaat_box.equals("0")) && (mangoginger_box.equals("") || mangoginger_box.equals("0")) && (berry_blast_box.equals("") || berry_blast_box.equals("0")) && (chyawanprash_box.equals("") || chyawanprash_box.equals("0")) && (variety_box.equals("") || variety_box.equals("0")) && (chocolate_cookies.equals("") || chocolate_cookies.equals("0")) && (dark_chocolate_cookies.equals("") || dark_chocolate_cookies.equals("0")) && (cranberry_cookies.equals("") || cranberry_cookies.equals("0")) && (cranberry_orange_zest.equals("") || cranberry_orange_zest.equals("0")) && (fig_raisins.equals("") || fig_raisins.equals("0")) && (papaya_pineapple.equals("") || papaya_pineapple.equals("0"))) {
                                Snackbar mSnackBar = Snackbar.make(view, Html.fromHtml("<font color=\"#ffffff\">Please enter atleast one order</font>"), Snackbar.LENGTH_LONG);
                                TextView tv = (TextView) (mSnackBar.getView()).findViewById(com.google.android.material.R.id.snackbar_text);
                                tv.setTypeface(face);
                                mSnackBar.show();
                            }
                            else {



                                showpopup();
                                add_event_in_db();
                                View_load_dialog.showDialog();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        postData();
                                    }
                                },150);

                                Visit_detailsActivity.fa.finish();
                                //RetailerDetailsActivity.fa.finish();
                            }

                }
            });

            cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View view = expListView.getChildAt(1);
                    EditText orange_bar_editText = view.findViewById(R.id.lblListItem2);
                    String orange_bar = orange_bar_editText.getText().toString();
                    OrdersData.order_orange_bar = orange_bar;

                    view = expListView.getChildAt(2);
                    EditText butterscotch_bar_editText = view.findViewById(R.id.lblListItem2);
                    String butterscotch_bar = butterscotch_bar_editText.getText().toString();
                    OrdersData.order_butterscotch_bar = butterscotch_bar;

                    view = expListView.getChildAt(3);
                    EditText chocopeanut_bar_editText = view.findViewById(R.id.lblListItem2);
                    String chocopeanut_bar = chocopeanut_bar_editText.getText().toString();
                    OrdersData.order_chocopeanut_bar = chocopeanut_bar;

                    view = expListView.getChildAt(4);
                    EditText bambaiyachaat_bar_editText = view.findViewById(R.id.lblListItem2);
                    String bambaiyachaat_bar = bambaiyachaat_bar_editText.getText().toString();
                    OrdersData.order_bambaiyachaat_bar = bambaiyachaat_bar;

                    view = expListView.getChildAt(5);
                    EditText mangoginger_bar_editText = view.findViewById(R.id.lblListItem2);
                    String mangoginger_bar = mangoginger_bar_editText.getText().toString();
                    OrdersData.order_mangoginger_bar = mangoginger_bar;

                    view = expListView.getChildAt(6);
                    EditText berry_blast_bar_editText = view.findViewById(R.id.lblListItem2);
                    String berry_blast_bar = berry_blast_bar_editText.getText().toString();
                    OrdersData.order_berry_blast_bar = berry_blast_bar;

                    view = expListView.getChildAt(7);
                    EditText chyawanprash_bar_editText = view.findViewById(R.id.lblListItem2);
                    String chyawanprash_bar = chyawanprash_bar_editText.getText().toString();
                    OrdersData.order_chyawanprash_bar = chyawanprash_bar;

                    view = expListView.getChildAt(9);
                    EditText orange_box_editText = view.findViewById(R.id.lblListItem2);
                    String orange_box = orange_box_editText.getText().toString();
                    OrdersData.order_orange_box = orange_box;

                    view = expListView.getChildAt(10);
                    EditText butterscotch_box_editText = view.findViewById(R.id.lblListItem2);
                    String butterscotch_box = butterscotch_box_editText.getText().toString();
                    OrdersData.order_butterscotch_box = butterscotch_box;

                    view = expListView.getChildAt(11);
                    EditText chocopeanut_box_editText = view.findViewById(R.id.lblListItem2);
                    String chocopeanut_box = chocopeanut_box_editText.getText().toString();
                    OrdersData.order_chocopeanut_box = chocopeanut_box;

                    view = expListView.getChildAt(12);
                    EditText bambaiyachaat_box_editText = view.findViewById(R.id.lblListItem2);
                    String bambaiyachaat_box = bambaiyachaat_box_editText.getText().toString();
                    OrdersData.order_bambaiyachaat_box = bambaiyachaat_box;

                    view = expListView.getChildAt(13);
                    EditText mangoginger_box_editText = view.findViewById(R.id.lblListItem2);
                    String mangoginger_box = mangoginger_box_editText.getText().toString();
                    OrdersData.order_mangoginger_box = mangoginger_box;

                    view = expListView.getChildAt(14);
                    EditText berry_blast_box_editText = view.findViewById(R.id.lblListItem2);
                    String berry_blast_box = berry_blast_box_editText.getText().toString();
                    OrdersData.order_berry_blast_box = berry_blast_box;

                    view = expListView.getChildAt(15);
                    EditText chyawanprash_box_editText = view.findViewById(R.id.lblListItem2);
                    String chyawanprash_box = chyawanprash_box_editText.getText().toString();
                    OrdersData.order_chyawanprash_box = chyawanprash_box;

                    view = expListView.getChildAt(16);
                    EditText variety_box_editText = view.findViewById(R.id.lblListItem2);
                    String variety_box = variety_box_editText.getText().toString();
                    OrdersData.order_variety_box = variety_box;

                    view = expListView.getChildAt(18);
                    EditText chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
                    String chocolate_cookies = chocolate_cookies_editText.getText().toString();
                    OrdersData.order_chocolate_cookies_box = chocolate_cookies;

                    view = expListView.getChildAt(19);
                    EditText dark_chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
                    String dark_chocolate_cookies = dark_chocolate_cookies_editText.getText().toString();
                    OrdersData.order_dark_chocolate_cookies_box = dark_chocolate_cookies;

                    view = expListView.getChildAt(20);
                    EditText cranberry_cookies_editText = view.findViewById(R.id.lblListItem2);
                    String cranberry_cookies = cranberry_cookies_editText.getText().toString();
                    OrdersData.order_cranberry_cookies_box = cranberry_cookies;

                    view = expListView.getChildAt(22);
                    EditText cranberry_orange_zest_editText = view.findViewById(R.id.lblListItem2);
                    String cranberry_orange_zest = cranberry_orange_zest_editText.getText().toString();
                    OrdersData.order_cranberry_orange_box = cranberry_orange_zest;

                    view = expListView.getChildAt(23);
                    EditText fig_raisins_editText = view.findViewById(R.id.lblListItem2);
                    String fig_raisins = fig_raisins_editText.getText().toString();
                    OrdersData.order_fig_raisins_box = fig_raisins;

                    view = expListView.getChildAt(24);
                    EditText papaya_pineapple_editText = view.findViewById(R.id.lblListItem2);
                    String papaya_pineapple = papaya_pineapple_editText.getText().toString();
                    OrdersData.order_papaya_pineapple_box = papaya_pineapple;

                    OrdersData.order_remarks = txtremarks.getText().toString();
                    onBackPressed();
                }
            });

        }
    }

    private void showpopup() {
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
                    if (!isAccessibilityOn (context, WhatsappAccessibilityService.class)) {
                        Intent intent = new Intent (Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        context.startActivity (intent);
                        deleteDialog.dismiss();
                        restartflag2=true;
                    }else {
                        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                        whatsappIntent.setType("text/plain");
                        whatsappIntent.setPackage("com.whatsapp");
                        whatsappIntent.putExtra(Intent.EXTRA_TEXT, returnWhatsappText()+"\n\n"+getResources().getString(R.string.whatsapp_suffix));
                        try {
                            startActivity(whatsappIntent);
                            Log.e("visit", "service started");
                            restartflag = true;
                            deleteDialog.dismiss();
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(context, "Please install whatsapp to share details on whatsapp", Toast.LENGTH_LONG).show();
                            deleteDialog.dismiss();
                            Log.e("visit", "error21 "+ex.toString());

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
                Intent i = new Intent(context, Visit_list_pageActivity.class);
                startActivityForResult(i,1);
                finish();
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

    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            expListView.expandGroup(i);
        }
    }

    public String returnWhatsappText() {
        String result="";
        try {
            DateFormat df = new SimpleDateFormat("h:mm a");
            String date = df.format(Calendar.getInstance().getTime());
            String area="";
            if(OrdersData.area!=null){area=OrdersData.area;}else if(OrdersData.reation!=null){area=OrdersData.location;}

            result+="Checkin: "+OrdersData.distributor_name + ", " + area + "\n";
            result+="Time: "+date+"\n\n";

            result += "*Orders* \n";
            int bars, cookies, trailmix;
            if ((!OrdersData.order_orange_bar.equals("") && !OrdersData.order_orange_bar.equals("0")) || (!OrdersData.order_butterscotch_bar.equals("") && !OrdersData.order_butterscotch_bar.equals("0")) || (!OrdersData.order_chocopeanut_bar.equals("") && !OrdersData.order_chocopeanut_bar.equals("0")) || (!OrdersData.order_bambaiyachaat_bar.equals("") && !OrdersData.order_bambaiyachaat_bar.equals("0")) || (!OrdersData.order_mangoginger_bar.equals("") && !OrdersData.order_mangoginger_bar.equals("0")) || (!OrdersData.order_berry_blast_bar.equals("") && !OrdersData.order_berry_blast_bar.equals("0")) || (!OrdersData.order_chyawanprash_bar.equals("") && !OrdersData.order_chyawanprash_bar.equals("0"))||(!OrdersData.order_orange_box.equals("") && !OrdersData.order_orange_box.equals("0")) || (!OrdersData.order_butterscotch_box.equals("") && !OrdersData.order_butterscotch_box.equals("0")) || (!OrdersData.order_chocopeanut_box.equals("") && !OrdersData.order_chocopeanut_box.equals("0")) || (!OrdersData.order_bambaiyachaat_box.equals("") && !OrdersData.order_bambaiyachaat_box.equals("0")) || (!OrdersData.order_mangoginger_box.equals("") && !OrdersData.order_mangoginger_box.equals("0")) || (!OrdersData.order_berry_blast_box.equals("") && !OrdersData.order_berry_blast_box.equals("0")) || (!OrdersData.order_chyawanprash_box.equals("") && !OrdersData.order_chyawanprash_box.equals("0"))|| (!OrdersData.order_variety_box.equals("") && !OrdersData.order_variety_box.equals("0"))) {
                result += "Bars- ";
                bars=0;
                if (!OrdersData.order_orange_bar.equals("") && !OrdersData.order_orange_bar.equals("0")&& OrdersData.order_orange_bar.matches("[0-9]+")) {
                    bars+=Integer.parseInt(OrdersData.order_orange_bar);
                }
                if (!OrdersData.order_butterscotch_bar.equals("") && !OrdersData.order_butterscotch_bar.equals("0")&& OrdersData.order_butterscotch_bar.matches("[0-9]+")) {
                    bars+=Integer.parseInt(OrdersData.order_butterscotch_bar);
                }
                if (!OrdersData.order_chocopeanut_bar.equals("") && !OrdersData.order_chocopeanut_bar.equals("0")&& OrdersData.order_chocopeanut_bar.matches("[0-9]+")) {
                    bars+=Integer.parseInt(OrdersData.order_chocopeanut_bar);
                }
                if (!OrdersData.order_bambaiyachaat_bar.equals("") && !OrdersData.order_bambaiyachaat_bar.equals("0")&& OrdersData.order_bambaiyachaat_bar.matches("[0-9]+")) {
                    bars+=Integer.parseInt(OrdersData.order_bambaiyachaat_bar);
                }
                if (!OrdersData.order_mangoginger_bar.equals("") && !OrdersData.order_mangoginger_bar.equals("0")&& OrdersData.order_mangoginger_bar.matches("[0-9]+")) {
                    bars+=Integer.parseInt(OrdersData.order_mangoginger_bar);
                }
                if (!OrdersData.order_berry_blast_bar.equals("") && !OrdersData.order_berry_blast_bar.equals("0")&& OrdersData.order_berry_blast_bar.matches("[0-9]+")) {
                    bars+=Integer.parseInt(OrdersData.order_berry_blast_bar);
                }
                if (!OrdersData.order_chyawanprash_bar.equals("") && !OrdersData.order_chyawanprash_bar.equals("0")&& OrdersData.order_chyawanprash_bar.matches("[0-9]+")) {
                    bars+=Integer.parseInt(OrdersData.order_chyawanprash_bar);
                }
                if (!OrdersData.order_orange_box.equals("") && !OrdersData.order_orange_box.equals("0")&& OrdersData.order_orange_box.matches("[0-9]+")) {
                    bars+=Integer.parseInt(OrdersData.order_orange_box)*6;
                }
                if (!OrdersData.order_butterscotch_box.equals("") && !OrdersData.order_butterscotch_box.equals("0")&& OrdersData.order_butterscotch_box.matches("[0-9]+")) {
                    bars+=Integer.parseInt(OrdersData.order_butterscotch_box)*6;
                }
                if (!OrdersData.order_chocopeanut_box.equals("") && !OrdersData.order_chocopeanut_box.equals("0")&& OrdersData.order_chocopeanut_box.matches("[0-9]+")) {
                    bars+=Integer.parseInt(OrdersData.order_chocopeanut_box)*6;
                }
                if (!OrdersData.order_bambaiyachaat_box.equals("") && !OrdersData.order_bambaiyachaat_box.equals("0")&& OrdersData.order_bambaiyachaat_box.matches("[0-9]+")) {
                    bars+=Integer.parseInt(OrdersData.order_bambaiyachaat_box)*6;
                }
                if (!OrdersData.order_mangoginger_box.equals("") && !OrdersData.order_mangoginger_box.equals("0")&& OrdersData.order_mangoginger_box.matches("[0-9]+")) {
                    bars+=Integer.parseInt(OrdersData.order_mangoginger_box)*6;
                }
                if (!OrdersData.order_berry_blast_box.equals("") && !OrdersData.order_berry_blast_box.equals("0")&& OrdersData.order_berry_blast_box.matches("[0-9]+")) {
                    bars+=Integer.parseInt(OrdersData.order_berry_blast_box)*6;
                }
                if (!OrdersData.order_berry_blast_box.equals("") && !OrdersData.order_berry_blast_box.equals("0")&& OrdersData.order_berry_blast_box.matches("[0-9]+")) {
                    bars+=Integer.parseInt(OrdersData.order_berry_blast_box)*6;
                }
                if (!OrdersData.order_chyawanprash_box.equals("") && !OrdersData.order_chyawanprash_box.equals("0")&& OrdersData.order_chyawanprash_box.matches("[0-9]+")) {
                    bars+=Integer.parseInt(OrdersData.order_chyawanprash_box)*6;
                }
                if (!OrdersData.order_variety_box.equals("") && !OrdersData.order_variety_box.equals("0")&& OrdersData.order_variety_box.matches("[0-9]+")) {
                    bars+=Integer.parseInt(OrdersData.order_variety_box)*6;
                }
                result+=bars+"\n";
            }

            if ((!OrdersData.order_cranberry_cookies_box.equals("") && !OrdersData.order_cranberry_orange_box.equals("0")) || (!OrdersData.order_dark_chocolate_cookies_box.equals("") && !OrdersData.order_dark_chocolate_cookies_box.equals("0")) || (!OrdersData.order_chocolate_cookies_box.equals("") && !OrdersData.order_chocolate_cookies_box.equals("0"))) {
                result += "Cookies- ";
                cookies=0;
                if (!OrdersData.order_chocolate_cookies_box.equals("") && !OrdersData.order_chocolate_cookies_box.equals("0")&& OrdersData.order_chocolate_cookies_box.matches("[0-9]+")) {
                    cookies+=Integer.parseInt(OrdersData.order_chocolate_cookies_box);
                }
                if (!OrdersData.order_dark_chocolate_cookies_box.equals("") && !OrdersData.order_dark_chocolate_cookies_box.equals("0")&& OrdersData.order_dark_chocolate_cookies_box.matches("[0-9]+")) {
                    cookies+=Integer.parseInt(OrdersData.order_dark_chocolate_cookies_box);
                }
                if (!OrdersData.order_cranberry_cookies_box.equals("") && !OrdersData.order_cranberry_cookies_box.equals("0")&& OrdersData.order_cranberry_cookies_box.matches("[0-9]+")) {
                    cookies+=Integer.parseInt(OrdersData.order_cranberry_cookies_box);
                }
                result+=cookies+"\n";
            }

            if ((!OrdersData.order_cranberry_orange_box.equals("") && !OrdersData.order_cranberry_orange_box.equals("0")) || (!OrdersData.order_fig_raisins_box.equals("") && !OrdersData.order_fig_raisins_box.equals("0")) || (!OrdersData.order_papaya_pineapple_box.equals("") && !OrdersData.order_papaya_pineapple_box.equals("0"))) {
                result += "Trailmix- ";
                trailmix=0;
                if (!OrdersData.order_fig_raisins_box.equals("") && !OrdersData.order_fig_raisins_box.equals("0")&& OrdersData.order_fig_raisins_box.matches("[0-9]+")) {
                    trailmix+=Integer.parseInt(OrdersData.order_fig_raisins_box);
                }
                if (!OrdersData.order_cranberry_orange_box.equals("") && !OrdersData.order_cranberry_orange_box.equals("0")&& OrdersData.order_cranberry_orange_box.matches("[0-9]+")) {
                    trailmix+=Integer.parseInt(OrdersData.order_cranberry_orange_box);
                }
                if (!OrdersData.order_papaya_pineapple_box.equals("") && !OrdersData.order_papaya_pineapple_box.equals("0")&& OrdersData.order_papaya_pineapple_box.matches("[0-9]+")) {
                    trailmix+=Integer.parseInt(OrdersData.order_papaya_pineapple_box);
                }
                result+=trailmix+"\n";
            }

            result +="\n *Inventory* \n";
            if ((!OrdersData.orange_bar.equals("") && !OrdersData.orange_bar.equals("0")) || (!OrdersData.butterscotch_bar.equals("") && !OrdersData.butterscotch_bar.equals("0")) || (!OrdersData.chocopeanut_bar.equals("") && !OrdersData.chocopeanut_bar.equals("0")) || (!OrdersData.bambaiyachaat_bar.equals("") && !OrdersData.bambaiyachaat_bar.equals("0")) || (!OrdersData.mangoginger_bar.equals("") && !OrdersData.mangoginger_bar.equals("0")) || (!OrdersData.berry_blast_bar.equals("") && !OrdersData.berry_blast_bar.equals("0")) ||(!OrdersData.chyawanprash_bar.equals("") && !OrdersData.chyawanprash_bar.equals("0"))) {
                result += "Bars- " + "\n";
            }
            if (!OrdersData.orange_bar.equals("") && !OrdersData.orange_bar.equals("0")) {
                result += "OG: " + OrdersData.orange_bar + "\n";
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
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                    }
                }result+="\n";
            }
            if (!OrdersData.butterscotch_bar.equals("") && !OrdersData.butterscotch_bar.equals("0")) {
                result += "BS: " + OrdersData.butterscotch_bar + "\n";
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
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                    }
                }result+="\n";
            }
            if (!OrdersData.chocopeanut_bar.equals("") && !OrdersData.chocopeanut_bar.equals("0")) {
                result += "CPC: " + OrdersData.chocopeanut_bar + "\n";
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
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                    }
                }result+="\n";
            }
            if (!OrdersData.bambaiyachaat_bar.equals("") && !OrdersData.bambaiyachaat_bar.equals("0")) {
                result += "BC: " + OrdersData.bambaiyachaat_bar + "\n";
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
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                    }
                }result+="\n";
            }
            if (!OrdersData.mangoginger_bar.equals("") && !OrdersData.mangoginger_bar.equals("0")) {
                result += "MG: " + OrdersData.mangoginger_bar + "\n";
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
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                    }
                }result+="\n";
            }
            if (!OrdersData.berry_blast_bar.equals("") && !OrdersData.berry_blast_bar.equals("0")) {
                result += "BB: " + OrdersData.berry_blast_bar + "\n";
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
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                    }
                }result+="\n";
            }
            if (!OrdersData.chyawanprash_bar.equals("") && !OrdersData.chyawanprash_bar.equals("0")) {
                result += "CHY: " + OrdersData.chyawanprash_bar + "\n";
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
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                    }
                }result+="\n";
            }
            if ((!OrdersData.orange_box.equals("") && !OrdersData.orange_box.equals("0")) || (!OrdersData.butterscotch_box.equals("") && !OrdersData.butterscotch_box.equals("0")) || (!OrdersData.chocopeanut_box.equals("") && !OrdersData.chocopeanut_box.equals("0")) || (!OrdersData.bambaiyachaat_box.equals("") && !OrdersData.bambaiyachaat_box.equals("0")) || (!OrdersData.mangoginger_box.equals("") && !OrdersData.mangoginger_box.equals("0")) || (!OrdersData.berry_blast_box.equals("") && !OrdersData.berry_blast_box.equals("0")) ||(!OrdersData.chyawanprash_box.equals("") && !OrdersData.chyawanprash_box.equals("0"))) {
                result += "Box- " + "\n";
            }
            if (!OrdersData.orange_box.equals("") && !OrdersData.orange_box.equals("0")) {
                result += "OG: " + OrdersData.orange_box + "\n";
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
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                    }
                }result+="\n";
            }
            if (!OrdersData.butterscotch_box.equals("") && !OrdersData.butterscotch_box.equals("0")) {
                result += "BS: " + OrdersData.butterscotch_box + "\n";
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
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                    }
                }result+="\n";
            }
            if (!OrdersData.chocopeanut_box.equals("") && !OrdersData.chocopeanut_box.equals("0")) {
                result += "CPC: " + OrdersData.chocopeanut_box + "\n";
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
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                    }
                }result+="\n";
            }
            if (!OrdersData.bambaiyachaat_box.equals("") && !OrdersData.bambaiyachaat_box.equals("0")) {
                result += "BC: " + OrdersData.bambaiyachaat_box + "\n";
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
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                    }
                }result+="\n";
            }
            if (!OrdersData.mangoginger_box.equals("") && !OrdersData.mangoginger_box.equals("0")) {
                result += "MG: " + OrdersData.mangoginger_box + "\n";
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
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                    }
                }result+="\n";
            }
            if (!OrdersData.chyawanprash_box.equals("") && !OrdersData.chyawanprash_box.equals("0")) {
                result += "CHY: " + OrdersData.chyawanprash_box + "\n";
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
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                    }
                }result+="\n";
            }
            if (!OrdersData.variety_box.equals("") && !OrdersData.variety_box.equals("0")) {
                result += "VP: " + OrdersData.variety_box + "\n";
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
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                    }
                }result+="\n";
            }
            if ((!OrdersData.cranberry_cookies_box.equals("") && !OrdersData.cranberry_cookies_box.equals("0")) || (!OrdersData.dark_chocolate_cookies_box.equals("") && !OrdersData.dark_chocolate_cookies_box.equals("0")) || (!OrdersData.chocolate_cookies_box.equals("") && !OrdersData.chocolate_cookies_box.equals("0"))) {
                result += "Cookies- " + "\n";
            }
            if (!OrdersData.chocolate_cookies_box.equals("") && !OrdersData.chocolate_cookies_box.equals("0")) {
                result += "MC: " + OrdersData.chocolate_cookies_box + "\n";
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
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                    }
                }result+="\n";
            }
            if (!OrdersData.dark_chocolate_cookies_box.equals("") && !OrdersData.dark_chocolate_cookies_box.equals("0")) {
                result += "DC: " + OrdersData.dark_chocolate_cookies_box + "\n";
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
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                    }
                }result+="\n";
            }
            if (!OrdersData.cranberry_cookies_box.equals("") && !OrdersData.cranberry_cookies_box.equals("0")) {
                result += "CC: " + OrdersData.cranberry_cookies_box + "\n";
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
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                    }
                }result+="\n";
            }
            if ((!OrdersData.cranberry_orange_box.equals("") && !OrdersData.cranberry_orange_box.equals("0")) || (!OrdersData.fig_raisins_box.equals("") && !OrdersData.fig_raisins_box.equals("0")) || (!OrdersData.papaya_pineapple_box.equals("") && !OrdersData.papaya_pineapple_box.equals("0"))) {
                result += "Trailmix- " + "\n";
            }

            if (!OrdersData.fig_raisins_box.equals("") && !OrdersData.fig_raisins_box.equals("0")) {
                result += "FR: " + OrdersData.fig_raisins_box + "\n";
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
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                    }
                }result+="\n";
            }
            if (!OrdersData.cranberry_orange_box.equals("") && !OrdersData.cranberry_orange_box.equals("0")) {
                result += "CO: " + OrdersData.cranberry_orange_box + "\n";
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
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                    }
                }result+="\n";
            }
            if (!OrdersData.papaya_pineapple_box.equals("") && !OrdersData.papaya_pineapple_box.equals("0")) {
                result += "PP: " + OrdersData.papaya_pineapple_box + "\n";
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
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += "Batch nos   " + "\n" + data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                        count++;
                    } else {
                        JSONArray data = new JSONArray(OrdersData.batch_no_json);
                        JSONObject data1 = data.getJSONObject(Integer.parseInt(key)-1);
                        result += data1.getString("batch_no") +":    "+ obj.getString(key)+"\n";
                    }
                }result+="\n";
            }

            if(result.endsWith(" *Inventory* \n")){
                result+="No Stock\n\n";
            }


        }catch (Exception e){
            e.printStackTrace();
            Log.e("order_details", e.toString());
        }
        return result;
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        View view = expListView.getChildAt(1);
        EditText orange_bar_editText = view.findViewById(R.id.lblListItem2);
        String orange_bar = orange_bar_editText.getText().toString();
        OrdersData.order_orange_bar = orange_bar;

        view = expListView.getChildAt(2);
        EditText butterscotch_bar_editText = view.findViewById(R.id.lblListItem2);
        String butterscotch_bar = butterscotch_bar_editText.getText().toString();
        OrdersData.order_butterscotch_bar = butterscotch_bar;

        view = expListView.getChildAt(3);
        EditText chocopeanut_bar_editText = view.findViewById(R.id.lblListItem2);
        String chocopeanut_bar = chocopeanut_bar_editText.getText().toString();
        OrdersData.order_chocopeanut_bar = chocopeanut_bar;

        view = expListView.getChildAt(4);
        EditText bambaiyachaat_bar_editText = view.findViewById(R.id.lblListItem2);
        String bambaiyachaat_bar = bambaiyachaat_bar_editText.getText().toString();
        OrdersData.order_bambaiyachaat_bar = bambaiyachaat_bar;

        view = expListView.getChildAt(5);
        EditText mangoginger_bar_editText = view.findViewById(R.id.lblListItem2);
        String mangoginger_bar = mangoginger_bar_editText.getText().toString();
        OrdersData.order_mangoginger_bar = mangoginger_bar;

        view = expListView.getChildAt(6);
        EditText berry_blast_bar_editText = view.findViewById(R.id.lblListItem2);
        String berry_blast_bar = berry_blast_bar_editText.getText().toString();
        OrdersData.order_berry_blast_bar = berry_blast_bar;

        view = expListView.getChildAt(7);
        EditText chyawanprash_bar_editText = view.findViewById(R.id.lblListItem2);
        String chyawanprash_bar = chyawanprash_bar_editText.getText().toString();
        OrdersData.order_chyawanprash_bar = chyawanprash_bar;

        view = expListView.getChildAt(9);
        EditText orange_box_editText = view.findViewById(R.id.lblListItem2);
        String orange_box = orange_box_editText.getText().toString();
        OrdersData.order_orange_box = orange_box;

        view = expListView.getChildAt(10);
        EditText butterscotch_box_editText = view.findViewById(R.id.lblListItem2);
        String butterscotch_box = butterscotch_box_editText.getText().toString();
        OrdersData.order_butterscotch_box = butterscotch_box;

        view = expListView.getChildAt(11);
        EditText chocopeanut_box_editText = view.findViewById(R.id.lblListItem2);
        String chocopeanut_box = chocopeanut_box_editText.getText().toString();
        OrdersData.order_chocopeanut_box = chocopeanut_box;

        view = expListView.getChildAt(12);
        EditText bambaiyachaat_box_editText = view.findViewById(R.id.lblListItem2);
        String bambaiyachaat_box = bambaiyachaat_box_editText.getText().toString();
        OrdersData.order_bambaiyachaat_box = bambaiyachaat_box;

        view = expListView.getChildAt(13);
        EditText mangoginger_box_editText = view.findViewById(R.id.lblListItem2);
        String mangoginger_box = mangoginger_box_editText.getText().toString();
        OrdersData.order_mangoginger_box = mangoginger_box;

        view = expListView.getChildAt(14);
        EditText berry_blast_box_editText = view.findViewById(R.id.lblListItem2);
        String berry_blast_box = berry_blast_box_editText.getText().toString();
        OrdersData.order_berry_blast_box = berry_blast_box;

        view = expListView.getChildAt(15);
        EditText chyawanprash_box_editText = view.findViewById(R.id.lblListItem2);
        String chyawanprash_box = chyawanprash_box_editText.getText().toString();
        OrdersData.order_chyawanprash_box = chyawanprash_box;

        view = expListView.getChildAt(16);
        EditText variety_box_editText = view.findViewById(R.id.lblListItem2);
        String variety_box = variety_box_editText.getText().toString();
        OrdersData.order_variety_box = variety_box;

        view = expListView.getChildAt(18);
        EditText chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
        String chocolate_cookies = chocolate_cookies_editText.getText().toString();
        OrdersData.order_chocolate_cookies_box = chocolate_cookies;

        view = expListView.getChildAt(19);
        EditText dark_chocolate_cookies_editText = view.findViewById(R.id.lblListItem2);
        String dark_chocolate_cookies = dark_chocolate_cookies_editText.getText().toString();
        OrdersData.order_dark_chocolate_cookies_box = dark_chocolate_cookies;

        view = expListView.getChildAt(20);
        EditText cranberry_cookies_editText = view.findViewById(R.id.lblListItem2);
        String cranberry_cookies = cranberry_cookies_editText.getText().toString();
        OrdersData.order_cranberry_cookies_box = cranberry_cookies;

        view = expListView.getChildAt(22);
        EditText cranberry_orange_zest_editText = view.findViewById(R.id.lblListItem2);
        String cranberry_orange_zest = cranberry_orange_zest_editText.getText().toString();
        OrdersData.order_cranberry_orange_box = cranberry_orange_zest;

        view = expListView.getChildAt(23);
        EditText fig_raisins_editText = view.findViewById(R.id.lblListItem2);
        String fig_raisins = fig_raisins_editText.getText().toString();
        OrdersData.order_fig_raisins_box = fig_raisins;

        view = expListView.getChildAt(24);
        EditText papaya_pineapple_editText = view.findViewById(R.id.lblListItem2);
        String papaya_pineapple = papaya_pineapple_editText.getText().toString();
        OrdersData.order_papaya_pineapple_box = papaya_pineapple;

        OrdersData.order_remarks = txtremarks.getText().toString();
        //Toast.makeText(context,OrdersData.order_remarks,Toast.LENGTH_LONG).show();

        finish();
        // your code.
    }

    public void postData() {
// Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        //HttpPost httppost = new HttpPost("https://www.eatanytime.in/eat_erp/index.php/Sales_rep_store_plan_mobile_app/get_array_api");

        try {
            // Add your data
            //you can add all the parameters your php needs in the BasicNameValuePair.
            //The first parameter refers to the name in the php field for example
            // $id=$_POST['id']; the second parameter is the value.

            HttpPost httppost = new HttpPost("https://www.eatanytime.in/eat_erp/index.php/Sales_rep_store_plan_mobile_app/save_order_api");

            JSONObject json_visit_details = new JSONObject();
            json_visit_details.put("channel_type", OrdersData.channel_type);
            json_visit_details.put("distributor_types", OrdersData.distributor_type);
            json_visit_details.put("distributor_name", OrdersData.distributor_name);
            json_visit_details.put("zone_id", OrdersData.zone_id);
            json_visit_details.put("area_id",OrdersData.area_id);
            json_visit_details.put("location_id",OrdersData.location_id);
            json_visit_details.put("remarks",OrdersData.remarks);
            json_visit_details.put("longitude",OrdersData.longitude);
            json_visit_details.put("latitude",OrdersData.latitude);
            json_visit_details.put("distributor_id",OrdersData.distributor_id);
            json_visit_details.put("mid",OrdersData.mid);
            json_visit_details.put("reation_id",OrdersData.reation_id);
            json_visit_details.put("beat_plan_id",OrdersData.beat_plan_id);
            json_visit_details.put("store_id",OrdersData.store_id);
            json_visit_details.put("distributor_status",OrdersData.distributor_status);
            json_visit_details.put("sales_rep_loc_id",OrdersData.sales_rep_loc_id);
            json_visit_details.put("sequence",OrdersData.sequence);
            json_visit_details.put("follow_type",OrdersData.follow_type);
            json_visit_details.put("merchandiser_stock_id",OrdersData.merchandiser_stock_id);
            JSONObject json_sales_rep_stock_detail = new JSONObject();
            json_sales_rep_stock_detail.put("chocolate_cookies_box",OrdersData.chocolate_cookies_box);
            json_sales_rep_stock_detail.put("dark_chocolate_cookies_box",OrdersData.dark_chocolate_cookies_box);
            json_sales_rep_stock_detail.put("cranberry_cookies_box",OrdersData.cranberry_cookies_box);
            json_sales_rep_stock_detail.put("cranberry_orange_box",OrdersData.cranberry_orange_box);
            json_sales_rep_stock_detail.put("fig_raisins_box",OrdersData.fig_raisins_box);
            json_sales_rep_stock_detail.put("papaya_pineapple_box",OrdersData.papaya_pineapple_box);
            json_sales_rep_stock_detail.put("orange_bar",OrdersData.orange_bar);
            json_sales_rep_stock_detail.put("orange_box",OrdersData.orange_box);
            json_sales_rep_stock_detail.put("butterscotch_bar",OrdersData.butterscotch_bar);
            json_sales_rep_stock_detail.put("butterscotch_box",OrdersData.butterscotch_box);
            json_sales_rep_stock_detail.put("chocopeanut_bar",OrdersData.chocopeanut_bar);
            json_sales_rep_stock_detail.put("chocopeanut_box",OrdersData.chocopeanut_box);
            json_sales_rep_stock_detail.put("bambaiyachaat_bar",OrdersData.bambaiyachaat_bar);
            json_sales_rep_stock_detail.put("bambaiyachaat_box",OrdersData.bambaiyachaat_box);
            json_sales_rep_stock_detail.put("mangoginger_bar",OrdersData.mangoginger_bar);
            json_sales_rep_stock_detail.put("mangoginger_box",OrdersData.mangoginger_box);
            json_sales_rep_stock_detail.put("berry_blast_bar",OrdersData.berry_blast_bar);
            json_sales_rep_stock_detail.put("berry_blast_box",OrdersData.berry_blast_box);
            json_sales_rep_stock_detail.put("chyawanprash_bar",OrdersData.chyawanprash_bar);
            json_sales_rep_stock_detail.put("chyawanprash_box",OrdersData.chyawanprash_box);
            json_sales_rep_stock_detail.put("variety_box",OrdersData.variety_box);


            JSONArray json_merchandiser_stock_details = new JSONArray();
            JSONObject json_chocolate_cookies_box = new JSONObject();
            json_chocolate_cookies_box.put("type","Box");
            json_chocolate_cookies_box.put("item_id","37");
            json_chocolate_cookies_box.put("qty",OrdersData.chocolate_cookies_box);

            JSONObject json_dark_chocolate_cookies_box = new JSONObject();
            json_dark_chocolate_cookies_box.put("type","Box");
            json_dark_chocolate_cookies_box.put("item_id","38");
            json_dark_chocolate_cookies_box.put("qty",OrdersData.dark_chocolate_cookies_box);

            JSONObject json_cranberry_cookies_box = new JSONObject();
            json_cranberry_cookies_box.put("type","Box");
            json_cranberry_cookies_box.put("item_id","39");
            json_cranberry_cookies_box.put("qty",OrdersData.cranberry_cookies_box);

            JSONObject json_cranberry_orange_box = new JSONObject();
            json_cranberry_orange_box.put("type","Box");
            json_cranberry_orange_box.put("item_id","42");
            json_cranberry_orange_box.put("qty",OrdersData.cranberry_orange_box);

            JSONObject json_fig_raisins_box = new JSONObject();
            json_fig_raisins_box.put("type","Box");
            json_fig_raisins_box.put("item_id","41");
            json_fig_raisins_box.put("qty",OrdersData.fig_raisins_box);

            JSONObject json_papaya_pineapple_box = new JSONObject();
            json_papaya_pineapple_box.put("type","Box");
            json_papaya_pineapple_box.put("item_id","40");
            json_papaya_pineapple_box.put("qty",OrdersData.papaya_pineapple_box);

            JSONObject json_orange_bar = new JSONObject();
            json_orange_bar.put("type","Bar");
            json_orange_bar.put("item_id","1");
            json_orange_bar.put("qty",OrdersData.orange_bar);

            JSONObject json_orange_box = new JSONObject();
            json_orange_box.put("type","Box");
            json_orange_box.put("item_id","1");
            json_orange_box.put("qty",OrdersData.orange_box);

            JSONObject json_butterscotch_bar = new JSONObject();
            json_butterscotch_bar.put("type","Bar");
            json_butterscotch_bar.put("item_id","3");
            json_butterscotch_bar.put("qty",OrdersData.butterscotch_bar);

            JSONObject json_butterscotch_box = new JSONObject();
            json_butterscotch_box.put("type","Box");
            json_butterscotch_box.put("item_id","3");
            json_butterscotch_box.put("qty",OrdersData.butterscotch_box);

            JSONObject json_chocopeanut_bar = new JSONObject();
            json_chocopeanut_bar.put("type","Bar");
            json_chocopeanut_bar.put("item_id","5");
            json_chocopeanut_bar.put("qty",OrdersData.chocopeanut_bar);

            JSONObject json_chocopeanut_box = new JSONObject();
            json_chocopeanut_box.put("type","Box");
            json_chocopeanut_box.put("item_id","9");
            json_chocopeanut_box.put("qty",OrdersData.chocopeanut_box);

            JSONObject json_bambaiyachaat_bar = new JSONObject();
            json_bambaiyachaat_bar.put("type","Bar");
            json_bambaiyachaat_bar.put("item_id","4");
            json_bambaiyachaat_bar.put("qty",OrdersData.bambaiyachaat_bar);

            JSONObject json_bambaiyachaat_box = new JSONObject();
            json_bambaiyachaat_box.put("type","Box");
            json_bambaiyachaat_box.put("item_id","8");
            json_bambaiyachaat_box.put("qty",OrdersData.bambaiyachaat_box);

            JSONObject json_mangoginger_bar = new JSONObject();
            json_mangoginger_bar.put("type","Bar");
            json_mangoginger_bar.put("item_id","6");
            json_mangoginger_bar.put("qty",OrdersData.mangoginger_bar);

            JSONObject json_mangoginger_box = new JSONObject();
            json_mangoginger_box.put("type","Box");
            json_mangoginger_box.put("item_id","12");
            json_mangoginger_box.put("qty",OrdersData.mangoginger_box);

            JSONObject json_berry_blast_bar = new JSONObject();
            json_berry_blast_bar.put("type","Bar");
            json_berry_blast_bar.put("item_id","9");
            json_berry_blast_bar.put("qty",OrdersData.berry_blast_bar);

            JSONObject json_berry_blast_box = new JSONObject();
            json_berry_blast_box.put("type","Box");
            json_berry_blast_box.put("item_id","29");
            json_berry_blast_box.put("qty",OrdersData.berry_blast_box);

            JSONObject json_chyawanprash_bar = new JSONObject();
            json_chyawanprash_bar.put("type","Bar");
            json_chyawanprash_bar.put("item_id","10");
            json_chyawanprash_bar.put("qty",OrdersData.chyawanprash_bar);

            JSONObject json_chyawanprash_box = new JSONObject();
            json_chyawanprash_box.put("type","Box");
            json_chyawanprash_box.put("item_id","31");
            json_chyawanprash_box.put("qty",OrdersData.chyawanprash_box);

            JSONObject json_variety_box = new JSONObject();
            json_variety_box.put("type","Box");
            json_variety_box.put("item_id","32");
            json_variety_box.put("qty",OrdersData.variety_box);


            json_merchandiser_stock_details.put(json_chocolate_cookies_box);
            json_merchandiser_stock_details.put(json_dark_chocolate_cookies_box);
            json_merchandiser_stock_details.put(json_cranberry_cookies_box);
            json_merchandiser_stock_details.put(json_cranberry_orange_box);
            json_merchandiser_stock_details.put(json_fig_raisins_box);
            json_merchandiser_stock_details.put(json_papaya_pineapple_box);
            json_merchandiser_stock_details.put(json_orange_bar);
            json_merchandiser_stock_details.put(json_orange_box);
            json_merchandiser_stock_details.put(json_butterscotch_bar);
            json_merchandiser_stock_details.put(json_butterscotch_box);
            json_merchandiser_stock_details.put(json_chocopeanut_bar);
            json_merchandiser_stock_details.put(json_chocopeanut_box);
            json_merchandiser_stock_details.put(json_bambaiyachaat_bar);
            json_merchandiser_stock_details.put(json_bambaiyachaat_box);
            json_merchandiser_stock_details.put(json_mangoginger_bar);
            json_merchandiser_stock_details.put(json_mangoginger_box);
            json_merchandiser_stock_details.put(json_berry_blast_bar);
            json_merchandiser_stock_details.put(json_berry_blast_box);
            json_merchandiser_stock_details.put(json_chyawanprash_bar);
            json_merchandiser_stock_details.put(json_chyawanprash_box);
            json_merchandiser_stock_details.put(json_variety_box);

            JSONObject json_retailer_detail = new JSONObject();
            json_retailer_detail.put("margin",OrdersData.margin);
            json_retailer_detail.put("retailer_remarks",OrdersData.retailer_remarks);
            json_retailer_detail.put("gst_number",OrdersData.gst_number);
            json_retailer_detail.put("master_distributor_id",session.getdistributor_id());


            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("visit_details", json_visit_details.toString()));
            nameValuePairs.add(new BasicNameValuePair("sales_rep_stock_detail", json_sales_rep_stock_detail.toString()));
            nameValuePairs.add(new BasicNameValuePair("merchandiser_stock_details", json_merchandiser_stock_details.toString()));
            nameValuePairs.add(new BasicNameValuePair("retailer_detail", json_retailer_detail.toString()));
            nameValuePairs.add(new BasicNameValuePair("srld", "Place Order"));
            nameValuePairs.add(new BasicNameValuePair("place_order", "Yes"));
            nameValuePairs.add(new BasicNameValuePair("sales_rep_id", session.getsales_rep_id()));
            nameValuePairs.add(new BasicNameValuePair("session_id", session.getSessionid()));
            nameValuePairs.add(new BasicNameValuePair("distributor_id", session.getdistributor_id()));
            nameValuePairs.add(new BasicNameValuePair("remarks", txtremarks.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("orange_bar", OrdersData.order_orange_bar));
            nameValuePairs.add(new BasicNameValuePair("orange_box", OrdersData.order_orange_box));
            nameValuePairs.add(new BasicNameValuePair("butterscotch_bar", OrdersData.order_butterscotch_bar));
            nameValuePairs.add(new BasicNameValuePair("butterscotch_box", OrdersData.order_butterscotch_box));
            nameValuePairs.add(new BasicNameValuePair("chocopeanut_bar", OrdersData.order_chocopeanut_bar));
            nameValuePairs.add(new BasicNameValuePair("chocopeanut_box", OrdersData.order_chocopeanut_box));
            nameValuePairs.add(new BasicNameValuePair("bambaiyachaat_bar", OrdersData.order_bambaiyachaat_bar));
            nameValuePairs.add(new BasicNameValuePair("bambaiyachaat_box", OrdersData.order_bambaiyachaat_box));
            nameValuePairs.add(new BasicNameValuePair("mangoginger_bar", OrdersData.order_mangoginger_bar));
            nameValuePairs.add(new BasicNameValuePair("mangoginger_box", OrdersData.order_mangoginger_box));
            nameValuePairs.add(new BasicNameValuePair("berry_blast_bar", OrdersData.order_berry_blast_bar));
            nameValuePairs.add(new BasicNameValuePair("berry_blast_box", OrdersData.order_berry_blast_box));
            nameValuePairs.add(new BasicNameValuePair("chyawanprash_bar", OrdersData.order_chyawanprash_bar));
            nameValuePairs.add(new BasicNameValuePair("chyawanprash_box", OrdersData.order_chyawanprash_box));
            nameValuePairs.add(new BasicNameValuePair("variety_box", OrdersData.order_variety_box));
            nameValuePairs.add(new BasicNameValuePair("chocolate_cookies", OrdersData.order_chocolate_cookies_box));
            nameValuePairs.add(new BasicNameValuePair("dark_chocolate_cookies", OrdersData.order_dark_chocolate_cookies_box));
            nameValuePairs.add(new BasicNameValuePair("cranberry_cookies", OrdersData.order_cranberry_cookies_box));
            nameValuePairs.add(new BasicNameValuePair("fig_raisins", OrdersData.order_fig_raisins_box));
            nameValuePairs.add(new BasicNameValuePair("papaya_pineapple", OrdersData.order_papaya_pineapple_box));
            nameValuePairs.add(new BasicNameValuePair("cranberry_orange_zest", OrdersData.order_cranberry_orange_box));
            nameValuePairs.add(new BasicNameValuePair("ispermenant", OrdersData.ispermenant));
            nameValuePairs.add(new BasicNameValuePair("batch_detail", OrdersData.batch_details));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            if((!OrdersData.order_orange_bar.equals("")&&!OrdersData.order_orange_bar.equals("0"))||(!OrdersData.order_orange_box.equals("")&&!OrdersData.order_orange_box.equals("0"))|| (!OrdersData.order_butterscotch_bar.equals("")&&!OrdersData.order_butterscotch_bar.equals("0"))|| (!OrdersData.order_butterscotch_box.equals("")&&!OrdersData.order_butterscotch_box.equals("0"))||(!OrdersData.order_chocopeanut_bar.equals("")&&!OrdersData.order_chocopeanut_bar.equals("0"))|| (!OrdersData.order_chocopeanut_box.equals("")&&!OrdersData.order_chocopeanut_box.equals("0"))|| (!OrdersData.order_bambaiyachaat_bar.equals("")&&!OrdersData.order_bambaiyachaat_bar.equals("0"))|| (!OrdersData.order_bambaiyachaat_box.equals("")&&!OrdersData.order_bambaiyachaat_box.equals("0"))|| (!OrdersData.order_mangoginger_bar.equals("")&&!OrdersData.order_mangoginger_bar.equals("0"))|| (!OrdersData.order_mangoginger_box.equals("")&&!OrdersData.order_mangoginger_box.equals("0"))|| (!OrdersData.order_berry_blast_bar.equals("")&&!OrdersData.order_berry_blast_bar.equals("0"))|| (!OrdersData.order_berry_blast_box.equals("")&&!OrdersData.order_berry_blast_box.equals("0"))||(! OrdersData.order_chyawanprash_bar.equals("")&&!OrdersData.order_chyawanprash_bar.equals("0"))|| (!OrdersData.order_chyawanprash_box.equals("")&&!OrdersData.order_chyawanprash_box.equals("0"))|| (!OrdersData.order_variety_box.equals("")&&!OrdersData.order_variety_box.equals("0"))|| (!OrdersData.order_chocolate_cookies_box.equals("")&&!OrdersData.order_chocolate_cookies_box.equals("0"))|| (!OrdersData.order_dark_chocolate_cookies_box.equals("")&&!OrdersData.order_dark_chocolate_cookies_box.equals("0"))||( !OrdersData.order_cranberry_cookies_box.equals("")&&!OrdersData.order_cranberry_cookies_box.equals("0"))|| (!OrdersData.order_fig_raisins_box.equals("")&&!OrdersData.order_fig_raisins_box.equals("0"))|| (!OrdersData.order_papaya_pineapple_box.equals("")&&!OrdersData.order_papaya_pineapple_box.equals("0"))|| (!OrdersData.order_cranberry_orange_box.equals("")&&!OrdersData.order_cranberry_orange_box.equals("0"))){
                Intent i=getIntent();
                if(i.hasExtra("type")){
                    String type=i.getStringExtra("type");
                    if(OrdersData.p_call!=null){
                        if(type.equals("add")&&!OrdersData.p_call.equals("") ){
                            int p_c=Integer.parseInt(OrdersData.p_call)+1;
                            OrdersData.p_call=p_c+"";
                        }
                    }
                }

            }

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            //Toast.makeText(context,result,Toast.LENGTH_LONG).show();
            JSONObject obj = new JSONObject(result);
            Log.e("orders", OrdersData.latitude+" "+OrdersData.longitude);

            if(obj==null){
                //return "";
            } else {
                String monday = obj.getString("Monday");
                String tuesday = obj.getString("Tuesday");
                String wednesday = obj.getString("Wednesday");
                String thursday = obj.getString("Thursday");
                String friday = obj.getString("Friday");
                String saturday = obj.getString("Saturday");
                String sunday= obj.getString("Sunday");

                if(!monday.equals("[]")){
                    //Toast.makeText(context,"monday "+text,Toast.LENGTH_LONG).show();
                    session.setmon_frag(monday);
                }
                if(!tuesday.equals("[]")){
                    //Toast.makeText(context,"tuesday "+text,Toast.LENGTH_LONG).show();
                    session.settues_frag(tuesday);
                }
                if(!wednesday.equals("[]")){
                    //Toast.makeText(context,"wednesday "+text,Toast.LENGTH_LONG).show();
                    session.setwed_frag(wednesday);
                }
                if(!thursday.equals("[]")){
                    //Toast.makeText(context,"thursday "+text,Toast.LENGTH_LONG).show();
                    session.setthu_frag(thursday);
                }
                if(!friday.equals("[]")){
                    //Toast.makeText(context,"friday "+text,Toast.LENGTH_LONG).show();
                    session.setfri_frag(friday);
                }
                if(!saturday.equals("[]")){
                    //Toast.makeText(context,"saturday "+text,Toast.LENGTH_LONG).show();
                    session.setsat_frag(saturday);
                }
                if(!sunday.equals("[]")){
                    //Toast.makeText(context,"saturday "+text,Toast.LENGTH_LONG).show();
                    session.setsun_frag(sunday);
                }
            }
            View_load_dialog.hideDialog();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("order1", e.toString());
            View_load_dialog.hideDialog();
            // TODO Auto-generated catch block
            //Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    public String addorderapi() {
// Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        //HttpPost httppost = new HttpPost("https://www.eatanytime.in/eat_erp/index.php/Sales_rep_store_plan_mobile_app/get_array_api");

        try {
            // Add your data
            //you can add all the parameters your php needs in the BasicNameValuePair.
            //The first parameter refers to the name in the php field for example
            // $id=$_POST['id']; the second parameter is the value.


            HttpPost httppost = new HttpPost("https://www.eatanytime.in/eat_erp/index.php/Sales_rep_store_plan_mobile_app/add_order_api");


            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("mid", OrdersData.mid));
            nameValuePairs.add(new BasicNameValuePair("sales_rep_loc_id", OrdersData.sales_rep_loc_id));
            nameValuePairs.add(new BasicNameValuePair("merchandiser_stock_id", OrdersData.merchandiser_stock_id));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            return result;


        } catch (Exception e) {
            Log.e("orderdetails", "addapi: "+e.toString());
            // TODO Auto-generated catch block
            //Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
        }
        return "";
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            Log.e("visit", "returned");
            View_load_dialog.hideDialog();
        }
    }//
    @Override
    protected void onRestart(){
        super.onRestart();
        if(restartflag){
            View_load_dialog.showDialog();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(context, Visit_list_pageActivity.class);
                    startActivityForResult(i,1);
                    finish();
                }
            },150);
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

    private void add_event_in_db() {

        int bars, cookies, trailmix;
        if ((!OrdersData.order_orange_bar.equals("") && !OrdersData.order_orange_bar.equals("0")) || (!OrdersData.order_butterscotch_bar.equals("") && !OrdersData.order_butterscotch_bar.equals("0")) || (!OrdersData.order_chocopeanut_bar.equals("") && !OrdersData.order_chocopeanut_bar.equals("0")) || (!OrdersData.order_bambaiyachaat_bar.equals("") && !OrdersData.order_bambaiyachaat_bar.equals("0")) || (!OrdersData.order_mangoginger_bar.equals("") && !OrdersData.order_mangoginger_bar.equals("0")) || (!OrdersData.order_berry_blast_bar.equals("") && !OrdersData.order_berry_blast_bar.equals("0")) || (!OrdersData.order_chyawanprash_bar.equals("") && !OrdersData.order_chyawanprash_bar.equals("0"))||(!OrdersData.order_orange_box.equals("") && !OrdersData.order_orange_box.equals("0")) || (!OrdersData.order_butterscotch_box.equals("") && !OrdersData.order_butterscotch_box.equals("0")) || (!OrdersData.order_chocopeanut_box.equals("") && !OrdersData.order_chocopeanut_box.equals("0")) || (!OrdersData.order_bambaiyachaat_box.equals("") && !OrdersData.order_bambaiyachaat_box.equals("0")) || (!OrdersData.order_mangoginger_box.equals("") && !OrdersData.order_mangoginger_box.equals("0")) || (!OrdersData.order_berry_blast_box.equals("") && !OrdersData.order_berry_blast_box.equals("0")) || (!OrdersData.order_chyawanprash_box.equals("") && !OrdersData.order_chyawanprash_box.equals("0"))|| (!OrdersData.order_variety_box.equals("") && !OrdersData.order_variety_box.equals("0"))) {
            bars=0;
            if (!OrdersData.order_orange_bar.equals("") && !OrdersData.order_orange_bar.equals("0")&& OrdersData.order_orange_bar.matches("[0-9]+")) {
                bars+=Integer.parseInt(OrdersData.order_orange_bar);
            }
            if (!OrdersData.order_butterscotch_bar.equals("") && !OrdersData.order_butterscotch_bar.equals("0")&& OrdersData.order_butterscotch_bar.matches("[0-9]+")) {
                bars+=Integer.parseInt(OrdersData.order_butterscotch_bar);
            }
            if (!OrdersData.order_chocopeanut_bar.equals("") && !OrdersData.order_chocopeanut_bar.equals("0")&& OrdersData.order_chocopeanut_bar.matches("[0-9]+")) {
                bars+=Integer.parseInt(OrdersData.order_chocopeanut_bar);
            }
            if (!OrdersData.order_bambaiyachaat_bar.equals("") && !OrdersData.order_bambaiyachaat_bar.equals("0")&& OrdersData.order_bambaiyachaat_bar.matches("[0-9]+")) {
                bars+=Integer.parseInt(OrdersData.order_bambaiyachaat_bar);
            }
            if (!OrdersData.order_mangoginger_bar.equals("") && !OrdersData.order_mangoginger_bar.equals("0")&& OrdersData.order_mangoginger_bar.matches("[0-9]+")) {
                bars+=Integer.parseInt(OrdersData.order_mangoginger_bar);
            }
            if (!OrdersData.order_berry_blast_bar.equals("") && !OrdersData.order_berry_blast_bar.equals("0")&& OrdersData.order_berry_blast_bar.matches("[0-9]+")) {
                bars+=Integer.parseInt(OrdersData.order_berry_blast_bar);
            }
            if (!OrdersData.order_chyawanprash_bar.equals("") && !OrdersData.order_chyawanprash_bar.equals("0")&& OrdersData.order_chyawanprash_bar.matches("[0-9]+")) {
                bars+=Integer.parseInt(OrdersData.order_chyawanprash_bar);
            }
            if (!OrdersData.order_orange_box.equals("") && !OrdersData.order_orange_box.equals("0")&& OrdersData.order_orange_box.matches("[0-9]+")) {
                bars+=Integer.parseInt(OrdersData.order_orange_box)*6;
            }
            if (!OrdersData.order_butterscotch_box.equals("") && !OrdersData.order_butterscotch_box.equals("0")&& OrdersData.order_butterscotch_box.matches("[0-9]+")) {
                bars+=Integer.parseInt(OrdersData.order_butterscotch_box)*6;
            }
            if (!OrdersData.order_chocopeanut_box.equals("") && !OrdersData.order_chocopeanut_box.equals("0")&& OrdersData.order_chocopeanut_box.matches("[0-9]+")) {
                bars+=Integer.parseInt(OrdersData.order_chocopeanut_box)*6;
            }
            if (!OrdersData.order_bambaiyachaat_box.equals("") && !OrdersData.order_bambaiyachaat_box.equals("0")&& OrdersData.order_bambaiyachaat_box.matches("[0-9]+")) {
                bars+=Integer.parseInt(OrdersData.order_bambaiyachaat_box)*6;
            }
            if (!OrdersData.order_mangoginger_box.equals("") && !OrdersData.order_mangoginger_box.equals("0")&& OrdersData.order_mangoginger_box.matches("[0-9]+")) {
                bars+=Integer.parseInt(OrdersData.order_mangoginger_box)*6;
            }
            if (!OrdersData.order_berry_blast_box.equals("") && !OrdersData.order_berry_blast_box.equals("0")&& OrdersData.order_berry_blast_box.matches("[0-9]+")) {
                bars+=Integer.parseInt(OrdersData.order_berry_blast_box)*6;
            }
            if (!OrdersData.order_berry_blast_box.equals("") && !OrdersData.order_berry_blast_box.equals("0")&& OrdersData.order_berry_blast_box.matches("[0-9]+")) {
                bars+=Integer.parseInt(OrdersData.order_berry_blast_box)*6;
            }
            if (!OrdersData.order_chyawanprash_box.equals("") && !OrdersData.order_chyawanprash_box.equals("0")&& OrdersData.order_chyawanprash_box.matches("[0-9]+")) {
                bars+=Integer.parseInt(OrdersData.order_chyawanprash_box)*6;
            }
            if (!OrdersData.order_variety_box.equals("") && !OrdersData.order_variety_box.equals("0")&& OrdersData.order_variety_box.matches("[0-9]+")) {
                bars+=Integer.parseInt(OrdersData.order_variety_box)*6;
            }
        }else{bars=0;}

        if ((!OrdersData.order_cranberry_cookies_box.equals("") && !OrdersData.order_cranberry_orange_box.equals("0")) || (!OrdersData.order_dark_chocolate_cookies_box.equals("") && !OrdersData.order_dark_chocolate_cookies_box.equals("0")) || (!OrdersData.order_chocolate_cookies_box.equals("") && !OrdersData.order_chocolate_cookies_box.equals("0"))) {
            cookies=0;
            if (!OrdersData.order_chocolate_cookies_box.equals("") && !OrdersData.order_chocolate_cookies_box.equals("0")&& OrdersData.order_chocolate_cookies_box.matches("[0-9]+")) {
                cookies+=Integer.parseInt(OrdersData.order_chocolate_cookies_box);
            }
            if (!OrdersData.order_dark_chocolate_cookies_box.equals("") && !OrdersData.order_dark_chocolate_cookies_box.equals("0")&& OrdersData.order_dark_chocolate_cookies_box.matches("[0-9]+")) {
                cookies+=Integer.parseInt(OrdersData.order_dark_chocolate_cookies_box);
            }
            if (!OrdersData.order_cranberry_cookies_box.equals("") && !OrdersData.order_cranberry_cookies_box.equals("0")&& OrdersData.order_cranberry_cookies_box.matches("[0-9]+")) {
                cookies+=Integer.parseInt(OrdersData.order_cranberry_cookies_box);
            }
        }else{cookies=0;}

        if ((!OrdersData.order_cranberry_orange_box.equals("") && !OrdersData.order_cranberry_orange_box.equals("0")) || (!OrdersData.order_fig_raisins_box.equals("") && !OrdersData.order_fig_raisins_box.equals("0")) || (!OrdersData.order_papaya_pineapple_box.equals("") && !OrdersData.order_papaya_pineapple_box.equals("0"))) {
            trailmix=0;
            if (!OrdersData.order_fig_raisins_box.equals("") && !OrdersData.order_fig_raisins_box.equals("0")&& OrdersData.order_fig_raisins_box.matches("[0-9]+")) {
                trailmix+=Integer.parseInt(OrdersData.order_fig_raisins_box);
            }
            if (!OrdersData.order_cranberry_orange_box.equals("") && !OrdersData.order_cranberry_orange_box.equals("0")&& OrdersData.order_cranberry_orange_box.matches("[0-9]+")) {
                trailmix+=Integer.parseInt(OrdersData.order_cranberry_orange_box);
            }
            if (!OrdersData.order_papaya_pineapple_box.equals("") && !OrdersData.order_papaya_pineapple_box.equals("0")&& OrdersData.order_papaya_pineapple_box.matches("[0-9]+")) {
                trailmix+=Integer.parseInt(OrdersData.order_papaya_pineapple_box);
            }
        }else{trailmix=0;}

        try {
            String area="";
            String type="";
            Intent i=getIntent();
            if(i.hasExtra("type")){
                type=i.getStringExtra("type");
            }
            if(OrdersData.area!=null){
                area=OrdersData.area;
            }else if(OrdersData.reation!=null){
                area=OrdersData.location;
            }
            String channel=OrdersData.channel_type;
            Log.e("visit", "channel: "+channel);
            String date = new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());
            JSONObject jsonObject=new JSONObject();
            GPSTracker gpsTracker=new GPSTracker(context);
            jsonObject.put("latitude", gpsTracker.getLatitude()+"");
            jsonObject.put("longitude", gpsTracker.getLongitude()+"");
            jsonObject.put("distributor_name", OrdersData.distributor_name);
            jsonObject.put("area",area );
            jsonObject.put("type",type );
            Intent intent=getIntent();
            if(intent.hasExtra("flag")){
                if(intent.getIntExtra("flag", 0)==0){
                    Log.e("this", "should be what");
                jsonObject.put("new_or_old", "old");
                }
                else{
                    Log.e("order", "what is it: "+"new_old_"+channel.toLowerCase());
                    jsonObject.put("new_or_old", "new_old_"+channel.toLowerCase()); }
            }
            else if(intent.hasExtra("activity")){
                jsonObject.put("new_or_old", "new_new_gt");
            }else{
                jsonObject.put("new_or_old", "new_new_gt");
            }
            jsonObject.put("is_permanent", OrdersData.ispermenant);
            jsonObject.put("order", "Yes");
            jsonObject.put("order_bar",bars+"" );
            jsonObject.put("order_cookies",cookies+"" );
            jsonObject.put("order_trailmix",trailmix +"");
            String event_json=jsonObject.toString();

            OfflineDbHelper offlineDbHelper = new OfflineDbHelper(context);
            SQLiteDatabase db = offlineDbHelper.getWritableDatabase();
            if(intent.hasExtra("flag")){
                offlineDbHelper.addEVENT("Store visit", date, event_json, db);
                postActvityDetails("Store visit", date, event_json);
            }
            offlineDbHelper.addEVENT("Order placed", date, event_json, db);
            postActvityDetails("Order placed", date, event_json);

        }catch (Exception e)
        {
            Log.e("orderdetails_db", e.toString());
        }
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
            Log.e("order", "postactivitydetails: "+ex.toString());
            //Toast.makeText(context,"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        }
        finally {
            try {

                reader.close();
            } catch (Exception ex) {
                Log.e("order", "postactivitydetails2: "+ex.toString());
            }
        }
    }


}
