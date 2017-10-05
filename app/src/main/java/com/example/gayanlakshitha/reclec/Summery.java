package com.example.gayanlakshitha.reclec;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gayan Lakshitha on 9/15/2017.
 */

public class Summery extends Activity {

    private ListView lst_subject;
    private Subject subject;
    private Subject_Adapter subject_adapter;
    private List<Subject> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summery);

        LinearLayout layout = (LinearLayout) findViewById(R.id.summery_back);
        lst_subject = (ListView) findViewById(R.id.lst_summery);
        list = new ArrayList<>();
        final SQLiteDatabase db = openOrCreateDatabase("reclec_db.db", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_Subjects", null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            list.add(new Subject(cursor.getString(1), cursor.getInt(2), cursor.getInt(3)));
        }

        subject_adapter = new Subject_Adapter(getApplicationContext(), list);
        lst_subject.setAdapter(subject_adapter);

        if (subject_adapter.isEmpty())
            layout.setBackgroundResource(R.drawable.xhdpi_no_subject);
    }
}
