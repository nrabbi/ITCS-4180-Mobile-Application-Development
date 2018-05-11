// Group 20 : Nazmul Rabbi and Dyrell Cole
// ITCS 4180 : In Class Assignment #07
// ContactsAdapter.java
// 3/20/18

package com.example.nrabbi.inclass07;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class ContactsAdapter extends ArrayAdapter<Contact> {
    
    public ContactsAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact contact = getItem(position);

        ViewP viewP;
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_item, parent, false);
            viewP = new ViewP();
            viewP.name = (TextView) convertView.findViewById(R.id.txtName);
            viewP.email = (TextView) convertView.findViewById(R.id.txtPhone);
            viewP.phone = (TextView) convertView.findViewById(R.id.txtEmail);
            viewP.dept = (TextView) convertView.findViewById(R.id.txtDept);
            viewP.profilepic = (ImageView) convertView.findViewById(R.id.imgProfile);
            convertView.setTag(viewP);

        } else {
            viewP = (ViewP) convertView.getTag();
        }
        
        viewP.name.setText(contact.name);
        viewP.email.setText(contact.email);
        viewP.phone.setText(contact.phone);
        viewP.dept.setText(contact.dept);
        viewP.profilepic.setImageDrawable(getContext().getResources().getDrawable(contact.image));
        return convertView;
    }

    private static class ViewP{
        TextView name;
        TextView email;
        TextView phone;
        TextView dept;
        ImageView profilepic;
    }
}
