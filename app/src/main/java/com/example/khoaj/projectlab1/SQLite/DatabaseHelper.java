package com.example.khoaj.projectlab1.SQLite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.khoaj.projectlab1.Constant;
import com.example.khoaj.projectlab1.DAO.BillDAO;
import com.example.khoaj.projectlab1.DAO.BookDAO;
import com.example.khoaj.projectlab1.DAO.DetailBillDAO;
import com.example.khoaj.projectlab1.DAO.GenreDAO;
import com.example.khoaj.projectlab1.DAO.UserDAO;
import com.example.khoaj.projectlab1.Model.DetailBill;

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME="BookManager";
    public static final int DATABASE_VERSION =1;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(UserDAO.CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(GenreDAO.CREATE_GENRE_TABLE);
        sqLiteDatabase.execSQL(BookDAO.CREATE_BOOK_TABLE);
        sqLiteDatabase.execSQL(BillDAO.CREATE_BILL_TABLE);
        sqLiteDatabase.execSQL(DetailBillDAO.CREATE_DETAILBILL_TABLE);
        if(Constant.isDEBUG) Log.e("CREATE_USER_TABLE",UserDAO.CREATE_USER_TABLE);
        if(Constant.isDEBUG) Log.e("CREATE_GENRE_TABLE",GenreDAO.CREATE_GENRE_TABLE);
        if(Constant.isDEBUG) Log.e("CREATE_BOOK_TABLE",BookDAO.CREATE_BOOK_TABLE);
        if(Constant.isDEBUG) Log.e("CREATE_BILL_TABLE",BillDAO.CREATE_BILL_TABLE);
        if(Constant.isDEBUG) Log.e("CREATE_BILL_TABLE",DetailBillDAO.CREATE_DETAILBILL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +UserDAO.USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +GenreDAO.GENRE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +BookDAO.BOOK_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +BillDAO.BILL_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +DetailBillDAO.DETAILBILL_TABLE);
        onCreate(sqLiteDatabase);
    }
}
