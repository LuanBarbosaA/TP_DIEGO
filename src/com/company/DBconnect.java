package com.company;

import java.sql.*;

public class DBconnect {
    private static final String host = "jdbc:mysql://localhost:3306/poo" + "?verifyServerCertificate=false"+
            "&useSSL=false"+
            "&requireSSL=false";
    private static final String uName = "root";
    private static final String uPassword = "root";

    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(host, uName, uPassword);
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão", e);
        }
    }

    public static void closeConnection(Connection con){
        if(con != null){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeConnection(Connection con, PreparedStatement smt){
        if(smt != null){
            try {
                smt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        closeConnection(con);
    }

    public static void closeConnection(Connection con, PreparedStatement smt, ResultSet rs){
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        closeConnection(con, smt, rs);
    }

}
