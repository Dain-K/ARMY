package com.example.myapplication;

public class ListViewItem {

    private String uid;
    private String report_date;
    private String report_time;
    private int isCheck;

    @Override
    public String toString() {
        return "ListViewItem{" +
                "uid='" + uid + '\'' +
                ", report_date='" + report_date + '\'' +
                ", report_time='" + report_time + '\'' +
                ", isCheck=" + isCheck +
                '}';
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getReport_date() {
        return report_date;
    }

    public void setReport_date(String report_date) {
        this.report_date = report_date;
    }

    public String getReport_time() {
        return report_time;
    }

    public void setReport_time(String report_time) {
        this.report_time = report_time;
    }

    public int getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(int isCheck) {
        this.isCheck = isCheck;
    }
}