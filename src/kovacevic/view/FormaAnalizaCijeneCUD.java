/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import kovacevic.controller.HibernateObrada;
import kovacevic.model.AnalizaCijene;
import kovacevic.model.AnalizaMaterijal;
import kovacevic.model.AnalizaRad;
import kovacevic.model.StavkaTroskovnik;
import kovacevic.pomocno.HibernateUtil;

/**
 *
 * @author Marko Kovačević
 */
public class FormaAnalizaCijeneCUD extends Forma<AnalizaCijene> {

    private List<AnalizaCijene> analizaCijena;
    private List<AnalizaMaterijal> analizaMaterijal;
    private List<AnalizaRad> analizaRad;
    private List<StavkaTroskovnik> stavkaTroskovnik;

    /**
     * Creates new form FormaAnalizaCijene
     */
    public FormaAnalizaCijeneCUD() {
        initComponents();
        setTitle("Analiza Cijena");
        obrada = new HibernateObrada();

        ucitaj();
        ucitajAnalizaRad();
        ucitajAnalizaMaterijal();
        ucitajStavkaTroskovnik();

    }

    @Override
    protected void ucitaj() {

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblOznakaNorme = new javax.swing.JLabel();
        txtOznakaNorme = new javax.swing.JTextField();
        lblOpisNorme = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tarOpisNorme = new javax.swing.JTextArea();
        lblJedinicaMjere = new javax.swing.JLabel();
        txtJedinicaMjere = new javax.swing.JTextField();
        lblUkupnoVrijeme = new javax.swing.JLabel();
        txtUkupanNormativVremena = new javax.swing.JTextField();
        lblUkupnoRad = new javax.swing.JLabel();
        txtUkupnoRad = new javax.swing.JTextField();
        lblKoeficijentFirme = new javax.swing.JLabel();
        txtKoeficijentFirme = new javax.swing.JTextField();
        lblUkupnoMaterijal = new javax.swing.JLabel();
        txtUkupnoMaterijal = new javax.swing.JTextField();
        lblUkupnaCijena = new javax.swing.JLabel();
        txtUkupnaCijena = new javax.swing.JTextField();
        btnDodaj = new javax.swing.JButton();
        btnPromjeni = new javax.swing.JButton();
        btnObrisi = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(300, 0));

        lblOznakaNorme.setText("Oznaka norme:");

        lblOpisNorme.setText("Opis norme:");

        jScrollPane2.setToolTipText("");

        tarOpisNorme.setColumns(20);
        tarOpisNorme.setRows(5);
        tarOpisNorme.setWrapStyleWord(true);
        tarOpisNorme.setPreferredSize(new java.awt.Dimension(104, 79));
        jScrollPane2.setViewportView(tarOpisNorme);
        tarOpisNorme.setLineWrap(true);

        lblJedinicaMjere.setText("Jedinica mjere:");

        lblUkupnoVrijeme.setText("Ukupan normativ vremena:");

        lblUkupnoRad.setText("Ukupna cijena rada:");

        lblKoeficijentFirme.setText("Koeficijent:");

        lblUkupnoMaterijal.setText("Ukupna cijena materijala:");

        lblUkupnaCijena.setText("Ukupna cijena:");

        btnDodaj.setText("Dodaj");
        btnDodaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDodajActionPerformed(evt);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOpisNorme)
                    .addComponent(lblJedinicaMjere)
                    .addComponent(lblUkupnoVrijeme)
                    .addComponent(lblUkupnoRad)
                    .addComponent(lblKoeficijentFirme)
                    .addComponent(lblUkupnoMaterijal)
                    .addComponent(lblUkupnaCijena)
                    .addComponent(lblOznakaNorme))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUkupnaCijena)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnDodaj)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPromjeni)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                        .addComponent(btnObrisi))
                    .addComponent(txtUkupnoMaterijal)
                    .addComponent(txtKoeficijentFirme)
                    .addComponent(txtUkupnoRad)
                    .addComponent(txtUkupanNormativVremena)
                    .addComponent(txtJedinicaMjere)
                    .addComponent(jScrollPane2)
                    .addComponent(txtOznakaNorme))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOznakaNorme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblOznakaNorme))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOpisNorme)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtJedinicaMjere, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblJedinicaMjere))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUkupanNormativVremena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUkupnoVrijeme))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUkupnoRad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUkupnoRad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKoeficijentFirme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblKoeficijentFirme))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUkupnoMaterijal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUkupnoMaterijal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUkupnaCijena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUkupnaCijena))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDodaj)
                    .addComponent(btnPromjeni)
                    .addComponent(btnObrisi))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDodajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDodajActionPerformed
        entitet = new AnalizaCijene();
        spremi();
        repaint();
    }//GEN-LAST:event_btnDodajActionPerformed

    private void btnPromjeniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPromjeniActionPerformed
//        if (lstAnalizaCijena.getSelectedValue() == null) {
//            JOptionPane.showConfirmDialog(rootPane, "Prvo odaberite stavku");
//        }
//        spremi();
    }//GEN-LAST:event_btnPromjeniActionPerformed

    private void btnObrisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObrisiActionPerformed
//        if (lstAnalizaCijena.getSelectedValue() == null) {
//            JOptionPane.showConfirmDialog(rootPane, "Prvo odaberite stavku");
//        }
//        obrisi();
    }//GEN-LAST:event_btnObrisiActionPerformed
    @Override
    protected void spremi() {
        entitet.setOznakaNorme(txtOznakaNorme.getText());
        entitet.setOpis(tarOpisNorme.getText());
        entitet.setJedinicaMjere(txtJedinicaMjere.getText());
        entitet.setUkupanNormativVremena(new BigDecimal(txtUkupanNormativVremena.getText()));
        entitet.setUkupnaCijenaRad(new BigDecimal(txtUkupnoRad.getText()));
        entitet.setKoeficijentFirme(new BigDecimal(txtKoeficijentFirme.getText()));
        entitet.setUkupnaCijenaMaterijal(new BigDecimal(txtUkupnoMaterijal.getText()));
        entitet.setSveukupanIznos(new BigDecimal(txtUkupnaCijena.getText()));
//        entitet.setStavkaTroskovnik((StavkaTroskovnik) cmbStavkaTroskovnik.getSelectedItem());
        super.spremi();
        repaint();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDodaj;
    private javax.swing.JButton btnObrisi;
    private javax.swing.JButton btnPromjeni;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblJedinicaMjere;
    private javax.swing.JLabel lblKoeficijentFirme;
    private javax.swing.JLabel lblOpisNorme;
    private javax.swing.JLabel lblOznakaNorme;
    private javax.swing.JLabel lblUkupnaCijena;
    private javax.swing.JLabel lblUkupnoMaterijal;
    private javax.swing.JLabel lblUkupnoRad;
    private javax.swing.JLabel lblUkupnoVrijeme;
    private javax.swing.JTextArea tarOpisNorme;
    private javax.swing.JTextField txtJedinicaMjere;
    private javax.swing.JTextField txtKoeficijentFirme;
    private javax.swing.JTextField txtOznakaNorme;
    private javax.swing.JTextField txtUkupanNormativVremena;
    private javax.swing.JTextField txtUkupnaCijena;
    private javax.swing.JTextField txtUkupnoMaterijal;
    private javax.swing.JTextField txtUkupnoRad;
    // End of variables declaration//GEN-END:variables

    private void ucitajAnalizaRad() {
        List<AnalizaRad> ar = new ArrayList<>();
        analizaRad = HibernateUtil.getSession().createQuery("from AnalizaRad a where a.obrisan=false").list();
        analizaRad.stream().forEach((p) -> {
            if (entitet != null) {
                boolean dodaj = true;
                for (AnalizaRad analizaR : entitet.getAnalizeRadova()) {
                    if (p.getId().equals(analizaR.getId())) {
                        dodaj = false;
                        break;
                    }
                }
                if (dodaj) {
                    ar.add(p);
                }
            }

        });

    }

    private void ucitajAnalizaMaterijal() {
        List<AnalizaMaterijal> am = new ArrayList<>();
        analizaMaterijal = HibernateUtil.getSession().createQuery("from AnalizaMaterijal a where a.obrisan=false").list();
        analizaMaterijal.stream().forEach((p) -> {
            if (entitet != null) {
                boolean dodaj = true;
                for (AnalizaMaterijal analizaM : entitet.getAnalize_materijala()) {
                    if (p.getId().equals(analizaM.getId())) {
                        dodaj = false;
                        break;
                    }
                }
                if (dodaj) {
                    am.add(p);
                }
            }

        });
        repaint();
    }

    private void ucitajStavkaTroskovnik() {
        DefaultComboBoxModel<StavkaTroskovnik> m = new DefaultComboBoxModel<>();
//        cmbStavkaTroskovnik.setModel(m);
        List<StavkaTroskovnik> stavka = HibernateUtil.getSession().
                createQuery("from StavkaTroskovnik a where "
                        + "a.obrisan=false  ").list();

        for (StavkaTroskovnik p : stavka) {

            m.addElement(p);
//            cmbStavkaTroskovnik.setSelectedItem(p);

        }

    }

}
