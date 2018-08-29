/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.view;

import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import kovacevic.controller.Obrada;
import kovacevic.model.Rad;
import kovacevic.pomocno.HibernateUtil;
import org.hibernate.Session;

/**
 *
 * @author Marko Kovačević
 */
public class FormaRad extends Forma<Rad> {

    private List<Rad> rezultati;

//    PreparedStatement
//    https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
    /**
     * Creates new form FormaRad
     */
    public FormaRad() {

        initComponents();
        setTitle("Rad");
        obrada = new Obrada();
        ucitaj();

    }

    @Override
    protected void ucitaj() {
        Session session = HibernateUtil.getSession();
        session.clear();
        rezultati = HibernateUtil.getSession().createQuery(
                "from Rad a where a.obrisan=false order by grupaRadova asc, kategorijaRad asc, cijena asc").list();

//        ucitavanje();
        popunjavanjeTablice();

    }

//    private void ucitavanje() {
//        DefaultListModel<Rad> m = new DefaultListModel<>();
//        lista.setModel(m);
//        rezultati.forEach((s) -> {
//            m.addElement(s);
//
//        });
//
//    }
    private void popunjavanjeTablice() {

        String[] coulumnName = {"Rb. (Id)", "Grupe radova", "Kategorije radova", "Cijene radova"};
        DefaultTableModel model = (DefaultTableModel) tblRad.getModel();
        model.setRowCount(0);
        model.setColumnIdentifiers(coulumnName);

        tblRad.setModel(model);
        int rb = 0;
        for (Rad rad : rezultati) {
            rb++;
            model.addRow(new Object[]{
                rb + ". (" + rad.getId() + ")",
                rad.getGrupaRadova(),
                rad.getKategorijaRad(),
                rad.getCijena(),});
        }

//                public boolean isCellEditable(int row, int column) {
//        return getModel().isCellEditable(convertRowIndexToModel(row),
//                                         convertColumnIndexToModel(column));
//        rezultati.forEach((Rad r) -> {
//            model.addRow(new Object[]{
//                r.getGrupaRadova(),
//                r.getKategorijaRad(),
//                r.getCijena(),});
//        });
    }

    @Override
    protected void spremi() {

        entitet.setGrupaRadova(txtGrupaRadova.getText());
        entitet.setKategorijaRad(txtKategorijaRad.getText());
        entitet.setCijena(new BigDecimal(txtCijena.getText()));
        super.spremi();
        popunjavanjeTablice();
    }

    private void trazi() {
        if (txtTraziCijena.getText().trim().equals("")) {
            if (txtTraziKategorija.getText().trim().equals("") && txtTraziCijena.getText().trim().equals("")) {
                rezultati = HibernateUtil.getSession().createQuery("from Rad a where "
                        + " a.obrisan=false and a.grupaRadova like :grupaRadova"
                        + " order by grupaRadova asc, kategorijaRad asc, cijena asc")
                        .setParameter("grupaRadova", "%" + txtTratziGrupa.getText() + "%")
                        .list();

                System.out.println("r1:" + rezultati);
            } else if (txtTraziCijena.getText().trim().equals("")) {
                rezultati = HibernateUtil.getSession().createQuery("from Rad a where "
                        + " a.obrisan=false and a.grupaRadova like :grupaRadova and"
                        + " a.kategorijaRad = :kategorijaRad order by grupaRadova asc, kategorijaRad asc, cijena asc")
                        .setParameter("grupaRadova", "%" + txtTratziGrupa.getText() + "%")
                        .setParameter("kategorijaRad", txtTraziKategorija.getText()).list();
                System.out.println("r2:" + rezultati);
            }
        } else {
            if (txtTraziKategorija.getText().trim().equals("")) {
                rezultati = HibernateUtil.getSession().createQuery("from Rad a where "
                        + " a.obrisan=false and a.grupaRadova like :grupaRadova "
                        //                    + "and a.kategorijaRad = :kategorijaRad "
                        + "and a.cijena like concat(:cijena,'%') order by grupaRadova asc, kategorijaRad asc, cijena asc")
                        .setParameter("grupaRadova", "%" + txtTratziGrupa.getText() + "%")
                        .setParameter("cijena", new BigDecimal(txtTraziCijena.getText()))
                        //                    .setParameter("kategorijaRad", txtTraziKategorija.getText())
                        .list();
                System.out.println("r3:" + rezultati);
            } else {
                rezultati = HibernateUtil.getSession().createQuery("from Rad a where "
                        + " a.obrisan=false and a.grupaRadova like :grupaRadova "
                        + "and a.kategorijaRad = :kategorijaRad "
                        + "and a.cijena like concat(:cijena,'%') order by grupaRadova asc, kategorijaRad asc, cijena asc")
                        .setParameter("grupaRadova", "%" + txtTratziGrupa.getText() + "%")
                        .setParameter("cijena", new BigDecimal(txtTraziCijena.getText()))
                        .setParameter("kategorijaRad", txtTraziKategorija.getText())
                        .list();
                System.out.println("r4:" + rezultati);
            }

        }

//        if (!txtTratziGrupa.getText().trim().equals("") && !txtTraziKategorija.getText().trim().equals("") && !txtTraziKategorija.getText().trim().equals("")) {
//        }
//        rezultati = HibernateUtil.getSession().createQuery("from Rad a where "
//                + " a.obrisan=false and a.grupaRadova like :grupaRadova and a.kategorijaRad = :kategorijaRad "
//                + "(and a.cijena like :cijena")
//                .setParameter("grupaRadova", "%" + txtTratziGrupa.getText() + "%")
//                .setParameter("cijena", "%" + txtTraziCijena.getText() + "%")
//                .setParameter("kategorijaRad", txtTraziKategorija.getText()).list();
//        popunjavanjeTablice();
//        System.out.println("r1:" + rezultati);
        popunjavanjeTablice();
    }

    private boolean provjeraDuplogUnosaRed() {
//        select cijena, grupaRadova, kategorijaRad,  count(*) from zavrsni.rad where obrisan = 0 group by cijena, grupaRadova, kategorijaRad having count(*) > 1 ;
        rezultati = HibernateUtil.getSession().createQuery(
                "from Rad a where a.obrisan=false order by grupaRadova asc, kategorijaRad asc, cijena asc").list();
        for (Rad rad : rezultati) {
            if (rad.getGrupaRadova().matches(txtGrupaRadova.getText()) && rad.getKategorijaRad().matches(txtKategorijaRad.getText()) && rad.getCijena().equals(new BigDecimal(txtCijena.getText())) && rad.isObrisan() == false) {
                System.out.println("provjeraDuplogUnosaRed === postoji unešen podatak");
                return true;
            }

        }
        return false;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblGrupaRadova = new javax.swing.JLabel();
        lblKategorijaRada = new javax.swing.JLabel();
        lblCijena = new javax.swing.JLabel();
        txtGrupaRadova = new javax.swing.JTextField();
        txtKategorijaRad = new javax.swing.JTextField();
        txtCijena = new javax.swing.JTextField();
        btnDodaj = new javax.swing.JButton();
        btnPromjeni = new javax.swing.JButton();
        btnObrisi = new javax.swing.JButton();
        txtTratziGrupa = new javax.swing.JTextField();
        lblPretraga = new javax.swing.JLabel();
        txtTraziKategorija = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblRad = new javax.swing.JTable();
        txtTraziCijena = new javax.swing.JTextField();
        lblId = new javax.swing.JLabel();
        txtIdValue = new javax.swing.JTextField();
        btnResetirajTrazilicu = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnUpute = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(730, 400));
        setSize(new java.awt.Dimension(0, 0));

        lblGrupaRadova.setText("Grupa radova:");

        lblKategorijaRada.setText("Kategorija rada:");

        lblCijena.setText("Cijena rada:");

        txtGrupaRadova.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGrupaRadovaActionPerformed(evt);
            }
        });

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

        txtTratziGrupa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTratziGrupaActionPerformed(evt);
            }
        });
        txtTratziGrupa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTratziGrupaKeyPressed(evt);
            }
        });

        lblPretraga.setText("Pretraga po grupi, kategoriji i cijeni:");

        txtTraziKategorija.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTraziKategorijaActionPerformed(evt);
            }
        });
        txtTraziKategorija.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTraziKategorijaKeyPressed(evt);
            }
        });

        tblRad.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "null", "null", "null", "null"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRad.setToolTipText("");
        tblRad.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblRad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRadMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblRad);

        txtTraziCijena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTraziCijenaActionPerformed(evt);
            }
        });
        txtTraziCijena.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTraziCijenaKeyPressed(evt);
            }
        });

        lblId.setText("Rb. (Id):");

        txtIdValue.setEditable(false);
        txtIdValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdValueActionPerformed(evt);
            }
        });

        btnResetirajTrazilicu.setText("Reset");
        btnResetirajTrazilicu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetirajTrazilicuActionPerformed(evt);
            }
        });

        jLabel1.setText("dodati povijest cijena za odabrani red");

        btnUpute.setText("?");
        btnUpute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUputeActionPerformed(evt);
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
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblGrupaRadova)
                                    .addGap(18, 18, 18))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblKategorijaRada)
                                        .addComponent(lblCijena))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblId)
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtGrupaRadova, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKategorijaRad, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCijena, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnDodaj)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPromjeni)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnObrisi))
                            .addComponent(txtIdValue, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtTratziGrupa, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTraziKategorija, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTraziCijena, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnResetirajTrazilicu)
                        .addGap(0, 350, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblPretraga)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnUpute)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPretraga)
                    .addComponent(btnUpute))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTratziGrupa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTraziKategorija, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTraziCijena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnResetirajTrazilicu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblId)
                            .addComponent(txtIdValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGrupaRadova, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGrupaRadova))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtKategorijaRad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblKategorijaRada))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCijena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCijena))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDodaj)
                            .addComponent(btnPromjeni)
                            .addComponent(btnObrisi))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDodajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDodajActionPerformed
        if (txtGrupaRadova.getText().trim().equals("") || txtKategorijaRad.getText().trim().equals("")
                || txtCijena.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Nisu unešeni sljedeći podatci:"
                    + (txtGrupaRadova.getText().trim().equals("") ? "\n" + lblGrupaRadova.getText().replace(":", "") : "")
                    + (txtKategorijaRad.getText().trim().equals("") ? "\n" + lblKategorijaRada.getText().replace(":", "") : "")
                    + (txtCijena.getText().trim().equals("") ? "\n" + lblCijena.getText().replace(":", "") : ""), getTitle() + " - Dodavanje novog", HEIGHT);

        } else if (provjeraDuplogUnosaRed() == true) {
            JOptionPane.showMessageDialog(rootPane, "Postoji unos u tablici", getTitle() + " - Dodavanje novog", HEIGHT);
        } else {
            entitet = new Rad();
            spremi();
            int row = 0;
            for (int i = 0; i < rezultati.size(); i++) {
                if (tblRad.getModel().getValueAt(i, 0).toString().contains("(" + entitet.getId().toString() + ")")) {
                    txtIdValue.setText(tblRad.getModel().getValueAt(i, 0).toString());
//                    entitet = rezultati.get(tblRad.convertRowIndexToModel(i));
                    row = i;
                    System.out.println("=== red " + (i + 1) + "=== rowVarijabla " + (row + 1));
                    break;
                }

            }
            trazi();
            tblRad.setRowSelectionInterval(row, row);
        }


    }//GEN-LAST:event_btnDodajActionPerformed

//            DefaultTableModel model = (DefaultTableModel) tblRad.getModel();
//        model.setRowCount(0);
//        model.setColumnIdentifiers(coulumnName);
//
//        tblRad.setModel(model);
//        int rb = 0;
//        for (Rad rad : rezultati) {
//            rb++;
//            model.addRow(new Object[]{
//                rb + ". (" + rad.getId() + ")",
//                rad.getGrupaRadova(),
//                rad.getKategorijaRad(),
//                rad.getCijena(),});
//        }

    private void btnPromjeniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPromjeniActionPerformed

        int i = tblRad.getSelectedRow();
        if (i >= 0 && (entitet != null && tblRad.getSelectedRow() != -1)
                && !txtGrupaRadova.getText().trim().equals("")
                && !txtKategorijaRad.getText().trim().equals("")
                && !txtCijena.getText().trim().equals("")) {
            entitet = rezultati.get(tblRad.convertRowIndexToModel(i));
            DefaultTableModel model = (DefaultTableModel) tblRad.getModel();
            model.setValueAt(txtGrupaRadova.getText(), i, 1);
            model.setValueAt(txtKategorijaRad.getText(), i, 2);
            model.setValueAt(txtCijena.getText(), i, 3);
            spremi();
            entitet = null;
            entitet = rezultati.get(tblRad.convertRowIndexToModel(i));
            tblRad.setRowSelectionInterval(i, i);
            trazi();
        } else if (provjeraDuplogUnosaRed() == true) {
            JOptionPane.showMessageDialog(rootPane, "Vrijednosti nisu izmjenjene", getTitle() + " - Promjena postojećeg", HEIGHT);
        } else {

            JOptionPane.showMessageDialog(rootPane, "Odaberite stavku vidljivu unutar tablice",
                    getTitle() + " - Promjena postojećeg", HEIGHT);
        }


    }//GEN-LAST:event_btnPromjeniActionPerformed

    private void btnObrisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObrisiActionPerformed
        System.out.println("btnObrisiActionPerformed()" + entitet);
        if (entitet == null || tblRad.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Odaberite stavku vidljivu unutar tablice", getTitle() + " - Obriši", HEIGHT);
        } else {

            int i = tblRad.getSelectedRow();
            DefaultTableModel model = (DefaultTableModel) tblRad.getModel();
//            entitet = rezultati.get(tblRad.convertRowIndexToModel(i));

            Object[] options = {"Da", "Ne"};
            int reply = JOptionPane.showOptionDialog(rootPane, "Sigurno želite obrisati stavku? \n"
                    + model.getValueAt(i, 0).toString() + ", " + model.getValueAt(i, 1).toString()
                    + ", " + model.getValueAt(i, 2).toString() + ", "
                    + model.getValueAt(i, 3).toString(), getTitle()
                    + " - Obriši", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
            if (reply == JOptionPane.YES_OPTION) {
                obrisi();
                entitet = null;
                txtIdValue.setText("");
                txtGrupaRadova.setText("");
                txtKategorijaRad.setText("");
                txtCijena.setText("");
                tblRad.getSelectionModel().clearSelection();
                trazi();

            }

        }
    }//GEN-LAST:event_btnObrisiActionPerformed

    private void txtTratziGrupaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTratziGrupaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTratziGrupaActionPerformed

    private void txtGrupaRadovaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGrupaRadovaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGrupaRadovaActionPerformed

    private void txtTraziKategorijaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTraziKategorijaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTraziKategorijaActionPerformed

    private void tblRadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRadMouseClicked
        if (evt.getClickCount() == 2) {
            int row = tblRad.getSelectedRow();
            System.out.println(row + 1);
            System.out.println("=== dvoklik unutar tablice ===");
            DefaultTableModel model = (DefaultTableModel) tblRad.getModel();
            entitet = rezultati.get(tblRad.convertRowIndexToModel(row));
            System.out.println(entitet.getId() + " " + entitet);
            txtIdValue.setText(model.getValueAt(row, 0).toString());
            txtGrupaRadova.setText(model.getValueAt(row, 1).toString());
            txtKategorijaRad.setText(model.getValueAt(row, 2).toString());
            txtCijena.setText(model.getValueAt(row, 3).toString());

            System.out.println("kovacevic.view.FormaRad.tblRadMouseClicked()" + tblRad.getSelectionBackground());
            
        } else if (evt.getClickCount() == 1 && evt.isAltDown() == true) {
            System.out.println("=== jedan klik i stisnut je alt ===");
            tblRad.getSelectionModel().clearSelection();
            entitet = null;
            txtIdValue.setText("");
            txtGrupaRadova.setText("");
            txtKategorijaRad.setText("");
            txtCijena.setText("");
        }

    }//GEN-LAST:event_tblRadMouseClicked

    private void txtTratziGrupaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTratziGrupaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            trazi();
        }
    }//GEN-LAST:event_txtTratziGrupaKeyPressed

    private void txtTraziKategorijaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTraziKategorijaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            trazi();
        }
    }//GEN-LAST:event_txtTraziKategorijaKeyPressed

    private void txtTraziCijenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTraziCijenaActionPerformed

    }//GEN-LAST:event_txtTraziCijenaActionPerformed

    private void txtTraziCijenaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTraziCijenaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            trazi();
        }
    }//GEN-LAST:event_txtTraziCijenaKeyPressed

    private void txtIdValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdValueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdValueActionPerformed

    private void btnResetirajTrazilicuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetirajTrazilicuActionPerformed
        txtTratziGrupa.setText("");
        txtTraziCijena.setText("");
        txtTraziKategorija.setText("");

        ucitaj();

    }//GEN-LAST:event_btnResetirajTrazilicuActionPerformed

    private void btnUputeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUputeActionPerformed
        JOptionPane.showMessageDialog(rootPane,
                "Pretraživanje:" + "\n"
                + "- izvodi se tipkom enter u jednom od tri polja" + "\n"
                + "- tipka " + btnResetirajTrazilicu.getText() + " briše vrijesnosti unešene u polja i prikazuje sve rezultate" + "\n\n"
                + "Tablica sa podatcima:" + "\n"
                + "- jednim klikom miša označava se odabrani red" + "\n"
                + "- dvostrukim klikom odabiru se vrijednosti odabranog reda i upisuju u polja kako bi se izvršile radnje promjene ili brisanja" + "\n"
                + "- držanjem tipke alt i lijevim klikom odznačava se red u tablici i ispraznjuju se vrijednosti polja za radnje dodavanja, promjene i brisanja",
                getTitle() + " - Upute za korištenje", JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_btnUputeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDodaj;
    private javax.swing.JButton btnObrisi;
    private javax.swing.JButton btnPromjeni;
    private javax.swing.JButton btnResetirajTrazilicu;
    private javax.swing.JButton btnUpute;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblCijena;
    private javax.swing.JLabel lblGrupaRadova;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblKategorijaRada;
    private javax.swing.JLabel lblPretraga;
    private javax.swing.JTable tblRad;
    private javax.swing.JTextField txtCijena;
    private javax.swing.JTextField txtGrupaRadova;
    private javax.swing.JTextField txtIdValue;
    private javax.swing.JTextField txtKategorijaRad;
    private javax.swing.JTextField txtTratziGrupa;
    private javax.swing.JTextField txtTraziCijena;
    private javax.swing.JTextField txtTraziKategorija;
    // End of variables declaration//GEN-END:variables
}
