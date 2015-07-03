package com.example.dominik.smsreader;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Dominik on 2015-07-01.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<String[]> smsData;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView adress;
        public TextView date;
        public TextView body;

        public ViewHolder(View v) {
            super(v);
            adress = (TextView) v.findViewById(R.id.adress);
            date = (TextView) v.findViewById(R.id.date);
            body = (TextView) v.findViewById(R.id.body);
        }
    }

    public MyAdapter(ArrayList<String[]> mySmsData) {
        System.out.println("SIZE3 " + mySmsData.size());
        this.smsData = mySmsData;
        System.out.println("SIZE4 " + this.smsData.size());
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sms_row, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Date date = new Date(Long.parseLong(smsData.get(position)[MainActivity.DATE]));
        holder.adress.setText(smsData.get(position)[MainActivity.ADRESS]);
        holder.date.setText(date.toString());
        holder.body.setText(smsData.get(position)[MainActivity.BODY]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return smsData.size();
    }

}
