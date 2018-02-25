/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalnipotpissifrovanje;


import cmd.SkladisteKljucevaSertifikata;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;



/**
 *
 * @author Cmiloje
 */
public class TEST {
    public static void main(String args[]) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        
        KeyStore skladiste = KeyStore.getInstance(KeyStore.getDefaultType());
        SkladisteKljucevaSertifikata sks = new SkladisteKljucevaSertifikata();
        FileInputStream fis = new FileInputStream(sks.lokacijaSkladista());
        char[] lozinka = {'s','i','n','e','r','g','i','j','a'};
        skladiste.load(fis, lozinka);
    }
    
}
