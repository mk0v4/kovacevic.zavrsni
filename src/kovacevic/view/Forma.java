/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.view;

import kovacevic.controller.HibernateObrada;
import kovacevic.model.Entitet;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.event.TableModelEvent;

/**
 *
 * @author Marko Kovačević
 */
public abstract class Forma<T extends Entitet> extends JFrame  {
    
    protected abstract void ucitaj();
    
   
     protected HibernateObrada<T> obrada;
    protected T entitet;
   

    
    protected void obrisi(){
        obrada.delete(entitet);
        ucitaj();
    }
    
    protected  void spremi(){
        entitet=obrada.save(entitet);
        ucitaj();
    }

    void getListCellRendererComponent(JList<?> list, Object value, int index, boolean selected, boolean cellHasFocus) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
