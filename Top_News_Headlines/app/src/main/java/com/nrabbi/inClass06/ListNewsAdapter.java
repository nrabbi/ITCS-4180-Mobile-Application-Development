// Nazmul Rabbi
// ListNewsAdapter.java
// In Class Assignment 06 : ITCS 4180
// Group 20

package com.nrabbi.inClass06;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;

class ListNewsViewHolder {
    ImageView galleryImage;
    TextView title, sdetails, time;
}

class ListNewsAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    public int getCount() {
        return data.size();
    }
    public Object getItem(int position) {
        return position;
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ListNewsViewHolder holder = null;
        if (convertView == null) {
            holder = new ListNewsViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.list_row, parent, false);
            holder.galleryImage = (ImageView) convertView.findViewById(R.id.galleryImage);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.sdetails = (TextView) convertView.findViewById(R.id.sdetails);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        } else {
            holder = (ListNewsViewHolder) convertView.getTag();
        }

        holder.galleryImage.setId(position);
        holder.title.setId(position);
        holder.sdetails.setId(position);
        holder.time.setId(position);
        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);

        try{
            holder.title.setText(song.get(MainActivity.TITLE));
            holder.time.setText(song.get(MainActivity.PUBLISHEDAT));
            holder.sdetails.setText(song.get(MainActivity.DESCRIPTION));

            if(song.get(MainActivity.URLTOIMAGE).toString().length() < 5)
            {
                holder.galleryImage.setVisibility(View.GONE);
            }
            else{
                Picasso.with(activity)
                        .load(song.get(MainActivity.URLTOIMAGE).toString())
                        .resize(300, 200)
                        .into(holder.galleryImage);
            }
        }catch(Exception e) {}

        return convertView;
    }

    public ListNewsAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
    }
}