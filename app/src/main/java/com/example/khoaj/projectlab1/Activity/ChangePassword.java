package com.example.khoaj.projectlab1.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.khoaj.projectlab1.DAO.UserDAO;
import com.example.khoaj.projectlab1.Model.User;
import com.example.khoaj.projectlab1.R;

public class ChangePassword extends AppCompatActivity {
    private Button btnChange;
    private EditText pass, repass;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarChangePass);
        btnChange = findViewById(R.id.btnLogin);
        pass = findViewById(R.id.edPass);
        repass = findViewById(R.id.edRePass);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.undo);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangePassword.this, MainBackGroundActivity.class);
                startActivity(intent);
            }
        });
    }

    public int validateForm(){
        int check = 1;
        if (pass.getText().length()==0 || repass.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "Bạn phải nhập đầy đủ thông ",
                    Toast.LENGTH_SHORT).show();
            check = -1;
        }else {
            String pass1 = pass.getText().toString();
            String rePass1 = repass.getText().toString();
            if (!pass1.equals(rePass1)) {
                Toast.makeText(getApplicationContext(), "Mật khẩu không trùng khớp",
                        Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
    public void changePassword(View view) {
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        String strUserName = pref.getString("USERNAME","");
        userDAO = new UserDAO(ChangePassword.this);
        User user = new User(strUserName, "", pass.getText().toString(),"");
            if (validateForm()>0){
                if (userDAO.changePasswordNguoiDung(user) > 0) {
                    Toast.makeText(getApplicationContext(), "Lưu thành công",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Lưu thất bại",
                            Toast.LENGTH_SHORT).show();
                }
            }
            finish();
        }
        }


