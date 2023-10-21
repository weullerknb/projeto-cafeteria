package com.example.projetocafeteria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetocafeteria.autenticacao.LoginActivity;
import com.example.projetocafeteria.databinding.ActivityMainBinding;
import com.example.projetocafeteria.helper.FirebaseHelper;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private TextView textInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(view -> {
            if (FirebaseHelper.getAutenticado()) {
                Toast.makeText(this, "Usuário já autenticado!", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
        });
    }
}