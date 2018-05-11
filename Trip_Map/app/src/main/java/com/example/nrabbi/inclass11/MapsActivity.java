// Nazmul Rabbi & Dyrell Cole
// In Class Assignment 11
// MapsActivity.java
// Group 20

package com.example.nrabbi.inclass11;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    Gson gson;
    String jsonString;
    ArrayList<Coordinates> coordinates;
    PolylineOptions lineOptions;
    ArrayList<LatLng> latpts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        new GetDataAsync(this).execute();
        gson = new Gson();
        coordinates = new ArrayList<>();
        InputStream is = getResources().openRawResource(R.raw.trip);
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

        latpts = new ArrayList<>();

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

        PolylineOptions lineOptions = null;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Test Marker
        mMap = googleMap;
        LatLng marker = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(marker).title("Marker in Oceania"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
    }

    public void setUpPoints(ArrayList<Coordinates> c){
        ArrayList<LatLng> points;
        PolylineOptions lineOptions = null;
        points = new ArrayList<>();
        lineOptions = new PolylineOptions();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (int j = 0; j < coordinates.size(); j++) {

            double lat = coordinates.get(j).getLat();
            double lng = coordinates.get(j).getLongitude();
            LatLng position = new LatLng(lat, lng);

            points.add(position);
            builder.include(points.get(j));
        }

        lineOptions.addAll(points);
        lineOptions.width(10);
        lineOptions.color(Color.RED);

        if(lineOptions != null) {
            mMap.addPolyline(lineOptions);
            mMap.addMarker(new MarkerOptions().position(latpts.get(0)).title("Start"));
            mMap.addMarker(new MarkerOptions().position(latpts.get(latpts.size() - 1)).title("End"));
            int width = (int) (getResources().getDisplayMetrics().widthPixels * .1);
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), getResources().getDisplayMetrics().widthPixels,getResources().getDisplayMetrics().heightPixels, width));
        }
    }
}
