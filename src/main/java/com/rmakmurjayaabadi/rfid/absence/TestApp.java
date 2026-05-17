package com.rmakmurjayaabadi.rfid.absence;

import com.mongodb.client.model.Filters;
import com.rmakmurjayaabadi.rfid.absence.GUI.DashboardPage;
import com.rmakmurjayaabadi.rfid.absence.GUI.DataKaryawanPage;
import java.util.List;

/**
 * Test class untuk verify aplikasi berjalan dengan baik
 * Run: java -cp "target/classes" com.rmakmurjayaabadi.rfid.absence.TestApp
 */
public class TestApp {

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("🧪 RFID Absence System - Test Application Suite");
        System.out.println("=".repeat(70));
        System.out.println();

        // Jalankan rangkaian pengujian backend
        testMongoDBConnection();
        testGenericDAOKaryawan();
        testGenericDAOLogAbsensi();

        // Navigasi GUI via parameter argumen NetBeans jika dibutuhkan
        if (args.length > 0 && args[0].equals("--gui")) {
            launchDashboard();
        } else if (args.length > 0 && args[0].equals("--data-karyawan")) {
            launchDataKaryawan();
        } else {
            System.out.println("\n💡 Tip: Run langsung file DashboardPage.java untuk melihat UI");
        }

        System.out.println("\n" + "=".repeat(70));
        System.out.println("✅ Seluruh rangkaian uji coba selesai!");
        System.out.println("=".repeat(70) + "\n");
    }

    private static void testMongoDBConnection() {
        System.out.println("📝 Test 1: Verifikasi Koneksi Driver MongoDB");
        System.out.println("-".repeat(70));
        try {
            MongoManager.getDatabase();
            if (MongoManager.isConnected()) {
                System.out.println("✅ Driver Terkoneksi Aktif ke Cluster Lokal!");
            } else {
                System.out.println("⚠️  MongoDB Offline / Tidak merespon (Aplikasi masuk Demo Mode)");
            }
        } catch (Exception e) {
            System.err.println("❌ Error koneksi: " + e.getMessage());
        }
        System.out.println();
    }

    private static void testGenericDAOKaryawan() {
        System.out.println("📝 Test 2: Pengujian Sinkronisasi DAO Karyawan");
        System.out.println("-".repeat(70));
        try {
            GenericDAO<Karyawan> dao = new GenericDAO<>("karyawan", Karyawan.class);
            List<Karyawan> list = dao.findAll();

            System.out.println("✅ Sukses memuat " + list.size() + " data objek karyawan.");

            if (!list.isEmpty()) {
                System.out.println("\n📊 Pratinjau Data Karyawan (Max 3):");
                for (int i = 0; i < Math.min(3, list.size()); i++) {
                    Karyawan k = list.get(i);
                    System.out.println("   [" + (i+1) + "] ID: " + k.getIdKaryawan() + " | Nama: " + k.getNamaLengkap() + " | Divisi: " + k.getDivisi());
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Error memuat data Karyawan: " + e.getMessage());
        }
        System.out.println();
    }

    private static void testGenericDAOLogAbsensi() {
        System.out.println("📝 Test 3: Pengujian Sinkronisasi DAO Log Absensi");
        System.out.println("-".repeat(70));
        try {
            // Memastikan penembakan nama koleksi "absensi" tepat sasaran seperti di Compass
            GenericDAO<LogAbsensi> dao = new GenericDAO<>("absensi", LogAbsensi.class);
            List<LogAbsensi> list = dao.findAll();

            System.out.println("✅ Sukses memuat " + list.size() + " rekaman log aktivitas.");

            if (!list.isEmpty()) {
                System.out.println("\n📊 Pratinjau Aktivitas Tapping Kartu (Max 3):");
                for (int i = 0; i < Math.min(3, list.size()); i++) {
                    LogAbsensi log = list.get(i);
                    System.out.println("   [" + (i+1) + "] Jam: " + log.getWaktu() + " | Karyawan: " + log.getNamaKaryawan() + " | Status: " + log.getStatus());
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Error memuat data Log Absensi: " + e.getMessage());
        }
        System.out.println();
    }

    private static void launchDashboard() {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new DashboardPage().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static void launchDataKaryawan() {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new DataKaryawanPage().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
