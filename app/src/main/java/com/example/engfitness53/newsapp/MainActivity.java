package com.example.engfitness53.newsapp;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<News>>,
        SearchView.OnQueryTextListener,
        NavigationView.OnNavigationItemSelectedListener {
    private static final int LOADER_ID = 1;
    private static final String BASE_URL =
            "https://newsapi.org/v2/top-headlines?";

    ArrayList<News> mNewsArrayList = new ArrayList<>();
    NewsArrayAdapter mNewsArrayAdapter;
    View mNewsListViewHeader;
    ProgressBar mProgressBar;
    String mCategory;
    String mSearch = "";
    TextView mEmptyView;
    TextView mListTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        mProgressBar = findViewById(R.id.progress_bar);

        mNewsListViewHeader = getLayoutInflater().inflate(R.layout.list_view_header, null);
        mListTitle = mNewsListViewHeader.findViewById(R.id.list_view_header_title);
        mCategory = "general";

        ListView newsListView = findViewById(R.id.news_list_view);
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = (News) parent.getAdapter().getItem(position);
                if (news != null) {
                    Uri url = Uri.parse(news.getUrl());
                    Intent intent = new Intent(Intent.ACTION_VIEW, url);
                    startActivity(intent);
                }
            }
        });

        mEmptyView = findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyView);

        mNewsArrayAdapter = new NewsArrayAdapter(this, mNewsArrayList);
        newsListView.addHeaderView(mNewsListViewHeader, null, false);

        newsListView.setAdapter(mNewsArrayAdapter);

        if (checkInternetConnectivity()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);
        } else {
            mEmptyView.setText(getString(R.string.verify_connection));
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    private boolean checkInternetConnectivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        MenuItem seachBarMenuItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) seachBarMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int i, @Nullable Bundle bundle) {
        mEmptyView.setVisibility(View.GONE);
        mNewsListViewHeader.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        Uri url = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter("q", mSearch)
                .appendQueryParameter("category", mCategory)
                .appendQueryParameter("country", "br")
                .appendQueryParameter("apiKey", getString(R.string.api_key))
                .build();

        return new NewsLoader(this, url.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> news) {
        mProgressBar.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
        mEmptyView.setText(getString(R.string.no_results));


        mNewsArrayAdapter.clear();
        if (news != null) {
            mNewsListViewHeader.setVisibility(View.VISIBLE);
            mNewsArrayAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {
        mNewsArrayAdapter.clear();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        int menuId = menuItem.getItemId();
        switch (menuId) {
            case R.id.business:
                drawerLayout.closeDrawer(GravityCompat.START);
                mCategory = "business";
                break;
            case R.id.entertainment:
                drawerLayout.closeDrawer(GravityCompat.START);
                mCategory = "entertainment";
                break;
            case R.id.health:
                drawerLayout.closeDrawer(GravityCompat.START);
                mCategory = "health";
                break;
            case R.id.science:
                drawerLayout.closeDrawer(GravityCompat.START);
                mCategory = "science";
                break;
            case R.id.sports:
                drawerLayout.closeDrawer(GravityCompat.START);
                mCategory = "sports";
                break;
            case R.id.technology:
                drawerLayout.closeDrawer(GravityCompat.START);
                mCategory = "technology";
                break;
            default:
                drawerLayout.closeDrawer(GravityCompat.START);
                mCategory = "general";
                break;
        }

        if (checkInternetConnectivity()) {
            mSearch = "";
            mListTitle.setText(menuItem.getTitle());
            getLoaderManager().restartLoader(LOADER_ID, null, this);
        } else {
            Toast.makeText(this, getString(R.string.verify_connection),
                    Toast.LENGTH_LONG).show();
        }

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (checkInternetConnectivity()) {
            mSearch = query;
            getLoaderManager().restartLoader(LOADER_ID, null, this);
        } else {
            Toast.makeText(this, getString(R.string.verify_connection),
                    Toast.LENGTH_LONG).show();
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
