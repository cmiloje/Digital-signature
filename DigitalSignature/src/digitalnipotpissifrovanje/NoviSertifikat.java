/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalnipotpissifrovanje;

/**
 *
 * @author Cmiloje
 */
public class NoviSertifikat {
    private String imeSertifikata, imePrezime, organizacionaJedinica, Organizacija, gradLokacija, drzavaRegija, skracenica;

    public NoviSertifikat(String imeSertifikata, String imePrezime, String organizacionaJedinica, String Organizacija, String gradLokacija, String drzavaRegija, String skracenica) {
        this.imeSertifikata = imeSertifikata;
        this.imePrezime = imePrezime;
        this.organizacionaJedinica = organizacionaJedinica;
        this.Organizacija = Organizacija;
        this.gradLokacija = gradLokacija;
        this.drzavaRegija = drzavaRegija;
        this.skracenica = skracenica;
    }

    
    public void setImePrezime(String imePrezime) {
        this.imePrezime = imePrezime;
    }

    public void setOrganizacionaJedinica(String organizacionaJedinica) {
        this.organizacionaJedinica = organizacionaJedinica;
    }

    public void setOrganizacija(String Organizacija) {
        this.Organizacija = Organizacija;
    }

    public void setGradLokacija(String gradLokacija) {
        this.gradLokacija = gradLokacija;
    }

    public void setDrzavaRegija(String drzavaRegija) {
        this.drzavaRegija = drzavaRegija;
    }

    public void setSkracenica(String skracenica) {
        this.skracenica = skracenica;
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

    public String getImeSertifikata() {
        return imeSertifikata;
    }

    public void setImeSertifikata(String imeSertifikata) {
        this.imeSertifikata = imeSertifikata;
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
    
}
