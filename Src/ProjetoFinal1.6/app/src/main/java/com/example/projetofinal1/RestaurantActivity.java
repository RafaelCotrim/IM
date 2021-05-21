package com.example.projetofinal1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RestaurantActivity extends AppCompatActivity {

    public static final String RES_ID = "res_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
    }

    public  void goToMenu(View v){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}