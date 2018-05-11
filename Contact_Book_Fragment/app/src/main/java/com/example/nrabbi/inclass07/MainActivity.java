// Group 20 : Nazmul Rabbi and Dyrell Cole
// ITCS 4180 : In Class Assignment #07
// MainActivity.java
// 3/20/18

package com.example.nrabbi.inclass07;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ContactsFragment.OnFragmentInteractionListener, SelectAvatarFragment.OnFragmentInteractionListener, CreateNewContactFragment.OnFragmentInteractionListener{
    private ArrayList<Contact> contacts = new ArrayList<>();
    private int iconID = R.drawable.select_avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Contacts");

        contacts.add(new Contact("John Smith", "jsmith@gmail.com","800-624-123","CS",R.drawable.avatar_m_1));
        contacts.add(new Contact("Jane Doe", "jdoe@yahoo.com","800-724-124","CS",R.drawable.avatar_f_1));

        getSupportFragmentManager().beginTransaction()
                .add(R.id.contentView, new ContactsFragment(), "contacts_fragment")
                .commit();
    }

    @Override
    public ArrayList<Contact> getList() {
        return contacts;
    }

    @Override
    public void setIconId(int iconId){
        this.iconID = iconId;
    }

    @Override
    public int getIconId(){
        return iconID;
    }

    @Override
    public void resetID(){
        this.iconID = R.drawable.select_avatar;
    }

}
