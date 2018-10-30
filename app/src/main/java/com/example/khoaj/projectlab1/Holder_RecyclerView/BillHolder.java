package com.example.khoaj.projectlab1.Holder_RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.khoaj.projectlab1.R;

public class BillHolder extends RecyclerView.ViewHolder {

    public final TextView tvId;
    public final TextView tvDate;
    public final ImageView imgDelete;
    public final ImageView imgEdit;

    public BillHolder(View itemView) {
        super(itemView);

        tvId = itemView.findViewById(R.id.tvID_Bill);
        tvDate = itemView.findViewById(R.id.tvDate_Bill);
        imgDelete = itemView.findViewById(R.id.imgDeleteBill);
        imgEdit = itemView.findViewById(R.id.imgEditBill);
    }
}
