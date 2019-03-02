
package com.lcw.demo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.lcw.library.utils.RevealAnimationHelper;

public class MainActivity extends AppCompatActivity {

    private ImageView mView;
    private boolean isShow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mView = findViewById(R.id.view);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (isShow) {
                    isShow = false;
                    RevealAnimationHelper.hideView(mView, 400);
                } else {
                    isShow = true;
                    RevealAnimationHelper.showView(mView, 400);
                }
            }
        });

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                RevealAnimationHelper.startActivity(MainActivity.this, intent, view, R.color.colorAccent, 400);
            }
        });


    }

}
