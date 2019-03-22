package com.example.engfitness53.newsapp;


import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    private static final int LOADER_ID = 1;
    private static final String BASE_URL =
            "https://newsapi.org/v2/top-headlines?";

    ArrayList<News> mNewsArrayList = new ArrayList<>();
    NewsArrayAdapter mNewsArrayAdapter;
    String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewsArrayAdapter = new NewsArrayAdapter(this, mNewsArrayList);

        View newsListViewHeader = getLayoutInflater().inflate(R.layout.list_view_header, null);

        ListView newsListView = findViewById(R.id.news_list_view);

        newsListView.setAdapter(mNewsArrayAdapter);
        newsListView.addHeaderView(newsListViewHeader);

        Uri url = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter("country", "br")
                .appendQueryParameter("apiKey", getString(R.string.api_key))
                .build();

        mUrl = url.toString();

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, this);
    }


    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new NewsLoader(this, mUrl);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> news) {
        mNewsArrayAdapter.clear();
        if (news != null) {
            mNewsArrayAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {
        mNewsArrayAdapter.clear();
    }
}
