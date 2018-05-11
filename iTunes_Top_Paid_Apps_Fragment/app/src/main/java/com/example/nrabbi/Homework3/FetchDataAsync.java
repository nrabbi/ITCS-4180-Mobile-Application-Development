// Nazmul Rabbi & Dyrell Cole
// FetchDataAsync.java
// ITCS 4180 : Homework 3
// Group 20

package com.example.nrabbi.Homework3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FetchDataAsync extends AsyncTask<String, Void, ArrayList<App>> {
    IData activity;
    public FetchDataAsync(IData activity) {
        this.activity = activity;
    }
    String iTunesUrl="";
    StringBuilder stringBuilder;
    ArrayList<App> AppList = new ArrayList<>();

    @Override
    protected ArrayList<App> doInBackground(String... params) {
        iTunesUrl = params[0];

        try {
            URL url = new URL(iTunesUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            int status_code = con.getResponseCode();
            Log.d("22222222status",""+status_code);
            if (status_code == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                stringBuilder = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
            }

            Log.d("demo","stringbuilder...."+stringBuilder.toString());
            AppList = JsonAppUtil.parser(stringBuilder.toString());
            Log.d("demo","AppList size "+AppList.size());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return AppList;
    }

    @Override
    protected void onPostExecute(ArrayList<App> apps) {
        super.onPostExecute(apps);
        activity.setUpData(apps);
    }

    static public interface IData {
        public void setUpData(ArrayList<App> apps);
        public Context getContext();

        SharedPreferences getSharedPreferences(String myPREFERENCES, int modeAppend);
    }
}
