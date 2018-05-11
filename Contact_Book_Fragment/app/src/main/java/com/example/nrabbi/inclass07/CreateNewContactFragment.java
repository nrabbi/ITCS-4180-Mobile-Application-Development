// Group 20 : Nazmul Rabbi and Dyrell Cole
// ITCS 4180 : In Class Assignment #07
// CreateNewContactFragment.java
// 3/20/18

package com.example.nrabbi.inclass07;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class CreateNewContactFragment extends Fragment {
    int profPicDraw = R.drawable.select_avatar;
    private OnFragmentInteractionListener mListener;

    public CreateNewContactFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_create_new_contact, container, false);
        getActivity().setTitle("Create New Contact");
        view.findViewById(R.id.imgAvatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentView, new SelectAvatarFragment(), "nrabbi")
                        .addToBackStack(null)
                        .commit();
            }
        });

        view.findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                boolean image = true;
                boolean name = true;
                boolean email = true;
                String n = ((TextView)getActivity().findViewById(R.id.txtName)).getText().toString();
                String e = ((TextView)getActivity().findViewById(R.id.txtEmail)).getText().toString();
                String p = ((TextView)getActivity().findViewById(R.id.txtPhone)).getText().toString();
                String d = "SIS";
                profPicDraw = mListener.getIconId();
                RadioGroup rg = (RadioGroup) getActivity().findViewById(R.id.radGroupDept);
                if(rg.getCheckedRadioButtonId() == R.id.radSIS){
                    d = "SIS";
                }
                else if(rg.getCheckedRadioButtonId() == R.id.radBIO){
                    d = "BIO";
                }
                else{
                    d = "CS";
                }
                if(!n.trim().equals(n)){
                    name = false;
                    Toast.makeText(getContext(), "Please Enter a Valid Name without the leading or trailing spaces", Toast.LENGTH_SHORT).show();
                }
                if(n == null || n.length() == 0) {
                    name = false;
                    Toast.makeText(getContext(), "Please Enter a Valid Name that is Not Null", Toast.LENGTH_SHORT).show();
                }
                if(name){
                    if(e.indexOf('@') == e.lastIndexOf('@') && e.indexOf('@') > 0){
                        if(e.indexOf('.', e.indexOf('@') + 2)  > 0 && e.indexOf('.') == e.lastIndexOf('.')){
                            email = true;
                        }
                        else{
                            email = false;
                            Toast.makeText(getContext(), "Please Enter a Valid Email", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        email = false;
                        Toast.makeText(getContext(), "Please Enter a Valid Email", Toast.LENGTH_SHORT).show();
                    }
                }

                if(name && email) {
                    Contact c = new Contact(n,e,p,d,profPicDraw);
                    mListener.getList().add(c);
                    mListener.resetID();
                    getActivity().getSupportFragmentManager().popBackStack();
                }
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        ((ImageView) getActivity().findViewById(R.id.imgAvatar)).setImageDrawable(getResources().getDrawable(mListener.getIconId()));

        getActivity().setTitle("Create New Contact");
        super.onResume();
    }

    public interface OnFragmentInteractionListener {
        ArrayList<Contact> getList();
        int getIconId();
        void resetID();
    }
}
