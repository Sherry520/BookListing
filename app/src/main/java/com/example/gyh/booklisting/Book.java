package com.example.gyh.booklisting;

/**
 * Book Class
 */

public class Book {
    private String mBookName;
    private String mAuthor;

    public Book(String bookName, String author) {
        mAuthor = author;
        mBookName = bookName;
    }

    public String getBookName() {
        return mBookName;
    }

    public String getAuthor() {
        return mAuthor;
    }
}
