package com.eat.erp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Check_attendanceActivity extends AppCompatActivity {

    Context context;
    MenuItem nav_checkintime;
    MenuItem nav_checkouttime;
    TextView txtname, txtinitial, txtworking;
    private session session;
    String response;
    Typeface face;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_attendance);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));
        face = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Demi.otf");
        context=Check_attendanceActivity.this;
        session = new session(context);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);*/

        /*session = new session(context);
        Menu menu = navigationView.getMenu();
        nav_checkintime = menu.findItem(R.id.nav_checkintime);
        nav_checkouttime = menu.findItem(R.id.nav_checkouttime);
*/
        txtname = (TextView) findViewById(R.id.textView1);
        txtinitial = (TextView) findViewById(R.id.txtinitial);
        txtworking = (TextView) findViewById(R.id.txtworking);

        TextView todaydate = (TextView) findViewById(R.id.todaydate);
        Calendar c=Calendar.getInstance();
        String dayNumberSuffix = dashboardActivity.getDayNumberSuffix(c.get(Calendar.DAY_OF_MONTH));
        Date date3 = Calendar.getInstance().getTime();
        SimpleDateFormat timeFormatter3 = new SimpleDateFormat("dd'"+dayNumberSuffix+"' MMM yyyy");
        String time3 = timeFormatter3.format(date3);
        todaydate.setText(time3+ ", Today");

        String emailid=session.getusername();
        if(emailid.equals("")) {
            Intent i = new Intent(context,MainActivity.class);
            startActivity(i);
        }
        else {
            try {

                response = GetText();

                JSONObject obj = new JSONObject(response);
                JSONArray data = obj.getJSONArray("data");
                JSONObject data1 = data.getJSONObject(0);
                String working_status = data1.getString("working_status");
                String first_name = session.getfirst_name();
                String last_name = session.getlast_name();
                Toast.makeText(context,first_name,Toast.LENGTH_LONG).show();
                String initial = first_name.substring(0, 1);
                txtname.setText(first_name + " " + last_name);
                txtinitial.setText(initial.toUpperCase());
                if(working_status.equals("Absent")) {

                    /*String checkintime = session.getcheck_in_time();
                    String checkouttime = session.getcheck_out_time();


                    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = dateFormatter.parse(checkintime);

                    SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a");
                    String time = timeFormatter.format(date);


                    SimpleDateFormat dateFormatter_checkout = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date_checkout = dateFormatter_checkout.parse(checkouttime);

                    SimpleDateFormat timeFormatter_checkout = new SimpleDateFormat("hh:mm a");
                    String time_checkout = timeFormatter_checkout.format(date_checkout);*/


                    /*nav_checkintime.setTitle("Check In Time (" + time + ")");
                    nav_checkouttime.setTitle("Check Out Time (" + time_checkout + ")");*/
                }
                else {
                    Log.e("check to nav", "chek to cj");
                    Intent i = new Intent(context,NavigationActivity.class);
                    startActivity(i);
                }


            }
            catch (Exception ex) {
                Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
            }
        }

        txtworking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.e("check ","check to nav5");
                    Savetext("","","");
                    Intent i = new Intent(context,NavigationActivity.class);
                    startActivity(i);


                    Calendar c1=Calendar.getInstance();
                    String dayNumberSuffix = dashboardActivity.getDayNumberSuffix(c1.get(Calendar.DAY_OF_MONTH));
                    Date date12 = Calendar.getInstance().getTime();
                    SimpleDateFormat timeFormatter1 = new SimpleDateFormat("dd'"+dayNumberSuffix+"' MMM yyyy");
                    String time12 = timeFormatter1.format(date12);

                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("date", time12);
                    GPSTracker gpsTracker=new GPSTracker(context);
                    jsonObject.put("latitude", gpsTracker.getLatitude()+"");
                    jsonObject.put("longitude", gpsTracker.getLongitude()+"");
                    OfflineDbHelper offlineDbHelper=new OfflineDbHelper(context);
                    SQLiteDatabase db=offlineDbHelper.getWritableDatabase();
                    String date = new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());

                    offlineDbHelper.addEVENT("Working again", date,jsonObject.toString() , db);
                    postActivityDetails("Working again", date,jsonObject.toString());



                }
                catch (Exception ex) {

                }
            }
        });
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

    /*@Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_checkintime) {

        } else if (id == R.id.nav_checkouttime) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //finish();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

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

    public String Savetext(String working_status_save, String applied_on_keka_save, String casual_reason_save)  throws UnsupportedEncodingException
    {
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        String sales_rep_id=session.getsales_rep_id();
        String working_status=working_status_save;
        String stringLatitude="";
        String stringLongitude="";
        String applied_on_keka=applied_on_keka_save;
        String casual_reason=casual_reason_save;
        GPSTracker gpsTracker = new GPSTracker(context);
        if (gpsTracker.getIsGPSTrackingEnabled())
        {
            stringLatitude = String.valueOf(gpsTracker.latitude);

            stringLongitude = String.valueOf(gpsTracker.longitude);
            //Toast.makeText(context,stringLatitude,Toast.LENGTH_LONG).show();
        }
        else {
            gpsTracker.showSettingsAlert();
            Toast.makeText(context,"no",Toast.LENGTH_LONG).show();
        }

        String data = URLEncoder.encode("working_status", "UTF-8")
                + "=" + URLEncoder.encode(working_status, "UTF-8");

        data += "&" + URLEncoder.encode("sales_rep_id", "UTF-8")
                + "=" + URLEncoder.encode(sales_rep_id, "UTF-8");

        data += "&" + URLEncoder.encode("casual_reason", "UTF-8")
                + "=" + URLEncoder.encode(casual_reason, "UTF-8");

        data += "&" + URLEncoder.encode("applied_on_keka", "UTF-8")
                + "=" + URLEncoder.encode(applied_on_keka, "UTF-8");

        data += "&" + URLEncoder.encode("lattitude", "UTF-8")
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
}
