package com.example.engfitness53.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<News> newsArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsArrayList.add(new News(R.drawable.ic_launcher_background,
                "fonte", "titulo", "11/20/2424", "https://www.google.com"));
        newsArrayList.add(new News(R.drawable.ic_launcher_background,
                "fonte", "titulo", "12/20/2424", "https://www.google.com"));
        newsArrayList.add(new News(R.drawable.ic_launcher_background,
                "fonte", "titulo", "13/20/2424", "https://www.google.com"));
        newsArrayList.add(new News(R.drawable.ic_launcher_background,
                "fonte", "titulo", "14/20/2424", "https://www.google.com"));

        NewsArrayAdapter newsArrayAdapter =
                new NewsArrayAdapter(this, newsArrayList, R.layout.news_list_item);

        View newsListViewHeader = getLayoutInflater().inflate(R.layout.list_view_header, null);

        ListView newsListView = findViewById(R.id.news_list_view);

        newsListView.setAdapter(newsArrayAdapter);
        newsListView.addHeaderView(newsListViewHeader);
    }
}
