package com.example.nrabbi.Homework3;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {
    ListView listView;
    ArrayList<App> favList = new ArrayList<>();
    FavAdapter adapter;

    public FavoritesFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        favList = (ArrayList<App>)getActivity().getIntent().getSerializableExtra(TopPaidAppsFragment.favorite_key);
        Log.d("demo","fav activity " + favList.toString());
        listView = (ListView)view.findViewById(R.id.listViewFav);
        adapter = new FavAdapter(getActivity(), R.layout.row_item_layout, favList);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);

        return view;
    }
}
