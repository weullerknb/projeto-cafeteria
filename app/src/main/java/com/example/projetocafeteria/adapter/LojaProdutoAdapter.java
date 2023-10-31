package com.example.projetocafeteria.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetocafeteria.R;
import com.example.projetocafeteria.helper.FirebaseHelper;
import com.example.projetocafeteria.model.Produto;
import com.example.projetocafeteria.util.GetMask;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LojaProdutoAdapter extends RecyclerView.Adapter<LojaProdutoAdapter.MyViewHolder> {

    private List<Produto> produtoList;
    private Context context;
    private boolean favorito;
    private List<String> idsFavoritos;
    private OnClickLister onClickLister;
    private OnClickFavorito onClickFavorito;

    public LojaProdutoAdapter(List<Produto> produtoList, Context context, boolean favorito, List<String> idsFavoritos, OnClickLister onClickLister, OnClickFavorito onClickFavorito) {
        this.produtoList = produtoList;
        this.context = context;
        this.favorito = favorito;
        this.idsFavoritos = idsFavoritos;
        this.onClickLister = onClickLister;
        this.onClickFavorito = onClickFavorito;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Produto produto = produtoList.get(position);

        holder.txtNomeProduto.setText(produto.getTitulo());

        if (favorito) {
            if (idsFavoritos.contains(produto.getId())) {
                holder.likeButton.setLiked(true);
            }
        }

        if (produto.getValorAntigo() > 0) {

            double resto = produto.getValorAntigo() - produto.getValorAtual();
            int porcentagem = (int) (resto / produto.getValorAntigo() * 100);

            if (porcentagem >= 10) {
                holder.txtDescontoProduto.setText(context.getString(R.string.valor_off, porcentagem, "%"));
            } else { // Tirar o numero zero 0 da frente. Ex: 09 para 9
                String porcent = String.valueOf(porcentagem).replace("0", "");
                holder.txtDescontoProduto.setText(context.getString(R.string.valor_off, Integer.parseInt(porcent), "%"));
            }

        } else {
            holder.txtDescontoProduto.setVisibility(View.GONE);
        }

        holder.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if (FirebaseHelper.getAutenticado()) {
                    onClickFavorito.onClickFavorito(produto.getId());
                } else {
                    Toast.makeText(context, "Entre na sua conta para adicionar aos favoritos.", Toast.LENGTH_SHORT).show();
                    holder.likeButton.setLiked(false);
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                onClickFavorito.onClickFavorito(produto.getId());
            }
        });

        for (int i = 0; i < produto.getUrlsImagens().size(); i++) {
            if (produto.getUrlsImagens().get(i).getIndex() == 0) {
                Picasso.get().load(produto.getUrlsImagens().get(i).getCaminhoImagem())
                        .into(holder.imagemProduto);
            }
        }
        
        holder.txtValorProduto.setText(context.getString(R.string.valor, GetMask.getValor(produto.getValorAtual())));
        holder.itemView.setOnClickListener(v -> onClickLister.onClick(produto));
    }

    @Override
    public int getItemCount() {
        return produtoList.size();
    }

    public interface OnClickLister {
        void onClick(Produto produto);
    }

    public interface OnClickFavorito {
        void onClickFavorito(String idProduto);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imagemProduto;
        TextView txtNomeProduto, txtValorProduto, txtDescontoProduto;
        LikeButton likeButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imagemProduto = itemView.findViewById(R.id.imagemProduto);
            txtNomeProduto = itemView.findViewById(R.id.txtNomeProduto);
            txtValorProduto = itemView.findViewById(R.id.txtValorProduto);
            txtDescontoProduto = itemView.findViewById(R.id.txtDescontoProduto);
            likeButton = itemView.findViewById(R.id.likeButton);
        }
    }
}
