package com.example.engfitness53.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsArrayAdapter extends ArrayAdapter {

    private int mLayoutResourceId;

    public NewsArrayAdapter(Context context, ArrayList<News> newsArrayList, int layoutResourceId) {
        super(context, 0, newsArrayList);
        //mLayoutResourceId = R.layout.news_list_item;
        mLayoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listViewItem = convertView;

        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(mLayoutResourceId, parent, false);
        }

        News news = (News) getItem(position);

        TextView source = listViewItem.findViewById(R.id.source);
        source.setText(news.getSource());

        TextView title = listViewItem.findViewById(R.id.title);
        title.setText(news.getTitle());

        TextView date = listViewItem.findViewById(R.id.date);
        date.setText(news.getDate());

        ImageView newsImage = listViewItem.findViewById(R.id.image);
        newsImage.setImageResource(news.getImageResourceId());

        return listViewItem;
    }
}
