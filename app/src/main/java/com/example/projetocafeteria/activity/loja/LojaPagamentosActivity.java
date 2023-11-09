package com.example.projetocafeteria.activity.loja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.projetocafeteria.databinding.ActivityLojaPagamentosBinding;

public class LojaPagamentosActivity extends AppCompatActivity {

    private ActivityLojaPagamentosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLojaPagamentosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iniciaComponentes();

        configClicks();
    }

    private void configClicks() {
        binding.include.btnAdd.setOnClickListener(v -> startActivity(new Intent(this, LojaFormPagamentoActivity.class)));
    }

    private void iniciaComponentes() {
        binding.include.textTitulo.setText("Formas de Pagamento");
        binding.include.include.ibVoltar.setOnClickListener(v -> finish());
    }
}