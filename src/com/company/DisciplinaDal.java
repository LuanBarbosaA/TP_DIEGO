package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class DisciplinaDal {

    private Connection con = null;
    PreparedStatement stmt = null;

    public DisciplinaDal(){
        con = DBconnect.getConnection();
    }

    public boolean saveDisciplina(Disciplina disciplina){
        final String insertDisciplina = "insert into disciplina (Nome, CargaHoraria) values (?,?)";
        try {
            stmt = con.prepareStatement(insertDisciplina);
            stmt.setString(1, disciplina.getNome());
            stmt.setInt(2, disciplina.getCargaHorariaGeral());
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
        final String getDisciplina = "select * from disciplina where idDisciplina = " + codigo + ";";
        try {
            stmt = con.prepareStatement(getDisciplina);
            ResultSet rs = stmt.executeQuery(getDisciplina);
            Disciplina disciplina = new Disciplina();

            while (rs.next())
            {
                disciplina.setCodigoDisciplina(rs.getInt("idDisciplina"));
                disciplina.setNome(rs.getString("Nome"));
                disciplina.setCargaHorariaGeral(rs.getInt("CargaHoraria"));
            }
            return disciplina;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBconnect.closeConnection(con, stmt);
        }
    }

}
