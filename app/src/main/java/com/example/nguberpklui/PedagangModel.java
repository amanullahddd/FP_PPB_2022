package com.example.nguberpklui;

public class PedagangModel {
    String judulDagangan;
    String noHP;
    String alamatPedagang;
    String latLongDagang;
    String detailKeterangan;
    int image;

    public PedagangModel(String judulDagangan, String noHP, String alamatPedagang, String latLongDagang, String detailKeterangan, int image) {
        this.judulDagangan = judulDagangan;
        this.noHP = noHP;
        this.alamatPedagang = alamatPedagang;
        this.latLongDagang = latLongDagang;
        this.detailKeterangan = detailKeterangan;
        this.image = image;
    }

    public String getJudulDagangan() {
        return judulDagangan;
    }

    public String getNoHP() {
        return noHP;
    }

    public String getAlamatPedagang() {
        return alamatPedagang;
    }

    public String getLatLongDagang() {
        return latLongDagang;
    }

    public String getDetailKeterangan() {
        return detailKeterangan;
    }

    public int getImage() {
        return image;
    }


}
