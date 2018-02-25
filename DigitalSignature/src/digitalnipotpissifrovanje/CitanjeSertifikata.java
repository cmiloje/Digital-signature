/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package digitalnipotpissifrovanje;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author student
 */
public class CitanjeSertifikata {
    
    public void DerSertifikat(String imeSertifikata){
        try {
            String ext = ".der";
            CertificateFactory fabrika = CertificateFactory.getInstance("X.509");
            File fajl = new File(imeSertifikata+ext);
            FileInputStream fis = new FileInputStream(fajl);
            Certificate sertifikat = fabrika.generateCertificate(fis);
            System.out.println("SERTIFIKAT JE UCITAN: \n"+sertifikat.toString());
            fis.close();
        } catch (CertificateException ex) {
            Logger.getLogger(CitanjeSertifikata.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CitanjeSertifikata.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CitanjeSertifikata.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void B64Sertifikat(String imeSertifikata){
        try {
            String ext = ".b64";
            CertificateFactory fabrika = CertificateFactory.getInstance("X.509");
            Path lokacija = Paths.get(imeSertifikata+ext);
            byte[] dsaSertifikabB64 = Files.readAllBytes(lokacija);
            ByteArrayInputStream bais = new ByteArrayInputStream(dsaSertifikabB64);
            Certificate sertifikat = fabrika.generateCertificate(bais);
            System.out.println("SERTIFIKAT JE UCITAN: \n"+sertifikat.toString());
        } catch (CertificateException ex) {
            Logger.getLogger(CitanjeSertifikata.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Nije pronadjen takav sertifikat");
            Logger.getLogger(CitanjeSertifikata.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
