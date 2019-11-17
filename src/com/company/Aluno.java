package com.company;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Aluno {
    private int idAluno;
    private String nome;
    private String sobrenome;
    private char sexo;
    private Calendar nascimento;
    private int idade;

    public Aluno(){
    }

    public Aluno(String nome, String sobrenome, String sexo, Calendar nascimento){
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.sexo = sexo.charAt(0);
        this.nascimento = nascimento;
    }

    private int calculaIdade(){
        Calendar hoje = Calendar.getInstance();
        int idade = hoje.get(Calendar.YEAR) - nascimento.get(Calendar.YEAR);
        if (hoje.get(Calendar.MONTH) < nascimento.get(Calendar.MONTH)) {
            idade--;
        }
        else
        {
            if (hoje.get(Calendar.MONTH) == nascimento.get(Calendar.MONTH)
                    && hoje.get(Calendar.DAY_OF_MONTH) < nascimento.get(Calendar.DAY_OF_MONTH)) {
                idade--;
            }
        }
        return idade;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getNascimento() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dataFormatada = df.format(nascimento.getTime());
        return dataFormatada;
    }

    public void setNascimento(Calendar nascimento) {
        this.nascimento = nascimento;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade() {
        this.idade = calculaIdade();
    }
}
