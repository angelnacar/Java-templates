/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postgree;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Plantilla para la conexion de bbdd en posgresql
 * @author angel
 */
public class posgre {
    private Connection conexion = null;
    private String url;
    private String user;
    private String password;
    private Statement sentencia;

    public posgre(String url){
        this.url = "jdbc:postgresql://localhost:5432/"+url;
        user = "openpg";
        password = "";
    }

    public void Conexion(){
        try {
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
        }

    }

    public Connection getConexion() {
        return conexion;
    }

    public Statement getSentencia(){
         return sentencia;
     }

     /**
      * Método para la consulta de datos
      * @param query
      * @return
      * @throws SQLException
      */
     public ResultSet getQuery(String query) throws SQLException{
         return getSentencia().executeQuery(query);
     }

     /**
      * Método para la actualización e inserción de datos
      * @param query
      * @throws SQLException
      */
     public void setActualiza(String query) throws SQLException{
         getSentencia().executeUpdate(query);
     }


}
