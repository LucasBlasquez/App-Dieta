package com.example.aluno.trabalhoandroid.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ProdutoDAO {

    public void gravar(Context context, Produto obj) throws Exception {
        Banco conexao;
        SQLiteDatabase bb;
        try
        {
            conexao = new Banco(context);
            bb=conexao.getWritableDatabase();
            bb.execSQL("insert into produto(descr,unidade,caloria) values(?,?,?)",new String[]{obj.getDescr(), String.valueOf(obj.getUnidade()),String.valueOf(obj.getCaloria())});
            bb.close();
        }
        catch(Exception ex){
            throw new Exception("Erro ao gravar: "+ ex.getMessage());
        }
    }

    //captura o codigo
    public void gravar2(Context context, Produto obj) throws Exception {
        Banco conexao;
        SQLiteDatabase bb;
        ContentValues campos = new ContentValues();
        long codigo;
        try
        {
            conexao = new Banco(context);
            bb=conexao.getWritableDatabase();
            campos.put("descr",obj.getDescr());
            campos.put("unidade",obj.getUnidade());
            campos.put("caloria",obj.getCaloria());
            codigo=bb.insert("produto",null,campos); //devolve o código gerado pelo sql lite
            obj.setCodigo((int)codigo);
            bb.close();
        }
        catch(Exception ex){
            throw new Exception("Erro ao gravar2: "+ ex.getMessage());
        }
    }

    public void alterar(Context context, Produto obj) throws Exception {
        Banco conexao;
        SQLiteDatabase bb;
        try
        {
            conexao = new Banco(context);
            bb=conexao.getWritableDatabase();
            bb.execSQL("update produto set descr= ?, unidade =?, caloria=? where codigo=?",
                    new String[]{obj.getDescr(), String.valueOf(obj.getUnidade()),String.valueOf(obj.getCaloria()),String.valueOf(obj.getCodigo())});
            bb.close();
        }
        catch(Exception ex){
            throw new Exception("Erro ao alterar: "+ ex.getMessage());
        }
    }

    public void remover(Context context, Produto obj) throws Exception {
        Banco conexao;
        SQLiteDatabase bb;
        try
        {
            conexao = new Banco(context);
            bb=conexao.getWritableDatabase();
            bb.execSQL("Delete from produto where codigo= ?",new String[]{String.valueOf(obj.getCodigo())});
            bb.close();
        }
        catch(Exception ex){
            throw new Exception("Erro ao remover: "+ ex.getMessage());
        }
    }

    public Cursor listar(Context context) throws Exception{
        Banco conexao;
        SQLiteDatabase bb;
        Cursor tabela= null;
        try{
            conexao = new Banco(context);
            bb = conexao.getReadableDatabase();
            tabela = bb.rawQuery ("Select codigo,descr,unidade,caloria from produto",null);
            // bb.close(); // não pode fechar senão o curso também fecha;
            return(tabela);
        }
        catch (Exception ex){
            throw new Exception("Erro ao consultar: "+ex.getMessage());
        }
    }

    public Produto preencher(Context context, int codigo) throws Exception{
        Banco conexao;
        SQLiteDatabase bb;
        Cursor tabela= null;
        Produto obj=null;
        try{
            conexao = new Banco(context);
            bb = conexao.getReadableDatabase();


            tabela = bb.rawQuery("Select codigo,descr,unidade,caloria from produto where codigo=?",new String[]{String.valueOf(codigo)});

            if((tabela!=null)&&(tabela.moveToNext())){
                obj = new Produto();
                obj.setCodigo(tabela.getInt(0));
                obj.setDescr(tabela.getString(1));
                obj.setUnidade(tabela.getDouble(2));
                obj.setCaloria(tabela.getDouble(3));

            }
            bb.close();
            return(obj);
        }
        catch (Exception ex){
            throw new Exception("Erro ao preencher: "+ex.getMessage());
        }
    }



}
