package com.eat.erp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Follow_upActivity extends AppCompatActivity {
    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up);

        Toast.makeText(Follow_upActivity.this,"clicked",Toast.LENGTH_LONG).show();
        txtDate = (EditText) findViewById(R.id.follow_up_date);






    }



}
