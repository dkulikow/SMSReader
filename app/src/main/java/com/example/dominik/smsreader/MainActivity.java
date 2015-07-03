package com.example.dominik.smsreader;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    public static final int ADRESS = 0;
    public static final int DATE = 1;
    public static final int BODY = 2;
    public static final String SMS_DATA = "SMS_DATA";

    private ArrayList<String[]> smsDataSet;
    private ArrayList<String[]> workingSmsDataSet;

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


        if (smsDataSet == null) {
            Uri uriSMSURI = Uri.parse("content://sms/inbox");
            Cursor cur = getContentResolver().query(uriSMSURI, null, null, null, null);
            smsDataSet = new ArrayList<>(cur.getCount());
            String[] sms;
            while (cur.moveToNext()) {
                sms = new String[3];
                sms[ADRESS] = cur.getString(cur.getColumnIndex("address"));
                sms[DATE] = cur.getString(cur.getColumnIndex("date"));
                sms[BODY] = cur.getString(cur.getColumnIndex("body"));
                smsDataSet.add(sms);
            }
            System.out.println("SIZE " + smsDataSet.size());
            workingSmsDataSet = smsDataSet;
            handleIntent(getIntent());
            System.out.println("SIZE2 " + smsDataSet.size());
            mAdapter = new MyAdapter(smsDataSet);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        MenuItem search = menu.findItem(R.id.search);

        search.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(getApplicationContext(), "Return", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

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
        smsDataSet = (ArrayList<String[]>) savedInstanceState.getSerializable(SMS_DATA);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            System.out.println("HANDLED" + query);

        }
    }
}
