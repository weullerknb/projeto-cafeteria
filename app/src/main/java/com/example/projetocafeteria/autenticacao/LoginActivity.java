package com.example.projetocafeteria.autenticacao;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.projetocafeteria.R;
import com.example.projetocafeteria.databinding.ActivityLoginBinding;
import com.example.projetocafeteria.helper.FirebaseHelper;
import com.example.projetocafeteria.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    String email = result.getData().getStringExtra("email");
                    binding.edtEmail.setText(email);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configClicks();
    }

    public void validaDados(View view) {
        String email = binding.edtEmail.getText().toString().trim();
        String senha = binding.edtSenha.getText().toString().trim();

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {

                binding.progressBar.setVisibility(View.VISIBLE);

                login(email, senha);

            } else {
                binding.edtSenha.requestFocus();
                binding.edtSenha.setError("Informe uma senha.");
            }
        } else {
            binding.edtEmail.requestFocus();
            binding.edtEmail.setError("Informe seu email.");
        }
    }

    private void login(String email, String senha) {
        FirebaseHelper.getAuth().signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        finish();
                    } else {
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, FirebaseHelper.validaErros(
                                task.getException().getMessage()), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void configClicks() {
        binding.include.ibVoltar.setOnClickListener(view -> finish());

        binding.btnRecuperaSenha.setOnClickListener(view ->
                startActivity(new Intent(this, RecuperaContaActivity.class)));

        binding.btnCadastro.setOnClickListener(view -> {
            Intent intent = new Intent(this, CadastroActivity.class);
            resultLauncher.launch(intent);
        });
    }
}