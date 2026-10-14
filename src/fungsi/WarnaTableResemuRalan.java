/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fungsi;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Owner
 */
public class WarnaTableResemuRalan extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

// Ambil isi kolom "icd utama" (indeks 12)
        Object status = table.getValueAt(row, 12);

        if (status != null && !status.toString().trim().equals("")) {
            // Ada isi → hijau
            component.setBackground(new Color(204, 255, 204));
        } else {
            // Kosong → warna default (selang-seling)
            if (row % 2 == 1) {
                component.setBackground(new Color(255, 244, 244));
            } else {
                component.setBackground(Color.WHITE);
            }
        }
        return component;
    }

}
