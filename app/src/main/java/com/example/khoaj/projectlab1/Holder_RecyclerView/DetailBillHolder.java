package com.example.khoaj.projectlab1.Holder_RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.khoaj.projectlab1.R;

public class DetailBillHolder extends RecyclerView.ViewHolder {

    public final TextView tvId;
    public final TextView tvNumber;
    public final TextView tvPrice;
    public final TextView tvTotal;
    public final ImageView imgDelete;
    public DetailBillHolder(View itemView) {
        super(itemView);
        tvId = itemView.findViewById(R.id.tvMaSach_HoaDonChiTiet);
        tvNumber = itemView.findViewById(R.id.tvSoluong_HoaDonChiTiet);
        tvPrice = itemView.findViewById(R.id.tvDonGia_HoaDonChiTiet);
        tvTotal = itemView.findViewById(R.id.tvThanhTien_HoaDonChiTiet);
        imgDelete = itemView.findViewById(R.id.imgDeleteHoaDonChiTiet_item);

    }
}
