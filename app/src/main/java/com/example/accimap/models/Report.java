package com.example.accimap.models;

public class Report {
    public String rId;
    public String mucdo;
    public String tentainan;
    public String ngaygio;
    public int songuoichet;
    public int songuoibithuong;
    public Double kinhdo;
    public Double vido;

    public Report() {
        // Default constructor required for calls to DataSnapshot.getValue(Report.class)
    }

    public Report(String tentainan, String ngaygio, String mucdo, int songuoibithuong, int songuoichet, Double kinhdo, Double vido, String rId) {
        this.tentainan = tentainan;
        this.ngaygio = ngaygio;
        this.mucdo = mucdo;
        this.songuoibithuong = songuoibithuong;
        this.songuoichet = songuoichet;
        this.kinhdo = kinhdo;
        this.vido = vido;
        this.rId = rId;
    }

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    public String getMucdo() {
        return mucdo;
    }

    public void setMucdo(String mucdo) {
        this.mucdo = mucdo;
    }

    public String getTentainan() {
        return tentainan;
    }

    public void setTentainan(String tentainan) {
        this.tentainan = tentainan;
    }

    public String getNgaygio() {
        return ngaygio;
    }

    public void setNgaygio(String ngaygio) {
        this.ngaygio = ngaygio;
    }

    public int getSonguoichet() {
        return songuoichet;
    }

    public void setSonguoichet(int songuoichet) {
        this.songuoichet = songuoichet;
    }

    public int getSonguoibithuong() {
        return songuoibithuong;
    }

    public void setSonguoibithuong(int songuoibithuong) {
        this.songuoibithuong = songuoibithuong;
    }

    public Double getKinhdo() {
        return kinhdo;
    }

    public void setKinhdo(Double kinhdo) {
        this.kinhdo = kinhdo;
    }

    public Double getVido() {
        return vido;
    }

    public void setVido(Double vido) {
        this.vido = vido;
    }
}
