/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.pomocno;

import java.awt.Component;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JTable;
import javax.swing.JTextArea;

import javax.swing.table.TableModel;
import javax.swing.table.TableCellRenderer;

import javax.swing.event.TableModelEvent;

/**
 *
 * @author 001
 */
public class WrappableTable extends JTable {

    protected WrappableTableRenderer renderer;

    private JTable extTablica;

    public WrappableTable(JTable table, TableModel tm) {
        super(tm);
        this.extTablica = table;
        this.renderer = new WrappableTableRenderer(table);

        table.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateRowHeights(0);
            }
        });
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        super.tableChanged(e);

        if (e.getFirstRow() >= 0) {
            updateRowHeights(e.getFirstRow());
        }
    }

    protected void updateRowHeights(int beginningRow) {
        if (getColumnModel().getColumnCount() <= 0) {
            return;
        }

        TableModel tm = getModel();
        int rowCount = tm.getRowCount();
        int cwidth = getColumnModel().getColumn(7).getWidth();

        for (int i = beginningRow; i < rowCount; i++) {
            for (int col = 0; col < getColumnCount(); col++) {
                int newHeight = renderer.getNeededHeight(getValueAt(i, col), cwidth);

                if (newHeight <= 0) {
                    newHeight = 16;
                }
                System.out.println(" === row " + i + " === newHeight " + newHeight);
                extTablica.setRowHeight(i, newHeight);
            }

        }
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        return renderer;
    }

    @Override
    public TableCellRenderer getDefaultRenderer(Class columnClass) {
        return renderer;
    }

    public static class WrappableTableRenderer extends JTextArea implements TableCellRenderer {

        /**
         * Row height ondosi se na cijelu tablicu
         */
        public WrappableTableRenderer(JTable table) {
            setWrapStyleWord(true);
            setLineWrap(true);
            
        }

        public int getNeededHeight(Object o, int columnWidth) {
            int length = o.toString().length();

            
            System.out.println("getFont()" + getFont());
//            System.out.println("=== o.toString() " + o.toString());
//            System.out.println(getLineWrap());
//            System.out.println("Length; " + length);
//            System.out.println("Table column width: " + columnWidth);
//            System.out.println("Letter width: " + getColumnWidth());
            int charsPerLine = columnWidth / getColumnWidth();
            charsPerLine += 1;
//            System.out.println("chars per line: " + charsPerLine);
            int ret = length / charsPerLine;
//            System.out.println("Length / charsperline: " + ret);
            if ((length % charsPerLine) > 0) {
                ret += 1;
            }
//            System.out.println("added 1?: " + ret);

            ret *= getRowHeight();
            System.out.println("RET: " + ret);

            return ret;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            setSize(table.getColumnModel().getColumn(column).getWidth(), (int) getPreferredSize().getHeight());
            return this;
        }
    }

//    public static void main(String[] args) {
//        javax.swing.JFrame frame = new javax.swing.JFrame();
//
//        DefaultTableModel table = new DefaultTableModel();
//        table.addColumn("Foo");
//        table.addColumn("Bar");
//
//        frame.getContentPane().add(new WrappableTable(table));
//
//        Object[] row = {"Ugh", "Blugh"};
//        table.addRow(row);
//        Object[] row2 = {"123456789123. faagf", "Blugh"};
//        table.addRow(row2);
//
//        frame.setLocation(50, 50);
//        frame.pack();
//        frame.show();
//    }
}
