/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package probando;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cursomulti
 */
public class derby {
   
    private String bbdd;
    private Connection conexion;
    private Statement sentencia;
    
    public derby(String bbdd){
        this.bbdd = bbdd;
    }
    
    /**
     * Crea la base de datos y se conecta a ella
     */
    public void ConexionCrear(){
        try {
           
            conexion = DriverManager.getConnection("jdbc:derby:"+bbdd+";create=true");
            sentencia =  conexion.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        
    }
    
    /**
     * Se conecta a una bbdd ya creada
     */
    public void Conectar(){
        try {
           
            conexion = DriverManager.getConnection("jdbc:derby:"+bbdd);
            sentencia =  conexion.createStatement();
        } catch (SQLException ex) {
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



