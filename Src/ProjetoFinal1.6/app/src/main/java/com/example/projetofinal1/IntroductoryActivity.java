package com.example.projetofinal1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class IntroductoryActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 9000; //tempo da IntroductoryActivity

    //Variaveis
    ImageView appname;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);


        appname = findViewById(R.id.name);
        lottieAnimationView = findViewById(R.id.lottie);

        appname.animate().translationY(500).setDuration(2000).setStartDelay(1000);
        lottieAnimationView.animate().translationY(-1000).setDuration(5000).setStartDelay(200);



        //No fim desta atividade é lançada a LoginActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(IntroductoryActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }

}