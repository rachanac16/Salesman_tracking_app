package com.eat.erp;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Splash_screenActivity extends AppCompatActivity {
    Handler handler;
    Context context = Splash_screenActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try{
                    session session = new session(context);
                    String emailid=session.getusername();
                    if(emailid.equals("")) {
                        session.setmon_frag("");
                        session.settues_frag("");
                        session.setwed_frag("");
                        session.setthu_frag("");
                        session.setfri_frag("");
                        session.setsat_frag("");
                        session.setsun_frag("");
                        Intent intent=new Intent(Splash_screenActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        // Intent i = new Intent(Splash_screenActivity.this, NavigationActivity.class);
                        // startActivity(i);
                        // finish();
                        String check_in_time = session.getcheck_in_time();
                        String strDate1 = "";
                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        String strDate2 = sdf.format(cal.getTime());

                        if(!check_in_time.equals("null")&&!check_in_time.equals("")) {
                            SimpleDateFormat dateFormatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date date2 = dateFormatter2.parse(check_in_time);
                            strDate1 = sdf.format(date2);
                        }else{

                            Intent i = new Intent(Splash_screenActivity.this, NavigationActivity.class);
                            startActivity(i);
                            finish();
                        }

                        if(strDate1.equals(strDate2)){
                            Intent intent = new Intent(Splash_screenActivity.this, dashboardActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                            finish();
                        } else {
                            session.setmon_frag("");
                            session.settues_frag("");
                            session.setwed_frag("");
                            session.setthu_frag("");
                            session.setfri_frag("");
                            session.setsat_frag("");
                            session.setsun_frag("");
                            Intent i = new Intent(Splash_screenActivity.this, NavigationActivity.class);
                            startActivity(i);
                            finish();
                            }
                        }
                    }
                catch(Exception ex)
                {
                    Log.e("splash", "f: "+ex.toString());
                    Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
                }


            }
        },500);
    }
}
