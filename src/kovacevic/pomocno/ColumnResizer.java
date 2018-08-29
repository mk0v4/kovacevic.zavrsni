/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.pomocno;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author 001
 */
public class ColumnResizer extends JTextArea {

    public ColumnResizer() {
        setLineWrap(true);
        setWrapStyleWord(true);
    }

    public static void adjustColumnPreferredWidths(JTable table) {
        // strategy - get max width for cells in column and
        // make that the preferred width
        TableColumnModel columnModel = table.getColumnModel();
        for (int col = 0; col < table.getColumnCount(); col++) {

            int maxwidth = 0;
            for (int row = 0; row < table.getRowCount(); row++) {
                System.out.println(table.getRowHeight(row));
                TableCellRenderer rend = table.getCellRenderer(row, col);
                Object value = table.getValueAt(row, col);
                Component comp = rend.getTableCellRendererComponent(table,
                        value, false, false, row, col);
                maxwidth = Math.max(comp.getPreferredSize().width, maxwidth);

            }

            TableColumn column = columnModel.getColumn(col);
            column.setPreferredWidth(maxwidth + 2);
        }
    }
}
