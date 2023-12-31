package com.example.projetocafeteria.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetocafeteria.R;
import com.example.projetocafeteria.model.FormaPagamento;
import com.example.projetocafeteria.util.GetMask;

import java.util.List;

public class LojaPagamentoAdapter extends RecyclerView.Adapter<LojaPagamentoAdapter.MyViewHolder> {

    private final Context context;
    private final List<FormaPagamento> formaPagamentoList;
    private final OnClick onClick;

    public LojaPagamentoAdapter(Context context, List<FormaPagamento> formaPagamentoList, OnClick onClick) {
        this.context = context;
        this.formaPagamentoList = formaPagamentoList;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forma_pagamento_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FormaPagamento formaPagamento = formaPagamentoList.get(position);

        holder.textNomePagamento.setText(formaPagamento.getNome());
        holder.textDescricaoPagamento.setText(formaPagamento.getDescricao());
        holder.textValor.setText(formaPagamento.getValor() + "%");

        if (formaPagamento.getTipoValor().equals("DESC")) {
            holder.textTipoPagamento.setText("Desconto");
        } else {
            holder.textTipoPagamento.setText("Acréscimo");
        }

        holder.itemView.setOnClickListener(v -> onClick.onClickListener(formaPagamento));
    }

    @Override
    public int getItemCount() {
        return formaPagamentoList.size();
    }

    public interface OnClick {
        void onClickListener(FormaPagamento formaPagamento);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textNomePagamento, textDescricaoPagamento, textValor, textTipoPagamento;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textNomePagamento = itemView.findViewById(R.id.textNomePagamento);
            textDescricaoPagamento = itemView.findViewById(R.id.textDescricaoPagamento);
            textValor = itemView.findViewById(R.id.textValor);
            textTipoPagamento = itemView.findViewById(R.id.textTipoPagamento);
        }
    }

}
