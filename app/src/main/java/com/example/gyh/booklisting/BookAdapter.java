package com.example.gyh.booklisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gyh on 2016/10/21.
 */

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        Book currentBook = getItem(position);

        TextView bookName = (TextView) listItemView.findViewById(R.id.bookNameText);
        bookName.setText(currentBook.getBookName());

        TextView author = (TextView) listItemView.findViewById(R.id.authorText);
        author.setText(currentBook.getAuthor());

        return listItemView;
    }
}
