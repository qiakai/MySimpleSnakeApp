package com.androidstudy.myapp.view.startmain;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.androidstudy.myapp.R;

public class myAppSnake extends AppCompatActivity implements View.OnClickListener{
    private SnakeView mSnakeView;
    private static final int REFRESH = 1;
    private static final int REFRESHINTERVAL = 100;
    private boolean isPaused = false;
//    private TextView textView=null;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if(msg.arg1 == REFRESH) {
                if(mSnakeView != null) {
                    mSnakeView.invalidate();
                }
            }
        }

    };

    private Thread mRefreshThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myapp_snake);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);

        mSnakeView = (SnakeView) findViewById(R.id.snake_view);
        isPaused = false;
//        textView =(TextView)findViewById(R.id.num);
        mRefreshThread = new Thread("TimerThread") {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                super.run();
                while (!isPaused) {
                    Message msg = mHandler.obtainMessage();
                    msg.arg1 = REFRESH;
                    mHandler.sendMessage(msg);
                    try {
                        Thread.sleep(REFRESHINTERVAL);
//                        if (mSnakeView.isEat()) {
//                            textView.setText(String.valueOf(mSnakeView.getfenshu()));
//                        }
//                        if (mSnakeView.isDeath() == true) {
//                            new AlertDialog.Builder(myappsnake.this).setTitle("分数").setMessage(String.valueOf(mSnakeView.getfenshu()))
//                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            Intent intent = new Intent(myappsnake.this, myappmain.class);
//                                            startActivity(intent);
//                                        }
//                                    }).setNegativeButton("返回", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            }).show();
//                        }
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        mRefreshThread.start();
    }
    @SuppressWarnings("deprecation")
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        isPaused = true;
    }


    @SuppressWarnings("deprecation")
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        isPaused = true;
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        isPaused = false;
        if(!mRefreshThread.isAlive()) {
            mRefreshThread.start();
        }
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn1:
                mSnakeView.changeSnake(1);
                break;
            case R.id.btn2:
                mSnakeView.changeSnake(2);
                break;
            case R.id.btn3:
                mSnakeView.changeSnake(3);
                break;
            case R.id.btn4:
                mSnakeView.changeSnake(4);
                break;
            default:
                break;
        }
    }
}
