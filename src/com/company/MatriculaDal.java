package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MatriculaDal {

    private Connection con;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    public MatriculaDal(){
        con = DBconnect.getConnection();
    }

    private void abrirConexao(){
        con = DBconnect.getConnection();
    }

    public boolean saveMatricula(Matricula matricula, boolean pratica){
        final String insertMatricula = "insert into matricula (AnoLetivo, Serie) values (?,?);";
        final String insertMatriculaAluno = "insert into matriculaaluno (MatriculaId, AlunoId) values (?,?);";
        final String insertDisciplinaMatricula = "insert into disciplinamatricula (DisciplinaId, MatriculaId) values (?,?);";
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement(insertMatricula, stmt.RETURN_GENERATED_KEYS);
            stmt.setInt(1, matricula.getAnoLetivo());
            stmt.setInt(2, matricula.getSerie());
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()){
                int codigo = rs.getInt(1);
                stmt = con.prepareStatement(insertMatriculaAluno);
                stmt.setInt(1, codigo);
                stmt.setInt(2, matricula.getAluno().getIdAluno());
                stmt.executeUpdate();

                stmt = con.prepareStatement(insertDisciplinaMatricula);
                if(pratica){
                    int codigoDisciplina = 0;
                    for (DisciplinaPratica disciplinaPratica : matricula.getDisciplinaPraticas()) {
                        codigoDisciplina = disciplinaPratica.getCodigoDisciplina();
                    }
                    stmt.setInt(1, codigoDisciplina);
                    stmt.setInt(2, codigo);
                    stmt.executeUpdate();
                }else{
                    int codigoDisciplina = 0;
                    for (Disciplina disciplina : matricula.getDisciplinas()) {
                        codigoDisciplina = disciplina.getCodigoDisciplina();
                    }
                    stmt.setInt(1, codigoDisciplina);
                    stmt.setInt(2, codigo);
                    stmt.executeUpdate();
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBconnect.closeConnection(con, stmt);
        }
    }

    public Matricula VerificaMatricula(int codAluno, int codDisciplina, short anoLetivo, byte serie){
        final String getMatricula = "select ma.idMatricula, al.Nome, ma.Media, ma.NotaPrimeiroBimestre, \n" +
                "ma.NotaSegundoBimestre, ma.NotaTerceiroBimestre, \n" +
                "ma.NotaQuartoBimestre from Matricula ma\n" +
                "inner join MatriculaAluno as ml on ml.MatriculaId = ma.idMatricula\n" +
                "inner join Aluno as al on al.idAluno = " + codAluno + "\n" +
                "inner join DisciplinaMatricula as dm on dm.MatriculaId = ma.idMatricula\n" +
                "inner join Disciplina as dp on dp.idDisciplina = " + codDisciplina + "\n" +
                "where AnoLetivo = " + anoLetivo + " and Serie = " + serie + ";";

        try{
            stmt = con.prepareStatement(getMatricula);
            rs = stmt.executeQuery(getMatricula);

            int codMatricula = 0;
            float media = 0.0f;
            Matricula matricula = new Matricula();

            while (rs.next())
            {
                matricula.setCod(rs.getInt("idMatricula"));
                matricula.setMedia(rs.getFloat("Media"));
                matricula.setNotaPrimeiroBimestre(rs.getInt("NotaPrimeiroBimestre"));
                matricula.setNotaSegundoBimestre(rs.getInt("NotaSegundoBimestre"));
                matricula.setNotaTerceiroBimestre(rs.getInt("NotaTerceiroBimestre"));
                matricula.setNotaQuartoBimestre(rs.getInt("NotaQuartoBimestre"));
            }
            return matricula;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBconnect.closeConnection(con, stmt);
        }
    }

    public boolean LancarNotas(int codMatricula, int bimestre, float nota, float media, String aprovado){
        abrirConexao();
        String lancarNota = "";
        switch (bimestre){
            case 1:
                lancarNota = "update matricula set NotaPrimeiroBimestre = " + nota + ", Aprovado = '"+ aprovado +"'"+
                        ", Media = "+ media + " where idMatricula = "+ codMatricula +";";
                break;
            case 2:
                lancarNota = "update matricula set NotaSegundoBimestre = "+ nota +", Aprovado = '"+ aprovado +"'" +
                        ", Media = "+ media +" where idMatricula = "+ codMatricula +";";
                break;
            case 3:
                lancarNota = "update matricula set NotaTerceiroBimestre = "+ nota +", Aprovado = '"+ aprovado +"'" +
                        ", Media = "+ media +" where idMatricula = "+ codMatricula +";";
                break;
            case 4:
                lancarNota = "update matricula set notaQuartoBimestre = "+ nota +", aprovado = '"+ aprovado +"'" +
                        ", Media = "+ media +" where idMatricula = "+ codMatricula +";";
                break;
        }
        try {
            stmt = con.prepareStatement(lancarNota);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBconnect.closeConnection(con, stmt);
        }
    }

    public ArrayList<Matricula> getBoletim(int codAluno, int ano){
        abrirConexao();
        String boletim = "select dp.Nome, dp.CargaHoraria, ma.NotaPrimeiroBimestre, \n" +
                "ma.NotaSegundoBimestre, ma.NotaTerceiroBimestre, \n" +
                "ma.NotaQuartoBimestre, ma.Media, ma.Aprovado from Matricula ma\n" +
                "inner join MatriculaAluno as ml on ml.MatriculaId = ma.idMatricula\n" +
                "inner join Aluno as al on al.idAluno = ml.AlunoId\n" +
                "inner join DisciplinaMatricula as dm on dm.MatriculaId = ma.idMatricula\n" +
                "inner join Disciplina as dp on dp.idDisciplina = dm.DisciplinaId\n" +
                "where AnoLetivo = "+ ano +" and al.idAluno ="+ codAluno;
        try{
            stmt = con.prepareStatement(boletim);
            rs = stmt.executeQuery(boletim);

            List<Matricula> matriculas = new ArrayList<>();
            while (rs.next())
            {
                Matricula matricula = new Matricula();
                Disciplina disciplina = new Disciplina();
                ArrayList<Disciplina> disciplinas = new ArrayList<>();
                matricula.setMedia(rs.getFloat("Media"));
                matricula.setAprovado(rs.getString("Aprovado"));
                matricula.setNotaPrimeiroBimestre(rs.getInt("NotaPrimeiroBimestre"));
                matricula.setNotaSegundoBimestre(rs.getInt("NotaSegundoBimestre"));
                matricula.setNotaTerceiroBimestre(rs.getInt("NotaTerceiroBimestre"));
                matricula.setNotaQuartoBimestre(rs.getInt("NotaQuartoBimestre"));
                disciplina.setNome(rs.getString("Nome"));
                disciplina.setCargaHorariaGeral(rs.getInt("CargaHoraria"));
                disciplinas.add(disciplina);
                matricula.setDisciplinas(disciplinas);
                matriculas.add(matricula);
            }
            return (ArrayList<Matricula>) matriculas;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DBconnect.closeConnection(con, stmt);
        }
    }

}
