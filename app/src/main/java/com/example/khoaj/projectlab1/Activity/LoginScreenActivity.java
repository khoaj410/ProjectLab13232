package com.example.khoaj.projectlab1.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.khoaj.projectlab1.DAO.UserDAO;
import com.example.khoaj.projectlab1.Model.User;
import com.example.khoaj.projectlab1.R;
import com.example.khoaj.projectlab1.SQLite.DatabaseHelper;

public class LoginScreenActivity extends AppCompatActivity {
    private EditText edtUserName, edtPassword;
    private Button btn1;
    private DatabaseHelper dbHelper;
    CheckBox chkRememberPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        edtUserName = findViewById(R.id.editText);
        edtPassword = findViewById(R.id.editText2);
        btn1 = findViewById(R.id.btnLogin);
        dbHelper = new DatabaseHelper(this);
        chkRememberPass = findViewById(R.id.chkRememberPass);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUserName.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (password.length() < 6 || username.isEmpty() || password.isEmpty()) {

                    if (username.isEmpty()) edtUserName.setError(getString(R.string.notify_empty_user));

                    if (password.length() < 6)
                       edtPassword.setError(getString(R.string.notify_length_pass));

                    if (password.isEmpty())
                        edtPassword.setError(getString(R.string.notify_empty_pass));

                } else {
//                    UserDAO userDAO = new UserDAO(dbHelper);
//                    User user = userDAO.getUser(username);
//                    Log.e("user", "" + user.getUsername());
//                    if (user == null) {
//                        Toast.makeText(
//                                LoginScreenActivity.this,
//                                "Sai", Toast.LENGTH_SHORT).show();
//                    } else {
//                        String passwordOnDB = user.getPassword();
//                        Log.e("passwordOnDB", "" + passwordOnDB);
//                        if (passwordOnDB.equals(password)) {
                            final ProgressDialog progressDialog = new ProgressDialog(LoginScreenActivity.this, R.style.Theme_AppCompat_Light_Dialog);
                            progressDialog.setIndeterminate(true);
                            progressDialog.setMessage("Đang Truy Cập");
                            progressDialog.show();

                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            btn1.setEnabled(true);
                                            progressDialog.dismiss();
                                        }
                                    }, 3000);
                            rememberUser(username,password,chkRememberPass.isChecked());
                            startActivity(new Intent(LoginScreenActivity.this, MainBackGroundActivity.class));

//                        } else {
//                            Toast.makeText(
//                                    LoginScreenActivity.this,
//                                    "Sai", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }


                }

            }
        });
    }
        public void rememberUser (String u, String p, boolean status){
            SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
            SharedPreferences.Editor edit = pref.edit();
            if (!status) {
                //xoa tinh trang luu tru truoc do
                edit.clear();
            } else {
                //luu du lieu
                edit.putString("USERNAME", u);
                edit.putString("PASSWORD", p);
                edit.putBoolean("REMEMBER", status);
            }
            //luu lai toan bo
            edit.commit();

        }
    }
