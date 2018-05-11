// Nazmul Rabbi & Dyrell Cole
// TopPaidApps.java
// ITCS 4180 : Homework 3
// Group 20

package com.example.nrabbi.Homework3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TopPaidApps extends AppCompatActivity implements FetchDataAsync.IData {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_paid_apps);
        new FetchDataAsync(TopPaidApps.this).execute("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");
    }


    @Override
    public void setUpData(ArrayList<App> apps) {
        
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onBackPressed() {
    }
}
