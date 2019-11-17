package com.company;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    private static final int inserirAluno = 1;
    private static final int inserirMatricula = 2;
    private static final int inserirDisciplina = 3;
    private static final int listarAlunos = 4;
    private static final int listarAluno = 5;
    private static final int sairSistema = 6;

    public static void main(String[] args) throws ParseException {
        Integer option = readOption();

        while (option != sairSistema) {

            switch (option) {

                case inserirAluno:
                    setInserirAluno();
                    break;

                case inserirMatricula:
                    setInserirMatricula();
                    break;

                case inserirDisciplina:
                    setInserirDisciplina();
                    break;

                case listarAlunos:
                    listarAlunos();
                    break;

                case listarAluno:
                    listarAluno();
                    break;

                case sairSistema:
                    JOptionPane.showMessageDialog(null, "Leaving the program...");
                    break;

                default:
                    JOptionPane.showMessageDialog(null,
                            "Invalid Option. Type numbers only between 1 and 6 :)");

            }

            option = readOption();
        }

    }

    private static Integer readOption() {

        String menu = " [------- MENU -------] ";

        menu += "\n -- Choose a Option -- ";
        menu += "\n[1] - Cadastrar aluno";
        menu += "\n[2] - Cadastrar matricula";
        menu += "\n[3] - Cadastrar disciplina";
        menu += "\n[4] - Listar todos alunos";
        menu += "\n[5] - Listar aluno por codigo";
        menu += "\n[6] - Exit";

        String startOption = JOptionPane.showInputDialog(null, menu);

        return Integer.parseInt(startOption);

    }

    private  static void setInserirAluno() throws ParseException {
        String nome = JOptionPane.showInputDialog(null, "Nome do aluno: ");
        String sobrenome = JOptionPane.showInputDialog(null, "Sobrenome: ");
        String sexo = JOptionPane.showInputDialog(null, "Sexo: ");
        Calendar nascimento = getNascimento();
        Aluno aluno = new Aluno(nome, sobrenome, sexo, nascimento);
        AlunoDal alunoDal = new AlunoDal();
        alunoDal.saveAluno(aluno);
    }

    private  static void setInserirMatricula() throws ParseException {
        short anoLetivo = Short.parseShort(JOptionPane.showInputDialog(null, "Ano letivo: "));
        byte serie = (byte) Integer.parseInt(JOptionPane.showInputDialog(null, "Serie: "));
        int codigoAluno = Integer.parseInt(JOptionPane.showInputDialog(null, "Codigo Aluno: "));
        int tipoDisciplina =  Integer.parseInt(JOptionPane.showInputDialog("Tipo de disciplina: " +
                "\n[1] Disciplina normal\n[2] Disciplina prática"));
        if(tipoDisciplina == 1){
            int codigoDsiciplina = Integer.parseInt(JOptionPane.showInputDialog(null, "Codigo disciplina: "));
            AlunoDal alunoDal = new AlunoDal();
            Aluno aluno = alunoDal.getAluno(codigoAluno);

            DisciplinaDal disciplinaDal = new DisciplinaDal();
            ArrayList<Disciplina> disciplinas = new ArrayList<Disciplina>();
            disciplinas.add((Disciplina) disciplinaDal.getDisciplina(codigoDsiciplina));

            Matricula matricula = new Matricula(anoLetivo, serie, aluno, disciplinas);
            MatriculaDal matriculaDal = new MatriculaDal();
            matriculaDal.saveMatricula(matricula, false);
        }else if(tipoDisciplina == 2){
            int codigoDsiciplina = Integer.parseInt(JOptionPane.showInputDialog(null, "Codigo disciplina: "));

            AlunoDal alunoDal = new AlunoDal();
            Aluno aluno = alunoDal.getAluno(codigoAluno);

            DisciplinaPraticaDal disciplinaPraticaDal = new DisciplinaPraticaDal();
            ArrayList<DisciplinaPratica> disciplinaPraticas = new ArrayList<DisciplinaPratica>();
            disciplinaPraticas.add((DisciplinaPratica) disciplinaPraticaDal.getDisciplina(codigoDsiciplina));

            Matricula matricula = new Matricula(anoLetivo, serie, aluno, disciplinaPraticas);
            MatriculaDal matriculaDal = new MatriculaDal();
            matriculaDal.saveMatricula(matricula, true);
        }

    }

    private static void setInserirDisciplina(){
        int tipoDisciplina =  Integer.parseInt(JOptionPane.showInputDialog("Tipo de disciplina: " +
                "\n[1] Disciplina normal\n[2] Disciplina prática"));
        if(tipoDisciplina == 1){
            String nome = JOptionPane.showInputDialog("Nome da disciplina");
            int cargaHoraria = Integer.parseInt(JOptionPane.showInputDialog("Carga horária"));
            Disciplina disciplina = new Disciplina(nome, cargaHoraria);
            DisciplinaDal disciplinaDal = new DisciplinaDal();
            disciplinaDal.saveDisciplina(disciplina);
        } else if(tipoDisciplina == 2){
            String nome = JOptionPane.showInputDialog("Nome da disciplina");
            int cargaHoraria = Integer.parseInt(JOptionPane.showInputDialog("Carga horária"));
            DisciplinaPratica disciplinaPratica = new DisciplinaPratica(nome, cargaHoraria);
            DisciplinaPraticaDal disciplinaPraticaDal = new DisciplinaPraticaDal();
            disciplinaPraticaDal.saveDisciplinaPratica(disciplinaPratica);
        }
    }

    private static void listarAlunos(){
        AlunoDal alunoDal = new AlunoDal();
        ArrayList<Aluno> aluno = alunoDal.getAlunos();
        JTable table = new JTable();
        DefaultTableModel dtm = new DefaultTableModel(0, 0);
        String header[] = new String[] { "Codigo", "Nome", "Sobrenome", "Sexo", "Nascimento"};

        dtm.setColumnIdentifiers(header);

        table.setModel(dtm);

        for(int i = 0; i < aluno.size(); i++){
            for (Aluno alunoTeste : aluno) {
                dtm.addRow(new Object[] { alunoTeste.getIdAluno(), alunoTeste.getNome(),
                        alunoTeste.getSobrenome(), alunoTeste.getSexo(), alunoTeste.getNascimento()});
            }
        }
        table.setModel(dtm);
        JOptionPane.showMessageDialog(null, new JScrollPane(table));
    }

    private static void listarAluno(){
        int codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Codigo do aluno: "));
        AlunoDal alunoDal = new AlunoDal();
        Aluno aluno = alunoDal.getAluno(codigo);

        String menu = " [------- Aluno -------] ";

        menu += "\nCodigo do Aluno: " + aluno.getIdAluno();
        menu += "\nNome do Aluno: " + aluno.getNome();
        menu += "\nSobrenome do Aluno: " + aluno.getSobrenome();
        menu += "\nSexo do Aluno: " + aluno.getSexo();
        menu += "\nData de nascimento do Aluno: " + aluno.getNascimento();

        JOptionPane.showMessageDialog(null, menu);
    }


    private static Calendar getNascimento() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nascimento = JOptionPane.showInputDialog(null, "Data de nascimento: ");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(nascimento));
        return c;
    }

}