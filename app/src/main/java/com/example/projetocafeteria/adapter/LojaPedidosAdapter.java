package com.example.projetocafeteria.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetocafeteria.R;
import com.example.projetocafeteria.helper.FirebaseHelper;
import com.example.projetocafeteria.model.Pedido;
import com.example.projetocafeteria.model.StatusPedido;
import com.example.projetocafeteria.model.Usuario;
import com.example.projetocafeteria.util.GetMask;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class LojaPedidosAdapter extends RecyclerView.Adapter<LojaPedidosAdapter.MyViewHolder> {

    private final List<Pedido> pedidoList;
    private final Context context;
    private final OnClickListener clickListener;

    public LojaPedidosAdapter(List<Pedido> pedidoList, Context context, OnClickListener clickListener) {
        this.pedidoList = pedidoList;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loja_pedido_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pedido pedido = pedidoList.get(position);

        recuperaCliente(pedido, holder);

        holder.textIdPedido.setText(pedido.getId());

        double valorExtra;
        double totalPedido = pedido.getTotal();
        if (pedido.getAcrescimo() > 0) {
            valorExtra = (double) pedido.getAcrescimo() / 100;
            totalPedido += (totalPedido * valorExtra);
        } else {
            valorExtra = (double) pedido.getDesconto() / 100;
            totalPedido -= (totalPedido * valorExtra);
        }

        holder.textTotalPedido.setText(context.getString(R.string.valor, GetMask.getValor(totalPedido)));
        holder.textDataPedido.setText(GetMask.getDate(pedido.getDataPedido(), 0));

        holder.btnDetalhesPedido.setOnClickListener(v -> clickListener.onClick(pedido, "detalhes"));
        holder.btnStatusPedido.setOnClickListener(v -> clickListener.onClick(pedido, "status"));

        switch (pedido.getStatusPedido()) {
            case PENDENTE:
                holder.textStatusPedido.setTextColor(Color.parseColor("#FF8C00"));
                break;
            case APROVADO:
            case ENVIADO:
            case FINALIZADO:
                holder.textStatusPedido.setTextColor(Color.parseColor("#34A853"));
                break;
            default:
                holder.textStatusPedido.setTextColor(Color.parseColor("#ff2400"));
                break;
        }
        holder.textStatusPedido.setText(StatusPedido.getStatusPedido(pedido.getStatusPedido()));
    }

    private void recuperaCliente(Pedido pedido, MyViewHolder holder) {
        DatabaseReference usuarioRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(pedido.getIdCliente());
        usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Usuario usuario =snapshot.getValue(Usuario.class);
                    assert usuario != null;
                    holder.textNomeCliente.setText(usuario.getNome());
                } else {
                    holder.textNomeCliente.setText("Usuário não encontrado.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return pedidoList.size();
    }

    public interface OnClickListener {
        void onClick(Pedido pedido, String operacao);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textIdPedido, textStatusPedido, textNomeCliente, textTotalPedido, textDataPedido;
        Button btnDetalhesPedido, btnStatusPedido;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textIdPedido = itemView.findViewById(R.id.textIdPedido);
            textStatusPedido = itemView.findViewById(R.id.textStatusPedido);
            textNomeCliente = itemView.findViewById(R.id.textNomeCliente);
            textTotalPedido = itemView.findViewById(R.id.textTotalPedido);
            textDataPedido = itemView.findViewById(R.id.textDataPedido);
            btnDetalhesPedido = itemView.findViewById(R.id.btnDetalhesPedido);
            btnStatusPedido = itemView.findViewById(R.id.btnStatusPedido);
        }
    }
}
