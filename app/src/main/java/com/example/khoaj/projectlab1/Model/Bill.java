package com.example.khoaj.projectlab1.Model;

public class Bill {
    String ma, ngay;

    public Bill(String ma, String ngay) {
        this.ma = ma;
        this.ngay = ngay;
    }


    public Bill() {

    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }
}
