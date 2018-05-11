// Nazmul Rabbi & Dyrell Cole
// TopPaidAppsFragment.java
// ITCS 4180 : Homework 3
// Group 20

package com.example.nrabbi.Homework3;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class TopPaidAppsFragment extends Fragment implements FetchDataAsync.IData{
    ArrayList<App> appArrayList = new ArrayList<>();
    ListView listView;
    ArrayList<App> favArrayList ;
    AppAdapter adapter;
    public static final String favorite_key = "favorite";
    ArrayList<App> appSortIncrease = new ArrayList<>();
    ArrayList<App> appSortDecrease = new ArrayList<>();
    ProgressBar pb;
    TextView load;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;


    public TopPaidAppsFragment(){
        //Required Empty Constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_paid_apps_fragment, container, false);
        setHasOptionsMenu(true);
        pb = (ProgressBar)view.findViewById(R.id.progressBar);
        load = (TextView)view.findViewById(R.id.textView);
        listView = (ListView)view.findViewById(R.id.listView);
        pb.setVisibility(View.VISIBLE);
        load.setVisibility(View.VISIBLE);
        new FetchDataAsync((FetchDataAsync.IData) TopPaidAppsFragment.this).execute("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");

        //Intent intent = new Intent(getActivity(), TopPaidApps.class);
       // intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        //startActivity(intent);

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout,menu);
        sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        super.onCreateOptionsMenu(menu,inflater);
    }


    public ArrayList<App> getFavArrayList(){
        return favArrayList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh_list:
                Log.d("demo","Refreshing List");
                new FetchDataAsync(TopPaidAppsFragment.this).execute("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");
                if(appArrayList.size()==0){
                    Toast.makeText(getActivity(),"Try again",Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d("demo","Success!!");
                    adapter = new AppAdapter(getActivity(), R.layout.row_item_layout, appArrayList);
                    listView.setAdapter(adapter);
                    adapter.setNotifyOnChange(true);
                }
                break;

            case R.id.favorites:
                Log.d("demo","Showing Favorites");
                favArrayList = new ArrayList<>();
                for(int i=0; i<appArrayList.size();i++){
                    App app =new App();
                    app = appArrayList.get(i);
                    if(sharedpreferences.contains(app.getName())&& !favArrayList.contains(app.getName())){
                        // if(!favArrayList.contains(app.getImageUrl()))
                        favArrayList.add(app);
                        Log.d("demo","Success size!!............."+favArrayList.size());

                    }else{
                        Log.d("demo","Success!!............."+i);
                    }
                }

                Log.d("demo","Showing Favorites"+favArrayList);

                Intent intent = new Intent(getActivity(),Favorites.class);
                intent.putExtra(favorite_key, favArrayList);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

                break;

            case R.id.sort_increase:
                Log.d("demo","Sorting Increasingly");
                appSortIncrease = appArrayList;

                Collections.sort(appSortIncrease, new Comparator<App>() {
                    @Override
                    public int compare(App p1, App p2) {
                        return p1.getPrice().compareTo(p2.getPrice());
                    }
                });
                adapter = new AppAdapter(getActivity(), R.layout.row_item_layout, appSortIncrease);
                listView.setAdapter(adapter);
                adapter.setNotifyOnChange(true);

                break;

            case R.id.sort_decrease:
                Log.d("demo","Sorting Decreasingly");
                appSortDecrease = appArrayList;

                Collections.sort(appSortDecrease, new Comparator<App>() {
                    @Override
                    public int compare(App p1, App p2) {
                        return p2.getPrice().compareTo(p1.getPrice());
                    }
                });
                adapter = new AppAdapter(getActivity(), R.layout.row_item_layout, appSortDecrease);
                listView.setAdapter(adapter);
                adapter.setNotifyOnChange(true);

                break;

            default:
                Log.d("demo","Something is Wrong!!");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume() {
        super.onResume();
        adapter = new AppAdapter(getActivity(), R.layout.row_item_layout, appArrayList);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
    }

    @Override
    public void setUpData(ArrayList<App> apps) {
        appArrayList = apps;
        if(appArrayList.size()==0){
            Toast.makeText(getActivity(),"Try again",Toast.LENGTH_SHORT).show();
        }
        else{
            Log.d("demo","Success!!");
            adapter = new AppAdapter(getActivity(), R.layout.row_item_layout, appArrayList);
            pb.setVisibility(View.INVISIBLE);
            load.setVisibility(View.INVISIBLE);
            listView.setAdapter(adapter);
            adapter.setNotifyOnChange(true);
        }
    }


    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public SharedPreferences getSharedPreferences(String myPREFERENCES, int modeAppend) {
        return null;
    }

}
