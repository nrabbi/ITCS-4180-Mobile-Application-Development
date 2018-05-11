// Nazmul Rabbi & Dyrell Cole
// AppAdapter.java
// ITCS 4180 : Homework 3
// Group 20

package com.example.nrabbi.Homework3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class AppAdapter extends ArrayAdapter<App> {
    Context mContext;
    int mRes;
    List<App> mData;
    ImageButton imageButton;
    SharedPreferences sharedpreferences;
    public static final String Name = "nameKey";
    public static final String Url = "urlKey";
    public static final String Price = "priceKey";
    public static final String MyPREFERENCES = "MyPrefs" ;
    ArrayList<App> favorites = new ArrayList<>();

    public AppAdapter(Context context, int resource, List<App> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mData = objects;
        this.mRes = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mRes,parent, false);
        }

        final App app = mData.get(position);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);
        final String imgUrl = app.getImageUrl();
        Picasso.with(mContext).load(imgUrl).into(imageView);
        final TextView name = (TextView)convertView.findViewById(R.id.textViewName);
        name.setText(app.getName());
        double price = Double.parseDouble(app.getPrice());
        final double priceRounded = Math.round(price * 100D) / 100D;
        TextView appPrice = (TextView)convertView.findViewById(R.id.textViewPrice);
        appPrice.setText("Price: USD "+priceRounded);
        imageButton = (ImageButton)convertView.findViewById(R.id.imageButton);
        sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if(sharedpreferences.getAll().size()==0 ){

            imageButton.setBackgroundResource(R.drawable.white_star);
            notifyDataSetChanged();
        }
        else if(sharedpreferences.contains(app.getName())){
            imageButton.setBackgroundResource(R.drawable.black_star);
            notifyDataSetChanged();
        }
        else{
            imageButton.setBackgroundResource(R.drawable.white_star);
            notifyDataSetChanged();
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedpreferences.contains(app.getName())){

                    new AlertDialog.Builder(mContext)
                            .setTitle("Remove from Favorites").setCancelable(false)
                            .setMessage("Are you sure you want to delete this App from favorites?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    imageButton.setBackgroundResource(R.drawable.white_star);
                                    sharedpreferences.edit().remove(app.getName()).commit();
                                    notifyDataSetChanged();
                                    favorites.remove(app);
                                    Log.d("demo","fav.... "+favorites.toString());
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    Log.d("demo","contains");
                }else{

                    new AlertDialog.Builder(mContext)
                            .setTitle("Add to Favorites").setCancelable(false)
                            .setMessage("Are you sure you want to add this App to favorites?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with add
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString(app.getName(), app.getName());
                                    editor.commit();
                                    favorites.add(app);
                                    Log.d("demo","fav.... "+favorites.toString());

                                    Log.d("demo","shared "+sharedpreferences.getAll().size());
                                    imageButton.setBackgroundResource(R.drawable.black_star);
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            }
        });

        return convertView;
    }
}
