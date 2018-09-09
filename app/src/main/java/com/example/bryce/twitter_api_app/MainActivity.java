package com.example.bryce.twitter_api_app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        // Basic thread for now.
        // Will need to research threads later on.
        new Thread(new Runnable() {
            @Override
            public void run() {

                // Need to change this code.
                // Will need to load userHandles.
                HashMap<String,String> userHandles = new HashMap<String,String>();
                userHandles.put("pewdiepie" , "");
                //userHandles.put("JuzzSteph" , "");

                DataGatherer twitApi = new TwitterApi(userHandles);
                final ArrayList<String> tweets = twitApi.RetrieveData();


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ShowTweets(tweets);
                    }
                });
            }



        }).start();

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

    public void ShowTweets(ArrayList<String> tweets)
    {
        TextView output = (TextView) findViewById(R.id.output);

        for(String tweet : tweets)
        {
            output.append("Tweet: " + tweet+ " \n");
        }


    }
}
