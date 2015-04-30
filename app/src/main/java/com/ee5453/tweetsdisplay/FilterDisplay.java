package com.ee5453.tweetsdisplay;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;



public class FilterDisplay extends ActionBarActivity {

    public static final String TAG = "FilterDisplay";
    Cursor cursor;
    SimpleCursorAdapter simpleCursorAdapter;
    ContentResolver resolver;
    String FROM[]={"user_name","status_text"};
    int TO[]={android.R.id.text1,android.R.id.text2};
    ListView listView ;
    long fromTime;
    long toTime;
    String name = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_display);
        listView = (ListView) findViewById(R.id.listView);

    }

    @Override
    protected void onResume() {
        super.onResume();

        fromTime = getIntent().getExtras().getLong("from");
        toTime = getIntent().getExtras().getLong("to");
        name = getIntent().getExtras().getString("name");

        String condition = "user_name = '"+name+"' AND created_at BETWEEN "+fromTime+" AND "+toTime+" ";

        Log.d (TAG, "SQLite query:: "+condition);
        Log.d(TAG, "Intent contents : "+Long.toString(fromTime));
        Log.d(TAG, Long.toString(toTime));

        resolver=getContentResolver();
        cursor=resolver.query(Uri.parse("content://com.ee5453.mytwitter"),
                null,condition,null,"    created_at"+" DESC");

        ContentObserver observer = new ContentObserver(new Handler()) {

            ContentResolver resolver=getContentResolver();
            String condition = "created_at BETWEEN "+fromTime+" AND "+toTime+" ";

            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                Cursor tweets;
                tweets=resolver.query(Uri.parse("content://com.ee5453.mytwitter"),
                        null, condition, null, "    created_at" + " DESC");
                simpleCursorAdapter.changeCursor(tweets);
            }

            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                Cursor tweets;
                tweets=resolver.query(Uri.parse("content://com.ee5453.mytwitter"),
                        null, condition, null, "    created_at" + " DESC");
                simpleCursorAdapter.changeCursor(tweets);
            }
        };
        resolver.registerContentObserver(Uri.parse("content://com.ee5453.mytwitter"), true, observer);

        simpleCursorAdapter=new SimpleCursorAdapter(getApplicationContext(),
                android.R.layout.two_line_list_item,cursor,FROM,TO);
        listView.setAdapter(simpleCursorAdapter);


    }
}


