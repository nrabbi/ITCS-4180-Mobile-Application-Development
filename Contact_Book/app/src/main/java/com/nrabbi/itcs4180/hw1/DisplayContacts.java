/*
Homework #1
DisplayContacts.java
Nazmul Rabbi & Dyrell Cole
Group #20
*/

package com.nrabbi.itcs4180.hw1;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;

public class DisplayContacts extends AppCompatActivity implements ScrollView.OnClickListener{
    static final String CONTACT = "CONTACT";
    static final String CONTACTS = "CONTACTS";
    static final String ECONTACT = "EDITTED_CONTACT";
    static final String UPDATE = "UPDATE";
    static final int EDIT_CODE = 777;
    static final int DELETE_CODE = 999;
    ArrayList<Integer> layout = new ArrayList<>();
    ArrayList<Contact> people = new ArrayList<Contact>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Contacts List");

        if(getIntent() != null && getIntent().getExtras() != null) {
            people = new ArrayList<>();
            people = (ArrayList<Contact>) getIntent().getExtras().get(CONTACTS);
            makeDisplay();
        }
    }

    private void makeDisplay() {

        if(people.size() == 0 && (boolean)getIntent().getExtras().get("DELETE")) {
            Intent results = new Intent();
            results.putExtra(UPDATE, new ArrayList<Contact>());
            setResult(RESULT_OK,results);
            finish();
        }

        layout = new ArrayList<>();
        ScrollView sv = new ScrollView(this);
        sv.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
        sv.setId(View.generateViewId());
        sv.setClickable(true);
        setContentView(sv);

        ConstraintLayout screen = new ConstraintLayout(this);
        screen.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        screen.setClickable(true);
        if(people.size() > 0) {
            ConstraintLayout currentLayout = new ConstraintLayout(this);
            ConstraintLayout.LayoutParams currentLayoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
            currentLayoutParams.topToBottom = screen.getId();
            currentLayout.setLayoutParams(currentLayoutParams);
            currentLayout.setId(View.generateViewId());
            currentLayout.setClickable(true);
            currentLayout.setOnClickListener(this);
            ImageView currentProfilePic = new ImageView(this);
            currentProfilePic.setId(View.generateViewId());
            currentProfilePic.setImageDrawable(getResources().getDrawable(R.drawable.default_image));

            ConstraintLayout.LayoutParams profilePicParams = new ConstraintLayout.LayoutParams(200, 180);
            profilePicParams.leftMargin = 16;
            profilePicParams.topMargin = 16;
            profilePicParams.leftToLeft = currentLayout.getId();
            profilePicParams.topToTop = currentLayout.getId();
            currentProfilePic.setLayoutParams(profilePicParams);
            currentProfilePic.setImageBitmap(BitmapFactory.decodeByteArray(people.get(0).getPhoto(), 0, people.get(0).getPhoto().length));
            currentLayout.addView(currentProfilePic);

            TextView firstName = new TextView(this);
            firstName.setId(View.generateViewId());
            firstName.setText(people.get(0).getFname());
            ConstraintLayout.LayoutParams fnameParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            fnameParams.leftMargin = 10;
            fnameParams.topMargin = 70;
            fnameParams.leftToRight = currentProfilePic.getId();
            fnameParams.topToTop = currentLayout.getId();
            firstName.setLayoutParams(fnameParams);

            currentLayout.addView(firstName);

            TextView lastName = new TextView(this);
            lastName.setId(View.generateViewId());
            lastName.setText(people.get(0).getLname());
            ConstraintLayout.LayoutParams lnameParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lnameParams.leftMargin = 12;
            lnameParams.topMargin = 70;
            lnameParams.leftToRight = firstName.getId();
            lnameParams.topToTop = currentLayout.getId();
            lnameParams.horizontalBias = (float) 1.0;
            lastName.setLayoutParams(lnameParams);

            currentLayout.addView(lastName);

            TextView phoneNum = new TextView(this);
            phoneNum.setId(View.generateViewId());
            phoneNum.setText("Phone : " + people.get(0).getPhone());
            ConstraintLayout.LayoutParams phoneNumParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            phoneNumParams.leftMargin = 8;
            phoneNumParams.topMargin = 12;
            phoneNumParams.leftToRight = currentProfilePic.getId();
            phoneNumParams.topToBottom = firstName.getId();
            phoneNumParams.horizontalBias = (float) 0.14;
            phoneNum.setLayoutParams(phoneNumParams);

            currentLayout.addView(phoneNum);

            screen.addView(currentLayout);


            int id = currentLayout.getId();

            for (int i = 1; i < people.size(); i++) {
                id = currentLayout.getId();
                layout.add(id);
                currentLayout = new ConstraintLayout(this);
                currentLayoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
                currentLayoutParams.topToBottom = id;
                currentLayout.setLayoutParams(currentLayoutParams);
                currentLayout.setId(View.generateViewId());
                currentLayout.setClickable(true);
                currentProfilePic = new ImageView(this);
                currentProfilePic.setId(View.generateViewId());

                currentProfilePic.setImageBitmap(BitmapFactory.decodeByteArray(people.get(i).getPhoto(), 0, people.get(i).getPhoto().length));

                profilePicParams = new ConstraintLayout.LayoutParams(200, 180);
                profilePicParams.leftMargin = 16;
                profilePicParams.topMargin = 16;
                profilePicParams.leftToLeft = currentLayout.getId();
                profilePicParams.topToTop = currentLayout.getId();
                currentProfilePic.setLayoutParams(profilePicParams);

                currentLayout.addView(currentProfilePic);

                firstName = new TextView(this);
                firstName.setId(View.generateViewId());
                firstName.setText(people.get(i).getFname());
                fnameParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                fnameParams.leftMargin = 10;
                fnameParams.topMargin = 70;
                fnameParams.leftToRight = currentProfilePic.getId();
                fnameParams.topToTop = currentLayout.getId();
                firstName.setLayoutParams(fnameParams);

                currentLayout.addView(firstName);

                lastName = new TextView(this);
                lastName.setId(View.generateViewId());
                lastName.setText(people.get(i).getLname());
                lnameParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lnameParams.leftMargin = 12;
                lnameParams.topMargin = 70;
                lnameParams.leftToRight = firstName.getId();
                lnameParams.topToTop = currentLayout.getId();
                lnameParams.horizontalBias = (float) 1.0;
                lastName.setLayoutParams(lnameParams);

                currentLayout.addView(lastName);

                phoneNum = new TextView(this);
                phoneNum.setId(View.generateViewId());
                phoneNum.setText("Phone : " + people.get(i).getPhone());
                phoneNumParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                phoneNumParams.leftMargin = 8;
                phoneNumParams.topMargin = 12;
                phoneNumParams.leftToRight = currentProfilePic.getId();
                phoneNumParams.topToBottom = firstName.getId();
                phoneNumParams.horizontalBias = (float) 0.14;
                phoneNum.setLayoutParams(phoneNumParams);

                currentLayout.addView(phoneNum);
                currentLayout.setOnClickListener(this);
                screen.addView(currentLayout);
            }
            id = currentLayout.getId();
            layout.add(id);
            sv.addView(screen);
        }
    }

    @Override
    public void onClick(View view) {
        if((boolean)getIntent().getExtras().get("VIEW")) {
            Intent intent = new Intent(DisplayContacts.this, ViewContact.class);
            intent.putExtra("CONTACT", people.get(layout.indexOf(view.getId())));
            startActivity(intent);
        }

        if((boolean)getIntent().getExtras().get("EDIT")) {
            Intent intent = new Intent(DisplayContacts.this, EditContact.class);
            intent.putExtra("CONTACT", people.get(layout.indexOf(view.getId())));
            intent.putExtra("LOCATION",layout.indexOf(view.getId()));
            startActivityForResult(intent,EDIT_CODE);
        }

        if((boolean)getIntent().getExtras().get("DELETE")) {
            final int x = layout.indexOf(view.getId());
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete " + people.get(x).getFname() + " " + people.get(x).getLname() + " from Contacts")
                    .setMessage("Confirm?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String f = people.get(x).getFname();
                            String l = people.get(x).getLname();
                            Toast.makeText(DisplayContacts.this, "Successfully removed " + f + " " + l + " from contacts" , Toast.LENGTH_SHORT).show();
                            people.remove(x);
                            Intent results = new Intent();
                            results.putExtra(UPDATE, people);
                            setResult(RESULT_OK,results);
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

            final AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == EDIT_CODE){
            if(resultCode == RESULT_OK){
                Contact x = (Contact) data.getExtras().get(ECONTACT);
                Toast.makeText(DisplayContacts.this, "Successfully editted " + x.getFname() + " " + x.getLname() + " from contacts" , Toast.LENGTH_SHORT).show();
                int pos = (int) data.getExtras().get("LOCATION");
                people.remove(pos);
                people.add(pos,x);
                Intent results = new Intent();
                results.putExtra(UPDATE, people);
                setResult(RESULT_OK,results);
                finish();

            }
        }
    }
}
