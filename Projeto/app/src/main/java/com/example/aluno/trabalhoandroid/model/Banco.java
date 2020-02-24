package com.example.aluno.trabalhoandroid.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Banco extends SQLiteOpenHelper {

    public Banco(Context context){
        // contexto,nomedobanco,cursor, versão
        super(context, "banco.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        sql = "create table produto(codigo integer primary key autoincrement,descr text,unidade real,caloria real);";
        db.execSQL(sql);
        sql = "create table consumo(codigo integer primary key autoincrement,codproduto integer not null,dia integer,mes integer,ano integer,hora integer,minuto integer,qtde real);";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS produto");
        db.execSQL("DROP TABLE IF EXISTS consumo");
        onCreate(db);
    }
}
