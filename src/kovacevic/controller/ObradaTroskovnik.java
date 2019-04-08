/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.controller;

import java.util.ArrayList;
import java.util.List;
import kovacevic.model.Troskovnik;
import kovacevic.pomocno.HibernateUtil;
import kovacevic.pomocno.PorukaIznimke;

/**
 *
 * @author Marko Kovačević
 */
public class ObradaTroskovnik {

    private final HibernateObrada<Troskovnik> obrada;
    private ArrayList<String> ordersBys = new ArrayList<>();

    public static final String OZNAKA_TROSKOVNIK = "Oznaka troskovnika";
    public static final String NASLOV_TROSKOVNIK = "Naslov troskovnika";
    public static final String NAZIV_OBJEKT = "Naziv objekta";
    public static final String DATUM_IZRADE = "Datum izrade";
    public static final String ENTITET_NULL = "vrijednost nije odabrana";

    private boolean brisanje = false;

    public ObradaTroskovnik() {
        obrada = new HibernateObrada<>();
    }

    public boolean provjeraDuplogUnosaTroskovnik(Troskovnik troskovnik) {
        List<Troskovnik> troskovnikIzBaze = HibernateUtil.getSession().createQuery("from Troskonik where obrisan=false "
                + "and oznaka=:oznaka and naslov=:naslov and objektNaziv=:objektNaziv")
                .setParameter("oznaka", troskovnik.getOznaka())
                .setParameter("naslov", troskovnik.getNaslov())
                .setParameter("objektNaziv", troskovnik.getObjektNaziv()).list();
        return troskovnikIzBaze.size() > 0;
    }

    public Troskovnik spremi(Troskovnik troskovnik) throws PorukaIznimke {
        List<String> greske = new ArrayList<>();
        if (troskovnik == null) {
            throw new PorukaIznimke("Entitet mora biti odabran ", "Odaberite stavku unutar tablice", ENTITET_NULL);
        }
        if (brisanje == false) {
            if (troskovnik.getOznaka().trim().length() == 0) {
                greske.add(OZNAKA_TROSKOVNIK);
            }
            if (troskovnik.getNaslov().trim().length() == 0) {
                greske.add(NASLOV_TROSKOVNIK);
            }
            if (troskovnik.getObjektNaziv().trim().length() == 0) {
                greske.add(NAZIV_OBJEKT);
            }
            if (troskovnik.getDatumIzrade() == null) {
                greske.add(DATUM_IZRADE);
            }
            if (troskovnik.getOznaka().trim().length() != 0 || troskovnik.getNaslov().trim().length() != 0 || troskovnik.getObjektNaziv().trim().length() != 0) {
                if (provjeraDuplogUnosaTroskovnik(troskovnik) == true) {
                    throw new PorukaIznimke("Rezultat mora biti različit u jednom od unosa", "Postoji unos u tablici za ",
                            OZNAKA_TROSKOVNIK + ": " + troskovnik.getOznaka() + "," + NASLOV_TROSKOVNIK + ": " + troskovnik.getNaslov()
                            + "," + NAZIV_OBJEKT + ": " + troskovnik.getObjektNaziv());
                }
            }
        }
        if (greske.size() > 0) {
            String joined = String.join(", ", greske);
            System.out.println(joined);
            throw new PorukaIznimke("Podatci moraju biti unešani " + greske, "Nisu unešeni sljedeći podatci: ", joined + ".");
        }
        if (brisanje == true) {
            troskovnik.setObrisan(true);
        }
        return obrada.save(troskovnik);
    }

    public Troskovnik promijeni(Troskovnik troskovnik) throws PorukaIznimke {
        if (troskovnik == null) {
            throw new PorukaIznimke("Entitet mora biti odabran ", "Odaberite stavku unutar tablice", ENTITET_NULL);
        }
        if (troskovnik.getOznaka().trim().length() != 0 || troskovnik.getNaslov().trim().length() != 0 || troskovnik.getObjektNaziv().trim().length() != 0) {
            if (provjeraDuplogUnosaTroskovnik(troskovnik) == true) {
                throw new PorukaIznimke("Rezultat mora biti različit u jednom od unosa", "Podatci nisu izmjenjeni za ",
                        OZNAKA_TROSKOVNIK + ": " + troskovnik.getOznaka() + "," + NASLOV_TROSKOVNIK + ": " + troskovnik.getNaslov()
                        + "," + NAZIV_OBJEKT + ": " + troskovnik.getObjektNaziv());
            }
        }
        return obrada.save(troskovnik);
    }

    public void obrisi(Troskovnik troskovnik) throws PorukaIznimke {
        brisanje = true;
        spremi(troskovnik);
        brisanje = false;
    }

    public List<Troskovnik> getListaTroskovnik(Troskovnik troskovnik) {
        String query = "from Troskovnik where obrisan=false" + getOrderByEntitetAsc(ordersBys);
        List<Troskovnik> list = HibernateUtil.getSession().createQuery(query).list();
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
