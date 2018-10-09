/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.pomocno;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.AbstractAction;
import javax.swing.JScrollPane;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.Scrollable;

import javax.swing.table.TableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Marko Kovačević
 */
public class WrappableTable extends JTable implements Scrollable {

    protected WrappableTableRenderer renderer;

    public WrappableTable(JTable table, TableModel tm, JScrollPane scrollPane) {
        super(tm);
        table.setSelectionModel(super.selectionModel);
        table.setColumnModel(super.columnModel);

        changedSize(table, scrollPane);

        table.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateRowHeights(table, scrollPane);
            }
        });
        scrollPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                changedSize(table, scrollPane);
            }
        });

        this.renderer = new WrappableTableRenderer(table);
        table.setDefaultRenderer(Object.class, this.renderer);
        
                AbstractAction action = new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                TableCellListener tcl = (TableCellListener) e.getSource();
                System.out.println("Row   : " + tcl.getRow());
                System.out.println("Column: " + tcl.getColumn());
                System.out.println("Old   : " + tcl.getOldValue());
                System.out.println("New   : " + tcl.getNewValue());
            }
        };
        TableCellListener tcl = new TableCellListener(table, action);
        
    }

    private void updateRowHeights(JTable table, JScrollPane scrollPane) {
//        System.out.println("kovacevic.pomocno.WrappableTable.updateRowHeights()");
        table.setRowHeight(16);
        int startRowHeight = table.getRowHeight();
        BigDecimal bdStartRowHeight = new BigDecimal(startRowHeight);
        int h = 0;
        changedSize(table, scrollPane);
        for (int row = 0; row < table.getRowCount(); row++) {
            int actualRowHeight = table.getRowHeight(row);
            h = actualRowHeight + 3;
            for (int column = 0; column < table.getModel().getColumnCount(); column++) {
                String value = table.getValueAt(row, column).toString();
                Font valueFont = table.getFont();
                int valueWidth = table.getFontMetrics(valueFont).stringWidth(value.toUpperCase());
                int columnWidth = table.getColumnModel().getColumn(column).getWidth();
                BigDecimal bdValueWidth = new BigDecimal(valueWidth);
                BigDecimal bdColumnWidth = new BigDecimal(columnWidth);
                BigDecimal wholeNumberOfLines = bdValueWidth.divide(bdColumnWidth, RoundingMode.UP);
                if (wholeNumberOfLines.equals(BigDecimal.ZERO)) {
                    wholeNumberOfLines = BigDecimal.ONE;
                }
                BigDecimal rowHeightColumn = wholeNumberOfLines.multiply(bdStartRowHeight);
                if (rowHeightColumn.intValue() > h) {
                    h = rowHeightColumn.intValue() + 3;
                }
            }
            table.setRowHeight(row, h);
        }
    }

    public void changedSize(JTable table, JScrollPane scrollPane) {
//        System.out.println("kovacevic.pomocno.WrappableTable.changedSize()");
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth((int) scrollPane.getViewport().getWidth() / table.getColumnCount());
        }
    }

    public static class WrappableTableRenderer extends JTextArea implements TableCellRenderer {

        /**
         *
         */
        public WrappableTableRenderer(JTable table) {
            setWrapStyleWord(true);
            setLineWrap(true);
            setMargin(new Insets(0, 2, 0, 2));
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

}
