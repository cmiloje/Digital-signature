/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmd;

import digitalnipotpissifrovanje.NoviSertifikat;
import gui.PregledSertifikataDialog;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Cmiloje
 */
public class KeyTool extends NoviSertifikat{
    
    String skladiste = "";
    
    

    public KeyTool(String imeSertifikata, String imePrezime, String organizacionaJedinica, String Organizacija, String gradLokacija, String drzavaRegija, String skracenica) {
        super(imeSertifikata, imePrezime, organizacionaJedinica, Organizacija, gradLokacija, drzavaRegija, skracenica);
        try {
            ucitajInfoFajl();
            System.out.println("Skladiste: "+this.skladiste);
        } catch (IOException ex) {
            Logger.getLogger(KeyTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void generisiSertifikat(boolean dsaRSA){
        System.out.println(getImeSertifikata());
        if(dsaRSA){
            kreirajDSAsertifikat();
        }else{
            kreirajRSAsertifikat();
        }
    }

    public KeyTool() {
        super(null, null, null, null, null, null, null);
        try {
            ucitajInfoFajl();
        } catch (IOException ex) {
            Logger.getLogger(KeyTool.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Skladiste: " + this.skladiste);
    }
    
    public void importujSertifikat(String alias, String lokacijaSertifikata){
        File fajl = new File(lokacijaSertifikata);
        ProcessBuilder pb = new ProcessBuilder("keytool.exe", "-import \" -noprompt -alias \""+alias+"\" -file \""+fajl.getAbsolutePath()+"\" -keystore "+this.skladiste+" -storepass sinergija");
        pb.redirectErrorStream(true);
        Process p;
        try {
            p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String linija;
            while (true) {
                linija = br.readLine();
                if (linija == null) {
                    break;
                }
                System.out.println(linija);
            }
            String[] opcije = {"DA", "NE"};
            int opcija = JOptionPane.showOptionDialog(null, "Uspjesno ste importovali sertifikat! \n Otvorite ekran za pregled sertifikata u keystoru ?", "Otvori skladiste", 0, JOptionPane.INFORMATION_MESSAGE, null, opcije, null);
            if(opcija == 0){
                PregledSertifikataDialog psd = new PregledSertifikataDialog(null, true);
                psd.setLocationRelativeTo(null);
                psd.setVisible(true);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex, "Greska!", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(KeyTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void eksportujSertifikatB64Format(String alias, String imeSertifikata, String lokacija){
        //RSA
        String ext = ".b64";
        ProcessBuilder pb = new ProcessBuilder("keytool.exe", "-export \" -noprompt -alias \""+alias+"\" -keystore "+this.skladiste+" -storepass sinergija -file \""+lokacija+"\"\\"+imeSertifikata+ext);
        pb.redirectErrorStream(true);
        Process p;
        try {
            p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String linija;
            while (true) {
                linija = br.readLine();
                if (linija == null) {
                    break;
                }
                System.out.println(linija);
            }
            String[] opcije = {"DA", "NE"};
            int opcija = JOptionPane.showOptionDialog(null, "Uspjesno ste ekportovali sertifikat! \n Otvorite folder sa sertifikatima ?", "Otvori folder", 0, JOptionPane.INFORMATION_MESSAGE, null, opcije, null);
            if(opcija == 0){
                Desktop.getDesktop().open(new File(lokacija));
            }
        } catch (IOException ex) {
            Logger.getLogger(KeyTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eksportujSertifikatDerFormat(String alias, String imeSertifikata, String lokacija){
        //RSA
        String ext = ".der";
        ProcessBuilder pb = new ProcessBuilder("keytool.exe", "-export \" -noprompt -alias \""+alias+"\" -keystore "+this.skladiste+" -storepass sinergija -file \""+lokacija+"\"\\"+imeSertifikata+ext);
        pb.redirectErrorStream(true);
        Process p;
        try {
            p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String linija;
            while (true) {
                linija = br.readLine();
                if (linija == null) {
                    break;
                }
                System.out.println(linija);
            }
            String[] opcije = {"DA", "NE"};
            int opcija = JOptionPane.showOptionDialog(null, "Uspjesno ste ekportovali sertifikat! \n Otvorite folder sa sertifikatima ?", "Otvori folder", 0, JOptionPane.INFORMATION_MESSAGE, null, opcije, null);
            if(opcija == 0){
                Desktop.getDesktop().open(new File(lokacija));
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex, "Greska!", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(KeyTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void kreirajRSAsertifikat(){
        //RSA
        ProcessBuilder pb = new ProcessBuilder("keytool.exe", "-genkey \" -noprompt -alias \""+getImeSertifikata()+"\" -v -keyalg RSA -keystore "+this.skladiste+" -storepass sinergija -dname \"CN="+getImePrezime()+", OU="+getOrganizacionaJedinica()+", O="+getOrganizacija()+", L="+getGradLokacija()+", S="+getDrzavaRegija()+", C="+getSkracenica()+"\" -keypass sinergija");
        pb.redirectErrorStream(true);
        Process p;
        try {
            p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String linija;
            while (true) {
                linija = br.readLine();
                if (linija == null) {
                    break;
                }
                System.out.println(linija);
            }
            JOptionPane.showMessageDialog(null, "Uspjesno ste generisali sertifikat!", "Info..", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(KeyTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void kreirajDSAsertifikat(){
        try {
            //DES
            ProcessBuilder pb = new ProcessBuilder("keytool.exe", "-genkeypair \" -noprompt -alias \""+getImeSertifikata()+"\" -v -keyalg DSA -keystore "+this.skladiste+" -storepass sinergija -dname \"CN="+getImePrezime()+", OU="+getOrganizacionaJedinica()+", O="+getOrganizacija()+", L="+getGradLokacija()+", S="+getDrzavaRegija()+", C="+getSkracenica()+"\" -keypass sinergija");
            pb.redirectErrorStream(true);
            Process p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String linija;
            while(true){
                linija = br.readLine();
                if(linija == null){break;}
                System.out.println(linija);
            }
            JOptionPane.showMessageDialog(null, "Uspjesno ste generisali sertifikat!", "Info..", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(KeyTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean izbrisiSertifikat(String alias){
        boolean status = false;
        try {
            System.out.println("Skladiste: "+this.skladiste);
            ProcessBuilder pb = new ProcessBuilder("keytool.exe", "-delete \" -noprompt -alias \""+alias+"\" -v -keystore "+this.skladiste+" -storepass sinergija");
            pb.redirectErrorStream(true);
            Process p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String linija;
            while(true){
                linija = br.readLine();
                if(linija == null){break;}
                System.out.println(linija);
                status = true;
            }
            JOptionPane.showMessageDialog(null, "Uspjesno ste izbrisali sertifikat!", "Info...", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            status = false;
            Logger.getLogger(KeyTool.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return status;
    }
    
    private void ucitajInfoFajl() throws FileNotFoundException, IOException {
        File fajl = new File("info.txt");
        if (fajl.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(fajl));
            while (br.ready()) {
                String linija = br.readLine();
                System.out.println("Linija: " + linija);
                String[] tmp = linija.split("#");
                if (tmp.length != 0) {
                    for (int i = 0; i < tmp.length; i++) {
                        if (tmp[i].equalsIgnoreCase("lokacijaSkladista")) {
                            this.skladiste = tmp[i + 1];
                        }
                    }
                }
            }
            br.close();
        }
    }
    
}
