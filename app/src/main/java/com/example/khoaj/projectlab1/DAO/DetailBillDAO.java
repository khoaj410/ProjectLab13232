package com.example.khoaj.projectlab1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.khoaj.projectlab1.Constant;
import com.example.khoaj.projectlab1.Model.Bill;
import com.example.khoaj.projectlab1.Model.DetailBill;
import com.example.khoaj.projectlab1.SQLite.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class DetailBillDAO {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public DetailBillDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

    }
    public DetailBillDAO(DatabaseHelper dbHelper)  {
        this.dbHelper = dbHelper;
    }
    public void deleteDetailBill(DetailBill detailBill) {
        db = dbHelper.getWritableDatabase();

        db.delete(DETAILBILL_TABLE,
                COLUMN_ID + " = ?",
                new String[]{(detailBill.getTensach())});

        db.close();
    }
    public int insertDetailBill(DetailBill detailBill) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ID, detailBill.getTensach());
        contentValues.put(COLUMN_NUMBER, detailBill.getSoluong());
        contentValues.put(COLUMN_PRICE, detailBill.getDongia());

        long id = db.insert(DETAILBILL_TABLE, null, contentValues);

        if (Constant.isDEBUG) Log.e("insertDetailBill", "insertDetailBill ID : " + id);

        db.close();

        return 1;
    }
    public int updateDetailBill(DetailBill detailBill) {
        db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NUMBER, detailBill.getSoluong());
        values.put(COLUMN_PRICE, detailBill.getDongia());

        long id = db.update(DETAILBILL_TABLE, values,
                COLUMN_ID + " = ?",
                new String[]{detailBill.getTensach()});

        if (Constant.isDEBUG) Log.e("updateBill", "updateBill ID: " + id);
        return 1;
    }
    public DetailBill getDetailBill(String id) {

        DetailBill detailBill = null;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truyen vao Ten bang, array bao gom ten cot, ten cot khoa chinh, gia tri khoa chinh, cac tham so con lai la null

        Cursor cursor = db.query(DETAILBILL_TABLE, new String[]{COLUMN_ID, COLUMN_NUMBER, COLUMN_PRICE, COLUMN_TOTAL}, COLUMN_ID + "=?", new String[]{id}, null, null, null, null);

        // moveToFirst : kiem tra xem cursor co chua du lieu khong, ham nay tra ve gia tri la true or false
        if (cursor != null && cursor.moveToFirst()) {

            String i_d = cursor.getString(cursor.getColumnIndex(COLUMN_ID));

            String number = cursor.getString(cursor.getColumnIndex(COLUMN_NUMBER));

            String price = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE));

            String total = cursor.getString(cursor.getColumnIndex(COLUMN_TOTAL));
            // khoi tao user voi cac gia tri lay duoc
            detailBill = new DetailBill(i_d, number, price, total);

        }
        cursor.close();

        return detailBill;
    }
    public List<DetailBill> getAllDetailBills() {
        List<DetailBill> detailBills = new ArrayList<>();

        String query = "SELECT * FROM " + DetailBillDAO.DETAILBILL_TABLE;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                DetailBill detailBill = new DetailBill();
                detailBill.setTensach(cursor.getString(cursor.getColumnIndex(DetailBillDAO.COLUMN_ID)));
                detailBill.setSoluong(cursor.getString(cursor.getColumnIndex(DetailBillDAO.COLUMN_NUMBER)));
                detailBill.setDongia(cursor.getString(cursor.getColumnIndex(DetailBillDAO.COLUMN_PRICE)));
                detailBills.add(detailBill);
            } while (cursor.moveToNext());
        }
        db.close();
        return detailBills;
    }
}
