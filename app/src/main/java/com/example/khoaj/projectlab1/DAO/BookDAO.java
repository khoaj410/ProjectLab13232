package com.example.khoaj.projectlab1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.khoaj.projectlab1.Constant;
import com.example.khoaj.projectlab1.Model.Books;
import com.example.khoaj.projectlab1.Model.User;
import com.example.khoaj.projectlab1.SQLite.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    public final static String BOOK_TABLE = "books";
    public final static String COLUMN_ID = "Id";
    public final static String COLUMN_NAME = "Name";
    public final static String COLUMN_IDGENRE = "IdGenre";
    public final static String COLUMN_AUTHOR = "Author";
    public final static String COLUMN_NUMBER = "Number";
    public final static String COLUMN_PUBLISHER = "Publisher";



    public final static String CREATE_BOOK_TABLE = "CREATE TABLE " + BOOK_TABLE + "(" +
            COLUMN_ID + " VARCHAR PRIMARY KEY, " +
            COLUMN_NAME + " VARCHAR, " +
            COLUMN_IDGENRE + " VARCHAR, " +
            COLUMN_AUTHOR + " VARCHAR, " +
            COLUMN_NUMBER + " VARCHAR, " +
            COLUMN_PUBLISHER + " VARCHAR " +
            ")";
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public BookDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

    }
    public BookDAO(DatabaseHelper dbHelper)  {
        this.dbHelper = dbHelper;
    }

    public void deleteBook(Books books) {
        db = dbHelper.getWritableDatabase();

        db.delete(BOOK_TABLE,
                COLUMN_ID + " = ?",
                new String[]{(books.getMasach())});

        db.close();
    }
    public int insertBook(Books books) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ID, books.getMasach());
        contentValues.put(COLUMN_NAME, books.getName());
        contentValues.put(COLUMN_IDGENRE, books.getMaTheLoai());
        contentValues.put(COLUMN_AUTHOR, books.getAuthor());
        contentValues.put(COLUMN_NUMBER, books.getSoluong());
        contentValues.put(COLUMN_PUBLISHER, books.getNxb());

        long id = db.insert(BOOK_TABLE, null, contentValues);

        if (Constant.isDEBUG) Log.e("insertBook", "insertBook ID : " + id);

        db.close();

        return 1;
    }
    public int updateBook(Books books) {
        db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, books.getMasach());
        values.put(COLUMN_NAME, books.getName());

        long id = db.update(BOOK_TABLE, values,
                COLUMN_ID + " = ?",
                new String[]{books.getMasach()});

        if (Constant.isDEBUG) Log.e("updateUser", "updateUser ID: " + id);
        return 1;
    }


    public Books getBook(String id) {

        Books books = null;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truyen vao Ten bang, array bao gom ten cot, ten cot khoa chinh, gia tri khoa chinh, cac tham so con lai la null

        Cursor cursor = db.query(BOOK_TABLE, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_IDGENRE, COLUMN_AUTHOR, COLUMN_NUMBER, COLUMN_PUBLISHER}, COLUMN_ID + "=?", new String[]{id}, null, null, null, null);

        // moveToFirst : kiem tra xem cursor co chua du lieu khong, ham nay tra ve gia tri la true or false
        if (cursor != null && cursor.moveToFirst()) {

            String i_d = cursor.getString(cursor.getColumnIndex(COLUMN_ID));

            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));

            String idGenre = cursor.getString(cursor.getColumnIndex(COLUMN_IDGENRE));

            String author = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR));

            String number = cursor.getString(cursor.getColumnIndex(COLUMN_NUMBER));

            String publisher = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER));

            // khoi tao user voi cac gia tri lay duoc
            books = new Books(i_d, name, idGenre, author, number, publisher);


        }
        cursor.close();

        return books;
    }

    public List<Books> getAllBooks() {
        List<Books> books = new ArrayList<>();

        String query = "SELECT * FROM " + BookDAO.BOOK_TABLE;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Books books1 = new Books();
                books1.setMasach(cursor.getString(cursor.getColumnIndex(BookDAO.COLUMN_ID)));
                books1.setName(cursor.getString(cursor.getColumnIndex(BookDAO.COLUMN_NAME)));
                books1.setMaTheLoai(cursor.getString(cursor.getColumnIndex(BookDAO.COLUMN_IDGENRE)));
                books1.setAuthor(cursor.getString(cursor.getColumnIndex(BookDAO.COLUMN_AUTHOR)));
                books1.setSoluong(cursor.getString(cursor.getColumnIndex(BookDAO.COLUMN_NUMBER)));
                books1.setNxb(cursor.getString(cursor.getColumnIndex(BookDAO.COLUMN_PUBLISHER)));

                books.add(books1);
            } while (cursor.moveToNext());
        }
        db.close();
        return books;
    }
}
