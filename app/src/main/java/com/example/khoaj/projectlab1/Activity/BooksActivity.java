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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.khoaj.projectlab1.Adapter.BookAdapter;
import com.example.khoaj.projectlab1.DAO.BookDAO;
import com.example.khoaj.projectlab1.DAO.GenreDAO;
import com.example.khoaj.projectlab1.Model.Books;
import com.example.khoaj.projectlab1.Model.Genre;
import com.example.khoaj.projectlab1.R;

import java.util.ArrayList;
import java.util.List;

public class BooksActivity extends AppCompatActivity {
    BookDAO bookDAO;
    GenreDAO genreDAO;
    Spinner spnGenre;
    private RecyclerView rvBook;
    private List<Books> booksList;
    private BookAdapter bookAdapter;
    private EditText edtID;
    private EditText edtName;
    private EditText edtAuthor;
    private EditText edtNumber;
    private EditText edtPublisher;
    private EditText edSearch;
    private Paint p = new Paint();
    String maTheLoai = "";
    List<Genre> genreList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rvBook = findViewById(R.id.RecyclerView_Books);
        setTitle("Books");

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
                showDialogThemSach();
            }
        });
        bookDAO = new BookDAO(this);
        booksList = bookDAO.getAllBooks();
        bookAdapter = new BookAdapter(this, booksList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvBook.setLayoutManager(manager);
        rvBook.setAdapter(bookAdapter);
        enableSwipe();
    }

    public void getGenre() {
        genreDAO = new GenreDAO(this);
        genreList = genreDAO.getAllGenre();
        ArrayAdapter<Genre> arrayAdapter = new ArrayAdapter<Genre>(this, android.R.layout.simple_spinner_item, genreList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGenre.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addBookk:
                ShowDialogSearchBook();
                break;


        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.books_menu, menu);
        return true;
    }

    public void SearchBook() {
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i2 < i1) {
                    bookAdapter.resetData();
                }
                bookAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    public void ShowDialogSearchBook() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_search_books, null);
        dialog.setView(dialogView);
        final Dialog dialog1 = dialog.show();
        Button search = dialogView.findViewById(R.id.searchBooks);
        Button huy = dialogView.findViewById(R.id.CancelBooks);
        edSearch = dialogView.findViewById(R.id.edSearchBooks);
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchBook();
                dialog1.dismiss();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void deleteBook(int position) {
        // deleting the note from db
        bookDAO.deleteBook(booksList.get(position));

        // removing the note from the list
        booksList.remove(position);
        bookAdapter.notifyItemRemoved(position);
    }

    private void updateBook(String name, int position) {
        Books n = booksList.get(position);
        // updating note text
        n.setName(name);

        // updating note in db
        bookDAO.updateBook(n);

        // refreshing the list
        booksList.set(position, n);
        bookAdapter.notifyItemChanged(position);

    }

    private void enableSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                bookDAO = new BookDAO(BooksActivity.this);
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    deleteBook(position);

                }
                if (direction == ItemTouchHelper.RIGHT) {
                    showDialogEditNguoiDung(position);
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
        itemTouchHelper.attachToRecyclerView(rvBook);
    }

    public void showDialogEditNguoiDung(final int position) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_edit_book, null);
        dialog.setView(dialogView);
        final Dialog dialog1 = dialog.show();
        Button edit = dialogView.findViewById(R.id.editBook);
        Button huy = dialogView.findViewById(R.id.CancelEditBook);
        edtName = dialogView.findViewById(R.id.edNameBook);
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                updateBook(name, position);
                Toast.makeText(BooksActivity.this, "Thanh Cong", Toast.LENGTH_SHORT).show();
                dialog1.dismiss();
            }
        });
    }

    public void showDialogThemSach() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_insert_books, null);
        dialog.setView(dialogView);
        final Dialog dialog1 = dialog.show();
        Button them = dialogView.findViewById(R.id.addBook);
        Button huy = dialogView.findViewById(R.id.CancelBook);
        edtID = dialogView.findViewById(R.id.edMaSach);
        spnGenre = dialogView.findViewById(R.id.spnTheLoai);
        getGenre();
        edtName = dialogView.findViewById(R.id.addNameBook);
        edtAuthor = dialogView.findViewById(R.id.addAuthorBook);
        edtNumber = dialogView.findViewById(R.id.addNumberBook);
        edtPublisher = dialogView.findViewById(R.id.addPublisherBook);
        spnGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maTheLoai = genreList.get(spnGenre.getSelectedItemPosition()).getMa();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookDAO = new BookDAO(BooksActivity.this);
                Books books = new Books(edtID.getText().toString().trim(), maTheLoai, edtName.getText().toString().trim(), edtAuthor.getText().toString().trim(), edtNumber.getText().toString().trim(), edtPublisher.getText().toString().trim());
                if (ValidateForm() > 0) {
                    if (bookDAO.insertBook(books) > 0) {

                        Toast.makeText(getApplicationContext(), "Đã Thành Công", Toast.LENGTH_SHORT).show();
                        dialog1.dismiss();
                        Intent intent = new Intent(BooksActivity.this, BooksActivity.class);
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
        String name = edtName.getText().toString().trim();
        String author = edtAuthor.getText().toString().trim();
        String number = edtNumber.getText().toString().trim();
        String publisher = edtPublisher.getText().toString().trim();
        int check = 1;
        if (id.length() == 0 || name.length() == 0 || author.length() == 0 || number.length() == 0 || publisher.length() == 0) {
            edtID.setError("Bạn Phải Nhập Đầy Đủ");
            edtName.setError("Bạn Phải Nhập Đầy Đủ");
            edtAuthor.setError("Bạn Phải Nhập Đầy Đủ");
            edtNumber.setError("Bạn Phải Nhập Đầy Đủ");
            edtPublisher.setError("Bạn Phải Nhập Đầy Đủ");
            check = -1;
        }

        return check;
    }
}

