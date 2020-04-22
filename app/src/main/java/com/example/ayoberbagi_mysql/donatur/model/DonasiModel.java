package com.example.ayoberbagi_mysql.donatur.model;

public class DonasiModel {
    private String id_donasi, nama_donatur, id_bencana, id_donatur, kategori, isi_donasi, waktu_donasi, waktu_diterima, jumlah, foto, path_foto, nominal, bukti, upload_path, konfirmasi, anonim;

    public DonasiModel() {
        this.id_donasi = id_donasi;
        this.nama_donatur = nama_donatur;
        this.id_bencana = id_bencana;
        this.id_donatur = id_donatur;
        this.kategori = kategori;
        this.isi_donasi = isi_donasi;
        this.waktu_donasi = waktu_donasi;
        this.waktu_diterima = waktu_diterima;
        this.jumlah = jumlah;
        this.foto = foto;
        this.path_foto = path_foto;
        this.nominal = nominal;
        this.bukti = bukti;
        this.upload_path = upload_path;
        this.konfirmasi = konfirmasi;
        this.anonim = anonim;
    }

    public String getId_donasi() {
        return id_donasi;
    }

    public void setId_donasi(String id_donasi) {
        this.id_donasi = id_donasi;
    }

    public String getNama_donatur() {
        return nama_donatur;
    }

    public void setNama_donatur(String nama_donatur) {
        this.nama_donatur = nama_donatur;
    }

    public String getId_bencana() {
        return id_bencana;
    }

    public void setId_bencana(String id_bencana) {
        this.id_bencana = id_bencana;
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

    public String getIsi_donasi() {
        return isi_donasi;
    }

    public void setIsi_donasi(String isi_donasi) {
        this.isi_donasi = isi_donasi;
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

    public String getPath_foto() {
        return path_foto;
    }

    public void setPath_foto(String path_foto) {
        this.path_foto = path_foto;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
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

    public String getAnonim() {
        return anonim;
    }

    public void setAnonim(String anonim) {
        this.anonim = anonim;
    }
}