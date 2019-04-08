/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.view;

import static java.awt.image.ImageObserver.HEIGHT;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import kovacevic.controller.ObradaTroskovnik;
import kovacevic.model.Troskovnik;
import kovacevic.pomocno.PorukaIznimke;

/**
 *
 * @author Marko Kovačević
 */
public class FormaTroskovnik extends JFrame {

    private Troskovnik troskovnik;
    private final ObradaTroskovnik obradaTroskovnik;

    private boolean dodaj = false;
    private boolean promijeni = false;
    private boolean obrisi = false;

    /**
     * Creates new form FormaAnalizaCijene
     */
    public FormaTroskovnik() {
        obradaTroskovnik = new ObradaTroskovnik();
        initComponents();
        setTitle("Troškovnik");
    }

    public FormaTroskovnik(boolean dodaj, boolean promjeni) {
        obradaTroskovnik = new ObradaTroskovnik();
        initComponents();
        setTitle("Troškovnik");
        if (dodaj != true) {
            btnSpremi.setEnabled(false);
        }
    }

    private void dpoTroskovnik() {
        
        String ePoruka = "";
        String dodatakNaslovu = "";
        troskovnik.setOznaka(txtOznakaTroskovnika.getText());
        troskovnik.setNaslov(txtNaslovTroskovnika.getText());
        troskovnik.setObjektNaziv(txtObjektTroskovnik.getText());
        troskovnik.setNarucitelj(txtNarucitelj.getText());
        troskovnik.setIzvodac(txtIzvodac.getText());
        try {
            troskovnik.setDatumIzrade(SimpleDateFormat.getInstance().parse(txtDatumIzrade.getText()));
        } catch (ParseException e) {
            ePoruka = "Unešena vrijednost polja " + lblDatum.getText().replace(":", "") + " ne odgovara formatu datuma.";
        }
        
        try {
            if (dodaj == true) {
                dodatakNaslovu = " - Dodavanje novog";
                obradaTroskovnik.spremi(troskovnik);
            }
            if (promijeni == true) {
                dodatakNaslovu = " - Promijena";
                obradaTroskovnik.promijeni(troskovnik);
            }
            if (obrisi == true) {
                dodatakNaslovu = " - Brisanje";
                obradaTroskovnik.obrisi(troskovnik);
            }
        } catch (PorukaIznimke pi) {
            JOptionPane.showMessageDialog(rootPane, (ePoruka.isEmpty() ? "" : ePoruka.toString() + "\n") + pi.getOpis() + pi.getGreska(),
                    getTitle() + dodatakNaslovu, HEIGHT);
            if (pi.getGreska().contains(obradaTroskovnik.OZNAKA_TROSKOVNIK)) {
                txtOznakaTroskovnika.requestFocus();
            } else if (pi.getGreska().contains(obradaTroskovnik.NASLOV_TROSKOVNIK)) {
                txtNaslovTroskovnika.requestFocus();
            } else if (pi.getGreska().contains(obradaTroskovnik.NAZIV_OBJEKT)) {
                txtObjektTroskovnik.requestFocus();
            } else if (pi.getGreska().contains(obradaTroskovnik.DATUM_IZRADE)) {
                txtDatumIzrade.requestFocus();
            }
            return;
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

        btnSpremi = new javax.swing.JButton();
        btnPromjeni = new javax.swing.JButton();
        btnObrisi = new javax.swing.JButton();
        jtpTroskovnik = new javax.swing.JTabbedPane();
        jpTroskovnik = new javax.swing.JPanel();
        lblNaslov = new javax.swing.JLabel();
        lblNarucitelj = new javax.swing.JLabel();
        lblIzvodac = new javax.swing.JLabel();
        lblObjekt = new javax.swing.JLabel();
        lblDatum = new javax.swing.JLabel();
        lblOznaka = new javax.swing.JLabel();
        txtOznakaTroskovnika = new javax.swing.JTextField();
        txtNaslovTroskovnika = new javax.swing.JTextField();
        txtObjektTroskovnik = new javax.swing.JTextField();
        txtNarucitelj = new javax.swing.JTextField();
        txtIzvodac = new javax.swing.JTextField();
        txtDatumIzrade = new javax.swing.JTextField();
        jpStavkaTroskovnika = new javax.swing.JPanel();
        jlpGrupaRadova = new javax.swing.JLayeredPane();
        jspStavkeGrupe = new javax.swing.JScrollPane();
        tblStavkeGrupa = new javax.swing.JTable();
        cmbGrupaNorme = new javax.swing.JComboBox<>();
        lblStavke = new javax.swing.JLabel();
        btnObrisiGrupu = new javax.swing.JButton();
        btnDodajStavku = new javax.swing.JButton();
        btnObrisiRed = new javax.swing.JButton();
        lblUkupno = new javax.swing.JLabel();
        txtUkupnaCijenaGrupa = new javax.swing.JTextField();
        promjeni = new javax.swing.JButton();
        btnGrupaRadova = new javax.swing.JButton();
        jpRekapitualcija = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(850, 0));

        btnSpremi.setText("Spremi");
        btnSpremi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSpremiActionPerformed(evt);
            }
        });

        btnPromjeni.setText("Promjeni");
        btnPromjeni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPromjeniActionPerformed(evt);
            }
        });

        btnObrisi.setText("Obriši");
        btnObrisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnObrisiActionPerformed(evt);
            }
        });

        lblNaslov.setText("Naslov:");

        lblNarucitelj.setText("Naručitelj:");

        lblIzvodac.setText("Izvođač:");

        lblObjekt.setText("Objekt:");

        lblDatum.setText("Datum izrade:");

        lblOznaka.setText("Oznaka:");

        txtOznakaTroskovnika.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOznakaTroskovnikaActionPerformed(evt);
            }
        });

        txtNaslovTroskovnika.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNaslovTroskovnikaActionPerformed(evt);
            }
        });

        txtNarucitelj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNaruciteljActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpTroskovnikLayout = new javax.swing.GroupLayout(jpTroskovnik);
        jpTroskovnik.setLayout(jpTroskovnikLayout);
        jpTroskovnikLayout.setHorizontalGroup(
            jpTroskovnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTroskovnikLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpTroskovnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOznaka)
                    .addComponent(lblNaslov)
                    .addComponent(lblObjekt)
                    .addComponent(lblDatum)
                    .addComponent(lblNarucitelj)
                    .addComponent(lblIzvodac))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpTroskovnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDatumIzrade)
                    .addComponent(txtIzvodac)
                    .addComponent(txtNarucitelj)
                    .addComponent(txtObjektTroskovnik)
                    .addComponent(txtNaslovTroskovnika, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                    .addComponent(txtOznakaTroskovnika))
                .addContainerGap())
        );
        jpTroskovnikLayout.setVerticalGroup(
            jpTroskovnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTroskovnikLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jpTroskovnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOznaka)
                    .addComponent(txtOznakaTroskovnika, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpTroskovnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNaslov)
                    .addComponent(txtNaslovTroskovnika, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpTroskovnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblObjekt)
                    .addComponent(txtObjektTroskovnik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpTroskovnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNarucitelj)
                    .addComponent(txtNarucitelj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpTroskovnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIzvodac)
                    .addComponent(txtIzvodac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpTroskovnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDatum)
                    .addComponent(txtDatumIzrade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(166, Short.MAX_VALUE))
        );

        jtpTroskovnik.addTab("Troškovnik", jpTroskovnik);

        jlpGrupaRadova.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblStavkeGrupa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Oznaka", "Norme", "Opis", "Jedinica mjere", "Jedninična cijena", "Količina", "Cijena"
            }
        ));
        jspStavkeGrupe.setViewportView(tblStavkeGrupa);

        lblStavke.setText("Stavke:");

        btnObrisiGrupu.setText("Obriši");

        btnDodajStavku.setText("Dodaj");

        btnObrisiRed.setText("Obriši");

        lblUkupno.setText("Ukupno:");

        promjeni.setText("Promjeni");
        promjeni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                promjeniActionPerformed(evt);
            }
        });

        jlpGrupaRadova.setLayer(jspStavkeGrupe, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jlpGrupaRadova.setLayer(cmbGrupaNorme, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jlpGrupaRadova.setLayer(lblStavke, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jlpGrupaRadova.setLayer(btnObrisiGrupu, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jlpGrupaRadova.setLayer(btnDodajStavku, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jlpGrupaRadova.setLayer(btnObrisiRed, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jlpGrupaRadova.setLayer(lblUkupno, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jlpGrupaRadova.setLayer(txtUkupnaCijenaGrupa, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jlpGrupaRadova.setLayer(promjeni, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jlpGrupaRadovaLayout = new javax.swing.GroupLayout(jlpGrupaRadova);
        jlpGrupaRadova.setLayout(jlpGrupaRadovaLayout);
        jlpGrupaRadovaLayout.setHorizontalGroup(
            jlpGrupaRadovaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jlpGrupaRadovaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jlpGrupaRadovaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jlpGrupaRadovaLayout.createSequentialGroup()
                        .addComponent(cmbGrupaNorme, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 377, Short.MAX_VALUE)
                        .addComponent(btnObrisiGrupu))
                    .addGroup(jlpGrupaRadovaLayout.createSequentialGroup()
                        .addGroup(jlpGrupaRadovaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(btnObrisiRed)
                            .addComponent(promjeni)
                            .addComponent(btnDodajStavku))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jlpGrupaRadovaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jlpGrupaRadovaLayout.createSequentialGroup()
                                .addComponent(lblStavke)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jspStavkeGrupe)
                            .addGroup(jlpGrupaRadovaLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblUkupno)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtUkupnaCijenaGrupa, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jlpGrupaRadovaLayout.setVerticalGroup(
            jlpGrupaRadovaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jlpGrupaRadovaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jlpGrupaRadovaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbGrupaNorme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnObrisiGrupu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStavke)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jlpGrupaRadovaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jspStavkeGrupe, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jlpGrupaRadovaLayout.createSequentialGroup()
                        .addComponent(btnDodajStavku)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(promjeni)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnObrisiRed)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jlpGrupaRadovaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUkupno)
                    .addComponent(txtUkupnaCijenaGrupa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnGrupaRadova.setText("Dodaj grupu radova");

        javax.swing.GroupLayout jpStavkaTroskovnikaLayout = new javax.swing.GroupLayout(jpStavkaTroskovnika);
        jpStavkaTroskovnika.setLayout(jpStavkaTroskovnikaLayout);
        jpStavkaTroskovnikaLayout.setHorizontalGroup(
            jpStavkaTroskovnikaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpStavkaTroskovnikaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpStavkaTroskovnikaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlpGrupaRadova)
                    .addGroup(jpStavkaTroskovnikaLayout.createSequentialGroup()
                        .addComponent(btnGrupaRadova)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpStavkaTroskovnikaLayout.setVerticalGroup(
            jpStavkaTroskovnikaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpStavkaTroskovnikaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnGrupaRadova)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlpGrupaRadova, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
        );

        jtpTroskovnik.addTab("Stavke", jpStavkaTroskovnika);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Grupa radova", "Iznos"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jpRekapitualcijaLayout = new javax.swing.GroupLayout(jpRekapitualcija);
        jpRekapitualcija.setLayout(jpRekapitualcijaLayout);
        jpRekapitualcijaLayout.setHorizontalGroup(
            jpRekapitualcijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpRekapitualcijaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpRekapitualcijaLayout.setVerticalGroup(
            jpRekapitualcijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpRekapitualcijaLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(223, Short.MAX_VALUE))
        );

        jtpTroskovnik.addTab("Rekapitulacija", jpRekapitualcija);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSpremi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPromjeni)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnObrisi)
                .addContainerGap())
            .addComponent(jtpTroskovnik)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jtpTroskovnik)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSpremi)
                    .addComponent(btnPromjeni)
                    .addComponent(btnObrisi))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPromjeniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPromjeniActionPerformed

    }//GEN-LAST:event_btnPromjeniActionPerformed

    private void btnObrisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObrisiActionPerformed
    }//GEN-LAST:event_btnObrisiActionPerformed

    private void promjeniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_promjeniActionPerformed
    }//GEN-LAST:event_promjeniActionPerformed

    private void txtOznakaTroskovnikaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOznakaTroskovnikaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOznakaTroskovnikaActionPerformed

    private void txtNaslovTroskovnikaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNaslovTroskovnikaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNaslovTroskovnikaActionPerformed

    private void txtNaruciteljActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNaruciteljActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNaruciteljActionPerformed

    private void btnSpremiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSpremiActionPerformed

    }//GEN-LAST:event_btnSpremiActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDodajStavku;
    private javax.swing.JButton btnGrupaRadova;
    private javax.swing.JButton btnObrisi;
    private javax.swing.JButton btnObrisiGrupu;
    private javax.swing.JButton btnObrisiRed;
    private javax.swing.JButton btnPromjeni;
    private javax.swing.JButton btnSpremi;
    private javax.swing.JComboBox<String> cmbGrupaNorme;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLayeredPane jlpGrupaRadova;
    private javax.swing.JPanel jpRekapitualcija;
    private javax.swing.JPanel jpStavkaTroskovnika;
    private javax.swing.JPanel jpTroskovnik;
    private javax.swing.JScrollPane jspStavkeGrupe;
    private javax.swing.JTabbedPane jtpTroskovnik;
    private javax.swing.JLabel lblDatum;
    private javax.swing.JLabel lblIzvodac;
    private javax.swing.JLabel lblNarucitelj;
    private javax.swing.JLabel lblNaslov;
    private javax.swing.JLabel lblObjekt;
    private javax.swing.JLabel lblOznaka;
    private javax.swing.JLabel lblStavke;
    private javax.swing.JLabel lblUkupno;
    private javax.swing.JButton promjeni;
    private javax.swing.JTable tblStavkeGrupa;
    private javax.swing.JTextField txtDatumIzrade;
    private javax.swing.JTextField txtIzvodac;
    private javax.swing.JTextField txtNarucitelj;
    private javax.swing.JTextField txtNaslovTroskovnika;
    private javax.swing.JTextField txtObjektTroskovnik;
    private javax.swing.JTextField txtOznakaTroskovnika;
    private javax.swing.JTextField txtUkupnaCijenaGrupa;
    // End of variables declaration//GEN-END:variables

}
