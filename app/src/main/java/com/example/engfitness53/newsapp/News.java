package com.example.engfitness53.newsapp;

public class News {
    private int mImageResourceId;
    private String mSource;
    private String mTitle;
    private String mDate;
    private String mUrl;

    public News(int imageResourceId, String source, String title, String date, String url) {
        mImageResourceId = imageResourceId;
        mSource = source;
        mTitle = title;
        mDate = date;
        mUrl = url;
    }

    public int getImageResourceId() {
        return mImageResourceId;
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
