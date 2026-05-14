/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rmakmurjayaabadi.rfid.absence;

/**
 *
 * @author sofya
 */
public class Shift {
    private String jamMasuk;
    private String batasToleransi;
    private boolean isActive;

    public Shift() {}

    public String getJamMasuk() { return jamMasuk; }
    public void setJamMasuk(String jamMasuk) { this.jamMasuk = jamMasuk; }

    public String getBatasToleransi() { return batasToleransi; }
    public void setBatasToleransi(String batasToleransi) { this.batasToleransi = batasToleransi; }

    public boolean isIsActive() { return isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }
}
