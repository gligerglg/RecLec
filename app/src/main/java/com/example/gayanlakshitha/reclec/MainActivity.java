package com.example.gayanlakshitha.reclec;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button btn_settings;
    Button btn_today;
    Button btn_summery;
    Button btn_logout;

    String today;
    String lastday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_summery = (Button) findViewById(R.id.btn_summery);
        btn_settings = (Button) findViewById(R.id.btn_settings);
        btn_today = (Button) findViewById(R.id.btn_today);
        btn_logout = (Button) findViewById(R.id.btn_logout);


        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Settings.class));
            }
        });

        btn_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (today.equals(lastday))
                    Toast.makeText(getApplicationContext(), "You've Already Recorded your Attendance Today!", Toast.LENGTH_SHORT).show();
                else
                    startActivity(new Intent(MainActivity.this, Today_Attendence.class));
            }
        });

        btn_summery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Summery.class));
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences shared = getSharedPreferences("check", 0);
                SharedPreferences.Editor editor = shared.edit();
                editor.putBoolean("login", false);
                editor.commit();

                startActivity(new Intent(MainActivity.this, SignIn.class));
                finish();
            }
        });

        //Create Databases
        final SQLiteDatabase reclec_db = openOrCreateDatabase("reclec_db.db", MODE_PRIVATE, null);
        reclec_db.execSQL("CREATE TABLE IF NOT EXISTS tbl_Subjects(id INTEGER PRIMARY KEY AUTOINCREMENT,subject text,att_days int,tot_days int)");
        reclec_db.execSQL("CREATE TABLE IF NOT EXISTS tbl_days(id INTEGER PRIMARY KEY AUTOINCREMENT,day text,subject text)");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Calendar cal = Calendar.getInstance();
        today = "" + cal.get(Calendar.YEAR) + (cal.get(Calendar.MONTH) + 1) + cal.get(Calendar.DAY_OF_MONTH);
        SharedPreferences shared = getSharedPreferences("check", 0);
        lastday = shared.getString("today", "");
    }
}
