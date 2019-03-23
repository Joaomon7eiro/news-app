package com.example.engfitness53.newsapp;

public class News {
    private String mImageLink;
    private String mSource;
    private String mTitle;
    private String mDate;
    private String mUrl;

    public News(String imageLink, String source, String title, String date, String url) {
        mImageLink = imageLink;
        mSource = source;
        mTitle = title;
        mDate = date;
        mUrl = url;
    }

    public String getImageLink() {
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
