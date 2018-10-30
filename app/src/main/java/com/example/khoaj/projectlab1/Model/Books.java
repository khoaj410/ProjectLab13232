package com.example.khoaj.projectlab1.Model;

public class Books {
    String masach, name, maTheLoai, author, soluong, nxb;

    public Books(String masach, String name, String maTheLoai, String author, String soluong, String nxb) {
        this.masach = masach;
        this.name = name;
        this.maTheLoai = maTheLoai;
        this.author = author;
        this.soluong = soluong;
        this.nxb = nxb;
    }

    public Books() {

    }

    public String getMasach() {
        return masach;
    }

    public void setMasach(String masach) {
        this.masach = masach;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(String maTheLoai) {
        this.maTheLoai = maTheLoai;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public String getNxb() {
        return nxb;
    }

    public void setNxb(String nxb) {
        this.nxb = nxb;
    }
}


