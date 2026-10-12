/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fungsi;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Owner
 */
public class WarnaTableReg extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row % 2 == 1) {
            component.setBackground(new Color(255, 244, 244));
            component.setForeground(new Color(50, 50, 50));
        } else {
            component.setBackground(new Color(255, 255, 255));
            component.setForeground(new Color(50, 50, 50));
        }
        if (table.getValueAt(row, 19).toString().equals("Sudah")) {
            component.setBackground(new Color(197, 232, 152));
            component.setForeground(new Color(0, 0, 0));
        } else if (table.getValueAt(row, 19).toString().equals("iterasi Obat")) {
            component.setBackground(new Color(174, 226, 255));
            component.setForeground(new Color(0, 0, 0));
        } else if (table.getValueAt(row, 19).toString().equals("Batal")) {
            component.setBackground(new Color(240, 89, 65));
            component.setForeground(new Color(0, 0, 0));
        } else if (table.getValueAt(row, 19).toString().equals("Berkas Diterima")) {
            component.setBackground(new Color(231, 188, 222));
            component.setForeground(new Color(0, 0, 0));
        } else if (table.getValueAt(row, 19).toString().equals("Dirujuk") || table.getValueAt(row, 10).toString().equals("Meninggal") || table.getValueAt(row, 10).toString().equals("Pulang Paksa")) {
            component.setBackground(new Color(152, 152, 156));
            component.setForeground(new Color(0, 0, 0));
        } else if (table.getValueAt(row, 19).toString().equals("Sep Terbit")) {
            component.setBackground(new Color(255, 178, 44));
            component.setForeground(new Color(0, 0, 0));
        } else if (table.getValueAt(row, 19).toString().equals("Dilayani")) {
            component.setBackground(new Color(0, 204, 221));
            component.setForeground(new Color(251, 251, 251));
        }
        if (table.getValueAt(row, 23).toString().equals("Sudah Bayar") && table.getValueAt(row, 19).toString().equals("Resume")) {
            // Jika kolom 15 adalah "Sudah Bayar" dan kolom 10 adalah "Resume"
            component.setBackground(new Color(31, 125, 83));
            component.setForeground(new Color(251, 251, 251));
        } else if (table.getValueAt(row, 23).toString().equals("Sudah Bayar")) {
            // Jika kolom 15 adalah "Sudah Bayar" (tanpa memeriksa kolom 10)
            component.setBackground(new Color(22, 255, 0));
            component.setForeground(new Color(0, 0, 0));
        }
        return component;
    }

}
