// Nazmul Rabbi & Dyrell Cole
// ShowArticles.java
// In Class Assignment # 5
// Group 20

package com.example.nrabbi.inclass05;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;

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

public class ShowArticles extends AppCompatActivity implements View.OnClickListener{

    Source source;
    String id;
    ArrayList<Article> articlesArrayList = new ArrayList<>();
    String url = "https://newsapi.org/v1/articles", apiKey = "eb9601bc3dbd4ae9950dad70a9fa6a48";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_articles);

        if(getIntent() != null && getIntent().getExtras() != null) {
            source = (Source) getIntent().getExtras().get("SOURCE");
            setTitle(source.name);
            new GetArticlesAsync(articlesArrayList,source.id).execute(url);
        }
    }


    private class GetArticlesAsync extends AsyncTask<String, Void, ArrayList<Article> > {
        private ArrayList<Article> articles;
        private String keyword;

        public GetArticlesAsync(ArrayList<Article> keywords, String source) {
            this.articles = keywords;
            this.keyword = source;
        }

        ProgressDialog loadDictionary;
        @Override
        protected void onPreExecute() {
            loadDictionary = new ProgressDialog(ShowArticles.this);
            loadDictionary.setCancelable(false);
            loadDictionary.setMessage("Loading Stories ...");
            loadDictionary.setProgress(0);
            loadDictionary.show();
        }

        @Override
        protected ArrayList<Article> doInBackground(String... strings) {
            StringTokenizer st = null;

            try {
                URL url = new URL(strings[0] + "?source=" + keyword + "&apiKey=" + apiKey);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                String json = IOUtils.toString(connection.getInputStream(), "UTF8");
                JSONObject root = new JSONObject(json);
                JSONArray keys = root.getJSONArray("articles");

                for(int i = 0; i < keys.length();i++){

                    Article currentArticle = new Article();
                    JSONObject currentObj = keys.getJSONObject(i);
                    currentArticle.author = currentObj.getString("author");
                    currentArticle.title = currentObj.getString("title");
                    currentArticle.description = currentObj.getString("description");
                    currentArticle.url = currentObj.getString("url");
                    currentArticle.urlToImage = currentObj.getString("urlToImage");
                    currentArticle.publishedAt = currentObj.getString("publishedAt");
                    articles.add(currentArticle);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return articles;
        }

        @Override
        protected void onPostExecute(ArrayList<Article> strings) {
            if(strings != null){
                loadDictionary.setMessage("Stories Loaded!");
                loadDictionary.setProgress(100);
                loadDictionary.dismiss();
                loadDictionary.setProgress(0);
                generateArticlesOnScreen();

            }else {

            }
        }
    }

    private void generateArticlesOnScreen() {
        ListView listView = (ListView)findViewById(R.id.listview);
        ArticlesAdapter adapter = new ArticlesAdapter(this, R.layout.article_layout, articlesArrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Article clickedSource = articlesArrayList.get(position);
                        WebView webview = new WebView(ShowArticles.this);
                        setContentView(webview);
                        webview.loadUrl(clickedSource.url);
                    }
                }
        );
    }

    @Override
    public void onClick(View view) {

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
}
