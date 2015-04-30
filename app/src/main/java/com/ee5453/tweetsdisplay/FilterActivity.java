package com.ee5453.tweetsdisplay;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class FilterActivity extends ActionBarActivity {

    public final String TAG = "FilterActivity";


    ListView listView;
    EditText et1;
    EditText et2;
    EditText et3;
    SimpleDateFormat sdf;
    Date d1;
    Date d2;
    private final String format = "yy/MM/dd,HH:mm";
    private long fromTime = 0l;
    private long toTime = 0l;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        et1 = (EditText) findViewById(R.id.lname);
        et2 = (EditText) findViewById(R.id.upTime);
        et3 = (EditText) findViewById(R.id.dTime);
        sdf =  new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("America/Chicago"));

    }

    public void filterOut (View v) {

        try {
            Log.d(TAG, "whats happening..");
            Log.d(TAG, format + " : : " + et3.getText().toString());
            d1 = sdf.parse(et2.getText().toString());
            d2 = sdf.parse(et3.getText().toString());
            fromTime = d1.getTime();
            toTime = d2.getTime();
            //fromTime = 1430189897000l;
            //toTime = 1430195742000l;
            Intent showFiltered = new Intent(FilterActivity.this, FilterDisplay.class);
            showFiltered.putExtra("from", fromTime);
            showFiltered.putExtra("to", toTime);
            showFiltered.putExtra("name", et1.getText().toString());
            Log.wtf(TAG, "Going ballistic..");
            startActivity(showFiltered);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
