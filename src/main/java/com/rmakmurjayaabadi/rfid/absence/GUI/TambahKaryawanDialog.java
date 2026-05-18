/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.rmakmurjayaabadi.rfid.absence.GUI;

import com.formdev.flatlaf.FlatClientProperties;
import com.rmakmurjayaabadi.rfid.absence.GenericDAO;
import com.rmakmurjayaabadi.rfid.absence.Karyawan;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author sofya
 */
public class TambahKaryawanDialog extends javax.swing.JDialog {
    
    private JTextField txtUidRfid;
    private JTextField txtIdKaryawan;
    private JTextField txtNamaLengkap;
    private JComboBox<String> cmbDivisi;
    private JComboBox<String> cmbKontrak;
    private JButton btnSimpan;
    private JButton btnBatal;

    private final Color PRIMARY_COLOR = new Color(2, 63, 75); // Teal Tua Figma
    private final Color BUTTON_GRAY = new Color(217, 217, 217);

    public TambahKaryawanDialog(java.awt.Frame parent, boolean modal) {
        super(parent, "Tambah Karyawan Baru", modal);
        initManualComponents();
        setLocationRelativeTo(parent);
    }

    private void initManualComponents() {
        setSize(480, 640);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);

        // --- 1. HEADER TITLE ---
        JLabel lblTitle = new JLabel("Tambah Karyawan Baru");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(PRIMARY_COLOR);
        lblTitle.setBounds(40, 20, 400, 40);
        add(lblTitle);

        int yPos = 80;
        int fieldWidth = 390;
        int fieldHeight = 40;
        int spacing = 75;
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);

        // --- 2. INPUT FIELDS WITH FLATLAF STYLING ---
        
        // UID RFID
        JLabel lblUid = new JLabel("UID RFID");
        lblUid.setFont(labelFont);
        lblUid.setBounds(40, yPos, 200, 25);
        add(lblUid);

        txtUidRfid = new JTextField();
        txtUidRfid.setFont(inputFont);
        txtUidRfid.setBounds(40, yPos + 25, fieldWidth, fieldHeight);
        txtUidRfid.putClientProperty(FlatClientProperties.STYLE, "arc: 12");
        add(txtUidRfid);

        yPos += spacing;

        // ID Karyawan
        JLabel lblId = new JLabel("ID Karyawan");
        lblId.setFont(labelFont);
        lblId.setBounds(40, yPos, 200, 25);
        add(lblId);

        txtIdKaryawan = new JTextField();
        txtIdKaryawan.setFont(inputFont);
        txtIdKaryawan.setBounds(40, yPos + 25, fieldWidth, fieldHeight);
        txtIdKaryawan.putClientProperty(FlatClientProperties.STYLE, "arc: 12");
        add(txtIdKaryawan);

        yPos += spacing;

        // Nama Lengkap
        JLabel lblNama = new JLabel("Nama Lengkap");
        lblNama.setFont(labelFont);
        lblNama.setBounds(40, yPos, 200, 25);
        add(lblNama);

        txtNamaLengkap = new JTextField();
        txtNamaLengkap.setFont(inputFont);
        txtNamaLengkap.setBounds(40, yPos + 25, fieldWidth, fieldHeight);
        txtNamaLengkap.putClientProperty(FlatClientProperties.STYLE, "arc: 12");
        add(txtNamaLengkap);

        yPos += spacing;

        // Divisi Dropdown (Sesuai mockup Figma)
        JLabel lblDivisi = new JLabel("Divisi");
        lblDivisi.setFont(labelFont);
        lblDivisi.setBounds(40, yPos, 200, 25);
        add(lblDivisi);

        String[] divisiOptions = {"Operasional", "Human Resource (HRD)", "Engineering (ENG)", "Finance (FIN)", "Safety (SAF)"};
        cmbDivisi = new JComboBox<>(divisiOptions);
        cmbDivisi.setFont(inputFont);
        cmbDivisi.setBounds(40, yPos + 25, fieldWidth, fieldHeight);
        cmbDivisi.setBackground(Color.WHITE);
        cmbDivisi.putClientProperty(FlatClientProperties.STYLE, "arc: 12");
        add(cmbDivisi);

        yPos += spacing;

        // Kontrak Dropdown (Sesuai mockup Figma)
        JLabel lblKontrak = new JLabel("Status Kontrak");
        lblKontrak.setFont(labelFont);
        lblKontrak.setBounds(40, yPos, 200, 25);
        add(lblKontrak);

        String[] kontrakOptions = {"3 Bulan", "6 Bulan", "12 Bulan", "24 Bulan"};
        cmbKontrak = new JComboBox<>(kontrakOptions);
        cmbKontrak.setFont(inputFont);
        cmbKontrak.setBounds(40, yPos + 25, fieldWidth, fieldHeight);
        cmbKontrak.setBackground(Color.WHITE);
        cmbKontrak.putClientProperty(FlatClientProperties.STYLE, "arc: 12");
        add(cmbKontrak);

        // --- 3. BUTTONS (Batal & Simpan) ---
        btnBatal = new JButton("Batal");
        btnBatal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBatal.setBackground(BUTTON_GRAY);
        btnBatal.setForeground(Color.BLACK);
        btnBatal.setBounds(40, 520, 185, 45);
        btnBatal.setFocusPainted(false);
        btnBatal.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBatal.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        btnBatal.addActionListener(e -> dispose());
        add(btnBatal);

        btnSimpan = new JButton("Simpan");
        btnSimpan.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSimpan.setBackground(PRIMARY_COLOR);
        btnSimpan.setForeground(Color.WHITE);
        btnSimpan.setBounds(245, 520, 185, 45);
        btnSimpan.setFocusPainted(false);
        btnSimpan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSimpan.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        btnSimpan.addActionListener(e -> simpanKaryawan());
        add(btnSimpan);
    }

    private void simpanKaryawan() {
        // Validasi ringkas
        if (txtUidRfid.getText().trim().isEmpty() || 
            txtIdKaryawan.getText().trim().isEmpty() || 
            txtNamaLengkap.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Mapping objek sesuai constructor Backend
            Karyawan karyawan = new Karyawan(
                txtUidRfid.getText().trim(),
                txtIdKaryawan.getText().trim(),
                txtNamaLengkap.getText().trim(),
                cmbDivisi.getSelectedItem().toString(),
                cmbKontrak.getSelectedItem().toString()
            );

            GenericDAO<Karyawan> dao = new GenericDAO<>("karyawan", Karyawan.class);
            dao.save(karyawan);

            JOptionPane.showMessageDialog(this, "Karyawan berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
