/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.controller;

import java.util.ArrayList;
import java.util.List;
import kovacevic.model.GrupacijaNorme;
import kovacevic.pomocno.HibernateUtil;
import kovacevic.pomocno.PorukaIznimke;

/**
 *
 * @author Marko Kovačević
 */
public class ObradaGrupacijaNorme {

    private final HibernateObrada<GrupacijaNorme> obrada;
    private ArrayList<String> ordersBys = new ArrayList<>();

    public static final String OZNAKA_NORME = "Oznaka grupe norme";
    public static final String GRUPA_NORME = "Grupa norme";
    public static final String OPIS = "Opis grupe norme";
    public static final String ENTITET_NULL = "vrijednost nije odabrana";

    private boolean brisanje = false;

    public ObradaGrupacijaNorme() {
        obrada = new HibernateObrada<>();
    }

    public boolean provjeraDuplogUnosaRad(GrupacijaNorme grupacijaNorme) {
        List<GrupacijaNorme> grupacijaNormeIzBaze = HibernateUtil.getSession().createQuery("from GrupacijaNorme where obrisan=false "
                + "and oznakaNorme=:oznakaNorme and grupaNorme=:grupaNorme and opis=:opis")
                .setParameter("oznakaNorme", grupacijaNorme.getOznakaNorme())
                .setParameter("grupaNorme", grupacijaNorme.getGrupaNorme())
                .setParameter("opis", grupacijaNorme.getOpis()).list();
        return grupacijaNormeIzBaze.size() > 0;
    }

    public GrupacijaNorme spremi(GrupacijaNorme grupacijaNorme) throws PorukaIznimke {
        List<String> greske = new ArrayList<>();
        if (grupacijaNorme == null) {
            throw new PorukaIznimke("Entitet mora biti odabran ", "Odaberite stavku unutar tablice", ENTITET_NULL);
        }
        if (brisanje == false) {
            if (grupacijaNorme.getOznakaNorme().trim().length() == 0) {
                greske.add(OZNAKA_NORME);
            }
            if (grupacijaNorme.getGrupaNorme().trim().length() == 0) {
                greske.add(GRUPA_NORME);
            }
            if (grupacijaNorme.getOpis().trim().length() == 0) {
                greske.add(OPIS);
            }
            if (grupacijaNorme.getOznakaNorme().trim().length() != 0 || grupacijaNorme.getGrupaNorme().trim().length() != 0 || grupacijaNorme.getOpis().trim().length() != 0) {
                if (provjeraDuplogUnosaRad(grupacijaNorme) == true) {
                    throw new PorukaIznimke("Rezultat mora biti različit u jednom od unosa", "Postoji unos u tablici za ",
                            OZNAKA_NORME + ": " + grupacijaNorme.getOznakaNorme() + "," + GRUPA_NORME + ": " + grupacijaNorme.getOznakaNorme()
                            + "," + OPIS + ": " + grupacijaNorme.getOpis());
                }
            }
        }
        if (greske.size() > 0) {
            String joined = String.join(", ", greske);
            System.out.println(joined);
            throw new PorukaIznimke("Podatci moraju biti unešani " + greske, "Nisu unešeni sljedeći podatci: ", joined + ".");
        }
        if (brisanje == true) {
            grupacijaNorme.setObrisan(true);
        }
        return obrada.save(grupacijaNorme);
    }

    public GrupacijaNorme promijeni(GrupacijaNorme grupacijaNorme) throws PorukaIznimke {
        if (grupacijaNorme == null) {
            throw new PorukaIznimke("Entitet mora biti odabran ", "Odaberite stavku unutar tablice", ENTITET_NULL);
        }
        if (grupacijaNorme.getOznakaNorme().trim().length() != 0 || grupacijaNorme.getGrupaNorme().trim().length() != 0 || grupacijaNorme.getOpis().trim().length() != 0) {
            if (provjeraDuplogUnosaRad(grupacijaNorme) == true) {
                throw new PorukaIznimke("Rezultat mora biti različit u jednom od unosa", "Podatci nisu izmjenjeni za ",
                        OZNAKA_NORME + ": " + grupacijaNorme.getOznakaNorme() + "," + GRUPA_NORME + ": " + grupacijaNorme.getOznakaNorme()
                        + "," + OPIS + ": " + grupacijaNorme.getOpis());
            }
        }
        return obrada.save(grupacijaNorme);
    }

    public void obrisi(GrupacijaNorme grupacijaNorme) throws PorukaIznimke {
        brisanje = true;
        spremi(grupacijaNorme);
        brisanje = false;
    }

    public List<GrupacijaNorme> getListaGrupacijaNorme(GrupacijaNorme grupacijaNorme) {
        String query = "from GrupacijaNorme where obrisan=false" + getOrderByEntitetAsc(ordersBys);
        List<GrupacijaNorme> list = HibernateUtil.getSession().createQuery(query).list();
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
