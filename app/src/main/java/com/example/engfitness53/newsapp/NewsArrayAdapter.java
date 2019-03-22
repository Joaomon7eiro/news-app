package com.example.engfitness53.newsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NewsArrayAdapter extends ArrayAdapter {

    public NewsArrayAdapter(Context context, ArrayList<News> newsArrayList) {
        super(context, 0, newsArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listViewItem = convertView;

        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
        }

        News news = (News) getItem(position);

        TextView source = listViewItem.findViewById(R.id.source);
        source.setText(news.getSource());

        TextView title = listViewItem.findViewById(R.id.title);
        title.setText(news.getTitle());

        TextView date = listViewItem.findViewById(R.id.date);
        date.setText(news.getDate());

        ImageView newsImage = listViewItem.findViewById(R.id.image);
        newsImage.setImageBitmap(news.getImageLink());

        return listViewItem;
    }
}
