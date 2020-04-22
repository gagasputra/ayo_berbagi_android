package com.example.ayoberbagi_mysql.donatur.model;

public class BeritaModel {
    String id_laporan, id_pj, nama_bencana, total_donasi, nama, laporan, gambar1, gambar2, gambar3;

    public BeritaModel() {
        this.id_laporan = id_laporan;
        this.id_pj = id_pj;
        this.nama_bencana = nama_bencana;
        this.total_donasi = total_donasi;
        this.nama = nama;
        this.laporan = laporan;
        this.gambar1 = gambar1;
        this.gambar2 = gambar2;
        this.gambar3 = gambar3;
    }

    public String getId_laporan() {
        return id_laporan;
    }

    public void setId_laporan(String id_laporan) {
        this.id_laporan = id_laporan;
    }

    public String getId_pj() {
        return id_pj;
    }

    public void setId_pj(String id_pj) {
        this.id_pj = id_pj;
    }

    public String getNama_bencana() {
        return nama_bencana;
    }

    public void setNama_bencana(String nama_bencana) {
        this.nama_bencana = nama_bencana;
    }

    public String getTotal_donasi() {
        return total_donasi;
    }

    public void setTotal_donasi(String total_donasi) {
        this.total_donasi = total_donasi;
    }

    public String getNama_relawan() {
        return nama;
    }

    public void setNama_relawan(String nama) {
        this.nama = nama;
    }

    public String getLaporan() {
        return laporan;
    }

    public void setLaporan(String laporan) {
        this.laporan = laporan;
    }

    public String getGambar1() {
        return gambar1;
    }

    public void setGambar1(String gambar1) {
        this.gambar1 = gambar1;
    }

    public String getGambar2() {
        return gambar2;
    }

    public void setGambar2(String gambar2) {
        this.gambar2 = gambar2;
    }

    public String getGambar3() {
        return gambar3;
    }

    public void setGambar3(String gambar3) {
        this.gambar3 = gambar3;
    }
}

