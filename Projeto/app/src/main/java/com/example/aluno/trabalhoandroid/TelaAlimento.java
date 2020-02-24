package com.example.aluno.trabalhoandroid;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aluno.trabalhoandroid.model.Produto;
import com.example.aluno.trabalhoandroid.model.ProdutoDAO;

import java.util.ArrayList;

public class TelaAlimento extends AppCompatActivity {
    private EditText edtCodigo;
    private EditText edtDescricao;
    private EditText edtUnidade;
    private EditText edtCalorias;

    private ArrayList<String> listaLst;
    private ArrayList<Integer> listaLstCodigo;

    private ArrayAdapter<String> adapter;
    private ListView lstAlimento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_alimento);

        edtCodigo=(EditText) findViewById(R.id.edtCodigo);
        edtDescricao=(EditText) findViewById(R.id.edtDescricao);
        edtUnidade=(EditText) findViewById(R.id.edtUnidade);
        edtCalorias=(EditText) findViewById(R.id.edtCalorias);

        lstAlimento=(ListView) findViewById(R.id.lstAlimento);

        listaLst = new ArrayList<>();
        listaLstCodigo = new ArrayList<>();


        lstAlimento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int pos = i;
                ProdutoDAO objDAO;
                Produto obj;

                try {
                    objDAO = new ProdutoDAO();

                    if ((pos >= 0) && (pos < listaLstCodigo.size())) {
                        obj = objDAO.preencher(getBaseContext(), listaLstCodigo.get(pos));
                        edtCodigo.setText(String.valueOf(obj.getCodigo()));
                        edtDescricao.setText(obj.getDescr());
                        edtUnidade.setText(String.valueOf(obj.getUnidade()));
                        edtCalorias.setText(String.valueOf(obj.getCaloria()));
                    }
                } catch (Exception ex) {
                    Toast.makeText(getBaseContext(), "Erro:  " + ex.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    public void gravar(View v)
    {
        Produto obj;
        ProdutoDAO objDAO;

        try{
            obj = new Produto();
            objDAO = new ProdutoDAO();

            obj.setDescr(edtDescricao.getText().toString());
            obj.setUnidade(edtUnidade.getText().toString());
            obj.setCaloria(edtCalorias.getText().toString());
            objDAO.gravar(getBaseContext(), obj);
            edtDescricao.setText("");
            edtUnidade.setText("");
            edtCalorias.setText("");
            edtDescricao.requestFocus();
        }
        catch (Exception ex){
            Toast.makeText(getBaseContext(),"Erro:  " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void remover(View v)
    {
        Produto obj;
        ProdutoDAO objDAO;

        try{
            obj=new Produto();
            objDAO = new ProdutoDAO();

            obj.setCodigo(edtCodigo.getText().toString());
            objDAO.remover(getBaseContext(), obj);

            edtCodigo.setText("");
            edtDescricao.setText("");
            edtUnidade.setText("");
            edtCalorias.setText("");
            edtDescricao.requestFocus();
        }
        catch (Exception ex){
            Toast.makeText(getBaseContext(),"Erro:  " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
    public void alterar(View v)
    {
        Produto obj;
        ProdutoDAO objDAO;

        try{
            obj=new Produto();
            objDAO = new ProdutoDAO();

            obj.setCodigo(edtCodigo.getText().toString());
            obj.setDescr(edtDescricao.getText().toString());
            obj.setUnidade(edtUnidade.getText().toString());
            obj.setCaloria(edtCalorias.getText().toString());
            objDAO.alterar(getBaseContext(), obj);
        }
        catch (Exception ex){
            Toast.makeText(getBaseContext(),"Erro:  " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void listar(View v)
    {
        int i;
        String s="";
        ProdutoDAO objDAO;
        Cursor tabela;

        try{
            objDAO = new ProdutoDAO();

            if(listaLst!=null) {
                listaLst.clear();
                listaLstCodigo.clear();
            }
            else {
                listaLst = new ArrayList<>();
                listaLstCodigo = new ArrayList<>();

            }

            tabela = objDAO.listar(getBaseContext());

            if(tabela != null) {
                while(tabela.moveToNext()){
                    s = tabela.getString(1) + "  " + tabela.getInt(2) + "g  " + tabela.getInt(3) + "cal/g";
                    listaLst.add(s);
                    listaLstCodigo.add(tabela.getInt(0));
                }
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaLst);
                lstAlimento.setAdapter(adapter);
            }
        }
        catch(Exception ex)
        {
            Toast.makeText(getBaseContext(),"Erro:  " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}
