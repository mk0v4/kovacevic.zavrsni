/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import kovacevic.controller.ObradaAnalizaCijene;
import kovacevic.model.AnalizaCijene;
import kovacevic.model.AnalizaMaterijal;
import kovacevic.model.AnalizaRad;
import kovacevic.model.GrupacijaNorme;
import kovacevic.model.StavkaTroskovnik;
import kovacevic.pomocno.HibernateUtil;
import kovacevic.pomocno.WrappableTable;

/**
 *
 * @author Marko Kovačević
 */
public class FormaAnalizaCijene extends JFrame {

    private List<AnalizaMaterijal> analizaMaterijal;
    private List<AnalizaRad> analizaRad;
    private List<StavkaTroskovnik> stavkaTroskovnik;

    private final ObradaAnalizaCijene obradaAnalizaCijene;
    private AnalizaCijene analizaCijene;
    private List<AnalizaCijene> rezultatiAnalizaCijena;

    private List<GrupacijaNorme> grupacijaNormeLista;
    private GrupacijaNorme grupacijaNorme;

    private final boolean dodaj = false;
    private final boolean promijeni = false;
    private final boolean obrisi = false;

    private final JPopupMenu menuJTree = new JPopupMenu("Popup");
    private final JMenuItem dodajGrupacijaNorme = new JMenuItem("Dodaj");
    private final JMenuItem promijeniGrupacijaNorme = new JMenuItem("Promijeni");

    private final JPopupMenu menuJTable = new JPopupMenu("Popup");
    private final JMenuItem dodajNorma = new JMenuItem("Dodaj");
    private final JMenuItem promijeniNorma = new JMenuItem("Promijeni");

    /**
     * Creates new form FormaAnalizaCijene
     */
    public FormaAnalizaCijene() {
        obradaAnalizaCijene = new ObradaAnalizaCijene();
//        jTreeNorma.setRootVisible(false);
        initComponents();
        setTitle("Analiza Cijena");

        popunjavanjeJTree();

        popupMenuJTree();
        popupMenuJTable();

        popunjavanjeTabliceAnalizaCijene(jTableNorma);

        jTableNorma = new WrappableTable(jTableNorma, jTableNorma.getModel(), jScrollPaneTableNorma);

    }

    private void popupMenuJTree() {
        menuJTree.add(dodajGrupacijaNorme);
        menuJTree.add(promijeniGrupacijaNorme);

        dodajGrupacijaNorme.addActionListener((ActionEvent e) -> {
            FormaGrupacijaNorme grupacijaNorme1 = new FormaGrupacijaNorme(new GrupacijaNorme(), true, true);
            grupacijaNorme1.setVisible(true);
            TreePath odabraniPath = jTreeNorma.getSelectionPath().getParentPath();
            DefaultMutableTreeNode node1 = (DefaultMutableTreeNode) odabraniPath.getPathComponent(1);
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) jTreeNorma.getModel().getRoot();
            System.out.println("jTreeNorma.getSelectionPath() " + jTreeNorma.getRowForPath(jTreeNorma.getSelectionPath()));
            int red = jTreeNorma.getRowForPath(jTreeNorma.getSelectionPath());
            grupacijaNorme1.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent arg0) {
                    int expand = 0;
                    for (int i = 0; i < root.getChildCount(); i++) {
                        jTreeNorma.collapseRow(i);
                        if (root.getChildAt(i).equals(node1)) {
                            expand = i;
                        }
                    }
                    popunjavanjeJTree();
                    jTreeNorma.expandRow(expand);
                    jTreeNorma.setSelectionRow(red);
                }
            });
        });
        promijeniGrupacijaNorme.addActionListener((ActionEvent e) -> {
            FormaGrupacijaNorme grupacijaNorme = new FormaGrupacijaNorme(this.grupacijaNorme, false, true);
            grupacijaNorme.setVisible(true);
            TreePath odabraniPath = jTreeNorma.getSelectionPath().getParentPath();
            DefaultMutableTreeNode node1 = (DefaultMutableTreeNode) odabraniPath.getPathComponent(1);
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) jTreeNorma.getModel().getRoot();
            int red = jTreeNorma.getRowForPath(jTreeNorma.getSelectionPath());
            grupacijaNorme.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent arg0) {
                    int expand = 0;
                    for (int i = 0; i < root.getChildCount(); i++) {
                        jTreeNorma.collapseRow(i);
                        if (root.getChildAt(i).equals(node1)) {
                            expand = i;
                        }
                    }
                    popunjavanjeJTree();
                    jTreeNorma.expandRow(expand);
                    jTreeNorma.setSelectionRow(red);
                }
            });
        });
    }

    private void popupMenuJTable() {
        menuJTable.add(dodajNorma);
        menuJTable.add(promijeniNorma);

        dodajNorma.addActionListener((ActionEvent e) -> {
            System.out.println("kovacevic.view.FormaAnalizaCijene.popupMenuJTable()");
            jFrameEditAnalizaCijene.setVisible(true);
//            grupacijaNorme1.addWindowListener(new WindowAdapter() {
//                @Override
//                public void windowClosing(WindowEvent arg0) {
//
//            });
        });
        promijeniNorma.addActionListener((ActionEvent e) -> {
            System.out.println("kovacevic.view.FormaAnalizaCijene.popupMenuJTable()");
            jFrameEditAnalizaCijene.setVisible(true);
//            grupacijaNorme.addWindowListener(new WindowAdapter() {
//                @Override
//                public void windowClosing(WindowEvent arg0) {
//                }
//            });
        });
    }

//    public static DefaultMutableTreeNode sortTree(DefaultMutableTreeNode root) {
//        {
//            for (int i = 0; i < root.getChildCount() - 1; i++) {
//                DefaultMutableTreeNode node = (DefaultMutableTreeNode) root.getChildAt(i);
//                String nt = node.getUserObject().toString();
//
//                for (int j = i + 1; j <= root.getChildCount() - 1; j++) {
//                    DefaultMutableTreeNode prevNode = (DefaultMutableTreeNode) root.getChildAt(j);
//                    String np = prevNode.getUserObject().toString();
//
//                    System.out.println(nt + " " + np);
//                    if (nt.compareToIgnoreCase(np) > 0) {
//
//                        root.insert(node, j);
//                        break;
//                    }
//                }
//                if (node.getChildCount() > 0) {
//                    node = sortTree(node);
//                }
//            }
//
//            return root;
//        }
//    }
    private void popunjavanjeTabliceAnalizaCijene(JTable tablica) {
        String[] coulumnName = {"Rb. (Id)", "Oznaka norme", "Opis norme",
            "Jedinica mjere", "Ukupan normativ vremena"};
        DefaultTableModel model = (DefaultTableModel) tablica.getModel();
        tablica.setDefaultRenderer(Object.class, new WrappableTable.WrappableTableRenderer(tablica));
        model.setRowCount(0);
        model.setColumnIdentifiers(coulumnName);
        int rb = 0;
        if (rezultatiAnalizaCijena != null) {
            for (AnalizaCijene analizaCijene : rezultatiAnalizaCijena) {
                rb++;
                model.addRow(new Object[]{
                    rb + ". (" + analizaCijene.getId() + ")",
                    analizaCijene.getOznakaNorme(),
                    analizaCijene.getOpis(),
                    analizaCijene.getJedinicaMjere(),
                    analizaCijene.getUkupanNormativVremena()});
            }
        }
    }

    private void popunjavanjeJTree() {
        grupacijaNormeLista = HibernateUtil.getSession().createQuery("from GrupacijaNorme a where a.obrisan=false order by a.oznakaNorme asc").list();

        DefaultTreeModel model = (DefaultTreeModel) jTreeNorma.getModel();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        jTreeNorma.setRootVisible(false);

        DefaultMutableTreeNode node1;
        DefaultMutableTreeNode node2;
        int polozaj = 0;

        int j = 0;

        for (GrupacijaNorme grupacijaNorme : grupacijaNormeLista) {
            boolean imaNoda = false;
            String grupaNorme = grupacijaNorme.getOznakaNorme();
            polozaj = grupaNorme.indexOf("-", 3);
            grupaNorme = grupaNorme.substring(0, polozaj) + " " + grupacijaNorme.getGrupaNorme();
            for (int i = 0; i < root.getChildCount(); i++) {
                TreeNode childAt = root.getChildAt(i);
                if (childAt.toString().contains(grupaNorme)) {
                    imaNoda = true;
                }
            }
            if (imaNoda == false) {
                node1 = new DefaultMutableTreeNode(grupaNorme);
                root.add(node1);
            }
        }

        for (int i = 0; i < root.getChildCount(); i++) {
            DefaultMutableTreeNode childAt = (DefaultMutableTreeNode) root.getChildAt(i);
            for (GrupacijaNorme grupacijaNorme : grupacijaNormeLista) {
                if (matchingCharactes(childAt.toString(), grupacijaNorme.getOznakaNorme(), 6)) {
                    node2 = new NameOfJTreeNode(grupacijaNorme.getOznakaNorme() + " " + grupacijaNorme.getOpis(), grupacijaNorme);
                    childAt.add(node2);
                }
            }
        }
//        sortTree(root);
        model.setRoot(root);
    }

    private class NameOfJTreeNode extends DefaultMutableTreeNode {

        private String nodeName;

        private NameOfJTreeNode(String nodeName, Object userObject) {
            super(userObject);
            this.nodeName = nodeName;
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
            ((DefaultTreeModel) jTreeNorma.getModel()).nodeChanged(this);
        }

        @Override
        public String toString() {
            return (nodeName != null) ? nodeName : super.toString();
        }

    }

    public static boolean matchingCharactes(String string1, String string2, int numberOfChar) {
        String matchingPart = "";
        searchingMatch:
        for (int i = 0; i <= numberOfChar - 1; i++) {
            while (string1.charAt(i) == string2.charAt(i)) {
                matchingPart = matchingPart + string1.charAt(i);
                continue searchingMatch;
            }
            break;
        }
//        System.out.println(matchingPart);
        if (matchingPart.length() == 6) {
            return true;
        } else {
            return false;
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

        jFrameEditAnalizaCijene = new javax.swing.JFrame();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        lblOznakaNorme = new javax.swing.JLabel();
        txtOznakaNorme = new javax.swing.JTextField();
        lblOpisNorme = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tarOpisNorme = new javax.swing.JTextArea();
        lblJedinicaMjere = new javax.swing.JLabel();
        txtJedinicaMjere = new javax.swing.JTextField();
        lblUkupnoVrijeme = new javax.swing.JLabel();
        txtUkupanNormativVremena = new javax.swing.JTextField();
        btnDodaj = new javax.swing.JButton();
        btnPromjeni = new javax.swing.JButton();
        btnObrisi = new javax.swing.JButton();
        jPanelOperacija = new javax.swing.JPanel();
        lblOperacije1 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        lstOperacije = new javax.swing.JList<>();
        lblUkupnoRad = new javax.swing.JLabel();
        txtUkupnoRad = new javax.swing.JTextField();
        txtUkupnaCijena = new javax.swing.JTextField();
        txtKoeficijentFirme = new javax.swing.JTextField();
        lblUkupnaCijena = new javax.swing.JLabel();
        lblKoeficijentFirme = new javax.swing.JLabel();
        jPanelMaterijal = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstMaterijali = new javax.swing.JList<>();
        lblMaterijali = new javax.swing.JLabel();
        lblUkupnoMaterijal = new javax.swing.JLabel();
        txtUkupnoMaterijal = new javax.swing.JTextField();
        jPanelNorma = new javax.swing.JPanel();
        jScrollPaneTreNorma = new javax.swing.JScrollPane();
        jTreeNorma = new javax.swing.JTree();
        jScrollPaneTableNorma = new javax.swing.JScrollPane();
        jTableNorma = new javax.swing.JTable();

        jFrameEditAnalizaCijene.setMinimumSize(new java.awt.Dimension(775, 500));
        jFrameEditAnalizaCijene.setPreferredSize(new java.awt.Dimension(773, 507));

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOpisNorme)
                    .addComponent(lblJedinicaMjere)
                    .addComponent(lblUkupnoVrijeme)
                    .addComponent(lblOznakaNorme))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnDodaj)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPromjeni)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnObrisi))
                    .addComponent(txtUkupanNormativVremena)
                    .addComponent(txtJedinicaMjere)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                    .addComponent(txtOznakaNorme))
                .addGap(231, 231, 231))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOznakaNorme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblOznakaNorme))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOpisNorme)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtJedinicaMjere, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblJedinicaMjere))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUkupanNormativVremena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUkupnoVrijeme))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDodaj)
                    .addComponent(btnPromjeni)
                    .addComponent(btnObrisi))
                .addContainerGap(212, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Ostalo", jPanel4);

        lblOperacije1.setText("Operacije:");

        jScrollPane6.setMinimumSize(new java.awt.Dimension(450, 23));
        jScrollPane6.setName(""); // NOI18N
        jScrollPane6.setPreferredSize(new java.awt.Dimension(450, 130));

        lstOperacije.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstOperacije.setToolTipText("");
        lstOperacije.setDropMode(javax.swing.DropMode.INSERT);
        lstOperacije.setMaximumSize(new java.awt.Dimension(1000, 1000));
        jScrollPane6.setViewportView(lstOperacije);

        lblUkupnoRad.setText("Ukupna cijena rada:");

        lblUkupnaCijena.setText("Ukupna cijena:");

        lblKoeficijentFirme.setText("Koeficijent:");

        javax.swing.GroupLayout jPanelOperacijaLayout = new javax.swing.GroupLayout(jPanelOperacija);
        jPanelOperacija.setLayout(jPanelOperacijaLayout);
        jPanelOperacijaLayout.setHorizontalGroup(
            jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOperacijaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOperacijaLayout.createSequentialGroup()
                        .addGroup(jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUkupnoRad)
                            .addComponent(lblOperacije1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtUkupnoRad)))
                    .addGroup(jPanelOperacijaLayout.createSequentialGroup()
                        .addGroup(jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblKoeficijentFirme)
                            .addComponent(lblUkupnaCijena))
                        .addGap(43, 43, 43)
                        .addGroup(jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUkupnaCijena)
                            .addComponent(txtKoeficijentFirme))))
                .addGap(185, 185, 185))
        );
        jPanelOperacijaLayout.setVerticalGroup(
            jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOperacijaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOperacije1)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUkupnoRad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUkupnoRad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKoeficijentFirme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblKoeficijentFirme))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUkupnaCijena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUkupnaCijena))
                .addContainerGap(266, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Operacije", jPanelOperacija);

        jScrollPane3.setMinimumSize(new java.awt.Dimension(450, 23));
        jScrollPane3.setName(""); // NOI18N
        jScrollPane3.setPreferredSize(new java.awt.Dimension(450, 130));

        lstMaterijali.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstMaterijali.setToolTipText("");
        lstMaterijali.setDropMode(javax.swing.DropMode.INSERT);
        lstMaterijali.setMaximumSize(new java.awt.Dimension(1000, 1000));
        jScrollPane3.setViewportView(lstMaterijali);

        lblMaterijali.setText("Materijali:");

        lblUkupnoMaterijal.setText("Ukupna cijena materijala:");

        javax.swing.GroupLayout jPanelMaterijalLayout = new javax.swing.GroupLayout(jPanelMaterijal);
        jPanelMaterijal.setLayout(jPanelMaterijalLayout);
        jPanelMaterijalLayout.setHorizontalGroup(
            jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMaterijalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUkupnoMaterijal)
                    .addComponent(lblMaterijali))
                .addGap(18, 18, 18)
                .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                    .addComponent(txtUkupnoMaterijal, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(157, 157, 157))
        );
        jPanelMaterijalLayout.setVerticalGroup(
            jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMaterijalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMaterijali)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUkupnoMaterijal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUkupnoMaterijal))
                .addContainerGap(306, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Materijali", jPanelMaterijal);

        javax.swing.GroupLayout jFrameEditAnalizaCijeneLayout = new javax.swing.GroupLayout(jFrameEditAnalizaCijene.getContentPane());
        jFrameEditAnalizaCijene.getContentPane().setLayout(jFrameEditAnalizaCijeneLayout);
        jFrameEditAnalizaCijeneLayout.setHorizontalGroup(
            jFrameEditAnalizaCijeneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jFrameEditAnalizaCijeneLayout.setVerticalGroup(
            jFrameEditAnalizaCijeneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(775, 500));

        jTreeNorma.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTreeNormaMouseClicked(evt);
            }
        });
        jTreeNorma.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreeNormaValueChanged(evt);
            }
        });
        jScrollPaneTreNorma.setViewportView(jTreeNorma);

        jScrollPaneTableNorma.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPaneTableNormaMouseClicked(evt);
            }
        });

        jTableNorma.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "null", "null", "null", "null", "null"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableNorma.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableNorma.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableNormaMouseClicked(evt);
            }
        });
        jTableNorma.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jTableNormaComponentResized(evt);
            }
        });
        jScrollPaneTableNorma.setViewportView(jTableNorma);

        javax.swing.GroupLayout jPanelNormaLayout = new javax.swing.GroupLayout(jPanelNorma);
        jPanelNorma.setLayout(jPanelNormaLayout);
        jPanelNormaLayout.setHorizontalGroup(
            jPanelNormaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNormaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelNormaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneTreNorma, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPaneTableNorma, javax.swing.GroupLayout.DEFAULT_SIZE, 753, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelNormaLayout.setVerticalGroup(
            jPanelNormaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNormaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneTreNorma, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jScrollPaneTableNorma, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelNorma, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelNorma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTreeNormaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTreeNormaMouseClicked
        if (SwingUtilities.isRightMouseButton(evt)) {
//            int index = jTreeNorma.getSelectionRows();
//                    .locationToIndex(evt.getPoint());
//            analizaCijene = lstAnalizaCijena.getModel().getElementAt(index);
//            lstAnalizaCijena.setSelectionInterval(index, index);
//            lstAnalizaCijena.setSelectionBackground(new Color(184, 207, 229));
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTreeNorma.getLastSelectedPathComponent();
            if (node != null && node.isLeaf()) {
                menuJTree.show(evt.getComponent(), evt.getX(), evt.getY());
            }
        }
    }//GEN-LAST:event_jTreeNormaMouseClicked

    private void jTableNormaComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jTableNormaComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableNormaComponentResized

    private void jTreeNormaValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTreeNormaValueChanged
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTreeNorma.getLastSelectedPathComponent();
        if (node == null) {
            return;
        }
        if (!node.isLeaf()) {
            DefaultMutableTreeNode node1 = (DefaultMutableTreeNode) jTreeNorma.getSelectionPath().getPathComponent(1);
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) jTreeNorma.getModel().getRoot();
            int expand = 0;
            for (int i = 0; i < root.getChildCount(); i++) {
                jTreeNorma.collapseRow(i);
                if (root.getChildAt(i).equals(node1)) {
                    expand = i;
                }
            }
            jTreeNorma.expandRow(expand);
            return;
        }
        if (node.isLeaf()) {
            grupacijaNorme = (GrupacijaNorme) node.getUserObject();
            System.out.println("grupacijaNorme " + grupacijaNorme);
            rezultatiAnalizaCijena = grupacijaNorme.getAnalizeCijena();
            popunjavanjeTabliceAnalizaCijene(jTableNorma);
        }
    }//GEN-LAST:event_jTreeNormaValueChanged

    private void btnObrisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObrisiActionPerformed
        //        if (lstAnalizaCijena.getSelectedValue() == null) {
        //            JOptionPane.showConfirmDialog(rootPane, "Prvo odaberite stavku");
        //        }
        //        obrisi();
    }//GEN-LAST:event_btnObrisiActionPerformed

    private void btnDodajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDodajActionPerformed
        analizaCijene = new AnalizaCijene();
        //        spremi();
        repaint();
    }//GEN-LAST:event_btnDodajActionPerformed

    private void btnPromjeniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPromjeniActionPerformed
        //        if (lstAnalizaCijena.getSelectedValue() == null) {
        //            JOptionPane.showConfirmDialog(rootPane, "Prvo odaberite stavku");
        //        }
        //        spremi();
    }//GEN-LAST:event_btnPromjeniActionPerformed

    private void jTableNormaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableNormaMouseClicked
        if (SwingUtilities.isRightMouseButton(evt)) {
            System.out.println("kovacevic.view.FormaAnalizaCijene.jTableNormaMouseClicked()");
            menuJTable.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jTableNormaMouseClicked

    private void jScrollPaneTableNormaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPaneTableNormaMouseClicked
        if (SwingUtilities.isRightMouseButton(evt)) {
            System.out.println("kovacevic.view.FormaAnalizaCijene.jScrollPaneTableNormaMouseClicked()");
            menuJTable.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jScrollPaneTableNormaMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDodaj;
    private javax.swing.JButton btnObrisi;
    private javax.swing.JButton btnPromjeni;
    private javax.swing.JFrame jFrameEditAnalizaCijene;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelMaterijal;
    private javax.swing.JPanel jPanelNorma;
    private javax.swing.JPanel jPanelOperacija;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPaneTableNorma;
    private javax.swing.JScrollPane jScrollPaneTreNorma;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableNorma;
    private javax.swing.JTree jTreeNorma;
    private javax.swing.JLabel lblJedinicaMjere;
    private javax.swing.JLabel lblKoeficijentFirme;
    private javax.swing.JLabel lblMaterijali;
    private javax.swing.JLabel lblOperacije1;
    private javax.swing.JLabel lblOpisNorme;
    private javax.swing.JLabel lblOznakaNorme;
    private javax.swing.JLabel lblUkupnaCijena;
    private javax.swing.JLabel lblUkupnoMaterijal;
    private javax.swing.JLabel lblUkupnoRad;
    private javax.swing.JLabel lblUkupnoVrijeme;
    private javax.swing.JList<AnalizaMaterijal> lstMaterijali;
    private javax.swing.JList<AnalizaRad> lstOperacije;
    private javax.swing.JTextArea tarOpisNorme;
    private javax.swing.JTextField txtJedinicaMjere;
    private javax.swing.JTextField txtKoeficijentFirme;
    private javax.swing.JTextField txtOznakaNorme;
    private javax.swing.JTextField txtUkupanNormativVremena;
    private javax.swing.JTextField txtUkupnaCijena;
    private javax.swing.JTextField txtUkupnoMaterijal;
    private javax.swing.JTextField txtUkupnoRad;
    // End of variables declaration//GEN-END:variables
}
