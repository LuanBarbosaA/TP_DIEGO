package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DisciplinaPraticaDal {

    private Connection con;
    PreparedStatement stmt = null;

    public DisciplinaPraticaDal(){
        con = DBconnect.getConnection();
    }

    public boolean saveDisciplinaPratica(DisciplinaPratica disciplinaPratica){
        final String insertDisciplina = "insert into disciplina (Nome, CargaHoraria, Pratica) values (?,?,?)";
        try {
            stmt = con.prepareStatement(insertDisciplina);
            stmt.setString(1, disciplinaPratica.getNome());
            stmt.setInt(2, disciplinaPratica.getCargaHorariaPratica());
            stmt.setBoolean(3, true);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBconnect.closeConnection(con, stmt);
        }
    }

    public Disciplina getDisciplina(int codigo){
        final String getDisciplina = "select * from disciplina where idDisciplina = " + codigo + " and pratica = 1;";
        try {
            stmt = con.prepareStatement(getDisciplina);
            ResultSet rs = stmt.executeQuery(getDisciplina);
            DisciplinaPratica disciplinaPratica = new DisciplinaPratica();

            while (rs.next())
            {
                disciplinaPratica.setCodigoDisciplina(rs.getInt("idDisciplina"));
                disciplinaPratica.setNome(rs.getString("Nome"));
                disciplinaPratica.setCargaHorariaPratica(rs.getInt("CargaHoraria"));
            }
            return disciplinaPratica;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBconnect.closeConnection(con, stmt);
        }
    }

}
