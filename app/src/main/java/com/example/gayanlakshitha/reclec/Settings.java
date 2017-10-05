package com.example.gayanlakshitha.reclec;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Gayan Lakshitha on 9/15/2017.
 */

public class Settings extends Activity {

    Button btn_add;
    Button btn_medit;
    Button btn_clear;
    Button btn_about;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        btn_add = (Button) findViewById(R.id.btn_add_subject);
        btn_medit = (Button) findViewById(R.id.btn_manual);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_about = (Button) findViewById(R.id.btn_about);

        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, About_App.class));
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, Add_Subject.class));
            }
        });

        btn_medit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, Manual_Editing.class));
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SQLiteDatabase db = openOrCreateDatabase("reclec_db.db", MODE_PRIVATE, null);
                AlertDialog.Builder aleart = new AlertDialog.Builder(Settings.this);
                aleart.setTitle("Reset Database");
                aleart.setMessage("Do You Want To Reset All Data?");

                aleart.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            db.execSQL("DELETE FROM tbl_Subjects");
                            db.execSQL("DELETE FROM tbl_days");
                            SharedPreferences shared = getSharedPreferences("check", 0);
                            SharedPreferences.Editor editor = shared.edit();
                            editor.putString("today", "");
                            editor.commit();
                            Toast.makeText(getApplicationContext(), "All Entries Deleted Successfully!", Toast.LENGTH_SHORT).show();
                        } catch (SQLException e) {
                            Toast.makeText(getApplicationContext(), "Database Error!", Toast.LENGTH_SHORT).show();
                        } finally {
                            dialog.dismiss();
                        }

                    }
                });

                aleart.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                aleart.create().show();
            }
        });
    }


}
