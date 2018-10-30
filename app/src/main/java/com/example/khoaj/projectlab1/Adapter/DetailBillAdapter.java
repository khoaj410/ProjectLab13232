package com.example.khoaj.projectlab1.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khoaj.projectlab1.Holder_RecyclerView.DetailBillHolder;
import com.example.khoaj.projectlab1.Model.Bill;
import com.example.khoaj.projectlab1.Model.DetailBill;
import com.example.khoaj.projectlab1.R;

import java.util.List;

public class DetailBillAdapter extends RecyclerView.Adapter<DetailBillHolder> {
    private Context context;
    private List<DetailBill> detailBillList;

    public DetailBillAdapter(Context context, List<DetailBill> detailBillList) {
        this.context = context;
        this.detailBillList = detailBillList;
    }

    @NonNull
    @Override
    public DetailBillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_bill, parent, false);
        return new DetailBillHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailBillHolder holder, int position) {
        holder.tvId.setText(detailBillList.get(position).getTensach());
        holder.tvNumber.setText(detailBillList.get(position).getSoluong());
        holder.tvPrice.setText(detailBillList.get(position).getDongia());
        holder.tvTotal.setText(detailBillList.get(position).getThanhtien());
    }

    @Override
    public int getItemCount() {
        return detailBillList.size();
    }

}
