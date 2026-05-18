/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.rmakmurjayaabadi.rfid.absence.GUI;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import com.rmakmurjayaabadi.rfid.absence.MongoManager;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
/**
 *
 * @author sofya
 */
public class ShiftJamKerjaPage extends javax.swing.JFrame {
    
    private JTable tblShift;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;

    private final Color PRIMARY_COLOR = new Color(2, 63, 75);
    private final Color BACKGROUND_COLOR = new Color(226, 222, 222);
    private final Color SIDEBAR_BG = Color.WHITE;
    private final Color BUTTON_GRAY = new Color(217, 217, 217);
    private final Color BUTTON_ACTIVE = PRIMARY_COLOR;

    public ShiftJamKerjaPage() {
        System.out.println("=".repeat(60));
        System.out.println("🚀 Starting Shift & Jam Kerja Page...");
        System.out.println("=".repeat(60));

        try {
            System.out.println("📝 Step 1: Connecting to MongoDB (optional)...");
            MongoManager.getDatabase();

            System.out.println("📝 Step 2: Initializing UI components...");
            initManualComponents();

            System.out.println("📝 Step 3: Loading shift data...");
            loadShiftData();

            System.out.println("📝 Step 4: Centering window...");
            this.setLocationRelativeTo(null);

            System.out.println("✅ Shift & Jam Kerja Page initialized successfully!");
            System.out.println("=".repeat(60));
        } catch (Exception e) {
            System.err.println("❌ CRITICAL ERROR in ShiftJamKerjaPage constructor:");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Error initializing Shift & Jam Kerja page:\n" + e.getMessage() +
                "\n\nCheck console for details.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initManualComponents() {
        System.out.println("  🔧 Setting up frame...");
        setTitle("ADMINISTRATOR - Shift & Jam Kerja");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(BACKGROUND_COLOR);

        System.out.println("  🔧 Creating sidebar...");
        createSidebar();

        System.out.println("  🔧 Creating header...");
        createHeader();

        System.out.println("  🔧 Creating main content...");
        createMainContent();

        System.out.println("  ✅ UI components created");
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
            try {
                System.out.println("🔄 Navigating to Dashboard...");
                new DashboardPage().setVisible(true);
                this.dispose();
            } catch (Exception ex) {
                System.err.println("❌ Error opening Dashboard:");
                ex.printStackTrace();
            }
        });
        sidebar.add(btnDashboard);
        yPos += btnSpacing;

        JButton btnDataKaryawan = createMenuButton("Data Karyawan", false);
        btnDataKaryawan.setBounds(20, yPos, btnWidth, btnHeight);
        btnDataKaryawan.addActionListener(e -> {
            try {
                System.out.println("🔄 Navigating to Data Karyawan...");
                new DataKaryawanPage().setVisible(true);
                this.dispose();
            } catch (Exception ex) {
                System.err.println("❌ Error opening Data Karyawan:");
                ex.printStackTrace();
            }
        });
        sidebar.add(btnDataKaryawan);
        yPos += btnSpacing;

        JButton btnRiwayat = createMenuButton("Riwayat Absensi", false);
        btnRiwayat.setBounds(20, yPos, btnWidth, btnHeight);
        btnRiwayat.addActionListener(e -> {
            try {
                System.out.println("🔄 Navigating to Riwayat Absensi...");
                new RiwayatAbsensiPage().setVisible(true);
                this.dispose();
            } catch (Exception ex) {
                System.err.println("❌ Error opening Riwayat Absensi:");
                ex.printStackTrace();
            }
        });
        sidebar.add(btnRiwayat);
        yPos += btnSpacing;

        JButton btnEkspor = createMenuButton("Ekspor Laporan", false);
        btnEkspor.setBounds(20, yPos, btnWidth, btnHeight);
        btnEkspor.addActionListener(e -> {
            try {
                System.out.println("🔄 Navigating to Ekspor Laporan...");
                new EksporLaporanPage().setVisible(true);
                this.dispose();
            } catch (Exception ex) {
                System.err.println("❌ Error opening Ekspor Laporan:");
                ex.printStackTrace();
            }
        });
        sidebar.add(btnEkspor);

        JLabel lblPengaturan = new JLabel("Pengaturan");
        lblPengaturan.setForeground(Color.BLACK);
        lblPengaturan.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblPengaturan.setBounds(20, 400, 200, 30);
        sidebar.add(lblPengaturan);

        yPos = 445;

        JButton btnShift = createMenuButton("Shift & Jam Kerja", true);
        btnShift.setBounds(20, yPos, btnWidth, btnHeight);
        sidebar.add(btnShift);

        JButton btnKeluar = new JButton("Keluar");
        btnKeluar.setBackground(Color.RED);
        btnKeluar.setForeground(Color.WHITE);
        btnKeluar.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnKeluar.setBounds(20, 630, btnWidth, btnHeight);
        btnKeluar.setFocusPainted(false);
        btnKeluar.setBorder(BorderFactory.createEmptyBorder());
        btnKeluar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        try {
            btnKeluar.putClientProperty(FlatClientProperties.STYLE, "arc: 13");
        } catch (Exception ignored) {}
        btnKeluar.addActionListener(e -> {
            System.out.println("👋 Exiting application...");
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
        try {
            txtSearch.putClientProperty(FlatClientProperties.STYLE, "arc: 16");
        } catch (Exception ignored) {}
        header.add(txtSearch);

        JLabel lblAdminName = new JLabel("HRD Admin", SwingConstants.CENTER);
        lblAdminName.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblAdminName.setBounds(750, 25, 150, 30);
        header.add(lblAdminName);
    }

    private void createMainContent() {
        JLabel lblTitle = new JLabel("Shift & Jam Kerja");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setBounds(270, 100, 300, 30);
        add(lblTitle);

        int buttonWidth = 230;
        int buttonHeight = 80;
        int buttonGap = 40;
        int buttonY = 145;

        JButton btnTambah = new JButton("Tambah Shift Baru");
        btnTambah.setBackground(BUTTON_GRAY);
        btnTambah.setForeground(Color.BLACK);
        btnTambah.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnTambah.setBounds(270, buttonY, buttonWidth, buttonHeight);
        btnTambah.setFocusPainted(false);
        btnTambah.setBorder(BorderFactory.createEmptyBorder());
        btnTambah.setCursor(new Cursor(Cursor.HAND_CURSOR));
        try {
            btnTambah.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        } catch (Exception ignored) {}
        btnTambah.addActionListener(e -> tambahShift());
        add(btnTambah);

        JButton btnLibur = new JButton("Atur Libur Nasional");
        btnLibur.setBackground(BUTTON_GRAY);
        btnLibur.setForeground(Color.BLACK);
        btnLibur.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnLibur.setBounds(270 + buttonWidth + buttonGap, buttonY, buttonWidth, buttonHeight);
        btnLibur.setFocusPainted(false);
        btnLibur.setBorder(BorderFactory.createEmptyBorder());
        btnLibur.setCursor(new Cursor(Cursor.HAND_CURSOR));
        try {
            btnLibur.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        } catch (Exception ignored) {}
        btnLibur.addActionListener(e -> aturLibur());
        add(btnLibur);

        createShiftTable();
    }

    private void createShiftTable() {
        String[] cols = {"Nama Shift", "Masuk", "Pulang", "Toleransi"};

        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblShift = new JTable(tableModel);

        tblShift.setRowHeight(45);
        tblShift.setShowGrid(true);
        tblShift.setGridColor(Color.BLACK);
        tblShift.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tblShift.setSelectionBackground(new Color(2, 63, 75, 30));
        tblShift.setBackground(Color.WHITE);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setFont(new Font("SansSerif", Font.PLAIN, 14));

        for (int i = 0; i < tblShift.getColumnCount(); i++) {
            tblShift.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JTableHeader h = tblShift.getTableHeader();
        h.setBackground(PRIMARY_COLOR);
        h.setForeground(Color.WHITE);
        h.setFont(new Font("SansSerif", Font.BOLD, 14));
        h.setPreferredSize(new Dimension(100, 40));

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) h.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        tblShift.getColumnModel().getColumn(0).setPreferredWidth(250);
        tblShift.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblShift.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblShift.getColumnModel().getColumn(3).setPreferredWidth(150);

        JScrollPane sp = new JScrollPane(tblShift);
        sp.setBounds(270, 245, 900, 420);
        sp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(sp);

        System.out.println("  ✅ Shift table structure created");
    }

    private JButton createMenuButton(String text, boolean isActive) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        try {
            button.putClientProperty(FlatClientProperties.STYLE, "arc: 13");
        } catch (Exception ignored) {}

        if (isActive) {
            button.setBackground(BUTTON_ACTIVE);
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(BUTTON_GRAY);
            button.setForeground(Color.BLACK);
        }

        return button;
    }

    private void loadShiftData() {
        try {
            System.out.println("  📊 Loading shift data...");
            tableModel.setRowCount(0);

            Object[][] sampleShifts = {
                {"Shift Pagi", "08:00", "16:00", "15 menit"},
                {"Shift Siang", "12:00", "20:00", "15 menit"},
                {"Shift Sore", "16:00", "00:00", "15 menit"},
                {"Shift Malam", "20:00", "04:00", "15 menit"}
            };

            for (Object[] shift : sampleShifts) {
                tableModel.addRow(shift);
            }

            System.out.println("  ✅ Successfully loaded " + sampleShifts.length + " shift records");
        } catch (Exception e) {
            System.err.println("  ❌ Error loading shift data:");
            e.printStackTrace();
        }
    }

    private void tambahShift() {
        System.out.println("➕ Opening Tambah Shift dialog...");
        JOptionPane.showMessageDialog(this,
            "Form Tambah Shift Baru\n(Fitur dalam pengembangan)",
            "Tambah Shift",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void aturLibur() {
        System.out.println("📅 Opening Atur Libur Nasional dialog...");
        JOptionPane.showMessageDialog(this,
            "Form Atur Libur Nasional\n(Fitur dalam pengembangan)",
            "Libur Nasional",
            JOptionPane.INFORMATION_MESSAGE);
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
            System.out.println("🚀 RFID Absence System - Shift & Jam Kerja");
            System.out.println("=".repeat(60));
            ShiftJamKerjaPage page = new ShiftJamKerjaPage();
            page.setVisible(true);
            System.out.println("✅ Shift & Jam Kerja window is now visible");
            System.out.println("=".repeat(60) + "\n");
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
