package com.example.projetocafeteria.activity.loja;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.projetocafeteria.R;
import com.example.projetocafeteria.databinding.ActivityLojaFormPagamentoBinding;

public class LojaFormPagamentoActivity extends AppCompatActivity {

    private ActivityLojaFormPagamentoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLojaFormPagamentoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}