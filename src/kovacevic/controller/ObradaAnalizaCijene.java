/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.controller;

import java.util.ArrayList;
import java.util.List;
import kovacevic.model.AnalizaCijene;
import kovacevic.pomocno.HibernateUtil;
import kovacevic.pomocno.PorukaIznimke;

/**
 *
 * @author Marko Kovačević
 */
public class ObradaAnalizaCijene {

    private final HibernateObrada<AnalizaCijene> obrada;
    private ArrayList<String> ordersBys = new ArrayList<>();

    public static final String OZNAKA_GRUPA_NORME = "Grupa norme";
    public static final String OZNAKA_NORME = "Oznaka norme";
    public static final String OPIS = "Opis norme";
    public static final String JEDINICA_MJERE = "Jedinica mjere";
    public static final String UKUPAN_NORMATIV_VREMENA = "Ukupan normativ vremena";
    public static final String UKUPNA_CIJENA_MATERIJAL = "Ukupna cijena materijala";
    public static final String UKUPNA_CIJENA_RAD = "Ukupna cijena rada";
    public static final String KOEFICIJENT_FIRME = "Koeficijent";
    public static final String SVEUKUPAN_IZNOS = "Ukupna cijena";
    public static final String ENTITET_NULL = "vrijednost nije odabrana";
    public static final String ANALIZE_RADOVA = "operacije nisu dodane";

    private boolean brisanje = false;

    public ObradaAnalizaCijene() {
        obrada = new HibernateObrada<>();
//        ordersBys.add("grupaRadova");
//        ordersBys.add("kategorijaRad");
//        ordersBys.add("cijena");
    }

    public boolean provjeraDuplogUnosaAnalizaCijena(AnalizaCijene analizaCijene) {
        List<AnalizaCijene> analizaCijeneIzBaze = HibernateUtil.getSession().createQuery("from AnalizaCijene where obrisan=false "
        )
                //                .setParameter("grupaRadova", rad.getGrupaRadova())
                //                .setParameter("kategorijaRad", rad.getKategorijaRad())
                //                .setParameter("cijena", rad.getCijena())
                .list();
        return analizaCijeneIzBaze.size() > 0;
    }

    public AnalizaCijene spremi(AnalizaCijene analizaCijene) throws PorukaIznimke {
        List<String> greske = new ArrayList<>();
        if (analizaCijene == null) {
            throw new PorukaIznimke("Entitet mora biti odabran ", "Odaberite stavku unutar tablice", ENTITET_NULL);
        }
        if (brisanje == false) {
//            txtOznakaGrupeNorme.setText(grupacijaNorme.getOznakaNorme() + " " + grupacijaNorme.getOpis());
            if (analizaCijene.getGrupacijaNorme().getOznakaNorme().trim().length() == 0
                    || analizaCijene.getGrupacijaNorme().getOpis().trim().length() == 0) {
                greske.add(OZNAKA_GRUPA_NORME);
            }
            if (analizaCijene.getOznakaNorme().trim().length() == 0) {
                greske.add(OZNAKA_NORME);
            }
            if (analizaCijene.getOpis().trim().length() == 0) {
                greske.add(OPIS);
            }
            if (analizaCijene.getJedinicaMjere().trim().length() == 0) {
                greske.add(JEDINICA_MJERE);
            }
//            if (analizaCijene.getAnalizeRadova().size() == 0) {
//                greske.add(ANALIZE_RADOVA);
//            }
//            if (analizaCijene.getGrupaRadova().trim().length() == 0) {
//                greske.add(GRUPA_RADOVA);
//            }
//            if (analizaCijene.getKategorijaRad().trim().length() == 0) {
//                greske.add(KATEGORIJA_RAD);
//            }
//            if (analizaCijene.getCijena() == null) {
//                greske.add(CIJENA_RAD);
//            }
//            if analizaCijene.getGrupaRadova().trim().length() != 0 || rad.getKategorijaRad().trim().length() != 0 || rad.getCijena() != null) {
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
            analizaCijene.setObrisan(true);
        }
        return obrada.save(analizaCijene);
    }

    public AnalizaCijene promijeni(AnalizaCijene analizaCijene) throws PorukaIznimke {
        if (analizaCijene == null) {
            throw new PorukaIznimke("Entitet mora biti odabran ", "Odaberite stavku unutar tablice", ENTITET_NULL);
        }
//        if (rad.getGrupaRadova().trim().length() != 0 || rad.getKategorijaRad().trim().length() != 0 || rad.getCijena() != null) {
//            if (provjeraDuplogUnosaRad(rad) == true) {
//                throw new PorukaIznimke("Rezultat mora biti različit u jednom od unosa", "Podatci nisu izmjenjeni za ",
//                        GRUPA_RADOVA + ": " + rad.getGrupaRadova() + ", " + KATEGORIJA_RAD + ": " + rad.getKategorijaRad() + ", " + CIJENA_RAD + ": " + rad.getCijena());
//            }
//        }
        return obrada.save(analizaCijene);
    }

    public void obrisi(AnalizaCijene analizaCijene) throws PorukaIznimke {
        brisanje = true;
        spremi(analizaCijene);
        brisanje = false;
    }

    public List<AnalizaCijene> getListaAnalizaCijena(AnalizaCijene analizaCijene) {
        String query = "from AnalizaCijene where obrisan=false" + getOrderByEntitetAsc(ordersBys);
        List<AnalizaCijene> list = HibernateUtil.getSession().createQuery(query).list();
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
