/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.rmakmurjayaabadi.rfid.absence.GUI;

import com.formdev.flatlaf.FlatClientProperties;
import com.rmakmurjayaabadi.rfid.absence.GenericDAO;
import com.rmakmurjayaabadi.rfid.absence.Karyawan;
import com.mongodb.client.model.Filters;
import java.awt.*;
import java.awt.event.ItemEvent;
import javax.swing.*;
/**
 *
 * @author sofya
 */
public class EditKaryawanDialog extends javax.swing.JDialog {
    
    private JTextField txtIdKaryawan;
    private JTextField txtNamaKaryawan;
    private JComboBox<String> cbDivisi;
    private JComboBox<String> cbKontrak;
    private JComboBox<String> cbDurasiBulan;
    private JButton btnSimpan;
    
    private String idTarget; 

    public EditKaryawanDialog(java.awt.Frame parent, boolean modal, String idKaryawan) {
        super(parent, modal);
        this.idTarget = idKaryawan;
        initManualComponents();
        loadDataTarget();
        this.setLocationRelativeTo(parent);
    }

    private void initManualComponents() {
        setTitle("ADMINISTRATOR - Edit Data Karyawan");
        setSize(480, 450); // Lebar digedein dikit biar muat combo berdampingan
        setLayout(null);
        getContentPane().setBackground(new Color(226, 222, 222));

        JLabel lblTitle = new JLabel("EDIT DATA KARYAWAN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitle.setForeground(new Color(2, 63, 75));
        lblTitle.setBounds(20, 20, 440, 30);
        add(lblTitle);

        int lblWidth = 120;
        int txtWidth = 270;
        int height = 30;
        int startY = 80;
        int gap = 45;

        // Field ID Karyawan
        JLabel lblId = new JLabel("ID Karyawan :");
        lblId.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblId.setBounds(30, startY, lblWidth, height);
        add(lblId);

        txtIdKaryawan = new JTextField();
        txtIdKaryawan.setBounds(150, startY, txtWidth, height);
        txtIdKaryawan.setEditable(false);
        txtIdKaryawan.setBackground(new Color(200, 200, 200)); 
        txtIdKaryawan.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        add(txtIdKaryawan);

        // Field Nama Karyawan
        startY += gap;
        JLabel lblNama = new JLabel("Nama Lengkap :");
        lblNama.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNama.setBounds(30, startY, lblWidth, height);
        add(lblNama);

        txtNamaKaryawan = new JTextField();
        txtNamaKaryawan.setBounds(150, startY, txtWidth, height);
        txtNamaKaryawan.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        add(txtNamaKaryawan);

        // Field Divisi
        startY += gap;
        JLabel lblDivisi = new JLabel("Divisi Kerja :");
        lblDivisi.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblDivisi.setBounds(30, startY, lblWidth, height);
        add(lblDivisi);

        String[] divisiOptions = {"IT", "HRD", "Marketing", "Finance", "Operations"};
        cbDivisi = new JComboBox<>(divisiOptions);
        cbDivisi.setBounds(150, startY, txtWidth, height);
        cbDivisi.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        add(cbDivisi);

        // Field Status Kontrak Utama
        startY += gap;
        JLabel lblKontrak = new JLabel("Status Kontrak :");
        lblKontrak.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblKontrak.setBounds(30, startY, lblWidth, height);
        add(lblKontrak);

        String[] kontrakOptions = {"Tetap", "Kontrak", "Magang"};
        cbKontrak = new JComboBox<>(kontrakOptions);
        cbKontrak.setBounds(150, startY, 130, height); // Lebar dipotong biar muat sebelahan
        cbKontrak.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        add(cbKontrak);

        // Field Durasi Bulan (Awalnya di-hide)
        String[] bulanOptions = {"3 Bulan", "6 Bulan", "12 Bulan"};
        cbDurasiBulan = new JComboBox<>(bulanOptions);
        cbDurasiBulan.setBounds(290, startY, 130, height); // Posisi pas di sebelah cbKontrak
        cbDurasiBulan.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        cbDurasiBulan.setVisible(false); // Sembunyikan default
        add(cbDurasiBulan);

        cbKontrak.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selected = cbKontrak.getSelectedItem().toString();
                if (selected.equals("Kontrak")) {
                    cbDurasiBulan.setVisible(true);
                } else {
                    cbDurasiBulan.setVisible(false);
                }
                revalidate();
                repaint();
            }
        });

        // Tombol Simpan Perubahan
        btnSimpan = new JButton("Simpan Perubahan");
        btnSimpan.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSimpan.setBackground(new Color(2, 63, 75));
        btnSimpan.setForeground(Color.WHITE);
        btnSimpan.setBounds(30, 330, 180, 40);
        btnSimpan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSimpan.putClientProperty(FlatClientProperties.STYLE, "arc: 12");
        btnSimpan.addActionListener(e -> updateKaryawanKeDatabase());
        add(btnSimpan);

        // Tombol Batal
        JButton btnBatal = new JButton("Batal");
        btnBatal.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnBatal.setBackground(Color.WHITE);
        btnBatal.setForeground(Color.BLACK);
        btnBatal.setBounds(240, 330, 180, 40);
        btnBatal.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBatal.putClientProperty(FlatClientProperties.STYLE, "arc: 12");
        btnBatal.addActionListener(e -> this.dispose());
        add(btnBatal);
    }

    private void loadDataTarget() {
        try {
            GenericDAO<Karyawan> dao = new GenericDAO<>("karyawan", Karyawan.class);
            Karyawan k = dao.findOne(Filters.eq("idKaryawan", idTarget));
            
            if (k != null) {
                txtIdKaryawan.setText(k.getIdKaryawan());
                txtNamaKaryawan.setText(k.getNamaLengkap());
                cbDivisi.setSelectedItem(k.getDivisi());
                
                String kontrakDb = k.getKontrak(); 
                
                if (kontrakDb != null && kontrakDb.startsWith("Kontrak")) {
                    cbKontrak.setSelectedItem("Kontrak");
                    cbDurasiBulan.setVisible(true);
                    
                    // Parsing durasi bulannya dari dalam kurung database
                    if (kontrakDb.contains("3 Bulan")) cbDurasiBulan.setSelectedItem("3 Bulan");
                    else if (kontrakDb.contains("6 Bulan")) cbDurasiBulan.setSelectedItem("6 Bulan");
                    else if (kontrakDb.contains("12 Bulan")) cbDurasiBulan.setSelectedItem("12 Bulan");
                } else {
                    cbKontrak.setSelectedItem(kontrakDb);
                    cbDurasiBulan.setVisible(false);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Data karyawan tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                this.dispose();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil data lama: " + e.getMessage());
            this.dispose();
        }
    }

    private void updateKaryawanKeDatabase() {
        String namaNew = txtNamaKaryawan.getText().trim();
        String divisiNew = cbDivisi.getSelectedItem().toString();
        String statusKontrakBase = cbKontrak.getSelectedItem().toString();
        
        String kontrakFinal;
        // Logic penggabungan string text untuk kontrak durasi bulan
        if (statusKontrakBase.equals("Kontrak")) {
            String durasiBulan = cbDurasiBulan.getSelectedItem().toString();
            kontrakFinal = "Kontrak (" + durasiBulan + ")"; 
        } else {
            kontrakFinal = statusKontrakBase; // Hasil murni: "Tetap" atau "Magang"
        }

        if (namaNew.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama karyawan tidak boleh dikosongkan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            GenericDAO<Karyawan> dao = new GenericDAO<>("karyawan", Karyawan.class);
            
            Karyawan kUpdated = new Karyawan();
            kUpdated.setIdKaryawan(idTarget);
            kUpdated.setNamaLengkap(namaNew);
            kUpdated.setDivisi(divisiNew);
            kUpdated.setKontrak(kontrakFinal);

            dao.update(Filters.eq("idKaryawan", idTarget), kUpdated);

            JOptionPane.showMessageDialog(this, "Data Karyawan berhasil diperbarui secara live!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal memperbarui database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
