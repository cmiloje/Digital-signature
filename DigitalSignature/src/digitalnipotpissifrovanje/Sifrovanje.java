/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalnipotpissifrovanje;

import cmd.SkladisteKljucevaSertifikata;
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
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Cmiloje
 */
public class Sifrovanje {
    
    public void sifruj(String lokacijaSertifikata, String fajlZaSifrovanje, String sifratFajl) throws CertificateException, FileNotFoundException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        //Ucitavanje sertifikata
        CertificateFactory fabrika = CertificateFactory.getInstance("X.509");
        FileInputStream fis = new FileInputStream(new File(lokacijaSertifikata));
        Certificate sertifikat = fabrika.generateCertificate(fis);
        System.out.println("Sertifikat je ucitan u memoriju");
        fis.close();
        //Inicijalizacija za sifrovanje
        Cipher sifra = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        sifra.init(Cipher.ENCRYPT_MODE, sertifikat);
        //Ucitavanje fajla za sifrovanje
        File fajl = new File(sifratFajl);
        String[] ext = fajl.getName().split("\\.");
        String otvorenaPoruka = "Univerzitet sinergija - Djuric Milenko";
        Path putanja = Paths.get(fajlZaSifrovanje);
        System.out.println("putanja: "+putanja);
        byte[] sifrat = sifra.doFinal(Files.readAllBytes(putanja));
        Path lokacija = Paths.get("sifrat."+ext);
        Files.write(lokacija, sifrat, StandardOpenOption.CREATE_NEW);
        System.out.println("Sifrovanje je uspjesno!");
    }
    
    public void desifruj(String alias, String lokacijaSifrata, String lokacijaDesifrovanePoruke) throws KeyStoreException, FileNotFoundException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        KeyStore skladiste = KeyStore.getInstance(KeyStore.getDefaultType());
        SkladisteKljucevaSertifikata sks = new SkladisteKljucevaSertifikata();
        FileInputStream fis = new FileInputStream(sks.lokacijaSkladista());
        char[] lozinka = {'s','i','n','e','r','g','i','j','a'};
        skladiste.load(fis, lozinka);
        RSAPrivateKey RSAprivatni = (RSAPrivateKey) skladiste.getKey(alias, lozinka);
        Arrays.fill(lozinka, '\u0000');
        Cipher sifra = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        sifra.init(Cipher.DECRYPT_MODE, RSAprivatni);
        Path lokacija = Paths.get(lokacijaSifrata);
        byte[] sifrat = Files.readAllBytes(lokacija);
        byte[] otvorenaPoruka = sifra.doFinal(sifrat);
        File fajl = new File(lokacijaSifrata);
        String[] ext = fajl.getName().split("\\.");
        Path lokacija2 = Paths.get(lokacijaDesifrovanePoruke+ext[1]);
        Files.write(lokacija2, otvorenaPoruka, StandardOpenOption.CREATE_NEW);
        System.out.println("Otvorena poruka: "+ new String(otvorenaPoruka));
    }
    
}
