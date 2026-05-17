package com.rmakmurjayaabadi.rfid.absence;

public class Karyawan {
    private String uidRfid;
    private String idKaryawan;
    private String namaLengkap; // Sesuai JSON
    private String divisi;      // Sesuai JSON
    private String kontrak;     // Tambahkan ini agar sinkron dengan tabel di UI

    public Karyawan() {}

    public Karyawan(String uidRfid, String idKaryawan, String namaLengkap, String divisi, String kontrak) {
        this.uidRfid = uidRfid;
        this.idKaryawan = idKaryawan;
        this.namaLengkap = namaLengkap;
        this.divisi = divisi;
        this.kontrak = kontrak;
    }

    @Override
    public String toString() {
        return "Karyawan{" + "uidRfid=" + uidRfid + ", idKaryawan=" + idKaryawan +
               ", namaLengkap=" + namaLengkap + ", divisi=" + divisi + ", kontrak=" + kontrak + '}';
    }

    // Getter & Setter (Penting: Nama method harus pas dengan nama variabel)
    public String getUidRfid() { return uidRfid; }
    public void setUidRfid(String uidRfid) { this.uidRfid = uidRfid; }

    public String getIdKaryawan() { return idKaryawan; }
    public void setIdKaryawan(String idKaryawan) { this.idKaryawan = idKaryawan; }

    public String getNamaLengkap() { return namaLengkap; }
    public void setNamaLengkap(String namaLengkap) { this.namaLengkap = namaLengkap; }

    public String getDivisi() { return divisi; }
    public void setDivisi(String divisi) { this.divisi = divisi; }

    public String getKontrak() { return kontrak; }
    public void setKontrak(String kontrak) { this.kontrak = kontrak; }
}
