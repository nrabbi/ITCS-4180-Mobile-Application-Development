// Nazmul Rabbi & Dyrell Cole
// In Class Assignment 11
// GetDataAsync.java
// Group 20

package com.example.nrabbi.inclass11;

import android.os.AsyncTask;
import com.google.android.gms.maps.model.LatLng;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

public class GetDataAsync extends AsyncTask<String, Void, ArrayList<Coordinates>> {
    MapsActivity activity;

    public GetDataAsync(MapsActivity activity){
        this.activity = activity;
    }

    @Override
    protected ArrayList<Coordinates> doInBackground(String... strings) {
        String jsonString = "";
        ArrayList<Coordinates> coordinates = new ArrayList<>();
        InputStream is = activity.getResources().openRawResource(R.raw.trip);
        Writer writer = new StringWriter();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = reader.readLine();

            while (line != null){
                writer.write(line);
                line = reader.readLine();
            }
            jsonString = writer.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<LatLng> latpts = new ArrayList<>();

        JSONObject root = null;
        try {
            root = new JSONObject(jsonString);
            JSONArray coord = root.getJSONArray("points");

            for (int i=0;i<coord.length();i++) {
                JSONObject sourceObject = coord.getJSONObject(i);
                latpts.add(new LatLng(sourceObject.getDouble("latitude"), sourceObject.getDouble("longitude")));
                Coordinates coordinate = new Coordinates(sourceObject.getDouble("latitude"), sourceObject.getDouble("longitude"));
                coordinates.add(coordinate);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return coordinates;
    }

    @Override
    protected void onPostExecute(ArrayList<Coordinates> coordinates) {
        super.onPostExecute(coordinates);
        activity.setUpPoints(coordinates);
    }
}
