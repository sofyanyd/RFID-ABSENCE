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
public class DashboardPage extends javax.swing.JFrame {
    private JTable tblAbsensi;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    
    private JLabel lblTepatWaktuValue;
    private JLabel lblTerlambatValue;
    private JLabel lblAbsenValue;

    private final Color PRIMARY_COLOR = new Color(2, 63, 75);
    private final Color BACKGROUND_COLOR = new Color(226, 222, 222);
    private final Color SIDEBAR_BG = Color.WHITE;
    private final Color BUTTON_GRAY = new Color(217, 217, 217);
    private final Color BUTTON_ACTIVE = PRIMARY_COLOR;

    public DashboardPage() {
        // Panggil komponen grafis manual kita
        initManualComponents();
        this.setLocationRelativeTo(null);
        
        try {
            System.out.println("🔗 Membuka koneksi MongoDB...");
            MongoManager.getDatabase(); 
            
            System.out.println("📊 Sinkronisasi data live absensi dari database...");
            loadLiveAbsensi(); 
        } catch (Exception e) {
            System.err.println("⚠️ Gagal sinkronisasi otomatis dengan MongoDB Compass!");
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal terhubung ke database MongoDB!", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initManualComponents() {
        setTitle("ADMINISTRATOR - Dashboard");
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

        // Dashboard Button (Active)
        JButton btnDashboard = createMenuButton("Dashboard", true);
        btnDashboard.setBounds(20, yPos, btnWidth, btnHeight);
        sidebar.add(btnDashboard);
        yPos += btnSpacing;

        // Data Karyawan Button
        JButton btnDataKaryawan = createMenuButton("Data Karyawan", false);
        btnDataKaryawan.setBounds(20, yPos, btnWidth, btnHeight);
        btnDataKaryawan.addActionListener(e -> {
            new DataKaryawanPage().setVisible(true);
            this.dispose();
        });
        sidebar.add(btnDataKaryawan);
        yPos += btnSpacing;

        // Riwayat Absensi Button
        JButton btnRiwayat = createMenuButton("Riwayat Absensi", false);
        btnRiwayat.setBounds(20, yPos, btnWidth, btnHeight);
        btnRiwayat.addActionListener(e -> {
            new RiwayatAbsensiPage().setVisible(true);
            this.dispose();
        });
        sidebar.add(btnRiwayat);
        yPos += btnSpacing;

        // Ekspor Laporan Button
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
        
        // Shift & Jam Kerja Button
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
        JLabel lblTitle = new JLabel("Dashboard");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setBounds(270, 100, 300, 30);
        add(lblTitle);

        createStatisticsCards();

        JLabel lblLiveAbsensi = new JLabel("Live Absensi");
        lblLiveAbsensi.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblLiveAbsensi.setForeground(Color.BLACK);
        lblLiveAbsensi.setBounds(270, 260, 300, 30);
        add(lblLiveAbsensi);

        createAbsensiTable();

        JButton btnDownload = new JButton("Download Log Hari Ini (.csv)");
        btnDownload.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnDownload.setBackground(Color.WHITE);
        btnDownload.setForeground(Color.BLACK);
        btnDownload.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDownload.setBounds(270, 650, 250, 35);
        add(btnDownload);
    }

    private void createStatisticsCards() {
        int xPos = 270;
        int yPos = 145;
        int cardWidth = 190;
        int cardHeight = 90;
        int gap = 50;

        lblTepatWaktuValue = new JLabel("0 Orang");
        createStatCard("Tepat Waktu", lblTepatWaktuValue, xPos, yPos, cardWidth, cardHeight);
        xPos += cardWidth + gap;

        lblTerlambatValue = new JLabel("0 Orang");
        createStatCard("Terlambat", lblTerlambatValue, xPos, yPos, cardWidth, cardHeight);
        xPos += cardWidth + gap;

        lblAbsenValue = new JLabel("0 Orang");
        createStatCard("Absen", lblAbsenValue, xPos, yPos, cardWidth, cardHeight);
    }

    private void createStatCard(String title, JLabel valueLabel, int x, int y, int width, int height) {
        JPanel card = new JPanel();
        card.setLayout(new GridBagLayout());
        card.setBackground(BUTTON_GRAY);
        card.setBounds(x, y, width, height);
        card.putClientProperty(FlatClientProperties.STYLE, "arc: 10");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(5, 0, 5, 0);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridy = 0;
        card.add(titleLabel, gbc);

        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridy = 1;
        card.add(valueLabel, gbc);

        add(card);
    }

    private void createAbsensiTable() {
        String[] columnNames = {"Waktu", "Nama Karyawan", "ID Karyawan", "Status Absen"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tblAbsensi = new JTable(tableModel);
        tblAbsensi.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tblAbsensi.setRowHeight(45);
        tblAbsensi.setBackground(Color.WHITE);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblAbsensi.getColumnCount(); i++) {
            tblAbsensi.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JTableHeader h = tblAbsensi.getTableHeader();
        h.setBackground(PRIMARY_COLOR);
        h.setForeground(Color.WHITE);
        h.setFont(new Font("SansSerif", Font.BOLD, 14));
        h.setPreferredSize(new Dimension(100, 40));

        JScrollPane scrollPane = new JScrollPane(tblAbsensi);
        scrollPane.setBounds(270, 305, 900, 330);
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
    
    public void loadLiveAbsensi() {
        try {
            // MENARIK DATA ASLI DARI DATABASE MONGODB
            GenericDAO<LogAbsensi> dao = new GenericDAO<>("absensi", LogAbsensi.class);
            List<LogAbsensi> list = dao.findAll();
            
            tableModel.setRowCount(0); // Bersihkan sisa tabel lama
            int tepatWaktuCount = 0;
            int terlambatCount = 0;
            int absenCount = 0;

            System.out.println("DEBUG: Ditemukan " + list.size() + " dokumen absensi di DB.");

            for (LogAbsensi log : list) {
                tableModel.addRow(new Object[]{
                    log.getWaktu(),
                    log.getNamaKaryawan(),
                    log.getIdKaryawan(),
                    log.getStatus()
                });

                String status = log.getStatus() != null ? log.getStatus().toLowerCase() : "";
                if (status.contains("tepat") || status.contains("waktu")) {
                    tepatWaktuCount++;
                } else if (status.contains("telat") || status.contains("lambat")) {
                    terlambatCount++;
                } else {
                    absenCount++;
                }
            }

            // Update isi statistik box atas sesuai data riil MongoDB Compass
            lblTepatWaktuValue.setText(tepatWaktuCount + " Orang");
            lblTerlambatValue.setText(terlambatCount + " Orang");
            lblAbsenValue.setText(absenCount + " Orang");
            
        } catch (Exception e) {
            System.err.println("❌ Gagal membaca live data dari database: " + e.getMessage());
        }
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
            FlatLightLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.awt.EventQueue.invokeLater(() -> {
            new DashboardPage().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
