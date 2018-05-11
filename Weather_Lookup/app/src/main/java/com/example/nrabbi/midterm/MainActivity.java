// Nazmul Rabbi
// ITCS 4180 : Mid Term
// MainActivity.java

package com.example.nrabbi.midterm;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetArticles.DataInterface{

    private Button goButton;
    private TextView search;
    public ProgressBar loading;
    private ListView listViewNews;
    private String api_key = "84bef3e490af8d12";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewNews = findViewById(R.id.listViewNews);
        loading = findViewById(R.id.pbLoading);
        goButton = findViewById(R.id.button_Go);
        search = findViewById(R.id.searchBox);

        if(!isConnected()){
            Toast.makeText(this, "Please check Internet connection!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Connected to Internet", Toast.LENGTH_SHORT).show();
        }

        //On Click Listenner for Go Button
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected()){
                    loading.setMax(100);
                    loading.setVisibility(View.VISIBLE);
                    loading.setProgress(0);
                    String getURL = null;
                    getURL = "http://autocomplete.wunderground.com/aq?query=" + search.getText().toString();
                    Log.d("URL", getURL);
                    new GetArticles(MainActivity.this, MainActivity.this).execute(getURL);

                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
                else{
                    Toast.makeText(MainActivity.this, "Please check Internet connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
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

    @Override
    public void updateProgress(Integer... progress) {
        loading.setProgress(progress[0]);
    }

    @Override
    public void sendNews(final ArrayList<Article> articles) {
        //Setting up the List View..............
        listViewNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(MainActivity.this, DetailsActivity.class);
                i.putExtra("Title", articles.get(position).title);
                //i.putExtra("url", dataList.get(+position).get(URL));
                //i.putExtra("Description", articles.get(position).description);
                //i.putExtra("img_url", articles.get(position).imageURL);
                i.putExtra("zmw", articles.get(position).datetime);
                Toast.makeText(MainActivity.this, "Current Location " + articles.get(position).title,  Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });
        NewsAdapter adapter = new NewsAdapter(MainActivity.this, R.layout.list_item_view, articles);
        listViewNews.setAdapter(adapter);
    }
}