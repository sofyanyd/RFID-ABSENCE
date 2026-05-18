package com.rmakmurjayaabadi.rfid.absence;

import com.mongodb.client.MongoCollection;
import org.bson.conversions.Bson;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementasi Generic DAO untuk MongoDB yang efisien dan reusable.
 * @param <T>
 */
public class GenericDAO<T> implements BaseDAO<T> {
    private MongoCollection<T> collection;
    private final Class<T> clazz;

    // Konstruktor menerima nama koleksi dan kelas entitas untuk mapping otomatis
    public GenericDAO(String collectionName, Class<T> clazz) {
        this.clazz = clazz;
        try {
            if (MongoManager.getDatabase() != null) {
                this.collection = MongoManager.getDatabase().getCollection(collectionName, clazz);
            } else {
                System.out.println("⚠️  MongoDB not available for collection: " + collectionName);
                this.collection = null;
            }
        } catch (Exception e) {
            System.err.println("⚠️  Error accessing MongoDB collection: " + e.getMessage());
            this.collection = null;
        }
    }

    @Override
    public void save(T entity) {
        if (collection != null) {
            try {
                collection.insertOne(entity);
                System.out.println("✅ Saved to MongoDB: " + entity);
            } catch (Exception e) {
                System.err.println("❌ Error saving to MongoDB: " + e.getMessage());
            }
        } else {
            System.out.println("📝 [DEMO MODE] Would save: " + entity);
        }
    }

    @Override
    public void update(Bson filter, T entity) {
        if (collection != null) {
            try {
                collection.replaceOne(filter, entity);
            } catch (Exception e) {
                System.err.println("❌ Error updating MongoDB: " + e.getMessage());
            }
        } else {
            System.out.println("📝 [DEMO MODE] Would update: " + entity);
        }
    }

    @Override
    public void delete(Bson filter) {
        if (collection != null) {
            try {
                collection.deleteOne(filter);
            } catch (Exception e) {
                System.err.println("❌ Error deleting from MongoDB: " + e.getMessage());
            }
        } else {
            System.out.println("📝 [DEMO MODE] Would delete with filter: " + filter);
        }
    }

    @Override
    public List<T> findAll() {
        if (collection != null) {
            try {
                return collection.find().into(new ArrayList<>());
            } catch (Exception e) {
                System.err.println("❌ Error reading from MongoDB: " + e.getMessage());
                System.out.println("⚠️  Falling back to sample data...");
                return getSampleData();
            }
        } else {
            return getSampleData();
        }
    }

    @Override
    public T findOne(Bson filter) {
        if (collection != null) {
            try {
                return collection.find(filter).first();
            } catch (Exception e) {
                System.err.println("❌ Error finding one from MongoDB: " + e.getMessage());
                return null;
            }
        }
        return null;
    }

    @Override
    public List<T> findMany(Bson filter) {
        if (collection != null) {
            try {
                return collection.find(filter).into(new ArrayList<>());
            } catch (Exception e) {
                System.err.println("❌ Error finding many from MongoDB: " + e.getMessage());
                return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }

    /**
     * Generate sample data untuk demo mode (ketika MongoDB tidak tersedia)
     */
    @SuppressWarnings("unchecked")
    private List<T> getSampleData() {
        List<T> list = new ArrayList<>();

        if (clazz == Karyawan.class) {
            System.out.println("📊 Loading sample data for Karyawan...");
            list.add((T) new Karyawan("RFID001", "MJA-001", "Budi Santoso", "IT", "Tetap"));
            list.add((T) new Karyawan("RFID002", "MJA-002", "Siti Aminah", "HRD", "Tetap"));
            list.add((T) new Karyawan("RFID003", "MJA-003", "Andi Rahman", "Marketing", "Kontrak"));
            list.add((T) new Karyawan("RFID004", "MJA-004", "Lisa Jayanti", "Finance", "Tetap"));
            list.add((T) new Karyawan("RFID005", "MJA-005", "Fajar Setiawan", "Production", "Tetap"));
            list.add((T) new Karyawan("RFID023", "MJA-023", "Nina Sari", "HR", "Tetap"));
            list.add((T) new Karyawan("RFID024", "MJA-024", "Rudi Hermawan", "IT", "Kontrak"));
            list.add((T) new Karyawan("RFID025", "MJA-025", "Dewi Lestari", "Finance", "Tetap"));
            list.add((T) new Karyawan("RFID026", "MJA-026", "Ahmad Fauzi", "Production", "Tetap"));
            list.add((T) new Karyawan("RFID027", "MJA-027", "Rina Susanti", "Marketing", "Kontrak"));
            list.add((T) new Karyawan("RFID028", "MJA-028", "Wahyu Pratama", "IT", "Tetap"));
            list.add((T) new Karyawan("RFID029", "MJA-029", "Rian Haris", "Production", "Tetap"));
        } else if (clazz == LogAbsensi.class) {
            System.out.println("📊 Loading sample data for LogAbsensi...");
            list.add((T) new LogAbsensi("MJA-029", "Rian Haris", "2026-05-14", "08:25:00", "Telat", "Production"));
            list.add((T) new LogAbsensi("MJA-027", "Rina Susanti", "2026-05-14", "08:20:00", "Telat", "Marketing"));
            list.add((T) new LogAbsensi("MJA-028", "Wahyu Pratama", "2026-05-14", "08:10:00", "Tepat", "IT"));
            list.add((T) new LogAbsensi("MJA-023", "Nina Sari", "2026-05-14", "08:05:00", "Tepat", "HR"));
            list.add((T) new LogAbsensi("MJA-025", "Dewi Lestari", "2026-05-14", "08:00:00", "Tepat", "Finance"));
            list.add((T) new LogAbsensi("MJA-026", "Ahmad Fauzi", "2026-05-14", "07:50:00", "Tepat", "Production"));
            list.add((T) new LogAbsensi("MJA-024", "Rudi Hermawan", "2026-05-14", "07:45:00", "Tepat", "IT"));
        }

        System.out.println("✅ Loaded " + list.size() + " sample records");
        return list;
    }
}
