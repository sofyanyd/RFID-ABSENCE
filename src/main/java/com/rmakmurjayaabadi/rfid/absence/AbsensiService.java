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

        // 2. Ambil pengaturan jam kerja (Shift)
        Shift aturanShift = shiftDAO.findOne(new Document()); // Mengambil dokumen pertama
        
        if (aturanShift == null) {
            System.out.println("⚠️ Pengaturan shift belum diatur di database!");
            return;
        }

        // 3. Logika perhitungan waktu
        LocalTime waktuSekarang = LocalTime.now();
        LocalTime batasToleransi = LocalTime.parse(aturanShift.getBatasToleransi());
        
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

        // 5. Simpan ke MongoDB
        absensiDAO.save(logBaru);
        
        System.out.println("✅ Absensi Berhasil: " + karyawan.getNamaLengkap() + " (" + statusAbsen + ")");
    }
}
