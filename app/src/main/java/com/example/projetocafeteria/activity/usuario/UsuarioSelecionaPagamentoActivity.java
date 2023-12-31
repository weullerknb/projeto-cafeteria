package com.example.projetocafeteria.activity.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.projetocafeteria.adapter.UsuarioPagamentoAdapter;
import com.example.projetocafeteria.databinding.ActivityUsuarioSelecionaPagamentoBinding;
import com.example.projetocafeteria.helper.FirebaseHelper;
import com.example.projetocafeteria.model.FormaPagamento;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsuarioSelecionaPagamentoActivity extends AppCompatActivity implements UsuarioPagamentoAdapter.OnClick {

    private ActivityUsuarioSelecionaPagamentoBinding binding;

    private UsuarioPagamentoAdapter usuarioPagamentoAdapter;
    private final List<FormaPagamento> formaPagamentoList = new ArrayList<>();

    private FormaPagamento formaPagamento = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsuarioSelecionaPagamentoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iniciaComponentes();

        configClicks();

        configRv();

        recuperaFormaPagamento();
    }

    private void configClicks() {
        binding.include.include.ibVoltar.setOnClickListener(v -> finish());

        binding.btnContinuar.setOnClickListener(v -> {
            if (formaPagamento != null) {
                Intent intent = new Intent(this, UsuarioResumoPedidoActivity.class);
                intent.putExtra("pagamentoSelecionado", formaPagamento);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Selecione uma forma de pagamento.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void recuperaFormaPagamento() {
        DatabaseReference pagamentoRef = FirebaseHelper.getDatabaseReference()
                .child("formapagamento");
        pagamentoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    formaPagamentoList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        FormaPagamento formaPagamento = ds.getValue(FormaPagamento.class);
                        formaPagamentoList.add(formaPagamento);
                    }
                    binding.textInfo.setText("");
                } else {
                    binding.textInfo.setText("Nenhuma forma de pagamento cadastrada.");
                }

                binding.progressBar.setVisibility(View.GONE);
                Collections.reverse(formaPagamentoList);
                usuarioPagamentoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
    public void onClickListener(FormaPagamento pagamento) {
        this.formaPagamento = pagamento;
    }
}