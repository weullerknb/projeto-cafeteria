package com.example.projetocafeteria.fragment.loja;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projetocafeteria.R;
import com.example.projetocafeteria.databinding.FragmentLojaCategoriaBinding;
import com.example.projetocafeteria.databinding.DialogFormCategoriaBinding;

public class LojaCategoriaFragment extends Fragment {

    private FragmentLojaCategoriaBinding binding;
    private AlertDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLojaCategoriaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configClicks();
    }

    private void configClicks() {
        binding.btnAddCategoria.setOnClickListener(v -> showDialog());
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                getContext(), R.style.CustomAlertDialog);

        DialogFormCategoriaBinding categoriaBinding = DialogFormCategoriaBinding
                .inflate(LayoutInflater.from(getContext()));

        categoriaBinding.btnFechar.setOnClickListener(v -> {
            dialog.dismiss();
        });

        categoriaBinding.btnSalvar.setOnClickListener(v -> {
            dialog.dismiss();
        });

        builder.setView(categoriaBinding.getRoot());

        dialog = builder.create();
        dialog.show();
    }
}