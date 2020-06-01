package com.example.ayoberbagi_mysql.relawan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.ImageLoader;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.example.ayoberbagi_mysql.R;
import com.example.ayoberbagi_mysql.config.config;
import com.squareup.picasso.Picasso;

public class R_DetailDonasiEnd extends AppCompatActivity {

    TextView id_bencana, nama_donatur, nominal, waktu_donasi, kategori, jumlah, waktu_diterima;
    ImageView bukti;
    Context context;

    private ImageLoader imageLoader;
    boolean isImageFitToScreen;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r_detail_donasi_end);

        identified();

        intent = getIntent();
        context = R_DetailDonasiEnd.this;

        id_bencana.setText(intent.getStringExtra("id_bencana"));
        nama_donatur.setText(intent.getStringExtra("nama_donatur"));
        String Tnominal = intent.getStringExtra("nominal");
        if (Tnominal.equalsIgnoreCase("null")) {
            nominal.setText("-");
        } else {
            nominal.setText(Tnominal + ",00");
        }
        String upload_path = intent.getStringExtra("upload_path");
        setProfileImage(config.URL_KOSONGAN + upload_path);
        waktu_donasi.setText(intent.getStringExtra("waktu_donasi"));
        kategori.setText(intent.getStringExtra("kategori"));
        waktu_diterima.setText(intent.getStringExtra("waktu_diterima"));
//        String bukti = intent.getStringExtra("bukti");
        String Tjumlah = intent.getStringExtra("jumlah_total");
        if (Tjumlah.equalsIgnoreCase("null")) {
            jumlah.setText("-");
        } else {
            jumlah.setText(Tjumlah);
        }


//        imageLoader = ImageAdapter.getInstance(context).getImageLoader();
//        final NetworkImageView imageView1 = (NetworkImageView)findViewById(R.id.bukti);
//        imageView1.setImageUrl(config.URL_KOSONGAN + upload_path, imageLoader);
    }

    public void identified() {
        id_bencana = findViewById(R.id.id_bencana);
        nama_donatur = findViewById(R.id.nama_donatur);
        nominal = findViewById(R.id.nominal);
        waktu_diterima = findViewById(R.id.waktu_diterima);
        waktu_donasi = findViewById(R.id.waktu_donasi);
        kategori = findViewById(R.id.kategori);
        jumlah = findViewById(R.id.jumlah);
        bukti = findViewById(R.id.bukti);
    }

    private void setProfileImage(String imgUrl) {
        final ImagePopup imagePopup = new ImagePopup(R_DetailDonasiEnd.this);
        Picasso.get().load(imgUrl).into(bukti);
        imagePopup.initiatePopupWithPicasso(imgUrl);
        bukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePopup.viewPopup();
            }
        });
    }
}
