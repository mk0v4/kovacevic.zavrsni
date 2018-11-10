/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.controller;

import java.util.ArrayList;
import java.util.List;
import kovacevic.model.AnalizaCijene;
import kovacevic.model.AnalizaRad;
import kovacevic.model.Rad;
import kovacevic.pomocno.HibernateUtil;
import kovacevic.pomocno.PorukaIznimke;

/**
 *
 * @author Marko Kovačević
 */
public class ObradaAnalizaRad {

    private final HibernateObrada<AnalizaRad> obrada;
    private ArrayList<String> ordersBys = new ArrayList<>();

    public static final String OPIS_OPERACIJE = "Opis operacije";
    public static final String BROJ_OPERACIJE = "Broj operacije";
    public static final String JEDINICNI_NORMATIV_VREMENA = "Jedinični normativ vremena";
    public static final String CIJENA_VRIJEME = "Cijena rada";
    public static final String RAD = "Rad";
    public static final String ANALIZA_CIJENE = "Analiza Cijene";
    public static final String ENTITET_NULL = "vrijednost nije odabrana";

    private boolean brisanje = false;

    public ObradaAnalizaRad() {
        obrada = new HibernateObrada<>();
//        ordersBys.add("grupaRadova");
//        ordersBys.add("kategorijaRad");
//        ordersBys.add("cijena");
    }

//    public boolean provjeraDuplogUnosaRad(AnalizaRad analizaRad) {
//        List<AnalizaRad> analizaRadIzBaze = HibernateUtil.getSession().createQuery("from AnalizaRad where obrisan=false "
//                + "and grupaRadova=:grupaRadova and kategorijaRad=:kategorijaRad and cijena=:cijena")
//                .setParameter("grupaRadova", rad.getGrupaRadova())
//                .setParameter("kategorijaRad", rad.getKategorijaRad())
//                .setParameter("cijena", rad.getCijena())
//                .list();
//        return analizaRadIzBaze.size() > 0;
//    }

    public AnalizaRad spremi(AnalizaRad analizaRad) throws PorukaIznimke {
        List<String> greske = new ArrayList<>();
        if (analizaRad == null) {
            throw new PorukaIznimke("Entitet mora biti odabran ", "Odaberite stavku unutar tablice", ENTITET_NULL);
        }
        if (brisanje == false) {

//            if (rad.getGrupaRadova().trim().length() == 0) {
//                greske.add(GRUPA_RADOVA);
//            }
//            if (rad.getKategorijaRad().trim().length() == 0) {
//                greske.add(KATEGORIJA_RAD);
//            }
//            if (rad.getCijena() == null) {
//                greske.add(CIJENA_RAD);
//            }
//            if (rad.getGrupaRadova().trim().length() != 0 || rad.getKategorijaRad().trim().length() != 0 || rad.getCijena() != null) {
//                if (provjeraDuplogUnosaRad(rad) == true) {
//                    throw new PorukaIznimke("Rezultat mora biti različit u jednom od unosa", "Postoji unos u tablici za ",
//                            GRUPA_RADOVA + ": " + rad.getGrupaRadova() + ", " + KATEGORIJA_RAD + ": " + rad.getKategorijaRad() + ", " + CIJENA_RAD + ": " + rad.getCijena());
//                }
//            }
        }
        if (greske.size() > 0) {
            String joined = String.join(", ", greske);
            System.out.println(joined);
            throw new PorukaIznimke("Podatci moraju biti unešani " + greske, "Nisu unešeni sljedeći podatci: ", joined + ".");
        }
        System.out.println("brisanje " + brisanje);
        if (brisanje == true) {
            analizaRad.setObrisan(true);
        }
        return obrada.save(analizaRad);
    }

    public AnalizaRad promijeni(AnalizaRad analizaRad) throws PorukaIznimke {
        if (analizaRad == null) {
            throw new PorukaIznimke("Entitet mora biti odabran ", "Odaberite stavku unutar tablice", ENTITET_NULL);
        }
//        if (rad.getGrupaRadova().trim().length() != 0 || rad.getKategorijaRad().trim().length() != 0 || rad.getCijena() != null) {
//            if (provjeraDuplogUnosaRad(rad) == true) {
//                throw new PorukaIznimke("Rezultat mora biti različit u jednom od unosa", "Podatci nisu izmjenjeni za ",
//                        GRUPA_RADOVA + ": " + rad.getGrupaRadova() + ", " + KATEGORIJA_RAD + ": " + rad.getKategorijaRad() + ", " + CIJENA_RAD + ": " + rad.getCijena());
//            }
//        }
        return obrada.save(analizaRad);
    }

    public void obrisi(AnalizaRad analizaRad) throws PorukaIznimke {
        brisanje = true;
        spremi(analizaRad);
        brisanje = false;
    }

    public List<AnalizaRad> getListaRad(AnalizaCijene analizaCijene) {
        String query = "from AnalizaRad where obrisan=false and analizaCijene =:analizaCijene" + getOrderByEntitetAsc(ordersBys);
        List<AnalizaRad> list = HibernateUtil.getSession().createQuery(query)
                .setParameter("analizaCijene", analizaCijene).list();
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
