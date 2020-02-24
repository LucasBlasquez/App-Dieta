package com.example.aluno.trabalhoandroid;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.aluno.trabalhoandroid.model.ConsumoDAO;

import java.util.ArrayList;

public class RelatorioTela2 extends AppCompatActivity {

    private TextView txtTotal;
    private ListView lstProdutosDia;
    private ArrayList<String> listaLstTotalDia;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_tela2);

        txtTotal=(TextView) findViewById(R.id.txtTotal);
        lstProdutosDia=(ListView) findViewById(R.id.lstProdutosDia);

        Intent intentRecebedora = getIntent();
        Bundle parametros = intentRecebedora.getExtras();
        int dia=0;
        int mes=0;
        int ano=0;

        if(parametros!=null)
        {
            dia = parametros.getInt("chave_dia");
            mes = parametros.getInt("chave_mes");
            ano = parametros.getInt("chave_ano");
        }

        double total = 0.0;
        ConsumoDAO objDAO;
        Cursor tabela;
        String s="";

        try {
            objDAO = new ConsumoDAO();
            tabela = objDAO.listarDiaMesAno(getBaseContext(), dia,mes,ano);

            if(listaLstTotalDia!=null) {
                listaLstTotalDia.clear();
            }
            else {
                listaLstTotalDia = new ArrayList<>();
            }

            while(tabela.moveToNext()){
                s = tabela.getString(5) + "  " + tabela.getInt(6) + "g";
                listaLstTotalDia.add(s);
                total = total + tabela.getDouble(6);
            }
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaLstTotalDia);
            lstProdutosDia.setAdapter(adapter);
            txtTotal.setText(String.valueOf(total) + "g");

        } catch (Exception ex) {
            Toast.makeText(getBaseContext(), "Erro:  " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}
