package com.androidstudy.myapp.view.signup;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.androidstudy.myapp.R;
import com.androidstudy.myapp.utils.DBHelper;
import com.androidstudy.myapp.utils.SystemUtils;
import com.androidstudy.myapp.utils.Utils;
import com.androidstudy.myapp.view.signin.myLogIn;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class mySignUp extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText editUserName, editPassword;
    private TextInputLayout username, password;

    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myapp_sign_up);
        dbHelper = new DBHelper(mySignUp.this);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        initView();
    }

    private void initView() {
        editUserName = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        findViewById(R.id.alreadyHaveAccount).setOnClickListener(this);
        findViewById(R.id.signUpButton).setOnClickListener(this);
    }

    private void inputValidation() {
        SystemUtils.hideKeyBoard(this);

        if (!Utils.inputValidation(editUserName)) {
            username.setError("请输入用户名");
            return;
        } else {
            username.setErrorEnabled(false);
        }
        if (!Utils.inputValidation(editPassword)) {
            password.setError("请输入密码");
            return;
        } else {
            password.setErrorEnabled(false);
        }

//        Toast.makeText(this, "继续注册", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signUpButton:
                inputValidation();
                new AlertDialog.Builder(mySignUp.this).setTitle("提示").setMessage("是否前往登录界面")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String username = editUserName.getText().toString();
                                String password = editPassword.getText().toString();
                                Cursor cursor = sqLiteDatabase.query("user", new String[]{"username"}, "username=?", new String[]{username}, null, null, null);
                                if (cursor.getCount() != 0) {
                                    Toast.makeText(mySignUp.this, "该用户已注册", Toast.LENGTH_SHORT).show();
                                } else {
                                    dbHelper.addData(sqLiteDatabase, username, password);
                                    Toast.makeText(mySignUp.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(mySignUp.this, myLogIn.class);
                                    startActivity(intent);
                                }
                            }
                        }).setNegativeButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                break;
            case R.id.alreadyHaveAccount:
                onBackPressed();
                break;
            default:
                break;
        }
    }
}
