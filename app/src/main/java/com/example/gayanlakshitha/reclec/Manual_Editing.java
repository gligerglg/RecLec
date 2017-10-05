package com.example.gayanlakshitha.reclec;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Gayan Lakshitha on 9/15/2017.
 */

public class Manual_Editing extends Activity {

    private Spinner spn_subject;
    private EditText txt_att;
    private EditText txt_tot;
    private Button btn_Update;
    private int tot_days, tot_att, id, count = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_editing);

        spn_subject = (Spinner) findViewById(R.id.spin_subjects);
        txt_att = (EditText) findViewById(R.id.txt_att_update);
        txt_tot = (EditText) findViewById(R.id.txt_tot_update);
        btn_Update = (Button) findViewById(R.id.btn_update);
        final SQLiteDatabase reclec_db = openOrCreateDatabase("reclec_db.db", MODE_PRIVATE, null);

        //Fill Spiner By Subjects
        Cursor cursor = reclec_db.rawQuery("SELECT * FROM tbl_Subjects", null);
        ArrayList<String> array = new ArrayList<String>();
        while (cursor.moveToNext()) {
            array.add(cursor.getString(cursor.getColumnIndex("subject")));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner, array);
        spn_subject.setAdapter(adapter);
        if (adapter.isEmpty())
            btn_Update.setEnabled(false);

        spn_subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long ipd) {
                Cursor cursor = reclec_db.rawQuery("SELECT * FROM tbl_Subjects WHERE subject='" + spn_subject.getSelectedItem().toString() + "'", null);
                while (cursor.moveToNext()) {
                    txt_tot.setText("" + cursor.getInt(cursor.getColumnIndex("tot_days")));
                    txt_att.setText("" + cursor.getInt(cursor.getColumnIndex("att_days")));
                    id = cursor.getInt(cursor.getColumnIndex("id"));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(txt_att.getText()) || TextUtils.isEmpty(txt_tot.getText())) {
                    txt_att.setError("These fields cannot be empty");
                    txt_tot.setError("These fields cannot be empty");
                } else {
                    tot_att = Integer.parseInt(txt_att.getText().toString());
                    tot_days = Integer.parseInt(txt_tot.getText().toString());
                    if (tot_att > tot_days)
                        Toast.makeText(getApplicationContext(), "Total Attendance cannot be higher than Total Days", Toast.LENGTH_SHORT).show();
                    else {
                        try {
                            reclec_db.execSQL("UPDATE tbl_Subjects SET att_days=" + tot_att + ",tot_days=" + tot_days + " WHERE id=" + id + "");
                            Toast.makeText(getApplicationContext(), "Manually Updated Successfully!", Toast.LENGTH_SHORT).show();
                        } catch (SQLiteException e) {
                            Toast.makeText(getApplicationContext(), "You are not selected a Subject!", Toast.LENGTH_SHORT).show();
                        }
                    }

                }


            }
        });
    }
}
