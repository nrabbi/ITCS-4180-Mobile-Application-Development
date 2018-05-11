// Nazmul Rabbi
// ITCS 4180 : Mid Term
// GetArticles.java

package com.example.nrabbi.midterm;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetArticles extends AsyncTask<String, Integer, ArrayList<Article>> {
    private Context ctx;
    private ListView listViewNews;
    DataInterface iData;
    private int length;
    GetArticles(Context ctx, DataInterface data){
        this.iData = data;
        this.ctx = ctx;
    }

    @Override
    protected ArrayList<Article> doInBackground(String... params) {
        //for Progress Bar Adding delays.....
        for(int i= 0;i<=25;i++){
            for(int k=0;k<1000000;k++){
                //ADDING DELAY
            }
            publishProgress(i);
        }
        //Get data...............................
        HttpURLConnection connection = null;
        ArrayList<Article> result = new ArrayList<>();

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String json = IOUtils.toString(connection.getInputStream(), "UTF8");

                JSONObject root = new JSONObject(json);
                JSONArray articles = root.getJSONArray("RESULTS");
                Log.d("total_results", String.valueOf(articles.length()));
                length = Integer.valueOf(articles.length());

                for (int i=0;i<articles.length();i++) {
                    JSONObject articleJSON = articles.getJSONObject(i);
                    Article article = new Article();
                    article.title = articleJSON.getString("name");
                    article.description = articleJSON.getString("c");
                    article.datetime = articleJSON.getString("zmw");
                    //article.imageURL = "http://image.tmdb.org/t/p/w185/" + articleJSON.getString("poster_path");
                    //article.description = articleJSON.getString("overview");
                    result.add(article);
                }
            }else{
                Toast.makeText(ctx, "Check Internet!!!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //Handle Exceptions
        } finally {
            //Close the connections
        }
        //Got Data adding more delays for Progress Bar...............................
        for(int i= 26; i<=100;i++){
            for(int k=0;k<1000000;k++){
                //ADDING DELAY
            }
            publishProgress(i);
        }

        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Article> articles) {
        super.onPostExecute(articles);
        iData.sendNews(articles);

        if(length==0){
            Toast.makeText(ctx, "No Such Location Found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        iData.updateProgress(values);
    }

    public static interface DataInterface{
        public void updateProgress(Integer... progress);
        public void sendNews(ArrayList<Article> articles);
    }

}
