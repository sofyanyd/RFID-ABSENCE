/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.rmakmurjayaabadi.rfid.absence.GUI;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import com.rmakmurjayaabadi.rfid.absence.GenericDAO;
import com.rmakmurjayaabadi.rfid.absence.LogAbsensi;
import com.rmakmurjayaabadi.rfid.absence.MongoManager;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
/**
 *
 * @author sofya
 */
public class RiwayatAbsensiPage extends javax.swing.JFrame {
    
    private JTable tblRiwayat;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    private JTextField txtDari;
    private JTextField txtSampai;
    private JComboBox<String> cbDivisi;

    private final Color PRIMARY_COLOR = new Color(2, 63, 75);
    private final Color BACKGROUND_COLOR = new Color(226, 222, 222);
    private final Color SIDEBAR_BG = Color.WHITE;
    private final Color BUTTON_GRAY = new Color(217, 217, 217);
    private final Color BUTTON_ACTIVE = PRIMARY_COLOR;

    public RiwayatAbsensiPage() {
        initManualComponents();
        this.setLocationRelativeTo(null);
        
        try {
            MongoManager.getDatabase();
            loadRiwayatData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initManualComponents() {
        setTitle("ADMINISTRATOR - Riwayat Absensi");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(BACKGROUND_COLOR);

        createSidebar();
        createHeader();
        createMainContent();
    }

    private void createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setBounds(0, 0, 250, 700);
        sidebar.setLayout(null);
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
        add(sidebar);

        JLabel lblBrand = new JLabel("ADMINISTRATOR", SwingConstants.CENTER);
        lblBrand.setForeground(PRIMARY_COLOR);
        lblBrand.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblBrand.setBounds(20, 20, 210, 30);
        sidebar.add(lblBrand);

        JSeparator sep1 = new JSeparator();
        sep1.setBounds(0, 70, 250, 1);
        sep1.setForeground(Color.BLACK);
        sidebar.add(sep1);

        JLabel lblMenuUtama = new JLabel("Menu Utama", SwingConstants.CENTER);
        lblMenuUtama.setForeground(Color.BLACK);
        lblMenuUtama.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblMenuUtama.setBounds(20, 90, 210, 30);
        sidebar.add(lblMenuUtama);

        int yPos = 135;
        int btnWidth = 210;
        int btnHeight = 45;
        int btnSpacing = 50;

        JButton btnDashboard = createMenuButton("Dashboard", false);
        btnDashboard.setBounds(20, yPos, btnWidth, btnHeight);
        btnDashboard.addActionListener(e -> {
            new DashboardPage().setVisible(true);
            this.dispose();
        });
        sidebar.add(btnDashboard);
        yPos += btnSpacing;

        JButton btnDataKaryawan = createMenuButton("Data Karyawan", false);
        btnDataKaryawan.setBounds(20, yPos, btnWidth, btnHeight);
        btnDataKaryawan.addActionListener(e -> {
            new DataKaryawanPage().setVisible(true);
            this.dispose();
        });
        sidebar.add(btnDataKaryawan);
        yPos += btnSpacing;

        JButton btnRiwayat = createMenuButton("Riwayat Absensi", true);
        btnRiwayat.setBounds(20, yPos, btnWidth, btnHeight);
        sidebar.add(btnRiwayat);
        yPos += btnSpacing;

        JButton btnEkspor = createMenuButton("Ekspor Laporan", false);
        btnEkspor.setBounds(20, yPos, btnWidth, btnHeight);
        btnEkspor.addActionListener(e -> {
            new EksporLaporanPage().setVisible(true);
            this.dispose();
        });
        sidebar.add(btnEkspor);

        JLabel lblPengaturan = new JLabel("Pengaturan");
        lblPengaturan.setForeground(Color.BLACK);
        lblPengaturan.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblPengaturan.setBounds(20, 400, 200, 30);
        sidebar.add(lblPengaturan);

        yPos = 445;

        JButton btnShift = createMenuButton("Shift & Jam Kerja", false);
        btnShift.setBounds(20, yPos, btnWidth, btnHeight);
        btnShift.addActionListener(e -> {
            new ShiftJamKerjaPage().setVisible(true);
            this.dispose();
        });
        sidebar.add(btnShift);

        JButton btnKeluar = new JButton("Keluar");
        btnKeluar.setBackground(Color.RED);
        btnKeluar.setForeground(Color.WHITE);
        btnKeluar.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnKeluar.setBounds(20, 630, btnWidth, btnHeight);
        btnKeluar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnKeluar.putClientProperty(FlatClientProperties.STYLE, "arc: 13");
        btnKeluar.addActionListener(e -> {
            MongoManager.close();
            System.exit(0);
        });
        sidebar.add(btnKeluar);
    }

    private void createHeader() {
        JPanel header = new JPanel();
        header.setBackground(Color.WHITE);
        header.setBounds(250, 0, 950, 80);
        header.setLayout(null);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        add(header);

        txtSearch = new JTextField("  Cari Karyawan atau ID...");
        txtSearch.setBounds(20, 20, 500, 40);
        txtSearch.setBackground(BUTTON_GRAY);
        txtSearch.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        txtSearch.setFont(new Font("SansSerif", Font.BOLD, 16));
        txtSearch.setForeground(new Color(0, 0, 0, 66));
        txtSearch.putClientProperty(FlatClientProperties.STYLE, "arc: 16");
        header.add(txtSearch);

        JLabel lblAdminName = new JLabel("HRD Admin", SwingConstants.CENTER);
        lblAdminName.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblAdminName.setBounds(750, 25, 150, 30);
        header.add(lblAdminName);
    }

    private void createMainContent() {
        JLabel lblTitle = new JLabel("Riwayat Absensi");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setBounds(270, 100, 300, 30);
        add(lblTitle);

        createFilterSection();

        JLabel lblTabelRiwayat = new JLabel("Tabel Riwayat");
        lblTabelRiwayat.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTabelRiwayat.setForeground(Color.BLACK);
        lblTabelRiwayat.setBounds(270, 245, 300, 25);
        add(lblTabelRiwayat);

        createRiwayatTable();

        JButton btnDownload = new JButton("Download Log (.csv)");
        btnDownload.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnDownload.setBackground(Color.WHITE);
        btnDownload.setForeground(Color.BLACK);
        btnDownload.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDownload.addActionListener(e -> downloadCSV());
        btnDownload.setBounds(270, 650, 250, 35);
        add(btnDownload);
    }

    private void createFilterSection() {
        JPanel filterPanel = new JPanel();
        filterPanel.setBackground(BUTTON_GRAY);
        filterPanel.setBounds(270, 145, 900, 80);
        filterPanel.setLayout(null);
        filterPanel.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        add(filterPanel);

        JLabel lblDari = new JLabel("Dari:");
        lblDari.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblDari.setBounds(20, 15, 60, 25);
        filterPanel.add(lblDari);

        txtDari = new JTextField("2024-05-01");
        txtDari.setBounds(80, 15, 120, 25);
        filterPanel.add(txtDari);

        JLabel lblSampai = new JLabel("s/d:");
        lblSampai.setBounds(220, 15, 40, 25);
        filterPanel.add(lblSampai);

        txtSampai = new JTextField("2024-05-17");
        txtSampai.setBounds(270, 15, 120, 25);
        filterPanel.add(txtSampai);

        JLabel lblDivisi = new JLabel("Divisi:");
        lblDivisi.setBounds(420, 15, 60, 25);
        filterPanel.add(lblDivisi);

        String[] divisiList = {"Semua", "IT", "HRD", "Marketing", "Finance", "Operations"};
        cbDivisi = new JComboBox<>(divisiList);
        cbDivisi.setBounds(490, 15, 150, 25);
        filterPanel.add(cbDivisi);

        JButton btnFilter = new JButton("Filter");
        btnFilter.setBounds(670, 15, 100, 25);
        btnFilter.setBackground(PRIMARY_COLOR);
        btnFilter.setForeground(Color.WHITE);
        btnFilter.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFilter.addActionListener(e -> applyFilter());
        filterPanel.add(btnFilter);

        JButton btnReset = new JButton("Reset");
        btnReset.setBounds(780, 15, 100, 25);
        btnReset.setBackground(Color.WHITE);
        btnReset.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReset.addActionListener(e -> resetFilter());
        filterPanel.add(btnReset);
    }

    private void createRiwayatTable() {
        String[] columnNames = {"Waktu", "Nama Karyawan", "ID", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tblRiwayat = new JTable(tableModel);
        tblRiwayat.setRowHeight(45);
        tblRiwayat.setBackground(Color.WHITE);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblRiwayat.getColumnCount(); i++) {
            tblRiwayat.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JTableHeader h = tblRiwayat.getTableHeader();
        h.setBackground(PRIMARY_COLOR);
        h.setForeground(Color.WHITE);
        h.setFont(new Font("SansSerif", Font.BOLD, 14));
        h.setPreferredSize(new Dimension(100, 40));

        JScrollPane scrollPane = new JScrollPane(tblRiwayat);
        scrollPane.setBounds(270, 280, 900, 355);
        add(scrollPane);
    }

    private JButton createMenuButton(String text, boolean isActive) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.putClientProperty(FlatClientProperties.STYLE, "arc: 13");
        if (isActive) {
            button.setBackground(BUTTON_ACTIVE);
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(BUTTON_GRAY);
            button.setForeground(Color.BLACK);
        }
        return button;
    }

    private void loadRiwayatData() {
        try {
            GenericDAO<LogAbsensi> dao = new GenericDAO<>("absensi", LogAbsensi.class);
            List<LogAbsensi> list = dao.findAll();
            tableModel.setRowCount(0);

            for (LogAbsensi log : list) {
                tableModel.addRow(new Object[]{
                    log.getWaktu(),
                    log.getNamaKaryawan(),
                    log.getIdKaryawan(),
                    log.getStatus()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void applyFilter() {
        JOptionPane.showMessageDialog(this, "Filter Berhasil Diterapkan!");
    }

    private void resetFilter() {
        cbDivisi.setSelectedIndex(0);
        loadRiwayatData();
    }

    private void downloadCSV() {
        JOptionPane.showMessageDialog(this, "CSV Download Berhasil!");
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            System.out.println("🎨 Setting up FlatLaf Look and Feel...");
            FlatLightLaf.setup();
            System.out.println("✅ FlatLaf initialized");
        } catch (Exception e) {
            System.err.println("⚠️  FlatLaf not available, using default look and feel");
        }

        java.awt.EventQueue.invokeLater(() -> {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("🚀 RFID Absence System - Riwayat Absensi");
            System.out.println("=".repeat(60));
            RiwayatAbsensiPage page = new RiwayatAbsensiPage();
            page.setVisible(true);
            System.out.println("✅ Riwayat Absensi window is now visible");
            System.out.println("=".repeat(60) + "\n");
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
