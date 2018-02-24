// Nazmul Rabbi
// In Class Assignment 04
// GetImage.java
// Group 20

package com.example.nrabbi.inclass04;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// asynctask that gets the image
public class GetImage extends AsyncTask<String, Void, Bitmap> {

    ImageView img;
    Context context;
    ProgressDialog progressDialog;

    public GetImage(ImageView imgv, Context c) {
        img = imgv;
        context = c;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Photo ...");
        progressDialog.setProgress(0);
        progressDialog.show();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        HttpURLConnection connection = null;
        Bitmap img = null;

        try {
            URL url = new URL(params[0]);
            Log.d("Image URL to Download", params[0]);
            connection = (HttpURLConnection)url.openConnection();
            connection.connect();

            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                img = BitmapFactory.decodeStream(connection.getInputStream());
            }

        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }

        return img;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        TextView progress = (TextView)((MainActivity) context).findViewById(R.id.progressInfo);
        Button prevBtn = (Button) ((MainActivity) context).findViewById(R.id.prevBtn);
        Button nextBtn = (Button) ((MainActivity) context).findViewById(R.id.nextBtn);

        if (bitmap != null && img != null){
            progressDialog.setMessage("Photo Loaded!");
            progressDialog.setProgress(100);
            progressDialog.dismiss();
            progressDialog.setProgress(0);
            img.setImageBitmap(bitmap);
            prevBtn.setEnabled(true);
            nextBtn.setEnabled(true);
        }

    }
}
