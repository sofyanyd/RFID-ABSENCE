package com.rmakmurjayaabadi.rfid.absence;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
/**
 * Singleton class untuk manage koneksi MongoDB
 * Dengan fallback graceful jika MongoDB tidak tersedia
 */
public class MongoManager {
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    
    // 1. PERBAIKAN: Sesuaikan dengan nama database asli di MongoDB Compass kamu
    private static final String DATABASE_NAME = "RFID-ABSENCE"; 
    private static boolean connectionAttempted = false;

    // Private constructor untuk Singleton
    private MongoManager() {}

    /**
     * Initialize koneksi MongoDB (dipanggil otomatis saat pertama kali getDatabase())
     */
    private static void connect() {
        if (connectionAttempted) {
            return; // Jangan coba lagi jika sudah pernah dicoba
        }

        connectionAttempted = true;

        try {
            System.out.println("🔄 Attempting to connect to MongoDB...");
            
            // 2. PERBAIKAN: Tambahkan Registry Codec agar POJO otomatis aktif (.withCodecRegistry)
            CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
                com.mongodb.MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
            );

            mongoClient = MongoClients.create(CONNECTION_STRING);
            
            // Pasang registry ke database target
            database = mongoClient.getDatabase(DATABASE_NAME).withCodecRegistry(pojoCodecRegistry);

            // Test connection dengan ping
            database.listCollectionNames().first();

            System.out.println("✅ Connected to MongoDB: " + DATABASE_NAME + " with POJO Mapping Support!");
        } catch (Exception e) {
            System.err.println("⚠️  MongoDB Connection Failed: " + e.getMessage());
            System.out.println("⚠️  Aplikasi akan menggunakan sample data (demo mode)");
            System.out.println("💡 Tip: Install MongoDB jika ingin data tersimpan permanen");
            mongoClient = null;
            database = null;
        }
    }

    /**
     * Get database instance
     * @return MongoDatabase atau null jika koneksi gagal
     */
    public static MongoDatabase getDatabase() {
        if (!connectionAttempted) {
            connect();
        }
        return database;
    }

    /**
     * Check apakah MongoDB tersedia
     * @return true jika terkoneksi
     */
    public static boolean isConnected() {
        try {
            if (database != null) {
                database.listCollectionNames().first();
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * Close koneksi MongoDB
     */
    public static void close() {
        if (mongoClient != null) {
            try {
                mongoClient.close();
                System.out.println("✅ MongoDB connection closed");
            } catch (Exception e) {
                System.err.println("⚠️  Error closing MongoDB: " + e.getMessage());
            } finally {
                mongoClient = null;
                database = null;
                connectionAttempted = false;
            }
        }
    }

    /**
     * Reset connection (untuk testing)
     */
    public static void reset() {
        close();
        connectionAttempted = false;
    }
}
