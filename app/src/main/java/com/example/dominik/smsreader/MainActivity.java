package com.example.dominik.smsreader;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;

public class MainActivity extends Activity {

    public static final int ADRESS = 0;
    public static final int DATE = 1;
    public static final int BODY = 2;
    public static final String SMS_DATA = "SMS_DATA";

    private String [][] smsDataSet;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if(smsDataSet == null) {
            Uri uriSMSURI = Uri.parse("content://sms/inbox");
            Cursor cur = getContentResolver().query(uriSMSURI, null, null, null, null);
            smsDataSet = new String[3][cur.getCount()];

            while (cur.moveToNext()) {
                smsDataSet[ADRESS][cur.getPosition()] = cur.getString(cur.getColumnIndex("address"));
                smsDataSet[DATE][cur.getPosition()] = cur.getString(cur.getColumnIndex("date"));
                smsDataSet[BODY][cur.getPosition()] = cur.getString(cur.getColumnIndex("body"));
            }
        }

        mAdapter = new MyAdapter(smsDataSet);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SMS_DATA, smsDataSet);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        smsDataSet = (String[][]) savedInstanceState.getSerializable(SMS_DATA);
    }
}
