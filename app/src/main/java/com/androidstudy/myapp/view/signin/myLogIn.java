package com.androidstudy.myapp.view.signin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.androidstudy.myapp.R;
import com.androidstudy.myapp.utils.SystemUtils;
import com.androidstudy.myapp.utils.Utils;
import com.androidstudy.myapp.view.signup.mySignUp;
import com.androidstudy.myapp.view.startmain.myAppMain;
import com.androidstudy.myapp.utils.DBHelper;

public class myLogIn extends AppCompatActivity implements View.OnClickListener {
    private ImageView appLogo;
    private TextView tvWelcomeThere;
    private TextView tvSignInToContinue;
    private TextInputLayout username, password;
    private Button goButton, signUpButton;
    private TextInputEditText editUserName, editPassword;

    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myapp_log_in);
        dbHelper = new DBHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        initView();
    }

    private void initView() {
        appLogo = findViewById(R.id.logo_image);
        tvWelcomeThere = findViewById(R.id.welcome_message);
        tvSignInToContinue = findViewById(R.id.sign_in_message);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        goButton = findViewById(R.id.goButton);
        signUpButton = findViewById(R.id.signUpButton);

        editPassword = findViewById(R.id.editPassword);
        editUserName = findViewById(R.id.editUsername);
        goButton.setOnClickListener(this);

        signUpButton.setOnClickListener(this);

    }

    private void openSignUpPage() {

        //Attach all the elements those you want to animate in design
        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<View, String>(appLogo, "app_icon");
        pairs[1] = new Pair<View, String>(tvWelcomeThere, getString(R.string.welcome));
        pairs[2] = new Pair<View, String>(tvSignInToContinue, getString(R.string.signup_to_start_your_new_journey));
        pairs[3] = new Pair<View, String>(username, getString(R.string.username));

        pairs[4] = new Pair<View, String>(password, getString(R.string.password));
        pairs[5] = new Pair<View, String>(goButton, getString(R.string.sign_up));

        pairs[6] = new Pair<View, String>(signUpButton, getString(R.string.already_have_an_account_login));

        Intent intent = new Intent(myLogIn.this, mySignUp.class);

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                myLogIn.this, pairs);


        startActivity(intent, options.toBundle());
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.goButton:
                loginValidation();
                Check();
                break;
            case R.id.signUpButton:
                openSignUpPage();
                break;
            default:
                break;
        }
    }

    private void loginValidation() {
        if (Utils.inputValidation(editUserName)) {
            username.setErrorEnabled(false);

            if (Utils.inputValidation(editPassword)) {
                SystemUtils.hideKeyBoard(this);
                password.setErrorEnabled(false);
            } else {
                password.setError("请输入密码");
            }
        } else {
            username.setError("请输入用户名");
        }
    }

    public void Check() {
        String username = editUserName.getText().toString().trim();
        String userpassword = editPassword.getText().toString().trim();

        //判定登录条件
        Cursor cursor = sqLiteDatabase.query("user", new String[]{"password"}, "username=?", new String[]{username}, null, null, null);
        if (username.equals("")) {
            Toast.makeText(this, "用户名为空", Toast.LENGTH_SHORT).show();
        } else {
            if (cursor.moveToNext()) {
                String upassword = cursor.getString(cursor.getColumnIndex("password"));
                if (userpassword.equals(upassword)) {
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(myLogIn.this, myAppMain.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "账号不存在", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
