// Nazmul Rabbi & Dyrell Cole
// MainActivity.java
// In Class Assignment # 5
// Group 20

package com.example.nrabbi.inclass05;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private String SOURCE_URL = "https://newsapi.org/v1/sources";
    ArrayList<Source> sources = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.sourcesView);
        if(isConnected()){
            new GetSourcesAsync(sources).execute(SOURCE_URL);
        }

        else {
            Toast.makeText(MainActivity.this, "No Internet!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

    public void generateListView(){
        String[] names = new String[sources.size()];
        for(int i = 0; i < sources.size(); i++){
            names[i] = sources.get(i).name;
        }

        ListAdapter sourceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        listView.setAdapter(sourceAdapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String item = String.valueOf(parent.getItemAtPosition(position));
                        Source clickedSource = (Source) sources.get(position);
                        Intent intent = new Intent(MainActivity.this, ShowArticles.class);
                        intent.putExtra("SOURCE", clickedSource);
                        startActivity(intent);
                    }
                }
        );

    }

    private class GetSourcesAsync extends AsyncTask<String, Void, ArrayList<Source> > {
        ProgressDialog loadDictionary;
        private ArrayList<Source> sources;
        public GetSourcesAsync(ArrayList<Source> keywords) {
            this.sources = keywords;
        }

        @Override
        protected void onPreExecute() {
            loadDictionary = new ProgressDialog(MainActivity.this);
            loadDictionary.setCancelable(false);
            loadDictionary.setMessage("Loading Sources ...");
            loadDictionary.setProgress(0);
            loadDictionary.show();
        }

        @Override
        protected ArrayList<Source> doInBackground(String... strings) {
            StringTokenizer st = null;

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                String json = IOUtils.toString(connection.getInputStream(), "UTF8");
                JSONObject root = new JSONObject(json);
                JSONArray keys = root.getJSONArray("sources");

                for(int i = 0; i < keys.length();i++){
                    Source currentSource = new Source();
                    JSONObject currentObj = keys.getJSONObject(i);
                    currentSource.id = currentObj.getString("id");
                    currentSource.name = currentObj.getString("name");
                    sources.add(currentSource);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return sources;
        }

        @Override
        protected void onPostExecute(ArrayList<Source> strings) {
            if(strings != null){
                loadDictionary.setMessage("Sources Loaded!");
                loadDictionary.setProgress(100);
                loadDictionary.dismiss();
                loadDictionary.setProgress(0);
                generateListView();

            }else {

            }
        }
    }
}
