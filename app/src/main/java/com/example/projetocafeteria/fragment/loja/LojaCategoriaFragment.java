package com.example.projetocafeteria.fragment.loja;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import android.Manifest;

import com.example.projetocafeteria.R;
import com.example.projetocafeteria.databinding.FragmentLojaCategoriaBinding;
import com.example.projetocafeteria.databinding.DialogFormCategoriaBinding;
import com.example.projetocafeteria.helper.FirebaseHelper;
import com.example.projetocafeteria.model.Categoria;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.List;

public class LojaCategoriaFragment extends Fragment {

    private DialogFormCategoriaBinding categoriaBinding;
    private FragmentLojaCategoriaBinding binding;
    private AlertDialog dialog;
    private String caminhoImagem = null;

    private Categoria categoria;

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

        categoriaBinding = DialogFormCategoriaBinding
                .inflate(LayoutInflater.from(getContext()));

        categoriaBinding.btnFechar.setOnClickListener(v -> {
            dialog.dismiss();
        });

        categoriaBinding.btnSalvar.setOnClickListener(v -> {

            String nomeCategoria = categoriaBinding.edtCategoria.getText().toString().trim();

            if (!nomeCategoria.isEmpty()) {
                if (caminhoImagem != null) {

                    ocultaTeclado();

                    categoriaBinding.progressBar.setVisibility(View.VISIBLE);

                    if (categoria == null) categoria = new Categoria();
                    categoria.setNome(nomeCategoria);
                    categoria.setTodos(categoriaBinding.cbTodos.isChecked());

                    salvarImagemFirebase();

                } else {
                    ocultaTeclado();
                    Toast.makeText(getContext(), "Escolha uma imagem para a categoria.", Toast.LENGTH_SHORT).show();
                }
            } else {
                categoriaBinding.edtCategoria.setError("Informação obrigatória.");
            }
        });

        categoriaBinding.imagemCategoria.setOnClickListener(v -> verificaPermissaoGaleria());

        builder.setView(categoriaBinding.getRoot());

        dialog = builder.create();
        dialog.show();
    }

    private void salvarImagemFirebase() {
        StorageReference storageReference = FirebaseHelper.getStorageReference()
                .child("imagens")
                .child("categorias")
                .child(categoria.getId() + ".jped");

        UploadTask uploadTask = storageReference.putFile(Uri.parse(caminhoImagem));
        uploadTask.addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnCompleteListener(task -> {

            String urlImagem = task.getResult().toString();

            categoria.setUrlImagem(urlImagem);
            categoria.salvar();

            categoria = null;
            dialog.dismiss();

        })).addOnFailureListener(e -> {
            dialog.dismiss();
            Toast.makeText(getContext(), "Erro ao fazer upload da imagem.", Toast.LENGTH_SHORT).show();
        });
    }

    private void verificaPermissaoGaleria() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                abrirGaleria();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getContext(), "Permissão negada.", Toast.LENGTH_SHORT).show();
            }

        };

        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedTitle("Permissões")
                .setDeniedMessage("Se você não aceitar a permissão não poderá acessar a Galeria do dispositivo, deseja ativar a permissão ?")
                .setDeniedCloseButtonText("Não")
                .setGotoSettingButtonText("Sim")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
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

                    try {
                        Bitmap bitmap;
                        if (Build.VERSION.SDK_INT < 28) {
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imagemSelecionada);
                        } else {
                            ImageDecoder.Source source = ImageDecoder.createSource(getActivity().getContentResolver(), imagemSelecionada);
                            bitmap = ImageDecoder.decodeBitmap(source);
                        }
                        categoriaBinding.imagemCategoria.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    private void ocultaTeclado() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(categoriaBinding.edtCategoria.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}