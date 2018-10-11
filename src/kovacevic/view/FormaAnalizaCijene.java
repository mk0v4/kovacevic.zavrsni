/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.view;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import kovacevic.controller.ObradaAnalizaCijene;
import kovacevic.model.AnalizaCijene;
import kovacevic.model.AnalizaMaterijal;
import kovacevic.model.AnalizaRad;
import kovacevic.model.GrupacijaNorme;
import kovacevic.model.Rad;
import kovacevic.model.StavkaTroskovnik;
import kovacevic.pomocno.HibernateUtil;
import kovacevic.pomocno.TableCellListener;
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

    private final JComboBox cmbGrupeRadova, cmbKategorijaRad;

    /**
     * Creates new form FormaAnalizaCijene
     */
    public FormaAnalizaCijene() {
        obradaAnalizaCijene = new ObradaAnalizaCijene();
//        jTreeNorma.setRootVisible(false);
        initComponents();
        cmbGrupeRadova = new JComboBox();
        cmbKategorijaRad = new JComboBox();
        cmbGrupeRadova.addActionListener((ActionEvent e) -> {
            String grupaRad = (String) cmbGrupeRadova.getSelectedItem();
            popuniKategorijaRad(grupaRad);
        });

        setTitle("Analiza Cijena");

        popunjavanjeJTree();

        popupMenuJTree();
//        popupMenuJTable();

        jTableNorma = new WrappableTable(jTableNorma, jTableNorma.getModel(), jScrollPaneTableNorma);
        tblOperacije = new WrappableTable(tblOperacije, tblOperacije.getModel(), jspOperacije);
        tblGrupeRada = new WrappableTable(tblGrupeRada, tblGrupeRada.getModel(), jspGrupeRada);

        popunjavanjeTabliceAnalizaCijene(jTableNorma);
        popuniGrupeRadova();

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
        int row = jTableNorma.getSelectedRow();
        menuJTable.add(dodajNorma);
        if (row != -1) {
            menuJTable.add(promijeniNorma);
        }
//        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//        jFrameEditAnalizaCijene.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        jFrameEditAnalizaCijene.setLocationRelativeTo(null);

        dodajNorma.addActionListener((ActionEvent e) -> {
            jFrameEditAnalizaCijene.setVisible(true);
            ispunjavanjeAnalizaCijena();
//            ispunjavanjePromjenaAnalizaCijena();

//            grupacijaNorme1.addWindowListener(new WindowAdapter() {
//                @Override
//                public void windowClosing(WindowEvent arg0) {
//
//            });
        });
        promijeniNorma.addActionListener((ActionEvent e) -> {
            jFrameEditAnalizaCijene.setVisible(true);
            ispunjavanjeAnalizaCijena();
            ispunjavanjePromjenaAnalizaCijena();
//            grupacijaNorme.addWindowListener(new WindowAdapter() {
//                @Override
//                public void windowClosing(WindowEvent arg0) {
//                }
//            });
        });
    }

    private void ispunjavanjeAnalizaCijena() {
        analizaCijene = null;
        txtOznakaNorme.setText("");
        tarOpisNorme.setText("");
        txtJedinicaMjere.setText("");
        txtUkupanNormativVremena.setText("");
        if (grupacijaNorme != null) {
            txtOznakaGrupeNorme.setText(grupacijaNorme.getOznakaNorme() + " " + grupacijaNorme.getOpis());
        }
    }

    private void ispunjavanjePromjenaAnalizaCijena() {
        int row = jTableNorma.getSelectedRow();
        if (row != -1) {
            DefaultTableModel model = (DefaultTableModel) jTableNorma.getModel();
            analizaCijene = rezultatiAnalizaCijena.get(jTableNorma.convertRowIndexToModel(row));
            txtOznakaNorme.setText(model.getValueAt(row, 1).toString());
            tarOpisNorme.setText(model.getValueAt(row, 2).toString());
            txtJedinicaMjere.setText(model.getValueAt(row, 3).toString());
            txtUkupanNormativVremena.setText(model.getValueAt(row, 4).toString());
        } else {
            analizaCijene = null;
        }
        txtKoeficijentFirme.setText(BigDecimal.ONE.toString());
        popunjavanjeTabliceOperacije(tblOperacije);
        popunjavanjeTabliceGrupeRada(tblGrupeRada);
        zbrojiCijenaRad();
        ukupnaCijenaRad();
        ukupanNormativVremena();
    }

    private void ukupanNormativVremena() {
        BigDecimal zbrojenNormativVremena = BigDecimal.ZERO;
        if (analizaCijene != null) {
            for (AnalizaRad analizaRad : analizaCijene.getAnalizeRadova()) {
                zbrojenNormativVremena = zbrojenNormativVremena.add(analizaRad.getJedinicniNormativVremena());
            }
        }
        txtUkupanNormativVremena.setText(zbrojenNormativVremena.toString());
        System.out.println("zbrojenNormativVremena.toString() " + zbrojenNormativVremena.toString());
    }

    private void ukupnaCijenaRad() {
        BigDecimal koefFirme = new BigDecimal(txtKoeficijentFirme.getText());
        BigDecimal cijenaRadova = new BigDecimal(txtCijenaRada.getText());
        txtUkupnaCijena.setText(koefFirme.multiply(cijenaRadova).toString());
        System.out.println("koefFirme.multiply(cijenaRadova).toString() " + koefFirme.multiply(cijenaRadova).toString());
    }

    private void zbrojiCijenaRad() {
        BigDecimal zbrojenaCijenaRad = BigDecimal.ZERO;
        if (analizaCijene != null) {
            for (AnalizaRad analizaRad : analizaCijene.getAnalizeRadova()) {
                zbrojenaCijenaRad = zbrojenaCijenaRad.add(analizaRad.getCijenaVrijeme());
            }
        }
        txtCijenaRada.setText(zbrojenaCijenaRad.toString());
        System.out.println("analizaCijene.getAnalizeRadova() " + analizaCijene.getAnalizeRadova().toString());
        System.out.println("zbrojenaCijenaRad.toString() " + zbrojenaCijenaRad.toString());
    }

    private void popunjavanjeTabliceGrupeRada(JTable tablica) {
        String[] coulumnName = {"Grupe radnika", "Kategorija rada", "Ukupno vrijeme grupe",
            "Ukupna cijena grupe"};
        DefaultTableModel model = (DefaultTableModel) tablica.getModel();
        model.setRowCount(0);
        model.setColumnIdentifiers(coulumnName);
        ArrayList<GrupaRadova> listaGrupeRadova = new ArrayList<>();
        if (analizaCijene != null) {
            for (AnalizaRad analizaRad : analizaCijene.getAnalizeRadova()) {
                if (listaGrupeRadova.size() > 0) {
                    GrupaRadova grupaRad = new GrupaRadova();
                    for (GrupaRadova grupaRadova : listaGrupeRadova) {
                        if (!grupaRadova.getGrupaRadova().matches(analizaRad.getRad().getGrupaRadova())
                                || !grupaRadova.getKategorijaGrupaRadova().matches(analizaRad.getRad().getKategorijaRad())) {
                            grupaRad.setGrupaRadova(analizaRad.getRad().getGrupaRadova());
                            grupaRad.setKategorijaGrupaRadova(analizaRad.getRad().getKategorijaRad());
                            grupaRad.setJedinicniNormativVremenaGrupaRadova(analizaRad.getJedinicniNormativVremena());
                            grupaRad.setCijenaGrupaRadova(analizaRad.getCijenaVrijeme());
                        } else if (grupaRadova.getGrupaRadova().matches(analizaRad.getRad().getGrupaRadova())
                                || grupaRadova.getKategorijaGrupaRadova().matches(analizaRad.getRad().getKategorijaRad())) {
                            grupaRadova.setJedinicniNormativVremenaGrupaRadova(grupaRadova.getJedinicniNormativVremenaGrupaRadova().add(analizaRad.getJedinicniNormativVremena()));
                            grupaRadova.setCijenaGrupaRadova(grupaRadova.getCijenaGrupaRadova().add(analizaRad.getCijenaVrijeme()));
                        }
                    }
                    if (!listaGrupeRadova.toString().contains(analizaRad.getRad().getGrupaRadova())
                            || !listaGrupeRadova.toString().contains(analizaRad.getRad().getKategorijaRad())) {
                        listaGrupeRadova.add(grupaRad);
                    }
                } else {
                    GrupaRadova grupaRad = new GrupaRadova();
                    grupaRad.setGrupaRadova(analizaRad.getRad().getGrupaRadova());
                    grupaRad.setKategorijaGrupaRadova(analizaRad.getRad().getKategorijaRad());
                    grupaRad.setJedinicniNormativVremenaGrupaRadova(analizaRad.getJedinicniNormativVremena());
                    grupaRad.setCijenaGrupaRadova(analizaRad.getCijenaVrijeme());
                    listaGrupeRadova.add(grupaRad);
                }
            }
            for (GrupaRadova grupaRadova : listaGrupeRadova) {
                model.addRow(new Object[]{
                    grupaRadova.getGrupaRadova(),
                    grupaRadova.getKategorijaGrupaRadova(),
                    grupaRadova.getJedinicniNormativVremenaGrupaRadova(),
                    grupaRadova.getCijenaGrupaRadova()});
            }
        }
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

    private void popuniKategorijaRad(String odabranaGrupaRadova) {
        DefaultComboBoxModel<String> m = new DefaultComboBoxModel<>();
        cmbKategorijaRad.setModel(m);
        List<String> rad = HibernateUtil.getSession().
                createQuery("select a.kategorijaRad from Rad a where "
                        + "a.obrisan=false and a.grupaRadova=:odabranaGrupaRadova")
                .setParameter("odabranaGrupaRadova", odabranaGrupaRadova).list();
        m.addElement("Nije odabrano");
        for (String r : rad) {
            m.addElement(r);
        }
    }

    private class GrupaRadova {

        private String grupaRadova, kategorijaGrupaRadova;

        private BigDecimal cijenaGrupaRadova, jedinicniNormativVremenaGrupaRadova;

        public String getGrupaRadova() {
            return grupaRadova;
        }

        public void setGrupaRadova(String grupaRadova) {
            this.grupaRadova = grupaRadova;
        }

        public String getKategorijaGrupaRadova() {
            return kategorijaGrupaRadova;
        }

        public void setKategorijaGrupaRadova(String kategorijaGrupaRadova) {
            this.kategorijaGrupaRadova = kategorijaGrupaRadova;
        }

        public BigDecimal getCijenaGrupaRadova() {
            return cijenaGrupaRadova;
        }

        public void setCijenaGrupaRadova(BigDecimal cijenaGrupaRadova) {
            this.cijenaGrupaRadova = cijenaGrupaRadova;
        }

        public BigDecimal getJedinicniNormativVremenaGrupaRadova() {
            return jedinicniNormativVremenaGrupaRadova;
        }

        public void setJedinicniNormativVremenaGrupaRadova(BigDecimal jedinicniNormativVremenaGrupaRadova) {
            this.jedinicniNormativVremenaGrupaRadova = jedinicniNormativVremenaGrupaRadova;
        }

        @Override
        public String toString() {
            return grupaRadova + " " + kategorijaGrupaRadova + " "
                    + jedinicniNormativVremenaGrupaRadova + " " + cijenaGrupaRadova;
        }

    }

    private void popunjavanjeTabliceOperacije(JTable tablica) {
        String[] coulumnName = {"Rb. operacije", "Opis operacije", "Normativ vremena",
            "Grupa radnik", "Kategorija rad", "Cijena"};
        DefaultTableModel model = (DefaultTableModel) tablica.getModel();
        model.setRowCount(0);
        model.setColumnIdentifiers(coulumnName);
        TableColumn grupaRadova = tablica.getColumnModel().getColumn(3);
        TableColumn kategorijaRad = tablica.getColumnModel().getColumn(4);
        grupaRadova.setCellEditor(new DefaultCellEditor(cmbGrupeRadova));
        kategorijaRad.setCellEditor(new DefaultCellEditor(cmbKategorijaRad));
        if (analizaCijene != null) {
            analizaCijene.getAnalizeRadova();
            for (AnalizaRad analizaRad : analizaCijene.getAnalizeRadova()) {
                model.addRow(new Object[]{
                    analizaRad.getBrojOperacije(),
                    analizaRad.getOpisOperacije(),
                    analizaRad.getJedinicniNormativVremena(),
                    analizaRad.getRad().getGrupaRadova(),
                    analizaRad.getRad().getKategorijaRad(),
                    analizaRad.getCijenaVrijeme()});
            }

        }
    }

    private void popunjavanjeTabliceAnalizaCijene(JTable tablica) {
        String[] coulumnName = {"Rb. (Id)", "Oznaka norme", "Opis norme",
            "Jedinica mjere", "Ukupan normativ vremena"};
        DefaultTableModel model = (DefaultTableModel) tablica.getModel();
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
        jPaneOsnovno = new javax.swing.JPanel();
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
        lblOznakaGrupeNorme = new javax.swing.JLabel();
        txtOznakaGrupeNorme = new javax.swing.JTextField();
        lblUkupnaCijenaPoJedinici = new javax.swing.JLabel();
        txtUkupnaCijenaPoJedinici = new javax.swing.JTextField();
        jPanelOperacija = new javax.swing.JPanel();
        lblPopisOperacija = new javax.swing.JLabel();
        jspOperacije = new javax.swing.JScrollPane();
        tblOperacije = new javax.swing.JTable();
        lblGrupeRada = new javax.swing.JLabel();
        jspGrupeRada = new javax.swing.JScrollPane();
        tblGrupeRada = new javax.swing.JTable();
        lblCijenaRad = new javax.swing.JLabel();
        txtCijenaRada = new javax.swing.JTextField();
        lblKoeficijentFirme = new javax.swing.JLabel();
        txtKoeficijentFirme = new javax.swing.JTextField();
        lblUkupnaCijena = new javax.swing.JLabel();
        txtUkupnaCijena = new javax.swing.JTextField();
        btnDodajRed = new javax.swing.JButton();
        btnObrisiRed = new javax.swing.JButton();
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

        jFrameEditAnalizaCijene.setMinimumSize(new java.awt.Dimension(600, 400));

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(600, 400));

        jPaneOsnovno.setPreferredSize(new java.awt.Dimension(500, 251));

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

        txtUkupanNormativVremena.setEditable(false);

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

        lblOznakaGrupeNorme.setText("Oznaka grupe norme:");

        txtOznakaGrupeNorme.setEditable(false);

        lblUkupnaCijenaPoJedinici.setText("Ukupna cijena po jedinici:");

        txtUkupnaCijenaPoJedinici.setEditable(false);

        javax.swing.GroupLayout jPaneOsnovnoLayout = new javax.swing.GroupLayout(jPaneOsnovno);
        jPaneOsnovno.setLayout(jPaneOsnovnoLayout);
        jPaneOsnovnoLayout.setHorizontalGroup(
            jPaneOsnovnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPaneOsnovnoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPaneOsnovnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOpisNorme)
                    .addComponent(lblJedinicaMjere)
                    .addComponent(lblUkupnoVrijeme)
                    .addComponent(lblOznakaNorme)
                    .addComponent(lblOznakaGrupeNorme)
                    .addComponent(lblUkupnaCijenaPoJedinici))
                .addGap(18, 18, 18)
                .addGroup(jPaneOsnovnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUkupnaCijenaPoJedinici)
                    .addGroup(jPaneOsnovnoLayout.createSequentialGroup()
                        .addComponent(btnDodaj)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPromjeni)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 228, Short.MAX_VALUE)
                        .addComponent(btnObrisi))
                    .addComponent(txtUkupanNormativVremena)
                    .addComponent(txtJedinicaMjere)
                    .addComponent(txtOznakaNorme)
                    .addComponent(jScrollPane2)
                    .addComponent(txtOznakaGrupeNorme))
                .addContainerGap())
        );
        jPaneOsnovnoLayout.setVerticalGroup(
            jPaneOsnovnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPaneOsnovnoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPaneOsnovnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOznakaGrupeNorme)
                    .addComponent(txtOznakaGrupeNorme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPaneOsnovnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOznakaNorme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblOznakaNorme))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPaneOsnovnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOpisNorme)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPaneOsnovnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtJedinicaMjere, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblJedinicaMjere))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPaneOsnovnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUkupanNormativVremena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUkupnoVrijeme))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPaneOsnovnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUkupnaCijenaPoJedinici, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUkupnaCijenaPoJedinici))
                .addGap(31, 31, 31)
                .addGroup(jPaneOsnovnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDodaj)
                    .addComponent(btnPromjeni)
                    .addComponent(btnObrisi))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Osnovno", jPaneOsnovno);

        jPanelOperacija.setPreferredSize(new java.awt.Dimension(500, 251));

        lblPopisOperacija.setText("Popis operacija:");

        tblOperacije.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "null", "null", "null", "null", "null", "null"
            }
        ));
        tblOperacije.setColumnSelectionAllowed(true);
        tblOperacije.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                tblOperacijeComponentAdded(evt);
            }
        });
        jspOperacije.setViewportView(tblOperacije);
        tblOperacije.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        lblGrupeRada.setText("Grupe rada:");

        tblGrupeRada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "null", "null", "null"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGrupeRada.setColumnSelectionAllowed(true);
        jspGrupeRada.setViewportView(tblGrupeRada);
        tblGrupeRada.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        lblCijenaRad.setText("Cijena rada:");

        txtCijenaRada.setEditable(false);

        lblKoeficijentFirme.setText("Koeficijent:");

        lblUkupnaCijena.setText("Ukupna cijena:");

        txtUkupnaCijena.setEditable(false);

        btnDodajRed.setText("Dodaj red");
        btnDodajRed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDodajRedActionPerformed(evt);
            }
        });

        btnObrisiRed.setText("Obrisi red");
        btnObrisiRed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnObrisiRedActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelOperacijaLayout = new javax.swing.GroupLayout(jPanelOperacija);
        jPanelOperacija.setLayout(jPanelOperacijaLayout);
        jPanelOperacijaLayout.setHorizontalGroup(
            jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOperacijaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnObrisiRed)
                    .addComponent(btnDodajRed))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCijenaRada, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtKoeficijentFirme)
                    .addComponent(txtUkupnaCijena)
                    .addComponent(jspGrupeRada, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
                    .addComponent(jspOperacije, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(jPanelOperacijaLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblGrupeRada)
                    .addComponent(lblUkupnaCijena)
                    .addComponent(lblKoeficijentFirme)
                    .addComponent(lblPopisOperacija)
                    .addComponent(lblCijenaRad))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelOperacijaLayout.setVerticalGroup(
            jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOperacijaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOperacijaLayout.createSequentialGroup()
                        .addComponent(lblPopisOperacija)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDodajRed)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnObrisiRed)
                        .addGap(0, 48, Short.MAX_VALUE))
                    .addComponent(jspOperacije, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jspGrupeRada, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanelOperacijaLayout.createSequentialGroup()
                        .addComponent(lblGrupeRada)
                        .addGap(0, 56, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCijenaRada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCijenaRad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKoeficijentFirme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblKoeficijentFirme))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUkupnaCijena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUkupnaCijena))
                .addGap(87, 87, 87))
        );

        jTabbedPane1.addTab("Operacije", jPanelOperacija);

        jPanelMaterijal.setPreferredSize(new java.awt.Dimension(500, 251));
        jPanelMaterijal.setRequestFocusEnabled(false);

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
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(txtUkupnoMaterijal, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE))
                .addContainerGap())
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
                .addContainerGap(229, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Materijali", jPanelMaterijal);

        javax.swing.GroupLayout jFrameEditAnalizaCijeneLayout = new javax.swing.GroupLayout(jFrameEditAnalizaCijene.getContentPane());
        jFrameEditAnalizaCijene.getContentPane().setLayout(jFrameEditAnalizaCijeneLayout);
        jFrameEditAnalizaCijeneLayout.setHorizontalGroup(
            jFrameEditAnalizaCijeneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jFrameEditAnalizaCijeneLayout.setVerticalGroup(
            jFrameEditAnalizaCijeneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        jTableNorma.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableNorma.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableNormaMouseClicked(evt);
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
            grupacijaNorme = null;
            return;
        }
        if (node.isLeaf()) {
            grupacijaNorme = (GrupacijaNorme) node.getUserObject();
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
        int row = jTableNorma.getSelectedRow();
        if (SwingUtilities.isRightMouseButton(evt)) {
            if (grupacijaNorme == null) {
                JOptionPane.showMessageDialog(null, "odaberite grpu norme");
                return;
            }
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "odaberite normu unutar tablice");
                analizaCijene = null;
                return;
            }
            popupMenuJTable();
            menuJTable.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jTableNormaMouseClicked

    private void jScrollPaneTableNormaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPaneTableNormaMouseClicked
        int row = jTableNorma.getSelectedRow();
        if (SwingUtilities.isRightMouseButton(evt)) {
//            System.out.println("grupacijaNorme " + grupacijaNorme);
            if (grupacijaNorme == null) {
                System.out.println("odaberite gurpu norme");
                JOptionPane.showMessageDialog(null, "odaberite gurpu norme");
                return;
            }
            if (row == -1) {
                analizaCijene = null;
            }
            popupMenuJTable();
            menuJTable.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jScrollPaneTableNormaMouseClicked

    private void btnDodajRedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDodajRedActionPerformed
//        DefaultTableModel model = (DefaultTableModel) tblOperacije.getModel();

        Rad dummyRad = new Rad();
        dummyRad.setGrupaRadova("Radnik");
        dummyRad.setKategorijaRad("III");
        dummyRad.setCijena(new BigDecimal("55.00"));

        AnalizaRad dodajAnalizaRad = new AnalizaRad();
        dodajAnalizaRad.setOpisOperacije("Opsi operacije");
        dodajAnalizaRad.setBrojOperacije(1);
        dodajAnalizaRad.setJedinicniNormativVremena(new BigDecimal("5.00"));
        dodajAnalizaRad.setCijenaVrijeme(new BigDecimal("5.00"));
        dodajAnalizaRad.setRad(dummyRad);
        dodajAnalizaRad.setAnalizaCijene(analizaCijene);
        analizaCijene.getAnalizeRadova().add(dodajAnalizaRad);

        popunjavanjeTabliceOperacije(tblOperacije);
        popunjavanjeTabliceGrupeRada(tblGrupeRada);

//        model.addRow(new Object[]{dodajAnalizaRad.getBrojOperacije(), dodajAnalizaRad.getOpisOperacije(),
//            dodajAnalizaRad.getJedinicniNormativVremena(), dummyRad.getGrupaRadova(),
//            dummyRad.getKategorijaRad(), dodajAnalizaRad.getCijenaVrijeme()});
        System.out.println("analizaCijene.getAnalizeRadova() " + analizaCijene.getAnalizeRadova().toString());
    }//GEN-LAST:event_btnDodajRedActionPerformed

    private void btnObrisiRedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObrisiRedActionPerformed
        int row = tblOperacije.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tblOperacije.getModel();
        model.removeRow(row);
    }//GEN-LAST:event_btnObrisiRedActionPerformed

    private void tblOperacijeComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_tblOperacijeComponentAdded
        System.out.println("kovacevic.view.FormaAnalizaCijene.tblOperacijeComponentAdded()");
        int row = tblOperacije.getSelectedRow();
        int column = tblOperacije.getSelectedColumn();
        DefaultTableModel model = (DefaultTableModel) tblOperacije.getModel();
        Object valueRowColumn = tblOperacije.getValueAt(row, column);
        switch (column) {
            case 0: ccc
                tblOperacije.getValueAt(row, column);

        }

        zbrojiCijenaRad();
        ukupnaCijenaRad();
        ukupanNormativVremena();

        txtCijenaRada.repaint();
        tblGrupeRada.repaint();
        tblOperacije.repaint();
        txtUkupnaCijena.repaint();
        txtUkupanNormativVremena.repaint();
    }//GEN-LAST:event_tblOperacijeComponentAdded

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDodaj;
    private javax.swing.JButton btnDodajRed;
    private javax.swing.JButton btnObrisi;
    private javax.swing.JButton btnObrisiRed;
    private javax.swing.JButton btnPromjeni;
    private javax.swing.JFrame jFrameEditAnalizaCijene;
    private javax.swing.JPanel jPaneOsnovno;
    private javax.swing.JPanel jPanelMaterijal;
    private javax.swing.JPanel jPanelNorma;
    private javax.swing.JPanel jPanelOperacija;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPaneTableNorma;
    private javax.swing.JScrollPane jScrollPaneTreNorma;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableNorma;
    private javax.swing.JTree jTreeNorma;
    private javax.swing.JScrollPane jspGrupeRada;
    private javax.swing.JScrollPane jspOperacije;
    private javax.swing.JLabel lblCijenaRad;
    private javax.swing.JLabel lblGrupeRada;
    private javax.swing.JLabel lblJedinicaMjere;
    private javax.swing.JLabel lblKoeficijentFirme;
    private javax.swing.JLabel lblMaterijali;
    private javax.swing.JLabel lblOpisNorme;
    private javax.swing.JLabel lblOznakaGrupeNorme;
    private javax.swing.JLabel lblOznakaNorme;
    private javax.swing.JLabel lblPopisOperacija;
    private javax.swing.JLabel lblUkupnaCijena;
    private javax.swing.JLabel lblUkupnaCijenaPoJedinici;
    private javax.swing.JLabel lblUkupnoMaterijal;
    private javax.swing.JLabel lblUkupnoVrijeme;
    private javax.swing.JList<AnalizaMaterijal> lstMaterijali;
    private javax.swing.JTextArea tarOpisNorme;
    private javax.swing.JTable tblGrupeRada;
    private javax.swing.JTable tblOperacije;
    private javax.swing.JTextField txtCijenaRada;
    private javax.swing.JTextField txtJedinicaMjere;
    private javax.swing.JTextField txtKoeficijentFirme;
    private javax.swing.JTextField txtOznakaGrupeNorme;
    private javax.swing.JTextField txtOznakaNorme;
    private javax.swing.JTextField txtUkupanNormativVremena;
    private javax.swing.JTextField txtUkupnaCijena;
    private javax.swing.JTextField txtUkupnaCijenaPoJedinici;
    private javax.swing.JTextField txtUkupnoMaterijal;
    // End of variables declaration//GEN-END:variables
}
