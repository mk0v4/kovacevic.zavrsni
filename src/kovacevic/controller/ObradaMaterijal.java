/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.controller;

import java.util.ArrayList;
import kovacevic.model.Materijal;
import kovacevic.pomocno.PorukaIznimke;

/**
 *
 * @author Marko Kovačević
 */
public class ObradaMaterijal {

    private HibernateObrada<Materijal> obrada;
    private ArrayList<String> ordersBys;

    public ObradaMaterijal() {
        obrada = new HibernateObrada<>();
    }

    public Materijal spremi(Materijal materijal) throws PorukaIznimke {

        return obrada.save(materijal);
    }

    public void obrisi(Materijal materijal) throws PorukaIznimke {
        materijal.setObrisan(true);
        spremi(materijal);
    }

}
