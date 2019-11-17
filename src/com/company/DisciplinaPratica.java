package com.company;

public class DisciplinaPratica extends Disciplina {
    private int cargaHorariaPratica;

    public DisciplinaPratica(String nome, int cargaHorariaPratica){
        this.setNome(nome);
        this.cargaHorariaPratica = cargaHorariaPratica;
    }

    public DisciplinaPratica() {

    }

    public int getCargaHorariaPratica() {
        return cargaHorariaPratica;
    }

    public void setCargaHorariaPratica(int cargaHorariaPratica) {
        cargaHorariaPratica = cargaHorariaPratica;
    }
}
