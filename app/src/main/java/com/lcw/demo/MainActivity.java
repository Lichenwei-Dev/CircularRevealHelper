
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
    private Button mButton;

    private boolean isShow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mView = findViewById(R.id.view);
        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (isShow) {
                    isShow = false;
                    RevealAnimationHelper.hideView(mView, 400, new RevealAnimationHelper.AnimationListener() {
                        @Override
                        public void onAnimationEnd() {
                            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                            RevealAnimationHelper.startActivityForResult(MainActivity.this, intent, 1, mView, 400);
                        }
                    });
                } else {
                    isShow = true;
                    RevealAnimationHelper.showView(mView, 400);
                }
            }
        });


    }

}
