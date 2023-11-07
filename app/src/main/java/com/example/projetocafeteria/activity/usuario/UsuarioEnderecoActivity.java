package com.example.projetocafeteria.activity.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.projetocafeteria.R;
import com.example.projetocafeteria.databinding.ActivityUsuarioEnderecoBinding;

public class UsuarioEnderecoActivity extends AppCompatActivity {

    private ActivityUsuarioEnderecoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsuarioEnderecoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iniciaComponentes();

        configClicks();
    }

    private void configClicks() {
        binding.include.btnAdd.setOnClickListener(v ->
                startActivity(new Intent(this, UsuarioFormEnderecoActivity.class))
        );
    }

    private void iniciaComponentes() {
        binding.include.textTitulo.setText("Meus Endereços");
        binding.include.include.ibVoltar.setOnClickListener(v -> finish());
    }
}