/*
Homework #1
MainActivity.java
Nazmul Rabbi & Dyrell Cole
Group #20
*/

package com.nrabbi.itcs4180.hw1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class MainActivity extends AppCompatActivity {
    static final int CONTACT_CODE = 100;
    static final int DELETE_CODE = 999;
    static final int EDIT_CODE = 888;
    static final String CONTACT_KEY = "NEW_CONTACT";
    static final String UPDATE = "UPDATE";
    private static ArrayList<Contact> people = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Contacts");

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.default_image);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        people.add(new Contact(byteArray, "John", "Smith", "UNCC", "980-777-1111","jsmith@uncc.edu","http://www.google.com","101 Gold Bridge Lane","Jan 1, 1971","James","http://www.facebook.com","http://www.twitter.com","http://www.skype.com","http://www.youtube.com"));
        people.add(new Contact(byteArray, "Jane", "Doe", "UNCC", "419-222-3333","jdoe@uncc.edu","http://www.google.com","104 Gold Bridge Lane","Feb 1, 1971","Jane","http://www.facebook.com","http://www.twitter.com","http://www.skype.com","http://www.youtube.com"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CONTACT_CODE){
            if(resultCode == RESULT_OK){
                Contact person = (Contact) data.getExtras().get(CONTACT_KEY);
                people.add(person);
            }
        }

        if(requestCode == EDIT_CODE || requestCode == DELETE_CODE){
            if(resultCode == RESULT_OK){
                people = (ArrayList<Contact>) data.getExtras().get(UPDATE);
            }
        }
    }

    public void onCreateClick(View view){
        Intent intent = new Intent(MainActivity.this, CreateNewContact.class);
        startActivityForResult(intent, CONTACT_CODE);
    }

    public void onEditClick(View view){
        Intent intent = new Intent(MainActivity.this, DisplayContacts.class);
        if(people.size() > 0) {
            intent.putExtra("CONTACTS", people);
            intent.putExtra("VIEW", false);
            intent.putExtra("EDIT", true);
            intent.putExtra("DELETE", false);
            startActivityForResult(intent, EDIT_CODE);
        }else
            Toast.makeText(this, "There are no contacts to edit", Toast.LENGTH_SHORT).show();
    }

    public void onDeleteClick(View view){

        Intent intent = new Intent(MainActivity.this, DisplayContacts.class);

        if(people.size() > 0) {
            intent.putExtra("CONTACTS", people);
            intent.putExtra("VIEW", false);
            intent.putExtra("EDIT", false);
            intent.putExtra("DELETE", true);
            startActivityForResult(intent, DELETE_CODE);
        }else
            Toast.makeText(this, "There are no contacts to Delete", Toast.LENGTH_SHORT).show();
    }

    public void onDisplayClick(View view){
        Intent intent = new Intent(MainActivity.this, DisplayContacts.class);

        if(people.size() > 0) {
            intent.putExtra("CONTACTS", people);
            intent.putExtra("VIEW", true);
            intent.putExtra("EDIT", false);
            intent.putExtra("DELETE", false);
            startActivity(intent);
        }else
            Toast.makeText(this, "There are no contacts to show", Toast.LENGTH_SHORT).show();
    }

    public void onFinishClick(View view){
        finish();
    }

}
