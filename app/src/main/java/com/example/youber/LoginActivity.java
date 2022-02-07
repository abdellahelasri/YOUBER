package com.example.youber;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.google.android.material.animation.AnimationUtils;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_login);



        /*new Handler().postDelayed((){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            Pair [] pairs = new Pair [];
            pairs [0]=new Pair<View , String>(, "logo_image");
            pairs [1]=new Pair<View , String>(, "logo_text");


            ActivityOptions options =ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
            startActivity(intent,options.toBundle());

        },SPLACH_SCREEN)*/

    }
}