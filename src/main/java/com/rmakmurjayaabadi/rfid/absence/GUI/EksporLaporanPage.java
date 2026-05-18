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
/**
 *
 * @author sofya
 */
public class EksporLaporanPage extends javax.swing.JFrame {
    
    private JTextField txtSearch;
    private JTextField txtDari;
    private JTextField txtSampai;
    private JComboBox<String> cbDivisi;
    private ButtonGroup formatGroup;
    private JRadioButton rbPDF;
    private JRadioButton rbExcel;
    private JRadioButton rbCSV;

    private final Color PRIMARY_COLOR = new Color(2, 63, 75);
    private final Color BACKGROUND_COLOR = new Color(226, 222, 222);
    private final Color SIDEBAR_BG = Color.WHITE;
    private final Color BUTTON_GRAY = new Color(217, 217, 217);
    private final Color BUTTON_ACTIVE = PRIMARY_COLOR;

    public EksporLaporanPage() {
        System.out.println("=".repeat(60));
        System.out.println("🚀 Starting Ekspor Laporan Page...");
        System.out.println("=".repeat(60));

        try {
            System.out.println("📝 Step 1: Connecting to MongoDB (optional)...");
            MongoManager.getDatabase();

            System.out.println("📝 Step 2: Initializing UI components...");
            initManualComponents();

            System.out.println("📝 Step 3: Centering window...");
            this.setLocationRelativeTo(null);

            System.out.println("✅ Ekspor Laporan Page initialized successfully!");
            System.out.println("=".repeat(60));
        } catch (Exception e) {
            System.err.println("❌ CRITICAL ERROR in EksporLaporanPage constructor:");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Error initializing Ekspor Laporan page:\n" + e.getMessage() +
                "\n\nCheck console for details.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initManualComponents() {
        System.out.println("  🔧 Setting up frame...");
        setTitle("ADMINISTRATOR - Ekspor Laporan");
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

        JButton btnEkspor = createMenuButton("Ekspor Laporan", true);
        btnEkspor.setBounds(20, yPos, btnWidth, btnHeight);
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
            try {
                System.out.println("🔄 Navigating to Shift & Jam Kerja...");
                new ShiftJamKerjaPage().setVisible(true);
                this.dispose();
            } catch (Exception ex) {
                System.err.println("❌ Error opening Shift & Jam Kerja:");
                ex.printStackTrace();
            }
        });
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
        JLabel lblTitle = new JLabel("Konfigurasi Laporan");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setBounds(270, 100, 300, 30);
        add(lblTitle);

        createConfigPanel();
    }

    private void createConfigPanel() {
        JPanel configPanel = new JPanel();
        configPanel.setBackground(BUTTON_GRAY);
        configPanel.setBounds(270, 150, 900, 450);
        configPanel.setLayout(null);
        try {
            configPanel.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        } catch (Exception ignored) {}
        add(configPanel);

        int yPos = 30;

        JLabel lblPeriode = new JLabel("Periode Laporan:");
        lblPeriode.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblPeriode.setBounds(40, yPos, 200, 25);
        configPanel.add(lblPeriode);
        yPos += 40;

        JLabel lblDari = new JLabel("Dari:");
        lblDari.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblDari.setBounds(60, yPos, 60, 25);
        configPanel.add(lblDari);

        txtDari = new JTextField("2024-05-01");
        txtDari.setBounds(130, yPos, 150, 30);
        txtDari.setFont(new Font("SansSerif", Font.PLAIN, 14));
        configPanel.add(txtDari);

        JLabel lblSampai = new JLabel("s/d:");
        lblSampai.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblSampai.setBounds(300, yPos, 40, 25);
        configPanel.add(lblSampai);

        txtSampai = new JTextField("2024-05-17");
        txtSampai.setBounds(350, yPos, 150, 30);
        txtSampai.setFont(new Font("SansSerif", Font.PLAIN, 14));
        configPanel.add(txtSampai);
        yPos += 60;

        JLabel lblDivisi = new JLabel("Divisi:");
        lblDivisi.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblDivisi.setBounds(40, yPos, 200, 25);
        configPanel.add(lblDivisi);
        yPos += 40;

        String[] divisiList = {"Semua Divisi", "IT", "HRD", "Marketing", "Finance", "Operations"};
        cbDivisi = new JComboBox<>(divisiList);
        cbDivisi.setBounds(60, yPos, 250, 30);
        cbDivisi.setFont(new Font("SansSerif", Font.PLAIN, 14));
        configPanel.add(cbDivisi);
        yPos += 70;

        JLabel lblFormat = new JLabel("Format Ekspor:");
        lblFormat.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblFormat.setBounds(40, yPos, 200, 25);
        configPanel.add(lblFormat);
        yPos += 40;

        formatGroup = new ButtonGroup();

        rbPDF = new JRadioButton("PDF");
        rbPDF.setFont(new Font("SansSerif", Font.PLAIN, 14));
        rbPDF.setBackground(BUTTON_GRAY);
        rbPDF.setBounds(60, yPos, 100, 25);
        formatGroup.add(rbPDF);
        configPanel.add(rbPDF);

        rbExcel = new JRadioButton("Excel (.xlsx)", true);
        rbExcel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        rbExcel.setBackground(BUTTON_GRAY);
        rbExcel.setBounds(170, yPos, 150, 25);
        formatGroup.add(rbExcel);
        configPanel.add(rbExcel);

        rbCSV = new JRadioButton("CSV");
        rbCSV.setFont(new Font("SansSerif", Font.PLAIN, 14));
        rbCSV.setBackground(BUTTON_GRAY);
        rbCSV.setBounds(330, yPos, 100, 25);
        formatGroup.add(rbCSV);
        configPanel.add(rbCSV);
        yPos += 80;

        JButton btnGenerate = new JButton("GENERATE & DOWNLOAD LAPORAN");
        btnGenerate.setBounds(60, yPos, 780, 50);
        btnGenerate.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnGenerate.setBackground(PRIMARY_COLOR);
        btnGenerate.setForeground(Color.WHITE);
        btnGenerate.setFocusPainted(false);
        btnGenerate.setBorder(BorderFactory.createEmptyBorder());
        btnGenerate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        try {
            btnGenerate.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        } catch (Exception ignored) {}
        btnGenerate.addActionListener(e -> generateReport());
        configPanel.add(btnGenerate);
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

    private void generateReport() {
        String dari = txtDari.getText();
        String sampai = txtSampai.getText();
        String divisi = (String) cbDivisi.getSelectedItem();
        String format = rbPDF.isSelected() ? "PDF" :
                        rbExcel.isSelected() ? "Excel" : "CSV";

        System.out.println("📊 Generating report...");
        System.out.println("  Periode: " + dari + " - " + sampai);
        System.out.println("  Divisi: " + divisi);
        System.out.println("  Format: " + format);

        JOptionPane.showMessageDialog(this,
            "Laporan akan diunduh!\n\n" +
            "Periode: " + dari + " s/d " + sampai + "\n" +
            "Divisi: " + divisi + "\n" +
            "Format: " + format + "\n\n" +
            "(Fitur dalam pengembangan)",
            "Generate Laporan",
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
            System.out.println("🚀 RFID Absence System - Ekspor Laporan");
            System.out.println("=".repeat(60));
            EksporLaporanPage page = new EksporLaporanPage();
            page.setVisible(true);
            System.out.println("✅ Ekspor Laporan window is now visible");
            System.out.println("=".repeat(60) + "\n");
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
