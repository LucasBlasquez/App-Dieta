package com.example.aluno.trabalhoandroid;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aluno.trabalhoandroid.model.Consumo;
import com.example.aluno.trabalhoandroid.model.ConsumoDAO;
import com.example.aluno.trabalhoandroid.model.Produto;
import com.example.aluno.trabalhoandroid.model.ProdutoDAO;

import java.util.ArrayList;

public class Relatorio extends AppCompatActivity {

    private EditText edtMes;
    private EditText edtAno;
    private ListView lstResultadoBusca;
    private ListView lstProdutosDia;

    private ArrayList<String> listaLstBusca;
    private ArrayList<Integer> listaLstDia;
    private ArrayList<Integer> listaLstMes;
    private ArrayList<Integer> listaLstAno;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);

        edtMes=(EditText) findViewById(R.id.edtMes);
        edtAno=(EditText) findViewById(R.id.edtAno);
        lstResultadoBusca=(ListView) findViewById(R.id.lstResultadoBusca);
        lstProdutosDia=(ListView) findViewById(R.id.lstProdutosDia);

        lstResultadoBusca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                double total = 0.0;
                int pos = i;
                int dia, mes, ano;
                ConsumoDAO objDAO;
                Cursor tabela;
                String s="";

                try {
                    objDAO = new ConsumoDAO();
                    dia = listaLstDia.get(pos);
                    mes = listaLstMes.get(pos);
                    ano = listaLstAno.get(pos);

                    Intent intentEnviadora = new Intent(getApplicationContext(), RelatorioTela2.class);
                    Bundle parametros = new Bundle();

                    parametros.putInt("chave_dia", dia);
                    parametros.putInt("chave_mes", mes);
                    parametros.putInt("chave_ano", ano);

                    intentEnviadora.putExtras(parametros);
                    startActivity(intentEnviadora);



                } catch (Exception ex) {
                    Toast.makeText(getBaseContext(), "Erro:  " + ex.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void buscar(View v)
    {
        ConsumoDAO objDAO;
        Cursor tabela;
        int mes, ano;
        String s="";

        try{
            objDAO = new ConsumoDAO();
            mes = Integer.parseInt(edtMes.getText().toString());
            ano = Integer.parseInt(edtAno.getText().toString());

            if(listaLstBusca!=null) {
                listaLstBusca.clear();
                listaLstDia.clear();
                listaLstMes.clear();
                listaLstAno.clear();

            }
            else {
                listaLstBusca = new ArrayList<>();
                listaLstDia = new ArrayList<>();
                listaLstMes = new ArrayList<>();
                listaLstAno = new ArrayList<>();
            }

            tabela = objDAO.listarMesAno(getBaseContext(), mes, ano);

            if(tabela != null) {
                while(tabela.moveToNext()){
                    s = tabela.getInt(0) + "/" + tabela.getInt(1) + "/" + tabela.getInt(2)
                            + "  " + tabela.getDouble(3) + " cal";
                    listaLstBusca.add(s);
                    listaLstDia.add(tabela.getInt(0));
                    listaLstMes.add(tabela.getInt(1));
                    listaLstAno.add(tabela.getInt(2));
                }
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaLstBusca);
                lstResultadoBusca.setAdapter(adapter);
            }

        }
        catch (Exception ex) {
            Toast.makeText(getBaseContext(), "Erro:  " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}
