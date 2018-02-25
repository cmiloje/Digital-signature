/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import cmd.KeyTool;
import cmd.SkladisteKljucevaSertifikata;
import digitalnipotpissifrovanje.CitanjeSertifikata;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Cmiloje
 */
public class PregledSertifikataDialog extends javax.swing.JDialog {

    /**
     * Creates new form NewJDialog
     */
    public PregledSertifikataDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        try {
            SkladisteKljucevaSertifikata sks = new SkladisteKljucevaSertifikata();
            sks.pregledSkladista(tabelaSertifikata_TABELA);
        } catch (KeyStoreException ex) {
            Logger.getLogger(PregledSertifikataDialog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PregledSertifikataDialog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PregledSertifikataDialog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(PregledSertifikataDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabelaPmeni_POPMENI = new javax.swing.JPopupMenu();
        import_MI = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        Eksportuj_MI = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        izbrisi_MI = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaSertifikata_TABELA = new javax.swing.JTable();

        import_MI.setText("Importuj sertifikat");
        import_MI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                import_MIActionPerformed(evt);
            }
        });
        tabelaPmeni_POPMENI.add(import_MI);
        tabelaPmeni_POPMENI.add(jSeparator1);

        Eksportuj_MI.setText("Eksportuj");
        Eksportuj_MI.setToolTipText("");
        Eksportuj_MI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Eksportuj_MIActionPerformed(evt);
            }
        });
        tabelaPmeni_POPMENI.add(Eksportuj_MI);
        tabelaPmeni_POPMENI.add(jSeparator2);

        izbrisi_MI.setText("Izbrisi");
        izbrisi_MI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                izbrisi_MIActionPerformed(evt);
            }
        });
        tabelaPmeni_POPMENI.add(izbrisi_MI);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pregled sertifikata u skladistu");

        tabelaSertifikata_TABELA.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "RB", "Alias", "Signature alg", "Key", "Valid from", "Valid to"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaSertifikata_TABELA.setComponentPopupMenu(tabelaPmeni_POPMENI);
        tabelaSertifikata_TABELA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaSertifikata_TABELAMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaSertifikata_TABELA);
        if (tabelaSertifikata_TABELA.getColumnModel().getColumnCount() > 0) {
            tabelaSertifikata_TABELA.getColumnModel().getColumn(0).setMinWidth(80);
            tabelaSertifikata_TABELA.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabelaSertifikata_TABELA.getColumnModel().getColumn(0).setMaxWidth(80);
            tabelaSertifikata_TABELA.getColumnModel().getColumn(1).setMinWidth(120);
            tabelaSertifikata_TABELA.getColumnModel().getColumn(1).setPreferredWidth(120);
            tabelaSertifikata_TABELA.getColumnModel().getColumn(1).setMaxWidth(120);
            tabelaSertifikata_TABELA.getColumnModel().getColumn(4).setMinWidth(100);
            tabelaSertifikata_TABELA.getColumnModel().getColumn(4).setPreferredWidth(100);
            tabelaSertifikata_TABELA.getColumnModel().getColumn(4).setMaxWidth(100);
            tabelaSertifikata_TABELA.getColumnModel().getColumn(5).setMinWidth(100);
            tabelaSertifikata_TABELA.getColumnModel().getColumn(5).setPreferredWidth(100);
            tabelaSertifikata_TABELA.getColumnModel().getColumn(5).setMaxWidth(100);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Eksportuj_MIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Eksportuj_MIActionPerformed
        int red = this.tabelaSertifikata_TABELA.getSelectedRow();
        if (red != -1) {
            String[] opcije = {"DER", "B64", "Odustani"};
            int opcija = JOptionPane.showOptionDialog(this, "Izaberite format po kojem zelite da eksportujete sertifikat:", "Ekportuj sertifikat", 0, JOptionPane.INFORMATION_MESSAGE, null, opcije, null);
            System.out.println(opcija);
            KeyTool kt = new KeyTool();
            String alias = this.tabelaSertifikata_TABELA.getValueAt(red, 1).toString().trim();
            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc.setDialogTitle("Izaberite lokaciju na koju zelite da exportujete sertifikat.");
            int opcija2 = jfc.showDialog(null, "Izaberi");
            if (opcija2 == JFileChooser.APPROVE_OPTION) {
                if (opcija == 0) {
                    kt.eksportujSertifikatDerFormat(alias, alias, jfc.getSelectedFile().getAbsolutePath());
                } else if (opcija == 1) {
                    kt.eksportujSertifikatB64Format(alias, alias, jfc.getSelectedFile().getAbsolutePath());
                }
            }
        }else{
            JOptionPane.showMessageDialog(this, "Molimo vas da izaberete jedan od sertifikata", "Greska", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_Eksportuj_MIActionPerformed

    private void izbrisi_MIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_izbrisi_MIActionPerformed
        KeyTool kt = new KeyTool();
        int red = this.tabelaSertifikata_TABELA.getSelectedRow();
        if (red != -1) {
            String alias = this.tabelaSertifikata_TABELA.getValueAt(red, 1).toString();
            if (kt.izbrisiSertifikat(alias)) {
                try {
                    SkladisteKljucevaSertifikata sks = new SkladisteKljucevaSertifikata();
                    sks.pregledSkladista(tabelaSertifikata_TABELA);
                } catch (KeyStoreException ex) {
                    Logger.getLogger(PregledSertifikataDialog.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(PregledSertifikataDialog.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(PregledSertifikataDialog.class.getName()).log(Level.SEVERE, null, ex);
                } catch (CertificateException ex) {
                    Logger.getLogger(PregledSertifikataDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Doslo je do greske", "Greska", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }//GEN-LAST:event_izbrisi_MIActionPerformed

    private void tabelaSertifikata_TABELAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaSertifikata_TABELAMouseClicked
        if(evt.getClickCount() == 2){
            int red = this.tabelaSertifikata_TABELA.getSelectedRow();
            String alias = (String) this.tabelaSertifikata_TABELA.getValueAt(red, 1);
            DigitalnoPotpisivanje.setSignAlg(this.tabelaSertifikata_TABELA.getValueAt(red, 2).toString());
            DigitalnoPotpisivanje.sertifikatAlias_TF.setText(alias);
            this.dispose();
        }
    }//GEN-LAST:event_tabelaSertifikata_TABELAMouseClicked

    private void import_MIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_import_MIActionPerformed
        ImportSertifikata is = new ImportSertifikata(null, true);
        is.setLocationRelativeTo(null);
        is.setVisible(true);
    }//GEN-LAST:event_import_MIActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PregledSertifikataDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PregledSertifikataDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PregledSertifikataDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PregledSertifikataDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                    PregledSertifikataDialog dialog = new PregledSertifikataDialog(new javax.swing.JFrame(), true);
                    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e) {
                            System.exit(0);
                        }
                    });
                    dialog.setVisible(true);
            }
        }); 
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Eksportuj_MI;
    private javax.swing.JMenuItem import_MI;
    private javax.swing.JMenuItem izbrisi_MI;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu tabelaPmeni_POPMENI;
    public static javax.swing.JTable tabelaSertifikata_TABELA;
    // End of variables declaration//GEN-END:variables
}
