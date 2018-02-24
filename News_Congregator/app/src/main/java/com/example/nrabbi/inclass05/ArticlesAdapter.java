// Nazmul Rabbi & Dyrell Cole
// ArticlesAdapter.java
// In Class Assignment # 5
// Group 20

package com.example.nrabbi.inclass05;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticlesAdapter extends ArrayAdapter<Article> {

    public ArticlesAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Article> objects) {
        super(context, resource, objects);
    }

    private static class viewP{
        TextView txtTitle;
        TextView txtAuthor;
        TextView txtDate;
        ImageView imgImageImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Article article = getItem(position);

        viewP viewP;
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.article_layout, parent, false);
            viewP = new viewP();
            viewP.txtAuthor = (TextView) convertView.findViewById(R.id.txtAuthor);
            viewP.txtTitle = (TextView) convertView.findViewById(R.id.txtNewsTitle);
            viewP.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            viewP.imgImageImage = (ImageView) convertView.findViewById(R.id.imgURLImage);
            viewP.txtAuthor.setText(article.author);
            viewP.txtDate.setText(article.publishedAt);
            viewP.txtTitle.setText(article.title);

            if(article.urlToImage.equals("") || article.urlToImage == null){
            }
            else
                Picasso.with(getContext()).load(article.urlToImage).into(viewP.imgImageImage);

        } else {
            viewP = (viewP) convertView.getTag();
        }

        return convertView;
    }
}
