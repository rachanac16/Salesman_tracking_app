package com.eat.erp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Forget_pswActivity extends AppCompatActivity {
    EditText recovery_email;
    Button getEmail;
    Typeface face;
    View_load_dialog View_load_dialog;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_psw);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        face = Typeface.createFromAsset(getAssets(), "AvenirNextLTPro-Demi.otf");
        View_load_dialog =new View_load_dialog(this);
        recovery_email = findViewById(R.id.recovery_email);
        getEmail = findViewById(R.id.email_submit);
        context=Forget_pswActivity.this;

        getEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recovery_email.getText().toString().equals("")) {
                    recovery_email.setError("Required");
                } else if (!isValidEmailFormat(recovery_email.getText().toString())) {

                    recovery_email.setError("Invalid Email");
                } else {
                    try {
                        if (!isValidEmail()) {


                            recovery_email.setError("Email doesn't exist");
                        } else {
                            showPopUp();
                        }
                    }
                    catch (Exception e){

                    }
                }

            }
        });


    }



    public void displayExceptionMessage(String msg){

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    public boolean isValidEmailFormat(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void showPopUp() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.alertdialog_forgot_psw, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).setIcon(R.drawable.shape).create();
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "AvenirNextLTPro-Regular.otf");
        fontChanger.replaceFonts((ViewGroup) deleteDialogView);
        deleteDialog.setView(deleteDialogView);
        deleteDialogView.findViewById(R.id.btn_okay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    deleteDialog.dismiss();
                    View_load_dialog.showDialog();

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            Intent i = new Intent(context, MainActivity.class);
                            startActivityForResult(i, 1);
                            //finish();
                            return;
                        }
                    },2000);

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
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); }

    public boolean isValidEmail(){
        try {
            String response = GetText();
            if (response.contains("Valid")) {
                return true;
            }
            else{
                return false;
            }
        }catch(Exception e){

        }
        return false;
    }


    public String GetText()  throws UnsupportedEncodingException
        {
            String email_id= recovery_email.getText().toString();

            String data = URLEncoder.encode("email", "UTF-8")
                    + "=" + URLEncoder.encode(email_id, "UTF-8");

            String text = "";
            BufferedReader reader=null;
            // Send data
            try
            {
                // Defined URL  where to send data
                URL url = new URL("https://www.eatanytime.in/eat_erp/index.php/login/send_password_email_api");
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
                Toast.makeText(this,"exception \n"+ex.toString(),Toast.LENGTH_LONG).show();
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



