// ITCS 4180 : Homework 2
// GetImageAsyncTask.java
// Nazmul Rabbi, Dyrell Cole

package com.nrabbi.hw2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

class GetImageAsyncTask extends AsyncTask<String, Void, Bitmap>
{
    private IData activity;

    GetImageAsyncTask(IData activity) {
        this.activity = activity;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try
        {
            URL url = new URL(params[0]);
            HttpURLConnection _HttpURLConnection = (HttpURLConnection) url.openConnection();
            _HttpURLConnection.setRequestMethod("GET");
            _HttpURLConnection.connect();

            int statusCode = _HttpURLConnection.getResponseCode();

            if(statusCode == HttpURLConnection.HTTP_OK)
            {
                return BitmapFactory.decodeStream(_HttpURLConnection.getInputStream());
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        activity.setTriviaImage(bitmap);
    }

    interface IData {
        void setTriviaImage(Bitmap image);
    }
}
