/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rmakmurjayaabadi.rfid.absence;

import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 *
 */
public class TesKoneksi {
    public static void main(String[] args) {
        try {
            System.out.println("🔄 Sedang mencoba menghubungkan ke database...");
            
            // 1. Ambil instance database dari MongoManager
            MongoDatabase database = MongoManager.getDatabase();
            
            if (database == null) {
                System.err.println("❌ STATUS: DATABASE NULL! Periksa service MongoDB di laptopmu.");
                return;
            }
            
            // 2. Gunakan perintah ping murni untuk verifikasi handshake server
            Document pingCommand = new Document("ping", 1);
            database.runCommand(pingCommand);
            
            System.out.println("=========================================");
            System.out.println("✅ STATUS: KONEKSI BERHASIL!");
            System.out.println("Terhubung ke Database: " + database.getName());
            System.out.println("=========================================");
            
            // 3. Menampilkan daftar koleksi asli yang ada di Compass
            System.out.println("📋 Daftar Koleksi Terdeteksi:");
            for (String name : database.listCollectionNames()) {
                System.out.println("  - " + name);
            }

        } catch (Exception e) {
            System.err.println("=========================================");
            System.err.println("❌ STATUS: KONEKSI GAGAL!");
            System.err.println("Pesan Error: " + e.getMessage());
            System.err.println("=========================================");
            e.printStackTrace();
        }
    }
}
