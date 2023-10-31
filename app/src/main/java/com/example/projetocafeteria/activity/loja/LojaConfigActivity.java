package com.example.projetocafeteria.activity.loja;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetocafeteria.R;
import com.example.projetocafeteria.databinding.ActivityLojaConfigBinding;
import com.example.projetocafeteria.helper.FirebaseHelper;
import com.example.projetocafeteria.model.ImagemUpload;
import com.example.projetocafeteria.model.Loja;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;
import java.util.Locale;

public class LojaConfigActivity extends AppCompatActivity {

    private ActivityLojaConfigBinding binding;

    private String caminhoImagem = null;

    private Loja loja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLojaConfigBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recuperaLoja();

        iniciaComponentes();

        configClicks();
    }

    private void configClicks() {
        binding.imgLogo.setOnClickListener(v -> {
            verificaPermissaoGaleria();
        });
        binding.btnSalvar.setOnClickListener(v -> {
            if (loja != null) {
                validaDados();
            } else {
                Toast.makeText(this, "Ainda estamos recuperando as informações da loja, aguarde...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void recuperaLoja() {
        DatabaseReference lojaRef = FirebaseHelper.getDatabaseReference()
                .child("loja");
        lojaRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loja = snapshot.getValue(Loja.class);

                configDados();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void configDados() {
        if (loja.getUrlLogo() != null) {
            Picasso.get().load(loja.getUrlLogo()).into(binding.imgLogo);
        }

        if (loja.getNome() != null) {
            binding.edtLoja.setText(loja.getNome());
        }

        if (loja.getCnpj() != null) {
            binding.edtCNPJ.setText(loja.getCnpj());
        }

        if (loja.getPedidoMinimo() != 0) {
            binding.edtPedidoMinimo.setText(String.valueOf(loja.getPedidoMinimo() * 10));
        }

        if (loja.getFreteGratis() != 0) {
            binding.edtFrete.setText(String.valueOf(loja.getFreteGratis() * 10));
        }
    }

    private void validaDados() {
        String nomeLoja = binding.edtLoja.getText().toString().trim();
        String cnpj = binding.edtCNPJ.getMasked();
        double pedidoMinimo = (double) binding.edtPedidoMinimo.getRawValue() / 100;
        double freteGratis = (double) binding.edtFrete.getRawValue() / 100;

        if (caminhoImagem != null) {
            if (!nomeLoja.isEmpty()) {
                if (!cnpj.isEmpty()) {
                    if (cnpj.length() == 18) {

                        loja.setNome(nomeLoja);
                        loja.setCnpj(cnpj);
                        loja.setPedidoMinimo(pedidoMinimo);
                        loja.setFreteGratis(freteGratis);

                        salvarImagemFirebase();

                    } else {
                        binding.edtCNPJ.setError("CNPJ inválido.");
                        binding.edtCNPJ.requestFocus();
                    }
                } else {
                    binding.edtCNPJ.setError("Informe o CNPJ da loja.");
                    binding.edtCNPJ.requestFocus();
                }
            } else {
                binding.edtLoja.setError("Informe um nome para sua loja.");
                binding.edtLoja.requestFocus();
            }
        } else {
            ocultaTeclado();
            Toast.makeText(this, "Escolha uma logo para sua loja", Toast.LENGTH_SHORT).show();
        }
    }

    private void salvarImagemFirebase() {

        StorageReference storageReference = FirebaseHelper.getStorageReference()
                .child("imagens")
                .child("loja")
                .child(loja.getId() + ".jpeg");

        UploadTask uploadTask = storageReference.putFile(Uri.parse(caminhoImagem));
        uploadTask.addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnCompleteListener(task -> {

            loja.setUrlLogo(task.getResult().toString());
            loja.salvar();

        })).addOnFailureListener(e -> Toast.makeText(
                this, "Ocorreu um erro com o upload, tente novamente.",
                Toast.LENGTH_SHORT).show());
    }

    private void verificaPermissaoGaleria() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                abrirGaleria();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getBaseContext(), "Permissão Negada.", Toast.LENGTH_SHORT).show();
            }
        };

        showDialogPermissao(
                permissionlistener,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                "Se você não aceitar a permissão não poderá acessar a Galeria do dispositivo, deseja ativar a permissão agora ?"
        );
    }

    private void showDialogPermissao(PermissionListener permissionListener, String[] permissoes, String msg) {
        TedPermission.create()
                .setPermissionListener(permissionListener)
                .setDeniedTitle("Permissão negada")
                .setDeniedMessage(msg)
                .setDeniedCloseButtonText("Não")
                .setGotoSettingButtonText("Sim")
                .setPermissions(permissoes)
                .check();
        // Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Uri imagemSelecionada = result.getData().getData();
                    caminhoImagem = imagemSelecionada.toString();
                    binding.imgLogo.setImageBitmap(getBitmap(imagemSelecionada));
                }
            }
    );

    private Bitmap getBitmap(Uri caminhoUri) {
        Bitmap bitmap = null;

        try {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), caminhoUri);
            } else {
                ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), caminhoUri);
                bitmap = ImageDecoder.decodeBitmap(source);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void iniciaComponentes() {
        binding.edtPedidoMinimo.setLocale(new Locale("PT", "br"));
        binding.edtFrete.setLocale(new Locale("PT", "br"));
    }

    private void ocultaTeclado() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(binding.edtPedidoMinimo.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}