/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.controller;

import java.util.ArrayList;
import java.util.List;
import kovacevic.model.Materijal;
import kovacevic.pomocno.HibernateUtil;
import kovacevic.pomocno.PorukaIznimke;

/**
 *
 * @author Marko Kovačević
 */
public class ObradaMaterijal {

    private HibernateObrada<Materijal> obrada;
    private ArrayList<String> ordersBys = new ArrayList<>();

    public static final String ENTITET_NULL = "vrijednost nije odabrana";
    public static final String GRUPA_MATERIJAL = "Grupa materijala";
    public static final String PROIZVODAC = "Proizvođač";
    public static final String OZNAKA = "Oznaka";
    public static final String OPIS = "Opis";
    public static final String JEDINICA_MJERE_AMBALAZA = "Jedinica mjere";
    public static final String KOLICINA_AMBALAZA = "Pakovanje količina";
    public static final String CIJENA_AMBALAZA = "Cijena pakovanja";

    private boolean brisanje = false;

    public ObradaMaterijal() {
        obrada = new HibernateObrada<>();
        ordersBys.add("grupaMaterijal");
        ordersBys.add("cijenaAmbalaza");
    }

    public boolean provjeraDuplogUnosaMaterijal(Materijal materijal) {
        List<Materijal> radIzBaze = HibernateUtil.getSession().createQuery("from Materijal where obrisan=false "
                + "and grupaMaterijal=:grupaMaterijal and proizvodac=:proizvodac and oznaka=:oznaka and opis=:opis "
                + "and jedinicaMjereAmbalaza=:jedinicaMjereAmbalaza and kolicinaAmbalaza=:kolicinaAmbalaza"
                + " and cijenaAmbalaza=:cijenaAmbalaza")
                .setParameter("grupaMaterijal", materijal.getGrupaMaterijal())
                .setParameter("proizvodac", materijal.getProizvodac())
                .setParameter("oznaka", materijal.getOznaka())
                .setParameter("opis", materijal.getOpis())
                .setParameter("jedinicaMjereAmbalaza", materijal.getJedinicaMjereAmbalaza())
                .setParameter("kolicinaAmbalaza", materijal.getKolicinaAmbalaza())
                .setParameter("cijenaAmbalaza", materijal.getCijenaAmbalaza()).list();
        return radIzBaze.size() > 0;
    }

    public Materijal spremi(Materijal materijal) throws PorukaIznimke {
        List<String> greske = new ArrayList<>();
        if (materijal == null) {
            throw new PorukaIznimke("Entitet mora biti odabran ", "Odaberite stavku unutar tablice", ENTITET_NULL);
        }
        if (brisanje == false) {
            if (materijal.getGrupaMaterijal().trim().length() == 0) {
                greske.add(GRUPA_MATERIJAL);
            }
            if (materijal.getProizvodac().trim().length() == 0) {
                greske.add(PROIZVODAC);
            }
            if (materijal.getOznaka().trim().length() == 0) {
                greske.add(OZNAKA);
            }
            if (materijal.getOpis().trim().length() == 0) {
                greske.add(OPIS);
            }
            if (materijal.getJedinicaMjereAmbalaza().trim().length() == 0) {
                greske.add(JEDINICA_MJERE_AMBALAZA);
            }
            if (materijal.getKolicinaAmbalaza() == null) {
                greske.add(KOLICINA_AMBALAZA);
            }
            if (materijal.getCijenaAmbalaza() == null) {
                greske.add(CIJENA_AMBALAZA);
            }
            if (materijal.getGrupaMaterijal().trim().length() != 0 || materijal.getProizvodac().trim().length() != 0
                    || materijal.getOznaka().trim().length() != 0 || materijal.getOpis().trim().length() != 0
                    || materijal.getJedinicaMjereAmbalaza().trim().length() != 0 || materijal.getKolicinaAmbalaza() != null
                    || materijal.getCijenaAmbalaza() != null) {
                if (provjeraDuplogUnosaMaterijal(materijal) == true) {
                    throw new PorukaIznimke("Rezultat mora biti različit u jednom od unosa",
                            "Postoji unos u tablici za ", GRUPA_MATERIJAL + ": " + materijal.getGrupaMaterijal() + ", "
                            + PROIZVODAC + ": " + materijal.getProizvodac() + ", "
                            + OZNAKA + ": " + materijal.getOznaka() + ", "
                            + KOLICINA_AMBALAZA + ": " + materijal.getKolicinaAmbalaza() + ", "
                            + JEDINICA_MJERE_AMBALAZA + ": " + materijal.getJedinicaMjereAmbalaza() + ", "
                            + CIJENA_AMBALAZA + ": " + materijal.getCijenaAmbalaza() + ", "
                            + OPIS + ": " + materijal.getOpis());
                }
            }
        }
        if (greske.size() > 0) {
            String joined = String.join(", ", greske);
            throw new PorukaIznimke("Podatci moraju biti unešani " + greske,
                    "Nisu unešeni sljedeći podatci: ", joined + ".");
        }
        System.out.println("brisanje " + brisanje);
        if (brisanje == true) {
            materijal.setObrisan(true);
        }
        return obrada.save(materijal);
    }

    public Materijal promijeni(Materijal materijal) throws PorukaIznimke {
        if (materijal == null) {
            throw new PorukaIznimke("Entitet mora biti odabran ", "Odaberite stavku unutar tablice", ENTITET_NULL);
        }
        if (materijal.getGrupaMaterijal().trim().length() != 0 || materijal.getProizvodac().trim().length() != 0
                || materijal.getOznaka().trim().length() != 0 || materijal.getOpis().trim().length() != 0
                || materijal.getJedinicaMjereAmbalaza().trim().length() != 0 || materijal.getKolicinaAmbalaza() != null
                || materijal.getCijenaAmbalaza() != null) {
            if (provjeraDuplogUnosaMaterijal(materijal) == true) {
                throw new PorukaIznimke("Rezultat mora biti različit u jednom od unosa", "Podatci nisu izmjenjeni za ",
                        GRUPA_MATERIJAL + ": " + materijal.getGrupaMaterijal() + ", "
                        + PROIZVODAC + ": " + materijal.getProizvodac() + ", "
                        + OZNAKA + ": " + materijal.getOznaka() + ", "
                        + KOLICINA_AMBALAZA + ": " + materijal.getKolicinaAmbalaza() + ", "
                        + JEDINICA_MJERE_AMBALAZA + ": " + materijal.getJedinicaMjereAmbalaza() + ", "
                        + CIJENA_AMBALAZA + ": " + materijal.getCijenaAmbalaza() + ", "
                        + OPIS + ": " + materijal.getOpis());
            }
        }
        return obrada.save(materijal);
    }

    public void obrisi(Materijal materijal) throws PorukaIznimke {
        brisanje = true;
        spremi(materijal);
        brisanje = false;
    }

    public List<Materijal> getListaMaterijal(Materijal materijal) {
        String query = "from Materijal where obrisan=false" + getOrderByEntitetAsc(ordersBys);
        List<Materijal> list = HibernateUtil.getSession().createQuery(query).list();
        return list;
    }

    public String getOrderByEntitetAsc(ArrayList<String> ordersBys) {
        this.ordersBys = ordersBys;
        if (ordersBys.size() > 0) {
            ArrayList<String> a = new ArrayList<>();
            ordersBys.forEach((o) -> {
                a.add(o.concat(" asc"));
            });
            String joined = String.join(", ", a);
            return " order by " + joined;
        } else {
            return "";
        }
    }

    public void setOrdersBys(ArrayList<String> ordersBys) {
        this.ordersBys = ordersBys;
    }

}
