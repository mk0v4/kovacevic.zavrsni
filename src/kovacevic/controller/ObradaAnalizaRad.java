/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kovacevic.model.AnalizaCijene;
import kovacevic.model.AnalizaRad;
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

    public boolean provjeraDuplogUnosaAnalizaRad(AnalizaRad analizaRad) {
        List<AnalizaRad> analizaRadIzBaze = HibernateUtil.getSession().createQuery("from AnalizaRad where obrisan=false "
                + "and analizaCijene=:analizaCijene and rad=:rad and opisOperacije=:opisOperacije and"
                + " jedinicniNormativVremena=:jedinicniNormativVremena and cijenaVrijeme=:cijenaVrijeme"
                + " and brojOperacije=:brojOperacije and ")
                .setParameter("analizaCijene", analizaRad.getAnalizaCijene())
                .setParameter("rad", analizaRad.getRad())
                .setParameter("opisOperacije", analizaRad.getOpisOperacije())
                .setParameter("jedinicniNormativVremena", analizaRad.getJedinicniNormativVremena())
                .setParameter("cijenaVrijeme", analizaRad.getCijenaVrijeme())
                .setParameter("brojOperacije", analizaRad.getBrojOperacije())
                .list();
        analizaRadIzBaze.remove(analizaRad);
        return analizaRadIzBaze.size() > 0;
    }

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
            if (analizaRad.getAnalizaCijene() != null || analizaRad.getRad() != null || analizaRad.getOpisOperacije().trim().length() != 0
                    || analizaRad.getJedinicniNormativVremena() != null || analizaRad.getCijenaVrijeme() != null || analizaRad.getBrojOperacije() <= 0) {
                if (provjeraDuplogUnosaAnalizaRad(analizaRad) == true) {
                    throw new PorukaIznimke("Rezultat mora biti različit u jednom od unosa", "Postoji unos u tablici za ",
                            BROJ_OPERACIJE + ": " + analizaRad.getBrojOperacije() + ", " + OPIS_OPERACIJE + ": " + analizaRad.getOpisOperacije() + ", "
                            + JEDINICNI_NORMATIV_VREMENA + ": " + analizaRad.getJedinicniNormativVremena() + ", " + RAD + ": "
                            + analizaRad.getRad().getGrupaRadova() + " " + analizaRad.getRad().getKategorijaRad() + ", "
                            + CIJENA_VRIJEME + ": " + analizaRad.getCijenaVrijeme());
                }
            }
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
