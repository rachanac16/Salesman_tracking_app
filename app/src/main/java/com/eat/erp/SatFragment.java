package com.eat.erp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yalantis.phoenix.PullToRefreshView;

import org.json.JSONArray;
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
import java.util.Locale;


public class SatFragment extends Fragment implements
        BeatPlanAdapter.customButtonListener, BeatPlanAdapter.customButtonListener1, LocationListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    session session;
    ArrayList<BeatPlan> beatPlan;
    ArrayList<BeatPlan> beatPlan_followup;
    ArrayList<BeatPlan> beatPlan_mt_followup;
    ArrayList<BeatPlan> beatPlan_mt_visit;
    private static BeatPlanAdapter adapter;
    private static BeatPlanAdapter adapter_folloup;
    private static BeatPlanAdapter adapter_mt_folloup;
    private static BeatPlanAdapter adapter_mt_visit;
    String temp;
    ProgressDialog spinnerDialog;
    String sday;
    View_load_dialog View_load_dialog;

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
    String daddr, saddr;
    String total_count = "0", actual_count = "0", unplanned_count = "0", p_call = "0";
    PullToRefreshView mScrollView;


    public SatFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mon, container, false);
        session = new session(getActivity());
        ListView lv = view.findViewById(R.id.visit_list1);
        ListView lv_gt_followup = view.findViewById(R.id.follow_up1);
        ListView lv_mt_followup = view.findViewById(R.id.mt_follow_up1);
        ListView lv_mt_visit = view.findViewById(R.id.mt_visit1);
        TextView txtretailer = view.findViewById(R.id.txtretailer);
        TextView txtrouteplan = view.findViewById(R.id.txtrouteplan);
        View_load_dialog = new View_load_dialog(getActivity());
        txtretailer.setText(session.getdistributor_name());
        txtrouteplan.setText(session.getbeat_name());

        TextView txtvisits1, txtcalls1, txtvper1, txtcper1, txtucalls1;

        txtcalls1=view.findViewById(R.id.txtcalls1);
        txtvisits1=view.findViewById(R.id.txtvisits1);
        txtvper1=view.findViewById(R.id.txtvper1);
        txtcper1=view.findViewById(R.id.txtcper1);
        txtucalls1=view.findViewById(R.id.txtucalls1);


        FontChangeCrawler fontChanger = new FontChangeCrawler(getActivity().getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) view);
        LinearLayout gtvisit = view.findViewById(R.id.gtvisit);
        LinearLayout gtfollow_up = view.findViewById(R.id.gtfollow_up);
        LinearLayout mtfollow_up = view.findViewById(R.id.mtfollow_up);
        LinearLayout mtvisit = view.findViewById(R.id.mtvisit);
        LinearLayout day_route=view.findViewById(R.id.day_route);
        day_route.setVisibility(View.GONE);

        ScrollView scrollView = (ScrollView)view.findViewById(R.id.frag_scrollview);
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        scrollView.setFocusable(true);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });

        try {

            Calendar calendar0 = Calendar.getInstance();
            Date date0 = calendar0.getTime();
            final String t=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date0.getTime());
            Context context=getContext();
            day_route.setVisibility(View.GONE);
            if(t.equals("Saturday")||t.equals("Sunday")){
                day_route.setVisibility(View.VISIBLE);
            }


            mScrollView = (PullToRefreshView) view.findViewById(R.id.pull_to_refresh);
            mScrollView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
                @Override
                public void onRefresh() {

                            if(t.equals("Saturday")){try{

                                View_load_dialog.showDialog();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        try{
                                            String respdata = getdashdata();
                                            JSONArray jsonObject = new JSONArray(respdata);
                                            JSONObject obj1 = jsonObject.getJSONObject(0);
                                            unplanned_count = obj1.getString("unplanned_count");
                                            p_call = obj1.getString("p_call");
                                            actual_count = obj1.getString("actual_count");
                                            total_count = obj1.getString("total_count");
                                            OrdersData.unplanned_count=unplanned_count;
                                            OrdersData.p_call=p_call;
                                            OrdersData.actual_count=actual_count;
                                            OrdersData.total_count=total_count;
                                            Intent i=new Intent(getContext(), Visit_list_pageActivity.class);
                                            i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            startActivityForResult(i, 1);
                                            getActivity().overridePendingTransition(0, 0);
                                            getActivity().finish();

                                        }catch (Exception e){e.printStackTrace();View_load_dialog.hideDialog();}
                                    }
                                },500);

                            }catch (Exception e){
                                e.printStackTrace();
                            }}


                            mScrollView.setRefreshing(false);

                }
            });


            DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            String[] days = new String[7];
            String[] days2 = new String[7];
            for (int i = 0; i < 7; i++)
            {
                days[i] = format.format(calendar.getTime());
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                try {
                    SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
                    Date dt1 = format1.parse(days[i]);
                    DateFormat format2 = new SimpleDateFormat("dd");
                    String finalDay = format2.format(dt1);
                    days2[i] = finalDay;
                }
                catch (Exception ex){

                }
            }

            try {
                if(t.equals("Saturday")) {
                    if (OrdersData.total_count == null || OrdersData.actual_count == null || OrdersData.p_call == null || OrdersData.unplanned_count == null) {
                        String respdata = getdashdata();
                        JSONArray jsonObject = new JSONArray(respdata);
                        JSONObject obj1 = jsonObject.getJSONObject(0);
                        unplanned_count = obj1.getString("unplanned_count");
                        p_call = obj1.getString("p_call");
                        actual_count = obj1.getString("actual_count");
                        total_count = obj1.getString("total_count");
                    } else {
                        total_count = OrdersData.total_count;
                        unplanned_count = OrdersData.unplanned_count;
                        actual_count = OrdersData.actual_count;
                        p_call = OrdersData.p_call;
                    }
                }
                else{
                    OfflineDbHelper offlineDbHelper=new OfflineDbHelper(context);
                    SQLiteDatabase db=offlineDbHelper.getReadableDatabase();
                    String selection="DAY==?";
                    String selectionargs[]={"Saturday"};
                    String columns[]={OfflineDatabase.VisitData.DAY, OfflineDatabase.VisitData.ACTUAL_COUNT, OfflineDatabase.VisitData.UNPLANNED_COUNT,OfflineDatabase.VisitData.TOTAL_COUNT,OfflineDatabase.VisitData.P_CALL};
                    Cursor cursor=db.query(OfflineDatabase.VisitData.TABLE_NAME, columns,selection, selectionargs,null,null,null, "1");
                    if(!(cursor.getCount()<0)){
                        while(cursor.moveToNext()){
                            actual_count=cursor.getString(cursor.getColumnIndex(OfflineDatabase.VisitData.ACTUAL_COUNT));
                            unplanned_count=cursor.getString(cursor.getColumnIndex(OfflineDatabase.VisitData.UNPLANNED_COUNT));
                            total_count=cursor.getString(cursor.getColumnIndex(OfflineDatabase.VisitData.TOTAL_COUNT));
                            p_call=cursor.getString(cursor.getColumnIndex(OfflineDatabase.VisitData.P_CALL));

                        }
                    }
                }

                int actcnt = Integer.parseInt(actual_count.trim()) + Integer.parseInt(unplanned_count.trim());
                actual_count = "" + actcnt;
                int totalcnt = Integer.parseInt(total_count.trim()) + Integer.parseInt(unplanned_count.trim());
                total_count = "" + totalcnt;

                txtvisits1.setText(actual_count + " / " + total_count);
                txtcalls1.setText(p_call + " / " + actual_count);
                txtucalls1.setText(unplanned_count);


                int act_cnt = Integer.parseInt(actual_count.trim());
                int tot_cnt = Integer.parseInt(total_count.trim());
                int act_perc = Math.round(((float) act_cnt / (float) tot_cnt) * 100);
                txtvper1.setText("" + act_perc + "%");
                //Toast.makeText(context,""+act_perc,Toast.LENGTH_LONG).show();

                if (!p_call.equals("0") && !actual_count.equals("0")) {
                    int pcall = Integer.parseInt(p_call.trim());
                    int actp_cnt = Integer.parseInt(actual_count.trim());
                    int actp_perc = Math.round(((float) pcall / (float) actp_cnt) * 100);
                    txtcper1.setText("" + actp_perc + "%");
                    //Toast.makeText(context,""+actual_count,Toast.LENGTH_LONG).show();
                } else {
                    txtcper1.setText("0%");
                }
            }catch (Exception e){
                Log.e("sat", e.toString());
            }


            // response = getbeat(""+days2[1]);
            String response = "";
            response = session.getsat_frag();
            if(response.equals("")){
                response=getbeat(""+days2[1]);
                session.setsat_frag(response);
                // Toast.makeText(getContext(),"Response "+response,Toast.LENGTH_LONG).show();
            } else {
                // Toast.makeText(getContext(),"Session Response "+response,Toast.LENGTH_LONG).show();
            }


            //String response = getbeat(""+days2[5]);
            JSONObject obj = new JSONObject(response);

            String distributorname = obj.getString("distributor_name");
            String beatname = obj.getString("beat_name");
            txtretailer.setText(distributorname);
            txtrouteplan.setText(beatname);


            JSONArray data = obj.getJSONArray("data");
            JSONArray gt_followup = obj.getJSONArray("gt_followup");
            JSONArray mt_followup = obj.getJSONArray("mt_followup");
            JSONArray mt_visit = obj.getJSONArray("merchendizer");


            beatPlan= new ArrayList<>();
            beatPlan_followup = new ArrayList<>();
            beatPlan_mt_followup = new ArrayList<>();
            beatPlan_mt_visit = new ArrayList<>();

            Calendar calendar1 = Calendar.getInstance();
            int day = calendar1.get(Calendar.DAY_OF_WEEK);
            if(day==Calendar.SATURDAY) {
                sday="true";
            }
            else {
                sday="false";
            }

            if(data.length()>0) {
                gtvisit.setVisibility(View.VISIBLE);
                for (int i = 0; i < data.length(); i++) {
                    JSONObject data1 = data.getJSONObject(i);
                    String distributor_name = data1.getString("distributor_name");
                    String date_of_visit = data1.getString("date_of_visit");
                    String bit_plan_id = data1.getString("bit_plan_id");
                    String latitude = data1.getString("latitude");
                    String longitude = data1.getString("longitude");
                    String id ="";
                    String channel_type = "GT";
                    String follow_type = "";
                    if(date_of_visit.equals("null")) {
                        id = data1.getString("bit_id");
                        beatPlan.add(new BeatPlan("" + (i + 1), distributor_name,"Checkin",id,channel_type,follow_type,temp,sday, latitude, longitude));
                    }
                    else {
                        if(bit_plan_id.equals("0")) {
                            id = data1.getString("mid");
                            temp = "temp";
                        }
                        else {
                            temp = "";
                            id = data1.getString("bit_id");
                        }
                        beatPlan.add(new BeatPlan("" + (i + 1), distributor_name,"Edit",id,channel_type,follow_type,temp,sday, latitude, longitude));

                    }
                }
                adapter= new BeatPlanAdapter(beatPlan,getContext());
                adapter.setCustomButtonListner(this);
                adapter.setCustomButtonListner1(this);
                lv.setAdapter(adapter);
                lv.setScrollContainer(false);
            }
            else {
                gtvisit.setVisibility(View.GONE);
            }

            if(gt_followup.length()>0) {
                gtfollow_up.setVisibility(View.VISIBLE);
                for (int i = 0; i < gt_followup.length(); i++) {
                    JSONObject data1 = gt_followup.getJSONObject(i);
                    String distributor_name = data1.getString("distributor_name");
                    String is_visited = data1.getString("is_visited");
                    String id = data1.getString("id");
                    String channel_type = "GT";
                    String latitude = data1.getString("latitude");
                    String longitude = data1.getString("longitude");
                    String follow_type = "gt_followup";
                    if(is_visited.equals("null")) {
                        beatPlan_followup.add(new BeatPlan("" + (i + 1), distributor_name,"Checkin",id,channel_type,follow_type,temp,sday,latitude,longitude));
                    }
                    else {
                        beatPlan_followup.add(new BeatPlan("" + (i + 1), distributor_name,"Edit",id,channel_type,follow_type,temp,sday,latitude,longitude));
                    }
                }
                adapter_folloup= new BeatPlanAdapter(beatPlan_followup,getContext());
                adapter_folloup.setCustomButtonListner(this);
                adapter_folloup.setCustomButtonListner1(this);
                lv_gt_followup.setAdapter(adapter_folloup);
            }
            else {
                gtfollow_up.setVisibility(View.GONE);
            }

            if(mt_followup.length()>0) {
                mtfollow_up.setVisibility(View.VISIBLE);
                for (int i = 0; i < mt_followup.length(); i++) {
                    JSONObject data1 = mt_followup.getJSONObject(i);
                    String distributor_name = data1.getString("relation");
                    String location = data1.getString("location");
                    String is_visited = data1.getString("is_visited");
                    String id = data1.getString("id");
                    String channel_type = "MT";
                    String follow_type = "mt_followup";
                    String latitude = data1.getString("latitude");
                    String longitude = data1.getString("longitude");
                    if(is_visited.equals("null")) {
                        beatPlan_mt_followup.add(new BeatPlan("" + (i + 1), distributor_name+"-("+location+")","Checkin",id,channel_type,follow_type,temp,sday,latitude,longitude));
                    }
                    else {
                        beatPlan_mt_followup.add(new BeatPlan("" + (i + 1), distributor_name+"-("+location+")","Edit",id,channel_type,follow_type,temp,sday,latitude,longitude));
                    }
                }
                adapter_mt_folloup= new BeatPlanAdapter(beatPlan_mt_followup,getContext());
                adapter_mt_folloup.setCustomButtonListner(this);
                adapter_mt_folloup.setCustomButtonListner1(this);
                lv_mt_followup.setAdapter(adapter_mt_folloup);
            }
            else {
                mtfollow_up.setVisibility(View.GONE);
            }

            if(mt_visit.length()>0) {
                mtvisit.setVisibility(View.VISIBLE);
                for (int i = 0; i < mt_visit.length(); i++) {
                    JSONObject data1 = mt_visit.getJSONObject(i);
                    String distributor_name = data1.getString("store_name");
                    String location = data1.getString("location");
                    String date_of_visit = data1.getString("date_of_visit");
                    String dist = data1.getString("dist_id");
                    String id = data1.getString("id");
                    String bit_plan_id = data1.getString("bit_plan_id");
                    String channel_type = "MT";
                    String follow_type = "";
                    String latitude = data1.getString("latitude");
                    String longitude = data1.getString("longitude");
                    if(date_of_visit.equals("null") && dist.equals("null")) {
                        id = data1.getString("bit_id");
                        beatPlan_mt_visit.add(new BeatPlan("" + (i + 1), distributor_name+"-("+location+")","Checkin",id,channel_type,follow_type,temp,sday,latitude,longitude));
                    }
                    else {
                        if(bit_plan_id.equals("0")) {
                            id = data1.getString("mid");
                            temp = "temp";
                        }
                        else {
                            temp = "";
                            id = data1.getString("bit_id");
                        }
                        beatPlan_mt_visit.add(new BeatPlan("" + (i + 1), distributor_name+"-("+location+")","Edit",id,channel_type,follow_type,temp,sday,latitude,longitude));
                    }
                }
                adapter_mt_visit= new BeatPlanAdapter(beatPlan_mt_visit,getContext());
                adapter_mt_visit.setCustomButtonListner(this);
                adapter_mt_visit.setCustomButtonListner1(this);
                lv_mt_visit.setAdapter(adapter_mt_visit);
            }
            else {
                mtvisit.setVisibility(View.GONE);
            }
        }
        catch (Exception ex) {
            //Toast.makeText(getContext(),ex.toString(),Toast.LENGTH_LONG).show();
        }

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onButtonClickListner(final View convertView, int position, final String value) {
        View_load_dialog.showDialog();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                //your method code

                View v = convertView;
                TextView txtid = (TextView) v.findViewById(R.id.txtid);
                TextView txtcheckin = (TextView) v.findViewById(R.id.txtcheckin);
                TextView txtchanneltype = (TextView) v.findViewById(R.id.txtchanneltype);
                TextView txtfollowtype = (TextView) v.findViewById(R.id.txtfollowtype);
                TextView txttemp = (TextView) v.findViewById(R.id.txttemp);

                //Toast.makeText(getContext(),txtchanneltype.getText().toString(),Toast.LENGTH_LONG).show();
                // if (value.toUpperCase().equals("CHECKIN")) {
                if (txtcheckin.getText().toString().toUpperCase().equals("CHECKIN")) {
                    Intent myIntent = new Intent(getContext(), Visit_detailsActivity.class);
                    myIntent.putExtra("id", txtid.getText().toString());
                    myIntent.putExtra("channel_type", txtchanneltype.getText().toString());
                    myIntent.putExtra("follow_type", txtfollowtype.getText().toString());
                    myIntent.putExtra("frequency", "Tuesday");
                    myIntent.putExtra("type", "add");
                    myIntent.putExtra("temp", txttemp.getText().toString());
                    startActivityForResult(myIntent,1);
                } else {
                    Intent myIntent = new Intent(getContext(), Visit_detailsActivity.class);
                    myIntent.putExtra("id", txtid.getText().toString());
                    myIntent.putExtra("channel_type", txtchanneltype.getText().toString());
                    myIntent.putExtra("follow_type", txtfollowtype.getText().toString());
                    myIntent.putExtra("frequency", "Tuesday");
                    myIntent.putExtra("temp", txttemp.getText().toString());
                    if (txtfollowtype.getText().toString().equals("gt_followup") || txtfollowtype.getText().toString().equals("mt_followup")) {
                        myIntent.putExtra("type", "add");
                    } else {
                        myIntent.putExtra("type", "edit");
                    }
                    //Toast.makeText(getContext(),"temp: "+temp+"\nid: "+txtid.getText().toString(),Toast.LENGTH_LONG).show();
                    startActivityForResult(myIntent,1);
                }
                getActivity().finish();
                return;
            }
        },2000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            View_load_dialog.hideDialog();
            getActivity().finish();
        }
    }//


    public String  getbeat(String day)  throws UnsupportedEncodingException
    {
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        String sales_rep_id=session.getsales_rep_id();
        String data = URLEncoder.encode("sales_rep_id", "UTF-8")
                + "=" + URLEncoder.encode(sales_rep_id, "UTF-8");

        data += "&" + URLEncoder.encode("frequency", "UTF-8")
                + "=" + URLEncoder.encode("Saturday", "UTF-8");

        data += "&" + URLEncoder.encode("temp_date", "UTF-8")
                + "=" + URLEncoder.encode(day, "UTF-8");


        String text = "";
        BufferedReader reader=null;

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

    @Override
    public void onButtonClickListner1(final View convertView, int position, final String value) {
        //View_load_dialog.showDialog();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //your method code
                // Intent myIntent = new Intent(getContext(), MapsActivity.class);
                // startActivity(myIntent);

                TextView txtlat = (TextView) convertView.findViewById(R.id.txtlat);
                TextView txtlong = (TextView) convertView.findViewById(R.id.txtlong);
                daddr = txtlat.getText().toString()+","+txtlong.getText().toString();
                saddr = "0,0";

                locationManager = (LocationManager) getActivity().getSystemService(Service.LOCATION_SERVICE);

                isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
                permissionsToRequest = findUnAskedPermissions(permissions);

                if (!isGPS && !isNetwork) {
                    // showSettingsAlert();
                    // getLastLocation();
                } else {

                    // check permissions
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (permissionsToRequest.size() > 0) {
                            requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
                            canGetLocation = false;
                        }
                    }

                    saddr = getLocation();
                    //  Toast.makeText(getContext(),saddr,Toast.LENGTH_LONG).show();
                }

                String uri = "http://maps.google.com/maps?daddr="+daddr;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivityForResult(intent,1);
                //View_load_dialog.hideDialog();
                // if (mapIntent.resolveActivity(getPackageManager()) != null) {
                // startActivity(mapIntent);
                // }
                //return;
            }
        },2000);
    }

    private String getLocation() {
        try {
            if (canGetLocation) {
                String strLat="0", strLong="0";
                if (isNetwork) {
                    // from GPS
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (loc != null) {
                            // Toast.makeText(getContext(),"second "+loc.getLatitude(),Toast.LENGTH_LONG).show();
                            strLat = Double.toString(loc.getLatitude());
                            strLong = Double.toString(loc.getLongitude());
                        } else {
                            GPSTracker gps=new GPSTracker(getContext());
                            strLat=gps.getLatitude()+"";
                            strLong=gps.getLongitude()+"";
                            // Toast.makeText(getContext(),"second error",Toast.LENGTH_LONG).show();
                        }
                    }

                } else if (isGPS) {
                    // from Network Provider
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (loc != null) {
                            // Toast.makeText(getContext(),"first "+loc.getLatitude(),Toast.LENGTH_LONG).show();
                            strLat = Double.toString(loc.getLatitude());
                            strLong = Double.toString(loc.getLongitude());
                        } else {
                            GPSTracker gps=new GPSTracker(getContext());
                            strLat=gps.getLatitude()+"";
                            strLong=gps.getLongitude()+"";
                            // Toast.makeText(getContext(),"first error",Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    loc.setLatitude(0);
                    loc.setLongitude(0);
                    if (loc != null) {
                        // Toast.makeText(getContext(),"third "+loc.getLatitude(),Toast.LENGTH_LONG).show();
                        strLat = Double.toString(loc.getLatitude());
                        strLong = Double.toString(loc.getLongitude());
                    }
                }

                return strLat+","+strLong;
            }
        } catch (SecurityException e) {
            //Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
        }

        return "0,0";
    }

    @Override
    public void onLocationChanged(Location location) {
        //updateUI(location);
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
                return (getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
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
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
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
                    }
                } else {
                    canGetLocation = true;
                    getLocation();
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {

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

   /* public class MyAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            View_load_dialog.showDialog();
        }
        @Override
        protected String doInBackground(String... params) {
            try{
                response = getbeat(params[0]);
            }catch(Exception e){
                Log.e("sat_frag", e.toString());
            }
            return "";
        }

        @Override
        protected void onProgressUpdate(Integer ... progress) {

        }

        @Override
        protected void onPostExecute(String result) {
            View_load_dialog.hideDialog();
        }
    }
*/

}
