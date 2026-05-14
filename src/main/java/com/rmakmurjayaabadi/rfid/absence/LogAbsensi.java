package com.rmakmurjayaabadi.rfid.absence;

public class LogAbsensi {
    private String idKaryawan;   // Sesuai JSON
    private String namaKaryawan; // Sesuai JSON
    private String tanggal;      // Sesuai JSON
    private String waktu;        // Sesuai JSON
    private String status;
    private String divisi;       // Tambahkan untuk filter riwayat

    public LogAbsensi() {}

    public LogAbsensi(String idKaryawan, String namaKaryawan, String tanggal, String waktu, String status, String divisi) {
        this.idKaryawan = idKaryawan;
        this.namaKaryawan = namaKaryawan;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.status = status;
        this.divisi = divisi;
    }

    // Getter & Setter
    public String getIdKaryawan() { return idKaryawan; }
    public void setIdKaryawan(String idKaryawan) { this.idKaryawan = idKaryawan; }

    public String getNamaKaryawan() { return namaKaryawan; }
    public void setNamaKaryawan(String namaKaryawan) { this.namaKaryawan = namaKaryawan; }

    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }

    public String getWaktu() { return waktu; }
    public void setWaktu(String waktu) { this.waktu = waktu; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDivisi() { return divisi; }
    public void setDivisi(String divisi) { this.divisi = divisi; }
}