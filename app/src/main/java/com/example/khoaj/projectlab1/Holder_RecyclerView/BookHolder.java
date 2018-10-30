package com.example.khoaj.projectlab1.Holder_RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.khoaj.projectlab1.R;

public class BookHolder extends RecyclerView.ViewHolder {
    public final TextView tvId;
    public final TextView tvName;
    public final ImageView imgDelete;
    public final ImageView imgEdit;


    public BookHolder(View itemView) {
        super(itemView);

        tvId = itemView.findViewById(R.id.tvID_Book);
        tvName = itemView.findViewById(R.id.tvName_Book);
        imgDelete = itemView.findViewById(R.id.imgDeleteBook);
        imgEdit = itemView.findViewById(R.id.imgEditBook);
    }
}
