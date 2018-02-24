// Nazmul Rabbi
// In Class Assignment 04
// MainActivity.java
// Group 20


package com.example.nrabbi.inclass04;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static ArrayList result = new ArrayList<String>();
    private static int imgIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button searchBtn = (Button) findViewById(R.id.goBtn);
        final Button prevBtn = (Button) findViewById(R.id.prevBtn);
        final Button nextBtn = (Button) findViewById(R.id.nextBtn);
        final EditText keywordInput = (EditText) findViewById(R.id.keywordInput);

        prevBtn.setEnabled(false);
        nextBtn.setEnabled(false);


        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConencted()) {
                    Toast.makeText(getApplicationContext(), "Error! Unable to Connect to the Internet...",
                            Toast.LENGTH_SHORT).show();
                }

                if(imgIndex>=1 && isConencted()) {
                    imgIndex--;
                    new GetImage(((ImageView) findViewById(R.id.display)),MainActivity.this).execute(result.get(imgIndex).toString());
                }

                else if (imgIndex == 0 && isConencted()){
                    imgIndex = result.size()-1;
                    new GetImage(((ImageView) findViewById(R.id.display)),MainActivity.this).execute(result.get(imgIndex).toString());
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConencted()) {
                    Toast.makeText(getApplicationContext(), "Error! Unable to Connect to the Internet...",
                            Toast.LENGTH_SHORT).show();
                }

                if(imgIndex < result.size()-1 && isConencted()) {
                    imgIndex++;
                    new GetImage(((ImageView) findViewById(R.id.display)),MainActivity.this).execute(result.get(imgIndex).toString());
                }
                else if (imgIndex == result.size()-1 && isConencted()){
                    imgIndex = 0;
                    new GetImage(((ImageView) findViewById(R.id.display)),MainActivity.this).execute(result.get(imgIndex).toString());
                }
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userInput = keywordInput.getText().toString();
                Boolean keywordCheck;

                if (userInput.toLowerCase().equals("android") || userInput.toLowerCase().equals("aurora") || userInput.toLowerCase().equals("uncc") || userInput.toLowerCase().equals("winter") || userInput.toLowerCase().equals("wonders")){
                    keywordCheck = true;
                }
                else{
                    keywordCheck = false;
                }

                if (isConencted() && keywordCheck == true) {
                    Log.d("User Input", keywordCheck.toString());;
                    Log.d("Internet Connection", "Connected to the internet...AsyncTask Executed!");
                    new ReadApi().execute("http://dev.theappsdr.com/apis/photos/index.php?keyword=" + keywordInput.getText());
                    Log.d("URL Input", "http://dev.theappsdr.com/apis/photos/index.php?keyword=" + keywordInput.getText());
                }
                else
                {
                    if (keywordCheck == false) {
                        ImageView img = (ImageView) findViewById(R.id.display);
                        result.clear();
                        img.setImageResource(0);
                        prevBtn.setEnabled(false);
                        nextBtn.setEnabled(false);
                        Log.d("User Input", keywordCheck.toString());
                        Toast.makeText(getApplicationContext(), "Invalid Input: " + keywordInput.getText(),
                                Toast.LENGTH_SHORT).show();
                    }

                    if (!isConencted()) {
                        Toast.makeText(getApplicationContext(), "Error! Unable to Connect to the Internet...",
                                Toast.LENGTH_SHORT).show();
                        Log.d("Internet Connection", "Error! Not Connected to the internet...");
                    }
                }
            }
        });

    }

    // method that checks if the android device is connected to the internet
    private boolean isConencted(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo == null || !networkInfo.isConnected()){
            return false;
        }
        else {
            return true;
        }
    }

    // asynctask that reads the api
    private class ReadApi extends AsyncTask<String, Void, ArrayList>{
        @Override
        protected ArrayList doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection)url.openConnection();
                connection.connect();

                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = "";
                    ArrayList aList = new ArrayList();

                    while((line = reader.readLine()) != null){
                        aList.add(line);
                    }
                    imgIndex = 0;
                    result = aList;
                }

            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            } finally {
                if (connection != null){
                    connection.disconnect();
                }

                if (reader != null){
                    try {
                        reader.close();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
            return result;

        }

        @Override
        protected void onPostExecute(ArrayList result) {
            if (result != null){
                new GetImage(((ImageView) findViewById(R.id.display)),MainActivity.this).execute(result.get(imgIndex).toString());
                for (int i = 0; i<result.size(); i++) {
                    Log.d("On Post Execute", result.get(i).toString());
                }
            }
            else{
                Log.d("On Post Execute", "Null Result");
            }
        }
    }
}
