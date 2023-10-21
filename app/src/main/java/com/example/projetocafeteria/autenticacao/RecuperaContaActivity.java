package com.example.projetocafeteria.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.projetocafeteria.R;
import com.example.projetocafeteria.databinding.ActivityRecuperaContaBinding;
import com.example.projetocafeteria.helper.FirebaseHelper;

public class RecuperaContaActivity extends AppCompatActivity {

    private ActivityRecuperaContaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecuperaContaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configClicks();
    }

    public void validaDados(View view) {
        String email = binding.edtEmail.getText().toString().trim();

        if (!email.isEmpty()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Acabamos de enviar um link para o e-mail informado.", Toast.LENGTH_SHORT).show();
            binding.progressBar.setVisibility(View.GONE);
            //recuperarContar(email);
        } else {
            binding.edtEmail.requestFocus();
            binding.edtEmail.setError("Informe seu e-mail.");
        }
    }

    private void recuperarContar(String email) {
        FirebaseHelper.getAuth().sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Acabamos de enviar um link para o e-mail informado.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, FirebaseHelper.validaErros(
                                task.getException().getMessage()), Toast.LENGTH_SHORT).show();
                    }
                    binding.progressBar.setVisibility(View.GONE);
                });
    }

    private void configClicks() {
        binding.include.ibVoltar.setOnClickListener(view -> finish());
    }
}