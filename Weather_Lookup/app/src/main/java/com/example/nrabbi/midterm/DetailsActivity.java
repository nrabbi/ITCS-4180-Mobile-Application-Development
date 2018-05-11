// Nazmul Rabbi
// ITCS 4180 : Mid Term
// DetailsActivity.java

package com.example.nrabbi.midterm;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView titleText = (TextView) findViewById(R.id.titleTxt);
        //ImageView imgDisplay = findViewById(R.id.coverImage);
        Button home = (Button) findViewById(R.id.homeBtn);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailsActivity.this, "Going back to home!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        titleText.setText("");
        Intent intent = getIntent();

        Bundle bd = intent.getExtras();
        if(bd != null)
        {
            String getName = (String) bd.get("Title");
            String getzmw = (String) bd.get("zmw");
            Toast.makeText(DetailsActivity.this, "http://api.wunderground.com/api/84bef3e490af8d12/conditions/q/zmw:" + getzmw + ".json",  Toast.LENGTH_SHORT).show();
            //String getImg = (String) bd.get("img_url");
            titleText.setText(getName);
            //Picasso.with(DetailsActivity.this).load(getImg).into(imgDisplay);
        }

    }




}
