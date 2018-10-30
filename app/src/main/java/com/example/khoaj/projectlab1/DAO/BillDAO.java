package com.example.khoaj.projectlab1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.khoaj.projectlab1.Constant;
import com.example.khoaj.projectlab1.Model.Bill;
import com.example.khoaj.projectlab1.Model.Books;
import com.example.khoaj.projectlab1.SQLite.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class BillDAO {
    public final static String BILL_TABLE = "bills";
    public final static String COLUMN_ID = "Id";
    public final static String COLUMN_DATE = "Date";

    public final static String CREATE_BILL_TABLE = "CREATE TABLE " + BILL_TABLE + "(" +
            COLUMN_ID + " VARCHAR PRIMARY KEY, " +
            COLUMN_DATE + " VARCHAR" +
            ")";
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public BillDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

    }
    public BillDAO(DatabaseHelper dbHelper)  {
        this.dbHelper = dbHelper;
    }
    public void deleteBill(Bill bill) {
        db = dbHelper.getWritableDatabase();

        db.delete(BILL_TABLE,
                COLUMN_ID + " = ?",
                new String[]{(bill.getMa())});

        db.close();
    }
    public int insertBill(Bill bill) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ID, bill.getMa());
        contentValues.put(COLUMN_DATE, bill.getNgay());

        long id = db.insert(BILL_TABLE, null, contentValues);

        if (Constant.isDEBUG) Log.e("insertBill", "insertBill ID : " + id);

        db.close();

        return 1;
    }
    public int updateBill(Bill bill) {
        db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, bill.getNgay());

        long id = db.update(BILL_TABLE, values,
                COLUMN_ID + " = ?",
                new String[]{bill.getMa()});

        if (Constant.isDEBUG) Log.e("updateBill", "updateBill ID: " + id);
        return 1;
    }


    public Bill getBill(String id) {

        Bill bill = null;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truyen vao Ten bang, array bao gom ten cot, ten cot khoa chinh, gia tri khoa chinh, cac tham so con lai la null

        Cursor cursor = db.query(BILL_TABLE, new String[]{COLUMN_ID, COLUMN_DATE}, COLUMN_ID + "=?", new String[]{id}, null, null, null, null);

        // moveToFirst : kiem tra xem cursor co chua du lieu khong, ham nay tra ve gia tri la true or false
        if (cursor != null && cursor.moveToFirst()) {

            String i_d = cursor.getString(cursor.getColumnIndex(COLUMN_ID));

            String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));


            // khoi tao user voi cac gia tri lay duoc
            bill = new Bill(i_d, date);

        }
        cursor.close();

        return bill;
    }

    public List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();

        String query = "SELECT * FROM " + BillDAO.BILL_TABLE;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Bill bill = new Bill();
                bill.setMa(cursor.getString(cursor.getColumnIndex(BillDAO.COLUMN_ID)));
                bill.setNgay(cursor.getString(cursor.getColumnIndex(BillDAO.COLUMN_DATE)));

                bills.add(bill);
            } while (cursor.moveToNext());
        }
        db.close();
        return bills;
    }

}
