package com.example.projetocafeteria.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.projetocafeteria.MainActivity;
import com.example.projetocafeteria.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(getMainLooper()).postDelayed(() -> {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }, 3000);
    }

}