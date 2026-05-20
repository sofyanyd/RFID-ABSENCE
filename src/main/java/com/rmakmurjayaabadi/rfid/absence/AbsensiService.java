/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rmakmurjayaabadi.rfid.absence;

import com.mongodb.client.model.Filters;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.bson.Document;
/**
 *
 * @author sofya
 */
public class AbsensiService {
    private final GenericDAO<Karyawan> karyawanDAO;
    private final GenericDAO<LogAbsensi> absensiDAO;
    private final GenericDAO<Shift> shiftDAO;

    public AbsensiService() {
        // Inisialisasi DAO untuk masing-masing koleksi
        this.karyawanDAO = new GenericDAO<>("karyawan", Karyawan.class);
        this.absensiDAO = new GenericDAO<>("absensi", LogAbsensi.class);
        this.shiftDAO = new GenericDAO<>("shift", Shift.class);
    }

    /**
     * Logika Utama: Memproses tap kartu RFID
     * @param uidRfid UID yang didapat dari scanner/arduino
     */
    public void prosesAbsensi(String uidRfid) {
        // 1. Cari data karyawan berdasarkan UID
        Karyawan karyawan = karyawanDAO.findOne(Filters.eq("uidRfid", uidRfid));

        if (karyawan == null) {
            System.out.println("⚠️ Kartu RFID tidak terdaftar!");
            return; 
        }

        // 2. Ambil pengaturan jam kerja (Shift) dengan pengaman otomatis (Fallback)
        LocalTime batasToleransi;
        try {
            Shift aturanShift = shiftDAO.findOne(new Document()); // Mengambil dokumen pertama
            if (aturanShift != null && aturanShift.getBatasToleransi() != null) {
                batasToleransi = LocalTime.parse(aturanShift.getBatasToleransi());
            } else {
                // Pengaman jika koleksi shift kosong di Compass, set default jam 08:15
                System.out.println("💡 Info: Koleksi 'shift' kosong, menggunakan batas default 08:15:00");
                batasToleransi = LocalTime.of(8, 15, 0);
            }
        } catch (Exception e) {
            System.out.println("💡 Info: Gagal memuat shift, menggunakan batas default 08:15:00");
            batasToleransi = LocalTime.of(8, 15, 0);
        }

        // 3. Logika perhitungan waktu riil
        LocalTime waktuSekarang = LocalTime.now();
        
        // Tentukan status: Jika waktu sekarang lewat dari batas toleransi -> Telat
        String statusAbsen = waktuSekarang.isAfter(batasToleransi) ? "Telat" : "Tepat";

        // 4. Siapkan objek LogAbsensi
        LogAbsensi logBaru = new LogAbsensi(
            karyawan.getIdKaryawan(),
            karyawan.getNamaLengkap(),
            LocalDate.now().toString(), // Format: YYYY-MM-DD
            waktuSekarang.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
            statusAbsen,
            karyawan.getDivisi()
        );

        // 5. Simpan ke MongoDB secara Live!
        absensiDAO.save(logBaru);
        
        System.out.println("🚀 [DATABASE UPDATE] Berhasil insert log absensi!");
        System.out.println("✅ Absensi Berhasil: " + karyawan.getNamaLengkap() + " [" + karyawan.getIdKaryawan() + "] -> Status: " + statusAbsen);
    }
}
