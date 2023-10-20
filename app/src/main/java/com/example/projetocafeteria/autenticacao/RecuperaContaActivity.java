package com.example.projetocafeteria.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.projetocafeteria.R;
import com.example.projetocafeteria.databinding.ActivityRecuperaContaBinding;

public class RecuperaContaActivity extends AppCompatActivity {

    private ActivityRecuperaContaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecuperaContaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configClicks();
    }

    private void configClicks() {
        binding.include.ibVoltar.setOnClickListener(view -> finish());
    }
}