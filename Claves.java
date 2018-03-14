/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteservidorfirma;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

/**
 *  Clase para la generación de claves públicas y privadas
 * @author Angel
 */
public class Claves {
    FileOutputStream fileout,fileout2;
    ObjectOutputStream object,object2;
    KeyPairGenerator keyGen;
    SecureRandom numero;
    KeyPair par;
    PrivateKey clavepri;
    PublicKey clavepub;
    
    /**
     * Genera las claves pública y privaada y lo guarda en dos archivos .dat
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NoSuchAlgorithmException 
     */
    public Claves() throws FileNotFoundException, IOException, NoSuchAlgorithmException{
        fileout = new FileOutputStream("clavePrivada.dat"); //genera los archivos donde se va a almacenar la clave privada
        fileout2 = new FileOutputStream("clavePublica.dat");
        object = new ObjectOutputStream(fileout);
        object2 = new ObjectOutputStream(fileout2);
        keyGen = KeyPairGenerator.getInstance("DSA"); //genera claves con el algoritmo dsa
        numero = SecureRandom.getInstance("SHA1PRNG"); //genera números aleatorios criptográficamente fuerte
        keyGen.initialize(1024, numero); //genera una clave aleatoria de 1024 bytes
        par = keyGen.generateKeyPair();
        clavepri = par.getPrivate(); //genera clave privada
        clavepub = par.getPublic(); //genera clave publica
        
    }
    public void Generar() throws IOException{
        object.writeObject(clavepri);
        object2.writeObject(clavepub);
    }
    public static void main(String args[]){
        try {
            Claves clave = new Claves();
            clave.Generar();
            
        } catch (IOException | NoSuchAlgorithmException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    
}
