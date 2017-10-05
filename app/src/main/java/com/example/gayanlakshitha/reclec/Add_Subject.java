package com.example.gayanlakshitha.reclec;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Gayan Lakshitha on 9/15/2017.
 */

public class Add_Subject extends Activity {

    private EditText txt_subject;
    private Spinner spin_days;
    private Button btn_add;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_subject);

        txt_subject = (EditText) findViewById(R.id.txt_subject);
        spin_days = (Spinner) findViewById(R.id.spin_days);
        btn_add = (Button) findViewById(R.id.btn_add);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.week_days, R.layout.spinner);
        spin_days.setAdapter(adapter);
        final SQLiteDatabase db = openOrCreateDatabase("reclec_db.db", MODE_PRIVATE, null);

        //

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(txt_subject.getText())) {
                    txt_subject.setError("Subject Field Cannot be Empty!");
                } else {
                    if (!CheckDay(txt_subject.getText().toString())) {
                        if (!CheckSubject(txt_subject.getText().toString())) {
                            //Add Subject to Subject Database
                            db.execSQL("INSERT INTO tbl_Subjects (subject,att_days,tot_days) VALUES('" + txt_subject.getText().toString() + "',0,0)");
                        }
                        //Add Subject to Days Database
                        db.execSQL("INSERT INTO tbl_days (day,subject) VALUES('" + spin_days.getSelectedItem().toString() + "','" + txt_subject.getText().toString() + "')");
                        Toast.makeText(getApplicationContext(), "Subject Added & Mapped Successfully", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), "Entry is Already Exists!", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    private boolean CheckDay(String subject) {
        SQLiteDatabase db = openOrCreateDatabase("reclec_db.db", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT subject FROM tbl_days WHERE day= '" + spin_days.getSelectedItem().toString() + "' AND subject='" + subject + "'", null);
        return cursor.moveToFirst();
    }

    private boolean CheckSubject(String subject) {
        SQLiteDatabase db = openOrCreateDatabase("reclec_db.db", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT subject FROM tbl_Subjects where subject='" + subject + "'", null);
        return cursor.moveToFirst();
    }

}
