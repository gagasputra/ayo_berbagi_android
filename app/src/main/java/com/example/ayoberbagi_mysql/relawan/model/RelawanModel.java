package com.example.ayoberbagi_mysql.relawan.model;

public class RelawanModel {
    private String id, username, idPj;

    public RelawanModel(String id, String username, String idPj) {
        this.id = id;
        this.username = username;
        this.idPj = idPj;
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

    public String getIdPj() {
        return idPj;
    }

    public void setIdPj(String idPj) {
        this.idPj = idPj;
    }
}