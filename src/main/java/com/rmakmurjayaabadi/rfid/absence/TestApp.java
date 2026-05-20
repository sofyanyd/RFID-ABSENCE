package com.rmakmurjayaabadi.rfid.absence;

import com.rmakmurjayaabadi.rfid.absence.GUI.DashboardPage;
import com.rmakmurjayaabadi.rfid.absence.GUI.DataKaryawanPage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
/**
 * Test class untuk verify aplikasi berjalan dengan baik
 * Run: java -cp "target/classes" com.rmakmurjayaabadi.rfid.absence.TestApp
 */
public class TestApp {

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(75));
        System.out.println("🧪 RFID Absence System - Live Tapping & Simulation Suite");
        System.out.println("=".repeat(75));
        System.out.println();

        // 1. Jalankan verifikasi koneksi cluster
        testMongoDBConnection();
        
        // 2. Jalankan logika simulasi tapping masuk secara acak dan push ke DB
        simulasiTappingMasukLive();

        // Navigasi GUI via parameter argumen NetBeans jika dibutuhkan
        if (args.length > 0 && args[0].equals("--gui")) {
            launchDashboard();
        } else if (args.length > 0 && args[0].equals("--data-karyawan")) {
            launchDataKaryawan();
        } else {
            System.out.println("\n💡 Tip: Run langsung file DashboardPage.java jika ingin melihat UI");
        }

        System.out.println("\n" + "=".repeat(75));
        System.out.println("✅ Seluruh simulasi test push database selesai!");
        System.out.println("=".repeat(75) + "\n");
    }

    private static void testMongoDBConnection() {
        System.out.println("📝 Test 1: Verifikasi Koneksi Driver MongoDB");
        System.out.println("-".repeat(75));
        try {
            MongoManager.getDatabase();
            if (MongoManager.isConnected()) {
                System.out.println("✅ Driver Terkoneksi Aktif ke Cluster Lokal!");
            } else {
                System.out.println("⚠️  MongoDB Offline / Menggunakan Demo Mode");
            }
        } catch (Exception e) {
            System.err.println("❌ Error koneksi: " + e.getMessage());
        }
        System.out.println();
    }

    private static void simulasiTappingMasukLive() {
        System.out.println("📝 Test 2: Pengujian Alur Absensi lewat AbsensiService");
        System.out.println("-".repeat(75));
        try {
            // 1. Ambil salah satu data UID RFID secara live dari database karyawan
            GenericDAO<Karyawan> karyawanDao = new GenericDAO<>("karyawan", Karyawan.class);
            List<Karyawan> listKaryawan = karyawanDao.findAll();

            if (listKaryawan.isEmpty()) {
                System.out.println("⚠️ Simulasi gagal: Koleksi data karyawan kosong!");
                return;
            }

            // 2. Acak karyawan yang akan melakukan tapping kartu
            Random rand = new Random();
            Karyawan kandidat = listKaryawan.get(rand.nextInt(listKaryawan.size()));
            String uidScanSimulasi = kandidat.getUidRfid();

            System.out.println("📟 [SCANNER RFID] Kartu Terbaca dengan UID: " + uidScanSimulasi);
            System.out.println("⏳ Mengirim UID ke AbsensiService...");

            // 3. PANGGIL ABSENSI SERVICE YANG LU PERTANYAKAN
            AbsensiService service = new AbsensiService();
            service.prosesAbsensi(uidScanSimulasi); // Logika pemrosesan database berjalan di sini

            System.out.println("-".repeat(75));
            
            // 4. Tarik ulang log absensi untuk memverifikasi live update di MongoDB
            GenericDAO<LogAbsensi> absensiDao = new GenericDAO<>("absensi", LogAbsensi.class);
            List<LogAbsensi> totalLog = absensiDao.findAll();
            System.out.println("📊 Verifikasi MongoDB: Total baris log di koleksi 'absensi' sekarang: " + totalLog.size());

        } catch (Exception e) {
            System.err.println("❌ Gagal memproses service absensi: " + e.getMessage());
            e.printStackTrace();
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
