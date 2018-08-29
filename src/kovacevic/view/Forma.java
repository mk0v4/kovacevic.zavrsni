/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.view;

import kovacevic.controller.Obrada;
import kovacevic.model.Entitet;
import javax.swing.JFrame;
import javax.swing.event.TableModelEvent;

/**
 *
 * @author Marko Kovačević
 */
public abstract class Forma<T extends Entitet> extends JFrame  {
    
    protected abstract void ucitaj();
    
   
     protected Obrada<T> obrada;
    protected T entitet;
   

    
    protected void obrisi(){
        obrada.delete(entitet);
        ucitaj();
    }
    
    protected  void spremi(){
        entitet=obrada.save(entitet);
        ucitaj();
    }
    
}
