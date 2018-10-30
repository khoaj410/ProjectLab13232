package com.example.khoaj.projectlab1.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.khoaj.projectlab1.Holder_RecyclerView.BookHolder;
import com.example.khoaj.projectlab1.Holder_RecyclerView.GenreHolder;
import com.example.khoaj.projectlab1.Model.Books;
import com.example.khoaj.projectlab1.R;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookHolder> implements Filterable {
    private Context context;
    private List<Books> booksList;
    private List<Books> booksList1;
    private Filter sachFilter;

    public BookAdapter(Context context, List<Books> books) {
        this.context = context;
        this.booksList = books;
        this.booksList1 = books;
    }

    public void resetData() {
        booksList = booksList1;
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new BookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {
        holder.tvId.setText(booksList.get(position).getMasach());
        holder.tvName.setText(booksList.get(position).getName());
    }
    public Filter getFilter() {
        if (sachFilter == null)
            sachFilter = new CustomFilter();
        return sachFilter;
    }
    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                results.values = booksList1;
                results.count = booksList1.size();
            }
            else {
                List<Books> lsSach = new ArrayList<>();
                for (Books p : booksList) {
                    if
                            (p.getMasach().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        lsSach.add(p);
                }
                results.values = lsSach;
                results.count = lsSach.size();
            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            if (results.count == 0){
            }
            else {
                booksList = (List<Books>) results.values;
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

}
