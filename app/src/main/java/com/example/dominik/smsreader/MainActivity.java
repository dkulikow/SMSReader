package com.example.dominik.smsreader;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends Activity {

    TextView textView;
    Uri uriSMSURI;
    Cursor cur;
    String sms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textview);
        uriSMSURI = Uri.parse("content://sms/inbox");
        cur = getContentResolver().query(uriSMSURI, null, null, null,null);
        sms = "";


    }

    @Override
    protected void onStart() {
        super.onStart();
        int n = 10;
        while (cur.moveToNext() && n >= 0) {
            sms += "From: ";
            for(int i = 0; i < cur.getColumnCount(); i++) {
                sms += cur.getString(i) + ": ";
            }
            sms += "\n\n\n";
        n--;
        }
        textView.setText(sms);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
