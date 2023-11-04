package com.example.projetocafeteria.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.projetocafeteria.model.ImagemUpload;
import com.example.projetocafeteria.model.ItemPedido;
import com.example.projetocafeteria.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ItemPedidoDAO {

    private final SQLiteDatabase write;
    private final SQLiteDatabase read;

    public ItemPedidoDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        write = dbHelper.getWritableDatabase();
        read = dbHelper.getReadableDatabase();
    }

    public boolean salvar(ItemPedido itemPedido) {

        ContentValues values = new ContentValues();
        values.put("id_produto", itemPedido.getIdProduto());
        values.put("valor", itemPedido.getValor());
        values.put("quantidade", itemPedido.getQuantidade());

        try {
            write.insert(DbHelper.TABELA_ITEM_PEDIDO, null, values);
        } catch (Exception e) {
            Log.i("INFODB:", " Erro ao salvar o itemPedido." + e.getMessage());
            return false;
        }

        return true;
    }

    public Produto getProduto(int idProduto) {
        Produto produto = null;
        List<ImagemUpload> uploadList = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_ITEM + " WHERE id = " + idProduto + ";";
        Cursor cursor = read.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String id_firebase = cursor.getString(cursor.getColumnIndexOrThrow("id_firebase"));
            String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
            double valor = cursor.getDouble(cursor.getColumnIndexOrThrow("valor"));
            String url_imagem = cursor.getString(cursor.getColumnIndexOrThrow("url_imagem"));

            produto = new Produto();
            produto.setIdLocal(id);
            produto.setId(id_firebase);
            produto.setTitulo(nome);
            produto.setValorAtual(valor);

            uploadList.add(new ImagemUpload(0, url_imagem));

            produto.setUrlsImagens(uploadList);
        }

        cursor.close();
        return produto;
    }

    public List<ItemPedido> getList() {
        List<ItemPedido> itemPedidoList = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_ITEM_PEDIDO + ";";
        Cursor cursor = read.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String id_produto = cursor.getString(cursor.getColumnIndexOrThrow("id_produto"));
            double valor = cursor.getDouble(cursor.getColumnIndexOrThrow("valor"));
            int quantidade = cursor.getInt(cursor.getColumnIndexOrThrow("quantidade"));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setId(id);
            itemPedido.setIdProduto(id_produto);
            itemPedido.setValor(valor);
            itemPedido.setQuantidade(quantidade);

            itemPedidoList.add(itemPedido);
        }

        cursor.close();
        return itemPedidoList;
    }

}
