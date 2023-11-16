package com.example.projetocafeteria.activity.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.projetocafeteria.DAO.ItemDAO;
import com.example.projetocafeteria.DAO.ItemPedidoDAO;
import com.example.projetocafeteria.R;
import com.example.projetocafeteria.adapter.LojaProdutoAdapter;
import com.example.projetocafeteria.adapter.SliderAdapter;
import com.example.projetocafeteria.databinding.ActivityDetalhesProdutoBinding;
import com.example.projetocafeteria.databinding.DialogAddItemCarrinhoBinding;
import com.example.projetocafeteria.databinding.DialogRemoverCarrinhoBinding;
import com.example.projetocafeteria.helper.FirebaseHelper;
import com.example.projetocafeteria.model.Favorito;
import com.example.projetocafeteria.model.ItemPedido;
import com.example.projetocafeteria.model.Produto;
import com.example.projetocafeteria.util.GetMask;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DetalhesProdutoActivity extends AppCompatActivity implements LojaProdutoAdapter.OnClickLister, LojaProdutoAdapter.OnClickFavorito {

    private ActivityDetalhesProdutoBinding binding;

    private final List<String> idsFavoritos = new ArrayList<>();
    private final List<Produto> produtoList = new ArrayList<>();

    private LojaProdutoAdapter lojaProdutoAdapter;

    private Produto produtoSelecionado;

    private ItemDAO itemDAO;
    private ItemPedidoDAO itemPedidoDAO;

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetalhesProdutoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        itemDAO = new ItemDAO(this);
        itemPedidoDAO = new ItemPedidoDAO(this);

        configClicks();

        getExtra();

        recuperaFavoritos();

        configRvProdutos();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) dialog.dismiss();
    }

    private void configClicks() {
        binding.include.textTitulo.setText("Detalhes do Produto");
        binding.include.include.ibVoltar.setOnClickListener(v -> finish());

        binding.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if (FirebaseHelper.getAutenticado()) {
                    idsFavoritos.add(produtoSelecionado.getId());
                    Favorito.salvar(idsFavoritos);
                } else {
                    Toast.makeText(getBaseContext(), "Entre na sua conta para adicionar aos favoritos.", Toast.LENGTH_SHORT).show();
                    binding.likeButton.setLiked(false);
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                idsFavoritos.remove(produtoSelecionado.getId());
                Favorito.salvar(idsFavoritos);
            }
        });

        binding.btnAddCarrinho.setOnClickListener(v -> showDialogCarrinho());
    }

    private void addCarrinho() {
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setIdProduto(produtoSelecionado.getId());
        itemPedido.setQuantidade(1);
        itemPedido.setValor(produtoSelecionado.getValorAtual());

        itemPedidoDAO.salvar(itemPedido);

        itemDAO.salvar(produtoSelecionado);
    }

    private void configRvProdutos() {
        binding.rvProdutos.setLayoutManager(new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false));
        binding.rvProdutos.setHasFixedSize(true);
        lojaProdutoAdapter = new LojaProdutoAdapter(R.layout.item_produto_similar_adapter, produtoList, this, true, idsFavoritos, this, this);
        binding.rvProdutos.setAdapter(lojaProdutoAdapter);
    }

    private void recuperaProdutos() {
        DatabaseReference produtoRef = FirebaseHelper.getDatabaseReference()
                .child("produtos");
        produtoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                produtoList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Produto produto = ds.getValue(Produto.class);

                    for (String categoria : produtoSelecionado.getIdsCategorias()) {
                        if (produto.getIdsCategorias().contains(categoria)) {
                            if (!produtoList.contains(produto) && !produto.getId().equals(produtoSelecionado.getId())) {
                                produtoList.add(produto);
                            }
                        }
                    }
                }

                Collections.reverse(produtoList);
                lojaProdutoAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void recuperaFavoritos() {
        if (FirebaseHelper.getAutenticado()) {
            DatabaseReference favoritoRef = FirebaseHelper.getDatabaseReference()
                    .child("favoritos")
                    .child(FirebaseHelper.getIdFirebase());
            favoritoRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    idsFavoritos.clear();

                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String idFavorito = ds.getValue(String.class);
                        idsFavoritos.add(idFavorito);
                    }

                    binding.likeButton.setLiked(idsFavoritos.contains(produtoSelecionado.getId()));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void getExtra() {
        produtoSelecionado = (Produto) getIntent().getSerializableExtra("produtoSelecionado");

        configDados();

        recuperaProdutos();
    }

    private void configDados() {
        binding.imageSlider.setSliderAdapter(new SliderAdapter(produtoSelecionado.getUrlsImagens()));
        binding.imageSlider.startAutoCycle();
        binding.imageSlider.setScrollTimeInSec(4);
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);

        binding.textProduto.setText(produtoSelecionado.getTitulo());
        binding.textDescricao.setText(produtoSelecionado.getDescricao());
        binding.textValor.setText(getString(R.string.valor, GetMask.getValor(produtoSelecionado.getValorAtual())));
    }

    private void showDialogCarrinho() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);

        DialogAddItemCarrinhoBinding dialogBinding = DialogAddItemCarrinhoBinding
                .inflate(LayoutInflater.from(this));

        dialogBinding.btnFechar.setOnClickListener(v -> {
            addCarrinho();
            dialog.dismiss();
        });

        dialogBinding.btnIrCarrinho.setOnClickListener(v -> {
            addCarrinho();
            Intent intent = new Intent(this, MainActivityUsuario.class);
            intent.putExtra("id", 2);
            startActivity(intent);
            finish();
        });

        builder.setView(dialogBinding.getRoot());

        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(Produto produto) {
        Intent intent = new Intent(this, DetalhesProdutoActivity.class);
        intent.putExtra("produtoSelecionado", produto);
        startActivity(intent);
    }

    @Override
    public void onClickFavorito(Produto produto) {
        if (!idsFavoritos.contains(produto.getId())) {
            idsFavoritos.add(produto.getId());
        } else {
            idsFavoritos.remove(produto.getId());
        }
        Favorito.salvar(idsFavoritos);
    }
}