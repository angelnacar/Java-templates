/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviciosftp;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import static java.lang.System.in;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author angel nacar
 */
public class ServiciosFtp {

    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        OpcionesFtp cliente = new OpcionesFtp();
        String opcion = "";
        try {
            cliente.Conexion();

            do {
                cliente.Menu();
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));//Lee por teclado
                opcion = in.readLine().trim();

                switch (opcion) {
                    case "1":
                        cliente.ListarHome();
                        break;
                    case "2":
                        cliente.ListarDirectorio();
                        break;
                    case "3":
                        cliente.CambiarDirectorio();
                        break;
                    case "4":
                        cliente.VerDirectorioActual();
                        break;
                    case "5":
                        cliente.Volver();
                        break;
                    case "6":
                        cliente.DescargarFichero();
                        break;
                    case "7":
                        cliente.SubirFichero();
                        break;
                    case "8":
                        cliente.Ayuda();
                        break;
                    case "9":
                        cliente.Salir();
                        break;

                }
            } while (!opcion.equals("9"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}


/**
 * Clase con los métodos necesarios para la conexion a un servidor ftp y la manipulacion de ficheros
 * @author Angel
 */
class OpcionesFtp{
    public FTPClient cliente;
    public ArrayList<String> ruta = new ArrayList();
    int posicion = 0; //controla la carpeta en la que está navegando
    
    public OpcionesFtp(){
        
    }
    public void Conexion() throws IOException {
        String server = "192.168.1.15"; //direccion del servidor
        String user = "campusfp"; 
        String pass = "campusfp";
        cliente = new FTPClient();
        cliente.connect(server);
        boolean login = cliente.login(user, pass); //devuelve true si el logueo es correcto
        ruta.add("\\");
        
        if (login) {
            System.out.println("Conexion establecida");
        } else {
            System.out.println("Login incorrecto");
            cliente.disconnect();
            System.exit(1);
        }
    }

    public void Menu() {
        System.out.println("--------- MENU --------------");
        System.out.println("1. Listar home");
        System.out.println("2. Listar directorio");
        System.out.println("3. Cambiar directorio");
        System.out.println("4. Ver directorio actual");
        System.out.println("5. Volver atras");
        System.out.println("6. Descargar fichero");
        System.out.println("7. Subir fichero");
        System.out.println("8. Ayuda");
        System.out.println("9. Salir");
    }

    public void ListarHome() {
        try {
            cliente.changeWorkingDirectory("\\"); //cambia de directorio
            System.out.println("Directorio actual " + cliente.printWorkingDirectory()); //
            FTPFile[] files = cliente.listFiles(); //guarda en un array los elementos del directorio donde se encuentra
            String[] tipos = {"Fichero", "Directorio", "Enlace simbolico"};
            System.out.println("Ficheros en el directorio actual " + files.length);
            for (int i = 0; i < files.length; i++) {
                System.out.println(" -----------------------------------------------------");
                System.out.println("\t" + files[i].getName() + " => " + tipos[files[i].getType()]); //devuelve nombre y tipo de elemento
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void ListarDirectorio() {
        try {
            FTPFile[] files = cliente.listFiles();
            String[] tipos = {"Fichero", "Directorio", "Enlace simbolico"};
            System.out.println("Ficheros en el directorio actual " + files.length);
            for (int i = 0; i < files.length; i++) {
                System.out.println(" -----------------------------------------------------");
                System.out.println("\t" + files[i].getName() + " => " + tipos[files[i].getType()]);
            }

        } catch (IOException ex) {
            Logger.getLogger(ServiciosFtp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void CambiarDirectorio() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));//Lee por teclado
            System.out.println("Introduzca directorio >");
            String directorio = in.readLine();
            cliente.changeWorkingDirectory(directorio);
            ruta.add(cliente.printWorkingDirectory());
            posicion++;

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void VerDirectorioActual() {
        try {
            System.out.println("Directorio actual " + cliente.printWorkingDirectory());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void DescargarFichero() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));//Lee por teclado
        BufferedReader in2 = new BufferedReader(new InputStreamReader(System.in));//Lee por teclado
        System.out.println("Introduzca fichero a descargar");

        try {
            String fichero = in.readLine();
            System.out.println("Guardar como");
            DataOutputStream out = new DataOutputStream(new FileOutputStream(in2.readLine()));
            cliente.retrieveFile(fichero, out); //descarga fichero del servidor remoto a la maquina local. string y dataoutput para abrir el flujo de datos

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
    
    public void Volver(){
        try {
            if(posicion <= 0){ //controla que no se salga del array
                System.out.println("Está en la carpeta raiz");
               posicion = 0;
            }
            cliente.changeWorkingDirectory( ruta.get(posicion));
            posicion--;
                
         } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void SubirFichero() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));//Lee por teclado
        BufferedReader in2 = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Guardar como");

        try {
            String fichero = in.readLine();
            System.out.println("Introduzca ruta y fichero a subir");
            DataInputStream in3 = new DataInputStream(new FileInputStream(in2.readLine()));
            cliente.storeFile(fichero, in3); //sube archivos de la maquina local al servidor, se especifica como se va a llamar en el servidor e indicas la ruta donde se encuentra el archivo que quieres subir

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void Ayuda() {
        String opcion = "";
        do {
            System.out.println(" ----------   Ayuda   --------");
            System.out.println("1. Mostrar ayuda Listar home");
            System.out.println("2. Mostrar ayuda Listar directorio");
            System.out.println("3. Mostrar ayuda Cambiar directorio");
            System.out.println("4. Mostrar ayuda Ver directorio actual");
            System.out.println("5. Mostrar ayuda Descargar fichero");
            System.out.println("6. Mostrar ayuda Subir fichero");
            System.out.println("7. Salir del menu ayuda");

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));//Lee por teclado
            try {
                opcion = in.readLine().trim();
                switch (opcion) {
                    case "1":
                        System.out.println("Te redirecciona a la ruta raiz y te lista los elementos de la carpeta");
                        break;
                    case "2":
                        System.out.println("Te muestra los elementos del directorio en que te encuentras");
                        break;
                    case "3":
                        System.out.println("Te permite desplazarte entre los distintos directorios");
                        break;
                    case "4":
                        System.out.println("Te muestra la ruta en la que te encuentras");
                        break;
                    case "5":
                        System.out.println("Te permite descargar un fichero del servidor a tu máquina.\n Primero le indicas que fichero quieres descargar y después cómo lo vas a llamar");
                        break;
                    case "6":
                        System.out.println("Te permite subir un fichero de tu máquina al servidor.\n Primero le indicas qué nombre le das al archivo y después le indicas qué archivo de tu máquina quieres subir");
                        break;

                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } while (!opcion.equals("7"));
    }

    public void Salir() {
        try {
            cliente.logout();
            cliente.disconnect();
            System.out.println("Adios");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
