// Group 20 : Nazmul Rabbi and Dyrell Cole
// ITCS 4180 : In Class Assignment #07
// ContactsFragment.java
// 3/20/18

package com.example.nrabbi.inclass07;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

public class ContactsFragment extends Fragment {

    ContactsAdapter adapter;
    private OnFragmentInteractionListener mListener;

    public ContactsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        getActivity().setTitle("Contacts");
        view.findViewById(R.id.btnCreateNew).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentView, new CreateNewContactFragment(), "new_contacts_fragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        final ListView listView = (ListView)view.findViewById(R.id.listViewContacts);
        adapter = new ContactsAdapter(this.getContext() , R.layout.contact_item, mListener.getList());
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                adapter.remove(adapter.getItem(position));
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        getActivity().setTitle("Contacts");
        super.onResume();
    }

    public interface OnFragmentInteractionListener {
        ArrayList<Contact> getList();
    }
}
