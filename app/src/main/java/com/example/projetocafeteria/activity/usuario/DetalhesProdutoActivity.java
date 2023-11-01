package com.example.projetocafeteria.activity.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.projetocafeteria.R;
import com.example.projetocafeteria.adapter.SliderAdapter;
import com.example.projetocafeteria.databinding.ActivityDetalhesProdutoBinding;
import com.example.projetocafeteria.model.Produto;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;

public class DetalhesProdutoActivity extends AppCompatActivity {

    private ActivityDetalhesProdutoBinding binding;

    private Produto produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetalhesProdutoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getExtra();
    }

    private void getExtra() {
        produto = (Produto) getIntent().getSerializableExtra("produtoSelecionado");

        configDados();
    }

    private void configDados() {
        binding.imageSlider.setSliderAdapter(new SliderAdapter(produto.getUrlsImagens()));
        binding.imageSlider.startAutoCycle();
        binding.imageSlider.setScrollTimeInSec(4);
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
    }
}