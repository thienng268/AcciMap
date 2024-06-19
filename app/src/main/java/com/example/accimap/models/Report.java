package com.example.accimap.models;

import java.text.DecimalFormat;

public class Report {
    public String rId;
    public String mucdo;
    public String tentainan;
    public String ngaygio;
    public Number songuoichet;
    public Number songuoibithuong;
    public Number kinhdo;
    public Number vido;

    public Report() {
        // Default constructor required for calls to DataSnapshot.getValue(Report.class)
    }
    public Report(String mucdo, String tentainan, String ngaygio, Number songuoichet,Number songuoibithuong, Number kinhdo, Number vido, String rId) {
        this.mucdo = mucdo;
        this.tentainan = tentainan;
        this.ngaygio = ngaygio;
        this.songuoichet = songuoichet;
        this.songuoibithuong = songuoibithuong;
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
    public Number getSonguoichet() {
        return songuoichet;
    }

    public void setSonguoichet(Number songuoichet) {
        this.songuoichet = songuoichet;
    }
    public Number getSonguoibithuong() {
        return songuoibithuong;
    }

    public void setSonguoibithuong(Number songuoibithuong) {
        this.songuoibithuong = songuoibithuong;
    }
    public Number getKinhdo() {
        return kinhdo;
    }

    public void setKinhdo(Number kinhdo) {
        this.kinhdo = kinhdo;
    }
    public Number getVido() {
        return vido;
    }

    public void setVido(Number vido) {
        this.vido = vido;
    }

}
