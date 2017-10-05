package com.example.gayanlakshitha.reclec;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Gayan Lakshitha on 9/15/2017.
 */

public class Additional_Lec extends Activity {

    private Spinner spin_subject;
    private Button btn_submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.additional_lec);

        spin_subject = (Spinner) findViewById(R.id.spin_subject_submit);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        final SQLiteDatabase db = openOrCreateDatabase("reclec_db.db", MODE_PRIVATE, null);

        //Fill Spinner
        ArrayList<String> array = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_Subjects", null);
        while (cursor.moveToNext())
            array.add(cursor.getString(1));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner, array);
        spin_subject.setAdapter(adapter);
        if (adapter.isEmpty())
            btn_submit.setEnabled(false);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = spin_subject.getSelectedItem().toString();
                Cursor cursor1 = db.rawQuery("SELECT * FROM tbl_Subjects WHERE subject='" + subject + "'", null);
                while (cursor1.moveToNext()) {
                    int tot_days = cursor1.getInt(cursor1.getColumnIndex("tot_days"));
                    int tot_att = cursor1.getInt(cursor1.getColumnIndex("att_days"));
                    tot_att++;
                    tot_days++;

                    try {
                        db.execSQL("UPDATE tbl_Subjects SET att_days=" + tot_att + ",tot_days=" + tot_days + " WHERE subject='" + subject + "'");
                        Toast.makeText(getApplicationContext(), "Subject Is Submitted Successfully!", Toast.LENGTH_SHORT).show();
                    } catch (SQLiteException e) {
                        Toast.makeText(getApplicationContext(), "Database Error!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
