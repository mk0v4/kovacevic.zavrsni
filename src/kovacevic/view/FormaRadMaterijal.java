/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.view;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import kovacevic.controller.ObradaMaterijal;
import kovacevic.controller.ObradaRad;
import kovacevic.model.Materijal;
import kovacevic.model.Rad;
import kovacevic.pomocno.HibernateUtil;
import kovacevic.pomocno.PorukaIznimke;
import kovacevic.pomocno.WordWrapCellRenderer;
import org.hibernate.Session;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Marko Kovačević
 */
public class FormaRadMaterijal extends JFrame {

    private ObradaRad obradaRad;
    private Rad rad;

    private ObradaMaterijal obradaMaterijal;
    private Materijal materijal;

    private List<Rad> rezultatiRad;
    private List<Materijal> rezultatiMaterijal;

    private boolean dodaj = false;
    private boolean promijeni = false;
    private boolean obrisi = false;

    /**
     * Creates new form FormaRadMaterijal
     */
    public FormaRadMaterijal() {
        obradaRad = new ObradaRad();
        obradaMaterijal = new ObradaMaterijal();

        initComponents();
        setTitle("Rad / Materijal");

        AutoCompleteDecorator.decorate(cmbGrupeRadova);
        AutoCompleteDecorator.decorate(cmbGrupeMaterijala);

        popuniGrupeRadova();
        popuniGrupeMaterijala();

        ucitajRad();
        ucitajMaterijal();
        jTabbedPane1.setEnabledAt(2, false);
    }

    protected void ucitajRad() {
        rezultatiRad = obradaRad.getListaRad(rad);
        popunjavanjeTabliceRad();
    }

    protected void ucitajMaterijal() {
        Session session = HibernateUtil.getSession();
        session.clear();
        rezultatiMaterijal = HibernateUtil.getSession().createQuery("from Materijal a "
                + "where a.obrisan=false order by grupaMaterijal").list();
        popunjavanjeTabliceMaterijal(tblMaterijal);
    }

    private void popunjavanjeTabliceRad() {
        String[] coulumnName = {"Rb. (Id)", "Grupe radova", "Kategorije radova", "Cijene radova"};
        DefaultTableModel model = (DefaultTableModel) tblRad.getModel();
        model.setRowCount(0);
        model.setColumnIdentifiers(coulumnName);

        tblRad.setModel(model);
        int rb = 0;
        for (Rad rad : rezultatiRad) {
            rb++;
            model.addRow(new Object[]{
                rb + ". (" + rad.getId() + ")",
                rad.getGrupaRadova(),
                rad.getKategorijaRad(),
                rad.getCijena(),});
        }
    }

    private void popunjavanjeTabliceMaterijal(JTable tablica) {
        String[] coulumnName = {"Rb. (Id)", "Grupe materijala", "Proizvođač",
            "Oznaka", "Pakovanje količina", "Jedinica mjere", "Cijena pakovanja", "Opis"};
        DefaultTableModel model = (DefaultTableModel) tablica.getModel();
        tablica.setDefaultRenderer(Object.class, new WordWrapCellRenderer());
        model.setRowCount(0);
        model.setColumnIdentifiers(coulumnName);
        int rb = 0;
        for (Materijal materijal : rezultatiMaterijal) {
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
        changedSize(tablica);
        updateRowHeights(tablica);
    }

    protected void dpoRad() {
        String nfePoruka = "";
        String dodatakNaslovu = "";
        rad.setGrupaRadova(txtRadGrupaRadova.getText());
        rad.setKategorijaRad(txtRadKategorijaRad.getText());
        if (txtRadCijena.getText().trim().equals("")) {
            rad.setCijena(null);
        } else {
            try {
                rad.setCijena(new BigDecimal(txtRadCijena.getText()));
            } catch (NumberFormatException nfe) {
                nfePoruka = "Unešena vrijednost polja " + lblRadCijena.getText().replace(":", "") + " ne odgovara numeričkom unosu.";
            }
        }
        try {
            if (dodaj == true) {
                dodatakNaslovu = " - Dodavanje novog";
                obradaRad.spremi(rad);
            }
            if (promijeni == true) {
                dodatakNaslovu = " - Promijena";
                obradaRad.promijeni(rad);
            }
            if (obrisi == true) {
                dodatakNaslovu = " - Brisanje";
                obradaRad.obrisi(rad);
            }
        } catch (PorukaIznimke pi) {
            JOptionPane.showMessageDialog(rootPane, (nfePoruka.trim().equals("") ? "" : nfePoruka + "\n") + pi.getOpis() + pi.getGreska(),
                    getTitle() + dodatakNaslovu, HEIGHT);
            if (pi.getGreska().contains(ObradaRad.GRUPA_RADOVA)) {
                txtRadGrupaRadova.requestFocus();
            } else if (pi.getGreska().contains(ObradaRad.KATEGORIJA_RAD)) {
                txtRadKategorijaRad.requestFocus();
            } else if (pi.getGreska().contains(ObradaRad.CIJENA_RAD)) {
                txtRadCijena.requestFocus();
            }
//            Logger.getLogger(FormaRadMaterijal.class.getName()).log(Level.SEVERE, null, e.getMessage());
            return;
        }
        popuniGrupeRadova();
        ucitajRad();
    }

    protected void spremiMaterijal() throws PorukaIznimke {
        materijal.setGrupaMaterijal(txtMaterijalGrupaMaterijal.getText());
        materijal.setProizvodac(txtMaterijalProizvodac.getText());
        materijal.setOznaka(txtMaterijalOznaka.getText());
        materijal.setKolicinaAmbalaza(new BigDecimal(txtMaterijalKolicinaAmbalaza.getText()));
        materijal.setJedinicaMjereAmbalaza(txtMaterijalJedinicaMjereAmbalaza.getText());
        materijal.setCijenaAmbalaza(new BigDecimal(txtMaterijalCijenaAmbalaza.getText()));
        materijal.setOpis(tarMaterijalOpis.getText());
        obradaMaterijal.spremi(materijal);
    }

    private void traziRad() {
        String grupaRadova = null;
        String cond = null;
        String p = null;
        if (!cmbGrupeRadova.getSelectedItem().equals("Nije odabrano")) {
            grupaRadova = (String) cmbGrupeRadova.getSelectedItem();
            cond = "=";
            p = "";

        } else {
            grupaRadova = "";
            cond = "like";
            p = "%";

        }
        try {

            if (txtRadTraziCijena.getText().trim().equals("")) {
                if (txtRadTraziKategorija.getText().trim().equals("") && txtRadTraziCijena.getText().trim().equals("")) {

                    rezultatiRad = HibernateUtil.getSession().createQuery("from Rad a where "
                            + " a.obrisan=false and a.grupaRadova " + cond + " :grupaRadova"
                            + " order by grupaRadova asc, kategorijaRad asc, cijena asc")
                            .setParameter("grupaRadova", p + grupaRadova + p)
                            .list();

//                System.out.println("r1:" + rezultati);
                } else if (txtRadTraziCijena.getText().trim().equals("")) {
                    rezultatiRad = HibernateUtil.getSession().createQuery("from Rad a where "
                            + " a.obrisan=false and a.grupaRadova " + cond + " :grupaRadova and"
                            + " a.kategorijaRad = :kategorijaRad order by grupaRadova asc, kategorijaRad asc, cijena asc")
                            .setParameter("grupaRadova", p + grupaRadova + p)
                            .setParameter("kategorijaRad", txtRadTraziKategorija.getText()).list();
//                System.out.println("r2:" + rezultati);
                }
            } else {
                if (txtRadTraziKategorija.getText().trim().equals("")) {
                    rezultatiRad = HibernateUtil.getSession().createQuery("from Rad a where "
                            + " a.obrisan=false and a.grupaRadova " + cond + " :grupaRadova "
                            //                    + "and a.kategorijaRad = :kategorijaRad "
                            + "and a.cijena like concat(:cijena,'%') order by grupaRadova asc, kategorijaRad asc, cijena asc")
                            .setParameter("grupaRadova", p + grupaRadova + p)
                            .setParameter("cijena", new BigDecimal(txtRadTraziCijena.getText()))
                            //                    .setParameter("kategorijaRad", txtTraziKategorija.getText())
                            .list();
//                System.out.println("r3:" + rezultati);
                } else {
                    rezultatiRad = HibernateUtil.getSession().createQuery("from Rad a where "
                            + " a.obrisan=false and a.grupaRadova " + cond + " :grupaRadova "
                            + "and a.kategorijaRad = :kategorijaRad "
                            + "and a.cijena like concat(:cijena,'%') order by grupaRadova asc, kategorijaRad asc, cijena asc")
                            .setParameter("grupaRadova", p + grupaRadova + p)
                            .setParameter("cijena", new BigDecimal(txtRadTraziCijena.getText()))
                            .setParameter("kategorijaRad", txtRadTraziKategorija.getText())
                            .list();
//                System.out.println("r4:" + rezultati);
                }

            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(rootPane, "Unešena vrijednost nije broj", getTitle() + " - Traži", HEIGHT);
            txtRadTraziCijena.setText("");
            return;
        }

        popunjavanjeTabliceRad();
    }

    private void traziMaterijal() {

        String grupaMaterijala = null;
        String cond = null;
        String p = null;
        if (!cmbGrupeMaterijala.getSelectedItem().equals("Nije odabrano")) {
            grupaMaterijala = (String) cmbGrupeMaterijala.getSelectedItem();
            cond = "=";
            p = "";

        } else {
            grupaMaterijala = "";
            cond = "like";
            p = "%";
        }

        rezultatiMaterijal = HibernateUtil.getSession().createQuery("from Materijal a where "
                + " a.obrisan=false and a.grupaMaterijal " + cond + " :grupaMaterijal and a.proizvodac like :proizvodac and a.oznaka like :oznaka "
                + " order by grupaMaterijal asc, cijenaAmbalaza asc").setParameter("grupaMaterijal", p + grupaMaterijala + p).
                setParameter("proizvodac", "%" + txtMaterijalTraziProizvodac.getText() + "%").setParameter("oznaka", "%" + txtMaterijalTraziOznaka.getText() + "%").list();

        popunjavanjeTabliceMaterijal(tblMaterijal);

//rezultati = HibernateUtil.getSession().createQuery("from Materijal a where "
//                + " a.obrisan=false and a.grupaMaterijal =:grupaMaterijal and a.proizvodac =:proizvodac and a.oznaka =:oznaka"
//                + " order by grupaMaterijal asc, cijenaAmbalaza asc").setParameter("grupaMaterijal", cmbGrupeMaterijala.getSelectedItem()).setParameter("proizvodac", "")
//                .setParameter("oznaka", "").list();
//
//        popunjavanjeTablice(tblMaterijal);
    }

    private void popuniGrupeRadova() {
        DefaultComboBoxModel<String> m = new DefaultComboBoxModel<>();
        cmbGrupeRadova.setModel(m);
        List<String> rad = HibernateUtil.getSession().
                createQuery("select distinct a.grupaRadova from Rad a where "
                        + "a.obrisan=false  ").list();

        m.addElement("Nije odabrano");
        for (String r : rad) {

            m.addElement(r);
        }
    }

    private void updateRowHeights(JTable table) {

        int startRowHeight = table.getRowHeight();
        BigDecimal bdStartRowHeight = new BigDecimal(startRowHeight);

        int h = 0;
        for (int row = 0; row < table.getRowCount(); row++) {
            int actualRowHeight = table.getRowHeight(row);
            h = actualRowHeight + 3;
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
                    h = rowHeightColumn.intValue() + 3;
                }
            }

            table.setRowHeight(row, h);
//            System.out.println("=== row " + row + " h " + h);

        }

    }

    public void changedSize(JTable table) {

//           System.out.println("jScrollPane3.getViewport().getWidth() "+jScrollPane3.getViewport().getWidth());
//           System.out.println("table.getParent().getSize().getWidth() "+table.getParent().getSize().getWidth());
//           System.out.println("jScrollPane3.getVerticalScrollBar().isShowing() " + jScrollPane3.getVerticalScrollBar().isShowing());
        for (int i = 0; i < table.getColumnCount(); i++) {

            table.getColumnModel().getColumn(i).setPreferredWidth((int) jScrollPaneMaterijal.getViewport().getWidth() / table.getColumnCount());

        }

    }

    private void popuniGrupeMaterijala() {
        DefaultComboBoxModel<String> m = new DefaultComboBoxModel<>();
        cmbGrupeMaterijala.setModel(m);
        List<String> materijal = HibernateUtil.getSession().
                createQuery("select distinct a.grupaMaterijal from Materijal a where "
                        + "a.obrisan=false  ").list();

        m.addElement("Nije odabrano");
        for (String s : materijal) {

            m.addElement(s);
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelRad = new javax.swing.JPanel();
        lblRadGrupaRadova = new javax.swing.JLabel();
        lblRadKategorijaRada = new javax.swing.JLabel();
        lblRadCijena = new javax.swing.JLabel();
        txtRadGrupaRadova = new javax.swing.JTextField();
        txtRadKategorijaRad = new javax.swing.JTextField();
        txtRadCijena = new javax.swing.JTextField();
        btnRadDodaj = new javax.swing.JButton();
        btnRadPromjeni = new javax.swing.JButton();
        btnRadObrisi = new javax.swing.JButton();
        lblRadTraziGrupaRadova = new javax.swing.JLabel();
        txtRadTraziKategorija = new javax.swing.JTextField();
        jScrollPaneRad = new javax.swing.JScrollPane();
        tblRad = new javax.swing.JTable();
        txtRadTraziCijena = new javax.swing.JTextField();
        lblRadId = new javax.swing.JLabel();
        txtRadIdValue = new javax.swing.JTextField();
        btnRadResetirajTrazilicu = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnRadUpute = new javax.swing.JButton();
        cmbGrupeRadova = new javax.swing.JComboBox<>();
        lblRadTraziKategorija = new javax.swing.JLabel();
        lblRadTraziCijena = new javax.swing.JLabel();
        jPanelMaterijal = new javax.swing.JPanel();
        btnMaterijalUpute = new javax.swing.JButton();
        lblMaterijalGrupaMaterijal = new javax.swing.JLabel();
        txtMaterijalGrupaMaterijal = new javax.swing.JTextField();
        lblMaterijalProizvodac = new javax.swing.JLabel();
        txtMaterijalProizvodac = new javax.swing.JTextField();
        lbMaterijallOznaka = new javax.swing.JLabel();
        lblMaterijalKolicinaAmbalaza = new javax.swing.JLabel();
        lblMaterijalJedinicaMjereAmbalaza = new javax.swing.JLabel();
        lblMaterijalCijenaAmbalaza = new javax.swing.JLabel();
        lblMaterijalOpis = new javax.swing.JLabel();
        txtMaterijalOznaka = new javax.swing.JTextField();
        txtMaterijalKolicinaAmbalaza = new javax.swing.JTextField();
        txtMaterijalJedinicaMjereAmbalaza = new javax.swing.JTextField();
        txtMaterijalCijenaAmbalaza = new javax.swing.JTextField();
        btnMaterijalDodaj = new javax.swing.JButton();
        btnMaterijalPromjeni = new javax.swing.JButton();
        btnMaterijalObrisi = new javax.swing.JButton();
        txtMaterijalTraziOznaka = new javax.swing.JTextField();
        btnMaterijalResetirajTrazilicu = new javax.swing.JButton();
        jScrollPaneMaterijalOpsi = new javax.swing.JScrollPane();
        tarMaterijalOpis = new javax.swing.JTextArea();
        lblMaterijalTraziGrupaMaterijal = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPaneMaterijal = new javax.swing.JScrollPane();
        tblMaterijal = new javax.swing.JTable();
        lblMaterijalId = new javax.swing.JLabel();
        txtMaterijalIdValue = new javax.swing.JTextField();
        cmbGrupeMaterijala = new javax.swing.JComboBox<>();
        txtMaterijalTraziProizvodac = new javax.swing.JTextField();
        lblMaterijalTraziProizvodac = new javax.swing.JLabel();
        lblMaterijalTraziOznaka = new javax.swing.JLabel();
        jPanelPrebacivanje = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(860, 500));

        jTabbedPane1.setMinimumSize(new java.awt.Dimension(850, 460));

        lblRadGrupaRadova.setText("Grupa radova:");

        lblRadKategorijaRada.setText("Kategorija rada:");

        lblRadCijena.setText("Cijena rada:");

        txtRadGrupaRadova.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRadGrupaRadovaActionPerformed(evt);
            }
        });

        btnRadDodaj.setText("Dodaj");
        btnRadDodaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRadDodajActionPerformed(evt);
            }
        });

        btnRadPromjeni.setText("Promijeni");
        btnRadPromjeni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRadPromjeniActionPerformed(evt);
            }
        });

        btnRadObrisi.setText("Obriši");
        btnRadObrisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRadObrisiActionPerformed(evt);
            }
        });

        lblRadTraziGrupaRadova.setText("grupa radova:");

        txtRadTraziKategorija.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRadTraziKategorijaActionPerformed(evt);
            }
        });
        txtRadTraziKategorija.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRadTraziKategorijaKeyPressed(evt);
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
        jScrollPaneRad.setViewportView(tblRad);

        txtRadTraziCijena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRadTraziCijenaActionPerformed(evt);
            }
        });
        txtRadTraziCijena.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRadTraziCijenaKeyPressed(evt);
            }
        });

        lblRadId.setText("Rb. (Id):");

        txtRadIdValue.setEditable(false);
        txtRadIdValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRadIdValueActionPerformed(evt);
            }
        });

        btnRadResetirajTrazilicu.setText("Reset");
        btnRadResetirajTrazilicu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRadResetirajTrazilicuActionPerformed(evt);
            }
        });

        jLabel2.setText("dodati povijest cijena za odabrani red");

        btnRadUpute.setText("?");
        btnRadUpute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRadUputeActionPerformed(evt);
            }
        });

        cmbGrupeRadova.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbGrupeRadovaActionPerformed(evt);
            }
        });

        lblRadTraziKategorija.setText("kategorija:");

        lblRadTraziCijena.setText("cijena:");

        javax.swing.GroupLayout jPanelRadLayout = new javax.swing.GroupLayout(jPanelRad);
        jPanelRad.setLayout(jPanelRadLayout);
        jPanelRadLayout.setHorizontalGroup(
            jPanelRadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRadLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelRadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRadLayout.createSequentialGroup()
                        .addGroup(jPanelRadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbGrupeRadova, 0, 132, Short.MAX_VALUE)
                            .addComponent(lblRadTraziGrupaRadova))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelRadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelRadLayout.createSequentialGroup()
                                .addComponent(txtRadTraziKategorija)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanelRadLayout.createSequentialGroup()
                                .addComponent(lblRadTraziKategorija)
                                .addGap(81, 81, 81)))
                        .addGroup(jPanelRadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelRadLayout.createSequentialGroup()
                                .addComponent(lblRadTraziCijena)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtRadTraziCijena, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRadResetirajTrazilicu)
                        .addGap(18, 18, 18))
                    .addGroup(jPanelRadLayout.createSequentialGroup()
                        .addComponent(jScrollPaneRad)
                        .addGap(18, 18, 18)))
                .addGroup(jPanelRadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRadUpute, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelRadLayout.createSequentialGroup()
                        .addGroup(jPanelRadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelRadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanelRadLayout.createSequentialGroup()
                                    .addComponent(lblRadGrupaRadova)
                                    .addGap(18, 18, 18))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelRadLayout.createSequentialGroup()
                                    .addGroup(jPanelRadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblRadKategorijaRada)
                                        .addComponent(lblRadCijena))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                            .addGroup(jPanelRadLayout.createSequentialGroup()
                                .addComponent(lblRadId)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanelRadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtRadGrupaRadova, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRadKategorijaRad, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRadCijena, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelRadLayout.createSequentialGroup()
                                .addComponent(btnRadDodaj)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRadPromjeni)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnRadObrisi))
                            .addComponent(txtRadIdValue, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanelRadLayout.setVerticalGroup(
            jPanelRadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRadLayout.createSequentialGroup()
                .addGroup(jPanelRadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRadLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanelRadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblRadTraziKategorija)
                            .addComponent(lblRadTraziCijena)
                            .addComponent(lblRadTraziGrupaRadova))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelRadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtRadTraziKategorija, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRadTraziCijena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRadResetirajTrazilicu)
                            .addComponent(cmbGrupeRadova, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelRadLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnRadUpute)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelRadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRadLayout.createSequentialGroup()
                        .addGroup(jPanelRadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblRadId)
                            .addComponent(txtRadIdValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelRadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtRadGrupaRadova, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblRadGrupaRadova))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelRadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtRadKategorijaRad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblRadKategorijaRada))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelRadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtRadCijena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblRadCijena))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelRadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRadDodaj)
                            .addComponent(btnRadPromjeni)
                            .addComponent(btnRadObrisi))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(0, 213, Short.MAX_VALUE))
                    .addGroup(jPanelRadLayout.createSequentialGroup()
                        .addComponent(jScrollPaneRad, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jTabbedPane1.addTab("Rad", jPanelRad);

        btnMaterijalUpute.setText("?");
        btnMaterijalUpute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaterijalUputeActionPerformed(evt);
            }
        });

        lblMaterijalGrupaMaterijal.setText("Grupa materijala:");

        txtMaterijalGrupaMaterijal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaterijalGrupaMaterijalActionPerformed(evt);
            }
        });

        lblMaterijalProizvodac.setText("Proizvođač:");

        lbMaterijallOznaka.setText("Oznaka:");

        lblMaterijalKolicinaAmbalaza.setText("Pakovanje količina:");

        lblMaterijalJedinicaMjereAmbalaza.setText("Jedinica mjere:");

        lblMaterijalCijenaAmbalaza.setText("Cijena pakovanja:");

        lblMaterijalOpis.setText("Opis:");

        btnMaterijalDodaj.setText("Dodaj");
        btnMaterijalDodaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaterijalDodajActionPerformed(evt);
            }
        });

        btnMaterijalPromjeni.setText("Promjeni");
        btnMaterijalPromjeni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaterijalPromjeniActionPerformed(evt);
            }
        });

        btnMaterijalObrisi.setText("Obriši");
        btnMaterijalObrisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaterijalObrisiActionPerformed(evt);
            }
        });

        txtMaterijalTraziOznaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaterijalTraziOznakaActionPerformed(evt);
            }
        });
        txtMaterijalTraziOznaka.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMaterijalTraziOznakaKeyPressed(evt);
            }
        });

        btnMaterijalResetirajTrazilicu.setText("Reset");
        btnMaterijalResetirajTrazilicu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaterijalResetirajTrazilicuActionPerformed(evt);
            }
        });

        tarMaterijalOpis.setColumns(20);
        tarMaterijalOpis.setRows(5);
        tarMaterijalOpis.setWrapStyleWord(true);
        jScrollPaneMaterijalOpsi.setViewportView(tarMaterijalOpis);
        tarMaterijalOpis.setLineWrap(true);

        lblMaterijalTraziGrupaMaterijal.setText("grupa materijala:");

        jLabel1.setText("dodati povijest cijena za odabrani red");

        jScrollPaneMaterijal.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jScrollPaneMaterijalComponentResized(evt);
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
        jScrollPaneMaterijal.setViewportView(tblMaterijal);

        lblMaterijalId.setText("Rb. (Id):");

        txtMaterijalIdValue.setEditable(false);
        txtMaterijalIdValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaterijalIdValueActionPerformed(evt);
            }
        });

        cmbGrupeMaterijala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbGrupeMaterijalaActionPerformed(evt);
            }
        });

        txtMaterijalTraziProizvodac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaterijalTraziProizvodacActionPerformed(evt);
            }
        });
        txtMaterijalTraziProizvodac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMaterijalTraziProizvodacKeyPressed(evt);
            }
        });

        lblMaterijalTraziProizvodac.setText(" proizvođači:");

        lblMaterijalTraziOznaka.setText("oznaka:");

        javax.swing.GroupLayout jPanelMaterijalLayout = new javax.swing.GroupLayout(jPanelMaterijal);
        jPanelMaterijal.setLayout(jPanelMaterijalLayout);
        jPanelMaterijalLayout.setHorizontalGroup(
            jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMaterijalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneMaterijal, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanelMaterijalLayout.createSequentialGroup()
                        .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelMaterijalLayout.createSequentialGroup()
                                .addComponent(cmbGrupeMaterijala, 0, 127, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanelMaterijalLayout.createSequentialGroup()
                                .addComponent(lblMaterijalTraziGrupaMaterijal)
                                .addGap(33, 33, 33)))
                        .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelMaterijalLayout.createSequentialGroup()
                                .addComponent(txtMaterijalTraziProizvodac, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanelMaterijalLayout.createSequentialGroup()
                                .addComponent(lblMaterijalTraziProizvodac)
                                .addGap(56, 56, 56)))
                        .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMaterijalTraziOznaka)
                            .addGroup(jPanelMaterijalLayout.createSequentialGroup()
                                .addComponent(txtMaterijalTraziOznaka, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnMaterijalResetirajTrazilicu)))))
                .addGap(18, 18, 18)
                .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMaterijalLayout.createSequentialGroup()
                        .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMaterijalGrupaMaterijal)
                            .addComponent(lblMaterijalProizvodac)
                            .addComponent(lbMaterijallOznaka)
                            .addComponent(lblMaterijalKolicinaAmbalaza)
                            .addComponent(lblMaterijalJedinicaMjereAmbalaza)
                            .addComponent(lblMaterijalCijenaAmbalaza)
                            .addComponent(lblMaterijalOpis)
                            .addComponent(lblMaterijalId))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaterijalGrupaMaterijal, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaterijalProizvodac, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaterijalOznaka, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaterijalKolicinaAmbalaza, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaterijalJedinicaMjereAmbalaza, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaterijalCijenaAmbalaza, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPaneMaterijalOpsi, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelMaterijalLayout.createSequentialGroup()
                                .addComponent(btnMaterijalDodaj)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMaterijalPromjeni)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnMaterijalObrisi))
                            .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanelMaterijalLayout.createSequentialGroup()
                                    .addGap(192, 192, 192)
                                    .addComponent(btnMaterijalUpute))
                                .addComponent(txtMaterijalIdValue, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMaterijalLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(87, 87, 87))))
        );
        jPanelMaterijalLayout.setVerticalGroup(
            jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMaterijalLayout.createSequentialGroup()
                .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMaterijalLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaterijalTraziGrupaMaterijal)
                            .addComponent(lblMaterijalTraziProizvodac)
                            .addComponent(lblMaterijalTraziOznaka))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMaterijalTraziOznaka, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMaterijalResetirajTrazilicu)
                            .addComponent(cmbGrupeMaterijala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaterijalTraziProizvodac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelMaterijalLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnMaterijalUpute)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneMaterijal, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanelMaterijalLayout.createSequentialGroup()
                        .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaterijalId)
                            .addComponent(txtMaterijalIdValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaterijalGrupaMaterijal)
                            .addComponent(txtMaterijalGrupaMaterijal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaterijalProizvodac)
                            .addComponent(txtMaterijalProizvodac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbMaterijallOznaka)
                            .addComponent(txtMaterijalOznaka, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaterijalKolicinaAmbalaza)
                            .addComponent(txtMaterijalKolicinaAmbalaza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaterijalJedinicaMjereAmbalaza)
                            .addComponent(txtMaterijalJedinicaMjereAmbalaza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaterijalCijenaAmbalaza)
                            .addComponent(txtMaterijalCijenaAmbalaza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelMaterijalLayout.createSequentialGroup()
                                .addComponent(jScrollPaneMaterijalOpsi, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnMaterijalDodaj)
                                    .addComponent(btnMaterijalPromjeni)
                                    .addComponent(btnMaterijalObrisi)))
                            .addComponent(lblMaterijalOpis))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addGap(0, 17, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Materijal", jPanelMaterijal);

        javax.swing.GroupLayout jPanelPrebacivanjeLayout = new javax.swing.GroupLayout(jPanelPrebacivanje);
        jPanelPrebacivanje.setLayout(jPanelPrebacivanjeLayout);
        jPanelPrebacivanjeLayout.setHorizontalGroup(
            jPanelPrebacivanjeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 845, Short.MAX_VALUE)
        );
        jPanelPrebacivanjeLayout.setVerticalGroup(
            jPanelPrebacivanjeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 432, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Prebacivanje", jPanelPrebacivanje);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtMaterijalGrupaMaterijalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaterijalGrupaMaterijalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaterijalGrupaMaterijalActionPerformed

    private void btnMaterijalDodajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaterijalDodajActionPerformed
        materijal = new Materijal();
        try {
            spremiMaterijal();
        } catch (PorukaIznimke ex) {
            Logger.getLogger(FormaRadMaterijal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnMaterijalDodajActionPerformed

    private void btnMaterijalPromjeniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaterijalPromjeniActionPerformed
        try {
            spremiMaterijal();
        } catch (PorukaIznimke ex) {
            Logger.getLogger(FormaRadMaterijal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnMaterijalPromjeniActionPerformed

    private void btnMaterijalObrisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaterijalObrisiActionPerformed
        try {
            //        if (lista.getSelectedValue() == null) {
            //            JOptionPane.showConfirmDialog(rootPane, "Prvo odaberite stavku");
            //        }
            obradaMaterijal.obrisi(materijal);
        } catch (PorukaIznimke ex) {
            Logger.getLogger(FormaRadMaterijal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnMaterijalObrisiActionPerformed

    private void txtMaterijalTraziOznakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaterijalTraziOznakaActionPerformed

    }//GEN-LAST:event_txtMaterijalTraziOznakaActionPerformed

    private void txtMaterijalTraziOznakaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaterijalTraziOznakaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            traziMaterijal();
        }
    }//GEN-LAST:event_txtMaterijalTraziOznakaKeyPressed

    private void btnMaterijalResetirajTrazilicuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaterijalResetirajTrazilicuActionPerformed

        tblMaterijal.getSelectionModel().clearSelection();
        materijal = null;
        txtMaterijalIdValue.setText("");
        txtMaterijalGrupaMaterijal.setText("");
        txtMaterijalProizvodac.setText("");
        txtMaterijalOznaka.setText("");
        txtMaterijalKolicinaAmbalaza.setText("");
        txtMaterijalJedinicaMjereAmbalaza.setText("");
        txtMaterijalCijenaAmbalaza.setText("");
        tarMaterijalOpis.setText("");
        ucitajMaterijal();
    }//GEN-LAST:event_btnMaterijalResetirajTrazilicuActionPerformed

    private void tblMaterijalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMaterijalMouseClicked
        if (evt.getClickCount() == 2) {
            DefaultTableModel model = (DefaultTableModel) tblMaterijal.getModel();
            int row = tblMaterijal.getSelectedRow();
            //            System.out.println("=== odabrani redak" + row);
            materijal = rezultatiMaterijal.get(tblMaterijal.convertRowIndexToModel(row));
            txtMaterijalIdValue.setText(model.getValueAt(row, 0).toString());
            txtMaterijalGrupaMaterijal.setText(model.getValueAt(row, 1).toString());
            txtMaterijalProizvodac.setText(model.getValueAt(row, 2).toString());
            txtMaterijalOznaka.setText(model.getValueAt(row, 3).toString());
            txtMaterijalKolicinaAmbalaza.setText(model.getValueAt(row, 4).toString());
            txtMaterijalJedinicaMjereAmbalaza.setText(model.getValueAt(row, 5).toString());
            txtMaterijalCijenaAmbalaza.setText(model.getValueAt(row, 6).toString());
            tarMaterijalOpis.setWrapStyleWord(true);
            tarMaterijalOpis.setText(model.getValueAt(row, 7).toString());
            //            System.out.println(model.getValueAt(row, 7).toString().length());
        } else if (evt.getClickCount() == 1 && evt.isAltDown() == true) {
            tblMaterijal.getSelectionModel().clearSelection();
            materijal = null;
            txtMaterijalIdValue.setText("");
            txtMaterijalGrupaMaterijal.setText("");
            txtMaterijalProizvodac.setText("");
            txtMaterijalOznaka.setText("");
            txtMaterijalKolicinaAmbalaza.setText("");
            txtMaterijalJedinicaMjereAmbalaza.setText("");
            txtMaterijalCijenaAmbalaza.setText("");
            tarMaterijalOpis.setText("");

        }
    }//GEN-LAST:event_tblMaterijalMouseClicked

    private void tblMaterijalComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tblMaterijalComponentResized
        tblMaterijal.setRowHeight(16);
        updateRowHeights(tblMaterijal);
    }//GEN-LAST:event_tblMaterijalComponentResized

    private void jScrollPaneMaterijalComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jScrollPaneMaterijalComponentResized
        changedSize(tblMaterijal);
    }//GEN-LAST:event_jScrollPaneMaterijalComponentResized

    private void txtMaterijalIdValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaterijalIdValueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaterijalIdValueActionPerformed

    private void cmbGrupeMaterijalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbGrupeMaterijalaActionPerformed
        traziMaterijal();
        //        jScrollPane3ComponentResized(new ComponentEvent(jScrollPane3, WIDTH));
        //System.out.println("jScrollPane3.getVerticalScrollBar().isShowing() " + jScrollPane3.getVerticalScrollBar().isShowing());
    }//GEN-LAST:event_cmbGrupeMaterijalaActionPerformed

    private void txtMaterijalTraziProizvodacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaterijalTraziProizvodacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaterijalTraziProizvodacActionPerformed

    private void txtMaterijalTraziProizvodacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaterijalTraziProizvodacKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            traziMaterijal();
        }
    }//GEN-LAST:event_txtMaterijalTraziProizvodacKeyPressed

    private void cmbGrupeRadovaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbGrupeRadovaActionPerformed
        traziRad();
    }//GEN-LAST:event_cmbGrupeRadovaActionPerformed

    private void btnRadUputeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRadUputeActionPerformed
        JOptionPane.showMessageDialog(rootPane,
                "Pretraživanje:" + "\n"
                + "- izvodi se tipkom enter u jednom od tri polja" + "\n"
                + "- tipka " + btnRadResetirajTrazilicu.getText() + " briše vrijesnosti unešene u polja i prikazuje sve rezultate" + "\n\n"
                + "Tablica sa podatcima:" + "\n"
                + "- jednim klikom miša označava se odabrani red" + "\n"
                + "- dvostrukim klikom odabiru se vrijednosti odabranog reda i upisuju u polja kako bi se izvršile radnje promjene ili brisanja" + "\n"
                + "- držanjem tipke alt i lijevim klikom odznačava se red u tablici i ispraznjuju se vrijednosti polja za radnje dodavanja, promjene i brisanja",
                getTitle() + " - Upute za korištenje", JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_btnRadUputeActionPerformed

    private void btnRadResetirajTrazilicuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRadResetirajTrazilicuActionPerformed
        cmbGrupeRadova.setSelectedItem("Nije odabrano");
        txtRadTraziCijena.setText("");
        txtRadTraziKategorija.setText("");
        ucitajRad();
    }//GEN-LAST:event_btnRadResetirajTrazilicuActionPerformed

    private void txtRadIdValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRadIdValueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRadIdValueActionPerformed

    private void txtRadTraziCijenaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRadTraziCijenaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            traziRad();
        }
    }//GEN-LAST:event_txtRadTraziCijenaKeyPressed

    private void txtRadTraziCijenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRadTraziCijenaActionPerformed

    }//GEN-LAST:event_txtRadTraziCijenaActionPerformed

    private void tblRadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRadMouseClicked
        if (evt.getClickCount() == 2) {
            int row = tblRad.getSelectedRow();
            DefaultTableModel model = (DefaultTableModel) tblRad.getModel();
            rad = rezultatiRad.get(tblRad.convertRowIndexToModel(row));
            System.out.println(rad.getId() + " " + rad);
            txtRadIdValue.setText(model.getValueAt(row, 0).toString());
            txtRadGrupaRadova.setText(model.getValueAt(row, 1).toString());
            txtRadKategorijaRad.setText(model.getValueAt(row, 2).toString());
            txtRadCijena.setText(model.getValueAt(row, 3).toString());
        } else if (evt.getClickCount() == 1 && evt.isAltDown() == true) {
            tblRad.getSelectionModel().clearSelection();
            rad = null;
            txtRadIdValue.setText("");
            txtRadGrupaRadova.setText("");
            txtRadKategorijaRad.setText("");
            txtRadCijena.setText("");
        }
    }//GEN-LAST:event_tblRadMouseClicked

    private void txtRadTraziKategorijaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRadTraziKategorijaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            traziRad();
        }
    }//GEN-LAST:event_txtRadTraziKategorijaKeyPressed

    private void txtRadTraziKategorijaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRadTraziKategorijaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRadTraziKategorijaActionPerformed

    private void btnRadObrisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRadObrisiActionPerformed
        obrisi = true;
        if (rad == null || tblRad.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Odaberite stavku vidljivu unutar tablice", getTitle() + " - Obriši", HEIGHT);
        } else {
            int i = tblRad.getSelectedRow();
            DefaultTableModel model = (DefaultTableModel) tblRad.getModel();

            Object[] options = {"Da", "Ne"};
            int reply = JOptionPane.showOptionDialog(rootPane, "Sigurno želite obrisati stavku? \n"
                    + model.getValueAt(i, 0).toString() + ", " + model.getValueAt(i, 1).toString()
                    + ", " + model.getValueAt(i, 2).toString() + ", "
                    + model.getValueAt(i, 3).toString(), getTitle()
                    + " - Obriši", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
            if (reply == JOptionPane.YES_OPTION) {
                dpoRad();
                ucitajRad();
                rad = null;
                txtRadIdValue.setText("");
                txtRadGrupaRadova.setText("");
                txtRadKategorijaRad.setText("");
                txtRadCijena.setText("");
                tblRad.getSelectionModel().clearSelection();
                traziRad();
            }
        }
        obrisi = false;
    }//GEN-LAST:event_btnRadObrisiActionPerformed

    private void btnRadPromjeniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRadPromjeniActionPerformed
        promijeni = true;
        if (rad == null || tblRad.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Odaberite stavku vidljivu unutar tablice",
                    getTitle() + " - Promjena postojećeg", HEIGHT);
        } else {
            int i = tblRad.getSelectedRow();
            rad = rezultatiRad.get(tblRad.convertRowIndexToModel(i));
            dpoRad();
            DefaultTableModel model = (DefaultTableModel) tblRad.getModel();
            model.setValueAt(txtRadGrupaRadova.getText(), i, 1);
            model.setValueAt(txtRadKategorijaRad.getText(), i, 2);
            model.setValueAt(txtRadCijena.getText(), i, 3);
            rad = null;
            traziRad();
            rad = rezultatiRad.get(tblRad.convertRowIndexToModel(i));
            tblRad.setRowSelectionInterval(i, i);
        }
        promijeni = false;
    }//GEN-LAST:event_btnRadPromjeniActionPerformed

    private void btnRadDodajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRadDodajActionPerformed
        dodaj = true;
        rad = new Rad();
        dpoRad();
        traziRad();
        dodaj = false;
    }//GEN-LAST:event_btnRadDodajActionPerformed

    private void txtRadGrupaRadovaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRadGrupaRadovaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRadGrupaRadovaActionPerformed

    private void btnMaterijalUputeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaterijalUputeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMaterijalUputeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMaterijalDodaj;
    private javax.swing.JButton btnMaterijalObrisi;
    private javax.swing.JButton btnMaterijalPromjeni;
    private javax.swing.JButton btnMaterijalResetirajTrazilicu;
    private javax.swing.JButton btnMaterijalUpute;
    private javax.swing.JButton btnRadDodaj;
    private javax.swing.JButton btnRadObrisi;
    private javax.swing.JButton btnRadPromjeni;
    private javax.swing.JButton btnRadResetirajTrazilicu;
    private javax.swing.JButton btnRadUpute;
    private javax.swing.JComboBox<String> cmbGrupeMaterijala;
    private javax.swing.JComboBox<String> cmbGrupeRadova;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanelMaterijal;
    private javax.swing.JPanel jPanelPrebacivanje;
    private javax.swing.JPanel jPanelRad;
    private javax.swing.JScrollPane jScrollPaneMaterijal;
    private javax.swing.JScrollPane jScrollPaneMaterijalOpsi;
    private javax.swing.JScrollPane jScrollPaneRad;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbMaterijallOznaka;
    private javax.swing.JLabel lblMaterijalCijenaAmbalaza;
    private javax.swing.JLabel lblMaterijalGrupaMaterijal;
    private javax.swing.JLabel lblMaterijalId;
    private javax.swing.JLabel lblMaterijalJedinicaMjereAmbalaza;
    private javax.swing.JLabel lblMaterijalKolicinaAmbalaza;
    private javax.swing.JLabel lblMaterijalOpis;
    private javax.swing.JLabel lblMaterijalProizvodac;
    private javax.swing.JLabel lblMaterijalTraziGrupaMaterijal;
    private javax.swing.JLabel lblMaterijalTraziOznaka;
    private javax.swing.JLabel lblMaterijalTraziProizvodac;
    private javax.swing.JLabel lblRadCijena;
    private javax.swing.JLabel lblRadGrupaRadova;
    private javax.swing.JLabel lblRadId;
    private javax.swing.JLabel lblRadKategorijaRada;
    private javax.swing.JLabel lblRadTraziCijena;
    private javax.swing.JLabel lblRadTraziGrupaRadova;
    private javax.swing.JLabel lblRadTraziKategorija;
    private javax.swing.JTextArea tarMaterijalOpis;
    private javax.swing.JTable tblMaterijal;
    private javax.swing.JTable tblRad;
    private javax.swing.JTextField txtMaterijalCijenaAmbalaza;
    private javax.swing.JTextField txtMaterijalGrupaMaterijal;
    private javax.swing.JTextField txtMaterijalIdValue;
    private javax.swing.JTextField txtMaterijalJedinicaMjereAmbalaza;
    private javax.swing.JTextField txtMaterijalKolicinaAmbalaza;
    private javax.swing.JTextField txtMaterijalOznaka;
    private javax.swing.JTextField txtMaterijalProizvodac;
    private javax.swing.JTextField txtMaterijalTraziOznaka;
    private javax.swing.JTextField txtMaterijalTraziProizvodac;
    private javax.swing.JTextField txtRadCijena;
    private javax.swing.JTextField txtRadGrupaRadova;
    private javax.swing.JTextField txtRadIdValue;
    private javax.swing.JTextField txtRadKategorijaRad;
    private javax.swing.JTextField txtRadTraziCijena;
    private javax.swing.JTextField txtRadTraziKategorija;
    // End of variables declaration//GEN-END:variables
}
