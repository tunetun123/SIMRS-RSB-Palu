package fungsi;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class WarnaTableSoap extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Warna default berdasarkan baris ganjil/genap
        if (row % 2 == 1) {
            component.setBackground(new Color(255, 244, 244)); // Warna merah muda pucat untuk baris ganjil
            component.setForeground(new Color(50, 50, 50));   // Warna teks abu-abu gelap
        } else {
            component.setBackground(new Color(255, 255, 255)); // Warna putih untuk baris genap
            component.setForeground(new Color(50, 50, 50));   // Warna teks abu-abu gelap
        }

        // Pengecekan kolom Dokter/Paramedis (kolom ke-25, index 24)
        if (table.getColumnCount() > 25) {
            Object pemeriksa = table.getValueAt(row, 25); // Kolom "profesi" ada di index 25

            if (pemeriksa != null) {
                String dokterParamedis = pemeriksa.toString().toLowerCase();

                if (dokterParamedis.contains("dokter")) {
                    // Dokter → merah
                    component.setBackground(new Color(255, 138, 138));
                } else if (dokterParamedis.contains("perawat poli")) {
                    // Perawat poli → hijau soft
                    component.setBackground(new Color(198, 235, 197));
                } else if (dokterParamedis.contains("karu rawat jalan") || dokterParamedis.contains("karu poli")) {
                    // Karu poli → oranye soft
                    component.setBackground(new Color(255, 204, 153));
                } else if (dokterParamedis.contains("bidan")) {
                    // Bidan → kuning soft
                    component.setBackground(new Color(255, 255, 153));
                } else if (dokterParamedis.contains("analis") || dokterParamedis.contains("karu lab")) {
                    // Analis & Karu lab → ungu
                    component.setBackground(new Color(220, 200, 240));
                } else if (dokterParamedis.contains("karu. radiologi")|| dokterParamedis.contains("radiografer")) {
                    // Karu farmasi → biru soft
                    component.setBackground(new Color(173, 216, 230));
                } else if (dokterParamedis.contains("apoteker")|| dokterParamedis.contains("karu. farmasi")) {
                    // Apoteker → biru muda berbeda
                    component.setBackground(new Color(135, 206, 250)); // light sky blue
                } else {
                    // Default → abu-abu soft
                    component.setBackground(new Color(224, 224, 224));
                }

                component.setForeground(new Color(0, 0, 0)); // teks hitam

            }

        }

        // Penanganan jika baris sedang dipilih
        //if (isSelected) {
        //  component.setBackground(new Color(0, 120, 215)); // Warna biru untuk baris yang dipilih
        //component.setForeground(Color.WHITE);           // Warna teks putih agar kontras
        //}
        return component;
    }
}
