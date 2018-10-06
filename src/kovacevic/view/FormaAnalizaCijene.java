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

    private final JPopupMenu menu = new JPopupMenu("Popup");
    private final JMenuItem dodajGrupacijaNorme = new JMenuItem("Dodaj");
    private final JMenuItem promijeniGrupacijaNorme = new JMenuItem("Promijeni");

    /**
     * Creates new form FormaAnalizaCijene
     */
    public FormaAnalizaCijene() {
        obradaAnalizaCijene = new ObradaAnalizaCijene();
//        jTreeNorma.setRootVisible(false);
        initComponents();
        setTitle("Analiza Cijena");

        popunjavanjeJTree();

        popupMenu();

        popunjavanjeTabliceAnalizaCijene(jTableNorma);

        jTableNorma = new WrappableTable(jTableNorma, jTableNorma.getModel(), jScrollPaneTableNorma);

    }

    private void popupMenu() {
        menu.add(dodajGrupacijaNorme);
        menu.add(promijeniGrupacijaNorme);

        dodajGrupacijaNorme.addActionListener((ActionEvent e) -> {
            FormaGrupacijaNorme grupacijaNorme1 = new FormaGrupacijaNorme(new GrupacijaNorme(), true, true);
            grupacijaNorme1.setVisible(true);
            TreePath odabraniPath = jTreeNorma.getSelectionPath().getParentPath();
            DefaultMutableTreeNode node1 = (DefaultMutableTreeNode) odabraniPath.getPathComponent(1);
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) jTreeNorma.getModel().getRoot();
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
                }
            });
        });
        promijeniGrupacijaNorme.addActionListener((ActionEvent e) -> {
            FormaGrupacijaNorme grupacijaNorme = new FormaGrupacijaNorme(this.grupacijaNorme, false, true);
            grupacijaNorme.setVisible(true);
            TreePath odabraniPath = jTreeNorma.getSelectionPath().getParentPath();
            DefaultMutableTreeNode node1 = (DefaultMutableTreeNode) odabraniPath.getPathComponent(1);
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) jTreeNorma.getModel().getRoot();
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
                }
            });
        });
    }

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
        grupacijaNormeLista = HibernateUtil.getSession().createQuery("from GrupacijaNorme a where a.obrisan=false").list();

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

        jtpNorme = new javax.swing.JTabbedPane();
        jPanelNorma = new javax.swing.JPanel();
        jScrollPaneTreNorma = new javax.swing.JScrollPane();
        jTreeNorma = new javax.swing.JTree();
        btnDodaj = new javax.swing.JButton();
        btnPromjeni = new javax.swing.JButton();
        btnObrisi = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstAnalizaCijena = new javax.swing.JList<>();
        jScrollPaneTableNorma = new javax.swing.JScrollPane();
        jTableNorma = new javax.swing.JTable();
        jPanelOperacija = new javax.swing.JPanel();
        lblOperacije1 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        lstOperacije = new javax.swing.JList<>();
        lblUkupnoRad = new javax.swing.JLabel();
        txtUkupnoRad = new javax.swing.JTextField();
        jPanelMaterijal = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstMaterijali = new javax.swing.JList<>();
        lblMaterijali = new javax.swing.JLabel();
        lblUkupnoMaterijal = new javax.swing.JLabel();
        txtUkupnoMaterijal = new javax.swing.JTextField();
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
        lblKoeficijentFirme = new javax.swing.JLabel();
        txtKoeficijentFirme = new javax.swing.JTextField();
        lblUkupnaCijena = new javax.swing.JLabel();
        txtUkupnaCijena = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(775, 500));
        setPreferredSize(new java.awt.Dimension(775, 500));

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

        jScrollPane1.setMinimumSize(new java.awt.Dimension(400, 23));
        jScrollPane1.setName(""); // NOI18N
        jScrollPane1.setPreferredSize(new java.awt.Dimension(650, 130));

        lstAnalizaCijena.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstAnalizaCijena.setToolTipText("");
        lstAnalizaCijena.setDropMode(javax.swing.DropMode.INSERT);
        lstAnalizaCijena.setMaximumSize(new java.awt.Dimension(1000, 1000));
        lstAnalizaCijena.setMinimumSize(new java.awt.Dimension(400, 0));
        lstAnalizaCijena.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstAnalizaCijenaMouseClicked(evt);
            }
        });
        lstAnalizaCijena.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstAnalizaCijenaValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstAnalizaCijena);

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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelNormaLayout.createSequentialGroup()
                        .addComponent(btnDodaj)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPromjeni)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnObrisi))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPaneTableNorma))
                .addContainerGap())
        );
        jPanelNormaLayout.setVerticalGroup(
            jPanelNormaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNormaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneTreNorma, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneTableNorma, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(jPanelNormaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDodaj)
                    .addComponent(btnPromjeni)
                    .addComponent(btnObrisi))
                .addContainerGap())
        );

        jtpNorme.addTab("Norme", jPanelNorma);

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

        javax.swing.GroupLayout jPanelOperacijaLayout = new javax.swing.GroupLayout(jPanelOperacija);
        jPanelOperacija.setLayout(jPanelOperacijaLayout);
        jPanelOperacijaLayout.setHorizontalGroup(
            jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOperacijaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUkupnoRad)
                    .addComponent(lblOperacije1))
                .addGap(18, 18, 18)
                .addGroup(jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 361, Short.MAX_VALUE)
                    .addComponent(txtUkupnoRad))
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
                .addContainerGap(346, Short.MAX_VALUE))
        );

        jtpNorme.addTab("Operacije", jPanelOperacija);

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
                .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 364, Short.MAX_VALUE)
                    .addComponent(txtUkupnoMaterijal))
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
                .addContainerGap(334, Short.MAX_VALUE))
        );

        jtpNorme.addTab("Materijali", jPanelMaterijal);

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

        lblKoeficijentFirme.setText("Koeficijent:");

        lblUkupnaCijena.setText("Ukupna cijena:");

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
                    .addComponent(lblKoeficijentFirme)
                    .addComponent(lblUkupnaCijena)
                    .addComponent(lblOznakaNorme))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUkupnaCijena)
                    .addComponent(txtKoeficijentFirme)
                    .addComponent(txtUkupanNormativVremena)
                    .addComponent(txtJedinicaMjere)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKoeficijentFirme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblKoeficijentFirme))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUkupnaCijena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUkupnaCijena))
                .addContainerGap(229, Short.MAX_VALUE))
        );

        jtpNorme.addTab("Ostalo", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpNorme)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpNorme)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lstAnalizaCijenaValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstAnalizaCijenaValueChanged

        if (evt.getValueIsAdjusting()) {
            return;
        }

        try {
            this.analizaCijene = lstAnalizaCijena.getSelectedValue();
            txtOznakaNorme.setText(lstAnalizaCijena.getSelectedValue().getOznakaNorme());
            tarOpisNorme.setText(lstAnalizaCijena.getSelectedValue().getOpis());
            txtJedinicaMjere.setText(lstAnalizaCijena.getSelectedValue().getJedinicaMjere());
            txtUkupanNormativVremena.setText(lstAnalizaCijena.getSelectedValue().getUkupanNormativVremena().toString());
            txtUkupnoRad.setText(lstAnalizaCijena.getSelectedValue().getUkupnaCijenaRad().toString());
            txtKoeficijentFirme.setText(lstAnalizaCijena.getSelectedValue().getKoeficijentFirme().toString());
            txtUkupnoMaterijal.setText(lstAnalizaCijena.getSelectedValue().getUkupnaCijenaMaterijal().toString());
            txtUkupnaCijena.setText(lstAnalizaCijena.getSelectedValue().getSveukupanIznos().toString());
            //            cmbStavkaTroskovnik.setSelectedItem(entitet.getStavkaTroskovnik());

            DefaultListModel<AnalizaRad> m = new DefaultListModel<>();
            lstOperacije.setModel(m);
            analizaCijene.getAnalizeRadova().forEach((s) -> {
                m.addElement(s);
            });

            DefaultListModel<AnalizaMaterijal> n = new DefaultListModel<>();
            lstMaterijali.setModel(n);
            analizaCijene.getAnalize_materijala().forEach((s) -> {
                n.addElement(s);
            });

        } catch (Exception e) {
        }
        repaint();
    }//GEN-LAST:event_lstAnalizaCijenaValueChanged

    private void lstAnalizaCijenaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstAnalizaCijenaMouseClicked
        if (SwingUtilities.isRightMouseButton(evt)) {
            int index = lstAnalizaCijena.locationToIndex(evt.getPoint());
            analizaCijene = lstAnalizaCijena.getModel().getElementAt(index);
            lstAnalizaCijena.setSelectionInterval(index, index);
            lstAnalizaCijena.setSelectionBackground(new Color(184, 207, 229));
            menu.show(evt.getComponent(), evt.getX(), evt.getY());

            System.out.println(lstAnalizaCijena.getModel().getElementAt(index));
        }
    }//GEN-LAST:event_lstAnalizaCijenaMouseClicked

    private void btnObrisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObrisiActionPerformed
        if (lstAnalizaCijena.getSelectedValue() == null) {
            JOptionPane.showConfirmDialog(rootPane, "Prvo odaberite stavku");
        }
//        obrisi();
    }//GEN-LAST:event_btnObrisiActionPerformed

    private void btnPromjeniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPromjeniActionPerformed
        if (lstAnalizaCijena.getSelectedValue() == null) {
            JOptionPane.showConfirmDialog(rootPane, "Prvo odaberite stavku");
        }
//        spremi();
    }//GEN-LAST:event_btnPromjeniActionPerformed

    private void btnDodajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDodajActionPerformed
        analizaCijene = new AnalizaCijene();
//        spremi();
        repaint();
    }//GEN-LAST:event_btnDodajActionPerformed

    private void jTreeNormaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTreeNormaMouseClicked
        if (SwingUtilities.isRightMouseButton(evt)) {
//            int index = jTreeNorma.getSelectionRows();
//                    .locationToIndex(evt.getPoint());
//            analizaCijene = lstAnalizaCijena.getModel().getElementAt(index);
//            lstAnalizaCijena.setSelectionInterval(index, index);
//            lstAnalizaCijena.setSelectionBackground(new Color(184, 207, 229));
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTreeNorma.getLastSelectedPathComponent();
            if (node != null && node.isLeaf()) {
                menu.show(evt.getComponent(), evt.getX(), evt.getY());
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
            int polozaj = node.getIndex(node);
            System.out.println("node1.getIndex(node1) " + node.getIndex(node));
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

            jTreeNorma.setSelectionInterval(node1.getIndex(node1), node1.getIndex(node1));
            jTreeNorma.getRowForPath(jTreeNorma.getSelectionPath());
            System.out.println("jTreeNorma.getRowForPath(jTreeNorma.getSelectionPath()) " + jTreeNorma.getRowForPath(jTreeNorma.getSelectionPath()));
            grupacijaNorme = (GrupacijaNorme) node.getUserObject();
            System.out.println("grupacijaNorme " + grupacijaNorme);
            rezultatiAnalizaCijena = grupacijaNorme.getAnalizeCijena();
            popunjavanjeTabliceAnalizaCijene(jTableNorma);
        }
    }//GEN-LAST:event_jTreeNormaValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDodaj;
    private javax.swing.JButton btnObrisi;
    private javax.swing.JButton btnPromjeni;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelMaterijal;
    private javax.swing.JPanel jPanelNorma;
    private javax.swing.JPanel jPanelOperacija;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPaneTableNorma;
    private javax.swing.JScrollPane jScrollPaneTreNorma;
    private javax.swing.JTable jTableNorma;
    private javax.swing.JTree jTreeNorma;
    private javax.swing.JTabbedPane jtpNorme;
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
    private javax.swing.JList<AnalizaCijene> lstAnalizaCijena;
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
