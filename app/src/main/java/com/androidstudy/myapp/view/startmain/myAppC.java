package com.androidstudy.myapp.view.startmain;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.androidstudy.myapp.R;

public class myAppC extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myapp_r2);
        TextView textView = (TextView) findViewById(R.id.text_r2);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
}