package com.example.projetocafeteria.activity.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.projetocafeteria.R;
import com.example.projetocafeteria.adapter.DetalhesPedidoAdapter;
import com.example.projetocafeteria.databinding.ActivityDetalhesPedidoBinding;
import com.example.projetocafeteria.model.Endereco;
import com.example.projetocafeteria.model.Pedido;
import com.example.projetocafeteria.util.GetMask;

public class DetalhesPedidoActivity extends AppCompatActivity {

    private ActivityDetalhesPedidoBinding binding;

    private Pedido pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetalhesPedidoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iniciaComponentes();

        getExtra();

        configClicks();
    }

    private void configRv() {
        binding.rvProdutos.setLayoutManager(new LinearLayoutManager(this));
        binding.rvProdutos.setHasFixedSize(true);
        DetalhesPedidoAdapter detalhesPedidoAdapter = new DetalhesPedidoAdapter(pedido.getItemPedidoList(), this);
        binding.rvProdutos.setAdapter(detalhesPedidoAdapter);
    }

    private void configClicks() {
        binding.include.include.ibVoltar.setOnClickListener(v -> finish());
    }

    private void getExtra() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            pedido = (Pedido) bundle.getSerializable("pedidoSelecionado");

            configRv();

            configDados();
        }
    }

    private void configDados() {

        Endereco endereco = pedido.getEndereco();
        StringBuilder enderecoCompleto = new StringBuilder();

        enderecoCompleto.append(endereco.getLogradouro())
                .append(", ")
                .append(endereco.getNumero())
                .append("\n")
                .append(endereco.getBairro())
                .append(", ")
                .append(endereco.getLocalidade())
                .append("/")
                .append(endereco.getUf())
                .append("\n")
                .append("CEP: ")
                .append(endereco.getCep());

        binding.textEnderecoEntrega.setText(enderecoCompleto);

        binding.textNomePagamento.setText(pedido.getPagamento());


        double valorExtra;
        double totalPedido = pedido.getTotal();
        if (pedido.getAcrescimo() > 0) {
            binding.textValorTipo.setText("Acréscimo");
            binding.textValorTipoPagamento.setText(pedido.getAcrescimo() + "%");
            valorExtra = (double) pedido.getAcrescimo() / 100;
            totalPedido += (totalPedido * valorExtra);
        } else {
            binding.textValorTipo.setText("Desconto");
            binding.textValorTipoPagamento.setText(pedido.getDesconto() + "%");
            valorExtra = (double) pedido.getDesconto() / 100;
            totalPedido -= (totalPedido * valorExtra);
        }

        binding.textValorProdutos.setText(getString(R.string.valor, GetMask.getValor(pedido.getTotal())));

        binding.textValorTotal.setText(getString(R.string.valor, GetMask.getValor(totalPedido)));
    }

    private void iniciaComponentes() {
        binding.include.textTitulo.setText("Detalhes do Pedido");
    }
}