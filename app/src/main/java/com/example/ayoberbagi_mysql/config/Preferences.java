package com.example.ayoberbagi_mysql.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ayoberbagi_mysql.donatur.model.DonaturModel;
import com.example.ayoberbagi_mysql.relawan.model.RelawanModel;

public class Preferences {

    SharedPreferences settings;
    SharedPreferences.Editor editor;
    Context context;

    private final String PREF_NAME = "AYO_BERBAGI_PREF";

    public Preferences() {
        super();
    }

    public Preferences(Context context) {
        super();
        this.context = context;
    }

    public void setUserSession(String id, String username, String idDonatur,
                               String nama_donatur, String no_ktp, String email, String telp, String alamat){
        settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.putString("id", id);
        editor.putString("username", username);
        editor.putString("idDonatur", idDonatur);
        editor.putString("nama_donatur", nama_donatur);
        editor.putString("no_ktp", no_ktp);
        editor.putString("email", email);
        editor.putString("telp", telp);
        editor.putString("alamat", alamat);
        editor.putBoolean("session", true);

        editor.commit();
    }

    public void setRelawanSession(String id, String username, String idPj){
        settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.putString("id", id);
        editor.putString("username", username);
        editor.putString("id_pj", idPj);
        editor.putBoolean("session_relawan", true);

        editor.commit();
    }

    public Boolean getUserStatus(){
        settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean("session", false);
    }

    public Boolean getRelawanStatus(){
        settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean("session_relawan", false);
    }

    public DonaturModel getUserSession(){
        DonaturModel dm;
        settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        String id = settings.getString("id", null);
        String username = settings.getString("username", null);
        String idDonatur = settings.getString("idDonatur", null);
        String nama = settings.getString("nama_donatur", null);
        String no_ktp = settings.getString("no_ktp", null);
        String email = settings.getString("email", null);
        String telp = settings.getString("telp", null);
        String alamat = settings.getString("alamat", null);

        return new DonaturModel(id, username, idDonatur, nama, no_ktp, email, telp, alamat);
    }

    public RelawanModel getRelawanSession(){
        RelawanModel rm;
        settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        String id = settings.getString("id", null);
        String username = settings.getString("username", null);
        String idPj = settings.getString("id_pj", null);

        return new RelawanModel(id, username, idPj);
    }

    public void destroyUserSession(){
        settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.commit();
    }
}
