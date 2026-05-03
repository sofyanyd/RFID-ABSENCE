package com.rmakmurjayaabadi.rfid.absence;

public class Karyawan {

    private String uidRfid;
    private String idKaryawan;
    private String namaKaryawan;
    private String divisi;

    public Karyawan() {
    }

    public Karyawan(String uidRfid, String idKaryawan, String namaLengkap, String departemen) {
        this.uidRfid = uidRfid;
        this.idKaryawan = idKaryawan;
        this.namaKaryawan = namaKaryawan;
        this.divisi = divisi;
    }

    

    @Override
    public String toString() {
        return "Karyawan{" + 
                "uidRfid=" + uidRfid + 
                ", idKaryawan=" + idKaryawan + 
                ", namaLengkap=" + namaKaryawan + 
                ", departemen=" + divisi + '}';
    }

    public String getUidRfid() {
        return uidRfid;
    }

    public void setUidRfid(String uidRfid) {
        this.uidRfid = uidRfid;
    }

    public String getIdKaryawan() {
        return idKaryawan;
    }

    public void setIdKaryawan(String idKaryawan) {
        this.idKaryawan = idKaryawan;
    }

    public String getNamaLengkap() {
        return namaKaryawan;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaKaryawan = namaKaryawan;
    }

    public String getDepartemen() {
        return divisi;
    }

    public void setDepartemen(String departemen) {
        this.divisi = divisi;
    }
    
    

}
