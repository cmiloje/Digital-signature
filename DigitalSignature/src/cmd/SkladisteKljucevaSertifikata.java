/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Cmiloje
 */
public class SkladisteKljucevaSertifikata {

    private String imeSkladista = "";
    private String lokacijaSkladisa = "";

    public SkladisteKljucevaSertifikata() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        boolean status = kreirajInfoFajl();
        System.out.println("STATUS: " + status);
        if (status) {
            if (provjeriPostojanjeSkladista()) {
                pregledSkladista();
            } else {
                Object[] opcije = {"DA", "NE"};
                int opcija = JOptionPane.showOptionDialog(null, "Da li zelite da kreirate novo skladiste kljuceva ?", "Nije pronadjeno skladiste...", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcije, opcije[0]);
                if (opcija == 0) {
                    JFileChooser jfc = new JFileChooser();
                    int save = jfc.showSaveDialog(null);
                    if (save == JFileChooser.APPROVE_OPTION) {
                        File fajl = jfc.getSelectedFile();
                        String[] tmp = fajl.getName().split("\\.");
                        //System.out.println("IME: "+tmp[0]);
                        //System.out.println("LOKACIJA: "+fajl.getParent());
                        kreirajNovoSkladiste(tmp[0], fajl.getParent());
                    } else {
                        JOptionPane.showMessageDialog(null, "Da bi ste koristili aplikaciju morate prvo definisati skladiste kljuceva!\nPonovo pokrenite aplikaciju i definisite skladiste kljuceva!", "Greska!", JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Da bi ste koristili aplikaciju morate prvo definisati skladiste kljuceva!\nPonovo pokrenite aplikaciju i definisite skladiste kljuceva!", "Greska!", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
                System.out.println("IZABRANO: " + opcija);
            }
        }
    }

    public void pregledSkladista(JTable tabela) throws KeyStoreException, FileNotFoundException, IOException, NoSuchAlgorithmException, CertificateException {
        String formatSkladista = Security.getProperty("keystore.type");
        KeyStore skladiste = KeyStore.getInstance(formatSkladista);
        FileInputStream fis = new FileInputStream(this.lokacijaSkladisa);
        char[] lozinka = {'s', 'i', 'n', 'e', 'r', 'g', 'i', 'j', 'a'};
        skladiste.load(fis, lozinka);
        Arrays.fill(lozinka, '\u0000');
        /*---------------------------------------------------------*/
        String[] imenaSertifikata = new String[skladiste.size()];
        int brojac = 0;
        for (Enumeration e = skladiste.aliases(); e.hasMoreElements();) {
            imenaSertifikata[brojac] = (String) e.nextElement();
            System.out.println("-----------------------------------------");
            brojac++;
            //System.out.println(skladiste.getCertificate(alias).toString());
        }
        /*---------------------------------------------------------*/
        String[] signatureAlgoritam = new String[skladiste.size()];
        String[] key = new String[skladiste.size()];
        String[] validFrom = new String[skladiste.size()];
        String[] validTo = new String[skladiste.size()];
        /*---------------------------------------------------------*/
        for (int i = 0; i < imenaSertifikata.length; i++) {
            String tmp = skladiste.getCertificate(imenaSertifikata[i]).toString();
            String[] tmp2 = tmp.split("\\n");
            for (int j = 0; j < tmp2.length; j++) {
                signatureAlgoritam[i] = tmp2[4];
                key[i] = tmp2[6];
                if (key[i].equalsIgnoreCase("  Key:  Sun DSA Public Key")) {
                    validFrom[i] = tmp2[24];
                    validTo[i] = tmp2[25];
                } else {
                    validFrom[i] = tmp2[9];
                    validTo[i] = tmp2[10];
                }
                //System.out.println("j = "+j+" "+tmp2[j]);
            }
        }
        /*---------------------------------------------------------*/
        SimpleDateFormat parseSDF = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        Date datumOd = null;
        Date datumDo = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        DefaultTableModel dtm = (DefaultTableModel) tabela.getModel();
        dtm.getDataVector().removeAllElements();
        for (int i = 0; i < imenaSertifikata.length; i++) {
            if (signatureAlgoritam[i].length() == 59) {
                signatureAlgoritam[i] = "SHA1withDSA";
                key[i] = "Sun DSA Public Key";
                String datumOd2[] = validFrom[i].split("From: ");
                datumOd2[1].replace(",", "");
                String datumDo2[] = validTo[i].split("To:");
                try {
                    datumOd = parseSDF.parse(datumOd2[1]);
                    datumDo = parseSDF.parse(datumDo2[1].replace("]", "").trim());
                } catch (ParseException ex) {
                    Logger.getLogger(SkladisteKljucevaSertifikata.class.getName()).log(Level.SEVERE, null, ex);
                }
                validFrom[i] = sdf.format(datumOd);
                validTo[i] = sdf.format(datumDo);
            } else {
                signatureAlgoritam[i] = "SHA256withRSA";
                key[i] = "Sun RSA public key, 2048 bits";
                String datumOd2[] = validFrom[i].split("From: ");
                datumOd2[1].replace(",", "");
                String datumDo2[] = validTo[i].split("To:");
                try {
                    datumOd = parseSDF.parse(datumOd2[1]);
                    datumDo = parseSDF.parse(datumDo2[1].replace("]", "").trim());
                } catch (ParseException ex) {
                    Logger.getLogger(SkladisteKljucevaSertifikata.class.getName()).log(Level.SEVERE, null, ex);
                }
                validFrom[i] = sdf.format(datumOd);
                validTo[i] = sdf.format(datumDo);
            }
            Object[] o = {i, imenaSertifikata[i].trim(), signatureAlgoritam[i], key[i], validFrom[i], validTo[i]};
            dtm.addRow(o);
        }
    }

    public void pregledSkladista() throws KeyStoreException, FileNotFoundException, IOException, NoSuchAlgorithmException, CertificateException {
        String formatSkladista = Security.getProperty("keystore.type");
        KeyStore skladiste = KeyStore.getInstance(formatSkladista);
        FileInputStream fis = new FileInputStream(this.lokacijaSkladisa);
        char[] lozinka = {'s', 'i', 'n', 'e', 'r', 'g', 'i', 'j', 'a'};
        skladiste.load(fis, lozinka);
        Arrays.fill(lozinka, '\u0000');
        /*---------------------------------------------------------*/
        String[] imenaSertifikata = new String[skladiste.size()];
        int brojac = 0;
        for (Enumeration e = skladiste.aliases(); e.hasMoreElements();) {
            imenaSertifikata[brojac] = (String) e.nextElement();
            System.out.println("-----------------------------------------");
            brojac++;
        }
        /*---------------------------------------------------------*/
        String[] signatureAlgoritam = new String[skladiste.size()];
        String[] key = new String[skladiste.size()];
        String[] validFrom = new String[skladiste.size()];
        String[] validTo = new String[skladiste.size()];
        /*---------------------------------------------------------*/
        for (int i = 0; i < imenaSertifikata.length; i++) {
            String tmp = skladiste.getCertificate(imenaSertifikata[i]).toString();
            String[] tmp2 = tmp.split("\\n");
            for (int j = 0; j < tmp2.length; j++) {
                signatureAlgoritam[i] = tmp2[4];
                key[i] = tmp2[6];
                if (key[i].equalsIgnoreCase("  Key:  Sun DSA Public Key")) {
                    validFrom[i] = tmp2[24];
                    validTo[i] = tmp2[25];
                } else {
                    validFrom[i] = tmp2[9];
                    validTo[i] = tmp2[10];
                }
            }
        }
        /*---------------------------------------------------------*/
        SimpleDateFormat parseSDF = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        Date datumOd = null;
        Date datumDo = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        for (int i = 0; i < imenaSertifikata.length; i++) {
            if (signatureAlgoritam[i].length() == 59) {
                signatureAlgoritam[i] = "SHA1withDSA";
                key[i] = "Sun DSA Public Key";
                String datumOd2[] = validFrom[i].split("From: ");
                datumOd2[1].replace(",", "");
                String datumDo2[] = validTo[i].split("To:");
                try {
                    datumOd = parseSDF.parse(datumOd2[1]);
                    datumDo = parseSDF.parse(datumDo2[1].replace("]", "").trim());
                } catch (ParseException ex) {
                    Logger.getLogger(SkladisteKljucevaSertifikata.class.getName()).log(Level.SEVERE, null, ex);
                }
                validFrom[i] = sdf.format(datumOd);
                validTo[i] = sdf.format(datumDo);
            } else {
                signatureAlgoritam[i] = "SHA256withRSA";
                key[i] = "Sun RSA public key, 2048 bits";
                String datumOd2[] = validFrom[i].split("From: ");
                datumOd2[1].replace(",", "");
                String datumDo2[] = validTo[i].split("To:");
                try {
                    datumOd = parseSDF.parse(datumOd2[1]);
                    datumDo = parseSDF.parse(datumDo2[1].replace("]", "").trim());
                } catch (ParseException ex) {
                    Logger.getLogger(SkladisteKljucevaSertifikata.class.getName()).log(Level.SEVERE, null, ex);
                }
                validFrom[i] = sdf.format(datumOd);
                validTo[i] = sdf.format(datumDo);
            }
            System.out.println("signatureAlgoritam: " + signatureAlgoritam[i] + " duzina: " + signatureAlgoritam[i].length());
            System.out.println("key: " + key[i] + " duzina: " + key[i].length());
            System.out.println("validFrom: " + validFrom[i] + " duzina: " + validFrom[i].length());
            System.out.println("validTo: " + validTo[i] + " duzina: " + validTo[i].length());
            System.out.println("-----------------------------------------");
        }
    }

    public void ucitajKljuceve() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        char[] lozinka = {'s', 'i', 'n', 'e', 'r', 'g', 'i', 'j', 'a'};
        try {
            FileInputStream fis = new FileInputStream(this.lokacijaSkladisa);
            ks.load(fis, lozinka);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SkladisteKljucevaSertifikata.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void kreirajNovoSkladiste(String imeSkladista, String lokacija) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        String ext = ".keystore";
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null, "".toCharArray());
        System.out.println(lokacija + "\\" + imeSkladista + ext);
        FileOutputStream fos = new FileOutputStream(new File(lokacija + "\\" + imeSkladista + ext));
        
        PrintWriter pw = new PrintWriter(new File("info.txt"));
        pw.println("lokacijaSkladista#"+lokacija + "\\" + imeSkladista + ext);
        pw.close();
        char[] lozinka = {'s', 'i', 'n', 'e', 'r', 'g', 'i', 'j', 'a'};
        ks.store(fos, lozinka);
        Arrays.fill(lozinka, '\u0000');
        System.out.println("Kreirano je novo prazno skladiste kljuceva!");
        System.out.println("Tip skladista je: " + ks.getType());
    }

    private boolean provjeriPostojanjeSkladista() throws FileNotFoundException, IOException {
        boolean status = true;
        //String ext = ".keystore"; 
        if (this.lokacijaSkladisa.length() != 0) {
            try {
                FileInputStream fis = new FileInputStream(this.lokacijaSkladisa);
                System.out.println("Pronadjeno je skladiste!");
            } catch (FileNotFoundException ex) {
                //Logger.getLogger(SkladisteKljuceva.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Nije prona]eno skladiste!");
                status = false;
            }

        } else {
            status = false;
        }

        return status;
    }
    
    public String lokacijaSkladista() throws FileNotFoundException, IOException {
        //String ext = ".keystore"; 
        if (this.lokacijaSkladisa.length() != 0) {
            try {
                FileInputStream fis = new FileInputStream(this.lokacijaSkladisa);
                System.out.println("Pronadjeno je skladiste!");
            } catch (FileNotFoundException ex) {
                //Logger.getLogger(SkladisteKljuceva.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Nije prona]eno skladiste!");
            }

        }
        return this.lokacijaSkladisa;
    }

    private boolean kreirajInfoFajl() throws FileNotFoundException, IOException {
        File fajl = new File("info.txt");
        if (fajl.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(fajl));
            while(br.ready()){
                String linija = br.readLine();
                System.out.println("Linija: "+linija);
                String[] tmp = linija.split("#");
                if(tmp.length != 0){
                    for(int i = 0; i<tmp.length; i++){
                        if(tmp[i].equalsIgnoreCase("lokacijaSkladista")){
                            this.lokacijaSkladisa = tmp[i+1];
                        }
                    }
                }
            }
        } else {
            fajl.createNewFile();
        }
        return true;
    }

}
