package com.example.khoaj.projectlab1.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.khoaj.projectlab1.Holder_RecyclerView.UserHolder;
import com.example.khoaj.projectlab1.Listener.OnDelete;
import com.example.khoaj.projectlab1.Model.User;
import com.example.khoaj.projectlab1.R;
import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserHolder> {
    private Context context;
    private List<User> listUsers;

    public UserAdapter(Context context, List<User> listUsers) {
        this.context = context;
        this.listUsers = listUsers;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        holder.tvFullName.setText(listUsers.get(position).getName());
        holder.tvPhoneNumber.setText(listUsers.get(position).getSdt());
    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }

        }
