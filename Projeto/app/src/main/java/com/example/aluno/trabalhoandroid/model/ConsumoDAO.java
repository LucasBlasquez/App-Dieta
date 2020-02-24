package com.example.aluno.trabalhoandroid.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ConsumoDAO {

    public void gravar(Context context, Consumo obj) throws Exception {
        Banco conexao;
        SQLiteDatabase bb;
        try
        {
            conexao = new Banco(context);
            bb=conexao.getWritableDatabase();
            bb.execSQL("insert into consumo(codproduto,dia,mes,ano,hora,minuto,qtde) values(?,?,?,?,?,?,?)",
                    new String[]{String.valueOf(obj.getCodproduto()), String.valueOf(obj.getDia()),String.valueOf(obj.getMes()),String.valueOf(obj.getAno()),String.valueOf(obj.getHora()),
                            String.valueOf(obj.getMinuto()),String.valueOf(obj.getQtde())});
            bb.close();
        }
        catch(Exception ex){
            throw new Exception("Erro ao gravar: "+ ex.getMessage());
        }
    }

    public void alterar(Context context, Consumo obj) throws Exception {
        Banco conexao;
        SQLiteDatabase bb;
        try
        {
            conexao = new Banco(context);
            bb=conexao.getWritableDatabase();
            bb.execSQL("update consumo set codproduto= ?, qtde=? where codigo=?",
                    new String[]{String.valueOf(obj.getCodproduto()), String.valueOf(obj.getQtde()),String.valueOf(obj.getCodigo())});
            bb.close();
        }
        catch(Exception ex){
            throw new Exception("Erro ao alterar: "+ ex.getMessage());
        }
    }

    public void remover(Context context, Consumo obj) throws Exception {
        Banco conexao;
        SQLiteDatabase bb;
        try
        {
            conexao = new Banco(context);
            bb=conexao.getWritableDatabase();
            bb.execSQL("Delete from consumo where codigo= ?",new String[]{String.valueOf(obj.getCodigo())});
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
            tabela = bb.rawQuery ("Select c.codigo,c.codproduto,p.descr,c.dia,c.mes,c.ano,c.hora,c.minuto,c.qtde from consumo c, produto p where c.codproduto=p.codigo",null);
            // bb.close(); // não pode fechar senão o curso também fecha;
            return(tabela);
        }
        catch (Exception ex){
            throw new Exception("Erro ao consultar: "+ex.getMessage());
        }
    }

    public Cursor listarMesAno(Context context, int mes, int ano) throws Exception{
        Banco conexao;
        SQLiteDatabase bb;
        Cursor tabela= null;
        try{
            conexao = new Banco(context);
            bb = conexao.getReadableDatabase();
            tabela = bb.rawQuery ("Select c.dia,c.mes,c.ano,sum((c.qtde/p.unidade)*p.caloria) from consumo c, produto p where c.codproduto=p.codigo and c.mes=? and c.ano=? group by c.ano,c.mes, c.dia",
                    new String []{String.valueOf(mes),String.valueOf(ano)});
            // bb.close(); // não pode fechar senão o curso também fecha;
            return(tabela);
        }
        catch (Exception ex){
            throw new Exception("Erro ao consultar: "+ex.getMessage());
        }
    }

    public Cursor listarDiaMesAno(Context context, int dia,int mes, int ano) throws Exception{
        Banco conexao;
        SQLiteDatabase bb;
        Cursor tabela= null;
        try{
            conexao = new Banco(context);
            bb = conexao.getReadableDatabase();
            tabela = bb.rawQuery ("Select c.hora,c.minuto, c.dia,c.mes,c.ano,p.descr, c.qtde,((c.qtde/p.unidade)*p.caloria) from consumo c, produto p where c.codproduto=p.codigo and c.dia=? and c.mes=? and c.ano=?",
                    new String []{String.valueOf(dia),String.valueOf(mes),String.valueOf(ano)});
            // bb.close(); // não pode fechar senão o curso também fecha;
            return(tabela);
        }
        catch (Exception ex){
            throw new Exception("Erro ao consultar: "+ex.getMessage());
        }
    }

    public Consumo preencher(Context context, int codigo) throws Exception{
        Banco conexao;
        SQLiteDatabase bb;
        Cursor tabela= null;
        Consumo obj=null;
        try{
            conexao = new Banco(context);
            bb = conexao.getReadableDatabase();


            tabela = bb.rawQuery("Select codigo,codproduto,dia,mes,ano,hora,minuto,qtde from consumo where codigo=?",new String[]{String.valueOf(codigo)});

            if((tabela!=null)&&(tabela.moveToNext())){
                obj = new Consumo();
                obj.setCodigo(tabela.getInt(0));
                obj.setCodproduto(tabela.getInt(1));
                obj.setDia(tabela.getInt(2));
                obj.setMes(tabela.getInt(3));
                obj.setAno(tabela.getInt(4));
                obj.setHora(tabela.getInt(5));
                obj.setMinuto(tabela.getInt(6));
                obj.setQtde(tabela.getDouble(7));
            }
            bb.close();
            return(obj);
        }
        catch (Exception ex){
            throw new Exception("Erro ao preencher: "+ex.getMessage());
        }
    }


}
