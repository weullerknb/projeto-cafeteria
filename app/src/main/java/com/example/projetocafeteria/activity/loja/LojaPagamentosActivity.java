package com.example.projetocafeteria.activity.loja;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.projetocafeteria.adapter.LojaPagamentoAdapter;
import com.example.projetocafeteria.databinding.ActivityLojaPagamentosBinding;
import com.example.projetocafeteria.model.FormaPagamento;

import java.util.ArrayList;
import java.util.List;

public class LojaPagamentosActivity extends AppCompatActivity implements LojaPagamentoAdapter.OnClick {

    private ActivityLojaPagamentosBinding binding;

    private LojaPagamentoAdapter lojaPagamentoAdapter;

    private List<FormaPagamento> formaPagamentoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLojaPagamentosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iniciaComponentes();

        configClicks();

        configRv();
    }

    private void configRv() {
        binding.rvPagamentos.setLayoutManager(new LinearLayoutManager(this));
        binding.rvPagamentos.setHasFixedSize(true);
        lojaPagamentoAdapter = new LojaPagamentoAdapter( this, formaPagamentoList, this);
        binding.rvPagamentos.setAdapter(lojaPagamentoAdapter);
    }

    private void configClicks() {
        binding.include.btnAdd.setOnClickListener(v -> startActivity(new Intent(this, LojaFormPagamentoActivity.class)));
    }

    private void iniciaComponentes() {
        binding.include.textTitulo.setText("Formas de Pagamento");
        binding.include.include.ibVoltar.setOnClickListener(v -> finish());
    }

    @Override
    public void onClickListener(FormaPagamento formaPagamento) {

    }
}