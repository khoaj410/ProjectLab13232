package com.example.khoaj.projectlab1.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khoaj.projectlab1.Holder_RecyclerView.GenreHolder;
import com.example.khoaj.projectlab1.Holder_RecyclerView.UserHolder;
import com.example.khoaj.projectlab1.Model.Genre;
import com.example.khoaj.projectlab1.Model.User;
import com.example.khoaj.projectlab1.R;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreHolder> {
    private Context context;
    private List<Genre> listGenres;

    public GenreAdapter(Context context, List<Genre> listGenres) {
        this.context = context;
        this.listGenres = listGenres;
    }

    @NonNull
    @Override
    public GenreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_genrre, parent, false);
        return new GenreHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreHolder holder, int position) {
        holder.tvId.setText(listGenres.get(position).getMa());
        holder.tvName.setText(listGenres.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return listGenres.size();
    }
}
