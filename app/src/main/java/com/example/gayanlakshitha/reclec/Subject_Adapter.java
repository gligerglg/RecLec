package com.example.gayanlakshitha.reclec;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Gayan Lakshitha on 9/15/2017.
 */

public class Subject_Adapter extends BaseAdapter {

    private Context myContext;
    private List<Subject> sub_list;
    private int percentage;

    public Subject_Adapter(Context myContext, List<Subject> sub_list) {
        this.myContext = myContext;
        this.sub_list = sub_list;
    }

    @Override
    public int getCount() {
        return sub_list.size();
    }

    @Override
    public Object getItem(int position) {
        return sub_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(myContext, R.layout.custom_summery_list, null);
        TextView txt_sub = (TextView) v.findViewById(R.id.txt_subject);
        TextView txt_days = (TextView) v.findViewById(R.id.txt_days);
        TextView txt_att = (TextView) v.findViewById(R.id.txt_tot);
        TextView txt_avg = (TextView) v.findViewById(R.id.txt_avg);
        ProgressBar progress = (ProgressBar) v.findViewById(R.id.progressBar);

        if (sub_list.get(position).getTot_days() == 0)
            percentage = 0;
        else
            percentage = ((sub_list.get(position).getTot_att()) * 100) / sub_list.get(position).getTot_days();
        txt_sub.setText(sub_list.get(position).getSubject());
        txt_att.setText(String.valueOf(sub_list.get(position).getTot_att()));
        txt_days.setText(String.valueOf(sub_list.get(position).getTot_days()));
        txt_avg.setText("Average " + String.valueOf(percentage) + "%");

        if (percentage > 80)
            progress.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        else if (percentage > 60)
            progress.getProgressDrawable().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
        else
            progress.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

        progress.setMax(sub_list.get(position).getTot_days());
        progress.setProgress(sub_list.get(position).getTot_att());

        return v;
    }
}
