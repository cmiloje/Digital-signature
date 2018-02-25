/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalnipotpissifrovanje;

import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Cmiloje
 */
public class Provjera {
    
    private String imeSertifikata, imePrezime, organizacionaJedinica, Organizacija, gradLokacija, drzavaRegija, skracenica;
    private boolean kontrola = false;

    public void provjeriPolja(JTextField is, JTextField ip, JTextField oj, JTextField o, JTextField gl, JTextField dr, JTextField s){
       
        this.imeSertifikata = is.getText();
        this.imePrezime = ip.getText().trim();
        this.organizacionaJedinica = oj.getText().trim();
        this.Organizacija = o.getText().trim();
        this.gradLokacija = gl.getText().trim();
        this.drzavaRegija = dr.getText().trim();
        this.skracenica = s.getText().trim();
        if(provjeriImeSertifikata(is))
        if(provjeriImePrezime(ip))
        if(provjeriOrganizacijonuJedinicu(oj))
        if(provjeriOrganizacija(o))
        if(provjeriGradLokaciju(gl))
        if(provjeriDrzavuRegiju(dr))
        if(provjeriSkracenicu(s))
            this.kontrola = true;
    }
    
    private boolean provjeriSkracenicu(JTextField polje){
        boolean status = true;
        this.skracenica = this.skracenica.toUpperCase();
        if(this.skracenica.length()>1 && this.skracenica.length()<3){
            status = false;
            JOptionPane.showMessageDialog(null, "Skracenica drzave mora da ima tri ili vise karaktera!", "Upozorenje", JOptionPane.INFORMATION_MESSAGE);
            polje.setBackground(Color.red);
            polje.requestFocus();
        }else{
            polje.setBackground(Color.white);
        }
        return status;
    }
    
    private boolean provjeriDrzavuRegiju(JTextField polje){
        boolean status = true;
        this.drzavaRegija = this.drzavaRegija.substring(0, 1).toUpperCase() + this.drzavaRegija.substring(1);
        if(this.drzavaRegija.length()<3){
            status = false;
            JOptionPane.showMessageDialog(null, "Drzava ili regija mora da ima vise od 3 karaktera", "Upozorenje", JOptionPane.INFORMATION_MESSAGE);
            polje.setBackground(Color.red);
            polje.requestFocus();
        }else{
            polje.setBackground(Color.white);
        }
        return status;
    }
    
    private boolean provjeriGradLokaciju(JTextField polje){
        boolean status = true;
        this.gradLokacija = this.gradLokacija.substring(0, 1).toUpperCase() + this.gradLokacija.substring(1);
        if(this.gradLokacija.length()<3){
            status = false;
            JOptionPane.showMessageDialog(null, "Grad ili lokacija mora da ima vise od 3 karaktera", "Upozorenje", JOptionPane.INFORMATION_MESSAGE);
            polje.setBackground(Color.red);
            polje.requestFocus();
        }else{
            polje.setBackground(Color.white);
        }
        return status;
    }
    
    private boolean provjeriOrganizacija(JTextField polje){
        boolean status = true;
        this.Organizacija = this.Organizacija.substring(0, 1).toUpperCase() + this.Organizacija.substring(1);
        if(this.Organizacija.length()<3){
            status = false;
            JOptionPane.showMessageDialog(null, "Organizacija mora da ima vise od 3 karaktera", "Upozorenje", JOptionPane.INFORMATION_MESSAGE);
            polje.setBackground(Color.red);
            polje.requestFocus();
        }else{
            polje.setBackground(Color.white);
        }
        return status;
    }
    
    private boolean provjeriOrganizacijonuJedinicu(JTextField polje){
        boolean status = true;
        this.organizacionaJedinica = this.organizacionaJedinica.substring(0, 1).toUpperCase() + this.organizacionaJedinica.substring(1);
        if(this.organizacionaJedinica.length()<3){
            status = false;
            JOptionPane.showMessageDialog(null, "Organizaciona jedinica mora da ima vise od 3 karaktera", "Upozorenje", JOptionPane.INFORMATION_MESSAGE);
            polje.setBackground(Color.red);
            polje.requestFocus();
        }else{
            polje.setBackground(Color.white);
        }
        return status;
    }

    private boolean provjeriImePrezime(JTextField polje){
        boolean status = true;
        boolean razmak = true;
        if (this.imePrezime.length() != 0 && this.imePrezime.contains(" ")) {
            razmak = false;
            String split[] = this.imePrezime.split(" ");
            String ime = split[0].substring(0, 1).toUpperCase() + split[0].substring(1);
            String prezime = split[1].substring(0, 1).toUpperCase() + split[1].substring(1);
            String zajedno = ime + " " + prezime;
            if (ime.length() < 3 || prezime.length() < 3) {
                status = false;
                JOptionPane.showMessageDialog(null, "Ime kao i prezime mora da sadrzi vise od 3 karaktera", "Upozorenje", JOptionPane.INFORMATION_MESSAGE);
                polje.setBackground(Color.red);
                polje.requestFocus();
            } else {
                this.imePrezime = zajedno;
                polje.setBackground(Color.white);
            }
        }else{
            status = false;
            if(this.imePrezime.length() != 0 && razmak){
                JOptionPane.showMessageDialog(null, "Ime i prezime mora da ima razmak", "Upozorenje", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "Ime i prezime mora da ima vise od 3 karaktera", "Upozorenje", JOptionPane.INFORMATION_MESSAGE);
            }
            polje.setBackground(Color.red);
            polje.requestFocus();
        }
        return status;
    }
    
    private boolean provjeriImeSertifikata(JTextField polje){
        boolean status = true;
        if (this.imeSertifikata.length() >= 8) {
            polje.setBackground(Color.WHITE);
        }else{
            status = false;
            JOptionPane.showMessageDialog(null, "Ime sertifikata mora da ima vise od 8 karaktera", "Upozorenje", JOptionPane.INFORMATION_MESSAGE);
            polje.setBackground(Color.red);
            polje.requestFocus();
        }
        return status;
    }
    
    public String getImePrezime() {
        return imePrezime;
    }

    public String getOrganizacionaJedinica() {
        return organizacionaJedinica;
    }

    public String getOrganizacija() {
        return Organizacija;
    }

    public String getGradLokacija() {
        return gradLokacija;
    }

    public String getDrzavaRegija() {
        return drzavaRegija;
    }

    public String getSkracenica() {
        return skracenica;
    }

    public boolean isKontrola() {
        return kontrola;
    }

    public String getImeSertifikata() {
        return imeSertifikata;
    }

}
