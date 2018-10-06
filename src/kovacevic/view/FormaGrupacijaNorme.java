/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import kovacevic.controller.ObradaGrupacijaNorme;
import kovacevic.model.GrupacijaNorme;
import kovacevic.pomocno.PorukaIznimke;

/**
 *
 * @author Marko Kovačević
 */
public class FormaGrupacijaNorme extends JFrame {

    private GrupacijaNorme grupacijaNorme;
    private final ObradaGrupacijaNorme obradaGrupacijaNorme;

    private boolean dodaj = false;
    private boolean promijeni = false;
    private boolean obrisi = false;

    /**
     * Creates new form FormaAnalizaCijene
     */
    public FormaGrupacijaNorme() {
        initComponents();
        setTitle("Grupacija Norme");

        obradaGrupacijaNorme = new ObradaGrupacijaNorme();

    }

    public FormaGrupacijaNorme(GrupacijaNorme grupacijaNorme, boolean dodaj, boolean promjeni) {
        initComponents();
        setTitle("Grupacija Norme - Dodavanje novog");
        this.grupacijaNorme = grupacijaNorme;
        obradaGrupacijaNorme = new ObradaGrupacijaNorme();
        if (dodaj == false) {
            setTitle("Grupacija Norme - Promjena");
            btnDodaj.getModel().setEnabled(false);
        }
        if (promjeni == false) {
            btnPromjeni.getModel().setEnabled(false);
        }
        povuciPodatkeZaCaller(grupacijaNorme);
    }

    public void povuciPodatkeZaCaller(GrupacijaNorme grupacijaNorme) {
        if (grupacijaNorme == null) {
            return;
        }
        txtOznakaNormeKombinirano.setText(formatTekstaZaKombiniranuOznaku(grupacijaNorme.getOznakaNorme(), grupacijaNorme.getGrupaNorme()));
        txtOznakaGrupeNorme.setText(grupacijaNorme.getOznakaNorme());
        txtGrupa.setText(grupacijaNorme.getGrupaNorme());
        tarOpisNorme.setText(grupacijaNorme.getOpis());

        txtOznakaGrupeNorme.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                txtOznakaNormeKombinirano.setText(formatTekstaZaKombiniranuOznaku(txtOznakaGrupeNorme.getText(), txtGrupa.getText()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                txtOznakaNormeKombinirano.setText(formatTekstaZaKombiniranuOznaku(txtOznakaGrupeNorme.getText(), txtGrupa.getText()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        txtGrupa.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                txtOznakaNormeKombinirano.setText(formatTekstaZaKombiniranuOznaku(txtOznakaGrupeNorme.getText(), txtGrupa.getText()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                txtOznakaNormeKombinirano.setText(formatTekstaZaKombiniranuOznaku(txtOznakaGrupeNorme.getText(), txtGrupa.getText()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    private String formatTekstaZaKombiniranuOznaku(String cOznakaNorme, String cOznakaGrupeNorme) {
        int polozaj = 0;
        String grupaNorme = cOznakaNorme;
        if (grupaNorme != null) {
            polozaj = grupaNorme.indexOf("-", 3);
            if (polozaj == -1) {
                return cOznakaNorme + " " + cOznakaGrupeNorme;
            } else {
                grupaNorme = grupaNorme.substring(0, polozaj) + " " + cOznakaGrupeNorme;
                return grupaNorme;
            }
        } else {
            return "";
        }
    }

    private void dpoGrupacijaNorme() {
        List<String> nfePoruke = new ArrayList<>();
//        String dodatakNaslovu = "";
        grupacijaNorme.setGrupaNorme(txtGrupa.getText());
        grupacijaNorme.setOznakaNorme(txtOznakaGrupeNorme.getText());
        grupacijaNorme.setOpis(tarOpisNorme.getText());

        try {
            if (dodaj == true) {
//                dodatakNaslovu = " - Dodavanje novog";
                obradaGrupacijaNorme.spremi(grupacijaNorme);
            }
            if (promijeni == true) {
//                dodatakNaslovu = " - Promijena";
                obradaGrupacijaNorme.promijeni(grupacijaNorme);
            }
            if (obrisi == true) {
//                dodatakNaslovu = " - Brisanje";
                obradaGrupacijaNorme.obrisi(grupacijaNorme);
            }
        } catch (PorukaIznimke pi) {
            JOptionPane.showMessageDialog(rootPane, (nfePoruke.isEmpty() ? "" : nfePoruke.toString() + "\n") + pi.getOpis() + pi.getGreska(),
                    getTitle()/* + dodatakNaslovu*/, HEIGHT);
            if (pi.getGreska().contains(obradaGrupacijaNorme.OZNAKA_NORME)) {
                txtOznakaGrupeNorme.requestFocus();
            } else if (pi.getGreska().contains(obradaGrupacijaNorme.GRUPA_NORME)) {
                txtGrupa.requestFocus();
            } else if (pi.getGreska().contains(obradaGrupacijaNorme.OPIS)) {
                tarOpisNorme.requestFocus();
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

        lblGrupaNorme = new javax.swing.JLabel();
        txtGrupa = new javax.swing.JTextField();
        lblOpisGrupeNorme = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tarOpisNorme = new javax.swing.JTextArea();
        btnDodaj = new javax.swing.JButton();
        btnPromjeni = new javax.swing.JButton();
        btnObrisi = new javax.swing.JButton();
        lbOznakaGrupeNorme = new javax.swing.JLabel();
        txtOznakaGrupeNorme = new javax.swing.JTextField();
        txtOznakaNormeKombinirano = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(300, 0));

        lblGrupaNorme.setText("Grupa norme:");

        lblOpisGrupeNorme.setText("Opis grupe norme:");

        jScrollPane2.setToolTipText("");

        tarOpisNorme.setColumns(20);
        tarOpisNorme.setRows(5);
        tarOpisNorme.setWrapStyleWord(true);
        tarOpisNorme.setPreferredSize(new java.awt.Dimension(104, 79));
        jScrollPane2.setViewportView(tarOpisNorme);
        tarOpisNorme.setLineWrap(true);

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

        lbOznakaGrupeNorme.setText("Oznaka grupe norme:");

        txtOznakaNormeKombinirano.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(158, 158, 158)
                        .addComponent(btnDodaj)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPromjeni)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                        .addComponent(btnObrisi))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblOpisGrupeNorme)
                            .addComponent(lblGrupaNorme)
                            .addComponent(lbOznakaGrupeNorme))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addComponent(txtOznakaNormeKombinirano)
                            .addComponent(txtOznakaGrupeNorme)
                            .addComponent(txtGrupa))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtOznakaNormeKombinirano, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOznakaGrupeNorme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbOznakaGrupeNorme))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGrupa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGrupaNorme))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOpisGrupeNorme)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        dodaj = true;
        grupacijaNorme = new GrupacijaNorme();
        dpoGrupacijaNorme();
        dodaj = false;
    }//GEN-LAST:event_btnDodajActionPerformed

    private void btnPromjeniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPromjeniActionPerformed
        promijeni = true;
        dpoGrupacijaNorme();
        promijeni = false;
    }//GEN-LAST:event_btnPromjeniActionPerformed

    private void btnObrisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObrisiActionPerformed

    }//GEN-LAST:event_btnObrisiActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDodaj;
    private javax.swing.JButton btnObrisi;
    private javax.swing.JButton btnPromjeni;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbOznakaGrupeNorme;
    private javax.swing.JLabel lblGrupaNorme;
    private javax.swing.JLabel lblOpisGrupeNorme;
    private javax.swing.JTextArea tarOpisNorme;
    private javax.swing.JTextField txtGrupa;
    private javax.swing.JTextField txtOznakaGrupeNorme;
    private javax.swing.JTextField txtOznakaNormeKombinirano;
    // End of variables declaration//GEN-END:variables
}
