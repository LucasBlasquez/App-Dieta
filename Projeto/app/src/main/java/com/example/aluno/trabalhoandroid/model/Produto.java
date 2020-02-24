package com.example.aluno.trabalhoandroid.model;

public class Produto {

    private int codigo;
    private String descr;
    private double unidade;
    private double caloria;


    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = Integer.parseInt(codigo);
    }


    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public double getUnidade() {
        return unidade;
    }

    public void setUnidade(double unidade) {
        this.unidade = unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = Double.parseDouble(unidade);
    }

    public double getCaloria() {
        return caloria;
    }

    public void setCaloria(double caloria) {
        this.caloria = caloria;
    }

    public void setCaloria(String caloria) {
        this.caloria = Double.parseDouble(caloria);
    }
}
