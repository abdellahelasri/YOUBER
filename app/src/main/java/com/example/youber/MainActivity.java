package com.example.youber;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youber.helper.DatabaseHelper;


public class MainActivity extends AppCompatActivity {
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo;
    Button StartBtn;

    @SuppressLint({"WrongViewCast", "Range"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Database creation
        SQLiteDatabase DB = new DatabaseHelper(getApplicationContext()).getReadableDatabase();
        Log.i("Database created","Success");

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        image = findViewById(R.id.imageView);

        image.setAnimation(topAnim);

        //Le boutton commencer
        StartBtn=findViewById(R.id.StartButton);
        StartBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });


    }

}