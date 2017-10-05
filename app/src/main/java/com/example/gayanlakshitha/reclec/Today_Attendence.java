package com.example.gayanlakshitha.reclec;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Gayan Lakshitha on 9/15/2017.
 */

public class Today_Attendence extends Activity {

    ListView lst_subject;
    Button btn_submit;
    Button btn_additional;
    String subject;
    int tot_att, tot_days;

    //Get Current Day of Week
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    String day = sdf.format(date);
    String today;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today_attendance);

        lst_subject = (ListView) findViewById(R.id.lst_today_subject);
        btn_additional = (Button) findViewById(R.id.btn_additional_lec);
        btn_submit = (Button) findViewById(R.id.btn_submit_today);
        LinearLayout layout = (LinearLayout) findViewById(R.id.background);

        ArrayList<String> array = new ArrayList<String>();
        final SQLiteDatabase db = openOrCreateDatabase("reclec_db.db", MODE_PRIVATE, null);
        final Cursor cursor = db.rawQuery("SELECT * FROM tbl_days WHERE day='" + day + "'", null);
        final ArrayAdapter<String> adapter;

        Calendar cal = Calendar.getInstance();
        today = "" + cal.get(Calendar.YEAR) + (cal.get(Calendar.MONTH) + 1) + cal.get(Calendar.DAY_OF_MONTH);

        //Load today Subjects
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            array.add(cursor.getString(cursor.getColumnIndex("subject")));
            layout.setBackgroundResource(R.drawable.xhdpi_no_lec);
        }
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, array);
        lst_subject.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lst_subject.setAdapter(adapter);
        if (adapter.isEmpty()) {
            btn_submit.setEnabled(false);
            layout.setBackgroundResource(R.drawable.xhdpi_no_lec);
        } else
            layout.setBackgroundResource(R.drawable.xhdpi_empty);

        btn_additional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Today_Attendence.this, Additional_Lec.class));
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Cursor cursor_day = db.rawQuery("SELECT * FROM tbl_days WHERE day='" + day + "'", null);
                    while (cursor_day.moveToNext()) {
                        String sub = cursor_day.getString(cursor_day.getColumnIndex("subject"));
                        Cursor cursor_sub = db.rawQuery("SELECT * FROM tbl_Subjects WHERE subject='" + sub + "'", null);
                        while (cursor_sub.moveToNext()) {
                            tot_days = cursor_sub.getInt(cursor_sub.getColumnIndex("tot_days"));
                            tot_days++;
                            db.execSQL("UPDATE tbl_Subjects SET tot_days=" + tot_days + " WHERE subject='" + sub + "'");
                        }
                    }

                    SparseBooleanArray checkedarr = lst_subject.getCheckedItemPositions();
                    for (int i = 0; i < checkedarr.size(); i++) {
                        if (checkedarr.valueAt(i)) {
                            subject = adapter.getItem(checkedarr.keyAt(i));
                            Cursor cursor1 = db.rawQuery("SELECT * FROM tbl_Subjects WHERE subject='" + subject + "'", null);

                            while (cursor1.moveToNext()) {

                                tot_att = cursor1.getInt(cursor1.getColumnIndex("att_days"));
                                tot_att++;


                                try {
                                    db.execSQL("UPDATE tbl_Subjects SET att_days=" + tot_att + " WHERE subject='" + subject + "'");
                                } catch (SQLiteException e) {
                                }
                            }
                        }
                    }

                    Toast.makeText(getApplicationContext(), "Today Attendance Submitted Successfully!", Toast.LENGTH_SHORT).show();
                    btn_submit.setEnabled(false);

                    //Update Shared Preferences
                    SharedPreferences shared = getSharedPreferences("check", 0);
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString("today", today);
                    editor.commit();
                    System.out.println("" + today);

                } catch (SQLiteException e) {
                    Toast.makeText(getApplicationContext(), "Database Error!", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
