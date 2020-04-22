package com.example.ayoberbagi_mysql.donatur.model;

public class DonasiHistoryModel {
    String id_donasi, id_donatur, kategori, jumlah, foto, id_pj, nama_bencana, lokasi, nama, no_rek, nominal, waktu_donasi, waktu_diterima, bukti, upload_path, konfirmasi, keterangan;

    public DonasiHistoryModel() {
        this.id_donasi = id_donasi;
        this.id_donatur = id_donatur;
        this.kategori = kategori;
        this.jumlah = jumlah;
        this.foto = foto;
        this.id_pj = id_pj;
        this.nama_bencana = nama_bencana;
        this.lokasi = lokasi;
        this.nama = nama;
        this.no_rek = no_rek;
        this.nominal = nominal;
        this.waktu_donasi = waktu_donasi;
        this.waktu_diterima = waktu_diterima;
        this.bukti = bukti;
        this.upload_path = upload_path;
        this.konfirmasi = konfirmasi;
        this.keterangan = keterangan;
    }

    public String getId_donasi() {
        return id_donasi;
    }

    public void setId_donasi(String id_donasi) {
        this.id_donasi = id_donasi;
    }

    public String getId_donatur() {
        return id_donatur;
    }

    public void setId_donatur(String id_donatur) {
        this.id_donatur = id_donatur;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo_rek() {
        return no_rek;
    }

    public void setNo_rek(String no_rek) {
        this.no_rek = no_rek;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getWaktu_donasi() {
        return waktu_donasi;
    }

    public void setWaktu_donasi(String waktu_donasi) {
        this.waktu_donasi = waktu_donasi;
    }

    public String getWaktu_diterima() {
        return waktu_diterima;
    }

    public void setWaktu_diterima(String waktu_diterima) {
        this.waktu_diterima = waktu_diterima;
    }

    public String getBukti() {
        return bukti;
    }

    public void setBukti(String bukti) {
        this.bukti = bukti;
    }

    public String getUpload_path() {
        return upload_path;
    }

    public void setUpload_path(String upload_path) {
        this.upload_path = upload_path;
    }

    public String getKonfirmasi() {
        return konfirmasi;
    }

    public void setKonfirmasi(String konfirmasi) {
        this.konfirmasi = konfirmasi;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
