// Nazmul Rabbi & Dyrell Cole
// ITCS 4180 : In Class Assignment 09
// CreateContact.java

package com.example.nrabbi.inclass10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class CreateContact extends AppCompatActivity {
    static String name = "NAME";
    String Name = null;
    static String email = "EMAIL";
    String Email = null;
    static String department = "DEPARTMENT";
    String dept = null;
    static String mood_key = "MOOD";
    static String STUDENT_KEY = "STUDENT";
    public static final String VALUE_KEY = "VALUE";
    String avatars = "0";
    String AV_KEY = "AVATAR";
    Boolean nameFlag = false;
    Boolean emailFlag = false;
    String userEmail = null;
    ArrayList<User> contactList;
    private DatabaseReference mDatabase;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);
        final EditText inName = (EditText) findViewById(R.id.name);
        final EditText inEmail = (EditText) findViewById(R.id.email);
        final EditText inPhone = (EditText) findViewById(R.id.inPhone);
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();
        userEmail = getIntent().getExtras().getString("EMAIL");
        contactList = (ArrayList<User>) getIntent().getExtras().getSerializable("LIST");

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = inName.getText().toString();
                Email = inEmail.getText().toString();

                if(Name.matches(""))
                    nameFlag = true;
                else
                    nameFlag = false;

                if(Email.matches("") || !(android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()))
                    emailFlag = true;
                else
                    emailFlag = false;

                if (!nameFlag && !emailFlag) {
                    writeNewUser(inName.getText().toString(), inEmail.getText().toString(), inPhone.getText().toString(), dept, avatars);
                    User user = new User(inName.getText().toString(), inEmail.getText().toString(), inPhone.getText().toString(), dept, avatars);

                    if(contactList.isEmpty())
                        contactList = new ArrayList<>();

                    contactList.add(user);
                    Intent intent = new Intent(CreateContact.this, ContactsList.class);
                    intent.putExtra("LIST", contactList);
                    intent.putExtra("EMAIL", userEmail);
                    startActivity(intent);
                }
                else if(nameFlag && !emailFlag) {
                    inName.setError("Please enter a name");
                    Toast.makeText(CreateContact.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                }
                else if(!nameFlag && emailFlag) {
                    inEmail.setError("Please enter a valid email");
                    Toast.makeText(CreateContact.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                }
                else if(nameFlag && emailFlag){
                    inName.setError("Please enter a name");
                    inEmail.setError("Please enter a valid email");
                    Toast.makeText(CreateContact.this, "Please enter a valid name & email", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }

    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        if (reqCode == 100)
            if (resultCode == RESULT_OK) {
                avatars = data.getExtras().getString(VALUE_KEY);
            }
    }

    private void writeNewUser(String name, String email, String phone, String dept, String avatar){
        User newUser = new User(name, email, phone, dept, avatar);
        String result = userEmail.replaceAll("[-+.^:,@]","");
        String contactEmail = email.replaceAll("[-+.^:,@]","");
        String newResult = result.toLowerCase();
        mDatabase.child("Users").child(newResult).child("Contact List").child(contactEmail).setValue(newUser);
    }
}
