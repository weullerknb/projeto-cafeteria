package com.example.projetocafeteria.activity.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.projetocafeteria.activity.usuario.MainActivityUsuario;
import com.example.projetocafeteria.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(getMainLooper()).postDelayed(() -> {
            finish();
            startActivity(new Intent(this, MainActivityUsuario.class));
        }, 3000);
    }

}