/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.rmakmurjayaabadi.rfid.absence.GUI;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import com.rmakmurjayaabadi.rfid.absence.GenericDAO;
import com.rmakmurjayaabadi.rfid.absence.Karyawan;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
/**
 *
 * @author sofya
 */
public class DataKaryawanPage extends javax.swing.JFrame {
    
    private JTable tblKaryawan;
    private DefaultTableModel tableModel;

    public DataKaryawanPage() {
        initManualComponents();
        loadDataKaryawan();
        this.setLocationRelativeTo(null);
    }
    
    private void initManualComponents() {
        // --- 1. SETUP FRAME ---
        setTitle("MJA System - Data Karyawan");
        setSize(1280, 832);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(249, 250, 251)); // Background abu muda Figma

        // --- 2. SIDEBAR (Dark Navy #111827) ---
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(17, 24, 39));
        sidebar.setBounds(0, 0, 260, 832);
        sidebar.setLayout(null);
        add(sidebar);

        // Logo & Brand (Ganti teks dengan icon jika ada asset)
        JLabel lblBrand = new JLabel("ADMINISTRATOR");
        lblBrand.setForeground(new Color(45, 212, 191)); // Warna Tosca Logo
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblBrand.setBounds(25, 30, 200, 30);
        sidebar.add(lblBrand);

        // Menu Utama Label
        JLabel lblMenuUtama = new JLabel("Menu Utama");
        lblMenuUtama.setForeground(new Color(156, 163, 175));
        lblMenuUtama.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblMenuUtama.setBounds(25, 90, 200, 20);
        sidebar.add(lblMenuUtama);

        // Active Menu Item (Data Karyawan)
        JPanel menuActive = new JPanel();
        menuActive.setBackground(new Color(17, 45, 50)); // Teal Dark
        menuActive.setBounds(15, 120, 230, 45);
        menuActive.setLayout(null);
        menuActive.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        sidebar.add(menuActive);

        JLabel lblDataKaryawan = new JLabel("  Data Karyawan");
        lblDataKaryawan.setForeground(Color.WHITE);
        lblDataKaryawan.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblDataKaryawan.setBounds(15, 0, 200, 45);
        menuActive.add(lblDataKaryawan);

        // Tombol Keluar (Merah Figma)
        JButton btnKeluar = new JButton("Keluar");
        btnKeluar.setBackground(Color.RED);
        btnKeluar.setForeground(Color.WHITE);
        btnKeluar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnKeluar.setBounds(25, 740, 210, 45);
        btnKeluar.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        sidebar.add(btnKeluar);

        // --- 3. HEADER (White) ---
        JPanel header = new JPanel();
        header.setBackground(Color.WHITE);
        header.setBounds(260, 0, 1020, 70);
        header.setLayout(null);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)));
        add(header);

        // Search Bar Simulasi
        JTextField txtSearch = new JTextField("  Cari Karyawan atau ID...");
        txtSearch.setBounds(20, 15, 400, 40);
        txtSearch.setBackground(new Color(229, 231, 235));
        txtSearch.setBorder(BorderFactory.createEmptyBorder());
        txtSearch.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        header.add(txtSearch);

        JLabel lblAdminName = new JLabel("HRD Admin", SwingConstants.RIGHT);
        lblAdminName.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblAdminName.setBounds(800, 20, 150, 30);
        header.add(lblAdminName);

        // --- 4. CONTENT AREA ---
        JLabel lblTitle = new JLabel("Data Karyawan");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(17, 24, 39));
        lblTitle.setBounds(300, 100, 300, 40);
        add(lblTitle);

        // Tombol Tambah Karyawan Baru (Sesuai Desain Figma)
        JButton btnTambah = new JButton("Tambah Karyawan Baru");
        btnTambah.setBackground(new Color(209, 213, 219)); // Abu Figma
        btnTambah.setForeground(Color.BLACK);
        btnTambah.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnTambah.setBounds(300, 160, 200, 80);
        btnTambah.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        add(btnTambah);

        btnTambah.addActionListener(e -> {
            new TambahKaryawanDialog(this, true).setVisible(true);
            loadDataKaryawan();
        });

        // --- 5. TABEL KARYAWAN ---
        String[] cols = {"ID Karyawan", "Nama Karyawan", "Divisi", "Kontrak"};
        tableModel = new DefaultTableModel(cols, 0);
        tblKaryawan = new JTable(tableModel);
        
        // Styling Tabel
        tblKaryawan.setRowHeight(45);
        tblKaryawan.setShowVerticalLines(false);
        tblKaryawan.setGridColor(new Color(243, 244, 246));
        tblKaryawan.setIntercellSpacing(new Dimension(0, 0));
        tblKaryawan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblKaryawan.setSelectionBackground(new Color(17, 45, 50, 20));

        // Styling Header Tabel (Navy)
        JTableHeader h = tblKaryawan.getTableHeader();
        h.setBackground(new Color(17, 45, 50));
        h.setForeground(Color.WHITE);
        h.setFont(new Font("Segoe UI", Font.BOLD, 14));
        h.setPreferredSize(new Dimension(100, 45));

        JScrollPane sp = new JScrollPane(tblKaryawan);
        sp.setBounds(300, 270, 930, 500);
        sp.setBorder(BorderFactory.createLineBorder(new Color(229, 231, 235)));
        add(sp);
    }
    
    public void loadDataKaryawan() {
        try {
            GenericDAO<Karyawan> dao = new GenericDAO<>("karyawan", Karyawan.class);
            List<Karyawan> list = dao.findAll();
            tableModel.setRowCount(0);
            for (Karyawan k : list) {
                tableModel.addRow(new Object[]{
                    k.getIdKaryawan(), 
                    k.getNamaLengkap(), 
                    k.getDivisi(), 
                    k.getKontrak()
                });
            }
        } catch (Exception e) {
            System.err.println("Gagal load data: " + e.getMessage());
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1280, 720));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       try {
            FlatLightLaf.setup(); // Aktifkan FlatLaf
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.awt.EventQueue.invokeLater(() -> {
            new DataKaryawanPage().setVisible(true);
        });
    }

    private static class TambahKaryawanDialog {

        public TambahKaryawanDialog(DataKaryawanPage aThis, boolean b) {
        }

        private void setVisible(boolean b) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
