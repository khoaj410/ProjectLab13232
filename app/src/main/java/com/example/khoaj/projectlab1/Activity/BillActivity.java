package com.example.khoaj.projectlab1.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khoaj.projectlab1.Adapter.BillAdapter;
import com.example.khoaj.projectlab1.Adapter.BookAdapter;
import com.example.khoaj.projectlab1.DAO.BillDAO;
import com.example.khoaj.projectlab1.DAO.BookDAO;
import com.example.khoaj.projectlab1.Model.Bill;
import com.example.khoaj.projectlab1.Model.Books;
import com.example.khoaj.projectlab1.Model.DetailBill;
import com.example.khoaj.projectlab1.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BillActivity extends AppCompatActivity {
    TextView ngay;
    BillDAO billDAO;
    private RecyclerView rvBill;
    private List<Bill> billList;
    private BillAdapter billAdapter;
    private EditText edtID;
    private EditText edtDate;
    private Paint p = new Paint();
    long datePicker = -1;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Bills");
        rvBill = findViewById(R.id.RecyclerView_Bill);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        toolbar.setNavigationIcon(R.drawable.undo);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogHoaDon();
            }
        });
        billDAO = new BillDAO(this);
        billList = billDAO.getAllBills();
        billAdapter = new BillAdapter(this, billList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvBill.setLayoutManager(manager);
        rvBill.setAdapter(billAdapter);
        enableSwipe();
    }
    private void deleteBill(int position) {
        // deleting the note from db
        billDAO.deleteBill(billList.get(position));

        // removing the note from the list
        billList.remove(position);
        billAdapter.notifyItemRemoved(position);
    }

    private void updateBill(String date, int position) {
        Bill n = billList.get(position);
        // updating note text
        n.setNgay(date);

        // updating note in db
        billDAO.updateBill(n);

        // refreshing the list
        billList.set(position, n);
        billAdapter.notifyItemChanged(position);

    }
    private void enableSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                billDAO = new BillDAO(BillActivity.this);
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    deleteBill(position);

                }
                if (direction == ItemTouchHelper.RIGHT) {
                    showDialogEditHoaDon(position);
                }
                if (direction == ItemTouchHelper.DOWN){
                    Bill bill = new Bill();
                    Intent intent = new Intent(BillActivity.this,
                            DetailBillActivity.class);
                    Bundle b = new Bundle();
                    b.putString("MAHOADON", bill.getMa());
                    intent.putExtras(b);
                    startActivity(intent);

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
        itemTouchHelper.attachToRecyclerView(rvBill);
    }
    public void showDialogEditHoaDon(final int position) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_edit_bill, null);
        dialog.setView(dialogView);
        final Dialog dialog1 = dialog.show();
        Button edit = dialogView.findViewById(R.id.editBill);
        Button huy = dialogView.findViewById(R.id.CancelEditBill);
        edtDate = dialogView.findViewById(R.id.date);
        Button chon = dialogView.findViewById(R.id.PickDate);
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = edtDate.getText().toString();
                updateBill(date, position);
                Toast.makeText(BillActivity.this, "Thanh Cong", Toast.LENGTH_SHORT).show();
                dialog1.dismiss();
            }
        });
        chon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

    }
    public void showDialogHoaDon() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_insert_bill, null);
        dialog.setView(dialogView);
        final Dialog dialog1 = dialog.show();
        Button them = dialogView.findViewById(R.id.addBill);
        edtID = dialogView.findViewById(R.id.edtIdBill);
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billDAO = new BillDAO(BillActivity.this);
                Bill bill = new Bill(edtID.getText().toString().trim(), edtDate.getText().toString().trim());
                if (ValidateForm() > 0) {
                    if (billDAO.insertBill(bill) > 0) {

                        Toast.makeText(getApplicationContext(), "Đã Thành Công", Toast.LENGTH_SHORT).show();
                        dialog1.dismiss();
                        Intent intent = new Intent(BillActivity.this, BillActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });

        Button huy = dialogView.findViewById(R.id.CancelBill);
        Button chon = dialogView.findViewById(R.id.PickDate);
        edtDate = dialogView.findViewById(R.id.date);
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        chon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

    }

    public void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // thiet lap thong tin cho date picker

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Integer n = year;
                Integer t = month+1;
                Integer d = dayOfMonth;

                edtDate.setText(d.toString()+"-"+t.toString()+"-"+n.toString());
            }
        }, year, month, day);

        datePickerDialog.show();
    }
    public int ValidateForm() {
        String id = edtID.getText().toString().trim();
        String date = edtDate.getText().toString().trim();
        int check = 1;
        if (id.length() == 0 || date.length() == 0) {
            edtID.setError("Bạn Phải Nhập Đầy Đủ");
            edtDate.setError("Bạn Phải Nhập Đầy Đủ");
            check = -1;
        }

        return check;
    }
}
