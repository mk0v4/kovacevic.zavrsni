/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kovacevic.model.AnalizaCijene;
import kovacevic.model.AnalizaMaterijal;
import kovacevic.model.AnalizaRad;
import kovacevic.pomocno.HibernateUtil;
import kovacevic.pomocno.PorukaIznimke;

/**
 *
 * @author Marko Kovačević
 */
public class ObradaAnalizaMaterijal {

    private final HibernateObrada<AnalizaMaterijal> obrada;
    private ArrayList<String> ordersBys = new ArrayList<>();

    public static final String ENTITET_NULL = "vrijednost nije odabrana";
    public static final String ANALIZA_CIJENE = "analiza cijene";
    public static final String MATERIJAL = "materijal";
    public static final String KOLICINA = "količina";
    public static final String JEDINICNA_CIJENA_MATERIJAL = "jedinična cijena materijala";
    public static final String CIJENA_MATERIJAL = "cijena materijala";

    private boolean brisanje = false;

    public ObradaAnalizaMaterijal() {
        obrada = new HibernateObrada<>();
//        ordersBys.add("grupaRadova");
//        ordersBys.add("kategorijaRad");
//        ordersBys.add("cijena");
    }

    public boolean provjeraDuplogUnosaAnalizaMaterijal(AnalizaMaterijal analizaMaterijal) {
        List<AnalizaRad> analizaMaterijalIzBaze = HibernateUtil.getSession().createQuery("from AnalizaMaterijal where obrisan=false "
                + "and analizaCijene=:analizaCijene and materijal=:materijal and kolicina=:kolicina and"
                + " jedinicnaCijenaMaterijal=:jedinicnaCijenaMaterijal and cijenaMaterijal=:cijenaMaterijal")
                .setParameter("analizaCijene", analizaMaterijal.getAnalizaCijene())
                .setParameter("materijal", analizaMaterijal.getMaterijal())
                .setParameter("kolicina", analizaMaterijal.getKolicina())
                .setParameter("jedinicnaCijenaMaterijal", analizaMaterijal.getJedinicnaCijenaMaterijal())
                .setParameter("cijenaMaterijal", analizaMaterijal.getCijenaMaterijal())
                .list();
        analizaMaterijalIzBaze.remove(analizaMaterijal);
        return analizaMaterijalIzBaze.size() > 0;
    }

    public AnalizaMaterijal spremi(AnalizaMaterijal analizaMaterijal) throws PorukaIznimke {
        if (analizaMaterijal == null) {
            throw new PorukaIznimke("Entitet mora biti odabran ", "Odaberite stavku unutar tablice", ENTITET_NULL);
        }
        if (brisanje == false) {

            if (analizaMaterijal.getAnalizaCijene() != null || analizaMaterijal.getMaterijal() != null || analizaMaterijal.getKolicina() != null
                    || analizaMaterijal.getJedinicnaCijenaMaterijal() != null || analizaMaterijal.getCijenaMaterijal() != null) {
                if (provjeraDuplogUnosaAnalizaMaterijal(analizaMaterijal) == true) {
                    throw new PorukaIznimke("Rezultat mora biti različit u jednom od unosa", "Postoji unos u tablici za ",
                            MATERIJAL + ": " + analizaMaterijal.getMaterijal().getGrupaMaterijal() + " " + analizaMaterijal.getMaterijal().getOznaka()
                            + ", " + KOLICINA + ": " + analizaMaterijal.getKolicina() + "; " + JEDINICNA_CIJENA_MATERIJAL + ": "
                            + analizaMaterijal.getJedinicnaCijenaMaterijal() + ", " + CIJENA_MATERIJAL + ": " + analizaMaterijal.getCijenaMaterijal());
                }
            }
        }
        if (brisanje == true) {
            analizaMaterijal.setObrisan(true);
        }
        return obrada.save(analizaMaterijal);
    }

    public AnalizaMaterijal promijeni(AnalizaMaterijal analizaMaterijal) throws PorukaIznimke {
        if (analizaMaterijal == null) {
            throw new PorukaIznimke("Entitet mora biti odabran ", "Odaberite stavku unutar tablice", ENTITET_NULL);
        }
        return obrada.save(analizaMaterijal);
    }

    public void obrisi(AnalizaMaterijal analizaMaterijal) throws PorukaIznimke {
        brisanje = true;
        spremi(analizaMaterijal);
        brisanje = false;
    }

    public List<AnalizaMaterijal> getListaAnalizaMaterijal(AnalizaCijene analizaCijene) {
        String query = "from AnalizaMaterijal where obrisan=false and analizaCijene =:analizaCijene" + getOrderByEntitetAsc(ordersBys);
        List<AnalizaMaterijal> list = HibernateUtil.getSession().createQuery(query)
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
