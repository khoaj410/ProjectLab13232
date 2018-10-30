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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.khoaj.projectlab1.Adapter.UserAdapter;
import com.example.khoaj.projectlab1.DAO.UserDAO;
import com.example.khoaj.projectlab1.Model.User;
import com.example.khoaj.projectlab1.R;

import java.util.List;

public class UserAtcivity extends AppCompatActivity {
    UserDAO userDAO;
    private RecyclerView rvUser;
    private List<User> listUsers;
    private UserAdapter userAdapter;
    private EditText edtUSN;
    private EditText edtFN;
    private EditText edtPassword;
    private EditText edtConfirmPass;
    private EditText edtPhoneNumber;
    private Paint p = new Paint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_atcivity);
        rvUser = findViewById(R.id.RecyclerView_User);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("User");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.undo);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View view) {
                showDialogThemNguoiDung();
           }
       });

        userDAO = new UserDAO(this);
        listUsers = userDAO.getAllUser();
        userAdapter = new UserAdapter(this,listUsers);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvUser.setLayoutManager(manager);
        rvUser.setAdapter(userAdapter);
        enableSwipe();
    }
    private void deleteUser(int position) {
        // deleting the note from db
        userDAO.deleteUser(listUsers.get(position));

        // removing the note from the list
        listUsers.remove(position);
        userAdapter.notifyItemRemoved(position);
    }
    private void updateUser(String user, String phonenumber, int position) {
        User n = listUsers.get(position);
        // updating note text
        n.setName(user);
        n.setSdt(phonenumber);

        // updating note in db
        userDAO.updateUser(n);

        // refreshing the list
        listUsers.set(position, n);
        userAdapter.notifyItemChanged(position);

    }

    private void enableSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                userDAO = new UserDAO(UserAtcivity.this);
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT){
                    deleteUser(position);

                }
                if (direction == ItemTouchHelper.RIGHT){
                   showDialogEditNguoiDung(position);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_edit);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_input_delete);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvUser);
    }

    public void showDialogEditNguoiDung(final int position) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_edit_user, null);
        dialog.setView(dialogView);
        final Dialog dialog1 = dialog.show();
        Button edit = dialogView.findViewById(R.id.editUser);
        Button huy = dialogView.findViewById(R.id.CancelEditUser);
        edtFN = dialogView.findViewById(R.id.FullNameUser1);
        edtPhoneNumber = dialogView.findViewById(R.id.PhoneNumberUser2);
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtFN.getText().toString();
                String phone = edtPhoneNumber.getText().toString();
                        updateUser(username,phone,position);
                        Toast.makeText(UserAtcivity.this,"Thanh Cong",Toast.LENGTH_SHORT).show();
                        dialog1.dismiss();
                    }
                });
            }
    public void showDialogThemNguoiDung() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_insert_user, null);
        dialog.setView(dialogView);
        final Dialog dialog1 = dialog.show();
        Button them = dialogView.findViewById(R.id.addUser);
        Button huy = dialogView.findViewById(R.id.CancelUser);
        edtUSN = dialogView.findViewById(R.id.UsernameUser);
        edtFN = dialogView.findViewById(R.id.FullNameUser);
        edtPassword = dialogView.findViewById(R.id.PasswordUser);
        edtConfirmPass = dialogView.findViewById(R.id.ConfirmPasswordUser);
        edtPhoneNumber = dialogView.findViewById(R.id.PhoneNumberUser);
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDAO = new UserDAO(UserAtcivity.this);
                User user = new User(edtUSN.getText().toString(), edtFN.getText().toString(), edtPassword.getText().toString(), edtPhoneNumber.getText().toString());
                if (ValidateForm() > 0) {
                    if (userDAO.insertUser(user) > 0) {

                        Toast.makeText(getApplicationContext(), "Đã Thành Công", Toast.LENGTH_SHORT).show();
                        dialog1.dismiss();
                        Intent intent = new Intent(UserAtcivity.this,UserAtcivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
    }
    public int ValidateFormm(){
        String name = edtFN.getText().toString().trim();
        String phone = edtPhoneNumber.getText().toString().trim();
        int check = 1;
        if (name.length() == 0 || phone.length() == 0) {
            edtFN.setError("Bạn Phải Nhập Đầy Đủ");
            edtPhoneNumber.setError("Bạn Phải Nhập Đầy Đủ");
            check = -1;
        } else {
            }
        return check;
    }
        public int ValidateForm() {
            String ten = edtUSN.getText().toString().trim();
            String pass = edtPassword.getText().toString().trim();
            String repass = edtConfirmPass.getText().toString().trim();
            String name = edtFN.getText().toString().trim();
            String phone = edtPhoneNumber.getText().toString().trim();
            int check = 1;
            if (ten.length() == 0 || pass.length() == 0 || repass.length() == 0 || name.length() == 0 || phone.length() == 0) {
                edtUSN.setError("Bạn Phải Nhập Đầy Đủ");
                edtPassword.setError("Bạn Phải Nhập Đầy Đủ");
                edtConfirmPass.setError("Bạn Phải Nhập Đầy Đủ");
                edtFN.setError("Bạn Phải Nhập Đầy Đủ");
                edtPhoneNumber.setError("Bạn Phải Nhập Đầy Đủ");
                check = -1;
            } else {
                if (!pass.equals(repass)) {
                    Toast.makeText(getApplicationContext(), "Mật Khẩu Ko Trùng Khớp", Toast.LENGTH_SHORT).show();
                    check = -1;
                }
            }
            return check;
        }
    }



