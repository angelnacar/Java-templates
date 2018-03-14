/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Angel
 */
public class SQLite {
    private String bbdd;
    private Connection conexion;
    private Statement sentencia;
    
    public SQLite(String bbdd){
        this.bbdd = bbdd;
    }
    
    public void Conexion(){
        try {
            Class.forName("org.sqlite.JDBC");
            conexion = DriverManager.getConnection("jdbc:sqlite:"+bbdd);
            sentencia =  conexion.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        
    }
    
    public ResultSet getQuery(String query) throws SQLException{
        return sentencia.executeQuery(query);
    }
    
    public void setActualiza(String query) throws SQLException{
        sentencia.executeUpdate(query);
    }
}
