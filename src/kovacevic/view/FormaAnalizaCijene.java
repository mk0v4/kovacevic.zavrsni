/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import kovacevic.controller.ObradaAnalizaMaterijal;
import kovacevic.controller.ObradaAnalizaRad;
import kovacevic.controller.ObradaMaterijal;
import kovacevic.model.AnalizaCijene;
import kovacevic.model.AnalizaMaterijal;
import kovacevic.model.AnalizaRad;
import kovacevic.model.GrupacijaNorme;
import kovacevic.model.Materijal;
import kovacevic.model.Rad;
import kovacevic.model.StavkaTroskovnik;
import kovacevic.pomocno.HibernateUtil;
import kovacevic.pomocno.PorukaIznimke;
import kovacevic.pomocno.WrappableTable;

/**
 *
 * @author Marko Kovačević
 */
public class FormaAnalizaCijene extends JFrame {

//    iz liste u obradu i save
    private List<AnalizaMaterijal> analizeMaterijala = new ArrayList<>();
//    iz liste u obradu i save
    private ObradaAnalizaRad obradaAnalizaRad;
    private AnalizaRad analizaRad;
    private List<AnalizaRad> rezultatiAnalizaRad = new ArrayList<>();
    private List<StavkaTroskovnik> stavkaTroskovnik;

    private ObradaAnalizaCijene obradaAnalizaCijene;
    private AnalizaCijene analizaCijene;
    private List<AnalizaCijene> rezultatiAnalizaCijena;

    private List<GrupacijaNorme> grupacijaNormeLista;
    private GrupacijaNorme grupacijaNorme;

    private ObradaMaterijal obradaMaterijal;
    private Materijal materijal;
    private List<Materijal> rezultatiMaterijal;

    private ObradaAnalizaMaterijal obradaAnalizaMaterijal;
    private AnalizaMaterijal analizaMaterijal;
    private List<AnalizaMaterijal> rezultatiAnalizaMaterijala = new ArrayList<>();

    private boolean dodaj = false;
    private boolean promijeni = false;
    private boolean obrisi = false;

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
        obradaMaterijal = new ObradaMaterijal();
        obradaAnalizaRad = new ObradaAnalizaRad();
        obradaAnalizaMaterijal = new ObradaAnalizaMaterijal();
//        rezultatiAnalizaRad = analizaCijene.getAnalizeRadova();
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
        tblMaterijal = new WrappableTable(tblMaterijal, tblMaterijal.getModel(), jspMaterijal);
        tblDodavanjeMaterijal = new WrappableTable(tblDodavanjeMaterijal, tblDodavanjeMaterijal.getModel(),
                jScrollPaneMaterijal);

        popunjavanjeTabliceAnalizaCijene(jTableNorma);
        popuniGrupeRadova();
        ucitajMaterijal();

    }

    protected void ucitajAnalizaRad(AnalizaCijene odabranaAnalizaCijene) {
        rezultatiAnalizaRad = obradaAnalizaRad.getListaRad(odabranaAnalizaCijene);
    }

    protected void ucitajAnalizaMaterijal(AnalizaCijene odabranaAnalizaCijene) {
        rezultatiAnalizaMaterijala = obradaAnalizaMaterijal.getListaAnalizaMaterijal(odabranaAnalizaCijene);
    }

    protected void ucitajMaterijal() {
        rezultatiMaterijal = obradaMaterijal.getListaMaterijal(materijal);
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
            System.out.println("jTreeNorma.getSelectionPath() " + jTreeNorma
                    .getRowForPath(jTreeNorma.getSelectionPath()));
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
//  spremanje
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
            jFrameEditAnalizaCijene.setTitle("Analiza Cijene - Dodavanje nove");
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
            jFrameEditAnalizaCijene.setTitle("Analiza Cijene - Promjena");
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

    /**
     * Ispunjavanje panela za unos
     */
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

    /**
     * Ispunjavanje panela sa izmjenama
     */
    private void ispunjavanjePromjenaAnalizaCijena() {
        int row = jTableNorma.getSelectedRow();
        if (row != -1) {
            DefaultTableModel model = (DefaultTableModel) jTableNorma.getModel();
            analizaCijene = rezultatiAnalizaCijena.get(jTableNorma.convertRowIndexToModel(row));
            ucitajAnalizaRad(analizaCijene);
            ucitajAnalizaMaterijal(analizaCijene);
            txtOznakaNorme.setText(model.getValueAt(row, 1).toString());
            tarOpisNorme.setText(model.getValueAt(row, 2).toString());
            txtJedinicaMjere.setText(model.getValueAt(row, 3).toString());
            txtUkupanNormativVremena.setText(model.getValueAt(row, 4).toString());
        } else {
            analizaCijene = null;
        }
        txtKoeficijentFirme.setText(BigDecimal.ONE.toString());

//        rezultatiAnalizaRad.addAll(analizaCijene.getAnalizeRadova());
        popunjavanjeTabliceOperacije(tblOperacije);
        popunjavanjeTabliceGrupeRada(tblGrupeRada);
        popunjavanjeTabliceMaterijali(tblMaterijal);
        zbrojiCijenaRad();
        ukupnaCijenaRad();
        zbrojiCijenaMaterijal();
        ukupanNormativVremena();
    }

    protected void dpoAnalizaCijene() {

//      - kod dodavanja i promjene napravit abakusiranje operacija i materijala; kod brisanja nije
//      - brisanje analizaRada i analieMaterijala veze
//      - rad i materijal ostaje
//    
//    @ManyToOne
//    private StavkaTroskovnik stavkaTroskovnik;
//
        String nfePoruka = "";
        String dodatakNaslovu = "";
        analizaCijene.setGrupacijaNorme(grupacijaNorme);
        analizaCijene.setOznakaNorme(txtOznakaNorme.getText());
        analizaCijene.setOpis(tarOpisNorme.getText());
        analizaCijene.setJedinicaMjere(txtJedinicaMjere.getText());

        analizaCijene.setAnalizeRadova(rezultatiAnalizaRad);
//        dpoAnalizaRad(rezultatiAnalizaRad);

        analizaCijene.setUkupnaCijenaRad(new BigDecimal(txtCijenaRada.getText()));
        if (txtKoeficijentFirme.getText().trim().equals("")) {
            analizaCijene.setKoeficijentFirme(null);
        } else {
            try {
                analizaCijene.setKoeficijentFirme(new BigDecimal(txtKoeficijentFirme.getText()));
            } catch (NumberFormatException nfe) {
                nfePoruka = "Unešena vrijednost polja " + lblKoeficijentFirme.getText().replace(":", "") + " ne odgovara numeričkom unosu.";
            }
        }
        analizaCijene.setUkupanNormativVremena(new BigDecimal(txtUkupanNormativVremena.getText()));

        analizaCijene.setAnalizeMaterijala(rezultatiAnalizaMaterijala);
        analizaCijene.setUkupnaCijenaMaterijal(new BigDecimal(txtUkupnoMaterijal.getText()));

        analizaCijene.setSveukupanIznos(new BigDecimal(txtUkupnaCijenaPoJedinici.getText()));

        try {
            if (dodaj == true) {
                dodatakNaslovu = " - Dodavanje novog";
                obradaAnalizaCijene.spremi(analizaCijene);
            }
            if (promijeni == true) {
                dodatakNaslovu = " - Promijena";
                obradaAnalizaCijene.promijeni(analizaCijene);
            }
            if (obrisi == true) {
                dodatakNaslovu = " - Brisanje";
                obradaAnalizaCijene.obrisi(analizaCijene);
            }
        } catch (PorukaIznimke pi) {
            JOptionPane.showMessageDialog(rootPane, (nfePoruka.trim().equals("") ? "" : nfePoruka + "\n") + pi.getOpis() + pi.getGreska(),
                    getTitle() + dodatakNaslovu, HEIGHT);
//            if (pi.getGreska().contains(ObradaRad.GRUPA_RADOVA)) {
//                txtRadGrupaRadova.requestFocus();
//            } else if (pi.getGreska().contains(ObradaRad.KATEGORIJA_RAD)) {
//                txtRadKategorijaRad.requestFocus();
//            } else if (pi.getGreska().contains(ObradaRad.CIJENA_RAD)) {
//                txtRadCijena.requestFocus();
//            }
            return;
        }
        popunjavanjeJTree();
    }

    private void ukupanNormativVremena() {
        BigDecimal zbrojenNormativVremena = BigDecimal.ZERO;
        if (analizaCijene != null) {
            for (AnalizaRad analizaRad : rezultatiAnalizaRad) {
                zbrojenNormativVremena = zbrojenNormativVremena.add(analizaRad.getJedinicniNormativVremena());
            }
        }
        txtUkupanNormativVremena.setText(zbrojenNormativVremena.toString());
//        System.out.println("zbrojenNormativVremena.toString() " + zbrojenNormativVremena.toString());
    }

    private void ukupnaCijenaRad() {
        BigDecimal koefFirme = new BigDecimal(txtKoeficijentFirme.getText());
        BigDecimal cijenaRadova = new BigDecimal(txtCijenaRada.getText());
        txtUkupnaCijena.setText(koefFirme.multiply(cijenaRadova).toString());
        if (!txtUkupnoMaterijal.getText().trim().equals("") && !txtUkupnaCijena.getText().trim().equals("")) {
            BigDecimal zbrojenMaterijal = new BigDecimal(txtUkupnoMaterijal.getText());
            BigDecimal ukupnaCijena = new BigDecimal(txtUkupnaCijena.getText());
            txtUkupnaCijenaPoJedinici.setText(zbrojenMaterijal.add(ukupnaCijena).toString());
        }
    }

    private void zbrojiCijenaRad() {
        BigDecimal zbrojenaCijenaRad = BigDecimal.ZERO;
        if (analizaCijene != null) {
            for (AnalizaRad analizaRad : rezultatiAnalizaRad) {
                zbrojenaCijenaRad = zbrojenaCijenaRad.add(analizaRad.getCijenaVrijeme());
            }
        }
        txtCijenaRada.setText(zbrojenaCijenaRad.toString());
    }

    private void popunjavanjeTabliceGrupeRada(JTable tablica) {
        String[] coulumnName = {"Grupe radnika", "Kategorija rada", "Ukupno vrijeme grupe",
            "Ukupna cijena grupe"};
        DefaultTableModel model = (DefaultTableModel) tablica.getModel();
        model.setRowCount(0);
        model.setColumnIdentifiers(coulumnName);
        ArrayList<GrupaRadova> listaGrupeRadova = new ArrayList<>();
        if (analizaCijene != null) {
            for (AnalizaRad analizaRad : rezultatiAnalizaRad) {
                boolean imaDuplikat = false;
                if (listaGrupeRadova.size() > 0) {
                    GrupaRadova grupaRad = new GrupaRadova();
                    grupaRad.setGrupaRadova(analizaRad.getRad().getGrupaRadova());
                    grupaRad.setKategorijaGrupaRadova(analizaRad.getRad().getKategorijaRad());
                    grupaRad.setJedinicniNormativVremenaGrupaRadova(analizaRad.getJedinicniNormativVremena());
                    grupaRad.setCijenaGrupaRadova(analizaRad.getCijenaVrijeme());

                    BigDecimal zbrojenoVrijeme = BigDecimal.ZERO;
                    BigDecimal zbrojenaCijena = BigDecimal.ZERO;
                    int i = 0;
                    for (GrupaRadova grupaRadova : listaGrupeRadova) {
                        if (grupaRadova.getGrupaRadova().matches(analizaRad.getRad().getGrupaRadova())
                                && grupaRadova.getKategorijaGrupaRadova()
                                        .matches(analizaRad.getRad().getKategorijaRad())) {
                            imaDuplikat = true;
                            zbrojenoVrijeme = grupaRadova.getJedinicniNormativVremenaGrupaRadova().add(
                                    analizaRad
                                            .getJedinicniNormativVremena());
                            zbrojenaCijena = grupaRadova.getCijenaGrupaRadova().add(analizaRad
                                    .getCijenaVrijeme());
                            break;
                        }
                        i++;
                    }

                    if (imaDuplikat == false) {
                        listaGrupeRadova.add(grupaRad);
                    } else {
                        grupaRad.setJedinicniNormativVremenaGrupaRadova(zbrojenoVrijeme);
                        grupaRad.setCijenaGrupaRadova(zbrojenaCijena);
                        listaGrupeRadova.set(i, grupaRad);
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
        tablica.getColumnModel().getColumn(0).setCellRenderer(new WrappableTable.WrappableTableRenderer(
                tablica) {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    setBackground(new Color(184, 207, 229));
                } else {
                    setBackground(Color.WHITE);
                }
                return this;
            }

        });
        tablica.getColumnModel().getColumn(1).setCellRenderer(new WrappableTable.WrappableTableRenderer(
                tablica) {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    setBackground(new Color(184, 207, 229));
                } else {
                    setBackground(Color.WHITE);
                }
                return this;
            }

        });
        tablica.getColumnModel().getColumn(2).setCellRenderer(new WrappableTable.WrappableTableRenderer(
                tablica) {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    setBackground(new Color(184, 207, 229));
                } else {
                    setBackground(Color.WHITE);
                }
                return this;
            }

        });
        tablica.getColumnModel().getColumn(3).setCellRenderer(new WrappableTable.WrappableTableRenderer(
                tablica) {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    setBackground(new Color(184, 207, 229));
                } else {
                    setBackground(Color.WHITE);
                }
                return this;
            }

        });
        tablica.getColumnModel().getColumn(4).setCellRenderer(new WrappableTable.WrappableTableRenderer(
                tablica) {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    setBackground(new Color(184, 207, 229));
                } else {
                    setBackground(Color.WHITE);
                }
                return this;
            }

        });

        ucitajAnalizaRad(analizaCijene);
        if (analizaCijene != null) {
            for (AnalizaRad analizaRad : rezultatiAnalizaRad) {
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

    private void zbrojiCijenaMaterijal() {
        BigDecimal zbrojenaCijenaMaterijal = BigDecimal.ZERO;
        if (analizaCijene != null) {
            for (AnalizaMaterijal analizaMaterijal : rezultatiAnalizaMaterijala/*analizaCijene.getAnalizeMaterijala()*/) {
                zbrojenaCijenaMaterijal = zbrojenaCijenaMaterijal.add(analizaMaterijal.getCijenaMaterijal());
            }
        }
        txtUkupnoMaterijal.setText(zbrojenaCijenaMaterijal.toString());

        if (!txtUkupnaCijena.getText().trim().equals("")) {
            BigDecimal ukupnaCijena = new BigDecimal(txtUkupnaCijena.getText());
            txtUkupnaCijenaPoJedinici.setText(zbrojenaCijenaMaterijal.add(ukupnaCijena).toString());
        }
    }

    private void popunjavanjeTabliceMaterijali(JTable tablica) {
        String[] coulumnName = {"Grupa materijala", "Proizvođač", "Oznaka",
            "Jedinica mjere", "Jedinična Cijena", "Količina", "Cijena"};
        DefaultTableModel model = (DefaultTableModel) tablica.getModel();
        model.setRowCount(0);
        model.setColumnIdentifiers(coulumnName);
        tablica.getColumnModel().getColumn(5).setCellRenderer(new WrappableTable.WrappableTableRenderer(
                tablica) {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    setBackground(new Color(184, 207, 229));
                } else {
                    setBackground(Color.WHITE);
                }
                return this;
            }
        });
        if (analizaCijene != null) {

            for (AnalizaMaterijal analizaMaterijal : rezultatiAnalizaMaterijala) {
                model.addRow(new Object[]{
                    analizaMaterijal.getMaterijal().getGrupaMaterijal(),
                    analizaMaterijal.getMaterijal().getProizvodac(),
                    analizaMaterijal.getMaterijal().getOznaka(),
                    analizaMaterijal.getMaterijal().getJedinicaMjereAmbalaza(),
                    analizaMaterijal.getJedinicnaCijenaMaterijal(),
                    analizaMaterijal.getKolicina(),
                    analizaMaterijal.getCijenaMaterijal()});
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
        grupacijaNormeLista = HibernateUtil.getSession().createQuery(
                "from GrupacijaNorme a where a.obrisan=false order by a.oznakaNorme asc").list();

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
                    node2 = new NameOfJTreeNode(grupacijaNorme.getOznakaNorme() + " " + grupacijaNorme
                            .getOpis(),
                            grupacijaNorme);
                    childAt.add(node2);
                }
            }
        }
//        sortTree(root);
        model.setRoot(root);
    }

    private static boolean matchingCharactes(String string1, String string2, int numberOfChar) {
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
        jPanelOsnovno = new javax.swing.JPanel();
        lblOznakaNorme = new javax.swing.JLabel();
        txtOznakaNorme = new javax.swing.JTextField();
        lblOpisNorme = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tarOpisNorme = new javax.swing.JTextArea();
        lblJedinicaMjere = new javax.swing.JLabel();
        txtJedinicaMjere = new javax.swing.JTextField();
        lblUkupnoVrijeme = new javax.swing.JLabel();
        txtUkupanNormativVremena = new javax.swing.JTextField();
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
        btnDodajOperaciju = new javax.swing.JButton();
        btnObrisiOperaciju = new javax.swing.JButton();
        btnKalkulirajOperacije = new javax.swing.JButton();
        jPanelMaterijal = new javax.swing.JPanel();
        lblMaterijali = new javax.swing.JLabel();
        lblUkupnoMaterijal = new javax.swing.JLabel();
        txtUkupnoMaterijal = new javax.swing.JTextField();
        jspMaterijal = new javax.swing.JScrollPane();
        tblMaterijal = new javax.swing.JTable();
        btnDodajMaterijal = new javax.swing.JButton();
        btnKalkulirajMaterijal = new javax.swing.JButton();
        btnObrisiMaterijal = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnObrisi = new javax.swing.JButton();
        btnPromjeni = new javax.swing.JButton();
        btnDodaj = new javax.swing.JButton();
        btnRadUpute = new javax.swing.JButton();
        jFrameDodavanjeMaterijala = new javax.swing.JFrame();
        jPanelDodavanjeMaterijal = new javax.swing.JPanel();
        jScrollPaneMaterijal = new javax.swing.JScrollPane();
        tblDodavanjeMaterijal = new javax.swing.JTable();
        jPanelNorma = new javax.swing.JPanel();
        jScrollPaneTreNorma = new javax.swing.JScrollPane();
        jTreeNorma = new javax.swing.JTree();
        jScrollPaneTableNorma = new javax.swing.JScrollPane();
        jTableNorma = new javax.swing.JTable();

        jFrameEditAnalizaCijene.setMinimumSize(new java.awt.Dimension(650, 440));

        jTabbedPane1.setMinimumSize(new java.awt.Dimension(600, 400));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(600, 400));

        jPanelOsnovno.setMinimumSize(new java.awt.Dimension(500, 251));
        jPanelOsnovno.setPreferredSize(new java.awt.Dimension(500, 251));

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

        lblOznakaGrupeNorme.setText("Oznaka grupe norme:");

        txtOznakaGrupeNorme.setEditable(false);

        lblUkupnaCijenaPoJedinici.setText("Ukupna cijena po jedinici:");

        txtUkupnaCijenaPoJedinici.setEditable(false);

        javax.swing.GroupLayout jPanelOsnovnoLayout = new javax.swing.GroupLayout(jPanelOsnovno);
        jPanelOsnovno.setLayout(jPanelOsnovnoLayout);
        jPanelOsnovnoLayout.setHorizontalGroup(
            jPanelOsnovnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOsnovnoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelOsnovnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOpisNorme)
                    .addComponent(lblJedinicaMjere)
                    .addComponent(lblUkupnoVrijeme)
                    .addComponent(lblOznakaNorme)
                    .addComponent(lblOznakaGrupeNorme)
                    .addComponent(lblUkupnaCijenaPoJedinici))
                .addGap(18, 18, 18)
                .addGroup(jPanelOsnovnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUkupnaCijenaPoJedinici)
                    .addComponent(txtUkupanNormativVremena)
                    .addComponent(txtJedinicaMjere)
                    .addComponent(txtOznakaNorme)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                    .addComponent(txtOznakaGrupeNorme))
                .addContainerGap())
        );
        jPanelOsnovnoLayout.setVerticalGroup(
            jPanelOsnovnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOsnovnoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelOsnovnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOznakaGrupeNorme)
                    .addComponent(txtOznakaGrupeNorme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelOsnovnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOznakaNorme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblOznakaNorme))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelOsnovnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOpisNorme)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelOsnovnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtJedinicaMjere, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblJedinicaMjere))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelOsnovnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUkupanNormativVremena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUkupnoVrijeme))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelOsnovnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUkupnaCijenaPoJedinici, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUkupnaCijenaPoJedinici))
                .addGap(42, 42, 42))
        );

        jTabbedPane1.addTab("Osnovno", jPanelOsnovno);

        jPanelOperacija.setMinimumSize(new java.awt.Dimension(500, 251));
        jPanelOperacija.setPreferredSize(new java.awt.Dimension(500, 251));

        lblPopisOperacija.setText("Popis operacija:");

        tblOperacije.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "null", "null", "null", "null", "null", "null"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblOperacije.setColumnSelectionAllowed(true);
        tblOperacije.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                tblOperacijeComponentAdded(evt);
            }
        });
        tblOperacije.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tblOperacijeMouseExited(evt);
            }
        });
        jspOperacije.setViewportView(tblOperacije);
        tblOperacije.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        lblGrupeRada.setText("Grupe rada:");

        jspGrupeRada.setMinimumSize(new java.awt.Dimension(20, 60));

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
        tblGrupeRada.setMinimumSize(new java.awt.Dimension(100, 50));
        jspGrupeRada.setViewportView(tblGrupeRada);
        tblGrupeRada.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        lblCijenaRad.setText("Cijena rada:");

        txtCijenaRada.setEditable(false);

        lblKoeficijentFirme.setText("Koeficijent:");

        txtKoeficijentFirme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKoeficijentFirmeActionPerformed(evt);
            }
        });

        lblUkupnaCijena.setText("Ukupna cijena:");

        txtUkupnaCijena.setEditable(false);

        btnDodajOperaciju.setText("<html><center>Dodaj<br>Operaciju</center></html>");
        btnDodajOperaciju.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDodajOperacijuActionPerformed(evt);
            }
        });

        btnObrisiOperaciju.setText("<html><center>Obriši<br>Operaciju</center></html>");
        btnObrisiOperaciju.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnObrisiOperacijuActionPerformed(evt);
            }
        });

        btnKalkulirajOperacije.setText("<html><center>Izračunaj<br>i spremi</center></html>");
        btnKalkulirajOperacije.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKalkulirajOperacijeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelOperacijaLayout = new javax.swing.GroupLayout(jPanelOperacija);
        jPanelOperacija.setLayout(jPanelOperacijaLayout);
        jPanelOperacijaLayout.setHorizontalGroup(
            jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelOperacijaLayout.createSequentialGroup()
                .addGroup(jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOperacijaLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUkupnaCijena)
                            .addComponent(lblKoeficijentFirme)
                            .addComponent(lblCijenaRad)
                            .addComponent(lblGrupeRada)
                            .addComponent(lblPopisOperacija)))
                    .addGroup(jPanelOperacijaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(btnObrisiOperaciju, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnKalkulirajOperacije, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDodajOperaciju, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jspOperacije, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                    .addComponent(jspGrupeRada, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                    .addComponent(txtCijenaRada)
                    .addComponent(txtKoeficijentFirme)
                    .addComponent(txtUkupnaCijena))
                .addGap(5, 5, 5))
        );
        jPanelOperacijaLayout.setVerticalGroup(
            jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOperacijaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOperacijaLayout.createSequentialGroup()
                        .addComponent(lblPopisOperacija)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDodajOperaciju, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnKalkulirajOperacije, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnObrisiOperaciju, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jspOperacije, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelOperacijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblGrupeRada)
                    .addComponent(jspGrupeRada, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
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
                .addContainerGap())
        );

        jTabbedPane1.addTab("Operacije", jPanelOperacija);

        jPanelMaterijal.setMinimumSize(new java.awt.Dimension(500, 251));
        jPanelMaterijal.setPreferredSize(new java.awt.Dimension(500, 251));
        jPanelMaterijal.setRequestFocusEnabled(false);

        lblMaterijali.setText("Materijali:");

        lblUkupnoMaterijal.setText("<html>Ukupna cijena<br> materijala:</html>");

        txtUkupnoMaterijal.setEditable(false);

        tblMaterijal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "null", "null", "null", "null", "null", "null", "null"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMaterijal.setColumnSelectionAllowed(true);
        jspMaterijal.setViewportView(tblMaterijal);
        tblMaterijal.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        btnDodajMaterijal.setText("<html><center>Dodaj<br>Materijal</center></html>");
        btnDodajMaterijal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDodajMaterijalActionPerformed(evt);
            }
        });

        btnKalkulirajMaterijal.setText("<html><center>Izračunaj<br>i spremi</center></html>");
        btnKalkulirajMaterijal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKalkulirajMaterijalActionPerformed(evt);
            }
        });

        btnObrisiMaterijal.setText("<html><center>Obriši<br>Materijal</center></html>");
        btnObrisiMaterijal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnObrisiMaterijalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelMaterijalLayout = new javax.swing.GroupLayout(jPanelMaterijal);
        jPanelMaterijal.setLayout(jPanelMaterijalLayout);
        jPanelMaterijalLayout.setHorizontalGroup(
            jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMaterijalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMaterijalLayout.createSequentialGroup()
                        .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(btnDodajMaterijal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnKalkulirajMaterijal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnObrisiMaterijal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblMaterijali))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jspMaterijal, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                            .addComponent(txtUkupnoMaterijal)))
                    .addGroup(jPanelMaterijalLayout.createSequentialGroup()
                        .addComponent(lblUkupnoMaterijal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelMaterijalLayout.setVerticalGroup(
            jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMaterijalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMaterijalLayout.createSequentialGroup()
                        .addComponent(lblMaterijali)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDodajMaterijal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnKalkulirajMaterijal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnObrisiMaterijal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jspMaterijal, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblUkupnoMaterijal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUkupnoMaterijal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(124, 124, 124))
        );

        jTabbedPane1.addTab("Materijali", jPanelMaterijal);

        btnObrisi.setText("Obriši");
        btnObrisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnObrisiActionPerformed(evt);
            }
        });

        btnPromjeni.setText("Promjeni");
        btnPromjeni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPromjeniActionPerformed(evt);
            }
        });

        btnDodaj.setText("Dodaj");
        btnDodaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDodajActionPerformed(evt);
            }
        });

        btnRadUpute.setText("?");
        btnRadUpute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRadUputeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnDodaj)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPromjeni)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRadUpute)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnObrisi)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnDodaj)
                        .addComponent(btnPromjeni)
                        .addComponent(btnRadUpute))
                    .addComponent(btnObrisi)))
        );

        javax.swing.GroupLayout jFrameEditAnalizaCijeneLayout = new javax.swing.GroupLayout(jFrameEditAnalizaCijene.getContentPane());
        jFrameEditAnalizaCijene.getContentPane().setLayout(jFrameEditAnalizaCijeneLayout);
        jFrameEditAnalizaCijeneLayout.setHorizontalGroup(
            jFrameEditAnalizaCijeneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrameEditAnalizaCijeneLayout.createSequentialGroup()
                .addGroup(jFrameEditAnalizaCijeneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        jFrameEditAnalizaCijeneLayout.setVerticalGroup(
            jFrameEditAnalizaCijeneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrameEditAnalizaCijeneLayout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jFrameDodavanjeMaterijala.setMinimumSize(new java.awt.Dimension(650, 400));

        tblDodavanjeMaterijal.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDodavanjeMaterijal.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblDodavanjeMaterijal.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblDodavanjeMaterijal.getTableHeader().setReorderingAllowed(false);
        tblDodavanjeMaterijal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDodavanjeMaterijalMouseClicked(evt);
            }
        });
        jScrollPaneMaterijal.setViewportView(tblDodavanjeMaterijal);

        javax.swing.GroupLayout jPanelDodavanjeMaterijalLayout = new javax.swing.GroupLayout(jPanelDodavanjeMaterijal);
        jPanelDodavanjeMaterijal.setLayout(jPanelDodavanjeMaterijalLayout);
        jPanelDodavanjeMaterijalLayout.setHorizontalGroup(
            jPanelDodavanjeMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDodavanjeMaterijalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneMaterijal, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelDodavanjeMaterijalLayout.setVerticalGroup(
            jPanelDodavanjeMaterijalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDodavanjeMaterijalLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jScrollPaneMaterijal, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jFrameDodavanjeMaterijalaLayout = new javax.swing.GroupLayout(jFrameDodavanjeMaterijala.getContentPane());
        jFrameDodavanjeMaterijala.getContentPane().setLayout(jFrameDodavanjeMaterijalaLayout);
        jFrameDodavanjeMaterijalaLayout.setHorizontalGroup(
            jFrameDodavanjeMaterijalaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelDodavanjeMaterijal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jFrameDodavanjeMaterijalaLayout.setVerticalGroup(
            jFrameDodavanjeMaterijalaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelDodavanjeMaterijal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            DefaultMutableTreeNode node1 = (DefaultMutableTreeNode) jTreeNorma.getSelectionPath()
                    .getPathComponent(1);
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
        dodaj = true;
        analizaCijene = new AnalizaCijene();
        dpoAnalizaCijene();
//      polozaj jtree ???
        dodaj = false;
    }//GEN-LAST:event_btnDodajActionPerformed

    private void btnPromjeniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPromjeniActionPerformed
        promijeni = true;
        if (analizaCijene == null || jTableNorma.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Odaberite stavku vidljivu unutar tablice",
                    getTitle() + " - Promjena postojećeg", HEIGHT);
        }

        btnKalkulirajOperacijeActionPerformed(evt);
        btnKalkulirajMaterijalActionPerformed(evt);
        dpoAnalizaCijene();

        promijeni = false;
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

    private void btnDodajOperacijuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDodajOperacijuActionPerformed
        DefaultTableModel model = (DefaultTableModel) tblOperacije.getModel();
        model.addRow(new Object[]{"0", "", "", "", "", ""});
    }//GEN-LAST:event_btnDodajOperacijuActionPerformed

    private void btnObrisiOperacijuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObrisiOperacijuActionPerformed
        int row = tblOperacije.getSelectedRow();
        System.out.println("btnObrisiOperacijuActionPerformed()" + row);
        DefaultTableModel model = (DefaultTableModel) tblOperacije.getModel();

        if (row == -1) {
            return;
        }

//        if (row + 1 == analizaCijene.getAnalizeRadova().size()) {
        analizaRad = rezultatiAnalizaRad.get(tblOperacije.convertColumnIndexToModel(row));
        try {
            obradaAnalizaRad.obrisi(analizaRad);
            ucitajAnalizaRad(analizaCijene);
        } catch (PorukaIznimke ex) {
            Logger.getLogger(FormaAnalizaCijene.class.getName()).log(Level.SEVERE, null, ex);
        }
        //            analizaCijene.getAnalizeRadova().remove(row);
//        }
        model.removeRow(row);
        popunjavanjeTabliceGrupeRada(tblGrupeRada);

        zbrojiCijenaRad();
        ukupnaCijenaRad();
        ukupanNormativVremena();

        txtCijenaRada.repaint();
        tblGrupeRada.repaint();
        txtUkupnaCijena.repaint();
        txtUkupanNormativVremena.repaint();
    }//GEN-LAST:event_btnObrisiOperacijuActionPerformed

    private void tblOperacijeComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_tblOperacijeComponentAdded
        System.out.println("kovacevic.view.FormaAnalizaCijene.tblOperacijeComponentAdded()");
        zbrojiCijenaRad();
        ukupnaCijenaRad();
        ukupanNormativVremena();

        txtCijenaRada.repaint();
        tblGrupeRada.repaint();
        tblOperacije.repaint();
        txtUkupnaCijena.repaint();
        txtUkupanNormativVremena.repaint();
    }//GEN-LAST:event_tblOperacijeComponentAdded

    private Rad nadjiRad(String grupaRadova, String kategorijaRad) {
        Rad rad = (Rad) HibernateUtil.getSession().
                createQuery("from Rad a where "
                        + "a.obrisan=false and a.grupaRadova=:odabranaGrupaRadova "
                        + "and a.kategorijaRad = :kategorijaRad")
                .setParameter("odabranaGrupaRadova", grupaRadova)
                .setParameter("kategorijaRad", kategorijaRad).uniqueResult();
        return rad;
    }

    private void btnKalkulirajOperacijeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKalkulirajOperacijeActionPerformed
        String nfePoruka = "";
        DefaultTableModel model = (DefaultTableModel) tblOperacije.getModel();

        for (int i = 0; i < tblOperacije.getRowCount(); i++) {
            if (!tblOperacije.getValueAt(i, 0).toString().equals("")) {
                try {
                    Integer.valueOf(tblOperacije.getValueAt(i, 0).toString());
                } catch (NumberFormatException e) {
                    nfePoruka = "Unešena vrijednost " + (i + 1) + ". reda i 1. stupca \"" + tblOperacije
                            .getValueAt(i, 0).toString() + "\" ne odgovara numeričkom unosu.";
                    JOptionPane.showMessageDialog(jFrameEditAnalizaCijene, nfePoruka);
                    return;
                }
            }
            if (tblOperacije.getValueAt(i, 2) != "" || tblOperacije.getValueAt(i, 3) != "" || tblOperacije.getValueAt(i, 4) != "") {

                Rad redRad = nadjiRad(tblOperacije.getValueAt(i, 3).toString(), tblOperacije.getValueAt(i, 4).toString());
                if (redRad == null) {
                    JOptionPane.showMessageDialog(jFrameEditAnalizaCijene,
                            "Grupa radnik i kategroija rad nije pravilno unešena za " + (i + 1) + ". red");
                    return;
                }
                model.setValueAt(redRad.getCijena().multiply(new BigDecimal(tblOperacije.getValueAt(i, 2).toString().replace(",", "."))), i, 5);
                if (rezultatiAnalizaRad.size() < 0 || rezultatiAnalizaRad == null) {
                    AnalizaRad redAnalizaRad = new AnalizaRad();
                    redAnalizaRad.setBrojOperacije(Integer.valueOf(tblOperacije.getValueAt(i, 0).toString()));
                    redAnalizaRad.setOpisOperacije(tblOperacije.getValueAt(i, 1).toString());
                    redAnalizaRad.setJedinicniNormativVremena(new BigDecimal(tblOperacije.getValueAt(i, 2)
                            .toString().replace(",", ".")));
                    redAnalizaRad.setCijenaVrijeme(new BigDecimal(tblOperacije.getValueAt(i, 5).toString()));
                    redAnalizaRad.setRad(redRad);
                    redAnalizaRad.setAnalizaCijene(analizaCijene);
                    rezultatiAnalizaRad.add(redAnalizaRad);
                    try {
                        System.out.println("=== 1. ===");
                        obradaAnalizaRad.spremi(redAnalizaRad);
                    } catch (PorukaIznimke ex) {
                        JOptionPane.showMessageDialog(jFrameEditAnalizaCijene, ex.getOpis() + " " + ex.getGreska());
                    }
                } else {
                    if (i > rezultatiAnalizaRad.size() - 1) {

                        AnalizaRad redAnalizaRad = new AnalizaRad();
                        redAnalizaRad.setBrojOperacije(Integer.valueOf(tblOperacije.getValueAt(i, 0).toString()));
                        redAnalizaRad.setOpisOperacije(tblOperacije.getValueAt(i, 1).toString());
                        redAnalizaRad.setJedinicniNormativVremena(new BigDecimal(tblOperacije.getValueAt(i, 2)
                                .toString().replace(",", ".")));
                        redAnalizaRad.setCijenaVrijeme(new BigDecimal(tblOperacije.getValueAt(i, 5).toString()));
                        redAnalizaRad.setRad(redRad);
                        redAnalizaRad.setAnalizaCijene(analizaCijene);
                        rezultatiAnalizaRad.add(redAnalizaRad);
                        try {
                            System.out.println("=== 2. ===");
                            obradaAnalizaRad.spremi(redAnalizaRad);
                        } catch (PorukaIznimke ex) {
                            JOptionPane.showMessageDialog(jFrameEditAnalizaCijene, ex.getOpis() + " " + ex.getGreska());
                        }
                    }

                    obradaAnalizaRad.getListaRad(analizaCijene);
                    analizaRad = rezultatiAnalizaRad.get(tblOperacije.convertRowIndexToModel(i));
                    for (AnalizaRad analizaRadFor : rezultatiAnalizaRad) {
                        if (analizaRadFor.equals(analizaRad)) {
                            analizaRad.setBrojOperacije(Integer.valueOf(tblOperacije.getValueAt(i, 0).toString()));
                            analizaRad.setOpisOperacije(tblOperacije.getValueAt(i, 1).toString());
                            analizaRad.setJedinicniNormativVremena(new BigDecimal(tblOperacije.getValueAt(i, 2)
                                    .toString().replace(",", ".")));
                            analizaRad.setCijenaVrijeme(new BigDecimal(tblOperacije.getValueAt(i, 5).toString()));
                            analizaRad.setRad(redRad);
                            analizaRad.setAnalizaCijene(analizaCijene);
                        }
                        if (obradaAnalizaRad.provjeraDuplogUnosaAnalizaRad(analizaRad) == false) {
                            try {
                                System.out.println("=== 3. ===");
                                obradaAnalizaRad.spremi(analizaRad);
                            } catch (PorukaIznimke ex) {
                                JOptionPane.showMessageDialog(jFrameEditAnalizaCijene, ex.getOpis() + " " + ex.getGreska());
                            }
                        }
                    }

                }
            } else {
                model.removeRow(i);
            }
        }

        popunjavanjeTabliceOperacije(tblOperacije);
        popunjavanjeTabliceGrupeRada(tblGrupeRada);

        zbrojiCijenaRad();
        ukupnaCijenaRad();
        ukupanNormativVremena();

        txtCijenaRada.repaint();
        tblGrupeRada.repaint();
        txtUkupnaCijena.repaint();
        txtUkupanNormativVremena.repaint();
    }//GEN-LAST:event_btnKalkulirajOperacijeActionPerformed

    private void tblOperacijeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOperacijeMouseExited
//        WrappableTable t = (WrappableTable) tblOperacije;
//        System.out.println(tblOperacije.isEditing());
//        System.out.println("tblOperacije.getClass().getMethods() " + Arrays.toString(tblOperacije.getClass().getMethods()));

//KeyEvent.VK_ENTER;
//        System.out.println("kovacevic.view.FormaAnalizaCijene.tblOperacijeMouseExited()");
    }//GEN-LAST:event_tblOperacijeMouseExited

    private void txtKoeficijentFirmeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKoeficijentFirmeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKoeficijentFirmeActionPerformed

    private void popunjavanjeTabliceDodajMaterijal(JTable tablica) {
        String[] coulumnName = {"Rb. (Id)", "Grupe materijala", "Proizvođač",
            "Oznaka", "Pakovanje količina", "Jedinica mjere", "Cijena pakovanja", "Opis"};
        DefaultTableModel model = (DefaultTableModel) tablica.getModel();
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
    }

    private void btnDodajMaterijalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDodajMaterijalActionPerformed
//        DefaultTableModel model = (DefaultTableModel) tblMaterijal.getModel();
//        model.addRow(new Object[]{"", "", "", "", "", ""});
        jFrameDodavanjeMaterijala.setLocationRelativeTo(null);
        jFrameDodavanjeMaterijala.setTitle("Analiza Cijene - Dodavanje materijala");
        ArrayList<Materijal> materijalUnutarAnalize = new ArrayList<>();
        for (AnalizaMaterijal analizaMaterijal : rezultatiAnalizaMaterijala/*analizaCijene.getAnalizeMaterijala()*/) {
            materijalUnutarAnalize.add(analizaMaterijal.getMaterijal());
        }
        ucitajMaterijal();
        rezultatiMaterijal.removeAll(materijalUnutarAnalize);
        jFrameDodavanjeMaterijala.setVisible(true);
        popunjavanjeTabliceDodajMaterijal(tblDodavanjeMaterijal);
    }//GEN-LAST:event_btnDodajMaterijalActionPerformed

    private void btnKalkulirajMaterijalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKalkulirajMaterijalActionPerformed
        String nfePoruka = "";

        int i = 0;
        for (AnalizaMaterijal analizaMaterijal : rezultatiAnalizaMaterijala) {
            if (analizaMaterijal.getId() != null) {
                if (!tblMaterijal.getValueAt(i, 5).toString().equals("0")) {
                    try {
                        new BigDecimal(tblMaterijal.getValueAt(i, 5).toString().replace(",", "."));
                    } catch (NumberFormatException nfe) {
                        nfePoruka = "Unešena vrijednost " + (i + 1) + ". reda i 6. stupca \"" + tblMaterijal.getValueAt(i, 5).toString() + "\" ne odgovara numeričkom unosu.";
                        JOptionPane.showMessageDialog(jFrameEditAnalizaCijene, nfePoruka);
                        return;
                    }
                    Materijal redMaterijal = analizaMaterijal.getMaterijal();
                    analizaMaterijal.setJedinicnaCijenaMaterijal(new BigDecimal(tblMaterijal.getValueAt(i, 4).toString()));
                    analizaMaterijal.setKolicina(new BigDecimal(tblMaterijal.getValueAt(i, 5).toString().replace(",", ".")));
                    analizaMaterijal.setCijenaMaterijal(new BigDecimal(tblMaterijal.getValueAt(i, 4).toString()).multiply(new BigDecimal(tblMaterijal.getValueAt(i, 5).toString())));
                    analizaMaterijal.setMaterijal(redMaterijal);
                    analizaMaterijal.setAnalizaCijene(analizaCijene);

                    try {
                        if (obradaAnalizaMaterijal.provjeraDuplogUnosaAnalizaMaterijal(analizaMaterijal) == true) {
                            JOptionPane.showMessageDialog(jFrameEditAnalizaCijene, "Postoji unos u tablici: "
                                    + analizaMaterijal.getMaterijal().getOznaka() + " " + analizaMaterijal.getMaterijal().getOpis());
                            break;
                        }
                        obradaAnalizaMaterijal.spremi(analizaMaterijal);

                    } catch (PorukaIznimke ex) {
                        JOptionPane.showMessageDialog(jFrameEditAnalizaCijene, ex.getOpis() + " " + ex.getGreska());
                    }
                }
            } else {
                try {
                    new BigDecimal(tblMaterijal.getValueAt(i, 5).toString().replace(",", "."));
                } catch (NumberFormatException nfe) {
                    nfePoruka = "Unešena vrijednost " + (i + 1) + ". reda i 6. stupca \"" + tblMaterijal.getValueAt(i, 5).toString() + "\" ne odgovara numeričkom unosu.";
                    JOptionPane.showMessageDialog(jFrameEditAnalizaCijene, nfePoruka);
                    return;
                }
                if (!tblMaterijal.getValueAt(i, 5).toString().equals("0")) {

                    Materijal redMaterijal = analizaMaterijal.getMaterijal();
                    AnalizaMaterijal redAnalizaMaterijal = new AnalizaMaterijal();
                    redAnalizaMaterijal.setJedinicnaCijenaMaterijal(new BigDecimal(tblMaterijal.getValueAt(i, 4).toString()));
                    redAnalizaMaterijal.setKolicina(new BigDecimal(tblMaterijal.getValueAt(i, 5).toString().replace(",", ".")));
                    redAnalizaMaterijal.setCijenaMaterijal(new BigDecimal(tblMaterijal.getValueAt(i, 4).toString()).multiply(new BigDecimal(tblMaterijal.getValueAt(i, 5).toString())));
                    redAnalizaMaterijal.setMaterijal(redMaterijal);
                    redAnalizaMaterijal.setAnalizaCijene(analizaCijene);

                    try {
                        obradaAnalizaMaterijal.spremi(redAnalizaMaterijal);

                    } catch (PorukaIznimke ex) {
                        JOptionPane.showMessageDialog(jFrameEditAnalizaCijene, ex.getOpis() + " " + ex.getGreska());
                    }
                }
            }
            i++;
        }

        List<AnalizaMaterijal> listaAnalizaMaterijalaZaMakniti = new ArrayList<>();
        i = 0;
        for (AnalizaMaterijal analizaMaterijal : rezultatiAnalizaMaterijala) {
            if (tblMaterijal.getValueAt(i, 5).toString().equals("0") && analizaMaterijal.getId() == null) {
                listaAnalizaMaterijalaZaMakniti.add(analizaMaterijal);
            }
            i++;
        }

        obradaAnalizaMaterijal.getListaAnalizaMaterijal(analizaCijene);
        rezultatiAnalizaMaterijala.removeAll(listaAnalizaMaterijalaZaMakniti);
        analizeMaterijala.addAll(rezultatiAnalizaMaterijala);

        ucitajAnalizaMaterijal(analizaCijene);
        popunjavanjeTabliceMaterijali(tblMaterijal);
        zbrojiCijenaMaterijal();
    }//GEN-LAST:event_btnKalkulirajMaterijalActionPerformed

    private void btnObrisiMaterijalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObrisiMaterijalActionPerformed
        int row = tblMaterijal.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tblMaterijal.getModel();

        if (row == -1) {
            return;
        }

        analizaMaterijal = rezultatiAnalizaMaterijala.get(tblMaterijal.convertRowIndexToModel(row));

        try {
            obradaAnalizaMaterijal.obrisi(analizaMaterijal);
            ucitajAnalizaMaterijal(analizaCijene);
        } catch (PorukaIznimke ex) {
            JOptionPane.showMessageDialog(jFrameEditAnalizaCijene, ex.getOpis() + " " + ex.getGreska());
        }

        model.removeRow(row);
        popunjavanjeTabliceMaterijali(tblMaterijal);
    }//GEN-LAST:event_btnObrisiMaterijalActionPerformed

    private void tblDodavanjeMaterijalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDodavanjeMaterijalMouseClicked
        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            int row = tblDodavanjeMaterijal.getSelectedRow();
            if (row != -1) {
//                DefaultTableModel model = (DefaultTableModel) tblDodavanjeMaterijal.getModel();
                Materijal odabraniMaterijal = rezultatiMaterijal.get(tblDodavanjeMaterijal.convertRowIndexToModel(row));
                AnalizaMaterijal dodavanjeAnalizaMaterijal = new AnalizaMaterijal();
                dodavanjeAnalizaMaterijal.setMaterijal(odabraniMaterijal);
                dodavanjeAnalizaMaterijal.setJedinicnaCijenaMaterijal(odabraniMaterijal.getCijenaAmbalaza().divide(odabraniMaterijal.getKolicinaAmbalaza()));
                dodavanjeAnalizaMaterijal.setKolicina(BigDecimal.ZERO);
                dodavanjeAnalizaMaterijal.setCijenaMaterijal(dodavanjeAnalizaMaterijal.
                        getJedinicnaCijenaMaterijal().multiply(dodavanjeAnalizaMaterijal.getKolicina()));
                dodavanjeAnalizaMaterijal.setAnalizaCijene(analizaCijene);

//                analizaCijene.getAnalizeMaterijala().add(dodavanjeAnalizaMaterijal);
                rezultatiAnalizaMaterijala.add(dodavanjeAnalizaMaterijal);
                popunjavanjeTabliceMaterijali(tblMaterijal);
                jFrameDodavanjeMaterijala.setVisible(false);
            }
        }
    }//GEN-LAST:event_tblDodavanjeMaterijalMouseClicked

    private void btnRadUputeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRadUputeActionPerformed
        int odabranTab = jTabbedPane1.getSelectedIndex();
        String objasnjenje = "";
        switch (odabranTab) {
            case 0: objasnjenje = "";
                break;
            case 1: objasnjenje = "Pretraživanje:" + "\n"
                        + "- izvodi se tipkom enter u jednom od polja" + "\n"
                        + "- tipka " + /*btnRadResetirajTrazilicu.getText() +*/ " briše vrijesnosti unešene u polja i prikazuje sve rezultate" + "\n\n"
                        + "Tablica sa podatcima:" + "\n"
                        + "- jednim klikom miša označava se odabrani red" + "\n"
                        + "- dvostrukim klikom odabiru se vrijednosti odabranog reda i upisuju u polja kako bi se izvršile radnje promjene ili brisanja" + "\n"
                        + "- držanjem tipke alt i lijevim klikom odznačava se red u tablici i ispraznjuju se vrijednosti polja za radnje dodavanja, promjene i brisanja";
                break;
            case 2: objasnjenje = "";
                break;
        }

        JOptionPane.showMessageDialog(jFrameEditAnalizaCijene, objasnjenje,
                getTitle() + " - Upute za korištenje", JOptionPane.PLAIN_MESSAGE
        );
    }//GEN-LAST:event_btnRadUputeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDodaj;
    private javax.swing.JButton btnDodajMaterijal;
    private javax.swing.JButton btnDodajOperaciju;
    private javax.swing.JButton btnKalkulirajMaterijal;
    private javax.swing.JButton btnKalkulirajOperacije;
    private javax.swing.JButton btnObrisi;
    private javax.swing.JButton btnObrisiMaterijal;
    private javax.swing.JButton btnObrisiOperaciju;
    private javax.swing.JButton btnPromjeni;
    private javax.swing.JButton btnRadUpute;
    private javax.swing.JFrame jFrameDodavanjeMaterijala;
    private javax.swing.JFrame jFrameEditAnalizaCijene;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelDodavanjeMaterijal;
    private javax.swing.JPanel jPanelMaterijal;
    private javax.swing.JPanel jPanelNorma;
    private javax.swing.JPanel jPanelOperacija;
    private javax.swing.JPanel jPanelOsnovno;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPaneMaterijal;
    private javax.swing.JScrollPane jScrollPaneTableNorma;
    private javax.swing.JScrollPane jScrollPaneTreNorma;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableNorma;
    private javax.swing.JTree jTreeNorma;
    private javax.swing.JScrollPane jspGrupeRada;
    private javax.swing.JScrollPane jspMaterijal;
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
    private javax.swing.JTextArea tarOpisNorme;
    private javax.swing.JTable tblDodavanjeMaterijal;
    private javax.swing.JTable tblGrupeRada;
    private javax.swing.JTable tblMaterijal;
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

}
