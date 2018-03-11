package com.example.gyh.booklisting;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gyh on 2016/10/21.
 */

public class BookActivity extends AppCompatActivity {

    private BookAdapter bookAdapter;
    private String bookQuery;
    /**
     * 列表为空时显示的 TextView
     */
    private TextView mEmptyStateTextView;

    String BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    public static final String LOG_TAG = BookActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list);

        bookQuery = getIntent().getStringExtra("bookQuery");
        String queryString = BOOK_REQUEST_URL + bookQuery;
        new BookAsyncTask().execute(queryString);

        // Create a new {@link ArrayAdapter} of books
        bookAdapter = new BookAdapter(this, new ArrayList<Book>());
        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = (ListView) findViewById(R.id.list);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_text);
        bookListView.setEmptyView(mEmptyStateTextView);
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter(bookAdapter);
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    /**
     * {@link AsyncTask} to perform the network request on a background thread
     */
    private class BookAsyncTask extends AsyncTask<String, Void, List<Book>> {

        @Override
        protected List<Book> doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null)
                return null;
            List<Book> books = QueryUtils.getBookList(urls[0]);
            return books;
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            // Hide loading indicator because the data has been loaded
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(getString(R.string.no_book));
            bookAdapter.clear();
            if (bookQuery.isEmpty() || bookQuery.length() < 1) {
                mEmptyStateTextView.setText(getString(R.string.no_input));
            }
            if (!isNetworkAvailable())
                mEmptyStateTextView.setText(getString(R.string.no_internet));

            if (books != null && !books.isEmpty()) {
                bookAdapter.addAll(books);
            }
        }
    }
}
