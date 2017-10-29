package com.example.allan.newsapp;

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

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {
    private Context context;
    private int resources;
    private ArrayList<News> newsArrayList;

    public NewsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<News> newsArrayList) {
        super(context, resource, newsArrayList);
        this.context = context;
        this.resources = resource;
        this.newsArrayList = newsArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(resources, null);
        }
        News news = newsArrayList.get(position);
        ImageView image = convertView.findViewById(R.id.image);
        TextView webTitle = convertView.findViewById(R.id.webTitle);
        TextView webPublicationDate = convertView.findViewById(R.id.webPublicationDate);
        TextView authorName = convertView.findViewById(R.id.authorName);
        TextView sectionName = convertView.findViewById(R.id.sectionName);
        Picasso.with(context).load(news.getImage()).into(image);
        webTitle.setText(news.getWebTitle());
        webPublicationDate.setText(news.getWebPublicationDate());
        authorName.setText(news.getAuthorName());
        sectionName.setText(news.getSectionName());
        return convertView;
    }
}
