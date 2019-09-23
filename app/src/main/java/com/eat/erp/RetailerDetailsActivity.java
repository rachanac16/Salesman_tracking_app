package com.eat.erp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class RetailerDetailsActivity extends AppCompatActivity {
    session session;
    Context context;
    MenuItem nav_checkintime;
    MenuItem nav_checkouttime;
    TextView txtretailer, txtgstnumber, txtarea, txtzone, txtlocation, txtmarginonmrp, txtremarks, txttitle;
    Button btnsave, cancel_btn;
    public static Activity fa;

    View_load_dialog View_load_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_details);
        View_load_dialog = new View_load_dialog(this);
        context = RetailerDetailsActivity.this;
        fa = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        Typeface face= Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Demi.otf");
        fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));


        txttitle = (TextView) findViewById(R.id.txttitle);
        txttitle.setText("Retailer");

        TextView todaydate = (TextView) findViewById(R.id.todaydate);
        Calendar c=Calendar.getInstance();
        String dayNumberSuffix = dashboardActivity.getDayNumberSuffix(c.get(Calendar.DAY_OF_MONTH));
        Date date3 = Calendar.getInstance().getTime();
        SimpleDateFormat timeFormatter3 = new SimpleDateFormat("dd'"+dayNumberSuffix+"' MMM yyyy");
        String time3 = timeFormatter3.format(date3);
        todaydate.setText(time3+ ", Today");

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
        Intent intent=getIntent();
        intent.hasExtra("Retailer_name");
        String retailer_name = intent.getStringExtra("Retailer_name");


        txtretailer = (EditText) findViewById(R.id.retailer);
        txtretailer.setText(retailer_name);
        txtgstnumber = (EditText) findViewById(R.id.gst_number);
        txtzone = (TextView) findViewById(R.id.zone);
        txtarea = (TextView) findViewById(R.id.area);
        txtlocation = (TextView) findViewById(R.id.location);
        txtmarginonmrp = (EditText) findViewById(R.id.margin_on_mrp);
        txtremarks = (EditText) findViewById(R.id.remarks);

        btnsave = (Button) findViewById(R.id.btnsave);
        btnsave.setTypeface(face);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);
        cancel_btn.setTypeface(face);

        String emailid = session.getusername();
        if (emailid.equals("")) {
            Intent i = new Intent(context, MainActivity.class);
            startActivity(i);
        } else {
            String channel_type = OrdersData.channel_type;
            txtretailer.setText(OrdersData.distributor_name);
            txtzone.setText(OrdersData.zone);
            txtarea.setText(OrdersData.area);
            txtlocation.setText(OrdersData.location);
            String resp = "";
            String margin = "";
            JSONObject jsonObject = null;
            String remarks = "";
            String gst_number = "";
            try {
                resp = getaddsalesrepdistributorapi(OrdersData.distributor_id);
                jsonObject = new JSONObject(resp);
                margin = jsonObject.getString("margin");
                remarks = jsonObject.getString("remarks");
                gst_number = jsonObject.getString("gst_number");
            }
            catch (Exception ex) {
                //Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
            }


            if (OrdersData.margin != null) {
                //Toast.makeText(context,OrdersData.margin,Toast.LENGTH_LONG).show();
                if (OrdersData.margin.equals("") || OrdersData.margin.equals(null) || OrdersData.margin.equals("null")) {
                    if (!margin.equals("null")) {
                        txtmarginonmrp.setText(margin);
                    }
                } else {
                    txtmarginonmrp.setText(OrdersData.margin);
                }
            }


            if (OrdersData.retailer_remarks != null) {
                if (OrdersData.retailer_remarks.equals("") || OrdersData.retailer_remarks.equals(null) || OrdersData.retailer_remarks.equals("null")) {
                    if (!remarks.equals("null")) {
                        txtremarks.setText(remarks);
                    }
                } else {
                    txtremarks.setText(OrdersData.retailer_remarks);
                }
            }

            if (OrdersData.gst_number != null) {
                if (OrdersData.gst_number.equals("") || OrdersData.gst_number.equals(null) || OrdersData.gst_number.equals("null")) {
                    if (!gst_number.equals("null")) {
                        txtgstnumber.setText(gst_number);
                    }
                } else {
                    txtgstnumber.setText(OrdersData.gst_number);
                }
            }
        }


        View view;

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrdersData.distributor_name = txtretailer.getText().toString();
                OrdersData.gst_number = txtgstnumber.getText().toString();
                OrdersData.margin = txtmarginonmrp.getText().toString();
                OrdersData.retailer_remarks = txtremarks.getText().toString();
                if (OrdersData.distributor_name.equals("") || OrdersData.margin.equals("")) {
                    if (OrdersData.distributor_name.equals("")) {
                        txtretailer.setError("Required");
                    }
                    else {
                        txtmarginonmrp.setError("Required");
                    }
                }
                else {
                    Intent i=getIntent();
                    String type=i.getStringExtra("type");
                    Intent intent = new Intent(context, OrderDetailsActivity.class);
                    intent.putExtra("type", type);
                    intent.putExtra("activity", "retailer");
                    startActivity(intent);
                }
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    public void onBackPressed() {
        OrdersData.distributor_name = txtretailer.getText().toString();
        OrdersData.gst_number = txtgstnumber.getText().toString();
        OrdersData.margin = txtmarginonmrp.getText().toString();
        OrdersData.retailer_remarks = txtremarks.getText().toString();
        finish();
    }

    public String getaddsalesrepdistributorapi(String store_id)  throws UnsupportedEncodingException
    {
        // Get user defined values
        //Name = fname.getText().toString();

        // Create data variable for sent values to server

        String sales_rep_id = session.getsales_rep_id();
        String distributor_id = session.getdistributor_id();
        String bit_id = session.getbeat_id();


        String data = "&" + URLEncoder.encode("distributor_id", "UTF-8")
                + "=" + URLEncoder.encode(store_id, "UTF-8");


        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/Sales_rep_store_plan_mobile_app/add_sales_rep_distributor_api");

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
}
