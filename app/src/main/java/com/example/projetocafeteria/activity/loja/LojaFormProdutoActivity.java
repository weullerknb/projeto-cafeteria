package com.example.projetocafeteria.activity.loja;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.projetocafeteria.R;
import com.example.projetocafeteria.databinding.ActivityLojaFormProdutoBinding;
import com.example.projetocafeteria.databinding.BottomSheetFromProdutoBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class LojaFormProdutoActivity extends AppCompatActivity {

    private ActivityLojaFormProdutoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLojaFormProdutoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configClicks();
    }

    private void configClicks() {
        binding.imagemProduto0.setOnClickListener(v -> showBottomSheet());
        binding.imagemProduto1.setOnClickListener(v -> showBottomSheet());
        binding.imagemProduto2.setOnClickListener(v -> showBottomSheet());
    }

    private void showBottomSheet() {
        BottomSheetFromProdutoBinding sheetBinding =
                BottomSheetFromProdutoBinding.inflate(LayoutInflater.from(this));
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                this, R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(sheetBinding.getRoot());
        bottomSheetDialog.show();

        sheetBinding.btnCamera.setOnClickListener(v -> {
            Toast.makeText(this, "Câmera", Toast.LENGTH_SHORT).show();
            bottomSheetDialog.dismiss();
        });

        sheetBinding.btnGaleria.setOnClickListener(v -> {
            Toast.makeText(this, "Galeria", Toast.LENGTH_SHORT).show();
            bottomSheetDialog.dismiss();
        });

        sheetBinding.btnCancelar.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
        });
    }
}