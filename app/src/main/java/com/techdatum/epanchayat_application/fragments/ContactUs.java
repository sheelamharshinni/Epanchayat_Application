package com.techdatum.epanchayat_application.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.techdatum.epanchayat_application.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ContactUs extends AppCompatActivity {
    TextView c_time, tv_c_psname;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    String formattedDate,s_Ps_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);


        c_time = findViewById(R.id.tv_acci_time);
        tv_c_psname = findViewById(R.id.tv_accic_psname);
        CountDownTimer newtimer = new CountDownTimer(1000000000, 1000) {
            public void onTick(long millisUntilFinished) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                formattedDate = df.format(c.getTime());
                c_time.setText(formattedDate);
            }

            public void onFinish() {
            }
        };
        newtimer.start();
        SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        s_Ps_name = bb.getString("Psname", "");
        tv_c_psname.setText(s_Ps_name);

    }

}
