package com.rmakmurjayaabadi.rfid.absence;

/**
 * Model untuk Shift kerja karyawan
 * @author MJA IT Team
 */
public class Shift {
    private String jamMasuk;
    private String batasToleransi;
    private boolean isActive;

    public Shift() {}

    public Shift(String jamMasuk, String batasToleransi, boolean isActive) {
        this.jamMasuk = jamMasuk;
        this.batasToleransi = batasToleransi;
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Shift{" +
                "jamMasuk='" + jamMasuk + '\'' +
                ", batasToleransi='" + batasToleransi + '\'' +
                ", isActive=" + isActive +
                '}';
    }

    // Getter & Setter
    public String getJamMasuk() { return jamMasuk; }
    public void setJamMasuk(String jamMasuk) { this.jamMasuk = jamMasuk; }

    public String getBatasToleransi() { return batasToleransi; }
    public void setBatasToleransi(String batasToleransi) { this.batasToleransi = batasToleransi; }

    public boolean isIsActive() { return isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }
}
