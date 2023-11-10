package com.example.projetocafeteria.fragment.usuario;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projetocafeteria.R;
import com.example.projetocafeteria.activity.usuario.MainActivityUsuario;
import com.example.projetocafeteria.activity.usuario.UsuarioEnderecoActivity;
import com.example.projetocafeteria.autenticacao.CadastroActivity;
import com.example.projetocafeteria.autenticacao.LoginActivity;
import com.example.projetocafeteria.databinding.FragmentUsuarioPerfilBinding;
import com.example.projetocafeteria.helper.FirebaseHelper;

public class UsuarioPerfilFragment extends Fragment {

    private FragmentUsuarioPerfilBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUsuarioPerfilBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configClicks();
    }

    private void configClicks() {
        binding.btnEntrar.setOnClickListener(v -> startActivity(LoginActivity.class));
        binding.btnCadastrar.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), CadastroActivity.class));
        });
        binding.btnPerfil.setOnClickListener(v -> startActivity(LoginActivity.class));
        binding.btnEnderecos.setOnClickListener(v -> startActivity(UsuarioEnderecoActivity.class));
        binding.btnDeslogar.setOnClickListener(v -> {
            FirebaseHelper.getAuth().signOut();
            requireActivity().finish();
            startActivity(new Intent(requireContext(), MainActivityUsuario.class));
        });
    }

    private void startActivity(Class<?> clazz) {
        if (FirebaseHelper.getAutenticado()) {
            startActivity(new Intent(requireContext(), clazz));
        } else {
            startActivity(new Intent(requireContext(), LoginActivity.class));
        }
    }
}