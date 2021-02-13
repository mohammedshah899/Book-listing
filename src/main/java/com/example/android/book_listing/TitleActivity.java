package com.example.android.book_listing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;

public class TitleActivity extends AppCompatActivity {

    private static final String  Base_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String API_KEY = "&key=AIzaSyDVh-DGs_6rXqppPgPKlkHFN2ZNOID3zbA";
    public static  String API_URL= "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        SearchView searchView = (SearchView) findViewById(R.id.search_id);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                API_URL = Base_URL+query+API_KEY;
                System.out.println(API_URL);

                Intent intent = new Intent(TitleActivity.this, MainActivity.class);
                intent.putExtra("URL", API_URL);
                startActivity(intent);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });





    }
}