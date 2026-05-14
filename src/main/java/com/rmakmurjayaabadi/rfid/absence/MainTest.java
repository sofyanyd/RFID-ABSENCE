/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rmakmurjayaabadi.rfid.absence;

/**
 *
 * @author sofya
 */
public class MainTest {
    public static void main(String[] args) {
        System.out.println("=== Simulasi Sistem Absensi RFID ===");
        
        // 1. Inisialisasi Service
        AbsensiService absensiService = new AbsensiService();
        
        // 2. Simulasi Tap Kartu dengan UID yang ada di database kamu tadi
        // Pastikan UID ini ada di koleksi 'karyawan' di MongoDB Compass
        String uidSimulasi = "12345678"; 
        
        System.out.println("Mencoba Tap Kartu dengan UID: " + uidSimulasi);
        absensiService.prosesAbsensi(uidSimulasi);
        
        System.out.println("Cek MongoDB Compass sekarang di koleksi 'absensi'!");
    }
}
