package com.example.ayoberbagi_mysql.config;

public class config {

    //PENTING! JANGAN LUPA GANTI IP SESUAI DENGAN IP KOMPUTER DIMANA DATA PHP BERADA

    //IP Tethering
//    public static final String URL = "http://192.168.43.8/ayoberbagi/proses.php";
//    public static final String URL_KOSONGAN = "http://192.168.43.8/";

    //IP Rumah
    public static final String URL = "http://192.168.1.12/ayoberbagi/proses.php";
    public static final String URL_KOSONGAN = "http://192.168.1.12/";

    //IP Wifi Kontrakan
//    public static final String URL = "http://192.168.1.19/ayoberbagi/proses.php";
//    public static final String URL_KOSONGAN = "http://192.168.1.19/";

    //IP kos Ilham Kafe Munjul
//    public static final String URL = "http://192.168.137.13/ayoberbagi/proses.php";
//    public static final String URL_KOSONGAN = "http://192.168.137.13/";

    //IP Telkom University
//    public static final String URL = "http://192.168.137.1/ayoberbagi/proses.php";
//    public static final String URL_KOSONGAN = "http://192.168.137.1/";

    //IP Ilham Kost35
//    public static final String URL = "http://192.168.1.16/ayoberbagi/proses.php";
//    public static final String URL_KOSONGAN = "http://192.168.1.16/";


    public static final String URL_GAMBAR = URL_KOSONGAN + "kuliah/AyoBerbagi/assets/upload/";
    public static final String URL_NOIMAGE = URL_GAMBAR + "noimage.jpg";
    public static final String URL_BUKTI = URL_KOSONGAN + "kuliah/AyoBerbagi/assets/upload/";

    public static final String URL_LOGIN = URL + "?menu=login";
    public static final String URL_REGISTER = URL + "?menu=register";
    public static final String URL_VIEW_BENCANA = URL + "?menu=view_bencana";
    public static final String URL_VIEW_SEMUA_DITERIMA = URL + "?menu=view_semua_diterima";
    public static final String URL_SELECT_BENCANA = URL + "?menu=select_bencana";
    public static final String URL_VIEW_DISTRIBUSI = URL + "?menu=view_distribusi";
    public static final String URL_DONASI_TERKINI = URL + "?menu=donasi_terkini";
    public static final String URL_DONASI = URL + "?menu=add_donasi";
    public static final String URL_DONASI_BARANG = URL + "?menu=donasi_barang";
    public static final String URL_VIEW_LAPORAN = URL + "?menu=view_laporan";
    public static final String URL_DETAIL_LAPORAN = URL + "?menu=detail_laporan";
    public static final String URL_UPLOAD_BUKTI = URL + "?menu=upload_bukti";
    public static final String URL_UPLOAD_DISTRIBUSI = URL + "?menu=upload_distribusi";
    public static final String URL_UPLOAD_PP = URL + "?menu=edit_foto_profile";
    public static final String URL_EDIT_AKUN = URL + "?menu=edit_akun";

    public static final String URL_PROFILE = URL + "?menu=view_profile";
    public static final String URL_CEK_PROFILE = URL + "?menu=cek_profile";
    public static final String URL_BEFORE_RESET_PASS = URL + "?menu=before_reset_pass";
    public static final String URL_NEW_PASS = URL + "?menu=ubah_password";
    public static final String URL_PROFILE_PJ = URL + "?menu=view_profile_pj";
    public static final String URL_VIEW_DONASI_PROSES = URL + "?menu=view_donasi_proses";
    public static final String URL_VIEW_DONASI_HISTORY = URL + "?menu=view_donasi_history";
    public static final String URL_VIEW_RELAWAN_PROSES = URL + "?menu=view_relawan_proses";
    public static final String URL_VIEW_RELAWAN_DITERIMA = URL + "?menu=view_relawan_diterima";
    public static final String URL_INFO_RELAWAN = URL + "?menu=info_relawan";
    public static final String URL_TERIMA_DONASI = URL + "?menu=terima_donasi";
    public static final String URL_TOLAK_DONASI = URL + "?menu=tolak_donasi";

    public static final String URL_DASHBOARD_RELAWAN = URL + "?menu=dashboard_relawan";
    public static final String URL_HOME_RELAWAN = URL + "?menu=home_relawan";

    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_ID = "id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_AKSES = "akses";
    public static final String KEY_NOKTP = "no_ktp";
    public static final String KEY_EMAIL = "email";

    //Donasi
    public static final String KEY_ID_BENCANA = "id_bencana";
    public static final String KEY_ID_DONATUR = "id_donatur";
    public static final String KEY_ID_DONASI = "id_donasi";
    public static final String KEY_ID_PJ = "id_pj";
    public static final String KEY_NOMINAL = "nominal";
    public static final String KEY_ANONIM = "anonim";


    //JSON Tags
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";
    public static final String TAG_PASSWORD = "password";

    public static final String SUCCESS_LOGIN = "success";
    public static final String SUCCESS_ADMIN = "admin";
    public static final String SUCCESS_USER = "user";
    public static final String SUCCESS_RELAWAN = "relawan";
}
