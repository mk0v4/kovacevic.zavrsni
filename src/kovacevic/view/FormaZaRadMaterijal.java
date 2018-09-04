/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.view;

import kovacevic.controller.HibernateObrada;
import kovacevic.model.Entitet;
import javax.swing.JFrame;

/**
 *
 * @author Marko Kovačević
 * @param <T>
 * @param <U>
 */
public abstract class FormaZaRadMaterijal<T extends Entitet, U extends Entitet> extends JFrame {

    protected abstract void ucitajRad();

    protected abstract void ucitajMaterijal();

    protected HibernateObrada<T> obradaRad;
    protected HibernateObrada<U> obradaMaterijal;

    protected T rad;
    protected U materijal;

    protected void obrisiRad() {
        obradaRad.delete(rad);
        ucitajRad();
    }

    protected void spremiRad() {
        rad = obradaRad.save(rad);
        ucitajRad();
    }

    protected void obrisiMaterijal() {
        obradaMaterijal.delete(materijal);
        ucitajMaterijal();
    }

    protected void spremiMaterijal() {
        materijal = obradaMaterijal.save(materijal);
        ucitajMaterijal();
    }

}
