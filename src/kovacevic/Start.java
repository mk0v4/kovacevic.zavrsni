/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic;

import kovacevic.pomocno.HibernateUtil;
import kovacevic.view.Izbornik;
/**
 *
 * @author Marko Kovačević
 */
public class Start {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Izbornik().setVisible(true);
        HibernateUtil.getSession();
    }

}
