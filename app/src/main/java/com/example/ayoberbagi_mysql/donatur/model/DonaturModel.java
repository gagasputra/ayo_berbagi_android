package com.example.ayoberbagi_mysql.donatur.model;

public class DonaturModel {
    private String id, username, idDonatur, nama, no_ktp, email, telp, alamat;

    public DonaturModel(String id, String username, String idDonatur, String nama, String no_ktp, String email, String telp, String alamat) {
        this.id = id;
        this.username = username;
        this.idDonatur = idDonatur;
        this.nama = nama;
        this.no_ktp = no_ktp;
        this.email = email;
        this.telp = telp;
        this.alamat = alamat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdDonatur() {
        return idDonatur;
    }

    public void setIdDonatur(String idDonatur) {
        this.idDonatur = idDonatur;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo_ktp() {
        return no_ktp;
    }

    public void setNo_ktp(String no_ktp) {
        this.no_ktp = no_ktp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}