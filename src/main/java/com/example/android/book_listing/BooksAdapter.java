package com.example.android.book_listing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class BooksAdapter extends ArrayAdapter<Book> {

    public BooksAdapter(Context context, ArrayList<Book> bookList){
        super(context, 0, bookList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.title_id);
        title.setText(getItem(position).getTitle());

        TextView author = (TextView) convertView.findViewById(R.id.author_id);
        author.setText(getItem(position).getAuthor());

        TextView publisher = (TextView) convertView.findViewById(R.id.publisher_id);
        publisher.setText(getItem(position).getPublisher());

        TextView publishedDate = (TextView) convertView.findViewById(R.id.date_id);
        publishedDate.setText(getItem(position).getPublishedDate());

        TextView language = (TextView) convertView.findViewById(R.id.language_id);
        language.setText(getItem(position).getLanguage());

        RatingBar ratingBar = (RatingBar)convertView.findViewById(R.id.rating_bar);
        ratingBar.setRating(getItem(position).getAverageRating());

        ImageView imageView = (ImageView)convertView.findViewById(R.id.image_id);
        Picasso.get().load(getItem(position).getStringUrl()).into(imageView);



        return convertView;
    }


}
