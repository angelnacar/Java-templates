/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * Plantilla para la conexion de bbdd en mysql
 * @author angel
 */
public class MySql {
    private Connection conexion = null;
    private String url;
    private String user;
    private String password;
    private Statement sentencia;
    
    public MySql(String url){
        this.url = "jdbc:mysql://localhost:3306/"+url;
        user = "root";
        password = "";
    }
    
    public void Conexion(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(url,user,password);
            sentencia = conexion.createStatement();
            if(conexion != null){
                JOptionPane aviso = new JOptionPane();
                JOptionPane.showMessageDialog(aviso, "La conexión a la bbdd se realizó con éxito", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane aviso = new JOptionPane();
                JOptionPane.showMessageDialog(aviso, "Error en la conexión a la bbdd", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            
        } catch (SQLException ex) {
            JOptionPane aviso = new JOptionPane();
            JOptionPane.showMessageDialog(aviso, ""+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane aviso = new JOptionPane();
            JOptionPane.showMessageDialog(aviso, ""+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    public Connection getConexion() {
        
        return conexion;
    }

    public Statement getSentencia(){
        return sentencia;
    }
    
    public ResultSet getQuery(String query) throws SQLException{
        return getSentencia().executeQuery(query);
    }
    
    
    
}
