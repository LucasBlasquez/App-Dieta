package com.example.aluno.trabalhoandroid.model;

import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Consumo {

    private int codigo;
    private int codproduto;
    private int dia;
    private int mes;
    private int ano;
    private int hora;
    private int minuto;
    private double qtde;

    public Consumo() throws Exception{
        Calendar hoje;

        try{
            hoje = new GregorianCalendar();
            hoje.setTime(new Date(System.currentTimeMillis()));
            ano= hoje.get(Calendar.YEAR);
            mes= hoje.get(Calendar.MONTH)+1;//mes 0-11
            dia= hoje.get(Calendar.DAY_OF_MONTH);
            hora= hoje.get(Calendar.HOUR_OF_DAY);
            minuto= hoje.get(Calendar.MINUTE);
        }
        catch (Exception ex){
            throw new Exception("+ex.getMessage()");
        }

    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = Integer.parseInt(codigo);
    }

    public int getCodproduto() {
        return codproduto;
    }

    public void setCodproduto(int codproduto) {
        this.codproduto = codproduto;
    }

    public void setCodproduto(String codproduto) {
        this.codproduto = Integer.parseInt(codproduto);
    }
    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public double getQtde() {
        return qtde;
    }

    public void setQtde(double qtde) {
        this.qtde = qtde;
    }

    public void setQtde(String qtde) {
        this.qtde = Double.parseDouble(qtde);
    }
}
