package com.example.accimap.models;

import java.text.DecimalFormat;

public class Report {
    public String rId;
    public String title;
    public String status;
    public String updateTime;
    public String distance;

    public Report(String title, String status, String updateTime, String distance) {
        this.title = title;
        this.status = status;
        this.updateTime = updateTime;
        this.distance = distance;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getDistance() {
        return distance;
    }
    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

}
