package com.Linmingming.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.Linmingming.MainActivity;
import com.Linmingming.R;

public class WelcomeActivity extends AppCompatActivity {

    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                startMainActivity();

            }
        }, 2000);
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));


        finish();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // 立即进入
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startMainActivity();

            return true;
        }


        return super.onTouchEvent(event);

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        handler.removeCallbacksAndMessages(null);

    }
}
