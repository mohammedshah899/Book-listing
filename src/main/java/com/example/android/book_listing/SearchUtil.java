package com.example.android.book_listing;

import android.text.TextUtils;
import android.util.Log;

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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SearchUtil {

    private static final String LOG_TAG = SearchUtil.class.getSimpleName();

    private SearchUtil(){

    }

    public static List<Book> fetchBookData(String stringUrl){
        URL url = CreateUrl(stringUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG, "Error Occurred", e);
        }
        return extractFromJson(jsonResponse);
    }

    private static URL CreateUrl(String stringUrl){
        URL url = null;
        try {
            url = new URL(stringUrl);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG, "Error Occurred", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";
        if (url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else {
                Log.e(LOG_TAG, "Help Error, Forced Stop" + urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG, "Error occurred", e);
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Book> extractFromJson(String jsonResponse){
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        List<Book> books = new ArrayList<>();
        float averageRating;
        try {
            JSONObject baseObject = new JSONObject(jsonResponse);
            JSONArray bookArray = baseObject.getJSONArray("items");
            for (int i = 0; i < bookArray.length(); i++){
                JSONObject currentBook = bookArray.getJSONObject(i);
                JSONObject properties = currentBook.getJSONObject("volumeInfo");
                String title = properties.getString("title");
                String publisher = properties.getString("publisher");
                String publishedDate = properties.getString("publishedDate");
                String language = properties.getString("language");
                JSONArray authorsArray = properties.getJSONArray("authors");
                String author = authorsArray.getString(0);
                JSONObject imageLinks = properties.getJSONObject("imageLinks");
                String imageUrl = imageLinks.getString("smallThumbnail");
                String infoUrl = properties.getString("previewLink");


                try {
                     averageRating = (float) properties.getInt("averageRating");
                }catch (JSONException e){
                    averageRating = 0;
                }

                if (publishedDate.length() > 4){
                    publishedDate = publishedDate.substring(0,4);
                }

                Book book = new Book(title, author,publisher, publishedDate, language, averageRating, imageUrl, infoUrl);

                books.add(book);
            }
        }catch (JSONException e){
            Log.e(LOG_TAG, "Error Occurred", e);
        }
        return books;
    }
}
