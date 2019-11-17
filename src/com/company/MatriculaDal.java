package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MatriculaDal {

    private Connection con;
    PreparedStatement stmt = null;

    public MatriculaDal(){
        con = DBconnect.getConnection();
    }

    public boolean saveMatricula(Matricula matricula, boolean pratica){
        final String insertMatricula = "insert into matricula (AnoLetivo, Serie) values (?,?);";
        final String insertMatriculaAluno = "insert into matriculaaluno (MatriculaId, AlunoId) values (?,?);";
        final String insertMatriculaDisciplina = "insert into disciplinamatricula (DisciplinaId, MatriculaId) values (?,?);";
        try {
            stmt = con.prepareStatement(insertMatricula, stmt.RETURN_GENERATED_KEYS);
            stmt.setInt(1, matricula.getAnoLetivo());
            stmt.setInt(2, matricula.getSerie());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){
                int codigo = rs.getInt(1);
                stmt = con.prepareStatement(insertMatriculaAluno);
                stmt.setInt(1, codigo);
                stmt.setInt(2, matricula.getAluno().getIdAluno());
                stmt.executeUpdate();

                stmt = con.prepareStatement(insertMatriculaDisciplina);
                if(pratica){
                    int codigoDisciplina = 0;
                    for (DisciplinaPratica disciplinaPratica : matricula.getDisciplinaPraticas()) {
                        codigoDisciplina = disciplinaPratica.getCodigoDisciplina();
                    }
                    stmt.setInt(1, codigoDisciplina);
                    stmt.setInt(2, matricula.getAluno().getIdAluno());
                    stmt.executeUpdate();
                }else{
                    int codigoDisciplina = 0;
                    for (Disciplina disciplina : matricula.getDisciplinas()) {
                        codigoDisciplina = disciplina.getCodigoDisciplina();
                    }
                    stmt.setInt(1, codigoDisciplina);
                    stmt.setInt(2, matricula.getAluno().getIdAluno());
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

}
