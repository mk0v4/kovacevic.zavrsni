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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 001
 */
public class WrappableTable1 extends JTable {

    protected WrappableTableRenderer renderer;

    public WrappableTable1(TableModel tm) {
        super(tm);
        this.renderer = new WrappableTableRenderer();
                   
        addComponentListener(new ComponentAdapter() {
//            @Override
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
        int cwidth = getColumnModel().getColumn(0).getWidth();
        for (int i = beginningRow; i < rowCount; i++) {
            int newHeight = renderer.getNeededHeight(getValueAt(i, 0), cwidth);
            setRowHeight(i, newHeight);
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
        public WrappableTableRenderer() {
            setWrapStyleWord(true);
            setLineWrap(true);
           
        }

        public int getNeededHeight(Object o, int columnWidth) {
            int length = o.toString().length();
            						System.out.println("Length; " + length);
            						System.out.println("Table column width: " + columnWidth);
            						System.out.println("Letter width: "+  getColumnWidth());
            int charsPerLine = columnWidth / getColumnWidth();
            charsPerLine += 1;
            						System.out.println("chars per line: " + charsPerLine);
            int ret = length / charsPerLine;
            						System.out.println("Length / charsperline: " + ret);
            if ((length % charsPerLine) > 0) {
                ret += 1;
            }
            						System.out.println("added 1?: " + ret);

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

    public static void main(String[] args) {
        javax.swing.JFrame frame = new javax.swing.JFrame();

        DefaultTableModel model = new DefaultTableModel();
        
        frame.getContentPane().add(new WrappableTable1(model));
        
        String[] coulumnName = {"Rb. (Id)", "Grupe materijala"};

        model.setColumnIdentifiers(coulumnName);
        
//        
//        model.addColumn("Foo");
//        model.addColumn("Bar");

        

        Object[] row = {"Ugh Ugh Ugh Ugh Ugh Ugh Ugh", "Blugh"};
        model.addRow(row);
        Object[] row2 = {"123456789123. faagf", "Blugh"};
        model.addRow(row2);
        
        JTable table = new JTable(model);
        
        table.getTableHeader().setResizingAllowed(true);

        frame.setLocation(50, 50);
        frame.pack();
        frame.show();
    }
}
