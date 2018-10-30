package com.example.khoaj.projectlab1.Holder_RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.khoaj.projectlab1.R;

public class GenreHolder extends RecyclerView.ViewHolder {
    public final TextView tvId;
    public final TextView tvName;
    public final ImageView imgDelete;
    public final ImageView imgEdit;


    public GenreHolder(View itemView) {
        super(itemView);

        tvId = itemView.findViewById(R.id.tvID_Genre);
        tvName = itemView.findViewById(R.id.tvName_Genre);
        imgDelete = itemView.findViewById(R.id.imgDeleteGenre);
        imgEdit = itemView.findViewById(R.id.imgEditGenre);
    }
}
