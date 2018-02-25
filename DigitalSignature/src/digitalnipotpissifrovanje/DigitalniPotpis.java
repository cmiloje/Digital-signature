/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalnipotpissifrovanje;

import cmd.SkladisteKljucevaSertifikata;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author Cmiloje
 */
public class DigitalniPotpis {
    
    public void Potpisi(String alias, String signAlg, String fajl, String lokacijaPotpisa) throws KeyStoreException, FileNotFoundException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, InvalidKeyException, SignatureException{
        //Ucitavanje privatnog kljuca
        KeyStore skladiste = KeyStore.getInstance(KeyStore.getDefaultType());
        SkladisteKljucevaSertifikata sks = new SkladisteKljucevaSertifikata();
        FileInputStream fis = new FileInputStream(sks.lokacijaSkladista());
        char[] lozinka = {'s','i','n','e','r','g','i','j','a'};
        skladiste.load(fis, lozinka);
        RSAPrivateKey RSAprivatni = null;
        DSAPrivateKey DSAprivatni = null;
        boolean dsa;
        System.out.println("STATUS: "+signAlg.equalsIgnoreCase("SHA1withDSA"));
        if(signAlg.equalsIgnoreCase("SHA1withDSA")){
            dsa = true;
            DSAprivatni = (DSAPrivateKey) skladiste.getKey(alias, lozinka);
        }else{
            dsa = false;
            RSAprivatni = (RSAPrivateKey) skladiste.getKey(alias, lozinka);
        }
        Arrays.fill(lozinka, '\u0000');
        //////////////////////////////////////////////////////////////////
        Signature potpis;
        if(dsa){
            potpis = Signature.getInstance("SHA1withDSA");
            potpis.initSign(DSAprivatni);
        }else{
            potpis = Signature.getInstance("MD5withRSA");
            potpis.initSign(RSAprivatni);
        }
        
        FileInputStream poruka = new FileInputStream(fajl);
        BufferedInputStream bis = new BufferedInputStream(poruka);
        byte[] buffer = new byte[1024];
        int lenght;
        while(bis.available() != 0){
            lenght = bis.read(buffer);
            potpis.update(buffer, 0, lenght);
        }
        bis.close();
        poruka.close();
        byte[] digitalniPotpis = potpis.sign();
        sacuvaj(digitalniPotpis, lokacijaPotpisa+"/potpis.sign");
        System.out.println("Digitalni potpis = "+digitalniPotpis.hashCode());
        System.out.println("Operacija uspjesna!");
    }
    
    
    public void sacuvaj(byte[] podaci, String nazivFajla) throws IOException{
        Path lokacija = Paths.get(nazivFajla);
        Files.write(lokacija, podaci, StandardOpenOption.CREATE_NEW);
    }
    
    public void provjeriDigitalniPotpis(String lokacijaSertifikata, String digitalniPotpisFajl, String lokacijaFajla) throws CertificateException, FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException{
        CertificateFactory fabrika = CertificateFactory.getInstance("X.509");
        FileInputStream fis = new FileInputStream(new File(lokacijaSertifikata));
        Certificate sertifikat = fabrika.generateCertificate(fis);
        fis.close();
        byte[] digitalniPotpis = ucitajFalj(digitalniPotpisFajl);
        PublicKey pk = sertifikat.getPublicKey();
        Signature potpis;
        if(pk.getAlgorithm().equalsIgnoreCase("DSA")){
            potpis = Signature.getInstance("SHA1withDSA");
        }else{
            potpis = Signature.getInstance("MD5withRSA");
        }
        potpis.initVerify(pk);
        FileInputStream poruka = new FileInputStream(lokacijaFajla);
        BufferedInputStream bis = new BufferedInputStream(poruka);
        byte[] buffer = new byte[1024];
        int lenght;
        while(bis.available() != 0){
            lenght = bis.read(buffer);
            potpis.update(buffer, 0, lenght);
        }
        //poruka.close();
        bis.close();
        boolean status = potpis.verify(digitalniPotpis);
        if(status){
            JOptionPane.showMessageDialog(null, "Uspjesna verifikacija potpisa", "Uspjesno!", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Digitalni potpis verifikovan uspjesno!");
        }else{
            JOptionPane.showMessageDialog(null, "Potpis nije verifikovan!", "Upozorenje!", JOptionPane.ERROR_MESSAGE);
            System.out.println("Verifikovanje nije uspjesno!");
        }
    }
    
    public byte[] ucitajFalj(String nazivFajla) throws IOException{
        Path lokacija = Paths.get(nazivFajla);
        return Files.readAllBytes(lokacija);
    }
    
}
