/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import ejemplo.entidad.Departamentos;
import ejemplo.entidad.Empleados;
import ejemplo.util.HibernateUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultListModel;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author cursomulti
 */
public class Hibernate {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Consulta();
      
    }
    
    public static void Empleado(Empleados user){
    float salario = (float) 2500.25;
       //Empleados user = new Empleados("Perez","Tecnologia",salario); 
       SessionFactory sesion = HibernateUtil.getSessionFactory();
       Session session;
       session = sesion.openSession();
       Transaction tx = session.beginTransaction();
       session.save(user);
       tx.commit();
       session.close();
        
    }
    
    public static void Consulta(){
     SessionFactory sesion = HibernateUtil.getSessionFactory();
     Session session = sesion.openSession();   
     Transaction tx = session.beginTransaction();
     Query q = session.createQuery("from Empleados");
     List<Empleados> lista = q.list();
    // Iterator<Empleados> iter = lista.iterator();
     tx.commit();
     session.close();
    // DefaultListModel dlm = new DefaultListModel();
     
     for(int i = 0;i<lista.size();i++){
     
        
         System.out.println(lista.get(i).toString());
         
     }
    }
    
}
