package com.example.projetocafeteria.model;

import com.example.projetocafeteria.helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

public class Produto {

    private String id; // id Firebase
    private int idLocal; //id Local
    private String titulo;
    private String descricao;
    private double precoAntigo;
    private double precoAtual;
    private boolean rascunho = false;
    private List<String> idsCategorias = new ArrayList<>();
    private List<String> urlsImagens = new ArrayList<>();

    public Produto() {
        DatabaseReference produtoRef = FirebaseHelper.getDatabaseReference();
        this.setId(produtoRef.push().getKey());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPrecoAntigo() {
        return precoAntigo;
    }

    public void setPrecoAntigo(double precoAntigo) {
        this.precoAntigo = precoAntigo;
    }

    public double getPrecoAtual() {
        return precoAtual;
    }

    public void setPrecoAtual(double precoAtual) {
        this.precoAtual = precoAtual;
    }

    public boolean isRascunho() {
        return rascunho;
    }

    public void setRascunho(boolean rascunho) {
        this.rascunho = rascunho;
    }

    public List<String> getIdsCategorias() {
        return idsCategorias;
    }

    public void setIdsCategorias(List<String> idsCategorias) {
        this.idsCategorias = idsCategorias;
    }

    public List<String> getUrlsImagens() {
        return urlsImagens;
    }

    public void setUrlsImagens(List<String> urlsImagens) {
        this.urlsImagens = urlsImagens;
    }

    public void salvar(boolean novoProduto) {

    }
}
