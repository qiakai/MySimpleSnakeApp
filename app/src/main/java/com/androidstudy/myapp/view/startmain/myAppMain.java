package com.androidstudy.myapp.view.startmain;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import com.androidstudy.myapp.R;

public class myAppMain extends AppCompatActivity implements View.OnClickListener {

    private Button btn0, btn1, btn2, btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myapp_main);
        btn0 = findViewById(R.id.ui_btn0);
        btn1 = findViewById(R.id.ui_btn1);
        btn2 = findViewById(R.id.ui_btn2);
        btn3 = findViewById(R.id.ui_btn3);
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ui_btn0:
                Toast.makeText(this, "000", Toast.LENGTH_SHORT).show();
                Intent intent0 = new Intent(myAppMain.this, myAppA.class);
                startActivity(intent0);
                break;
            case R.id.ui_btn1:
                Toast.makeText(this, "111", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(myAppMain.this, myAppB.class);
                startActivity(intent1);
                break;
            case R.id.ui_btn2:
                Toast.makeText(this, "222", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(myAppMain.this, myAppC.class);
                startActivity(intent2);
                break;
            case R.id.ui_btn3:
                Toast.makeText(this, "333", Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(myAppMain.this, myAppSnake.class);
                startActivity(intent3);
                break;
            default:
                Intent intent4 = new Intent(myAppMain.this, myAppSnake.class);
                startActivity(intent4);
                break;
        }
    }
}
