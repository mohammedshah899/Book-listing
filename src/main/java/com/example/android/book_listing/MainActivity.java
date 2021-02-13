package com.example.android.book_listing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BooksAdapter mAdapter;
    private static final String Base_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String API_KEY = "&key=AIzaSyDVh-DGs_6rXqppPgPKlkHFN2ZNOID3zbA";
    //private static final String API_URL1 = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=15&key=AIzaSyDVh-DGs_6rXqppPgPKlkHFN2ZNOID3zbA";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        String URL = bundle.getString("URL");

        ListView listView = (ListView)findViewById(R.id.list_view_id);

        mAdapter = new BooksAdapter(this, new ArrayList<>());
        listView.setAdapter(mAdapter);

        BookAsyncTask task = new BookAsyncTask();
        task.execute(URL);



        //listView.setVisibility(View.GONE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Book currentObject = (Book)parent.getItemAtPosition(position);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentObject.getInfoUrl()));
                startActivity(browserIntent);

            }
        });

    }

    private class BookAsyncTask extends AsyncTask<String, Void, List<Book>>{

        @Override
        protected List<Book> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null){
                return null;
            }

            return SearchUtil.fetchBookData(urls[0]);
        }



        @Override
        protected void onPostExecute(List<Book> data) {
            mAdapter.clear();

            if (data != null && !data.isEmpty()){
                mAdapter.addAll(data);
            }
        }
    }


}