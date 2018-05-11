// Group 20 : Nazmul Rabbi and Dyrell Cole
// ITCS 4180 : In Class Assignment #07
// SelectAvatarFragment.java
// 3/20/18

package com.example.nrabbi.inclass07;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SelectAvatarFragment extends Fragment implements View.OnClickListener{

    private OnFragmentInteractionListener mListener;

    public SelectAvatarFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_avatar, container, false);
        getActivity().setTitle("Select Avatar");
        view.findViewById(R.id.imgBoy1).setOnClickListener(this);
        view.findViewById(R.id.imgBoy2).setOnClickListener(this);
        view.findViewById(R.id.imgBoy3).setOnClickListener(this);
        view.findViewById(R.id.imgGirl1).setOnClickListener(this);
        view.findViewById(R.id.imgGirl2).setOnClickListener(this);
        view.findViewById(R.id.imgGirl3).setOnClickListener(this);
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
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.imgBoy1:
                mListener.setIconId(R.drawable.avatar_m_1);
                break;
            case R.id.imgBoy2:
                mListener.setIconId(R.drawable.avatar_m_2);
                break;
            case R.id.imgBoy3:
                mListener.setIconId(R.drawable.avatar_m_3);
                break;
            case R.id.imgGirl1:
                mListener.setIconId(R.drawable.avatar_f_1);
                break;
            case R.id.imgGirl2:
                mListener.setIconId(R.drawable.avatar_f_2);
                break;
            case R.id.imgGirl3:
                mListener.setIconId(R.drawable.avatar_f_3);
                break;
        }

        getActivity().getSupportFragmentManager().popBackStack();
    }

    public interface OnFragmentInteractionListener {
        void setIconId(int iconId);
    }
}
