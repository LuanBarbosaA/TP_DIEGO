package com.company;

public class Disciplina {
    private  int codigoDisciplina;
    private String nome;
    private int cargaHorariaGeral;

    public Disciplina(){

    }

    public Disciplina(String nome, int cargaHorariaGeral){
        this.nome = nome;
        this.cargaHorariaGeral = cargaHorariaGeral;
    }

    public int getCodigoDisciplina() {
        return codigoDisciplina;
    }

    public void setCodigoDisciplina(int codigoDisciplina) {
        codigoDisciplina = codigoDisciplina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCargaHorariaGeral() {
        return cargaHorariaGeral;
    }

    public void setCargaHorariaGeral(int cargaHorariaGeral) {
        cargaHorariaGeral = cargaHorariaGeral;
    }
}
