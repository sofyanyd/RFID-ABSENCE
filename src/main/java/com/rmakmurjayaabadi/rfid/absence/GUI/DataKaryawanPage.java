/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.rmakmurjayaabadi.rfid.absence.GUI;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import com.rmakmurjayaabadi.rfid.absence.GenericDAO;
import com.rmakmurjayaabadi.rfid.absence.Karyawan;
import com.rmakmurjayaabadi.rfid.absence.MongoManager;
import java.awt.*;
import java.util.List; 
import javax.swing.*;
import javax.swing.table.*;
import com.mongodb.client.model.Filters;
/**
 *
 * @author sofya
 */
public class DataKaryawanPage extends javax.swing.JFrame {
    
    private JTable tblKaryawan;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;

    private final Color PRIMARY_COLOR = new Color(2, 63, 75);      
    private final Color BACKGROUND_COLOR = new Color(226, 222, 222); 
    private final Color SIDEBAR_BG = Color.WHITE;
    private final Color BUTTON_GRAY = new Color(217, 217, 217);     
    private final Color BUTTON_ACTIVE = PRIMARY_COLOR;

    public DataKaryawanPage() {
        initManualComponents();
        this.setLocationRelativeTo(null);
        
        try {
            MongoManager.getDatabase(); 
            loadDataKaryawan();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initManualComponents() {
        setTitle("ADMINISTRATOR - Data Karyawan");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(BACKGROUND_COLOR);

        createSidebar();
        createHeader();
        createContentArea();
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

        JButton btnDataKaryawan = createMenuButton("Data Karyawan", true);
        btnDataKaryawan.setBounds(20, yPos, btnWidth, btnHeight);
        sidebar.add(btnDataKaryawan);
        yPos += btnSpacing;

        JButton btnRiwayat = createMenuButton("Riwayat Absensi", false);
        btnRiwayat.setBounds(20, yPos, btnWidth, btnHeight);
        btnRiwayat.addActionListener(e -> {
            new RiwayatAbsensiPage().setVisible(true);
            this.dispose();
        });
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
            int confirm = JOptionPane.showConfirmDialog(this, "Apakah yakin ingin keluar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                MongoManager.close();
                System.exit(0);
            }
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

    private void createContentArea() {
        JLabel lblTitle = new JLabel("Data Karyawan");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setBounds(270, 100, 300, 30);
        add(lblTitle);

        int buttonWidth = 180;
        int buttonHeight = 80;
        int buttonGap = 40;
        int buttonY = 145;

        JButton btnTambah = new JButton("Tambah Karyawan Baru");
        btnTambah.setBackground(BUTTON_GRAY);
        btnTambah.setForeground(Color.BLACK);
        btnTambah.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnTambah.setBounds(270, buttonY, buttonWidth, buttonHeight);
        btnTambah.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTambah.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        btnTambah.addActionListener(e -> {
            // FIX: Mengambil Frame induk dengan aman agar JDialog tidak error list differ length
            Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
            new TambahKaryawanDialog(parentFrame, true).setVisible(true);
            loadDataKaryawan(); 
        });
        add(btnTambah);

        JButton btnEdit = new JButton("Edit Karyawan");
        btnEdit.setBackground(BUTTON_GRAY);
        btnEdit.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnEdit.setBounds(270 + buttonWidth + buttonGap, buttonY, buttonWidth, buttonHeight);
        btnEdit.addActionListener(e -> editKaryawan());
        add(btnEdit);

        JButton btnHapus = new JButton("Hapus Karyawan");
        btnHapus.setBackground(BUTTON_GRAY);
        btnHapus.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnHapus.setBounds(270 + (buttonWidth + buttonGap) * 2, buttonY, buttonWidth, buttonHeight);
        btnHapus.addActionListener(e -> hapusKaryawan());
        add(btnHapus);

        createKaryawanTable();
    }

    private void createKaryawanTable() {
        String[] cols = {"ID Karyawan", "Nama Karyawan", "Divisi", "Kontrak"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblKaryawan = new JTable(tableModel);
        tblKaryawan.setRowHeight(45);
        tblKaryawan.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tblKaryawan.setSelectionBackground(new Color(2, 63, 75, 30));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblKaryawan.getColumnCount(); i++) {
            tblKaryawan.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JTableHeader h = tblKaryawan.getTableHeader();
        h.setBackground(PRIMARY_COLOR);
        h.setForeground(Color.WHITE);
        h.setFont(new Font("SansSerif", Font.BOLD, 14));
        h.setPreferredSize(new Dimension(100, 40));

        JScrollPane sp = new JScrollPane(tblKaryawan);
        sp.setBounds(270, 245, 900, 420);
        add(sp);
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
            e.printStackTrace();
        }
    }

    private void editKaryawan() {
        int selectedRow = tblKaryawan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Silakan pilih karyawan yang ingin diedit!");
            return;
        }
        String idKaryawan = tableModel.getValueAt(selectedRow, 0).toString();
        JOptionPane.showMessageDialog(this, "Edit karyawan: " + idKaryawan + "\n(Fitur dalam pengembangan)");
    }

    private void hapusKaryawan() {
        int selectedRow = tblKaryawan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Silakan pilih karyawan yang ingin dihapus!");
            return;
        }

        String idKaryawan = tableModel.getValueAt(selectedRow, 0).toString();
        String namaKaryawan = tableModel.getValueAt(selectedRow, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Apakah Anda yakin ingin menghapus karyawan:\n" + namaKaryawan + " (" + idKaryawan + ") dari database?", 
            "Konfirmasi Hapus Permanen", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                GenericDAO<Karyawan> dao = new GenericDAO<>("karyawan", Karyawan.class);
                dao.delete(Filters.eq("idKaryawan", idKaryawan)); 
                
                tableModel.removeRow(selectedRow); 
                JOptionPane.showMessageDialog(this, "Karyawan " + namaKaryawan + " berhasil dihapus secara permanen!");
                loadDataKaryawan();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
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
            FlatLightLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.awt.EventQueue.invokeLater(() -> {
            new DataKaryawanPage().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
