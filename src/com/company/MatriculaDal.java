package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MatriculaDal {

    private Connection con;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    public MatriculaDal(){
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

    public int VerificaMatricula(int codAluno, int codDisciplina, short anoLetivo, byte serie){
        final String getMatricula = "select ma.idMatricula, al.Nome from Matricula ma\n" +
                "inner join MatriculaAluno as ml on ml.MatriculaId = ma.idMatricula\n" +
                "inner join Aluno as al on al.idAluno = " + codAluno + "\n" +
                "inner join DisciplinaMatricula as dm on dm.MatriculaId = ma.idMatricula\n" +
                "inner join Disciplina as dp on dp.idDisciplina = " + codDisciplina + "\n" +
                "where AnoLetivo = " + anoLetivo + "and Serie = " + serie + ";";

        try{
            stmt = con.prepareStatement(getMatricula);
            rs = stmt.executeQuery(getMatricula);

            int codMatricula = 0;

            while (rs.next())
            {
                codMatricula = rs.getInt("idMatricula");
            }
            return codMatricula;
        } catch (SQLException e) {
            e.printStackTrace();
            return codAluno;
        } finally {
            DBconnect.closeConnection(con, stmt);
        }
    }

    public boolean LancarNotas(int codMatricula, int bimestre, int nota){
        String lancarNota = "";
        switch (bimestre){
            case 1:
                lancarNota = "update from matricula set notaPrimeiroBimestre = " + nota + " where idMatricula = " + codMatricula + ";";
                break;
            case 2:
                lancarNota = "update from matricula set notaSegundoBimestre = " + nota + " where idMatricula = " + codMatricula + ";";
                break;
            case 3:
                lancarNota = "update from matricula set notaSegundoBimestre = " + nota + " where idMatricula = " + codMatricula + ";";
                break;
            case 4:
                lancarNota = "update from matricula set notaSegundoBimestre = " + nota + " where idMatricula = " + codMatricula + ";";
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

        //final String lancarNota = "update from matricula set "
    }

}
