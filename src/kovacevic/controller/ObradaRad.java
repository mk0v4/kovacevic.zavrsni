/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.controller;

import java.util.ArrayList;
import java.util.List;
import kovacevic.model.Rad;
import kovacevic.pomocno.HibernateUtil;
import kovacevic.pomocno.PorukaIznimke;

/**
 *
 * @author Marko Kovačević
 */
public class ObradaRad {

    private final HibernateObrada<Rad> obrada;
    private ArrayList<String> ordersBys = new ArrayList<>();

    public static final String GRUPA_RADOVA = "Grupa radova";
    public static final String KATEGORIJA_RAD = "Kategorija rada";
    public static final String CIJENA_RAD = "Cijena rada";
    public static final String ENTITET_NULL = "vrijednost nije odabrana";

    private boolean brisanje = false;

    public ObradaRad() {
        obrada = new HibernateObrada<>();
        ordersBys.add("grupaRadova");
        ordersBys.add("kategorijaRad");
        ordersBys.add("cijena");
    }

    public boolean provjeraDuplogUnosaRad(Rad rad) {
        List<Rad> radIzBaze = HibernateUtil.getSession().createQuery("from Rad where obrisan=false "
                + "and grupaRadova=:grupaRadova and kategorijaRad=:kategorijaRad and cijena=:cijena")
                .setParameter("grupaRadova", rad.getGrupaRadova())
                .setParameter("kategorijaRad", rad.getKategorijaRad())
                .setParameter("cijena", rad.getCijena()).list();
        return radIzBaze.size() > 0;
    }

    public Rad spremi(Rad rad) throws PorukaIznimke {
        List<String> greske = new ArrayList<>();
        if (rad == null) {
            throw new PorukaIznimke("Entitet mora biti odabran ", "Odaberite stavku unutar tablice", ENTITET_NULL);
        }
        if (brisanje == false) {
            if (rad.getGrupaRadova().trim().length() == 0) {
                greske.add(GRUPA_RADOVA);
            }
            if (rad.getKategorijaRad().trim().length() == 0) {
                greske.add(KATEGORIJA_RAD);
            }
            if (rad.getCijena() == null) {
                greske.add(CIJENA_RAD);
            }
            if (rad.getGrupaRadova().trim().length() != 0 || rad.getKategorijaRad().trim().length() != 0 || rad.getCijena() != null) {
                if (provjeraDuplogUnosaRad(rad) == true) {
                    throw new PorukaIznimke("Rezultat mora biti različit u jednom od unosa", "Postoji unos u tablici za ",
                            GRUPA_RADOVA + ": " + rad.getGrupaRadova() + ", " + KATEGORIJA_RAD + ": " + rad.getKategorijaRad() + ", " + CIJENA_RAD + ": " + rad.getCijena());
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
            rad.setObrisan(true);
        }
        return obrada.save(rad);
    }

    public Rad promijeni(Rad rad) throws PorukaIznimke {
        if (rad == null) {
            throw new PorukaIznimke("Entitet mora biti odabran ", "Odaberite stavku unutar tablice", ENTITET_NULL);
        }
        if (rad.getGrupaRadova().trim().length() != 0 || rad.getKategorijaRad().trim().length() != 0 || rad.getCijena() != null) {
            if (provjeraDuplogUnosaRad(rad) == true) {
                throw new PorukaIznimke("Rezultat mora biti različit u jednom od unosa", "Postoji unos u tablici za ",
                        GRUPA_RADOVA + ": " + rad.getGrupaRadova() + ", " + KATEGORIJA_RAD + ": " + rad.getKategorijaRad() + ", " + CIJENA_RAD + ": " + rad.getCijena());
            }
        }
        return obrada.save(rad);
    }

    public void obrisi(Rad rad) throws PorukaIznimke {
        brisanje = true;
        spremi(rad);
        brisanje = false;
    }

    public List<Rad> getListaRad(Rad rad) {
        String query = "from Rad where obrisan=false" + getOrderByEntitetAsc(ordersBys);
        System.out.println("=== " + query);
        List<Rad> list = HibernateUtil.getSession().createQuery(query).list();
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
