/*
Homework #1
ViewContact.java
Nazmul Rabbi & Dyrell Cole
Group #20
*/

package com.nrabbi.itcs4180.hw1;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.graphics.BitmapFactory;

public class ViewContact extends AppCompatActivity implements View.OnClickListener{
    static final String CONTACT = "CONTACT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        if(getIntent() != null && getIntent().getExtras() != null){
            Contact thisperson = (Contact) getIntent().getExtras().get(CONTACT);
            setTitle(thisperson.getFname() + " " + thisperson.getLname());

            byte[] byteArray = thisperson.getPhoto();
            ((ImageView) findViewById(R.id.viewprofilephoto)).setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));
            ((TextView) findViewById(R.id.viewfirstname)).setText(thisperson.getFname());
            ((TextView) findViewById(R.id.viewlastname)).setText(thisperson.getLname());
            ((TextView) findViewById(R.id.viewaddress)).setText(thisperson.getAddress());
            ((TextView) findViewById(R.id.viewbirthday)).setText(thisperson.getBirthday());
            ((TextView) findViewById(R.id.viewcompany)).setText(thisperson.getCompany());
            ((TextView) findViewById(R.id.viewemail)).setText(thisperson.getEmail());
            ((TextView) findViewById(R.id.viewfburl)).setText(thisperson.getFbURL());
            ((TextView) findViewById(R.id.viewnickname)).setText(thisperson.getNickname());
            ((TextView) findViewById(R.id.viewphone)).setText(thisperson.getPhone());
            ((TextView) findViewById(R.id.viewskype)).setText(thisperson.getSkypeURL());
            ((TextView) findViewById(R.id.viewtwitterurl)).setText(thisperson.getTwitterURL());
            ((TextView) findViewById(R.id.viewurl)).setText(thisperson.getUrl());
            ((TextView) findViewById(R.id.viewyoutube)).setText(thisperson.getYoutubeChannel());
        }

        if(!((TextView)findViewById(R.id.viewurl)).getText().toString().trim().isEmpty())
            findViewById(R.id.viewurl).setOnClickListener(this);
        if(!((TextView)findViewById(R.id.viewfburl)).getText().toString().trim().isEmpty())
            findViewById(R.id.viewfburl).setOnClickListener(this);
        if(!((TextView)findViewById(R.id.viewskype)).getText().toString().trim().isEmpty())
            findViewById(R.id.viewskype).setOnClickListener(this);
        if(!((TextView)findViewById(R.id.viewtwitterurl)).getText().toString().trim().isEmpty())
            findViewById(R.id.viewtwitterurl).setOnClickListener(this);
        if(!((TextView)findViewById(R.id.viewyoutube)).getText().toString().trim().isEmpty())
            findViewById(R.id.viewyoutube).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent x = new Intent("android.intent.action.VIEW");
        x.setData(Uri.parse(((TextView)findViewById(view.getId())).getText().toString()));
        startActivity(x);
    }
}
