package com.ee5453.tweetsdisplay;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    Cursor cursor;
    ListView listView;
    SimpleCursorAdapter simpleCursorAdapter;
    String FROM[] = {"user_name", "status_text"};
    int TO[] = {android.R.id.text1, android.R.id.text2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);


        Toast.makeText(getApplicationContext(),  " if delete is pressed - RELOAD mytwitter app . " +
                        "if update button is pressed, all tweets will be updated to a predefined text," ,
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ContentResolver resolver = getContentResolver();
        cursor = resolver.query(Uri.parse("content://com.ee5453.mytwitter"),
                null, null, null, "created_at" + " DESC");

        ContentObserver observer = new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                Cursor tweets;
                tweets = getContentResolver().query(Uri.parse("content://com.ee5453.mytwitter"),
                        null, null, null, "created_at" + " DESC");
                simpleCursorAdapter.changeCursor(tweets);

            }

            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                Cursor tweets;
                tweets = getContentResolver().query(Uri.parse("content://com.ee5453.mytwitter"),
                        null, null, null, "created_at" + " DESC");
                simpleCursorAdapter.changeCursor(tweets);
            }
        };
        resolver.registerContentObserver(Uri.parse("content://com.ee5453.mytwitter"), true, observer);

        simpleCursorAdapter = new SimpleCursorAdapter(getApplicationContext(),
                android.R.layout.two_line_list_item, cursor, FROM, TO);
        listView.setAdapter(simpleCursorAdapter);

    }


    public void FilterPage(View v) {
        Intent filterIntent = new Intent(MainActivity.this, FilterActivity.class);
        startActivity(filterIntent);
    }

    public void DeleteContent(View v) {
        // A dumb content resolver that deletes all records
        Toast.makeText(getApplicationContext(), "All records deleted," +
                        " filter output will be empty, reload app to use filter",
                Toast.LENGTH_SHORT).show();
        getContentResolver().delete(Uri.parse("content://com.ee5453.mytwitter"), null, null);


    }


    public void UpdateContent(View v) {
        // A dumb content resolver that updates all records
        Toast.makeText(getApplicationContext(), "All records updated, filtered " +
                        "tweets will be having text 'You have been updated!!",
                Toast.LENGTH_SHORT).show();
        ContentValues values = new ContentValues();
        values.put("status_text", "You have been updated!!");
        getContentResolver().update(Uri.parse("content://com.ee5453.mytwitter"),
                values, null, null); //updates entire table, check it using filter page

    }

}
