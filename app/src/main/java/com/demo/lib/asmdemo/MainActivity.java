package com.demo.lib.asmdemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.demo.lib.annotations.Cost;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static int MESSAGE_KEY = 0x2019;
    @SuppressLint("HandlerLeak")
    private static Handler sHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MESSAGE_KEY) {
                if (msg.obj != null) {
                    Log.i("xmq", String.valueOf(msg.obj));
                }
            }
        }
    };
    /**
     * kotlinic
     */
    private Button mGetUser;
    /**
     * 移除用户
     */
    private Button mRemoveUser;
    /**
     * 获取文章
     */
    private Button mGetArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        sendMessage(getClass().getSimpleName());
    }

    @Cost
    private String sendMessage(String string) {
        Message message = new Message();
        message.what = MESSAGE_KEY;
        sHandler.sendMessage(message);
        return string;
    }

    @Cost
    private void initView() {
        mGetUser = (Button) findViewById(R.id.getUser);
        mGetUser.setOnClickListener(this);
        mRemoveUser = (Button) findViewById(R.id.removeUser);
        mRemoveUser.setOnClickListener(this);
        mGetArticle = (Button) findViewById(R.id.getArticle);
        mGetArticle.setOnClickListener(this);
    }

    @Cost
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                sendMessage("sf----");
                break;
            case R.id.getUser:
                break;
            case R.id.removeUser:
                break;
            case R.id.getArticle:
                initView();
                break;
        }
    }
}
