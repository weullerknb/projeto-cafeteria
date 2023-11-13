package com.example.projetocafeteria.activity.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.projetocafeteria.databinding.ActivityUsuarioPerfilBinding;
import com.example.projetocafeteria.helper.FirebaseHelper;
import com.example.projetocafeteria.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class UsuarioPerfilActivity extends AppCompatActivity {

    private ActivityUsuarioPerfilBinding binding;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsuarioPerfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iniciaComponentes();

        configClicks();

        recuperaUsuario();
    }

    private void configDados() {
        binding.edtNome.setText(usuario.getNome());
        binding.edtTelefone.setText(usuario.getTelefone());
        binding.edtDataNascimento.setText(usuario.getDataNascimento());
        binding.edtEmail.setText(usuario.getEmail());

        binding.progressBar.setVisibility(View.GONE);
    }

    private void recuperaUsuario() {
        DatabaseReference usuarioRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(FirebaseHelper.getIdFirebase());
        usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario = snapshot.getValue(Usuario.class);

                configDados();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void configClicks() {
        binding.include.include.ibVoltar.setOnClickListener(v -> finish());
    }

    private void iniciaComponentes() {
        binding.include.textTitulo.setText("Meus dados");
    }
}