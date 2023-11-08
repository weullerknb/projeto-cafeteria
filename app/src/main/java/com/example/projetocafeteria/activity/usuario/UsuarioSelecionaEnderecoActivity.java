package com.example.projetocafeteria.activity.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.projetocafeteria.R;
import com.example.projetocafeteria.databinding.ActivityUsuarioSelecionaEnderecoBinding;

public class UsuarioSelecionaEnderecoActivity extends AppCompatActivity {

    private ActivityUsuarioSelecionaEnderecoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsuarioSelecionaEnderecoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iniciaComponentes();

        configClicks();
    }

    private void configClicks() {
        binding.include.include.ibVoltar.setOnClickListener(v -> finish());
    }

    private void iniciaComponentes() {
        binding.include.textTitulo.setText("Endereço de Entrega");
    }
}