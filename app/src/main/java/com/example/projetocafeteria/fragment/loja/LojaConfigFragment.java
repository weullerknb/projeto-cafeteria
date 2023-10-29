package com.example.projetocafeteria.fragment.loja;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projetocafeteria.R;
import com.example.projetocafeteria.activity.loja.LojaConfigActivity;
import com.example.projetocafeteria.databinding.FragmentLojaConfigBinding;

public class LojaConfigFragment extends Fragment {

    private FragmentLojaConfigBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLojaConfigBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configClicks();
    }

    private void configClicks() {
        binding.btnConfigLoja.setOnClickListener(v -> {
            startActivity(LojaConfigActivity.class);
        });
    }

    private void startActivity(Class<?> clazz) {
        startActivity(new Intent(requireContext(), clazz));
    }
}