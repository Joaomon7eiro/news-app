package com.example.engfitness53.newsapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public final class QueryUtils {

    private QueryUtils(){}

    public static ArrayList<News> fetchNewsData(String urlString) {
        URL url = createUrl(urlString);

        String jsonResponse = "";

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<News> newsArrayList = new ArrayList<>();

        try {

            JSONObject newsJsonObject = new JSONObject(jsonResponse);
            JSONArray articles = newsJsonObject.getJSONArray("articles");

            for (int i = 0; i < articles.length(); i++) {
                JSONObject newsDetails = articles.getJSONObject(i);
                String source = newsDetails.getJSONObject("source").getString("name");
                String title = newsDetails.getString("title");
                String urlLink = newsDetails.getString("url");
                String date = newsDetails.getString("publishedAt");
                String image = newsDetails.getString("urlToImage");

                News news = new News(image, source, title, date, urlLink);
                newsArrayList.add(news);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newsArrayList;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readInputStream(inputStream);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }


        return jsonResponse;
    }

    private static String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = bufferedReader.readLine();
        while (line != null) {
            output.append(line);
            line = bufferedReader.readLine();
        }

        return output.toString();
    }

    private static URL createUrl(String urlString) {
        URL url = null;

        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
