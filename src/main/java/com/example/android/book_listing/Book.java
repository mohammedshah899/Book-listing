package com.example.android.book_listing;

public class Book {

    private final String Title;
    private final String Author;
    private final String Publisher;
    private final String PublishedDate;
    private final String Language;
    private final float AverageRating;
    private final String StringUrl;
    private final String InfoUrl;


    public Book(String title, String author, String publisher, String publishedDate, String language, float averageRating, String stringUrl, String infoUrl){
        Title = title;
        Author = author;
        Publisher = publisher;
        PublishedDate = publishedDate;
        Language = language;
        AverageRating = averageRating;
        StringUrl = stringUrl;
        InfoUrl = infoUrl;

    }

    public String getTitle(){ return Title;}

    public String getAuthor() {
        return Author;
    }

    public String getPublisher() {
        return Publisher;
    }

    public String getPublishedDate() {
        return PublishedDate;
    }

    public String getLanguage() {
        return Language;
    }

    public float getAverageRating() {
        return AverageRating;
    }

    public String getStringUrl() {
        return StringUrl;
    }

    public String getInfoUrl() {
        return InfoUrl;
    }
}
