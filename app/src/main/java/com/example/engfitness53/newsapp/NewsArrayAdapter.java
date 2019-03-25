package com.example.engfitness53.newsapp;

import android.content.Context;

import android.support.annotation.NonNull;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsArrayAdapter extends ArrayAdapter {

    public NewsArrayAdapter(Context context, List<News> newsArrayList) {
        super(context, 0, newsArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
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
        date.setText(formatDate(news.getDate()));

        ImageView newsImage = listViewItem.findViewById(R.id.image);
        Picasso.get().load(news.getImageLink()).placeholder(R.drawable.placeholder_image)
                .into(newsImage);

        return listViewItem;
    }

    private String formatDate(String date) {
        return (String) DateUtils.getRelativeTimeSpanString(getDateInMillis(date),
                System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS);
    }

    private long getDateInMillis(String srcDate) {
        SimpleDateFormat desiredFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        long dateInMillis;
        try {
            Date date = desiredFormat.parse(srcDate);
            dateInMillis = date.getTime();
            return dateInMillis;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
