package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AlunoDal {

    private Connection con;
    PreparedStatement stmt = null;

    public AlunoDal(){
        con = DBconnect.getConnection();
    }

    public boolean saveAluno(Aluno aluno){
        final String insertAluno = "insert into aluno (Nome, Sobrenome, Sexo, Nascimento, Idade) values (?,?,?,?,?)";
        try {
            stmt = con.prepareStatement(insertAluno);
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getSobrenome());
            stmt.setString(3, String.valueOf(aluno.getSexo()));
            stmt.setString(4, aluno.getNascimento().toString());
            stmt.setInt(5, aluno.getIdade());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBconnect.closeConnection(con, stmt);
        }
    }

    public Aluno getAluno(int codigo){
        final String getAluno = "select * from aluno where idAluno = " + codigo + ";";
        try {
            stmt = con.prepareStatement(getAluno);
            ResultSet rs = stmt.executeQuery(getAluno);
            Aluno aluno = new Aluno();

            while (rs.next())
            {
                aluno.setIdAluno(rs.getInt("idAluno"));
                aluno.setNome(rs.getString("Nome"));
                aluno.setSobrenome(rs.getString("Sobrenome"));
                aluno.setSexo(rs.getString("Sexo").charAt(0));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(rs.getDate("Nascimento"));
                aluno.setNascimento(calendar);
                aluno.setIdade(rs.getInt("Idade"));
            }
            return aluno;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBconnect.closeConnection(con, stmt);
        }
    }

    public ArrayList<Aluno> getAlunos(){
        final String getAluno = "select * from aluno;";
        try {
            stmt = con.prepareStatement(getAluno);
            ResultSet rs = stmt.executeQuery(getAluno);
            List<Aluno> alunos = new ArrayList<>();
            while (rs.next())
            {
                Aluno aluno = new Aluno();
                aluno.setIdAluno(rs.getInt("idAluno"));
                aluno.setNome(rs.getString("Nome"));
                aluno.setSobrenome(rs.getString("Sobrenome"));
                aluno.setSexo(rs.getString("Sexo").charAt(0));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(rs.getDate("Nascimento"));
                aluno.setNascimento(calendar);
                aluno.setIdade(rs.getInt("Idade"));
                alunos.add(aluno);
            }
            return (ArrayList<Aluno>) alunos;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBconnect.closeConnection(con, stmt);
        }
    }

}
