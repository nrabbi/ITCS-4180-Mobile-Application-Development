package com.example.nrabbi.midterm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<Article> {
    private Context ctx;

    public NewsAdapter(Context context, int resource, List<Article> objects) {
        super(context, resource, objects);
        this.ctx = context;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Article article = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_view, parent, false);

        TextView textviewTitle = convertView.findViewById(R.id.tv_title);
        //TextView textviewDate = convertView.findViewById(R.id.tv_date);
        TextView textviewDescription = convertView.findViewById(R.id.tv_description);
        //ImageView imageviewNews = convertView.findViewById(R.id.tv_image);

        textviewTitle.setText(article.title);
        //textviewDate.setText(article.datetime);
        textviewDescription.setText(article.description);
        //Picasso.with(ctx).load(article.imageURL).into(imageviewNews);

        return convertView;

    }
}
