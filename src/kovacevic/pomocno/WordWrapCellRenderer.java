/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.pomocno;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author 001
 */
public class WordWrapCellRenderer extends JTextArea implements TableCellRenderer {

    public WordWrapCellRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setMargin(new Insets(0,2,0,2));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        setText(value.toString());
        setSize(table.getColumnModel().getColumn(column).getWidth(), (int) getPreferredSize().getHeight());

        if (isSelected) {
            setBackground(new Color(184, 207, 229));
        } else {
            setBackground(Color.WHITE);
        }

//        System.out.println(new SimpleDateFormat("HH:mm.ssSSS").format(new Date()).toString());
        return this;
    }

}
