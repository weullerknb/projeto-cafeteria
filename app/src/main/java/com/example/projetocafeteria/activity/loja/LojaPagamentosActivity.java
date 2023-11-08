package com.example.projetocafeteria.activity.loja;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.projetocafeteria.R;
import com.example.projetocafeteria.databinding.ActivityLojaPagamentosBinding;

public class LojaPagamentosActivity extends AppCompatActivity {

    private ActivityLojaPagamentosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLojaPagamentosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iniciaComponentes();
    }

    private void iniciaComponentes() {
        binding.include.textTitulo.setText("Formas de Pagamento");
        binding.include.include.ibVoltar.setOnClickListener(v -> finish());
    }
}