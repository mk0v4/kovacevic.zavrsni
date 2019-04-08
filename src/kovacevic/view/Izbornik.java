/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marko Kovačević
 */
public class Izbornik extends javax.swing.JFrame {
//nasljedjje se form??

    /**
     * Creates new form Izbornik
     */
    public Izbornik() {
        initComponents();
        setTitle("Završni rad");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnRad = new javax.swing.JButton();
        btnMaterijal = new javax.swing.JButton();
        btnStavkaTroskovnik = new javax.swing.JButton();
        btnEraDijagram = new javax.swing.JButton();
        btnAnalizaRad = new javax.swing.JButton();
        btnAnalizaMaterijal = new javax.swing.JButton();
        btnAnalizaCijene = new javax.swing.JButton();
        lblGithub = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnUnosRadMaterijal = new javax.swing.JButton();
        btnUnosAnalizaCijene = new javax.swing.JButton();
        btnTroskovnici = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(400, 350));

        btnRad.setText("Rad");
        btnRad.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRadActionPerformed(evt);
            }
        });

        btnMaterijal.setText("Materijal");
        btnMaterijal.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMaterijal.setMinimumSize(new java.awt.Dimension(51, 23));
        btnMaterijal.setPreferredSize(new java.awt.Dimension(51, 23));
        btnMaterijal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaterijalActionPerformed(evt);
            }
        });

        btnStavkaTroskovnik.setText("<html><center>Stavka<br>Troškovnika</center></html>");
        btnStavkaTroskovnik.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStavkaTroskovnik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStavkaTroskovnikActionPerformed(evt);
            }
        });

        btnEraDijagram.setText("<html><center>ERA<br> Dijagram</center></html>");
        btnEraDijagram.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEraDijagram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEraDijagramActionPerformed(evt);
            }
        });

        btnAnalizaRad.setText("Analiza Rada");
        btnAnalizaRad.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAnalizaRad.setMinimumSize(new java.awt.Dimension(51, 23));
        btnAnalizaRad.setPreferredSize(new java.awt.Dimension(51, 23));
        btnAnalizaRad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalizaRadActionPerformed(evt);
            }
        });

        btnAnalizaMaterijal.setText("Analiza Materijala");
        btnAnalizaMaterijal.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAnalizaMaterijal.setMinimumSize(new java.awt.Dimension(51, 23));
        btnAnalizaMaterijal.setPreferredSize(new java.awt.Dimension(51, 23));
        btnAnalizaMaterijal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalizaMaterijalActionPerformed(evt);
            }
        });

        btnAnalizaCijene.setText("Analiza cijene");
        btnAnalizaCijene.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAnalizaCijene.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalizaCijeneActionPerformed(evt);
            }
        });

        lblGithub.setText("Github");
        lblGithub.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblGithubMouseClicked(evt);
            }
        });

        jLabel1.setText("Novi raspored");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        btnUnosRadMaterijal.setText("Rad i materijal");
        btnUnosRadMaterijal.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUnosRadMaterijal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnosRadMaterijalActionPerformed(evt);
            }
        });

        btnUnosAnalizaCijene.setText("Analize cijene");
        btnUnosAnalizaCijene.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUnosAnalizaCijene.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnosAnalizaCijeneActionPerformed(evt);
            }
        });

        btnTroskovnici.setText("Troškovnici");
        btnTroskovnici.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTroskovnici.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTroskovniciActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAnalizaRad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAnalizaMaterijal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnMaterijal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(btnAnalizaCijene, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnStavkaTroskovnik)
                    .addComponent(btnEraDijagram)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 128, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(93, 93, 93)
                        .addComponent(lblGithub))
                    .addComponent(btnUnosRadMaterijal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUnosAnalizaCijene, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTroskovnici, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRad, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAnalizaRad, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMaterijal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAnalizaMaterijal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnAnalizaCijene, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnStavkaTroskovnik, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEraDijagram, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGithub)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUnosRadMaterijal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUnosAnalizaCijene, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTroskovnici, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRadActionPerformed
        new FormaRad().setVisible(true);
    }//GEN-LAST:event_btnRadActionPerformed

    private void btnMaterijalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaterijalActionPerformed
        new FormaMaterijal().setVisible(true);
    }//GEN-LAST:event_btnMaterijalActionPerformed

    private void btnEraDijagramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEraDijagramActionPerformed
        new EraDijagram().setVisible(true);
    }//GEN-LAST:event_btnEraDijagramActionPerformed

    private void btnAnalizaRadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalizaRadActionPerformed
        new FormaAnalizaRad().setVisible(true);
    }//GEN-LAST:event_btnAnalizaRadActionPerformed

    private void btnAnalizaMaterijalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalizaMaterijalActionPerformed
        new FormaAnalizaMaterijal().setVisible(true);
    }//GEN-LAST:event_btnAnalizaMaterijalActionPerformed

    private void btnAnalizaCijeneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalizaCijeneActionPerformed
        new FormaAnalizaCijeneOld().setVisible(true);
    }//GEN-LAST:event_btnAnalizaCijeneActionPerformed

    private void lblGithubMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGithubMouseClicked

        try {
            Runtime.getRuntime().exec("cmd /c start https://github.com/mk0v4 ");
        } catch (IOException ex) {
            Logger.getLogger(Izbornik.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_lblGithubMouseClicked

    private void btnStavkaTroskovnikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStavkaTroskovnikActionPerformed
        new FormaStavkaTroskovnikStaro().setVisible(true);
    }//GEN-LAST:event_btnStavkaTroskovnikActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1MouseClicked

    private void btnUnosRadMaterijalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnosRadMaterijalActionPerformed
        new FormaRadMaterijal().setVisible(true);
    }//GEN-LAST:event_btnUnosRadMaterijalActionPerformed

    private void btnUnosAnalizaCijeneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnosAnalizaCijeneActionPerformed
        new FormaAnalizaCijene().setVisible(true);
    }//GEN-LAST:event_btnUnosAnalizaCijeneActionPerformed

    private void btnTroskovniciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTroskovniciActionPerformed
        new FormaTroskovnici().setVisible(true);
    }//GEN-LAST:event_btnTroskovniciActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnalizaCijene;
    private javax.swing.JButton btnAnalizaMaterijal;
    private javax.swing.JButton btnAnalizaRad;
    private javax.swing.JButton btnEraDijagram;
    private javax.swing.JButton btnMaterijal;
    private javax.swing.JButton btnRad;
    private javax.swing.JButton btnStavkaTroskovnik;
    private javax.swing.JButton btnTroskovnici;
    private javax.swing.JButton btnUnosAnalizaCijene;
    private javax.swing.JButton btnUnosRadMaterijal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblGithub;
    // End of variables declaration//GEN-END:variables
}
