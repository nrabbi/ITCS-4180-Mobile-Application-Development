// Nazmul Rabbi
// MainActivity.java
// In Class Assignment 06 : ITCS 4180
// Group 20

package com.nrabbi.inClass06;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String URL = "url";
    static final String URLTOIMAGE = "urlToImage";
    static final String PUBLISHEDAT = "publishedAt";
    private ProgressBar loadingBar;
    private int currentProgress = 0;
    private Handler progressHandler = new Handler();
    String [] newsCategory = {"Business", "Entertainment", "General", "Health" ,"Empty List"};
    ListView listNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listNews = (ListView) findViewById(R.id.listNews);
        Button goButton = (Button) findViewById(R.id.goBtn);
        loadingBar = (ProgressBar) findViewById(R.id.progressBar);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Select from the list");
                builder.setItems(newsCategory, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (isConnected()) {
                            final TextView categoryName = (TextView) findViewById(R.id.category);
                            categoryName.setText(newsCategory[i].toString());
                            DownloadNews newsTask = new DownloadNews();
                            newsTask.execute();
                        }
                        else
                        {
                            ListView listView = (ListView)findViewById(R.id.listNews);
                            listView.setAdapter(null);
                            Toast.makeText(MainActivity.this, "Error! Not Connected to the internet.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
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

    class DownloadNews extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            currentProgress = 0;
        }

        protected String doInBackground(String... args) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (currentProgress < 95){
                        currentProgress++;
                        android.os.SystemClock.sleep(15);
                        progressHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                loadingBar.setProgress(currentProgress);
                            }
                        });
                    }
                }
            }).start();

            TextView categoryName = (TextView) findViewById(R.id.category);
            String xml = "";
            String urlParameters = "";
            String test = "https://newsapi.org/v2/top-headlines?country=us&category="+categoryName.getText().toString()+"&sortBy=top&apiKey=9d4e3b538e2142828eaf253f082d6b72";
            xml = Function.excuteGet(test, urlParameters);
            Log.d("URL", test);
            return  xml;
        }

        @Override
        protected void onPostExecute(String xml) {
            final ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
            final ListView lv1 = (ListView) findViewById(R.id.listNews);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (currentProgress < 100){
                        currentProgress++;
                        android.os.SystemClock.sleep(5);
                        progressHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                loadingBar.setProgress(currentProgress);
                            }
                        });
                    }
                }
            }).start();

            lv1.setAdapter(new ListNewsAdapter(MainActivity.this, dataList));

            if(xml.length()>47){ // Just checking if not empty
                try {
                    final JSONObject jsonResponse = new JSONObject(xml);
                    final JSONArray jsonArray = jsonResponse.optJSONArray("articles");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(TITLE, jsonObject.optString(TITLE).toString());
                        map.put(DESCRIPTION, jsonObject.optString(DESCRIPTION).toString());
                        map.put(URL, jsonObject.optString(URL).toString());
                        map.put(URLTOIMAGE, jsonObject.optString(URLTOIMAGE).toString());
                        map.put(PUBLISHEDAT, jsonObject.optString(PUBLISHEDAT).toString());
                        dataList.add(map);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }

                listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                        Intent i = new Intent(MainActivity.this, DetailsActivity.class);
                        i.putExtra("url", dataList.get(+position).get(URL));
                        startActivity(i);
                    }
                });

            }else{
                Toast.makeText(getApplicationContext(), "No news found", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
