package com.example.projetocafeteria.fragment.loja;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projetocafeteria.R;
import com.example.projetocafeteria.activity.loja.LojaFormProdutoActivity;
import com.example.projetocafeteria.adapter.LojaProdutoAdapter;
import com.example.projetocafeteria.databinding.FragmentLojaProdutoBinding;
import com.example.projetocafeteria.helper.FirebaseHelper;
import com.example.projetocafeteria.model.Produto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LojaProdutoFragment extends Fragment implements LojaProdutoAdapter.OnClickLister {

    private List<Produto> produtoList = new ArrayList<>();

    private LojaProdutoAdapter lojaProdutoAdapter;

    private FragmentLojaProdutoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLojaProdutoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configClicks();

        configRv();
    }

    @Override
    public void onStart() {
        super.onStart();

        recuperaProdutos();
    }

    private void configClicks() {
        binding.toolbar.btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), LojaFormProdutoActivity.class));
        });
    }

    private void configRv() {
        binding.rvProdutos.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.rvProdutos.setHasFixedSize(true);
        lojaProdutoAdapter = new LojaProdutoAdapter(produtoList, requireContext(), this);
        binding.rvProdutos.setAdapter(lojaProdutoAdapter);
    }

    private void recuperaProdutos() {
        DatabaseReference produtoRef = FirebaseHelper.getDatabaseReference()
                .child("produtos");
        produtoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    produtoList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Produto produto = ds.getValue(Produto.class);
                        produtoList.add(produto);
                    }
                    binding.textInfo.setText("");
                } else {
                    binding.textInfo.setText("Nenhum produto cadastrado.");
                }

                binding.progressBar.setVisibility(View.GONE);
                Collections.reverse(produtoList);
                lojaProdutoAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(Produto produto) {
        Toast.makeText(requireContext(), produto.getTitulo(), Toast.LENGTH_SHORT).show();
    }
}