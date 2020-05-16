package com.example.ayoberbagi_mysql.donatur.model;

public class BeritaModel {
    String id_distribusi, id_bencana, id_pj, nama_bencana, tanggal_distribusi, tgl_akhir_distribusi, lokasi_distribusi, total_donasi, nama, laporan, gambar1, gambar2, gambar3, status;

    public BeritaModel() {
        this.id_distribusi = id_distribusi;
        this.id_bencana = id_bencana;
        this.id_pj = id_pj;
        this.nama_bencana = nama_bencana;
        this.tanggal_distribusi = tanggal_distribusi;
        this.tgl_akhir_distribusi = tgl_akhir_distribusi;
        this.lokasi_distribusi = lokasi_distribusi;
        this.total_donasi = total_donasi;
        this.nama = nama;
        this.laporan = laporan;
        this.gambar1 = gambar1;
        this.gambar2 = gambar2;
        this.gambar3 = gambar3;
        this.status = status;
    }

    public String getId_distribusi() {
        return id_distribusi;
    }

    public void setId_distribusi(String id_distribusi) {
        this.id_distribusi = id_distribusi;
    }

    public String getId_bencana() {
        return id_bencana;
    }

    public void setId_bencana(String id_bencana) {
        this.id_bencana = id_bencana;
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

    public String getTanggal_distribusi() {
        return tanggal_distribusi;
    }

    public void setTanggal_distribusi(String tanggal_distribusi) {
        this.tanggal_distribusi = tanggal_distribusi;
    }

    public String getTgl_akhir_distribusi() {
        return tgl_akhir_distribusi;
    }

    public void setTgl_akhir_distribusi(String tgl_akhir_distribusi) {
        this.tgl_akhir_distribusi = tgl_akhir_distribusi;
    }

    public String getLokasi_distribusi() {
        return lokasi_distribusi;
    }

    public void setLokasi_distribusi(String lokasi_distribusi) {
        this.lokasi_distribusi = lokasi_distribusi;
    }

    public String getTotal_donasi() {
        return total_donasi;
    }

    public void setTotal_donasi(String total_donasi) {
        this.total_donasi = total_donasi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}