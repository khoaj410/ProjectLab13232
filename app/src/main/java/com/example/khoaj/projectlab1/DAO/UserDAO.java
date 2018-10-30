package com.example.khoaj.projectlab1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.khoaj.projectlab1.Adapter.UserAdapter;
import com.example.khoaj.projectlab1.Constant;
import com.example.khoaj.projectlab1.Model.User;
import com.example.khoaj.projectlab1.SQLite.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {


    public final static String USER_TABLE = "users";
    public final static String COLUMN_USERNAME = "Username";
    public final static String COLUMN_NAME = "Name";
    public final static String COLUMN_PASSWORD = "Password";
    public final static String COLUMN_PHONE_NUMBER = "Phone_number";

    public final static String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE + "(" +
            COLUMN_USERNAME + " VARCHAR PRIMARY KEY," +
            COLUMN_NAME + " VARCHAR," +
            COLUMN_PASSWORD + " VARCHAR," +
            COLUMN_PHONE_NUMBER + " VARCHAR" +
            ")";
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

    };

    public UserDAO(DatabaseHelper dbHelper)  {
        this.dbHelper = dbHelper;
    }

    public void deleteUser(User user) {
        db = dbHelper.getWritableDatabase();

        db.delete(USER_TABLE,
                COLUMN_USERNAME + " = ?",
                new String[]{user.getUsername()});

        db.close();
    }
    public int changePasswordNguoiDung(User nd){
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME,nd.getUsername());
        values.put(COLUMN_PASSWORD,nd.getPassword());
        int result = db.update(USER_TABLE,values,COLUMN_USERNAME+" = ?", new
                String[]{nd.getUsername()});
        if (result == 0){
            return -1;
        }
        return 1;
    }


    public int insertUser(User user) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_USERNAME, user.getUsername());
        contentValues.put(COLUMN_PASSWORD, user.getPassword());
        contentValues.put(COLUMN_NAME, user.getName());
        contentValues.put(COLUMN_PHONE_NUMBER, user.getSdt());

        long id = db.insert(USER_TABLE, null, contentValues);

        if (Constant.isDEBUG) Log.e("insertUser", "insertUser ID : " + id);

        db.close();

        return 1;
    }
    public int updateUser(User user) {
        db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_PHONE_NUMBER, user.getSdt());

        long id = db.update(USER_TABLE, values,
                COLUMN_USERNAME + " = ?",
                new String[]{user.getUsername()});

        if (Constant.isDEBUG) Log.e("updateUser", "updateUser ID: " + id);
        return 1;
    }


    public User getUser(String username) {

        User user = null;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truyen vao Ten bang, array bao gom ten cot, ten cot khoa chinh, gia tri khoa chinh, cac tham so con lai la null

        Cursor cursor = db.query(USER_TABLE, new String[]{COLUMN_USERNAME, COLUMN_NAME, COLUMN_PASSWORD, COLUMN_PHONE_NUMBER}, COLUMN_USERNAME + "=?", new String[]{username}, null, null, null, null);

        // moveToFirst : kiem tra xem cursor co chua du lieu khong, ham nay tra ve gia tri la true or false
        if (cursor != null && cursor.moveToFirst()) {

            String user_name = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));

            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));

            String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));


            String phoneNumber = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER));

            // khoi tao user voi cac gia tri lay duoc
            user = new User(user_name, name, password, phoneNumber);


        }
        cursor.close();

        return user;
    }

    public List<User> getAllUser() {
        List<User> users = new ArrayList<>();

        String query = "SELECT * FROM " + UserDAO.USER_TABLE;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setName(cursor.getString(cursor.getColumnIndex(UserDAO.COLUMN_NAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(UserDAO.COLUMN_PASSWORD)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(UserDAO.COLUMN_USERNAME)));
                user.setSdt(cursor.getString(cursor.getColumnIndex(UserDAO.COLUMN_PHONE_NUMBER)));

                users.add(user);
            } while (cursor.moveToNext());
        }
        db.close();
        return users;
    }
}
