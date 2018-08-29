/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.view;

import java.awt.Font;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;

import kovacevic.controller.Obrada;
import kovacevic.model.Materijal;
import kovacevic.pomocno.HibernateUtil;
import kovacevic.pomocno.WordWrapCellRenderer;

import org.hibernate.Session;

/**
 *
 * @author Marko Kovačević
 */
public class FormaMaterijal extends Forma<Materijal> {

    private List<Materijal> rezultati;

//    private TableCellRenderer tableCellRenderer = new WordWrapCellRenderer();
    /**
     * Creates new form FormaMaterijal
     */
    public FormaMaterijal() {
        initComponents();
        setTitle("Materijal");
        obrada = new Obrada();
        ucitaj();

//        addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                whaaa=false;
//
////                changedSize(tblMaterijal);
//            }
//
//        });
    }

    public void changedSize(JTable table) {

        System.out.println(" table.getSize() " + table.getSize());
        System.out.println(" table.getParent().getSize().getWidth() " + table.getParent().getSize().getWidth());
        System.out.println("table.getColumnCount() " + table.getColumnCount());
        System.out.println("(int) table.getParent().getSize().getWidth()/table.getColumnCount() " + (int) table.getParent().getSize().getWidth() / table.getColumnCount());

        for (int i = 0; i < table.getColumnCount(); i++) {
            System.out.println("table.getColumnModel().getColumn(i).getWidth() "+table.getColumnModel().getColumn(i).getWidth());
            table.getColumnModel().getColumn(i).setPreferredWidth((int) table.getParent().getSize().getWidth() / table.getColumnCount());

        }
        System.out.println("changedSize()" + table.getSize().toString());

    }

    private void updateRowHeights(JTable table) {
        int startRowHeight = table.getRowHeight();
        BigDecimal bdStartRowHeight = new BigDecimal(startRowHeight);

        int h = 0;
        for (int row = 0; row < table.getRowCount(); row++) {
            int actualRowHeight = table.getRowHeight(row);
            h = actualRowHeight+3;
            for (int column = 0; column < table.getModel().getColumnCount(); column++) {

                String value = table.getValueAt(row, column).toString();
                Font valueFont = table.getFont();
//                int valueWidth = table.getFontMetrics(valueFont).stringWidth(value);
                int valueWidth = table.getFontMetrics(valueFont).stringWidth(value.toUpperCase());

                int columnWidth = table.getColumnModel().getColumn(column).getWidth();

                BigDecimal bdValueWidth = new BigDecimal(valueWidth);
                BigDecimal bdColumnWidth = new BigDecimal(columnWidth);

                BigDecimal wholeNumberOfLines = bdValueWidth.divide(bdColumnWidth, RoundingMode.UP);

                if (wholeNumberOfLines.equals(BigDecimal.ZERO)) {
                    wholeNumberOfLines = BigDecimal.ONE;
                }

                BigDecimal rowHeightColumn = wholeNumberOfLines.multiply(bdStartRowHeight);

                if (rowHeightColumn.intValue() > h) {
                    h = rowHeightColumn.intValue()+3;
                }
            }

            table.setRowHeight(row, h);
//            System.out.println("=== row " + row + " h " + h);

        }

    }

    @Override
    protected void ucitaj() {
        Session session = HibernateUtil.getSession();
        session.clear();
        rezultati = HibernateUtil.getSession().createQuery("from Materijal a "
                + "where a.obrisan=false order by grupaMaterijal").list();
        popunjavanjeTablice(tblMaterijal);
    }

    @Override
    protected void spremi() {
        entitet.setGrupaMaterijal(txtGrupaMaterijal.getText());
        entitet.setProizvodac(txtProizvodac.getText());
        entitet.setOznaka(txtOznaka.getText());
        entitet.setKolicinaAmbalaza(new BigDecimal(txtKolicinaAmbalaza.getText()));
        entitet.setJedinicaMjereAmbalaza(txtJedinicaMjereAmbalaza.getText());
        entitet.setCijenaAmbalaza(new BigDecimal(txtCijenaAmbalaza.getText()));
        entitet.setOpis(tarOpis.getText());
        super.spremi();
    }

    private void popunjavanjeTablice(JTable tablica) {

        String[] coulumnName = {"Rb. (Id)", "Grupe materijala", "Proizvođač",
            "Oznaka", "Pakovanje količina", "Jedinica mjere", "Cijena pakovanja", "Opis"};

        DefaultTableModel model = (DefaultTableModel) tablica.getModel();
//        tblMaterijal = new WrappableTable(tablica, model);

//            DefaultTableModel table = new DefaultTableModel();
//        tblMaterijal = new WrappableTable(model);
//        tblMaterijal.setDefaultRenderer(Object.class, tableCellRenderer);
        tablica.setDefaultRenderer(Object.class, new WordWrapCellRenderer());

        model.setRowCount(0);
        model.setColumnIdentifiers(coulumnName);

        //        tablica.setModel(model);
        int rb = 0;
        for (Materijal materijal : rezultati) {
            rb++;
            model.addRow(new Object[]{
                rb + ". (" + materijal.getId() + ")",
                materijal.getGrupaMaterijal(),
                materijal.getProizvodac(),
                materijal.getOznaka(),
                materijal.getKolicinaAmbalaza(),
                materijal.getJedinicaMjereAmbalaza(),
                materijal.getCijenaAmbalaza(),
                materijal.getOpis()});
        }

        updateRowHeights(tablica);

//        tablica = new WrappableTable(tablica, model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblGrupaMaterijal = new javax.swing.JLabel();
        txtGrupaMaterijal = new javax.swing.JTextField();
        lblProizvodac = new javax.swing.JLabel();
        txtProizvodac = new javax.swing.JTextField();
        lblOznaka = new javax.swing.JLabel();
        lblKolicinaAmbalaza = new javax.swing.JLabel();
        lblJedinicaMjereAmbalaza = new javax.swing.JLabel();
        lblCijenaAmbalaza = new javax.swing.JLabel();
        lblOpis = new javax.swing.JLabel();
        txtOznaka = new javax.swing.JTextField();
        txtKolicinaAmbalaza = new javax.swing.JTextField();
        txtJedinicaMjereAmbalaza = new javax.swing.JTextField();
        txtCijenaAmbalaza = new javax.swing.JTextField();
        btnDodaj = new javax.swing.JButton();
        btnPromjeni = new javax.swing.JButton();
        btnObrisi = new javax.swing.JButton();
        txtUvjet = new javax.swing.JTextField();
        btnTrazi = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tarOpis = new javax.swing.JTextArea();
        lblPretraga = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblMaterijal = new javax.swing.JTable();
        lblId = new javax.swing.JLabel();
        txtIdValue = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(850, 450));
        setPreferredSize(new java.awt.Dimension(450, 333));

        lblGrupaMaterijal.setText("Grupa materijala:");

        txtGrupaMaterijal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGrupaMaterijalActionPerformed(evt);
            }
        });

        lblProizvodac.setText("Proizvođač:");

        lblOznaka.setText("Oznaka:");

        lblKolicinaAmbalaza.setText("Pakovanje količina:");

        lblJedinicaMjereAmbalaza.setText("Jedinica mjere:");

        lblCijenaAmbalaza.setText("Cijena pakovanja:");

        lblOpis.setText("Opis:");

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

        txtUvjet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUvjetActionPerformed(evt);
            }
        });

        btnTrazi.setText("Traži");
        btnTrazi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraziActionPerformed(evt);
            }
        });

        tarOpis.setColumns(20);
        tarOpis.setRows(5);
        tarOpis.setWrapStyleWord(true);
        jScrollPane2.setViewportView(tarOpis);
        tarOpis.setLineWrap(true);

        lblPretraga.setText("Pretraga po grupi materijala, proizvođaču i oznaci:");

        jLabel1.setText("dodati povijest cijena za odabrani red");

        jScrollPane3.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jScrollPane3ComponentResized(evt);
            }
        });

        tblMaterijal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "null", "null", "null", "null", "null", "null", "null", "null"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMaterijal.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblMaterijal.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblMaterijal.getTableHeader().setReorderingAllowed(false);
        tblMaterijal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMaterijalMouseClicked(evt);
            }
        });
        tblMaterijal.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                tblMaterijalComponentResized(evt);
            }
        });
        jScrollPane3.setViewportView(tblMaterijal);

        lblId.setText("Rb. (Id):");

        txtIdValue.setEditable(false);
        txtIdValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdValueActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPretraga)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtUvjet, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTrazi)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblGrupaMaterijal)
                            .addComponent(lblProizvodac)
                            .addComponent(lblOznaka)
                            .addComponent(lblKolicinaAmbalaza)
                            .addComponent(lblJedinicaMjereAmbalaza)
                            .addComponent(lblCijenaAmbalaza)
                            .addComponent(lblOpis)
                            .addComponent(lblId))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtIdValue, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGrupaMaterijal, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtProizvodac, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtOznaka, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKolicinaAmbalaza, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtJedinicaMjereAmbalaza, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCijenaAmbalaza, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnDodaj)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPromjeni)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnObrisi)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(41, 41, 41))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lblPretraga)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUvjet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTrazi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblId)
                            .addComponent(txtIdValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblGrupaMaterijal)
                            .addComponent(txtGrupaMaterijal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblProizvodac)
                            .addComponent(txtProizvodac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblOznaka)
                            .addComponent(txtOznaka, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblKolicinaAmbalaza)
                            .addComponent(txtKolicinaAmbalaza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblJedinicaMjereAmbalaza)
                            .addComponent(txtJedinicaMjereAmbalaza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCijenaAmbalaza)
                            .addComponent(txtCijenaAmbalaza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnDodaj)
                                        .addComponent(btnPromjeni))
                                    .addComponent(btnObrisi)))
                            .addComponent(lblOpis))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addGap(0, 20, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDodajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDodajActionPerformed
        entitet = new Materijal();
        spremi();
    }//GEN-LAST:event_btnDodajActionPerformed

    private void btnPromjeniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPromjeniActionPerformed
//        if (lista.getSelectedValue() == null) {
//            JOptionPane.showConfirmDialog(rootPane, "Prvo odaberite stavku");
//        }
        spremi();
    }//GEN-LAST:event_btnPromjeniActionPerformed

    private void btnObrisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObrisiActionPerformed
//        if (lista.getSelectedValue() == null) {
//            JOptionPane.showConfirmDialog(rootPane, "Prvo odaberite stavku");
//        }
        obrisi();
    }//GEN-LAST:event_btnObrisiActionPerformed

    private void txtUvjetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUvjetActionPerformed

    }//GEN-LAST:event_txtUvjetActionPerformed

    private void btnTraziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraziActionPerformed
        rezultati = HibernateUtil.getSession().createQuery("from Materijal a where "
                + " a.obrisan=false and concat(a.grupaMaterijal, ' ', a.proizvodac, ' ',a.oznaka, ' ') like :uvjet").setString("uvjet", "%" + txtUvjet.getText() + "%").list();
        popunjavanjeTablice(tblMaterijal);
    }//GEN-LAST:event_btnTraziActionPerformed

    private void tblMaterijalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMaterijalMouseClicked
        if (evt.getClickCount() == 2) {
            DefaultTableModel model = (DefaultTableModel) tblMaterijal.getModel();
            int row = tblMaterijal.getSelectedRow();
            System.out.println("=== odabrani redak" + row);
            entitet = rezultati.get(tblMaterijal.convertRowIndexToModel(row));
            txtIdValue.setText(model.getValueAt(row, 0).toString());
            txtGrupaMaterijal.setText(model.getValueAt(row, 1).toString());
            txtProizvodac.setText(model.getValueAt(row, 2).toString());
            txtOznaka.setText(model.getValueAt(row, 3).toString());
            txtKolicinaAmbalaza.setText(model.getValueAt(row, 4).toString());
            txtJedinicaMjereAmbalaza.setText(model.getValueAt(row, 5).toString());
            txtCijenaAmbalaza.setText(model.getValueAt(row, 6).toString());
            tarOpis.setWrapStyleWord(true);
            tarOpis.setText(model.getValueAt(row, 7).toString());
            System.out.println(model.getValueAt(row, 7).toString().length());
        } else if (evt.getClickCount() == 1 && evt.isAltDown() == true) {
            tblMaterijal.getSelectionModel().clearSelection();
            entitet = null;
            txtIdValue.setText("");
            txtGrupaMaterijal.setText("");
            txtProizvodac.setText("");
            txtOznaka.setText("");
            txtKolicinaAmbalaza.setText("");
            txtJedinicaMjereAmbalaza.setText("");
            txtCijenaAmbalaza.setText("");
            tarOpis.setText("");

        }
    }//GEN-LAST:event_tblMaterijalMouseClicked

    private void txtIdValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdValueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdValueActionPerformed

    private void txtGrupaMaterijalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGrupaMaterijalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGrupaMaterijalActionPerformed

    private void tblMaterijalComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tblMaterijalComponentResized
        tblMaterijal.setRowHeight(16);
        updateRowHeights(tblMaterijal);
    }//GEN-LAST:event_tblMaterijalComponentResized

    private void jScrollPane3ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jScrollPane3ComponentResized
        System.out.println("-");
        changedSize(tblMaterijal);
    }//GEN-LAST:event_jScrollPane3ComponentResized

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDodaj;
    private javax.swing.JButton btnObrisi;
    private javax.swing.JButton btnPromjeni;
    private javax.swing.JButton btnTrazi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblCijenaAmbalaza;
    private javax.swing.JLabel lblGrupaMaterijal;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblJedinicaMjereAmbalaza;
    private javax.swing.JLabel lblKolicinaAmbalaza;
    private javax.swing.JLabel lblOpis;
    private javax.swing.JLabel lblOznaka;
    private javax.swing.JLabel lblPretraga;
    private javax.swing.JLabel lblProizvodac;
    private javax.swing.JTextArea tarOpis;
    private javax.swing.JTable tblMaterijal;
    private javax.swing.JTextField txtCijenaAmbalaza;
    private javax.swing.JTextField txtGrupaMaterijal;
    private javax.swing.JTextField txtIdValue;
    private javax.swing.JTextField txtJedinicaMjereAmbalaza;
    private javax.swing.JTextField txtKolicinaAmbalaza;
    private javax.swing.JTextField txtOznaka;
    private javax.swing.JTextField txtProizvodac;
    private javax.swing.JTextField txtUvjet;
    // End of variables declaration//GEN-END:variables

}
