package com.example.khoaj.projectlab1.Holder_RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.khoaj.projectlab1.R;

public class UserHolder extends RecyclerView.ViewHolder {
    public final TextView tvFullName;
    public final TextView tvPhoneNumber;
    public final ImageView imgDelete;
    public final ImageView imgEdit;


    public UserHolder(View itemView) {
        super(itemView);

        tvFullName = itemView.findViewById(R.id.tvName_User);
        tvPhoneNumber = itemView.findViewById(R.id.tvSDT_User);
        imgDelete = itemView.findViewById(R.id.imgDeleteUser);
        imgEdit = itemView.findViewById(R.id.imgEditUser);
    }
}
