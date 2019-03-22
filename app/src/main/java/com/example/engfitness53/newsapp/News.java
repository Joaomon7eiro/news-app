package com.example.engfitness53.newsapp;

import android.graphics.Bitmap;

public class News {
    private Bitmap mImageLink;
    private String mSource;
    private String mTitle;
    private String mDate;
    private String mUrl;

    public News(Bitmap imageLink, String source, String title, String date, String url) {
        mImageLink = imageLink;
        mSource = source;
        mTitle = title;
        mDate = date;
        mUrl = url;
    }

    public Bitmap getImageLink() {
        return mImageLink;
    }

    public String getSource() {
        return mSource;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }
}
