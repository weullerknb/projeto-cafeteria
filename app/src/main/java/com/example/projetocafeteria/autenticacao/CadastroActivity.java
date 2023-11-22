package com.example.projetocafeteria.autenticacao;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.projetocafeteria.R;
import com.example.projetocafeteria.databinding.ActivityCadastroBinding;
import com.example.projetocafeteria.helper.FirebaseHelper;
import com.example.projetocafeteria.model.Loja;
import com.example.projetocafeteria.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private ActivityCadastroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configClicks();
    }

    public void validaDados(View view) {
        String nome = binding.edtNome.getText().toString().trim();
        String cpf = binding.edtCpf.getMasked();
        String telefone = binding.edtTelefone.getMasked();
        String dataNascimento = binding.edtDataNascimento.getMasked();
        String email = binding.edtEmail.getText().toString().trim();
        String senha = binding.edtSenha.getText().toString().trim();
        String confirmaSenha = binding.edtConfirmaSenha.getText().toString().trim();

        if (!nome.isEmpty()) {
            if (!cpf.isEmpty()) {
                if (cpf.length() == 12) {
                    if (!telefone.isEmpty()) {
                        if (telefone.length() == 15) {
                            if (!dataNascimento.isEmpty()) {
                                if (dataNascimento.length() == 10) {
                                    if (!email.isEmpty()) {
                                        if (!senha.isEmpty()) {
                                            if (!confirmaSenha.isEmpty()) {
                                                if (senha.equals(confirmaSenha)) {

                                                    binding.progressBar.setVisibility(View.VISIBLE);

                                                    Usuario usuario = new Usuario();
                                                    usuario.setNome(nome);
                                                    usuario.setCpf(cpf);
                                                    usuario.setTelefone(telefone);
                                                    usuario.setDataNascimento(dataNascimento);
                                                    usuario.setEmail(email);
                                                    usuario.setSenha(senha);

                                                    criarConta(usuario);

                                                } else {
                                                    binding.edtConfirmaSenha.requestFocus();
                                                    binding.edtConfirmaSenha.setError("Senhas são diferentes.");
                                                }
                                            } else {
                                                binding.edtConfirmaSenha.requestFocus();
                                                binding.edtConfirmaSenha.setError("Confirme sua senha.");
                                            }
                                        } else {
                                            binding.edtSenha.requestFocus();
                                            binding.edtSenha.setError("Informe uma senha.");
                                        }
                                    } else {
                                        binding.edtEmail.requestFocus();
                                        binding.edtEmail.setError("Informe seu email.");
                                    }
                                } else {
                                    binding.edtDataNascimento.requestFocus();
                                    binding.edtDataNascimento.setError("Data de nascimneto inválida.");
                                }
                            } else {
                                binding.edtDataNascimento.requestFocus();
                                binding.edtDataNascimento.setError("Informe sua data de nascimento.");
                            }
                        } else {
                            binding.edtTelefone.requestFocus();
                            binding.edtTelefone.setError("Formato do telefone inválido.");
                        }
                    } else {
                        binding.edtTelefone.requestFocus();
                        binding.edtTelefone.setError("Informe seu telefone.");
                    }
                } else {
                    binding.edtCpf.requestFocus();
                    binding.edtCpf.setError("Formato de CPF inválido.");
                }
            } else {
                binding.edtCpf.requestFocus();
                binding.edtCpf.setError("Informe seu CPF.");
            }
        } else {
            binding.edtNome.requestFocus();
            binding.edtNome.setError("Informe seu nome.");
        }
    }

    private void criarConta(Usuario usuario) {
        FirebaseHelper.getAuth().createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String id = task.getResult().getUser().getUid();

                usuario.setId(id);
                usuario.salvar();

                Intent intent = new Intent();
                intent.putExtra("email", usuario.getEmail());
                setResult(RESULT_OK, intent);
                finish();

            } else {
                Toast.makeText(this, FirebaseHelper.validaErros(
                        task.getException().getMessage()), Toast.LENGTH_SHORT).show();
            }
            binding.progressBar.setVisibility(View.GONE);
        });
    }

    private void configClicks() {
        binding.include.ibVoltar.setOnClickListener(view -> finish());

        binding.btnLogin.setOnClickListener(view -> finish());
    }

}