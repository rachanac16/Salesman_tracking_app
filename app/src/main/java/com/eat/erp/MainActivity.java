package com.eat.erp;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    View_load_dialog View_load_dialog;
    EditText txtusername;
    EditText txtpassword;
    TextView forget_psw;
    TextView textview21;
    Button btnsubmit;
    String Name, Email, Login, Pass;
    Context context;
    String response;
    private session session;
    private int vflag;
    boolean isNetwork=false;
    boolean isGPS=false;
    LocationManager locationManager;
    //Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));
        Typeface face= Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Demi.otf");
        context = MainActivity.this;
        View_load_dialog = new View_load_dialog(this);

        // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        session = new session(context);
        String emailid=session.getusername();
        if(emailid.equals("")) {

        }
        else {
            Intent i = new Intent(MainActivity.this,NavigationActivity.class);
            startActivity(i);
        }
        //button =findViewById(R.id.notify);
        txtusername = (EditText) findViewById(R.id.editText);
        txtpassword = (EditText) findViewById(R.id.editText2);
        btnsubmit = (Button) findViewById(R.id.button2);
        forget_psw =(TextView) findViewById(R.id.forget_pw);
        textview21 =(TextView) findViewById(R.id.textview21);
        textview21.setTypeface(face);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        forget_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,Forget_pswActivity.class);
                startActivity(i);

            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
                    isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    isNetwork = isNETWORK();
                    //Toast.makeText(context, "" + isGPS + isNetwork, Toast.LENGTH_LONG).show();
                    if ((!isGPS && !isNetwork)||(isGPS&&!isNetwork)||(!isGPS&&isNetwork)) {
                        showSettingsAlert();
                    } else {
                        btnsubmit.setBackgroundResource(R.drawable.border_onclick_solid);
                        btnsubmit.setTextColor(Color.WHITE);
                        String username = txtusername.getText().toString();
                        if (username.equals("")) {
                            btnsubmit.setBackgroundResource(R.drawable.border_solid_btn);
                            btnsubmit.setTextColor(Color.BLACK);

                            txtusername.setError("Email id is required");
                            vflag = 0;
                        } else {
                            vflag = 1;
                        }
                        if (!isValidEmail(username)) {
                            btnsubmit.setBackgroundResource(R.drawable.border_solid_btn);
                            btnsubmit.setTextColor(Color.BLACK);

                            txtusername.setError("Invalid Email");
                            vflag = 0;
                        } else {
                            vflag = 1;
                        }
                        if (txtpassword.getText().toString().equals("")) {
                            btnsubmit.setBackgroundResource(R.drawable.border_solid_btn);
                            txtpassword.setError("Password is required");
                            vflag = 0;
                        } else {
                            vflag = 1;
                        }
                        if (vflag != 0) {
                            View_load_dialog.showDialog();
                            //txtusername.setText("dhavalbright@gmail.com");
                            //txtpassword.setText("pass@123");

                            // CALL GetText method to make post method call
                            response = GetText();
                            JSONObject obj = new JSONObject(response);


                            String result = obj.getString("result");

                            // Toast.makeText(context,result,Toast.LENGTH_LONG).show();
                            if (result.equals("1")) {
                                JSONArray data = obj.getJSONArray("data");
                                JSONObject data1 = data.getJSONObject(0);

                                String email = data1.getString("email_id");
                                session.setUsername(email);

                                String session_id = data1.getString("id");
                                session.setSessionid(session_id);


                                String role_id = data1.getString("role_id");
                                session.setrole_id(role_id);

                                String sales_rep_id = data1.getString("sales_rep_id");
                                session.setsales_rep_id(sales_rep_id);

                                String sr_type = data1.getString("sr_type");
                                session.settype(sr_type);

                                String emp_code = data1.getString("emp_code");
                                session.setemp_code(emp_code);

                                String first_name = data1.getString("first_name");
                                session.setfirst_name(first_name);

                                String last_name = data1.getString("last_name");
                                session.setlast_name(last_name);

                                String check_in_time = data1.getString("check_in_time");
                                session.setcheck_in_time(check_in_time);

                                String check_out_time = data1.getString("check_out_time");
                                session.setcheck_out_time(check_out_time);


                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        Intent i = new Intent(MainActivity.this, NavigationActivity.class);
                                        startActivityForResult(i, 1);
                                        //finish();
                                        return;
                                    }
                                }, 500);


                            } else {
                                btnsubmit.setBackgroundResource(R.drawable.border_solid_btn);
                                btnsubmit.setTextColor(Color.BLACK);

                                String msg = obj.getString("msg");
                                Snackbar snackbar = Snackbar
                                        .make(findViewById(R.id.clmainactivity), msg, Snackbar.LENGTH_LONG);
                                View snackBarView = snackbar.getView();
                                snackBarView.setBackgroundColor(Color.BLACK);
                                TextView textView = (TextView) snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
                                textView.setTextColor(Color.LTGRAY);
                                snackbar.show();
                                View_load_dialog.hideDialog();
                            }
                        }

                    }
                }
                catch(Exception ex)
                    {
                        //content.setText(" url exeption! " );
                        //Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();

                }
            }
        });

        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                    NotificationChannel notificationChannel = new NotificationChannel("ID", "Name", importance);
                    notificationManager.createNotificationChannel(notificationChannel);
                    builder = new NotificationCompat.Builder(getApplicationContext(), notificationChannel.getId());
                } else {
                    builder = new NotificationCompat.Builder(getApplicationContext());
                }

                builder = builder
                        .setSmallIcon(R.drawable.logo2)
                        .setColor(ContextCompat.getColor(context, R.color.background))
                        .setContentTitle(context.getString(R.string.title_activity_dashboard))
                        .setContentText("whatver")
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true);
                notificationManager.notify(0, builder.build());
                Toast.makeText(context, "why this not working", Toast.LENGTH_SHORT).show();
            }
        });*/



    }

    public void showSettingsAlert() {

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        if(!isGPS) {
            alertDialog.setTitle("GPS is not Enabled!");
            alertDialog.setMessage("Do you want to turn on GPS?");
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });

        }
        else{
            alertDialog.setTitle("Network is not Enabled!");
            alertDialog.setMessage("Do you want to turn on Network?");
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
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
            });

        }

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                View_load_dialog.showDialog();

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //your method code
                        Intent i =new Intent(context,MainActivity.class);
                        startActivity(i);
                    }
                },50);
            }
        });

        alertDialog.show();
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }



    public String  GetText()  throws UnsupportedEncodingException
    {
        // Get user defined values
        //Name = fname.getText().toString();
        Email   = txtusername.getText().toString();
        //Login   = login.getText().toString();
        Pass   = txtpassword.getText().toString();

        // Create data variable for sent values to server

        String data = URLEncoder.encode("email", "UTF-8")
                + "=" + URLEncoder.encode(Email, "UTF-8");

        data += "&" + URLEncoder.encode("password", "UTF-8")
                + "=" + URLEncoder.encode(Pass, "UTF-8");

        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/login/check_credentials_api");

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
            Log.e("main", "f: "+ex.toString());
            //Toast.makeText(context,"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
        }
        finally
        {
            try
            {

                reader.close();
            }

            catch(Exception ex) {
                Log.e("main", "f:3 "+ex.toString());
            }
        }

        // Show response on activity
        return "";

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(View_load_dialog.isShowing()){
                 View_load_dialog.hideDialog();
            }
        }
    }

}




