package com.example.aluno.trabalhoandroid;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aluno.trabalhoandroid.model.Consumo;
import com.example.aluno.trabalhoandroid.model.ConsumoDAO;
import com.example.aluno.trabalhoandroid.model.Produto;
import com.example.aluno.trabalhoandroid.model.ProdutoDAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class TelaConsumo extends AppCompatActivity {

    private EditText edtQuantidade;
    private EditText edtCodigo;
    private Spinner spinner;

    private ArrayList<String> listaLstConsumo;
    private ArrayList<Integer> listaLstCodigo;
    private ArrayList<String> listaLstAlimento;
    private ArrayList<String> listaLstAlimentoSpinner;
    private ArrayList<Integer> listaLstCodigoProduto;

    private ArrayAdapter<String> adapter;
    private ListView lstConsumo;

    int codigoProduto = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_consumo);

        edtQuantidade = (EditText) findViewById(R.id.edtQuantidade);
        edtCodigo = (EditText) findViewById(R.id.edtCodigo);
        lstConsumo = (ListView) findViewById(R.id.lstConsumo);
        spinner = (Spinner) findViewById(R.id.spinner);

        listaLstAlimento = new ArrayList<>();
        listaLstAlimentoSpinner = new ArrayList<>();
        listaLstConsumo = new ArrayList<>();
        listaLstCodigoProduto = new ArrayList<>();
        listaLstCodigo = new ArrayList<>();

        Produto objP;
        ProdutoDAO objPDAO;
        Cursor tabelaProduto;
        String stringProduto = "";

        // mostrar Produtos no spinner
        try {
            objPDAO = new ProdutoDAO();

            if (listaLstAlimentoSpinner != null) {
                listaLstAlimentoSpinner.clear();
                listaLstCodigoProduto.clear();
            } else {
                listaLstAlimentoSpinner = new ArrayList<>();
                listaLstCodigoProduto = new ArrayList<>();
            }

            tabelaProduto = objPDAO.listar(getBaseContext());

            if (tabelaProduto != null) {
                while (tabelaProduto.moveToNext()) {
                    stringProduto = tabelaProduto.getString(1);
                    listaLstAlimentoSpinner.add(stringProduto);
                    listaLstCodigoProduto.add(tabelaProduto.getInt(0));
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaLstAlimentoSpinner);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            // pegar codigo do Produto selecionado
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    int pos = i;
                    Produto objP;
                    ProdutoDAO objPDAO;

                    try {
                        objPDAO = new ProdutoDAO();

                        if ((pos >= 0) && (pos < listaLstCodigoProduto.size())) {
                            objP = objPDAO.preencher(getBaseContext(), listaLstCodigoProduto.get(pos));
                            codigoProduto = objP.getCodigo();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getBaseContext(), "Erro: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            //preencher ao clicar na lista
            lstConsumo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int pos = i;
                ConsumoDAO objDAO;
                Consumo obj;

                try {
                    objDAO = new ConsumoDAO();

                    if ((pos >= 0) && (pos < listaLstCodigo.size())) {
                        obj = objDAO.preencher(getBaseContext(), listaLstCodigo.get(pos));
                        edtCodigo.setText(String.valueOf(obj.getCodigo()));
                        edtQuantidade.setText(String.valueOf(obj.getQtde()));
                        // mudar no spinner
                    }
                } catch (Exception ex) {
                    Toast.makeText(getBaseContext(), "Erro:  " + ex.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        } catch (Exception ex) {
            Toast.makeText(getBaseContext(), "Erro:  " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }


    public void gravar (View v)
    {
        Consumo obj;
        ConsumoDAO objDAO;
        Calendar hoje;

        try {
            obj = new Consumo();
            objDAO = new ConsumoDAO();

            hoje = new GregorianCalendar();
            hoje.setTime(new Date(System.currentTimeMillis()));

            obj.setQtde(edtQuantidade.getText().toString());
            obj.setCodproduto(codigoProduto);
            obj.setAno(hoje.get(Calendar.YEAR));
            obj.setMes(hoje.get(Calendar.MONTH) + 1);
            obj.setDia(hoje.get(Calendar.DAY_OF_MONTH));
            obj.setHora(hoje.get(Calendar.HOUR_OF_DAY));
            obj.setMinuto(hoje.get(Calendar.MINUTE));

            objDAO.gravar(getBaseContext(), obj);

            edtQuantidade.setText("");
            edtCodigo.setText("");
            edtQuantidade.requestFocus();
        } catch (Exception ex) {
            Toast.makeText(getBaseContext(), "Erro:  " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void remover (View v)
    {
        Consumo obj;
        ConsumoDAO objDAO;

        try {
            obj = new Consumo();
            objDAO = new ConsumoDAO();
            obj.setCodigo(edtCodigo.getText().toString());
            objDAO.remover(getBaseContext(), obj);

            edtQuantidade.setText("");
            edtCodigo.setText("");
            edtQuantidade.requestFocus();
        } catch (Exception ex) {
            Toast.makeText(getBaseContext(), "Erro:  " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void alterar (View v)
    {
        Consumo obj;
        ConsumoDAO objDAO;

        try {
            obj = new Consumo();
            objDAO = new ConsumoDAO();

            obj.setCodigo(edtCodigo.getText().toString());
            obj.setQtde(edtQuantidade.getText().toString());
            obj.setCodproduto(codigoProduto);

            objDAO.alterar(getBaseContext(), obj);
            edtQuantidade.setText("");
            edtCodigo.setText("");
            edtQuantidade.requestFocus();
        } catch (Exception ex) {
            Toast.makeText(getBaseContext(), "Erro:  " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void listar (View v)
    {
        int i;
        String stringConsumo = "";
        ConsumoDAO objDAO;
        Cursor tabela;

        try {
            objDAO = new ConsumoDAO();

            if (listaLstAlimento != null) {
                listaLstAlimento.clear();
                listaLstCodigo.clear();

            } else {
                listaLstAlimento = new ArrayList<>();
                listaLstCodigo = new ArrayList<>();
            }

            tabela = objDAO.listar(getBaseContext());

            if (tabela != null) {
                while (tabela.moveToNext()) {
                    stringConsumo = tabela.getString(2) + "  " + tabela.getInt(3) + "/" + tabela.getInt(4) + "/" + tabela.getInt(5) + "  " +
                            tabela.getInt(6) + ":" + tabela.getInt(7) + "  " + tabela.getInt(8) + "g";
                    listaLstAlimento.add(stringConsumo);
                    listaLstCodigo.add(tabela.getInt(0));
                }
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaLstAlimento);
                lstConsumo.setAdapter(adapter);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(getBaseContext(), "Erro:  " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}
