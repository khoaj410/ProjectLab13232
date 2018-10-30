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

import com.example.khoaj.projectlab1.Adapter.GenreAdapter;
import com.example.khoaj.projectlab1.Adapter.UserAdapter;
import com.example.khoaj.projectlab1.DAO.GenreDAO;
import com.example.khoaj.projectlab1.DAO.UserDAO;
import com.example.khoaj.projectlab1.Model.Books;
import com.example.khoaj.projectlab1.Model.Genre;
import com.example.khoaj.projectlab1.Model.User;
import com.example.khoaj.projectlab1.R;

import java.util.List;

public class GenreActivity extends AppCompatActivity {
    GenreDAO genreDAO;
    private RecyclerView rvGenre;
    private List<Genre> listGenres;
    private GenreAdapter genreAdapter;
    private EditText edtID;
    private EditText edtName;
    private Paint p = new Paint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        rvGenre = findViewById(R.id.RecyclerView_Genre);
        setSupportActionBar(toolbar);
        setTitle("Genre");
        toolbar.setNavigationIcon(R.drawable.undo);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenreActivity.this,MainBackGroundActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogThemTheLoai();
            }
        });

        genreDAO = new GenreDAO(this);
        listGenres = genreDAO.getAllGenre();
        genreAdapter = new GenreAdapter(this,listGenres);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvGenre.setLayoutManager(manager);
        rvGenre.setAdapter(genreAdapter);
        enableSwipe();
    }
    private void deleteGenre(int position) {
        // deleting the note from db
        genreDAO.deleteGenre(listGenres.get(position));

        // removing the note from the list
        listGenres.remove(position);
        genreAdapter.notifyItemRemoved(position);
    }
    private void updateGenre(String name, int position) {
        Genre n = listGenres.get(position);
        // updating note text
        n.setName(name);

        // updating note in db
        genreDAO.updateGenre(n);

        // refreshing the list
        listGenres.set(position, n);
        genreAdapter.notifyItemChanged(position);

    }
    private void enableSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                genreDAO = new GenreDAO(GenreActivity.this);
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT){
                    deleteGenre(position);

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
        itemTouchHelper.attachToRecyclerView(rvGenre);
    }

    public void showDialogEditNguoiDung(final int position) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_edit_genre, null);
        dialog.setView(dialogView);
        final Dialog dialog1 = dialog.show();
        Button edit = dialogView.findViewById(R.id.editGenre);
        Button huy = dialogView.findViewById(R.id.CancelEditGenre);
        edtName = dialogView.findViewById(R.id.edNameGenre);
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtName.getText().toString();
                updateGenre(username,position);
                Toast.makeText(GenreActivity.this,"Thanh Cong",Toast.LENGTH_SHORT).show();
                dialog1.dismiss();
            }
        });
    }
    public void showDialogThemTheLoai() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_insert_genre, null);
        dialog.setView(dialogView);
        final Dialog dialog1 = dialog.show();
        Button them = dialogView.findViewById(R.id.addGenre);
        Button huy = dialogView.findViewById(R.id.CancelGenre);
        edtID = dialogView.findViewById(R.id.IdGenre);
        edtName = dialogView.findViewById(R.id.NameGenre);
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genreDAO = new GenreDAO(GenreActivity.this);
                Genre genre = new Genre(edtID.getText().toString(), edtName.getText().toString());
                if (ValidateForm() > 0) {
                    if (genreDAO.insertGenre(genre) > 0) {

                        Toast.makeText(getApplicationContext(), "Đã Thành Công", Toast.LENGTH_SHORT).show();
                        dialog1.dismiss();
                        Intent intent = new Intent(GenreActivity.this, GenreActivity.class);
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
            String name= edtName.getText().toString().trim();
            int check = 1;
            if (id.length() == 0 || name.length()  == 0) {
                edtID.setError("Bạn Phải Nhập Đầy Đủ");
                edtName.setError("Bạn Phải Nhập Đầy Đủ");
                check = -1;
            }
            return check;
        }
    }

