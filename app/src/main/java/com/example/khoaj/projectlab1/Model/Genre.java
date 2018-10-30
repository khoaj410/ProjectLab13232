package com.example.khoaj.projectlab1.Model;

public class Genre {
    String ma,name;

    public Genre(String ma, String name){
        this.ma = ma;
        this.name = name;

    }

    public Genre() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    @Override
    public String toString(){
        return ma;
    }
}
