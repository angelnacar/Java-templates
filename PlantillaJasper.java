/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postgree;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Admin
 */
public class PlantillaJasper {
    private Map parametros;
    private JasperPrint print;
    private JFileChooser fichero;
    private String nombreParametro;
    private String rutaArchivoJasper;
    private Connection conexion;
    /**
     * 
     * @param nombreParametro introduce el nombre del parametro que has creado en jasperstudio
     * @param rutaArchivoJasper //introduce la ruta el archivo *.jasper creado
     * @param conexion  //pasa la variable Connect a la bbdd
     */
    public PlantillaJasper(String nombreParametro, String rutaArchivoJasper, Connection conexion) {
       
       
        this.nombreParametro = nombreParametro;
        this.rutaArchivoJasper = rutaArchivoJasper;
        this.conexion = conexion;
    }
    
    /**
     * 
     * @param datos //introduce la condicion por la que quieras buscar en la tabla de la bbdd
     */
    public void Conexion(String datos){
        try { 
            parametros = new HashMap();
            parametros.put(nombreParametro,datos);
            String seleccion2 = "";
            File seleccion = null;
            print = JasperFillManager.fillReport(rutaArchivoJasper,parametros,conexion);
            fichero = new JFileChooser();
            fichero.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int selecfichero = fichero.showDialog(null,"Guardar como");
            
            if (selecfichero == JFileChooser.APPROVE_OPTION) {
                seleccion = fichero.getSelectedFile();
                seleccion2 = seleccion.getAbsolutePath();
            }
                JasperExportManager.exportReportToPdfFile(print, seleccion2);
                Desktop.getDesktop().open(seleccion);
            
            
        } catch (JRException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
