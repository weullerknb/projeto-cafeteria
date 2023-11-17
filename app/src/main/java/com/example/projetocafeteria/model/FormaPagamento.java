package com.example.projetocafeteria.model;

import com.example.projetocafeteria.helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

public class FormaPagamento implements Serializable {

    private String id;
    private String nome;
    private String descricao;
    private int valor;
    private String tipoValor; // Desconto ou Acréscimo

    public FormaPagamento() {
        DatabaseReference pagamentoRef = FirebaseHelper.getDatabaseReference();
        this.setId(pagamentoRef.push().getKey());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getTipoValor() {
        return tipoValor;
    }

    public void setTipoValor(String tipoValor) {
        this.tipoValor = tipoValor;
    }

    public void salvar() {
        DatabaseReference pagamentoRef = FirebaseHelper.getDatabaseReference()
                .child("formapagamento")
                .child(this.getId());
        pagamentoRef.setValue(this);
    }

    public void remover() {
        DatabaseReference pagamentoRef = FirebaseHelper.getDatabaseReference()
                .child("formapagamento")
                .child(this.getId());
        pagamentoRef.removeValue();
    }
}
