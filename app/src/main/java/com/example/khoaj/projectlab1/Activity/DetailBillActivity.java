package com.example.khoaj.projectlab1.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.khoaj.projectlab1.Adapter.BillAdapter;
import com.example.khoaj.projectlab1.Adapter.DetailBillAdapter;
import com.example.khoaj.projectlab1.DAO.BillDAO;
import com.example.khoaj.projectlab1.DAO.BookDAO;
import com.example.khoaj.projectlab1.DAO.DetailBillDAO;
import com.example.khoaj.projectlab1.Model.Bill;
import com.example.khoaj.projectlab1.Model.Books;
import com.example.khoaj.projectlab1.Model.DetailBill;
import com.example.khoaj.projectlab1.R;

import java.util.List;

public class DetailBillActivity extends AppCompatActivity {
    DetailBillDAO DetailBillDAO;
    private RecyclerView rvDetailBillDAO;
    private List<DetailBill> dbillList;
    private DetailBillAdapter dbillAdapter;
    private EditText edtID;
    private EditText edtNumber;
    private EditText edtPrice;
    private EditText edtTotal;
    private Paint p = new Paint();
    double thanhTien = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bill);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rvDetailBillDAO = findViewById(R.id.RecyclerView_DetailBill);
        toolbar.setNavigationIcon(R.drawable.undo);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogHoaDonChitiet();
            }
        });

        DetailBillDAO = new DetailBillDAO(this);
        dbillList = DetailBillDAO.getAllDetailBills();
        dbillAdapter = new DetailBillAdapter(this, dbillList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvDetailBillDAO.setLayoutManager(manager);
        rvDetailBillDAO.setAdapter(dbillAdapter);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_edit_detailbill, null);
        edtID = dialogView.findViewById(R.id.idDB);
        dialog.setView(dialogView);
        if (b != null) {
            edtID.setText(b.getString("MAHOADON"));
        }
        enableSwipe();


    }

    private void deleteDetailBill(int position) {
        // deleting the note from db
        DetailBillDAO.deleteDetailBill(dbillList.get(position));

        // removing the note from the list
        dbillList.remove(position);
        dbillAdapter.notifyItemRemoved(position);
    }

    private void updatedBill(String id, String number, String price, int position) {
        DetailBill n = dbillList.get(position);
        // updating note text
        n.setTensach(id);
        n.setSoluong(number);
        n.setDongia(price);
        // updating note in db
        DetailBillDAO.updateDetailBill(n);

        // refreshing the list
        dbillList.set(position, n);
        dbillAdapter.notifyItemChanged(position);

    }
    private void enableSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                DetailBillDAO = new DetailBillDAO(DetailBillActivity.this);
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    deleteDetailBill(position);

                }
                if (direction == ItemTouchHelper.RIGHT) {
                    showDialogEditHoaDon(position);
                }

            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_edit);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_input_delete);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvDetailBillDAO);
    }
    public void showDialogEditHoaDon(final int position) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_edit_detailbill, null);
        dialog.setView(dialogView);
        final Dialog dialog1 = dialog.show();
        Button edit = dialogView.findViewById(R.id.editBill);
        Button huy = dialogView.findViewById(R.id.CancelEditBill);

        edtID = dialogView.findViewById(R.id.idDB);
        edtNumber = dialogView.findViewById(R.id.nameDB);
        edtPrice = dialogView.findViewById(R.id.priceDB);
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edtID.getText().toString();
                String number = edtNumber.getText().toString();
                String price = edtPrice.getText().toString();
                updatedBill(id, number, price, position);
                Toast.makeText(DetailBillActivity.this, "Thanh Cong", Toast.LENGTH_SHORT).show();
                dialog1.dismiss();
            }
        });
    }
        public void showDialogHoaDonChitiet(){
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = inflater.inflate(R.layout.dialog_insert_detail_bill, null);
            dialog.setView(dialogView);
            final Dialog dialog1 = dialog.show();
            edtID = dialogView.findViewById(R.id.idDB);
            edtNumber = dialogView.findViewById(R.id.nameDB);
            edtPrice = dialogView.findViewById(R.id.priceDB);
            edtTotal = dialogView.findViewById(R.id.totalDB);
            Button them = dialogView.findViewById(R.id.addDetailBill);
            Button huy = dialogView.findViewById(R.id.CancelDetailBill);
            huy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    thanhToanHoaDon();
                }
            });
            them.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DetailBillDAO = new DetailBillDAO(DetailBillActivity.this);
                    DetailBill detailBill = new DetailBill(edtID.getText().toString().trim(),edtNumber.getText().toString().trim(), edtPrice.getText().toString().trim(), edtTotal.getText().toString());
                    if (ValidateForm() > 0) {
                        if (DetailBillDAO.insertDetailBill(detailBill) > 0) {

                            Toast.makeText(getApplicationContext(), "Đã Thành Công", Toast.LENGTH_SHORT).show();
                            dialog1.dismiss();
                            Intent intent = new Intent(DetailBillActivity.this, DetailBillActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getApplicationContext(), "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            });
        }

    public int ValidateForm() {
        String id = edtID.getText().toString().trim();
        String number = edtNumber.getText().toString().trim();
        String price = edtPrice.getText().toString().trim();
        int check = 1;
        if (id.length() == 0 || number.length() == 0 || price.length() == 0) {
            edtID.setError("Bạn Phải Nhập Đầy Đủ");
            edtNumber.setError("Bạn Phải Nhập Đầy Đủ");
            edtPrice.setError("Bạn Phải Nhập Đầy Đủ");
            check = -1;
        }

        return check;
    }
    public void thanhToanHoaDon() {
        DetailBillDAO = new DetailBillDAO(DetailBillActivity.this);
        //tinh tien
        thanhTien = 0;
        try {
            for (DetailBill hd: dbillList) {
                DetailBillDAO.insertDetailBill(hd);
                thanhTien = thanhTien + Integer.parseInt(hd.getSoluong())*
                        Integer.parseInt(hd.getDongia());

            }
            edtTotal.setText("Tổng tiền: " +thanhTien);
        } catch (Exception ex) {
            Log.e("Error", ex.toString());
        }
    }
        }


