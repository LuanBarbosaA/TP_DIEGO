package com.company;

import java.util.ArrayList;
import java.util.List;

public class Matricula {

    private short anoLetivo;
    private byte serie;
    private float notaPrimeiroBimestre = 0.0f;
    private float notaSegundoBimestre = 0.0f;
    private float notaTerceiroBimestre = 0.0f;
    private float notaQuartoBimestre = 0.0f;
    private boolean aprovado = false;
    private float media = 0.0f;
    private Aluno aluno;
    private ArrayList<Disciplina> disciplinas;
    private ArrayList<DisciplinaPratica> disciplinaPraticas;

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public ArrayList<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(ArrayList<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public ArrayList<DisciplinaPratica> getDisciplinaPraticas() {
        return disciplinaPraticas;
    }

    public void setDisciplinaPraticas(ArrayList<DisciplinaPratica> disciplinaPraticas) {
        this.disciplinaPraticas = disciplinaPraticas;
    }

    public Matricula(short anoLetivo, byte serie, Aluno aluno, List<Disciplina> disciplinas) {
        this.aluno = aluno;
        this.disciplinas = (ArrayList<Disciplina>) disciplinas;
        this.anoLetivo = anoLetivo;
        this.serie = serie;
        this.notaPrimeiroBimestre = 0.0f;
        this.notaSegundoBimestre = 0.0f;
        this.notaTerceiroBimestre = 0.0f;
        this.notaQuartoBimestre = 0.0f;
        this.aprovado = false;
        this.media = 0.0f;
    }

    public Matricula(short anoLetivo, byte serie, Aluno aluno, ArrayList<Disciplina> disciplinas,
                     ArrayList<DisciplinaPratica> disciplinaPratica) {
        this.aluno = aluno;
        this.disciplinas = disciplinas;
        this.disciplinaPraticas = disciplinaPratica;
        this.anoLetivo = anoLetivo;
        this.serie = serie;
        this.notaPrimeiroBimestre = 0.0f;
        this.notaSegundoBimestre = 0.0f;
        this.notaTerceiroBimestre = 0.0f;
        this.notaQuartoBimestre = 0.0f;
        this.aprovado = false;
        this.media = 0.0f;
    }

    public Matricula(short anoLetivo, byte serie, Aluno aluno, ArrayList<DisciplinaPratica> disciplinaPratica) {
        this.aluno = aluno;
        this.disciplinaPraticas = disciplinaPratica;
        this.anoLetivo = anoLetivo;
        this.serie = serie;
        this.notaPrimeiroBimestre = 0.0f;
        this.notaSegundoBimestre = 0.0f;
        this.notaTerceiroBimestre = 0.0f;
        this.notaQuartoBimestre = 0.0f;
        this.aprovado = false;
        this.media = 0.0f;
    }

    public short getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(short anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public byte getSerie() {
        return serie;
    }

    public void setSerie(byte serie) {
        this.serie = serie;
    }

    public float getNotaPrimeiroBimestre() {
        return notaPrimeiroBimestre;
    }

    public void setNotaPrimeiroBimestre(float notaPrimeiroBimestre) {
        this.notaPrimeiroBimestre = notaPrimeiroBimestre;
    }

    public float getNotaSegundoBimestre() {
        return notaSegundoBimestre;
    }

    public void setNotaSegundoBimestre(float notaSegundoBimestre) {
        this.notaSegundoBimestre = notaSegundoBimestre;
    }

    public float getNotaTerceiroBimestre() {
        return notaTerceiroBimestre;
    }

    public void setNotaTerceiroBimestre(float notaTerceiroBimestre) {
        this.notaTerceiroBimestre = notaTerceiroBimestre;
    }

    public float getNotaQuartoBimestre() {
        return notaQuartoBimestre;
    }

    public void setNotaQuartoBimestre(float notaQuartoBimestre) {
        this.notaQuartoBimestre = notaQuartoBimestre;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }

    public float getMedia() {
        return media;
    }

    public void setMedia(float media) {
        this.media = media;
    }

    public float MaiorNota(){
        if(notaPrimeiroBimestre > notaSegundoBimestre
                && notaPrimeiroBimestre > notaTerceiroBimestre
                && notaPrimeiroBimestre > notaQuartoBimestre){
            return notaPrimeiroBimestre;
        }else if(notaSegundoBimestre > notaPrimeiroBimestre
                && notaSegundoBimestre > notaTerceiroBimestre
                && notaSegundoBimestre > notaQuartoBimestre){
            return notaSegundoBimestre;
        }else if(notaTerceiroBimestre > notaSegundoBimestre
                && notaTerceiroBimestre > notaPrimeiroBimestre
                && notaTerceiroBimestre > notaQuartoBimestre){
            return notaTerceiroBimestre;
        }else {
            return notaQuartoBimestre;
        }
    }

    public float MenorNota(){
        if(notaPrimeiroBimestre < notaSegundoBimestre
                && notaPrimeiroBimestre < notaTerceiroBimestre
                && notaPrimeiroBimestre < notaQuartoBimestre){
            return notaPrimeiroBimestre;
        }else if(notaSegundoBimestre < notaPrimeiroBimestre
                && notaSegundoBimestre < notaTerceiroBimestre
                && notaSegundoBimestre < notaQuartoBimestre){
            return notaSegundoBimestre;
        }else if(notaTerceiroBimestre < notaSegundoBimestre
                && notaTerceiroBimestre < notaPrimeiroBimestre
                && notaTerceiroBimestre < notaQuartoBimestre){
            return notaTerceiroBimestre;
        }else {
            return notaQuartoBimestre;
        }
    }

    public float Media(float[] notas){
        float resultado = 0.0f;
        for(int i = 0; i < notas.length; i++){
            resultado += notas[i];
        }
        return resultado / notas.length;
    }

    public float MediaPonderada(float[] notas, int pesos[]){
        float resultado = 0.0f;
        int peso = 0;
        for(int i = 0; i < notas.length; i++){
            peso += pesos[i];
            resultado += (notas[i] * pesos[i]);
        }
        resultado = resultado / peso;
        setMedia(resultado);
        return resultado;
    }
    private void VerificaNotas(){
        if(media > 7){
            setAprovado(true);
        }
    }
}
