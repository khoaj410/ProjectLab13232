package com.example.khoaj.projectlab1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.khoaj.projectlab1.Constant;
import com.example.khoaj.projectlab1.Model.Genre;
import com.example.khoaj.projectlab1.Model.User;
import com.example.khoaj.projectlab1.SQLite.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class GenreDAO {
    public final static String GENRE_TABLE ="genres";
    public final static String COLUMN_ID = "Id";
    public final static String COLUMN_NAME = "Name";

    public final static String CREATE_GENRE_TABLE = "CREATE TABLE " + GENRE_TABLE + "(" +
            COLUMN_ID + " VARCHAR PRIMARY KEY, " +
            COLUMN_NAME + " VARCHAR" +
            ")";
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public GenreDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

    };
    public void deleteGenre(Genre genre) {
        db = dbHelper.getWritableDatabase();

        db.delete(GENRE_TABLE,
                COLUMN_ID + " = ?",
                new String[]{genre.getMa()});

        db.close();
    }
    public int updateGenre(Genre genre) {
        db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, genre.getMa());
        values.put(COLUMN_NAME, genre.getName());

        long id = db.update(GENRE_TABLE, values,
                COLUMN_ID + " = ?",
                new String[]{genre.getMa()});

        if (Constant.isDEBUG) Log.e("updateUser", "updateUser ID: " + id);
        return 1;
    }
    public int insertGenre(Genre genre) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ID, genre.getMa());
        contentValues.put(COLUMN_NAME, genre.getName());


        long id = db.insert(GENRE_TABLE, null, contentValues);

        if (Constant.isDEBUG) Log.e("insertGenre", "insertGenre ID : " + id);

        db.close();

        return 1;
    }
    public GenreDAO(DatabaseHelper dbHelper)  {
        this.dbHelper = dbHelper;
    }
    public Genre getGenre(String id) {

        Genre genre = null;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truyen vao Ten bang, array bao gom ten cot, ten cot khoa chinh, gia tri khoa chinh, cac tham so con lai la null

        Cursor cursor = db.query(GENRE_TABLE, new String[]{COLUMN_ID, COLUMN_NAME}, COLUMN_ID + "=?", new String[]{id}, null, null, null, null);

        // moveToFirst : kiem tra xem cursor co chua du lieu khong, ham nay tra ve gia tri la true or false
        if (cursor != null && cursor.moveToFirst()) {

            String i_d = cursor.getString(cursor.getColumnIndex(COLUMN_ID));

            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));

            // khoi tao user voi cac gia tri lay duoc
            genre = new Genre(i_d, name);


        }
        cursor.close();

        return genre;
    }

    public List<Genre> getAllGenre() {
        List<Genre> genres = new ArrayList<>();

        String query = "SELECT * FROM " + GenreDAO.GENRE_TABLE;

        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
                Genre genre = new Genre();
                genre.setMa(cursor.getString(cursor.getColumnIndex(GenreDAO.COLUMN_ID)));
                genre.setName(cursor.getString(cursor.getColumnIndex(UserDAO.COLUMN_NAME)));
                genres.add(genre);
    } while (cursor.moveToNext());
}
        db.close();
        return genres;
    }
}
