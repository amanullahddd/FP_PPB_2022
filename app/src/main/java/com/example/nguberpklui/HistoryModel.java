package com.example.nguberpklui;

public class HistoryModel {
    String judul;
    String nomerhp;
    String tanggal;
    String kondisi;

    public HistoryModel(String judul, String nomerhp, String tanggal, String kondisi) {
        this.judul = judul;
        this.nomerhp = nomerhp;
        this.tanggal = tanggal;
        this.kondisi = kondisi;
    }

    public String getJudul() {
        return judul;
    }

    public String getNomerhp() {
        return nomerhp;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getKondisi() {
        return kondisi;
    }


}
