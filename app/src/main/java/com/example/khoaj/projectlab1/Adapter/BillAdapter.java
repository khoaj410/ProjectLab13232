package com.example.khoaj.projectlab1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.khoaj.projectlab1.Holder_RecyclerView.BillHolder;
import com.example.khoaj.projectlab1.Holder_RecyclerView.GenreHolder;
import com.example.khoaj.projectlab1.Holder_RecyclerView.UserHolder;
import com.example.khoaj.projectlab1.Model.Bill;
import com.example.khoaj.projectlab1.Model.Genre;
import com.example.khoaj.projectlab1.Model.User;
import com.example.khoaj.projectlab1.R;

import java.util.Date;
import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillHolder> {

    private Context context;
    private List<Bill> listBills;

    public BillAdapter(Context context, List<Bill> listBills) {
        this.context = context;
        this.listBills = listBills;
    }

    @NonNull
    @Override
    public BillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bill, parent, false);
        return new BillHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillHolder holder, int position) {
        holder.tvId.setText(listBills.get(position).getMa());
        holder.tvDate.setText(listBills.get(position).getNgay());
    }


    @Override
    public int getItemCount() {
        return listBills.size();
    }


        }

