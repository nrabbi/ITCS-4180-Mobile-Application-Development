// Nazmul Rabbi & Dyrell Cole
// ITCS 4180 : In Class Assignment 10
// ContactsList.java

package com.example.nrabbi.inclass10;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContactsList extends AppCompatActivity {
    private DatabaseReference mDatabase;
    String email;
    ArrayList<User> contactList;
    ListView list;
    CustomAdapter customAdapter;
    DataSnapshot snapshot;
    String newResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(ContactsList.this,
                "Long Press a List Item to Delete",
                Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_contacts_list);
        Intent intent = getIntent();
        email = intent.getExtras().getString("EMAIL");
        String result = email.replaceAll("[-+.^:,@]","");
        newResult = result.toLowerCase();
        contactList = (ArrayList<User>) intent.getExtras().getSerializable("LIST");
        mDatabase = FirebaseDatabase.getInstance().getReference("/Users/" + newResult + "/Contact List");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contactList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    snapshot = postSnapshot;
                    User user = snapshot.getValue(User.class);
                    contactList.add(user);
                    Log.d("demo", contactList.toString());
                    System.out.println("plz work");
                }
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ContactsList.this, "Failed to Connect. Please try again later", Toast.LENGTH_SHORT).show();
            }
        });

        list = (ListView) findViewById(R.id.contacList3);
        customAdapter = new CustomAdapter(ContactsList.this, contactList);
        list.setAdapter(customAdapter);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String delEmail = contactList.get(i).email.replaceAll("[-+.^:,@]","");
                DatabaseReference rDatabase = FirebaseDatabase.getInstance().getReference("/Users/" + newResult + "/Contact List/" + delEmail);
                contactList.remove(i);

                rDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().removeValue();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                customAdapter.notifyDataSetChanged();
                return false;
            }

            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

            }
        });

        findViewById(R.id.createNewContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactsList.this, CreateContact.class);
                intent.putExtra("EMAIL", email);
                intent.putExtra("LIST", contactList);
                startActivity(intent);
            }
        });

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent1 = new Intent(ContactsList.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }

    @IgnoreExtraProperties
    public class Post {
        public String name;
        public String email;
        public String phone;
        public String dept;
        public Map<String, Boolean> stars = new HashMap<>();

        public Post() {

        }

        public Post(String name, String email, String phone, String dept) {
            this.name = name;
            this.email = email;
            this.phone = phone;
        }

        @Exclude
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("name", name);
            result.put("email", email);
            result.put("phone", phone);
            return result;
        }

    }

    class CustomAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater mInflater;
        private ArrayList<User> mDataSource;

        public CustomAdapter(Context context, ArrayList<User> items) {
            mContext = context;
            mDataSource = items;
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mDataSource.size();
        }

        @Override
        public Object getItem(int position) {
            return mDataSource.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = mInflater.inflate(R.layout.contact_item, parent, false);
            TextView name = (TextView) convertView.findViewById(R.id.cName);
            TextView email = (TextView) convertView.findViewById(R.id.cEmail);
            TextView phone = (TextView) convertView.findViewById(R.id.cPhone);
            ImageView pic = (ImageView) convertView.findViewById(R.id.imageView);
            User user = mDataSource.get(position);
            name.setText("Name:" + user.name);
            email.setText("Email: " + user.email);
            phone.setText("Phone:" + user.phone);
            return convertView;
        }
    }
}
