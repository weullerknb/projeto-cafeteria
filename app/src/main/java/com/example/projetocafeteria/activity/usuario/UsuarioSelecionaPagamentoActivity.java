package com.example.projetocafeteria.activity.usuario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.projetocafeteria.adapter.UsuarioPagamentoAdapter;
import com.example.projetocafeteria.databinding.ActivityUsuarioSelecionaPagamentoBinding;
import com.example.projetocafeteria.model.FormaPagamento;

import java.util.ArrayList;
import java.util.List;

public class UsuarioSelecionaPagamentoActivity extends AppCompatActivity implements UsuarioPagamentoAdapter.OnClick {

    private ActivityUsuarioSelecionaPagamentoBinding binding;

    private UsuarioPagamentoAdapter usuarioPagamentoAdapter;
    private List<FormaPagamento> formaPagamentoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsuarioSelecionaPagamentoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iniciaComponentes();

        configClicks();

        configRv();
    }

    private void configClicks() {
        binding.include.include.ibVoltar.setOnClickListener(v -> finish());
    }

    private void configRv() {
        binding.rvPagamentos.setLayoutManager(new LinearLayoutManager(this));
        binding.rvPagamentos.setHasFixedSize(true);
        usuarioPagamentoAdapter = new UsuarioPagamentoAdapter(formaPagamentoList, this);
        binding.rvPagamentos.setAdapter(usuarioPagamentoAdapter);
    }

    private void iniciaComponentes() {
        binding.include.textTitulo.setText("Forma de Pagamento");
    }

    @Override
    public void onClickListener(FormaPagamento formaPagamento) {
        Toast.makeText(this, formaPagamento.getNome(), Toast.LENGTH_SHORT).show();
    }
}